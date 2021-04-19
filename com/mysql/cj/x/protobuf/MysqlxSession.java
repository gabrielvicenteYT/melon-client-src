package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import com.google.protobuf.*;

public final class MysqlxSession
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_Reset_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_Reset_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_Close_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxSession() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxSession.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0014mysqlx_session.proto\u0012\u000eMysqlx.Session\u001a\fmysqlx.proto\"Y\n\u0011AuthenticateStart\u0012\u0011\n\tmech_name\u0018\u0001 \u0002(\t\u0012\u0011\n\tauth_data\u0018\u0002 \u0001(\f\u0012\u0018\n\u0010initial_response\u0018\u0003 \u0001(\f:\u0004\u0088\u00ea0\u0004\"3\n\u0014AuthenticateContinue\u0012\u0011\n\tauth_data\u0018\u0001 \u0002(\f:\b\u0090\u00ea0\u0003\u0088\u00ea0\u0005\")\n\u000eAuthenticateOk\u0012\u0011\n\tauth_data\u0018\u0001 \u0001(\f:\u0004\u0090\u00ea0\u0004\"'\n\u0005Reset\u0012\u0018\n\tkeep_open\u0018\u0001 \u0001(\b:\u0005false:\u0004\u0088\u00ea0\u0006\"\r\n\u0005Close:\u0004\u0088\u00ea0\u0007B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxSession.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor() });
        internal_static_Mysqlx_Session_AuthenticateStart_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor, new String[] { "MechName", "AuthData", "InitialResponse" });
        internal_static_Mysqlx_Session_AuthenticateContinue_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor, new String[] { "AuthData" });
        internal_static_Mysqlx_Session_AuthenticateOk_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor, new String[] { "AuthData" });
        internal_static_Mysqlx_Session_Reset_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Session_Reset_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor, new String[] { "KeepOpen" });
        internal_static_Mysqlx_Session_Close_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Session_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor, new String[0]);
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.clientMessageId);
        registry.add(Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxSession.descriptor, registry);
        Mysqlx.getDescriptor();
    }
    
    public static final class AuthenticateStart extends GeneratedMessageV3 implements AuthenticateStartOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MECH_NAME_FIELD_NUMBER = 1;
        private volatile Object mechName_;
        public static final int AUTH_DATA_FIELD_NUMBER = 2;
        private ByteString authData_;
        public static final int INITIAL_RESPONSE_FIELD_NUMBER = 3;
        private ByteString initialResponse_;
        private byte memoizedIsInitialized;
        private static final AuthenticateStart DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateStart> PARSER;
        
        private AuthenticateStart(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateStart() {
            this.memoizedIsInitialized = -1;
            this.mechName_ = "";
            this.authData_ = ByteString.EMPTY;
            this.initialResponse_ = ByteString.EMPTY;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new AuthenticateStart();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateStart(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.mechName_ = bs;
                            continue;
                        }
                        case 18: {
                            this.bitField0_ |= 0x2;
                            this.authData_ = input.readBytes();
                            continue;
                        }
                        case 26: {
                            this.bitField0_ |= 0x4;
                            this.initialResponse_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateStart.class, Builder.class);
        }
        
        @Override
        public boolean hasMechName() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public String getMechName() {
            final Object ref = this.mechName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.mechName_ = s;
            }
            return s;
        }
        
        @Override
        public ByteString getMechNameBytes() {
            final Object ref = this.mechName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.mechName_ = b);
            }
            return (ByteString)ref;
        }
        
        @Override
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public ByteString getAuthData() {
            return this.authData_;
        }
        
        @Override
        public boolean hasInitialResponse() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public ByteString getInitialResponse() {
            return this.initialResponse_;
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
            if (!this.hasMechName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                GeneratedMessageV3.writeString(output, 1, this.mechName_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                output.writeBytes(2, this.authData_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeBytes(3, this.initialResponse_);
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
                size += GeneratedMessageV3.computeStringSize(1, this.mechName_);
            }
            if ((this.bitField0_ & 0x2) != 0x0) {
                size += CodedOutputStream.computeBytesSize(2, this.authData_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeBytesSize(3, this.initialResponse_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateStart)) {
                return super.equals(obj);
            }
            final AuthenticateStart other = (AuthenticateStart)obj;
            return this.hasMechName() == other.hasMechName() && (!this.hasMechName() || this.getMechName().equals(other.getMechName())) && this.hasAuthData() == other.hasAuthData() && (!this.hasAuthData() || this.getAuthData().equals(other.getAuthData())) && this.hasInitialResponse() == other.hasInitialResponse() && (!this.hasInitialResponse() || this.getInitialResponse().equals(other.getInitialResponse())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasMechName()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getMechName().hashCode();
            }
            if (this.hasAuthData()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            if (this.hasInitialResponse()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getInitialResponse().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateStart parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateStart parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateStart.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateStart prototype) {
            return AuthenticateStart.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == AuthenticateStart.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateStart getDefaultInstance() {
            return AuthenticateStart.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateStart> parser() {
            return AuthenticateStart.PARSER;
        }
        
        @Override
        public Parser<AuthenticateStart> getParserForType() {
            return AuthenticateStart.PARSER;
        }
        
        @Override
        public AuthenticateStart getDefaultInstanceForType() {
            return AuthenticateStart.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateStart();
            PARSER = new AbstractParser<AuthenticateStart>() {
                @Override
                public AuthenticateStart parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateStart(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateStartOrBuilder
        {
            private int bitField0_;
            private Object mechName_;
            private ByteString authData_;
            private ByteString initialResponse_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateStart.class, Builder.class);
            }
            
            private Builder() {
                this.mechName_ = "";
                this.authData_ = ByteString.EMPTY;
                this.initialResponse_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.mechName_ = "";
                this.authData_ = ByteString.EMPTY;
                this.initialResponse_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateStart.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.mechName_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.initialResponse_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
            }
            
            @Override
            public AuthenticateStart getDefaultInstanceForType() {
                return AuthenticateStart.getDefaultInstance();
            }
            
            @Override
            public AuthenticateStart build() {
                final AuthenticateStart result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public AuthenticateStart buildPartial() {
                final AuthenticateStart result = new AuthenticateStart((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.mechName_ = this.mechName_;
                if ((from_bitField0_ & 0x2) != 0x0) {
                    to_bitField0_ |= 0x2;
                }
                result.authData_ = this.authData_;
                if ((from_bitField0_ & 0x4) != 0x0) {
                    to_bitField0_ |= 0x4;
                }
                result.initialResponse_ = this.initialResponse_;
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
                if (other instanceof AuthenticateStart) {
                    return this.mergeFrom((AuthenticateStart)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateStart other) {
                if (other == AuthenticateStart.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMechName()) {
                    this.bitField0_ |= 0x1;
                    this.mechName_ = other.mechName_;
                    this.onChanged();
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
                }
                if (other.hasInitialResponse()) {
                    this.setInitialResponse(other.getInitialResponse());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasMechName();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                AuthenticateStart parsedMessage = null;
                try {
                    parsedMessage = AuthenticateStart.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateStart)e.getUnfinishedMessage();
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
            public boolean hasMechName() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public String getMechName() {
                final Object ref = this.mechName_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.mechName_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            @Override
            public ByteString getMechNameBytes() {
                final Object ref = this.mechName_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.mechName_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setMechName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.mechName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearMechName() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.mechName_ = AuthenticateStart.getDefaultInstance().getMechName();
                this.onChanged();
                return this;
            }
            
            public Builder setMechNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.mechName_ = value;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.authData_ = AuthenticateStart.getDefaultInstance().getAuthData();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasInitialResponse() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public ByteString getInitialResponse() {
                return this.initialResponse_;
            }
            
            public Builder setInitialResponse(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.initialResponse_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearInitialResponse() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.initialResponse_ = AuthenticateStart.getDefaultInstance().getInitialResponse();
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
    
    public static final class AuthenticateContinue extends GeneratedMessageV3 implements AuthenticateContinueOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int AUTH_DATA_FIELD_NUMBER = 1;
        private ByteString authData_;
        private byte memoizedIsInitialized;
        private static final AuthenticateContinue DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateContinue> PARSER;
        
        private AuthenticateContinue(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateContinue() {
            this.memoizedIsInitialized = -1;
            this.authData_ = ByteString.EMPTY;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new AuthenticateContinue();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateContinue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.authData_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateContinue.class, Builder.class);
        }
        
        @Override
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public ByteString getAuthData() {
            return this.authData_;
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
            if (!this.hasAuthData()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        @Override
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeBytes(1, this.authData_);
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
                size += CodedOutputStream.computeBytesSize(1, this.authData_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateContinue)) {
                return super.equals(obj);
            }
            final AuthenticateContinue other = (AuthenticateContinue)obj;
            return this.hasAuthData() == other.hasAuthData() && (!this.hasAuthData() || this.getAuthData().equals(other.getAuthData())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasAuthData()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateContinue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateContinue parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateContinue.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateContinue prototype) {
            return AuthenticateContinue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == AuthenticateContinue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateContinue getDefaultInstance() {
            return AuthenticateContinue.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateContinue> parser() {
            return AuthenticateContinue.PARSER;
        }
        
        @Override
        public Parser<AuthenticateContinue> getParserForType() {
            return AuthenticateContinue.PARSER;
        }
        
        @Override
        public AuthenticateContinue getDefaultInstanceForType() {
            return AuthenticateContinue.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateContinue();
            PARSER = new AbstractParser<AuthenticateContinue>() {
                @Override
                public AuthenticateContinue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateContinue(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateContinueOrBuilder
        {
            private int bitField0_;
            private ByteString authData_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateContinue.class, Builder.class);
            }
            
            private Builder() {
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateContinue.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
            }
            
            @Override
            public AuthenticateContinue getDefaultInstanceForType() {
                return AuthenticateContinue.getDefaultInstance();
            }
            
            @Override
            public AuthenticateContinue build() {
                final AuthenticateContinue result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public AuthenticateContinue buildPartial() {
                final AuthenticateContinue result = new AuthenticateContinue((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.authData_ = this.authData_;
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
                if (other instanceof AuthenticateContinue) {
                    return this.mergeFrom((AuthenticateContinue)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateContinue other) {
                if (other == AuthenticateContinue.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            @Override
            public final boolean isInitialized() {
                return this.hasAuthData();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                AuthenticateContinue parsedMessage = null;
                try {
                    parsedMessage = AuthenticateContinue.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateContinue)e.getUnfinishedMessage();
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
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = AuthenticateContinue.getDefaultInstance().getAuthData();
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
    
    public static final class AuthenticateOk extends GeneratedMessageV3 implements AuthenticateOkOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int AUTH_DATA_FIELD_NUMBER = 1;
        private ByteString authData_;
        private byte memoizedIsInitialized;
        private static final AuthenticateOk DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateOk> PARSER;
        
        private AuthenticateOk(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateOk() {
            this.memoizedIsInitialized = -1;
            this.authData_ = ByteString.EMPTY;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new AuthenticateOk();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateOk(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.authData_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateOk.class, Builder.class);
        }
        
        @Override
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public ByteString getAuthData() {
            return this.authData_;
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
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeBytes(1, this.authData_);
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
                size += CodedOutputStream.computeBytesSize(1, this.authData_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateOk)) {
                return super.equals(obj);
            }
            final AuthenticateOk other = (AuthenticateOk)obj;
            return this.hasAuthData() == other.hasAuthData() && (!this.hasAuthData() || this.getAuthData().equals(other.getAuthData())) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasAuthData()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateOk parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateOk parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateOk.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateOk prototype) {
            return AuthenticateOk.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == AuthenticateOk.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateOk getDefaultInstance() {
            return AuthenticateOk.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateOk> parser() {
            return AuthenticateOk.PARSER;
        }
        
        @Override
        public Parser<AuthenticateOk> getParserForType() {
            return AuthenticateOk.PARSER;
        }
        
        @Override
        public AuthenticateOk getDefaultInstanceForType() {
            return AuthenticateOk.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateOk();
            PARSER = new AbstractParser<AuthenticateOk>() {
                @Override
                public AuthenticateOk parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateOk(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateOkOrBuilder
        {
            private int bitField0_;
            private ByteString authData_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable.ensureFieldAccessorsInitialized(AuthenticateOk.class, Builder.class);
            }
            
            private Builder() {
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateOk.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
            }
            
            @Override
            public AuthenticateOk getDefaultInstanceForType() {
                return AuthenticateOk.getDefaultInstance();
            }
            
            @Override
            public AuthenticateOk build() {
                final AuthenticateOk result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public AuthenticateOk buildPartial() {
                final AuthenticateOk result = new AuthenticateOk((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.authData_ = this.authData_;
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
                if (other instanceof AuthenticateOk) {
                    return this.mergeFrom((AuthenticateOk)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateOk other) {
                if (other == AuthenticateOk.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
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
                AuthenticateOk parsedMessage = null;
                try {
                    parsedMessage = AuthenticateOk.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateOk)e.getUnfinishedMessage();
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
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = AuthenticateOk.getDefaultInstance().getAuthData();
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
    
    public static final class Reset extends GeneratedMessageV3 implements ResetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int KEEP_OPEN_FIELD_NUMBER = 1;
        private boolean keepOpen_;
        private byte memoizedIsInitialized;
        private static final Reset DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Reset> PARSER;
        
        private Reset(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Reset() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Reset();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Reset(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.keepOpen_ = input.readBool();
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
            return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_Reset_fieldAccessorTable.ensureFieldAccessorsInitialized(Reset.class, Builder.class);
        }
        
        @Override
        public boolean hasKeepOpen() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public boolean getKeepOpen() {
            return this.keepOpen_;
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
            if ((this.bitField0_ & 0x1) != 0x0) {
                output.writeBool(1, this.keepOpen_);
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
                size += CodedOutputStream.computeBoolSize(1, this.keepOpen_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Reset)) {
                return super.equals(obj);
            }
            final Reset other = (Reset)obj;
            return this.hasKeepOpen() == other.hasKeepOpen() && (!this.hasKeepOpen() || this.getKeepOpen() == other.getKeepOpen()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasKeepOpen()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + Internal.hashBoolean(this.getKeepOpen());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Reset parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Reset.PARSER, input);
        }
        
        public static Reset parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Reset.PARSER, input, extensionRegistry);
        }
        
        public static Reset parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Reset.PARSER, input);
        }
        
        public static Reset parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Reset.PARSER, input, extensionRegistry);
        }
        
        public static Reset parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Reset.PARSER, input);
        }
        
        public static Reset parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Reset.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Reset.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Reset prototype) {
            return Reset.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Reset.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Reset getDefaultInstance() {
            return Reset.DEFAULT_INSTANCE;
        }
        
        public static Parser<Reset> parser() {
            return Reset.PARSER;
        }
        
        @Override
        public Parser<Reset> getParserForType() {
            return Reset.PARSER;
        }
        
        @Override
        public Reset getDefaultInstanceForType() {
            return Reset.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Reset();
            PARSER = new AbstractParser<Reset>() {
                @Override
                public Reset parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Reset(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ResetOrBuilder
        {
            private int bitField0_;
            private boolean keepOpen_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_fieldAccessorTable.ensureFieldAccessorsInitialized(Reset.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Reset.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.keepOpen_ = false;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
            }
            
            @Override
            public Reset getDefaultInstanceForType() {
                return Reset.getDefaultInstance();
            }
            
            @Override
            public Reset build() {
                final Reset result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Reset buildPartial() {
                final Reset result = new Reset((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    result.keepOpen_ = this.keepOpen_;
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
                if (other instanceof Reset) {
                    return this.mergeFrom((Reset)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Reset other) {
                if (other == Reset.getDefaultInstance()) {
                    return this;
                }
                if (other.hasKeepOpen()) {
                    this.setKeepOpen(other.getKeepOpen());
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
                Reset parsedMessage = null;
                try {
                    parsedMessage = Reset.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Reset)e.getUnfinishedMessage();
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
            public boolean hasKeepOpen() {
                return (this.bitField0_ & 0x1) != 0x0;
            }
            
            @Override
            public boolean getKeepOpen() {
                return this.keepOpen_;
            }
            
            public Builder setKeepOpen(final boolean value) {
                this.bitField0_ |= 0x1;
                this.keepOpen_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearKeepOpen() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.keepOpen_ = false;
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
            return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
                return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_Close_fieldAccessorTable.ensureFieldAccessorsInitialized(Close.class, Builder.class);
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
                return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
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
    
    public interface ResetOrBuilder extends MessageOrBuilder
    {
        boolean hasKeepOpen();
        
        boolean getKeepOpen();
    }
    
    public interface AuthenticateOkOrBuilder extends MessageOrBuilder
    {
        boolean hasAuthData();
        
        ByteString getAuthData();
    }
    
    public interface AuthenticateContinueOrBuilder extends MessageOrBuilder
    {
        boolean hasAuthData();
        
        ByteString getAuthData();
    }
    
    public interface AuthenticateStartOrBuilder extends MessageOrBuilder
    {
        boolean hasMechName();
        
        String getMechName();
        
        ByteString getMechNameBytes();
        
        boolean hasAuthData();
        
        ByteString getAuthData();
        
        boolean hasInitialResponse();
        
        ByteString getInitialResponse();
    }
}
