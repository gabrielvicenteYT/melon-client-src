package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.interceptors.*;
import com.mysql.cj.jdbc.*;
import com.mysql.cj.log.*;
import java.util.*;
import java.util.function.*;
import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.util.*;
import com.mysql.cj.exceptions.*;
import java.sql.*;

public class ServerStatusDiffInterceptor implements QueryInterceptor
{
    private Map<String, String> preExecuteValues;
    private Map<String, String> postExecuteValues;
    private JdbcConnection connection;
    private Log log;
    
    public ServerStatusDiffInterceptor() {
        this.preExecuteValues = new HashMap<String, String>();
        this.postExecuteValues = new HashMap<String, String>();
    }
    
    @Override
    public QueryInterceptor init(final MysqlConnection conn, final Properties props, final Log l) {
        this.connection = (JdbcConnection)conn;
        this.log = l;
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        this.populateMapWithSessionStatusValues(this.postExecuteValues);
        this.log.logInfo("Server status change for query:\n" + Util.calculateDifferences(this.preExecuteValues, this.postExecuteValues));
        return null;
    }
    
    private void populateMapWithSessionStatusValues(final Map<String, String> toPopulate) {
        try (final Statement stmt = this.connection.createStatement()) {
            toPopulate.clear();
            try (final ResultSet rs = stmt.executeQuery("SHOW SESSION STATUS")) {
                while (rs.next()) {
                    toPopulate.put(rs.getString(1), rs.getString(2));
                }
            }
        }
        catch (SQLException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        this.populateMapWithSessionStatusValues(this.preExecuteValues);
        return null;
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return true;
    }
    
    @Override
    public void destroy() {
        this.connection = null;
        this.log = null;
    }
}
