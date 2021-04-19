package com.mysql.cj.jdbc.result;

import com.mysql.cj.log.*;
import java.time.*;
import com.mysql.cj.protocol.a.result.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.jdbc.exceptions.*;
import java.math.*;
import com.mysql.cj.result.*;
import com.mysql.cj.util.*;
import java.io.*;
import java.util.*;
import java.net.*;
import com.mysql.cj.protocol.*;
import java.sql.*;
import com.mysql.cj.jdbc.*;
import com.mysql.cj.*;

public class ResultSetImpl extends NativeResultset implements ResultSetInternalMethods, WarningListener
{
    static int resultCounter;
    protected String db;
    protected boolean[] columnUsed;
    protected volatile JdbcConnection connection;
    protected NativeSession session;
    protected int currentRow;
    protected ProfilerEventHandler eventSink;
    Calendar fastDefaultCal;
    Calendar fastClientCal;
    protected int fetchDirection;
    protected int fetchSize;
    protected char firstCharOfQuery;
    protected boolean isClosed;
    private StatementImpl owningStatement;
    private String pointOfOrigin;
    protected int resultSetConcurrency;
    protected int resultSetType;
    JdbcPreparedStatement statementUsedForFetchingRows;
    protected boolean useUsageAdvisor;
    protected boolean gatherPerfMetrics;
    protected SQLWarning warningChain;
    protected Statement wrapperStatement;
    private boolean padCharsWithSpace;
    private boolean useColumnNamesInFindColumn;
    private ExceptionInterceptor exceptionInterceptor;
    private ValueFactory<Boolean> booleanValueFactory;
    private ValueFactory<Byte> byteValueFactory;
    private ValueFactory<Short> shortValueFactory;
    private ValueFactory<Integer> integerValueFactory;
    private ValueFactory<Long> longValueFactory;
    private ValueFactory<Float> floatValueFactory;
    private ValueFactory<Double> doubleValueFactory;
    private ValueFactory<BigDecimal> bigDecimalValueFactory;
    private ValueFactory<InputStream> binaryStreamValueFactory;
    private ValueFactory<Time> defaultTimeValueFactory;
    private ValueFactory<Timestamp> defaultTimestampValueFactory;
    private ValueFactory<Calendar> defaultUtilCalendarValueFactory;
    private ValueFactory<LocalDate> defaultLocalDateValueFactory;
    private ValueFactory<LocalDateTime> defaultLocalDateTimeValueFactory;
    private ValueFactory<LocalTime> defaultLocalTimeValueFactory;
    private ValueFactory<OffsetTime> defaultOffsetTimeValueFactory;
    private ValueFactory<OffsetDateTime> defaultOffsetDateTimeValueFactory;
    private ValueFactory<ZonedDateTime> defaultZonedDateTimeValueFactory;
    protected RuntimeProperty<Boolean> emulateLocators;
    protected boolean yearIsDateType;
    private boolean onValidRow;
    private String invalidRowReason;
    
    public ResultSetImpl(final OkPacket ok, final JdbcConnection conn, final StatementImpl creatorStmt) {
        super(ok);
        this.db = null;
        this.columnUsed = null;
        this.session = null;
        this.currentRow = -1;
        this.eventSink = null;
        this.fastDefaultCal = null;
        this.fastClientCal = null;
        this.fetchDirection = 1000;
        this.fetchSize = 0;
        this.isClosed = false;
        this.resultSetConcurrency = 0;
        this.resultSetType = 0;
        this.useUsageAdvisor = false;
        this.gatherPerfMetrics = false;
        this.warningChain = null;
        this.padCharsWithSpace = false;
        this.yearIsDateType = true;
        this.onValidRow = false;
        this.invalidRowReason = null;
        this.connection = conn;
        this.owningStatement = creatorStmt;
        if (this.connection != null) {
            this.session = (NativeSession)conn.getSession();
            this.exceptionInterceptor = this.connection.getExceptionInterceptor();
            this.padCharsWithSpace = this.connection.getPropertySet().getBooleanProperty(PropertyKey.padCharsWithSpace).getValue();
        }
    }
    
