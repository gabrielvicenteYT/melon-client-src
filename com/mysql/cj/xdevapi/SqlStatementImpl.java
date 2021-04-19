package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;
import java.util.*;
import com.mysql.cj.exceptions.*;

public class SqlStatementImpl implements SqlStatement
{
    private MysqlxSession mysqlxSession;
    private String sql;
    private List<Object> args;
    
    public SqlStatementImpl(final MysqlxSession mysqlxSession, final String sql) {
        this.args = new ArrayList<Object>();
        this.mysqlxSession = mysqlxSession;
        this.sql = sql;
    }
    
    @Override
    public SqlResult execute() {
        return this.mysqlxSession.query((Message)this.mysqlxSession.getMessageBuilder().buildSqlStatement(this.sql, this.args), (ResultBuilder<SqlResult>)new StreamingSqlResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public CompletableFuture<SqlResult> executeAsync() {
        return this.mysqlxSession.queryAsync((Message)this.mysqlxSession.getMessageBuilder().buildSqlStatement(this.sql, this.args), (ResultBuilder<SqlResult>)new SqlResultBuilder(this.mysqlxSession));
    }
    
    @Override
    public SqlStatement clearBindings() {
        this.args.clear();
        return this;
    }
    
    @Override
    public SqlStatement bind(final List<Object> values) {
        this.args.addAll(values);
        return this;
    }
    
    @Override
    public SqlStatement bind(final Map<String, Object> values) {
        throw new FeatureNotAvailableException("Cannot bind named parameters for SQL statements");
    }
}
