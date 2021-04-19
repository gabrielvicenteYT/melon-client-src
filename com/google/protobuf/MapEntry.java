package com.google.protobuf;

import java.io.*;
import java.util.*;

public final class MapEntry<K, V> extends AbstractMessage
{
    private final K key;
    private final V value;
    private final Metadata<K, V> metadata;
    private volatile int cachedSerializedSize;
    
    private MapEntry(final Descriptors.Descriptor descriptor, final WireFormat.FieldType keyType, final K defaultKey, final WireFormat.FieldType valueType, final V defaultValue) {
        this.cachedSerializedSize = -1;
        this.key = defaultKey;
        this.value = defaultValue;
        this.metadata = new Metadata<K, V>(descriptor, this, keyType, valueType);
    }
    
    private MapEntry(final Metadata metadata, final K key, final V value) {
        this.cachedSerializedSize = -1;
        this.key = key;
        this.value = value;
        this.metadata = (Metadata<K, V>)metadata;
    }
    
    private MapEntry(final Metadata<K, V> metadata, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this.cachedSerializedSize = -1;
        try {
            this.metadata = metadata;
            final Map.Entry<K, V> entry = MapEntryLite.parseEntry(input, metadata, extensionRegistry);
            this.key = entry.getKey();
            this.value = entry.getValue();
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        }
        catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
        }
    }
    
    public static <K, V> MapEntry<K, V> newDefaultInstance(final Descriptors.Descriptor descriptor, final WireFormat.FieldType keyType, final K defaultKey, final WireFormat.FieldType valueType, final V defaultValue) {
        return new MapEntry<K, V>(descriptor, keyType, defaultKey, valueType, defaultValue);
    }
    
    public K getKey() {
        return this.key;
    }
    
    public V getValue() {
        return this.value;
    }
    
    @Override
    public int getSerializedSize() {
        if (this.cachedSerializedSize != -1) {
            return this.cachedSerializedSize;
        }
        final int size = MapEntryLite.computeSerializedSize(this.metadata, this.key, this.value);
        return this.cachedSerializedSize = size;
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        MapEntryLite.writeTo(output, this.metadata, this.key, this.value);
    }
    
    @Override
    public boolean isInitialized() {
        return isInitialized(this.metadata, this.value);
    }
    
    @Override
    public Parser<MapEntry<K, V>> getParserForType() {
        return this.metadata.parser;
    }
    
    @Override
    public Builder<K, V> newBuilderForType() {
        return new Builder<K, V>((Metadata)this.metadata);
    }
    
    @Override
    public Builder<K, V> toBuilder() {
        return new Builder<K, V>((Metadata)this.metadata, (Object)this.key, (Object)this.value, true, true);
    }
    
    @Override
    public MapEntry<K, V> getDefaultInstanceForType() {
        return new MapEntry<K, V>(this.metadata, this.metadata.defaultKey, this.metadata.defaultValue);
    }
    
    @Override
    public Descriptors.Descriptor getDescriptorForType() {
        return this.metadata.descriptor;
    }
    
    @Override
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        final TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<Descriptors.FieldDescriptor, Object>();
        for (final Descriptors.FieldDescriptor field : this.metadata.descriptor.getFields()) {
            if (this.hasField(field)) {
                result.put(field, this.getField(field));
            }
        }
        return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)result);
    }
    
    private void checkFieldDescriptor(final Descriptors.FieldDescriptor field) {
        if (field.getContainingType() != this.metadata.descriptor) {
            throw new RuntimeException("Wrong FieldDescriptor \"" + field.getFullName() + "\" used in message \"" + this.metadata.descriptor.getFullName());
        }
    }
    
    @Override
    public boolean hasField(final Descriptors.FieldDescriptor field) {
        this.checkFieldDescriptor(field);
        return true;
    }
    
    @Override
    public Object getField(final Descriptors.FieldDescriptor field) {
        this.checkFieldDescriptor(field);
        Object result = (field.getNumber() == 1) ? this.getKey() : this.getValue();
        if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
            result = field.getEnumType().findValueByNumberCreatingIfUnknown((int)result);
        }
        return result;
    }
    
    @Override
    public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
        throw new RuntimeException("There is no repeated field in a map entry message.");
    }
    
    @Override
    public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
        throw new RuntimeException("There is no repeated field in a map entry message.");
    }
    
    @Override
    public UnknownFieldSet getUnknownFields() {
        return UnknownFieldSet.getDefaultInstance();
    }
    
    private static <V> boolean isInitialized(final Metadata metadata, final V value) {
        return metadata.valueType.getJavaType() != WireFormat.JavaType.MESSAGE || ((MessageLite)value).isInitialized();
    }
    
    final Metadata<K, V> getMetadata() {
        return this.metadata;
    }
    
    private static final class Metadata<K, V> extends MapEntryLite.Metadata<K, V>
    {
        public final Descriptors.Descriptor descriptor;
        public final Parser<MapEntry<K, V>> parser;
        
        public Metadata(final Descriptors.Descriptor descriptor, final MapEntry<K, V> defaultInstance, final WireFormat.FieldType keyType, final WireFormat.FieldType valueType) {
            super(keyType, ((MapEntry<Object, Object>)defaultInstance).key, valueType, ((MapEntry<Object, Object>)defaultInstance).value);
            this.descriptor = descriptor;
            this.parser = new AbstractParser<MapEntry<K, V>>() {
                @Override
                public MapEntry<K, V> parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new MapEntry<K, V>(Metadata.this, input, extensionRegistry, null);
                }
            };
        }
    }
    
    public static class Builder<K, V> extends AbstractMessage.Builder<Builder<K, V>>
    {
        private final Metadata<K, V> metadata;
        private K key;
        private V value;
        private boolean hasKey;
        private boolean hasValue;
        
        private Builder(final Metadata<K, V> metadata) {
            this((Metadata<Object, Object>)metadata, metadata.defaultKey, metadata.defaultValue, false, false);
        }
        
        private Builder(final Metadata<K, V> metadata, final K key, final V value, final boolean hasKey, final boolean hasValue) {
            this.metadata = metadata;
            this.key = key;
            this.value = value;
            this.hasKey = hasKey;
            this.hasValue = hasValue;
        }
        
        public K getKey() {
            return this.key;
        }
        
        public V getValue() {
            return this.value;
        }
        
        public Builder<K, V> setKey(final K key) {
            this.key = key;
            this.hasKey = true;
            return this;
        }
        
        public Builder<K, V> clearKey() {
            this.key = this.metadata.defaultKey;
            this.hasKey = false;
            return this;
        }
        
        public Builder<K, V> setValue(final V value) {
            this.value = value;
            this.hasValue = true;
            return this;
        }
        
        public Builder<K, V> clearValue() {
            this.value = this.metadata.defaultValue;
            this.hasValue = false;
            return this;
        }
        
        @Override
        public MapEntry<K, V> build() {
            final MapEntry<K, V> result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public MapEntry<K, V> buildPartial() {
            return new MapEntry<K, V>(this.metadata, this.key, this.value, null);
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.metadata.descriptor;
        }
        
        private void checkFieldDescriptor(final Descriptors.FieldDescriptor field) {
            if (field.getContainingType() != this.metadata.descriptor) {
                throw new RuntimeException("Wrong FieldDescriptor \"" + field.getFullName() + "\" used in message \"" + this.metadata.descriptor.getFullName());
            }
        }
        
        @Override
        public Message.Builder newBuilderForField(final Descriptors.FieldDescriptor field) {
            this.checkFieldDescriptor(field);
            if (field.getNumber() != 2 || field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new RuntimeException("\"" + field.getFullName() + "\" is not a message value field.");
            }
            return ((Message)this.value).newBuilderForType();
        }
        
        @Override
        public Builder<K, V> setField(final Descriptors.FieldDescriptor field, Object value) {
            this.checkFieldDescriptor(field);
            if (field.getNumber() == 1) {
                this.setKey(value);
            }
            else {
                if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
                    value = ((Descriptors.EnumValueDescriptor)value).getNumber();
                }
                else if (field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && value != null && !this.metadata.defaultValue.getClass().isInstance(value)) {
                    value = ((Message)this.metadata.defaultValue).toBuilder().mergeFrom((Message)value).build();
                }
                this.setValue(value);
            }
            return this;
        }
        
        @Override
        public Builder<K, V> clearField(final Descriptors.FieldDescriptor field) {
            this.checkFieldDescriptor(field);
            if (field.getNumber() == 1) {
                this.clearKey();
            }
            else {
                this.clearValue();
            }
            return this;
        }
        
        @Override
        public Builder<K, V> setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            throw new RuntimeException("There is no repeated field in a map entry message.");
        }
        
        @Override
        public Builder<K, V> addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            throw new RuntimeException("There is no repeated field in a map entry message.");
        }
        
        @Override
        public Builder<K, V> setUnknownFields(final UnknownFieldSet unknownFields) {
            return this;
        }
        
        @Override
        public MapEntry<K, V> getDefaultInstanceForType() {
            return new MapEntry<K, V>(this.metadata, this.metadata.defaultKey, this.metadata.defaultValue, null);
        }
        
        @Override
        public boolean isInitialized() {
            return isInitialized(this.metadata, this.value);
        }
        
        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            final TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<Descriptors.FieldDescriptor, Object>();
            for (final Descriptors.FieldDescriptor field : this.metadata.descriptor.getFields()) {
                if (this.hasField(field)) {
                    result.put(field, this.getField(field));
                }
            }
            return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)result);
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            this.checkFieldDescriptor(field);
            return (field.getNumber() == 1) ? this.hasKey : this.hasValue;
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            this.checkFieldDescriptor(field);
            Object result = (field.getNumber() == 1) ? this.getKey() : this.getValue();
            if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
                result = field.getEnumType().findValueByNumberCreatingIfUnknown((int)result);
            }
            return result;
        }
        
        @Override
        public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
            throw new RuntimeException("There is no repeated field in a map entry message.");
        }
        
        @Override
        public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
            throw new RuntimeException("There is no repeated field in a map entry message.");
        }
        
        @Override
        public UnknownFieldSet getUnknownFields() {
            return UnknownFieldSet.getDefaultInstance();
        }
        
        @Override
        public Builder<K, V> clone() {
            return new Builder<K, V>(this.metadata, this.key, this.value, this.hasKey, this.hasValue);
        }
    }
}
