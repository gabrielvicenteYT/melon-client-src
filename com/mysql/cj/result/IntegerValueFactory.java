package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import com.mysql.cj.util.*;

public class IntegerValueFactory extends AbstractNumericValueFactory<Integer>
{
    public IntegerValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Integer createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (i.compareTo(Constants.BIG_INTEGER_MIN_INTEGER_VALUE) < 0 || i.compareTo(Constants.BIG_INTEGER_MAX_INTEGER_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return i.intValue();
    }
    
    @Override
    public Integer createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < -2147483648L || l > 2147483647L)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (int)l;
    }
    
    @Override
    public Integer createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MIN_INTEGER_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_INTEGER_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (int)d.longValue();
    }
    
    @Override
    public Integer createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -2.147483648E9 || d > 2.147483647E9)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (int)d;
    }
    
    @Override
    public Integer createFromBit(final byte[] bytes, final int offset, final int length) {
        final long l = DataTypeUtil.bitToLong(bytes, offset, length);
        if (this.jdbcCompliantTruncationForReads && l >> 32 != 0L) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (int)l;
    }
    
    @Override
    public String getTargetTypeName() {
        return Integer.class.getName();
    }
}
