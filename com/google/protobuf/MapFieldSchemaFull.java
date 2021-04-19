package com.google.protobuf;

import java.util.*;

class MapFieldSchemaFull implements MapFieldSchema
{
    @Override
    public Map<?, ?> forMutableMapData(final Object mapField) {
        return ((MapField)mapField).getMutableMap();
    }
    
    @Override
    public Map<?, ?> forMapData(final Object mapField) {
        return ((MapField)mapField).getMap();
    }
    
    @Override
    public boolean isImmutable(final Object mapField) {
        return !((MapField)mapField).isMutable();
    }
    
    @Override
    public Object toImmutable(final Object mapField) {
        ((MapField)mapField).makeImmutable();
        return mapField;
    }
    
    @Override
    public Object newMapField(final Object mapDefaultEntry) {
        return MapField.newMapField((MapEntry<Object, Object>)mapDefaultEntry);
    }
    
    @Override
    public MapEntryLite.Metadata<?, ?> forMapMetadata(final Object mapDefaultEntry) {
        return (MapEntryLite.Metadata<?, ?>)((MapEntry)mapDefaultEntry).getMetadata();
    }
    
    @Override
    public Object mergeFrom(final Object destMapField, final Object srcMapField) {
        return mergeFromFull(destMapField, srcMapField);
    }
    
    private static <K, V> Object mergeFromFull(final Object destMapField, final Object srcMapField) {
        final MapField<K, V> mine = (MapField<K, V>)destMapField;
        final MapField<K, V> other = (MapField<K, V>)srcMapField;
        if (!mine.isMutable()) {
            mine.copy();
        }
        mine.mergeFrom(other);
        return mine;
    }
    
    @Override
    public int getSerializedSize(final int number, final Object mapField, final Object mapDefaultEntry) {
        return getSerializedSizeFull(number, mapField, mapDefaultEntry);
    }
    
    private static <K, V> int getSerializedSizeFull(final int number, final Object mapField, final Object defaultEntryObject) {
        if (mapField == null) {
            return 0;
        }
        final Map<K, V> map = ((MapField)mapField).getMap();
        final MapEntry<K, V> defaultEntry = (MapEntry<K, V>)defaultEntryObject;
        if (map.isEmpty()) {
            return 0;
        }
        int size = 0;
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeLengthDelimitedFieldSize(MapEntryLite.computeSerializedSize(defaultEntry.getMetadata(), entry.getKey(), entry.getValue()));
        }
        return size;
    }
}
