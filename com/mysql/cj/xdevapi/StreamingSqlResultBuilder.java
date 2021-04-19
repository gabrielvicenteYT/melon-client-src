package com.mysql.cj.xdevapi;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.result.*;
import com.mysql.cj.protocol.x.*;
import java.util.function.*;
import com.mysql.cj.protocol.*;

public class StreamingSqlResultBuilder implements ResultBuilder<SqlResult>
{
    TimeZone defaultTimeZone;
    PropertySet pset;
    XProtocol protocol;
    StatementExecuteOkBuilder statementExecuteOkBuilder;
    boolean isRowResult;
    ProtocolEntity lastEntity;
    List<SqlSingleResult> resultSets;
    private SqlResult result;
    
    public StreamingSqlResultBuilder(final MysqlxSession sess) {
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        this.isRowResult = false;
        this.lastEntity = null;
        this.resultSets = new ArrayList<SqlSingleResult>();
        this.defaultTimeZone = sess.getServerSession().getDefaultTimeZone();
        this.pset = sess.getPropertySet();
        this.protocol = sess.getProtocol();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
        }
        else {
            this.lastEntity = entity;
        }
        final AtomicBoolean readLastResult = new AtomicBoolean(false);
        final AtomicBoolean atomicBoolean;
        StatementExecuteOk res;
        final Supplier<ProtocolEntity> okReader = () -> {
            if (atomicBoolean.get()) {
                throw new CJCommunicationsException("Invalid state attempting to read ok packet");
            }
            else if (this.protocol.hasMoreResults()) {
                res = this.statementExecuteOkBuilder.build();
                this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
                return res;
            }
            else {
                atomicBoolean.set(true);
                return (ProtocolEntity)this.protocol.readQueryResult((ResultBuilder<ProtocolEntity>)this.statementExecuteOkBuilder);
            }
        };
        final AtomicBoolean atomicBoolean2;
        ColumnDefinition cd;
        final SqlSingleResult sqlSingleResult;
        final Supplier<ProtocolEntity> completer;
        SqlResultBuilder rb;
        final Supplier<SqlResult> resultStream = () -> {
            if (atomicBoolean2.get()) {
                return null;
            }
            else if ((this.lastEntity != null && this.lastEntity instanceof Field) || this.protocol.isSqlResultPending()) {
                if (this.lastEntity != null && this.lastEntity instanceof Field) {
                    cd = this.protocol.readMetadata((Field)this.lastEntity, n -> this.statementExecuteOkBuilder.addProtocolEntity(n));
                    this.lastEntity = null;
                }
                else {
                    cd = this.protocol.readMetadata();
                }
                new SqlSingleResult(cd, this.protocol.getServerSession().getDefaultTimeZone(), new XProtocolRowInputStream(cd, this.protocol, n -> this.statementExecuteOkBuilder.addProtocolEntity(n)), completer, this.pset);
                return sqlSingleResult;
            }
            else {
                atomicBoolean2.set(true);
                rb = new SqlResultBuilder(this.defaultTimeZone, this.pset);
                rb.addProtocolEntity(entity);
                return (SqlResult)this.protocol.readQueryResult((ResultBuilder<SqlResult>)rb);
            }
        };
        this.result = new SqlMultiResult(resultStream);
        return true;
    }
    
    @Override
    public SqlResult build() {
        return this.result;
    }
}
