package com.mysql.cj.jdbc;

import java.util.*;
import java.sql.*;

public interface ClientInfoProvider
{
    void initialize(final Connection p0, final Properties p1) throws SQLException;
    
    void destroy() throws SQLException;
    
    Properties getClientInfo(final Connection p0) throws SQLException;
    
    String getClientInfo(final Connection p0, final String p1) throws SQLException;
    
    void setClientInfo(final Connection p0, final Properties p1) throws SQLClientInfoException;
    
    void setClientInfo(final Connection p0, final String p1, final String p2) throws SQLClientInfoException;
}
