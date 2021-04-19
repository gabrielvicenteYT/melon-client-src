package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class BytesValue extends GeneratedMessageV3 implements BytesValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private ByteString value_;
    private byte memoizedIsInitialized;
    private static final BytesValue DEFAULT_INSTANCE;
    private static final Parser<BytesValue> PARSER;
    
    private BytesValue(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private BytesValue() {
        this.memoizedIsInitialized = -1;
        this.value_ = ByteString.EMPTY;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new BytesValue();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private BytesValue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 10: {
                        this.value_ = input.readBytes();
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
        return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_BytesValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BytesValue.class, Builder.class);
    }
    
    @Override
    public ByteString getValue() {
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
        if (!this.value_.isEmpty()) {
            output.writeBytes(1, this.value_);
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
        if (!this.value_.isEmpty()) {
            size += CodedOutputStream.computeBytesSize(1, this.value_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BytesValue)) {
            return super.equals(obj);
        }
        final BytesValue other = (BytesValue)obj;
        return this.getValue().equals(other.getValue()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getValue().hashCode();
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static BytesValue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data);
    }
    
    public static BytesValue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static BytesValue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data);
    }
    
    public static BytesValue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static BytesValue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data);
    }
    
    public static BytesValue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return BytesValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static BytesValue parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(BytesValue.PARSER, input);
    }
    
    public static BytesValue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(BytesValue.PARSER, input, extensionRegistry);
    }
    
    public static BytesValue parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(BytesValue.PARSER, input);
    }
    
    public static BytesValue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(BytesValue.PARSER, input, extensionRegistry);
    }
    
    public static BytesValue parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(BytesValue.PARSER, input);
    }
    
    public static BytesValue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(BytesValue.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return BytesValue.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final BytesValue prototype) {
        return BytesValue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == BytesValue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static BytesValue getDefaultInstance() {
        return BytesValue.DEFAULT_INSTANCE;
    }
    
    public static BytesValue of(final ByteString value) {
        return newBuilder().setValue(value).build();
    }
    
    public static Parser<BytesValue> parser() {
        return BytesValue.PARSER;
    }
    
    @Override
    public Parser<BytesValue> getParserForType() {
        return BytesValue.PARSER;
    }
    
    @Override
    public BytesValue getDefaultInstanceForType() {
        return BytesValue.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new BytesValue();
        PARSER = new AbstractParser<BytesValue>() {
            @Override
            public BytesValue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new BytesValue(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements BytesValueOrBuilder
    {
        private ByteString value_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_BytesValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BytesValue.class, Builder.class);
        }
        
        private Builder() {
            this.value_ = ByteString.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.value_ = ByteString.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.value_ = ByteString.EMPTY;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_BytesValue_descriptor;
        }
        
        @Override
        public BytesValue getDefaultInstanceForType() {
            return BytesValue.getDefaultInstance();
        }
        
        @Override
        public BytesValue build() {
            final BytesValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public BytesValue buildPartial() {
            final BytesValue result = new BytesValue(this, null);
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
            if (other instanceof BytesValue) {
                return this.mergeFrom((BytesValue)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final BytesValue other) {
            if (other == BytesValue.getDefaultInstance()) {
                return this;
            }
            if (other.getValue() != ByteString.EMPTY) {
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
            BytesValue parsedMessage = null;
            try {
                parsedMessage = BytesValue.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (BytesValue)e.getUnfinishedMessage();
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
        public ByteString getValue() {
            return this.value_;
        }
        
        public Builder setValue(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.value_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearValue() {
            this.value_ = BytesValue.getDefaultInstance().getValue();
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
