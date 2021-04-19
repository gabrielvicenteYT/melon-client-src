package com.mysql.cj.jdbc.result;

import com.mysql.cj.result.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import java.sql.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.*;

public class ResultSetMetaData implements java.sql.ResultSetMetaData
{
    private Session session;
    private Field[] fields;
    boolean useOldAliasBehavior;
    boolean treatYearAsDate;
    private ExceptionInterceptor exceptionInterceptor;
    
    private static int clampedGetLength(final Field f) {
        long fieldLength = f.getLength();
        if (fieldLength > 2147483647L) {
            fieldLength = 2147483647L;
        }
        return (int)fieldLength;
    }
    
    public ResultSetMetaData(final Session session, final Field[] fields, final boolean useOldAliasBehavior, final boolean treatYearAsDate, final ExceptionInterceptor exceptionInterceptor) {
        this.useOldAliasBehavior = false;
        this.treatYearAsDate = true;
        this.session = session;
        this.fields = fields;
        this.useOldAliasBehavior = useOldAliasBehavior;
        this.treatYearAsDate = treatYearAsDate;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public String getCatalogName(final int column) throws SQLException {
        try {
            if (this.session.getPropertySet().getEnumProperty(PropertyKey.databaseTerm).getValue() == PropertyDefinitions.DatabaseTerm.SCHEMA) {
                return "";
            }
            final String database = this.getField(column).getDatabaseName();
            return (database == null) ? "" : database;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    public String getColumnCharacterEncoding(final int column) throws SQLException {
        return this.getField(column).getEncoding();
    }
    
    public String getColumnCharacterSet(final int column) throws SQLException {
        final int index = this.getField(column).getCollationIndex();
        String charsetName = null;
        if (((NativeSession)this.session).getProtocol().getServerSession().indexToCustomMysqlCharset != null) {
            charsetName = ((NativeSession)this.session).getProtocol().getServerSession().indexToCustomMysqlCharset.get(index);
        }
        if (charsetName == null) {
            charsetName = CharsetMapping.getMysqlCharsetNameForCollationIndex(index);
        }
        return charsetName;
    }
    
    @Override
    public String getColumnClassName(final int column) throws SQLException {
        try {
            final Field f = this.getField(column);
            switch (f.getMysqlType()) {
                case YEAR: {
                    if (!this.treatYearAsDate) {
                        return Short.class.getName();
                    }
                    return f.getMysqlType().getClassName();
                }
                default: {
                    return f.getMysqlType().getClassName();
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getColumnCount() throws SQLException {
        try {
            return this.fields.length;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getColumnDisplaySize(final int column) throws SQLException {
        try {
            final Field f = this.getField(column);
            final int lengthInBytes = clampedGetLength(f);
            return lengthInBytes / this.session.getServerSession().getMaxBytesPerChar(f.getCollationIndex(), f.getEncoding());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getColumnLabel(final int column) throws SQLException {
        try {
            if (this.useOldAliasBehavior) {
                return this.getColumnName(column);
            }
            return this.getField(column).getColumnLabel();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getColumnName(final int column) throws SQLException {
        try {
            if (this.useOldAliasBehavior) {
                return this.getField(column).getName();
            }
            final String name = this.getField(column).getOriginalName();
            if (name == null) {
                return this.getField(column).getName();
            }
            return name;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getColumnType(final int column) throws SQLException {
        try {
            return this.getField(column).getJavaType();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getColumnTypeName(final int column) throws SQLException {
        try {
            final Field field = this.getField(column);
            return field.getMysqlType().getName();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    protected Field getField(final int columnIndex) throws SQLException {
        if (columnIndex < 1 || columnIndex > this.fields.length) {
            throw SQLError.createSQLException(Messages.getString("ResultSetMetaData.46"), "S1002", this.exceptionInterceptor);
        }
        return this.fields[columnIndex - 1];
    }
    
    @Override
    public int getPrecision(final int column) throws SQLException {
        try {
            final Field f = this.getField(column);
            switch (f.getMysqlType()) {
                case TINYBLOB:
                case BLOB:
                case MEDIUMBLOB:
                case LONGBLOB: {
                    return clampedGetLength(f);
                }
                default: {
                    return f.getMysqlType().isDecimal() ? clampedGetLength(f) : (clampedGetLength(f) / this.session.getServerSession().getMaxBytesPerChar(f.getCollationIndex(), f.getEncoding()));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getScale(final int column) throws SQLException {
        try {
            final Field f = this.getField(column);
            if (f.getMysqlType().isDecimal()) {
                return f.getDecimals();
            }
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getSchemaName(final int column) throws SQLException {
        try {
            if (this.session.getPropertySet().getEnumProperty(PropertyKey.databaseTerm).getValue() == PropertyDefinitions.DatabaseTerm.CATALOG) {
                return "";
            }
            final String database = this.getField(column).getDatabaseName();
            return (database == null) ? "" : database;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getTableName(final int column) throws SQLException {
        try {
            final String res = this.useOldAliasBehavior ? this.getField(column).getTableName() : this.getField(column).getOriginalTableName();
            return (res == null) ? "" : res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isAutoIncrement(final int column) throws SQLException {
        try {
            final Field f = this.getField(column);
            return f.isAutoIncrement();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isCaseSensitive(final int column) throws SQLException {
        try {
            final Field field = this.getField(column);
            switch (field.getMysqlType()) {
                case YEAR:
                case BIT:
                case TINYINT:
                case TINYINT_UNSIGNED:
                case SMALLINT:
                case SMALLINT_UNSIGNED:
                case INT:
                case INT_UNSIGNED:
                case MEDIUMINT:
                case MEDIUMINT_UNSIGNED:
                case BIGINT:
                case BIGINT_UNSIGNED:
                case FLOAT:
                case FLOAT_UNSIGNED:
                case DOUBLE:
                case DOUBLE_UNSIGNED:
                case DATE:
                case TIME:
                case TIMESTAMP:
                case DATETIME: {
                    return false;
                }
                case CHAR:
                case VARCHAR:
                case TINYTEXT:
                case TEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case JSON:
                case ENUM:
                case SET: {
                    final String collationName = CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[field.getCollationIndex()];
                    return collationName != null && !collationName.endsWith("_ci");
                }
                default: {
                    return true;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isCurrency(final int column) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isDefinitelyWritable(final int column) throws SQLException {
        try {
            return this.isWritable(column);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int isNullable(final int column) throws SQLException {
        try {
            if (!this.getField(column).isNotNull()) {
                return 1;
            }
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isReadOnly(final int column) throws SQLException {
        try {
            return this.getField(column).isReadOnly();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isSearchable(final int column) throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isSigned(final int column) throws SQLException {
        try {
            return MysqlType.isSigned(this.getField(column).getMysqlType());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isWritable(final int column) throws SQLException {
        try {
            return !this.isReadOnly(column);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder toStringBuf = new StringBuilder();
        toStringBuf.append(super.toString());
        toStringBuf.append(" - Field level information: ");
        for (int i = 0; i < this.fields.length; ++i) {
            toStringBuf.append("\n\t");
            toStringBuf.append(this.fields[i].toString());
        }
        return toStringBuf.toString();
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.exceptionInterceptor);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    public Field[] getFields() {
        return this.fields;
    }
}
