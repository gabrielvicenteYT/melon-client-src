package com.mysql.cj.jdbc.ha;

import java.sql.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import com.mysql.cj.conf.*;
import java.util.*;
import java.util.concurrent.*;
import com.mysql.cj.jdbc.*;
import java.lang.reflect.*;
import com.mysql.cj.conf.url.*;

public class ReplicationConnectionProxy extends MultiHostConnectionProxy implements PingTarget
{
    private ReplicationConnection thisAsReplicationConnection;
    protected boolean enableJMX;
    protected boolean allowSourceDownConnections;
    protected boolean allowReplicaDownConnections;
    protected boolean readFromSourceWhenNoReplicas;
    protected boolean readFromSourceWhenNoReplicasOriginal;
    protected boolean readOnly;
    ReplicationConnectionGroup connectionGroup;
    private long connectionGroupID;
    private List<HostInfo> sourceHosts;
    protected LoadBalancedConnection sourceConnection;
    private List<HostInfo> replicaHosts;
    protected LoadBalancedConnection replicasConnection;
    
    public static ReplicationConnection createProxyInstance(final ConnectionUrl connectionUrl) throws SQLException {
        final ReplicationConnectionProxy connProxy = new ReplicationConnectionProxy(connectionUrl);
        return (ReplicationConnection)Proxy.newProxyInstance(ReplicationConnection.class.getClassLoader(), new Class[] { ReplicationConnection.class, JdbcConnection.class }, connProxy);
    }
    
    private ReplicationConnectionProxy(final ConnectionUrl connectionUrl) throws SQLException {
        this.enableJMX = false;
        this.allowSourceDownConnections = false;
        this.allowReplicaDownConnections = false;
        this.readFromSourceWhenNoReplicas = false;
        this.readFromSourceWhenNoReplicasOriginal = false;
        this.readOnly = false;
        this.connectionGroupID = -1L;
        final Properties props = connectionUrl.getConnectionArgumentsAsProperties();
        this.thisAsReplicationConnection = (ReplicationConnection)this.thisAsConnection;
        this.connectionUrl = connectionUrl;
        final String enableJMXAsString = props.getProperty(PropertyKey.ha_enableJMX.getKeyName(), "false");
        try {
            this.enableJMX = Boolean.parseBoolean(enableJMXAsString);
        }
        catch (Exception e3) {
            throw SQLError.createSQLException(Messages.getString("MultihostConnection.badValueForHaEnableJMX", new Object[] { enableJMXAsString }), "S1009", null);
        }
        final String allowSourceDownConnectionsAsString = props.getProperty(PropertyKey.allowSourceDownConnections.getKeyName(), "false");
        try {
            this.allowSourceDownConnections = Boolean.parseBoolean(allowSourceDownConnectionsAsString);
        }
        catch (Exception e4) {
            throw SQLError.createSQLException(Messages.getString("ReplicationConnectionProxy.badValueForAllowSourceDownConnections", new Object[] { enableJMXAsString }), "S1009", null);
        }
        final String allowReplicaDownConnectionsAsString = props.getProperty(PropertyKey.allowReplicaDownConnections.getKeyName(), "false");
        try {
            this.allowReplicaDownConnections = Boolean.parseBoolean(allowReplicaDownConnectionsAsString);
        }
        catch (Exception e5) {
            throw SQLError.createSQLException(Messages.getString("ReplicationConnectionProxy.badValueForAllowReplicaDownConnections", new Object[] { allowReplicaDownConnectionsAsString }), "S1009", null);
        }
        final String readFromSourceWhenNoReplicasAsString = props.getProperty(PropertyKey.readFromSourceWhenNoReplicas.getKeyName());
        try {
            this.readFromSourceWhenNoReplicasOriginal = Boolean.parseBoolean(readFromSourceWhenNoReplicasAsString);
        }
        catch (Exception e6) {
            throw SQLError.createSQLException(Messages.getString("ReplicationConnectionProxy.badValueForReadFromSourceWhenNoReplicas", new Object[] { readFromSourceWhenNoReplicasAsString }), "S1009", null);
        }
        final String group = props.getProperty(PropertyKey.replicationConnectionGroup.getKeyName(), null);
        if (!StringUtils.isNullOrEmpty(group) && ReplicationConnectionUrl.class.isAssignableFrom(connectionUrl.getClass())) {
            this.connectionGroup = ReplicationConnectionGroupManager.getConnectionGroupInstance(group);
            if (this.enableJMX) {
                ReplicationConnectionGroupManager.registerJmx();
            }
            this.connectionGroupID = this.connectionGroup.registerReplicationConnection(this.thisAsReplicationConnection, ((ReplicationConnectionUrl)connectionUrl).getSourcesListAsHostPortPairs(), ((ReplicationConnectionUrl)connectionUrl).getReplicasListAsHostPortPairs());
            this.sourceHosts = ((ReplicationConnectionUrl)connectionUrl).getSourceHostsListFromHostPortPairs(this.connectionGroup.getSourceHosts());
            this.replicaHosts = ((ReplicationConnectionUrl)connectionUrl).getReplicaHostsListFromHostPortPairs(this.connectionGroup.getReplicaHosts());
        }
        else {
            this.sourceHosts = new ArrayList<HostInfo>(connectionUrl.getHostsList(HostsListView.SOURCES));
            this.replicaHosts = new ArrayList<HostInfo>(connectionUrl.getHostsList(HostsListView.REPLICAS));
        }
        this.resetReadFromSourceWhenNoReplicas();
        try {
            this.initializeReplicasConnection();
        }
        catch (SQLException e) {
            if (!this.allowReplicaDownConnections) {
                if (this.connectionGroup != null) {
                    this.connectionGroup.handleCloseConnection(this.thisAsReplicationConnection);
                }
                throw e;
            }
        }
        SQLException exCaught = null;
        try {
            this.currentConnection = this.initializeSourceConnection();
        }
        catch (SQLException e2) {
            exCaught = e2;
        }
        if (this.currentConnection == null) {
            if (this.allowSourceDownConnections && this.replicasConnection != null) {
                this.readOnly = true;
                this.currentConnection = this.replicasConnection;
            }
            else {
                if (this.connectionGroup != null) {
                    this.connectionGroup.handleCloseConnection(this.thisAsReplicationConnection);
                }
                if (exCaught != null) {
                    throw exCaught;
                }
                throw SQLError.createSQLException(Messages.getString("ReplicationConnectionProxy.initializationWithEmptyHostsLists"), "S1009", null);
            }
        }
    }
    
