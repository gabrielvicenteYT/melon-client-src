package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class Duration extends GeneratedMessageV3 implements DurationOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int SECONDS_FIELD_NUMBER = 1;
    private long seconds_;
    public static final int NANOS_FIELD_NUMBER = 2;
    private int nanos_;
    private byte memoizedIsInitialized;
    private static final Duration DEFAULT_INSTANCE;
    private static final Parser<Duration> PARSER;
    
    private Duration(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Duration() {
        this.memoizedIsInitialized = -1;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Duration();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Duration(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.seconds_ = input.readInt64();
                        continue;
                    }
                    case 16: {
                        this.nanos_ = input.readInt32();
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
        return DurationProto.internal_static_google_protobuf_Duration_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable.ensureFieldAccessorsInitialized(Duration.class, Builder.class);
    }
    
    @Override
    public long getSeconds() {
        return this.seconds_;
    }
    
    @Override
    public int getNanos() {
        return this.nanos_;
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
        if (this.seconds_ != 0L) {
            output.writeInt64(1, this.seconds_);
        }
        if (this.nanos_ != 0) {
            output.writeInt32(2, this.nanos_);
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
        if (this.seconds_ != 0L) {
            size += CodedOutputStream.computeInt64Size(1, this.seconds_);
        }
        if (this.nanos_ != 0) {
            size += CodedOutputStream.computeInt32Size(2, this.nanos_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Duration)) {
            return super.equals(obj);
        }
        final Duration other = (Duration)obj;
        return this.getSeconds() == other.getSeconds() && this.getNanos() == other.getNanos() && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(this.getSeconds());
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getNanos();
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Duration parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data);
    }
    
    public static Duration parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Duration parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data);
    }
    
    public static Duration parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Duration parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data);
    }
    
    public static Duration parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Duration.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Duration parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Duration.PARSER, input);
    }
    
    public static Duration parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Duration.PARSER, input, extensionRegistry);
    }
    
    public static Duration parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Duration.PARSER, input);
    }
    
    public static Duration parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Duration.PARSER, input, extensionRegistry);
    }
    
    public static Duration parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Duration.PARSER, input);
    }
    
    public static Duration parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Duration.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Duration.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Duration prototype) {
        return Duration.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Duration.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Duration getDefaultInstance() {
        return Duration.DEFAULT_INSTANCE;
    }
    
    public static Parser<Duration> parser() {
        return Duration.PARSER;
    }
    
    @Override
    public Parser<Duration> getParserForType() {
        return Duration.PARSER;
    }
    
    @Override
    public Duration getDefaultInstanceForType() {
        return Duration.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Duration();
        PARSER = new AbstractParser<Duration>() {
            @Override
            public Duration parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Duration(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements DurationOrBuilder
    {
        private long seconds_;
        private int nanos_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return DurationProto.internal_static_google_protobuf_Duration_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable.ensureFieldAccessorsInitialized(Duration.class, Builder.class);
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
            this.seconds_ = 0L;
            this.nanos_ = 0;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DurationProto.internal_static_google_protobuf_Duration_descriptor;
        }
        
        @Override
        public Duration getDefaultInstanceForType() {
            return Duration.getDefaultInstance();
        }
        
        @Override
        public Duration build() {
            final Duration result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Duration buildPartial() {
            final Duration result = new Duration(this, null);
            result.seconds_ = this.seconds_;
            result.nanos_ = this.nanos_;
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
            if (other instanceof Duration) {
                return this.mergeFrom((Duration)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Duration other) {
            if (other == Duration.getDefaultInstance()) {
                return this;
            }
            if (other.getSeconds() != 0L) {
                this.setSeconds(other.getSeconds());
            }
            if (other.getNanos() != 0) {
                this.setNanos(other.getNanos());
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
            Duration parsedMessage = null;
            try {
                parsedMessage = Duration.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Duration)e.getUnfinishedMessage();
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
        public long getSeconds() {
            return this.seconds_;
        }
        
        public Builder setSeconds(final long value) {
            this.seconds_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearSeconds() {
            this.seconds_ = 0L;
            this.onChanged();
            return this;
        }
        
        @Override
        public int getNanos() {
            return this.nanos_;
        }
        
        public Builder setNanos(final int value) {
            this.nanos_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearNanos() {
            this.nanos_ = 0;
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