    public ResultSetImpl(final ResultsetRows tuples, final JdbcConnection conn, final StatementImpl creatorStmt) throws SQLException {
        this.db = null;
        this.columnUsed = null;
        this.session = null;
        this.currentRow = -1;
        this.eventSink = null;
        this.fastDefaultCal = null;
        this.fastClientCal = null;
        this.fetchDirection = 1000;
        this.fetchSize = 0;
        this.isClosed = false;
        this.resultSetConcurrency = 0;
        this.resultSetType = 0;
        this.useUsageAdvisor = false;
        this.gatherPerfMetrics = false;
        this.warningChain = null;
        this.padCharsWithSpace = false;
        this.yearIsDateType = true;
        this.onValidRow = false;
        this.invalidRowReason = null;
        this.connection = conn;
        this.session = (NativeSession)conn.getSession();
        this.db = ((creatorStmt != null) ? creatorStmt.getCurrentDatabase() : conn.getDatabase());
        this.owningStatement = creatorStmt;
        this.exceptionInterceptor = this.connection.getExceptionInterceptor();
        final PropertySet pset = this.connection.getPropertySet();
        this.emulateLocators = pset.getBooleanProperty(PropertyKey.emulateLocators);
        this.padCharsWithSpace = pset.getBooleanProperty(PropertyKey.padCharsWithSpace).getValue();
        this.yearIsDateType = pset.getBooleanProperty(PropertyKey.yearIsDateType).getValue();
        this.useUsageAdvisor = pset.getBooleanProperty(PropertyKey.useUsageAdvisor).getValue();
        this.gatherPerfMetrics = pset.getBooleanProperty(PropertyKey.gatherPerfMetrics).getValue();
        this.booleanValueFactory = new BooleanValueFactory(pset);
        this.byteValueFactory = new ByteValueFactory(pset);
        this.shortValueFactory = new ShortValueFactory(pset);
        this.integerValueFactory = new IntegerValueFactory(pset);
        this.longValueFactory = new LongValueFactory(pset);
        this.floatValueFactory = new FloatValueFactory(pset);
        this.doubleValueFactory = new DoubleValueFactory(pset);
        this.bigDecimalValueFactory = new BigDecimalValueFactory(pset);
        this.binaryStreamValueFactory = new BinaryStreamValueFactory(pset);
        this.defaultTimeValueFactory = new SqlTimeValueFactory(pset, null, this.session.getServerSession().getDefaultTimeZone(), this);
        this.defaultTimestampValueFactory = new SqlTimestampValueFactory(pset, null, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getSessionTimeZone());
        this.defaultUtilCalendarValueFactory = new UtilCalendarValueFactory(pset, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getSessionTimeZone());
        this.defaultLocalDateValueFactory = new LocalDateValueFactory(pset, this);
        this.defaultLocalTimeValueFactory = new LocalTimeValueFactory(pset, this);
        this.defaultLocalDateTimeValueFactory = new LocalDateTimeValueFactory(pset);
        this.defaultOffsetTimeValueFactory = new OffsetTimeValueFactory(pset, this.session.getProtocol().getServerSession().getDefaultTimeZone());
        this.defaultOffsetDateTimeValueFactory = new OffsetDateTimeValueFactory(pset, this.session.getProtocol().getServerSession().getDefaultTimeZone(), this.session.getProtocol().getServerSession().getSessionTimeZone());
        this.defaultZonedDateTimeValueFactory = new ZonedDateTimeValueFactory(pset, this.session.getProtocol().getServerSession().getDefaultTimeZone(), this.session.getProtocol().getServerSession().getSessionTimeZone());
        this.columnDefinition = tuples.getMetadata();
        this.rowData = tuples;
        this.updateCount = this.rowData.size();
        if (this.rowData.size() > 0) {
            if (this.updateCount == 1L && this.thisRow == null) {
                this.rowData.close();
                this.updateCount = -1L;
            }
        }
        else {
            this.thisRow = null;
        }
        this.rowData.setOwner(this);
        if (this.columnDefinition.getFields() != null) {
            this.initializeWithMetadata();
        }
        this.useColumnNamesInFindColumn = pset.getBooleanProperty(PropertyKey.useColumnNamesInFindColumn).getValue();
        this.setRowPositionValidity();
    }
    
    @Override
    public void initializeWithMetadata() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.initRowsWithMetadata();
                if (this.useUsageAdvisor) {
                    this.columnUsed = new boolean[this.columnDefinition.getFields().length];
                    this.pointOfOrigin = LogUtils.findCallingClassAndMethod(new Throwable());
                    this.resultId = ResultSetImpl.resultCounter++;
                    this.eventSink = this.session.getProfilerEventHandler();
                }
                if (this.gatherPerfMetrics) {
                    this.session.getProtocol().getMetricsHolder().incrementNumberOfResultSetsCreated();
                    final Set<String> tableNamesSet = new HashSet<String>();
                    for (int i = 0; i < this.columnDefinition.getFields().length; ++i) {
                        final Field f = this.columnDefinition.getFields()[i];
                        String tableName = f.getOriginalTableName();
                        if (tableName == null) {
                            tableName = f.getTableName();
                        }
                        if (tableName != null) {
                            if (this.connection.lowerCaseTableNames()) {
                                tableName = tableName.toLowerCase();
                            }
                            tableNamesSet.add(tableName);
                        }
                    }
                    this.session.getProtocol().getMetricsHolder().reportNumberOfTablesAccessed(tableNamesSet.size());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean absolute(int row) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                boolean b;
                if (this.rowData.size() == 0) {
                    b = false;
                }
                else if (row == 0) {
                    this.beforeFirst();
                    b = false;
                }
                else if (row == 1) {
                    b = this.first();
                }
                else if (row == -1) {
                    b = this.last();
                }
                else if (row > this.rowData.size()) {
                    this.afterLast();
                    b = false;
                }
                else if (row < 0) {
                    final int newRowPosition = this.rowData.size() + row + 1;
                    if (newRowPosition <= 0) {
                        this.beforeFirst();
                        b = false;
                    }
                    else {
                        b = this.absolute(newRowPosition);
                    }
                }
                else {
                    --row;
                    this.rowData.setCurrentRow(row);
                    this.thisRow = this.rowData.get(row);
                    b = true;
                }
                this.setRowPositionValidity();
                return b;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void afterLast() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                if (this.rowData.size() != 0) {
                    this.rowData.afterLast();
                    this.thisRow = null;
                }
                this.setRowPositionValidity();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void beforeFirst() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                if (this.rowData.size() == 0) {
                    return;
                }
                this.rowData.beforeFirst();
                this.thisRow = null;
                this.setRowPositionValidity();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void cancelRowUpdates() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected final JdbcConnection checkClosed() throws SQLException {
        final JdbcConnection c = this.connection;
        if (c == null) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000", this.getExceptionInterceptor());
        }
        return c;
    }
    
