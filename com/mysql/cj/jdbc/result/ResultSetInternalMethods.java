package com.mysql.cj.jdbc.result;

import com.mysql.cj.protocol.*;
import java.util.*;
import com.mysql.cj.jdbc.*;
import java.sql.*;
import java.math.*;

public interface ResultSetInternalMethods extends ResultSet, ResultsetRowsOwner, Resultset
{
    Object getObjectStoredProc(final int p0, final int p1) throws SQLException;
    
    Object getObjectStoredProc(final int p0, final Map<Object, Object> p1, final int p2) throws SQLException;
    
    Object getObjectStoredProc(final String p0, final int p1) throws SQLException;
    
    Object getObjectStoredProc(final String p0, final Map<Object, Object> p1, final int p2) throws SQLException;
    
    void realClose(final boolean p0) throws SQLException;
    
    void setFirstCharOfQuery(final char p0);
    
    void setOwningStatement(final JdbcStatement p0);
    
    char getFirstCharOfQuery();
    
    void setStatementUsedForFetchingRows(final JdbcPreparedStatement p0);
    
    void setWrapperStatement(final Statement p0);
    
    void initializeWithMetadata() throws SQLException;
    
    void populateCachedMetaData(final CachedResultSetMetaData p0) throws SQLException;
    
    BigInteger getBigInteger(final int p0) throws SQLException;
}
