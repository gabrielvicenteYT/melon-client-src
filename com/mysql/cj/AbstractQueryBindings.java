package com.mysql.cj;

import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.a.*;
import java.time.*;
import java.math.*;
import java.sql.*;
import java.text.*;
import com.mysql.cj.util.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import java.util.*;

public abstract class AbstractQueryBindings<T extends BindValue> implements QueryBindings<T>
{
    protected static final byte[] HEX_DIGITS;
    protected static final LocalDate DEFAULT_DATE;
    protected static final LocalTime DEFAULT_TIME;
    protected Session session;
    protected T[] bindValues;
    protected String charEncoding;
    protected int numberOfExecutions;
    protected RuntimeProperty<Boolean> useStreamLengthsInPrepStmts;
    protected RuntimeProperty<Boolean> preserveInstants;
    protected RuntimeProperty<Boolean> sendFractionalSeconds;
    protected RuntimeProperty<Boolean> sendFractionalSecondsForTime;
    private RuntimeProperty<Boolean> treatUtilDateAsTimestamp;
    protected boolean isLoadDataQuery;
    protected ColumnDefinition columnDefinition;
    static Map<Class<?>, MysqlType> DEFAULT_MYSQL_TYPES;
    private byte[] streamConvertBuf;
    
    public AbstractQueryBindings(final int parameterCount, final Session sess) {
        this.numberOfExecutions = 0;
        this.isLoadDataQuery = false;
        this.streamConvertBuf = null;
        this.session = sess;
        this.charEncoding = this.session.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue();
        this.preserveInstants = this.session.getPropertySet().getBooleanProperty(PropertyKey.preserveInstants);
        this.sendFractionalSeconds = this.session.getPropertySet().getBooleanProperty(PropertyKey.sendFractionalSeconds);
        this.sendFractionalSecondsForTime = this.session.getPropertySet().getBooleanProperty(PropertyKey.sendFractionalSecondsForTime);
        this.treatUtilDateAsTimestamp = this.session.getPropertySet().getBooleanProperty(PropertyKey.treatUtilDateAsTimestamp);
        this.useStreamLengthsInPrepStmts = this.session.getPropertySet().getBooleanProperty(PropertyKey.useStreamLengthsInPrepStmts);
        this.initBindValues(parameterCount);
    }
    
    protected abstract void initBindValues(final int p0);
    
    @Override
    public abstract AbstractQueryBindings<T> clone();
    
    @Override
    public void setColumnDefinition(final ColumnDefinition colDef) {
        this.columnDefinition = colDef;
    }
    
    @Override
    public boolean isLoadDataQuery() {
        return this.isLoadDataQuery;
    }
    
    @Override
    public void setLoadDataQuery(final boolean isLoadDataQuery) {
        this.isLoadDataQuery = isLoadDataQuery;
    }
    
    @Override
    public T[] getBindValues() {
        return this.bindValues;
    }
    
    @Override
    public void setBindValues(final T[] bindValues) {
        this.bindValues = bindValues;
    }
    
    @Override
    public boolean clearBindValues() {
        boolean hadLongData = false;
        if (this.bindValues != null) {
            for (int i = 0; i < this.bindValues.length; ++i) {
                if (this.bindValues[i] != null && this.bindValues[i].isStream()) {
                    hadLongData = true;
                }
                this.bindValues[i].reset();
            }
        }
        return hadLongData;
    }
    
    @Override
    public abstract void checkParameterSet(final int p0);
    
    @Override
    public void checkAllParametersSet() {
        for (int i = 0; i < this.bindValues.length; ++i) {
            this.checkParameterSet(i);
        }
    }
    
    @Override
    public int getNumberOfExecutions() {
        return this.numberOfExecutions;
    }
    
    @Override
    public void setNumberOfExecutions(final int numberOfExecutions) {
        this.numberOfExecutions = numberOfExecutions;
    }
    
    @Override
    public final synchronized void setValue(final int paramIndex, final byte[] val, final MysqlType type) {
        this.bindValues[paramIndex].setByteValue(val);
        this.bindValues[paramIndex].setMysqlType(type);
    }
    
