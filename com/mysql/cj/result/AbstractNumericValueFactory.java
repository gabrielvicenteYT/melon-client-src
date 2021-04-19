package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public abstract class AbstractNumericValueFactory<T> extends DefaultValueFactory<T>
{
    public AbstractNumericValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public T createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        if (length == 0 && this.pset.getBooleanProperty(PropertyKey.emptyStringsConvertToZero).getValue()) {
            return this.createFromLong(0L);
        }
        final String s = StringUtils.toString(bytes, offset, length, f.getEncoding());
        final byte[] newBytes = s.getBytes();
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
    
    @Override
    public T createFromYear(final long l) {
        return this.createFromLong(l);
    }
}
