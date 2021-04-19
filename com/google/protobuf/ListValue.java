package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class ListValue extends GeneratedMessageV3 implements ListValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int VALUES_FIELD_NUMBER = 1;
    private List<Value> values_;
    private byte memoizedIsInitialized;
    private static final ListValue DEFAULT_INSTANCE;
    private static final Parser<ListValue> PARSER;
    
    private ListValue(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private ListValue() {
        this.memoizedIsInitialized = -1;
        this.values_ = Collections.emptyList();
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new ListValue();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private ListValue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.values_ = new ArrayList<Value>();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.values_.add(input.readMessage(Value.parser(), extensionRegistry));
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
                this.values_ = Collections.unmodifiableList((List<? extends Value>)this.values_);
            }
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return StructProto.internal_static_google_protobuf_ListValue_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
    }
    
    @Override
    public List<Value> getValuesList() {
        return this.values_;
    }
    
    @Override
    public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
        return this.values_;
    }
    
    @Override
    public int getValuesCount() {
        return this.values_.size();
    }
    
    @Override
    public Value getValues(final int index) {
        return this.values_.get(index);
    }
    
    @Override
    public ValueOrBuilder getValuesOrBuilder(final int index) {
        return this.values_.get(index);
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
        for (int i = 0; i < this.values_.size(); ++i) {
            output.writeMessage(1, this.values_.get(i));
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
        for (int i = 0; i < this.values_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.values_.get(i));
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListValue)) {
            return super.equals(obj);
        }
        final ListValue other = (ListValue)obj;
        return this.getValuesList().equals(other.getValuesList()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        if (this.getValuesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getValuesList().hashCode();
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static ListValue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data);
    }
    
    public static ListValue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static ListValue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data);
    }
    
    public static ListValue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static ListValue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data);
    }
    
    public static ListValue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return ListValue.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static ListValue parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(ListValue.PARSER, input);
    }
    
    public static ListValue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(ListValue.PARSER, input, extensionRegistry);
    }
    
    public static ListValue parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(ListValue.PARSER, input);
    }
    
    public static ListValue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(ListValue.PARSER, input, extensionRegistry);
    }
    
    public static ListValue parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(ListValue.PARSER, input);
    }
    
    public static ListValue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(ListValue.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return ListValue.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final ListValue prototype) {
        return ListValue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == ListValue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static ListValue getDefaultInstance() {
        return ListValue.DEFAULT_INSTANCE;
    }
    
    public static Parser<ListValue> parser() {
        return ListValue.PARSER;
    }
    
    @Override
    public Parser<ListValue> getParserForType() {
        return ListValue.PARSER;
    }
    
    @Override
    public ListValue getDefaultInstanceForType() {
        return ListValue.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new ListValue();
        PARSER = new AbstractParser<ListValue>() {
            @Override
            public ListValue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ListValue(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ListValueOrBuilder
    {
        private int bitField0_;
        private List<Value> values_;
        private RepeatedFieldBuilderV3<Value, Value.Builder, ValueOrBuilder> valuesBuilder_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_ListValue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
        }
        
        private Builder() {
            this.values_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.values_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                this.getValuesFieldBuilder();
            }
        }
        
        @Override
        public Builder clear() {
            super.clear();
            if (this.valuesBuilder_ == null) {
                this.values_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            else {
                this.valuesBuilder_.clear();
            }
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_ListValue_descriptor;
        }
        
        @Override
        public ListValue getDefaultInstanceForType() {
            return ListValue.getDefaultInstance();
        }
        
        @Override
        public ListValue build() {
            final ListValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public ListValue buildPartial() {
            final ListValue result = new ListValue(this, null);
            final int from_bitField0_ = this.bitField0_;
            if (this.valuesBuilder_ == null) {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    this.values_ = Collections.unmodifiableList((List<? extends Value>)this.values_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.values_ = this.values_;
            }
            else {
                result.values_ = this.valuesBuilder_.build();
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
            if (other instanceof ListValue) {
                return this.mergeFrom((ListValue)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final ListValue other) {
            if (other == ListValue.getDefaultInstance()) {
                return this;
            }
            if (this.valuesBuilder_ == null) {
                if (!other.values_.isEmpty()) {
                    if (this.values_.isEmpty()) {
                        this.values_ = other.values_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureValuesIsMutable();
                        this.values_.addAll(other.values_);
                    }
                    this.onChanged();
                }
            }
            else if (!other.values_.isEmpty()) {
                if (this.valuesBuilder_.isEmpty()) {
                    this.valuesBuilder_.dispose();
                    this.valuesBuilder_ = null;
                    this.values_ = other.values_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.valuesBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getValuesFieldBuilder() : null);
                }
                else {
                    this.valuesBuilder_.addAllMessages(other.values_);
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
            ListValue parsedMessage = null;
            try {
                parsedMessage = ListValue.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (ListValue)e.getUnfinishedMessage();
                throw e.unwrapIOException();
            }
            finally {
                if (parsedMessage != null) {
                    this.mergeFrom(parsedMessage);
                }
            }
            return this;
        }
        
        private void ensureValuesIsMutable() {
            if ((this.bitField0_ & 0x1) == 0x0) {
                this.values_ = new ArrayList<Value>(this.values_);
                this.bitField0_ |= 0x1;
            }
        }
        
        @Override
        public List<Value> getValuesList() {
            if (this.valuesBuilder_ == null) {
                return Collections.unmodifiableList((List<? extends Value>)this.values_);
            }
            return this.valuesBuilder_.getMessageList();
        }
        
        @Override
        public int getValuesCount() {
            if (this.valuesBuilder_ == null) {
                return this.values_.size();
            }
            return this.valuesBuilder_.getCount();
        }
        
        @Override
        public Value getValues(final int index) {
            if (this.valuesBuilder_ == null) {
                return this.values_.get(index);
            }
            return this.valuesBuilder_.getMessage(index);
        }
        
        public Builder setValues(final int index, final Value value) {
            if (this.valuesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureValuesIsMutable();
                this.values_.set(index, value);
                this.onChanged();
            }
            else {
                this.valuesBuilder_.setMessage(index, value);
            }
            return this;
        }
        
        public Builder setValues(final int index, final Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                this.ensureValuesIsMutable();
                this.values_.set(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.valuesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addValues(final Value value) {
            if (this.valuesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureValuesIsMutable();
                this.values_.add(value);
                this.onChanged();
            }
            else {
                this.valuesBuilder_.addMessage(value);
            }
            return this;
        }
        
        public Builder addValues(final int index, final Value value) {
            if (this.valuesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureValuesIsMutable();
                this.values_.add(index, value);
                this.onChanged();
            }
            else {
                this.valuesBuilder_.addMessage(index, value);
            }
            return this;
        }
        
        public Builder addValues(final Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                this.ensureValuesIsMutable();
                this.values_.add(builderForValue.build());
                this.onChanged();
            }
            else {
                this.valuesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder addValues(final int index, final Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                this.ensureValuesIsMutable();
                this.values_.add(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.valuesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addAllValues(final Iterable<? extends Value> values) {
            if (this.valuesBuilder_ == null) {
                this.ensureValuesIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.values_);
                this.onChanged();
            }
            else {
                this.valuesBuilder_.addAllMessages(values);
            }
            return this;
        }
        
        public Builder clearValues() {
            if (this.valuesBuilder_ == null) {
                this.values_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            }
            else {
                this.valuesBuilder_.clear();
            }
            return this;
        }
        
        public Builder removeValues(final int index) {
            if (this.valuesBuilder_ == null) {
                this.ensureValuesIsMutable();
                this.values_.remove(index);
                this.onChanged();
            }
            else {
                this.valuesBuilder_.remove(index);
            }
            return this;
        }
        
        public Value.Builder getValuesBuilder(final int index) {
            return this.getValuesFieldBuilder().getBuilder(index);
        }
        
        @Override
        public ValueOrBuilder getValuesOrBuilder(final int index) {
            if (this.valuesBuilder_ == null) {
                return this.values_.get(index);
            }
            return this.valuesBuilder_.getMessageOrBuilder(index);
        }
        
        @Override
        public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
            if (this.valuesBuilder_ != null) {
                return this.valuesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList((List<? extends ValueOrBuilder>)this.values_);
        }
        
        public Value.Builder addValuesBuilder() {
            return this.getValuesFieldBuilder().addBuilder(Value.getDefaultInstance());
        }
        
        public Value.Builder addValuesBuilder(final int index) {
            return this.getValuesFieldBuilder().addBuilder(index, Value.getDefaultInstance());
        }
        
        public List<Value.Builder> getValuesBuilderList() {
            return this.getValuesFieldBuilder().getBuilderList();
        }
        
        private RepeatedFieldBuilderV3<Value, Value.Builder, ValueOrBuilder> getValuesFieldBuilder() {
            if (this.valuesBuilder_ == null) {
                this.valuesBuilder_ = new RepeatedFieldBuilderV3<Value, Value.Builder, ValueOrBuilder>(this.values_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                this.values_ = null;
            }
            return this.valuesBuilder_;
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
