package com.mysql.cj.result;

import java.sql.*;
import com.mysql.cj.*;
import java.util.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;

public class SqlTimestampValueFactory extends AbstractDateTimeValueFactory<Timestamp>
{
    private Calendar cal;
    private TimeZone defaultTimeZone;
    private TimeZone connectionTimeZone;
    
    public SqlTimestampValueFactory(final PropertySet pset, final Calendar calendar, final TimeZone defaultTimeZone, final TimeZone connectionTimeZone) {
        super(pset);
        this.defaultTimeZone = defaultTimeZone;
        this.connectionTimeZone = connectionTimeZone;
        this.cal = ((calendar != null) ? ((Calendar)calendar.clone()) : null);
    }
    
    public Timestamp localCreateFromDate(final InternalDate idate) {
        if (idate.getYear() == 0 && idate.getMonth() == 0 && idate.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        synchronized (this.defaultTimeZone) {
            Calendar c;
            if (this.cal != null) {
                c = this.cal;
            }
            else {
                c = Calendar.getInstance(this.defaultTimeZone, Locale.US);
                c.setLenient(false);
            }
            try {
                c.clear();
                c.set(idate.getYear(), idate.getMonth() - 1, idate.getDay(), 0, 0, 0);
                return new Timestamp(c.getTimeInMillis());
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Timestamp localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        synchronized (this.defaultTimeZone) {
            Calendar c;
            if (this.cal != null) {
                c = this.cal;
            }
            else {
                c = Calendar.getInstance(this.defaultTimeZone, Locale.US);
                c.setLenient(false);
            }
            try {
                c.set(1970, 0, 1, it.getHours(), it.getMinutes(), it.getSeconds());
                final Timestamp ts = new Timestamp(c.getTimeInMillis());
                ts.setNanos(it.getNanos());
                return ts;
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Timestamp localCreateFromTimestamp(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        synchronized (this.defaultTimeZone) {
            Calendar c;
            if (this.cal != null) {
                c = this.cal;
            }
            else {
                c = Calendar.getInstance(this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone, Locale.US);
                c.setLenient(false);
            }
            try {
                c.set(its.getYear(), its.getMonth() - 1, its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds());
                final Timestamp ts = new Timestamp(c.getTimeInMillis());
                ts.setNanos(its.getNanos());
                return ts;
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Timestamp localCreateFromDatetime(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        synchronized (this.defaultTimeZone) {
            Calendar c;
            if (this.cal != null) {
                c = this.cal;
            }
            else {
                c = Calendar.getInstance(this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone, Locale.US);
                c.setLenient(false);
            }
            try {
                c.set(its.getYear(), its.getMonth() - 1, its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds());
                final Timestamp ts = new Timestamp(c.getTimeInMillis());
                ts.setNanos(its.getNanos());
                return ts;
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    @Override
    public String getTargetTypeName() {
        return Timestamp.class.getName();
    }
}
