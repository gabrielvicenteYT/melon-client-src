package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import java.util.concurrent.*;

public class SelectStatementImpl extends FilterableStatement<SelectStatement, RowResult> implements SelectStatement
{
    SelectStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final String... projection) {
        super(new TableFilterParams(schema, table));
        this.mysqlxSession = mysqlxSession;
        if (projection != null && projection.length > 0) {
            this.filterParams.setFields(projection);
        }
    }
    
    @Override
    protected RowResult executeStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildFind(this.filterParams), (ResultBuilder<RowResult>)new StreamingRowResultBuilder(this.mysqlxSession));
    }
    
    @Override
    protected XMessage getPrepareStatementXMessage() {
        return this.getMessageBuilder().buildPrepareFind(this.preparedStatementId, this.filterParams);
    }
    
    @Override
    protected RowResult executePreparedStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildPrepareExecute(this.preparedStatementId, this.filterParams), (ResultBuilder<RowResult>)new StreamingRowResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public CompletableFuture<RowResult> executeAsync() {
        return this.mysqlxSession.queryAsync(this.getMessageBuilder().buildFind(this.filterParams), (ResultBuilder<RowResult>)new RowResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public SelectStatement groupBy(final String... groupBy) {
        this.resetPrepareState();
        this.filterParams.setGrouping(groupBy);
        return this;
    }
    
    @Override
    public SelectStatement having(final String having) {
        this.resetPrepareState();
        this.filterParams.setGroupingCriteria(having);
        return this;
    }
    
    @Override
    public FilterParams getFilterParams() {
        return this.filterParams;
    }
    
    @Override
    public SelectStatement lockShared() {
        return this.lockShared(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public SelectStatement lockShared(final Statement.LockContention lockContention) {
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
    public SelectStatement lockExclusive() {
        return this.lockExclusive(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public SelectStatement lockExclusive(final Statement.LockContention lockContention) {
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
}
