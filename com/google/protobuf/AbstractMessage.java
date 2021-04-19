package com.google.protobuf;

import java.util.*;
import java.io.*;

public abstract class AbstractMessage extends AbstractMessageLite implements Message
{
    protected int memoizedSize;
    
    public AbstractMessage() {
        this.memoizedSize = -1;
    }
    
    @Override
    public boolean isInitialized() {
        return MessageReflection.isInitialized(this);
    }
    
    protected Message.Builder newBuilderForType(final BuilderParent parent) {
        throw new UnsupportedOperationException("Nested builder is not supported for this type.");
    }
    
    @Override
    public List<String> findInitializationErrors() {
        return MessageReflection.findMissingFields(this);
    }
    
    @Override
    public String getInitializationErrorString() {
        return MessageReflection.delimitWithCommas(this.findInitializationErrors());
    }
    
    @Override
    public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
        throw new UnsupportedOperationException("hasOneof() is not implemented.");
    }
    
    @Override
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
        throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
    }
    
    @Override
    public final String toString() {
        return TextFormat.printer().printToString(this);
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        MessageReflection.writeMessageTo(this, this.getAllFields(), output, false);
    }
    
    @Override
    int getMemoizedSerializedSize() {
        return this.memoizedSize;
    }
    
    @Override
    void setMemoizedSerializedSize(final int size) {
        this.memoizedSize = size;
    }
    
    @Override
    public int getSerializedSize() {
        final int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        return this.memoizedSize = MessageReflection.getSerializedSize(this, this.getAllFields());
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Message)) {
            return false;
        }
        final Message otherMessage = (Message)other;
        return this.getDescriptorForType() == otherMessage.getDescriptorForType() && compareFields(this.getAllFields(), otherMessage.getAllFields()) && this.getUnknownFields().equals(otherMessage.getUnknownFields());
    }
    
    @Override
    public int hashCode() {
        int hash = this.memoizedHashCode;
        if (hash == 0) {
            hash = 41;
            hash = 19 * hash + this.getDescriptorForType().hashCode();
            hash = hashFields(hash, this.getAllFields());
            hash = 29 * hash + this.getUnknownFields().hashCode();
            this.memoizedHashCode = hash;
        }
        return hash;
    }
    
    private static ByteString toByteString(final Object value) {
        if (value instanceof byte[]) {
            return ByteString.copyFrom((byte[])value);
        }
        return (ByteString)value;
    }
    
    private static boolean compareBytes(final Object a, final Object b) {
        if (a instanceof byte[] && b instanceof byte[]) {
            return Arrays.equals((byte[])a, (byte[])b);
        }
        return toByteString(a).equals(toByteString(b));
    }
    
    private static Map convertMapEntryListToMap(final List list) {
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        final Map result = new HashMap();
        final Iterator iterator = list.iterator();
        Message entry = iterator.next();
        final Descriptors.Descriptor descriptor = entry.getDescriptorForType();
        final Descriptors.FieldDescriptor key = descriptor.findFieldByName("key");
        final Descriptors.FieldDescriptor value = descriptor.findFieldByName("value");
        Object fieldValue = entry.getField(value);
        if (fieldValue instanceof Descriptors.EnumValueDescriptor) {
            fieldValue = ((Descriptors.EnumValueDescriptor)fieldValue).getNumber();
        }
        result.put(entry.getField(key), fieldValue);
        while (iterator.hasNext()) {
            entry = iterator.next();
            fieldValue = entry.getField(value);
            if (fieldValue instanceof Descriptors.EnumValueDescriptor) {
                fieldValue = ((Descriptors.EnumValueDescriptor)fieldValue).getNumber();
            }
            result.put(entry.getField(key), fieldValue);
        }
        return result;
    }
    
    private static boolean compareMapField(final Object a, final Object b) {
        final Map ma = convertMapEntryListToMap((List)a);
        final Map mb = convertMapEntryListToMap((List)b);
        return MapFieldLite.equals(ma, (Map<Object, Object>)mb);
    }
    
    static boolean compareFields(final Map<Descriptors.FieldDescriptor, Object> a, final Map<Descriptors.FieldDescriptor, Object> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (final Descriptors.FieldDescriptor descriptor : a.keySet()) {
            if (!b.containsKey(descriptor)) {
                return false;
            }
            final Object value1 = a.get(descriptor);
            final Object value2 = b.get(descriptor);
            if (descriptor.getType() == Descriptors.FieldDescriptor.Type.BYTES) {
                if (descriptor.isRepeated()) {
                    final List list1 = (List)value1;
                    final List list2 = (List)value2;
                    if (list1.size() != list2.size()) {
                        return false;
                    }
                    for (int i = 0; i < list1.size(); ++i) {
                        if (!compareBytes(list1.get(i), list2.get(i))) {
                            return false;
                        }
                    }
                }
                else {
                    if (!compareBytes(value1, value2)) {
                        return false;
                    }
                    continue;
                }
            }
            else if (descriptor.isMapField()) {
                if (!compareMapField(value1, value2)) {
                    return false;
                }
                continue;
            }
            else {
                if (!value1.equals(value2)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    private static int hashMapField(final Object value) {
        return MapFieldLite.calculateHashCodeForMap((Map<Object, Object>)convertMapEntryListToMap((List)value));
    }
    
    protected static int hashFields(int hash, final Map<Descriptors.FieldDescriptor, Object> map) {
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : map.entrySet()) {
            final Descriptors.FieldDescriptor field = entry.getKey();
            final Object value = entry.getValue();
            hash = 37 * hash + field.getNumber();
            if (field.isMapField()) {
                hash = 53 * hash + hashMapField(value);
            }
            else if (field.getType() != Descriptors.FieldDescriptor.Type.ENUM) {
                hash = 53 * hash + value.hashCode();
            }
            else if (field.isRepeated()) {
                final List<? extends Internal.EnumLite> list = (List<? extends Internal.EnumLite>)value;
                hash = 53 * hash + Internal.hashEnumList(list);
            }
            else {
                hash = 53 * hash + Internal.hashEnum((Internal.EnumLite)value);
            }
        }
        return hash;
    }
    
    @Override
    UninitializedMessageException newUninitializedMessageException() {
        return Builder.newUninitializedMessageException(this);
    }
    
    @Deprecated
    protected static int hashLong(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    @Deprecated
    protected static int hashBoolean(final boolean b) {
        return b ? 1231 : 1237;
    }
    
    @Deprecated
    protected static int hashEnum(final Internal.EnumLite e) {
        return e.getNumber();
    }
    
    @Deprecated
    protected static int hashEnumList(final List<? extends Internal.EnumLite> list) {
        int hash = 1;
        for (final Internal.EnumLite e : list) {
            hash = 31 * hash + hashEnum(e);
        }
        return hash;
    }
    
    public abstract static class Builder<BuilderType extends Builder<BuilderType>> extends AbstractMessageLite.Builder implements Message.Builder
    {
        @Override
        public BuilderType clone() {
            throw new UnsupportedOperationException("clone() should be implemented in subclasses.");
        }
        
        @Override
        public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
            throw new UnsupportedOperationException("hasOneof() is not implemented.");
        }
        
        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
            throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
        }
        
        @Override
        public BuilderType clearOneof(final Descriptors.OneofDescriptor oneof) {
            throw new UnsupportedOperationException("clearOneof() is not implemented.");
        }
        
        @Override
        public BuilderType clear() {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : this.getAllFields().entrySet()) {
                this.clearField(entry.getKey());
            }
            return (BuilderType)this;
        }
        
        @Override
        public List<String> findInitializationErrors() {
            return MessageReflection.findMissingFields(this);
        }
        
        @Override
        public String getInitializationErrorString() {
            return MessageReflection.delimitWithCommas(this.findInitializationErrors());
        }
        
        @Override
        protected BuilderType internalMergeFrom(final AbstractMessageLite other) {
            return this.mergeFrom((Message)other);
        }
        
        @Override
        public BuilderType mergeFrom(final Message other) {
            return this.mergeFrom(other, other.getAllFields());
        }
        
        BuilderType mergeFrom(final Message other, final Map<Descriptors.FieldDescriptor, Object> allFields) {
            if (other.getDescriptorForType() != this.getDescriptorForType()) {
                throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
            }
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                final Descriptors.FieldDescriptor field = entry.getKey();
                if (field.isRepeated()) {
                    for (final Object element : entry.getValue()) {
                        this.addRepeatedField(field, element);
                    }
                }
                else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    final Message existingValue = (Message)this.getField(field);
                    if (existingValue == existingValue.getDefaultInstanceForType()) {
                        this.setField(field, entry.getValue());
                    }
                    else {
                        this.setField(field, existingValue.newBuilderForType().mergeFrom(existingValue).mergeFrom(entry.getValue()).build());
                    }
                }
                else {
                    this.setField(field, entry.getValue());
                }
            }
            this.mergeUnknownFields(other.getUnknownFields());
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType mergeFrom(final CodedInputStream input) throws IOException {
            return this.mergeFrom(input, ExtensionRegistry.getEmptyRegistry());
        }
        
        @Override
        public BuilderType mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final boolean discardUnknown = input.shouldDiscardUnknownFields();
            final UnknownFieldSet.Builder unknownFields = discardUnknown ? null : UnknownFieldSet.newBuilder(this.getUnknownFields());
            int tag;
            MessageReflection.BuilderAdapter builderAdapter;
            do {
                tag = input.readTag();
                if (tag == 0) {
                    break;
                }
                builderAdapter = new MessageReflection.BuilderAdapter(this);
            } while (MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, this.getDescriptorForType(), builderAdapter, tag));
            if (unknownFields != null) {
                this.setUnknownFields(unknownFields.build());
            }
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType mergeUnknownFields(final UnknownFieldSet unknownFields) {
            this.setUnknownFields(UnknownFieldSet.newBuilder(this.getUnknownFields()).mergeFrom(unknownFields).build());
            return (BuilderType)this;
        }
        
        @Override
        public Message.Builder getFieldBuilder(final Descriptors.FieldDescriptor field) {
            throw new UnsupportedOperationException("getFieldBuilder() called on an unsupported message type.");
        }
        
        @Override
        public Message.Builder getRepeatedFieldBuilder(final Descriptors.FieldDescriptor field, final int index) {
            throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on an unsupported message type.");
        }
        
        @Override
        public String toString() {
            return TextFormat.printer().printToString(this);
        }
        
        protected static UninitializedMessageException newUninitializedMessageException(final Message message) {
            return new UninitializedMessageException(MessageReflection.findMissingFields(message));
        }
        
        void markClean() {
            throw new IllegalStateException("Should be overridden by subclasses.");
        }
        
        void dispose() {
            throw new IllegalStateException("Should be overridden by subclasses.");
        }
        
        @Override
        public BuilderType mergeFrom(final ByteString data) throws InvalidProtocolBufferException {
            return super.mergeFrom(data);
        }
        
        @Override
        public BuilderType mergeFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return super.mergeFrom(data, extensionRegistry);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data) throws InvalidProtocolBufferException {
            return super.mergeFrom(data);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final int off, final int len) throws InvalidProtocolBufferException {
            return super.mergeFrom(data, off, len);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return super.mergeFrom(data, extensionRegistry);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final int off, final int len, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return super.mergeFrom(data, off, len, extensionRegistry);
        }
        
        @Override
        public BuilderType mergeFrom(final InputStream input) throws IOException {
            return super.mergeFrom(input);
        }
        
        @Override
        public BuilderType mergeFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return super.mergeFrom(input, extensionRegistry);
        }
        
        @Override
        public boolean mergeDelimitedFrom(final InputStream input) throws IOException {
            return super.mergeDelimitedFrom(input);
        }
        
        @Override
        public boolean mergeDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return super.mergeDelimitedFrom(input, extensionRegistry);
        }
    }
    
    protected interface BuilderParent
    {
        void markDirty();
    }
}
