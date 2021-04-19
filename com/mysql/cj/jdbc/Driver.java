package com.mysql.cj.jdbc;

import java.sql.*;

public class Driver extends NonRegisteringDriver implements java.sql.Driver
{
    public Driver() throws SQLException {
    }
    
    static {
        try {
            DriverManager.registerDriver(new Driver());
        }
        catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
}
