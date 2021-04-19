package com.mysql.cj.jdbc.ha;

import java.sql.*;
import java.util.*;
import com.mysql.cj.util.*;
import com.mysql.cj.jdbc.*;
import com.mysql.cj.conf.*;
import java.util.concurrent.*;
import java.lang.reflect.*;

public abstract class MultiHostConnectionProxy implements InvocationHandler
{
    private static final String METHOD_GET_MULTI_HOST_SAFE_PROXY = "getMultiHostSafeProxy";
    private static final String METHOD_EQUALS = "equals";
    private static final String METHOD_HASH_CODE = "hashCode";
    private static final String METHOD_CLOSE = "close";
    private static final String METHOD_ABORT_INTERNAL = "abortInternal";
    private static final String METHOD_ABORT = "abort";
    private static final String METHOD_IS_CLOSED = "isClosed";
    private static final String METHOD_GET_AUTO_COMMIT = "getAutoCommit";
    private static final String METHOD_GET_CATALOG = "getCatalog";
    private static final String METHOD_GET_SCHEMA = "getSchema";
    private static final String METHOD_GET_DATABASE = "getDatabase";
    private static final String METHOD_GET_TRANSACTION_ISOLATION = "getTransactionIsolation";
    private static final String METHOD_GET_SESSION_MAX_ROWS = "getSessionMaxRows";
    List<HostInfo> hostsList;
    protected ConnectionUrl connectionUrl;
    boolean autoReconnect;
    JdbcConnection thisAsConnection;
    JdbcConnection parentProxyConnection;
    JdbcConnection topProxyConnection;
    JdbcConnection currentConnection;
    boolean isClosed;
    boolean closedExplicitly;
    String closedReason;
    protected Throwable lastExceptionDealtWith;
    
    MultiHostConnectionProxy() throws SQLException {
        this.autoReconnect = false;
        this.thisAsConnection = null;
        this.parentProxyConnection = null;
        this.topProxyConnection = null;
        this.currentConnection = null;
        this.isClosed = false;
        this.closedExplicitly = false;
        this.closedReason = null;
        this.lastExceptionDealtWith = null;
        this.thisAsConnection = this.getNewWrapperForThisAsConnection();
    }
    
    MultiHostConnectionProxy(final ConnectionUrl connectionUrl) throws SQLException {
        this();
        this.initializeHostsSpecs(connectionUrl, connectionUrl.getHostsList());
    }
    
    int initializeHostsSpecs(final ConnectionUrl connUrl, final List<HostInfo> hosts) {
        this.connectionUrl = connUrl;
        final Properties props = connUrl.getConnectionArgumentsAsProperties();
        this.autoReconnect = ("true".equalsIgnoreCase(props.getProperty(PropertyKey.autoReconnect.getKeyName())) || "true".equalsIgnoreCase(props.getProperty(PropertyKey.autoReconnectForPools.getKeyName())));
        this.hostsList = new ArrayList<HostInfo>(hosts);
        final int numHosts = this.hostsList.size();
        return numHosts;
    }
    
    protected JdbcConnection getProxy() {
        return (this.topProxyConnection != null) ? this.topProxyConnection : this.thisAsConnection;
    }
    
    protected JdbcConnection getParentProxy() {
        return this.parentProxyConnection;
    }
    
    protected final void setProxy(final JdbcConnection proxyConn) {
        if (this.parentProxyConnection == null) {
            this.parentProxyConnection = proxyConn;
        }
        this.propagateProxyDown(this.topProxyConnection = proxyConn);
    }
    
    protected void propagateProxyDown(final JdbcConnection proxyConn) {
        this.currentConnection.setProxy(proxyConn);
    }
    
    JdbcConnection getNewWrapperForThisAsConnection() throws SQLException {
        return new MultiHostMySQLConnection(this);
    }
    
    Object proxyIfReturnTypeIsJdbcInterface(final Class<?> returnType, final Object toProxy) {
        if (toProxy != null && Util.isJdbcInterface(returnType)) {
            final Class<?> toProxyClass = toProxy.getClass();
            return Proxy.newProxyInstance(toProxyClass.getClassLoader(), Util.getImplementedInterfaces(toProxyClass), this.getNewJdbcInterfaceProxy(toProxy));
        }
        return toProxy;
    }
    
    InvocationHandler getNewJdbcInterfaceProxy(final Object toProxy) {
        return new JdbcInterfaceProxy(toProxy);
    }
    
    void dealWithInvocationException(final InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
        final Throwable t = e.getTargetException();
        if (t != null) {
            if (this.lastExceptionDealtWith != t && this.shouldExceptionTriggerConnectionSwitch(t)) {
                this.invalidateCurrentConnection();
                this.pickNewConnection();
                this.lastExceptionDealtWith = t;
            }
            throw t;
        }
        throw e;
    }
    
    abstract boolean shouldExceptionTriggerConnectionSwitch(final Throwable p0);
    
    abstract boolean isSourceConnection();
    
    @Deprecated
    boolean isMasterConnection() {
        return this.isSourceConnection();
    }
    
    synchronized void invalidateCurrentConnection() throws SQLException {
        this.invalidateConnection(this.currentConnection);
    }
    
