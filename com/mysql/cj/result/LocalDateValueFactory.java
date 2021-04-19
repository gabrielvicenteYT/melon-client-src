package com.mysql.cj.result;

import java.time.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public class LocalDateValueFactory extends AbstractDateTimeValueFactory<LocalDate>
{
    private WarningListener warningListener;
    
    public LocalDateValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    public LocalDateValueFactory(final PropertySet pset, final WarningListener warningListener) {
        this(pset);
        this.warningListener = warningListener;
    }
    
    public LocalDate localCreateFromDate(final InternalDate idate) {
        if (idate.getYear() == 0 && idate.getMonth() == 0 && idate.getDay() == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDate.of(idate.getYear(), idate.getMonth(), idate.getDay());
    }
    
    public LocalDate localCreateFromTimestamp(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromDate(its);
    }
    
    public LocalDate localCreateFromDatetime(final InternalTimestamp its) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromDate(its);
    }
    
    @Override
    LocalDate localCreateFromTime(final InternalTime it) {
        return LocalDate.of(1970, 1, 1);
    }
    
    @Override
    public String getTargetTypeName() {
        return LocalDate.class.getName();
    }
}
