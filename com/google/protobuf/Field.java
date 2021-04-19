package com.google.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;

public final class Field extends GeneratedMessageV3 implements FieldOrBuilder
{
    private static final long serialVersionUID = 0L;
    public static final int KIND_FIELD_NUMBER = 1;
    private int kind_;
    public static final int CARDINALITY_FIELD_NUMBER = 2;
    private int cardinality_;
    public static final int NUMBER_FIELD_NUMBER = 3;
    private int number_;
    public static final int NAME_FIELD_NUMBER = 4;
    private volatile Object name_;
    public static final int TYPE_URL_FIELD_NUMBER = 6;
    private volatile Object typeUrl_;
    public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
    private int oneofIndex_;
    public static final int PACKED_FIELD_NUMBER = 8;
    private boolean packed_;
    public static final int OPTIONS_FIELD_NUMBER = 9;
    private List<Option> options_;
    public static final int JSON_NAME_FIELD_NUMBER = 10;
    private volatile Object jsonName_;
    public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
    private volatile Object defaultValue_;
    private byte memoizedIsInitialized;
    private static final Field DEFAULT_INSTANCE;
    private static final Parser<Field> PARSER;
    
    private Field(final GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }
    
    private Field() {
        this.memoizedIsInitialized = -1;
        this.kind_ = 0;
        this.cardinality_ = 0;
        this.name_ = "";
        this.typeUrl_ = "";
        this.options_ = Collections.emptyList();
        this.jsonName_ = "";
        this.defaultValue_ = "";
    }
    
    @Override
    protected Object newInstance(final UnusedPrivateParameter unused) {
        return new Field();
    }
    
    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    private Field(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 8: {
                        final int rawValue = input.readEnum();
                        this.kind_ = rawValue;
                        continue;
                    }
                    case 16: {
                        final int rawValue = input.readEnum();
                        this.cardinality_ = rawValue;
                        continue;
                    }
                    case 24: {
                        this.number_ = input.readInt32();
                        continue;
                    }
                    case 34: {
                        final String s = input.readStringRequireUtf8();
                        this.name_ = s;
                        continue;
                    }
                    case 50: {
                        final String s = input.readStringRequireUtf8();
                        this.typeUrl_ = s;
                        continue;
                    }
                    case 56: {
                        this.oneofIndex_ = input.readInt32();
                        continue;
                    }
                    case 64: {
                        this.packed_ = input.readBool();
                        continue;
                    }
                    case 74: {
                        if ((mutable_bitField0_ & 0x1) == 0x0) {
                            this.options_ = new ArrayList<Option>();
                            mutable_bitField0_ |= 0x1;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        continue;
                    }
                    case 82: {
                        final String s = input.readStringRequireUtf8();
                        this.jsonName_ = s;
                        continue;
                    }
                    case 90: {
                        final String s = input.readStringRequireUtf8();
                        this.defaultValue_ = s;
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
        return TypeProto.internal_static_google_protobuf_Field_descriptor;
    }
    
    @Override
    protected FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable.ensureFieldAccessorsInitialized(Field.class, Builder.class);
    }
    
    @Override
    public int getKindValue() {
        return this.kind_;
    }
    
    @Override
    public Kind getKind() {
        final Kind result = Kind.valueOf(this.kind_);
        return (result == null) ? Kind.UNRECOGNIZED : result;
    }
    
    @Override
    public int getCardinalityValue() {
        return this.cardinality_;
    }
    
    @Override
    public Cardinality getCardinality() {
        final Cardinality result = Cardinality.valueOf(this.cardinality_);
        return (result == null) ? Cardinality.UNRECOGNIZED : result;
    }
    
    @Override
    public int getNumber() {
        return this.number_;
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
    public int getOneofIndex() {
        return this.oneofIndex_;
    }
    
    @Override
    public boolean getPacked() {
        return this.packed_;
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
    public String getJsonName() {
        final Object ref = this.jsonName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.jsonName_ = s);
    }
    
    @Override
    public ByteString getJsonNameBytes() {
        final Object ref = this.jsonName_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.jsonName_ = b);
        }
        return (ByteString)ref;
    }
    
    @Override
    public String getDefaultValue() {
        final Object ref = this.defaultValue_;
        if (ref instanceof String) {
            return (String)ref;
        }
        final ByteString bs = (ByteString)ref;
        final String s = bs.toStringUtf8();
        return (String)(this.defaultValue_ = s);
    }
    
