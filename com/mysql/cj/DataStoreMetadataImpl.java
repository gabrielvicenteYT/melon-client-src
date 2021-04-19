package com.mysql.cj;

import java.util.*;
import java.util.stream.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.result.*;
import java.util.function.*;
import com.mysql.cj.xdevapi.*;

public class DataStoreMetadataImpl implements DataStoreMetadata
{
    private Session session;
    
    public DataStoreMetadataImpl(final Session sess) {
        this.session = sess;
    }
    
    @Override
    public boolean schemaExists(final String schemaName) {
        final StringBuilder stmt = new StringBuilder("select count(*) from information_schema.schemata where schema_name = '");
        stmt.append(schemaName.replaceAll("'", "\\'"));
        stmt.append("'");
        final Function<Row, Long> rowToLong = (Function<Row, Long>)(r -> r.getValue(0, (ValueFactory<Long>)new LongValueFactory(this.session.getPropertySet())));
        final List<Long> counters = this.session.query((Message)this.session.getMessageBuilder().buildSqlStatement(stmt.toString()), null, rowToLong, Collectors.toList());
        return 1L == counters.get(0);
    }
    
    @Override
    public boolean tableExists(final String schemaName, final String tableName) {
        final StringBuilder stmt = new StringBuilder("select count(*) from information_schema.tables where table_schema = '");
        stmt.append(schemaName.replaceAll("'", "\\'"));
        stmt.append("' and table_name = '");
        stmt.append(tableName.replaceAll("'", "\\'"));
        stmt.append("'");
        final Function<Row, Long> rowToLong = (Function<Row, Long>)(r -> r.getValue(0, (ValueFactory<Long>)new LongValueFactory(this.session.getPropertySet())));
        final List<Long> counters = this.session.query((Message)this.session.getMessageBuilder().buildSqlStatement(stmt.toString()), null, rowToLong, Collectors.toList());
        return 1L == counters.get(0);
    }
    
    @Override
    public long getTableRowCount(final String schemaName, final String tableName) {
        final StringBuilder stmt = new StringBuilder("select count(*) from ");
        stmt.append(ExprUnparser.quoteIdentifier(schemaName));
        stmt.append(".");
        stmt.append(ExprUnparser.quoteIdentifier(tableName));
        final Function<Row, Long> rowToLong = (Function<Row, Long>)(r -> r.getValue(0, (ValueFactory<Long>)new LongValueFactory(this.session.getPropertySet())));
        final List<Long> counters = this.session.query((Message)this.session.getMessageBuilder().buildSqlStatement(stmt.toString()), null, rowToLong, Collectors.toList());
        return counters.get(0);
    }
}
