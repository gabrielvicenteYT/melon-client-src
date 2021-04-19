package com.mysql.cj.jdbc.ha;

import java.lang.reflect.*;
import java.util.*;
import com.mysql.cj.jdbc.*;
import java.sql.*;

public interface BalanceStrategy
{
    JdbcConnection pickConnection(final InvocationHandler p0, final List<String> p1, final Map<String, JdbcConnection> p2, final long[] p3, final int p4) throws SQLException;
}
