package com.mysql.cj.result;

import java.sql.*;
import com.mysql.cj.conf.*;
import java.util.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.time.*;
import com.mysql.cj.protocol.*;

public class SqlDateValueFactory extends AbstractDateTimeValueFactory<Date>
{
    private WarningListener warningListener;
    private Calendar cal;
    
    public SqlDateValueFactory(final PropertySet pset, final Calendar calendar, final TimeZone tz) {
        super(pset);
        if (calendar != null) {
            this.cal = (Calendar)calendar.clone();
        }
        else {
            (this.cal = Calendar.getInstance(tz, Locale.US)).set(14, 0);
            this.cal.setLenient(false);
        }
    }
    
    public SqlDateValueFactory(final PropertySet pset, final Calendar calendar, final TimeZone tz, final WarningListener warningListener) {
        this(pset, calendar, tz);
        this.warningListener = warningListener;
    }
    
    public Date localCreateFromDate(final InternalDate idate) {
        synchronized (this.cal) {
            try {
                if (idate.isZero()) {
                    throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
                }
                this.cal.clear();
                this.cal.set(idate.getYear(), idate.getMonth() - 1, idate.getDay());
                final long ms = this.cal.getTimeInMillis();
                return new Date(ms);
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    public Date localCreateFromTime(final InternalTime it) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.ImplicitDatePartWarning", new Object[] { "java.sql.Date" }));
        }
        return Date.valueOf(LocalDate.of(1970, 1, 1));
    }
    
    public Date localCreateFromTimestamp(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Date" }));
        }
        return this.createFromDate(its);
    }
    
    public Date localCreateFromDatetime(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Date" }));
        }
        return this.createFromDate(its);
    }
    
    @Override
    public String getTargetTypeName() {
        return Date.class.getName();
    }
}
