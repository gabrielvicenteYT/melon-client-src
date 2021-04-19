package com.mysql.cj.x.protobuf;

import java.nio.*;
import java.io.*;
import java.util.*;
import com.google.protobuf.*;

public final class MysqlxResultset
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDone_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchSuspended_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchSuspended_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_Row_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_Row_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxResultset() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxResultset.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0016mysqlx_resultset.proto\u0012\u0010Mysqlx.Resultset\u001a\fmysqlx.proto\"\u001e\n\u0016FetchDoneMoreOutParams:\u0004\u0090\u00ea0\u0012\"\u001f\n\u0017FetchDoneMoreResultsets:\u0004\u0090\u00ea0\u0010\"\u0011\n\tFetchDone:\u0004\u0090\u00ea0\u000e\"\u0016\n\u000eFetchSuspended:\u0004\u0090\u00ea0\u000f\"¥\u0003\n\u000eColumnMetaData\u00128\n\u0004type\u0018\u0001 \u0002(\u000e2*.Mysqlx.Resultset.ColumnMetaData.FieldType\u0012\f\n\u0004name\u0018\u0002 \u0001(\f\u0012\u0015\n\roriginal_name\u0018\u0003 \u0001(\f\u0012\r\n\u0005table\u0018\u0004 \u0001(\f\u0012\u0016\n\u000eoriginal_table\u0018\u0005 \u0001(\f\u0012\u000e\n\u0006schema\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007catalog\u0018\u0007 \u0001(\f\u0012\u0011\n\tcollation\u0018\b \u0001(\u0004\u0012\u0019\n\u0011fractional_digits\u0018\t \u0001(\r\u0012\u000e\n\u0006length\u0018\n \u0001(\r\u0012\r\n\u0005flags\u0018\u000b \u0001(\r\u0012\u0014\n\fcontent_type\u0018\f \u0001(\r\"\u0082\u0001\n\tFieldType\u0012\b\n\u0004SINT\u0010\u0001\u0012\b\n\u0004UINT\u0010\u0002\u0012\n\n\u0006DOUBLE\u0010\u0005\u0012\t\n\u0005FLOAT\u0010\u0006\u0012\t\n\u0005BYTES\u0010\u0007\u0012\b\n\u0004TIME\u0010\n\u0012\f\n\bDATETIME\u0010\f\u0012\u0007\n\u0003SET\u0010\u000f\u0012\b\n\u0004ENUM\u0010\u0010\u0012\u0007\n\u0003BIT\u0010\u0011\u0012\u000b\n\u0007DECIMAL\u0010\u0012:\u0004\u0090\u00ea0\f\"\u001a\n\u0003Row\u0012\r\n\u0005field\u0018\u0001 \u0003(\f:\u0004\u0090\u00ea0\r*4\n\u0011ContentType_BYTES\u0012\f\n\bGEOMETRY\u0010\u0001\u0012\b\n\u0004JSON\u0010\u0002\u0012\u0007\n\u0003XML\u0010\u0003*.\n\u0014ContentType_DATETIME\u0012\b\n\u0004DATE\u0010\u0001\u0012\f\n\bDATETIME\u0010\u0002B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        MysqlxResultset.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor() });
        internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_FetchDone_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_FetchSuspended_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Resultset_FetchSuspended_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor, new String[] { "Type", "Name", "OriginalName", "Table", "OriginalTable", "Schema", "Catalog", "Collation", "FractionalDigits", "Length", "Flags", "ContentType" });
        internal_static_Mysqlx_Resultset_Row_descriptor = getDescriptor().getMessageTypes().get(5);
        internal_static_Mysqlx_Resultset_Row_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor, new String[] { "Field" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxResultset.descriptor, registry);
        Mysqlx.getDescriptor();
    }
    
    public enum ContentType_BYTES implements ProtocolMessageEnum
    {
        GEOMETRY(1), 
        JSON(2), 
        XML(3);
        
        public static final int GEOMETRY_VALUE = 1;
        public static final int JSON_VALUE = 2;
        public static final int XML_VALUE = 3;
        private static final Internal.EnumLiteMap<ContentType_BYTES> internalValueMap;
        private static final ContentType_BYTES[] VALUES;
        private final int value;
        
        @Override
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ContentType_BYTES valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ContentType_BYTES forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ContentType_BYTES.GEOMETRY;
                }
                case 2: {
                    return ContentType_BYTES.JSON;
                }
                case 3: {
                    return ContentType_BYTES.XML;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ContentType_BYTES> internalGetValueMap() {
            return ContentType_BYTES.internalValueMap;
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
            return MysqlxResultset.getDescriptor().getEnumTypes().get(0);
        }
        
        public static ContentType_BYTES valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ContentType_BYTES.VALUES[desc.getIndex()];
        }
        
        private ContentType_BYTES(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = new Internal.EnumLiteMap<ContentType_BYTES>() {
                @Override
                public ContentType_BYTES findValueByNumber(final int number) {
                    return ContentType_BYTES.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum ContentType_DATETIME implements ProtocolMessageEnum
    {
        DATE(1), 
        DATETIME(2);
        
        public static final int DATE_VALUE = 1;
        public static final int DATETIME_VALUE = 2;
        private static final Internal.EnumLiteMap<ContentType_DATETIME> internalValueMap;
        private static final ContentType_DATETIME[] VALUES;
        private final int value;
        
        @Override
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ContentType_DATETIME valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ContentType_DATETIME forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ContentType_DATETIME.DATE;
                }
                case 2: {
                    return ContentType_DATETIME.DATETIME;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ContentType_DATETIME> internalGetValueMap() {
            return ContentType_DATETIME.internalValueMap;
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
            return MysqlxResultset.getDescriptor().getEnumTypes().get(1);
        }
        
        public static ContentType_DATETIME valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ContentType_DATETIME.VALUES[desc.getIndex()];
        }
        
        private ContentType_DATETIME(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = new Internal.EnumLiteMap<ContentType_DATETIME>() {
                @Override
                public ContentType_DATETIME findValueByNumber(final int number) {
                    return ContentType_DATETIME.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public static final class FetchDoneMoreOutParams extends GeneratedMessageV3 implements FetchDoneMoreOutParamsOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDoneMoreOutParams DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDoneMoreOutParams> PARSER;
        
        private FetchDoneMoreOutParams(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDoneMoreOutParams() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new FetchDoneMoreOutParams();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDoneMoreOutParams(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDoneMoreOutParams.class, Builder.class);
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
            if (!(obj instanceof FetchDoneMoreOutParams)) {
                return super.equals(obj);
            }
            final FetchDoneMoreOutParams other = (FetchDoneMoreOutParams)obj;
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
        
        public static FetchDoneMoreOutParams parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDoneMoreOutParams prototype) {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == FetchDoneMoreOutParams.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDoneMoreOutParams getDefaultInstance() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDoneMoreOutParams> parser() {
            return FetchDoneMoreOutParams.PARSER;
        }
        
        @Override
        public Parser<FetchDoneMoreOutParams> getParserForType() {
            return FetchDoneMoreOutParams.PARSER;
        }
        
        @Override
        public FetchDoneMoreOutParams getDefaultInstanceForType() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDoneMoreOutParams();
            PARSER = new AbstractParser<FetchDoneMoreOutParams>() {
                @Override
                public FetchDoneMoreOutParams parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDoneMoreOutParams(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneMoreOutParamsOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDoneMoreOutParams.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDoneMoreOutParams.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
            }
            
            @Override
            public FetchDoneMoreOutParams getDefaultInstanceForType() {
                return FetchDoneMoreOutParams.getDefaultInstance();
            }
            
            @Override
            public FetchDoneMoreOutParams build() {
                final FetchDoneMoreOutParams result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public FetchDoneMoreOutParams buildPartial() {
                final FetchDoneMoreOutParams result = new FetchDoneMoreOutParams((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDoneMoreOutParams) {
                    return this.mergeFrom((FetchDoneMoreOutParams)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDoneMoreOutParams other) {
                if (other == FetchDoneMoreOutParams.getDefaultInstance()) {
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
                FetchDoneMoreOutParams parsedMessage = null;
                try {
                    parsedMessage = FetchDoneMoreOutParams.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDoneMoreOutParams)e.getUnfinishedMessage();
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
    
    public static final class FetchDoneMoreResultsets extends GeneratedMessageV3 implements FetchDoneMoreResultsetsOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDoneMoreResultsets DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDoneMoreResultsets> PARSER;
        
        private FetchDoneMoreResultsets(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDoneMoreResultsets() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new FetchDoneMoreResultsets();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDoneMoreResultsets(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDoneMoreResultsets.class, Builder.class);
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
            if (!(obj instanceof FetchDoneMoreResultsets)) {
                return super.equals(obj);
            }
            final FetchDoneMoreResultsets other = (FetchDoneMoreResultsets)obj;
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
        
        public static FetchDoneMoreResultsets parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDoneMoreResultsets prototype) {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == FetchDoneMoreResultsets.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDoneMoreResultsets getDefaultInstance() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDoneMoreResultsets> parser() {
            return FetchDoneMoreResultsets.PARSER;
        }
        
        @Override
        public Parser<FetchDoneMoreResultsets> getParserForType() {
            return FetchDoneMoreResultsets.PARSER;
        }
        
        @Override
        public FetchDoneMoreResultsets getDefaultInstanceForType() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDoneMoreResultsets();
            PARSER = new AbstractParser<FetchDoneMoreResultsets>() {
                @Override
                public FetchDoneMoreResultsets parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDoneMoreResultsets(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneMoreResultsetsOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDoneMoreResultsets.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDoneMoreResultsets.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
            }
            
            @Override
            public FetchDoneMoreResultsets getDefaultInstanceForType() {
                return FetchDoneMoreResultsets.getDefaultInstance();
            }
            
            @Override
            public FetchDoneMoreResultsets build() {
                final FetchDoneMoreResultsets result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public FetchDoneMoreResultsets buildPartial() {
                final FetchDoneMoreResultsets result = new FetchDoneMoreResultsets((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDoneMoreResultsets) {
                    return this.mergeFrom((FetchDoneMoreResultsets)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDoneMoreResultsets other) {
                if (other == FetchDoneMoreResultsets.getDefaultInstance()) {
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
                FetchDoneMoreResultsets parsedMessage = null;
                try {
                    parsedMessage = FetchDoneMoreResultsets.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDoneMoreResultsets)e.getUnfinishedMessage();
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
    
    public static final class FetchDone extends GeneratedMessageV3 implements FetchDoneOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDone DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDone> PARSER;
        
        private FetchDone(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDone() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new FetchDone();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDone(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDone.class, Builder.class);
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
            if (!(obj instanceof FetchDone)) {
                return super.equals(obj);
            }
            final FetchDone other = (FetchDone)obj;
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
        
        public static FetchDone parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDone.PARSER, input);
        }
        
        public static FetchDone parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDone.PARSER, input, extensionRegistry);
        }
        
        public static FetchDone parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDone.PARSER, input);
        }
        
        public static FetchDone parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchDone.PARSER, input, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDone.PARSER, input);
        }
        
        public static FetchDone parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchDone.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDone.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDone prototype) {
            return FetchDone.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == FetchDone.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDone getDefaultInstance() {
            return FetchDone.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDone> parser() {
            return FetchDone.PARSER;
        }
        
        @Override
        public Parser<FetchDone> getParserForType() {
            return FetchDone.PARSER;
        }
        
        @Override
        public FetchDone getDefaultInstanceForType() {
            return FetchDone.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDone();
            PARSER = new AbstractParser<FetchDone>() {
                @Override
                public FetchDone parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDone(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchDone.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDone.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
            }
            
            @Override
            public FetchDone getDefaultInstanceForType() {
                return FetchDone.getDefaultInstance();
            }
            
            @Override
            public FetchDone build() {
                final FetchDone result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public FetchDone buildPartial() {
                final FetchDone result = new FetchDone((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDone) {
                    return this.mergeFrom((FetchDone)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDone other) {
                if (other == FetchDone.getDefaultInstance()) {
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
                FetchDone parsedMessage = null;
                try {
                    parsedMessage = FetchDone.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDone)e.getUnfinishedMessage();
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
    
    public static final class FetchSuspended extends GeneratedMessageV3 implements FetchSuspendedOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchSuspended DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchSuspended> PARSER;
        
        private FetchSuspended(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchSuspended() {
            this.memoizedIsInitialized = -1;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new FetchSuspended();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchSuspended(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchSuspended.class, Builder.class);
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
            if (!(obj instanceof FetchSuspended)) {
                return super.equals(obj);
            }
            final FetchSuspended other = (FetchSuspended)obj;
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
        
        public static FetchSuspended parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data);
        }
        
        public static FetchSuspended parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchSuspended parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data);
        }
        
        public static FetchSuspended parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchSuspended parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data);
        }
        
        public static FetchSuspended parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return FetchSuspended.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchSuspended parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchSuspended.PARSER, input);
        }
        
        public static FetchSuspended parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchSuspended.PARSER, input, extensionRegistry);
        }
        
        public static FetchSuspended parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchSuspended.PARSER, input);
        }
        
        public static FetchSuspended parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(FetchSuspended.PARSER, input, extensionRegistry);
        }
        
        public static FetchSuspended parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchSuspended.PARSER, input);
        }
        
        public static FetchSuspended parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(FetchSuspended.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchSuspended.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchSuspended prototype) {
            return FetchSuspended.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == FetchSuspended.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchSuspended getDefaultInstance() {
            return FetchSuspended.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchSuspended> parser() {
            return FetchSuspended.PARSER;
        }
        
        @Override
        public Parser<FetchSuspended> getParserForType() {
            return FetchSuspended.PARSER;
        }
        
        @Override
        public FetchSuspended getDefaultInstanceForType() {
            return FetchSuspended.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchSuspended();
            PARSER = new AbstractParser<FetchSuspended>() {
                @Override
                public FetchSuspended parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchSuspended(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchSuspendedOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_fieldAccessorTable.ensureFieldAccessorsInitialized(FetchSuspended.class, Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchSuspended.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchSuspended_descriptor;
            }
            
            @Override
            public FetchSuspended getDefaultInstanceForType() {
                return FetchSuspended.getDefaultInstance();
            }
            
            @Override
            public FetchSuspended build() {
                final FetchSuspended result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public FetchSuspended buildPartial() {
                final FetchSuspended result = new FetchSuspended((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchSuspended) {
                    return this.mergeFrom((FetchSuspended)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchSuspended other) {
                if (other == FetchSuspended.getDefaultInstance()) {
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
                FetchSuspended parsedMessage = null;
                try {
                    parsedMessage = FetchSuspended.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchSuspended)e.getUnfinishedMessage();
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
    
    public static final class ColumnMetaData extends GeneratedMessageV3 implements ColumnMetaDataOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int NAME_FIELD_NUMBER = 2;
        private ByteString name_;
        public static final int ORIGINAL_NAME_FIELD_NUMBER = 3;
        private ByteString originalName_;
        public static final int TABLE_FIELD_NUMBER = 4;
        private ByteString table_;
        public static final int ORIGINAL_TABLE_FIELD_NUMBER = 5;
        private ByteString originalTable_;
        public static final int SCHEMA_FIELD_NUMBER = 6;
        private ByteString schema_;
        public static final int CATALOG_FIELD_NUMBER = 7;
        private ByteString catalog_;
        public static final int COLLATION_FIELD_NUMBER = 8;
        private long collation_;
        public static final int FRACTIONAL_DIGITS_FIELD_NUMBER = 9;
        private int fractionalDigits_;
        public static final int LENGTH_FIELD_NUMBER = 10;
        private int length_;
        public static final int FLAGS_FIELD_NUMBER = 11;
        private int flags_;
        public static final int CONTENT_TYPE_FIELD_NUMBER = 12;
        private int contentType_;
        private byte memoizedIsInitialized;
        private static final ColumnMetaData DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ColumnMetaData> PARSER;
        
        private ColumnMetaData(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ColumnMetaData() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
            this.name_ = ByteString.EMPTY;
            this.originalName_ = ByteString.EMPTY;
            this.table_ = ByteString.EMPTY;
            this.originalTable_ = ByteString.EMPTY;
            this.schema_ = ByteString.EMPTY;
            this.catalog_ = ByteString.EMPTY;
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new ColumnMetaData();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ColumnMetaData(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final FieldType value = FieldType.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 18: {
                            this.bitField0_ |= 0x2;
                            this.name_ = input.readBytes();
                            continue;
                        }
                        case 26: {
                            this.bitField0_ |= 0x4;
                            this.originalName_ = input.readBytes();
                            continue;
                        }
                        case 34: {
                            this.bitField0_ |= 0x8;
                            this.table_ = input.readBytes();
                            continue;
                        }
                        case 42: {
                            this.bitField0_ |= 0x10;
                            this.originalTable_ = input.readBytes();
                            continue;
                        }
                        case 50: {
                            this.bitField0_ |= 0x20;
                            this.schema_ = input.readBytes();
                            continue;
                        }
                        case 58: {
                            this.bitField0_ |= 0x40;
                            this.catalog_ = input.readBytes();
                            continue;
                        }
                        case 64: {
                            this.bitField0_ |= 0x80;
                            this.collation_ = input.readUInt64();
                            continue;
                        }
                        case 72: {
                            this.bitField0_ |= 0x100;
                            this.fractionalDigits_ = input.readUInt32();
                            continue;
                        }
                        case 80: {
                            this.bitField0_ |= 0x200;
                            this.length_ = input.readUInt32();
                            continue;
                        }
                        case 88: {
                            this.bitField0_ |= 0x400;
                            this.flags_ = input.readUInt32();
                            continue;
                        }
                        case 96: {
                            this.bitField0_ |= 0x800;
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable.ensureFieldAccessorsInitialized(ColumnMetaData.class, Builder.class);
        }
        
        @Override
        public boolean hasType() {
            return (this.bitField0_ & 0x1) != 0x0;
        }
        
        @Override
        public FieldType getType() {
            final FieldType result = FieldType.valueOf(this.type_);
            return (result == null) ? FieldType.SINT : result;
        }
        
        @Override
        public boolean hasName() {
            return (this.bitField0_ & 0x2) != 0x0;
        }
        
        @Override
        public ByteString getName() {
            return this.name_;
        }
        
        @Override
        public boolean hasOriginalName() {
            return (this.bitField0_ & 0x4) != 0x0;
        }
        
        @Override
        public ByteString getOriginalName() {
            return this.originalName_;
        }
        
        @Override
        public boolean hasTable() {
            return (this.bitField0_ & 0x8) != 0x0;
        }
        
        @Override
        public ByteString getTable() {
            return this.table_;
        }
        
        @Override
        public boolean hasOriginalTable() {
            return (this.bitField0_ & 0x10) != 0x0;
        }
        
        @Override
        public ByteString getOriginalTable() {
            return this.originalTable_;
        }
        
        @Override
        public boolean hasSchema() {
            return (this.bitField0_ & 0x20) != 0x0;
        }
        
        @Override
        public ByteString getSchema() {
            return this.schema_;
        }
        
        @Override
        public boolean hasCatalog() {
            return (this.bitField0_ & 0x40) != 0x0;
        }
        
        @Override
        public ByteString getCatalog() {
            return this.catalog_;
        }
        
        @Override
        public boolean hasCollation() {
            return (this.bitField0_ & 0x80) != 0x0;
        }
        
        @Override
        public long getCollation() {
            return this.collation_;
        }
        
        @Override
        public boolean hasFractionalDigits() {
            return (this.bitField0_ & 0x100) != 0x0;
        }
        
        @Override
        public int getFractionalDigits() {
            return this.fractionalDigits_;
        }
        
        @Override
        public boolean hasLength() {
            return (this.bitField0_ & 0x200) != 0x0;
        }
        
        @Override
        public int getLength() {
            return this.length_;
        }
        
        @Override
        public boolean hasFlags() {
            return (this.bitField0_ & 0x400) != 0x0;
        }
        
        @Override
        public int getFlags() {
            return this.flags_;
        }
        
        @Override
        public boolean hasContentType() {
            return (this.bitField0_ & 0x800) != 0x0;
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
            if (!this.hasType()) {
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
                output.writeBytes(2, this.name_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                output.writeBytes(3, this.originalName_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                output.writeBytes(4, this.table_);
            }
            if ((this.bitField0_ & 0x10) != 0x0) {
                output.writeBytes(5, this.originalTable_);
            }
            if ((this.bitField0_ & 0x20) != 0x0) {
                output.writeBytes(6, this.schema_);
            }
            if ((this.bitField0_ & 0x40) != 0x0) {
                output.writeBytes(7, this.catalog_);
            }
            if ((this.bitField0_ & 0x80) != 0x0) {
                output.writeUInt64(8, this.collation_);
            }
            if ((this.bitField0_ & 0x100) != 0x0) {
                output.writeUInt32(9, this.fractionalDigits_);
            }
            if ((this.bitField0_ & 0x200) != 0x0) {
                output.writeUInt32(10, this.length_);
            }
            if ((this.bitField0_ & 0x400) != 0x0) {
                output.writeUInt32(11, this.flags_);
            }
            if ((this.bitField0_ & 0x800) != 0x0) {
                output.writeUInt32(12, this.contentType_);
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
                size += CodedOutputStream.computeBytesSize(2, this.name_);
            }
            if ((this.bitField0_ & 0x4) != 0x0) {
                size += CodedOutputStream.computeBytesSize(3, this.originalName_);
            }
            if ((this.bitField0_ & 0x8) != 0x0) {
                size += CodedOutputStream.computeBytesSize(4, this.table_);
            }
            if ((this.bitField0_ & 0x10) != 0x0) {
                size += CodedOutputStream.computeBytesSize(5, this.originalTable_);
            }
            if ((this.bitField0_ & 0x20) != 0x0) {
                size += CodedOutputStream.computeBytesSize(6, this.schema_);
            }
            if ((this.bitField0_ & 0x40) != 0x0) {
                size += CodedOutputStream.computeBytesSize(7, this.catalog_);
            }
            if ((this.bitField0_ & 0x80) != 0x0) {
                size += CodedOutputStream.computeUInt64Size(8, this.collation_);
            }
            if ((this.bitField0_ & 0x100) != 0x0) {
                size += CodedOutputStream.computeUInt32Size(9, this.fractionalDigits_);
            }
            if ((this.bitField0_ & 0x200) != 0x0) {
                size += CodedOutputStream.computeUInt32Size(10, this.length_);
            }
            if ((this.bitField0_ & 0x400) != 0x0) {
                size += CodedOutputStream.computeUInt32Size(11, this.flags_);
            }
            if ((this.bitField0_ & 0x800) != 0x0) {
                size += CodedOutputStream.computeUInt32Size(12, this.contentType_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ColumnMetaData)) {
                return super.equals(obj);
            }
            final ColumnMetaData other = (ColumnMetaData)obj;
            return this.hasType() == other.hasType() && (!this.hasType() || this.type_ == other.type_) && this.hasName() == other.hasName() && (!this.hasName() || this.getName().equals(other.getName())) && this.hasOriginalName() == other.hasOriginalName() && (!this.hasOriginalName() || this.getOriginalName().equals(other.getOriginalName())) && this.hasTable() == other.hasTable() && (!this.hasTable() || this.getTable().equals(other.getTable())) && this.hasOriginalTable() == other.hasOriginalTable() && (!this.hasOriginalTable() || this.getOriginalTable().equals(other.getOriginalTable())) && this.hasSchema() == other.hasSchema() && (!this.hasSchema() || this.getSchema().equals(other.getSchema())) && this.hasCatalog() == other.hasCatalog() && (!this.hasCatalog() || this.getCatalog().equals(other.getCatalog())) && this.hasCollation() == other.hasCollation() && (!this.hasCollation() || this.getCollation() == other.getCollation()) && this.hasFractionalDigits() == other.hasFractionalDigits() && (!this.hasFractionalDigits() || this.getFractionalDigits() == other.getFractionalDigits()) && this.hasLength() == other.hasLength() && (!this.hasLength() || this.getLength() == other.getLength()) && this.hasFlags() == other.hasFlags() && (!this.hasFlags() || this.getFlags() == other.getFlags()) && this.hasContentType() == other.hasContentType() && (!this.hasContentType() || this.getContentType() == other.getContentType()) && this.unknownFields.equals(other.unknownFields);
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
            if (this.hasName()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getName().hashCode();
            }
            if (this.hasOriginalName()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getOriginalName().hashCode();
            }
            if (this.hasTable()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getTable().hashCode();
            }
            if (this.hasOriginalTable()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getOriginalTable().hashCode();
            }
            if (this.hasSchema()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getSchema().hashCode();
            }
            if (this.hasCatalog()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getCatalog().hashCode();
            }
            if (this.hasCollation()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + Internal.hashLong(this.getCollation());
            }
            if (this.hasFractionalDigits()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getFractionalDigits();
            }
            if (this.hasLength()) {
                hash = 37 * hash + 10;
                hash = 53 * hash + this.getLength();
            }
            if (this.hasFlags()) {
                hash = 37 * hash + 11;
                hash = 53 * hash + this.getFlags();
            }
            if (this.hasContentType()) {
                hash = 37 * hash + 12;
                hash = 53 * hash + this.getContentType();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ColumnMetaData parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        public static ColumnMetaData parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ColumnMetaData.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ColumnMetaData prototype) {
            return ColumnMetaData.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == ColumnMetaData.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ColumnMetaData getDefaultInstance() {
            return ColumnMetaData.DEFAULT_INSTANCE;
        }
        
        public static Parser<ColumnMetaData> parser() {
            return ColumnMetaData.PARSER;
        }
        
        @Override
        public Parser<ColumnMetaData> getParserForType() {
            return ColumnMetaData.PARSER;
        }
        
        @Override
        public ColumnMetaData getDefaultInstanceForType() {
            return ColumnMetaData.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ColumnMetaData();
            PARSER = new AbstractParser<ColumnMetaData>() {
                @Override
                public ColumnMetaData parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ColumnMetaData(input, extensionRegistry);
                }
            };
        }
        
        public enum FieldType implements ProtocolMessageEnum
        {
            SINT(1), 
            UINT(2), 
            DOUBLE(5), 
            FLOAT(6), 
            BYTES(7), 
            TIME(10), 
            DATETIME(12), 
            SET(15), 
            ENUM(16), 
            BIT(17), 
            DECIMAL(18);
            
            public static final int SINT_VALUE = 1;
            public static final int UINT_VALUE = 2;
            public static final int DOUBLE_VALUE = 5;
            public static final int FLOAT_VALUE = 6;
            public static final int BYTES_VALUE = 7;
            public static final int TIME_VALUE = 10;
            public static final int DATETIME_VALUE = 12;
            public static final int SET_VALUE = 15;
            public static final int ENUM_VALUE = 16;
            public static final int BIT_VALUE = 17;
            public static final int DECIMAL_VALUE = 18;
            private static final Internal.EnumLiteMap<FieldType> internalValueMap;
            private static final FieldType[] VALUES;
            private final int value;
            
            @Override
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static FieldType valueOf(final int value) {
                return forNumber(value);
            }
            
            public static FieldType forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return FieldType.SINT;
                    }
                    case 2: {
                        return FieldType.UINT;
                    }
                    case 5: {
                        return FieldType.DOUBLE;
                    }
                    case 6: {
                        return FieldType.FLOAT;
                    }
                    case 7: {
                        return FieldType.BYTES;
                    }
                    case 10: {
                        return FieldType.TIME;
                    }
                    case 12: {
                        return FieldType.DATETIME;
                    }
                    case 15: {
                        return FieldType.SET;
                    }
                    case 16: {
                        return FieldType.ENUM;
                    }
                    case 17: {
                        return FieldType.BIT;
                    }
                    case 18: {
                        return FieldType.DECIMAL;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<FieldType> internalGetValueMap() {
                return FieldType.internalValueMap;
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
                return ColumnMetaData.getDescriptor().getEnumTypes().get(0);
            }
            
            public static FieldType valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return FieldType.VALUES[desc.getIndex()];
            }
            
            private FieldType(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = new Internal.EnumLiteMap<FieldType>() {
                    @Override
                    public FieldType findValueByNumber(final int number) {
                        return FieldType.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ColumnMetaDataOrBuilder
        {
            private int bitField0_;
            private int type_;
            private ByteString name_;
            private ByteString originalName_;
            private ByteString table_;
            private ByteString originalTable_;
            private ByteString schema_;
            private ByteString catalog_;
            private long collation_;
            private int fractionalDigits_;
            private int length_;
            private int flags_;
            private int contentType_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable.ensureFieldAccessorsInitialized(ColumnMetaData.class, Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.name_ = ByteString.EMPTY;
                this.originalName_ = ByteString.EMPTY;
                this.table_ = ByteString.EMPTY;
                this.originalTable_ = ByteString.EMPTY;
                this.schema_ = ByteString.EMPTY;
                this.catalog_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.name_ = ByteString.EMPTY;
                this.originalName_ = ByteString.EMPTY;
                this.table_ = ByteString.EMPTY;
                this.originalTable_ = ByteString.EMPTY;
                this.schema_ = ByteString.EMPTY;
                this.catalog_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ColumnMetaData.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                this.name_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.originalName_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                this.table_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                this.originalTable_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFEF;
                this.schema_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                this.catalog_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFBF;
                this.collation_ = 0L;
                this.bitField0_ &= 0xFFFFFF7F;
                this.fractionalDigits_ = 0;
                this.bitField0_ &= 0xFFFFFEFF;
                this.length_ = 0;
                this.bitField0_ &= 0xFFFFFDFF;
                this.flags_ = 0;
                this.bitField0_ &= 0xFFFFFBFF;
                this.contentType_ = 0;
                this.bitField0_ &= 0xFFFFF7FF;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
            }
            
            @Override
            public ColumnMetaData getDefaultInstanceForType() {
                return ColumnMetaData.getDefaultInstance();
            }
            
            @Override
            public ColumnMetaData build() {
                final ColumnMetaData result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public ColumnMetaData buildPartial() {
                final ColumnMetaData result = new ColumnMetaData((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) != 0x0) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) != 0x0) {
                    to_bitField0_ |= 0x2;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x4) != 0x0) {
                    to_bitField0_ |= 0x4;
                }
                result.originalName_ = this.originalName_;
                if ((from_bitField0_ & 0x8) != 0x0) {
                    to_bitField0_ |= 0x8;
                }
                result.table_ = this.table_;
                if ((from_bitField0_ & 0x10) != 0x0) {
                    to_bitField0_ |= 0x10;
                }
                result.originalTable_ = this.originalTable_;
                if ((from_bitField0_ & 0x20) != 0x0) {
                    to_bitField0_ |= 0x20;
                }
                result.schema_ = this.schema_;
                if ((from_bitField0_ & 0x40) != 0x0) {
                    to_bitField0_ |= 0x40;
                }
                result.catalog_ = this.catalog_;
                if ((from_bitField0_ & 0x80) != 0x0) {
                    result.collation_ = this.collation_;
                    to_bitField0_ |= 0x80;
                }
                if ((from_bitField0_ & 0x100) != 0x0) {
                    result.fractionalDigits_ = this.fractionalDigits_;
                    to_bitField0_ |= 0x100;
                }
                if ((from_bitField0_ & 0x200) != 0x0) {
                    result.length_ = this.length_;
                    to_bitField0_ |= 0x200;
                }
                if ((from_bitField0_ & 0x400) != 0x0) {
                    result.flags_ = this.flags_;
                    to_bitField0_ |= 0x400;
                }
                if ((from_bitField0_ & 0x800) != 0x0) {
                    result.contentType_ = this.contentType_;
                    to_bitField0_ |= 0x800;
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
                if (other instanceof ColumnMetaData) {
                    return this.mergeFrom((ColumnMetaData)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ColumnMetaData other) {
                if (other == ColumnMetaData.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasOriginalName()) {
                    this.setOriginalName(other.getOriginalName());
                }
                if (other.hasTable()) {
                    this.setTable(other.getTable());
                }
                if (other.hasOriginalTable()) {
                    this.setOriginalTable(other.getOriginalTable());
                }
                if (other.hasSchema()) {
                    this.setSchema(other.getSchema());
                }
                if (other.hasCatalog()) {
                    this.setCatalog(other.getCatalog());
                }
                if (other.hasCollation()) {
                    this.setCollation(other.getCollation());
                }
                if (other.hasFractionalDigits()) {
                    this.setFractionalDigits(other.getFractionalDigits());
                }
                if (other.hasLength()) {
                    this.setLength(other.getLength());
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
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
                return this.hasType();
            }
            
            @Override
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ColumnMetaData parsedMessage = null;
                try {
                    parsedMessage = ColumnMetaData.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ColumnMetaData)e.getUnfinishedMessage();
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
            public FieldType getType() {
                final FieldType result = FieldType.valueOf(this.type_);
                return (result == null) ? FieldType.SINT : result;
            }
            
            public Builder setType(final FieldType value) {
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
            public boolean hasName() {
                return (this.bitField0_ & 0x2) != 0x0;
            }
            
            @Override
            public ByteString getName() {
                return this.name_;
            }
            
            public Builder setName(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearName() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.name_ = ColumnMetaData.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasOriginalName() {
                return (this.bitField0_ & 0x4) != 0x0;
            }
            
            @Override
            public ByteString getOriginalName() {
                return this.originalName_;
            }
            
            public Builder setOriginalName(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.originalName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearOriginalName() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.originalName_ = ColumnMetaData.getDefaultInstance().getOriginalName();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasTable() {
                return (this.bitField0_ & 0x8) != 0x0;
            }
            
            @Override
            public ByteString getTable() {
                return this.table_;
            }
            
            public Builder setTable(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.table_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearTable() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.table_ = ColumnMetaData.getDefaultInstance().getTable();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasOriginalTable() {
                return (this.bitField0_ & 0x10) != 0x0;
            }
            
            @Override
            public ByteString getOriginalTable() {
                return this.originalTable_;
            }
            
            public Builder setOriginalTable(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.originalTable_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearOriginalTable() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.originalTable_ = ColumnMetaData.getDefaultInstance().getOriginalTable();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasSchema() {
                return (this.bitField0_ & 0x20) != 0x0;
            }
            
            @Override
            public ByteString getSchema() {
                return this.schema_;
            }
            
            public Builder setSchema(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x20;
                this.schema_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSchema() {
                this.bitField0_ &= 0xFFFFFFDF;
                this.schema_ = ColumnMetaData.getDefaultInstance().getSchema();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasCatalog() {
                return (this.bitField0_ & 0x40) != 0x0;
            }
            
            @Override
            public ByteString getCatalog() {
                return this.catalog_;
            }
            
            public Builder setCatalog(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x40;
                this.catalog_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCatalog() {
                this.bitField0_ &= 0xFFFFFFBF;
                this.catalog_ = ColumnMetaData.getDefaultInstance().getCatalog();
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasCollation() {
                return (this.bitField0_ & 0x80) != 0x0;
            }
            
            @Override
            public long getCollation() {
                return this.collation_;
            }
            
            public Builder setCollation(final long value) {
                this.bitField0_ |= 0x80;
                this.collation_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCollation() {
                this.bitField0_ &= 0xFFFFFF7F;
                this.collation_ = 0L;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasFractionalDigits() {
                return (this.bitField0_ & 0x100) != 0x0;
            }
            
            @Override
            public int getFractionalDigits() {
                return this.fractionalDigits_;
            }
            
            public Builder setFractionalDigits(final int value) {
                this.bitField0_ |= 0x100;
                this.fractionalDigits_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFractionalDigits() {
                this.bitField0_ &= 0xFFFFFEFF;
                this.fractionalDigits_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasLength() {
                return (this.bitField0_ & 0x200) != 0x0;
            }
            
            @Override
            public int getLength() {
                return this.length_;
            }
            
            public Builder setLength(final int value) {
                this.bitField0_ |= 0x200;
                this.length_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearLength() {
                this.bitField0_ &= 0xFFFFFDFF;
                this.length_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasFlags() {
                return (this.bitField0_ & 0x400) != 0x0;
            }
            
            @Override
            public int getFlags() {
                return this.flags_;
            }
            
            public Builder setFlags(final int value) {
                this.bitField0_ |= 0x400;
                this.flags_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFlags() {
                this.bitField0_ &= 0xFFFFFBFF;
                this.flags_ = 0;
                this.onChanged();
                return this;
            }
            
            @Override
            public boolean hasContentType() {
                return (this.bitField0_ & 0x800) != 0x0;
            }
            
            @Override
            public int getContentType() {
                return this.contentType_;
            }
            
            public Builder setContentType(final int value) {
                this.bitField0_ |= 0x800;
                this.contentType_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearContentType() {
                this.bitField0_ &= 0xFFFFF7FF;
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
    
    public static final class Row extends GeneratedMessageV3 implements RowOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int FIELD_FIELD_NUMBER = 1;
        private List<ByteString> field_;
        private byte memoizedIsInitialized;
        private static final Row DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Row> PARSER;
        
        private Row(final GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Row() {
            this.memoizedIsInitialized = -1;
            this.field_ = Collections.emptyList();
        }
        
        @Override
        protected Object newInstance(final UnusedPrivateParameter unused) {
            return new Row();
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Row(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.field_ = new ArrayList<ByteString>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.field_.add(input.readBytes());
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
                    this.field_ = Collections.unmodifiableList((List<? extends ByteString>)this.field_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
        }
        
        @Override
        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_fieldAccessorTable.ensureFieldAccessorsInitialized(Row.class, Builder.class);
        }
        
        @Override
        public List<ByteString> getFieldList() {
            return this.field_;
        }
        
        @Override
        public int getFieldCount() {
            return this.field_.size();
        }
        
        @Override
        public ByteString getField(final int index) {
            return this.field_.get(index);
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
            for (int i = 0; i < this.field_.size(); ++i) {
                output.writeBytes(1, this.field_.get(i));
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
            int dataSize = 0;
            for (int i = 0; i < this.field_.size(); ++i) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag(this.field_.get(i));
            }
            size += dataSize;
            size += 1 * this.getFieldList().size();
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Row)) {
                return super.equals(obj);
            }
            final Row other = (Row)obj;
            return this.getFieldList().equals(other.getFieldList()) && this.unknownFields.equals(other.unknownFields);
        }
        
        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getFieldCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFieldList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Row parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Row.PARSER, input);
        }
        
        public static Row parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Row.PARSER, input, extensionRegistry);
        }
        
        public static Row parseDelimitedFrom(final InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Row.PARSER, input);
        }
        
        public static Row parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(Row.PARSER, input, extensionRegistry);
        }
        
        public static Row parseFrom(final CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Row.PARSER, input);
        }
        
        public static Row parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(Row.PARSER, input, extensionRegistry);
        }
        
        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Row.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Row prototype) {
            return Row.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        @Override
        public Builder toBuilder() {
            return (this == Row.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        @Override
        protected Builder newBuilderForType(final BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Row getDefaultInstance() {
            return Row.DEFAULT_INSTANCE;
        }
        
        public static Parser<Row> parser() {
            return Row.PARSER;
        }
        
        @Override
        public Parser<Row> getParserForType() {
            return Row.PARSER;
        }
        
        @Override
        public Row getDefaultInstanceForType() {
            return Row.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Row();
            PARSER = new AbstractParser<Row>() {
                @Override
                public Row parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Row(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements RowOrBuilder
        {
            private int bitField0_;
            private List<ByteString> field_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
            }
            
            @Override
            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_fieldAccessorTable.ensureFieldAccessorsInitialized(Row.class, Builder.class);
            }
            
            private Builder() {
                this.field_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.field_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Row.alwaysUseFieldBuilders) {}
            }
            
            @Override
            public Builder clear() {
                super.clear();
                this.field_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
            }
            
            @Override
            public Row getDefaultInstanceForType() {
                return Row.getDefaultInstance();
            }
            
            @Override
            public Row build() {
                final Row result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw AbstractMessage.Builder.newUninitializedMessageException(result);
                }
                return result;
            }
            
            @Override
            public Row buildPartial() {
                final Row result = new Row((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 0x1) != 0x0) {
                    this.field_ = Collections.unmodifiableList((List<? extends ByteString>)this.field_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.field_ = this.field_;
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
                if (other instanceof Row) {
                    return this.mergeFrom((Row)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Row other) {
                if (other == Row.getDefaultInstance()) {
                    return this;
                }
                if (!other.field_.isEmpty()) {
                    if (this.field_.isEmpty()) {
                        this.field_ = other.field_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureFieldIsMutable();
                        this.field_.addAll(other.field_);
                    }
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
                Row parsedMessage = null;
                try {
                    parsedMessage = Row.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Row)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureFieldIsMutable() {
                if ((this.bitField0_ & 0x1) == 0x0) {
                    this.field_ = new ArrayList<ByteString>(this.field_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            @Override
            public List<ByteString> getFieldList() {
                return ((this.bitField0_ & 0x1) != 0x0) ? Collections.unmodifiableList((List<? extends ByteString>)this.field_) : this.field_;
            }
            
            @Override
            public int getFieldCount() {
                return this.field_.size();
            }
            
            @Override
            public ByteString getField(final int index) {
                return this.field_.get(index);
            }
            
            public Builder setField(final int index, final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldIsMutable();
                this.field_.set(index, value);
                this.onChanged();
                return this;
            }
            
            public Builder addField(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldIsMutable();
                this.field_.add(value);
                this.onChanged();
                return this;
            }
            
            public Builder addAllField(final Iterable<? extends ByteString> values) {
                this.ensureFieldIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.field_);
                this.onChanged();
                return this;
            }
            
            public Builder clearField() {
                this.field_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
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
    
    public interface RowOrBuilder extends MessageOrBuilder
    {
        List<ByteString> getFieldList();
        
        int getFieldCount();
        
        ByteString getField(final int p0);
    }
    
    public interface ColumnMetaDataOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        ColumnMetaData.FieldType getType();
        
        boolean hasName();
        
        ByteString getName();
        
        boolean hasOriginalName();
        
        ByteString getOriginalName();
        
        boolean hasTable();
        
        ByteString getTable();
        
        boolean hasOriginalTable();
        
        ByteString getOriginalTable();
        
        boolean hasSchema();
        
        ByteString getSchema();
        
        boolean hasCatalog();
        
        ByteString getCatalog();
        
        boolean hasCollation();
        
        long getCollation();
        
        boolean hasFractionalDigits();
        
        int getFractionalDigits();
        
        boolean hasLength();
        
        int getLength();
        
        boolean hasFlags();
        
        int getFlags();
        
        boolean hasContentType();
        
        int getContentType();
    }
    
    public interface FetchSuspendedOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface FetchDoneOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface FetchDoneMoreResultsetsOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface FetchDoneMoreOutParamsOrBuilder extends MessageOrBuilder
    {
    }
}