    @Override
    public ByteString getDefaultValueBytes() {
        final Object ref = this.defaultValue_;
        if (ref instanceof String) {
            final ByteString b = ByteString.copyFromUtf8((String)ref);
            return (ByteString)(this.defaultValue_ = b);
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
        if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
            output.writeEnum(1, this.kind_);
        }
        if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
            output.writeEnum(2, this.cardinality_);
        }
        if (this.number_ != 0) {
            output.writeInt32(3, this.number_);
        }
        if (!this.getNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 4, this.name_);
        }
        if (!this.getTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 6, this.typeUrl_);
        }
        if (this.oneofIndex_ != 0) {
            output.writeInt32(7, this.oneofIndex_);
        }
        if (this.packed_) {
            output.writeBool(8, this.packed_);
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            output.writeMessage(9, this.options_.get(i));
        }
        if (!this.getJsonNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 10, this.jsonName_);
        }
        if (!this.getDefaultValueBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 11, this.defaultValue_);
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
        if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
            size += CodedOutputStream.computeEnumSize(1, this.kind_);
        }
        if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
            size += CodedOutputStream.computeEnumSize(2, this.cardinality_);
        }
        if (this.number_ != 0) {
            size += CodedOutputStream.computeInt32Size(3, this.number_);
        }
        if (!this.getNameBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(4, this.name_);
        }
        if (!this.getTypeUrlBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(6, this.typeUrl_);
        }
        if (this.oneofIndex_ != 0) {
            size += CodedOutputStream.computeInt32Size(7, this.oneofIndex_);
        }
        if (this.packed_) {
            size += CodedOutputStream.computeBoolSize(8, this.packed_);
        }
        for (int i = 0; i < this.options_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(9, this.options_.get(i));
        }
        if (!this.getJsonNameBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(10, this.jsonName_);
        }
        if (!this.getDefaultValueBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(11, this.defaultValue_);
        }
        size += this.unknownFields.getSerializedSize();
        return this.memoizedSize = size;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return super.equals(obj);
        }
        final Field other = (Field)obj;
        return this.kind_ == other.kind_ && this.cardinality_ == other.cardinality_ && this.getNumber() == other.getNumber() && this.getName().equals(other.getName()) && this.getTypeUrl().equals(other.getTypeUrl()) && this.getOneofIndex() == other.getOneofIndex() && this.getPacked() == other.getPacked() && this.getOptionsList().equals(other.getOptionsList()) && this.getJsonName().equals(other.getJsonName()) && this.getDefaultValue().equals(other.getDefaultValue()) && this.unknownFields.equals(other.unknownFields);
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.kind_;
        hash = 37 * hash + 2;
        hash = 53 * hash + this.cardinality_;
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getNumber();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getTypeUrl().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getOneofIndex();
        hash = 37 * hash + 8;
        hash = 53 * hash + Internal.hashBoolean(this.getPacked());
        if (this.getOptionsCount() > 0) {
            hash = 37 * hash + 9;
            hash = 53 * hash + this.getOptionsList().hashCode();
        }
        hash = 37 * hash + 10;
        hash = 53 * hash + this.getJsonName().hashCode();
        hash = 37 * hash + 11;
        hash = 53 * hash + this.getDefaultValue().hashCode();
        hash = 29 * hash + this.unknownFields.hashCode();
        return this.memoizedHashCode = hash;
    }
    
    public static Field parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data);
    }
    
    public static Field parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Field parseFrom(final ByteString data) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data);
    }
    
    public static Field parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Field parseFrom(final byte[] data) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data);
    }
    
    public static Field parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return Field.PARSER.parseFrom(data, extensionRegistry);
    }
    
    public static Field parseFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Field.PARSER, input);
    }
    
    public static Field parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Field.PARSER, input, extensionRegistry);
    }
    
    public static Field parseDelimitedFrom(final InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Field.PARSER, input);
    }
    
    public static Field parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(Field.PARSER, input, extensionRegistry);
    }
    
    public static Field parseFrom(final CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Field.PARSER, input);
    }
    
    public static Field parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(Field.PARSER, input, extensionRegistry);
    }
    
    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }
    
    public static Builder newBuilder() {
        return Field.DEFAULT_INSTANCE.toBuilder();
    }
    
    public static Builder newBuilder(final Field prototype) {
        return Field.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    
    @Override
    public Builder toBuilder() {
        return (this == Field.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
    }
    
    @Override
    protected Builder newBuilderForType(final BuilderParent parent) {
        final Builder builder = new Builder(parent);
        return builder;
    }
    
    public static Field getDefaultInstance() {
        return Field.DEFAULT_INSTANCE;
    }
    
    public static Parser<Field> parser() {
        return Field.PARSER;
    }
    
    @Override
    public Parser<Field> getParserForType() {
        return Field.PARSER;
    }
    
    @Override
    public Field getDefaultInstanceForType() {
        return Field.DEFAULT_INSTANCE;
    }
    
    static {
        DEFAULT_INSTANCE = new Field();
        PARSER = new AbstractParser<Field>() {
            @Override
            public Field parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Field(input, extensionRegistry, null);
            }
        };
    }
    
    public enum Kind implements ProtocolMessageEnum
    {
        TYPE_UNKNOWN(0), 
        TYPE_DOUBLE(1), 
        TYPE_FLOAT(2), 
        TYPE_INT64(3), 
        TYPE_UINT64(4), 
        TYPE_INT32(5), 
        TYPE_FIXED64(6), 
        TYPE_FIXED32(7), 
        TYPE_BOOL(8), 
        TYPE_STRING(9), 
        TYPE_GROUP(10), 
        TYPE_MESSAGE(11), 
        TYPE_BYTES(12), 
        TYPE_UINT32(13), 
        TYPE_ENUM(14), 
        TYPE_SFIXED32(15), 
        TYPE_SFIXED64(16), 
        TYPE_SINT32(17), 
        TYPE_SINT64(18), 
        UNRECOGNIZED(-1);
        
        public static final int TYPE_UNKNOWN_VALUE = 0;
        public static final int TYPE_DOUBLE_VALUE = 1;
        public static final int TYPE_FLOAT_VALUE = 2;
        public static final int TYPE_INT64_VALUE = 3;
        public static final int TYPE_UINT64_VALUE = 4;
        public static final int TYPE_INT32_VALUE = 5;
        public static final int TYPE_FIXED64_VALUE = 6;
        public static final int TYPE_FIXED32_VALUE = 7;
        public static final int TYPE_BOOL_VALUE = 8;
        public static final int TYPE_STRING_VALUE = 9;
        public static final int TYPE_GROUP_VALUE = 10;
        public static final int TYPE_MESSAGE_VALUE = 11;
        public static final int TYPE_BYTES_VALUE = 12;
        public static final int TYPE_UINT32_VALUE = 13;
        public static final int TYPE_ENUM_VALUE = 14;
        public static final int TYPE_SFIXED32_VALUE = 15;
        public static final int TYPE_SFIXED64_VALUE = 16;
        public static final int TYPE_SINT32_VALUE = 17;
        public static final int TYPE_SINT64_VALUE = 18;
        private static final Internal.EnumLiteMap<Kind> internalValueMap;
        private static final Kind[] VALUES;
        private final int value;
        
        @Override
        public final int getNumber() {
            if (this == Kind.UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }
        
        @Deprecated
        public static Kind valueOf(final int value) {
            return forNumber(value);
        }
        
        public static Kind forNumber(final int value) {
            switch (value) {
                case 0: {
                    return Kind.TYPE_UNKNOWN;
                }
                case 1: {
                    return Kind.TYPE_DOUBLE;
                }
                case 2: {
                    return Kind.TYPE_FLOAT;
                }
                case 3: {
                    return Kind.TYPE_INT64;
                }
                case 4: {
                    return Kind.TYPE_UINT64;
                }
                case 5: {
                    return Kind.TYPE_INT32;
                }
                case 6: {
                    return Kind.TYPE_FIXED64;
                }
                case 7: {
                    return Kind.TYPE_FIXED32;
                }
                case 8: {
                    return Kind.TYPE_BOOL;
                }
                case 9: {
                    return Kind.TYPE_STRING;
                }
                case 10: {
                    return Kind.TYPE_GROUP;
                }
                case 11: {
                    return Kind.TYPE_MESSAGE;
                }
                case 12: {
                    return Kind.TYPE_BYTES;
                }
                case 13: {
                    return Kind.TYPE_UINT32;
                }
                case 14: {
                    return Kind.TYPE_ENUM;
                }
                case 15: {
                    return Kind.TYPE_SFIXED32;
                }
                case 16: {
                    return Kind.TYPE_SFIXED64;
                }
                case 17: {
                    return Kind.TYPE_SINT32;
                }
                case 18: {
                    return Kind.TYPE_SINT64;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<Kind> internalGetValueMap() {
            return Kind.internalValueMap;
        }
        
        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return Field.getDescriptor().getEnumTypes().get(0);
        }
        
        public static Kind valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return Kind.UNRECOGNIZED;
            }
            return Kind.VALUES[desc.getIndex()];
        }
        
        private Kind(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = new Internal.EnumLiteMap<Kind>() {
                @Override
                public Kind findValueByNumber(final int number) {
                    return Kind.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum Cardinality implements ProtocolMessageEnum
    {
        CARDINALITY_UNKNOWN(0), 
        CARDINALITY_OPTIONAL(1), 
        CARDINALITY_REQUIRED(2), 
        CARDINALITY_REPEATED(3), 
        UNRECOGNIZED(-1);
        
        public static final int CARDINALITY_UNKNOWN_VALUE = 0;
        public static final int CARDINALITY_OPTIONAL_VALUE = 1;
        public static final int CARDINALITY_REQUIRED_VALUE = 2;
        public static final int CARDINALITY_REPEATED_VALUE = 3;
        private static final Internal.EnumLiteMap<Cardinality> internalValueMap;
        private static final Cardinality[] VALUES;
        private final int value;
        
        @Override
        public final int getNumber() {
            if (this == Cardinality.UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }
        
        @Deprecated
        public static Cardinality valueOf(final int value) {
            return forNumber(value);
        }
        
        public static Cardinality forNumber(final int value) {
            switch (value) {
                case 0: {
                    return Cardinality.CARDINALITY_UNKNOWN;
                }
                case 1: {
                    return Cardinality.CARDINALITY_OPTIONAL;
                }
                case 2: {
                    return Cardinality.CARDINALITY_REQUIRED;
                }
                case 3: {
                    return Cardinality.CARDINALITY_REPEATED;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<Cardinality> internalGetValueMap() {
            return Cardinality.internalValueMap;
        }
        
        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return Field.getDescriptor().getEnumTypes().get(1);
        }
        
        public static Cardinality valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return Cardinality.UNRECOGNIZED;
            }
            return Cardinality.VALUES[desc.getIndex()];
        }
        
        private Cardinality(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = new Internal.EnumLiteMap<Cardinality>() {
                @Override
                public Cardinality findValueByNumber(final int number) {
                    return Cardinality.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FieldOrBuilder
    {
        private int bitField0_;
        private int kind_;
        private int cardinality_;
        private int number_;
        private Object name_;
        private Object typeUrl_;
        private int oneofIndex_;
        private boolean packed_;
        private List<Option> options_;
        private RepeatedFieldBuilderV3<Option, Option.Builder, OptionOrBuilder> optionsBuilder_;
        private Object jsonName_;
        private Object defaultValue_;
        
        public static final Descriptors.Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Field_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable.ensureFieldAccessorsInitialized(Field.class, Builder.class);
        }
        
        private Builder() {
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.jsonName_ = "";
            this.defaultValue_ = "";
            this.maybeForceBuilderInitialization();
        }
        
        private Builder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.jsonName_ = "";
            this.defaultValue_ = "";
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
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.number_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.oneofIndex_ = 0;
            this.packed_ = false;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
            }
            else {
                this.optionsBuilder_.clear();
            }
            this.jsonName_ = "";
            this.defaultValue_ = "";
            return this;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Field_descriptor;
        }
        
        @Override
        public Field getDefaultInstanceForType() {
            return Field.getDefaultInstance();
        }
        
        @Override
        public Field build() {
            final Field result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        public Field buildPartial() {
            final Field result = new Field(this, null);
            final int from_bitField0_ = this.bitField0_;
            result.kind_ = this.kind_;
            result.cardinality_ = this.cardinality_;
            result.number_ = this.number_;
            result.name_ = this.name_;
            result.typeUrl_ = this.typeUrl_;
            result.oneofIndex_ = this.oneofIndex_;
            result.packed_ = this.packed_;
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
            result.jsonName_ = this.jsonName_;
            result.defaultValue_ = this.defaultValue_;
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
            if (other instanceof Field) {
                return this.mergeFrom((Field)other);
            }
            super.mergeFrom(other);
            return this;
        }
        
        public Builder mergeFrom(final Field other) {
            if (other == Field.getDefaultInstance()) {
                return this;
            }
            if (other.kind_ != 0) {
                this.setKindValue(other.getKindValue());
            }
            if (other.cardinality_ != 0) {
                this.setCardinalityValue(other.getCardinalityValue());
            }
            if (other.getNumber() != 0) {
                this.setNumber(other.getNumber());
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.onChanged();
            }
            if (!other.getTypeUrl().isEmpty()) {
                this.typeUrl_ = other.typeUrl_;
                this.onChanged();
            }
            if (other.getOneofIndex() != 0) {
                this.setOneofIndex(other.getOneofIndex());
            }
            if (other.getPacked()) {
                this.setPacked(other.getPacked());
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
            if (!other.getJsonName().isEmpty()) {
                this.jsonName_ = other.jsonName_;
                this.onChanged();
            }
            if (!other.getDefaultValue().isEmpty()) {
                this.defaultValue_ = other.defaultValue_;
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
            Field parsedMessage = null;
            try {
                parsedMessage = Field.PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Field)e.getUnfinishedMessage();
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
        public int getKindValue() {
            return this.kind_;
        }
        
        public Builder setKindValue(final int value) {
            this.kind_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public Kind getKind() {
            final Kind result = Kind.valueOf(this.kind_);
            return (result == null) ? Kind.UNRECOGNIZED : result;
        }
        
        public Builder setKind(final Kind value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kind_ = value.getNumber();
            this.onChanged();
            return this;
        }
        
        public Builder clearKind() {
            this.kind_ = 0;
            this.onChanged();
            return this;
        }
        
        @Override
        public int getCardinalityValue() {
            return this.cardinality_;
        }
        
        public Builder setCardinalityValue(final int value) {
            this.cardinality_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public Cardinality getCardinality() {
            final Cardinality result = Cardinality.valueOf(this.cardinality_);
            return (result == null) ? Cardinality.UNRECOGNIZED : result;
        }
        
        public Builder setCardinality(final Cardinality value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.cardinality_ = value.getNumber();
            this.onChanged();
            return this;
        }
        
        public Builder clearCardinality() {
            this.cardinality_ = 0;
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
            this.name_ = Field.getDefaultInstance().getName();
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
            this.typeUrl_ = Field.getDefaultInstance().getTypeUrl();
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
        public int getOneofIndex() {
            return this.oneofIndex_;
        }
        
        public Builder setOneofIndex(final int value) {
            this.oneofIndex_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearOneofIndex() {
            this.oneofIndex_ = 0;
            this.onChanged();
            return this;
        }
        
        @Override
        public boolean getPacked() {
            return this.packed_;
        }
        
        public Builder setPacked(final boolean value) {
            this.packed_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearPacked() {
            this.packed_ = false;
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
        public String getJsonName() {
            final Object ref = this.jsonName_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.jsonName_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getJsonNameBytes() {
            final Object ref = this.jsonName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.jsonName_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setJsonName(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.jsonName_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearJsonName() {
            this.jsonName_ = Field.getDefaultInstance().getJsonName();
            this.onChanged();
            return this;
        }
        
        public Builder setJsonNameBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.jsonName_ = value;
            this.onChanged();
            return this;
        }
        
        @Override
        public String getDefaultValue() {
            final Object ref = this.defaultValue_;
            if (!(ref instanceof String)) {
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                return (String)(this.defaultValue_ = s);
            }
            return (String)ref;
        }
        
        @Override
        public ByteString getDefaultValueBytes() {
            final Object ref = this.defaultValue_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.defaultValue_ = b);
            }
            return (ByteString)ref;
        }
        
        public Builder setDefaultValue(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.defaultValue_ = value;
            this.onChanged();
            return this;
        }
        
        public Builder clearDefaultValue() {
            this.defaultValue_ = Field.getDefaultInstance().getDefaultValue();
            this.onChanged();
            return this;
        }
        
        public Builder setDefaultValueBytes(final ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.defaultValue_ = value;
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
