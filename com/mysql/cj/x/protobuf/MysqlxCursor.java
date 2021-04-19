package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import com.google.protobuf.*;

public final class MysqlxCursor
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Cursor_Open_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Cursor_Open_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Cursor_Open_OneOfMessage_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Cursor_Fetch_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Cursor_Fetch_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Cursor_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Cursor_Close_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxCursor() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxCursor.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0013mysqlx_cursor.proto\u0012\rMysqlx.Cursor\u001a\fmysqlx.proto\u001a\u0014mysqlx_prepare.proto\"\u00f8\u0001\n\u0004Open\u0012\u0011\n\tcursor_id\u0018\u0001 \u0002(\r\u0012.\n\u0004stmt\u0018\u0004 \u0002(\u000b2 .Mysqlx.Cursor.Open.OneOfMessage\u0012\u0012\n\nfetch_rows\u0018\u0005 \u0001(\u0004\u001a\u0092\u0001\n\fOneOfMessage\u00123\n\u0004type\u0018\u0001 \u0002(\u000e2%.Mysqlx.Cursor.Open.OneOfMessage.Type\u00120\n\u000fprepare_execute\u0018\u0002 \u0001(\u000b2\u0017.Mysqlx.Prepare.Execute\"\u001b\n\u0004Type\u0012\u0013\n\u000fPREPARE_EXECUTE\u0010\u0000:\u0004\u0088\u00ea0+\"4\n\u0005Fetch\u0012\u0011\n\tcursor_id\u0018\u0001 \u0002(\r\u0012\u0012\n\nfetch_rows\u0018\u0005 \u0001(\u0004:\u0004\u0088\u00ea0-\" \n\u0005Close\u0012\u0011\n\tcursor_id\u0018\u0001 \u0002(\r:\u0004\u0088\u00ea0,B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxCursor.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor(), MysqlxPrepare.getDescriptor() });
        internal_static_Mysqlx_Cursor_Open_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Cursor_Open_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCursor.internal_static_Mysqlx_Cursor_Open_descriptor, new String[] { "CursorId", "Stmt", "FetchRows" });
        internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor = MysqlxCursor.internal_static_Mysqlx_Cursor_Open_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Cursor_Open_OneOfMessage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor, new String[] { "Type", "PrepareExecute" });
        internal_static_Mysqlx_Cursor_Fetch_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Cursor_Fetch_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_descriptor, new String[] { "CursorId", "FetchRows" });
        internal_static_Mysqlx_Cursor_Close_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Cursor_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCursor.internal_static_Mysqlx_Cursor_Close_descriptor, new String[] { "CursorId" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.clientMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxCursor.descriptor, registry);
        Mysqlx.getDescriptor();
        MysqlxPrepare.getDescriptor();
    }
    
    public static final class Open extends GeneratedMessageV3 implements OpenOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CURSOR_ID_FIELD_NUMBER = 1;
        private int cursorId_;
        public static final int STMT_FIELD_NUMBER = 4;
        private OneOfMessage stmt_;
        public static final int FETCH_ROWS_FIELD_NUMBER = 5;
        private long fetchRows_;
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
                            this.cursorId_ = input.readUInt32();
                            continue;
                        }
                        case 34: {
                            OneOfMessage.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) != 0x0) {
                                subBuilder = this.stmt_.toBuilder();
                            }
                            this.stmt_ = input.readMessage(OneOfMessage.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.stmt_);
                                this.stmt_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 40: {
                            this.bitField0_ |= 0x4;
                            this.fetchRows_ = input.readUInt64();
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
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_fieldAccessorTable.ensureFieldAccessorsInitialized(Open.class, Builder.class);
        }
        
        @Override
        public boolean hasCursorId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getCursorId() {
            return this.cursorId_;
        }
        
        @Override
        public boolean hasStmt() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public OneOfMessage getStmt() {
            return (this.stmt_ == null) ? OneOfMessage.getDefaultInstance() : this.stmt_;
        }
        
        @Override
        public OneOfMessageOrBuilder getStmtOrBuilder() {
            return (this.stmt_ == null) ? OneOfMessage.getDefaultInstance() : this.stmt_;
        }
        
        @Override
        public boolean hasFetchRows() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public long getFetchRows() {
            return this.fetchRows_;
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
            if (!this.hasCursorId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasStmt()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getStmt().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeUInt32(1, this.cursorId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeMessage(4, this.getStmt());
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeUInt64(5, this.fetchRows_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.cursorId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeMessageSize(4, this.getStmt());
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeUInt64Size(5, this.fetchRows_);
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
            return this.hasCursorId() == other.hasCursorId() && (!this.hasCursorId() || this.getCursorId() == other.getCursorId()) && this.hasStmt() == other.hasStmt() && (!this.hasStmt() || this.getStmt().equals(other.getStmt())) && this.hasFetchRows() == other.hasFetchRows() && (!this.hasFetchRows() || this.getFetchRows() == other.getFetchRows()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCursorId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCursorId();
            }
            if (this.hasStmt()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getStmt().hashCode();
            }
            if (this.hasFetchRows()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + Internal.hashLong(this.getFetchRows());
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
        
        public static final class OneOfMessage extends GeneratedMessageV3 implements OneOfMessageOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int TYPE_FIELD_NUMBER = 1;
            private int type_;
            public static final int PREPARE_EXECUTE_FIELD_NUMBER = 2;
            private MysqlxPrepare.Execute prepareExecute_;
            private byte memoizedIsInitialized;
            private static final OneOfMessage DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<OneOfMessage> PARSER;
            
            private OneOfMessage(final GeneratedMessageV3.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = -1;
            }
            
            private OneOfMessage() {
                this.memoizedIsInitialized = -1;
                this.type_ = 0;
            }
            
            @Override
            protected Object newInstance(final UnusedPrivateParameter unused) {
                return new OneOfMessage();
            }
            
            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private OneOfMessage(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                MysqlxPrepare.Execute.Builder subBuilder = null;
                                if ((this.bitField0_ & 0x2) != 0x0) {
                                    subBuilder = this.prepareExecute_.toBuilder();
                                }
                                this.prepareExecute_ = input.readMessage(MysqlxPrepare.Execute.PARSER, extensionRegistry);
                                if (subBuilder != null) {
                                    subBuilder.mergeFrom(this.prepareExecute_);
                                    this.prepareExecute_ = subBuilder.buildPartial();
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
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(OneOfMessage.class, Builder.class);
            }
            
            @Override
            public boolean hasType() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.PREPARE_EXECUTE : result;
            }
            
            @Override
            public boolean hasPrepareExecute() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public MysqlxPrepare.Execute getPrepareExecute() {
                return (this.prepareExecute_ == null) ? MysqlxPrepare.Execute.getDefaultInstance() : this.prepareExecute_;
            }
            
            @Override
            public MysqlxPrepare.ExecuteOrBuilder getPrepareExecuteOrBuilder() {
                return (this.prepareExecute_ == null) ? MysqlxPrepare.Execute.getDefaultInstance() : this.prepareExecute_;
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
                if (this.hasPrepareExecute() && !this.getPrepareExecute().isInitialized()) {
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
                    output.writeMessage(2, this.getPrepareExecute());
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
                    size += CodedOutputStream.computeMessageSize(2, this.getPrepareExecute());
                }
                size += this.unknownFields.getSerializedSize();
                return this.memoizedSize = size;
            }
            
            @Override
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof OneOfMessage)) {
                    return super.equals(obj);
                }
                final OneOfMessage other = (OneOfMessage)obj;
                return this.hasType() == other.hasType() && (!this.hasType() || this.type_ == other.type_) && this.hasPrepareExecute() == other.hasPrepareExecute() && (!this.hasPrepareExecute() || this.getPrepareExecute().equals(other.getPrepareExecute())) && this.unknownFields.equals(other.unknownFields);
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
                if (this.hasPrepareExecute()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getPrepareExecute().hashCode();
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static OneOfMessage parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data);
            }
            
            public static OneOfMessage parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static OneOfMessage parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data);
            }
            
            public static OneOfMessage parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static OneOfMessage parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data);
            }
            
            public static OneOfMessage parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return OneOfMessage.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static OneOfMessage parseFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(OneOfMessage.PARSER, input);
            }
            
            public static OneOfMessage parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(OneOfMessage.PARSER, input, extensionRegistry);
            }
            
            public static OneOfMessage parseDelimitedFrom(final InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(OneOfMessage.PARSER, input);
            }
            
            public static OneOfMessage parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(OneOfMessage.PARSER, input, extensionRegistry);
            }
            
            public static OneOfMessage parseFrom(final CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(OneOfMessage.PARSER, input);
            }
            
            public static OneOfMessage parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(OneOfMessage.PARSER, input, extensionRegistry);
            }
            
            @Override
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return OneOfMessage.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final OneOfMessage prototype) {
                return OneOfMessage.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            @Override
            public Builder toBuilder() {
                return (this == OneOfMessage.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            @Override
            protected Builder newBuilderForType(final BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static OneOfMessage getDefaultInstance() {
                return OneOfMessage.DEFAULT_INSTANCE;
            }
            
            public static Parser<OneOfMessage> parser() {
                return OneOfMessage.PARSER;
            }
            
            @Override
            public Parser<OneOfMessage> getParserForType() {
                return OneOfMessage.PARSER;
            }
            
            @Override
            public OneOfMessage getDefaultInstanceForType() {
                return OneOfMessage.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new OneOfMessage();
                PARSER = new AbstractParser<OneOfMessage>() {
                    @Override
                    public OneOfMessage parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new OneOfMessage(input, extensionRegistry);
                    }
                };
            }
            
            public enum Type implements ProtocolMessageEnum
            {
                PREPARE_EXECUTE(0);
                
                public static final int PREPARE_EXECUTE_VALUE = 0;
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
                        case 0: {
                            return Type.PREPARE_EXECUTE;
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
                    return OneOfMessage.getDescriptor().getEnumTypes().get(0);
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
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OneOfMessageOrBuilder
            {
                private int bitField0_;
                private int type_;
                private MysqlxPrepare.Execute prepareExecute_;
                private SingleFieldBuilderV3<MysqlxPrepare.Execute, MysqlxPrepare.Execute.Builder, MysqlxPrepare.ExecuteOrBuilder> prepareExecuteBuilder_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(OneOfMessage.class, Builder.class);
                }
                
                private Builder() {
                    this.type_ = 0;
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.type_ = 0;
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (OneOfMessage.alwaysUseFieldBuilders) {
                        this.getPrepareExecuteFieldBuilder();
                    }
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.type_ = 0;
                    this.bitField0_ &= 0xFFFFFFFE;
                    if (this.prepareExecuteBuilder_ == null) {
                        this.prepareExecute_ = null;
                    }
                    else {
                        this.prepareExecuteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_OneOfMessage_descriptor;
                }
                
                @Override
                public OneOfMessage getDefaultInstanceForType() {
                    return OneOfMessage.getDefaultInstance();
                }
                
                @Override
                public OneOfMessage build() {
                    final OneOfMessage result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw AbstractMessage.Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }
                
                @Override
                public OneOfMessage buildPartial() {
                    final OneOfMessage result = new OneOfMessage((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) != 0x0) {
                        to_bitField0_ |= 0x1;
                    }
                    result.type_ = this.type_;
                    if ((from_bitField0_ & 0x2) != 0x0) {
                        if (this.prepareExecuteBuilder_ == null) {
                            result.prepareExecute_ = this.prepareExecute_;
                        }
                        else {
                            result.prepareExecute_ = this.prepareExecuteBuilder_.build();
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
                    if (other instanceof OneOfMessage) {
                        return this.mergeFrom((OneOfMessage)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final OneOfMessage other) {
                    if (other == OneOfMessage.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasType()) {
                        this.setType(other.getType());
                    }
                    if (other.hasPrepareExecute()) {
                        this.mergePrepareExecute(other.getPrepareExecute());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                @Override
                public final boolean isInitialized() {
                    return this.hasType() && (!this.hasPrepareExecute() || this.getPrepareExecute().isInitialized());
                }
                
                @Override
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    OneOfMessage parsedMessage = null;
                    try {
                        parsedMessage = OneOfMessage.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (OneOfMessage)e.getUnfinishedMessage();
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
                    return (result == null) ? Type.PREPARE_EXECUTE : result;
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
                    this.type_ = 0;
                    this.onChanged();
                    return this;
                }
                
                @Override
                public boolean hasPrepareExecute() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }
                
                @Override
                public MysqlxPrepare.Execute getPrepareExecute() {
                    if (this.prepareExecuteBuilder_ == null) {
                        return (this.prepareExecute_ == null) ? MysqlxPrepare.Execute.getDefaultInstance() : this.prepareExecute_;
                    }
                    return this.prepareExecuteBuilder_.getMessage();
                }
                
                public Builder setPrepareExecute(final MysqlxPrepare.Execute value) {
                    if (this.prepareExecuteBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.prepareExecute_ = value;
                        this.onChanged();
                    }
                    else {
                        this.prepareExecuteBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder setPrepareExecute(final MysqlxPrepare.Execute.Builder builderForValue) {
                    if (this.prepareExecuteBuilder_ == null) {
                        this.prepareExecute_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.prepareExecuteBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder mergePrepareExecute(final MysqlxPrepare.Execute value) {
                    if (this.prepareExecuteBuilder_ == null) {
                        if ((this.bitField0_ & 0x2) != 0x0 && this.prepareExecute_ != null && this.prepareExecute_ != MysqlxPrepare.Execute.getDefaultInstance()) {
                            this.prepareExecute_ = MysqlxPrepare.Execute.newBuilder(this.prepareExecute_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.prepareExecute_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.prepareExecuteBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder clearPrepareExecute() {
                    if (this.prepareExecuteBuilder_ == null) {
                        this.prepareExecute_ = null;
                        this.onChanged();
                    }
                    else {
                        this.prepareExecuteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                public MysqlxPrepare.Execute.Builder getPrepareExecuteBuilder() {
                    this.bitField0_ |= 0x2;
                    this.onChanged();
                    return this.getPrepareExecuteFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxPrepare.ExecuteOrBuilder getPrepareExecuteOrBuilder() {
                    if (this.prepareExecuteBuilder_ != null) {
                        return this.prepareExecuteBuilder_.getMessageOrBuilder();
                    }
                    return (this.prepareExecute_ == null) ? MysqlxPrepare.Execute.getDefaultInstance() : this.prepareExecute_;
                }
                
                private SingleFieldBuilderV3<MysqlxPrepare.Execute, MysqlxPrepare.Execute.Builder, MysqlxPrepare.ExecuteOrBuilder> getPrepareExecuteFieldBuilder() {
                    if (this.prepareExecuteBuilder_ == null) {
                        this.prepareExecuteBuilder_ = new SingleFieldBuilderV3<MysqlxPrepare.Execute, MysqlxPrepare.Execute.Builder, MysqlxPrepare.ExecuteOrBuilder>(this.getPrepareExecute(), this.getParentForChildren(), this.isClean());
                        this.prepareExecute_ = null;
                    }
                    return this.prepareExecuteBuilder_;
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
            private int cursorId_;
            private OneOfMessage stmt_;
            private SingleFieldBuilderV3<OneOfMessage, OneOfMessage.Builder, OneOfMessageOrBuilder> stmtBuilder_;
            private long fetchRows_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_fieldAccessorTable.ensureFieldAccessorsInitialized(Open.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Open.alwaysUseFieldBuilders) {
                    this.getStmtFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.cursorId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                this.fetchRows_ = 0L;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Open_descriptor;
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
                    result.cursorId_ = this.cursorId_;
                    to_bitField0_ |= 0x1;
                }
                if ((from_bitField0_ & 0x2) != 0x0) {
                    if (this.stmtBuilder_ == null) {
                        result.stmt_ = this.stmt_;
                    }
                    else {
                        result.stmt_ = this.stmtBuilder_.build();
                    }
                    to_bitField0_ |= 0x2;
                }
                if ((from_bitField0_ & 0x4) != 0x0) {
                    result.fetchRows_ = this.fetchRows_;
                    to_bitField0_ |= 0x4;
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
                if (other.hasCursorId()) {
                    this.setCursorId(other.getCursorId());
                }
                if (other.hasStmt()) {
                    this.mergeStmt(other.getStmt());
                }
                if (other.hasFetchRows()) {
                    this.setFetchRows(other.getFetchRows());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasCursorId() && this.hasStmt() && this.getStmt().isInitialized();
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
            public boolean hasCursorId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getCursorId() {
                return this.cursorId_;
            }
            
            public Builder setCursorId(final int value) {
                this.bitField0_ |= 0x1;
                this.cursorId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCursorId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.cursorId_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasStmt() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public OneOfMessage getStmt() {
                if (this.stmtBuilder_ == null) {
                    return (this.stmt_ == null) ? OneOfMessage.getDefaultInstance() : this.stmt_;
                }
                return this.stmtBuilder_.getMessage();
            }
            
            public Builder setStmt(final OneOfMessage value) {
                if (this.stmtBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.stmt_ = value;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder setStmt(final OneOfMessage.Builder builderForValue) {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder mergeStmt(final OneOfMessage value) {
                if (this.stmtBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) != 0x0 && this.stmt_ != null && this.stmt_ != OneOfMessage.getDefaultInstance()) {
                        this.stmt_ = OneOfMessage.newBuilder(this.stmt_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.stmt_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder clearStmt() {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public OneOfMessage.Builder getStmtBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return this.getStmtFieldBuilder().getBuilder();
            }
            
            @Override
            public OneOfMessageOrBuilder getStmtOrBuilder() {
                if (this.stmtBuilder_ != null) {
                    return this.stmtBuilder_.getMessageOrBuilder();
                }
                return (this.stmt_ == null) ? OneOfMessage.getDefaultInstance() : this.stmt_;
            }
            
            private SingleFieldBuilderV3<OneOfMessage, OneOfMessage.Builder, OneOfMessageOrBuilder> getStmtFieldBuilder() {
                if (this.stmtBuilder_ == null) {
                    this.stmtBuilder_ = new SingleFieldBuilderV3<OneOfMessage, OneOfMessage.Builder, OneOfMessageOrBuilder>(this.getStmt(), this.getParentForChildren(), this.isClean());
                    this.stmt_ = null;
                }
                return this.stmtBuilder_;
            }
            
            @Override
            public boolean hasFetchRows() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public long getFetchRows() {
                return this.fetchRows_;
            }
            
            public Builder setFetchRows(final long value) {
                this.bitField0_ |= 0x4;
                this.fetchRows_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFetchRows() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.fetchRows_ = 0L;
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
        
        public interface OneOfMessageOrBuilder extends MessageOrBuilder
        {
            boolean hasType();
            
            OneOfMessage.Type getType();
            
            boolean hasPrepareExecute();
            
            MysqlxPrepare.Execute getPrepareExecute();
            
            MysqlxPrepare.ExecuteOrBuilder getPrepareExecuteOrBuilder();
        }
    }
    
    public static final class Fetch extends GeneratedMessageV3 implements FetchOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CURSOR_ID_FIELD_NUMBER = 1;
        private int cursorId_;
        public static final int FETCH_ROWS_FIELD_NUMBER = 5;
        private long fetchRows_;
        private byte memoizedIsInitialized;
        private static final Fetch DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Fetch> PARSER;
        
        private Fetch(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Fetch() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Fetch();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Fetch(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.cursorId_ = input.readUInt32();
                            continue;
                        }
                        case 40: {
                            this.bitField0_ |= 0x2;
                            this.fetchRows_ = input.readUInt64();
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
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_fieldAccessorTable.ensureFieldAccessorsInitialized(Fetch.class, Builder.class);
        }
        
        @Override
        public boolean hasCursorId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getCursorId() {
            return this.cursorId_;
        }
        
        @Override
        public boolean hasFetchRows() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public long getFetchRows() {
            return this.fetchRows_;
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
            if (!this.hasCursorId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeUInt32(1, this.cursorId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeUInt64(5, this.fetchRows_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.cursorId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeUInt64Size(5, this.fetchRows_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Fetch)) {
                return super.equals(obj);
            }
            final Fetch other = (Fetch)obj;
            return this.hasCursorId() == other.hasCursorId() && (!this.hasCursorId() || this.getCursorId() == other.getCursorId()) && this.hasFetchRows() == other.hasFetchRows() && (!this.hasFetchRows() || this.getFetchRows() == other.getFetchRows()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCursorId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCursorId();
            }
            if (this.hasFetchRows()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + Internal.hashLong(this.getFetchRows());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Fetch parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data);
        }
        
        public static Fetch parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Fetch parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data);
        }
        
        public static Fetch parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Fetch parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data);
        }
        
        public static Fetch parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Fetch.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Fetch parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Fetch.PARSER, input);
        }
        
        public static Fetch parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Fetch.PARSER, input, extensionRegistry);
        }
        
        public static Fetch parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Fetch.PARSER, input);
        }
        
        public static Fetch parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Fetch.PARSER, input, extensionRegistry);
        }
        
        public static Fetch parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Fetch.PARSER, input);
        }
        
        public static Fetch parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Fetch.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Fetch.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Fetch prototype) {
            return Fetch.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Fetch.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Fetch getDefaultInstance() {
            return Fetch.DEFAULT_INSTANCE;
        }
        
        public static Parser<Fetch> parser() {
            return Fetch.PARSER;
        }
        
        @Override
        public Parser<Fetch> getParserForType() {
            return Fetch.PARSER;
        }
        
        @Override
        public Fetch getDefaultInstanceForType() {
            return Fetch.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Fetch();
            PARSER = new AbstractParser<Fetch>() {
                @Override
                public Fetch parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Fetch(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchOrBuilder
        {
            private int bitField0_;
            private int cursorId_;
            private long fetchRows_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_fieldAccessorTable.ensureFieldAccessorsInitialized(Fetch.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Fetch.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.cursorId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                this.fetchRows_ = 0L;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Fetch_descriptor;
            }
            
            @Override
            public Fetch getDefaultInstanceForType() {
                return Fetch.getDefaultInstance();
            }
            
            @Override
            public Fetch build() {
                final Fetch result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Fetch buildPartial() {
                final Fetch result = new Fetch((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.cursorId_ = this.cursorId_;
                    to_bitField0_ |= 0x1;
                }
                if ((from_bitField0_ & 0x2) != 0x0) {
                    result.fetchRows_ = this.fetchRows_;
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
                if (other instanceof Fetch) {
                    return this.mergeFrom((Fetch)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Fetch other) {
                if (other == Fetch.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCursorId()) {
                    this.setCursorId(other.getCursorId());
                }
                if (other.hasFetchRows()) {
                    this.setFetchRows(other.getFetchRows());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasCursorId();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Fetch parsedMessage = null;
                try {
                    parsedMessage = Fetch.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Fetch)e.getUnfinishedMessage();
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
            public boolean hasCursorId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getCursorId() {
                return this.cursorId_;
            }
            
            public Builder setCursorId(final int value) {
                this.bitField0_ |= 0x1;
                this.cursorId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCursorId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.cursorId_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasFetchRows() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public long getFetchRows() {
                return this.fetchRows_;
            }
            
            public Builder setFetchRows(final long value) {
                this.bitField0_ |= 0x2;
                this.fetchRows_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFetchRows() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.fetchRows_ = 0L;
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
    
    public static final class Close extends GeneratedMessageV3 implements CloseOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CURSOR_ID_FIELD_NUMBER = 1;
        private int cursorId_;
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
                            this.cursorId_ = input.readUInt32();
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
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Close_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCursor.internal_static_Mysqlx_Cursor_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
        }
        
        @Override
        public boolean hasCursorId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getCursorId() {
            return this.cursorId_;
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
            if (!this.hasCursorId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeUInt32(1, this.cursorId_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.cursorId_);
            }
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
            return this.hasCursorId() == other.hasCursorId() && (!this.hasCursorId() || this.getCursorId() == other.getCursorId()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCursorId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCursorId();
            }
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
            private int bitField0_;
            private int cursorId_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Close_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
                this.cursorId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCursor.internal_static_Mysqlx_Cursor_Close_descriptor;
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
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.cursorId_ = this.cursorId_;
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
                if (other.hasCursorId()) {
                    this.setCursorId(other.getCursorId());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasCursorId();
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
            public boolean hasCursorId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getCursorId() {
                return this.cursorId_;
            }
            
            public Builder setCursorId(final int value) {
                this.bitField0_ |= 0x1;
                this.cursorId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCursorId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.cursorId_ = 0;
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
    
    public interface CloseOrBuilder extends MessageOrBuilder
    {
        boolean hasCursorId();
        
        int getCursorId();
    }
    
    public interface FetchOrBuilder extends MessageOrBuilder
    {
        boolean hasCursorId();
        
        int getCursorId();
        
        boolean hasFetchRows();
        
        long getFetchRows();
    }
    
    public interface OpenOrBuilder extends MessageOrBuilder
    {
        boolean hasCursorId();
        
        int getCursorId();
        
        boolean hasStmt();
        
        Open.OneOfMessage getStmt();
        
        Open.OneOfMessageOrBuilder getStmtOrBuilder();
        
        boolean hasFetchRows();
        
        long getFetchRows();
    }
}
