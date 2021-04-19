package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class Any extends GeneratedMessageV3 implements AnyOrBuilder
{
    private static final long serialVersionUID = 0L;
    private volatile Message cachedUnpackValue;
    public static final int TYPE_URL_FIELD_NUMBER = 1;
    private volatile Object typeUrl_;
    public static final int VALUE_FIELD_NUMBER = 2;
    private ByteString value_;
    private byte memoizedIsInitialized;
    private static final Any DEFAULT_INSTANCE;
    private static final Parser<Any> PARSER;
    
    private Any(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Any() {
        this.memoizedIsInitialized = -1;
        this.typeUrl_ = "";
        this.value_ = ByteString.EMPTY;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Any();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Any(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.typeUrl_ = s;
                        continue;
                    }
                    case 18: {
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
        return AnyProto.internal_static_google_protobuf_Any_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
    }
    
    private static String getTypeUrl(final String typeUrlPrefix, final Descriptors.Descriptor descriptor) {
        return typeUrlPrefix.endsWith("/") ? (typeUrlPrefix + descriptor.getFullName()) : (typeUrlPrefix + "/" + descriptor.getFullName());
    }
    
    private static String getTypeNameFromTypeUrl(final String typeUrl) {
        final int pos = typeUrl.lastIndexOf(47);
        return (pos == -1) ? "" : typeUrl.substring(pos + 1);
    }
    
    public static <T extends Message> Any pack(final T message) {
        return newBuilder().setTypeUrl(getTypeUrl("type.googleapis.com", message.getDescriptorForType())).setValue(message.toByteString()).build();
    }
    
    public static <T extends Message> Any pack(final T message, final String typeUrlPrefix) {
        return newBuilder().setTypeUrl(getTypeUrl(typeUrlPrefix, message.getDescriptorForType())).setValue(message.toByteString()).build();
    }
    
    public <T extends Message> boolean is(final Class<T> clazz) {
        final T defaultInstance = Internal.getDefaultInstance(clazz);
        return getTypeNameFromTypeUrl(this.getTypeUrl()).equals(defaultInstance.getDescriptorForType().getFullName());
    }
    
    public <T extends Message> T unpack(final Class<T> clazz) throws InvalidProtocolBufferException {
        boolean invalidClazz = false;
        if (this.cachedUnpackValue != null) {
            if (this.cachedUnpackValue.getClass() == clazz) {
                return (T)this.cachedUnpackValue;
            }
            invalidClazz = true;
        }
        if (invalidClazz || !this.is(clazz)) {
            throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");
        }
        final T defaultInstance = Internal.getDefaultInstance(clazz);
        final T result = (T)defaultInstance.getParserForType().parseFrom(this.getValue());
        return (T)(this.cachedUnpackValue = result);
    }
    
    @Override
    public String getTypeUrl() {
        final Object ref = this.typeUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.typeUrl_ = s);
    }
    
    @Override
    public ByteString getTypeUrlBytes() {
        final Object ref = this.typeUrl_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.typeUrl_ = b);
        }
        return (ByteString)ref;
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
        if (!this.getTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            output.writeBytes(2, this.value_);
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
        if (!this.getTypeUrlBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            size += CodedOutputStream.computeBytesSize(2, this.value_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Any)) {
            return super.equals(obj);
        }
        final Any other = (Any)obj;
        return this.getTypeUrl().equals(other.getTypeUrl()) && this.getValue().equals(other.getValue()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getTypeUrl().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getValue().hashCode();
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Any parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data);
    }
    
    public static Any parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Any parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data);
    }
    
    public static Any parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Any parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data);
    }
    
    public static Any parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Any.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Any parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Any.PARSER, input);
    }
    
    public static Any parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Any.PARSER, input, extensionRegistry);
    }
    
    public static Any parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Any.PARSER, input);
    }
    
    public static Any parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Any.PARSER, input, extensionRegistry);
    }
    
    public static Any parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Any.PARSER, input);
    }
    
    public static Any parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Any.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Any.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Any prototype) {
        return Any.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Any.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Any getDefaultInstance() {
        return Any.DEFAULT_INSTANCE;
    }
    
    public static Parser<Any> parser() {
        return Any.PARSER;
    }
    
    @Override
    public Parser<Any> getParserForType() {
        return Any.PARSER;
    }
    
    @Override
    public Any getDefaultInstanceForType() {
        return Any.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Any();
        PARSER = new AbstractParser<Any>() {
            @Override
            public Any parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Any(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AnyOrBuilder
    {
        private Object typeUrl_;
        private ByteString value_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
        }
        
        private Builder() {
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }
        
        @Override
        public Any getDefaultInstanceForType() {
            return Any.getDefaultInstance();
        }
        
        @Override
        public Any build() {
            final Any result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Any buildPartial() {
            final Any result = new Any(this, null);
            result.typeUrl_ = this.typeUrl_;
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
            if (other instanceof Any) {
                return this.mergeFrom((Any)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Any other) {
            if (other == Any.getDefaultInstance()) {
                return this;
            }
            if (!other.getTypeUrl().isEmpty()) {
                this.typeUrl_ = other.typeUrl_;
                this.onChanged();
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
            Any parsedMessage = null;
            try {
                parsedMessage = Any.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Any)e.getUnfinishedMessage();
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
        public String getTypeUrl() {
            final Object ref = this.typeUrl_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.typeUrl_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getTypeUrlBytes() {
            final Object ref = this.typeUrl_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.typeUrl_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setTypeUrl(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.typeUrl_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearTypeUrl() {
            this.typeUrl_ = Any.getDefaultInstance().getTypeUrl();
            this.onChanged();
            return this;
        }
        
        public Builder setTypeUrlBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.typeUrl_ = value;
            this.onChanged();
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
            this.value_ = Any.getDefaultInstance().getValue();
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
