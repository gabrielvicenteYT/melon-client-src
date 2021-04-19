package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.*;
import java.sql.*;

public interface LoadBalancedConnection extends JdbcConnection
{
    boolean addHost(final String p0) throws SQLException;
    
    void removeHost(final String p0) throws SQLException;
    
    void removeHostWhenNotInUse(final String p0) throws SQLException;
    
    void ping(final boolean p0) throws SQLException;
}
