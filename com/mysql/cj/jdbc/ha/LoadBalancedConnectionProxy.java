package com.mysql.cj.jdbc.ha;

import com.mysql.cj.*;
import com.mysql.cj.conf.url.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.jdbc.exceptions.*;
import java.util.stream.*;
import com.mysql.cj.interceptors.*;
import java.sql.*;
import java.util.concurrent.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.jdbc.*;
import java.lang.reflect.*;
import java.util.*;

public class LoadBalancedConnectionProxy extends MultiHostConnectionProxy implements PingTarget
{
    private ConnectionGroup connectionGroup;
    private long connectionGroupProxyID;
    protected Map<String, ConnectionImpl> liveConnections;
    private Map<String, Integer> hostsToListIndexMap;
    private Map<ConnectionImpl, String> connectionsToHostsMap;
    private long totalPhysicalConnections;
    private long[] responseTimes;
    private int retriesAllDown;
    private BalanceStrategy balancer;
    private int globalBlocklistTimeout;
    private static Map<String, Long> globalBlocklist;
    private int hostRemovalGracePeriod;
    private Set<String> hostsToRemove;
    private boolean inTransaction;
    private long transactionStartTime;
    private long transactionCount;
    private LoadBalanceExceptionChecker exceptionChecker;
    private static Class<?>[] INTERFACES_TO_PROXY;
    private static LoadBalancedConnection nullLBConnectionInstance;
    
    public static LoadBalancedConnection createProxyInstance(final ConnectionUrl connectionUrl) throws SQLException {
        final LoadBalancedConnectionProxy connProxy = new LoadBalancedConnectionProxy(connectionUrl);
        return (LoadBalancedConnection)Proxy.newProxyInstance(LoadBalancedConnection.class.getClassLoader(), LoadBalancedConnectionProxy.INTERFACES_TO_PROXY, connProxy);
    }
    
