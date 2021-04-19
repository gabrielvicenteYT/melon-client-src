package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class Api extends GeneratedMessageV3 implements ApiOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_;
    public static final int METHODS_FIELD_NUMBER = 2;
    private List<Method> methods_;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private List<Option> options_;
    public static final int VERSION_FIELD_NUMBER = 4;
    private volatile Object version_;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    private SourceContext sourceContext_;
    public static final int MIXINS_FIELD_NUMBER = 6;
    private List<Mixin> mixins_;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    private int syntax_;
    private byte memoizedIsInitialized;
    private static final Api DEFAULT_INSTANCE;
    private static final Parser<Api> PARSER;
    
    private Api(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Api() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.methods_ = Collections.emptyList();
        this.options_ = Collections.emptyList();
        this.version_ = "";
        this.mixins_ = Collections.emptyList();
        this.syntax_ = 0;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Api();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Api(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.methods_ = new ArrayList<Method>();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.methods_.add(input.readMessage(Method.parser(), extensionRegistry));
                        continue;
                    }
                    case 26: {
                        if ((mutable_bitField0_ & 0x2) == 0x0) {
                            this.options_ = new ArrayList<Option>();
                            mutable_bitField0_ |= 0x2;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        continue;
                    }
                    case 34: {
                        final String s = input.readStringRequireUtf8();
                        this.version_ = s;
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
                    case 50: {
                        if ((mutable_bitField0_ & 0x4) == 0x0) {
                            this.mixins_ = new ArrayList<Mixin>();
                            mutable_bitField0_ |= 0x4;
                        }
                        this.mixins_.add(input.readMessage(Mixin.parser(), extensionRegistry));
                        continue;
                    }
                    case 56: {
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
                this.methods_ = Collections.unmodifiableList((List<? extends Method>)this.methods_);
            }
            if ((mutable_bitField0_ & 0x2) != 0x0) {
                this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
            }
            if ((mutable_bitField0_ & 0x4) != 0x0) {
                this.mixins_ = Collections.unmodifiableList((List<? extends Mixin>)this.mixins_);
            }
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }
    
    public static final Descriptors.Descriptor getDescriptor() {
        return ApiProto.internal_static_google_protobuf_Api_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
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
    public List<Method> getMethodsList() {
        return this.methods_;
    }
    
    @Override
    public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
        return this.methods_;
    }
    
    @Override
    public int getMethodsCount() {
        return this.methods_.size();
    }
    
    @Override
    public Method getMethods(final int index) {
        return this.methods_.get(index);
    }
    
    @Override
    public MethodOrBuilder getMethodsOrBuilder(final int index) {
        return this.methods_.get(index);
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
    public String getVersion() {
        final Object ref = this.version_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.version_ = s);
    }
    
    @Override
    public ByteString getVersionBytes() {
        final Object ref = this.version_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.version_ = b);
        }
        return (ByteString)ref;
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
    public List<Mixin> getMixinsList() {
        return this.mixins_;
    }
    
    @Override
    public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
        return this.mixins_;
    }
    
    @Override
    public int getMixinsCount() {
        return this.mixins_.size();
    }
    
    @Override
    public Mixin getMixins(final int index) {
        return this.mixins_.get(index);
    }
    
    @Override
    public MixinOrBuilder getMixinsOrBuilder(final int index) {
        return this.mixins_.get(index);
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
        for (int i = 0; i < this.methods_.size(); ++i) {
            output.writeMessage(2, this.methods_.get(i));
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            output.writeMessage(3, this.options_.get(i));
        }
        if (!this.getVersionBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 4, this.version_);
        }
        if (this.sourceContext_ != null) {
            output.writeMessage(5, this.getSourceContext());
        }
        for (int i = 0; i < this.mixins_.size(); ++i) {
            output.writeMessage(6, this.mixins_.get(i));
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            output.writeEnum(7, this.syntax_);
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
        for (int i = 0; i < this.methods_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.methods_.get(i));
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.options_.get(i));
        }
        if (!this.getVersionBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(4, this.version_);
        }
        if (this.sourceContext_ != null) {
            size += CodedOutputStream.computeMessageSize(5, this.getSourceContext());
        }
        for (int i = 0; i < this.mixins_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(6, this.mixins_.get(i));
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            size += CodedOutputStream.computeEnumSize(7, this.syntax_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Api)) {
            return super.equals(obj);
        }
        final Api other = (Api)obj;
        return this.getName().equals(other.getName()) && this.getMethodsList().equals(other.getMethodsList()) && this.getOptionsList().equals(other.getOptionsList()) && this.getVersion().equals(other.getVersion()) && this.hasSourceContext() == other.hasSourceContext() && (!this.hasSourceContext() || this.getSourceContext().equals(other.getSourceContext())) && this.getMixinsList().equals(other.getMixinsList()) && this.syntax_ == other.syntax_ && this.unknownFields.equals(other.unknownFields);
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
        if (this.getMethodsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMethodsList().hashCode();
        }
        if (this.getOptionsCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getOptionsList().hashCode();
        }
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getVersion().hashCode();
        if (this.hasSourceContext()) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getSourceContext().hashCode();
        }
        if (this.getMixinsCount() > 0) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getMixinsList().hashCode();
        }
        hash = 37 * hash + 7;
        hash = 53 * hash + this.syntax_;
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Api parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data);
    }
    
    public static Api parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Api parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data);
    }
    
    public static Api parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Api parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data);
    }
    
    public static Api parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Api.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Api parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Api.PARSER, input);
    }
    
    public static Api parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Api.PARSER, input, extensionRegistry);
    }
    
    public static Api parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Api.PARSER, input);
    }
    
    public static Api parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Api.PARSER, input, extensionRegistry);
    }
    
    public static Api parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Api.PARSER, input);
    }
    
    public static Api parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Api.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Api.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Api prototype) {
        return Api.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Api.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Api getDefaultInstance() {
        return Api.DEFAULT_INSTANCE;
    }
    
    public static Parser<Api> parser() {
        return Api.PARSER;
    }
    
    @Override
    public Parser<Api> getParserForType() {
        return Api.PARSER;
    }
    
    @Override
    public Api getDefaultInstanceForType() {
        return Api.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Api();
        PARSER = new AbstractParser<Api>() {
            @Override
            public Api parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Api(input, extensionRegistry, null);
            }
        };
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ApiOrBuilder
    {
        private int bitField0_;
        private Object name_;
        private List<Method> methods_;
        private RepeatedFieldBuilderV3<Method, Method.Builder, MethodOrBuilder> methodsBuilder_;
        private List<Option> options_;
        private RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
        private Object version_;
        private SourceContext sourceContext_;
        private SingleFieldBuilderV3<SourceContext, SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
        private List<Mixin> mixins_;
        private RepeatedFieldBuilderV3<Mixin, Mixin.Builder, MixinOrBuilder> mixinsBuilder_;
        private int syntax_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return ApiProto.internal_static_google_protobuf_Api_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
        }
        
        private Builder() {
            this.name_ = "";
            this.methods_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.version_ = "";
            this.mixins_ = Collections.emptyList();
            this.syntax_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.methods_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.version_ = "";
            this.mixins_ = Collections.emptyList();
            this.syntax_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                this.getMethodsFieldBuilder();
                this.getOptionsFieldBuilder();
                this.getMixinsFieldBuilder();
            }
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.methodsBuilder_ == null) {
                this.methods_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            else {
                this.methodsBuilder_.clear();
            }
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
            }
            else {
                this.optionsBuilder_.clear();
            }
            this.version_ = "";
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
            }
            else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            if (this.mixinsBuilder_ == null) {
                this.mixins_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
            }
            else {
                this.mixinsBuilder_.clear();
            }
            this.syntax_ = 0;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ApiProto.internal_static_google_protobuf_Api_descriptor;
        }
        
        @Override
        public Api getDefaultInstanceForType() {
            return Api.getDefaultInstance();
        }
        
        @Override
        public Api build() {
            final Api result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Api buildPartial() {
            final Api result = new Api(this, null);
            final int from_bitField0_ = this.bitField0_;
            result.name_ = this.name_;
            if (this.methodsBuilder_ == null) {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    this.methods_ = Collections.unmodifiableList((List<? extends Method>)this.methods_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.methods_ = this.methods_;
            }
            else {
                result.methods_ = this.methodsBuilder_.build();
            }
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 0x2) != 0x0) {
                    this.options_ = Collections.unmodifiableList((List<? extends Option>)this.options_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.options_ = this.options_;
            }
            else {
                result.options_ = this.optionsBuilder_.build();
            }
            result.version_ = this.version_;
            if (this.sourceContextBuilder_ == null) {
                result.sourceContext_ = this.sourceContext_;
            }
            else {
                result.sourceContext_ = this.sourceContextBuilder_.build();
            }
            if (this.mixinsBuilder_ == null) {
                if ((this.bitField0_ & 0x4) != 0x0) {
                    this.mixins_ = Collections.unmodifiableList((List<? extends Mixin>)this.mixins_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.mixins_ = this.mixins_;
            }
            else {
                result.mixins_ = this.mixinsBuilder_.build();
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
            if (other instanceof Api) {
                return this.mergeFrom((Api)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Api other) {
            if (other == Api.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.onChanged();
            }
            if (this.methodsBuilder_ == null) {
                if (!other.methods_.isEmpty()) {
                    if (this.methods_.isEmpty()) {
                        this.methods_ = other.methods_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureMethodsIsMutable();
                        this.methods_.addAll(other.methods_);
                    }
                    this.onChanged();
                }
            }
            else if (!other.methods_.isEmpty()) {
                if (this.methodsBuilder_.isEmpty()) {
                    this.methodsBuilder_.dispose();
                    this.methodsBuilder_ = null;
                    this.methods_ = other.methods_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.methodsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getMethodsFieldBuilder() : null);
                }
                else {
                    this.methodsBuilder_.addAllMessages(other.methods_);
                }
            }
            if (this.optionsBuilder_ == null) {
                if (!other.options_.isEmpty()) {
                    if (this.options_.isEmpty()) {
                        this.options_ = other.options_;
                        this.bitField0_ &= 0xFFFFFFFD;
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
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.optionsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getOptionsFieldBuilder() : null);
                }
                else {
                    this.optionsBuilder_.addAllMessages(other.options_);
                }
            }
            if (!other.getVersion().isEmpty()) {
                this.version_ = other.version_;
                this.onChanged();
            }
            if (other.hasSourceContext()) {
                this.mergeSourceContext(other.getSourceContext());
            }
            if (this.mixinsBuilder_ == null) {
                if (!other.mixins_.isEmpty()) {
                    if (this.mixins_.isEmpty()) {
                        this.mixins_ = other.mixins_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    else {
                        this.ensureMixinsIsMutable();
                        this.mixins_.addAll(other.mixins_);
                    }
                    this.onChanged();
                }
            }
            else if (!other.mixins_.isEmpty()) {
                if (this.mixinsBuilder_.isEmpty()) {
                    this.mixinsBuilder_.dispose();
                    this.mixinsBuilder_ = null;
                    this.mixins_ = other.mixins_;
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.mixinsBuilder_ = (GeneratedMessageV3.alwaysUseFieldBuilders ? this.getMixinsFieldBuilder() : null);
                }
                else {
                    this.mixinsBuilder_.addAllMessages(other.mixins_);
                }
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
            Api parsedMessage = null;
            try {
                parsedMessage = Api.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Api)e.getUnfinishedMessage();
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
            this.name_ = Api.getDefaultInstance().getName();
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
        
        private void ensureMethodsIsMutable() {
            if ((this.bitField0_ & 0x1) == 0x0) {
                this.methods_ = new ArrayList<Method>(this.methods_);
                this.bitField0_ |= 0x1;
            }
        }
        
        @Override
        public List<Method> getMethodsList() {
            if (this.methodsBuilder_ == null) {
                return Collections.unmodifiableList((List<? extends Method>)this.methods_);
            }
            return this.methodsBuilder_.getMessageList();
        }
        
        @Override
        public int getMethodsCount() {
            if (this.methodsBuilder_ == null) {
                return this.methods_.size();
            }
            return this.methodsBuilder_.getCount();
        }
        
        @Override
        public Method getMethods(final int index) {
            if (this.methodsBuilder_ == null) {
                return this.methods_.get(index);
            }
            return this.methodsBuilder_.getMessage(index);
        }
        
        public Builder setMethods(final int index, final Method value) {
            if (this.methodsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodsIsMutable();
                this.methods_.set(index, value);
                this.onChanged();
            }
            else {
                this.methodsBuilder_.setMessage(index, value);
            }
            return this;
        }
        
        public Builder setMethods(final int index, final Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                this.ensureMethodsIsMutable();
                this.methods_.set(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.methodsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addMethods(final Method value) {
            if (this.methodsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodsIsMutable();
                this.methods_.add(value);
                this.onChanged();
            }
            else {
                this.methodsBuilder_.addMessage(value);
            }
            return this;
        }
        
        public Builder addMethods(final int index, final Method value) {
            if (this.methodsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodsIsMutable();
                this.methods_.add(index, value);
                this.onChanged();
            }
            else {
                this.methodsBuilder_.addMessage(index, value);
            }
            return this;
        }
        
        public Builder addMethods(final Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                this.ensureMethodsIsMutable();
                this.methods_.add(builderForValue.build());
                this.onChanged();
            }
            else {
                this.methodsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder addMethods(final int index, final Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                this.ensureMethodsIsMutable();
                this.methods_.add(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.methodsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addAllMethods(final Iterable<? extends Method> values) {
            if (this.methodsBuilder_ == null) {
                this.ensureMethodsIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.methods_);
                this.onChanged();
            }
            else {
                this.methodsBuilder_.addAllMessages(values);
            }
            return this;
        }
        
        public Builder clearMethods() {
            if (this.methodsBuilder_ == null) {
                this.methods_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            }
            else {
                this.methodsBuilder_.clear();
            }
            return this;
        }
        
        public Builder removeMethods(final int index) {
            if (this.methodsBuilder_ == null) {
                this.ensureMethodsIsMutable();
                this.methods_.remove(index);
                this.onChanged();
            }
            else {
                this.methodsBuilder_.remove(index);
            }
            return this;
        }
        
        public Method.Builder getMethodsBuilder(final int index) {
            return this.getMethodsFieldBuilder().getBuilder(index);
        }
        
        @Override
        public MethodOrBuilder getMethodsOrBuilder(final int index) {
            if (this.methodsBuilder_ == null) {
                return this.methods_.get(index);
            }
            return this.methodsBuilder_.getMessageOrBuilder(index);
        }
        
        @Override
        public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
            if (this.methodsBuilder_ != null) {
                return this.methodsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList((List<? extends MethodOrBuilder>)this.methods_);
        }
        
        public Method.Builder addMethodsBuilder() {
            return this.getMethodsFieldBuilder().addBuilder(Method.getDefaultInstance());
        }
        
        public Method.Builder addMethodsBuilder(final int index) {
            return this.getMethodsFieldBuilder().addBuilder(index, Method.getDefaultInstance());
        }
        
        public List<Method.Builder> getMethodsBuilderList() {
            return this.getMethodsFieldBuilder().getBuilderList();
        }
        
        private RepeatedFieldBuilderV3<Method, Method.Builder, MethodOrBuilder> getMethodsFieldBuilder() {
            if (this.methodsBuilder_ == null) {
                this.methodsBuilder_ = new RepeatedFieldBuilderV3<Method, Method.Builder, MethodOrBuilder>(this.methods_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                this.methods_ = null;
            }
            return this.methodsBuilder_;
        }
        
        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 0x2) == 0x0) {
                this.options_ = new ArrayList<Option>(this.options_);
                this.bitField0_ |= 0x2;
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
                this.bitField0_ &= 0xFFFFFFFD;
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
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder>(this.options_, (this.bitField0_ & 0x2) != 0x0, this.getParentForChildren(), this.isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
        }
        
        @Override
        public String getVersion() {
            final Object ref = this.version_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.version_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getVersionBytes() {
            final Object ref = this.version_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.version_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setVersion(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.version_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearVersion() {
            this.version_ = Api.getDefaultInstance().getVersion();
            this.onChanged();
            return this;
        }
        
        public Builder setVersionBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.version_ = value;
            this.onChanged();
            return this;
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
        
        private void ensureMixinsIsMutable() {
            if ((this.bitField0_ & 0x4) == 0x0) {
                this.mixins_ = new ArrayList<Mixin>(this.mixins_);
                this.bitField0_ |= 0x4;
            }
        }
        
        @Override
        public List<Mixin> getMixinsList() {
            if (this.mixinsBuilder_ == null) {
                return Collections.unmodifiableList((List<? extends Mixin>)this.mixins_);
            }
            return this.mixinsBuilder_.getMessageList();
        }
        
        @Override
        public int getMixinsCount() {
            if (this.mixinsBuilder_ == null) {
                return this.mixins_.size();
            }
            return this.mixinsBuilder_.getCount();
        }
        
        @Override
        public Mixin getMixins(final int index) {
            if (this.mixinsBuilder_ == null) {
                return this.mixins_.get(index);
            }
            return this.mixinsBuilder_.getMessage(index);
        }
        
        public Builder setMixins(final int index, final Mixin value) {
            if (this.mixinsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMixinsIsMutable();
                this.mixins_.set(index, value);
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.setMessage(index, value);
            }
            return this;
        }
        
        public Builder setMixins(final int index, final Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                this.ensureMixinsIsMutable();
                this.mixins_.set(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addMixins(final Mixin value) {
            if (this.mixinsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMixinsIsMutable();
                this.mixins_.add(value);
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.addMessage(value);
            }
            return this;
        }
        
        public Builder addMixins(final int index, final Mixin value) {
            if (this.mixinsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMixinsIsMutable();
                this.mixins_.add(index, value);
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.addMessage(index, value);
            }
            return this;
        }
        
        public Builder addMixins(final Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                this.ensureMixinsIsMutable();
                this.mixins_.add(builderForValue.build());
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }
        
        public Builder addMixins(final int index, final Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                this.ensureMixinsIsMutable();
                this.mixins_.add(index, builderForValue.build());
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }
        
        public Builder addAllMixins(final Iterable<? extends Mixin> values) {
            if (this.mixinsBuilder_ == null) {
                this.ensureMixinsIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.mixins_);
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.addAllMessages(values);
            }
            return this;
        }
        
        public Builder clearMixins() {
            if (this.mixinsBuilder_ == null) {
                this.mixins_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.clear();
            }
            return this;
        }
        
        public Builder removeMixins(final int index) {
            if (this.mixinsBuilder_ == null) {
                this.ensureMixinsIsMutable();
                this.mixins_.remove(index);
                this.onChanged();
            }
            else {
                this.mixinsBuilder_.remove(index);
            }
            return this;
        }
        
        public Mixin.Builder getMixinsBuilder(final int index) {
            return this.getMixinsFieldBuilder().getBuilder(index);
        }
        
        @Override
        public MixinOrBuilder getMixinsOrBuilder(final int index) {
            if (this.mixinsBuilder_ == null) {
                return this.mixins_.get(index);
            }
            return this.mixinsBuilder_.getMessageOrBuilder(index);
        }
        
        @Override
        public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
            if (this.mixinsBuilder_ != null) {
                return this.mixinsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList((List<? extends MixinOrBuilder>)this.mixins_);
        }
        
        public Mixin.Builder addMixinsBuilder() {
            return this.getMixinsFieldBuilder().addBuilder(Mixin.getDefaultInstance());
        }
        
        public Mixin.Builder addMixinsBuilder(final int index) {
            return this.getMixinsFieldBuilder().addBuilder(index, Mixin.getDefaultInstance());
        }
        
        public List<Mixin.Builder> getMixinsBuilderList() {
            return this.getMixinsFieldBuilder().getBuilderList();
        }
        
        private RepeatedFieldBuilderV3<Mixin, Mixin.Builder, MixinOrBuilder> getMixinsFieldBuilder() {
            if (this.mixinsBuilder_ == null) {
                this.mixinsBuilder_ = new RepeatedFieldBuilderV3<Mixin, Mixin.Builder, MixinOrBuilder>(this.mixins_, (this.bitField0_ & 0x4) != 0x0, this.getParentForChildren(), this.isClean());
                this.mixins_ = null;
            }
            return this.mixinsBuilder_;
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
