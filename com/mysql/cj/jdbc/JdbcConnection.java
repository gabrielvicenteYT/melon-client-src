package com.mysql.cj.jdbc;

import java.sql.*;
import com.mysql.cj.*;
import java.util.*;
import com.mysql.cj.interceptors.*;
import com.mysql.cj.jdbc.result.*;
import com.mysql.cj.conf.*;

public interface JdbcConnection extends Connection, MysqlConnection, TransactionEventHandler
{
    JdbcPropertySet getPropertySet();
    
    void changeUser(final String p0, final String p1) throws SQLException;
    
    @Deprecated
    void clearHasTriedMaster();
    
    PreparedStatement clientPrepareStatement(final String p0) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1, final int p2) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int[] p1) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1, final int p2, final int p3) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final String[] p1) throws SQLException;
    
    int getActiveStatementCount();
    
    long getIdleFor();
    
    String getStatementComment();
    
    @Deprecated
    boolean hasTriedMaster();
    
    boolean isInGlobalTx();
    
    void setInGlobalTx(final boolean p0);
    
    boolean isSourceConnection();
    
    @Deprecated
    default boolean isMasterConnection() {
        return this.isSourceConnection();
    }
    
    boolean isSameResource(final JdbcConnection p0);
    
    boolean lowerCaseTableNames();
    
    void ping() throws SQLException;
    
    void resetServerState() throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1, final int p2) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1, final int p2, final int p3) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int[] p1) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final String[] p1) throws SQLException;
    
    void setFailedOver(final boolean p0);
    
    void setStatementComment(final String p0);
    
    void shutdownServer() throws SQLException;
    
    int getAutoIncrementIncrement();
    
    boolean hasSameProperties(final JdbcConnection p0);
    
    String getHost();
    
    String getHostPortPair();
    
    void setProxy(final JdbcConnection p0);
    
    boolean isServerLocal() throws SQLException;
    
    int getSessionMaxRows();
    
    void setSessionMaxRows(final int p0) throws SQLException;
    
    void abortInternal() throws SQLException;
    
    boolean isProxySet();
    
    CachedResultSetMetaData getCachedMetaData(final String p0);
    
    String getCharacterSetMetadata();
    
    Statement getMetadataSafeStatement() throws SQLException;
    
    ServerVersion getServerVersion();
    
    List<QueryInterceptor> getQueryInterceptorsInstances();
    
    void initializeResultsMetadataFromCache(final String p0, final CachedResultSetMetaData p1, final ResultSetInternalMethods p2) throws SQLException;
    
    void initializeSafeQueryInterceptors() throws SQLException;
    
    boolean isReadOnly(final boolean p0) throws SQLException;
    
    void pingInternal(final boolean p0, final int p1) throws SQLException;
    
    void realClose(final boolean p0, final boolean p1, final boolean p2, final Throwable p3) throws SQLException;
    
    void recachePreparedStatement(final JdbcPreparedStatement p0) throws SQLException;
    
    void decachePreparedStatement(final JdbcPreparedStatement p0) throws SQLException;
    
    void registerStatement(final JdbcStatement p0);
    
    void setReadOnlyInternal(final boolean p0) throws SQLException;
    
    boolean storesLowerCaseTableName();
    
    void throwConnectionClosedException() throws SQLException;
    
    void unregisterStatement(final JdbcStatement p0);
    
    void unSafeQueryInterceptors() throws SQLException;
    
    JdbcConnection getMultiHostSafeProxy();
    
    JdbcConnection getMultiHostParentProxy();
    
    JdbcConnection getActiveMySQLConnection();
    
    ClientInfoProvider getClientInfoProviderImpl() throws SQLException;
    
    void setDatabase(final String p0) throws SQLException;
    
    String getDatabase() throws SQLException;
}
