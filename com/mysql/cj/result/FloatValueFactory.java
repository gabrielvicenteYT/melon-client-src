package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import java.math.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.nio.*;

public class FloatValueFactory extends AbstractNumericValueFactory<Float>
{
    public FloatValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Float createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (new BigDecimal(i).compareTo(Constants.BIG_DECIMAL_MAX_NEGATIVE_FLOAT_VALUE) < 0 || new BigDecimal(i).compareTo(Constants.BIG_DECIMAL_MAX_FLOAT_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return (float)i.doubleValue();
    }
    
    @Override
    public Float createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < -3.4028235E38f || l > Float.MAX_VALUE)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { l, this.getTargetTypeName() }));
        }
        return Float.valueOf(l);
    }
    
    @Override
    public Float createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MAX_NEGATIVE_FLOAT_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_FLOAT_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (float)d.doubleValue();
    }
    
    @Override
    public Float createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -3.4028234663852886E38 || d > 3.4028234663852886E38)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (float)d;
    }
    
    @Override
    public Float createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()).floatValue();
    }
    
    @Override
    public String getTargetTypeName() {
        return Float.class.getName();
    }
}
