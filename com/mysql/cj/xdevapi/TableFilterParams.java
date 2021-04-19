package com.mysql.cj.xdevapi;

import java.util.*;
import java.util.stream.*;

public class TableFilterParams extends AbstractFilterParams
{
    public TableFilterParams(final String schemaName, final String collectionName) {
        this(schemaName, collectionName, true);
    }
    
    public TableFilterParams(final String schemaName, final String collectionName, final boolean supportsOffset) {
        super(schemaName, collectionName, supportsOffset, true);
    }
    
    @Override
    public void setFields(final String... projection) {
        this.projection = projection;
        this.fields = new ExprParser(Arrays.stream(projection).collect(Collectors.joining(", ")), true).parseTableSelectProjection();
    }
}
