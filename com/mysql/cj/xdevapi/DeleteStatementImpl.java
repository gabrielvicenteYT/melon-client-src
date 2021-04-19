package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import java.util.concurrent.*;

public class DeleteStatementImpl extends FilterableStatement<DeleteStatement, Result> implements DeleteStatement
{
    DeleteStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table) {
        super(new TableFilterParams(schema, table, false));
        this.mysqlxSession = mysqlxSession;
    }
    
    @Override
    protected Result executeStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildDelete(this.filterParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    protected XMessage getPrepareStatementXMessage() {
        return this.getMessageBuilder().buildPrepareDelete(this.preparedStatementId, this.filterParams);
    }
    
    @Override
    protected Result executePreparedStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildPrepareExecute(this.preparedStatementId, this.filterParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        return this.mysqlxSession.queryAsync(this.getMessageBuilder().buildDelete(this.filterParams), new UpdateResultBuilder<Result>());
    }
}
