package com.mysql.cj.jdbc;

import javax.sql.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import java.sql.*;

public class MysqlConnectionPoolDataSource extends MysqlDataSource implements ConnectionPoolDataSource
{
    static final long serialVersionUID = -7767325445592304961L;
    
    @Override
    public synchronized PooledConnection getPooledConnection() throws SQLException {
        try {
            final Connection connection = this.getConnection();
            final MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((JdbcConnection)connection);
            return mysqlPooledConnection;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public synchronized PooledConnection getPooledConnection(final String u, final String p) throws SQLException {
        try {
            final Connection connection = this.getConnection(u, p);
            final MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((JdbcConnection)connection);
            return mysqlPooledConnection;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
}
