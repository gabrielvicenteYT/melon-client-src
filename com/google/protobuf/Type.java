package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class Type extends GeneratedMessageV3 implements TypeOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_;
    public static final int FIELDS_FIELD_NUMBER = 2;
    private List<Field> fields_;
    public static final int ONEOFS_FIELD_NUMBER = 3;
    private LazyStringList oneofs_;
    public static final int OPTIONS_FIELD_NUMBER = 4;
    private List<Option> options_;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    private SourceContext sourceContext_;
    public static final int SYNTAX_FIELD_NUMBER = 6;
    private int syntax_;
    private byte memoizedIsInitialized;
    private static final Type DEFAULT_INSTANCE;
    private static final Parser<Type> PARSER;
    
    private Type(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Type() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.fields_ = Collections.emptyList();
        this.oneofs_ = LazyStringArrayList.EMPTY;
        this.options_ = Collections.emptyList();
        this.syntax_ = 0;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Type();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Type(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 18: {
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.fields_ = new ArrayList<Field>();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.fields_.add(input.readMessage(Field.parser(), extensionRegistry));
                        continue;
                    }
                    case 26: {
                        final String s = input.readStringRequireUtf8();
                        if ((mutable_bitField0_ & 0x2) == 0x0) {
                            this.oneofs_ = new LazyStringArrayList();
                            mutable_bitField0_ |= 0x2;
                        }
                        this.oneofs_.add(s);
                        continue;
                    }
                    case 34: {
                        if ((mutable_bitField0_ & 0x4) == 0x0) {
                            this.options_ = new ArrayList<Option>();
                            mutable_bitField0_ |= 0x4;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        continue;
                    }
                    case 42: {
                        SourceContext.Builder subBuilder = null;
                        if (this.sourceContext_ != null) {
                            subBuilder = this.sourceContext_.toBuilder();
                        }
                        this.sourceContext_ = input.readMessage(SourceContext.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom(this.sourceContext_);
                            this.sourceContext_ = subBuilder.buildPartial();
                            continue;
                        }
                        continue;
                    }
                    case 48: {
                        final int rawValue = input.readEnum();
                        this.syntax_ = rawValue;
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
                this.fields_ = Collections.unmodifiableList((List<? extends Field>)this.fields_);
            }
            if ((mutable_bitField0_ & 0x2) != 0x0) {
                this.oneofs_ = this.oneofs_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 0x4) != 0x0) {
                this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
            }
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return TypeProto.internal_static_google_protobuf_Type_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Type_fieldAccessorTable.ensureFieldAccessorsInitialized(Type.class, Builder.class);
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
    public List<Field> getFieldsList() {
        return this.fields_;
    }
    
    @Override
    public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
        return this.fields_;
    }
    
    @Override
    public int getFieldsCount() {
        return this.fields_.size();
    }
    
    @Override
    public Field getFields(final int index) {
        return this.fields_.get(index);
    }
    
    @Override
    public FieldOrBuilder getFieldsOrBuilder(final int index) {
        return this.fields_.get(index);
    }
    
    @Override
    public ProtocolStringList getOneofsList() {
        return this.oneofs_;
    }
    
    @Override
    public int getOneofsCount() {
        return this.oneofs_.size();
    }
    
    @Override
    public String getOneofs(final int index) {
        return this.oneofs_.get(index);
    }
    
    @Override
    public ByteString getOneofsBytes(final int index) {
        return this.oneofs_.getByteString(index);
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
    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }
    
    @Override
    public SourceContext getSourceContext() {
        return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;
    }
    
    @Override
    public SourceContextOrBuilder getSourceContextOrBuilder() {
        return this.getSourceContext();
    }
    
    @Override
    public int getSyntaxValue() {
        return this.syntax_;
    }
    
    @Override
    public Syntax getSyntax() {
        final Syntax result = Syntax.valueOf(this.syntax_);
        return (result == null) ? Syntax.UNRECOGNIZED : result;
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
        for (int i = 0; i < this.fields_.size(); ++i) {
            output.writeMessage(2, this.fields_.get(i));
        }
        for (int i = 0; i < this.oneofs_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 3, this.oneofs_.getRaw(i));
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            output.writeMessage(4, this.options_.get(i));
        }
        if (this.sourceContext_ != null) {
            output.writeMessage(5, this.getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            output.writeEnum(6, this.syntax_);
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
        for (int i = 0; i < this.fields_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.fields_.get(i));
        }
        int dataSize = 0;
        for (int j = 0; j < this.oneofs_.size(); ++j) {
            dataSize += GeneratedMessageV3.computeStringSizeNoTag(this.oneofs_.getRaw(j));
        }
        size += dataSize;
        size += 1 * this.getOneofsList().size();
        for (int i = 0; i < this.options_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.options_.get(i));
        }
        if (this.sourceContext_ != null) {
            size += CodedOutputStream.computeMessageSize(5, this.getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            size += CodedOutputStream.computeEnumSize(6, this.syntax_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Type)) {
            return super.equals(obj);
        }
        final Type other = (Type)obj;
        return this.getName().equals(other.getName()) && this.getFieldsList().equals(other.getFieldsList()) && this.getOneofsList().equals(other.getOneofsList()) && this.getOptionsList().equals(other.getOptionsList()) && this.hasSourceContext() == other.hasSourceContext() && (!this.hasSourceContext() || this.getSourceContext().equals(other.getSourceContext())) && this.syntax_ == other.syntax_ && this.unknownFields.equals(other.unknownFields);
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
        if (this.getFieldsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getFieldsList().hashCode();
        }
        if (this.getOneofsCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getOneofsList().hashCode();
        }
        if (this.getOptionsCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getOptionsList().hashCode();
        }
        if (this.hasSourceContext()) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getSourceContext().hashCode();
        }
        hash = 37 * hash + 6;
        hash = 53 * hash + this.syntax_;
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Type parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data);
    }
    
    public static Type parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Type parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data);
    }
    
    public static Type parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Type parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data);
    }
    
    public static Type parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Type.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Type parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Type.PARSER, input);
    }
    
    public static Type parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Type.PARSER, input, extensionRegistry);
    }
    
    public static Type parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Type.PARSER, input);
    }
    
    public static Type parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Type.PARSER, input, extensionRegistry);
    }
    
    public static Type parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Type.PARSER, input);
    }
    
    public static Type parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Type.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Type.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Type prototype) {
        return Type.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Type.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Type getDefaultInstance() {
        return Type.DEFAULT_INSTANCE;
    }
    
    public static Parser<Type> parser() {
        return Type.PARSER;
    }
    
    @Override
    public Parser<Type> getParserForType() {
        return Type.PARSER;
    }
    
    @Override
    public Type getDefaultInstanceForType() {
        return Type.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Type();
        PARSER = new AbstractParser<Type>() {
            @Override
            public Type parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Type(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements TypeOrBuilder
    {
        private int bitField0_;
        private Object name_;
        private List<Field> fields_;
        private RepeatedFieldBuilderV3<Field, Field.Builder, FieldOrBuilder> fieldsBuilder_;
        private LazyStringList oneofs_;
        private List<Option> options_;
        private RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
        private SourceContext sourceContext_;
        private SingleFieldBuilderV3<SourceContext, SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
        private int syntax_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Type_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Type_fieldAccessorTable.ensureFieldAccessorsInitialized(Type.class, Builder.class);
        }
        
        private Builder() {
            this.name_ = "";
            this.fields_ = Collections.emptyList();
            this.oneofs_ = LazyStringArrayList.EMPTY;
            this.options_ = Collections.emptyList();
            this.syntax_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.fields_ = Collections.emptyList();
            this.oneofs_ = LazyStringArrayList.EMPTY;
            this.options_ = Collections.emptyList();
            this.syntax_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                this.getFieldsFieldBuilder();
                this.getOptionsFieldBuilder();
            }
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.fieldsBuilder_ == null) {
                this.fields_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            else {
                this.fieldsBuilder_.clear();
            }
            this.oneofs_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= 0xFFFFFFFD;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
            }
            else {
                this.optionsBuilder_.clear();
            }
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
            }
            else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            this.syntax_ = 0;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Type_descriptor;
        }
        
        @Override
        public Type getDefaultInstanceForType() {
            return Type.getDefaultInstance();
        }
        
        @Override
        public Type build() {
            final Type result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Type buildPartial() {
            final Type result = new Type(this, null);
            final int from_bitField0_ = this.bitField0_;
            result.name_ = this.name_;
            if (this.fieldsBuilder_ == null) {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    this.fields_ = Collections.unmodifiableList((List<? extends Field>)this.fields_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.fields_ = this.fields_;
            }
            else {
                result.fields_ = this.fieldsBuilder_.build();
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                this.oneofs_ = this.oneofs_.getUnmodifiableView();
                this.bitField0_ &= 0xFFFFFFFD;
            }
            result.oneofs_ = this.oneofs_;
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 0x4) != 0x0) {
                    this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.options_ = this.options_;
            }
            else {
                result.options_ = this.optionsBuilder_.build();
            }
            if (this.sourceContextBuilder_ == null) {
                result.sourceContext_ = this.sourceContext_;
            }
            else {
                result.sourceContext_ = this.sourceContextBuilder_.build();
            }
            result.syntax_ = this.syntax_;
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
            if (other instanceof Type) {
                return this.mergeFrom((Type)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Type other) {
            if (other == Type.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.onChanged();
            }
            if (this.fieldsBuilder_ == null) {
                if (!other.fields_.isEmpty()) {
                    if (this.fields_.isEmpty()) {
                        this.fields_ = other.fields_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureFieldsIsMutable();
                        this.fields_.addAll(other.fields_);
                    }
                    this.onChanged();
                }
            }
            else if (!other.fields_.isEmpty()) {
                if (this.fieldsBuilder_.isEmpty()) {
                    this.fieldsBuilder_.dispose();
                    this.fieldsBuilder_ = null;
                    this.fields_ = other.fields_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.fieldsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getFieldsFieldBuilder() : null);
                }
                else {
                    this.fieldsBuilder_.addAllMessages(other.fields_);
                }
            }
            if (!other.oneofs_.isEmpty()) {
                if (this.oneofs_.isEmpty()) {
                    this.oneofs_ = other.oneofs_;
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.ensureOneofsIsMutable();
                    this.oneofs_.addAll(other.oneofs_);
                }
                this.onChanged();
            }
            if (this.optionsBuilder_ == null) {
                if (!other.options_.isEmpty()) {
                    if (this.options_.isEmpty()) {
                        this.options_ = other.options_;
                        this.bitField0_ &= 0xFFFFFFFB;
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
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.optionsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getOptionsFieldBuilder() : null);
                }
                else {
                    this.optionsBuilder_.addAllMessages(other.options_);
                }
            }
            if (other.hasSourceContext()) {
                this.mergeSourceContext(other.getSourceContext());
            }
            if (other.syntax_ != 0) {
                this.setSyntaxValue(other.getSyntaxValue());
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
            Type parsedMessage = null;
            try {
                parsedMessage = Type.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Type)e.getUnfinishedMessage();
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
            this.name_ = Type.getDefaultInstance().getName();
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
        
        private void ensureFieldsIsMutable() {
            if ((this.bitField0_ & 0x1) == 0x0) {
                this.fields_ = new ArrayList<Field>(this.fields_);
                this.bitField0_ |= 0x1;
            }
        }
        
        @Override
        public List<Field> getFieldsList() {
            if (this.fieldsBuilder_ == null) {
                return Collections.unmodifiableList((List<? extends Field>)this.fields_);
            }
            return this.fieldsBuilder_.getMessageList();
        }
        
        @Override
        public int getFieldsCount() {
            if (this.fieldsBuilder_ == null) {
                return this.fields_.size();
            }
            return this.fieldsBuilder_.getCount();
        }
        
        @Override
        public Field getFields(final int index) {
            if (this.fieldsBuilder_ == null) {
                return this.fields_.get(index);
            }
            return this.fieldsBuilder_.getMessage(index);
        }
        
        public Builder setFields(final int index, final Field value) {
            if (this.fieldsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldsIsMutable();
                this.fields_.set(index, value);
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.setMessage(index, value);
            }
            return this;
        }
        
        public Builder setFields(final int index, final Field.Builder builderForValue) {
            if (this.fieldsBuilder_ == null) {
                this.ensureFieldsIsMutable();
                this.fields_.set(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addFields(final Field value) {
            if (this.fieldsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldsIsMutable();
                this.fields_.add(value);
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.addMessage(value);
            }
            return this;
        }
        
        public Builder addFields(final int index, final Field value) {
            if (this.fieldsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldsIsMutable();
                this.fields_.add(index, value);
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.addMessage(index, value);
            }
            return this;
        }
        
        public Builder addFields(final Field.Builder builderForValue) {
            if (this.fieldsBuilder_ == null) {
                this.ensureFieldsIsMutable();
                this.fields_.add(builderForValue.build());
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder addFields(final int index, final Field.Builder builderForValue) {
            if (this.fieldsBuilder_ == null) {
                this.ensureFieldsIsMutable();
                this.fields_.add(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addAllFields(final Iterable<? extends Field> values) {
            if (this.fieldsBuilder_ == null) {
                this.ensureFieldsIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.fields_);
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.addAllMessages(values);
            }
            return this;
        }
        
        public Builder clearFields() {
            if (this.fieldsBuilder_ == null) {
                this.fields_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.clear();
            }
            return this;
        }
        
        public Builder removeFields(final int index) {
            if (this.fieldsBuilder_ == null) {
                this.ensureFieldsIsMutable();
                this.fields_.remove(index);
                this.onChanged();
            }
            else {
                this.fieldsBuilder_.remove(index);
            }
            return this;
        }
        
        public Field.Builder getFieldsBuilder(final int index) {
            return this.getFieldsFieldBuilder().getBuilder(index);
        }
        
        @Override
        public FieldOrBuilder getFieldsOrBuilder(final int index) {
            if (this.fieldsBuilder_ == null) {
                return this.fields_.get(index);
            }
            return this.fieldsBuilder_.getMessageOrBuilder(index);
        }
        
        @Override
        public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
            if (this.fieldsBuilder_ != null) {
                return this.fieldsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList((List<? extends FieldOrBuilder>)this.fields_);
        }
        
        public Field.Builder addFieldsBuilder() {
            return this.getFieldsFieldBuilder().addBuilder(Field.getDefaultInstance());
        }
        
        public Field.Builder addFieldsBuilder(final int index) {
            return this.getFieldsFieldBuilder().addBuilder(index, Field.getDefaultInstance());
        }
        
        public List<Field.Builder> getFieldsBuilderList() {
            return this.getFieldsFieldBuilder().getBuilderList();
        }
        
        private RepeatedFieldBuilderV3<Field, Field.Builder, FieldOrBuilder> getFieldsFieldBuilder() {
            if (this.fieldsBuilder_ == null) {
                this.fieldsBuilder_ = new RepeatedFieldBuilderV3<Field, Field.Builder, FieldOrBuilder>(this.fields_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                this.fields_ = null;
            }
            return this.fieldsBuilder_;
        }
        
        private void ensureOneofsIsMutable() {
            if ((this.bitField0_ & 0x2) == 0x0) {
                this.oneofs_ = new LazyStringArrayList(this.oneofs_);
                this.bitField0_ |= 0x2;
            }
        }
        
        @Override
        public ProtocolStringList getOneofsList() {
            return this.oneofs_.getUnmodifiableView();
        }
        
        @Override
        public int getOneofsCount() {
            return this.oneofs_.size();
        }
        
        @Override
        public String getOneofs(final int index) {
            return this.oneofs_.get(index);
        }
        
        @Override
        public ByteString getOneofsBytes(final int index) {
            return this.oneofs_.getByteString(index);
        }
        
        public Builder setOneofs(final int index, final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureOneofsIsMutable();
            this.oneofs_.set(index, value);
            this.onChanged();
            return this;
        }
        
        public Builder addOneofs(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureOneofsIsMutable();
            this.oneofs_.add(value);
            this.onChanged();
            return this;
        }
        
        public Builder addAllOneofs(final Iterable<String> values) {
            this.ensureOneofsIsMutable();
            AbstractMessageLite.Builder.addAll(values, this.oneofs_);
            this.onChanged();
            return this;
        }
        
        public Builder clearOneofs() {
            this.oneofs_ = LazyStringArrayList.EMPTY;
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }
        
        public Builder addOneofsBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.ensureOneofsIsMutable();
            this.oneofs_.add(value);
            this.onChanged();
            return this;
        }
        
        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 0x4) == 0x0) {
                this.options_ = new ArrayList<Option>(this.options_);
                this.bitField0_ |= 0x4;
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
                this.bitField0_ &= 0xFFFFFFFB;
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
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder>(this.options_, (this.bitField0_ & 0x4) != 0x0, this.getParentForChildren(), this.isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
        }
        
        @Override
        public boolean hasSourceContext() {
            return this.sourceContextBuilder_ != null || this.sourceContext_ != null;
        }
        
        @Override
        public SourceContext getSourceContext() {
            if (this.sourceContextBuilder_ == null) {
                return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;
            }
            return this.sourceContextBuilder_.getMessage();
        }
        
        public Builder setSourceContext(final SourceContext value) {
            if (this.sourceContextBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.sourceContext_ = value;
                this.onChanged();
            }
            else {
                this.sourceContextBuilder_.setMessage(value);
            }
            return this;
        }
        
        public Builder setSourceContext(final SourceContext.Builder builderForValue) {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = builderForValue.build();
                this.onChanged();
            }
            else {
                this.sourceContextBuilder_.setMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder mergeSourceContext(final SourceContext value) {
            if (this.sourceContextBuilder_ == null) {
                if (this.sourceContext_ != null) {
                    this.sourceContext_ = SourceContext.newBuilder(this.sourceContext_).mergeFrom(value).buildPartial();
                }
                else {
                    this.sourceContext_ = value;
                }
                this.onChanged();
            }
            else {
                this.sourceContextBuilder_.mergeFrom(value);
            }
            return this;
        }
        
        public Builder clearSourceContext() {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
                this.onChanged();
            }
            else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            return this;
        }
        
        public SourceContext.Builder getSourceContextBuilder() {
            this.onChanged();
            return this.getSourceContextFieldBuilder().getBuilder();
        }
        
        @Override
        public SourceContextOrBuilder getSourceContextOrBuilder() {
            if (this.sourceContextBuilder_ != null) {
                return this.sourceContextBuilder_.getMessageOrBuilder();
            }
            return (this.sourceContext_ == null) ? SourceContext.getDefaultInstance() : this.sourceContext_;
        }
        
        private SingleFieldBuilderV3<SourceContext, SourceContext.Builder, SourceContextOrBuilder> getSourceContextFieldBuilder() {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContextBuilder_ = new SingleFieldBuilderV3<SourceContext, SourceContext.Builder, SourceContextOrBuilder>(this.getSourceContext(), this.getParentForChildren(), this.isClean());
                this.sourceContext_ = null;
            }
            return this.sourceContextBuilder_;
        }
        
        @Override
        public int getSyntaxValue() {
            return this.syntax_;
        }
        
        public Builder setSyntaxValue(final int value) {
            this.syntax_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public Syntax getSyntax() {
            final Syntax result = Syntax.valueOf(this.syntax_);
            return (result == null) ? Syntax.UNRECOGNIZED : result;
        }
        
        public Builder setSyntax(final Syntax value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.syntax_ = value.getNumber();
            this.onChanged();
            return this;
        }
        
        public Builder clearSyntax() {
            this.syntax_ = 0;
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
