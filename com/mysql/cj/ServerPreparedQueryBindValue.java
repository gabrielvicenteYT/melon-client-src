package com.mysql.cj;

import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.util.*;
import com.mysql.cj.exceptions.*;
import java.time.*;
import java.util.*;
import java.sql.*;

public class ServerPreparedQueryBindValue extends ClientPreparedQueryBindValue implements BindValue
{
    public long boundBeforeExecutionNum;
    public int bufferType;
    public Calendar calendar;
    PropertySet pset;
    private TimeZone defaultTimeZone;
    private TimeZone connectionTimeZone;
    private RuntimeProperty<Boolean> cacheDefaultTimeZone;
    protected String charEncoding;
    
    public ServerPreparedQueryBindValue(final TimeZone defaultTimeZone, final TimeZone connectionTimeZone, final PropertySet pset) {
        this.boundBeforeExecutionNum = 0L;
        this.cacheDefaultTimeZone = null;
        this.charEncoding = null;
        this.pset = pset;
        this.defaultTimeZone = defaultTimeZone;
        this.connectionTimeZone = connectionTimeZone;
        this.cacheDefaultTimeZone = pset.getBooleanProperty(PropertyKey.cacheDefaultTimeZone);
    }
    
    @Override
    public ServerPreparedQueryBindValue clone() {
        return new ServerPreparedQueryBindValue(this);
    }
    
    private ServerPreparedQueryBindValue(final ServerPreparedQueryBindValue copyMe) {
        super(copyMe);
        this.boundBeforeExecutionNum = 0L;
        this.cacheDefaultTimeZone = null;
        this.charEncoding = null;
        this.pset = copyMe.pset;
        this.defaultTimeZone = copyMe.defaultTimeZone;
        this.connectionTimeZone = copyMe.connectionTimeZone;
        this.cacheDefaultTimeZone = copyMe.cacheDefaultTimeZone;
        this.bufferType = copyMe.bufferType;
        this.calendar = copyMe.calendar;
        this.charEncoding = copyMe.charEncoding;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.calendar = null;
        this.charEncoding = null;
    }
    
