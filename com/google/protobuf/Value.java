package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class Value extends GeneratedMessageV3 implements ValueOrBuilder
{
    private static final long serialVersionUID = 0L;
    private int kindCase_;
    private Object kind_;
    public static final int NULL_VALUE_FIELD_NUMBER = 1;
    public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
    public static final int STRING_VALUE_FIELD_NUMBER = 3;
    public static final int BOOL_VALUE_FIELD_NUMBER = 4;
    public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
    public static final int LIST_VALUE_FIELD_NUMBER = 6;
    private byte memoizedIsInitialized;
    private static final Value DEFAULT_INSTANCE;
    private static final Parser<Value> PARSER;
    
    private Value(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.kindCase_ = 0;
        this.memoizedIsInitialized = -1;
    }
    
    private Value() {
        this.kindCase_ = 0;
        this.memoizedIsInitialized = -1;
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Value();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Value(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        final int rawValue = input.readEnum();
                        this.kindCase_ = 1;
                        this.kind_ = rawValue;
                        continue;
                    }
                    case 17: {
                        this.kindCase_ = 2;
                        this.kind_ = input.readDouble();
                        continue;
                    }
                    case 26: {
                        final String s = input.readStringRequireUtf8();
                        this.kindCase_ = 3;
                        this.kind_ = s;
                        continue;
                    }
                    case 32: {
                        this.kindCase_ = 4;
                        this.kind_ = input.readBool();
                        continue;
                    }
                    case 42: {
                        Struct.Builder subBuilder = null;
                        if (this.kindCase_ == 5) {
                            subBuilder = ((Struct)this.kind_).toBuilder();
                        }
                        this.kind_ = input.readMessage(Struct.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom((Struct)this.kind_);
                            this.kind_ = subBuilder.buildPartial();
                        }
                        this.kindCase_ = 5;
                        continue;
                    }
                    case 50: {
                        ListValue.Builder subBuilder2 = null;
                        if (this.kindCase_ == 6) {
                            subBuilder2 = ((ListValue)this.kind_).toBuilder();
                        }
                        this.kind_ = input.readMessage(ListValue.parser(), extensionRegistry);
                        if (subBuilder2 != null) {
                            subBuilder2.mergeFrom((ListValue)this.kind_);
                            this.kind_ = subBuilder2.buildPartial();
                        }
                        this.kindCase_ = 6;
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
        return StructProto.internal_static_google_protobuf_Value_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
    }
    
    @Override
    public KindCase getKindCase() {
        return KindCase.forNumber(this.kindCase_);
    }
    
    @Override
    public int getNullValueValue() {
        if (this.kindCase_ == 1) {
            return (int)this.kind_;
        }
        return 0;
    }
    
    @Override
    public NullValue getNullValue() {
        if (this.kindCase_ == 1) {
            final NullValue result = NullValue.valueOf((int)this.kind_);
            return (result == null) ? NullValue.UNRECOGNIZED : result;
        }
        return NullValue.NULL_VALUE;
    }
    
    @Override
    public double getNumberValue() {
        if (this.kindCase_ == 2) {
            return (double)this.kind_;
        }
        return 0.0;
    }
    
    @Override
    public String getStringValue() {
        Object ref = "";
        if (this.kindCase_ == 3) {
            ref = this.kind_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        if (this.kindCase_ == 3) {
            this.kind_ = s;
        }
        return s;
    }
    
    @Override
    public ByteString getStringValueBytes() {
        Object ref = "";
        if (this.kindCase_ == 3) {
            ref = this.kind_;
        }
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.kindCase_ == 3) {
                this.kind_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }
    
    @Override
    public boolean getBoolValue() {
        return this.kindCase_ == 4 && (boolean)this.kind_;
    }
    
    @Override
    public boolean hasStructValue() {
        return this.kindCase_ == 5;
    }
    
    @Override
    public Struct getStructValue() {
        if (this.kindCase_ == 5) {
            return (Struct)this.kind_;
        }
        return Struct.getDefaultInstance();
    }
    
    @Override
    public StructOrBuilder getStructValueOrBuilder() {
        if (this.kindCase_ == 5) {
            return (Struct)this.kind_;
        }
        return Struct.getDefaultInstance();
    }
    
    @Override
    public boolean hasListValue() {
        return this.kindCase_ == 6;
    }
    
    @Override
    public ListValue getListValue() {
        if (this.kindCase_ == 6) {
            return (ListValue)this.kind_;
        }
        return ListValue.getDefaultInstance();
    }
    
    @Override
    public ListValueOrBuilder getListValueOrBuilder() {
        if (this.kindCase_ == 6) {
            return (ListValue)this.kind_;
        }
        return ListValue.getDefaultInstance();
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
        if (this.kindCase_ == 1) {
            output.writeEnum(1, (int)this.kind_);
        }
        if (this.kindCase_ == 2) {
            output.writeDouble(2, (double)this.kind_);
        }
        if (this.kindCase_ == 3) {
            GeneratedMessageV3.writeString(output, 3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            output.writeBool(4, (boolean)this.kind_);
        }
        if (this.kindCase_ == 5) {
            output.writeMessage(5, (MessageLite)this.kind_);
        }
        if (this.kindCase_ == 6) {
            output.writeMessage(6, (MessageLite)this.kind_);
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
        if (this.kindCase_ == 1) {
            size += CodedOutputStream.computeEnumSize(1, (int)this.kind_);
        }
        if (this.kindCase_ == 2) {
            size += CodedOutputStream.computeDoubleSize(2, (double)this.kind_);
        }
        if (this.kindCase_ == 3) {
            size += GeneratedMessageV3.computeStringSize(3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            size += CodedOutputStream.computeBoolSize(4, (boolean)this.kind_);
        }
        if (this.kindCase_ == 5) {
            size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.kind_);
        }
        if (this.kindCase_ == 6) {
            size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.kind_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Value)) {
            return super.equals(obj);
        }
        final Value other = (Value)obj;
        if (!this.getKindCase().equals(other.getKindCase())) {
            return false;
        }
        switch (this.kindCase_) {
            case 1: {
                if (this.getNullValueValue() != other.getNullValueValue()) {
                    return false;
                }
                break;
            }
            case 2: {
                if (Double.doubleToLongBits(this.getNumberValue()) != Double.doubleToLongBits(other.getNumberValue())) {
                    return false;
                }
                break;
            }
            case 3: {
                if (!this.getStringValue().equals(other.getStringValue())) {
                    return false;
                }
                break;
            }
            case 4: {
                if (this.getBoolValue() != other.getBoolValue()) {
                    return false;
                }
                break;
            }
            case 5: {
                if (!this.getStructValue().equals(other.getStructValue())) {
                    return false;
                }
                break;
            }
            case 6: {
                if (!this.getListValue().equals(other.getListValue())) {
                    return false;
                }
                break;
            }
        }
        return this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        switch (this.kindCase_) {
            case 1: {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getNullValueValue();
                break;
            }
            case 2: {
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getNumberValue()));
                break;
            }
            case 3: {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getStringValue().hashCode();
                break;
            }
            case 4: {
                hash = 37 * hash + 4;
                hash = 53 * hash + Internal.hashBoolean(this.getBoolValue());
                break;
            }
            case 5: {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getStructValue().hashCode();
                break;
            }
            case 6: {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getListValue().hashCode();
                break;
            }
        }
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Value parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data);
    }
    
    public static Value parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Value parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data);
    }
    
    public static Value parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Value parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data);
    }
    
    public static Value parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Value.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Value parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Value.PARSER, input);
    }
    
    public static Value parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Value.PARSER, input, extensionRegistry);
    }
    
    public static Value parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Value.PARSER, input);
    }
    
    public static Value parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Value.PARSER, input, extensionRegistry);
    }
    
    public static Value parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Value.PARSER, input);
    }
    
    public static Value parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Value.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Value.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Value prototype) {
        return Value.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Value.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Value getDefaultInstance() {
        return Value.DEFAULT_INSTANCE;
    }
    
    public static Parser<Value> parser() {
        return Value.PARSER;
    }
    
    @Override
    public Parser<Value> getParserForType() {
        return Value.PARSER;
    }
    
    @Override
    public Value getDefaultInstanceForType() {
        return Value.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Value();
        PARSER = new AbstractParser<Value>() {
            @Override
            public Value parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Value(input, extensionRegistry, null);
            }
        };
    }
    
    public enum KindCase implements Internal.EnumLite, InternalOneOfEnum
    {
        NULL_VALUE(1), 
        NUMBER_VALUE(2), 
        STRING_VALUE(3), 
        BOOL_VALUE(4), 
        STRUCT_VALUE(5), 
        LIST_VALUE(6), 
        KIND_NOT_SET(0);
        
        private final int value;
        
        private KindCase(final int value) {
            this.value = value;
        }
        
        @Deprecated
        public static KindCase valueOf(final int value) {
            return forNumber(value);
        }
        
        public static KindCase forNumber(final int value) {
            switch (value) {
                case 1: {
                    return KindCase.NULL_VALUE;
                }
                case 2: {
                    return KindCase.NUMBER_VALUE;
                }
                case 3: {
                    return KindCase.STRING_VALUE;
                }
                case 4: {
                    return KindCase.BOOL_VALUE;
                }
                case 5: {
                    return KindCase.STRUCT_VALUE;
                }
                case 6: {
                    return KindCase.LIST_VALUE;
                }
                case 0: {
                    return KindCase.KIND_NOT_SET;
                }
                default: {
                    return null;
                }
            }
        }
        
        @Override
        public int getNumber() {
            return this.value;
        }
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ValueOrBuilder
    {
        private int kindCase_;
        private Object kind_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> structValueBuilder_;
        private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> listValueBuilder_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_Value_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
        }
        
        private Builder() {
            this.kindCase_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.kindCase_ = 0;
            this.maybeForceBuilderInitialization();
        }
        
        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {}
        }
        
        @Override
        public Builder clear() {
            super.clear();
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_Value_descriptor;
        }
        
        @Override
        public Value getDefaultInstanceForType() {
            return Value.getDefaultInstance();
        }
        
        @Override
        public Value build() {
            final Value result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Value buildPartial() {
            final Value result = new Value(this, null);
            if (this.kindCase_ == 1) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 2) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 3) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 4) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 5) {
                if (this.structValueBuilder_ == null) {
                    result.kind_ = this.kind_;
                }
                else {
                    result.kind_ = this.structValueBuilder_.build();
                }
            }
            if (this.kindCase_ == 6) {
                if (this.listValueBuilder_ == null) {
                    result.kind_ = this.kind_;
                }
                else {
                    result.kind_ = this.listValueBuilder_.build();
                }
            }
            result.kindCase_ = this.kindCase_;
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
            if (other instanceof Value) {
                return this.mergeFrom((Value)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Value other) {
            if (other == Value.getDefaultInstance()) {
                return this;
            }
            switch (other.getKindCase()) {
                case NULL_VALUE: {
                    this.setNullValueValue(other.getNullValueValue());
                    break;
                }
                case NUMBER_VALUE: {
                    this.setNumberValue(other.getNumberValue());
                    break;
                }
                case STRING_VALUE: {
                    this.kindCase_ = 3;
                    this.kind_ = other.kind_;
                    this.onChanged();
                    break;
                }
                case BOOL_VALUE: {
                    this.setBoolValue(other.getBoolValue());
                    break;
                }
                case STRUCT_VALUE: {
                    this.mergeStructValue(other.getStructValue());
                    break;
                }
                case LIST_VALUE: {
                    this.mergeListValue(other.getListValue());
                    break;
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
            Value parsedMessage = null;
            try {
                parsedMessage = Value.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Value)e.getUnfinishedMessage();
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
        public KindCase getKindCase() {
            return KindCase.forNumber(this.kindCase_);
        }
        
        public Builder clearKind() {
            this.kindCase_ = 0;
            this.kind_ = null;
            this.onChanged();
            return this;
        }
        
        @Override
        public int getNullValueValue() {
            if (this.kindCase_ == 1) {
                return (int)this.kind_;
            }
            return 0;
        }
        
        public Builder setNullValueValue(final int value) {
            this.kindCase_ = 1;
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public NullValue getNullValue() {
            if (this.kindCase_ == 1) {
                final NullValue result = NullValue.valueOf((int)this.kind_);
                return (result == null) ? NullValue.UNRECOGNIZED : result;
            }
            return NullValue.NULL_VALUE;
        }
        
        public Builder setNullValue(final NullValue value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kindCase_ = 1;
            this.kind_ = value.getNumber();
            this.onChanged();
            return this;
        }
        
        public Builder clearNullValue() {
            if (this.kindCase_ == 1) {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
            }
            return this;
        }
        
        @Override
        public double getNumberValue() {
            if (this.kindCase_ == 2) {
                return (double)this.kind_;
            }
            return 0.0;
        }
        
        public Builder setNumberValue(final double value) {
            this.kindCase_ = 2;
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearNumberValue() {
            if (this.kindCase_ == 2) {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
            }
            return this;
        }
        
        @Override
        public String getStringValue() {
            Object ref = "";
            if (this.kindCase_ == 3) {
                ref = this.kind_;
            }
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                if (this.kindCase_ == 3) {
                    this.kind_ = s;
                }
                return s;
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getStringValueBytes() {
            Object ref = "";
            if (this.kindCase_ == 3) {
                ref = this.kind_;
            }
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.kindCase_ == 3) {
                    this.kind_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }
        
        public Builder setStringValue(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kindCase_ = 3;
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearStringValue() {
            if (this.kindCase_ == 3) {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
            }
            return this;
        }
        
        public Builder setStringValueBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.kindCase_ = 3;
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public boolean getBoolValue() {
            return this.kindCase_ == 4 && (boolean)this.kind_;
        }
        
        public Builder setBoolValue(final boolean value) {
            this.kindCase_ = 4;
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearBoolValue() {
            if (this.kindCase_ == 4) {
                this.kindCase_ = 0;
                this.kind_ = null;
                this.onChanged();
            }
            return this;
        }
        
        @Override
        public boolean hasStructValue() {
            return this.kindCase_ == 5;
        }
        
        @Override
        public Struct getStructValue() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ == 5) {
                    return (Struct)this.kind_;
                }
                return Struct.getDefaultInstance();
            }
            else {
                if (this.kindCase_ == 5) {
                    return this.structValueBuilder_.getMessage();
                }
                return Struct.getDefaultInstance();
            }
        }
        
        public Builder setStructValue(final Struct value) {
            if (this.structValueBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.kind_ = value;
                this.onChanged();
            }
            else {
                this.structValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 5;
            return this;
        }
        
        public Builder setStructValue(final Struct.Builder builderForValue) {
            if (this.structValueBuilder_ == null) {
                this.kind_ = builderForValue.build();
                this.onChanged();
            }
            else {
                this.structValueBuilder_.setMessage(builderForValue.build());
            }
            this.kindCase_ = 5;
            return this;
        }
        
        public Builder mergeStructValue(final Struct value) {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ == 5 && this.kind_ != Struct.getDefaultInstance()) {
                    this.kind_ = Struct.newBuilder((Struct)this.kind_).mergeFrom(value).buildPartial();
                }
                else {
                    this.kind_ = value;
                }
                this.onChanged();
            }
            else {
                if (this.kindCase_ == 5) {
                    this.structValueBuilder_.mergeFrom(value);
                }
                this.structValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 5;
            return this;
        }
        
        public Builder clearStructValue() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ == 5) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
            }
            else {
                if (this.kindCase_ == 5) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                this.structValueBuilder_.clear();
            }
            return this;
        }
        
        public Struct.Builder getStructValueBuilder() {
            return this.getStructValueFieldBuilder().getBuilder();
        }
        
        @Override
        public StructOrBuilder getStructValueOrBuilder() {
            if (this.kindCase_ == 5 && this.structValueBuilder_ != null) {
                return this.structValueBuilder_.getMessageOrBuilder();
            }
            if (this.kindCase_ == 5) {
                return (Struct)this.kind_;
            }
            return Struct.getDefaultInstance();
        }
        
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getStructValueFieldBuilder() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ != 5) {
                    this.kind_ = Struct.getDefaultInstance();
                }
                this.structValueBuilder_ = new SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder>((Struct)this.kind_, this.getParentForChildren(), this.isClean());
                this.kind_ = null;
            }
            this.kindCase_ = 5;
            this.onChanged();
            return this.structValueBuilder_;
        }
        
        @Override
        public boolean hasListValue() {
            return this.kindCase_ == 6;
        }
        
        @Override
        public ListValue getListValue() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ == 6) {
                    return (ListValue)this.kind_;
                }
                return ListValue.getDefaultInstance();
            }
            else {
                if (this.kindCase_ == 6) {
                    return this.listValueBuilder_.getMessage();
                }
                return ListValue.getDefaultInstance();
            }
        }
        
        public Builder setListValue(final ListValue value) {
            if (this.listValueBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.kind_ = value;
                this.onChanged();
            }
            else {
                this.listValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 6;
            return this;
        }
        
        public Builder setListValue(final ListValue.Builder builderForValue) {
            if (this.listValueBuilder_ == null) {
                this.kind_ = builderForValue.build();
                this.onChanged();
            }
            else {
                this.listValueBuilder_.setMessage(builderForValue.build());
            }
            this.kindCase_ = 6;
            return this;
        }
        
        public Builder mergeListValue(final ListValue value) {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ == 6 && this.kind_ != ListValue.getDefaultInstance()) {
                    this.kind_ = ListValue.newBuilder((ListValue)this.kind_).mergeFrom(value).buildPartial();
                }
                else {
                    this.kind_ = value;
                }
                this.onChanged();
            }
            else {
                if (this.kindCase_ == 6) {
                    this.listValueBuilder_.mergeFrom(value);
                }
                this.listValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 6;
            return this;
        }
        
        public Builder clearListValue() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ == 6) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                    this.onChanged();
                }
            }
            else {
                if (this.kindCase_ == 6) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                this.listValueBuilder_.clear();
            }
            return this;
        }
        
        public ListValue.Builder getListValueBuilder() {
            return this.getListValueFieldBuilder().getBuilder();
        }
        
        @Override
        public ListValueOrBuilder getListValueOrBuilder() {
            if (this.kindCase_ == 6 && this.listValueBuilder_ != null) {
                return this.listValueBuilder_.getMessageOrBuilder();
            }
            if (this.kindCase_ == 6) {
                return (ListValue)this.kind_;
            }
            return ListValue.getDefaultInstance();
        }
        
        private SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder> getListValueFieldBuilder() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ != 6) {
                    this.kind_ = ListValue.getDefaultInstance();
                }
                this.listValueBuilder_ = new SingleFieldBuilderV3<ListValue, ListValue.Builder, ListValueOrBuilder>((ListValue)this.kind_, this.getParentForChildren(), this.isClean());
                this.kind_ = null;
            }
            this.kindCase_ = 6;
            this.onChanged();
            return this.listValueBuilder_;
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