    synchronized void invalidateConnection(final JdbcConnection conn) throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.realClose(true, !conn.getAutoCommit(), true, null);
            }
        }
        catch (SQLException ex) {}
    }
    
    abstract void pickNewConnection() throws SQLException;
    
    synchronized ConnectionImpl createConnectionForHost(final HostInfo hostInfo) throws SQLException {
        final ConnectionImpl conn = (ConnectionImpl)ConnectionImpl.getInstance(hostInfo);
        final JdbcConnection topmostProxy = this.getProxy();
        if (topmostProxy != this.thisAsConnection) {
            conn.setProxy(this.thisAsConnection);
        }
        conn.setProxy(topmostProxy);
        return conn;
    }
    
    void syncSessionState(final JdbcConnection source, final JdbcConnection target) throws SQLException {
        if (source == null || target == null) {
            return;
        }
        final RuntimeProperty<Boolean> sourceUseLocalSessionState = source.getPropertySet().getBooleanProperty(PropertyKey.useLocalSessionState);
        final boolean prevUseLocalSessionState = sourceUseLocalSessionState.getValue();
        sourceUseLocalSessionState.setValue(true);
        final boolean readOnly = source.isReadOnly();
        sourceUseLocalSessionState.setValue(prevUseLocalSessionState);
        this.syncSessionState(source, target, readOnly);
    }
    
    void syncSessionState(final JdbcConnection source, final JdbcConnection target, final boolean readOnly) throws SQLException {
        if (target != null) {
            target.setReadOnly(readOnly);
        }
        if (source == null || target == null) {
            return;
        }
        final RuntimeProperty<Boolean> sourceUseLocalSessionState = source.getPropertySet().getBooleanProperty(PropertyKey.useLocalSessionState);
        final boolean prevUseLocalSessionState = sourceUseLocalSessionState.getValue();
        sourceUseLocalSessionState.setValue(true);
        target.setAutoCommit(source.getAutoCommit());
        final String db = source.getDatabase();
        if (db != null && !db.isEmpty()) {
            target.setDatabase(db);
        }
        target.setTransactionIsolation(source.getTransactionIsolation());
        target.setSessionMaxRows(source.getSessionMaxRows());
        sourceUseLocalSessionState.setValue(prevUseLocalSessionState);
    }
    
    abstract void doClose() throws SQLException;
    
    abstract void doAbortInternal() throws SQLException;
    
    abstract void doAbort(final Executor p0) throws SQLException;
    
    @Override
    public synchronized Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final String methodName = method.getName();
        if ("getMultiHostSafeProxy".equals(methodName)) {
            return this.thisAsConnection;
        }
        if ("equals".equals(methodName)) {
            return args[0].equals(this);
        }
        if ("hashCode".equals(methodName)) {
            return this.hashCode();
        }
        if ("close".equals(methodName)) {
            this.doClose();
            this.isClosed = true;
            this.closedReason = "Connection explicitly closed.";
            this.closedExplicitly = true;
            return null;
        }
        if ("abortInternal".equals(methodName)) {
            this.doAbortInternal();
            this.currentConnection.abortInternal();
            this.isClosed = true;
            this.closedReason = "Connection explicitly closed.";
            return null;
        }
        if ("abort".equals(methodName) && args.length == 1) {
            this.doAbort((Executor)args[0]);
            this.isClosed = true;
            this.closedReason = "Connection explicitly closed.";
            return null;
        }
        if ("isClosed".equals(methodName)) {
            return this.isClosed;
        }
        try {
            return this.invokeMore(proxy, method, args);
        }
        catch (InvocationTargetException e) {
            throw (e.getCause() != null) ? e.getCause() : e;
        }
        catch (Exception e2) {
            final Class<?>[] exceptionTypes;
            final Class<?>[] declaredException = exceptionTypes = method.getExceptionTypes();
            for (final Class<?> declEx : exceptionTypes) {
                if (declEx.isAssignableFrom(e2.getClass())) {
                    throw e2;
                }
            }
            throw new IllegalStateException(e2.getMessage(), e2);
        }
    }
    
    abstract Object invokeMore(final Object p0, final Method p1, final Object[] p2) throws Throwable;
    
    protected boolean allowedOnClosedConnection(final Method method) {
        final String methodName = method.getName();
        return methodName.equals("getAutoCommit") || methodName.equals("getCatalog") || methodName.equals("getSchema") || methodName.equals("getDatabase") || methodName.equals("getTransactionIsolation") || methodName.equals("getSessionMaxRows");
    }
    
    class JdbcInterfaceProxy implements InvocationHandler
    {
        Object invokeOn;
        
        JdbcInterfaceProxy(final Object toInvokeOn) {
            this.invokeOn = null;
            this.invokeOn = toInvokeOn;
        }
        
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if ("equals".equals(method.getName())) {
                return args[0].equals(this);
            }
            synchronized (MultiHostConnectionProxy.this) {
                Object result = null;
                try {
                    result = method.invoke(this.invokeOn, args);
                    result = MultiHostConnectionProxy.this.proxyIfReturnTypeIsJdbcInterface(method.getReturnType(), result);
                }
                catch (InvocationTargetException e) {
                    MultiHostConnectionProxy.this.dealWithInvocationException(e);
                }
                return result;
            }
        }
    }
}