    public final synchronized void setOrigValue(final int paramIndex, final byte[] val) {
        this.bindValues[paramIndex].setOrigByteValue(val);
    }
    
    @Override
    public synchronized byte[] getOrigBytes(final int parameterIndex) {
        return this.bindValues[parameterIndex].getOrigByteValue();
    }
    
    @Override
    public final synchronized void setValue(final int paramIndex, final String val, final MysqlType type) {
        final byte[] parameterAsBytes = StringUtils.getBytes(val, this.charEncoding);
        this.setValue(paramIndex, parameterAsBytes, type);
    }
    
    public final void hexEscapeBlock(final byte[] buf, final NativePacketPayload packet, final int size) {
        for (final byte b : buf) {
            final int lowBits = (b & 0xFF) / 16;
            final int highBits = (b & 0xFF) % 16;
            packet.writeInteger(NativeConstants.IntegerDataType.INT1, AbstractQueryBindings.HEX_DIGITS[lowBits]);
            packet.writeInteger(NativeConstants.IntegerDataType.INT1, AbstractQueryBindings.HEX_DIGITS[highBits]);
        }
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x, final MysqlType targetMysqlType) {
        int fractLen = -1;
        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
            fractLen = 0;
        }
        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0) {
            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
        }
        this.setTimestamp(parameterIndex, x, null, fractLen, targetMysqlType);
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal, final MysqlType targetMysqlType) {
        int fractLen = -1;
        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
            fractLen = 0;
        }
        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0 && this.columnDefinition.getFields()[parameterIndex].getDecimals() > 0) {
            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
        }
        this.setTimestamp(parameterIndex, x, cal, fractLen, targetMysqlType);
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, Timestamp x, final Calendar targetCalendar, final int fractionalLength, final MysqlType targetMysqlType) {
        if (x == null) {
            this.setNull(parameterIndex);
            return;
        }
        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
            x = TimeUtil.truncateFractionalSeconds(x);
        }
        this.bindTimestamp(parameterIndex, x, targetCalendar, fractionalLength, targetMysqlType);
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj) {
        if (parameterObj == null) {
            this.setNull(parameterIndex);
            return;
        }
        MysqlType defaultMysqlType = AbstractQueryBindings.DEFAULT_MYSQL_TYPES.get(parameterObj.getClass());
        if (defaultMysqlType == null) {
            final Optional<MysqlType> mysqlType = AbstractQueryBindings.DEFAULT_MYSQL_TYPES.entrySet().stream().filter(m -> m.getKey().isAssignableFrom(parameterObj.getClass())).map(m -> m.getValue()).findFirst();
            if (mysqlType.isPresent()) {
                defaultMysqlType = mysqlType.get();
            }
        }
        if (defaultMysqlType != null) {
            this.setObject(parameterIndex, parameterObj, defaultMysqlType);
        }
        else {
            this.setSerializableObject(parameterIndex, parameterObj);
        }
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj, final MysqlType targetMysqlType) {
        this.setObject(parameterIndex, parameterObj, targetMysqlType, (parameterObj instanceof BigDecimal) ? ((BigDecimal)parameterObj).scale() : 0);
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj, final MysqlType targetMysqlType, final int scaleOrLength) {
        if (parameterObj == null) {
            this.setNull(parameterIndex);
            return;
        }
        try {
            if (parameterObj instanceof LocalDate) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setLocalDate(parameterIndex, (LocalDate)parameterObj, targetMysqlType);
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        this.setLocalDateTime(parameterIndex, LocalDateTime.of((LocalDate)parameterObj, AbstractQueryBindings.DEFAULT_TIME), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        this.setInt(parameterIndex, ((LocalDate)parameterObj).getYear());
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof LocalTime) {
                switch (targetMysqlType) {
                    case TIME: {
                        this.setLocalTime(parameterIndex, (LocalTime)parameterObj, targetMysqlType);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, ((LocalTime)parameterObj).format((this.sendFractionalSeconds.getValue() && ((LocalTime)parameterObj).getNano() > 0) ? TimeUtil.TIME_FORMATTER_WITH_NANOS_NO_OFFSET : TimeUtil.TIME_FORMATTER_NO_FRACT_NO_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof LocalDateTime) {
                switch (targetMysqlType) {
                    case DATE:
                    case DATETIME:
                    case TIMESTAMP:
                    case TIME: {
                        this.setLocalDateTime(parameterIndex, (LocalDateTime)parameterObj, targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        this.setInt(parameterIndex, ((LocalDateTime)parameterObj).getYear());
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, ((LocalDateTime)parameterObj).format((this.sendFractionalSeconds.getValue() && ((LocalDateTime)parameterObj).getNano() > 0) ? TimeUtil.DATETIME_FORMATTER_WITH_NANOS_NO_OFFSET : TimeUtil.DATETIME_FORMATTER_NO_FRACT_NO_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof OffsetTime) {
                switch (targetMysqlType) {
                    case TIME: {
                        this.setLocalTime(parameterIndex, ((OffsetTime)parameterObj).withOffsetSameInstant(ZoneOffset.ofTotalSeconds(this.session.getServerSession().getDefaultTimeZone().getRawOffset() / 1000)).toLocalTime(), targetMysqlType);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, ((OffsetTime)parameterObj).format((this.sendFractionalSeconds.getValue() && ((OffsetTime)parameterObj).getNano() > 0) ? TimeUtil.TIME_FORMATTER_WITH_NANOS_WITH_OFFSET : TimeUtil.TIME_FORMATTER_NO_FRACT_WITH_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof OffsetDateTime) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setLocalDate(parameterIndex, ((OffsetDateTime)parameterObj).atZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalDate(), targetMysqlType);
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        Timestamp ts = Timestamp.valueOf(((OffsetDateTime)parameterObj).atZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalDateTime());
                        int fractLen = -1;
                        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
                            fractLen = 0;
                        }
                        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0 && this.columnDefinition.getFields()[parameterIndex].getDecimals() > 0) {
                            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
                        }
                        if (fractLen == 0) {
                            ts = TimeUtil.truncateFractionalSeconds(ts);
                        }
                        this.bindTimestamp(parameterIndex, ts, null, fractLen, targetMysqlType);
                        break;
                    }
                    case TIME: {
                        this.setLocalTime(parameterIndex, ((OffsetDateTime)parameterObj).atZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalTime(), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        this.setInt(parameterIndex, ((OffsetDateTime)parameterObj).atZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).getYear());
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, ((OffsetDateTime)parameterObj).format((this.sendFractionalSeconds.getValue() && ((OffsetDateTime)parameterObj).getNano() > 0) ? TimeUtil.DATETIME_FORMATTER_WITH_NANOS_WITH_OFFSET : TimeUtil.DATETIME_FORMATTER_NO_FRACT_WITH_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof ZonedDateTime) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setLocalDate(parameterIndex, ((ZonedDateTime)parameterObj).withZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalDate(), targetMysqlType);
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        Timestamp ts = Timestamp.valueOf(((ZonedDateTime)parameterObj).withZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalDateTime());
                        int fractLen = -1;
                        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
                            fractLen = 0;
                        }
                        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0 && this.columnDefinition.getFields()[parameterIndex].getDecimals() > 0) {
                            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
                        }
                        if (fractLen == 0) {
                            ts = TimeUtil.truncateFractionalSeconds(ts);
                        }
                        this.bindTimestamp(parameterIndex, ts, null, fractLen, targetMysqlType);
                        break;
                    }
                    case TIME: {
                        this.setLocalTime(parameterIndex, ((ZonedDateTime)parameterObj).withZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalTime(), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        this.setInt(parameterIndex, ((ZonedDateTime)parameterObj).withZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId()).getYear());
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, ((ZonedDateTime)parameterObj).format((this.sendFractionalSeconds.getValue() && ((ZonedDateTime)parameterObj).getNano() > 0) ? TimeUtil.DATETIME_FORMATTER_WITH_NANOS_WITH_OFFSET : TimeUtil.DATETIME_FORMATTER_NO_FRACT_WITH_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof Date) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setDate(parameterIndex, (Date)parameterObj);
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        this.setTimestamp(parameterIndex, new Timestamp(((java.util.Date)parameterObj).getTime()), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        final Calendar cal = Calendar.getInstance();
                        cal.setTime((java.util.Date)parameterObj);
                        this.setInt(parameterIndex, cal.get(1));
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof Timestamp) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setDate(parameterIndex, new Date(((java.util.Date)parameterObj).getTime()));
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        this.setTimestamp(parameterIndex, (Timestamp)parameterObj, targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        final Calendar cal = Calendar.getInstance();
                        cal.setTime((java.util.Date)parameterObj);
                        this.setInt(parameterIndex, cal.get(1));
                        break;
                    }
                    case TIME: {
                        this.setLocalTime(parameterIndex, ((Timestamp)parameterObj).toLocalDateTime().toLocalTime(), targetMysqlType);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        String val = parameterObj.toString();
                        final int dotPos;
                        if ((((Timestamp)parameterObj).getNanos() == 0 || !this.sendFractionalSeconds.getValue()) && (dotPos = val.indexOf(".")) > 0) {
                            val = val.substring(0, dotPos);
                        }
                        this.setString(parameterIndex, val);
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof Time) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setDate(parameterIndex, new Date(((java.util.Date)parameterObj).getTime()));
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        Timestamp ts = new Timestamp(((Time)parameterObj).getTime());
                        int fractLen = -1;
                        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSecondsForTime.getValue() || !this.sendFractionalSeconds.getValue()) {
                            fractLen = 0;
                        }
                        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0 && this.columnDefinition.getFields()[parameterIndex].getDecimals() > 0) {
                            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
                        }
                        if (fractLen == 0) {
                            ts = TimeUtil.truncateFractionalSeconds(ts);
                        }
                        this.bindTimestamp(parameterIndex, ts, null, fractLen, MysqlType.DATETIME);
                        break;
                    }
                    case YEAR: {
                        final Calendar cal2 = Calendar.getInstance();
                        cal2.setTime((java.util.Date)parameterObj);
                        this.setInt(parameterIndex, cal2.get(1));
                        break;
                    }
                    case TIME: {
                        this.setTime(parameterIndex, (Time)parameterObj);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, TimeUtil.getSimpleDateFormat((this.session.getServerSession().getCapabilities().serverSupportsFracSecs() && this.sendFractionalSeconds.getValue() && this.sendFractionalSecondsForTime.getValue() && TimeUtil.hasFractionalSeconds((Time)parameterObj)) ? "HH:mm:ss.SSS" : "HH:mm:ss", null).format(parameterObj));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof java.util.Date) {
                if (!this.treatUtilDateAsTimestamp.getValue()) {
                    this.setSerializableObject(parameterIndex, parameterObj);
                    return;
                }
                switch (targetMysqlType) {
                    case DATE: {
                        this.setDate(parameterIndex, new Date(((java.util.Date)parameterObj).getTime()));
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        this.setTimestamp(parameterIndex, new Timestamp(((java.util.Date)parameterObj).getTime()), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        final Calendar cal = Calendar.getInstance();
                        cal.setTime((java.util.Date)parameterObj);
                        this.setInt(parameterIndex, cal.get(1));
                        break;
                    }
                    case TIME: {
                        final LocalTime lt = ((java.util.Date)parameterObj).toInstant().atZone(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalTime();
                        this.setLocalTime(parameterIndex, lt, targetMysqlType);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, TimeUtil.getSimpleDateFormat((this.session.getServerSession().getCapabilities().serverSupportsFracSecs() && this.sendFractionalSeconds.getValue() && ((java.util.Date)parameterObj).toInstant().getNano() > 0) ? "yyyy-MM-dd HH:mm:ss.SSS" : "yyyy-MM-dd HH:mm:ss", null).format(parameterObj));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof Calendar) {
                switch (targetMysqlType) {
                    case DATE: {
                        this.setDate(parameterIndex, new Date(((Calendar)parameterObj).getTimeInMillis()));
                        break;
                    }
                    case DATETIME:
                    case TIMESTAMP: {
                        this.setTimestamp(parameterIndex, new Timestamp(((Calendar)parameterObj).getTimeInMillis()), targetMysqlType);
                        break;
                    }
                    case YEAR: {
                        this.setInt(parameterIndex, ((Calendar)parameterObj).get(1));
                        break;
                    }
                    case TIME: {
                        final LocalTime lt2 = ((Calendar)parameterObj).toInstant().atZone(this.session.getServerSession().getDefaultTimeZone().toZoneId()).toLocalTime();
                        this.setLocalTime(parameterIndex, lt2, targetMysqlType);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        final ZonedDateTime zdt = ZonedDateTime.ofInstant(((Calendar)parameterObj).toInstant(), ((Calendar)parameterObj).getTimeZone().toZoneId()).withZoneSameInstant(this.session.getServerSession().getDefaultTimeZone().toZoneId());
                        this.setString(parameterIndex, zdt.format((zdt.getNano() > 0 && this.session.getServerSession().getCapabilities().serverSupportsFracSecs() && this.sendFractionalSeconds.getValue()) ? TimeUtil.DATETIME_FORMATTER_WITH_MILLIS_NO_OFFSET : TimeUtil.DATETIME_FORMATTER_NO_FRACT_NO_OFFSET));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof String) {
                switch (targetMysqlType) {
                    case BOOLEAN: {
                        if ("true".equalsIgnoreCase((String)parameterObj) || "Y".equalsIgnoreCase((String)parameterObj)) {
                            this.setBoolean(parameterIndex, true);
                            break;
                        }
                        if ("false".equalsIgnoreCase((String)parameterObj) || "N".equalsIgnoreCase((String)parameterObj)) {
                            this.setBoolean(parameterIndex, false);
                            break;
                        }
                        if (((String)parameterObj).matches("-?\\d+\\.?\\d*")) {
                            this.setBoolean(parameterIndex, !((String)parameterObj).matches("-?[0]+[.]*[0]*"));
                            break;
                        }
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.66", new Object[] { parameterObj }), this.session.getExceptionInterceptor());
                    }
                    case BIT: {
                        if ("1".equals(parameterObj) || "0".equals(parameterObj)) {
                            this.setInt(parameterIndex, Integer.valueOf((String)parameterObj));
                            break;
                        }
                        final boolean parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
                        this.setInt(parameterIndex, parameterAsBoolean ? 1 : 0);
                        break;
                    }
                    case TINYINT:
                    case TINYINT_UNSIGNED:
                    case SMALLINT:
                    case SMALLINT_UNSIGNED:
                    case MEDIUMINT:
                    case MEDIUMINT_UNSIGNED:
                    case INT:
                    case INT_UNSIGNED: {
                        this.setInt(parameterIndex, Integer.valueOf((String)parameterObj));
                        break;
                    }
                    case BIGINT: {
                        this.setLong(parameterIndex, Long.valueOf((String)parameterObj));
                        break;
                    }
                    case BIGINT_UNSIGNED: {
                        this.setLong(parameterIndex, new BigInteger((String)parameterObj).longValue());
                        break;
                    }
                    case FLOAT:
                    case FLOAT_UNSIGNED: {
                        this.setFloat(parameterIndex, Float.valueOf((String)parameterObj));
                        break;
                    }
                    case DOUBLE:
                    case DOUBLE_UNSIGNED: {
                        this.setDouble(parameterIndex, Double.valueOf((String)parameterObj));
                        break;
                    }
                    case DECIMAL:
                    case DECIMAL_UNSIGNED: {
                        final BigDecimal parameterAsNum = new BigDecimal((String)parameterObj);
                        BigDecimal scaledBigDecimal = null;
                        try {
                            scaledBigDecimal = parameterAsNum.setScale(scaleOrLength);
                        }
                        catch (ArithmeticException ex2) {
                            try {
                                scaledBigDecimal = parameterAsNum.setScale(scaleOrLength, 4);
                            }
                            catch (ArithmeticException arEx) {
                                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.65", new Object[] { scaleOrLength, parameterAsNum }), this.session.getExceptionInterceptor());
                            }
                        }
                        this.setBigDecimal(parameterIndex, scaledBigDecimal);
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case ENUM:
                    case SET:
                    case JSON: {
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case BINARY:
                    case GEOMETRY:
                    case VARBINARY:
                    case TINYBLOB:
                    case BLOB:
                    case MEDIUMBLOB:
                    case LONGBLOB: {
                        this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charEncoding));
                        break;
                    }
                    case DATE:
                    case DATETIME:
                    case TIMESTAMP:
                    case YEAR: {
                        final ParsePosition pp = new ParsePosition(0);
                        final DateFormat sdf = new SimpleDateFormat(TimeUtil.getDateTimePattern((String)parameterObj, false), Locale.US);
                        this.setObject(parameterIndex, sdf.parse((String)parameterObj, pp), targetMysqlType, scaleOrLength);
                        break;
                    }
                    case TIME: {
                        final DateFormat sdf = new SimpleDateFormat(TimeUtil.getDateTimePattern((String)parameterObj, true), Locale.US);
                        this.setTime(parameterIndex, new Time(sdf.parse((String)parameterObj).getTime()));
                        break;
                    }
                    case UNKNOWN: {
                        this.setSerializableObject(parameterIndex, parameterObj);
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof InputStream) {
                this.setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
            }
            else if (parameterObj instanceof Boolean) {
                switch (targetMysqlType) {
                    case BOOLEAN: {
                        this.setBoolean(parameterIndex, (boolean)parameterObj);
                        break;
                    }
                    case YEAR:
                    case BIT:
                    case TINYINT:
                    case TINYINT_UNSIGNED:
                    case SMALLINT:
                    case SMALLINT_UNSIGNED:
                    case MEDIUMINT:
                    case MEDIUMINT_UNSIGNED:
                    case INT:
                    case INT_UNSIGNED: {
                        this.setInt(parameterIndex, ((boolean)parameterObj) ? 1 : 0);
                        break;
                    }
                    case BIGINT:
                    case BIGINT_UNSIGNED: {
                        this.setLong(parameterIndex, ((boolean)parameterObj) ? 1 : 0);
                        break;
                    }
                    case FLOAT:
                    case FLOAT_UNSIGNED: {
                        this.setFloat(parameterIndex, ((boolean)parameterObj) ? 1.0f : 0.0f);
                        break;
                    }
                    case DOUBLE:
                    case DOUBLE_UNSIGNED: {
                        this.setDouble(parameterIndex, ((boolean)parameterObj) ? 1.0 : 0.0);
                        break;
                    }
                    case DECIMAL:
                    case DECIMAL_UNSIGNED: {
                        this.setBigDecimal(parameterIndex, new BigDecimal(parameterObj ? 1.0 : 0.0));
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT: {
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else if (parameterObj instanceof Number) {
                final Number parameterAsNum2 = (Number)parameterObj;
                switch (targetMysqlType) {
                    case BOOLEAN: {
                        this.setBoolean(parameterIndex, parameterAsNum2.intValue() != 0);
                        break;
                    }
                    case YEAR:
                    case BIT:
                    case TINYINT:
                    case TINYINT_UNSIGNED:
                    case SMALLINT:
                    case SMALLINT_UNSIGNED:
                    case MEDIUMINT:
                    case MEDIUMINT_UNSIGNED:
                    case INT:
                    case INT_UNSIGNED: {
                        this.setInt(parameterIndex, parameterAsNum2.intValue());
                        break;
                    }
                    case BIGINT:
                    case BIGINT_UNSIGNED: {
                        this.setLong(parameterIndex, parameterAsNum2.longValue());
                        break;
                    }
                    case FLOAT:
                    case FLOAT_UNSIGNED: {
                        this.setFloat(parameterIndex, parameterAsNum2.floatValue());
                        break;
                    }
                    case DOUBLE:
                    case DOUBLE_UNSIGNED: {
                        this.setDouble(parameterIndex, parameterAsNum2.doubleValue());
                        break;
                    }
                    case DECIMAL:
                    case DECIMAL_UNSIGNED: {
                        if (parameterAsNum2 instanceof BigDecimal) {
                            BigDecimal scaledBigDecimal = null;
                            try {
                                scaledBigDecimal = ((BigDecimal)parameterAsNum2).setScale(scaleOrLength);
                            }
                            catch (ArithmeticException ex2) {
                                try {
                                    scaledBigDecimal = ((BigDecimal)parameterAsNum2).setScale(scaleOrLength, 4);
                                }
                                catch (ArithmeticException arEx) {
                                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.65", new Object[] { scaleOrLength, parameterAsNum2 }), this.session.getExceptionInterceptor());
                                }
                            }
                            this.setBigDecimal(parameterIndex, scaledBigDecimal);
                            break;
                        }
                        if (parameterAsNum2 instanceof BigInteger) {
                            this.setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum2, scaleOrLength));
                            break;
                        }
                        this.setBigDecimal(parameterIndex, new BigDecimal(parameterAsNum2.doubleValue()));
                        break;
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case ENUM:
                    case SET:
                    case JSON: {
                        if (parameterObj instanceof BigDecimal) {
                            this.setString(parameterIndex, StringUtils.fixDecimalExponent(((BigDecimal)parameterObj).toPlainString()));
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case BINARY:
                    case GEOMETRY:
                    case VARBINARY:
                    case TINYBLOB:
                    case BLOB:
                    case MEDIUMBLOB:
                    case LONGBLOB: {
                        this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charEncoding));
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
            else {
                switch (targetMysqlType) {
                    case BOOLEAN: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.66", new Object[] { parameterObj.getClass().getName() }), this.session.getExceptionInterceptor());
                    }
                    case CHAR:
                    case VARCHAR:
                    case TINYTEXT:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case ENUM:
                    case SET:
                    case JSON: {
                        if (parameterObj instanceof BigDecimal) {
                            this.setString(parameterIndex, StringUtils.fixDecimalExponent(((BigDecimal)parameterObj).toPlainString()));
                            break;
                        }
                        if (parameterObj instanceof Clob) {
                            this.setClob(parameterIndex, (Clob)parameterObj);
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case BINARY:
                    case GEOMETRY:
                    case VARBINARY:
                    case TINYBLOB:
                    case BLOB:
                    case MEDIUMBLOB:
                    case LONGBLOB: {
                        if (parameterObj instanceof byte[]) {
                            this.setBytes(parameterIndex, (byte[])parameterObj);
                            break;
                        }
                        if (parameterObj instanceof Blob) {
                            this.setBlob(parameterIndex, (Blob)parameterObj);
                            break;
                        }
                        this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charEncoding));
                        break;
                    }
                    case UNKNOWN: {
                        this.setSerializableObject(parameterIndex, parameterObj);
                        break;
                    }
                    default: {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.67", new Object[] { parameterObj.getClass().getName(), targetMysqlType.toString() }), this.session.getExceptionInterceptor());
                    }
                }
            }
        }
        catch (Exception ex) {
            throw ExceptionFactory.createException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + ex.getClass().getName() + Messages.getString("PreparedStatement.19") + ex.getMessage(), ex, this.session.getExceptionInterceptor());
        }
    }
    
    protected final void setSerializableObject(final int parameterIndex, final Object parameterObj) {
        try {
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            final ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
            objectOut.writeObject(parameterObj);
            objectOut.flush();
            objectOut.close();
            bytesOut.flush();
            bytesOut.close();
            final byte[] buf = bytesOut.toByteArray();
            final ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
            this.setBinaryStream(parameterIndex, bytesIn, buf.length);
            this.bindValues[parameterIndex].setMysqlType(MysqlType.BINARY);
        }
        catch (Exception ex) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.54") + ex.getClass().getName(), ex, this.session.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isNull(final int parameterIndex) {
        return this.bindValues[parameterIndex].isNull();
    }
    
    @Override
    public byte[] getBytesRepresentation(final int parameterIndex) {
        if (this.bindValues[parameterIndex].isStream()) {
            return this.streamToBytes(parameterIndex, this.session.getPropertySet().getBooleanProperty(PropertyKey.useStreamLengthsInPrepStmts).getValue());
        }
        final byte[] parameterVal = this.bindValues[parameterIndex].getByteValue();
        if (parameterVal == null) {
            return null;
        }
        return StringUtils.unquoteBytes(parameterVal);
    }
    
    private final byte[] streamToBytes(final int parameterIndex, boolean useLength) {
        InputStream in = this.bindValues[parameterIndex].getStreamValue();
        in.mark(Integer.MAX_VALUE);
        try {
            if (this.streamConvertBuf == null) {
                this.streamConvertBuf = new byte[4096];
            }
            if (this.bindValues[parameterIndex].getStreamLength() == -1L) {
                useLength = false;
            }
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            int bc = useLength ? Util.readBlock(in, this.streamConvertBuf, (int)this.bindValues[parameterIndex].getStreamLength(), null) : Util.readBlock(in, this.streamConvertBuf, null);
            int lengthLeftToRead = (int)this.bindValues[parameterIndex].getStreamLength() - bc;
            while (bc > 0) {
                bytesOut.write(this.streamConvertBuf, 0, bc);
                if (useLength) {
                    bc = Util.readBlock(in, this.streamConvertBuf, lengthLeftToRead, null);
                    if (bc <= 0) {
                        continue;
                    }
                    lengthLeftToRead -= bc;
                }
                else {
                    bc = Util.readBlock(in, this.streamConvertBuf, null);
                }
            }
            return bytesOut.toByteArray();
        }
        finally {
            try {
                in.reset();
            }
            catch (IOException ex) {}
            if (this.session.getPropertySet().getBooleanProperty(PropertyKey.autoClosePStmtStreams).getValue()) {
                try {
                    in.close();
                }
                catch (IOException ex2) {}
                in = null;
            }
        }
    }
    
    static {
        HEX_DIGITS = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
        DEFAULT_DATE = LocalDate.of(1970, 1, 1);
        DEFAULT_TIME = LocalTime.of(0, 0);
        (AbstractQueryBindings.DEFAULT_MYSQL_TYPES = new HashMap<Class<?>, MysqlType>()).put(String.class, MysqlType.VARCHAR);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Date.class, MysqlType.DATE);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Time.class, MysqlType.TIME);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Timestamp.class, MysqlType.TIMESTAMP);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Byte.class, MysqlType.INT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(BigDecimal.class, MysqlType.DECIMAL);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Short.class, MysqlType.SMALLINT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Integer.class, MysqlType.INT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Long.class, MysqlType.BIGINT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Float.class, MysqlType.FLOAT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Double.class, MysqlType.DOUBLE);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(byte[].class, MysqlType.BINARY);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Boolean.class, MysqlType.BOOLEAN);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(LocalDate.class, MysqlType.DATE);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(LocalTime.class, MysqlType.TIME);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(LocalDateTime.class, MysqlType.DATETIME);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(OffsetTime.class, MysqlType.TIME);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(OffsetDateTime.class, MysqlType.TIMESTAMP);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(ZonedDateTime.class, MysqlType.TIMESTAMP);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Blob.class, MysqlType.BLOB);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Clob.class, MysqlType.TEXT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(BigInteger.class, MysqlType.BIGINT);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(java.util.Date.class, MysqlType.TIMESTAMP);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(Calendar.class, MysqlType.TIMESTAMP);
        AbstractQueryBindings.DEFAULT_MYSQL_TYPES.put(InputStream.class, MysqlType.BLOB);
    }
}
