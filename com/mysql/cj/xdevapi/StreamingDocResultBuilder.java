package com.mysql.cj.xdevapi;

import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.result.*;
import com.mysql.cj.protocol.x.*;

public class StreamingDocResultBuilder implements ResultBuilder<DocResult>
{
    private ArrayList<Field> fields;
    private ColumnDefinition metadata;
    private RowList rowList;
    PropertySet pset;
    XProtocol protocol;
    private StatementExecuteOkBuilder statementExecuteOkBuilder;
    
    public StreamingDocResultBuilder(final MysqlxSession sess) {
        this.fields = new ArrayList<Field>();
        this.rowList = null;
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
        this.pset = sess.getPropertySet();
        this.protocol = sess.getProtocol();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Field) {
            this.fields.add((Field)entity);
            return false;
        }
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
        }
        if (this.metadata == null) {
            this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
        }
        this.rowList = ((entity instanceof Row) ? new XProtocolRowInputStream(this.metadata, (Row)entity, this.protocol, n -> this.statementExecuteOkBuilder.addProtocolEntity(n)) : new XProtocolRowInputStream(this.metadata, this.protocol, n -> this.statementExecuteOkBuilder.addProtocolEntity(n)));
        return true;
    }
    
    @Override
    public DocResult build() {
        return new DocResultImpl(this.rowList, () -> this.protocol.readQueryResult((ResultBuilder<ProtocolEntity>)this.statementExecuteOkBuilder), this.pset);
    }
}
