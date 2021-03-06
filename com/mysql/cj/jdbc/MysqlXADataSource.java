package com.mysql.cj.jdbc;

import javax.sql.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import java.sql.*;
import com.mysql.cj.conf.*;

public class MysqlXADataSource extends MysqlDataSource implements XADataSource
{
    static final long serialVersionUID = 7911390333152247455L;
    
    @Override
    public XAConnection getXAConnection() throws SQLException {
        try {
            final Connection conn = this.getConnection();
            return this.wrapConnection(conn);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public XAConnection getXAConnection(final String u, final String p) throws SQLException {
        try {
            final Connection conn = this.getConnection(u, p);
            return this.wrapConnection(conn);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    private XAConnection wrapConnection(final Connection conn) throws SQLException {
        if (this.getBooleanProperty(PropertyKey.pinGlobalTxToPhysicalConnection).getValue() || ((JdbcConnection)conn).getPropertySet().getBooleanProperty(PropertyKey.pinGlobalTxToPhysicalConnection).getValue()) {
            return SuspendableXAConnection.getInstance((JdbcConnection)conn);
        }
        return MysqlXAConnection.getInstance((JdbcConnection)conn, this.getBooleanProperty(PropertyKey.logXaCommands).getValue());
    }
}
