package com.mysql.cj.result;

import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public abstract class AbstractDateTimeValueFactory<T> extends DefaultValueFactory<T>
{
    public AbstractDateTimeValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    abstract T localCreateFromDate(final InternalDate p0);
    
    abstract T localCreateFromTime(final InternalTime p0);
    
    abstract T localCreateFromTimestamp(final InternalTimestamp p0);
    
    abstract T localCreateFromDatetime(final InternalTimestamp p0);
    
    @Override
    public T createFromDate(final InternalDate idate) {
        if (idate.isZero()) {
            switch (this.pset.getEnumProperty(PropertyKey.zeroDateTimeBehavior).getValue()) {
                case CONVERT_TO_NULL: {
                    return null;
                }
                case ROUND: {
                    return this.localCreateFromDate(new InternalDate(1, 1, 1));
                }
            }
        }
        return this.localCreateFromDate(idate);
    }
    
    @Override
    public T createFromTime(final InternalTime it) {
        return this.localCreateFromTime(it);
    }
    
    @Override
    public T createFromTimestamp(final InternalTimestamp its) {
        if (its.isZero()) {
            switch (this.pset.getEnumProperty(PropertyKey.zeroDateTimeBehavior).getValue()) {
                case CONVERT_TO_NULL: {
                    return null;
                }
                case ROUND: {
                    return this.localCreateFromTimestamp(new InternalTimestamp(1, 1, 1, 0, 0, 0, 0, 0));
                }
            }
        }
        return this.localCreateFromTimestamp(its);
    }
    
    @Override
    public T createFromDatetime(final InternalTimestamp its) {
        if (its.isZero()) {
            switch (this.pset.getEnumProperty(PropertyKey.zeroDateTimeBehavior).getValue()) {
                case CONVERT_TO_NULL: {
                    return null;
                }
                case ROUND: {
                    return this.localCreateFromDatetime(new InternalTimestamp(1, 1, 1, 0, 0, 0, 0, 0));
                }
            }
        }
        return this.localCreateFromDatetime(its);
    }
    
    @Override
    public T createFromYear(long year) {
        if (this.pset.getBooleanProperty(PropertyKey.yearIsDateType).getValue()) {
            if (year < 100L) {
                if (year <= 69L) {
                    year += 100L;
                }
                year += 1900L;
            }
            return this.createFromDate(new InternalDate((int)year, 1, 1));
        }
        return this.createFromLong(year);
    }
    
    @Override
    public T createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        if (length == 0 && this.pset.getBooleanProperty(PropertyKey.emptyStringsConvertToZero).getValue()) {
            return this.createFromLong(0L);
        }
        final String s = StringUtils.toString(bytes, offset, length, f.getEncoding());
        final byte[] newBytes = s.getBytes();
        if (MysqlTextValueDecoder.isDate(s)) {
            return this.createFromDate(MysqlTextValueDecoder.getDate(newBytes, 0, newBytes.length));
        }
        if (MysqlTextValueDecoder.isTime(s)) {
            return this.createFromTime(MysqlTextValueDecoder.getTime(newBytes, 0, newBytes.length, f.getDecimals()));
        }
        if (MysqlTextValueDecoder.isTimestamp(s)) {
            return this.createFromTimestamp(MysqlTextValueDecoder.getTimestamp(newBytes, 0, newBytes.length, f.getDecimals()));
        }
        throw new DataConversionException(Messages.getString("ResultSet.UnableToConvertString", new Object[] { s, this.getTargetTypeName() }));
    }
}
