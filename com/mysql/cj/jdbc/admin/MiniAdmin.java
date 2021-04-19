package com.mysql.cj.jdbc.admin;

import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import java.sql.*;
import java.util.*;
import com.mysql.cj.jdbc.*;

public class MiniAdmin
{
    private JdbcConnection conn;
    
    public MiniAdmin(final Connection conn) throws SQLException {
        if (conn == null) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.0"), "S1000", null);
        }
        if (!(conn instanceof JdbcConnection)) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.1"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
        }
        this.conn = (JdbcConnection)conn;
    }
    
    public MiniAdmin(final String jdbcUrl) throws SQLException {
        this(jdbcUrl, new Properties());
    }
    
    public MiniAdmin(final String jdbcUrl, final Properties props) throws SQLException {
        this.conn = (JdbcConnection)new Driver().connect(jdbcUrl, props);
    }
    
    public void shutdown() throws SQLException {
        this.conn.shutdownServer();
    }
}
