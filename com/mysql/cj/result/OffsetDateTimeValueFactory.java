package com.mysql.cj.result;

import java.util.*;
import com.mysql.cj.*;
import java.time.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.exceptions.*;
import java.time.format.*;

public class OffsetDateTimeValueFactory extends AbstractDateTimeValueFactory<OffsetDateTime>
{
    private TimeZone defaultTimeZone;
    private TimeZone connectionTimeZone;
    
    public OffsetDateTimeValueFactory(final PropertySet pset, final TimeZone defaultTimeZone, final TimeZone connectionTimeZone) {
        super(pset);
        this.defaultTimeZone = defaultTimeZone;
        this.connectionTimeZone = connectionTimeZone;
    }
    
    public OffsetDateTime localCreateFromDate(final InternalDate idate) {
        if (idate.getYear() == 0 && idate.getMonth() == 0 && idate.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDateTime.of(idate.getYear(), idate.getMonth(), idate.getDay(), 0, 0, 0, 0).atZone(this.defaultTimeZone.toZoneId()).toOffsetDateTime();
    }
    
    public OffsetDateTime localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        return LocalDateTime.of(1970, 1, 1, it.getHours(), it.getMinutes(), it.getSeconds(), it.getNanos()).atZone(this.defaultTimeZone.toZoneId()).toOffsetDateTime();
    }
    
    public OffsetDateTime localCreateFromTimestamp(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDateTime.of(its.getYear(), its.getMonth(), its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos()).atZone((this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone).toZoneId()).toOffsetDateTime();
    }
    
    public OffsetDateTime localCreateFromDatetime(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDateTime.of(its.getYear(), its.getMonth(), its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos()).atZone((this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone).toZoneId()).toOffsetDateTime();
    }
    
    @Override
    public OffsetDateTime createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
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
        try {
            return OffsetDateTime.parse(s.replace(" ", "T"));
        }
        catch (DateTimeParseException e) {
            throw new DataConversionException(Messages.getString("ResultSet.UnableToConvertString", new Object[] { s, this.getTargetTypeName() }));
        }
    }
    
    @Override
    public String getTargetTypeName() {
        return OffsetDateTime.class.getName();
    }
}