    @Override
    JdbcConnection getNewWrapperForThisAsConnection() throws SQLException {
        return new ReplicationMySQLConnection(this);
    }
    
    @Override
    protected void propagateProxyDown(final JdbcConnection proxyConn) {
        if (this.sourceConnection != null) {
            this.sourceConnection.setProxy(proxyConn);
        }
        if (this.replicasConnection != null) {
            this.replicasConnection.setProxy(proxyConn);
        }
    }
    
    @Override
    boolean shouldExceptionTriggerConnectionSwitch(final Throwable t) {
        return false;
    }
    
    public boolean isSourceConnection() {
        return this.currentConnection != null && this.currentConnection == this.sourceConnection;
    }
    
    public boolean isReplicasConnection() {
        return this.currentConnection != null && this.currentConnection == this.replicasConnection;
    }
    
    @Deprecated
    public boolean isSlavesConnection() {
        return this.isReplicasConnection();
    }
    
    @Override
    void pickNewConnection() throws SQLException {
    }
    
    @Override
    void syncSessionState(final JdbcConnection source, final JdbcConnection target, final boolean readonly) throws SQLException {
        try {
            super.syncSessionState(source, target, readonly);
        }
        catch (SQLException e1) {
            try {
                super.syncSessionState(source, target, readonly);
            }
            catch (SQLException ex) {}
        }
    }
    
    @Override
    void doClose() throws SQLException {
        if (this.sourceConnection != null) {
            this.sourceConnection.close();
        }
        if (this.replicasConnection != null) {
            this.replicasConnection.close();
        }
        if (this.connectionGroup != null) {
            this.connectionGroup.handleCloseConnection(this.thisAsReplicationConnection);
        }
    }
    
    @Override
    void doAbortInternal() throws SQLException {
        this.sourceConnection.abortInternal();
        this.replicasConnection.abortInternal();
        if (this.connectionGroup != null) {
            this.connectionGroup.handleCloseConnection(this.thisAsReplicationConnection);
        }
    }
    
    @Override
    void doAbort(final Executor executor) throws SQLException {
        this.sourceConnection.abort(executor);
        this.replicasConnection.abort(executor);
        if (this.connectionGroup != null) {
            this.connectionGroup.handleCloseConnection(this.thisAsReplicationConnection);
        }
    }
    
