package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.*;
import java.sql.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import java.util.concurrent.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;

public class ReplicationMySQLConnection extends MultiHostMySQLConnection implements ReplicationConnection
{
    public ReplicationMySQLConnection(final MultiHostConnectionProxy proxy) {
        super(proxy);
    }
    
    @Override
    public ReplicationConnectionProxy getThisAsProxy() {
        return (ReplicationConnectionProxy)super.getThisAsProxy();
    }
    
    @Override
    public JdbcConnection getActiveMySQLConnection() {
        return this.getCurrentConnection();
    }
    
    @Override
    public synchronized JdbcConnection getCurrentConnection() {
        return this.getThisAsProxy().getCurrentConnection();
    }
    
    @Override
    public long getConnectionGroupId() {
        return this.getThisAsProxy().getConnectionGroupId();
    }
    
    @Override
    public synchronized JdbcConnection getSourceConnection() {
        return this.getThisAsProxy().getSourceConnection();
    }
    
    private JdbcConnection getValidatedSourceConnection() {
        final JdbcConnection conn = this.getThisAsProxy().sourceConnection;
        try {
            return (conn == null || conn.isClosed()) ? null : conn;
        }
        catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public void promoteReplicaToSource(final String host) throws SQLException {
        try {
            this.getThisAsProxy().promoteReplicaToSource(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeSourceHost(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeSourceHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeSourceHost(final String host, final boolean waitUntilNotInUse) throws SQLException {
        try {
            this.getThisAsProxy().removeSourceHost(host, waitUntilNotInUse);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isHostSource(final String host) {
        return this.getThisAsProxy().isHostSource(host);
    }
    
    @Override
    public synchronized JdbcConnection getReplicaConnection() {
        return this.getThisAsProxy().getReplicasConnection();
    }
    
    private JdbcConnection getValidatedReplicasConnection() {
        final JdbcConnection conn = this.getThisAsProxy().replicasConnection;
        try {
            return (conn == null || conn.isClosed()) ? null : conn;
        }
        catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public void addReplicaHost(final String host) throws SQLException {
        try {
            this.getThisAsProxy().addReplicaHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeReplica(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeReplica(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeReplica(final String host, final boolean closeGently) throws SQLException {
        try {
            this.getThisAsProxy().removeReplica(host, closeGently);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isHostReplica(final String host) {
        return this.getThisAsProxy().isHostReplica(host);
    }
    
    @Override
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        try {
            this.getThisAsProxy().setReadOnly(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return this.getThisAsProxy().isReadOnly();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void ping() throws SQLException {
        try {
            try {
                final JdbcConnection conn;
                if ((conn = this.getValidatedSourceConnection()) != null) {
                    conn.ping();
                }
            }
            catch (SQLException e) {
                if (this.isSourceConnection()) {
                    throw e;
                }
            }
            try {
                final JdbcConnection conn;
                if ((conn = this.getValidatedReplicasConnection()) != null) {
                    conn.ping();
                }
            }
            catch (SQLException e) {
                if (!this.isSourceConnection()) {
                    throw e;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void changeUser(final String userName, final String newPassword) throws SQLException {
        try {
            JdbcConnection conn;
            if ((conn = this.getValidatedSourceConnection()) != null) {
                conn.changeUser(userName, newPassword);
            }
            if ((conn = this.getValidatedReplicasConnection()) != null) {
                conn.changeUser(userName, newPassword);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void setStatementComment(final String comment) {
        JdbcConnection conn;
        if ((conn = this.getValidatedSourceConnection()) != null) {
            conn.setStatementComment(comment);
        }
        if ((conn = this.getValidatedReplicasConnection()) != null) {
            conn.setStatementComment(comment);
        }
    }
    
    @Override
    public boolean hasSameProperties(final JdbcConnection c) {
        final JdbcConnection connM = this.getValidatedSourceConnection();
        final JdbcConnection connS = this.getValidatedReplicasConnection();
        return (connM != null || connS != null) && (connM == null || connM.hasSameProperties(c)) && (connS == null || connS.hasSameProperties(c));
    }
    
    @Override
    public Properties getProperties() {
        final Properties props = new Properties();
        JdbcConnection conn;
        if ((conn = this.getValidatedSourceConnection()) != null) {
            props.putAll(conn.getProperties());
        }
        if ((conn = this.getValidatedReplicasConnection()) != null) {
            props.putAll(conn.getProperties());
        }
        return props;
    }
    
    @Override
    public void abort(final Executor executor) throws SQLException {
        try {
            this.getThisAsProxy().doAbort(executor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void abortInternal() throws SQLException {
        try {
            this.getThisAsProxy().doAbortInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setProxy(final JdbcConnection proxy) {
        this.getThisAsProxy().setProxy(proxy);
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public synchronized void clearHasTriedMaster() {
        this.getThisAsProxy().sourceConnection.clearHasTriedMaster();
        this.getThisAsProxy().replicasConnection.clearHasTriedMaster();
    }
}
