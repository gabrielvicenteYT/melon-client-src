package com.mysql.cj.result;

import java.sql.*;
import com.mysql.cj.conf.*;
import java.util.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public class SqlTimeValueFactory extends AbstractDateTimeValueFactory<Time>
{
    private WarningListener warningListener;
    private Calendar cal;
    
    public SqlTimeValueFactory(final PropertySet pset, final Calendar calendar, final TimeZone tz) {
        super(pset);
        if (calendar != null) {
            this.cal = (Calendar)calendar.clone();
        }
        else {
            (this.cal = Calendar.getInstance(tz, Locale.US)).setLenient(false);
        }
    }
    
    public SqlTimeValueFactory(final PropertySet pset, final Calendar calendar, final TimeZone tz, final WarningListener warningListener) {
        this(pset, calendar, tz);
        this.warningListener = warningListener;
    }
    
    @Override
    Time localCreateFromDate(final InternalDate idate) {
        synchronized (this.cal) {
            try {
                this.cal.clear();
                return new Time(this.cal.getTimeInMillis());
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Time localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        synchronized (this.cal) {
            try {
                this.cal.set(1970, 0, 1, it.getHours(), it.getMinutes(), it.getSeconds());
                this.cal.set(14, 0);
                final long ms = it.getNanos() / 1000000 + this.cal.getTimeInMillis();
                return new Time(ms);
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Time localCreateFromDatetime(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Time" }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    public Time localCreateFromTimestamp(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Time" }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    @Override
    public String getTargetTypeName() {
        return Time.class.getName();
    }
}
