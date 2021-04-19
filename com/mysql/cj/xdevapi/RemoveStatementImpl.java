package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;
import com.mysql.cj.protocol.x.*;

public class RemoveStatementImpl extends FilterableStatement<RemoveStatement, Result> implements RemoveStatement
{
    RemoveStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection, false));
        this.mysqlxSession = mysqlxSession;
        if (criteria == null || criteria.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("RemoveStatement.0", new String[] { "criteria" }));
        }
        this.filterParams.setCriteria(criteria);
    }
    
    @Deprecated
    @Override
    public RemoveStatement orderBy(final String... sortFields) {
        return super.orderBy(sortFields);
    }
    
    public Result executeStatement() {
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
        return this.mysqlxSession.queryAsync(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDelete(this.filterParams), new UpdateResultBuilder<Result>());
    }
    
    @Deprecated
    @Override
    public RemoveStatement where(final String searchCondition) {
        return super.where(searchCondition);
    }
}