    @Override
    Object invokeMore(final Object proxy, final Method method, final Object[] args) throws Throwable {
        this.checkConnectionCapabilityForMethod(method);
        boolean invokeAgain = false;
        while (true) {
            try {
                final Object result = method.invoke(this.thisAsConnection, args);
                if (result != null && result instanceof JdbcStatement) {
                    ((JdbcStatement)result).setPingTarget(this);
                }
                return result;
            }
            catch (InvocationTargetException e) {
                if (invokeAgain) {
                    invokeAgain = false;
                }
                else if (e.getCause() != null && e.getCause() instanceof SQLException && ((SQLException)e.getCause()).getSQLState() == "25000" && ((SQLException)e.getCause()).getErrorCode() == 1000001) {
                    try {
                        this.setReadOnly(this.readOnly);
                        invokeAgain = true;
                    }
                    catch (SQLException ex) {}
                }
                if (!invokeAgain) {
                    throw e;
                }
                continue;
            }
            break;
        }
    }
    
    private void checkConnectionCapabilityForMethod(final Method method) throws Throwable {
        if (this.sourceHosts.isEmpty() && this.replicaHosts.isEmpty() && !ReplicationConnection.class.isAssignableFrom(method.getDeclaringClass())) {
            throw SQLError.createSQLException(Messages.getString("ReplicationConnectionProxy.noHostsInconsistentState"), "25000", 1000002, true, null);
        }
    }
    
    @Override
    public void doPing() throws SQLException {
        boolean isSourceConn = this.isSourceConnection();
        SQLException sourcesPingException = null;
        SQLException replicasPingException = null;
        if (this.sourceConnection != null) {
            try {
                this.sourceConnection.ping();
            }
            catch (SQLException e) {
                sourcesPingException = e;
            }
        }
        else {
            this.initializeSourceConnection();
        }
        if (this.replicasConnection != null) {
            try {
                this.replicasConnection.ping();
            }
            catch (SQLException e) {
                replicasPingException = e;
            }
        }
        else {
            try {
                this.initializeReplicasConnection();
                if (this.switchToReplicasConnectionIfNecessary()) {
                    isSourceConn = false;
                }
            }
            catch (SQLException e) {
                if (this.sourceConnection == null || !this.readFromSourceWhenNoReplicas) {
                    throw e;
                }
            }
        }
        if (isSourceConn && sourcesPingException != null) {
            if (this.replicasConnection != null && replicasPingException == null) {
                this.sourceConnection = null;
                this.currentConnection = this.replicasConnection;
                this.readOnly = true;
            }
            throw sourcesPingException;
        }
        if (!isSourceConn && (replicasPingException != null || this.replicasConnection == null)) {
            if (this.sourceConnection != null && this.readFromSourceWhenNoReplicas && sourcesPingException == null) {
                this.replicasConnection = null;
                this.currentConnection = this.sourceConnection;
                this.readOnly = true;
                this.currentConnection.setReadOnly(true);
            }
            if (replicasPingException != null) {
                throw replicasPingException;
            }
        }
    }
    
    private JdbcConnection initializeSourceConnection() throws SQLException {
        this.sourceConnection = null;
        if (this.sourceHosts.size() == 0) {
            return null;
        }
        final LoadBalancedConnection newSourceConn = LoadBalancedConnectionProxy.createProxyInstance(new LoadBalanceConnectionUrl(this.sourceHosts, this.connectionUrl.getOriginalProperties()));
        newSourceConn.setProxy(this.getProxy());
        return this.sourceConnection = newSourceConn;
    }
    
    private JdbcConnection initializeReplicasConnection() throws SQLException {
        this.replicasConnection = null;
        if (this.replicaHosts.size() == 0) {
            return null;
        }
        final LoadBalancedConnection newReplicasConn = LoadBalancedConnectionProxy.createProxyInstance(new LoadBalanceConnectionUrl(this.replicaHosts, this.connectionUrl.getOriginalProperties()));
        newReplicasConn.setProxy(this.getProxy());
        newReplicasConn.setReadOnly(true);
        return this.replicasConnection = newReplicasConn;
    }
    
    private synchronized boolean switchToSourceConnection() throws SQLException {
        Label_0039: {
            if (this.sourceConnection != null) {
                if (!this.sourceConnection.isClosed()) {
                    break Label_0039;
                }
            }
            try {
                if (this.initializeSourceConnection() == null) {
                    return false;
                }
            }
            catch (SQLException e) {
                this.currentConnection = null;
                throw e;
            }
        }
        if (!this.isSourceConnection() && this.sourceConnection != null) {
            this.syncSessionState(this.currentConnection, this.sourceConnection, false);
            this.currentConnection = this.sourceConnection;
        }
        return true;
    }
    
