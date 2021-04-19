package com.mysql.cj.result;

import java.time.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public class LocalTimeValueFactory extends AbstractDateTimeValueFactory<LocalTime>
{
    private WarningListener warningListener;
    
    public LocalTimeValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    public LocalTimeValueFactory(final PropertySet pset, final WarningListener warningListener) {
        this(pset);
        this.warningListener = warningListener;
    }
    
    @Override
    LocalTime localCreateFromDate(final InternalDate idate) {
        return LocalTime.of(0, 0);
    }
    
    public LocalTime localCreateFromTime(final InternalTime it) {
        if (it.getHours() < 0 || it.getHours() >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + it.getHours() + ":" + it.getMinutes() + ":" + it.getSeconds() }));
        }
        return LocalTime.of(it.getHours(), it.getMinutes(), it.getSeconds(), it.getNanos());
    }
    
    public LocalTime localCreateFromTimestamp(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    public LocalTime localCreateFromDatetime(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromTime(new InternalTime(its.getHours(), its.getMinutes(), its.getSeconds(), its.getNanos(), its.getScale()));
    }
    
    @Override
    public String getTargetTypeName() {
        return LocalTime.class.getName();
    }
}
