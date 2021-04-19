package com.google.protobuf;

import java.io.*;
import java.util.*;

public final class DynamicMessage extends AbstractMessage
{
    private final Descriptors.Descriptor type;
    private final FieldSet<Descriptors.FieldDescriptor> fields;
    private final Descriptors.FieldDescriptor[] oneofCases;
    private final UnknownFieldSet unknownFields;
    private int memoizedSize;
    
    DynamicMessage(final Descriptors.Descriptor type, final FieldSet<Descriptors.FieldDescriptor> fields, final Descriptors.FieldDescriptor[] oneofCases, final UnknownFieldSet unknownFields) {
        this.memoizedSize = -1;
        this.type = type;
        this.fields = fields;
        this.oneofCases = oneofCases;
        this.unknownFields = unknownFields;
    }
    
    public static DynamicMessage getDefaultInstance(final Descriptors.Descriptor type) {
        final int oneofDeclCount = type.toProto().getOneofDeclCount();
        final Descriptors.FieldDescriptor[] oneofCases = new Descriptors.FieldDescriptor[oneofDeclCount];
        return new DynamicMessage(type, FieldSet.emptySet(), oneofCases, UnknownFieldSet.getDefaultInstance());
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final CodedInputStream input) throws IOException {
        return newBuilder(type).mergeFrom(input).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final CodedInputStream input, final ExtensionRegistry extensionRegistry) throws IOException {
        return newBuilder(type).mergeFrom(input, extensionRegistry).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final ByteString data) throws InvalidProtocolBufferException {
        return newBuilder(type).mergeFrom(data).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final ByteString data, final ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return newBuilder(type).mergeFrom(data, extensionRegistry).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final byte[] data) throws InvalidProtocolBufferException {
        return newBuilder(type).mergeFrom(data).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final byte[] data, final ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return newBuilder(type).mergeFrom(data, extensionRegistry).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final InputStream input) throws IOException {
        return newBuilder(type).mergeFrom(input).buildParsed();
    }
    
    public static DynamicMessage parseFrom(final Descriptors.Descriptor type, final InputStream input, final ExtensionRegistry extensionRegistry) throws IOException {
        return newBuilder(type).mergeFrom(input, extensionRegistry).buildParsed();
    }
    
    public static Builder newBuilder(final Descriptors.Descriptor type) {
        return new Builder(type);
    }
    
    public static Builder newBuilder(final Message prototype) {
        return new Builder(prototype.getDescriptorForType()).mergeFrom(prototype);
    }
    
    @Override
    public Descriptors.Descriptor getDescriptorForType() {
        return this.type;
    }
    
    @Override
    public DynamicMessage getDefaultInstanceForType() {
        return getDefaultInstance(this.type);
    }
    
