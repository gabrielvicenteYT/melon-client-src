package com.google.protobuf;

import java.util.*;

public interface StructOrBuilder extends MessageOrBuilder
{
    int getFieldsCount();
    
    boolean containsFields(final String p0);
    
    @Deprecated
    Map<String, Value> getFields();
    
    Map<String, Value> getFieldsMap();
    
    Value getFieldsOrDefault(final String p0, final Value p1);
    
    Value getFieldsOrThrow(final String p0);
}
