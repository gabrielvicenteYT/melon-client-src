package com.mysql.cj.jdbc.result;

import com.mysql.cj.protocol.*;
import java.sql.*;

public interface CachedResultSetMetaData extends ColumnDefinition
{
    ResultSetMetaData getMetadata();
    
    void setMetadata(final ResultSetMetaData p0);
}
