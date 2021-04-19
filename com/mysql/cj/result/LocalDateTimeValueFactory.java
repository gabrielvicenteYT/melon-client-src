package com.mysql.cj.result;

import java.time.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public class LocalDateTimeValueFactory extends AbstractDateTimeValueFactory<LocalDateTime>
{
    public LocalDateTimeValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    public LocalDateTime localCreateFromDate(final InternalDate idate) {
        return this.createFromTimestamp(new InternalTimestamp(idate.getYear(), idate.getMonth(), idate.getDay(), 0, 0, 0, 0, 0));
    }
    
    public LocalDateTime localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        return this.createFromTimestamp(new InternalTimestamp(1970, 1, 1, it.getHours(), it.getMinutes(), it.getSeconds(), it.getNanos(), it.getScale()));
    }
    
    public LocalDateTime localCreateFromTimestamp(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDateTime.of(its.getYear(), its.getMonth(), its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos());
    }
    
    public LocalDateTime localCreateFromDatetime(final InternalTimestamp its) {
        if (its.getYear() == 0 && its.getMonth() == 0 && its.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDateTime.of(its.getYear(), its.getMonth(), its.getDay(), its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos());
    }
    
    @Override
    public String getTargetTypeName() {
        return LocalDateTime.class.getName();
    }
}
