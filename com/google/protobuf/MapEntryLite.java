package com.google.protobuf;

import java.io.*;
import java.util.*;

public class MapEntryLite<K, V>
{
    private static final int KEY_FIELD_NUMBER = 1;
    private static final int VALUE_FIELD_NUMBER = 2;
    private final Metadata<K, V> metadata;
    private final K key;
    private final V value;
    
    private MapEntryLite(final WireFormat.FieldType keyType, final K defaultKey, final WireFormat.FieldType valueType, final V defaultValue) {
        this.metadata = new Metadata<K, V>(keyType, defaultKey, valueType, defaultValue);
        this.key = defaultKey;
        this.value = defaultValue;
    }
    
    private MapEntryLite(final Metadata<K, V> metadata, final K key, final V value) {
        this.metadata = metadata;
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return this.key;
    }
    
    public V getValue() {
        return this.value;
    }
    
    public static <K, V> MapEntryLite<K, V> newDefaultInstance(final WireFormat.FieldType keyType, final K defaultKey, final WireFormat.FieldType valueType, final V defaultValue) {
        return new MapEntryLite<K, V>(keyType, defaultKey, valueType, defaultValue);
    }
    
    static <K, V> void writeTo(final CodedOutputStream output, final Metadata<K, V> metadata, final K key, final V value) throws IOException {
        FieldSet.writeElement(output, metadata.keyType, 1, key);
        FieldSet.writeElement(output, metadata.valueType, 2, value);
    }
    
    static <K, V> int computeSerializedSize(final Metadata<K, V> metadata, final K key, final V value) {
        return FieldSet.computeElementSize(metadata.keyType, 1, key) + FieldSet.computeElementSize(metadata.valueType, 2, value);
    }
    
    static <T> T parseField(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final WireFormat.FieldType type, final T value) throws IOException {
        switch (type) {
            case MESSAGE: {
                final MessageLite.Builder subBuilder = ((MessageLite)value).toBuilder();
                input.readMessage(subBuilder, extensionRegistry);
                return (T)subBuilder.buildPartial();
            }
            case ENUM: {
                return (T)Integer.valueOf(input.readEnum());
            }
            case GROUP: {
                throw new RuntimeException("Groups are not allowed in maps.");
            }
            default: {
                return (T)FieldSet.readPrimitiveField(input, type, true);
            }
        }
    }
    
    public void serializeTo(final CodedOutputStream output, final int fieldNumber, final K key, final V value) throws IOException {
        output.writeTag(fieldNumber, 2);
        output.writeUInt32NoTag(computeSerializedSize(this.metadata, key, value));
        writeTo(output, this.metadata, key, value);
    }
    
    public int computeMessageSize(final int fieldNumber, final K key, final V value) {
        return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(computeSerializedSize(this.metadata, key, value));
    }
    
    public Map.Entry<K, V> parseEntry(final ByteString bytes, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return parseEntry(bytes.newCodedInput(), this.metadata, extensionRegistry);
    }
    
    static <K, V> Map.Entry<K, V> parseEntry(final CodedInputStream input, final Metadata<K, V> metadata, final ExtensionRegistryLite extensionRegistry) throws IOException {
        K key = metadata.defaultKey;
        V value = metadata.defaultValue;
        while (true) {
            final int tag = input.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.makeTag(1, metadata.keyType.getWireType())) {
                key = parseField(input, extensionRegistry, metadata.keyType, key);
            }
            else if (tag == WireFormat.makeTag(2, metadata.valueType.getWireType())) {
                value = parseField(input, extensionRegistry, metadata.valueType, value);
            }
            else {
                if (!input.skipField(tag)) {
                    break;
                }
                continue;
            }
        }
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }
    
    public void parseInto(final MapFieldLite<K, V> map, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final int length = input.readRawVarint32();
        final int oldLimit = input.pushLimit(length);
        K key = this.metadata.defaultKey;
        V value = this.metadata.defaultValue;
        while (true) {
            final int tag = input.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.makeTag(1, this.metadata.keyType.getWireType())) {
                key = parseField(input, extensionRegistry, this.metadata.keyType, key);
            }
            else if (tag == WireFormat.makeTag(2, this.metadata.valueType.getWireType())) {
                value = parseField(input, extensionRegistry, this.metadata.valueType, value);
            }
            else {
                if (!input.skipField(tag)) {
                    break;
                }
                continue;
            }
        }
        input.checkLastTagWas(0);
        input.popLimit(oldLimit);
        map.put(key, value);
    }
    
    Metadata<K, V> getMetadata() {
        return this.metadata;
    }
    
    static class Metadata<K, V>
    {
        public final WireFormat.FieldType keyType;
        public final K defaultKey;
        public final WireFormat.FieldType valueType;
        public final V defaultValue;
        
        public Metadata(final WireFormat.FieldType keyType, final K defaultKey, final WireFormat.FieldType valueType, final V defaultValue) {
            this.keyType = keyType;
            this.defaultKey = defaultKey;
            this.valueType = valueType;
            this.defaultValue = defaultValue;
        }
    }
}
