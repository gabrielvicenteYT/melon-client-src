package com.mysql.cj.jdbc;

import com.mysql.cj.conf.*;
import java.util.*;
import java.sql.*;

public interface JdbcPropertySet extends PropertySet
{
    List<DriverPropertyInfo> exposeAsDriverPropertyInfo() throws SQLException;
}
