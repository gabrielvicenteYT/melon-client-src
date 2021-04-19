package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import java.math.*;

public interface ValueFactory<T>
{
    void setPropertySet(final PropertySet p0);
    
    T createFromDate(final InternalDate p0);
    
    T createFromTime(final InternalTime p0);
    
    T createFromTimestamp(final InternalTimestamp p0);
    
    T createFromDatetime(final InternalTimestamp p0);
    
    T createFromLong(final long p0);
    
    T createFromBigInteger(final BigInteger p0);
    
    T createFromDouble(final double p0);
    
    T createFromBigDecimal(final BigDecimal p0);
    
    T createFromBytes(final byte[] p0, final int p1, final int p2, final Field p3);
    
    T createFromBit(final byte[] p0, final int p1, final int p2);
    
    T createFromYear(final long p0);
    
    T createFromNull();
    
    String getTargetTypeName();
}