    @Override
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return this.fields.getAllFields();
    }
    
    @Override
    public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
        this.verifyOneofContainingType(oneof);
        final Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
        return field != null;
    }
    
    @Override
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
        this.verifyOneofContainingType(oneof);
        return this.oneofCases[oneof.getIndex()];
    }
    
    @Override
    public boolean hasField(final Descriptors.FieldDescriptor field) {
        this.verifyContainingType(field);
        return this.fields.hasField(field);
    }
    
    @Override
    public Object getField(final Descriptors.FieldDescriptor field) {
        this.verifyContainingType(field);
        Object result = this.fields.getField(field);
        if (result == null) {
            if (field.isRepeated()) {
                result = Collections.emptyList();
            }
            else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                result = getDefaultInstance(field.getMessageType());
            }
            else {
                result = field.getDefaultValue();
            }
        }
        return result;
    }
    
    @Override
    public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
        this.verifyContainingType(field);
        return this.fields.getRepeatedFieldCount(field);
    }
    
    @Override
    public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
        this.verifyContainingType(field);
        return this.fields.getRepeatedField(field, index);
    }
    
    @Override
    public UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    static boolean isInitialized(final Descriptors.Descriptor type, final FieldSet<Descriptors.FieldDescriptor> fields) {
        for (final Descriptors.FieldDescriptor field : type.getFields()) {
            if (field.isRequired() && !fields.hasField(field)) {
                return false;
            }
        }
        return fields.isInitialized();
    }
    
    @Override
    public boolean isInitialized() {
        return isInitialized(this.type, this.fields);
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        if (this.type.getOptions().getMessageSetWireFormat()) {
            this.fields.writeMessageSetTo(output);
            this.unknownFields.writeAsMessageSetTo(output);
        }
        else {
            this.fields.writeTo(output);
            this.unknownFields.writeTo(output);
        }
    }
    
    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        if (this.type.getOptions().getMessageSetWireFormat()) {
            size = this.fields.getMessageSetSerializedSize();
            size += this.unknownFields.getSerializedSizeAsMessageSet();
        }
        else {
            size = this.fields.getSerializedSize();
            size += this.unknownFields.getSerializedSize();
        }
        return this.memoizedSize = size;
    }
    
    @Override
    public Builder newBuilderForType() {
        return new Builder(this.type);
    }
    
    @Override
    public Builder toBuilder() {
        return this.newBuilderForType().mergeFrom(this);
    }
    
    @Override
    public Parser<DynamicMessage> getParserForType() {
        return new AbstractParser<DynamicMessage>() {
            @Override
            public DynamicMessage parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                final Builder builder = DynamicMessage.newBuilder(DynamicMessage.this.type);
                try {
                    builder.mergeFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                }
                catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(builder.buildPartial());
                }
                return builder.buildPartial();
            }
        };
    }
    
    private void verifyContainingType(final Descriptors.FieldDescriptor field) {
        if (field.getContainingType() != this.type) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
        }
    }
    
    private void verifyOneofContainingType(final Descriptors.OneofDescriptor oneof) {
        if (oneof.getContainingType() != this.type) {
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }
    }
    
    public static final class Builder extends AbstractMessage.Builder<Builder>
    {
        private final Descriptors.Descriptor type;
        private FieldSet<Descriptors.FieldDescriptor> fields;
        private final Descriptors.FieldDescriptor[] oneofCases;
        private UnknownFieldSet unknownFields;
        
        private Builder(final Descriptors.Descriptor type) {
            this.type = type;
            this.fields = FieldSet.newFieldSet();
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.oneofCases = new Descriptors.FieldDescriptor[type.toProto().getOneofDeclCount()];
            if (type.getOptions().getMapEntry()) {
                this.populateMapEntry();
            }
        }
        
        private void populateMapEntry() {
            for (final Descriptors.FieldDescriptor field : this.type.getFields()) {
                if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    this.fields.setField(field, DynamicMessage.getDefaultInstance(field.getMessageType()));
                }
                else {
                    this.fields.setField(field, field.getDefaultValue());
                }
            }
        }
        
        @Override
        public Builder clear() {
            if (this.fields.isImmutable()) {
                this.fields = FieldSet.newFieldSet();
            }
            else {
                this.fields.clear();
            }
            if (this.type.getOptions().getMapEntry()) {
                this.populateMapEntry();
            }
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            return this;
        }
        
        @Override
        public Builder mergeFrom(final Message other) {
            if (!(other instanceof DynamicMessage)) {
                return super.mergeFrom(other);
            }
            final DynamicMessage otherDynamicMessage = (DynamicMessage)other;
            if (otherDynamicMessage.type != this.type) {
                throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
            }
            this.ensureIsMutable();
            this.fields.mergeFrom(otherDynamicMessage.fields);
            this.mergeUnknownFields(otherDynamicMessage.unknownFields);
            for (int i = 0; i < this.oneofCases.length; ++i) {
                if (this.oneofCases[i] == null) {
                    this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                }
                else if (otherDynamicMessage.oneofCases[i] != null && this.oneofCases[i] != otherDynamicMessage.oneofCases[i]) {
                    this.fields.clearField(this.oneofCases[i]);
                    this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                }
            }
            return this;
        }
        
        @Override
        public DynamicMessage build() {
            if (!this.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields, Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields));
            }
            return this.buildPartial();
        }
        
        private DynamicMessage buildParsed() throws InvalidProtocolBufferException {
            if (!this.isInitialized()) {
                throw AbstractMessage.Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields, Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields)).asInvalidProtocolBufferException();
            }
            return this.buildPartial();
        }
        
        @Override
        public DynamicMessage buildPartial() {
            this.fields.makeImmutable();
            final DynamicMessage result = new DynamicMessage(this.type, this.fields, Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields);
            return result;
        }
        
        @Override
        public Builder clone() {
            final Builder result = new Builder(this.type);
            result.fields.mergeFrom(this.fields);
            result.mergeUnknownFields(this.unknownFields);
            System.arraycopy(this.oneofCases, 0, result.oneofCases, 0, this.oneofCases.length);
            return result;
        }
        
        @Override
        public boolean isInitialized() {
            return DynamicMessage.isInitialized(this.type, this.fields);
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.type;
        }
        
        @Override
        public DynamicMessage getDefaultInstanceForType() {
            return DynamicMessage.getDefaultInstance(this.type);
        }
        
        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return this.fields.getAllFields();
        }
        
        @Override
        public Builder newBuilderForField(final Descriptors.FieldDescriptor field) {
            this.verifyContainingType(field);
            if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new IllegalArgumentException("newBuilderForField is only valid for fields with message type.");
            }
            return new Builder(field.getMessageType());
        }
        
        @Override
        public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            final Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
            return field != null;
        }
        
        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            return this.oneofCases[oneof.getIndex()];
        }
        
        @Override
        public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
            this.verifyOneofContainingType(oneof);
            final Descriptors.FieldDescriptor field = this.oneofCases[oneof.getIndex()];
            if (field != null) {
                this.clearField(field);
            }
            return this;
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            this.verifyContainingType(field);
            return this.fields.hasField(field);
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            this.verifyContainingType(field);
            Object result = this.fields.getField(field);
            if (result == null) {
                if (field.isRepeated()) {
                    result = Collections.emptyList();
                }
                else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    result = DynamicMessage.getDefaultInstance(field.getMessageType());
                }
                else {
                    result = field.getDefaultValue();
                }
            }
            return result;
        }
        
        @Override
        public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
            this.verifyContainingType(field);
            this.ensureIsMutable();
            if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
                this.ensureEnumValueDescriptor(field, value);
            }
            final Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                final int index = oneofDescriptor.getIndex();
                final Descriptors.FieldDescriptor oldField = this.oneofCases[index];
                if (oldField != null && oldField != field) {
                    this.fields.clearField(oldField);
                }
                this.oneofCases[index] = field;
            }
            else if (field.getFile().getSyntax() == Descriptors.FileDescriptor.Syntax.PROTO3 && !field.isRepeated() && field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE && value.equals(field.getDefaultValue())) {
                this.fields.clearField(field);
                return this;
            }
            this.fields.setField(field, value);
            return this;
        }
        
        @Override
        public Builder clearField(final Descriptors.FieldDescriptor field) {
            this.verifyContainingType(field);
            this.ensureIsMutable();
            final Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                final int index = oneofDescriptor.getIndex();
                if (this.oneofCases[index] == field) {
                    this.oneofCases[index] = null;
                }
            }
            this.fields.clearField(field);
            return this;
        }
        
        @Override
        public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
            this.verifyContainingType(field);
            return this.fields.getRepeatedFieldCount(field);
        }
        
        @Override
        public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
            this.verifyContainingType(field);
            return this.fields.getRepeatedField(field, index);
        }
        
        @Override
        public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            this.verifyContainingType(field);
            this.ensureIsMutable();
            this.fields.setRepeatedField(field, index, value);
            return this;
        }
        
        @Override
        public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            this.verifyContainingType(field);
            this.ensureIsMutable();
            this.fields.addRepeatedField(field, value);
            return this;
        }
        
        @Override
        public UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        @Override
        public Builder setUnknownFields(final UnknownFieldSet unknownFields) {
            this.unknownFields = unknownFields;
            return this;
        }
        
        @Override
        public Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build();
            return this;
        }
        
        private void verifyContainingType(final Descriptors.FieldDescriptor field) {
            if (field.getContainingType() != this.type) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
        
        private void verifyOneofContainingType(final Descriptors.OneofDescriptor oneof) {
            if (oneof.getContainingType() != this.type) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
        }
        
        private void ensureSingularEnumValueDescriptor(final Descriptors.FieldDescriptor field, final Object value) {
            Internal.checkNotNull(value);
            if (!(value instanceof Descriptors.EnumValueDescriptor)) {
                throw new IllegalArgumentException("DynamicMessage should use EnumValueDescriptor to set Enum Value.");
            }
        }
        
        private void ensureEnumValueDescriptor(final Descriptors.FieldDescriptor field, final Object value) {
            if (field.isRepeated()) {
                for (final Object item : (List)value) {
                    this.ensureSingularEnumValueDescriptor(field, item);
                }
            }
            else {
                this.ensureSingularEnumValueDescriptor(field, value);
            }
        }
        
        private void ensureIsMutable() {
            if (this.fields.isImmutable()) {
                this.fields = this.fields.clone();
            }
        }
        
        @Override
        public Message.Builder getFieldBuilder(final Descriptors.FieldDescriptor field) {
            throw new UnsupportedOperationException("getFieldBuilder() called on a dynamic message type.");
        }
        
        @Override
        public Message.Builder getRepeatedFieldBuilder(final Descriptors.FieldDescriptor field, final int index) {
            throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a dynamic message type.");
        }
    }
}
