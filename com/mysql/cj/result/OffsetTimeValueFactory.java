package com.mysql.cj.result;

import java.util.*;
import java.time.*;
import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.exceptions.*;
import java.time.format.*;

public class OffsetTimeValueFactory extends AbstractDateTimeValueFactory<OffsetTime>
{
    private WarningListener warningListener;
    private TimeZone tz;
    
    public OffsetTimeValueFactory(final PropertySet pset, final TimeZone tz) {
        super(pset);
        this.tz = tz;
    }
    
    public OffsetTimeValueFactory(final PropertySet pset, final TimeZone tz, final WarningListener warningListener) {
        this(pset, tz);
        this.warningListener = warningListener;
    }
    
    @Override
    OffsetTime localCreateFromDate(final InternalDate idate) {
        return LocalTime.of(0, 0).atOffset(ZoneOffset.ofTotalSeconds(this.tz.getRawOffset() / 1000));
    }
    
    public OffsetTime localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        return LocalTime.of(it.getHours(), it.getMinutes(), it.getSeconds(), it.getNanos()).atOffset(ZoneOffset.ofTotalSeconds(this.tz.getRawOffset() / 1000));
    }
    
    public OffsetTime localCreateFromTimestamp(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    public OffsetTime localCreateFromDatetime(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    @Override
    public OffsetTime createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
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
            return OffsetTime.parse(s);
        }
        catch (DateTimeParseException e) {
            throw new DataConversionException(Messages.getString("ResultSet.UnableToConvertString", new Object[] { s, this.getTargetTypeName() }));
        }
    }
    
    @Override
    public String getTargetTypeName() {
        return OffsetTime.class.getName();
    }
}
