package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class Int64Value extends GeneratedMessageV3 implements Int64ValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private long value_;
    private byte memoizedIsInitialized;
    private static final Int64Value DEFAULT_INSTANCE;
    private static final Parser<Int64Value> PARSER;
    
    private Int64Value(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Int64Value() {
        this.memoizedIsInitialized = -1;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Int64Value();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Int64Value(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
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
                    case 8: {
                        this.value_ = input.readInt64();
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
        return WrappersProto.internal_static_google_protobuf_Int64Value_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_Int64Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Int64Value.class, Builder.class);
    }
    
    @Override
    public long getValue() {
        return this.value_;
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
        if (this.value_ != 0L) {
            output.writeInt64(1, this.value_);
        }
        this.unknownFields.writeTo(output);
    }
    
    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (this.value_ != 0L) {
            size += CodedOutputStream.computeInt64Size(1, this.value_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Int64Value)) {
            return super.equals(obj);
        }
        final Int64Value other = (Int64Value)obj;
        return this.getValue() == other.getValue() && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(this.getValue());
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Int64Value parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data);
    }
    
    public static Int64Value parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Int64Value parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data);
    }
    
    public static Int64Value parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Int64Value parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data);
    }
    
    public static Int64Value parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Int64Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Int64Value parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Int64Value.PARSER, input);
    }
    
    public static Int64Value parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Int64Value.PARSER, input, extensionRegistry);
    }
    
    public static Int64Value parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Int64Value.PARSER, input);
    }
    
    public static Int64Value parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Int64Value.PARSER, input, extensionRegistry);
    }
    
    public static Int64Value parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Int64Value.PARSER, input);
    }
    
    public static Int64Value parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Int64Value.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Int64Value.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Int64Value prototype) {
        return Int64Value.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Int64Value.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Int64Value getDefaultInstance() {
        return Int64Value.DEFAULT_INSTANCE;
    }
    
    public static Int64Value of(final long value) {
        return newBuilder().setValue(value).build();
    }
    
    public static Parser<Int64Value> parser() {
        return Int64Value.PARSER;
    }
    
    @Override
    public Parser<Int64Value> getParserForType() {
        return Int64Value.PARSER;
    }
    
    @Override
    public Int64Value getDefaultInstanceForType() {
        return Int64Value.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Int64Value();
        PARSER = new AbstractParser<Int64Value>() {
            @Override
            public Int64Value parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Int64Value(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements Int64ValueOrBuilder
    {
        private long value_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_Int64Value_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_Int64Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Int64Value.class, Builder.class);
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
            this.value_ = 0L;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_Int64Value_descriptor;
        }
        
        @Override
        public Int64Value getDefaultInstanceForType() {
            return Int64Value.getDefaultInstance();
        }
        
        @Override
        public Int64Value build() {
            final Int64Value result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Int64Value buildPartial() {
            final Int64Value result = new Int64Value(this, null);
            result.value_ = this.value_;
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
            if (other instanceof Int64Value) {
                return this.mergeFrom((Int64Value)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Int64Value other) {
            if (other == Int64Value.getDefaultInstance()) {
                return this;
            }
            if (other.getValue() != 0L) {
                this.setValue(other.getValue());
            }
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
            Int64Value parsedMessage = null;
            try {
                parsedMessage = Int64Value.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Int64Value)e.getUnfinishedMessage();
                throw e.unwrapIOException();
            }
            finally {
                if (parsedMessage != null) {
                    this.mergeFrom(parsedMessage);
                }
            }
            return this;
        }
        
        @Override
        public long getValue() {
            return this.value_;
        }
        
        public Builder setValue(final long value) {
            this.value_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearValue() {
            this.value_ = 0L;
            this.onChanged();
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
