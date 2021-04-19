package com.mysql.cj;

import java.util.concurrent.atomic.*;
import com.mysql.cj.exceptions.*;
import java.math.*;
import java.io.*;
import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import java.time.*;
import java.sql.*;

public class ServerPreparedQueryBindings extends AbstractQueryBindings<ServerPreparedQueryBindValue>
{
    private AtomicBoolean sendTypesToServer;
    private boolean longParameterSwitchDetected;
    
    public ServerPreparedQueryBindings(final int parameterCount, final Session sess) {
        super(parameterCount, sess);
        this.sendTypesToServer = new AtomicBoolean(false);
        this.longParameterSwitchDetected = false;
    }
    
    @Override
    protected void initBindValues(final int parameterCount) {
        this.bindValues = (T[])new ServerPreparedQueryBindValue[parameterCount];
        for (int i = 0; i < parameterCount; ++i) {
            ((ServerPreparedQueryBindValue[])this.bindValues)[i] = new ServerPreparedQueryBindValue(this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getSessionTimeZone(), this.session.getPropertySet());
        }
    }
    
    @Override
    public ServerPreparedQueryBindings clone() {
        final ServerPreparedQueryBindings newBindings = new ServerPreparedQueryBindings(((ServerPreparedQueryBindValue[])this.bindValues).length, this.session);
        final ServerPreparedQueryBindValue[] bvs = new ServerPreparedQueryBindValue[((ServerPreparedQueryBindValue[])this.bindValues).length];
        for (int i = 0; i < ((ServerPreparedQueryBindValue[])this.bindValues).length; ++i) {
            bvs[i] = ((ServerPreparedQueryBindValue[])this.bindValues)[i].clone();
        }
        newBindings.bindValues = (T[])bvs;
        newBindings.sendTypesToServer = this.sendTypesToServer;
        newBindings.longParameterSwitchDetected = this.longParameterSwitchDetected;
        newBindings.isLoadDataQuery = this.isLoadDataQuery;
        return newBindings;
    }
    
