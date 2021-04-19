package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import java.math.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.nio.*;

public class DoubleValueFactory extends AbstractNumericValueFactory<Double>
{
    public DoubleValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Double createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (new BigDecimal(i).compareTo(Constants.BIG_DECIMAL_MAX_NEGATIVE_DOUBLE_VALUE) < 0 || new BigDecimal(i).compareTo(Constants.BIG_DECIMAL_MAX_DOUBLE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return i.doubleValue();
    }
    
    @Override
    public Double createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < -1.7976931348623157E308 || l > Double.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { l, this.getTargetTypeName() }));
        }
        return Double.valueOf(l);
    }
    
    @Override
    public Double createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MAX_NEGATIVE_DOUBLE_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_DOUBLE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return d.doubleValue();
    }
    
    @Override
    public Double createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -1.7976931348623157E308 || d > Double.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return d;
    }
    
    @Override
    public Double createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()).doubleValue();
    }
    
    @Override
    public String getTargetTypeName() {
        return Double.class.getName();
    }
}
