package com.mysql.cj.result;

import com.mysql.cj.protocol.*;
import java.math.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;

public class StringValueFactory implements ValueFactory<String>
{
    protected PropertySet pset;
    
    public StringValueFactory(final PropertySet pset) {
        this.pset = null;
        this.pset = pset;
    }
    
    @Override
    public void setPropertySet(final PropertySet pset) {
        this.pset = pset;
    }
    
    @Override
    public String createFromDate(final InternalDate idate) {
        return String.format("%04d-%02d-%02d", idate.getYear(), idate.getMonth(), idate.getDay());
    }
    
    @Override
    public String createFromTime(final InternalTime it) {
        if (it.getNanos() > 0) {
            return String.format("%02d:%02d:%02d.%s", it.getHours(), it.getMinutes(), it.getSeconds(), TimeUtil.formatNanos(it.getNanos(), it.getScale(), false));
        }
        return String.format("%02d:%02d:%02d", it.getHours(), it.getMinutes(), it.getSeconds());
    }
    
    @Override
    public String createFromTimestamp(final InternalTimestamp its) {
        return String.format("%s %s", this.createFromDate((InternalDate)its), this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale())));
    }
    
    @Override
    public String createFromDatetime(final InternalTimestamp its) {
        return String.format("%s %s", this.createFromDate((InternalDate)its), this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale())));
    }
    
    @Override
    public String createFromLong(final long l) {
        return String.valueOf(l);
    }
    
    @Override
    public String createFromBigInteger(final BigInteger i) {
        return i.toString();
    }
    
    @Override
    public String createFromDouble(final double d) {
        return String.valueOf(d);
    }
    
    @Override
    public String createFromBigDecimal(final BigDecimal d) {
        return d.toString();
    }
    
    @Override
    public String createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        return StringUtils.toString(bytes, offset, length, (f.getCollationIndex() == 63) ? this.pset.getStringProperty(PropertyKey.characterEncoding).getValue() : f.getEncoding());
    }
    
    @Override
    public String createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.createFromLong(DataTypeUtil.bitToLong(bytes, offset, length));
    }
    
    @Override
    public String createFromYear(long l) {
        if (this.pset.getBooleanProperty(PropertyKey.yearIsDateType).getValue()) {
            if (l < 100L) {
                if (l <= 69L) {
                    l += 100L;
                }
                l += 1900L;
            }
            return this.createFromDate(new InternalDate((int)l, 1, 1));
        }
        return this.createFromLong(l);
    }
    
    @Override
    public String createFromNull() {
        return null;
    }
    
    @Override
    public String getTargetTypeName() {
        return String.class.getName();
    }
}