    protected final void checkColumnBounds(final int columnIndex) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            if (columnIndex < 1) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_low", new Object[] { columnIndex, this.columnDefinition.getFields().length }), "S1009", this.getExceptionInterceptor());
            }
            if (columnIndex > this.columnDefinition.getFields().length) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_high", new Object[] { columnIndex, this.columnDefinition.getFields().length }), "S1009", this.getExceptionInterceptor());
            }
            if (this.useUsageAdvisor) {
                this.columnUsed[columnIndex - 1] = true;
            }
        }
    }
    
    protected void checkRowPos() throws SQLException {
        this.checkClosed();
        if (!this.onValidRow) {
            throw SQLError.createSQLException(this.invalidRowReason, "S1000", this.getExceptionInterceptor());
        }
    }
    
    private void setRowPositionValidity() throws SQLException {
        if (!this.rowData.isDynamic() && this.rowData.size() == 0) {
            this.invalidRowReason = Messages.getString("ResultSet.Illegal_operation_on_empty_result_set");
            this.onValidRow = false;
        }
        else if (this.rowData.isBeforeFirst()) {
            this.invalidRowReason = Messages.getString("ResultSet.Before_start_of_result_set_146");
            this.onValidRow = false;
        }
        else if (this.rowData.isAfterLast()) {
            this.invalidRowReason = Messages.getString("ResultSet.After_end_of_result_set_148");
            this.onValidRow = false;
        }
        else {
            this.onValidRow = true;
            this.invalidRowReason = null;
        }
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.warningChain = null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            this.realClose(true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void populateCachedMetaData(final CachedResultSetMetaData cachedMetaData) throws SQLException {
        try {
            this.columnDefinition.exportTo(cachedMetaData);
            cachedMetaData.setMetadata(this.getMetaData());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void deleteRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int findColumn(final String columnName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final Integer index = this.columnDefinition.findColumn(columnName, this.useColumnNamesInFindColumn, 1);
                if (index == -1) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Column____112") + columnName + Messages.getString("ResultSet.___not_found._113"), "S0022", this.getExceptionInterceptor());
                }
                return index;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean first() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                boolean b = true;
                if (this.rowData.isEmpty()) {
                    b = false;
                }
                else {
                    this.rowData.beforeFirst();
                    this.thisRow = this.rowData.next();
                }
                this.setRowPositionValidity();
                return b;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array getArray(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array getArray(final String colName) throws SQLException {
        try {
            return this.getArray(this.findColumn(colName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public InputStream getAsciiStream(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.getBinaryStream(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public InputStream getAsciiStream(final String columnName) throws SQLException {
        try {
            return this.getAsciiStream(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, this.bigDecimalValueFactory);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final ValueFactory<BigDecimal> vf = new BigDecimalValueFactory(this.session.getPropertySet(), scale);
            vf.setPropertySet(this.connection.getPropertySet());
            return this.thisRow.getValue(columnIndex - 1, vf);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final String columnName) throws SQLException {
        try {
            return this.getBigDecimal(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public BigDecimal getBigDecimal(final String columnName, final int scale) throws SQLException {
        try {
            return this.getBigDecimal(this.findColumn(columnName), scale);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public InputStream getBinaryStream(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, this.binaryStreamValueFactory);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public InputStream getBinaryStream(final String columnName) throws SQLException {
        try {
            return this.getBinaryStream(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob getBlob(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            if (this.thisRow.getNull(columnIndex - 1)) {
                return null;
            }
            if (!this.emulateLocators.getValue()) {
                return new com.mysql.cj.jdbc.Blob(this.thisRow.getBytes(columnIndex - 1), this.getExceptionInterceptor());
            }
            return new BlobFromLocator(this, columnIndex, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob getBlob(final String colName) throws SQLException {
        try {
            return this.getBlob(this.findColumn(colName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getBoolean(final int columnIndex) throws SQLException {
        try {
            final Boolean res = this.getObject(columnIndex, Boolean.TYPE);
            return res != null && res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getBoolean(final String columnName) throws SQLException {
        try {
            return this.getBoolean(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte getByte(final int columnIndex) throws SQLException {
        try {
            final Byte res = this.getObject(columnIndex, Byte.TYPE);
            return (byte)((res == null) ? 0 : ((byte)res));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte getByte(final String columnName) throws SQLException {
        try {
            return this.getByte(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte[] getBytes(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getBytes(columnIndex - 1);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte[] getBytes(final String columnName) throws SQLException {
        try {
            return this.getBytes(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getCharacterStream(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final InputStream stream = this.getBinaryStream(columnIndex);
            if (stream == null) {
                return null;
            }
            final Field f = this.columnDefinition.getFields()[columnIndex - 1];
            try {
                return new InputStreamReader(stream, f.getEncoding());
            }
            catch (UnsupportedEncodingException e) {
                final SQLException sqlEx = SQLError.createSQLException("Cannot read value with encoding: " + f.getEncoding(), this.exceptionInterceptor);
                sqlEx.initCause(e);
                throw sqlEx;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getCharacterStream(final String columnName) throws SQLException {
        try {
            return this.getCharacterStream(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob getClob(final int columnIndex) throws SQLException {
        try {
            final String asString = this.getStringForClob(columnIndex);
            if (asString == null) {
                return null;
            }
            return new com.mysql.cj.jdbc.Clob(asString, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob getClob(final String colName) throws SQLException {
        try {
            return this.getClob(this.findColumn(colName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, (ValueFactory<Date>)new SqlDateValueFactory(this.session.getPropertySet(), null, this.session.getServerSession().getDefaultTimeZone(), this));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, (ValueFactory<Date>)new SqlDateValueFactory(this.session.getPropertySet(), cal, (cal != null) ? cal.getTimeZone() : this.session.getServerSession().getDefaultTimeZone(), this));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final String columnName) throws SQLException {
        try {
            return this.getDate(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final String columnName, final Calendar cal) throws SQLException {
        try {
            return this.getDate(this.findColumn(columnName), cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public double getDouble(final int columnIndex) throws SQLException {
        try {
            final Double res = this.getObject(columnIndex, Double.TYPE);
            return (res == null) ? 0.0 : res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public double getDouble(final String columnName) throws SQLException {
        try {
            return this.getDouble(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public float getFloat(final int columnIndex) throws SQLException {
        try {
            final Float res = this.getObject(columnIndex, Float.TYPE);
            return (res == null) ? 0.0f : res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public float getFloat(final String columnName) throws SQLException {
        try {
            return this.getFloat(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getInt(final int columnIndex) throws SQLException {
        try {
            final Integer res = this.getObject(columnIndex, Integer.TYPE);
            return (res == null) ? 0 : res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public BigInteger getBigInteger(final int columnIndex) throws SQLException {
        try {
            final String stringVal = this.getString(columnIndex);
            if (stringVal == null) {
                return null;
            }
            try {
                return new BigInteger(stringVal);
            }
            catch (NumberFormatException nfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigInteger", new Object[] { columnIndex, stringVal }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getInt(final String columnName) throws SQLException {
        try {
            return this.getInt(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLong(final int columnIndex) throws SQLException {
        try {
            final Long res = this.getObject(columnIndex, Long.TYPE);
            return (res == null) ? 0L : res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLong(final String columnName) throws SQLException {
        try {
            return this.getLong(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public short getShort(final int columnIndex) throws SQLException {
        try {
            final Short res = this.getObject(columnIndex, Short.TYPE);
            return (short)((res == null) ? 0 : ((short)res));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public short getShort(final String columnName) throws SQLException {
        try {
            return this.getShort(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getString(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final Field f = this.columnDefinition.getFields()[columnIndex - 1];
            final ValueFactory<String> vf = new StringValueFactory(this.session.getPropertySet());
            final String stringVal = this.thisRow.getValue(columnIndex - 1, vf);
            if (this.padCharsWithSpace && stringVal != null && f.getMysqlTypeId() == 254) {
                final int maxBytesPerChar = this.session.getServerSession().getMaxBytesPerChar(f.getCollationIndex(), f.getEncoding());
                final int fieldLength = (int)f.getLength() / maxBytesPerChar;
                return StringUtils.padString(stringVal, fieldLength);
            }
            return stringVal;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getString(final String columnName) throws SQLException {
        try {
            return this.getString(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private String getStringForClob(final int columnIndex) throws SQLException {
        String asString = null;
        final String forcedEncoding = this.connection.getPropertySet().getStringProperty(PropertyKey.clobCharacterEncoding).getStringValue();
        if (forcedEncoding == null) {
            asString = this.getString(columnIndex);
        }
        else {
            final byte[] asBytes = this.getBytes(columnIndex);
            if (asBytes != null) {
                asString = StringUtils.toString(asBytes, forcedEncoding);
            }
        }
        return asString;
    }
    
    @Override
    public Time getTime(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, this.defaultTimeValueFactory);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final ValueFactory<Time> vf = new SqlTimeValueFactory(this.session.getPropertySet(), cal, (cal != null) ? cal.getTimeZone() : this.session.getServerSession().getDefaultTimeZone());
            return this.thisRow.getValue(columnIndex - 1, vf);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final String columnName) throws SQLException {
        try {
            return this.getTime(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final String columnName, final Calendar cal) throws SQLException {
        try {
            return this.getTime(this.findColumn(columnName), cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            return this.thisRow.getValue(columnIndex - 1, this.defaultTimestampValueFactory);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public LocalDate getLocalDate(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        return this.thisRow.getValue(columnIndex - 1, this.defaultLocalDateValueFactory);
    }
    
    public LocalDateTime getLocalDateTime(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        return this.thisRow.getValue(columnIndex - 1, this.defaultLocalDateTimeValueFactory);
    }
    
    public LocalTime getLocalTime(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        return this.thisRow.getValue(columnIndex - 1, this.defaultLocalTimeValueFactory);
    }
    
    public Calendar getUtilCalendar(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        return this.thisRow.getValue(columnIndex - 1, this.defaultUtilCalendarValueFactory);
    }
    
    @Override
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final ValueFactory<Timestamp> vf = new SqlTimestampValueFactory(this.session.getPropertySet(), cal, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getSessionTimeZone());
            return this.thisRow.getValue(columnIndex - 1, vf);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String columnName) throws SQLException {
        try {
            return this.getTimestamp(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String columnName, final Calendar cal) throws SQLException {
        try {
            return this.getTimestamp(this.findColumn(columnName), cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final String fieldEncoding = this.columnDefinition.getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException("Can not call getNCharacterStream() when field's charset isn't UTF-8");
            }
            return this.getCharacterStream(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final String columnName) throws SQLException {
        try {
            return this.getNCharacterStream(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final String fieldEncoding = this.columnDefinition.getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
            }
            final String asString = this.getStringForNClob(columnIndex);
            if (asString == null) {
                return null;
            }
            return new com.mysql.cj.jdbc.NClob(asString, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final String columnName) throws SQLException {
        try {
            return this.getNClob(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private String getStringForNClob(final int columnIndex) throws SQLException {
        String asString = null;
        final String forcedEncoding = "UTF-8";
        try {
            final byte[] asBytes = this.getBytes(columnIndex);
            if (asBytes != null) {
                asString = new String(asBytes, forcedEncoding);
            }
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
        }
        return asString;
    }
    
    @Override
    public String getNString(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final String fieldEncoding = this.columnDefinition.getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
            }
            return this.getString(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNString(final String columnName) throws SQLException {
        try {
            return this.getNString(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getConcurrency() throws SQLException {
        try {
            return 1007;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCursorName() throws SQLException {
        try {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Positioned_Update_not_supported"), "S1C00", this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getFetchDirection() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.fetchDirection;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getFetchSize() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.fetchSize;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public char getFirstCharOfQuery() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.firstCharOfQuery;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            this.checkClosed();
            return new com.mysql.cj.jdbc.result.ResultSetMetaData(this.session, this.columnDefinition.getFields(), this.session.getPropertySet().getBooleanProperty(PropertyKey.useOldAliasMetadataBehavior).getValue(), this.yearIsDateType, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final int columnIndexMinusOne = columnIndex - 1;
            if (this.thisRow.getNull(columnIndexMinusOne)) {
                return null;
            }
            final Field field = this.columnDefinition.getFields()[columnIndexMinusOne];
            switch (field.getMysqlType()) {
                case BIT: {
                    if (!field.isBinary() && !field.isBlob()) {
                        return field.isSingleBit() ? Boolean.valueOf(this.getBoolean(columnIndex)) : this.getBytes(columnIndex);
                    }
                    final byte[] data = this.getBytes(columnIndex);
                    if (this.connection.getPropertySet().getBooleanProperty(PropertyKey.autoDeserialize).getValue()) {
                        Object obj = data;
                        if (data != null && data.length >= 2) {
                            if (data[0] == -84 && data[1] == -19) {
                                try {
                                    final ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
                                    final ObjectInputStream objIn = new ObjectInputStream(bytesIn);
                                    obj = objIn.readObject();
                                    objIn.close();
                                    bytesIn.close();
                                    return obj;
                                }
                                catch (ClassNotFoundException cnfe) {
                                    throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), this.getExceptionInterceptor());
                                }
                                catch (IOException ex) {
                                    obj = data;
                                    return obj;
                                }
                            }
                            return this.getString(columnIndex);
                        }
                        return obj;
                    }
                    return data;
                }
                case BOOLEAN: {
                    return this.getBoolean(columnIndex);
                }
                case TINYINT: {
                    return this.getByte(columnIndex);
                }
                case TINYINT_UNSIGNED:
                case SMALLINT:
                case SMALLINT_UNSIGNED:
                case MEDIUMINT:
                case MEDIUMINT_UNSIGNED:
                case INT: {
                    return this.getInt(columnIndex);
                }
                case INT_UNSIGNED:
                case BIGINT: {
                    return this.getLong(columnIndex);
                }
                case BIGINT_UNSIGNED: {
                    return this.getBigInteger(columnIndex);
                }
                case DECIMAL:
                case DECIMAL_UNSIGNED: {
                    final String stringVal = this.getString(columnIndex);
                    if (stringVal != null) {
                        if (stringVal.length() == 0) {
                            return new BigDecimal(0);
                        }
                        try {
                            return new BigDecimal(stringVal);
                        }
                        catch (NumberFormatException ex2) {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, columnIndex }), "S1009", this.getExceptionInterceptor());
                        }
                    }
                    return null;
                }
                case FLOAT:
                case FLOAT_UNSIGNED: {
                    return new Float(this.getFloat(columnIndex));
                }
                case DOUBLE:
                case DOUBLE_UNSIGNED: {
                    return new Double(this.getDouble(columnIndex));
                }
                case CHAR:
                case ENUM:
                case SET:
                case VARCHAR:
                case TINYTEXT: {
                    return this.getString(columnIndex);
                }
                case TEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case JSON: {
                    return this.getStringForClob(columnIndex);
                }
                case GEOMETRY: {
                    return this.getBytes(columnIndex);
                }
                case BINARY:
                case VARBINARY:
                case TINYBLOB:
                case MEDIUMBLOB:
                case LONGBLOB:
                case BLOB: {
                    if (!field.isBinary() && !field.isBlob()) {
                        return this.getBytes(columnIndex);
                    }
                    final byte[] data2 = this.getBytes(columnIndex);
                    if (this.connection.getPropertySet().getBooleanProperty(PropertyKey.autoDeserialize).getValue()) {
                        Object obj2 = data2;
                        if (data2 != null && data2.length >= 2) {
                            if (data2[0] == -84 && data2[1] == -19) {
                                try {
                                    final ByteArrayInputStream bytesIn2 = new ByteArrayInputStream(data2);
                                    final ObjectInputStream objIn2 = new ObjectInputStream(bytesIn2);
                                    obj2 = objIn2.readObject();
                                    objIn2.close();
                                    bytesIn2.close();
                                    return obj2;
                                }
                                catch (ClassNotFoundException cnfe2) {
                                    throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe2.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), this.getExceptionInterceptor());
                                }
                                catch (IOException ex3) {
                                    obj2 = data2;
                                    return obj2;
                                }
                            }
                            return this.getString(columnIndex);
                        }
                        return obj2;
                    }
                    return data2;
                }
                case YEAR: {
                    return this.yearIsDateType ? this.getDate(columnIndex) : Short.valueOf(this.getShort(columnIndex));
                }
                case DATE: {
                    return this.getDate(columnIndex);
                }
                case TIME: {
                    return this.getTime(columnIndex);
                }
                case TIMESTAMP: {
                    return this.getTimestamp(columnIndex);
                }
                case DATETIME: {
                    return this.getLocalDateTime(columnIndex);
                }
                default: {
                    return this.getString(columnIndex);
                }
            }
        }
        catch (CJException ex4) {
            throw SQLExceptionsMapping.translateException(ex4, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
        try {
            if (type == null) {
                throw SQLError.createSQLException("Type parameter can not be null", "S1009", this.getExceptionInterceptor());
            }
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (type.equals(String.class)) {
                    return (T)this.getString(columnIndex);
                }
                if (type.equals(BigDecimal.class)) {
                    return (T)this.getBigDecimal(columnIndex);
                }
                if (type.equals(BigInteger.class)) {
                    return (T)this.getBigInteger(columnIndex);
                }
                if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.booleanValueFactory);
                }
                if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.byteValueFactory);
                }
                if (type.equals(Short.class) || type.equals(Short.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.shortValueFactory);
                }
                if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.integerValueFactory);
                }
                if (type.equals(Long.class) || type.equals(Long.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.longValueFactory);
                }
                if (type.equals(Float.class) || type.equals(Float.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.floatValueFactory);
                }
                if (type.equals(Double.class) || type.equals(Double.TYPE)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.doubleValueFactory);
                }
                if (type.equals(byte[].class)) {
                    return (T)(Object)this.getBytes(columnIndex);
                }
                if (type.equals(Date.class)) {
                    return (T)this.getDate(columnIndex);
                }
                if (type.equals(Time.class)) {
                    return (T)this.getTime(columnIndex);
                }
                if (type.equals(Timestamp.class)) {
                    return (T)this.getTimestamp(columnIndex);
                }
                if (type.equals(java.util.Date.class)) {
                    return (T)java.util.Date.from(this.getTimestamp(columnIndex).toInstant());
                }
                if (type.equals(Calendar.class)) {
                    return (T)this.getUtilCalendar(columnIndex);
                }
                if (type.equals(com.mysql.cj.jdbc.Clob.class)) {
                    return (T)this.getClob(columnIndex);
                }
                if (type.equals(com.mysql.cj.jdbc.Blob.class)) {
                    return (T)this.getBlob(columnIndex);
                }
                if (type.equals(Array.class)) {
                    return (T)this.getArray(columnIndex);
                }
                if (type.equals(Ref.class)) {
                    return (T)this.getRef(columnIndex);
                }
                if (type.equals(URL.class)) {
                    return (T)this.getURL(columnIndex);
                }
                if (type.equals(Struct.class)) {
                    throw new SQLFeatureNotSupportedException();
                }
                if (type.equals(RowId.class)) {
                    return (T)this.getRowId(columnIndex);
                }
                if (type.equals(NClob.class)) {
                    return (T)this.getNClob(columnIndex);
                }
                if (type.equals(SQLXML.class)) {
                    return (T)this.getSQLXML(columnIndex);
                }
                if (type.equals(LocalDate.class)) {
                    return (T)this.getLocalDate(columnIndex);
                }
                if (type.equals(LocalDateTime.class)) {
                    return (T)this.getLocalDateTime(columnIndex);
                }
                if (type.equals(LocalTime.class)) {
                    return (T)this.getLocalTime(columnIndex);
                }
                if (type.equals(OffsetDateTime.class)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.defaultOffsetDateTimeValueFactory);
                }
                if (type.equals(OffsetTime.class)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.defaultOffsetTimeValueFactory);
                }
                if (type.equals(ZonedDateTime.class)) {
                    this.checkRowPos();
                    this.checkColumnBounds(columnIndex);
                    return this.thisRow.getValue(columnIndex - 1, (ValueFactory<T>)this.defaultZonedDateTimeValueFactory);
                }
                if (this.connection.getPropertySet().getBooleanProperty(PropertyKey.autoDeserialize).getValue()) {
                    try {
                        return (T)this.getObject(columnIndex);
                    }
                    catch (ClassCastException cce) {
                        final SQLException sqlEx = SQLError.createSQLException("Conversion not supported for type " + type.getName(), "S1009", this.getExceptionInterceptor());
                        sqlEx.initCause(cce);
                        throw sqlEx;
                    }
                }
                throw SQLError.createSQLException("Conversion not supported for type " + type.getName(), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
        try {
            return this.getObject(this.findColumn(columnLabel), type);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final int i, final Map<String, Class<?>> map) throws SQLException {
        try {
            return this.getObject(i);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final String columnName) throws SQLException {
        try {
            return this.getObject(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final String colName, final Map<String, Class<?>> map) throws SQLException {
        try {
            return this.getObject(this.findColumn(colName), map);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObjectStoredProc(final int columnIndex, final int desiredSqlType) throws SQLException {
        try {
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            final Object value = this.thisRow.getBytes(columnIndex - 1);
            if (value == null) {
                return null;
            }
            final Field field = this.columnDefinition.getFields()[columnIndex - 1];
            final MysqlType desiredMysqlType = MysqlType.getByJdbcType(desiredSqlType);
            switch (desiredMysqlType) {
                case BIT:
                case BOOLEAN: {
                    return this.getBoolean(columnIndex);
                }
                case TINYINT:
                case TINYINT_UNSIGNED: {
                    return this.getInt(columnIndex);
                }
                case SMALLINT:
                case SMALLINT_UNSIGNED: {
                    return this.getInt(columnIndex);
                }
                case MEDIUMINT:
                case MEDIUMINT_UNSIGNED:
                case INT:
                case INT_UNSIGNED: {
                    if (!field.isUnsigned() || field.getMysqlTypeId() == 9) {
                        return this.getInt(columnIndex);
                    }
                    return this.getLong(columnIndex);
                }
                case BIGINT: {
                    return this.getLong(columnIndex);
                }
                case BIGINT_UNSIGNED: {
                    return this.getBigInteger(columnIndex);
                }
                case DECIMAL:
                case DECIMAL_UNSIGNED: {
                    final String stringVal = this.getString(columnIndex);
                    if (stringVal == null) {
                        return null;
                    }
                    if (stringVal.length() == 0) {
                        final BigDecimal val = new BigDecimal(0);
                        return val;
                    }
                    BigDecimal val;
                    try {
                        val = new BigDecimal(stringVal);
                    }
                    catch (NumberFormatException ex) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, columnIndex }), "S1009", this.getExceptionInterceptor());
                    }
                    return val;
                }
                case FLOAT:
                case FLOAT_UNSIGNED: {
                    return new Float(this.getFloat(columnIndex));
                }
                case DOUBLE:
                case DOUBLE_UNSIGNED: {
                    return new Double(this.getDouble(columnIndex));
                }
                case CHAR:
                case ENUM:
                case SET:
                case VARCHAR:
                case TINYTEXT: {
                    return this.getString(columnIndex);
                }
                case TEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case JSON: {
                    return this.getStringForClob(columnIndex);
                }
                case GEOMETRY:
                case BINARY:
                case VARBINARY:
                case TINYBLOB:
                case MEDIUMBLOB:
                case LONGBLOB:
                case BLOB: {
                    return this.getBytes(columnIndex);
                }
                case YEAR:
                case DATE: {
                    if (field.getMysqlType() == MysqlType.YEAR && !this.yearIsDateType) {
                        return this.getShort(columnIndex);
                    }
                    return this.getDate(columnIndex);
                }
                case TIME: {
                    return this.getTime(columnIndex);
                }
                case TIMESTAMP: {
                    return this.getTimestamp(columnIndex);
                }
                default: {
                    return this.getString(columnIndex);
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObjectStoredProc(final int i, final Map<Object, Object> map, final int desiredSqlType) throws SQLException {
        try {
            return this.getObjectStoredProc(i, desiredSqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObjectStoredProc(final String columnName, final int desiredSqlType) throws SQLException {
        try {
            return this.getObjectStoredProc(this.findColumn(columnName), desiredSqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObjectStoredProc(final String colName, final Map<Object, Object> map, final int desiredSqlType) throws SQLException {
        try {
            return this.getObjectStoredProc(this.findColumn(colName), map, desiredSqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Ref getRef(final int i) throws SQLException {
        try {
            this.checkColumnBounds(i);
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Ref getRef(final String colName) throws SQLException {
        try {
            return this.getRef(this.findColumn(colName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getRow() throws SQLException {
        try {
            this.checkClosed();
            final int currentRowNumber = this.rowData.getPosition();
            int row = 0;
            if (!this.rowData.isDynamic()) {
                if (currentRowNumber < 0 || this.rowData.isAfterLast() || this.rowData.isEmpty()) {
                    row = 0;
                }
                else {
                    row = currentRowNumber + 1;
                }
            }
            else {
                row = currentRowNumber + 1;
            }
            return row;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement getStatement() throws SQLException {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    if (this.wrapperStatement != null) {
                        return this.wrapperStatement;
                    }
                    return this.owningStatement;
                }
            }
            catch (SQLException sqlEx) {
                throw SQLError.createSQLException("Operation not allowed on closed ResultSet.", "S1000", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getType() throws SQLException {
        try {
            return this.resultSetType;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
        try {
            this.checkRowPos();
            return this.getBinaryStream(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public InputStream getUnicodeStream(final String columnName) throws SQLException {
        try {
            return this.getUnicodeStream(this.findColumn(columnName));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public URL getURL(final int colIndex) throws SQLException {
        try {
            final String val = this.getString(colIndex);
            if (val == null) {
                return null;
            }
            try {
                return new URL(val);
            }
            catch (MalformedURLException mfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____104") + val + "'", "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public URL getURL(final String colName) throws SQLException {
        try {
            final String val = this.getString(colName);
            if (val == null) {
                return null;
            }
            try {
                return new URL(val);
            }
            catch (MalformedURLException mfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____107") + val + "'", "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.warningChain;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void insertRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isAfterLast() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final boolean b = this.rowData.isAfterLast();
                return b;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isBeforeFirst() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.rowData.isBeforeFirst();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isFirst() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.rowData.isFirst();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isLast() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.rowData.isLast();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean last() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                boolean b = true;
                if (this.rowData.size() == 0) {
                    b = false;
                }
                else {
                    this.rowData.beforeLast();
                    this.thisRow = this.rowData.next();
                }
                this.setRowPositionValidity();
                return b;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void moveToCurrentRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void moveToInsertRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean next() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.hasRows()) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.ResultSet_is_from_UPDATE._No_Data_115"), "S1000", this.getExceptionInterceptor());
                }
                boolean b;
                if (this.rowData.size() == 0) {
                    b = false;
                }
                else {
                    this.thisRow = this.rowData.next();
                    if (this.thisRow == null) {
                        b = false;
                    }
                    else {
                        this.clearWarnings();
                        b = true;
                    }
                }
                this.setRowPositionValidity();
                return b;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public boolean prev() throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            int rowIndex = this.rowData.getPosition();
            boolean b = true;
            if (rowIndex - 1 >= 0) {
                --rowIndex;
                this.rowData.setCurrentRow(rowIndex);
                this.thisRow = this.rowData.get(rowIndex);
                b = true;
            }
            else if (rowIndex - 1 == -1) {
                --rowIndex;
                this.rowData.setCurrentRow(rowIndex);
                this.thisRow = null;
                b = false;
            }
            else {
                b = false;
            }
            this.setRowPositionValidity();
            return b;
        }
    }
    
    @Override
    public boolean previous() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                return this.prev();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly) throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn == null) {
                return;
            }
            synchronized (locallyScopedConn.getConnectionMutex()) {
                if (this.isClosed) {
                    return;
                }
                try {
                    if (this.useUsageAdvisor) {
                        if (!calledExplicitly) {
                            this.eventSink.processEvent((byte)0, this.session, this.owningStatement, this, 0L, new Throwable(), Messages.getString("ResultSet.ResultSet_implicitly_closed_by_driver"));
                        }
                        final int resultSetSizeThreshold = locallyScopedConn.getPropertySet().getIntegerProperty(PropertyKey.resultSetSizeThreshold).getValue();
                        if (this.rowData.size() > resultSetSizeThreshold) {
                            this.eventSink.processEvent((byte)0, this.session, this.owningStatement, this, 0L, new Throwable(), Messages.getString("ResultSet.Too_Large_Result_Set", new Object[] { this.rowData.size(), resultSetSizeThreshold }));
                        }
                        if (!this.isLast() && !this.isAfterLast() && this.rowData.size() != 0) {
                            this.eventSink.processEvent((byte)0, this.session, this.owningStatement, this, 0L, new Throwable(), Messages.getString("ResultSet.Possible_incomplete_traversal_of_result_set", new Object[] { this.getRow(), this.rowData.size() }));
                        }
                        if (this.columnUsed.length > 0 && !this.rowData.wasEmpty()) {
                            final StringBuilder buf = new StringBuilder();
                            for (int i = 0; i < this.columnUsed.length; ++i) {
                                if (!this.columnUsed[i]) {
                                    if (buf.length() > 0) {
                                        buf.append(", ");
                                    }
                                    buf.append(this.columnDefinition.getFields()[i].getFullName());
                                }
                            }
                            if (buf.length() > 0) {
                                this.eventSink.processEvent((byte)0, this.session, this.owningStatement, this, 0L, new Throwable(), Messages.getString("ResultSet.The_following_columns_were_never_referenced", new String[] { buf.toString() }));
                            }
                        }
                    }
                }
                finally {
                    if (this.owningStatement != null && calledExplicitly) {
                        this.owningStatement.removeOpenResultSet(this);
                    }
                    SQLException exceptionDuringClose = null;
                    if (this.rowData != null) {
                        try {
                            this.rowData.close();
                        }
                        catch (CJException sqlEx) {
                            exceptionDuringClose = SQLExceptionsMapping.translateException(sqlEx);
                        }
                    }
                    if (this.statementUsedForFetchingRows != null) {
                        try {
                            this.statementUsedForFetchingRows.realClose(true, false);
                        }
                        catch (SQLException sqlEx2) {
                            if (exceptionDuringClose != null) {
                                exceptionDuringClose.setNextException(sqlEx2);
                            }
                            else {
                                exceptionDuringClose = sqlEx2;
                            }
                        }
                    }
                    this.rowData = null;
                    this.columnDefinition = null;
                    this.eventSink = null;
                    this.warningChain = null;
                    this.owningStatement = null;
                    this.db = null;
                    this.serverInfo = null;
                    this.thisRow = null;
                    this.fastDefaultCal = null;
                    this.fastClientCal = null;
                    this.connection = null;
                    this.session = null;
                    this.isClosed = true;
                    if (exceptionDuringClose != null) {
                        throw exceptionDuringClose;
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.isClosed;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void refreshRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean relative(final int rows) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.getType() == 1003) {
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
                }
                if (this.rowData.size() == 0) {
                    this.setRowPositionValidity();
                    return false;
                }
                this.rowData.moveRowRelative(rows);
                this.thisRow = this.rowData.get(this.rowData.getPosition());
                this.setRowPositionValidity();
                return !this.rowData.isAfterLast() && !this.rowData.isBeforeFirst();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean rowDeleted() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean rowInserted() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean rowUpdated() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (direction != 1000 && direction != 1001 && direction != 1002) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_value_for_fetch_direction_64"), "S1009", this.getExceptionInterceptor());
                }
                if (this.getType() == 1003 && direction != 1000) {
                    final String constName = (direction == 1001) ? "ResultSet.FETCH_REVERSE" : "ResultSet.FETCH_UNKNOWN";
                    throw ExceptionFactory.createException(Messages.getString("ResultSet.Unacceptable_value_for_fetch_direction", new Object[] { constName }));
                }
                this.fetchDirection = direction;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFetchSize(final int rows) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (rows < 0) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Value_must_be_between_0_and_getMaxRows()_66"), "S1009", this.getExceptionInterceptor());
                }
                this.fetchSize = rows;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFirstCharOfQuery(final char c) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.firstCharOfQuery = c;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void setOwningStatement(final JdbcStatement owningStatement) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.owningStatement = (StatementImpl)owningStatement;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public synchronized void setResultSetConcurrency(final int concurrencyFlag) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.resultSetConcurrency = concurrencyFlag;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public synchronized void setResultSetType(final int typeFlag) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.resultSetType = typeFlag;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setServerInfo(final String info) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.serverInfo = info;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public synchronized void setStatementUsedForFetchingRows(final JdbcPreparedStatement stmt) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.statementUsedForFetchingRows = stmt;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public synchronized void setWrapperStatement(final Statement wrapperStatement) {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.wrapperStatement = wrapperStatement;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String toString() {
        return this.hasRows() ? super.toString() : ("Result set representing update count of " + this.updateCount);
    }
    
    @Override
    public void updateArray(final int columnIndex, final Array arg1) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateArray(final String columnLabel, final Array arg1) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnName, final InputStream x, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBigDecimal(final String columnName, final BigDecimal x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnName, final InputStream x, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final Blob arg1) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final Blob arg1) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBoolean(final String columnName, final boolean x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateByte(final int columnIndex, final byte x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateByte(final String columnName, final byte x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBytes(final String columnName, final byte[] x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnName, final Reader reader, final int length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Clob arg1) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnName, final Clob clob) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDate(final int columnIndex, final Date x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDate(final String columnName, final Date x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDouble(final int columnIndex, final double x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDouble(final String columnName, final double x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateFloat(final int columnIndex, final float x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateFloat(final String columnName, final float x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateInt(final int columnIndex, final int x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateInt(final String columnName, final int x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateLong(final int columnIndex, final long x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateLong(final String columnName, final long x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnName, final NClob nClob) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNull(final int columnIndex) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNull(final String columnName) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNString(final int columnIndex, final String nString) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNString(final String columnLabel, final String nString) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnName, final Object x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final int scale) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnName, final Object x, final int scale) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final SQLType targetSqlType) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x, final SQLType targetSqlType) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateRef(final int columnIndex, final Ref arg1) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateRef(final String columnLabel, final Ref arg1) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateRow() throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateRowId(final String columnName, final RowId x) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateShort(final int columnIndex, final short x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateShort(final String columnName, final short x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateString(final int columnIndex, final String x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateString(final String columnName, final String x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTime(final int columnIndex, final Time x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTime(final String columnName, final Time x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTimestamp(final String columnName, final Timestamp x) throws SQLException {
        try {
            throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean wasNull() throws SQLException {
        try {
            return this.thisRow.wasNull();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public int getHoldability() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public RowId getRowId(final int columnIndex) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public RowId getRowId(final String columnLabel) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final int columnIndex) throws SQLException {
        try {
            this.checkColumnBounds(columnIndex);
            return new MysqlSQLXML(this, columnIndex, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final String columnLabel) throws SQLException {
        try {
            return this.getSQLXML(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            this.checkClosed();
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void warningEncountered(final String warning) {
        final SQLWarning w = new SQLWarning(warning);
        if (this.warningChain == null) {
            this.warningChain = w;
        }
        else {
            this.warningChain.setNextWarning(w);
        }
    }
    
    public ColumnDefinition getMetadata() {
        return this.columnDefinition;
    }
    
    public StatementImpl getOwningStatement() {
        return this.owningStatement;
    }
    
    @Override
    public void closeOwner(final boolean calledExplicitly) {
        try {
            this.realClose(calledExplicitly);
        }
        catch (SQLException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    @Override
    public JdbcConnection getConnection() {
        return this.connection;
    }
    
    @Override
    public Session getSession() {
        return (this.connection != null) ? this.connection.getSession() : null;
    }
    
    @Override
    public String getPointOfOrigin() {
        return this.pointOfOrigin;
    }
    
    @Override
    public int getOwnerFetchSize() {
        try {
            return this.getFetchSize();
        }
        catch (SQLException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    @Override
    public Query getOwningQuery() {
        return this.owningStatement;
    }
    
    @Override
    public int getOwningStatementMaxRows() {
        return (this.owningStatement == null) ? -1 : this.owningStatement.maxRows;
    }
    
    @Override
    public int getOwningStatementFetchSize() {
        try {
            return (this.owningStatement == null) ? 0 : this.owningStatement.getFetchSize();
        }
        catch (SQLException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    @Override
    public long getOwningStatementServerId() {
        return (this.owningStatement == null) ? 0L : this.owningStatement.getServerStatementId();
    }
    
    @Override
    public Object getSyncMutex() {
        return (this.connection != null) ? this.connection.getConnectionMutex() : null;
    }
    
    static {
        ResultSetImpl.resultCounter = 1;
    }
}
