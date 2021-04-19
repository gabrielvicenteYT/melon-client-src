package com.google.protobuf;

import java.nio.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.reflect.*;
import java.io.*;

public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType>
{
    protected UnknownFieldSetLite unknownFields;
    protected int memoizedSerializedSize;
    private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap;
    
    public GeneratedMessageLite() {
        this.unknownFields = UnknownFieldSetLite.getDefaultInstance();
        this.memoizedSerializedSize = -1;
    }
    
    @Override
    public final Parser<MessageType> getParserForType() {
        return (Parser<MessageType>)this.dynamicMethod(MethodToInvoke.GET_PARSER);
    }
    
    @Override
    public final MessageType getDefaultInstanceForType() {
        return (MessageType)this.dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }
    
    @Override
    public final BuilderType newBuilderForType() {
        return (BuilderType)this.dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }
    
    @Override
    public String toString() {
        return MessageLiteToString.toString(this, super.toString());
    }
    
    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        return this.memoizedHashCode = Protobuf.getInstance().schemaFor(this).hashCode(this);
    }
    
    @Override
    public boolean equals(final Object other) {
        return this == other || (this.getDefaultInstanceForType().getClass().isInstance(other) && Protobuf.getInstance().schemaFor(this).equals(this, (GeneratedMessageLite)other));
    }
    
    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.newInstance();
        }
    }
    
    protected boolean parseUnknownField(final int tag, final CodedInputStream input) throws IOException {
        if (WireFormat.getTagWireType(tag) == 4) {
            return false;
        }
        this.ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(tag, input);
    }
    
    protected void mergeVarintField(final int tag, final int value) {
        this.ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(tag, value);
    }
    
    protected void mergeLengthDelimitedField(final int fieldNumber, final ByteString value) {
        this.ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(fieldNumber, value);
    }
    
    protected void makeImmutable() {
        Protobuf.getInstance().schemaFor(this).makeImmutable(this);
    }
    
    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (BuilderType)this.dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }
    
    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder(final MessageType prototype) {
        return this.createBuilder().mergeFrom(prototype);
    }
    
    @Override
    public final boolean isInitialized() {
        return isInitialized(this, Boolean.TRUE);
    }
    
    @Override
    public final BuilderType toBuilder() {
        final BuilderType builder = (BuilderType)this.dynamicMethod(MethodToInvoke.NEW_BUILDER);
        ((Builder<MessageType, Builder>)builder).mergeFrom((MessageType)this);
        return builder;
    }
    
    protected abstract Object dynamicMethod(final MethodToInvoke p0, final Object p1, final Object p2);
    
    protected Object dynamicMethod(final MethodToInvoke method, final Object arg0) {
        return this.dynamicMethod(method, arg0, null);
    }
    
    protected Object dynamicMethod(final MethodToInvoke method) {
        return this.dynamicMethod(method, null, null);
    }
    
    @Override
    int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize;
    }
    
    @Override
    void setMemoizedSerializedSize(final int size) {
        this.memoizedSerializedSize = size;
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        Protobuf.getInstance().schemaFor(this).writeTo(this, CodedOutputStreamWriter.forCodedOutput(output));
    }
    
    @Override
    public int getSerializedSize() {
        if (this.memoizedSerializedSize == -1) {
            this.memoizedSerializedSize = Protobuf.getInstance().schemaFor(this).getSerializedSize(this);
        }
        return this.memoizedSerializedSize;
    }
    
    Object buildMessageInfo() throws Exception {
        return this.dynamicMethod(MethodToInvoke.BUILD_MESSAGE_INFO);
    }
    
    static <T extends GeneratedMessageLite<?, ?>> T getDefaultInstance(final Class<T> clazz) {
        T result = (T)GeneratedMessageLite.defaultInstanceMap.get(clazz);
        if (result == null) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            }
            catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
            result = (T)GeneratedMessageLite.defaultInstanceMap.get(clazz);
        }
        if (result == null) {
            result = (T)UnsafeUtil.allocateInstance(clazz).getDefaultInstanceForType();
            if (result == null) {
                throw new IllegalStateException();
            }
            GeneratedMessageLite.defaultInstanceMap.put(clazz, result);
        }
        return result;
    }
    
    protected static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(final Class<T> clazz, final T defaultInstance) {
        GeneratedMessageLite.defaultInstanceMap.put(clazz, defaultInstance);
    }
    
    protected static Object newMessageInfo(final MessageLite defaultInstance, final String info, final Object[] objects) {
        return new RawMessageInfo(defaultInstance, info, objects);
    }
    
    protected final void mergeUnknownFields(final UnknownFieldSetLite unknownFields) {
        this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFields);
    }
    
    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(final ContainingType containingTypeDefaultInstance, final Type defaultValue, final MessageLite messageDefaultInstance, final Internal.EnumLiteMap<?> enumTypeMap, final int number, final WireFormat.FieldType type, final Class singularType) {
        return new GeneratedExtension<ContainingType, Type>(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, false, false), singularType);
    }
    
    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(final ContainingType containingTypeDefaultInstance, final MessageLite messageDefaultInstance, final Internal.EnumLiteMap<?> enumTypeMap, final int number, final WireFormat.FieldType type, final boolean isPacked, final Class singularType) {
        final Type emptyList = (Type)Collections.emptyList();
        return new GeneratedExtension<ContainingType, Type>(containingTypeDefaultInstance, emptyList, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, true, isPacked), singularType);
    }
    
    static Method getMethodOrDie(final Class clazz, final String name, final Class... params) {
        try {
            return clazz.getMethod(name, (Class[])params);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }
    
    static Object invokeOrDie(final Method method, final Object object, final Object... params) {
        try {
            return method.invoke(object, params);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        }
        catch (InvocationTargetException e2) {
            final Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            if (cause instanceof Error) {
                throw (Error)cause;
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }
    
    private static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(final ExtensionLite<MessageType, T> extension) {
        if (!extension.isLite()) {
            throw new IllegalArgumentException("Expected a lite extension.");
        }
        return (GeneratedExtension<MessageType, T>)(GeneratedExtension)extension;
    }
    
    protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(final T message, final boolean shouldMemoize) {
        final byte memoizedIsInitialized = (byte)message.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED);
        if (memoizedIsInitialized == 1) {
            return true;
        }
        if (memoizedIsInitialized == 0) {
            return false;
        }
        final boolean isInitialized = Protobuf.getInstance().schemaFor(message).isInitialized(message);
        if (shouldMemoize) {
            message.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, isInitialized ? message : null);
        }
        return isInitialized;
    }
    
    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }
    
    protected static Internal.IntList mutableCopy(final Internal.IntList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }
    
    protected static Internal.LongList mutableCopy(final Internal.LongList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }
    
    protected static Internal.FloatList mutableCopy(final Internal.FloatList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }
    
    protected static Internal.DoubleList mutableCopy(final Internal.DoubleList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }
    
    protected static Internal.BooleanList mutableCopy(final Internal.BooleanList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
        return (Internal.ProtobufList<E>)ProtobufArrayList.emptyList();
    }
    
    protected static <E> Internal.ProtobufList<E> mutableCopy(final Internal.ProtobufList<E> list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(final T instance, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        final T result = (T)instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            final Schema<T> schema = Protobuf.getInstance().schemaFor(result);
            schema.mergeFrom(result, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
            schema.makeImmutable(result);
        }
        catch (IOException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw (InvalidProtocolBufferException)e.getCause();
            }
            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(result);
        }
        catch (RuntimeException e2) {
            if (e2.getCause() instanceof InvalidProtocolBufferException) {
                throw (InvalidProtocolBufferException)e2.getCause();
            }
            throw e2;
        }
        return result;
    }
    
    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(final T instance, final byte[] input, final int offset, final int length, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        final T result = (T)instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            final Schema<T> schema = Protobuf.getInstance().schemaFor(result);
            schema.mergeFrom(result, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
            schema.makeImmutable(result);
            if (result.memoizedHashCode != 0) {
                throw new RuntimeException();
            }
        }
        catch (IOException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw (InvalidProtocolBufferException)e.getCause();
            }
            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(result);
        }
        catch (IndexOutOfBoundsException e2) {
            throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(result);
        }
        return result;
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(final T defaultInstance, final CodedInputStream input) throws InvalidProtocolBufferException {
        return parsePartialFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
    }
    
    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(final T message) throws InvalidProtocolBufferException {
        if (message != null && !message.isInitialized()) {
            throw message.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(message);
        }
        return message;
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parseFrom((T)defaultInstance, CodedInputStream.newInstance(data), extensionRegistry));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final ByteBuffer data) throws InvalidProtocolBufferException {
        return parseFrom(defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry());
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final ByteString data) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parseFrom((T)defaultInstance, data, ExtensionRegistryLite.getEmptyRegistry()));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, data, extensionRegistry));
    }
    
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(final T defaultInstance, final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        try {
            final CodedInputStream input = data.newCodedInput();
            final T message = parsePartialFrom(defaultInstance, input, extensionRegistry);
            try {
                input.checkLastTagWas(0);
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(message);
            }
            return message;
        }
        catch (InvalidProtocolBufferException e2) {
            throw e2;
        }
    }
    
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(final T defaultInstance, final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, data, 0, data.length, extensionRegistry));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final byte[] data) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, data, 0, data.length, ExtensionRegistryLite.getEmptyRegistry()));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, data, 0, data.length, extensionRegistry));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, CodedInputStream.newInstance(input), ExtensionRegistryLite.getEmptyRegistry()));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final InputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, CodedInputStream.newInstance(input), extensionRegistry));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final CodedInputStream input) throws InvalidProtocolBufferException {
        return parseFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(final T defaultInstance, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialFrom((T)defaultInstance, input, extensionRegistry));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(final T defaultInstance, final InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialDelimitedFrom((T)defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry()));
    }
    
    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(final T defaultInstance, final InputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized((T)parsePartialDelimitedFrom((T)defaultInstance, input, extensionRegistry));
    }
    
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(final T defaultInstance, final InputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        int size;
        try {
            final int firstByte = input.read();
            if (firstByte == -1) {
                return null;
            }
            size = CodedInputStream.readRawVarint32(firstByte, input);
        }
        catch (IOException e) {
            throw new InvalidProtocolBufferException(e.getMessage());
        }
        final InputStream limitedInput = new AbstractMessageLite.Builder.LimitedInputStream(input, size);
        final CodedInputStream codedInput = CodedInputStream.newInstance(limitedInput);
        final T message = parsePartialFrom(defaultInstance, codedInput, extensionRegistry);
        try {
            codedInput.checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(message);
        }
        return message;
    }
    
    static {
        GeneratedMessageLite.defaultInstanceMap = new ConcurrentHashMap<Object, GeneratedMessageLite<?, ?>>();
    }
    
    public enum MethodToInvoke
    {
        GET_MEMOIZED_IS_INITIALIZED, 
        SET_MEMOIZED_IS_INITIALIZED, 
        BUILD_MESSAGE_INFO, 
        NEW_MUTABLE_INSTANCE, 
        NEW_BUILDER, 
        GET_DEFAULT_INSTANCE, 
        GET_PARSER;
    }
    
    public abstract static class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType>
    {
        private final MessageType defaultInstance;
        protected MessageType instance;
        protected boolean isBuilt;
        
        protected Builder(final MessageType defaultInstance) {
            this.defaultInstance = defaultInstance;
            this.instance = (MessageType)defaultInstance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            this.isBuilt = false;
        }
        
        protected final void copyOnWrite() {
            if (this.isBuilt) {
                this.copyOnWriteInternal();
                this.isBuilt = false;
            }
        }
        
        protected void copyOnWriteInternal() {
            final MessageType newInstance = (MessageType)this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            this.mergeFromInstance(newInstance, this.instance);
            this.instance = newInstance;
        }
        
        @Override
        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }
        
        @Override
        public final BuilderType clear() {
            this.instance = (MessageType)this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType clone() {
            final BuilderType builder = this.getDefaultInstanceForType().newBuilderForType();
            builder.mergeFrom(this.buildPartial());
            return builder;
        }
        
        @Override
        public MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            this.instance.makeImmutable();
            this.isBuilt = true;
            return this.instance;
        }
        
        @Override
        public final MessageType build() {
            final MessageType result = this.buildPartial();
            if (!result.isInitialized()) {
                throw AbstractMessageLite.Builder.newUninitializedMessageException(result);
            }
            return result;
        }
        
        @Override
        protected BuilderType internalMergeFrom(final MessageType message) {
            return this.mergeFrom(message);
        }
        
        public BuilderType mergeFrom(final MessageType message) {
            this.copyOnWrite();
            this.mergeFromInstance(this.instance, message);
            return (BuilderType)this;
        }
        
        private void mergeFromInstance(final MessageType dest, final MessageType src) {
            Protobuf.getInstance().schemaFor(dest).mergeFrom(dest, src);
        }
        
        @Override
        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] input, final int offset, final int length, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor(this.instance).mergeFrom(this.instance, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
            }
            catch (InvalidProtocolBufferException e) {
                throw e;
            }
            catch (IndexOutOfBoundsException e3) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            }
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] input, final int offset, final int length) throws InvalidProtocolBufferException {
            return this.mergeFrom(input, offset, length, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public BuilderType mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            this.copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor(this.instance).mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
            }
            catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw (IOException)e.getCause();
                }
                throw e;
            }
            return (BuilderType)this;
        }
    }
    
    public abstract static class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType>
    {
        protected FieldSet<ExtensionDescriptor> extensions;
        
        public ExtendableMessage() {
            this.extensions = FieldSet.emptySet();
        }
        
        protected final void mergeExtensionFields(final MessageType other) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            this.extensions.mergeFrom(other.extensions);
        }
        
        protected <MessageType extends MessageLite> boolean parseUnknownField(final MessageType defaultInstance, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
            final int fieldNumber = WireFormat.getTagFieldNumber(tag);
            final GeneratedExtension<MessageType, ?> extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber);
            return this.parseExtension(input, extensionRegistry, extension, tag, fieldNumber);
        }
        
        private boolean parseExtension(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final GeneratedExtension<?, ?> extension, final int tag, final int fieldNumber) throws IOException {
            final int wireType = WireFormat.getTagWireType(tag);
            boolean unknown = false;
            boolean packed = false;
            if (extension == null) {
                unknown = true;
            }
            else if (wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), false)) {
                packed = false;
            }
            else if (extension.descriptor.isRepeated && extension.descriptor.type.isPackable() && wireType == FieldSet.getWireFormatForFieldType(extension.descriptor.getLiteType(), true)) {
                packed = true;
            }
            else {
                unknown = true;
            }
            if (unknown) {
                return this.parseUnknownField(tag, input);
            }
            this.ensureExtensionsAreMutable();
            if (packed) {
                final int length = input.readRawVarint32();
                final int limit = input.pushLimit(length);
                if (extension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        final int rawValue = input.readEnum();
                        final Object value = extension.descriptor.getEnumType().findValueByNumber(rawValue);
                        if (value == null) {
                            return true;
                        }
                        this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value));
                    }
                }
                else {
                    while (input.getBytesUntilLimit() > 0) {
                        final Object value2 = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                        this.extensions.addRepeatedField(extension.descriptor, value2);
                    }
                }
                input.popLimit(limit);
            }
            else {
                Object value3 = null;
                switch (extension.descriptor.getLiteJavaType()) {
                    case MESSAGE: {
                        MessageLite.Builder subBuilder = null;
                        if (!extension.descriptor.isRepeated()) {
                            final MessageLite existingValue = (MessageLite)this.extensions.getField(extension.descriptor);
                            if (existingValue != null) {
                                subBuilder = existingValue.toBuilder();
                            }
                        }
                        if (subBuilder == null) {
                            subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
                        }
                        if (extension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                            input.readGroup(extension.getNumber(), subBuilder, extensionRegistry);
                        }
                        else {
                            input.readMessage(subBuilder, extensionRegistry);
                        }
                        value3 = subBuilder.build();
                        break;
                    }
                    case ENUM: {
                        final int rawValue2 = input.readEnum();
                        value3 = extension.descriptor.getEnumType().findValueByNumber(rawValue2);
                        if (value3 == null) {
                            this.mergeVarintField(fieldNumber, rawValue2);
                            return true;
                        }
                        break;
                    }
                    default: {
                        value3 = FieldSet.readPrimitiveField(input, extension.descriptor.getLiteType(), false);
                        break;
                    }
                }
                if (extension.descriptor.isRepeated()) {
                    this.extensions.addRepeatedField(extension.descriptor, extension.singularToFieldSetType(value3));
                }
                else {
                    this.extensions.setField(extension.descriptor, extension.singularToFieldSetType(value3));
                }
            }
            return true;
        }
        
        protected <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(final MessageType defaultInstance, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
            if (tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
                this.mergeMessageSetExtensionFromCodedStream(defaultInstance, input, extensionRegistry);
                return true;
            }
            final int wireType = WireFormat.getTagWireType(tag);
            if (wireType == 2) {
                return this.parseUnknownField((MessageLite)defaultInstance, input, extensionRegistry, tag);
            }
            return input.skipField(tag);
        }
        
        private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(final MessageType defaultInstance, final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            int typeId = 0;
            ByteString rawBytes = null;
            GeneratedExtension<?, ?> extension = null;
            while (true) {
                final int tag = input.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = input.readUInt32();
                    if (typeId == 0) {
                        continue;
                    }
                    extension = extensionRegistry.findLiteExtensionByNumber((Object)defaultInstance, typeId);
                }
                else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (typeId != 0 && extension != null) {
                        this.eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, typeId);
                        rawBytes = null;
                    }
                    else {
                        rawBytes = input.readBytes();
                    }
                }
                else {
                    if (!input.skipField(tag)) {
                        break;
                    }
                    continue;
                }
            }
            input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
            if (rawBytes != null && typeId != 0) {
                if (extension != null) {
                    this.mergeMessageSetExtensionFromBytes(rawBytes, extensionRegistry, extension);
                }
                else if (rawBytes != null) {
                    this.mergeLengthDelimitedField(typeId, rawBytes);
                }
            }
        }
        
        private void eagerlyMergeMessageSetExtension(final CodedInputStream input, final GeneratedExtension<?, ?> extension, final ExtensionRegistryLite extensionRegistry, final int typeId) throws IOException {
            final int fieldNumber = typeId;
            final int tag = WireFormat.makeTag(typeId, 2);
            this.parseExtension(input, extensionRegistry, extension, tag, fieldNumber);
        }
        
        private void mergeMessageSetExtensionFromBytes(final ByteString rawBytes, final ExtensionRegistryLite extensionRegistry, final GeneratedExtension<?, ?> extension) throws IOException {
            MessageLite.Builder subBuilder = null;
            final MessageLite existingValue = (MessageLite)this.extensions.getField(extension.descriptor);
            if (existingValue != null) {
                subBuilder = existingValue.toBuilder();
            }
            if (subBuilder == null) {
                subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
            }
            subBuilder.mergeFrom(rawBytes, extensionRegistry);
            final MessageLite value = subBuilder.build();
            this.ensureExtensionsAreMutable().setField(extension.descriptor, extension.singularToFieldSetType(value));
        }
        
        FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            return this.extensions;
        }
        
        private void verifyExtensionContainingType(final GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != this.getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }
        
        @Override
        public final <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> extension) {
            final GeneratedExtension<MessageType, Type> extensionLite = (GeneratedExtension<MessageType, Type>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            return this.extensions.hasField(extensionLite.descriptor);
        }
        
        @Override
        public final <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> extension) {
            final GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedExtension<MessageType, List<Type>>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            return this.extensions.getRepeatedFieldCount(extensionLite.descriptor);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, Type> extension) {
            final GeneratedExtension<MessageType, Type> extensionLite = (GeneratedExtension<MessageType, Type>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            final Object value = this.extensions.getField(extensionLite.descriptor);
            if (value == null) {
                return extensionLite.defaultValue;
            }
            return (Type)extensionLite.fromFieldSetType(value);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> extension, final int index) {
            final GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedExtension<MessageType, List<Type>>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            return (Type)extensionLite.singularFromFieldSetType(this.extensions.getRepeatedField(extensionLite.descriptor, index));
        }
        
        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }
        
        protected ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }
        
        protected ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }
        
        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }
        
        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }
        
        protected class ExtensionWriter
        {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private Map.Entry<ExtensionDescriptor, Object> next;
            private final boolean messageSetWireFormat;
            
            private ExtensionWriter(final boolean messageSetWireFormat) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat;
            }
            
            public void writeUntil(final int end, final CodedOutputStream output) throws IOException {
                while (this.next != null && this.next.getKey().getNumber() < end) {
                    final ExtensionDescriptor extension = this.next.getKey();
                    if (this.messageSetWireFormat && extension.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !extension.isRepeated()) {
                        output.writeMessageSetExtension(extension.getNumber(), this.next.getValue());
                    }
                    else {
                        FieldSet.writeField(extension, this.next.getValue(), output);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    }
                    else {
                        this.next = null;
                    }
                }
            }
        }
    }
    
    public abstract static class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType>
    {
        protected ExtendableBuilder(final MessageType defaultInstance) {
            super(defaultInstance);
        }
        
        void internalSetExtensionSet(final FieldSet<ExtensionDescriptor> extensions) {
            this.copyOnWrite();
            this.instance.extensions = extensions;
        }
        
        @Override
        protected void copyOnWriteInternal() {
            super.copyOnWriteInternal();
            this.instance.extensions = this.instance.extensions.clone();
        }
        
        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            FieldSet<ExtensionDescriptor> extensions = this.instance.extensions;
            if (extensions.isImmutable()) {
                extensions = extensions.clone();
                this.instance.extensions = extensions;
            }
            return extensions;
        }
        
        @Override
        public final MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            this.instance.extensions.makeImmutable();
            return super.buildPartial();
        }
        
        private void verifyExtensionContainingType(final GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != this.getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }
        
        @Override
        public final <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage<MessageType, BuilderType>)this.instance).hasExtension(extension);
        }
        
        @Override
        public final <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> extension) {
            return ((ExtendableMessage<MessageType, BuilderType>)this.instance).getExtensionCount(extension);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage<MessageType, BuilderType>)this.instance).getExtension(extension);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> extension, final int index) {
            return ((ExtendableMessage<MessageType, BuilderType>)this.instance).getExtension(extension, index);
        }
        
        public final <Type> BuilderType setExtension(final ExtensionLite<MessageType, Type> extension, final Type value) {
            final GeneratedExtension<MessageType, Type> extensionLite = (GeneratedExtension<MessageType, Type>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            this.copyOnWrite();
            this.ensureExtensionsAreMutable().setField(extensionLite.descriptor, extensionLite.toFieldSetType(value));
            return (BuilderType)this;
        }
        
        public final <Type> BuilderType setExtension(final ExtensionLite<MessageType, List<Type>> extension, final int index, final Type value) {
            final GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedExtension<MessageType, List<Type>>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            this.copyOnWrite();
            this.ensureExtensionsAreMutable().setRepeatedField(extensionLite.descriptor, index, extensionLite.singularToFieldSetType(value));
            return (BuilderType)this;
        }
        
        public final <Type> BuilderType addExtension(final ExtensionLite<MessageType, List<Type>> extension, final Type value) {
            final GeneratedExtension<MessageType, List<Type>> extensionLite = (GeneratedExtension<MessageType, List<Type>>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            this.copyOnWrite();
            this.ensureExtensionsAreMutable().addRepeatedField(extensionLite.descriptor, extensionLite.singularToFieldSetType(value));
            return (BuilderType)this;
        }
        
        public final BuilderType clearExtension(final ExtensionLite<MessageType, ?> extension) {
            final GeneratedExtension<MessageType, ?> extensionLite = (GeneratedExtension<MessageType, ?>)checkIsLite((ExtensionLite<ExtendableMessage, Object>)extension);
            this.verifyExtensionContainingType(extensionLite);
            this.copyOnWrite();
            this.ensureExtensionsAreMutable().clearField(extensionLite.descriptor);
            return (BuilderType)this;
        }
    }
    
    static final class ExtensionDescriptor implements FieldSet.FieldDescriptorLite<ExtensionDescriptor>
    {
        final Internal.EnumLiteMap<?> enumTypeMap;
        final int number;
        final WireFormat.FieldType type;
        final boolean isRepeated;
        final boolean isPacked;
        
        ExtensionDescriptor(final Internal.EnumLiteMap<?> enumTypeMap, final int number, final WireFormat.FieldType type, final boolean isRepeated, final boolean isPacked) {
            this.enumTypeMap = enumTypeMap;
            this.number = number;
            this.type = type;
            this.isRepeated = isRepeated;
            this.isPacked = isPacked;
        }
        
        @Override
        public int getNumber() {
            return this.number;
        }
        
        @Override
        public WireFormat.FieldType getLiteType() {
            return this.type;
        }
        
        @Override
        public WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }
        
        @Override
        public boolean isRepeated() {
            return this.isRepeated;
        }
        
        @Override
        public boolean isPacked() {
            return this.isPacked;
        }
        
        @Override
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }
        
        @Override
        public MessageLite.Builder internalMergeFrom(final MessageLite.Builder to, final MessageLite from) {
            return ((GeneratedMessageLite.Builder)to).mergeFrom((GeneratedMessageLite)from);
        }
        
        @Override
        public int compareTo(final ExtensionDescriptor other) {
            return this.number - other.number;
        }
    }
    
    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type>
    {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final MessageLite messageDefaultInstance;
        final ExtensionDescriptor descriptor;
        
        GeneratedExtension(final ContainingType containingTypeDefaultInstance, final Type defaultValue, final MessageLite messageDefaultInstance, final ExtensionDescriptor descriptor, final Class singularType) {
            if (containingTypeDefaultInstance == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (descriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageDefaultInstance == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.containingTypeDefaultInstance = containingTypeDefaultInstance;
            this.defaultValue = defaultValue;
            this.messageDefaultInstance = messageDefaultInstance;
            this.descriptor = descriptor;
        }
        
        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }
        
        @Override
        public int getNumber() {
            return this.descriptor.getNumber();
        }
        
        @Override
        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }
        
        Object fromFieldSetType(final Object value) {
            if (!this.descriptor.isRepeated()) {
                return this.singularFromFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                final List result = new ArrayList();
                for (final Object element : (List)value) {
                    result.add(this.singularFromFieldSetType(element));
                }
                return result;
            }
            return value;
        }
        
        Object singularFromFieldSetType(final Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber((int)value);
            }
            return value;
        }
        
        Object toFieldSetType(final Object value) {
            if (!this.descriptor.isRepeated()) {
                return this.singularToFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                final List result = new ArrayList();
                for (final Object element : (List)value) {
                    result.add(this.singularToFieldSetType(element));
                }
                return result;
            }
            return value;
        }
        
        Object singularToFieldSetType(final Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return ((Internal.EnumLite)value).getNumber();
            }
            return value;
        }
        
        @Override
        public WireFormat.FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }
        
        @Override
        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }
        
        @Override
        public Type getDefaultValue() {
            return this.defaultValue;
        }
    }
    
    protected static final class SerializedForm implements Serializable
    {
        private static final long serialVersionUID = 0L;
        private final Class<?> messageClass;
        private final String messageClassName;
        private final byte[] asBytes;
        
        public static SerializedForm of(final MessageLite message) {
            return new SerializedForm(message);
        }
        
        SerializedForm(final MessageLite regularForm) {
            this.messageClass = regularForm.getClass();
            this.messageClassName = this.messageClass.getName();
            this.asBytes = regularForm.toByteArray();
        }
        
        protected Object readResolve() throws ObjectStreamException {
            try {
                final Class<?> messageClass = this.resolveMessageClass();
                final Field defaultInstanceField = messageClass.getDeclaredField("DEFAULT_INSTANCE");
                defaultInstanceField.setAccessible(true);
                final MessageLite defaultInstance = (MessageLite)defaultInstanceField.get(null);
                return defaultInstance.newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e);
            }
            catch (NoSuchFieldException e5) {
                return this.readResolveFallback();
            }
            catch (SecurityException e2) {
                throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e2);
            }
            catch (IllegalAccessException e3) {
                throw new RuntimeException("Unable to call parsePartialFrom", e3);
            }
            catch (InvalidProtocolBufferException e4) {
                throw new RuntimeException("Unable to understand proto buffer", e4);
            }
        }
        
        @Deprecated
        private Object readResolveFallback() throws ObjectStreamException {
            try {
                final Class<?> messageClass = this.resolveMessageClass();
                final Field defaultInstanceField = messageClass.getDeclaredField("defaultInstance");
                defaultInstanceField.setAccessible(true);
                final MessageLite defaultInstance = (MessageLite)defaultInstanceField.get(null);
                return defaultInstance.newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e);
            }
            catch (NoSuchFieldException e2) {
                throw new RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e2);
            }
            catch (SecurityException e3) {
                throw new RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e3);
            }
            catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            }
            catch (InvalidProtocolBufferException e5) {
                throw new RuntimeException("Unable to understand proto buffer", e5);
            }
        }
        
        private Class<?> resolveMessageClass() throws ClassNotFoundException {
            return (this.messageClass != null) ? this.messageClass : Class.forName(this.messageClassName);
        }
    }
    
    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T>
    {
        private final T defaultInstance;
        
        public DefaultInstanceBasedParser(final T defaultInstance) {
            this.defaultInstance = defaultInstance;
        }
        
        @Override
        public T parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, extensionRegistry);
        }
        
        @Override
        public T parsePartialFrom(final byte[] input, final int offset, final int length, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, offset, length, extensionRegistry);
        }
    }
    
    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder
    {
         <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> p0);
        
         <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> p0);
        
         <Type> Type getExtension(final ExtensionLite<MessageType, Type> p0);
        
         <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> p0, final int p1);
    }
}