    public boolean resetToType(final int bufType, final long numberOfExecutions) {
        boolean sendTypesToServer = false;
        this.reset();
        if (bufType != 6 || this.bufferType == 0) {
            if (this.bufferType != bufType) {
                sendTypesToServer = true;
                this.bufferType = bufType;
            }
        }
        this.isSet = true;
        this.boundBeforeExecutionNum = numberOfExecutions;
        return sendTypesToServer;
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(final boolean quoteIfNeeded) {
        if (this.isStream) {
            return "' STREAM DATA '";
        }
        if (this.isNull) {
            return "NULL";
        }
        switch (this.bufferType) {
            case 1:
            case 2:
            case 3:
            case 8: {
                return String.valueOf((long)this.value);
            }
            case 4: {
                return String.valueOf((float)this.value);
            }
            case 5: {
                return String.valueOf((double)this.value);
            }
            case 7:
            case 10:
            case 11:
            case 12:
            case 15:
            case 253:
            case 254: {
                if (quoteIfNeeded) {
                    return "'" + String.valueOf(this.value) + "'";
                }
                return String.valueOf(this.value);
            }
            default: {
                if (this.value instanceof byte[]) {
                    return "byte data";
                }
                if (quoteIfNeeded) {
                    return "'" + String.valueOf(this.value) + "'";
                }
                return String.valueOf(this.value);
            }
        }
    }
    
    public long getBoundLength() {
        if (this.isNull) {
            return 0L;
        }
        if (this.isStream) {
            return this.streamLength;
        }
        switch (this.bufferType) {
            case 1: {
                return 1L;
            }
            case 2: {
                return 2L;
            }
            case 3: {
                return 4L;
            }
            case 8: {
                return 8L;
            }
            case 4: {
                return 4L;
            }
            case 5: {
                return 8L;
            }
            case 11: {
                return 9L;
            }
            case 10: {
                return 7L;
            }
            case 7:
            case 12: {
                return 11L;
            }
            case 0:
            case 15:
            case 246:
            case 253:
            case 254: {
                if (this.value instanceof byte[]) {
                    return ((byte[])this.value).length;
                }
                return ((String)this.value).length();
            }
            default: {
                return 0L;
            }
        }
    }
    
    public void storeBinding(final NativePacketPayload intoPacket, final boolean isLoadDataQuery, final String characterEncoding, final ExceptionInterceptor interceptor) {
        synchronized (this) {
            try {
                switch (this.bufferType) {
                    case 1: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, (long)this.value);
                    }
                    case 2: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT2, (long)this.value);
                    }
                    case 3: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, (long)this.value);
                    }
                    case 8: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT8, (long)this.value);
                    }
                    case 4: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, Float.floatToIntBits((float)this.value));
                    }
                    case 5: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT8, Double.doubleToLongBits((double)this.value));
                    }
                    case 11: {
                        this.storeTime(intoPacket);
                    }
                    case 10: {
                        this.storeDate(intoPacket);
                    }
                    case 7:
                    case 12: {
                        this.storeDateTime(intoPacket, this.bufferType);
                    }
                    case 0:
                    case 15:
                    case 246:
                    case 253:
                    case 254: {
                        if (this.value instanceof byte[]) {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, (byte[])this.value);
                        }
                        else if (!isLoadDataQuery) {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)this.value, characterEncoding));
                        }
                        else {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)this.value));
                        }
                    }
                }
            }
            catch (CJException uEE) {
                throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.22") + characterEncoding + "'", uEE, interceptor);
            }
        }
    }
    
    private void storeDate(final NativePacketPayload intoPacket) {
        synchronized (this) {
            int year;
            int month;
            int day;
            if (this.value instanceof LocalDate) {
                year = ((LocalDate)this.value).getYear();
                month = ((LocalDate)this.value).getMonthValue();
                day = ((LocalDate)this.value).getDayOfMonth();
            }
            else if (this.value instanceof LocalTime) {
                year = AbstractQueryBindings.DEFAULT_DATE.getYear();
                month = AbstractQueryBindings.DEFAULT_DATE.getMonthValue();
                day = AbstractQueryBindings.DEFAULT_DATE.getDayOfMonth();
            }
            else if (this.value instanceof LocalDateTime) {
                year = ((LocalDateTime)this.value).getYear();
                month = ((LocalDateTime)this.value).getMonthValue();
                day = ((LocalDateTime)this.value).getDayOfMonth();
            }
            else {
                if (this.calendar == null) {
                    this.calendar = Calendar.getInstance(this.cacheDefaultTimeZone.getValue() ? this.defaultTimeZone : TimeZone.getDefault(), Locale.US);
                }
                this.calendar.setTime((Date)this.value);
                this.calendar.set(11, 0);
                this.calendar.set(12, 0);
                this.calendar.set(13, 0);
                year = this.calendar.get(1);
                month = this.calendar.get(2) + 1;
                day = this.calendar.get(5);
            }
            intoPacket.ensureCapacity(5);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 4L);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT2, year);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, month);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, day);
        }
    }
    
    private void storeTime(final NativePacketPayload intoPacket) {
        int hours;
        int minutes;
        int seconds;
        int microseconds;
        if (this.value instanceof LocalDateTime) {
            hours = ((LocalDateTime)this.value).getHour();
            minutes = ((LocalDateTime)this.value).getMinute();
            seconds = ((LocalDateTime)this.value).getSecond();
            microseconds = ((LocalDateTime)this.value).getNano() / 1000;
        }
        else if (this.value instanceof LocalTime) {
            hours = ((LocalTime)this.value).getHour();
            minutes = ((LocalTime)this.value).getMinute();
            seconds = ((LocalTime)this.value).getSecond();
            microseconds = ((LocalTime)this.value).getNano() / 1000;
        }
        else {
            if (this.calendar == null) {
                this.calendar = Calendar.getInstance(this.defaultTimeZone, Locale.US);
            }
            this.calendar.setTime((Date)this.value);
            hours = this.calendar.get(11);
            minutes = this.calendar.get(12);
            seconds = this.calendar.get(13);
            microseconds = this.calendar.get(14) * 1000;
        }
        intoPacket.ensureCapacity((microseconds > 0) ? 13 : 9);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, (microseconds > 0) ? 12L : 8L);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, 0L);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, hours);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, minutes);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, seconds);
        if (microseconds > 0) {
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, microseconds);
        }
    }
    
    private void storeDateTime(final NativePacketPayload intoPacket, final int mysqlType) {
        synchronized (this) {
            int year = 0;
            int month = 0;
            int day = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            int microseconds = 0;
            if (this.value instanceof LocalDate) {
                year = ((LocalDate)this.value).getYear();
                month = ((LocalDate)this.value).getMonthValue();
                day = ((LocalDate)this.value).getDayOfMonth();
            }
            else if (this.value instanceof LocalTime) {
                year = AbstractQueryBindings.DEFAULT_DATE.getYear();
                month = AbstractQueryBindings.DEFAULT_DATE.getMonthValue();
                day = AbstractQueryBindings.DEFAULT_DATE.getDayOfMonth();
                hours = ((LocalTime)this.value).getHour();
                minutes = ((LocalTime)this.value).getMinute();
                seconds = ((LocalTime)this.value).getSecond();
                microseconds = ((LocalTime)this.value).getNano() / 1000;
            }
            else if (this.value instanceof LocalDateTime) {
                year = ((LocalDateTime)this.value).getYear();
                month = ((LocalDateTime)this.value).getMonthValue();
                day = ((LocalDateTime)this.value).getDayOfMonth();
                hours = ((LocalDateTime)this.value).getHour();
                minutes = ((LocalDateTime)this.value).getMinute();
                seconds = ((LocalDateTime)this.value).getSecond();
                microseconds = ((LocalDateTime)this.value).getNano() / 1000;
            }
            else {
                if (this.calendar == null) {
                    this.calendar = Calendar.getInstance((mysqlType == 7 && this.pset.getBooleanProperty(PropertyKey.preserveInstants).getValue()) ? this.connectionTimeZone : this.defaultTimeZone, Locale.US);
                }
                this.calendar.setTime((Date)this.value);
                if (this.value instanceof java.sql.Date) {
                    this.calendar.set(11, 0);
                    this.calendar.set(12, 0);
                    this.calendar.set(13, 0);
                }
                year = this.calendar.get(1);
                month = this.calendar.get(2) + 1;
                day = this.calendar.get(5);
                hours = this.calendar.get(11);
                minutes = this.calendar.get(12);
                seconds = this.calendar.get(13);
                if (this.value instanceof Timestamp) {
                    microseconds = ((Timestamp)this.value).getNanos() / 1000;
                }
                else {
                    microseconds = this.calendar.get(14) * 1000;
                }
            }
            intoPacket.ensureCapacity((microseconds > 0) ? 12 : 8);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, (microseconds > 0) ? 11L : 7L);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT2, year);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, month);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, day);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, hours);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, minutes);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, seconds);
            if (microseconds > 0) {
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, microseconds);
            }
        }
    }
    
    @Override
    public byte[] getByteValue() {
        if (!this.isStream) {
            return (this.charEncoding != null) ? StringUtils.getBytes(this.toString(), this.charEncoding) : this.toString().getBytes();
        }
        return null;
    }
}
