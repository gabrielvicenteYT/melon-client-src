package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import com.mysql.cj.util.*;

public class ShortValueFactory extends AbstractNumericValueFactory<Short>
{
    public ShortValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Short createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (i.compareTo(Constants.BIG_INTEGER_MIN_SHORT_VALUE) < 0 || i.compareTo(Constants.BIG_INTEGER_MAX_SHORT_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return (short)i.intValue();
    }
    
    @Override
    public Short createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < -32768L || l > 32767L)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (short)l;
    }
    
    @Override
    public Short createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MIN_SHORT_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_SHORT_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (short)d.longValue();
    }
    
    @Override
    public Short createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -32768.0 || d > 32767.0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (short)d;
    }
    
    @Override
    public Short createFromBit(final byte[] bytes, final int offset, final int length) {
        final long l = DataTypeUtil.bitToLong(bytes, offset, length);
        if (this.jdbcCompliantTruncationForReads && l >> 16 != 0L) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (short)l;
    }
    
    @Override
    public String getTargetTypeName() {
        return Short.class.getName();
    }
}