    private synchronized boolean switchToReplicasConnection() throws SQLException {
        Label_0039: {
            if (this.replicasConnection != null) {
                if (!this.replicasConnection.isClosed()) {
                    break Label_0039;
                }
            }
            try {
                if (this.initializeReplicasConnection() == null) {
                    return false;
                }
            }
            catch (SQLException e) {
                this.currentConnection = null;
                throw e;
            }
        }
        if (!this.isReplicasConnection() && this.replicasConnection != null) {
            this.syncSessionState(this.currentConnection, this.replicasConnection, true);
            this.currentConnection = this.replicasConnection;
        }
        return true;
    }
    
    private boolean switchToReplicasConnectionIfNecessary() throws SQLException {
        return (this.currentConnection == null || (this.isSourceConnection() && (this.readOnly || (this.sourceHosts.isEmpty() && this.currentConnection.isClosed()))) || (!this.isSourceConnection() && this.currentConnection.isClosed())) && this.switchToReplicasConnection();
    }
    
    public synchronized JdbcConnection getCurrentConnection() {
        return (this.currentConnection == null) ? LoadBalancedConnectionProxy.getNullLoadBalancedConnectionInstance() : this.currentConnection;
    }
    
    public long getConnectionGroupId() {
        return this.connectionGroupID;
    }
    
    public synchronized JdbcConnection getSourceConnection() {
        return this.sourceConnection;
    }
    
    @Deprecated
    public synchronized JdbcConnection getMasterConnection() {
        return this.getSourceConnection();
    }
    
    public synchronized void promoteReplicaToSource(final String hostPortPair) throws SQLException {
        final HostInfo host = this.getReplicaHost(hostPortPair);
        if (host == null) {
            return;
        }
        this.sourceHosts.add(host);
        this.removeReplica(hostPortPair);
        if (this.sourceConnection != null) {
            this.sourceConnection.addHost(hostPortPair);
        }
        if (!this.readOnly && !this.isSourceConnection()) {
            this.switchToSourceConnection();
        }
    }
    
    @Deprecated
    public synchronized void promoteSlaveToMaster(final String hostPortPair) throws SQLException {
        this.promoteReplicaToSource(hostPortPair);
    }
    
    public synchronized void removeSourceHost(final String hostPortPair) throws SQLException {
        this.removeSourceHost(hostPortPair, true);
    }
    
    @Deprecated
    public synchronized void removeMasterHost(final String hostPortPair) throws SQLException {
        this.removeSourceHost(hostPortPair);
    }
    
    public synchronized void removeSourceHost(final String hostPortPair, final boolean waitUntilNotInUse) throws SQLException {
        this.removeSourceHost(hostPortPair, waitUntilNotInUse, false);
    }
    
    @Deprecated
    public synchronized void removeMasterHost(final String hostPortPair, final boolean waitUntilNotInUse) throws SQLException {
        this.removeSourceHost(hostPortPair, waitUntilNotInUse);
    }
    
    public synchronized void removeSourceHost(final String hostPortPair, final boolean waitUntilNotInUse, final boolean isNowReplica) throws SQLException {
        final HostInfo host = this.getSourceHost(hostPortPair);
        if (host == null) {
            return;
        }
        if (isNowReplica) {
            this.replicaHosts.add(host);
            this.resetReadFromSourceWhenNoReplicas();
        }
        this.sourceHosts.remove(host);
        if (this.sourceConnection == null || this.sourceConnection.isClosed()) {
            this.sourceConnection = null;
            return;
        }
        if (waitUntilNotInUse) {
            this.sourceConnection.removeHostWhenNotInUse(hostPortPair);
        }
        else {
            this.sourceConnection.removeHost(hostPortPair);
        }
        if (this.sourceHosts.isEmpty()) {
            this.sourceConnection.close();
            this.sourceConnection = null;
            this.switchToReplicasConnectionIfNecessary();
        }
    }
    
    @Deprecated
    public synchronized void removeMasterHost(final String hostPortPair, final boolean waitUntilNotInUse, final boolean isNowReplica) throws SQLException {
        this.removeSourceHost(hostPortPair, waitUntilNotInUse, isNowReplica);
    }
    
    public boolean isHostSource(final String hostPortPair) {
        return hostPortPair != null && this.sourceHosts.stream().anyMatch(hi -> hostPortPair.equalsIgnoreCase(hi.getHostPortPair()));
    }
    
    @Deprecated
    public boolean isHostMaster(final String hostPortPair) {
        return this.isHostSource(hostPortPair);
    }
    
    public synchronized JdbcConnection getReplicasConnection() {
        return this.replicasConnection;
    }
    
    @Deprecated
    public synchronized JdbcConnection getSlavesConnection() {
        return this.getReplicasConnection();
    }
    
