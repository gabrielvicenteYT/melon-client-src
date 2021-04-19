package com.google.protobuf;

import java.util.*;

class MapFieldSchemaLite implements MapFieldSchema
{
    @Override
    public Map<?, ?> forMutableMapData(final Object mapField) {
        return (Map<?, ?>)mapField;
    }
    
    @Override
    public MapEntryLite.Metadata<?, ?> forMapMetadata(final Object mapDefaultEntry) {
        return ((MapEntryLite)mapDefaultEntry).getMetadata();
    }
    
    @Override
    public Map<?, ?> forMapData(final Object mapField) {
        return (Map<?, ?>)mapField;
    }
    
    @Override
    public boolean isImmutable(final Object mapField) {
        return !((MapFieldLite)mapField).isMutable();
    }
    
    @Override
    public Object toImmutable(final Object mapField) {
        ((MapFieldLite)mapField).makeImmutable();
        return mapField;
    }
    
    @Override
    public Object newMapField(final Object unused) {
        return MapFieldLite.emptyMapField().mutableCopy();
    }
    
    @Override
    public Object mergeFrom(final Object destMapField, final Object srcMapField) {
        return mergeFromLite(destMapField, srcMapField);
    }
    
    private static <K, V> MapFieldLite<K, V> mergeFromLite(final Object destMapField, final Object srcMapField) {
        MapFieldLite<K, V> mine = (MapFieldLite<K, V>)destMapField;
        final MapFieldLite<K, V> other = (MapFieldLite<K, V>)srcMapField;
        if (!other.isEmpty()) {
            if (!mine.isMutable()) {
                mine = mine.mutableCopy();
            }
            mine.mergeFrom(other);
        }
        return mine;
    }
    
    @Override
    public int getSerializedSize(final int fieldNumber, final Object mapField, final Object mapDefaultEntry) {
        return getSerializedSizeLite(fieldNumber, mapField, mapDefaultEntry);
    }
    
    private static <K, V> int getSerializedSizeLite(final int fieldNumber, final Object mapField, final Object defaultEntry) {
        final MapFieldLite<K, V> mapFieldLite = (MapFieldLite<K, V>)mapField;
        final MapEntryLite<K, V> defaultEntryLite = (MapEntryLite<K, V>)defaultEntry;
        if (mapFieldLite.isEmpty()) {
            return 0;
        }
        int size = 0;
        for (final Map.Entry<K, V> entry : mapFieldLite.entrySet()) {
            size += defaultEntryLite.computeMessageSize(fieldNumber, entry.getKey(), entry.getValue());
        }
        return size;
    }
}
