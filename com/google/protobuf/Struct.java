package com.google.protobuf;

import java.util.*;
import java.nio.*;
import java.io.*;

public final class Struct extends GeneratedMessageV3 implements StructOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int FIELDS_FIELD_NUMBER = 1;
    private MapField<String, Value> fields_;
    private byte memoizedIsInitialized;
    private static final Struct DEFAULT_INSTANCE;
    private static final Parser<Struct> PARSER;
    
    private Struct(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Struct() {
        this.memoizedIsInitialized = -1;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Struct();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Struct(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        int mutable_bitField0_ = 0;
        final UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                final int tag = input.readTag();
                switch (tag) {
                    case 0: {
                        done = true;
                        continue;
                    }
                    case 10: {
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.fields_ = MapField.newMapField(FieldsDefaultEntryHolder.defaultEntry);
                            mutable_bitField0_ |= 0x1;
                        }
                        final MapEntry<String, Value> fields__ = input.readMessage(FieldsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                        this.fields_.getMutableMap().put(fields__.getKey(), fields__.getValue());
                        continue;
                    }
                    default: {
                        if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                            continue;
                        }
                        continue;
                    }
                }
            }
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        }
        catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
        }
        finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return StructProto.internal_static_google_protobuf_Struct_descriptor;
    }
    
    @Override
    protected MapField internalGetMapField(final int number) {
        switch (number) {
            case 1: {
                return this.internalGetFields();
            }
            default: {
                throw new RuntimeException("Invalid map field number: " + number);
            }
        }
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
    }
    
    private MapField<String, Value> internalGetFields() {
        if (this.fields_ == null) {
            return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);
        }
        return this.fields_;
    }
    
    @Override
    public int getFieldsCount() {
        return this.internalGetFields().getMap().size();
    }
    
    @Override
    public boolean containsFields(final String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return this.internalGetFields().getMap().containsKey(key);
    }
    
    @Deprecated
    @Override
    public Map<String, Value> getFields() {
        return this.getFieldsMap();
    }
    
    @Override
    public Map<String, Value> getFieldsMap() {
        return this.internalGetFields().getMap();
    }
    
    @Override
    public Value getFieldsOrDefault(final String key, final Value defaultValue) {
        if (key == null) {
            throw new NullPointerException();
        }
        final Map<String, Value> map = this.internalGetFields().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    
    @Override
    public Value getFieldsOrThrow(final String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        final Map<String, Value> map = this.internalGetFields().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
    }
    
    @Override
    public final boolean isInitialized() {
        final byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetFields(), FieldsDefaultEntryHolder.defaultEntry, 1);
        this.unknownFields.writeTo(output);
    }
    
    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        for (final Map.Entry<String, Value> entry : this.internalGetFields().getMap().entrySet()) {
            final MapEntry<String, Value> fields__ = FieldsDefaultEntryHolder.defaultEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(1, fields__);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Struct)) {
            return super.equals(obj);
        }
        final Struct other = (Struct)obj;
        return this.internalGetFields().equals(other.internalGetFields()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        if (!this.internalGetFields().getMap().isEmpty()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.internalGetFields().hashCode();
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Struct parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data);
    }
    
    public static Struct parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Struct parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data);
    }
    
    public static Struct parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Struct parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data);
    }
    
    public static Struct parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Struct.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Struct parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Struct.PARSER, input);
    }
    
    public static Struct parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Struct.PARSER, input, extensionRegistry);
    }
    
    public static Struct parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Struct.PARSER, input);
    }
    
    public static Struct parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Struct.PARSER, input, extensionRegistry);
    }
    
    public static Struct parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Struct.PARSER, input);
    }
    
    public static Struct parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Struct.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Struct.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Struct prototype) {
        return Struct.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Struct.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Struct getDefaultInstance() {
        return Struct.DEFAULT_INSTANCE;
    }
    
    public static Parser<Struct> parser() {
        return Struct.PARSER;
    }
    
    @Override
    public Parser<Struct> getParserForType() {
        return Struct.PARSER;
    }
    
    @Override
    public Struct getDefaultInstanceForType() {
        return Struct.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Struct();
        PARSER = new AbstractParser<Struct>() {
            @Override
            public Struct parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Struct(input, extensionRegistry, null);
            }
        };
    }
    
    private static final class FieldsDefaultEntryHolder
    {
        static final MapEntry<String, Value> defaultEntry;
        
        static {
            defaultEntry = MapEntry.newDefaultInstance(StructProto.internal_static_google_protobuf_Struct_FieldsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());
        }
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StructOrBuilder
    {
        private int bitField0_;
        private MapField<String, Value> fields_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_Struct_descriptor;
        }
        
        @Override
        protected MapField internalGetMapField(final int number) {
            switch (number) {
                case 1: {
                    return this.internalGetFields();
                }
                default: {
                    throw new RuntimeException("Invalid map field number: " + number);
                }
            }
        }
        
        @Override
        protected MapField internalGetMutableMapField(final int number) {
            switch (number) {
                case 1: {
                    return this.internalGetMutableFields();
                }
                default: {
                    throw new RuntimeException("Invalid map field number: " + number);
                }
            }
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
        }
        
        private Builder() {
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.internalGetMutableFields().clear();
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_Struct_descriptor;
        }
        
        @Override
        public Struct getDefaultInstanceForType() {
            return Struct.getDefaultInstance();
        }
        
        @Override
        public Struct build() {
            final Struct result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Struct buildPartial() {
            final Struct result = new Struct(this, null);
            final int from_bitField0_ = this.bitField0_;
            result.fields_ = this.internalGetFields();
            result.fields_.makeImmutable();
            this.onBuilt();
            return result;
        }
        
        @Override
        public Builder clone() {
            return super.clone();
        }
        
        @Override
        public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
            return super.setField(field, value);
        }
        
        @Override
        public Builder clearField(final Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }
        
        @Override
        public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }
        
        @Override
        public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            return super.setRepeatedField(field, index, value);
        }
        
        @Override
        public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            return super.addRepeatedField(field, value);
        }
        
        @Override
        public Builder mergeFrom(final Message other) {
            if (other instanceof Struct) {
                return this.mergeFrom((Struct)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Struct other) {
            if (other == Struct.getDefaultInstance()) {
                return this;
            }
            this.internalGetMutableFields().mergeFrom(other.internalGetFields());
            this.mergeUnknownFields(other.unknownFields);
            this.onChanged();
            return this;
        }
        
        @Override
        public final boolean isInitialized() {
            return true;
        }
        
        @Override
        public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            Struct parsedMessage = null;
            try {
                parsedMessage = Struct.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Struct)e.getUnfinishedMessage();
                throw e.unwrapIOException();
            }
            finally {
                if (parsedMessage != null) {
                    this.mergeFrom(parsedMessage);
                }
            }
            return this;
        }
        
        private MapField<String, Value> internalGetFields() {
            if (this.fields_ == null) {
                return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);
            }
            return this.fields_;
        }
        
        private MapField<String, Value> internalGetMutableFields() {
            this.onChanged();
            if (this.fields_ == null) {
                this.fields_ = MapField.newMapField(FieldsDefaultEntryHolder.defaultEntry);
            }
            if (!this.fields_.isMutable()) {
                this.fields_ = this.fields_.copy();
            }
            return this.fields_;
        }
        
        @Override
        public int getFieldsCount() {
            return this.internalGetFields().getMap().size();
        }
        
        @Override
        public boolean containsFields(final String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            return this.internalGetFields().getMap().containsKey(key);
        }
        
        @Deprecated
        @Override
        public Map<String, Value> getFields() {
            return this.getFieldsMap();
        }
        
        @Override
        public Map<String, Value> getFieldsMap() {
            return this.internalGetFields().getMap();
        }
        
        @Override
        public Value getFieldsOrDefault(final String key, final Value defaultValue) {
            if (key == null) {
                throw new NullPointerException();
            }
            final Map<String, Value> map = this.internalGetFields().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }
        
        @Override
        public Value getFieldsOrThrow(final String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            final Map<String, Value> map = this.internalGetFields().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }
        
        public Builder clearFields() {
            this.internalGetMutableFields().getMutableMap().clear();
            return this;
        }
        
        public Builder removeFields(final String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            this.internalGetMutableFields().getMutableMap().remove(key);
            return this;
        }
        
        @Deprecated
        public Map<String, Value> getMutableFields() {
            return this.internalGetMutableFields().getMutableMap();
        }
        
        public Builder putFields(final String key, final Value value) {
            if (key == null) {
                throw new NullPointerException();
            }
            if (value == null) {
                throw new NullPointerException();
            }
            this.internalGetMutableFields().getMutableMap().put(key, value);
            return this;
        }
        
        public Builder putAllFields(final Map<String, Value> values) {
            this.internalGetMutableFields().getMutableMap().putAll(values);
            return this;
        }
        
        @Override
        public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }
        
        @Override
        public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }
    }
}
