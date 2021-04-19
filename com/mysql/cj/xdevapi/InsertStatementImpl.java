package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;
import java.util.*;

public class InsertStatementImpl implements InsertStatement
{
    private MysqlxSession mysqlxSession;
    private String schemaName;
    private String tableName;
    private InsertParams insertParams;
    
    InsertStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final String[] fields) {
        this.insertParams = new InsertParams();
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.tableName = table;
        this.insertParams.setProjection(fields);
    }
    
    InsertStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final Map<String, Object> fieldsAndValues) {
        this.insertParams = new InsertParams();
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.tableName = table;
        this.insertParams.setFieldsAndValues(fieldsAndValues);
    }
    
    @Override
    public InsertResult execute() {
        return this.mysqlxSession.query(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowInsert(this.schemaName, this.tableName, this.insertParams), (ResultBuilder<InsertResult>)new InsertResultBuilder());
    }
    
    @Override
    public CompletableFuture<InsertResult> executeAsync() {
        return this.mysqlxSession.queryAsync(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowInsert(this.schemaName, this.tableName, this.insertParams), (ResultBuilder<InsertResult>)new InsertResultBuilder());
    }
    
    @Override
    public InsertStatement values(final List<Object> row) {
        this.insertParams.addRow(row);
        return this;
    }
}
