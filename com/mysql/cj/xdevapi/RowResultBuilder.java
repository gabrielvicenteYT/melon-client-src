package com.mysql.cj.xdevapi;

import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.result.*;

public class RowResultBuilder implements ResultBuilder<RowResult>
{
    private ArrayList<Field> fields;
    private ColumnDefinition metadata;
    private List<Row> rows;
    private RowResult result;
    TimeZone defaultTimeZone;
    PropertySet pset;
    private StatementExecuteOkBuilder statementExecuteOkBuilder;
    
    public RowResultBuilder(final MysqlxSession sess) {
        this.fields = new ArrayList<Field>();
        this.rows = new ArrayList<Row>();
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        this.defaultTimeZone = sess.getServerSession().getDefaultTimeZone();
        this.pset = sess.getPropertySet();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Field) {
            this.fields.add((Field)entity);
            return false;
        }
        if (entity instanceof Row) {
            if (this.metadata == null) {
                this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
            }
            this.rows.add(((Row)entity).setMetadata(this.metadata));
            return false;
        }
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
            return false;
        }
        if (entity instanceof FetchDoneEntity) {
            return false;
        }
        if (entity instanceof StatementExecuteOk) {
            return true;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unexpected protocol entity " + entity);
    }
    
    @Override
    public RowResult build() {
        if (this.metadata == null) {
            this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
        }
        return this.result = new RowResultImpl(this.metadata, this.defaultTimeZone, (RowList)new BufferedRowList(this.rows), () -> this.statementExecuteOkBuilder.build(), this.pset);
    }
}
