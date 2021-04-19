package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;
import com.mysql.cj.protocol.x.*;
import java.util.*;
import java.util.stream.*;

public class ModifyStatementImpl extends FilterableStatement<ModifyStatement, Result> implements ModifyStatement
{
    private List<UpdateSpec> updates;
    
    ModifyStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection, false));
        this.updates = new ArrayList<UpdateSpec>();
        this.mysqlxSession = mysqlxSession;
        if (criteria == null || criteria.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("ModifyStatement.0", new String[] { "criteria" }));
        }
        this.filterParams.setCriteria(criteria);
        if (!this.mysqlxSession.supportsPreparedStatements()) {
            this.preparedState = PreparedState.UNSUPPORTED;
        }
    }
    
    @Override
    protected Result executeStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildDocUpdate(this.filterParams, this.updates), new UpdateResultBuilder<Result>());
    }
    
    @Override
    protected XMessage getPrepareStatementXMessage() {
        return this.getMessageBuilder().buildPrepareDocUpdate(this.preparedStatementId, this.filterParams, this.updates);
    }
    
    @Override
    protected Result executePreparedStatement() {
        return this.mysqlxSession.query(this.getMessageBuilder().buildPrepareExecute(this.preparedStatementId, this.filterParams), new UpdateResultBuilder<Result>());
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        return this.mysqlxSession.queryAsync(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDocUpdate(this.filterParams, this.updates), new UpdateResultBuilder<Result>());
    }
    
    @Override
    public ModifyStatement set(final String docPath, final Object value) {
        this.resetPrepareState();
        this.updates.add(new UpdateSpec(UpdateType.ITEM_SET, docPath).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement change(final String docPath, final Object value) {
        this.resetPrepareState();
        this.updates.add(new UpdateSpec(UpdateType.ITEM_REPLACE, docPath).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement unset(final String... fields) {
        this.resetPrepareState();
        this.updates.addAll(Arrays.stream(fields).map(docPath -> new UpdateSpec(UpdateType.ITEM_REMOVE, docPath)).collect((Collector<? super Object, ?, Collection<? extends UpdateSpec>>)Collectors.toList()));
        return this;
    }
    
    @Override
    public ModifyStatement patch(final DbDoc document) {
        this.resetPrepareState();
        return this.patch(document.toString());
    }
    
    @Override
    public ModifyStatement patch(final String document) {
        this.resetPrepareState();
        this.updates.add(new UpdateSpec(UpdateType.MERGE_PATCH, "").setValue(Expression.expr(document)));
        return this;
    }
    
    @Override
    public ModifyStatement arrayInsert(final String field, final Object value) {
        this.resetPrepareState();
        this.updates.add(new UpdateSpec(UpdateType.ARRAY_INSERT, field).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement arrayAppend(final String docPath, final Object value) {
        this.resetPrepareState();
        this.updates.add(new UpdateSpec(UpdateType.ARRAY_APPEND, docPath).setValue(value));
        return this;
    }
    
    @Deprecated
    @Override
    public ModifyStatement where(final String searchCondition) {
        return super.where(searchCondition);
    }
}
