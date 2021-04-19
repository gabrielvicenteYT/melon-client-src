package com.mysql.cj.jdbc;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.net.*;

public interface ParameterBindings
{
    Array getArray(final int p0) throws SQLException;
    
    InputStream getAsciiStream(final int p0) throws SQLException;
    
    BigDecimal getBigDecimal(final int p0) throws SQLException;
    
    InputStream getBinaryStream(final int p0) throws SQLException;
    
    Blob getBlob(final int p0) throws SQLException;
    
    boolean getBoolean(final int p0) throws SQLException;
    
    byte getByte(final int p0) throws SQLException;
    
    byte[] getBytes(final int p0) throws SQLException;
    
    Reader getCharacterStream(final int p0) throws SQLException;
    
    Clob getClob(final int p0) throws SQLException;
    
    Date getDate(final int p0) throws SQLException;
    
    double getDouble(final int p0) throws SQLException;
    
    float getFloat(final int p0) throws SQLException;
    
    int getInt(final int p0) throws SQLException;
    
    BigInteger getBigInteger(final int p0) throws SQLException;
    
    long getLong(final int p0) throws SQLException;
    
    Reader getNCharacterStream(final int p0) throws SQLException;
    
    Reader getNClob(final int p0) throws SQLException;
    
    Object getObject(final int p0) throws SQLException;
    
    Ref getRef(final int p0) throws SQLException;
    
    short getShort(final int p0) throws SQLException;
    
    String getString(final int p0) throws SQLException;
    
    Time getTime(final int p0) throws SQLException;
    
    Timestamp getTimestamp(final int p0) throws SQLException;
    
    URL getURL(final int p0) throws SQLException;
    
    boolean isNull(final int p0) throws SQLException;
}