    public ServerPreparedQueryBindValue getBinding(final int parameterIndex, final boolean forLongData) {
        if (((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex] != null) {
            if (((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex].isStream && !forLongData) {
                this.longParameterSwitchDetected = true;
            }
        }
        return ((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex];
    }
    
    @Override
    public void checkParameterSet(final int columnIndex) {
        if (!((ServerPreparedQueryBindValue[])this.bindValues)[columnIndex].isSet()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ServerPreparedStatement.13") + (columnIndex + 1) + Messages.getString("ServerPreparedStatement.14"));
        }
    }
    
    public AtomicBoolean getSendTypesToServer() {
        return this.sendTypesToServer;
    }
    
    public boolean isLongParameterSwitchDetected() {
        return this.longParameterSwitchDetected;
    }
    
    public void setLongParameterSwitchDetected(final boolean longParameterSwitchDetected) {
        this.longParameterSwitchDetected = longParameterSwitchDetected;
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x) {
        this.setAsciiStream(parameterIndex, x, -1);
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = x;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) {
        this.setAsciiStream(parameterIndex, x, (int)length);
        ((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.TEXT);
    }
    
    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(246, this.numberOfExecutions));
            binding.value = StringUtils.fixDecimalExponent(x.toPlainString());
            binding.parameterType = MysqlType.DECIMAL;
        }
    }
    
    @Override
    public void setBigInteger(final int parameterIndex, final BigInteger x) {
        this.setValue(parameterIndex, x.toString(), MysqlType.BIGINT_UNSIGNED);
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x) {
        this.setBinaryStream(parameterIndex, x, -1);
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = x;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
            binding.parameterType = MysqlType.BLOB;
        }
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) {
        this.setBinaryStream(parameterIndex, x, (int)length);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream) {
        this.setBinaryStream(parameterIndex, inputStream);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) {
        this.setBinaryStream(parameterIndex, inputStream, (int)length);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final Blob x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
                binding.value = x;
                binding.isStream = true;
                binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? x.length() : -1L);
                binding.parameterType = MysqlType.BLOB;
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
        }
    }
    
    @Override
    public void setBoolean(final int parameterIndex, final boolean x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(1, this.numberOfExecutions));
        binding.value = (x ? 1 : 0);
        binding.parameterType = MysqlType.BOOLEAN;
    }
    
    @Override
    public void setByte(final int parameterIndex, final byte x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(1, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.TINYINT;
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(253, this.numberOfExecutions));
            binding.value = x;
            binding.parameterType = MysqlType.BINARY;
        }
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x, final boolean checkForIntroducer, final boolean escapeForMBChars) {
        this.setBytes(parameterIndex, x);
    }
    
    @Override
    public void setBytesNoEscape(final int parameterIndex, final byte[] parameterAsBytes) {
        this.setBytes(parameterIndex, parameterAsBytes);
    }
    
    @Override
    public void setBytesNoEscapeNoQuotes(final int parameterIndex, final byte[] parameterAsBytes) {
        this.setBytes(parameterIndex, parameterAsBytes);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader) {
        this.setCharacterStream(parameterIndex, reader, -1);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) {
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = reader;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
            binding.parameterType = MysqlType.TEXT;
        }
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) {
        this.setCharacterStream(parameterIndex, reader, (int)length);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader) {
        this.setCharacterStream(parameterIndex, reader);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader, final long length) {
        this.setCharacterStream(parameterIndex, reader, length);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Clob x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
                binding.value = x.getCharacterStream();
                binding.isStream = true;
                binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? x.length() : -1L);
                binding.parameterType = MysqlType.TEXT;
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
        }
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x) {
        this.setDate(parameterIndex, x, null);
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(10, this.numberOfExecutions));
            binding.value = x;
            binding.calendar = ((cal == null) ? null : ((Calendar)cal.clone()));
            binding.parameterType = MysqlType.DATE;
        }
    }
    
    @Override
    public void setDouble(final int parameterIndex, final double x) {
        if (!this.session.getPropertySet().getBooleanProperty(PropertyKey.allowNanAndInf).getValue() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.64", new Object[] { x }), this.session.getExceptionInterceptor());
        }
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(5, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.DOUBLE;
    }
    
    @Override
    public void setFloat(final int parameterIndex, final float x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(4, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.FLOAT;
    }
    
    @Override
    public void setInt(final int parameterIndex, final int x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(3, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.INT;
    }
    
    @Override
    public void setLocalDate(final int parameterIndex, final LocalDate x, final MysqlType targetMysqlType) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(10, this.numberOfExecutions));
        binding.parameterType = targetMysqlType;
        binding.value = x;
    }
    
    @Override
    public void setLocalTime(final int parameterIndex, LocalTime x, final MysqlType targetMysqlType) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        if (targetMysqlType == MysqlType.DATE) {
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(10, this.numberOfExecutions));
            binding.parameterType = targetMysqlType;
            binding.value = ServerPreparedQueryBindings.DEFAULT_DATE;
            return;
        }
        if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
            if (x.getNano() > 0) {
                x = x.withNano(0);
            }
        }
        else {
            int fractLen = 6;
            if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0) {
                fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
            }
            x = TimeUtil.adjustNanosPrecision(x, fractLen, !this.session.getServerSession().isServerTruncatesFracSecs());
        }
        if (targetMysqlType == MysqlType.TIME) {
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(11, this.numberOfExecutions));
        }
        else {
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(12, this.numberOfExecutions));
        }
        binding.parameterType = targetMysqlType;
        binding.value = x;
    }
    
    @Override
    public void setLocalDateTime(final int parameterIndex, LocalDateTime x, final MysqlType targetMysqlType) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        if (targetMysqlType == MysqlType.DATE) {
            x = LocalDateTime.of(x.toLocalDate(), ServerPreparedQueryBindings.DEFAULT_TIME);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(10, this.numberOfExecutions));
        }
        else {
            int fractLen = 6;
            if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0) {
                fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
            }
            if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue()) {
                if (x.getNano() > 0) {
                    x = x.withNano(0);
                }
            }
            else {
                x = TimeUtil.adjustNanosPrecision(x, fractLen, !this.session.getServerSession().isServerTruncatesFracSecs());
            }
            if (targetMysqlType == MysqlType.TIME) {
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(11, this.numberOfExecutions));
            }
            else {
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(12, this.numberOfExecutions));
            }
        }
        binding.parameterType = targetMysqlType;
        binding.value = x;
    }
    
    @Override
    public void setLong(final int parameterIndex, final long x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(8, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.BIGINT;
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value) {
        this.setNCharacterStream(parameterIndex, value, -1L);
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.28"), this.session.getExceptionInterceptor());
        }
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = reader;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
            binding.parameterType = MysqlType.TEXT;
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader) {
        this.setNCharacterStream(parameterIndex, reader);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.29"), this.session.getExceptionInterceptor());
        }
        this.setNCharacterStream(parameterIndex, reader, length);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) {
        try {
            this.setNClob(parameterIndex, value.getCharacterStream(), ((boolean)this.useStreamLengthsInPrepStmts.getValue()) ? value.length() : -1L);
        }
        catch (Throwable t) {
            throw ExceptionFactory.createException(t.getMessage(), t, this.session.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNString(final int parameterIndex, final String x) {
        if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
            this.setString(parameterIndex, x);
            return;
        }
        throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.30"), this.session.getExceptionInterceptor());
    }
    
    @Override
    public void setNull(final int parameterIndex) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(6, this.numberOfExecutions));
        binding.setNull(true);
    }
    
    @Override
    public void setShort(final int parameterIndex, final short x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(2, this.numberOfExecutions));
        binding.value = x;
        binding.parameterType = MysqlType.SMALLINT;
    }
    
    @Override
    public void setString(final int parameterIndex, final String x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(253, this.numberOfExecutions));
            binding.value = x;
            binding.charEncoding = this.charEncoding;
            binding.parameterType = MysqlType.VARCHAR;
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, Time x, final Calendar cal) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || !this.sendFractionalSeconds.getValue() || !this.sendFractionalSecondsForTime.getValue()) {
                x = TimeUtil.truncateFractionalSeconds(x);
            }
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(11, this.numberOfExecutions));
            binding.value = x;
            binding.calendar = ((cal == null) ? null : ((Calendar)cal.clone()));
            binding.parameterType = MysqlType.TIME;
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x) {
        this.setTime(parameterIndex, x, null);
    }
    
    @Override
    public void bindTimestamp(final int parameterIndex, Timestamp x, final Calendar targetCalendar, int fractionalLength, final MysqlType targetMysqlType) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType((targetMysqlType == MysqlType.TIMESTAMP) ? 7 : 12, this.numberOfExecutions));
        if (fractionalLength < 0) {
            fractionalLength = 6;
        }
        x = TimeUtil.adjustNanosPrecision(x, fractionalLength, !this.session.getServerSession().isServerTruncatesFracSecs());
        binding.value = x;
        binding.calendar = ((targetCalendar == null) ? null : ((Calendar)targetCalendar.clone()));
        binding.parameterType = targetMysqlType;
    }
}
