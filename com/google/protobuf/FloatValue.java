package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class FloatValue extends GeneratedMessageV3 implements FloatValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private float value_;
    private byte memoizedIsInitialized;
    private static final FloatValue DEFAULT_INSTANCE;
    private static final Parser<FloatValue> PARSER;
    
    private FloatValue(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private FloatValue() {
        this.memoizedIsInitialized = -1;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new FloatValue();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private FloatValue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 13: {
                        this.value_ = input.readFloat();
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
        return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
    }
    
    @Override
    public float getValue() {
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
        if (this.value_ != 0.0f) {
            output.writeFloat(1, this.value_);
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
        if (this.value_ != 0.0f) {
            size += CodedOutputStream.computeFloatSize(1, this.value_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FloatValue)) {
            return super.equals(obj);
        }
        final FloatValue other = (FloatValue)obj;
        return Float.floatToIntBits(this.getValue()) == Float.floatToIntBits(other.getValue()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Float.floatToIntBits(this.getValue());
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static FloatValue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data);
    }
    
    public static FloatValue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FloatValue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data);
    }
    
    public static FloatValue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FloatValue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data);
    }
    
    public static FloatValue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FloatValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FloatValue parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FloatValue.PARSER, input);
    }
    
    public static FloatValue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FloatValue.PARSER, input, extensionRegistry);
    }
    
    public static FloatValue parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(FloatValue.PARSER, input);
    }
    
    public static FloatValue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(FloatValue.PARSER, input, extensionRegistry);
    }
    
    public static FloatValue parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FloatValue.PARSER, input);
    }
    
    public static FloatValue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FloatValue.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return FloatValue.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final FloatValue prototype) {
        return FloatValue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == FloatValue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static FloatValue getDefaultInstance() {
        return FloatValue.DEFAULT_INSTANCE;
    }
    
    public static FloatValue of(final float value) {
        return newBuilder().setValue(value).build();
    }
    
    public static Parser<FloatValue> parser() {
        return FloatValue.PARSER;
    }
    
    @Override
    public Parser<FloatValue> getParserForType() {
        return FloatValue.PARSER;
    }
    
    @Override
    public FloatValue getDefaultInstanceForType() {
        return FloatValue.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new FloatValue();
        PARSER = new AbstractParser<FloatValue>() {
            @Override
            public FloatValue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FloatValue(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FloatValueOrBuilder
    {
        private float value_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
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
            this.value_ = 0.0f;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }
        
        @Override
        public FloatValue getDefaultInstanceForType() {
            return FloatValue.getDefaultInstance();
        }
        
        @Override
        public FloatValue build() {
            final FloatValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public FloatValue buildPartial() {
            final FloatValue result = new FloatValue(this, null);
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
            if (other instanceof FloatValue) {
                return this.mergeFrom((FloatValue)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final FloatValue other) {
            if (other == FloatValue.getDefaultInstance()) {
                return this;
            }
            if (other.getValue() != 0.0f) {
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
            FloatValue parsedMessage = null;
            try {
                parsedMessage = FloatValue.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (FloatValue)e.getUnfinishedMessage();
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
        public float getValue() {
            return this.value_;
        }
        
        public Builder setValue(final float value) {
            this.value_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearValue() {
            this.value_ = 0.0f;
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
