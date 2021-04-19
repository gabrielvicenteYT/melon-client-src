package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;
import com.mysql.cj.protocol.x.*;
import java.util.*;

public class UpdateStatementImpl extends FilterableStatement<UpdateStatement, Result> implements UpdateStatement
{
    private UpdateParams updateParams;
    
    UpdateStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table) {
        super(new TableFilterParams(schema, table, false));
        this.updateParams = new UpdateParams();
        this.mysqlxSession = mysqlxSession;
    }
    
    @Override
    protected Result executeStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildRowUpdate(this.filterParams, this.updateParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    protected XMessage getPrepareStatementXMessage() {
        return this.getMessageBuilder().buildPrepareRowUpdate(this.preparedStatementId, this.filterParams, this.updateParams);
    }
    
    @Override
    protected Result executePreparedStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildPrepareExecute(this.preparedStatementId, this.filterParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        return this.mysqlxSession.queryAsync(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowUpdate(this.filterParams, this.updateParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    public UpdateStatement set(final Map<String, Object> fieldsAndValues) {
        this.resetPrepareState();
        this.updateParams.setUpdates(fieldsAndValues);
        return this;
    }
    
    @Override
    public UpdateStatement set(final String field, final Object value) {
        this.resetPrepareState();
        this.updateParams.addUpdate(field, value);
        return this;
    }
}
