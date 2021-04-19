package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;
import com.google.protobuf.*;

public final class MysqlxConnection
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Capability_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Capability_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Capabilities_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Close_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Compression_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Compression_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxConnection() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxConnection.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0017mysqlx_connection.proto\u0012\u0011Mysqlx.Connection\u001a\u0016mysqlx_datatypes.proto\u001a\fmysqlx.proto\"@\n\nCapability\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012$\n\u0005value\u0018\u0002 \u0002(\u000b2\u0015.Mysqlx.Datatypes.Any\"I\n\fCapabilities\u00123\n\fcapabilities\u0018\u0001 \u0003(\u000b2\u001d.Mysqlx.Connection.Capability:\u0004\u0090\u00ea0\u0002\"\u0017\n\u000fCapabilitiesGet:\u0004\u0088\u00ea0\u0001\"N\n\u000fCapabilitiesSet\u00125\n\fcapabilities\u0018\u0001 \u0002(\u000b2\u001f.Mysqlx.Connection.Capabilities:\u0004\u0088\u00ea0\u0002\"\r\n\u0005Close:\u0004\u0088\u00ea0\u0003\"¯\u0001\n\u000bCompression\u0012\u0019\n\u0011uncompressed_size\u0018\u0001 \u0001(\u0004\u00124\n\u000fserver_messages\u0018\u0002 \u0001(\u000e2\u001b.Mysqlx.ServerMessages.Type\u00124\n\u000fclient_messages\u0018\u0003 \u0001(\u000e2\u001b.Mysqlx.ClientMessages.Type\u0012\u000f\n\u0007payload\u0018\u0004 \u0002(\f:\b\u0090\u00ea0\u0013\u0088\u00ea0.B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxConnection.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { MysqlxDatatypes.getDescriptor(), Mysqlx.getDescriptor() });
        internal_static_Mysqlx_Connection_Capability_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Connection_Capability_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor, new String[] { "Name", "Value" });
        internal_static_Mysqlx_Connection_Capabilities_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor, new String[] { "Capabilities" });
        internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor, new String[0]);
        internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor, new String[] { "Capabilities" });
        internal_static_Mysqlx_Connection_Close_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Connection_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor, new String[0]);
        internal_static_Mysqlx_Connection_Compression_descriptor = getDescriptor().getMessageTypes().get(5);
        internal_static_Mysqlx_Connection_Compression_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Compression_descriptor, new String[] { "UncompressedSize", "ServerMessages", "ClientMessages", "Payload" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.clientMessageId);
        registry.add(Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxConnection.descriptor, registry);
        MysqlxDatatypes.getDescriptor();
        Mysqlx.getDescriptor();
    }
    
    public static final class Capability extends GeneratedMessageV3 implements CapabilityOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int VALUE_FIELD_NUMBER = 2;
        private MysqlxDatatypes.Any value_;
        private byte memoizedIsInitialized;
        private static final Capability DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Capability> PARSER;
        
        private Capability(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Capability() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Capability();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Capability(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.name_ = bs;
                            continue;
                        }
                        case 18: {
                            MysqlxDatatypes.Any.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) != 0x0) {
                                subBuilder = this.value_.toBuilder();
                            }
                            this.value_ = input.readMessage(MysqlxDatatypes.Any.PARSER, extensionRegistry);
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_fieldAccessorTable.ensureFieldAccessorsInitialized(Capability.class, Builder.class);
        }
        
        @Override
        public boolean hasName() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public String getName() {
            final Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
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
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public MysqlxDatatypes.Any getValue() {
            return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
        }
        
        @Override
        public MysqlxDatatypes.AnyOrBuilder getValueOrBuilder() {
            return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
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
            if (!this.hasName()) {
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
                GeneratedMessageV3.writeString(output, 1, this.name_);
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
                size += GeneratedMessageV3.computeStringSize(1, this.name_);
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
            if (!(obj instanceof Capability)) {
                return super.equals(obj);
            }
            final Capability other = (Capability)obj;
            return this.hasName() == other.hasName() && (!this.hasName() || this.getName().equals(other.getName())) && this.hasValue() == other.hasValue() && (!this.hasValue() || this.getValue().equals(other.getValue())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasName()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getName().hashCode();
            }
            if (this.hasValue()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getValue().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Capability parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capability.PARSER, input);
        }
        
        public static Capability parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capability.PARSER, input, extensionRegistry);
        }
        
        public static Capability parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Capability.PARSER, input);
        }
        
        public static Capability parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Capability.PARSER, input, extensionRegistry);
        }
        
        public static Capability parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capability.PARSER, input);
        }
        
        public static Capability parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capability.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Capability.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Capability prototype) {
            return Capability.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Capability.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Capability getDefaultInstance() {
            return Capability.DEFAULT_INSTANCE;
        }
        
        public static Parser<Capability> parser() {
            return Capability.PARSER;
        }
        
        @Override
        public Parser<Capability> getParserForType() {
            return Capability.PARSER;
        }
        
        @Override
        public Capability getDefaultInstanceForType() {
            return Capability.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Capability();
            PARSER = new AbstractParser<Capability>() {
                @Override
                public Capability parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Capability(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilityOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private MysqlxDatatypes.Any value_;
            private SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_fieldAccessorTable.ensureFieldAccessorsInitialized(Capability.class, Builder.class);
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
                if (Capability.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.name_ = "";
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
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
            }
            
            @Override
            public Capability getDefaultInstanceForType() {
                return Capability.getDefaultInstance();
            }
            
            @Override
            public Capability build() {
                final Capability result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Capability buildPartial() {
                final Capability result = new Capability((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
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
                if (other instanceof Capability) {
                    return this.mergeFrom((Capability)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Capability other) {
                if (other == Capability.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
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
                return this.hasName() && this.hasValue() && this.getValue().isInitialized();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Capability parsedMessage = null;
                try {
                    parsedMessage = Capability.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Capability)e.getUnfinishedMessage();
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
            public boolean hasName() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public String getName() {
                final Object ref = this.name_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.name_ = s;
                    }
                    return s;
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
                this.bitField0_ |= 0x1;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearName() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.name_ = Capability.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }
            
            public Builder setNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public MysqlxDatatypes.Any getValue() {
                if (this.valueBuilder_ == null) {
                    return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
                }
                return this.valueBuilder_.getMessage();
            }
            
            public Builder setValue(final MysqlxDatatypes.Any value) {
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
            
            public Builder setValue(final MysqlxDatatypes.Any.Builder builderForValue) {
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
            
            public Builder mergeValue(final MysqlxDatatypes.Any value) {
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) != 0x0 && this.value_ != null && this.value_ != MysqlxDatatypes.Any.getDefaultInstance()) {
                        this.value_ = MysqlxDatatypes.Any.newBuilder(this.value_).mergeFrom(value).buildPartial();
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
            
            public MysqlxDatatypes.Any.Builder getValueBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return this.getValueFieldBuilder().getBuilder();
            }
            
            @Override
            public MysqlxDatatypes.AnyOrBuilder getValueOrBuilder() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilder();
                }
                return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
            }
            
            private SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder>(this.getValue(), this.getParentForChildren(), this.isClean());
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
    
    public static final class Capabilities extends GeneratedMessageV3 implements CapabilitiesOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int CAPABILITIES_FIELD_NUMBER = 1;
        private List<Capability> capabilities_;
        private byte memoizedIsInitialized;
        private static final Capabilities DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Capabilities> PARSER;
        
        private Capabilities(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Capabilities() {
            this.memoizedIsInitialized = -1;
            this.capabilities_ = Collections.emptyList();
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Capabilities();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Capabilities(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.capabilities_ = new ArrayList<Capability>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.capabilities_.add(input.readMessage(Capability.PARSER, extensionRegistry));
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
                    this.capabilities_ = Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable.ensureFieldAccessorsInitialized(Capabilities.class, Builder.class);
        }
        
        @Override
        public List<Capability> getCapabilitiesList() {
            return this.capabilities_;
        }
        
        @Override
        public List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList() {
            return this.capabilities_;
        }
        
        @Override
        public int getCapabilitiesCount() {
            return this.capabilities_.size();
        }
        
        @Override
        public Capability getCapabilities(final int index) {
            return this.capabilities_.get(index);
        }
        
        @Override
        public CapabilityOrBuilder getCapabilitiesOrBuilder(final int index) {
            return this.capabilities_.get(index);
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
            for (int i = 0; i < this.getCapabilitiesCount(); ++i) {
                if (!this.getCapabilities(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.capabilities_.size(); ++i) {
                output.writeMessage(1, this.capabilities_.get(i));
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
            for (int i = 0; i < this.capabilities_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, this.capabilities_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Capabilities)) {
                return super.equals(obj);
            }
            final Capabilities other = (Capabilities)obj;
            return this.getCapabilitiesList().equals(other.getCapabilitiesList()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getCapabilitiesCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCapabilitiesList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Capabilities parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capabilities.PARSER, input);
        }
        
        public static Capabilities parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capabilities.PARSER, input, extensionRegistry);
        }
        
        public static Capabilities parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Capabilities.PARSER, input);
        }
        
        public static Capabilities parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Capabilities.PARSER, input, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capabilities.PARSER, input);
        }
        
        public static Capabilities parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Capabilities.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Capabilities.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Capabilities prototype) {
            return Capabilities.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Capabilities.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Capabilities getDefaultInstance() {
            return Capabilities.DEFAULT_INSTANCE;
        }
        
        public static Parser<Capabilities> parser() {
            return Capabilities.PARSER;
        }
        
        @Override
        public Parser<Capabilities> getParserForType() {
            return Capabilities.PARSER;
        }
        
        @Override
        public Capabilities getDefaultInstanceForType() {
            return Capabilities.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Capabilities();
            PARSER = new AbstractParser<Capabilities>() {
                @Override
                public Capabilities parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Capabilities(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesOrBuilder
        {
            private int bitField0_;
            private List<Capability> capabilities_;
            private RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder> capabilitiesBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable.ensureFieldAccessorsInitialized(Capabilities.class, Builder.class);
            }
            
            private Builder() {
                this.capabilities_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.capabilities_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Capabilities.alwaysUseFieldBuilders) {
                    this.getCapabilitiesFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
            }
            
            @Override
            public Capabilities getDefaultInstanceForType() {
                return Capabilities.getDefaultInstance();
            }
            
            @Override
            public Capabilities build() {
                final Capabilities result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Capabilities buildPartial() {
                final Capabilities result = new Capabilities((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.capabilitiesBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) != 0x0) {
                        this.capabilities_ = Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.capabilities_ = this.capabilities_;
                }
                else {
                    result.capabilities_ = this.capabilitiesBuilder_.build();
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
                if (other instanceof Capabilities) {
                    return this.mergeFrom((Capabilities)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Capabilities other) {
                if (other == Capabilities.getDefaultInstance()) {
                    return this;
                }
                if (this.capabilitiesBuilder_ == null) {
                    if (!other.capabilities_.isEmpty()) {
                        if (this.capabilities_.isEmpty()) {
                            this.capabilities_ = other.capabilities_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureCapabilitiesIsMutable();
                            this.capabilities_.addAll(other.capabilities_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.capabilities_.isEmpty()) {
                    if (this.capabilitiesBuilder_.isEmpty()) {
                        this.capabilitiesBuilder_.dispose();
                        this.capabilitiesBuilder_ = null;
                        this.capabilities_ = other.capabilities_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.capabilitiesBuilder_ = (Capabilities.alwaysUseFieldBuilders ? this.getCapabilitiesFieldBuilder() : null);
                    }
                    else {
                        this.capabilitiesBuilder_.addAllMessages(other.capabilities_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getCapabilitiesCount(); ++i) {
                    if (!this.getCapabilities(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Capabilities parsedMessage = null;
                try {
                    parsedMessage = Capabilities.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Capabilities)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureCapabilitiesIsMutable() {
                if ((this.bitField0_ & 0x1) == 0x0) {
                    this.capabilities_ = new ArrayList<Capability>(this.capabilities_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            @Override
            public List<Capability> getCapabilitiesList() {
                if (this.capabilitiesBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                }
                return this.capabilitiesBuilder_.getMessageList();
            }
            
            @Override
            public int getCapabilitiesCount() {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.size();
                }
                return this.capabilitiesBuilder_.getCount();
            }
            
            @Override
            public Capability getCapabilities(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.get(index);
                }
                return this.capabilitiesBuilder_.getMessage(index);
            }
            
            public Builder setCapabilities(final int index, final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(index, value);
                }
                return this;
            }
            
            public Builder setCapabilities(final int index, final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addCapabilities(final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(value);
                }
                return this;
            }
            
            public Builder addCapabilities(final int index, final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(index, value);
                }
                return this;
            }
            
            public Builder addCapabilities(final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            
            public Builder addCapabilities(final int index, final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllCapabilities(final Iterable<? extends Capability> values) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.capabilities_);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addAllMessages(values);
                }
                return this;
            }
            
            public Builder clearCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeCapabilities(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.remove(index);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.remove(index);
                }
                return this;
            }
            
            public Capability.Builder getCapabilitiesBuilder(final int index) {
                return this.getCapabilitiesFieldBuilder().getBuilder(index);
            }
            
            @Override
            public CapabilityOrBuilder getCapabilitiesOrBuilder(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.get(index);
                }
                return this.capabilitiesBuilder_.getMessageOrBuilder(index);
            }
            
            @Override
            public List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList() {
                if (this.capabilitiesBuilder_ != null) {
                    return this.capabilitiesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends CapabilityOrBuilder>)this.capabilities_);
            }
            
            public Capability.Builder addCapabilitiesBuilder() {
                return this.getCapabilitiesFieldBuilder().addBuilder(Capability.getDefaultInstance());
            }
            
            public Capability.Builder addCapabilitiesBuilder(final int index) {
                return this.getCapabilitiesFieldBuilder().addBuilder(index, Capability.getDefaultInstance());
            }
            
            public List<Capability.Builder> getCapabilitiesBuilderList() {
                return this.getCapabilitiesFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder> getCapabilitiesFieldBuilder() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilitiesBuilder_ = new RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder>(this.capabilities_, (this.bitField0_ & 0x1) != 0x0, this.getParentForChildren(), this.isClean());
                    this.capabilities_ = null;
                }
                return this.capabilitiesBuilder_;
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
    
    public static final class CapabilitiesGet extends GeneratedMessageV3 implements CapabilitiesGetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final CapabilitiesGet DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<CapabilitiesGet> PARSER;
        
        private CapabilitiesGet(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private CapabilitiesGet() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new CapabilitiesGet();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private CapabilitiesGet(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable.ensureFieldAccessorsInitialized(CapabilitiesGet.class, Builder.class);
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
            this.unknownFields.writeTo(output);
        }
        
        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CapabilitiesGet)) {
                return super.equals(obj);
            }
            final CapabilitiesGet other = (CapabilitiesGet)obj;
            return this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static CapabilitiesGet parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesGet parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return CapabilitiesGet.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final CapabilitiesGet prototype) {
            return CapabilitiesGet.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == CapabilitiesGet.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static CapabilitiesGet getDefaultInstance() {
            return CapabilitiesGet.DEFAULT_INSTANCE;
        }
        
        public static Parser<CapabilitiesGet> parser() {
            return CapabilitiesGet.PARSER;
        }
        
        @Override
        public Parser<CapabilitiesGet> getParserForType() {
            return CapabilitiesGet.PARSER;
        }
        
        @Override
        public CapabilitiesGet getDefaultInstanceForType() {
            return CapabilitiesGet.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new CapabilitiesGet();
            PARSER = new AbstractParser<CapabilitiesGet>() {
                @Override
                public CapabilitiesGet parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new CapabilitiesGet(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesGetOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable.ensureFieldAccessorsInitialized(CapabilitiesGet.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (CapabilitiesGet.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
            }
            
            @Override
            public CapabilitiesGet getDefaultInstanceForType() {
                return CapabilitiesGet.getDefaultInstance();
            }
            
            @Override
            public CapabilitiesGet build() {
                final CapabilitiesGet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public CapabilitiesGet buildPartial() {
                final CapabilitiesGet result = new CapabilitiesGet((GeneratedMessageV3.Builder)this);
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
                if (other instanceof CapabilitiesGet) {
                    return this.mergeFrom((CapabilitiesGet)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final CapabilitiesGet other) {
                if (other == CapabilitiesGet.getDefaultInstance()) {
                    return this;
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
                CapabilitiesGet parsedMessage = null;
                try {
                    parsedMessage = CapabilitiesGet.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (CapabilitiesGet)e.getUnfinishedMessage();
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
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }
            
            @Override
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class CapabilitiesSet extends GeneratedMessageV3 implements CapabilitiesSetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CAPABILITIES_FIELD_NUMBER = 1;
        private Capabilities capabilities_;
        private byte memoizedIsInitialized;
        private static final CapabilitiesSet DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<CapabilitiesSet> PARSER;
        
        private CapabilitiesSet(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private CapabilitiesSet() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new CapabilitiesSet();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private CapabilitiesSet(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Capabilities.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) != 0x0) {
                                subBuilder = this.capabilities_.toBuilder();
                            }
                            this.capabilities_ = input.readMessage(Capabilities.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.capabilities_);
                                this.capabilities_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable.ensureFieldAccessorsInitialized(CapabilitiesSet.class, Builder.class);
        }
        
        @Override
        public boolean hasCapabilities() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public Capabilities getCapabilities() {
            return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
        }
        
        @Override
        public CapabilitiesOrBuilder getCapabilitiesOrBuilder() {
            return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
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
            if (!this.hasCapabilities()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCapabilities().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeMessage(1, this.getCapabilities());
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
                size += CodedOutputStream.computeMessageSize(1, this.getCapabilities());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CapabilitiesSet)) {
                return super.equals(obj);
            }
            final CapabilitiesSet other = (CapabilitiesSet)obj;
            return this.hasCapabilities() == other.hasCapabilities() && (!this.hasCapabilities() || this.getCapabilities().equals(other.getCapabilities())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCapabilities()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCapabilities().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static CapabilitiesSet parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesSet parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return CapabilitiesSet.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final CapabilitiesSet prototype) {
            return CapabilitiesSet.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == CapabilitiesSet.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static CapabilitiesSet getDefaultInstance() {
            return CapabilitiesSet.DEFAULT_INSTANCE;
        }
        
        public static Parser<CapabilitiesSet> parser() {
            return CapabilitiesSet.PARSER;
        }
        
        @Override
        public Parser<CapabilitiesSet> getParserForType() {
            return CapabilitiesSet.PARSER;
        }
        
        @Override
        public CapabilitiesSet getDefaultInstanceForType() {
            return CapabilitiesSet.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new CapabilitiesSet();
            PARSER = new AbstractParser<CapabilitiesSet>() {
                @Override
                public CapabilitiesSet parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new CapabilitiesSet(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesSetOrBuilder
        {
            private int bitField0_;
            private Capabilities capabilities_;
            private SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder> capabilitiesBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable.ensureFieldAccessorsInitialized(CapabilitiesSet.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (CapabilitiesSet.alwaysUseFieldBuilders) {
                    this.getCapabilitiesFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = null;
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
            }
            
            @Override
            public CapabilitiesSet getDefaultInstanceForType() {
                return CapabilitiesSet.getDefaultInstance();
            }
            
            @Override
            public CapabilitiesSet build() {
                final CapabilitiesSet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public CapabilitiesSet buildPartial() {
                final CapabilitiesSet result = new CapabilitiesSet((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    if (this.capabilitiesBuilder_ == null) {
                        result.capabilities_ = this.capabilities_;
                    }
                    else {
                        result.capabilities_ = this.capabilitiesBuilder_.build();
                    }
                    to_bitField0_ |= 0x1;
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
                if (other instanceof CapabilitiesSet) {
                    return this.mergeFrom((CapabilitiesSet)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final CapabilitiesSet other) {
                if (other == CapabilitiesSet.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCapabilities()) {
                    this.mergeCapabilities(other.getCapabilities());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasCapabilities() && this.getCapabilities().isInitialized();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                CapabilitiesSet parsedMessage = null;
                try {
                    parsedMessage = CapabilitiesSet.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (CapabilitiesSet)e.getUnfinishedMessage();
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
            public boolean hasCapabilities() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public Capabilities getCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
                }
                return this.capabilitiesBuilder_.getMessage();
            }
            
            public Builder setCapabilities(final Capabilities value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.capabilities_ = value;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCapabilities(final Capabilities.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCapabilities(final Capabilities value) {
                if (this.capabilitiesBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) != 0x0 && this.capabilities_ != null && this.capabilities_ != Capabilities.getDefaultInstance()) {
                        this.capabilities_ = Capabilities.newBuilder(this.capabilities_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.capabilities_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = null;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Capabilities.Builder getCapabilitiesBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return this.getCapabilitiesFieldBuilder().getBuilder();
            }
            
            @Override
            public CapabilitiesOrBuilder getCapabilitiesOrBuilder() {
                if (this.capabilitiesBuilder_ != null) {
                    return this.capabilitiesBuilder_.getMessageOrBuilder();
                }
                return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
            }
            
            private SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder> getCapabilitiesFieldBuilder() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilitiesBuilder_ = new SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder>(this.getCapabilities(), this.getParentForChildren(), this.isClean());
                    this.capabilities_ = null;
                }
                return this.capabilitiesBuilder_;
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
    
    public static final class Close extends GeneratedMessageV3 implements CloseOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final Close DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Close> PARSER;
        
        private Close(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Close() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Close();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Close(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
            this.unknownFields.writeTo(output);
        }
        
        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Close)) {
                return super.equals(obj);
            }
            final Close other = (Close)obj;
            return this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Close parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Close.PARSER, input);
        }
        
        public static Close parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Close.PARSER, input, extensionRegistry);
        }
        
        public static Close parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Close.PARSER, input);
        }
        
        public static Close parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Close.PARSER, input, extensionRegistry);
        }
        
        public static Close parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Close.PARSER, input);
        }
        
        public static Close parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Close.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Close.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Close prototype) {
            return Close.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Close.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Close getDefaultInstance() {
            return Close.DEFAULT_INSTANCE;
        }
        
        public static Parser<Close> parser() {
            return Close.PARSER;
        }
        
        @Override
        public Parser<Close> getParserForType() {
            return Close.PARSER;
        }
        
        @Override
        public Close getDefaultInstanceForType() {
            return Close.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Close();
            PARSER = new AbstractParser<Close>() {
                @Override
                public Close parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Close(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CloseOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Close.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
            }
            
            @Override
            public Close getDefaultInstanceForType() {
                return Close.getDefaultInstance();
            }
            
            @Override
            public Close build() {
                final Close result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Close buildPartial() {
                final Close result = new Close((GeneratedMessageV3.Builder)this);
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
                if (other instanceof Close) {
                    return this.mergeFrom((Close)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Close other) {
                if (other == Close.getDefaultInstance()) {
                    return this;
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
                Close parsedMessage = null;
                try {
                    parsedMessage = Close.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Close)e.getUnfinishedMessage();
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
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }
            
            @Override
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Compression extends GeneratedMessageV3 implements CompressionOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int UNCOMPRESSED_SIZE_FIELD_NUMBER = 1;
        private long uncompressedSize_;
        public static final int SERVER_MESSAGES_FIELD_NUMBER = 2;
        private int serverMessages_;
        public static final int CLIENT_MESSAGES_FIELD_NUMBER = 3;
        private int clientMessages_;
        public static final int PAYLOAD_FIELD_NUMBER = 4;
        private ByteString payload_;
        private byte memoizedIsInitialized;
        private static final Compression DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Compression> PARSER;
        
        private Compression(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Compression() {
            this.memoizedIsInitialized = -1;
            this.serverMessages_ = 0;
            this.clientMessages_ = 1;
            this.payload_ = ByteString.EMPTY;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Compression();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Compression(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.uncompressedSize_ = input.readUInt64();
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final Mysqlx.ServerMessages.Type value = Mysqlx.ServerMessages.Type.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.serverMessages_ = rawValue;
                            continue;
                        }
                        case 24: {
                            final int rawValue = input.readEnum();
                            final Mysqlx.ClientMessages.Type value2 = Mysqlx.ClientMessages.Type.valueOf(rawValue);
                            if (value2 == null) {
                                unknownFields.mergeVarintField(3, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x4;
                            this.clientMessages_ = rawValue;
                            continue;
                        }
                        case 34: {
                            this.bitField0_ |= 0x8;
                            this.payload_ = input.readBytes();
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_Compression_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Compression_fieldAccessorTable.ensureFieldAccessorsInitialized(Compression.class, Builder.class);
        }
        
        @Override
        public boolean hasUncompressedSize() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public long getUncompressedSize() {
            return this.uncompressedSize_;
        }
        
        @Override
        public boolean hasServerMessages() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public Mysqlx.ServerMessages.Type getServerMessages() {
            final Mysqlx.ServerMessages.Type result = Mysqlx.ServerMessages.Type.valueOf(this.serverMessages_);
            return (result == null) ? Mysqlx.ServerMessages.Type.OK : result;
        }
        
        @Override
        public boolean hasClientMessages() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public Mysqlx.ClientMessages.Type getClientMessages() {
            final Mysqlx.ClientMessages.Type result = Mysqlx.ClientMessages.Type.valueOf(this.clientMessages_);
            return (result == null) ? Mysqlx.ClientMessages.Type.CON_CAPABILITIES_GET : result;
        }
        
        @Override
        public boolean hasPayload() {
            return (this.bitField0_ & 0x8) != 0x0;
        }
        
        @Override
        public ByteString getPayload() {
            return this.payload_;
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
            if (!this.hasPayload()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeUInt64(1, this.uncompressedSize_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeEnum(2, this.serverMessages_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeEnum(3, this.clientMessages_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                output.writeBytes(4, this.payload_);
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
                size += CodedOutputStream.computeUInt64Size(1, this.uncompressedSize_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeEnumSize(2, this.serverMessages_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeEnumSize(3, this.clientMessages_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                size += CodedOutputStream.computeBytesSize(4, this.payload_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Compression)) {
                return super.equals(obj);
            }
            final Compression other = (Compression)obj;
            return this.hasUncompressedSize() == other.hasUncompressedSize() && (!this.hasUncompressedSize() || this.getUncompressedSize() == other.getUncompressedSize()) && this.hasServerMessages() == other.hasServerMessages() && (!this.hasServerMessages() || this.serverMessages_ == other.serverMessages_) && this.hasClientMessages() == other.hasClientMessages() && (!this.hasClientMessages() || this.clientMessages_ == other.clientMessages_) && this.hasPayload() == other.hasPayload() && (!this.hasPayload() || this.getPayload().equals(other.getPayload())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasUncompressedSize()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + Internal.hashLong(this.getUncompressedSize());
            }
            if (this.hasServerMessages()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.serverMessages_;
            }
            if (this.hasClientMessages()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.clientMessages_;
            }
            if (this.hasPayload()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getPayload().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Compression parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data);
        }
        
        public static Compression parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Compression parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data);
        }
        
        public static Compression parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Compression parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data);
        }
        
        public static Compression parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Compression.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Compression parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Compression.PARSER, input);
        }
        
        public static Compression parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Compression.PARSER, input, extensionRegistry);
        }
        
        public static Compression parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Compression.PARSER, input);
        }
        
        public static Compression parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Compression.PARSER, input, extensionRegistry);
        }
        
        public static Compression parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Compression.PARSER, input);
        }
        
        public static Compression parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Compression.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Compression.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Compression prototype) {
            return Compression.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Compression.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Compression getDefaultInstance() {
            return Compression.DEFAULT_INSTANCE;
        }
        
        public static Parser<Compression> parser() {
            return Compression.PARSER;
        }
        
        @Override
        public Parser<Compression> getParserForType() {
            return Compression.PARSER;
        }
        
        @Override
        public Compression getDefaultInstanceForType() {
            return Compression.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Compression();
            PARSER = new AbstractParser<Compression>() {
                @Override
                public Compression parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Compression(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CompressionOrBuilder
        {
            private int bitField0_;
            private long uncompressedSize_;
            private int serverMessages_;
            private int clientMessages_;
            private ByteString payload_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Compression_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Compression_fieldAccessorTable.ensureFieldAccessorsInitialized(Compression.class, Builder.class);
            }
            
            private Builder() {
                this.serverMessages_ = 0;
                this.clientMessages_ = 1;
                this.payload_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.serverMessages_ = 0;
                this.clientMessages_ = 1;
                this.payload_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Compression.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.uncompressedSize_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                this.serverMessages_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                this.clientMessages_ = 1;
                this.bitField0_ &= 0xFFFFFFFB;
                this.payload_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Compression_descriptor;
            }
            
            @Override
            public Compression getDefaultInstanceForType() {
                return Compression.getDefaultInstance();
            }
            
            @Override
            public Compression build() {
                final Compression result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Compression buildPartial() {
                final Compression result = new Compression((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.uncompressedSize_ = this.uncompressedSize_;
                    to_bitField0_ |= 0x1;
                }
                if ((from_bitField0_ & 0x2) != 0x0) {
                    to_bitField0_ |= 0x2;
                }
                result.serverMessages_ = this.serverMessages_;
                if ((from_bitField0_ & 0x4) != 0x0) {
                    to_bitField0_ |= 0x4;
                }
                result.clientMessages_ = this.clientMessages_;
                if ((from_bitField0_ & 0x8) != 0x0) {
                    to_bitField0_ |= 0x8;
                }
                result.payload_ = this.payload_;
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
                if (other instanceof Compression) {
                    return this.mergeFrom((Compression)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Compression other) {
                if (other == Compression.getDefaultInstance()) {
                    return this;
                }
                if (other.hasUncompressedSize()) {
                    this.setUncompressedSize(other.getUncompressedSize());
                }
                if (other.hasServerMessages()) {
                    this.setServerMessages(other.getServerMessages());
                }
                if (other.hasClientMessages()) {
                    this.setClientMessages(other.getClientMessages());
                }
                if (other.hasPayload()) {
                    this.setPayload(other.getPayload());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasPayload();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Compression parsedMessage = null;
                try {
                    parsedMessage = Compression.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Compression)e.getUnfinishedMessage();
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
            public boolean hasUncompressedSize() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public long getUncompressedSize() {
                return this.uncompressedSize_;
            }
            
            public Builder setUncompressedSize(final long value) {
                this.bitField0_ |= 0x1;
                this.uncompressedSize_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearUncompressedSize() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.uncompressedSize_ = 0L;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasServerMessages() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public Mysqlx.ServerMessages.Type getServerMessages() {
                final Mysqlx.ServerMessages.Type result = Mysqlx.ServerMessages.Type.valueOf(this.serverMessages_);
                return (result == null) ? Mysqlx.ServerMessages.Type.OK : result;
            }
            
            public Builder setServerMessages(final Mysqlx.ServerMessages.Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.serverMessages_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearServerMessages() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.serverMessages_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasClientMessages() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public Mysqlx.ClientMessages.Type getClientMessages() {
                final Mysqlx.ClientMessages.Type result = Mysqlx.ClientMessages.Type.valueOf(this.clientMessages_);
                return (result == null) ? Mysqlx.ClientMessages.Type.CON_CAPABILITIES_GET : result;
            }
            
            public Builder setClientMessages(final Mysqlx.ClientMessages.Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.clientMessages_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearClientMessages() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.clientMessages_ = 1;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasPayload() {
                return (this.bitField0_ & 0x8) != 0x0;
            }
            
            @Override
            public ByteString getPayload() {
                return this.payload_;
            }
            
            public Builder setPayload(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.payload_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearPayload() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.payload_ = Compression.getDefaultInstance().getPayload();
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
    
    public interface CompressionOrBuilder extends MessageOrBuilder
    {
        boolean hasUncompressedSize();
        
        long getUncompressedSize();
        
        boolean hasServerMessages();
        
        Mysqlx.ServerMessages.Type getServerMessages();
        
        boolean hasClientMessages();
        
        Mysqlx.ClientMessages.Type getClientMessages();
        
        boolean hasPayload();
        
        ByteString getPayload();
    }
    
    public interface CloseOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface CapabilityOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasValue();
        
        MysqlxDatatypes.Any getValue();
        
        MysqlxDatatypes.AnyOrBuilder getValueOrBuilder();
    }
    
    public interface CapabilitiesOrBuilder extends MessageOrBuilder
    {
        List<Capability> getCapabilitiesList();
        
        Capability getCapabilities(final int p0);
        
        int getCapabilitiesCount();
        
        List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList();
        
        CapabilityOrBuilder getCapabilitiesOrBuilder(final int p0);
    }
    
    public interface CapabilitiesSetOrBuilder extends MessageOrBuilder
    {
        boolean hasCapabilities();
        
        Capabilities getCapabilities();
        
        CapabilitiesOrBuilder getCapabilitiesOrBuilder();
    }
    
    public interface CapabilitiesGetOrBuilder extends MessageOrBuilder
    {
    }
}
