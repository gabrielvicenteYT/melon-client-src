package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import com.google.protobuf.*;
import java.util.*;

public final class MysqlxPrepare
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Prepare_Prepare_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Prepare_Prepare_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Prepare_Execute_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Prepare_Execute_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Prepare_Deallocate_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Prepare_Deallocate_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxPrepare() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxPrepare.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0014mysqlx_prepare.proto\u0012\u000eMysqlx.Prepare\u001a\fmysqlx.proto\u001a\u0010mysqlx_sql.proto\u001a\u0011mysqlx_crud.proto\u001a\u0016mysqlx_datatypes.proto\"\u009d\u0003\n\u0007Prepare\u0012\u000f\n\u0007stmt_id\u0018\u0001 \u0002(\r\u00122\n\u0004stmt\u0018\u0002 \u0002(\u000b2$.Mysqlx.Prepare.Prepare.OneOfMessage\u001a\u00c6\u0002\n\fOneOfMessage\u00127\n\u0004type\u0018\u0001 \u0002(\u000e2).Mysqlx.Prepare.Prepare.OneOfMessage.Type\u0012\u001f\n\u0004find\u0018\u0002 \u0001(\u000b2\u0011.Mysqlx.Crud.Find\u0012#\n\u0006insert\u0018\u0003 \u0001(\u000b2\u0013.Mysqlx.Crud.Insert\u0012#\n\u0006update\u0018\u0004 \u0001(\u000b2\u0013.Mysqlx.Crud.Update\u0012#\n\u0006delete\u0018\u0005 \u0001(\u000b2\u0013.Mysqlx.Crud.Delete\u0012-\n\fstmt_execute\u0018\u0006 \u0001(\u000b2\u0017.Mysqlx.Sql.StmtExecute\">\n\u0004Type\u0012\b\n\u0004FIND\u0010\u0000\u0012\n\n\u0006INSERT\u0010\u0001\u0012\n\n\u0006UPDATE\u0010\u0002\u0012\n\n\u0006DELETE\u0010\u0004\u0012\b\n\u0004STMT\u0010\u0005:\u0004\u0088\u00ea0(\"f\n\u0007Execute\u0012\u000f\n\u0007stmt_id\u0018\u0001 \u0002(\r\u0012#\n\u0004args\u0018\u0002 \u0003(\u000b2\u0015.Mysqlx.Datatypes.Any\u0012\u001f\n\u0010compact_metadata\u0018\u0003 \u0001(\b:\u0005false:\u0004\u0088\u00ea0)\"#\n\nDeallocate\u0012\u000f\n\u0007stmt_id\u0018\u0001 \u0002(\r:\u0004\u0088\u00ea0*B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxPrepare.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor(), MysqlxSql.getDescriptor(), MysqlxCrud.getDescriptor(), MysqlxDatatypes.getDescriptor() });
        internal_static_Mysqlx_Prepare_Prepare_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Prepare_Prepare_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_descriptor, new String[] { "StmtId", "Stmt" });
        internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor = MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor, new String[] { "Type", "Find", "Insert", "Update", "Delete", "StmtExecute" });
        internal_static_Mysqlx_Prepare_Execute_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Prepare_Execute_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_descriptor, new String[] { "StmtId", "Args", "CompactMetadata" });
        internal_static_Mysqlx_Prepare_Deallocate_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Prepare_Deallocate_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_descriptor, new String[] { "StmtId" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.clientMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxPrepare.descriptor, registry);
        Mysqlx.getDescriptor();
        MysqlxSql.getDescriptor();
        MysqlxCrud.getDescriptor();
        MysqlxDatatypes.getDescriptor();
    }
    
    public static final class Prepare extends GeneratedMessageV3 implements PrepareOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int STMT_ID_FIELD_NUMBER = 1;
        private int stmtId_;
        public static final int STMT_FIELD_NUMBER = 2;
        private OneOfMessage stmt_;
        private byte memoizedIsInitialized;
        private static final Prepare DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Prepare> PARSER;
        
        private Prepare(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Prepare() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Prepare();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Prepare(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.stmtId_ = input.readUInt32();
                            continue;
                        }
                        case 18: {
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
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_fieldAccessorTable.ensureFieldAccessorsInitialized(Prepare.class, Builder.class);
        }
        
        @Override
        public boolean hasStmtId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getStmtId() {
            return this.stmtId_;
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
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasStmtId()) {
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
                output.writeUInt32(1, this.stmtId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeMessage(2, this.getStmt());
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
                size += CodedOutputStream.computeUInt32Size(1, this.stmtId_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeMessageSize(2, this.getStmt());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Prepare)) {
                return super.equals(obj);
            }
            final Prepare other = (Prepare)obj;
            return this.hasStmtId() == other.hasStmtId() && (!this.hasStmtId() || this.getStmtId() == other.getStmtId()) && this.hasStmt() == other.hasStmt() && (!this.hasStmt() || this.getStmt().equals(other.getStmt())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasStmtId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getStmtId();
            }
            if (this.hasStmt()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getStmt().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Prepare parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data);
        }
        
        public static Prepare parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Prepare parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data);
        }
        
        public static Prepare parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Prepare parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data);
        }
        
        public static Prepare parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Prepare.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Prepare parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Prepare.PARSER, input);
        }
        
        public static Prepare parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Prepare.PARSER, input, extensionRegistry);
        }
        
        public static Prepare parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Prepare.PARSER, input);
        }
        
        public static Prepare parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Prepare.PARSER, input, extensionRegistry);
        }
        
        public static Prepare parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Prepare.PARSER, input);
        }
        
        public static Prepare parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Prepare.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Prepare.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Prepare prototype) {
            return Prepare.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Prepare.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Prepare getDefaultInstance() {
            return Prepare.DEFAULT_INSTANCE;
        }
        
        public static Parser<Prepare> parser() {
            return Prepare.PARSER;
        }
        
        @Override
        public Parser<Prepare> getParserForType() {
            return Prepare.PARSER;
        }
        
        @Override
        public Prepare getDefaultInstanceForType() {
            return Prepare.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Prepare();
            PARSER = new AbstractParser<Prepare>() {
                @Override
                public Prepare parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Prepare(input, extensionRegistry);
                }
            };
        }
        
        public static final class OneOfMessage extends GeneratedMessageV3 implements OneOfMessageOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int TYPE_FIELD_NUMBER = 1;
            private int type_;
            public static final int FIND_FIELD_NUMBER = 2;
            private MysqlxCrud.Find find_;
            public static final int INSERT_FIELD_NUMBER = 3;
            private MysqlxCrud.Insert insert_;
            public static final int UPDATE_FIELD_NUMBER = 4;
            private MysqlxCrud.Update update_;
            public static final int DELETE_FIELD_NUMBER = 5;
            private MysqlxCrud.Delete delete_;
            public static final int STMT_EXECUTE_FIELD_NUMBER = 6;
            private MysqlxSql.StmtExecute stmtExecute_;
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
                                MysqlxCrud.Find.Builder subBuilder = null;
                                if ((this.bitField0_ & 0x2) != 0x0) {
                                    subBuilder = this.find_.toBuilder();
                                }
                                this.find_ = input.readMessage(MysqlxCrud.Find.PARSER, extensionRegistry);
                                if (subBuilder != null) {
                                    subBuilder.mergeFrom(this.find_);
                                    this.find_ = subBuilder.buildPartial();
                                }
                                this.bitField0_ |= 0x2;
                                continue;
                            }
                            case 26: {
                                MysqlxCrud.Insert.Builder subBuilder2 = null;
                                if ((this.bitField0_ & 0x4) != 0x0) {
                                    subBuilder2 = this.insert_.toBuilder();
                                }
                                this.insert_ = input.readMessage(MysqlxCrud.Insert.PARSER, extensionRegistry);
                                if (subBuilder2 != null) {
                                    subBuilder2.mergeFrom(this.insert_);
                                    this.insert_ = subBuilder2.buildPartial();
                                }
                                this.bitField0_ |= 0x4;
                                continue;
                            }
                            case 34: {
                                MysqlxCrud.Update.Builder subBuilder3 = null;
                                if ((this.bitField0_ & 0x8) != 0x0) {
                                    subBuilder3 = this.update_.toBuilder();
                                }
                                this.update_ = input.readMessage(MysqlxCrud.Update.PARSER, extensionRegistry);
                                if (subBuilder3 != null) {
                                    subBuilder3.mergeFrom(this.update_);
                                    this.update_ = subBuilder3.buildPartial();
                                }
                                this.bitField0_ |= 0x8;
                                continue;
                            }
                            case 42: {
                                MysqlxCrud.Delete.Builder subBuilder4 = null;
                                if ((this.bitField0_ & 0x10) != 0x0) {
                                    subBuilder4 = this.delete_.toBuilder();
                                }
                                this.delete_ = input.readMessage(MysqlxCrud.Delete.PARSER, extensionRegistry);
                                if (subBuilder4 != null) {
                                    subBuilder4.mergeFrom(this.delete_);
                                    this.delete_ = subBuilder4.buildPartial();
                                }
                                this.bitField0_ |= 0x10;
                                continue;
                            }
                            case 50: {
                                MysqlxSql.StmtExecute.Builder subBuilder5 = null;
                                if ((this.bitField0_ & 0x20) != 0x0) {
                                    subBuilder5 = this.stmtExecute_.toBuilder();
                                }
                                this.stmtExecute_ = input.readMessage(MysqlxSql.StmtExecute.PARSER, extensionRegistry);
                                if (subBuilder5 != null) {
                                    subBuilder5.mergeFrom(this.stmtExecute_);
                                    this.stmtExecute_ = subBuilder5.buildPartial();
                                }
                                this.bitField0_ |= 0x20;
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
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(OneOfMessage.class, Builder.class);
            }
            
            @Override
            public boolean hasType() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.FIND : result;
            }
            
            @Override
            public boolean hasFind() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public MysqlxCrud.Find getFind() {
                return (this.find_ == null) ? MysqlxCrud.Find.getDefaultInstance() : this.find_;
            }
            
            @Override
            public MysqlxCrud.FindOrBuilder getFindOrBuilder() {
                return (this.find_ == null) ? MysqlxCrud.Find.getDefaultInstance() : this.find_;
            }
            
            @Override
            public boolean hasInsert() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public MysqlxCrud.Insert getInsert() {
                return (this.insert_ == null) ? MysqlxCrud.Insert.getDefaultInstance() : this.insert_;
            }
            
            @Override
            public MysqlxCrud.InsertOrBuilder getInsertOrBuilder() {
                return (this.insert_ == null) ? MysqlxCrud.Insert.getDefaultInstance() : this.insert_;
            }
            
            @Override
            public boolean hasUpdate() {
                return (this.bitField0_ & 0x8) != 0x0;
            }
            
            @Override
            public MysqlxCrud.Update getUpdate() {
                return (this.update_ == null) ? MysqlxCrud.Update.getDefaultInstance() : this.update_;
            }
            
            @Override
            public MysqlxCrud.UpdateOrBuilder getUpdateOrBuilder() {
                return (this.update_ == null) ? MysqlxCrud.Update.getDefaultInstance() : this.update_;
            }
            
            @Override
            public boolean hasDelete() {
                return (this.bitField0_ & 0x10) != 0x0;
            }
            
            @Override
            public MysqlxCrud.Delete getDelete() {
                return (this.delete_ == null) ? MysqlxCrud.Delete.getDefaultInstance() : this.delete_;
            }
            
            @Override
            public MysqlxCrud.DeleteOrBuilder getDeleteOrBuilder() {
                return (this.delete_ == null) ? MysqlxCrud.Delete.getDefaultInstance() : this.delete_;
            }
            
            @Override
            public boolean hasStmtExecute() {
                return (this.bitField0_ & 0x20) != 0x0;
            }
            
            @Override
            public MysqlxSql.StmtExecute getStmtExecute() {
                return (this.stmtExecute_ == null) ? MysqlxSql.StmtExecute.getDefaultInstance() : this.stmtExecute_;
            }
            
            @Override
            public MysqlxSql.StmtExecuteOrBuilder getStmtExecuteOrBuilder() {
                return (this.stmtExecute_ == null) ? MysqlxSql.StmtExecute.getDefaultInstance() : this.stmtExecute_;
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
                if (this.hasFind() && !this.getFind().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (this.hasInsert() && !this.getInsert().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (this.hasUpdate() && !this.getUpdate().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (this.hasDelete() && !this.getDelete().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
                if (this.hasStmtExecute() && !this.getStmtExecute().isInitialized()) {
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
                    output.writeMessage(2, this.getFind());
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    output.writeMessage(3, this.getInsert());
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    output.writeMessage(4, this.getUpdate());
                }
                if ((this.bitField0_ & 0x10) != 0x0) {
                    output.writeMessage(5, this.getDelete());
                }
                if ((this.bitField0_ & 0x20) != 0x0) {
                    output.writeMessage(6, this.getStmtExecute());
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
                    size += CodedOutputStream.computeMessageSize(2, this.getFind());
                }
                if ((this.bitField0_ & 0x4) != 0x0) {
                    size += CodedOutputStream.computeMessageSize(3, this.getInsert());
                }
                if ((this.bitField0_ & 0x8) != 0x0) {
                    size += CodedOutputStream.computeMessageSize(4, this.getUpdate());
                }
                if ((this.bitField0_ & 0x10) != 0x0) {
                    size += CodedOutputStream.computeMessageSize(5, this.getDelete());
                }
                if ((this.bitField0_ & 0x20) != 0x0) {
                    size += CodedOutputStream.computeMessageSize(6, this.getStmtExecute());
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
                return this.hasType() == other.hasType() && (!this.hasType() || this.type_ == other.type_) && this.hasFind() == other.hasFind() && (!this.hasFind() || this.getFind().equals(other.getFind())) && this.hasInsert() == other.hasInsert() && (!this.hasInsert() || this.getInsert().equals(other.getInsert())) && this.hasUpdate() == other.hasUpdate() && (!this.hasUpdate() || this.getUpdate().equals(other.getUpdate())) && this.hasDelete() == other.hasDelete() && (!this.hasDelete() || this.getDelete().equals(other.getDelete())) && this.hasStmtExecute() == other.hasStmtExecute() && (!this.hasStmtExecute() || this.getStmtExecute().equals(other.getStmtExecute())) && this.unknownFields.equals(other.unknownFields);
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
                if (this.hasFind()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getFind().hashCode();
                }
                if (this.hasInsert()) {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + this.getInsert().hashCode();
                }
                if (this.hasUpdate()) {
                    hash = 37 * hash + 4;
                    hash = 53 * hash + this.getUpdate().hashCode();
                }
                if (this.hasDelete()) {
                    hash = 37 * hash + 5;
                    hash = 53 * hash + this.getDelete().hashCode();
                }
                if (this.hasStmtExecute()) {
                    hash = 37 * hash + 6;
                    hash = 53 * hash + this.getStmtExecute().hashCode();
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
                FIND(0), 
                INSERT(1), 
                UPDATE(2), 
                DELETE(4), 
                STMT(5);
                
                public static final int FIND_VALUE = 0;
                public static final int INSERT_VALUE = 1;
                public static final int UPDATE_VALUE = 2;
                public static final int DELETE_VALUE = 4;
                public static final int STMT_VALUE = 5;
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
                            return Type.FIND;
                        }
                        case 1: {
                            return Type.INSERT;
                        }
                        case 2: {
                            return Type.UPDATE;
                        }
                        case 4: {
                            return Type.DELETE;
                        }
                        case 5: {
                            return Type.STMT;
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
                private MysqlxCrud.Find find_;
                private SingleFieldBuilderV3<MysqlxCrud.Find, MysqlxCrud.Find.Builder, MysqlxCrud.FindOrBuilder> findBuilder_;
                private MysqlxCrud.Insert insert_;
                private SingleFieldBuilderV3<MysqlxCrud.Insert, MysqlxCrud.Insert.Builder, MysqlxCrud.InsertOrBuilder> insertBuilder_;
                private MysqlxCrud.Update update_;
                private SingleFieldBuilderV3<MysqlxCrud.Update, MysqlxCrud.Update.Builder, MysqlxCrud.UpdateOrBuilder> updateBuilder_;
                private MysqlxCrud.Delete delete_;
                private SingleFieldBuilderV3<MysqlxCrud.Delete, MysqlxCrud.Delete.Builder, MysqlxCrud.DeleteOrBuilder> deleteBuilder_;
                private MysqlxSql.StmtExecute stmtExecute_;
                private SingleFieldBuilderV3<MysqlxSql.StmtExecute, MysqlxSql.StmtExecute.Builder, MysqlxSql.StmtExecuteOrBuilder> stmtExecuteBuilder_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor;
                }
                
                @Override
                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(OneOfMessage.class, Builder.class);
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
                        this.getFindFieldBuilder();
                        this.getInsertFieldBuilder();
                        this.getUpdateFieldBuilder();
                        this.getDeleteFieldBuilder();
                        this.getStmtExecuteFieldBuilder();
                    }
                }
                
                @Override
                public Builder clear() {
                    super.clear();
                    this.type_ = 0;
                    this.bitField0_ &= 0xFFFFFFFE;
                    if (this.findBuilder_ == null) {
                        this.find_ = null;
                    }
                    else {
                        this.findBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    if (this.insertBuilder_ == null) {
                        this.insert_ = null;
                    }
                    else {
                        this.insertBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFB;
                    if (this.updateBuilder_ == null) {
                        this.update_ = null;
                    }
                    else {
                        this.updateBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFF7;
                    if (this.deleteBuilder_ == null) {
                        this.delete_ = null;
                    }
                    else {
                        this.deleteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFEF;
                    if (this.stmtExecuteBuilder_ == null) {
                        this.stmtExecute_ = null;
                    }
                    else {
                        this.stmtExecuteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFDF;
                    return this;
                }
                
                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_OneOfMessage_descriptor;
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
                        if (this.findBuilder_ == null) {
                            result.find_ = this.find_;
                        }
                        else {
                            result.find_ = this.findBuilder_.build();
                        }
                        to_bitField0_ |= 0x2;
                    }
                    if ((from_bitField0_ & 0x4) != 0x0) {
                        if (this.insertBuilder_ == null) {
                            result.insert_ = this.insert_;
                        }
                        else {
                            result.insert_ = this.insertBuilder_.build();
                        }
                        to_bitField0_ |= 0x4;
                    }
                    if ((from_bitField0_ & 0x8) != 0x0) {
                        if (this.updateBuilder_ == null) {
                            result.update_ = this.update_;
                        }
                        else {
                            result.update_ = this.updateBuilder_.build();
                        }
                        to_bitField0_ |= 0x8;
                    }
                    if ((from_bitField0_ & 0x10) != 0x0) {
                        if (this.deleteBuilder_ == null) {
                            result.delete_ = this.delete_;
                        }
                        else {
                            result.delete_ = this.deleteBuilder_.build();
                        }
                        to_bitField0_ |= 0x10;
                    }
                    if ((from_bitField0_ & 0x20) != 0x0) {
                        if (this.stmtExecuteBuilder_ == null) {
                            result.stmtExecute_ = this.stmtExecute_;
                        }
                        else {
                            result.stmtExecute_ = this.stmtExecuteBuilder_.build();
                        }
                        to_bitField0_ |= 0x20;
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
                    if (other.hasFind()) {
                        this.mergeFind(other.getFind());
                    }
                    if (other.hasInsert()) {
                        this.mergeInsert(other.getInsert());
                    }
                    if (other.hasUpdate()) {
                        this.mergeUpdate(other.getUpdate());
                    }
                    if (other.hasDelete()) {
                        this.mergeDelete(other.getDelete());
                    }
                    if (other.hasStmtExecute()) {
                        this.mergeStmtExecute(other.getStmtExecute());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                @Override
                public final boolean isInitialized() {
                    return this.hasType() && (!this.hasFind() || this.getFind().isInitialized()) && (!this.hasInsert() || this.getInsert().isInitialized()) && (!this.hasUpdate() || this.getUpdate().isInitialized()) && (!this.hasDelete() || this.getDelete().isInitialized()) && (!this.hasStmtExecute() || this.getStmtExecute().isInitialized());
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
                    return (result == null) ? Type.FIND : result;
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
                public boolean hasFind() {
                    return (this.bitField0_ & 0x2) != 0x0;
                }
                
                @Override
                public MysqlxCrud.Find getFind() {
                    if (this.findBuilder_ == null) {
                        return (this.find_ == null) ? MysqlxCrud.Find.getDefaultInstance() : this.find_;
                    }
                    return this.findBuilder_.getMessage();
                }
                
                public Builder setFind(final MysqlxCrud.Find value) {
                    if (this.findBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.find_ = value;
                        this.onChanged();
                    }
                    else {
                        this.findBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder setFind(final MysqlxCrud.Find.Builder builderForValue) {
                    if (this.findBuilder_ == null) {
                        this.find_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.findBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder mergeFind(final MysqlxCrud.Find value) {
                    if (this.findBuilder_ == null) {
                        if ((this.bitField0_ & 0x2) != 0x0 && this.find_ != null && this.find_ != MysqlxCrud.Find.getDefaultInstance()) {
                            this.find_ = MysqlxCrud.Find.newBuilder(this.find_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.find_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.findBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x2;
                    return this;
                }
                
                public Builder clearFind() {
                    if (this.findBuilder_ == null) {
                        this.find_ = null;
                        this.onChanged();
                    }
                    else {
                        this.findBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFD;
                    return this;
                }
                
                public MysqlxCrud.Find.Builder getFindBuilder() {
                    this.bitField0_ |= 0x2;
                    this.onChanged();
                    return this.getFindFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxCrud.FindOrBuilder getFindOrBuilder() {
                    if (this.findBuilder_ != null) {
                        return this.findBuilder_.getMessageOrBuilder();
                    }
                    return (this.find_ == null) ? MysqlxCrud.Find.getDefaultInstance() : this.find_;
                }
                
                private SingleFieldBuilderV3<MysqlxCrud.Find, MysqlxCrud.Find.Builder, MysqlxCrud.FindOrBuilder> getFindFieldBuilder() {
                    if (this.findBuilder_ == null) {
                        this.findBuilder_ = new SingleFieldBuilderV3<MysqlxCrud.Find, MysqlxCrud.Find.Builder, MysqlxCrud.FindOrBuilder>(this.getFind(), this.getParentForChildren(), this.isClean());
                        this.find_ = null;
                    }
                    return this.findBuilder_;
                }
                
                @Override
                public boolean hasInsert() {
                    return (this.bitField0_ & 0x4) != 0x0;
                }
                
                @Override
                public MysqlxCrud.Insert getInsert() {
                    if (this.insertBuilder_ == null) {
                        return (this.insert_ == null) ? MysqlxCrud.Insert.getDefaultInstance() : this.insert_;
                    }
                    return this.insertBuilder_.getMessage();
                }
                
                public Builder setInsert(final MysqlxCrud.Insert value) {
                    if (this.insertBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.insert_ = value;
                        this.onChanged();
                    }
                    else {
                        this.insertBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x4;
                    return this;
                }
                
                public Builder setInsert(final MysqlxCrud.Insert.Builder builderForValue) {
                    if (this.insertBuilder_ == null) {
                        this.insert_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.insertBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x4;
                    return this;
                }
                
                public Builder mergeInsert(final MysqlxCrud.Insert value) {
                    if (this.insertBuilder_ == null) {
                        if ((this.bitField0_ & 0x4) != 0x0 && this.insert_ != null && this.insert_ != MysqlxCrud.Insert.getDefaultInstance()) {
                            this.insert_ = MysqlxCrud.Insert.newBuilder(this.insert_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.insert_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.insertBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x4;
                    return this;
                }
                
                public Builder clearInsert() {
                    if (this.insertBuilder_ == null) {
                        this.insert_ = null;
                        this.onChanged();
                    }
                    else {
                        this.insertBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFFB;
                    return this;
                }
                
                public MysqlxCrud.Insert.Builder getInsertBuilder() {
                    this.bitField0_ |= 0x4;
                    this.onChanged();
                    return this.getInsertFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxCrud.InsertOrBuilder getInsertOrBuilder() {
                    if (this.insertBuilder_ != null) {
                        return this.insertBuilder_.getMessageOrBuilder();
                    }
                    return (this.insert_ == null) ? MysqlxCrud.Insert.getDefaultInstance() : this.insert_;
                }
                
                private SingleFieldBuilderV3<MysqlxCrud.Insert, MysqlxCrud.Insert.Builder, MysqlxCrud.InsertOrBuilder> getInsertFieldBuilder() {
                    if (this.insertBuilder_ == null) {
                        this.insertBuilder_ = new SingleFieldBuilderV3<MysqlxCrud.Insert, MysqlxCrud.Insert.Builder, MysqlxCrud.InsertOrBuilder>(this.getInsert(), this.getParentForChildren(), this.isClean());
                        this.insert_ = null;
                    }
                    return this.insertBuilder_;
                }
                
                @Override
                public boolean hasUpdate() {
                    return (this.bitField0_ & 0x8) != 0x0;
                }
                
                @Override
                public MysqlxCrud.Update getUpdate() {
                    if (this.updateBuilder_ == null) {
                        return (this.update_ == null) ? MysqlxCrud.Update.getDefaultInstance() : this.update_;
                    }
                    return this.updateBuilder_.getMessage();
                }
                
                public Builder setUpdate(final MysqlxCrud.Update value) {
                    if (this.updateBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.update_ = value;
                        this.onChanged();
                    }
                    else {
                        this.updateBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x8;
                    return this;
                }
                
                public Builder setUpdate(final MysqlxCrud.Update.Builder builderForValue) {
                    if (this.updateBuilder_ == null) {
                        this.update_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.updateBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x8;
                    return this;
                }
                
                public Builder mergeUpdate(final MysqlxCrud.Update value) {
                    if (this.updateBuilder_ == null) {
                        if ((this.bitField0_ & 0x8) != 0x0 && this.update_ != null && this.update_ != MysqlxCrud.Update.getDefaultInstance()) {
                            this.update_ = MysqlxCrud.Update.newBuilder(this.update_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.update_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.updateBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x8;
                    return this;
                }
                
                public Builder clearUpdate() {
                    if (this.updateBuilder_ == null) {
                        this.update_ = null;
                        this.onChanged();
                    }
                    else {
                        this.updateBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFF7;
                    return this;
                }
                
                public MysqlxCrud.Update.Builder getUpdateBuilder() {
                    this.bitField0_ |= 0x8;
                    this.onChanged();
                    return this.getUpdateFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxCrud.UpdateOrBuilder getUpdateOrBuilder() {
                    if (this.updateBuilder_ != null) {
                        return this.updateBuilder_.getMessageOrBuilder();
                    }
                    return (this.update_ == null) ? MysqlxCrud.Update.getDefaultInstance() : this.update_;
                }
                
                private SingleFieldBuilderV3<MysqlxCrud.Update, MysqlxCrud.Update.Builder, MysqlxCrud.UpdateOrBuilder> getUpdateFieldBuilder() {
                    if (this.updateBuilder_ == null) {
                        this.updateBuilder_ = new SingleFieldBuilderV3<MysqlxCrud.Update, MysqlxCrud.Update.Builder, MysqlxCrud.UpdateOrBuilder>(this.getUpdate(), this.getParentForChildren(), this.isClean());
                        this.update_ = null;
                    }
                    return this.updateBuilder_;
                }
                
                @Override
                public boolean hasDelete() {
                    return (this.bitField0_ & 0x10) != 0x0;
                }
                
                @Override
                public MysqlxCrud.Delete getDelete() {
                    if (this.deleteBuilder_ == null) {
                        return (this.delete_ == null) ? MysqlxCrud.Delete.getDefaultInstance() : this.delete_;
                    }
                    return this.deleteBuilder_.getMessage();
                }
                
                public Builder setDelete(final MysqlxCrud.Delete value) {
                    if (this.deleteBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.delete_ = value;
                        this.onChanged();
                    }
                    else {
                        this.deleteBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x10;
                    return this;
                }
                
                public Builder setDelete(final MysqlxCrud.Delete.Builder builderForValue) {
                    if (this.deleteBuilder_ == null) {
                        this.delete_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.deleteBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x10;
                    return this;
                }
                
                public Builder mergeDelete(final MysqlxCrud.Delete value) {
                    if (this.deleteBuilder_ == null) {
                        if ((this.bitField0_ & 0x10) != 0x0 && this.delete_ != null && this.delete_ != MysqlxCrud.Delete.getDefaultInstance()) {
                            this.delete_ = MysqlxCrud.Delete.newBuilder(this.delete_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.delete_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.deleteBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x10;
                    return this;
                }
                
                public Builder clearDelete() {
                    if (this.deleteBuilder_ == null) {
                        this.delete_ = null;
                        this.onChanged();
                    }
                    else {
                        this.deleteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFEF;
                    return this;
                }
                
                public MysqlxCrud.Delete.Builder getDeleteBuilder() {
                    this.bitField0_ |= 0x10;
                    this.onChanged();
                    return this.getDeleteFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxCrud.DeleteOrBuilder getDeleteOrBuilder() {
                    if (this.deleteBuilder_ != null) {
                        return this.deleteBuilder_.getMessageOrBuilder();
                    }
                    return (this.delete_ == null) ? MysqlxCrud.Delete.getDefaultInstance() : this.delete_;
                }
                
                private SingleFieldBuilderV3<MysqlxCrud.Delete, MysqlxCrud.Delete.Builder, MysqlxCrud.DeleteOrBuilder> getDeleteFieldBuilder() {
                    if (this.deleteBuilder_ == null) {
                        this.deleteBuilder_ = new SingleFieldBuilderV3<MysqlxCrud.Delete, MysqlxCrud.Delete.Builder, MysqlxCrud.DeleteOrBuilder>(this.getDelete(), this.getParentForChildren(), this.isClean());
                        this.delete_ = null;
                    }
                    return this.deleteBuilder_;
                }
                
                @Override
                public boolean hasStmtExecute() {
                    return (this.bitField0_ & 0x20) != 0x0;
                }
                
                @Override
                public MysqlxSql.StmtExecute getStmtExecute() {
                    if (this.stmtExecuteBuilder_ == null) {
                        return (this.stmtExecute_ == null) ? MysqlxSql.StmtExecute.getDefaultInstance() : this.stmtExecute_;
                    }
                    return this.stmtExecuteBuilder_.getMessage();
                }
                
                public Builder setStmtExecute(final MysqlxSql.StmtExecute value) {
                    if (this.stmtExecuteBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.stmtExecute_ = value;
                        this.onChanged();
                    }
                    else {
                        this.stmtExecuteBuilder_.setMessage(value);
                    }
                    this.bitField0_ |= 0x20;
                    return this;
                }
                
                public Builder setStmtExecute(final MysqlxSql.StmtExecute.Builder builderForValue) {
                    if (this.stmtExecuteBuilder_ == null) {
                        this.stmtExecute_ = builderForValue.build();
                        this.onChanged();
                    }
                    else {
                        this.stmtExecuteBuilder_.setMessage(builderForValue.build());
                    }
                    this.bitField0_ |= 0x20;
                    return this;
                }
                
                public Builder mergeStmtExecute(final MysqlxSql.StmtExecute value) {
                    if (this.stmtExecuteBuilder_ == null) {
                        if ((this.bitField0_ & 0x20) != 0x0 && this.stmtExecute_ != null && this.stmtExecute_ != MysqlxSql.StmtExecute.getDefaultInstance()) {
                            this.stmtExecute_ = MysqlxSql.StmtExecute.newBuilder(this.stmtExecute_).mergeFrom(value).buildPartial();
                        }
                        else {
                            this.stmtExecute_ = value;
                        }
                        this.onChanged();
                    }
                    else {
                        this.stmtExecuteBuilder_.mergeFrom(value);
                    }
                    this.bitField0_ |= 0x20;
                    return this;
                }
                
                public Builder clearStmtExecute() {
                    if (this.stmtExecuteBuilder_ == null) {
                        this.stmtExecute_ = null;
                        this.onChanged();
                    }
                    else {
                        this.stmtExecuteBuilder_.clear();
                    }
                    this.bitField0_ &= 0xFFFFFFDF;
                    return this;
                }
                
                public MysqlxSql.StmtExecute.Builder getStmtExecuteBuilder() {
                    this.bitField0_ |= 0x20;
                    this.onChanged();
                    return this.getStmtExecuteFieldBuilder().getBuilder();
                }
                
                @Override
                public MysqlxSql.StmtExecuteOrBuilder getStmtExecuteOrBuilder() {
                    if (this.stmtExecuteBuilder_ != null) {
                        return this.stmtExecuteBuilder_.getMessageOrBuilder();
                    }
                    return (this.stmtExecute_ == null) ? MysqlxSql.StmtExecute.getDefaultInstance() : this.stmtExecute_;
                }
                
                private SingleFieldBuilderV3<MysqlxSql.StmtExecute, MysqlxSql.StmtExecute.Builder, MysqlxSql.StmtExecuteOrBuilder> getStmtExecuteFieldBuilder() {
                    if (this.stmtExecuteBuilder_ == null) {
                        this.stmtExecuteBuilder_ = new SingleFieldBuilderV3<MysqlxSql.StmtExecute, MysqlxSql.StmtExecute.Builder, MysqlxSql.StmtExecuteOrBuilder>(this.getStmtExecute(), this.getParentForChildren(), this.isClean());
                        this.stmtExecute_ = null;
                    }
                    return this.stmtExecuteBuilder_;
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PrepareOrBuilder
        {
            private int bitField0_;
            private int stmtId_;
            private OneOfMessage stmt_;
            private SingleFieldBuilderV3<OneOfMessage, OneOfMessage.Builder, OneOfMessageOrBuilder> stmtBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_fieldAccessorTable.ensureFieldAccessorsInitialized(Prepare.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Prepare.alwaysUseFieldBuilders) {
                    this.getStmtFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.stmtId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Prepare_descriptor;
            }
            
            @Override
            public Prepare getDefaultInstanceForType() {
                return Prepare.getDefaultInstance();
            }
            
            @Override
            public Prepare build() {
                final Prepare result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Prepare buildPartial() {
                final Prepare result = new Prepare((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.stmtId_ = this.stmtId_;
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
                if (other instanceof Prepare) {
                    return this.mergeFrom((Prepare)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Prepare other) {
                if (other == Prepare.getDefaultInstance()) {
                    return this;
                }
                if (other.hasStmtId()) {
                    this.setStmtId(other.getStmtId());
                }
                if (other.hasStmt()) {
                    this.mergeStmt(other.getStmt());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasStmtId() && this.hasStmt() && this.getStmt().isInitialized();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Prepare parsedMessage = null;
                try {
                    parsedMessage = Prepare.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Prepare)e.getUnfinishedMessage();
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
            public boolean hasStmtId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getStmtId() {
                return this.stmtId_;
            }
            
            public Builder setStmtId(final int value) {
                this.bitField0_ |= 0x1;
                this.stmtId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearStmtId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.stmtId_ = 0;
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
            
            boolean hasFind();
            
            MysqlxCrud.Find getFind();
            
            MysqlxCrud.FindOrBuilder getFindOrBuilder();
            
            boolean hasInsert();
            
            MysqlxCrud.Insert getInsert();
            
            MysqlxCrud.InsertOrBuilder getInsertOrBuilder();
            
            boolean hasUpdate();
            
            MysqlxCrud.Update getUpdate();
            
            MysqlxCrud.UpdateOrBuilder getUpdateOrBuilder();
            
            boolean hasDelete();
            
            MysqlxCrud.Delete getDelete();
            
            MysqlxCrud.DeleteOrBuilder getDeleteOrBuilder();
            
            boolean hasStmtExecute();
            
            MysqlxSql.StmtExecute getStmtExecute();
            
            MysqlxSql.StmtExecuteOrBuilder getStmtExecuteOrBuilder();
        }
    }
    
    public static final class Execute extends GeneratedMessageV3 implements ExecuteOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int STMT_ID_FIELD_NUMBER = 1;
        private int stmtId_;
        public static final int ARGS_FIELD_NUMBER = 2;
        private List<MysqlxDatatypes.Any> args_;
        public static final int COMPACT_METADATA_FIELD_NUMBER = 3;
        private boolean compactMetadata_;
        private byte memoizedIsInitialized;
        private static final Execute DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Execute> PARSER;
        
        private Execute(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Execute() {
            this.memoizedIsInitialized = -1;
            this.args_ = Collections.emptyList();
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Execute();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Execute(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.stmtId_ = input.readUInt32();
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x2) == 0x0) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Any>();
                                mutable_bitField0_ |= 0x2;
                            }
                            this.args_.add(input.readMessage(MysqlxDatatypes.Any.PARSER, extensionRegistry));
                            continue;
                        }
                        case 24: {
                            this.bitField0_ |= 0x2;
                            this.compactMetadata_ = input.readBool();
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
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_fieldAccessorTable.ensureFieldAccessorsInitialized(Execute.class, Builder.class);
        }
        
        @Override
        public boolean hasStmtId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getStmtId() {
            return this.stmtId_;
        }
        
        @Override
        public List<MysqlxDatatypes.Any> getArgsList() {
            return this.args_;
        }
        
        @Override
        public List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        @Override
        public int getArgsCount() {
            return this.args_.size();
        }
        
        @Override
        public MysqlxDatatypes.Any getArgs(final int index) {
            return this.args_.get(index);
        }
        
        @Override
        public MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        @Override
        public boolean hasCompactMetadata() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public boolean getCompactMetadata() {
            return this.compactMetadata_;
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
            if (!this.hasStmtId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgsCount(); ++i) {
                if (!this.getArgs(i).isInitialized()) {
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
                output.writeUInt32(1, this.stmtId_);
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(2, this.args_.get(i));
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeBool(3, this.compactMetadata_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.stmtId_);
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, this.args_.get(i));
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeBoolSize(3, this.compactMetadata_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Execute)) {
                return super.equals(obj);
            }
            final Execute other = (Execute)obj;
            return this.hasStmtId() == other.hasStmtId() && (!this.hasStmtId() || this.getStmtId() == other.getStmtId()) && this.getArgsList().equals(other.getArgsList()) && this.hasCompactMetadata() == other.hasCompactMetadata() && (!this.hasCompactMetadata() || this.getCompactMetadata() == other.getCompactMetadata()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasStmtId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getStmtId();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasCompactMetadata()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + Internal.hashBoolean(this.getCompactMetadata());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Execute parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data);
        }
        
        public static Execute parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Execute parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data);
        }
        
        public static Execute parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Execute parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data);
        }
        
        public static Execute parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Execute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Execute parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Execute.PARSER, input);
        }
        
        public static Execute parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Execute.PARSER, input, extensionRegistry);
        }
        
        public static Execute parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Execute.PARSER, input);
        }
        
        public static Execute parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Execute.PARSER, input, extensionRegistry);
        }
        
        public static Execute parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Execute.PARSER, input);
        }
        
        public static Execute parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Execute.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Execute.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Execute prototype) {
            return Execute.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Execute.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Execute getDefaultInstance() {
            return Execute.DEFAULT_INSTANCE;
        }
        
        public static Parser<Execute> parser() {
            return Execute.PARSER;
        }
        
        @Override
        public Parser<Execute> getParserForType() {
            return Execute.PARSER;
        }
        
        @Override
        public Execute getDefaultInstanceForType() {
            return Execute.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Execute();
            PARSER = new AbstractParser<Execute>() {
                @Override
                public Execute parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Execute(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ExecuteOrBuilder
        {
            private int bitField0_;
            private int stmtId_;
            private List<MysqlxDatatypes.Any> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> argsBuilder_;
            private boolean compactMetadata_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_fieldAccessorTable.ensureFieldAccessorsInitialized(Execute.class, Builder.class);
            }
            
            private Builder() {
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Execute.alwaysUseFieldBuilders) {
                    this.getArgsFieldBuilder();
                }
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.stmtId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.argsBuilder_.clear();
                }
                this.compactMetadata_ = false;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Execute_descriptor;
            }
            
            @Override
            public Execute getDefaultInstanceForType() {
                return Execute.getDefaultInstance();
            }
            
            @Override
            public Execute build() {
                final Execute result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Execute buildPartial() {
                final Execute result = new Execute((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.stmtId_ = this.stmtId_;
                    to_bitField0_ |= 0x1;
                }
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) != 0x0) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x4) != 0x0) {
                    result.compactMetadata_ = this.compactMetadata_;
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
                if (other instanceof Execute) {
                    return this.mergeFrom((Execute)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Execute other) {
                if (other == Execute.getDefaultInstance()) {
                    return this;
                }
                if (other.hasStmtId()) {
                    this.setStmtId(other.getStmtId());
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        }
                        else {
                            this.ensureArgsIsMutable();
                            this.args_.addAll(other.args_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.args_.isEmpty()) {
                    if (this.argsBuilder_.isEmpty()) {
                        this.argsBuilder_.dispose();
                        this.argsBuilder_ = null;
                        this.args_ = other.args_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.argsBuilder_ = (Execute.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages(other.args_);
                    }
                }
                if (other.hasCompactMetadata()) {
                    this.setCompactMetadata(other.getCompactMetadata());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                if (!this.hasStmtId()) {
                    return false;
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Execute parsedMessage = null;
                try {
                    parsedMessage = Execute.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Execute)e.getUnfinishedMessage();
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
            public boolean hasStmtId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getStmtId() {
                return this.stmtId_;
            }
            
            public Builder setStmtId(final int value) {
                this.bitField0_ |= 0x1;
                this.stmtId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearStmtId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.stmtId_ = 0;
                this.onChanged();
                return this;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x2) == 0x0) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Any>(this.args_);
                    this.bitField0_ |= 0x2;
                }
            }
            
            @Override
            public List<MysqlxDatatypes.Any> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                }
                return this.argsBuilder_.getMessageList();
            }
            
            @Override
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            @Override
            public MysqlxDatatypes.Any getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.setMessage(index, value);
                }
                return this;
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addArgs(final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.add(value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(value);
                }
                return this;
            }
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(index, value);
                }
                return this;
            }
            
            public Builder addArgs(final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Any> values) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.args_);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addAllMessages(values);
                }
                return this;
            }
            
            public Builder clearArgs() {
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.remove(index);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.remove(index);
                }
                return this;
            }
            
            public MysqlxDatatypes.Any.Builder getArgsBuilder(final int index) {
                return this.getArgsFieldBuilder().getBuilder(index);
            }
            
            @Override
            public MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            @Override
            public List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.AnyOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Any.Builder addArgsBuilder() {
                return this.getArgsFieldBuilder().addBuilder(MysqlxDatatypes.Any.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Any.Builder addArgsBuilder(final int index) {
                return this.getArgsFieldBuilder().addBuilder(index, MysqlxDatatypes.Any.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Any.Builder> getArgsBuilderList() {
                return this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = new RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder>(this.args_, (this.bitField0_ & 0x2) != 0x0, this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            @Override
            public boolean hasCompactMetadata() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public boolean getCompactMetadata() {
                return this.compactMetadata_;
            }
            
            public Builder setCompactMetadata(final boolean value) {
                this.bitField0_ |= 0x4;
                this.compactMetadata_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCompactMetadata() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.compactMetadata_ = false;
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
    
    public static final class Deallocate extends GeneratedMessageV3 implements DeallocateOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int STMT_ID_FIELD_NUMBER = 1;
        private int stmtId_;
        private byte memoizedIsInitialized;
        private static final Deallocate DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Deallocate> PARSER;
        
        private Deallocate(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Deallocate() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Deallocate();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Deallocate(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.stmtId_ = input.readUInt32();
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
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_fieldAccessorTable.ensureFieldAccessorsInitialized(Deallocate.class, Builder.class);
        }
        
        @Override
        public boolean hasStmtId() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public int getStmtId() {
            return this.stmtId_;
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
            if (!this.hasStmtId()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeUInt32(1, this.stmtId_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.stmtId_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Deallocate)) {
                return super.equals(obj);
            }
            final Deallocate other = (Deallocate)obj;
            return this.hasStmtId() == other.hasStmtId() && (!this.hasStmtId() || this.getStmtId() == other.getStmtId()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasStmtId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getStmtId();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Deallocate parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data);
        }
        
        public static Deallocate parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Deallocate parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data);
        }
        
        public static Deallocate parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Deallocate parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data);
        }
        
        public static Deallocate parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Deallocate.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Deallocate parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Deallocate.PARSER, input);
        }
        
        public static Deallocate parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Deallocate.PARSER, input, extensionRegistry);
        }
        
        public static Deallocate parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Deallocate.PARSER, input);
        }
        
        public static Deallocate parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Deallocate.PARSER, input, extensionRegistry);
        }
        
        public static Deallocate parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Deallocate.PARSER, input);
        }
        
        public static Deallocate parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Deallocate.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Deallocate.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Deallocate prototype) {
            return Deallocate.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Deallocate.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Deallocate getDefaultInstance() {
            return Deallocate.DEFAULT_INSTANCE;
        }
        
        public static Parser<Deallocate> parser() {
            return Deallocate.PARSER;
        }
        
        @Override
        public Parser<Deallocate> getParserForType() {
            return Deallocate.PARSER;
        }
        
        @Override
        public Deallocate getDefaultInstanceForType() {
            return Deallocate.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Deallocate();
            PARSER = new AbstractParser<Deallocate>() {
                @Override
                public Deallocate parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Deallocate(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements DeallocateOrBuilder
        {
            private int bitField0_;
            private int stmtId_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_fieldAccessorTable.ensureFieldAccessorsInitialized(Deallocate.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Deallocate.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.stmtId_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxPrepare.internal_static_Mysqlx_Prepare_Deallocate_descriptor;
            }
            
            @Override
            public Deallocate getDefaultInstanceForType() {
                return Deallocate.getDefaultInstance();
            }
            
            @Override
            public Deallocate build() {
                final Deallocate result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Deallocate buildPartial() {
                final Deallocate result = new Deallocate((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.stmtId_ = this.stmtId_;
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
                if (other instanceof Deallocate) {
                    return this.mergeFrom((Deallocate)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Deallocate other) {
                if (other == Deallocate.getDefaultInstance()) {
                    return this;
                }
                if (other.hasStmtId()) {
                    this.setStmtId(other.getStmtId());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasStmtId();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Deallocate parsedMessage = null;
                try {
                    parsedMessage = Deallocate.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Deallocate)e.getUnfinishedMessage();
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
            public boolean hasStmtId() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public int getStmtId() {
                return this.stmtId_;
            }
            
            public Builder setStmtId(final int value) {
                this.bitField0_ |= 0x1;
                this.stmtId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearStmtId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.stmtId_ = 0;
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
    
    public interface DeallocateOrBuilder extends MessageOrBuilder
    {
        boolean hasStmtId();
        
        int getStmtId();
    }
    
    public interface ExecuteOrBuilder extends MessageOrBuilder
    {
        boolean hasStmtId();
        
        int getStmtId();
        
        List<MysqlxDatatypes.Any> getArgsList();
        
        MysqlxDatatypes.Any getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasCompactMetadata();
        
        boolean getCompactMetadata();
    }
    
    public interface PrepareOrBuilder extends MessageOrBuilder
    {
        boolean hasStmtId();
        
        int getStmtId();
        
        boolean hasStmt();
        
        Prepare.OneOfMessage getStmt();
        
        Prepare.OneOfMessageOrBuilder getStmtOrBuilder();
    }
}
