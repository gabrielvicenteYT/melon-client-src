package com.google.protobuf;

import java.util.*;

public interface ListValueOrBuilder extends MessageOrBuilder
{
    List<Value> getValuesList();
    
    Value getValues(final int p0);
    
    int getValuesCount();
    
    List<? extends ValueOrBuilder> getValuesOrBuilderList();
    
    ValueOrBuilder getValuesOrBuilder(final int p0);
}
