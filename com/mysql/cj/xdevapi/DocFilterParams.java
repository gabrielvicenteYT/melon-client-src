package com.mysql.cj.xdevapi;

import com.mysql.cj.x.protobuf.*;
import java.util.*;
import java.util.stream.*;

public class DocFilterParams extends AbstractFilterParams
{
    public DocFilterParams(final String schemaName, final String collectionName) {
        this(schemaName, collectionName, true);
    }
    
    public DocFilterParams(final String schemaName, final String collectionName, final boolean supportsOffset) {
        super(schemaName, collectionName, supportsOffset, false);
    }
    
    public void setFields(final Expression docProjection) {
        this.fields = Collections.singletonList(MysqlxCrud.Projection.newBuilder().setSource(new ExprParser(docProjection.getExpressionString(), false).parse()).build());
    }
    
    @Override
    public void setFields(final String... projection) {
        this.fields = new ExprParser(Arrays.stream(projection).collect(Collectors.joining(", ")), false).parseDocumentProjection();
    }
}