    public synchronized void addReplicaHost(final String hostPortPair) throws SQLException {
        if (this.isHostReplica(hostPortPair)) {
            return;
        }
        this.replicaHosts.add(this.getConnectionUrl().getReplicaHostOrSpawnIsolated(hostPortPair));
        this.resetReadFromSourceWhenNoReplicas();
        if (this.replicasConnection == null) {
            this.initializeReplicasConnection();
            this.switchToReplicasConnectionIfNecessary();
        }
        else {
            this.replicasConnection.addHost(hostPortPair);
        }
    }
    
    @Deprecated
    public synchronized void addSlaveHost(final String hostPortPair) throws SQLException {
        this.addReplicaHost(hostPortPair);
    }
    
    public synchronized void removeReplica(final String hostPortPair) throws SQLException {
        this.removeReplica(hostPortPair, true);
    }
    
    @Deprecated
    public synchronized void removeSlave(final String hostPortPair) throws SQLException {
        this.removeReplica(hostPortPair);
    }
    
    public synchronized void removeReplica(final String hostPortPair, final boolean closeGently) throws SQLException {
        final HostInfo host = this.getReplicaHost(hostPortPair);
        if (host == null) {
            return;
        }
        this.replicaHosts.remove(host);
        this.resetReadFromSourceWhenNoReplicas();
        if (this.replicasConnection == null || this.replicasConnection.isClosed()) {
            this.replicasConnection = null;
            return;
        }
        if (closeGently) {
            this.replicasConnection.removeHostWhenNotInUse(hostPortPair);
        }
        else {
            this.replicasConnection.removeHost(hostPortPair);
        }
        if (this.replicaHosts.isEmpty()) {
            this.replicasConnection.close();
            this.replicasConnection = null;
            this.switchToSourceConnection();
            if (this.isSourceConnection()) {
                this.currentConnection.setReadOnly(this.readOnly);
            }
        }
    }
    
    @Deprecated
    public synchronized void removeSlave(final String hostPortPair, final boolean closeGently) throws SQLException {
        this.removeReplica(hostPortPair, closeGently);
    }
    
    public boolean isHostReplica(final String hostPortPair) {
        return hostPortPair != null && this.replicaHosts.stream().anyMatch(hi -> hostPortPair.equalsIgnoreCase(hi.getHostPortPair()));
    }
    
    @Deprecated
    public boolean isHostSlave(final String hostPortPair) {
        return this.isHostReplica(hostPortPair);
    }
    
    public synchronized void setReadOnly(final boolean readOnly) throws SQLException {
        if (readOnly) {
            if (!this.isReplicasConnection() || this.currentConnection.isClosed()) {
                boolean switched = true;
                SQLException exceptionCaught = null;
                try {
                    switched = this.switchToReplicasConnection();
                }
                catch (SQLException e) {
                    switched = false;
                    exceptionCaught = e;
                }
                if (!switched && this.readFromSourceWhenNoReplicas && this.switchToSourceConnection()) {
                    exceptionCaught = null;
                }
                if (exceptionCaught != null) {
                    throw exceptionCaught;
                }
            }
        }
        else if (!this.isSourceConnection() || this.currentConnection.isClosed()) {
            boolean switched = true;
            SQLException exceptionCaught = null;
            try {
                switched = this.switchToSourceConnection();
            }
            catch (SQLException e) {
                switched = false;
                exceptionCaught = e;
            }
            if (!switched && this.switchToReplicasConnectionIfNecessary()) {
                exceptionCaught = null;
            }
            if (exceptionCaught != null) {
                throw exceptionCaught;
            }
        }
        this.readOnly = readOnly;
        if (this.readFromSourceWhenNoReplicas && this.isSourceConnection()) {
            this.currentConnection.setReadOnly(this.readOnly);
        }
    }
    
    public boolean isReadOnly() throws SQLException {
        return !this.isSourceConnection() || this.readOnly;
    }
    
    private void resetReadFromSourceWhenNoReplicas() {
        this.readFromSourceWhenNoReplicas = (this.replicaHosts.isEmpty() || this.readFromSourceWhenNoReplicasOriginal);
    }
    
    private HostInfo getSourceHost(final String hostPortPair) {
        return this.sourceHosts.stream().filter(hi -> hostPortPair.equalsIgnoreCase(hi.getHostPortPair())).findFirst().orElse(null);
    }
    
    private HostInfo getReplicaHost(final String hostPortPair) {
        return this.replicaHosts.stream().filter(hi -> hostPortPair.equalsIgnoreCase(hi.getHostPortPair())).findFirst().orElse(null);
    }
    
    private ReplicationConnectionUrl getConnectionUrl() {
        return (ReplicationConnectionUrl)this.connectionUrl;
    }
}
