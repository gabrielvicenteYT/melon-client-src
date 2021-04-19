package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import com.google.protobuf.*;
import java.util.*;

public final class MysqlxDatatypes
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Scalar_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Scalar_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Scalar_String_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Scalar_String_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Scalar_Octets_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Object_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Object_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Object_ObjectField_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Array_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Array_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Datatypes_Any_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Datatypes_Any_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxDatatypes() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxDatatypes.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0016mysqlx_datatypes.proto\u0012\u0010Mysqlx.Datatypes\"\u00c6\u0003\n\u0006Scalar\u0012+\n\u0004type\u0018\u0001 \u0002(\u000e2\u001d.Mysqlx.Datatypes.Scalar.Type\u0012\u0014\n\fv_signed_int\u0018\u0002 \u0001(\u0012\u0012\u0016\n\u000ev_unsigned_int\u0018\u0003 \u0001(\u0004\u00121\n\bv_octets\u0018\u0005 \u0001(\u000b2\u001f.Mysqlx.Datatypes.Scalar.Octets\u0012\u0010\n\bv_double\u0018\u0006 \u0001(\u0001\u0012\u000f\n\u0007v_float\u0018\u0007 \u0001(\u0002\u0012\u000e\n\u0006v_bool\u0018\b \u0001(\b\u00121\n\bv_string\u0018\t \u0001(\u000b2\u001f.Mysqlx.Datatypes.Scalar.String\u001a*\n\u0006String\u0012\r\n\u0005value\u0018\u0001 \u0002(\f\u0012\u0011\n\tcollation\u0018\u0002 \u0001(\u0004\u001a-\n\u0006Octets\u0012\r\n\u0005value\u0018\u0001 \u0002(\f\u0012\u0014\n\fcontent_type\u0018\u0002 \u0001(\r\"m\n\u0004Type\u0012\n\n\u0006V_SINT\u0010\u0001\u0012\n\n\u0006V_UINT\u0010\u0002\u0012\n\n\u0006V_NULL\u0010\u0003\u0012\f\n\bV_OCTETS\u0010\u0004\u0012\f\n\bV_DOUBLE\u0010\u0005\u0012\u000b\n\u0007V_FLOAT\u0010\u0006\u0012\n\n\u0006V_BOOL\u0010\u0007\u0012\f\n\bV_STRING\u0010\b\"}\n\u0006Object\u00121\n\u0003fld\u0018\u0001 \u0003(\u000b2$.Mysqlx.Datatypes.Object.ObjectField\u001a@\n\u000bObjectField\u0012\u000b\n\u0003key\u0018\u0001 \u0002(\t\u0012$\n\u0005value\u0018\u0002 \u0002(\u000b2\u0015.Mysqlx.Datatypes.Any\"-\n\u0005Array\u0012$\n\u0005value\u0018\u0001 \u0003(\u000b2\u0015.Mysqlx.Datatypes.Any\"\u00d3\u0001\n\u0003Any\u0012(\n\u0004type\u0018\u0001 \u0002(\u000e2\u001a.Mysqlx.Datatypes.Any.Type\u0012(\n\u0006scalar\u0018\u0002 \u0001(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012%\n\u0003obj\u0018\u0003 \u0001(\u000b2\u0018.Mysqlx.Datatypes.Object\u0012&\n\u0005array\u0018\u0004 \u0001(\u000b2\u0017.Mysqlx.Datatypes.Array\")\n\u0004Type\u0012\n\n\u0006SCALAR\u0010\u0001\u0012\n\n\u0006OBJECT\u0010\u0002\u0012\t\n\u0005ARRAY\u0010\u0003B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxDatatypes.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_Mysqlx_Datatypes_Scalar_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Datatypes_Scalar_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor, new String[] { "Type", "VSignedInt", "VUnsignedInt", "VOctets", "VDouble", "VFloat", "VBool", "VString" });
        internal_static_Mysqlx_Datatypes_Scalar_String_descriptor = MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Datatypes_Scalar_String_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_descriptor, new String[] { "Value", "Collation" });
        internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor = MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor.getNestedTypes().get(1);
        internal_static_Mysqlx_Datatypes_Scalar_Octets_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor, new String[] { "Value", "ContentType" });
        internal_static_Mysqlx_Datatypes_Object_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Datatypes_Object_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_descriptor, new String[] { "Fld" });
        internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor = MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Datatypes_Object_ObjectField_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor, new String[] { "Key", "Value" });
        internal_static_Mysqlx_Datatypes_Array_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Datatypes_Array_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_descriptor, new String[] { "Value" });
        internal_static_Mysqlx_Datatypes_Any_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Datatypes_Any_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_descriptor, new String[] { "Type", "Scalar", "Obj", "Array" });
    }
    
    public static final class Scalar extends GeneratedMessageV3 implements ScalarOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int V_SIGNED_INT_FIELD_NUMBER = 2;
        private long vSignedInt_;
        public static final int V_UNSIGNED_INT_FIELD_NUMBER = 3;
        private long vUnsignedInt_;
        public static final int V_OCTETS_FIELD_NUMBER = 5;
        private Octets vOctets_;
        public static final int V_DOUBLE_FIELD_NUMBER = 6;
        private double vDouble_;
        public static final int V_FLOAT_FIELD_NUMBER = 7;
        private float vFloat_;
        public static final int V_BOOL_FIELD_NUMBER = 8;
        private boolean vBool_;
        public static final int V_STRING_FIELD_NUMBER = 9;
        private String vString_;
        private byte memoizedIsInitialized;
        private static final Scalar DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Scalar> PARSER;
        
        private Scalar(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Scalar() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Scalar();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Scalar(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final int mutable_bitField0_ = 0;
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
                            final Type value = Type.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 16: {
                            this.bitField0_ |= 0x2;
                            this.vSignedInt_ = input.readSInt64();
                            continue;
                        }
                        case 24: {
                            this.bitField0_ |= 0x4;
                            this.vUnsignedInt_ = input.readUInt64();
                            continue;
                        }
                        case 42: {
                            Octets.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x8) != 0x0) {
                                subBuilder = this.vOctets_.toBuilder();
                            }
                            this.vOctets_ = input.readMessage(Octets.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.vOctets_);
                                this.vOctets_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 49: {
                            this.bitField0_ |= 0x10;
                            this.vDouble_ = input.readDouble();
                            continue;
                        }
                        case 61: {
                            this.bitField0_ |= 0x20;
                            this.vFloat_ = input.readFloat();
                            continue;
                        }
                        case 64: {
                            this.bitField0_ |= 0x40;
                            this.vBool_ = input.readBool();
                            continue;
                        }
                        case 74: {
                            String.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x80) != 0x0) {
                                subBuilder2 = this.vString_.toBuilder();
                            }
                            this.vString_ = input.readMessage(String.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.vString_);
                                this.vString_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x80;
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
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_fieldAccessorTable.ensureFieldAccessorsInitialized(Scalar.class, Builder.class);
        }
        
        @Override
        public boolean hasType() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public Type getType() {
            final Type result = Type.valueOf(this.type_);
            return (result == null) ? Type.V_SINT : result;
        }
        
        @Override
        public boolean hasVSignedInt() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public long getVSignedInt() {
            return this.vSignedInt_;
        }
        
        @Override
        public boolean hasVUnsignedInt() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public long getVUnsignedInt() {
            return this.vUnsignedInt_;
        }
        
        @Override
        public boolean hasVOctets() {
            return (this.bitField0_ & 0x8) != 0x0;
        }
        
        @Override
        public Octets getVOctets() {
            return (this.vOctets_ == null) ? Octets.getDefaultInstance() : this.vOctets_;
        }
        
        @Override
        public OctetsOrBuilder getVOctetsOrBuilder() {
            return (this.vOctets_ == null) ? Octets.getDefaultInstance() : this.vOctets_;
        }
        
        @Override
        public boolean hasVDouble() {
            return (this.bitField0_ & 0x10) != 0x0;
        }
        
        @Override
        public double getVDouble() {
            return this.vDouble_;
        }
        
        @Override
        public boolean hasVFloat() {
            return (this.bitField0_ & 0x20) != 0x0;
        }
        
        @Override
        public float getVFloat() {
            return this.vFloat_;
        }
        
        @Override
        public boolean hasVBool() {
            return (this.bitField0_ & 0x40) != 0x0;
        }
        
        @Override
        public boolean getVBool() {
            return this.vBool_;
        }
        
        @Override
        public boolean hasVString() {
            return (this.bitField0_ & 0x80) != 0x0;
        }
        
        @Override
        public String getVString() {
            return (this.vString_ == null) ? String.getDefaultInstance() : this.vString_;
        }
        
        @Override
        public StringOrBuilder getVStringOrBuilder() {
            return (this.vString_ == null) ? String.getDefaultInstance() : this.vString_;
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
            if (!this.hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasVOctets() && !this.getVOctets().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasVString() && !this.getVString().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeEnum(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeSInt64(2, this.vSignedInt_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeUInt64(3, this.vUnsignedInt_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                output.writeMessage(5, this.getVOctets());
            }
            if ((this.bitField0_ & 0x10) != 0x0) {
                output.writeDouble(6, this.vDouble_);
            }
            if ((this.bitField0_ & 0x20) != 0x0) {
                output.writeFloat(7, this.vFloat_);
            }
            if ((this.bitField0_ & 0x40) != 0x0) {
                output.writeBool(8, this.vBool_);
            }
            if ((this.bitField0_ & 0x80) != 0x0) {
                output.writeMessage(9, this.getVString());
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
            if ((this.bitField0_ & 0x1) != 0x0) {
                size += CodedOutputStream.computeEnumSize(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeSInt64Size(2, this.vSignedInt_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeUInt64Size(3, this.vUnsignedInt_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                size += CodedOutputStream.computeMessageSize(5, this.getVOctets());
            }
            if ((this.bitField0_ & 0x10) != 0x0) {
                size += CodedOutputStream.computeDoubleSize(6, this.vDouble_);
            }
            if ((this.bitField0_ & 0x20) != 0x0) {
                size += CodedOutputStream.computeFloatSize(7, this.vFloat_);
            }
            if ((this.bitField0_ & 0x40) != 0x0) {
                size += CodedOutputStream.computeBoolSize(8, this.vBool_);
            }
            if ((this.bitField0_ & 0x80) != 0x0) {
                size += CodedOutputStream.computeMessageSize(9, this.getVString());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Scalar)) {
                return super.equals(obj);
            }
            final Scalar other = (Scalar)obj;
            return this.hasType() == other.hasType() && (!this.hasType() || this.type_ == other.type_) && this.hasVSignedInt() == other.hasVSignedInt() && (!this.hasVSignedInt() || this.getVSignedInt() == other.getVSignedInt()) && this.hasVUnsignedInt() == other.hasVUnsignedInt() && (!this.hasVUnsignedInt() || this.getVUnsignedInt() == other.getVUnsignedInt()) && this.hasVOctets() == other.hasVOctets() && (!this.hasVOctets() || this.getVOctets().equals(other.getVOctets())) && this.hasVDouble() == other.hasVDouble() && (!this.hasVDouble() || Double.doubleToLongBits(this.getVDouble()) == Double.doubleToLongBits(other.getVDouble())) && this.hasVFloat() == other.hasVFloat() && (!this.hasVFloat() || Float.floatToIntBits(this.getVFloat()) == Float.floatToIntBits(other.getVFloat())) && this.hasVBool() == other.hasVBool() && (!this.hasVBool() || this.getVBool() == other.getVBool()) && this.hasVString() == other.hasVString() && (!this.hasVString() || this.getVString().equals(other.getVString())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasType()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.type_;
            }
            if (this.hasVSignedInt()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashLong(this.getVSignedInt());
            }
            if (this.hasVUnsignedInt()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + Internal.hashLong(this.getVUnsignedInt());
            }
            if (this.hasVOctets()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getVOctets().hashCode();
            }
            if (this.hasVDouble()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getVDouble()));
            }
            if (this.hasVFloat()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + Float.floatToIntBits(this.getVFloat());
            }
            if (this.hasVBool()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + Internal.hashBoolean(this.getVBool());
            }
            if (this.hasVString()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getVString().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Scalar parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data);
        }
        
        public static Scalar parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Scalar parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data);
        }
        
        public static Scalar parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Scalar parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data);
        }
        
        public static Scalar parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Scalar.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Scalar parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Scalar.PARSER, input);
        }
        
        public static Scalar parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Scalar.PARSER, input, extensionRegistry);
        }
        
        public static Scalar parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Scalar.PARSER, input);
        }
        
        public static Scalar parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Scalar.PARSER, input, extensionRegistry);
        }
        
        public static Scalar parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Scalar.PARSER, input);
        }
        
        public static Scalar parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Scalar.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Scalar.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Scalar prototype) {
            return Scalar.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Scalar.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Scalar getDefaultInstance() {
            return Scalar.DEFAULT_INSTANCE;
        }
        
        public static Parser<Scalar> parser() {
            return Scalar.PARSER;
        }
        
        @Override
        public Parser<Scalar> getParserForType() {
            return Scalar.PARSER;
        }
        
        @Override
        public Scalar getDefaultInstanceForType() {
            return Scalar.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Scalar();
            PARSER = new AbstractParser<Scalar>() {
                @Override
                public Scalar parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Scalar(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            V_SINT(1), 
            V_UINT(2), 
            V_NULL(3), 
            V_OCTETS(4), 
            V_DOUBLE(5), 
            V_FLOAT(6), 
            V_BOOL(7), 
            V_STRING(8);
            
            public static final int V_SINT_VALUE = 1;
            public static final int V_UINT_VALUE = 2;
            public static final int V_NULL_VALUE = 3;
            public static final int V_OCTETS_VALUE = 4;
            public static final int V_DOUBLE_VALUE = 5;
            public static final int V_FLOAT_VALUE = 6;
            public static final int V_BOOL_VALUE = 7;
            public static final int V_STRING_VALUE = 8;
            private static final Internal.EnumLiteMap<Type> internalValueMap;
            private static final Type[] VALUES;
            private final int value;
            
            @Override
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Type valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Type forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Type.V_SINT;
                    }
                    case 2: {
                        return Type.V_UINT;
                    }
                    case 3: {
                        return Type.V_NULL;
                    }
                    case 4: {
                        return Type.V_OCTETS;
                    }
                    case 5: {
                        return Type.V_DOUBLE;
                    }
                    case 6: {
                        return Type.V_FLOAT;
                    }
                    case 7: {
                        return Type.V_BOOL;
                    }
                    case 8: {
                        return Type.V_STRING;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return Type.internalValueMap;
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
                return Scalar.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Type valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Type.VALUES[desc.getIndex()];
            }
            
            private Type(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = new Internal.EnumLiteMap<Type>() {
                    @Override
                    public Type findValueByNumber(final int number) {
                        return Type.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class String extends GeneratedMessageV3 implements StringOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int VALUE_FIELD_NUMBER = 1;
            private ByteString value_;
            public static final int COLLATION_FIELD_NUMBER = 2;
            private long collation_;
            private byte memoizedIsInitialized;
            private static final String DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<String> PARSER;
            
            private String(final GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }
            
            private String() {
                this.memoizedIsInitialized = -1;
                this.value_ = ByteString.EMPTY;
            }
            
            @Override
            protected Object newInstance(final UnusedPrivateParameter unused) {
                return new String();
            }
            
            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private String(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                final int mutable_bitField0_ = 0;
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
                                this.bitField0_ |= 0x1;
                                this.value_ = input.readBytes();
                                continue;
                            }
                            case 16: {
                                this.bitField0_ |= 0x2;
                                this.collation_ = input.readUInt64();
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
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_fieldAccessorTable.ensureFieldAccessorsInitialized(String.class, Builder.class);
            }
            
            @Override
            public boolean hasValue() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public ByteString getValue() {
                return this.value_;
            }
            
            @Override
            public boolean hasCollation() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public long getCollation() {
                return this.collation_;
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
                if (!this.hasValue()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
            
            @Override
            public void writeTo(final CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    output.writeBytes(1, this.value_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    output.writeUInt64(2, this.collation_);
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
                if ((this.bitField0_ & 0x1) != 0x0) {
                    size += CodedOutputStream.computeBytesSize(1, this.value_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    size += CodedOutputStream.computeUInt64Size(2, this.collation_);
                }
                size += this.unknownFields.getSerializedSize();
                return this.memoizedSize = size;
            }
            
            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof String)) {
                    return super.equals(obj);
                }
                final String other = (String)obj;
                return this.hasValue() == other.hasValue() && (!this.hasValue() || this.getValue().equals(other.getValue())) && this.hasCollation() == other.hasCollation() && (!this.hasCollation() || this.getCollation() == other.getCollation()) && this.unknownFields.equals(other.unknownFields);
            }
            
            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + getDescriptor().hashCode();
                if (this.hasValue()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getValue().hashCode();
                }
                if (this.hasCollation()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + Internal.hashLong(this.getCollation());
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static String parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data);
            }
            
            public static String parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static String parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data);
            }
            
            public static String parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static String parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data);
            }
            
            public static String parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return String.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static String parseFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(String.PARSER, input);
            }
            
            public static String parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(String.PARSER, input, extensionRegistry);
            }
            
            public static String parseDelimitedFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(String.PARSER, input);
            }
            
            public static String parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(String.PARSER, input, extensionRegistry);
            }
            
            public static String parseFrom(final CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(String.PARSER, input);
            }
            
            public static String parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(String.PARSER, input, extensionRegistry);
            }
            
            @Override
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return String.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final String prototype) {
                return String.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            @Override
            public Builder toBuilder() {
                return (this == String.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            @Override
            protected Builder newBuilderForType(final BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static String getDefaultInstance() {
                return String.DEFAULT_INSTANCE;
            }
            
            public static Parser<String> parser() {
                return String.PARSER;
            }
            
            @Override
            public Parser<String> getParserForType() {
                return String.PARSER;
            }
            
            @Override
            public String getDefaultInstanceForType() {
                return String.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new String();
                PARSER = new AbstractParser<String>() {
                    @Override
                    public String parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new String(input, extensionRegistry);
                    }
                };
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StringOrBuilder
            {
                private int bitField0_;
                private ByteString value_;
                private long collation_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_fieldAccessorTable.ensureFieldAccessorsInitialized(String.class, Builder.class);
                }
                
                private Builder() {
                    this.value_ = ByteString.EMPTY;
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.value_ = ByteString.EMPTY;
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (String.alwaysUseFieldBuilders) {}
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.value_ = ByteString.EMPTY;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.collation_ = 0L;
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_String_descriptor;
                }
                
                @Override
                public String getDefaultInstanceForType() {
                    return String.getDefaultInstance();
                }
                
                @Override
                public String build() {
                    final String result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw AbstractMessage.Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }
                
                @Override
                public String buildPartial() {
                    final String result = new String((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) != 0x0) {
                        to_bitField0_ |= 0x1;
                    }
                    result.value_ = this.value_;
                    if ((from_bitField0_ & 0x2) != 0x0) {
                        result.collation_ = this.collation_;
                        to_bitField0_ |= 0x2;
                    }
                    result.bitField0_ = to_bitField0_;
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
                    if (other instanceof String) {
                        return this.mergeFrom((String)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final String other) {
                    if (other == String.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasValue()) {
                        this.setValue(other.getValue());
                    }
                    if (other.hasCollation()) {
                        this.setCollation(other.getCollation());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                @Override
                public final boolean isInitialized() {
                    return this.hasValue();
                }
                
                @Override
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    String parsedMessage = null;
                    try {
                        parsedMessage = String.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (String)e.getUnfinishedMessage();
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
                public boolean hasValue() {
                    return (this.bitField0_ & 0x1) != 0x0;
                }
                
                @Override
                public ByteString getValue() {
                    return this.value_;
                }
                
                public Builder setValue(final ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.value_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearValue() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.value_ = String.getDefaultInstance().getValue();
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasCollation() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }
                
                @Override
                public long getCollation() {
                    return this.collation_;
                }
                
                public Builder setCollation(final long value) {
                    this.bitField0_ |= 0x2;
                    this.collation_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearCollation() {
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.collation_ = 0L;
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
        
        public static final class Octets extends GeneratedMessageV3 implements OctetsOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int VALUE_FIELD_NUMBER = 1;
            private ByteString value_;
            public static final int CONTENT_TYPE_FIELD_NUMBER = 2;
            private int contentType_;
            private byte memoizedIsInitialized;
            private static final Octets DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<Octets> PARSER;
            
            private Octets(final GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }
            
            private Octets() {
                this.memoizedIsInitialized = -1;
                this.value_ = ByteString.EMPTY;
            }
            
            @Override
            protected Object newInstance(final UnusedPrivateParameter unused) {
                return new Octets();
            }
            
            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private Octets(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                final int mutable_bitField0_ = 0;
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
                                this.bitField0_ |= 0x1;
                                this.value_ = input.readBytes();
                                continue;
                            }
                            case 16: {
                                this.bitField0_ |= 0x2;
                                this.contentType_ = input.readUInt32();
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
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_fieldAccessorTable.ensureFieldAccessorsInitialized(Octets.class, Builder.class);
            }
            
            @Override
            public boolean hasValue() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public ByteString getValue() {
                return this.value_;
            }
            
            @Override
            public boolean hasContentType() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public int getContentType() {
                return this.contentType_;
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
                if (!this.hasValue()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
            
            @Override
            public void writeTo(final CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    output.writeBytes(1, this.value_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    output.writeUInt32(2, this.contentType_);
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
                if ((this.bitField0_ & 0x1) != 0x0) {
                    size += CodedOutputStream.computeBytesSize(1, this.value_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    size += CodedOutputStream.computeUInt32Size(2, this.contentType_);
                }
                size += this.unknownFields.getSerializedSize();
                return this.memoizedSize = size;
            }
            
            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Octets)) {
                    return super.equals(obj);
                }
                final Octets other = (Octets)obj;
                return this.hasValue() == other.hasValue() && (!this.hasValue() || this.getValue().equals(other.getValue())) && this.hasContentType() == other.hasContentType() && (!this.hasContentType() || this.getContentType() == other.getContentType()) && this.unknownFields.equals(other.unknownFields);
            }
            
            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + getDescriptor().hashCode();
                if (this.hasValue()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getValue().hashCode();
                }
                if (this.hasContentType()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getContentType();
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static Octets parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data);
            }
            
            public static Octets parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Octets parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data);
            }
            
            public static Octets parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Octets parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data);
            }
            
            public static Octets parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Octets.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Octets parseFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Octets.PARSER, input);
            }
            
            public static Octets parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Octets.PARSER, input, extensionRegistry);
            }
            
            public static Octets parseDelimitedFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(Octets.PARSER, input);
            }
            
            public static Octets parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(Octets.PARSER, input, extensionRegistry);
            }
            
            public static Octets parseFrom(final CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Octets.PARSER, input);
            }
            
            public static Octets parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Octets.PARSER, input, extensionRegistry);
            }
            
            @Override
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return Octets.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final Octets prototype) {
                return Octets.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            @Override
            public Builder toBuilder() {
                return (this == Octets.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            @Override
            protected Builder newBuilderForType(final BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static Octets getDefaultInstance() {
                return Octets.DEFAULT_INSTANCE;
            }
            
            public static Parser<Octets> parser() {
                return Octets.PARSER;
            }
            
            @Override
            public Parser<Octets> getParserForType() {
                return Octets.PARSER;
            }
            
            @Override
            public Octets getDefaultInstanceForType() {
                return Octets.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new Octets();
                PARSER = new AbstractParser<Octets>() {
                    @Override
                    public Octets parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new Octets(input, extensionRegistry);
                    }
                };
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OctetsOrBuilder
            {
                private int bitField0_;
                private ByteString value_;
                private int contentType_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_fieldAccessorTable.ensureFieldAccessorsInitialized(Octets.class, Builder.class);
                }
                
                private Builder() {
                    this.value_ = ByteString.EMPTY;
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.value_ = ByteString.EMPTY;
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (Octets.alwaysUseFieldBuilders) {}
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.value_ = ByteString.EMPTY;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.contentType_ = 0;
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_Octets_descriptor;
                }
                
                @Override
                public Octets getDefaultInstanceForType() {
                    return Octets.getDefaultInstance();
                }
                
                @Override
                public Octets build() {
                    final Octets result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw AbstractMessage.Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }
                
                @Override
                public Octets buildPartial() {
                    final Octets result = new Octets((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) != 0x0) {
                        to_bitField0_ |= 0x1;
                    }
                    result.value_ = this.value_;
                    if ((from_bitField0_ & 0x2) != 0x0) {
                        result.contentType_ = this.contentType_;
                        to_bitField0_ |= 0x2;
                    }
                    result.bitField0_ = to_bitField0_;
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
                    if (other instanceof Octets) {
                        return this.mergeFrom((Octets)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final Octets other) {
                    if (other == Octets.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasValue()) {
                        this.setValue(other.getValue());
                    }
                    if (other.hasContentType()) {
                        this.setContentType(other.getContentType());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                @Override
                public final boolean isInitialized() {
                    return this.hasValue();
                }
                
                @Override
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    Octets parsedMessage = null;
                    try {
                        parsedMessage = Octets.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (Octets)e.getUnfinishedMessage();
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
                public boolean hasValue() {
                    return (this.bitField0_ & 0x1) != 0x0;
                }
                
                @Override
                public ByteString getValue() {
                    return this.value_;
                }
                
                public Builder setValue(final ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.value_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearValue() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.value_ = Octets.getDefaultInstance().getValue();
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasContentType() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }
                
                @Override
                public int getContentType() {
                    return this.contentType_;
                }
                
                public Builder setContentType(final int value) {
                    this.bitField0_ |= 0x2;
                    this.contentType_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearContentType() {
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.contentType_ = 0;
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ScalarOrBuilder
        {
            private int bitField0_;
            private int type_;
            private long vSignedInt_;
            private long vUnsignedInt_;
            private Octets vOctets_;
            private SingleFieldBuilderV3<Octets, Octets.Builder, OctetsOrBuilder> vOctetsBuilder_;
            private double vDouble_;
            private float vFloat_;
            private boolean vBool_;
            private String vString_;
            private SingleFieldBuilderV3<String, String.Builder, StringOrBuilder> vStringBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_fieldAccessorTable.ensureFieldAccessorsInitialized(Scalar.class, Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Scalar.alwaysUseFieldBuilders) {
                    this.getVOctetsFieldBuilder();
                    this.getVStringFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                this.vSignedInt_ = 0L;
                this.bitField0_ &= 0xFFFFFFFD;
                this.vUnsignedInt_ = 0L;
                this.bitField0_ &= 0xFFFFFFFB;
                if (this.vOctetsBuilder_ == null) {
                    this.vOctets_ = null;
                }
                else {
                    this.vOctetsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                this.vDouble_ = 0.0;
                this.bitField0_ &= 0xFFFFFFEF;
                this.vFloat_ = 0.0f;
                this.bitField0_ &= 0xFFFFFFDF;
                this.vBool_ = false;
                this.bitField0_ &= 0xFFFFFFBF;
                if (this.vStringBuilder_ == null) {
                    this.vString_ = null;
                }
                else {
                    this.vStringBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFF7F;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Scalar_descriptor;
            }
            
            @Override
            public Scalar getDefaultInstanceForType() {
                return Scalar.getDefaultInstance();
            }
            
            @Override
            public Scalar build() {
                final Scalar result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Scalar buildPartial() {
                final Scalar result = new Scalar((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) != 0x0) {
                    result.vSignedInt_ = this.vSignedInt_;
                    to_bitField0_ |= 0x2;
                }
                if ((from_bitField0_ & 0x4) != 0x0) {
                    result.vUnsignedInt_ = this.vUnsignedInt_;
                    to_bitField0_ |= 0x4;
                }
                if ((from_bitField0_ & 0x8) != 0x0) {
                    if (this.vOctetsBuilder_ == null) {
                        result.vOctets_ = this.vOctets_;
                    }
                    else {
                        result.vOctets_ = this.vOctetsBuilder_.build();
                    }
                    to_bitField0_ |= 0x8;
                }
                if ((from_bitField0_ & 0x10) != 0x0) {
                    result.vDouble_ = this.vDouble_;
                    to_bitField0_ |= 0x10;
                }
                if ((from_bitField0_ & 0x20) != 0x0) {
                    result.vFloat_ = this.vFloat_;
                    to_bitField0_ |= 0x20;
                }
                if ((from_bitField0_ & 0x40) != 0x0) {
                    result.vBool_ = this.vBool_;
                    to_bitField0_ |= 0x40;
                }
                if ((from_bitField0_ & 0x80) != 0x0) {
                    if (this.vStringBuilder_ == null) {
                        result.vString_ = this.vString_;
                    }
                    else {
                        result.vString_ = this.vStringBuilder_.build();
                    }
                    to_bitField0_ |= 0x80;
                }
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof Scalar) {
                    return this.mergeFrom((Scalar)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Scalar other) {
                if (other == Scalar.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasVSignedInt()) {
                    this.setVSignedInt(other.getVSignedInt());
                }
                if (other.hasVUnsignedInt()) {
                    this.setVUnsignedInt(other.getVUnsignedInt());
                }
                if (other.hasVOctets()) {
                    this.mergeVOctets(other.getVOctets());
                }
                if (other.hasVDouble()) {
                    this.setVDouble(other.getVDouble());
                }
                if (other.hasVFloat()) {
                    this.setVFloat(other.getVFloat());
                }
                if (other.hasVBool()) {
                    this.setVBool(other.getVBool());
                }
                if (other.hasVString()) {
                    this.mergeVString(other.getVString());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasType() && (!this.hasVOctets() || this.getVOctets().isInitialized()) && (!this.hasVString() || this.getVString().isInitialized());
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Scalar parsedMessage = null;
                try {
                    parsedMessage = Scalar.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Scalar)e.getUnfinishedMessage();
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
            public boolean hasType() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.V_SINT : result;
            }
            
            public Builder setType(final Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.type_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearType() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.type_ = 1;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVSignedInt() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public long getVSignedInt() {
                return this.vSignedInt_;
            }
            
            public Builder setVSignedInt(final long value) {
                this.bitField0_ |= 0x2;
                this.vSignedInt_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVSignedInt() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.vSignedInt_ = 0L;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVUnsignedInt() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public long getVUnsignedInt() {
                return this.vUnsignedInt_;
            }
            
            public Builder setVUnsignedInt(final long value) {
                this.bitField0_ |= 0x4;
                this.vUnsignedInt_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVUnsignedInt() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.vUnsignedInt_ = 0L;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVOctets() {
                return (this.bitField0_ & 0x8) != 0x0;
            }
            
            @Override
            public Octets getVOctets() {
                if (this.vOctetsBuilder_ == null) {
                    return (this.vOctets_ == null) ? Octets.getDefaultInstance() : this.vOctets_;
                }
                return this.vOctetsBuilder_.getMessage();
            }
            
            public Builder setVOctets(final Octets value) {
                if (this.vOctetsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.vOctets_ = value;
                    this.onChanged();
                }
                else {
                    this.vOctetsBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder setVOctets(final Octets.Builder builderForValue) {
                if (this.vOctetsBuilder_ == null) {
                    this.vOctets_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.vOctetsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder mergeVOctets(final Octets value) {
                if (this.vOctetsBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) != 0x0 && this.vOctets_ != null && this.vOctets_ != Octets.getDefaultInstance()) {
                        this.vOctets_ = Octets.newBuilder(this.vOctets_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.vOctets_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.vOctetsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder clearVOctets() {
                if (this.vOctetsBuilder_ == null) {
                    this.vOctets_ = null;
                    this.onChanged();
                }
                else {
                    this.vOctetsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public Octets.Builder getVOctetsBuilder() {
                this.bitField0_ |= 0x8;
                this.onChanged();
                return this.getVOctetsFieldBuilder().getBuilder();
            }
            
            @Override
            public OctetsOrBuilder getVOctetsOrBuilder() {
                if (this.vOctetsBuilder_ != null) {
                    return this.vOctetsBuilder_.getMessageOrBuilder();
                }
                return (this.vOctets_ == null) ? Octets.getDefaultInstance() : this.vOctets_;
            }
            
            private SingleFieldBuilderV3<Octets, Octets.Builder, OctetsOrBuilder> getVOctetsFieldBuilder() {
                if (this.vOctetsBuilder_ == null) {
                    this.vOctetsBuilder_ = new SingleFieldBuilderV3<Octets, Octets.Builder, OctetsOrBuilder>(this.getVOctets(), this.getParentForChildren(), this.isClean());
                    this.vOctets_ = null;
                }
                return this.vOctetsBuilder_;
            }
            
            @Override
            public boolean hasVDouble() {
                return (this.bitField0_ & 0x10) != 0x0;
            }
            
            @Override
            public double getVDouble() {
                return this.vDouble_;
            }
            
            public Builder setVDouble(final double value) {
                this.bitField0_ |= 0x10;
                this.vDouble_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVDouble() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.vDouble_ = 0.0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVFloat() {
                return (this.bitField0_ & 0x20) != 0x0;
            }
            
            @Override
            public float getVFloat() {
                return this.vFloat_;
            }
            
            public Builder setVFloat(final float value) {
                this.bitField0_ |= 0x20;
                this.vFloat_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVFloat() {
                this.bitField0_ &= 0xFFFFFFDF;
                this.vFloat_ = 0.0f;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVBool() {
                return (this.bitField0_ & 0x40) != 0x0;
            }
            
            @Override
            public boolean getVBool() {
                return this.vBool_;
            }
            
            public Builder setVBool(final boolean value) {
                this.bitField0_ |= 0x40;
                this.vBool_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVBool() {
                this.bitField0_ &= 0xFFFFFFBF;
                this.vBool_ = false;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasVString() {
                return (this.bitField0_ & 0x80) != 0x0;
            }
            
            @Override
            public String getVString() {
                if (this.vStringBuilder_ == null) {
                    return (this.vString_ == null) ? String.getDefaultInstance() : this.vString_;
                }
                return this.vStringBuilder_.getMessage();
            }
            
            public Builder setVString(final String value) {
                if (this.vStringBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.vString_ = value;
                    this.onChanged();
                }
                else {
                    this.vStringBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder setVString(final String.Builder builderForValue) {
                if (this.vStringBuilder_ == null) {
                    this.vString_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.vStringBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder mergeVString(final String value) {
                if (this.vStringBuilder_ == null) {
                    if ((this.bitField0_ & 0x80) != 0x0 && this.vString_ != null && this.vString_ != String.getDefaultInstance()) {
                        this.vString_ = String.newBuilder(this.vString_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.vString_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.vStringBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder clearVString() {
                if (this.vStringBuilder_ == null) {
                    this.vString_ = null;
                    this.onChanged();
                }
                else {
                    this.vStringBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFF7F;
                return this;
            }
            
            public String.Builder getVStringBuilder() {
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this.getVStringFieldBuilder().getBuilder();
            }
            
            @Override
            public StringOrBuilder getVStringOrBuilder() {
                if (this.vStringBuilder_ != null) {
                    return this.vStringBuilder_.getMessageOrBuilder();
                }
                return (this.vString_ == null) ? String.getDefaultInstance() : this.vString_;
            }
            
            private SingleFieldBuilderV3<String, String.Builder, StringOrBuilder> getVStringFieldBuilder() {
                if (this.vStringBuilder_ == null) {
                    this.vStringBuilder_ = new SingleFieldBuilderV3<String, String.Builder, StringOrBuilder>(this.getVString(), this.getParentForChildren(), this.isClean());
                    this.vString_ = null;
                }
                return this.vStringBuilder_;
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
        
        public interface OctetsOrBuilder extends MessageOrBuilder
        {
            boolean hasValue();
            
            ByteString getValue();
            
            boolean hasContentType();
            
            int getContentType();
        }
        
        public interface StringOrBuilder extends MessageOrBuilder
        {
            boolean hasValue();
            
            ByteString getValue();
            
            boolean hasCollation();
            
            long getCollation();
        }
    }
    
    public static final class Object extends GeneratedMessageV3 implements ObjectOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int FLD_FIELD_NUMBER = 1;
        private List<ObjectField> fld_;
        private byte memoizedIsInitialized;
        private static final Object DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Object> PARSER;
        
        private Object(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Object() {
            this.memoizedIsInitialized = -1;
            this.fld_ = Collections.emptyList();
        }
        
        @Override
        protected java.lang.Object newInstance(final UnusedPrivateParameter unused) {
            return new Object();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Object(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.fld_ = new ArrayList<ObjectField>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.fld_.add(input.readMessage(ObjectField.PARSER, extensionRegistry));
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
                    this.fld_ = Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_fieldAccessorTable.ensureFieldAccessorsInitialized(Object.class, Builder.class);
        }
        
        @Override
        public List<ObjectField> getFldList() {
            return this.fld_;
        }
        
        @Override
        public List<? extends ObjectFieldOrBuilder> getFldOrBuilderList() {
            return this.fld_;
        }
        
        @Override
        public int getFldCount() {
            return this.fld_.size();
        }
        
        @Override
        public ObjectField getFld(final int index) {
            return this.fld_.get(index);
        }
        
        @Override
        public ObjectFieldOrBuilder getFldOrBuilder(final int index) {
            return this.fld_.get(index);
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
            for (int i = 0; i < this.getFldCount(); ++i) {
                if (!this.getFld(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.fld_.size(); ++i) {
                output.writeMessage(1, this.fld_.get(i));
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
            for (int i = 0; i < this.fld_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.fld_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Object)) {
                return super.equals(obj);
            }
            final Object other = (Object)obj;
            return this.getFldList().equals(other.getFldList()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getFldCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFldList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Object parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Object.PARSER, input);
        }
        
        public static Object parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Object.PARSER, input, extensionRegistry);
        }
        
        public static Object parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Object.PARSER, input);
        }
        
        public static Object parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Object.PARSER, input, extensionRegistry);
        }
        
        public static Object parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Object.PARSER, input);
        }
        
        public static Object parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Object.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Object.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Object prototype) {
            return Object.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Object.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Object getDefaultInstance() {
            return Object.DEFAULT_INSTANCE;
        }
        
        public static Parser<Object> parser() {
            return Object.PARSER;
        }
        
        @Override
        public Parser<Object> getParserForType() {
            return Object.PARSER;
        }
        
        @Override
        public Object getDefaultInstanceForType() {
            return Object.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Object();
            PARSER = new AbstractParser<Object>() {
                @Override
                public MysqlxDatatypes.Object parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new MysqlxDatatypes.Object(input, extensionRegistry);
                }
            };
        }
        
        public static final class ObjectField extends GeneratedMessageV3 implements ObjectFieldOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int KEY_FIELD_NUMBER = 1;
            private volatile Object key_;
            public static final int VALUE_FIELD_NUMBER = 2;
            private Any value_;
            private byte memoizedIsInitialized;
            private static final ObjectField DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<ObjectField> PARSER;
            
            private ObjectField(final GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }
            
            private ObjectField() {
                this.memoizedIsInitialized = -1;
                this.key_ = "";
            }
            
            @Override
            protected Object newInstance(final UnusedPrivateParameter unused) {
                return new ObjectField();
            }
            
            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private ObjectField(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
                final int mutable_bitField0_ = 0;
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
                                final ByteString bs = input.readBytes();
                                this.bitField0_ |= 0x1;
                                this.key_ = bs;
                                continue;
                            }
                            case 18: {
                                Any.Builder subBuilder = null;
                                if ((this.bitField0_ & 0x2) != 0x0) {
                                    subBuilder = this.value_.toBuilder();
                                }
                                this.value_ = input.readMessage(Any.PARSER, extensionRegistry);
                                if (subBuilder != null) {
                                    subBuilder.mergeFrom(this.value_);
                                    this.value_ = subBuilder.buildPartial();
                                }
                                this.bitField0_ |= 0x2;
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
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_fieldAccessorTable.ensureFieldAccessorsInitialized(ObjectField.class, Builder.class);
            }
            
            @Override
            public boolean hasKey() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public String getKey() {
                final Object ref = this.key_;
                if (ref instanceof String) {
                    return (String)ref;
                }
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.key_ = s;
                }
                return s;
            }
            
            @Override
            public ByteString getKeyBytes() {
                final Object ref = this.key_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.key_ = b);
                }
                return (ByteString)ref;
            }
            
            @Override
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public Any getValue() {
                return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
            }
            
            @Override
            public AnyOrBuilder getValueOrBuilder() {
                return (this.value_ == null) ? Any.getDefaultInstance() : this.value_;
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
                if (!this.hasKey()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (!this.hasValue()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (!this.getValue().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
            
            @Override
            public void writeTo(final CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    GeneratedMessageV3.writeString(output, 1, this.key_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
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
                if ((this.bitField0_ & 0x1) != 0x0) {
                    size += GeneratedMessageV3.computeStringSize(1, this.key_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
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
                if (!(obj instanceof ObjectField)) {
                    return super.equals(obj);
                }
                final ObjectField other = (ObjectField)obj;
                return this.hasKey() == other.hasKey() && (!this.hasKey() || this.getKey().equals(other.getKey())) && this.hasValue() == other.hasValue() && (!this.hasValue() || this.getValue().equals(other.getValue())) && this.unknownFields.equals(other.unknownFields);
            }
            
            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + getDescriptor().hashCode();
                if (this.hasKey()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getKey().hashCode();
                }
                if (this.hasValue()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getValue().hashCode();
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static ObjectField parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(ObjectField.PARSER, input);
            }
            
            public static ObjectField parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(ObjectField.PARSER, input, extensionRegistry);
            }
            
            public static ObjectField parseDelimitedFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(ObjectField.PARSER, input);
            }
            
            public static ObjectField parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(ObjectField.PARSER, input, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(ObjectField.PARSER, input);
            }
            
            public static ObjectField parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(ObjectField.PARSER, input, extensionRegistry);
            }
            
            @Override
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return ObjectField.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final ObjectField prototype) {
                return ObjectField.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            @Override
            public Builder toBuilder() {
                return (this == ObjectField.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            @Override
            protected Builder newBuilderForType(final BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static ObjectField getDefaultInstance() {
                return ObjectField.DEFAULT_INSTANCE;
            }
            
            public static Parser<ObjectField> parser() {
                return ObjectField.PARSER;
            }
            
            @Override
            public Parser<ObjectField> getParserForType() {
                return ObjectField.PARSER;
            }
            
            @Override
            public ObjectField getDefaultInstanceForType() {
                return ObjectField.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new ObjectField();
                PARSER = new AbstractParser<ObjectField>() {
                    @Override
                    public ObjectField parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new ObjectField(input, extensionRegistry);
                    }
                };
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ObjectFieldOrBuilder
            {
                private int bitField0_;
                private Object key_;
                private Any value_;
                private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> valueBuilder_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_fieldAccessorTable.ensureFieldAccessorsInitialized(ObjectField.class, Builder.class);
                }
                
                private Builder() {
                    this.key_ = "";
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.key_ = "";
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (ObjectField.alwaysUseFieldBuilders) {
                        this.getValueFieldBuilder();
                    }
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.key_ = "";
                    this.bitField0_ &= 0xFFFFFFFE;
                    if (this.valueBuilder_ == null) {
                        this.value_ = null;
                    }
                    else {
                        this.valueBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_ObjectField_descriptor;
                }
                
                @Override
                public ObjectField getDefaultInstanceForType() {
                    return ObjectField.getDefaultInstance();
                }
                
                @Override
                public ObjectField build() {
                    final ObjectField result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw AbstractMessage.Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }
                
                @Override
                public ObjectField buildPartial() {
                    final ObjectField result = new ObjectField((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) != 0x0) {
                        to_bitField0_ |= 0x1;
                    }
                    result.key_ = this.key_;
                    if ((from_bitField0_ & 0x2) != 0x0) {
                        if (this.valueBuilder_ == null) {
                            result.value_ = this.value_;
                        }
                        else {
                            result.value_ = this.valueBuilder_.build();
                        }
                        to_bitField0_ |= 0x2;
                    }
                    result.bitField0_ = to_bitField0_;
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
                    if (other instanceof ObjectField) {
                        return this.mergeFrom((ObjectField)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final ObjectField other) {
                    if (other == ObjectField.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasKey()) {
                        this.bitField0_ |= 0x1;
                        this.key_ = other.key_;
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
                    return this.hasKey() && this.hasValue() && this.getValue().isInitialized();
                }
                
                @Override
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    ObjectField parsedMessage = null;
                    try {
                        parsedMessage = ObjectField.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (ObjectField)e.getUnfinishedMessage();
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
                public boolean hasKey() {
                    return (this.bitField0_ & 0x1) != 0x0;
                }
                
                @Override
                public String getKey() {
                    final Object ref = this.key_;
                    if (!(ref instanceof String)) {
                        final ByteString bs = (ByteString)ref;
                        final String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            this.key_ = s;
                        }
                        return s;
                    }
                    return (String)ref;
                }
                
                @Override
                public ByteString getKeyBytes() {
                    final Object ref = this.key_;
                    if (ref instanceof String) {
                        final ByteString b = ByteString.copyFromUtf8((String)ref);
                        return (ByteString)(this.key_ = b);
                    }
                    return (ByteString)ref;
                }
                
                public Builder setKey(final String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.key_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearKey() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.key_ = ObjectField.getDefaultInstance().getKey();
                    this.onChanged();
                    return this;
                }
                
                public Builder setKeyBytes(final ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.key_ = value;
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasValue() {
                    return (this.bitField0_ & 0x2) != 0x0;
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
                    this.bitField0_ |= 0x2;
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
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder mergeValue(final Any value) {
                    if (this.valueBuilder_ == null) {
                        if ((this.bitField0_ & 0x2) != 0x0 && this.value_ != null && this.value_ != Any.getDefaultInstance()) {
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
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder clearValue() {
                    if (this.valueBuilder_ == null) {
                        this.value_ = null;
                        this.onChanged();
                    }
                    else {
                        this.valueBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                public Any.Builder getValueBuilder() {
                    this.bitField0_ |= 0x2;
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ObjectOrBuilder
        {
            private int bitField0_;
            private List<ObjectField> fld_;
            private RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder> fldBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxDatatypes.Object.class, Builder.class);
            }
            
            private Builder() {
                this.fld_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.fld_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (MysqlxDatatypes.Object.alwaysUseFieldBuilders) {
                    this.getFldFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                if (this.fldBuilder_ == null) {
                    this.fld_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.fldBuilder_.clear();
                }
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Object_descriptor;
            }
            
            @Override
            public MysqlxDatatypes.Object getDefaultInstanceForType() {
                return MysqlxDatatypes.Object.getDefaultInstance();
            }
            
            @Override
            public MysqlxDatatypes.Object build() {
                final MysqlxDatatypes.Object result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public MysqlxDatatypes.Object buildPartial() {
                final MysqlxDatatypes.Object result = new MysqlxDatatypes.Object((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.fldBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) != 0x0) {
                        this.fld_ = Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.fld_ = this.fld_;
                }
                else {
                    result.fld_ = this.fldBuilder_.build();
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
                if (other instanceof MysqlxDatatypes.Object) {
                    return this.mergeFrom((MysqlxDatatypes.Object)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final MysqlxDatatypes.Object other) {
                if (other == MysqlxDatatypes.Object.getDefaultInstance()) {
                    return this;
                }
                if (this.fldBuilder_ == null) {
                    if (!other.fld_.isEmpty()) {
                        if (this.fld_.isEmpty()) {
                            this.fld_ = other.fld_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureFldIsMutable();
                            this.fld_.addAll(other.fld_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.fld_.isEmpty()) {
                    if (this.fldBuilder_.isEmpty()) {
                        this.fldBuilder_.dispose();
                        this.fldBuilder_ = null;
                        this.fld_ = other.fld_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.fldBuilder_ = (MysqlxDatatypes.Object.alwaysUseFieldBuilders ? this.getFldFieldBuilder() : null);
                    }
                    else {
                        this.fldBuilder_.addAllMessages(other.fld_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getFldCount(); ++i) {
                    if (!this.getFld(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                MysqlxDatatypes.Object parsedMessage = null;
                try {
                    parsedMessage = MysqlxDatatypes.Object.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (MysqlxDatatypes.Object)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureFldIsMutable() {
                if ((this.bitField0_ & 0x1) == 0x0) {
                    this.fld_ = new ArrayList<ObjectField>(this.fld_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            @Override
            public List<ObjectField> getFldList() {
                if (this.fldBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                }
                return this.fldBuilder_.getMessageList();
            }
            
            @Override
            public int getFldCount() {
                if (this.fldBuilder_ == null) {
                    return this.fld_.size();
                }
                return this.fldBuilder_.getCount();
            }
            
            @Override
            public ObjectField getFld(final int index) {
                if (this.fldBuilder_ == null) {
                    return this.fld_.get(index);
                }
                return this.fldBuilder_.getMessage(index);
            }
            
            public Builder setFld(final int index, final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.setMessage(index, value);
                }
                return this;
            }
            
            public Builder setFld(final int index, final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addFld(final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.add(value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(value);
                }
                return this;
            }
            
            public Builder addFld(final int index, final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(index, value);
                }
                return this;
            }
            
            public Builder addFld(final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            
            public Builder addFld(final int index, final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllFld(final Iterable<? extends ObjectField> values) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.fld_);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addAllMessages(values);
                }
                return this;
            }
            
            public Builder clearFld() {
                if (this.fldBuilder_ == null) {
                    this.fld_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeFld(final int index) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.remove(index);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.remove(index);
                }
                return this;
            }
            
            public ObjectField.Builder getFldBuilder(final int index) {
                return this.getFldFieldBuilder().getBuilder(index);
            }
            
            @Override
            public ObjectFieldOrBuilder getFldOrBuilder(final int index) {
                if (this.fldBuilder_ == null) {
                    return this.fld_.get(index);
                }
                return this.fldBuilder_.getMessageOrBuilder(index);
            }
            
            @Override
            public List<? extends ObjectFieldOrBuilder> getFldOrBuilderList() {
                if (this.fldBuilder_ != null) {
                    return this.fldBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ObjectFieldOrBuilder>)this.fld_);
            }
            
            public ObjectField.Builder addFldBuilder() {
                return this.getFldFieldBuilder().addBuilder(ObjectField.getDefaultInstance());
            }
            
            public ObjectField.Builder addFldBuilder(final int index) {
                return this.getFldFieldBuilder().addBuilder(index, ObjectField.getDefaultInstance());
            }
            
            public List<ObjectField.Builder> getFldBuilderList() {
                return this.getFldFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder> getFldFieldBuilder() {
                if (this.fldBuilder_ == null) {
                    this.fldBuilder_ = new RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder>(this.fld_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                    this.fld_ = null;
                }
                return this.fldBuilder_;
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
        
        public interface ObjectFieldOrBuilder extends MessageOrBuilder
        {
            boolean hasKey();
            
            String getKey();
            
            ByteString getKeyBytes();
            
            boolean hasValue();
            
            Any getValue();
            
            AnyOrBuilder getValueOrBuilder();
        }
    }
    
    public static final class Array extends GeneratedMessageV3 implements ArrayOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<Any> value_;
        private byte memoizedIsInitialized;
        private static final Array DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Array> PARSER;
        
        private Array(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Array() {
            this.memoizedIsInitialized = -1;
            this.value_ = Collections.emptyList();
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Array();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Array(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<Any>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.value_.add(input.readMessage(Any.PARSER, extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList((List<? extends Any>)this.value_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_fieldAccessorTable.ensureFieldAccessorsInitialized(Array.class, Builder.class);
        }
        
        @Override
        public List<Any> getValueList() {
            return this.value_;
        }
        
        @Override
        public List<? extends AnyOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }
        
        @Override
        public int getValueCount() {
            return this.value_.size();
        }
        
        @Override
        public Any getValue(final int index) {
            return this.value_.get(index);
        }
        
        @Override
        public AnyOrBuilder getValueOrBuilder(final int index) {
            return this.value_.get(index);
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
            for (int i = 0; i < this.getValueCount(); ++i) {
                if (!this.getValue(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.value_.size(); ++i) {
                output.writeMessage(1, this.value_.get(i));
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
            for (int i = 0; i < this.value_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Array)) {
                return super.equals(obj);
            }
            final Array other = (Array)obj;
            return this.getValueList().equals(other.getValueList()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Array parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Array.PARSER, input);
        }
        
        public static Array parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Array.PARSER, input, extensionRegistry);
        }
        
        public static Array parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Array.PARSER, input);
        }
        
        public static Array parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Array.PARSER, input, extensionRegistry);
        }
        
        public static Array parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Array.PARSER, input);
        }
        
        public static Array parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Array.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Array.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Array prototype) {
            return Array.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Array.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Array getDefaultInstance() {
            return Array.DEFAULT_INSTANCE;
        }
        
        public static Parser<Array> parser() {
            return Array.PARSER;
        }
        
        @Override
        public Parser<Array> getParserForType() {
            return Array.PARSER;
        }
        
        @Override
        public Array getDefaultInstanceForType() {
            return Array.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Array();
            PARSER = new AbstractParser<Array>() {
                @Override
                public Array parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Array(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ArrayOrBuilder
        {
            private int bitField0_;
            private List<Any> value_;
            private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_fieldAccessorTable.ensureFieldAccessorsInitialized(Array.class, Builder.class);
            }
            
            private Builder() {
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Array.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.valueBuilder_.clear();
                }
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Array_descriptor;
            }
            
            @Override
            public Array getDefaultInstanceForType() {
                return Array.getDefaultInstance();
            }
            
            @Override
            public Array build() {
                final Array result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Array buildPartial() {
                final Array result = new Array((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) != 0x0) {
                        this.value_ = Collections.unmodifiableList((List<? extends Any>)this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
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
                if (other instanceof Array) {
                    return this.mergeFrom((Array)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Array other) {
                if (other == Array.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = (Array.alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null);
                    }
                    else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getValueCount(); ++i) {
                    if (!this.getValue(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Array parsedMessage = null;
                try {
                    parsedMessage = Array.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Array)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 0x1) == 0x0) {
                    this.value_ = new ArrayList<Any>(this.value_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            @Override
            public List<Any> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Any>)this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }
            
            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }
            
            @Override
            public Any getValue(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }
            
            public Builder setValue(final int index, final Any value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }
            
            public Builder setValue(final int index, final Any.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addValue(final Any value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }
            
            public Builder addValue(final int index, final Any value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }
            
            public Builder addValue(final Any.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            
            public Builder addValue(final int index, final Any.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllValue(final Iterable<? extends Any> values) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.value_);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addAllMessages(values);
                }
                return this;
            }
            
            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeValue(final int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }
            
            public Any.Builder getValueBuilder(final int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }
            
            @Override
            public AnyOrBuilder getValueOrBuilder(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }
            
            @Override
            public List<? extends AnyOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends AnyOrBuilder>)this.value_);
            }
            
            public Any.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(Any.getDefaultInstance());
            }
            
            public Any.Builder addValueBuilder(final int index) {
                return this.getValueFieldBuilder().addBuilder(index, Any.getDefaultInstance());
            }
            
            public List<Any.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder>(this.value_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
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
    
    public static final class Any extends GeneratedMessageV3 implements AnyOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int SCALAR_FIELD_NUMBER = 2;
        private Scalar scalar_;
        public static final int OBJ_FIELD_NUMBER = 3;
        private MysqlxDatatypes.Object obj_;
        public static final int ARRAY_FIELD_NUMBER = 4;
        private Array array_;
        private byte memoizedIsInitialized;
        private static final Any DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Any> PARSER;
        
        private Any(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Any() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Any();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Any(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final int mutable_bitField0_ = 0;
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
                            final Type value = Type.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 18: {
                            Scalar.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) != 0x0) {
                                subBuilder = this.scalar_.toBuilder();
                            }
                            this.scalar_ = input.readMessage(Scalar.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.scalar_);
                                this.scalar_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 26: {
                            MysqlxDatatypes.Object.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x4) != 0x0) {
                                subBuilder2 = this.obj_.toBuilder();
                            }
                            this.obj_ = input.readMessage(MysqlxDatatypes.Object.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.obj_);
                                this.obj_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 34: {
                            Array.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 0x8) != 0x0) {
                                subBuilder3 = this.array_.toBuilder();
                            }
                            this.array_ = input.readMessage(Array.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.array_);
                                this.array_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
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
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
        }
        
        @Override
        public boolean hasType() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public Type getType() {
            final Type result = Type.valueOf(this.type_);
            return (result == null) ? Type.SCALAR : result;
        }
        
        @Override
        public boolean hasScalar() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public Scalar getScalar() {
            return (this.scalar_ == null) ? Scalar.getDefaultInstance() : this.scalar_;
        }
        
        @Override
        public ScalarOrBuilder getScalarOrBuilder() {
            return (this.scalar_ == null) ? Scalar.getDefaultInstance() : this.scalar_;
        }
        
        @Override
        public boolean hasObj() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public MysqlxDatatypes.Object getObj() {
            return (this.obj_ == null) ? MysqlxDatatypes.Object.getDefaultInstance() : this.obj_;
        }
        
        @Override
        public ObjectOrBuilder getObjOrBuilder() {
            return (this.obj_ == null) ? MysqlxDatatypes.Object.getDefaultInstance() : this.obj_;
        }
        
        @Override
        public boolean hasArray() {
            return (this.bitField0_ & 0x8) != 0x0;
        }
        
        @Override
        public Array getArray() {
            return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
        }
        
        @Override
        public ArrayOrBuilder getArrayOrBuilder() {
            return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
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
            if (!this.hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasScalar() && !this.getScalar().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasObj() && !this.getObj().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasArray() && !this.getArray().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeEnum(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeMessage(2, this.getScalar());
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeMessage(3, this.getObj());
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                output.writeMessage(4, this.getArray());
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
            if ((this.bitField0_ & 0x1) != 0x0) {
                size += CodedOutputStream.computeEnumSize(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeMessageSize(2, this.getScalar());
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeMessageSize(3, this.getObj());
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                size += CodedOutputStream.computeMessageSize(4, this.getArray());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Any)) {
                return super.equals(obj);
            }
            final Any other = (Any)obj;
            return this.hasType() == other.hasType() && (!this.hasType() || this.type_ == other.type_) && this.hasScalar() == other.hasScalar() && (!this.hasScalar() || this.getScalar().equals(other.getScalar())) && this.hasObj() == other.hasObj() && (!this.hasObj() || this.getObj().equals(other.getObj())) && this.hasArray() == other.hasArray() && (!this.hasArray() || this.getArray().equals(other.getArray())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasType()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.type_;
            }
            if (this.hasScalar()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getScalar().hashCode();
            }
            if (this.hasObj()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getObj().hashCode();
            }
            if (this.hasArray()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getArray().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Any parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data);
        }
        
        public static Any parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Any parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data);
        }
        
        public static Any parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Any parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data);
        }
        
        public static Any parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Any.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Any parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Any.PARSER, input);
        }
        
        public static Any parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Any.PARSER, input, extensionRegistry);
        }
        
        public static Any parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Any.PARSER, input);
        }
        
        public static Any parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Any.PARSER, input, extensionRegistry);
        }
        
        public static Any parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Any.PARSER, input);
        }
        
        public static Any parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Any.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Any.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Any prototype) {
            return Any.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Any.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Any getDefaultInstance() {
            return Any.DEFAULT_INSTANCE;
        }
        
        public static Parser<Any> parser() {
            return Any.PARSER;
        }
        
        @Override
        public Parser<Any> getParserForType() {
            return Any.PARSER;
        }
        
        @Override
        public Any getDefaultInstanceForType() {
            return Any.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Any();
            PARSER = new AbstractParser<Any>() {
                @Override
                public Any parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Any(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            SCALAR(1), 
            OBJECT(2), 
            ARRAY(3);
            
            public static final int SCALAR_VALUE = 1;
            public static final int OBJECT_VALUE = 2;
            public static final int ARRAY_VALUE = 3;
            private static final Internal.EnumLiteMap<Type> internalValueMap;
            private static final Type[] VALUES;
            private final int value;
            
            @Override
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Type valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Type forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Type.SCALAR;
                    }
                    case 2: {
                        return Type.OBJECT;
                    }
                    case 3: {
                        return Type.ARRAY;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return Type.internalValueMap;
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
                return Any.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Type valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Type.VALUES[desc.getIndex()];
            }
            
            private Type(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = new Internal.EnumLiteMap<Type>() {
                    @Override
                    public Type findValueByNumber(final int number) {
                        return Type.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AnyOrBuilder
        {
            private int bitField0_;
            private int type_;
            private Scalar scalar_;
            private SingleFieldBuilderV3<Scalar, Scalar.Builder, ScalarOrBuilder> scalarBuilder_;
            private MysqlxDatatypes.Object obj_;
            private SingleFieldBuilderV3<MysqlxDatatypes.Object, MysqlxDatatypes.Object.Builder, ObjectOrBuilder> objBuilder_;
            private Array array_;
            private SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder> arrayBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Any.alwaysUseFieldBuilders) {
                    this.getScalarFieldBuilder();
                    this.getObjFieldBuilder();
                    this.getArrayFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.scalarBuilder_ == null) {
                    this.scalar_ = null;
                }
                else {
                    this.scalarBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.objBuilder_ == null) {
                    this.obj_ = null;
                }
                else {
                    this.objBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                if (this.arrayBuilder_ == null) {
                    this.array_ = null;
                }
                else {
                    this.arrayBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxDatatypes.internal_static_Mysqlx_Datatypes_Any_descriptor;
            }
            
            @Override
            public Any getDefaultInstanceForType() {
                return Any.getDefaultInstance();
            }
            
            @Override
            public Any build() {
                final Any result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Any buildPartial() {
                final Any result = new Any((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) != 0x0) {
                    if (this.scalarBuilder_ == null) {
                        result.scalar_ = this.scalar_;
                    }
                    else {
                        result.scalar_ = this.scalarBuilder_.build();
                    }
                    to_bitField0_ |= 0x2;
                }
                if ((from_bitField0_ & 0x4) != 0x0) {
                    if (this.objBuilder_ == null) {
                        result.obj_ = this.obj_;
                    }
                    else {
                        result.obj_ = this.objBuilder_.build();
                    }
                    to_bitField0_ |= 0x4;
                }
                if ((from_bitField0_ & 0x8) != 0x0) {
                    if (this.arrayBuilder_ == null) {
                        result.array_ = this.array_;
                    }
                    else {
                        result.array_ = this.arrayBuilder_.build();
                    }
                    to_bitField0_ |= 0x8;
                }
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof Any) {
                    return this.mergeFrom((Any)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Any other) {
                if (other == Any.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasScalar()) {
                    this.mergeScalar(other.getScalar());
                }
                if (other.hasObj()) {
                    this.mergeObj(other.getObj());
                }
                if (other.hasArray()) {
                    this.mergeArray(other.getArray());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasType() && (!this.hasScalar() || this.getScalar().isInitialized()) && (!this.hasObj() || this.getObj().isInitialized()) && (!this.hasArray() || this.getArray().isInitialized());
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Any parsedMessage = null;
                try {
                    parsedMessage = Any.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Any)e.getUnfinishedMessage();
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
            public boolean hasType() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.SCALAR : result;
            }
            
            public Builder setType(final Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.type_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearType() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.type_ = 1;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasScalar() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public Scalar getScalar() {
                if (this.scalarBuilder_ == null) {
                    return (this.scalar_ == null) ? Scalar.getDefaultInstance() : this.scalar_;
                }
                return this.scalarBuilder_.getMessage();
            }
            
            public Builder setScalar(final Scalar value) {
                if (this.scalarBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.scalar_ = value;
                    this.onChanged();
                }
                else {
                    this.scalarBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder setScalar(final Scalar.Builder builderForValue) {
                if (this.scalarBuilder_ == null) {
                    this.scalar_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.scalarBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder mergeScalar(final Scalar value) {
                if (this.scalarBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) != 0x0 && this.scalar_ != null && this.scalar_ != Scalar.getDefaultInstance()) {
                        this.scalar_ = Scalar.newBuilder(this.scalar_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.scalar_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.scalarBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder clearScalar() {
                if (this.scalarBuilder_ == null) {
                    this.scalar_ = null;
                    this.onChanged();
                }
                else {
                    this.scalarBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Scalar.Builder getScalarBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return this.getScalarFieldBuilder().getBuilder();
            }
            
            @Override
            public ScalarOrBuilder getScalarOrBuilder() {
                if (this.scalarBuilder_ != null) {
                    return this.scalarBuilder_.getMessageOrBuilder();
                }
                return (this.scalar_ == null) ? Scalar.getDefaultInstance() : this.scalar_;
            }
            
            private SingleFieldBuilderV3<Scalar, Scalar.Builder, ScalarOrBuilder> getScalarFieldBuilder() {
                if (this.scalarBuilder_ == null) {
                    this.scalarBuilder_ = new SingleFieldBuilderV3<Scalar, Scalar.Builder, ScalarOrBuilder>(this.getScalar(), this.getParentForChildren(), this.isClean());
                    this.scalar_ = null;
                }
                return this.scalarBuilder_;
            }
            
            @Override
            public boolean hasObj() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public MysqlxDatatypes.Object getObj() {
                if (this.objBuilder_ == null) {
                    return (this.obj_ == null) ? MysqlxDatatypes.Object.getDefaultInstance() : this.obj_;
                }
                return this.objBuilder_.getMessage();
            }
            
            public Builder setObj(final MysqlxDatatypes.Object value) {
                if (this.objBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.obj_ = value;
                    this.onChanged();
                }
                else {
                    this.objBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder setObj(final MysqlxDatatypes.Object.Builder builderForValue) {
                if (this.objBuilder_ == null) {
                    this.obj_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.objBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder mergeObj(final MysqlxDatatypes.Object value) {
                if (this.objBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) != 0x0 && this.obj_ != null && this.obj_ != MysqlxDatatypes.Object.getDefaultInstance()) {
                        this.obj_ = MysqlxDatatypes.Object.newBuilder(this.obj_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.obj_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.objBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder clearObj() {
                if (this.objBuilder_ == null) {
                    this.obj_ = null;
                    this.onChanged();
                }
                else {
                    this.objBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public MysqlxDatatypes.Object.Builder getObjBuilder() {
                this.bitField0_ |= 0x4;
                this.onChanged();
                return this.getObjFieldBuilder().getBuilder();
            }
            
            @Override
            public ObjectOrBuilder getObjOrBuilder() {
                if (this.objBuilder_ != null) {
                    return this.objBuilder_.getMessageOrBuilder();
                }
                return (this.obj_ == null) ? MysqlxDatatypes.Object.getDefaultInstance() : this.obj_;
            }
            
            private SingleFieldBuilderV3<MysqlxDatatypes.Object, MysqlxDatatypes.Object.Builder, ObjectOrBuilder> getObjFieldBuilder() {
                if (this.objBuilder_ == null) {
                    this.objBuilder_ = new SingleFieldBuilderV3<MysqlxDatatypes.Object, MysqlxDatatypes.Object.Builder, ObjectOrBuilder>(this.getObj(), this.getParentForChildren(), this.isClean());
                    this.obj_ = null;
                }
                return this.objBuilder_;
            }
            
            @Override
            public boolean hasArray() {
                return (this.bitField0_ & 0x8) != 0x0;
            }
            
            @Override
            public Array getArray() {
                if (this.arrayBuilder_ == null) {
                    return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
                }
                return this.arrayBuilder_.getMessage();
            }
            
            public Builder setArray(final Array value) {
                if (this.arrayBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.array_ = value;
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder setArray(final Array.Builder builderForValue) {
                if (this.arrayBuilder_ == null) {
                    this.array_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder mergeArray(final Array value) {
                if (this.arrayBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) != 0x0 && this.array_ != null && this.array_ != Array.getDefaultInstance()) {
                        this.array_ = Array.newBuilder(this.array_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.array_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder clearArray() {
                if (this.arrayBuilder_ == null) {
                    this.array_ = null;
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public Array.Builder getArrayBuilder() {
                this.bitField0_ |= 0x8;
                this.onChanged();
                return this.getArrayFieldBuilder().getBuilder();
            }
            
            @Override
            public ArrayOrBuilder getArrayOrBuilder() {
                if (this.arrayBuilder_ != null) {
                    return this.arrayBuilder_.getMessageOrBuilder();
                }
                return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
            }
            
            private SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder> getArrayFieldBuilder() {
                if (this.arrayBuilder_ == null) {
                    this.arrayBuilder_ = new SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder>(this.getArray(), this.getParentForChildren(), this.isClean());
                    this.array_ = null;
                }
                return this.arrayBuilder_;
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
    
    public interface ScalarOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        Scalar.Type getType();
        
        boolean hasVSignedInt();
        
        long getVSignedInt();
        
        boolean hasVUnsignedInt();
        
        long getVUnsignedInt();
        
        boolean hasVOctets();
        
        Scalar.Octets getVOctets();
        
        Scalar.OctetsOrBuilder getVOctetsOrBuilder();
        
        boolean hasVDouble();
        
        double getVDouble();
        
        boolean hasVFloat();
        
        float getVFloat();
        
        boolean hasVBool();
        
        boolean getVBool();
        
        boolean hasVString();
        
        Scalar.String getVString();
        
        Scalar.StringOrBuilder getVStringOrBuilder();
    }
    
    public interface AnyOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        Any.Type getType();
        
        boolean hasScalar();
        
        Scalar getScalar();
        
        ScalarOrBuilder getScalarOrBuilder();
        
        boolean hasObj();
        
        MysqlxDatatypes.Object getObj();
        
        ObjectOrBuilder getObjOrBuilder();
        
        boolean hasArray();
        
        Array getArray();
        
        ArrayOrBuilder getArrayOrBuilder();
    }
    
    public interface ObjectOrBuilder extends MessageOrBuilder
    {
        List<MysqlxDatatypes.Object.ObjectField> getFldList();
        
        MysqlxDatatypes.Object.ObjectField getFld(final int p0);
        
        int getFldCount();
        
        List<? extends MysqlxDatatypes.Object.ObjectFieldOrBuilder> getFldOrBuilderList();
        
        MysqlxDatatypes.Object.ObjectFieldOrBuilder getFldOrBuilder(final int p0);
    }
    
    public interface ArrayOrBuilder extends MessageOrBuilder
    {
        List<Any> getValueList();
        
        Any getValue(final int p0);
        
        int getValueCount();
        
        List<? extends AnyOrBuilder> getValueOrBuilderList();
        
        AnyOrBuilder getValueOrBuilder(final int p0);
    }
}
