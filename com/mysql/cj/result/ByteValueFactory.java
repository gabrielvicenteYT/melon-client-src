package com.mysql.cj.result;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;

public class ByteValueFactory extends DefaultValueFactory<Byte>
{
    public ByteValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Byte createFromBigInteger(final BigInteger i) {
        if (this.jdbcCompliantTruncationForReads && (i.compareTo(Constants.BIG_INTEGER_MIN_BYTE_VALUE) < 0 || i.compareTo(Constants.BIG_INTEGER_MAX_BYTE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.getTargetTypeName() }));
        }
        return (byte)i.intValue();
    }
    
    @Override
    public Byte createFromLong(final long l) {
        if (this.jdbcCompliantTruncationForReads && (l < -128L || l > 127L)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (byte)l;
    }
    
    @Override
    public Byte createFromBigDecimal(final BigDecimal d) {
        if (this.jdbcCompliantTruncationForReads && (d.compareTo(Constants.BIG_DECIMAL_MIN_BYTE_VALUE) < 0 || d.compareTo(Constants.BIG_DECIMAL_MAX_BYTE_VALUE) > 0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (byte)d.longValue();
    }
    
    @Override
    public Byte createFromDouble(final double d) {
        if (this.jdbcCompliantTruncationForReads && (d < -128.0 || d > 127.0)) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.getTargetTypeName() }));
        }
        return (byte)d;
    }
    
    @Override
    public Byte createFromBit(final byte[] bytes, final int offset, final int length) {
        final long l = DataTypeUtil.bitToLong(bytes, offset, length);
        if (this.jdbcCompliantTruncationForReads && l >> 8 != 0L) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.getTargetTypeName() }));
        }
        return (byte)l;
    }
    
    @Override
    public Byte createFromYear(final long l) {
        return this.createFromLong(l);
    }
    
    @Override
    public String getTargetTypeName() {
        return Byte.class.getName();
    }
    
    @Override
    public Byte createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        if (length == 0 && this.pset.getBooleanProperty(PropertyKey.emptyStringsConvertToZero).getValue()) {
            return 0;
        }
        final String s = StringUtils.toString(bytes, offset, length, f.getEncoding());
        final byte[] newBytes = s.getBytes();
        if (this.jdbcCompliantTruncationForReads && newBytes.length != 1) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { s, this.getTargetTypeName() }));
        }
        return newBytes[0];
    }
}
