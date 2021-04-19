package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import java.util.*;
import java.sql.*;
import com.mysql.cj.result.*;

public class RowImpl implements Row
{
    private com.mysql.cj.result.Row row;
    private ColumnDefinition metadata;
    private TimeZone defaultTimeZone;
    private PropertySet pset;
    
    public RowImpl(final com.mysql.cj.result.Row row, final ColumnDefinition metadata, final TimeZone defaultTimeZone, final PropertySet pset) {
        this.row = row;
        this.metadata = metadata;
        this.defaultTimeZone = defaultTimeZone;
        this.pset = pset;
    }
    
    private int fieldNameToIndex(final String fieldName) {
        final int idx = this.metadata.findColumn(fieldName, true, 0);
        if (idx == -1) {
            throw new DataReadException("Invalid column");
        }
        return idx;
    }
    
    @Override
    public BigDecimal getBigDecimal(final String fieldName) {
        return this.getBigDecimal(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public BigDecimal getBigDecimal(final int pos) {
        return this.row.getValue(pos, (ValueFactory<BigDecimal>)new BigDecimalValueFactory(this.pset));
    }
    
    @Override
    public boolean getBoolean(final String fieldName) {
        return this.getBoolean(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public boolean getBoolean(final int pos) {
        final Boolean res = this.row.getValue(pos, (ValueFactory<Boolean>)new BooleanValueFactory(this.pset));
        return res != null && res;
    }
    
    @Override
    public byte getByte(final String fieldName) {
        return this.getByte(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public byte getByte(final int pos) {
        final Byte res = this.row.getValue(pos, (ValueFactory<Byte>)new ByteValueFactory(this.pset));
        return (byte)((res == null) ? 0 : ((byte)res));
    }
    
    @Override
    public Date getDate(final String fieldName) {
        return this.getDate(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Date getDate(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Date>)new SqlDateValueFactory(this.pset, null, this.defaultTimeZone));
    }
    
    @Override
    public DbDoc getDbDoc(final String fieldName) {
        return this.getDbDoc(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public DbDoc getDbDoc(final int pos) {
        return this.row.getValue(pos, (ValueFactory<DbDoc>)new DbDocValueFactory(this.pset));
    }
    
    @Override
    public double getDouble(final String fieldName) {
        return this.getDouble(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public double getDouble(final int pos) {
        final Double res = this.row.getValue(pos, (ValueFactory<Double>)new DoubleValueFactory(this.pset));
        return (res == null) ? 0.0 : res;
    }
    
    @Override
    public int getInt(final String fieldName) {
        return this.getInt(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public int getInt(final int pos) {
        final Integer res = this.row.getValue(pos, (ValueFactory<Integer>)new IntegerValueFactory(this.pset));
        return (res == null) ? 0 : res;
    }
    
    @Override
    public long getLong(final String fieldName) {
        return this.getLong(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public long getLong(final int pos) {
        final Long res = this.row.getValue(pos, (ValueFactory<Long>)new LongValueFactory(this.pset));
        return (res == null) ? 0L : res;
    }
    
    @Override
    public String getString(final String fieldName) {
        return this.getString(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public String getString(final int pos) {
        return this.row.getValue(pos, (ValueFactory<String>)new StringValueFactory(this.pset));
    }
    
    @Override
    public Time getTime(final String fieldName) {
        return this.getTime(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Time getTime(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Time>)new SqlTimeValueFactory(this.pset, null, this.defaultTimeZone));
    }
    
    @Override
    public Timestamp getTimestamp(final String fieldName) {
        return this.getTimestamp(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Timestamp getTimestamp(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Timestamp>)new SqlTimestampValueFactory(this.pset, null, this.defaultTimeZone, this.defaultTimeZone));
    }
}
