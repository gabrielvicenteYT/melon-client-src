package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class SourceContext extends GeneratedMessageV3 implements SourceContextOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int FILE_NAME_FIELD_NUMBER = 1;
    private volatile Object fileName_;
    private byte memoizedIsInitialized;
    private static final SourceContext DEFAULT_INSTANCE;
    private static final Parser<SourceContext> PARSER;
    
    private SourceContext(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private SourceContext() {
        this.memoizedIsInitialized = -1;
        this.fileName_ = "";
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new SourceContext();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private SourceContext(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.fileName_ = s;
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
        return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceContext.class, Builder.class);
    }
    
    @Override
    public String getFileName() {
        final Object ref = this.fileName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.fileName_ = s);
    }
    
    @Override
    public ByteString getFileNameBytes() {
        final Object ref = this.fileName_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.fileName_ = b);
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
        if (!this.getFileNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.fileName_);
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
        if (!this.getFileNameBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.fileName_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SourceContext)) {
            return super.equals(obj);
        }
        final SourceContext other = (SourceContext)obj;
        return this.getFileName().equals(other.getFileName()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getFileName().hashCode();
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static SourceContext parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data);
    }
    
    public static SourceContext parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static SourceContext parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data);
    }
    
    public static SourceContext parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static SourceContext parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data);
    }
    
    public static SourceContext parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return SourceContext.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static SourceContext parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(SourceContext.PARSER, input);
    }
    
    public static SourceContext parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(SourceContext.PARSER, input, extensionRegistry);
    }
    
    public static SourceContext parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(SourceContext.PARSER, input);
    }
    
    public static SourceContext parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(SourceContext.PARSER, input, extensionRegistry);
    }
    
    public static SourceContext parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(SourceContext.PARSER, input);
    }
    
    public static SourceContext parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(SourceContext.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return SourceContext.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final SourceContext prototype) {
        return SourceContext.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == SourceContext.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static SourceContext getDefaultInstance() {
        return SourceContext.DEFAULT_INSTANCE;
    }
    
    public static Parser<SourceContext> parser() {
        return SourceContext.PARSER;
    }
    
    @Override
    public Parser<SourceContext> getParserForType() {
        return SourceContext.PARSER;
    }
    
    @Override
    public SourceContext getDefaultInstanceForType() {
        return SourceContext.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new SourceContext();
        PARSER = new AbstractParser<SourceContext>() {
            @Override
            public SourceContext parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SourceContext(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements SourceContextOrBuilder
    {
        private Object fileName_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceContext.class, Builder.class);
        }
        
        private Builder() {
            this.fileName_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.fileName_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.fileName_ = "";
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
        }
        
        @Override
        public SourceContext getDefaultInstanceForType() {
            return SourceContext.getDefaultInstance();
        }
        
        @Override
        public SourceContext build() {
            final SourceContext result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public SourceContext buildPartial() {
            final SourceContext result = new SourceContext(this, null);
            result.fileName_ = this.fileName_;
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
            if (other instanceof SourceContext) {
                return this.mergeFrom((SourceContext)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final SourceContext other) {
            if (other == SourceContext.getDefaultInstance()) {
                return this;
            }
            if (!other.getFileName().isEmpty()) {
                this.fileName_ = other.fileName_;
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
            SourceContext parsedMessage = null;
            try {
                parsedMessage = SourceContext.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (SourceContext)e.getUnfinishedMessage();
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
        public String getFileName() {
            final Object ref = this.fileName_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.fileName_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getFileNameBytes() {
            final Object ref = this.fileName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.fileName_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setFileName(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.fileName_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearFileName() {
            this.fileName_ = SourceContext.getDefaultInstance().getFileName();
            this.onChanged();
            return this;
        }
        
        public Builder setFileNameBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.fileName_ = value;
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
