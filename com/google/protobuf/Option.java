package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class Option extends GeneratedMessageV3 implements OptionOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_;
    public static final int VALUE_FIELD_NUMBER = 2;
    private Any value_;
    private byte memoizedIsInitialized;
    private static final Option DEFAULT_INSTANCE;
    private static final Parser<Option> PARSER;
    
    private Option(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Option() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Option();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Option(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.name_ = s;
                        continue;
                    }
                    case 18: {
                        Any.Builder subBuilder = null;
                        if (this.value_ != null) {
                            subBuilder = this.value_.toBuilder();
                        }
                        this.value_ = input.readMessage(Any.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom(this.value_);
                            this.value_ = subBuilder.buildPartial();
                            continue;
                        }
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
        return TypeProto.internal_static_google_protobuf_Option_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
    }
    
    @Override
    public String getName() {
        final Object ref = this.name_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.name_ = s);
    }
    
    @Override
    public ByteString getNameBytes() {
        final Object ref = this.name_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.name_ = b);
        }
        return (ByteString)ref;
    }
    
    @Override
    public boolean hasValue() {
        return this.value_ != null;
    }
    
    @Override
    public Any getValue() {
        return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
    }
    
    @Override
    public AnyOrBuilder getValueOrBuilder() {
        return this.getValue();
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
        if (!this.getNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        if (this.value_ != null) {
            output.writeMessage(2, this.getValue());
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
        if (!this.getNameBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        if (this.value_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getValue());
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Option)) {
            return super.equals(obj);
        }
        final Option other = (Option)obj;
        return this.getName().equals(other.getName()) && this.hasValue() == other.hasValue() && (!this.hasValue() || this.getValue().equals(other.getValue())) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        if (this.hasValue()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getValue().hashCode();
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Option parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data);
    }
    
    public static Option parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Option parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data);
    }
    
    public static Option parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Option parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data);
    }
    
    public static Option parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Option.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Option parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Option.PARSER, input);
    }
    
    public static Option parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Option.PARSER, input, extensionRegistry);
    }
    
    public static Option parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Option.PARSER, input);
    }
    
    public static Option parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Option.PARSER, input, extensionRegistry);
    }
    
    public static Option parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Option.PARSER, input);
    }
    
    public static Option parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Option.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Option.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Option prototype) {
        return Option.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Option.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Option getDefaultInstance() {
        return Option.DEFAULT_INSTANCE;
    }
    
    public static Parser<Option> parser() {
        return Option.PARSER;
    }
    
    @Override
    public Parser<Option> getParserForType() {
        return Option.PARSER;
    }
    
    @Override
    public Option getDefaultInstanceForType() {
        return Option.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Option();
        PARSER = new AbstractParser<Option>() {
            @Override
            public Option parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Option(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OptionOrBuilder
    {
        private Object name_;
        private Any value_;
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> valueBuilder_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Option_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
        }
        
        private Builder() {
            this.name_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.valueBuilder_ == null) {
                this.value_ = null;
            }
            else {
                this.value_ = null;
                this.valueBuilder_ = null;
            }
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Option_descriptor;
        }
        
        @Override
        public Option getDefaultInstanceForType() {
            return Option.getDefaultInstance();
        }
        
        @Override
        public Option build() {
            final Option result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Option buildPartial() {
            final Option result = new Option(this, null);
            result.name_ = this.name_;
            if (this.valueBuilder_ == null) {
                result.value_ = this.value_;
            }
            else {
                result.value_ = this.valueBuilder_.build();
            }
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
            if (other instanceof Option) {
                return this.mergeFrom((Option)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Option other) {
            if (other == Option.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.onChanged();
            }
            if (other.hasValue()) {
                this.mergeValue(other.getValue());
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
            Option parsedMessage = null;
            try {
                parsedMessage = Option.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Option)e.getUnfinishedMessage();
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
        public String getName() {
            final Object ref = this.name_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.name_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getNameBytes() {
            final Object ref = this.name_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.name_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setName(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearName() {
            this.name_ = Option.getDefaultInstance().getName();
            this.onChanged();
            return this;
        }
        
        public Builder setNameBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public boolean hasValue() {
            return this.valueBuilder_ != null || this.value_ != null;
        }
        
        @Override
        public Any getValue() {
            if (this.valueBuilder_ == null) {
                return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
            }
            return this.valueBuilder_.getMessage();
        }
        
        public Builder setValue(final Any value) {
            if (this.valueBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.value_ = value;
                this.onChanged();
            }
            else {
                this.valueBuilder_.setMessage(value);
            }
            return this;
        }
        
        public Builder setValue(final Any.Builder builderForValue) {
            if (this.valueBuilder_ == null) {
                this.value_ = builderForValue.build();
                this.onChanged();
            }
            else {
                this.valueBuilder_.setMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder mergeValue(final Any value) {
            if (this.valueBuilder_ == null) {
                if (this.value_ != null) {
                    this.value_ = Any.newBuilder(this.value_).mergeFrom(value).buildPartial();
                }
                else {
                    this.value_ = value;
                }
                this.onChanged();
            }
            else {
                this.valueBuilder_.mergeFrom(value);
            }
            return this;
        }
        
        public Builder clearValue() {
            if (this.valueBuilder_ == null) {
                this.value_ = null;
                this.onChanged();
            }
            else {
                this.value_ = null;
                this.valueBuilder_ = null;
            }
            return this;
        }
        
        public Any.Builder getValueBuilder() {
            this.onChanged();
            return this.getValueFieldBuilder().getBuilder();
        }
        
        @Override
        public AnyOrBuilder getValueOrBuilder() {
            if (this.valueBuilder_ != null) {
                return this.valueBuilder_.getMessageOrBuilder();
            }
            return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
        }
        
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getValueFieldBuilder() {
            if (this.valueBuilder_ == null) {
                this.valueBuilder_ = new SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder>(this.getValue(), this.getParentForChildren(), this.isClean());
                this.value_ = null;
            }
            return this.valueBuilder_;
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
