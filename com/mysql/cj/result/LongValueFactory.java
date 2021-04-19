package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import com.mysql.cj.util.*;

public class LongValueFactory extends AbstractNumericValueFactory<Long>
{
    public LongValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Long createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (i.compareTo(Constants.BIG_INTEGER_MIN_LONG_VALUE) < 0 || i.compareTo(Constants.BIG_INTEGER_MAX_LONG_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return i.longValue();
    }
    
    @Override
    public Long createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < Long.MIN_VALUE || l > Long.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return l;
    }
    
    @Override
    public Long createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MIN_LONG_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_LONG_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return d.longValue();
    }
    
    @Override
    public Long createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -9.223372036854776E18 || d > 9.223372036854776E18)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (long)d;
    }
    
    @Override
    public Long createFromBit(final byte[] bytes, final int offset, final int length) {
        return DataTypeUtil.bitToLong(bytes, offset, length);
    }
    
    @Override
    public String getTargetTypeName() {
        return Long.class.getName();
    }
}
