package com.mysql.cj.jdbc;

import com.mysql.cj.exceptions.*;
import com.mysql.cj.jdbc.result.*;
import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.result.*;
import com.mysql.cj.protocol.a.result.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.util.*;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.net.*;

public class ParameterBindingsImpl implements ParameterBindings
{
    private QueryBindings<?> queryBindings;
    private List<Object> batchedArgs;
    private PropertySet propertySet;
    private ExceptionInterceptor exceptionInterceptor;
    private ResultSetImpl bindingsAsRs;
    private BindValue[] bindValues;
    
    ParameterBindingsImpl(final PreparedQuery<?> query, final Session session, final ResultSetFactory resultSetFactory) throws SQLException {
        this.queryBindings = (QueryBindings<?>)query.getQueryBindings();
        this.batchedArgs = query.getBatchedArgs();
        this.propertySet = session.getPropertySet();
        this.exceptionInterceptor = session.getExceptionInterceptor();
        final List<Row> rows = new ArrayList<Row>();
        final int paramCount = query.getParameterCount();
        this.bindValues = new BindValue[paramCount];
        for (int i = 0; i < paramCount; ++i) {
            this.bindValues[i] = ((BindValue)this.queryBindings.getBindValues()[i]).clone();
        }
        final byte[][] rowData = new byte[paramCount][];
        final Field[] typeMetadata = new Field[paramCount];
        for (int j = 0; j < paramCount; ++j) {
            final int batchCommandIndex = query.getBatchCommandIndex();
            rowData[j] = ((batchCommandIndex == -1) ? this.getBytesRepresentation(j) : this.getBytesRepresentationForBatch(j, batchCommandIndex));
            int charsetIndex = 0;
            switch (((BindValue)this.queryBindings.getBindValues()[j]).getMysqlType()) {
                case BINARY:
                case BLOB:
                case GEOMETRY:
                case LONGBLOB:
                case MEDIUMBLOB:
                case TINYBLOB:
                case UNKNOWN:
                case VARBINARY: {
                    charsetIndex = 63;
                    break;
                }
                default: {
                    try {
                        charsetIndex = CharsetMapping.getCollationIndexForJavaEncoding(this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue(), session.getServerSession().getServerVersion());
                    }
                    catch (RuntimeException ex) {
                        throw SQLError.createSQLException(ex.toString(), "S1009", ex, null);
                    }
                    break;
                }
            }
            final Field parameterMetadata = new Field(null, "parameter_" + (j + 1), charsetIndex, this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue(), ((BindValue)this.queryBindings.getBindValues()[j]).getMysqlType(), rowData[j].length);
            typeMetadata[j] = parameterMetadata;
        }
        rows.add(new ByteArrayRow(rowData, this.exceptionInterceptor));
        (this.bindingsAsRs = resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(typeMetadata)))).next();
    }
    
    private byte[] getBytesRepresentation(final int parameterIndex) {
        return this.queryBindings.getBytesRepresentation(parameterIndex);
    }
    
    private byte[] getBytesRepresentationForBatch(final int parameterIndex, final int commandIndex) {
        final Object batchedArg = this.batchedArgs.get(commandIndex);
        if (batchedArg instanceof String) {
            return StringUtils.getBytes((String)batchedArg, this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue());
        }
        return ((QueryBindings)batchedArg).getBytesRepresentation(parameterIndex);
    }
    
    @Override
    public Array getArray(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getArray(parameterIndex);
    }
    
    @Override
    public InputStream getAsciiStream(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getAsciiStream(parameterIndex);
    }
    
    @Override
    public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBigDecimal(parameterIndex);
    }
    
    @Override
    public InputStream getBinaryStream(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBinaryStream(parameterIndex);
    }
    
    @Override
    public Blob getBlob(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBlob(parameterIndex);
    }
    
    @Override
    public boolean getBoolean(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBoolean(parameterIndex);
    }
    
    @Override
    public byte getByte(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getByte(parameterIndex);
    }
    
    @Override
    public byte[] getBytes(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBytes(parameterIndex);
    }
    
    @Override
    public Reader getCharacterStream(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getCharacterStream(parameterIndex);
    }
    
    @Override
    public Clob getClob(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getClob(parameterIndex);
    }
    
    @Override
    public Date getDate(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getDate(parameterIndex);
    }
    
    @Override
    public double getDouble(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getDouble(parameterIndex);
    }
    
    @Override
    public float getFloat(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getFloat(parameterIndex);
    }
    
    @Override
    public int getInt(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getInt(parameterIndex);
    }
    
    @Override
    public BigInteger getBigInteger(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getBigInteger(parameterIndex);
    }
    
    @Override
    public long getLong(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getLong(parameterIndex);
    }
    
    @Override
    public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getCharacterStream(parameterIndex);
    }
    
    @Override
    public Reader getNClob(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getCharacterStream(parameterIndex);
    }
    
    @Override
    public Object getObject(final int parameterIndex) throws SQLException {
        if (this.bindValues[parameterIndex - 1].isNull()) {
            return null;
        }
        switch (((BindValue)this.queryBindings.getBindValues()[parameterIndex - 1]).getMysqlType()) {
            case TINYINT:
            case TINYINT_UNSIGNED: {
                return this.getByte(parameterIndex);
            }
            case SMALLINT:
            case SMALLINT_UNSIGNED: {
                return this.getShort(parameterIndex);
            }
            case INT:
            case INT_UNSIGNED: {
                return this.getInt(parameterIndex);
            }
            case BIGINT: {
                return this.getLong(parameterIndex);
            }
            case BIGINT_UNSIGNED: {
                return this.getBigInteger(parameterIndex);
            }
            case FLOAT:
            case FLOAT_UNSIGNED: {
                return this.getFloat(parameterIndex);
            }
            case DOUBLE:
            case DOUBLE_UNSIGNED: {
                return this.getDouble(parameterIndex);
            }
            default: {
                return this.bindingsAsRs.getObject(parameterIndex);
            }
        }
    }
    
    @Override
    public Ref getRef(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getRef(parameterIndex);
    }
    
    @Override
    public short getShort(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getShort(parameterIndex);
    }
    
    @Override
    public String getString(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getString(parameterIndex);
    }
    
    @Override
    public Time getTime(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getTime(parameterIndex);
    }
    
    @Override
    public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getTimestamp(parameterIndex);
    }
    
    @Override
    public URL getURL(final int parameterIndex) throws SQLException {
        return this.bindingsAsRs.getURL(parameterIndex);
    }
    
    @Override
    public boolean isNull(final int parameterIndex) throws SQLException {
        return this.queryBindings.isNull(parameterIndex - 1);
    }
}
