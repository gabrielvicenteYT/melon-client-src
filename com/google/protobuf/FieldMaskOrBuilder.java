package com.google.protobuf;

import java.util.*;

public interface FieldMaskOrBuilder extends MessageOrBuilder
{
    List<String> getPathsList();
    
    int getPathsCount();
    
    String getPaths(final int p0);
    
    ByteString getPathsBytes(final int p0);
}
