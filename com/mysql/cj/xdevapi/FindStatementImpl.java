package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import java.util.concurrent.*;

public class FindStatementImpl extends FilterableStatement<FindStatement, DocResult> implements FindStatement
{
    FindStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection));
        this.mysqlxSession = mysqlxSession;
        if (criteria != null && criteria.length() > 0) {
            this.filterParams.setCriteria(criteria);
        }
        if (!this.mysqlxSession.supportsPreparedStatements()) {
            this.preparedState = PreparedState.UNSUPPORTED;
        }
    }
    
    @Override
    protected DocResult executeStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildFind(this.filterParams), (ResultBuilder<DocResult>)new StreamingDocResultBuilder(this.mysqlxSession));
    }
    
    @Override
    protected XMessage getPrepareStatementXMessage() {
        return this.getMessageBuilder().buildPrepareFind(this.preparedStatementId, this.filterParams);
    }
    
    @Override
    protected DocResult executePreparedStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildPrepareExecute(this.preparedStatementId, this.filterParams), (ResultBuilder<DocResult>)new StreamingDocResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public CompletableFuture<DocResult> executeAsync() {
        return this.mysqlxSession.queryAsync(this.getMessageBuilder().buildFind(this.filterParams), (ResultBuilder<DocResult>)new DocResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public FindStatement fields(final String... projection) {
        this.resetPrepareState();
        this.filterParams.setFields(projection);
        return this;
    }
    
    @Override
    public FindStatement fields(final Expression docProjection) {
        this.resetPrepareState();
        ((DocFilterParams)this.filterParams).setFields(docProjection);
        return this;
    }
    
    @Override
    public FindStatement groupBy(final String... groupBy) {
        this.resetPrepareState();
        this.filterParams.setGrouping(groupBy);
        return this;
    }
    
    @Override
    public FindStatement having(final String having) {
        this.resetPrepareState();
        this.filterParams.setGroupingCriteria(having);
        return this;
    }
    
    @Override
    public FindStatement lockShared() {
        return this.lockShared(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public FindStatement lockShared(final Statement.LockContention lockContention) {
        this.resetPrepareState();
        this.filterParams.setLock(FilterParams.RowLock.SHARED_LOCK);
        switch (lockContention) {
            case NOWAIT: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.NOWAIT);
                break;
            }
            case SKIP_LOCKED: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.SKIP_LOCKED);
                break;
            }
        }
        return this;
    }
    
    @Override
    public FindStatement lockExclusive() {
        return this.lockExclusive(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public FindStatement lockExclusive(final Statement.LockContention lockContention) {
        this.resetPrepareState();
        this.filterParams.setLock(FilterParams.RowLock.EXCLUSIVE_LOCK);
        switch (lockContention) {
            case NOWAIT: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.NOWAIT);
                break;
            }
            case SKIP_LOCKED: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.SKIP_LOCKED);
                break;
            }
        }
        return this;
    }
    
    @Deprecated
    @Override
    public FindStatement where(final String searchCondition) {
        return super.where(searchCondition);
    }
}
