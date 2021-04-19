package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;

public class AddStatementImpl implements AddStatement
{
    private MysqlxSession mysqlxSession;
    private String schemaName;
    private String collectionName;
    private List<DbDoc> newDocs;
    private boolean upsert;
    
    AddStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final DbDoc newDoc) {
        this.upsert = false;
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.collectionName = collection;
        (this.newDocs = new ArrayList<DbDoc>()).add(newDoc);
    }
    
    AddStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final DbDoc[] newDocs) {
        this.upsert = false;
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.collectionName = collection;
        (this.newDocs = new ArrayList<DbDoc>()).addAll(Arrays.asList(newDocs));
    }
    
    @Override
    public AddStatement add(final String jsonString) {
        try {
            final DbDoc doc = JsonParser.parseDoc(new StringReader(jsonString));
            return this.add(doc);
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    @Override
    public AddStatement add(final DbDoc... docs) {
        this.newDocs.addAll(Arrays.asList(docs));
        return this;
    }
    
    private List<String> serializeDocs() {
        return this.newDocs.stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    @Override
    public AddResult execute() {
        if (this.newDocs.size() == 0) {
            final StatementExecuteOk ok = new StatementExecuteOk(0L, null, Collections.emptyList(), Collections.emptyList());
            return new AddResultImpl(ok);
        }
        return this.mysqlxSession.query(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDocInsert(this.schemaName, this.collectionName, this.serializeDocs(), this.upsert), (ResultBuilder<AddResult>)new AddResultBuilder());
    }
    
    @Override
    public CompletableFuture<AddResult> executeAsync() {
        if (this.newDocs.size() == 0) {
            final StatementExecuteOk ok = new StatementExecuteOk(0L, null, Collections.emptyList(), Collections.emptyList());
            return (CompletableFuture<AddResult>)CompletableFuture.completedFuture(new AddResultImpl(ok));
        }
        return this.mysqlxSession.queryAsync(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDocInsert(this.schemaName, this.collectionName, this.serializeDocs(), this.upsert), (ResultBuilder<AddResult>)new AddResultBuilder());
    }
    
    @Override
    public boolean isUpsert() {
        return this.upsert;
    }
    
    @Override
    public AddStatement setUpsert(final boolean upsert) {
        this.upsert = upsert;
        return this;
    }
}
