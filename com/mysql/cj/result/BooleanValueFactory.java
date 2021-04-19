package com.mysql.cj.result;

import java.math.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public class BooleanValueFactory extends DefaultValueFactory<Boolean>
{
    public BooleanValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public Boolean createFromLong(final long l) {
        return l == -1L || l > 0L;
    }
    
    @Override
    public Boolean createFromBigInteger(final BigInteger i) {
        return i.compareTo(Constants.BIG_INTEGER_ZERO) > 0 || i.compareTo(Constants.BIG_INTEGER_NEGATIVE_ONE) == 0;
    }
    
    @Override
    public Boolean createFromDouble(final double d) {
        return d > 0.0 || d == -1.0;
    }
    
    @Override
    public Boolean createFromBigDecimal(final BigDecimal d) {
        return d.compareTo(Constants.BIG_DECIMAL_ZERO) > 0 || d.compareTo(Constants.BIG_DECIMAL_NEGATIVE_ONE) == 0;
    }
    
    @Override
    public Boolean createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.createFromLong(DataTypeUtil.bitToLong(bytes, offset, length));
    }
    
    @Override
    public Boolean createFromYear(final long l) {
        return this.createFromLong(l);
    }
    
    @Override
    public String getTargetTypeName() {
        return Boolean.class.getName();
    }
    
    @Override
    public Boolean createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        if (length == 0 && this.pset.getBooleanProperty(PropertyKey.emptyStringsConvertToZero).getValue()) {
            return this.createFromLong(0L);
        }
        final String s = StringUtils.toString(bytes, offset, length, f.getEncoding());
        final byte[] newBytes = s.getBytes();
        if (s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("true")) {
            return this.createFromLong(1L);
        }
        if (s.equalsIgnoreCase("N") || s.equalsIgnoreCase("false")) {
            return this.createFromLong(0L);
        }
        if (s.contains("e") || s.contains("E") || s.matches("-?\\d*\\.\\d*")) {
            return this.createFromDouble(MysqlTextValueDecoder.getDouble(newBytes, 0, newBytes.length));
        }
        if (!s.matches("-?\\d+")) {
            throw new DataConversionException(Messages.getString("ResultSet.UnableToInterpretString", new Object[] { s }));
        }
        if (s.charAt(0) == '-' || (length <= 19 && newBytes[0] >= 48 && newBytes[0] <= 56)) {
            return this.createFromLong(MysqlTextValueDecoder.getLong(newBytes, 0, newBytes.length));
        }
        return this.createFromBigInteger(MysqlTextValueDecoder.getBigInteger(newBytes, 0, newBytes.length));
    }
}