    public LoadBalancedConnectionProxy(final ConnectionUrl connectionUrl) throws SQLException {
        this.connectionGroup = null;
        this.connectionGroupProxyID = 0L;
        this.totalPhysicalConnections = 0L;
        this.globalBlocklistTimeout = 0;
        this.hostRemovalGracePeriod = 0;
        this.hostsToRemove = new HashSet<String>();
        this.inTransaction = false;
        this.transactionStartTime = 0L;
        this.transactionCount = 0L;
        final Properties props = connectionUrl.getConnectionArgumentsAsProperties();
        final String group = props.getProperty(PropertyKey.loadBalanceConnectionGroup.getKeyName(), null);
        boolean enableJMX = false;
        final String enableJMXAsString = props.getProperty(PropertyKey.ha_enableJMX.getKeyName(), "false");
        try {
            enableJMX = Boolean.parseBoolean(enableJMXAsString);
        }
        catch (Exception e2) {
            throw SQLError.createSQLException(Messages.getString("MultihostConnection.badValueForHaEnableJMX", new Object[] { enableJMXAsString }), "S1009", null);
        }
        List<HostInfo> hosts;
        if (!StringUtils.isNullOrEmpty(group) && LoadBalanceConnectionUrl.class.isAssignableFrom(connectionUrl.getClass())) {
            this.connectionGroup = ConnectionGroupManager.getConnectionGroupInstance(group);
            if (enableJMX) {
                ConnectionGroupManager.registerJmx();
            }
            this.connectionGroupProxyID = this.connectionGroup.registerConnectionProxy(this, ((LoadBalanceConnectionUrl)connectionUrl).getHostInfoListAsHostPortPairs());
            hosts = ((LoadBalanceConnectionUrl)connectionUrl).getHostInfoListFromHostPortPairs(this.connectionGroup.getInitialHosts());
        }
        else {
            hosts = connectionUrl.getHostsList();
        }
        final int numHosts = this.initializeHostsSpecs(connectionUrl, hosts);
        this.liveConnections = new HashMap<String, ConnectionImpl>(numHosts);
        this.hostsToListIndexMap = new HashMap<String, Integer>(numHosts);
        for (int i = 0; i < numHosts; ++i) {
            this.hostsToListIndexMap.put(this.hostsList.get(i).getHostPortPair(), i);
        }
        this.connectionsToHostsMap = new HashMap<ConnectionImpl, String>(numHosts);
        this.responseTimes = new long[numHosts];
        final String retriesAllDownAsString = props.getProperty(PropertyKey.retriesAllDown.getKeyName(), "120");
        try {
            this.retriesAllDown = Integer.parseInt(retriesAllDownAsString);
        }
        catch (NumberFormatException nfe) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.badValueForRetriesAllDown", new Object[] { retriesAllDownAsString }), "S1009", null);
        }
        final String blocklistTimeoutAsString = props.getProperty(PropertyKey.loadBalanceBlocklistTimeout.getKeyName(), "0");
        try {
            this.globalBlocklistTimeout = Integer.parseInt(blocklistTimeoutAsString);
        }
        catch (NumberFormatException nfe2) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.badValueForLoadBalanceBlocklistTimeout", new Object[] { blocklistTimeoutAsString }), "S1009", null);
        }
        final String hostRemovalGracePeriodAsString = props.getProperty(PropertyKey.loadBalanceHostRemovalGracePeriod.getKeyName(), "15000");
        try {
            this.hostRemovalGracePeriod = Integer.parseInt(hostRemovalGracePeriodAsString);
        }
        catch (NumberFormatException nfe3) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.badValueForLoadBalanceHostRemovalGracePeriod", new Object[] { hostRemovalGracePeriodAsString }), "S1009", null);
        }
        final String strategy = props.getProperty(PropertyKey.ha_loadBalanceStrategy.getKeyName(), "random");
        try {
            final String s = strategy;
            switch (s) {
                case "random": {
                    this.balancer = new RandomBalanceStrategy();
                    break;
                }
                case "bestResponseTime": {
                    this.balancer = new BestResponseTimeBalanceStrategy();
                    break;
                }
                case "serverAffinity": {
                    this.balancer = new ServerAffinityStrategy(props.getProperty(PropertyKey.serverAffinityOrder.getKeyName(), null));
                    break;
                }
                default: {
                    this.balancer = (BalanceStrategy)Class.forName(strategy).newInstance();
                    break;
                }
            }
        }
        catch (Throwable t) {
            throw SQLError.createSQLException(Messages.getString("InvalidLoadBalanceStrategy", new Object[] { strategy }), "S1009", t, null);
        }
        final String autoCommitSwapThresholdAsString = props.getProperty(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName(), "0");
        try {
            Integer.parseInt(autoCommitSwapThresholdAsString);
        }
        catch (NumberFormatException nfe4) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.badValueForLoadBalanceAutoCommitStatementThreshold", new Object[] { autoCommitSwapThresholdAsString }), "S1009", null);
        }
        final String autoCommitSwapRegex = props.getProperty(PropertyKey.loadBalanceAutoCommitStatementRegex.getKeyName(), "");
        if (!"".equals(autoCommitSwapRegex)) {
            try {
                "".matches(autoCommitSwapRegex);
            }
            catch (Exception e3) {
                throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.badValueForLoadBalanceAutoCommitStatementRegex", new Object[] { autoCommitSwapRegex }), "S1009", null);
            }
        }
        try {
            final String lbExceptionChecker = props.getProperty(PropertyKey.loadBalanceExceptionChecker.getKeyName(), StandardLoadBalanceExceptionChecker.class.getName());
            (this.exceptionChecker = (LoadBalanceExceptionChecker)Util.getInstance(lbExceptionChecker, new Class[0], new Object[0], null, Messages.getString("InvalidLoadBalanceExceptionChecker"))).init(props);
        }
        catch (CJException e) {
            throw SQLExceptionsMapping.translateException(e, null);
        }
        this.pickNewConnection();
    }
    
    @Override
    JdbcConnection getNewWrapperForThisAsConnection() throws SQLException {
        return new LoadBalancedMySQLConnection(this);
    }
    
    @Override
    protected void propagateProxyDown(final JdbcConnection proxyConn) {
        for (final JdbcConnection c : this.liveConnections.values()) {
            c.setProxy(proxyConn);
        }
    }
    
    @Deprecated
    public boolean shouldExceptionTriggerFailover(final Throwable t) {
        return this.shouldExceptionTriggerConnectionSwitch(t);
    }
    
    @Override
    boolean shouldExceptionTriggerConnectionSwitch(final Throwable t) {
        return t instanceof SQLException && this.exceptionChecker.shouldExceptionTriggerFailover(t);
    }
    
    @Override
    boolean isSourceConnection() {
        return true;
    }
    
    @Override
    synchronized void invalidateConnection(final JdbcConnection conn) throws SQLException {
        super.invalidateConnection(conn);
        if (this.isGlobalBlocklistEnabled()) {
            final String host = this.connectionsToHostsMap.get(conn);
            if (host != null) {
                this.addToGlobalBlocklist(host);
            }
        }
        this.liveConnections.remove(this.connectionsToHostsMap.get(conn));
        final Object mappedHost = this.connectionsToHostsMap.remove(conn);
        if (mappedHost != null && this.hostsToListIndexMap.containsKey(mappedHost)) {
            final int hostIndex = this.hostsToListIndexMap.get(mappedHost);
            synchronized (this.responseTimes) {
                this.responseTimes[hostIndex] = 0L;
            }
        }
    }
    
    public synchronized void pickNewConnection() throws SQLException {
        if (this.isClosed && this.closedExplicitly) {
            return;
        }
        final List<String> hostPortList = Collections.unmodifiableList((List<? extends String>)this.hostsList.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<? extends T>>)Collectors.toList()));
        if (this.currentConnection == null) {
            this.currentConnection = this.balancer.pickConnection(this, hostPortList, Collections.unmodifiableMap((Map<? extends String, ? extends JdbcConnection>)this.liveConnections), this.responseTimes.clone(), this.retriesAllDown);
            return;
        }
        if (this.currentConnection.isClosed()) {
            this.invalidateCurrentConnection();
        }
        final int pingTimeout = this.currentConnection.getPropertySet().getIntegerProperty(PropertyKey.loadBalancePingTimeout).getValue();
        final boolean pingBeforeReturn = this.currentConnection.getPropertySet().getBooleanProperty(PropertyKey.loadBalanceValidateConnectionOnSwapServer).getValue();
        int hostsTried = 0;
        final int hostsToTry = this.hostsList.size();
        while (hostsTried < hostsToTry) {
            ConnectionImpl newConn = null;
            try {
                newConn = (ConnectionImpl)this.balancer.pickConnection(this, hostPortList, Collections.unmodifiableMap((Map<? extends String, ? extends JdbcConnection>)this.liveConnections), this.responseTimes.clone(), this.retriesAllDown);
                if (this.currentConnection != null) {
                    if (pingBeforeReturn) {
                        newConn.pingInternal(true, pingTimeout);
                    }
                    this.syncSessionState(this.currentConnection, newConn);
                }
                this.currentConnection = newConn;
                return;
            }
            catch (SQLException e) {
                if (this.shouldExceptionTriggerConnectionSwitch(e) && newConn != null) {
                    this.invalidateConnection(newConn);
                }
                ++hostsTried;
                continue;
            }
            break;
        }
        this.isClosed = true;
        this.closedReason = "Connection closed after inability to pick valid new connection during load-balance.";
    }
    
    public synchronized ConnectionImpl createConnectionForHost(final HostInfo hostInfo) throws SQLException {
        final ConnectionImpl conn = super.createConnectionForHost(hostInfo);
        this.liveConnections.put(hostInfo.getHostPortPair(), conn);
        this.connectionsToHostsMap.put(conn, hostInfo.getHostPortPair());
        this.removeFromGlobalBlocklist(hostInfo.getHostPortPair());
        ++this.totalPhysicalConnections;
        for (final QueryInterceptor stmtInterceptor : conn.getQueryInterceptorsInstances()) {
            if (stmtInterceptor instanceof LoadBalancedAutoCommitInterceptor) {
                ((LoadBalancedAutoCommitInterceptor)stmtInterceptor).resumeCounters();
                break;
            }
        }
        return conn;
    }
    
    @Override
    void syncSessionState(final JdbcConnection source, final JdbcConnection target, final boolean readOnly) throws SQLException {
        LoadBalancedAutoCommitInterceptor lbAutoCommitStmtInterceptor = null;
        for (final QueryInterceptor stmtInterceptor : target.getQueryInterceptorsInstances()) {
            if (stmtInterceptor instanceof LoadBalancedAutoCommitInterceptor) {
                lbAutoCommitStmtInterceptor = (LoadBalancedAutoCommitInterceptor)stmtInterceptor;
                lbAutoCommitStmtInterceptor.pauseCounters();
                break;
            }
        }
        super.syncSessionState(source, target, readOnly);
        if (lbAutoCommitStmtInterceptor != null) {
            lbAutoCommitStmtInterceptor.resumeCounters();
        }
    }
    
    public synchronized ConnectionImpl createConnectionForHost(final String hostPortPair) throws SQLException {
        for (final HostInfo hi : this.hostsList) {
            if (hi.getHostPortPair().equals(hostPortPair)) {
                return this.createConnectionForHost(hi);
            }
        }
        return null;
    }
    
    private synchronized void closeAllConnections() {
        for (final Connection c : this.liveConnections.values()) {
            try {
                c.close();
            }
            catch (SQLException ex) {}
        }
        if (!this.isClosed && this.connectionGroup != null) {
            this.connectionGroup.closeConnectionProxy(this);
        }
        this.liveConnections.clear();
        this.connectionsToHostsMap.clear();
    }
    
    @Override
    synchronized void doClose() {
        this.closeAllConnections();
    }
    
    @Override
    synchronized void doAbortInternal() {
        for (final JdbcConnection c : this.liveConnections.values()) {
            try {
                c.abortInternal();
            }
            catch (SQLException ex) {}
        }
        if (!this.isClosed && this.connectionGroup != null) {
            this.connectionGroup.closeConnectionProxy(this);
        }
        this.liveConnections.clear();
        this.connectionsToHostsMap.clear();
    }
    
    @Override
    synchronized void doAbort(final Executor executor) {
        for (final Connection c : this.liveConnections.values()) {
            try {
                c.abort(executor);
            }
            catch (SQLException ex) {}
        }
        if (!this.isClosed && this.connectionGroup != null) {
            this.connectionGroup.closeConnectionProxy(this);
        }
        this.liveConnections.clear();
        this.connectionsToHostsMap.clear();
    }
    
    public synchronized Object invokeMore(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final String methodName = method.getName();
        if (this.isClosed && !this.allowedOnClosedConnection(method) && method.getExceptionTypes().length > 0) {
            if (!this.autoReconnect || this.closedExplicitly) {
                String reason = "No operations allowed after connection closed.";
                if (this.closedReason != null) {
                    reason = reason + " " + this.closedReason;
                }
                for (final Class<?> excls : method.getExceptionTypes()) {
                    if (SQLException.class.isAssignableFrom(excls)) {
                        throw SQLError.createSQLException(reason, "08003", null);
                    }
                }
                throw ExceptionFactory.createException(CJCommunicationsException.class, reason);
            }
            this.currentConnection = null;
            this.pickNewConnection();
            this.isClosed = false;
            this.closedReason = null;
        }
        if (!this.inTransaction) {
            this.inTransaction = true;
            this.transactionStartTime = System.nanoTime();
            ++this.transactionCount;
        }
        Object result = null;
        try {
            result = method.invoke(this.thisAsConnection, args);
            if (result != null) {
                if (result instanceof JdbcStatement) {
                    ((JdbcStatement)result).setPingTarget(this);
                }
                result = this.proxyIfReturnTypeIsJdbcInterface(method.getReturnType(), result);
            }
        }
        catch (InvocationTargetException e) {
            this.dealWithInvocationException(e);
        }
        finally {
            if ("commit".equals(methodName) || "rollback".equals(methodName)) {
                this.inTransaction = false;
                final String host = this.connectionsToHostsMap.get(this.currentConnection);
                if (host != null) {
                    synchronized (this.responseTimes) {
                        final Integer hostIndex = this.hostsToListIndexMap.get(host);
                        if (hostIndex != null && hostIndex < this.responseTimes.length) {
                            this.responseTimes[hostIndex] = System.nanoTime() - this.transactionStartTime;
                        }
                    }
                }
                this.pickNewConnection();
            }
        }
        return result;
    }
    
    @Override
    public synchronized void doPing() throws SQLException {
        SQLException se = null;
        boolean foundHost = false;
        final int pingTimeout = this.currentConnection.getPropertySet().getIntegerProperty(PropertyKey.loadBalancePingTimeout).getValue();
        synchronized (this) {
            for (final HostInfo hi : this.hostsList) {
                final String host = hi.getHostPortPair();
                final ConnectionImpl conn = this.liveConnections.get(host);
                if (conn == null) {
                    continue;
                }
                try {
                    if (pingTimeout == 0) {
                        conn.ping();
                    }
                    else {
                        conn.pingInternal(true, pingTimeout);
                    }
                    foundHost = true;
                }
                catch (SQLException e) {
                    if (host.equals(this.connectionsToHostsMap.get(this.currentConnection))) {
                        this.closeAllConnections();
                        this.isClosed = true;
                        this.closedReason = "Connection closed because ping of current connection failed.";
                        throw e;
                    }
                    if (e.getMessage().equals(Messages.getString("Connection.exceededConnectionLifetime"))) {
                        if (se == null) {
                            se = e;
                        }
                    }
                    else {
                        se = e;
                        if (this.isGlobalBlocklistEnabled()) {
                            this.addToGlobalBlocklist(host);
                        }
                    }
                    this.liveConnections.remove(this.connectionsToHostsMap.get(conn));
                }
            }
        }
        if (!foundHost) {
            this.closeAllConnections();
            this.isClosed = true;
            this.closedReason = "Connection closed due to inability to ping any active connections.";
            if (se != null) {
                throw se;
            }
            ((ConnectionImpl)this.currentConnection).throwConnectionClosedException();
        }
    }
    
    public void addToGlobalBlocklist(final String host, final long timeout) {
        if (this.isGlobalBlocklistEnabled()) {
            synchronized (LoadBalancedConnectionProxy.globalBlocklist) {
                LoadBalancedConnectionProxy.globalBlocklist.put(host, timeout);
            }
        }
    }
    
    public void removeFromGlobalBlocklist(final String host) {
        if (this.isGlobalBlocklistEnabled() && LoadBalancedConnectionProxy.globalBlocklist.containsKey(host)) {
            synchronized (LoadBalancedConnectionProxy.globalBlocklist) {
                LoadBalancedConnectionProxy.globalBlocklist.remove(host);
            }
        }
    }
    
    @Deprecated
    public void removeFromGlobalBlacklist(final String host) {
        this.removeFromGlobalBlocklist(host);
    }
    
    @Deprecated
    public void addToGlobalBlacklist(final String host, final long timeout) {
        this.addToGlobalBlocklist(host, timeout);
    }
    
    public void addToGlobalBlocklist(final String host) {
        this.addToGlobalBlocklist(host, System.currentTimeMillis() + this.globalBlocklistTimeout);
    }
    
    @Deprecated
    public void addToGlobalBlacklist(final String host) {
        this.addToGlobalBlocklist(host);
    }
    
    public boolean isGlobalBlocklistEnabled() {
        return this.globalBlocklistTimeout > 0;
    }
    
    @Deprecated
    public boolean isGlobalBlacklistEnabled() {
        return this.isGlobalBlocklistEnabled();
    }
    
    public synchronized Map<String, Long> getGlobalBlocklist() {
        if (!this.isGlobalBlocklistEnabled()) {
            if (this.hostsToRemove.isEmpty()) {
                return new HashMap<String, Long>(1);
            }
            final HashMap<String, Long> fakedBlocklist = new HashMap<String, Long>();
            for (final String h : this.hostsToRemove) {
                fakedBlocklist.put(h, System.currentTimeMillis() + 5000L);
            }
            return fakedBlocklist;
        }
        else {
            final Map<String, Long> blocklistClone = new HashMap<String, Long>(LoadBalancedConnectionProxy.globalBlocklist.size());
            synchronized (LoadBalancedConnectionProxy.globalBlocklist) {
                blocklistClone.putAll(LoadBalancedConnectionProxy.globalBlocklist);
            }
            final Set<String> keys = blocklistClone.keySet();
            keys.retainAll(this.hostsList.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
            final Iterator<String> i = keys.iterator();
            while (i.hasNext()) {
                final String host = i.next();
                final Long timeout = LoadBalancedConnectionProxy.globalBlocklist.get(host);
                if (timeout != null && timeout < System.currentTimeMillis()) {
                    synchronized (LoadBalancedConnectionProxy.globalBlocklist) {
                        LoadBalancedConnectionProxy.globalBlocklist.remove(host);
                    }
                    i.remove();
                }
            }
            if (keys.size() == this.hostsList.size()) {
                return new HashMap<String, Long>(1);
            }
            return blocklistClone;
        }
    }
    
    @Deprecated
    public synchronized Map<String, Long> getGlobalBlacklist() {
        return this.getGlobalBlocklist();
    }
    
    public void removeHostWhenNotInUse(final String hostPortPair) throws SQLException {
        if (this.hostRemovalGracePeriod <= 0) {
            this.removeHost(hostPortPair);
            return;
        }
        final int timeBetweenChecks = (this.hostRemovalGracePeriod > 1000) ? 1000 : this.hostRemovalGracePeriod;
        synchronized (this) {
            this.addToGlobalBlocklist(hostPortPair, System.currentTimeMillis() + this.hostRemovalGracePeriod + timeBetweenChecks);
            final long cur = System.currentTimeMillis();
            while (System.currentTimeMillis() < cur + this.hostRemovalGracePeriod) {
                this.hostsToRemove.add(hostPortPair);
                if (!hostPortPair.equals(this.currentConnection.getHostPortPair())) {
                    this.removeHost(hostPortPair);
                    return;
                }
                try {
                    Thread.sleep(timeBetweenChecks);
                }
                catch (InterruptedException ex) {}
            }
        }
        this.removeHost(hostPortPair);
    }
    
    public synchronized void removeHost(final String hostPortPair) throws SQLException {
        if (this.connectionGroup != null && this.connectionGroup.getInitialHosts().size() == 1 && this.connectionGroup.getInitialHosts().contains(hostPortPair)) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.0"), null);
        }
        this.hostsToRemove.add(hostPortPair);
        this.connectionsToHostsMap.remove(this.liveConnections.remove(hostPortPair));
        if (this.hostsToListIndexMap.remove(hostPortPair) != null) {
            final long[] newResponseTimes = new long[this.responseTimes.length - 1];
            int newIdx = 0;
            for (final HostInfo hostInfo : this.hostsList) {
                final String host = hostInfo.getHostPortPair();
                if (!this.hostsToRemove.contains(host)) {
                    final Integer idx = this.hostsToListIndexMap.get(host);
                    if (idx != null && idx < this.responseTimes.length) {
                        newResponseTimes[newIdx] = this.responseTimes[idx];
                    }
                    this.hostsToListIndexMap.put(host, newIdx++);
                }
            }
            this.responseTimes = newResponseTimes;
        }
        if (hostPortPair.equals(this.currentConnection.getHostPortPair())) {
            this.invalidateConnection(this.currentConnection);
            this.pickNewConnection();
        }
    }
    
    public synchronized boolean addHost(final String hostPortPair) {
        if (this.hostsToListIndexMap.containsKey(hostPortPair)) {
            return false;
        }
        final long[] newResponseTimes = new long[this.responseTimes.length + 1];
        System.arraycopy(this.responseTimes, 0, newResponseTimes, 0, this.responseTimes.length);
        this.responseTimes = newResponseTimes;
        if (this.hostsList.stream().noneMatch(hi -> hostPortPair.equals(hi.getHostPortPair()))) {
            this.hostsList.add(this.connectionUrl.getHostOrSpawnIsolated(hostPortPair));
        }
        this.hostsToListIndexMap.put(hostPortPair, this.responseTimes.length - 1);
        this.hostsToRemove.remove(hostPortPair);
        return true;
    }
    
    public synchronized boolean inTransaction() {
        return this.inTransaction;
    }
    
    public synchronized long getTransactionCount() {
        return this.transactionCount;
    }
    
    public synchronized long getActivePhysicalConnectionCount() {
        return this.liveConnections.size();
    }
    
    public synchronized long getTotalPhysicalConnectionCount() {
        return this.totalPhysicalConnections;
    }
    
    public synchronized long getConnectionGroupProxyID() {
        return this.connectionGroupProxyID;
    }
    
    public synchronized String getCurrentActiveHost() {
        final JdbcConnection c = this.currentConnection;
        if (c != null) {
            final Object o = this.connectionsToHostsMap.get(c);
            if (o != null) {
                return o.toString();
            }
        }
        return null;
    }
    
    public synchronized long getCurrentTransactionDuration() {
        if (this.inTransaction && this.transactionStartTime > 0L) {
            return System.nanoTime() - this.transactionStartTime;
        }
        return 0L;
    }
    
    static synchronized LoadBalancedConnection getNullLoadBalancedConnectionInstance() {
        if (LoadBalancedConnectionProxy.nullLBConnectionInstance == null) {
            LoadBalancedConnectionProxy.nullLBConnectionInstance = (LoadBalancedConnection)Proxy.newProxyInstance(LoadBalancedConnection.class.getClassLoader(), LoadBalancedConnectionProxy.INTERFACES_TO_PROXY, new NullLoadBalancedConnectionProxy());
        }
        return LoadBalancedConnectionProxy.nullLBConnectionInstance;
    }
    
    static {
        LoadBalancedConnectionProxy.globalBlocklist = new HashMap<String, Long>();
        LoadBalancedConnectionProxy.INTERFACES_TO_PROXY = (Class<?>[])new Class[] { LoadBalancedConnection.class, JdbcConnection.class };
        LoadBalancedConnectionProxy.nullLBConnectionInstance = null;
    }
    
    private static class NullLoadBalancedConnectionProxy implements InvocationHandler
    {
        public NullLoadBalancedConnectionProxy() {
        }
        
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final SQLException exceptionToThrow = SQLError.createSQLException(Messages.getString("LoadBalancedConnectionProxy.unusableConnection"), "25000", 1000001, true, null);
            final Class<?>[] exceptionTypes;
            final Class<?>[] declaredException = exceptionTypes = method.getExceptionTypes();
            for (final Class<?> declEx : exceptionTypes) {
                if (declEx.isAssignableFrom(exceptionToThrow.getClass())) {
                    throw exceptionToThrow;
                }
            }
            throw new IllegalStateException(exceptionToThrow.getMessage(), exceptionToThrow);
        }
    }
}
