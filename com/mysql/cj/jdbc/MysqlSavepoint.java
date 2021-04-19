package com.mysql.cj.jdbc;

import com.mysql.cj.util.*;
import java.sql.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;

public class MysqlSavepoint implements Savepoint
{
    private String savepointName;
    private ExceptionInterceptor exceptionInterceptor;
    
    MysqlSavepoint(final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        this(StringUtils.getUniqueSavepointId(), exceptionInterceptor);
    }
    
    MysqlSavepoint(final String name, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        if (name == null || name.length() == 0) {
            throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.0"), "S1009", exceptionInterceptor);
        }
        this.savepointName = name;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public int getSavepointId() throws SQLException {
        try {
            throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.1"), "S1C00", this.exceptionInterceptor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getSavepointName() throws SQLException {
        try {
            return this.savepointName;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
}
