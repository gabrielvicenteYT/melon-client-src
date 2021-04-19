package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;
import com.google.protobuf.*;

public final class MysqlxExpect
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expect_Open_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expect_Open_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expect_Open_Condition_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expect_Open_Condition_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expect_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expect_Close_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxExpect() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxExpect.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0013mysqlx_expect.proto\u0012\rMysqlx.Expect\u001a\fmysqlx.proto\"\u00d6\u0003\n\u0004Open\u0012B\n\u0002op\u0018\u0001 \u0001(\u000e2 .Mysqlx.Expect.Open.CtxOperation:\u0014EXPECT_CTX_COPY_PREV\u0012+\n\u0004cond\u0018\u0002 \u0003(\u000b2\u001d.Mysqlx.Expect.Open.Condition\u001a\u0096\u0002\n\tCondition\u0012\u0015\n\rcondition_key\u0018\u0001 \u0002(\r\u0012\u0017\n\u000fcondition_value\u0018\u0002 \u0001(\f\u0012K\n\u0002op\u0018\u0003 \u0001(\u000e20.Mysqlx.Expect.Open.Condition.ConditionOperation:\rEXPECT_OP_SET\"N\n\u0003Key\u0012\u0013\n\u000fEXPECT_NO_ERROR\u0010\u0001\u0012\u0016\n\u0012EXPECT_FIELD_EXIST\u0010\u0002\u0012\u001a\n\u0016EXPECT_DOCID_GENERATED\u0010\u0003\"<\n\u0012ConditionOperation\u0012\u0011\n\rEXPECT_OP_SET\u0010\u0000\u0012\u0013\n\u000fEXPECT_OP_UNSET\u0010\u0001\">\n\fCtxOperation\u0012\u0018\n\u0014EXPECT_CTX_COPY_PREV\u0010\u0000\u0012\u0014\n\u0010EXPECT_CTX_EMPTY\u0010\u0001:\u0004\u0088\u00ea0\u0018\"\r\n\u0005Close:\u0004\u0088\u00ea0\u0019B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxExpect.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor() });
        internal_static_Mysqlx_Expect_Open_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Expect_Open_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpect.internal_static_Mysqlx_Expect_Open_descriptor, new String[] { "Op", "Cond" });
        internal_static_Mysqlx_Expect_Open_Condition_descriptor = MysqlxExpect.internal_static_Mysqlx_Expect_Open_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Expect_Open_Condition_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_descriptor, new String[] { "ConditionKey", "ConditionValue", "Op" });
        internal_static_Mysqlx_Expect_Close_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Expect_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpect.internal_static_Mysqlx_Expect_Close_descriptor, new String[0]);
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.clientMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxExpect.descriptor, registry);
        Mysqlx.getDescriptor();
    }
    
    public static final class Open extends GeneratedMessageV3 implements OpenOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int OP_FIELD_NUMBER = 1;
        private int op_;
        public static final int COND_FIELD_NUMBER = 2;
        private List<Condition> cond_;
        private byte memoizedIsInitialized;
        private static final Open DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Open> PARSER;
        
        private Open(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Open() {
            this.memoizedIsInitialized = -1;
            this.op_ = 0;
            this.cond_ = Collections.emptyList();
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Open();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Open(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final CtxOperation value = CtxOperation.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.op_ = rawValue;
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x2) == 0x0) {
                                this.cond_ = new ArrayList<Condition>();
                                mutable_bitField0_ |= 0x2;
                            }
                            this.cond_.add(input.readMessage(Condition.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 0x2) != 0x0) {
                    this.cond_ = Collections.unmodifiableList((List<? extends Condition>)this.cond_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpect.internal_static_Mysqlx_Expect_Open_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpect.internal_static_Mysqlx_Expect_Open_fieldAccessorTable.ensureFieldAccessorsInitialized(Open.class, Builder.class);
        }
        
        @Override
        public boolean hasOp() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public CtxOperation getOp() {
            final CtxOperation result = CtxOperation.valueOf(this.op_);
            return (result == null) ? CtxOperation.EXPECT_CTX_COPY_PREV : result;
        }
        
        @Override
        public List<Condition> getCondList() {
            return this.cond_;
        }
        
        @Override
        public List<? extends ConditionOrBuilder> getCondOrBuilderList() {
            return this.cond_;
        }
        
        @Override
        public int getCondCount() {
            return this.cond_.size();
        }
        
        @Override
        public Condition getCond(final int index) {
            return this.cond_.get(index);
        }
        
        @Override
        public ConditionOrBuilder getCondOrBuilder(final int index) {
            return this.cond_.get(index);
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
            for (int i = 0; i < this.getCondCount(); ++i) {
                if (!this.getCond(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeEnum(1, this.op_);
            }
            for (int i = 0; i < this.cond_.size(); ++i) {
                output.writeMessage(2, this.cond_.get(i));
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
                size += CodedOutputStream.computeEnumSize(1, this.op_);
            }
            for (int i = 0; i < this.cond_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.cond_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Open)) {
                return super.equals(obj);
            }
            final Open other = (Open)obj;
            return this.hasOp() == other.hasOp() && (!this.hasOp() || this.op_ == other.op_) && this.getCondList().equals(other.getCondList()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasOp()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.op_;
            }
            if (this.getCondCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCondList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Open parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data);
        }
        
        public static Open parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Open parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data);
        }
        
        public static Open parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Open parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data);
        }
        
        public static Open parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Open.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Open parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Open.PARSER, input);
        }
        
        public static Open parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Open.PARSER, input, extensionRegistry);
        }
        
        public static Open parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Open.PARSER, input);
        }
        
        public static Open parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Open.PARSER, input, extensionRegistry);
        }
        
        public static Open parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Open.PARSER, input);
        }
        
        public static Open parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Open.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Open.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Open prototype) {
            return Open.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Open.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Open getDefaultInstance() {
            return Open.DEFAULT_INSTANCE;
        }
        
        public static Parser<Open> parser() {
            return Open.PARSER;
        }
        
        @Override
        public Parser<Open> getParserForType() {
            return Open.PARSER;
        }
        
        @Override
        public Open getDefaultInstanceForType() {
            return Open.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Open();
            PARSER = new AbstractParser<Open>() {
                @Override
                public Open parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Open(input, extensionRegistry);
                }
            };
        }
        
        public enum CtxOperation implements ProtocolMessageEnum
        {
            EXPECT_CTX_COPY_PREV(0), 
            EXPECT_CTX_EMPTY(1);
            
            public static final int EXPECT_CTX_COPY_PREV_VALUE = 0;
            public static final int EXPECT_CTX_EMPTY_VALUE = 1;
            private static final Internal.EnumLiteMap<CtxOperation> internalValueMap;
            private static final CtxOperation[] VALUES;
            private final int value;
            
            @Override
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static CtxOperation valueOf(final int value) {
                return forNumber(value);
            }
            
            public static CtxOperation forNumber(final int value) {
                switch (value) {
                    case 0: {
                        return CtxOperation.EXPECT_CTX_COPY_PREV;
                    }
                    case 1: {
                        return CtxOperation.EXPECT_CTX_EMPTY;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<CtxOperation> internalGetValueMap() {
                return CtxOperation.internalValueMap;
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
                return Open.getDescriptor().getEnumTypes().get(0);
            }
            
            public static CtxOperation valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return CtxOperation.VALUES[desc.getIndex()];
            }
            
            private CtxOperation(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = new Internal.EnumLiteMap<CtxOperation>() {
                    @Override
                    public CtxOperation findValueByNumber(final int number) {
                        return CtxOperation.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Condition extends GeneratedMessageV3 implements ConditionOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int CONDITION_KEY_FIELD_NUMBER = 1;
            private int conditionKey_;
            public static final int CONDITION_VALUE_FIELD_NUMBER = 2;
            private ByteString conditionValue_;
            public static final int OP_FIELD_NUMBER = 3;
            private int op_;
            private byte memoizedIsInitialized;
            private static final Condition DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<Condition> PARSER;
            
            private Condition(final GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }
            
            private Condition() {
                this.memoizedIsInitialized = -1;
                this.conditionValue_ = ByteString.EMPTY;
                this.op_ = 0;
            }
            
            @Override
            protected Object newInstance(final UnusedPrivateParameter unused) {
                return new Condition();
            }
            
            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private Condition(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.conditionKey_ = input.readUInt32();
                                continue;
                            }
                            case 18: {
                                this.bitField0_ |= 0x2;
                                this.conditionValue_ = input.readBytes();
                                continue;
                            }
                            case 24: {
                                final int rawValue = input.readEnum();
                                final ConditionOperation value = ConditionOperation.valueOf(rawValue);
                                if (value == null) {
                                    unknownFields.mergeVarintField(3, rawValue);
                                    continue;
                                }
                                this.bitField0_ |= 0x4;
                                this.op_ = rawValue;
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
                return MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_fieldAccessorTable.ensureFieldAccessorsInitialized(Condition.class, Builder.class);
            }
            
            @Override
            public boolean hasConditionKey() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getConditionKey() {
                return this.conditionKey_;
            }
            
            @Override
            public boolean hasConditionValue() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public ByteString getConditionValue() {
                return this.conditionValue_;
            }
            
            @Override
            public boolean hasOp() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public ConditionOperation getOp() {
                final ConditionOperation result = ConditionOperation.valueOf(this.op_);
                return (result == null) ? ConditionOperation.EXPECT_OP_SET : result;
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
                if (!this.hasConditionKey()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
            
            @Override
            public void writeTo(final CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 0x1) != 0x0) {
                    output.writeUInt32(1, this.conditionKey_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    output.writeBytes(2, this.conditionValue_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    output.writeEnum(3, this.op_);
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
                    size += CodedOutputStream.computeUInt32Size(1, this.conditionKey_);
                }
                if ((this.bitField0_ & 0x2) != 0x0) {
                    size += CodedOutputStream.computeBytesSize(2, this.conditionValue_);
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    size += CodedOutputStream.computeEnumSize(3, this.op_);
                }
                size += this.unknownFields.getSerializedSize();
                return this.memoizedSize = size;
            }
            
            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Condition)) {
                    return super.equals(obj);
                }
                final Condition other = (Condition)obj;
                return this.hasConditionKey() == other.hasConditionKey() && (!this.hasConditionKey() || this.getConditionKey() == other.getConditionKey()) && this.hasConditionValue() == other.hasConditionValue() && (!this.hasConditionValue() || this.getConditionValue().equals(other.getConditionValue())) && this.hasOp() == other.hasOp() && (!this.hasOp() || this.op_ == other.op_) && this.unknownFields.equals(other.unknownFields);
            }
            
            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + getDescriptor().hashCode();
                if (this.hasConditionKey()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getConditionKey();
                }
                if (this.hasConditionValue()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getConditionValue().hashCode();
                }
                if (this.hasOp()) {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + this.op_;
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static Condition parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data);
            }
            
            public static Condition parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Condition parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data);
            }
            
            public static Condition parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Condition parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data);
            }
            
            public static Condition parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return Condition.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static Condition parseFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Condition.PARSER, input);
            }
            
            public static Condition parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Condition.PARSER, input, extensionRegistry);
            }
            
            public static Condition parseDelimitedFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(Condition.PARSER, input);
            }
            
            public static Condition parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(Condition.PARSER, input, extensionRegistry);
            }
            
            public static Condition parseFrom(final CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Condition.PARSER, input);
            }
            
            public static Condition parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(Condition.PARSER, input, extensionRegistry);
            }
            
            @Override
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return Condition.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final Condition prototype) {
                return Condition.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            @Override
            public Builder toBuilder() {
                return (this == Condition.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            @Override
            protected Builder newBuilderForType(final BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static Condition getDefaultInstance() {
                return Condition.DEFAULT_INSTANCE;
            }
            
            public static Parser<Condition> parser() {
                return Condition.PARSER;
            }
            
            @Override
            public Parser<Condition> getParserForType() {
                return Condition.PARSER;
            }
            
            @Override
            public Condition getDefaultInstanceForType() {
                return Condition.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new Condition();
                PARSER = new AbstractParser<Condition>() {
                    @Override
                    public Condition parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new Condition(input, extensionRegistry);
                    }
                };
            }
            
            public enum Key implements ProtocolMessageEnum
            {
                EXPECT_NO_ERROR(1), 
                EXPECT_FIELD_EXIST(2), 
                EXPECT_DOCID_GENERATED(3);
                
                public static final int EXPECT_NO_ERROR_VALUE = 1;
                public static final int EXPECT_FIELD_EXIST_VALUE = 2;
                public static final int EXPECT_DOCID_GENERATED_VALUE = 3;
                private static final Internal.EnumLiteMap<Key> internalValueMap;
                private static final Key[] VALUES;
                private final int value;
                
                @Override
                public final int getNumber() {
                    return this.value;
                }
                
                @Deprecated
                public static Key valueOf(final int value) {
                    return forNumber(value);
                }
                
                public static Key forNumber(final int value) {
                    switch (value) {
                        case 1: {
                            return Key.EXPECT_NO_ERROR;
                        }
                        case 2: {
                            return Key.EXPECT_FIELD_EXIST;
                        }
                        case 3: {
                            return Key.EXPECT_DOCID_GENERATED;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                
                public static Internal.EnumLiteMap<Key> internalGetValueMap() {
                    return Key.internalValueMap;
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
                    return Condition.getDescriptor().getEnumTypes().get(0);
                }
                
                public static Key valueOf(final Descriptors.EnumValueDescriptor desc) {
                    if (desc.getType() != getDescriptor()) {
                        throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                    }
                    return Key.VALUES[desc.getIndex()];
                }
                
                private Key(final int value) {
                    this.value = value;
                }
                
                static {
                    internalValueMap = new Internal.EnumLiteMap<Key>() {
                        @Override
                        public Key findValueByNumber(final int number) {
                            return Key.forNumber(number);
                        }
                    };
                    VALUES = values();
                }
            }
            
            public enum ConditionOperation implements ProtocolMessageEnum
            {
                EXPECT_OP_SET(0), 
                EXPECT_OP_UNSET(1);
                
                public static final int EXPECT_OP_SET_VALUE = 0;
                public static final int EXPECT_OP_UNSET_VALUE = 1;
                private static final Internal.EnumLiteMap<ConditionOperation> internalValueMap;
                private static final ConditionOperation[] VALUES;
                private final int value;
                
                @Override
                public final int getNumber() {
                    return this.value;
                }
                
                @Deprecated
                public static ConditionOperation valueOf(final int value) {
                    return forNumber(value);
                }
                
                public static ConditionOperation forNumber(final int value) {
                    switch (value) {
                        case 0: {
                            return ConditionOperation.EXPECT_OP_SET;
                        }
                        case 1: {
                            return ConditionOperation.EXPECT_OP_UNSET;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                
                public static Internal.EnumLiteMap<ConditionOperation> internalGetValueMap() {
                    return ConditionOperation.internalValueMap;
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
                    return Condition.getDescriptor().getEnumTypes().get(1);
                }
                
                public static ConditionOperation valueOf(final Descriptors.EnumValueDescriptor desc) {
                    if (desc.getType() != getDescriptor()) {
                        throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                    }
                    return ConditionOperation.VALUES[desc.getIndex()];
                }
                
                private ConditionOperation(final int value) {
                    this.value = value;
                }
                
                static {
                    internalValueMap = new Internal.EnumLiteMap<ConditionOperation>() {
                        @Override
                        public ConditionOperation findValueByNumber(final int number) {
                            return ConditionOperation.forNumber(number);
                        }
                    };
                    VALUES = values();
                }
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ConditionOrBuilder
            {
                private int bitField0_;
                private int conditionKey_;
                private ByteString conditionValue_;
                private int op_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_fieldAccessorTable.ensureFieldAccessorsInitialized(Condition.class, Builder.class);
                }
                
                private Builder() {
                    this.conditionValue_ = ByteString.EMPTY;
                    this.op_ = 0;
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.conditionValue_ = ByteString.EMPTY;
                    this.op_ = 0;
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (Condition.alwaysUseFieldBuilders) {}
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.conditionKey_ = 0;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.conditionValue_ = ByteString.EMPTY;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.op_ = 0;
                    this.bitField0_ &= 0xFFFFFFFB;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxExpect.internal_static_Mysqlx_Expect_Open_Condition_descriptor;
                }
                
                @Override
                public Condition getDefaultInstanceForType() {
                    return Condition.getDefaultInstance();
                }
                
                @Override
                public Condition build() {
                    final Condition result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw AbstractMessage.Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }
                
                @Override
                public Condition buildPartial() {
                    final Condition result = new Condition((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) != 0x0) {
                        result.conditionKey_ = this.conditionKey_;
                        to_bitField0_ |= 0x1;
                    }
                    if ((from_bitField0_ & 0x2) != 0x0) {
                        to_bitField0_ |= 0x2;
                    }
                    result.conditionValue_ = this.conditionValue_;
                    if ((from_bitField0_ & 0x4) != 0x0) {
                        to_bitField0_ |= 0x4;
                    }
                    result.op_ = this.op_;
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
                    if (other instanceof Condition) {
                        return this.mergeFrom((Condition)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final Condition other) {
                    if (other == Condition.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasConditionKey()) {
                        this.setConditionKey(other.getConditionKey());
                    }
                    if (other.hasConditionValue()) {
                        this.setConditionValue(other.getConditionValue());
                    }
                    if (other.hasOp()) {
                        this.setOp(other.getOp());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                @Override
                public final boolean isInitialized() {
                    return this.hasConditionKey();
                }
                
                @Override
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    Condition parsedMessage = null;
                    try {
                        parsedMessage = Condition.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (Condition)e.getUnfinishedMessage();
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
                public boolean hasConditionKey() {
                    return (this.bitField0_ & 0x1) != 0x0;
                }
                
                @Override
                public int getConditionKey() {
                    return this.conditionKey_;
                }
                
                public Builder setConditionKey(final int value) {
                    this.bitField0_ |= 0x1;
                    this.conditionKey_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearConditionKey() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.conditionKey_ = 0;
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasConditionValue() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }
                
                @Override
                public ByteString getConditionValue() {
                    return this.conditionValue_;
                }
                
                public Builder setConditionValue(final ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x2;
                    this.conditionValue_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearConditionValue() {
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.conditionValue_ = Condition.getDefaultInstance().getConditionValue();
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasOp() {
                    return (this.bitField0_ & 0x4) != 0x0;
                }
                
                @Override
                public ConditionOperation getOp() {
                    final ConditionOperation result = ConditionOperation.valueOf(this.op_);
                    return (result == null) ? ConditionOperation.EXPECT_OP_SET : result;
                }
                
                public Builder setOp(final ConditionOperation value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x4;
                    this.op_ = value.getNumber();
                    this.onChanged();
                    return this;
                }
                
                public Builder clearOp() {
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.op_ = 0;
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OpenOrBuilder
        {
            private int bitField0_;
            private int op_;
            private List<Condition> cond_;
            private RepeatedFieldBuilderV3<Condition, Condition.Builder, ConditionOrBuilder> condBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpect.internal_static_Mysqlx_Expect_Open_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpect.internal_static_Mysqlx_Expect_Open_fieldAccessorTable.ensureFieldAccessorsInitialized(Open.class, Builder.class);
            }
            
            private Builder() {
                this.op_ = 0;
                this.cond_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.op_ = 0;
                this.cond_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Open.alwaysUseFieldBuilders) {
                    this.getCondFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.op_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.condBuilder_ == null) {
                    this.cond_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.condBuilder_.clear();
                }
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpect.internal_static_Mysqlx_Expect_Open_descriptor;
            }
            
            @Override
            public Open getDefaultInstanceForType() {
                return Open.getDefaultInstance();
            }
            
            @Override
            public Open build() {
                final Open result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Open buildPartial() {
                final Open result = new Open((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.op_ = this.op_;
                if (this.condBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) != 0x0) {
                        this.cond_ = Collections.unmodifiableList((List<? extends Condition>)this.cond_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.cond_ = this.cond_;
                }
                else {
                    result.cond_ = this.condBuilder_.build();
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
                if (other instanceof Open) {
                    return this.mergeFrom((Open)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Open other) {
                if (other == Open.getDefaultInstance()) {
                    return this;
                }
                if (other.hasOp()) {
                    this.setOp(other.getOp());
                }
                if (this.condBuilder_ == null) {
                    if (!other.cond_.isEmpty()) {
                        if (this.cond_.isEmpty()) {
                            this.cond_ = other.cond_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        }
                        else {
                            this.ensureCondIsMutable();
                            this.cond_.addAll(other.cond_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.cond_.isEmpty()) {
                    if (this.condBuilder_.isEmpty()) {
                        this.condBuilder_.dispose();
                        this.condBuilder_ = null;
                        this.cond_ = other.cond_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.condBuilder_ = (Open.alwaysUseFieldBuilders ? this.getCondFieldBuilder() : null);
                    }
                    else {
                        this.condBuilder_.addAllMessages(other.cond_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                for (int i = 0; i < this.getCondCount(); ++i) {
                    if (!this.getCond(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Open parsedMessage = null;
                try {
                    parsedMessage = Open.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Open)e.getUnfinishedMessage();
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
            public boolean hasOp() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public CtxOperation getOp() {
                final CtxOperation result = CtxOperation.valueOf(this.op_);
                return (result == null) ? CtxOperation.EXPECT_CTX_COPY_PREV : result;
            }
            
            public Builder setOp(final CtxOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.op_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearOp() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.op_ = 0;
                this.onChanged();
                return this;
            }
            
            private void ensureCondIsMutable() {
                if ((this.bitField0_ & 0x2) == 0x0) {
                    this.cond_ = new ArrayList<Condition>(this.cond_);
                    this.bitField0_ |= 0x2;
                }
            }
            
            @Override
            public List<Condition> getCondList() {
                if (this.condBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Condition>)this.cond_);
                }
                return this.condBuilder_.getMessageList();
            }
            
            @Override
            public int getCondCount() {
                if (this.condBuilder_ == null) {
                    return this.cond_.size();
                }
                return this.condBuilder_.getCount();
            }
            
            @Override
            public Condition getCond(final int index) {
                if (this.condBuilder_ == null) {
                    return this.cond_.get(index);
                }
                return this.condBuilder_.getMessage(index);
            }
            
            public Builder setCond(final int index, final Condition value) {
                if (this.condBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCondIsMutable();
                    this.cond_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.condBuilder_.setMessage(index, value);
                }
                return this;
            }
            
            public Builder setCond(final int index, final Condition.Builder builderForValue) {
                if (this.condBuilder_ == null) {
                    this.ensureCondIsMutable();
                    this.cond_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.condBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addCond(final Condition value) {
                if (this.condBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCondIsMutable();
                    this.cond_.add(value);
                    this.onChanged();
                }
                else {
                    this.condBuilder_.addMessage(value);
                }
                return this;
            }
            
            public Builder addCond(final int index, final Condition value) {
                if (this.condBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCondIsMutable();
                    this.cond_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.condBuilder_.addMessage(index, value);
                }
                return this;
            }
            
            public Builder addCond(final Condition.Builder builderForValue) {
                if (this.condBuilder_ == null) {
                    this.ensureCondIsMutable();
                    this.cond_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.condBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            
            public Builder addCond(final int index, final Condition.Builder builderForValue) {
                if (this.condBuilder_ == null) {
                    this.ensureCondIsMutable();
                    this.cond_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.condBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllCond(final Iterable<? extends Condition> values) {
                if (this.condBuilder_ == null) {
                    this.ensureCondIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.cond_);
                    this.onChanged();
                }
                else {
                    this.condBuilder_.addAllMessages(values);
                }
                return this;
            }
            
            public Builder clearCond() {
                if (this.condBuilder_ == null) {
                    this.cond_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                }
                else {
                    this.condBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeCond(final int index) {
                if (this.condBuilder_ == null) {
                    this.ensureCondIsMutable();
                    this.cond_.remove(index);
                    this.onChanged();
                }
                else {
                    this.condBuilder_.remove(index);
                }
                return this;
            }
            
            public Condition.Builder getCondBuilder(final int index) {
                return this.getCondFieldBuilder().getBuilder(index);
            }
            
            @Override
            public ConditionOrBuilder getCondOrBuilder(final int index) {
                if (this.condBuilder_ == null) {
                    return this.cond_.get(index);
                }
                return this.condBuilder_.getMessageOrBuilder(index);
            }
            
            @Override
            public List<? extends ConditionOrBuilder> getCondOrBuilderList() {
                if (this.condBuilder_ != null) {
                    return this.condBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ConditionOrBuilder>)this.cond_);
            }
            
            public Condition.Builder addCondBuilder() {
                return this.getCondFieldBuilder().addBuilder(Condition.getDefaultInstance());
            }
            
            public Condition.Builder addCondBuilder(final int index) {
                return this.getCondFieldBuilder().addBuilder(index, Condition.getDefaultInstance());
            }
            
            public List<Condition.Builder> getCondBuilderList() {
                return this.getCondFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Condition, Condition.Builder, ConditionOrBuilder> getCondFieldBuilder() {
                if (this.condBuilder_ == null) {
                    this.condBuilder_ = new RepeatedFieldBuilderV3<Condition, Condition.Builder, ConditionOrBuilder>(this.cond_, (this.bitField0_ & 0x2) != 0x0, this.getParentForChildren(), this.isClean());
                    this.cond_ = null;
                }
                return this.condBuilder_;
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
        
        public interface ConditionOrBuilder extends MessageOrBuilder
        {
            boolean hasConditionKey();
            
            int getConditionKey();
            
            boolean hasConditionValue();
            
            ByteString getConditionValue();
            
            boolean hasOp();
            
            Condition.ConditionOperation getOp();
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
            return MysqlxExpect.internal_static_Mysqlx_Expect_Close_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpect.internal_static_Mysqlx_Expect_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
                return MysqlxExpect.internal_static_Mysqlx_Expect_Close_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpect.internal_static_Mysqlx_Expect_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
                return MysqlxExpect.internal_static_Mysqlx_Expect_Close_descriptor;
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
    
    public interface CloseOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface OpenOrBuilder extends MessageOrBuilder
    {
        boolean hasOp();
        
        Open.CtxOperation getOp();
        
        List<Open.Condition> getCondList();
        
        Open.Condition getCond(final int p0);
        
        int getCondCount();
        
        List<? extends Open.ConditionOrBuilder> getCondOrBuilderList();
        
        Open.ConditionOrBuilder getCondOrBuilder(final int p0);
    }
}
