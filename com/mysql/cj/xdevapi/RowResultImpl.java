package com.mysql.cj.xdevapi;

import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import java.util.*;
import java.util.function.*;
import com.mysql.cj.result.*;
import java.util.stream.*;

public class RowResultImpl extends AbstractDataResult<Row> implements RowResult
{
    private ColumnDefinition metadata;
    
    public RowResultImpl(final ColumnDefinition metadata, final TimeZone defaultTimeZone, final RowList rows, final Supplier<ProtocolEntity> completer, final PropertySet pset) {
        super(rows, completer, new RowFactory(metadata, defaultTimeZone, pset));
        this.metadata = metadata;
    }
    
    @Override
    public int getColumnCount() {
        return this.metadata.getFields().length;
    }
    
    @Override
    public List<Column> getColumns() {
        return Arrays.stream(this.metadata.getFields()).map((Function<? super Field, ?>)ColumnImpl::new).collect((Collector<? super Object, ?, List<Column>>)Collectors.toList());
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.stream(this.metadata.getFields()).map((Function<? super Field, ?>)Field::getColumnLabel).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
