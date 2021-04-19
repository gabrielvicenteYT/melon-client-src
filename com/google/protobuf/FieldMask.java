package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class FieldMask extends GeneratedMessageV3 implements FieldMaskOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int PATHS_FIELD_NUMBER = 1;
    private LazyStringList paths_;
    private byte memoizedIsInitialized;
    private static final FieldMask DEFAULT_INSTANCE;
    private static final Parser<FieldMask> PARSER;
    
    private FieldMask(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private FieldMask() {
        this.memoizedIsInitialized = -1;
        this.paths_ = LazyStringArrayList.EMPTY;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new FieldMask();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private FieldMask(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        final String s = input.readStringRequireUtf8();
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.paths_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.paths_.add(s);
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
            if ((mutable_bitField0_ & 0x1) != 0x0) {
                this.paths_ = this.paths_.getUnmodifiableView();
            }
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldMask.class, Builder.class);
    }
    
    @Override
    public ProtocolStringList getPathsList() {
        return this.paths_;
    }
    
    @Override
    public int getPathsCount() {
        return this.paths_.size();
    }
    
    @Override
    public String getPaths(final int index) {
        return this.paths_.get(index);
    }
    
    @Override
    public ByteString getPathsBytes(final int index) {
        return this.paths_.getByteString(index);
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
        for (int i = 0; i < this.paths_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 1, this.paths_.getRaw(i));
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
        int dataSize = 0;
        for (int i = 0; i < this.paths_.size(); ++i) {
            dataSize += GeneratedMessageV3.computeStringSizeNoTag(this.paths_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getPathsList().size();
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FieldMask)) {
            return super.equals(obj);
        }
        final FieldMask other = (FieldMask)obj;
        return this.getPathsList().equals(other.getPathsList()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        if (this.getPathsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPathsList().hashCode();
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static FieldMask parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data);
    }
    
    public static FieldMask parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FieldMask parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data);
    }
    
    public static FieldMask parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FieldMask parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data);
    }
    
    public static FieldMask parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return FieldMask.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static FieldMask parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FieldMask.PARSER, input);
    }
    
    public static FieldMask parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FieldMask.PARSER, input, extensionRegistry);
    }
    
    public static FieldMask parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(FieldMask.PARSER, input);
    }
    
    public static FieldMask parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(FieldMask.PARSER, input, extensionRegistry);
    }
    
    public static FieldMask parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FieldMask.PARSER, input);
    }
    
    public static FieldMask parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(FieldMask.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return FieldMask.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final FieldMask prototype) {
        return FieldMask.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == FieldMask.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static FieldMask getDefaultInstance() {
        return FieldMask.DEFAULT_INSTANCE;
    }
    
    public static Parser<FieldMask> parser() {
        return FieldMask.PARSER;
    }
    
    @Override
    public Parser<FieldMask> getParserForType() {
        return FieldMask.PARSER;
    }
    
    @Override
    public FieldMask getDefaultInstanceForType() {
        return FieldMask.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new FieldMask();
        PARSER = new AbstractParser<FieldMask>() {
            @Override
            public FieldMask parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FieldMask(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FieldMaskOrBuilder
    {
        private int bitField0_;
        private LazyStringList paths_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldMask.class, Builder.class);
        }
        
        private Builder() {
            this.paths_ = LazyStringArrayList.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.paths_ = LazyStringArrayList.EMPTY;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.paths_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor;
        }
        
        @Override
        public FieldMask getDefaultInstanceForType() {
            return FieldMask.getDefaultInstance();
        }
        
        @Override
        public FieldMask build() {
            final FieldMask result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public FieldMask buildPartial() {
            final FieldMask result = new FieldMask(this, null);
            final int from_bitField0_ = this.bitField0_;
            if ((this.bitField0_ & 0x1) != 0x0) {
                this.paths_ = this.paths_.getUnmodifiableView();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            result.paths_ = this.paths_;
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
            if (other instanceof FieldMask) {
                return this.mergeFrom((FieldMask)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final FieldMask other) {
            if (other == FieldMask.getDefaultInstance()) {
                return this;
            }
            if (!other.paths_.isEmpty()) {
                if (this.paths_.isEmpty()) {
                    this.paths_ = other.paths_;
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.ensurePathsIsMutable();
                    this.paths_.addAll(other.paths_);
                }
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
            FieldMask parsedMessage = null;
            try {
                parsedMessage = FieldMask.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (FieldMask)e.getUnfinishedMessage();
                throw e.unwrapIOException();
            }
            finally {
                if (parsedMessage != null) {
                    this.mergeFrom(parsedMessage);
                }
            }
            return this;
        }
        
        private void ensurePathsIsMutable() {
            if ((this.bitField0_ & 0x1) == 0x0) {
                this.paths_ = new LazyStringArrayList(this.paths_);
                this.bitField0_ |= 0x1;
            }
        }
        
        @Override
        public ProtocolStringList getPathsList() {
            return this.paths_.getUnmodifiableView();
        }
        
        @Override
        public int getPathsCount() {
            return this.paths_.size();
        }
        
        @Override
        public String getPaths(final int index) {
            return this.paths_.get(index);
        }
        
        @Override
        public ByteString getPathsBytes(final int index) {
            return this.paths_.getByteString(index);
        }
        
        public Builder setPaths(final int index, final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensurePathsIsMutable();
            this.paths_.set(index, value);
            this.onChanged();
            return this;
        }
        
        public Builder addPaths(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensurePathsIsMutable();
            this.paths_.add(value);
            this.onChanged();
            return this;
        }
        
        public Builder addAllPaths(final Iterable<String> values) {
            this.ensurePathsIsMutable();
            AbstractMessageLite.Builder.addAll(values, this.paths_);
            this.onChanged();
            return this;
        }
        
        public Builder clearPaths() {
            this.paths_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }
        
        public Builder addPathsBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.ensurePathsIsMutable();
            this.paths_.add(value);
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
