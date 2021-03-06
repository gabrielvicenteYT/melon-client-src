package com.mysql.cj.jdbc;

import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.jdbc.result.*;
import java.sql.*;
import java.util.*;
import java.lang.reflect.*;

public class StatementWrapper extends WrapperBase implements Statement
{
    protected Statement wrappedStmt;
    protected ConnectionWrapper wrappedConn;
    
    protected static StatementWrapper getInstance(final ConnectionWrapper c, final MysqlPooledConnection conn, final Statement toWrap) throws SQLException {
        return new StatementWrapper(c, conn, toWrap);
    }
    
    public StatementWrapper(final ConnectionWrapper c, final MysqlPooledConnection conn, final Statement toWrap) {
        super(conn);
        this.wrappedStmt = toWrap;
        this.wrappedConn = c;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedConn;
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public void setCursorName(final String name) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setCursorName(name);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setEscapeProcessing(enable);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setFetchDirection(direction);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public int getFetchDirection() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getFetchDirection();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 1000;
        }
    }
    
    @Override
    public void setFetchSize(final int rows) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setFetchSize(rows);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public int getFetchSize() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getFetchSize();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0;
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getGeneratedKeys();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public void setMaxFieldSize(final int max) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setMaxFieldSize(max);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public int getMaxFieldSize() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getMaxFieldSize();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0;
        }
    }
    
    @Override
    public void setMaxRows(final int max) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setMaxRows(max);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public int getMaxRows() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getMaxRows();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0;
        }
    }
    
    @Override
    public boolean getMoreResults() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getMoreResults();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public boolean getMoreResults(final int current) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getMoreResults(current);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public void setQueryTimeout(final int seconds) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setQueryTimeout(seconds);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public int getQueryTimeout() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getQueryTimeout();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0;
        }
    }
    
    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                final ResultSet rs = this.wrappedStmt.getResultSet();
                if (rs != null) {
                    ((ResultSetInternalMethods)rs).setWrapperStatement(this);
                }
                return rs;
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public int getResultSetConcurrency() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getResultSetConcurrency();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0;
        }
    }
    
    @Override
    public int getResultSetHoldability() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getResultSetHoldability();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 1;
        }
    }
    
    @Override
    public int getResultSetType() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getResultSetType();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 1003;
        }
    }
    
    @Override
    public int getUpdateCount() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getUpdateCount();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1;
        }
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.getWarnings();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public void addBatch(final String sql) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.addBatch(sql);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void cancel() throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.cancel();
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void clearBatch() throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.clearBatch();
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.clearWarnings();
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                this.wrappedStmt.close();
            }
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
        finally {
            this.wrappedStmt = null;
            this.pooledConnection = null;
            this.unwrappedInterfaces = null;
        }
    }
    
    @Override
    public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.execute(sql, autoGeneratedKeys);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.execute(sql, columnIndexes);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public boolean execute(final String sql, final String[] columnNames) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.execute(sql, columnNames);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public boolean execute(final String sql) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.execute(sql);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public int[] executeBatch() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.executeBatch();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public ResultSet executeQuery(final String sql) throws SQLException {
        ResultSet rs = null;
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            rs = this.wrappedStmt.executeQuery(sql);
            ((ResultSetInternalMethods)rs).setWrapperStatement(this);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
        return rs;
    }
    
    @Override
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.executeUpdate(sql, autoGeneratedKeys);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1;
        }
    }
    
    @Override
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.executeUpdate(sql, columnIndexes);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1;
        }
    }
    
    @Override
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.executeUpdate(sql, columnNames);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1;
        }
    }
    
    @Override
    public int executeUpdate(final String sql) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.executeUpdate(sql);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1;
        }
    }
    
    public void enableStreamingResults() throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1000", this.exceptionInterceptor);
            }
            ((JdbcStatement)this.wrappedStmt).enableStreamingResults();
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public synchronized <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            if ("java.sql.Statement".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName())) {
                return iface.cast(this);
            }
            if (this.unwrappedInterfaces == null) {
                this.unwrappedInterfaces = new HashMap<Class<?>, Object>();
            }
            Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
            if (cachedUnwrapped == null) {
                cachedUnwrapped = Proxy.newProxyInstance(this.wrappedStmt.getClass().getClassLoader(), new Class[] { iface }, new ConnectionErrorFiringInvocationHandler(this.wrappedStmt));
                this.unwrappedInterfaces.put(iface, cachedUnwrapped);
            }
            return iface.cast(cachedUnwrapped);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        final boolean isInstance = iface.isInstance(this);
        if (isInstance) {
            return true;
        }
        final String interfaceClassName = iface.getName();
        return interfaceClassName.equals("com.mysql.cj.jdbc.Statement") || interfaceClassName.equals("java.sql.Statement") || interfaceClassName.equals("java.sql.Wrapper");
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.isClosed();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            this.wrappedStmt.setPoolable(poolable);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
    
    @Override
    public boolean isPoolable() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return this.wrappedStmt.isPoolable();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return false;
        }
    }
    
    @Override
    public void closeOnCompletion() throws SQLException {
        if (this.wrappedStmt == null) {
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        if (this.wrappedStmt == null) {
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        return false;
    }
    
    @Override
    public long[] executeLargeBatch() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).executeLargeBatch();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return null;
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).executeLargeUpdate(sql);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1L;
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).executeLargeUpdate(sql, autoGeneratedKeys);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1L;
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).executeLargeUpdate(sql, columnIndexes);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1L;
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final String[] columnNames) throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).executeLargeUpdate(sql, columnNames);
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1L;
        }
    }
    
    @Override
    public long getLargeMaxRows() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).getLargeMaxRows();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return 0L;
        }
    }
    
    @Override
    public long getLargeUpdateCount() throws SQLException {
        try {
            if (this.wrappedStmt != null) {
                return ((StatementImpl)this.wrappedStmt).getLargeUpdateCount();
            }
            throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
            return -1L;
        }
    }
    
    @Override
    public void setLargeMaxRows(final long max) throws SQLException {
        try {
            if (this.wrappedStmt == null) {
                throw SQLError.createSQLException(Messages.getString("Statement.AlreadyClosed"), "S1009", this.exceptionInterceptor);
            }
            ((StatementImpl)this.wrappedStmt).setLargeMaxRows(max);
        }
        catch (SQLException sqlEx) {
            this.checkAndFireConnectionError(sqlEx);
        }
    }
}
