package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.*;
import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;

public class RowFactory implements ProtocolEntityFactory<Row, XMessage>
{
    private ColumnDefinition metadata;
    private TimeZone defaultTimeZone;
    private PropertySet pset;
    
    public RowFactory(final ColumnDefinition metadata, final TimeZone defaultTimeZone, final PropertySet pset) {
        this.metadata = metadata;
        this.defaultTimeZone = defaultTimeZone;
        this.pset = pset;
    }
    
    @Override
    public Row createFromProtocolEntity(final ProtocolEntity internalRow) {
        return new RowImpl((com.mysql.cj.result.Row)internalRow, this.metadata, this.defaultTimeZone, this.pset);
    }
}
