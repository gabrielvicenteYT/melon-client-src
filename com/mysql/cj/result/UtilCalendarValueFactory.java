package com.mysql.cj.result;

import com.mysql.cj.*;
import java.util.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;

public class UtilCalendarValueFactory extends AbstractDateTimeValueFactory<Calendar>
{
    private TimeZone defaultTimeZone;
    private TimeZone connectionTimeZone;
    
    public UtilCalendarValueFactory(final PropertySet pset, final TimeZone defaultTimeZone, final TimeZone connectionTimeZone) {
        super(pset);
        this.defaultTimeZone = defaultTimeZone;
        this.connectionTimeZone = connectionTimeZone;
    }
    
    public Calendar localCreateFromDate(final InternalDate idate) {
        if (idate.getYear() == 0 && idate.getMonth() == 0 && idate.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        try {
            final Calendar c = Calendar.getInstance(this.defaultTimeZone, Locale.US);
            c.set(idate.getYear(), idate.getMonth() - 1, idate.getDay(), 0, 0, 0);
            c.set(14, 0);
            c.setLenient(false);
            return c;
        }
        catch (IllegalArgumentException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
        }
    }
    
    public Calendar localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        try {
            final Calendar c = Calendar.getInstance(this.defaultTimeZone, Locale.US);
            c.set(1970, 0, 1, it.getHours(), it.getMinutes(), it.getSeconds());
            c.set(14, it.getNanos() / 1000000);
            c.setLenient(false);
            return c;
        }
        catch (IllegalArgumentException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
        }
    }
    
    public Calendar localCreateFromTimestamp(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        try {
            final Calendar c = Calendar.getInstance(this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone, Locale.US);
            c.set(its.getYear(), its.getMonth() - 1, its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds());
            c.set(14, its.getNanos() / 1000000);
            c.setLenient(false);
            return c;
        }
        catch (IllegalArgumentException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
        }
    }
    
    public Calendar localCreateFromDatetime(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        try {
            final Calendar c = Calendar.getInstance(this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue() ? this.connectionTimeZone : this.defaultTimeZone, Locale.US);
            c.set(its.getYear(), its.getMonth() - 1, its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds());
            c.set(14, its.getNanos() / 1000000);
            c.setLenient(false);
            return c;
        }
        catch (IllegalArgumentException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
        }
    }
    
    @Override
    public String getTargetTypeName() {
        return Calendar.class.getName();
    }
}
