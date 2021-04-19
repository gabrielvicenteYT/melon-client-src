package com.mysql.cj.xdevapi;

import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.*;
import com.mysql.cj.result.*;
import com.mysql.cj.protocol.x.*;

public class SqlResultBuilder implements ResultBuilder<SqlResult>
{
    private ArrayList<Field> fields;
    private ColumnDefinition metadata;
    private List<Row> rows;
    TimeZone defaultTimeZone;
    PropertySet pset;
    boolean isRowResult;
    List<SqlSingleResult> resultSets;
    private ProtocolEntity prevEntity;
    private StatementExecuteOkBuilder statementExecuteOkBuilder;
    
    public SqlResultBuilder(final TimeZone defaultTimeZone, final PropertySet pset) {
        this.fields = new ArrayList<Field>();
        this.rows = new ArrayList<Row>();
        this.isRowResult = false;
        this.resultSets = new ArrayList<SqlSingleResult>();
        this.prevEntity = null;
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        this.defaultTimeZone = defaultTimeZone;
        this.pset = pset;
    }
    
    public SqlResultBuilder(final MysqlxSession sess) {
        this.fields = new ArrayList<Field>();
        this.rows = new ArrayList<Row>();
        this.isRowResult = false;
        this.resultSets = new ArrayList<SqlSingleResult>();
        this.prevEntity = null;
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        this.defaultTimeZone = sess.getServerSession().getDefaultTimeZone();
        this.pset = sess.getPropertySet();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Field) {
            this.fields.add((Field)entity);
            if (!this.isRowResult) {
                this.isRowResult = true;
            }
            this.prevEntity = entity;
            return false;
        }
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
            return false;
        }
        if (this.isRowResult && this.metadata == null) {
            this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
        }
        if (entity instanceof Row) {
            this.rows.add(((Row)entity).setMetadata(this.metadata));
        }
        else if (entity instanceof FetchDoneMoreResults) {
            this.resultSets.add(new SqlSingleResult(this.metadata, this.defaultTimeZone, (RowList)new BufferedRowList(this.rows), () -> this.statementExecuteOkBuilder.build(), this.pset));
            this.fields = new ArrayList<Field>();
            this.metadata = null;
            this.rows = new ArrayList<Row>();
            this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        }
        else if (entity instanceof FetchDoneEntity) {
            if (!(this.prevEntity instanceof FetchDoneMoreResults)) {
                this.resultSets.add(new SqlSingleResult(this.metadata, this.defaultTimeZone, (RowList)new BufferedRowList(this.rows), () -> this.statementExecuteOkBuilder.build(), this.pset));
            }
        }
        else if (entity instanceof StatementExecuteOk) {
            return true;
        }
        this.prevEntity = entity;
        return false;
    }
    
    @Override
    public SqlResult build() {
        return this.isRowResult ? new SqlMultiResult(() -> (this.resultSets.size() > 0) ? this.resultSets.remove(0) : null) : new SqlUpdateResult(this.statementExecuteOkBuilder.build());
    }
}
