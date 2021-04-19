package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;
import java.math.*;

public abstract class DefaultValueFactory<T> implements ValueFactory<T>
{
    protected boolean jdbcCompliantTruncationForReads;
    protected PropertySet pset;
    
    public DefaultValueFactory(final PropertySet pset) {
        this.jdbcCompliantTruncationForReads = true;
        this.pset = null;
        this.pset = pset;
        this.jdbcCompliantTruncationForReads = this.pset.getBooleanProperty(PropertyKey.jdbcCompliantTruncation).getInitialValue();
    }
    
    @Override
    public void setPropertySet(final PropertySet pset) {
        this.pset = pset;
    }
    
    protected T unsupported(final String sourceType) {
        throw new DataConversionException(Messages.getString("ResultSet.UnsupportedConversion", new Object[] { sourceType, this.getTargetTypeName() }));
    }
    
    @Override
    public T createFromDate(final InternalDate idate) {
        return this.unsupported("DATE");
    }
    
    @Override
    public T createFromTime(final InternalTime it) {
        return this.unsupported("TIME");
    }
    
    @Override
    public T createFromTimestamp(final InternalTimestamp its) {
        return this.unsupported("TIMESTAMP");
    }
    
    @Override
    public T createFromDatetime(final InternalTimestamp its) {
        return this.unsupported("DATETIME");
    }
    
    @Override
    public T createFromLong(final long l) {
        return this.unsupported("LONG");
    }
    
    @Override
    public T createFromBigInteger(final BigInteger i) {
        return this.unsupported("BIGINT");
    }
    
    @Override
    public T createFromDouble(final double d) {
        return this.unsupported("DOUBLE");
    }
    
    @Override
    public T createFromBigDecimal(final BigDecimal d) {
        return this.unsupported("DECIMAL");
    }
    
    @Override
    public T createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.unsupported("BIT");
    }
    
    @Override
    public T createFromYear(final long l) {
        return this.unsupported("YEAR");
    }
    
    @Override
    public T createFromNull() {
        return null;
    }
}
