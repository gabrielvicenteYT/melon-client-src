package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class StringValue extends GeneratedMessageV3 implements StringValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private volatile Object value_;
    private byte memoizedIsInitialized;
    private static final StringValue DEFAULT_INSTANCE;
    private static final Parser<StringValue> PARSER;
    
    private StringValue(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private StringValue() {
        this.memoizedIsInitialized = -1;
        this.value_ = "";
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new StringValue();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private StringValue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        final String s = input.readStringRequireUtf8();
                        this.value_ = s;
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
        return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
    }
    
    @Override
    public String getValue() {
        final Object ref = this.value_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.value_ = s);
    }
    
    @Override
    public ByteString getValueBytes() {
        final Object ref = this.value_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.value_ = b);
        }
        return (ByteString)ref;
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
        if (!this.getValueBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.value_);
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
        if (!this.getValueBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.value_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StringValue)) {
            return super.equals(obj);
        }
        final StringValue other = (StringValue)obj;
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
    
    public static StringValue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data);
    }
    
    public static StringValue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static StringValue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data);
    }
    
    public static StringValue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static StringValue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data);
    }
    
    public static StringValue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return StringValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static StringValue parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(StringValue.PARSER, input);
    }
    
    public static StringValue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(StringValue.PARSER, input, extensionRegistry);
    }
    
    public static StringValue parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(StringValue.PARSER, input);
    }
    
    public static StringValue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(StringValue.PARSER, input, extensionRegistry);
    }
    
    public static StringValue parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(StringValue.PARSER, input);
    }
    
    public static StringValue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(StringValue.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return StringValue.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final StringValue prototype) {
        return StringValue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == StringValue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static StringValue getDefaultInstance() {
        return StringValue.DEFAULT_INSTANCE;
    }
    
    public static StringValue of(final String value) {
        return newBuilder().setValue(value).build();
    }
    
    public static Parser<StringValue> parser() {
        return StringValue.PARSER;
    }
    
    @Override
    public Parser<StringValue> getParserForType() {
        return StringValue.PARSER;
    }
    
    @Override
    public StringValue getDefaultInstanceForType() {
        return StringValue.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new StringValue();
        PARSER = new AbstractParser<StringValue>() {
            @Override
            public StringValue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new StringValue(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StringValueOrBuilder
    {
        private Object value_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
        }
        
        private Builder() {
            this.value_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.value_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.value_ = "";
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }
        
        @Override
        public StringValue getDefaultInstanceForType() {
            return StringValue.getDefaultInstance();
        }
        
        @Override
        public StringValue build() {
            final StringValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public StringValue buildPartial() {
            final StringValue result = new StringValue(this, null);
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
            if (other instanceof StringValue) {
                return this.mergeFrom((StringValue)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final StringValue other) {
            if (other == StringValue.getDefaultInstance()) {
                return this;
            }
            if (!other.getValue().isEmpty()) {
                this.value_ = other.value_;
                this.onChanged();
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
            StringValue parsedMessage = null;
            try {
                parsedMessage = StringValue.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (StringValue)e.getUnfinishedMessage();
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
        public String getValue() {
            final Object ref = this.value_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.value_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getValueBytes() {
            final Object ref = this.value_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.value_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setValue(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.value_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearValue() {
            this.value_ = StringValue.getDefaultInstance().getValue();
            this.onChanged();
            return this;
        }
        
        public Builder setValueBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.value_ = value;
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
