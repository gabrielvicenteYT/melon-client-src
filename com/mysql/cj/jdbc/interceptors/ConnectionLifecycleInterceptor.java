package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.*;
import java.util.*;
import com.mysql.cj.log.*;
import java.sql.*;

public interface ConnectionLifecycleInterceptor
{
    ConnectionLifecycleInterceptor init(final MysqlConnection p0, final Properties p1, final Log p2);
    
    void destroy();
    
    void close() throws SQLException;
    
    boolean commit() throws SQLException;
    
    boolean rollback() throws SQLException;
    
    boolean rollback(final Savepoint p0) throws SQLException;
    
    boolean setAutoCommit(final boolean p0) throws SQLException;
    
    boolean setDatabase(final String p0) throws SQLException;
    
    boolean transactionBegun();
    
    boolean transactionCompleted();
}
