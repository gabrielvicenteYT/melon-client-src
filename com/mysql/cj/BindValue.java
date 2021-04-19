package com.mysql.cj;

import java.io.*;

public interface BindValue
{
    BindValue clone();
    
    void reset();
    
    boolean isNull();
    
    void setNull(final boolean p0);
    
    boolean isStream();
    
    void setIsStream(final boolean p0);
    
    MysqlType getMysqlType();
    
    void setMysqlType(final MysqlType p0);
    
    byte[] getByteValue();
    
    void setByteValue(final byte[] p0);
    
    void setOrigByteValue(final byte[] p0);
    
    byte[] getOrigByteValue();
    
    InputStream getStreamValue();
    
    void setStreamValue(final InputStream p0, final long p1);
    
    long getStreamLength();
    
    boolean isSet();
}
