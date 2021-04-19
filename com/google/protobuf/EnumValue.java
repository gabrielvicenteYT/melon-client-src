package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class EnumValue extends GeneratedMessageV3 implements EnumValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_;
    public static final int NUMBER_FIELD_NUMBER = 2;
    private int number_;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private List<Option> options_;
    private byte memoizedIsInitialized;
    private static final EnumValue DEFAULT_INSTANCE;
    private static final Parser<EnumValue> PARSER;
    
    private EnumValue(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private EnumValue() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.options_ = Collections.emptyList();
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new EnumValue();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private EnumValue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.name_ = s;
                        continue;
                    }
                    case 16: {
                        this.number_ = input.readInt32();
                        continue;
                    }
                    case 26: {
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.options_ = new ArrayList<Option>();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
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
                this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
            }
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValue.class, Builder.class);
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
    public int getNumber() {
        return this.number_;
    }
    
    @Override
    public List<Option> getOptionsList() {
        return this.options_;
    }
    
    @Override
    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }
    
    @Override
    public int getOptionsCount() {
        return this.options_.size();
    }
    
    @Override
    public Option getOptions(final int index) {
        return this.options_.get(index);
    }
    
    @Override
    public OptionOrBuilder getOptionsOrBuilder(final int index) {
        return this.options_.get(index);
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
        if (this.number_ != 0) {
            output.writeInt32(2, this.number_);
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            output.writeMessage(3, this.options_.get(i));
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
        if (this.number_ != 0) {
            size += CodedOutputStream.computeInt32Size(2, this.number_);
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.options_.get(i));
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EnumValue)) {
            return super.equals(obj);
        }
        final EnumValue other = (EnumValue)obj;
        return this.getName().equals(other.getName()) && this.getNumber() == other.getNumber() && this.getOptionsList().equals(other.getOptionsList()) && this.unknownFields.equals(other.unknownFields);
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
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getNumber();
        if (this.getOptionsCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getOptionsList().hashCode();
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static EnumValue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data);
    }
    
    public static EnumValue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static EnumValue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data);
    }
    
    public static EnumValue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static EnumValue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data);
    }
    
    public static EnumValue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return EnumValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static EnumValue parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(EnumValue.PARSER, input);
    }
    
    public static EnumValue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(EnumValue.PARSER, input, extensionRegistry);
    }
    
    public static EnumValue parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(EnumValue.PARSER, input);
    }
    
    public static EnumValue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(EnumValue.PARSER, input, extensionRegistry);
    }
    
    public static EnumValue parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(EnumValue.PARSER, input);
    }
    
    public static EnumValue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(EnumValue.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return EnumValue.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final EnumValue prototype) {
        return EnumValue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == EnumValue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static EnumValue getDefaultInstance() {
        return EnumValue.DEFAULT_INSTANCE;
    }
    
    public static Parser<EnumValue> parser() {
        return EnumValue.PARSER;
    }
    
    @Override
    public Parser<EnumValue> getParserForType() {
        return EnumValue.PARSER;
    }
    
    @Override
    public EnumValue getDefaultInstanceForType() {
        return EnumValue.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new EnumValue();
        PARSER = new AbstractParser<EnumValue>() {
            @Override
            public EnumValue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EnumValue(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements EnumValueOrBuilder
    {
        private int bitField0_;
        private Object name_;
        private int number_;
        private List<Option> options_;
        private RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValue.class, Builder.class);
        }
        
        private Builder() {
            this.name_ = "";
            this.options_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.options_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                this.getOptionsFieldBuilder();
            }
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.name_ = "";
            this.number_ = 0;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            else {
                this.optionsBuilder_.clear();
            }
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
        }
        
        @Override
        public EnumValue getDefaultInstanceForType() {
            return EnumValue.getDefaultInstance();
        }
        
        @Override
        public EnumValue build() {
            final EnumValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public EnumValue buildPartial() {
            final EnumValue result = new EnumValue(this, null);
            final int from_bitField0_ = this.bitField0_;
            result.name_ = this.name_;
            result.number_ = this.number_;
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.options_ = this.options_;
            }
            else {
                result.options_ = this.optionsBuilder_.build();
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
            if (other instanceof EnumValue) {
                return this.mergeFrom((EnumValue)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final EnumValue other) {
            if (other == EnumValue.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.onChanged();
            }
            if (other.getNumber() != 0) {
                this.setNumber(other.getNumber());
            }
            if (this.optionsBuilder_ == null) {
                if (!other.options_.isEmpty()) {
                    if (this.options_.isEmpty()) {
                        this.options_ = other.options_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureOptionsIsMutable();
                        this.options_.addAll(other.options_);
                    }
                    this.onChanged();
                }
            }
            else if (!other.options_.isEmpty()) {
                if (this.optionsBuilder_.isEmpty()) {
                    this.optionsBuilder_.dispose();
                    this.optionsBuilder_ = null;
                    this.options_ = other.options_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.optionsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getOptionsFieldBuilder() : null);
                }
                else {
                    this.optionsBuilder_.addAllMessages(other.options_);
                }
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
            EnumValue parsedMessage = null;
            try {
                parsedMessage = EnumValue.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (EnumValue)e.getUnfinishedMessage();
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
            this.name_ = EnumValue.getDefaultInstance().getName();
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
        public int getNumber() {
            return this.number_;
        }
        
        public Builder setNumber(final int value) {
            this.number_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearNumber() {
            this.number_ = 0;
            this.onChanged();
            return this;
        }
        
        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 0x1) == 0x0) {
                this.options_ = new ArrayList<Option>(this.options_);
                this.bitField0_ |= 0x1;
            }
        }
        
        @Override
        public List<Option> getOptionsList() {
            if (this.optionsBuilder_ == null) {
                return Collections.unmodifiableList((List<? extends Option>)this.options_);
            }
            return this.optionsBuilder_.getMessageList();
        }
        
        @Override
        public int getOptionsCount() {
            if (this.optionsBuilder_ == null) {
                return this.options_.size();
            }
            return this.optionsBuilder_.getCount();
        }
        
        @Override
        public Option getOptions(final int index) {
            if (this.optionsBuilder_ == null) {
                return this.options_.get(index);
            }
            return this.optionsBuilder_.getMessage(index);
        }
        
        public Builder setOptions(final int index, final Option value) {
            if (this.optionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOptionsIsMutable();
                this.options_.set(index, value);
                this.onChanged();
            }
            else {
                this.optionsBuilder_.setMessage(index, value);
            }
            return this;
        }
        
        public Builder setOptions(final int index, final Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                this.ensureOptionsIsMutable();
                this.options_.set(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.optionsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addOptions(final Option value) {
            if (this.optionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOptionsIsMutable();
                this.options_.add(value);
                this.onChanged();
            }
            else {
                this.optionsBuilder_.addMessage(value);
            }
            return this;
        }
        
        public Builder addOptions(final int index, final Option value) {
            if (this.optionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureOptionsIsMutable();
                this.options_.add(index, value);
                this.onChanged();
            }
            else {
                this.optionsBuilder_.addMessage(index, value);
            }
            return this;
        }
        
        public Builder addOptions(final Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                this.ensureOptionsIsMutable();
                this.options_.add(builderForValue.build());
                this.onChanged();
            }
            else {
                this.optionsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder addOptions(final int index, final Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                this.ensureOptionsIsMutable();
                this.options_.add(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.optionsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addAllOptions(final Iterable<? extends Option> values) {
            if (this.optionsBuilder_ == null) {
                this.ensureOptionsIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.options_);
                this.onChanged();
            }
            else {
                this.optionsBuilder_.addAllMessages(values);
            }
            return this;
        }
        
        public Builder clearOptions() {
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            }
            else {
                this.optionsBuilder_.clear();
            }
            return this;
        }
        
        public Builder removeOptions(final int index) {
            if (this.optionsBuilder_ == null) {
                this.ensureOptionsIsMutable();
                this.options_.remove(index);
                this.onChanged();
            }
            else {
                this.optionsBuilder_.remove(index);
            }
            return this;
        }
        
        public Option.Builder getOptionsBuilder(final int index) {
            return this.getOptionsFieldBuilder().getBuilder(index);
        }
        
        @Override
        public OptionOrBuilder getOptionsOrBuilder(final int index) {
            if (this.optionsBuilder_ == null) {
                return this.options_.get(index);
            }
            return this.optionsBuilder_.getMessageOrBuilder(index);
        }
        
        @Override
        public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
            if (this.optionsBuilder_ != null) {
                return this.optionsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList((List<? extends OptionOrBuilder>)this.options_);
        }
        
        public Option.Builder addOptionsBuilder() {
            return this.getOptionsFieldBuilder().addBuilder(Option.getDefaultInstance());
        }
        
        public Option.Builder addOptionsBuilder(final int index) {
            return this.getOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance());
        }
        
        public List<Option.Builder> getOptionsBuilderList() {
            return this.getOptionsFieldBuilder().getBuilderList();
        }
        
        private RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder> getOptionsFieldBuilder() {
            if (this.optionsBuilder_ == null) {
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder>(this.options_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
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
