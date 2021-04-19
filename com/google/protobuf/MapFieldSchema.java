package com.google.protobuf;

import java.util.*;

interface MapFieldSchema
{
    Map<?, ?> forMutableMapData(final Object p0);
    
    Map<?, ?> forMapData(final Object p0);
    
    boolean isImmutable(final Object p0);
    
    Object toImmutable(final Object p0);
    
    Object newMapField(final Object p0);
    
    MapEntryLite.Metadata<?, ?> forMapMetadata(final Object p0);
    
    Object mergeFrom(final Object p0, final Object p1);
    
    int getSerializedSize(final int p0, final Object p1, final Object p2);
}
