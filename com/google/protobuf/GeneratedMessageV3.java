package com.google.protobuf;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;

public abstract class GeneratedMessageV3 extends AbstractMessage implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected static boolean alwaysUseFieldBuilders;
    protected UnknownFieldSet unknownFields;
    
    protected GeneratedMessageV3() {
        this.unknownFields = UnknownFieldSet.getDefaultInstance();
    }
    
    protected GeneratedMessageV3(final Builder<?> builder) {
        this.unknownFields = builder.getUnknownFields();
    }
    
    @Override
    public Parser<? extends GeneratedMessageV3> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }
    
    static void enableAlwaysUseFieldBuildersForTesting() {
        setAlwaysUseFieldBuildersForTesting(true);
    }
    
    static void setAlwaysUseFieldBuildersForTesting(final boolean useBuilders) {
        GeneratedMessageV3.alwaysUseFieldBuilders = useBuilders;
    }
    
    protected abstract FieldAccessorTable internalGetFieldAccessorTable();
    
    @Override
    public Descriptors.Descriptor getDescriptorForType() {
        return this.internalGetFieldAccessorTable().descriptor;
    }
    
    protected void mergeFromAndMakeImmutableInternal(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        final Schema<GeneratedMessageV3> schema = Protobuf.getInstance().schemaFor(this);
        try {
            schema.mergeFrom(this, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        }
        catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
        }
        schema.makeImmutable(this);
    }
    
    private Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable(final boolean getBytesForString) {
        final TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<Descriptors.FieldDescriptor, Object>();
        final Descriptors.Descriptor descriptor = this.internalGetFieldAccessorTable().descriptor;
        final List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
        for (int i = 0; i < fields.size(); ++i) {
            Descriptors.FieldDescriptor field = fields.get(i);
            final Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                i += oneofDescriptor.getFieldCount() - 1;
                if (!this.hasOneof(oneofDescriptor)) {
                    continue;
                }
                field = this.getOneofFieldDescriptor(oneofDescriptor);
            }
            else if (field.isRepeated()) {
                final List<?> value = (List<?>)this.getField(field);
                if (!value.isEmpty()) {
                    result.put(field, value);
                }
                continue;
            }
            else if (!this.hasField(field)) {
                continue;
            }
            if (getBytesForString && field.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
                result.put(field, this.getFieldRaw(field));
            }
            else {
                result.put(field, this.getField(field));
            }
        }
        return result;
    }
    
    @Override
    public boolean isInitialized() {
        for (final Descriptors.FieldDescriptor field : this.getDescriptorForType().getFields()) {
            if (field.isRequired() && !this.hasField(field)) {
                return false;
            }
            if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                continue;
            }
            if (field.isRepeated()) {
                final List<Message> messageList = (List<Message>)this.getField(field);
                for (final Message element : messageList) {
                    if (!element.isInitialized()) {
                        return false;
                    }
                }
            }
            else {
                if (this.hasField(field) && !((Message)this.getField(field)).isInitialized()) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    @Override
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)this.getAllFieldsMutable(false));
    }
    
    Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
        return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)this.getAllFieldsMutable(true));
    }
    
    @Override
    public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
        return this.internalGetFieldAccessorTable().getOneof(oneof).has(this);
    }
    
    @Override
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
        return this.internalGetFieldAccessorTable().getOneof(oneof).get(this);
    }
    
    @Override
    public boolean hasField(final Descriptors.FieldDescriptor field) {
        return this.internalGetFieldAccessorTable().getField(field).has(this);
    }
    
    @Override
    public Object getField(final Descriptors.FieldDescriptor field) {
        return this.internalGetFieldAccessorTable().getField(field).get(this);
    }
    
    Object getFieldRaw(final Descriptors.FieldDescriptor field) {
        return this.internalGetFieldAccessorTable().getField(field).getRaw(this);
    }
    
    @Override
    public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
        return this.internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
    }
    
    @Override
    public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
        return this.internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
    }
    
    @Override
    public UnknownFieldSet getUnknownFields() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }
    
    protected boolean parseUnknownField(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
        if (input.shouldDiscardUnknownFields()) {
            return input.skipField(tag);
        }
        return unknownFields.mergeFieldFrom(tag, input);
    }
    
    protected boolean parseUnknownFieldProto3(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
        return this.parseUnknownField(input, unknownFields, extensionRegistry, tag);
    }
    
    protected static <M extends Message> M parseWithIOException(final Parser<M> parser, final InputStream input) throws IOException {
        try {
            return parser.parseFrom(input);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static <M extends Message> M parseWithIOException(final Parser<M> parser, final InputStream input, final ExtensionRegistryLite extensions) throws IOException {
        try {
            return parser.parseFrom(input, extensions);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static <M extends Message> M parseWithIOException(final Parser<M> parser, final CodedInputStream input) throws IOException {
        try {
            return parser.parseFrom(input);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static <M extends Message> M parseWithIOException(final Parser<M> parser, final CodedInputStream input, final ExtensionRegistryLite extensions) throws IOException {
        try {
            return parser.parseFrom(input, extensions);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static <M extends Message> M parseDelimitedWithIOException(final Parser<M> parser, final InputStream input) throws IOException {
        try {
            return parser.parseDelimitedFrom(input);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static <M extends Message> M parseDelimitedWithIOException(final Parser<M> parser, final InputStream input, final ExtensionRegistryLite extensions) throws IOException {
        try {
            return parser.parseDelimitedFrom(input, extensions);
        }
        catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }
    
    protected static boolean canUseUnsafe() {
        return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
    }
    
    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }
    
    protected static Internal.IntList newIntList() {
        return new IntArrayList();
    }
    
    protected static Internal.IntList mutableCopy(final Internal.IntList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }
    
    protected static Internal.LongList newLongList() {
        return new LongArrayList();
    }
    
    protected static Internal.LongList mutableCopy(final Internal.LongList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }
    
    protected static Internal.FloatList newFloatList() {
        return new FloatArrayList();
    }
    
    protected static Internal.FloatList mutableCopy(final Internal.FloatList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }
    
    protected static Internal.DoubleList newDoubleList() {
        return new DoubleArrayList();
    }
    
    protected static Internal.DoubleList mutableCopy(final Internal.DoubleList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }
    
    protected static Internal.BooleanList newBooleanList() {
        return new BooleanArrayList();
    }
    
    protected static Internal.BooleanList mutableCopy(final Internal.BooleanList list) {
        final int size = list.size();
        return list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
    }
    
    @Override
    public void writeTo(final CodedOutputStream output) throws IOException {
        MessageReflection.writeMessageTo(this, this.getAllFieldsRaw(), output, false);
    }
    
    @Override
    public int getSerializedSize() {
        final int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        return this.memoizedSize = MessageReflection.getSerializedSize(this, this.getAllFieldsRaw());
    }
    
    protected Object newInstance(final UnusedPrivateParameter unused) {
        throw new UnsupportedOperationException("This method must be overridden by the subclass.");
    }
    
    protected void makeExtensionsImmutable() {
    }
    
    protected abstract Message.Builder newBuilderForType(final BuilderParent p0);
    
    @Override
    protected Message.Builder newBuilderForType(final AbstractMessage.BuilderParent parent) {
        return this.newBuilderForType(new BuilderParent() {
            @Override
            public void markDirty() {
                parent.markDirty();
            }
        });
    }
    
    private static Method getMethodOrDie(final Class clazz, final String name, final Class... params) {
        try {
            return clazz.getMethod(name, (Class[])params);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }
    
    private static Object invokeOrDie(final Method method, final Object object, final Object... params) {
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
    
    protected MapField internalGetMapField(final int fieldNumber) {
        throw new RuntimeException("No map fields found in " + this.getClass().getName());
    }
    
    protected Object writeReplace() throws ObjectStreamException {
        return new GeneratedMessageLite.SerializedForm(this);
    }
    
    private static <MessageType extends ExtendableMessage<MessageType>, T> Extension<MessageType, T> checkNotLite(final ExtensionLite<MessageType, T> extension) {
        if (extension.isLite()) {
            throw new IllegalArgumentException("Expected non-lite extension.");
        }
        return (Extension<MessageType, T>)(Extension)extension;
    }
    
    protected static int computeStringSize(final int fieldNumber, final Object value) {
        if (value instanceof String) {
            return CodedOutputStream.computeStringSize(fieldNumber, (String)value);
        }
        return CodedOutputStream.computeBytesSize(fieldNumber, (ByteString)value);
    }
    
    protected static int computeStringSizeNoTag(final Object value) {
        if (value instanceof String) {
            return CodedOutputStream.computeStringSizeNoTag((String)value);
        }
        return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
    }
    
    protected static void writeString(final CodedOutputStream output, final int fieldNumber, final Object value) throws IOException {
        if (value instanceof String) {
            output.writeString(fieldNumber, (String)value);
        }
        else {
            output.writeBytes(fieldNumber, (ByteString)value);
        }
    }
    
    protected static void writeStringNoTag(final CodedOutputStream output, final Object value) throws IOException {
        if (value instanceof String) {
            output.writeStringNoTag((String)value);
        }
        else {
            output.writeBytesNoTag((ByteString)value);
        }
    }
    
    protected static <V> void serializeIntegerMapTo(final CodedOutputStream out, final MapField<Integer, V> field, final MapEntry<Integer, V> defaultEntry, final int fieldNumber) throws IOException {
        final Map<Integer, V> m = field.getMap();
        if (!out.isSerializationDeterministic()) {
            serializeMapTo(out, m, defaultEntry, fieldNumber);
            return;
        }
        final int[] keys = new int[m.size()];
        int index = 0;
        for (final int k : m.keySet()) {
            keys[index++] = k;
        }
        Arrays.sort(keys);
        for (final int key : keys) {
            out.writeMessage(fieldNumber, defaultEntry.newBuilderForType().setKey(key).setValue(m.get(key)).build());
        }
    }
    
    protected static <V> void serializeLongMapTo(final CodedOutputStream out, final MapField<Long, V> field, final MapEntry<Long, V> defaultEntry, final int fieldNumber) throws IOException {
        final Map<Long, V> m = field.getMap();
        if (!out.isSerializationDeterministic()) {
            serializeMapTo(out, m, defaultEntry, fieldNumber);
            return;
        }
        final long[] keys = new long[m.size()];
        int index = 0;
        for (final long k : m.keySet()) {
            keys[index++] = k;
        }
        Arrays.sort(keys);
        for (final long key : keys) {
            out.writeMessage(fieldNumber, defaultEntry.newBuilderForType().setKey(key).setValue(m.get(key)).build());
        }
    }
    
    protected static <V> void serializeStringMapTo(final CodedOutputStream out, final MapField<String, V> field, final MapEntry<String, V> defaultEntry, final int fieldNumber) throws IOException {
        final Map<String, V> m = field.getMap();
        if (!out.isSerializationDeterministic()) {
            serializeMapTo(out, m, defaultEntry, fieldNumber);
            return;
        }
        String[] keys = new String[m.size()];
        keys = m.keySet().toArray(keys);
        Arrays.sort(keys);
        for (final String key : keys) {
            out.writeMessage(fieldNumber, defaultEntry.newBuilderForType().setKey(key).setValue(m.get(key)).build());
        }
    }
    
    protected static <V> void serializeBooleanMapTo(final CodedOutputStream out, final MapField<Boolean, V> field, final MapEntry<Boolean, V> defaultEntry, final int fieldNumber) throws IOException {
        final Map<Boolean, V> m = field.getMap();
        if (!out.isSerializationDeterministic()) {
            serializeMapTo(out, m, defaultEntry, fieldNumber);
            return;
        }
        maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, false);
        maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, true);
    }
    
    private static <V> void maybeSerializeBooleanEntryTo(final CodedOutputStream out, final Map<Boolean, V> m, final MapEntry<Boolean, V> defaultEntry, final int fieldNumber, final boolean key) throws IOException {
        if (m.containsKey(key)) {
            out.writeMessage(fieldNumber, defaultEntry.newBuilderForType().setKey(key).setValue(m.get(key)).build());
        }
    }
    
    private static <K, V> void serializeMapTo(final CodedOutputStream out, final Map<K, V> m, final MapEntry<K, V> defaultEntry, final int fieldNumber) throws IOException {
        for (final Map.Entry<K, V> entry : m.entrySet()) {
            out.writeMessage(fieldNumber, defaultEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build());
        }
    }
    
    static {
        GeneratedMessageV3.alwaysUseFieldBuilders = false;
    }
    
    protected static final class UnusedPrivateParameter
    {
        static final UnusedPrivateParameter INSTANCE;
        
        private UnusedPrivateParameter() {
        }
        
        static {
            INSTANCE = new UnusedPrivateParameter();
        }
    }
    
    public abstract static class Builder<BuilderType extends Builder<BuilderType>> extends AbstractMessage.Builder<BuilderType>
    {
        private GeneratedMessageV3.BuilderParent builderParent;
        private BuilderParentImpl meAsParent;
        private boolean isClean;
        private UnknownFieldSet unknownFields;
        
        protected Builder() {
            this(null);
        }
        
        protected Builder(final GeneratedMessageV3.BuilderParent builderParent) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent;
        }
        
        @Override
        void dispose() {
            this.builderParent = null;
        }
        
        protected void onBuilt() {
            if (this.builderParent != null) {
                this.markClean();
            }
        }
        
        protected void markClean() {
            this.isClean = true;
        }
        
        protected boolean isClean() {
            return this.isClean;
        }
        
        @Override
        public BuilderType clone() {
            final BuilderType builder = (BuilderType)this.getDefaultInstanceForType().newBuilderForType();
            builder.mergeFrom(this.buildPartial());
            return builder;
        }
        
        @Override
        public BuilderType clear() {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.onChanged();
            return (BuilderType)this;
        }
        
        protected abstract FieldAccessorTable internalGetFieldAccessorTable();
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.internalGetFieldAccessorTable().descriptor;
        }
        
        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)this.getAllFieldsMutable());
        }
        
        private Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
            final TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<Descriptors.FieldDescriptor, Object>();
            final Descriptors.Descriptor descriptor = this.internalGetFieldAccessorTable().descriptor;
            final List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
            for (int i = 0; i < fields.size(); ++i) {
                Descriptors.FieldDescriptor field = fields.get(i);
                final Descriptors.OneofDescriptor oneofDescriptor = field.getContainingOneof();
                if (oneofDescriptor != null) {
                    i += oneofDescriptor.getFieldCount() - 1;
                    if (!this.hasOneof(oneofDescriptor)) {
                        continue;
                    }
                    field = this.getOneofFieldDescriptor(oneofDescriptor);
                }
                else if (field.isRepeated()) {
                    final List<?> value = (List<?>)this.getField(field);
                    if (!value.isEmpty()) {
                        result.put(field, value);
                    }
                    continue;
                }
                else if (!this.hasField(field)) {
                    continue;
                }
                result.put(field, this.getField(field));
            }
            return result;
        }
        
        @Override
        public Message.Builder newBuilderForField(final Descriptors.FieldDescriptor field) {
            return this.internalGetFieldAccessorTable().getField(field).newBuilder();
        }
        
        @Override
        public Message.Builder getFieldBuilder(final Descriptors.FieldDescriptor field) {
            return this.internalGetFieldAccessorTable().getField(field).getBuilder(this);
        }
        
        @Override
        public Message.Builder getRepeatedFieldBuilder(final Descriptors.FieldDescriptor field, final int index) {
            return this.internalGetFieldAccessorTable().getField(field).getRepeatedBuilder(this, index);
        }
        
        @Override
        public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
            return this.internalGetFieldAccessorTable().getOneof(oneof).has(this);
        }
        
        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
            return this.internalGetFieldAccessorTable().getOneof(oneof).get(this);
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            return this.internalGetFieldAccessorTable().getField(field).has(this);
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            final Object object = this.internalGetFieldAccessorTable().getField(field).get(this);
            if (field.isRepeated()) {
                return Collections.unmodifiableList((List<?>)object);
            }
            return object;
        }
        
        @Override
        public BuilderType setField(final Descriptors.FieldDescriptor field, final Object value) {
            this.internalGetFieldAccessorTable().getField(field).set(this, value);
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType clearField(final Descriptors.FieldDescriptor field) {
            this.internalGetFieldAccessorTable().getField(field).clear(this);
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType clearOneof(final Descriptors.OneofDescriptor oneof) {
            this.internalGetFieldAccessorTable().getOneof(oneof).clear(this);
            return (BuilderType)this;
        }
        
        @Override
        public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
            return this.internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
        }
        
        @Override
        public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
            return this.internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
        }
        
        @Override
        public BuilderType setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            this.internalGetFieldAccessorTable().getField(field).setRepeated(this, index, value);
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            this.internalGetFieldAccessorTable().getField(field).addRepeated(this, value);
            return (BuilderType)this;
        }
        
        private BuilderType setUnknownFieldsInternal(final UnknownFieldSet unknownFields) {
            this.unknownFields = unknownFields;
            this.onChanged();
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType setUnknownFields(final UnknownFieldSet unknownFields) {
            return this.setUnknownFieldsInternal(unknownFields);
        }
        
        protected BuilderType setUnknownFieldsProto3(final UnknownFieldSet unknownFields) {
            return this.setUnknownFieldsInternal(unknownFields);
        }
        
        @Override
        public BuilderType mergeUnknownFields(final UnknownFieldSet unknownFields) {
            return this.setUnknownFields(UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build());
        }
        
        @Override
        public boolean isInitialized() {
            for (final Descriptors.FieldDescriptor field : this.getDescriptorForType().getFields()) {
                if (field.isRequired() && !this.hasField(field)) {
                    return false;
                }
                if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    continue;
                }
                if (field.isRepeated()) {
                    final List<Message> messageList = (List<Message>)this.getField(field);
                    for (final Message element : messageList) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                }
                else {
                    if (this.hasField(field) && !((Message)this.getField(field)).isInitialized()) {
                        return false;
                    }
                    continue;
                }
            }
            return true;
        }
        
        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        protected GeneratedMessageV3.BuilderParent getParentForChildren() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl();
            }
            return this.meAsParent;
        }
        
        protected final void onChanged() {
            if (this.isClean && this.builderParent != null) {
                this.builderParent.markDirty();
                this.isClean = false;
            }
        }
        
        protected MapField internalGetMapField(final int fieldNumber) {
            throw new RuntimeException("No map fields found in " + this.getClass().getName());
        }
        
        protected MapField internalGetMutableMapField(final int fieldNumber) {
            throw new RuntimeException("No map fields found in " + this.getClass().getName());
        }
        
        private class BuilderParentImpl implements GeneratedMessageV3.BuilderParent
        {
            @Override
            public void markDirty() {
                GeneratedMessageV3.Builder.this.onChanged();
            }
        }
    }
    
    public abstract static class ExtendableMessage<MessageType extends ExtendableMessage> extends GeneratedMessageV3 implements ExtendableMessageOrBuilder<MessageType>
    {
        private static final long serialVersionUID = 1L;
        private final FieldSet<Descriptors.FieldDescriptor> extensions;
        
        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }
        
        protected ExtendableMessage(final ExtendableBuilder<MessageType, ?> builder) {
            super(builder);
            this.extensions = (FieldSet<Descriptors.FieldDescriptor>)((ExtendableBuilder<ExtendableMessage, ExtendableBuilder>)builder).buildExtensions();
        }
        
        private void verifyExtensionContainingType(final Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != this.getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + this.getDescriptorForType().getFullName() + "\".");
            }
        }
        
        @Override
        public final <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> extensionLite) {
            final Extension<MessageType, Type> extension = (Extension<MessageType, Type>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }
        
        @Override
        public final <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> extensionLite) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            return this.extensions.getRepeatedFieldCount(descriptor);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, Type> extensionLite) {
            final Extension<MessageType, Type> extension = (Extension<MessageType, Type>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            final Object value = this.extensions.getField(descriptor);
            if (value != null) {
                return (Type)extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return (Type)Collections.emptyList();
            }
            if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return (Type)extension.getMessageDefaultInstance();
            }
            return (Type)extension.fromReflectionType(descriptor.getDefaultValue());
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> extensionLite, final int index) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            return (Type)extension.singularFromReflectionType(this.extensions.getRepeatedField(descriptor, index));
        }
        
        @Override
        public final <Type> boolean hasExtension(final Extension<MessageType, Type> extension) {
            return this.hasExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> boolean hasExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> extension) {
            return this.hasExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> int getExtensionCount(final Extension<MessageType, List<Type>> extension) {
            return this.getExtensionCount((ExtensionLite<MessageType, List<Type>>)extension);
        }
        
        @Override
        public final <Type> int getExtensionCount(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension) {
            return this.getExtensionCount((ExtensionLite<MessageType, List<Type>>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final Extension<MessageType, Type> extension) {
            return this.getExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> extension) {
            return this.getExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final Extension<MessageType, List<Type>> extension, final int index) {
            return this.getExtension((ExtensionLite<MessageType, List<Type>>)extension, index);
        }
        
        @Override
        public final <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension, final int index) {
            return this.getExtension((ExtensionLite<MessageType, List<Type>>)extension, index);
        }
        
        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }
        
        @Override
        public boolean isInitialized() {
            return super.isInitialized() && this.extensionsAreInitialized();
        }
        
        @Override
        protected boolean parseUnknownField(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
            return MessageReflection.mergeFieldFrom(input, input.shouldDiscardUnknownFields() ? null : unknownFields, extensionRegistry, this.getDescriptorForType(), new MessageReflection.ExtensionAdapter(this.extensions), tag);
        }
        
        @Override
        protected boolean parseUnknownFieldProto3(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final int tag) throws IOException {
            return this.parseUnknownField(input, unknownFields, extensionRegistry, tag);
        }
        
        @Override
        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
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
        
        protected Map<Descriptors.FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }
        
        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            final Map<Descriptors.FieldDescriptor, Object> result = GeneratedMessageV3.this.getAllFieldsMutable(false);
            result.putAll(this.getExtensionFields());
            return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)result);
        }
        
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
            final Map<Descriptors.FieldDescriptor, Object> result = GeneratedMessageV3.this.getAllFieldsMutable(false);
            result.putAll(this.getExtensionFields());
            return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)result);
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                return this.extensions.hasField(field);
            }
            return super.hasField(field);
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getField(field);
            }
            this.verifyContainingType(field);
            final Object value = this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.isRepeated()) {
                return Collections.emptyList();
            }
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }
        
        @Override
        public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                return this.extensions.getRepeatedFieldCount(field);
            }
            return super.getRepeatedFieldCount(field);
        }
        
        @Override
        public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                return this.extensions.getRepeatedField(field, index);
            }
            return super.getRepeatedField(field, index);
        }
        
        private void verifyContainingType(final Descriptors.FieldDescriptor field) {
            if (field.getContainingType() != this.getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
        
        protected class ExtensionWriter
        {
            private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter;
            private Map.Entry<Descriptors.FieldDescriptor, Object> next;
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
                    final Descriptors.FieldDescriptor descriptor = this.next.getKey();
                    if (this.messageSetWireFormat && descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !descriptor.isRepeated()) {
                        if (this.next instanceof LazyField.LazyEntry) {
                            output.writeRawMessageSetExtension(descriptor.getNumber(), ((LazyField.LazyEntry)this.next).getField().toByteString());
                        }
                        else {
                            output.writeMessageSetExtension(descriptor.getNumber(), this.next.getValue());
                        }
                    }
                    else {
                        FieldSet.writeField(descriptor, this.next.getValue(), output);
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
    
    public abstract static class ExtendableBuilder<MessageType extends ExtendableMessage, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<BuilderType> implements ExtendableMessageOrBuilder<MessageType>
    {
        private FieldSet.Builder<Descriptors.FieldDescriptor> extensions;
        
        protected ExtendableBuilder() {
        }
        
        protected ExtendableBuilder(final GeneratedMessageV3.BuilderParent parent) {
            super(parent);
        }
        
        void internalSetExtensionSet(final FieldSet<Descriptors.FieldDescriptor> extensions) {
            this.extensions = FieldSet.Builder.fromFieldSet(extensions);
        }
        
        @Override
        public BuilderType clear() {
            this.extensions = null;
            return super.clear();
        }
        
        private void ensureExtensionsIsMutable() {
            if (this.extensions == null) {
                this.extensions = FieldSet.newBuilder();
            }
        }
        
        private void verifyExtensionContainingType(final Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != this.getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + this.getDescriptorForType().getFullName() + "\".");
            }
        }
        
        @Override
        public final <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> extensionLite) {
            final Extension<MessageType, Type> extension = (Extension<MessageType, Type>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            return this.extensions != null && this.extensions.hasField(extension.getDescriptor());
        }
        
        @Override
        public final <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> extensionLite) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            return (this.extensions == null) ? 0 : this.extensions.getRepeatedFieldCount(descriptor);
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, Type> extensionLite) {
            final Extension<MessageType, Type> extension = (Extension<MessageType, Type>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            final Object value = (this.extensions == null) ? null : this.extensions.getField(descriptor);
            if (value != null) {
                return (Type)extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return (Type)Collections.emptyList();
            }
            if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return (Type)extension.getMessageDefaultInstance();
            }
            return (Type)extension.fromReflectionType(descriptor.getDefaultValue());
        }
        
        @Override
        public final <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> extensionLite, final int index) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            if (this.extensions == null) {
                throw new IndexOutOfBoundsException();
            }
            return (Type)extension.singularFromReflectionType(this.extensions.getRepeatedField(descriptor, index));
        }
        
        public final <Type> BuilderType setExtension(final ExtensionLite<MessageType, Type> extensionLite, final Type value) {
            final Extension<MessageType, Type> extension = (Extension<MessageType, Type>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            this.ensureExtensionsIsMutable();
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            this.extensions.setField(descriptor, extension.toReflectionType(value));
            this.onChanged();
            return (BuilderType)this;
        }
        
        public final <Type> BuilderType setExtension(final ExtensionLite<MessageType, List<Type>> extensionLite, final int index, final Type value) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            this.ensureExtensionsIsMutable();
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            this.extensions.setRepeatedField(descriptor, index, extension.singularToReflectionType(value));
            this.onChanged();
            return (BuilderType)this;
        }
        
        public final <Type> BuilderType addExtension(final ExtensionLite<MessageType, List<Type>> extensionLite, final Type value) {
            final Extension<MessageType, List<Type>> extension = (Extension<MessageType, List<Type>>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            this.ensureExtensionsIsMutable();
            final Descriptors.FieldDescriptor descriptor = extension.getDescriptor();
            this.extensions.addRepeatedField(descriptor, extension.singularToReflectionType(value));
            this.onChanged();
            return (BuilderType)this;
        }
        
        public final BuilderType clearExtension(final ExtensionLite<MessageType, ?> extensionLite) {
            final Extension<MessageType, ?> extension = (Extension<MessageType, ?>)checkNotLite((ExtensionLite<ExtendableMessage, Object>)extensionLite);
            this.verifyExtensionContainingType(extension);
            this.ensureExtensionsIsMutable();
            this.extensions.clearField(extension.getDescriptor());
            this.onChanged();
            return (BuilderType)this;
        }
        
        @Override
        public final <Type> boolean hasExtension(final Extension<MessageType, Type> extension) {
            return this.hasExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> boolean hasExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> extension) {
            return this.hasExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> int getExtensionCount(final Extension<MessageType, List<Type>> extension) {
            return this.getExtensionCount((ExtensionLite<MessageType, List<Type>>)extension);
        }
        
        @Override
        public final <Type> int getExtensionCount(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension) {
            return this.getExtensionCount((ExtensionLite<MessageType, List<Type>>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final Extension<MessageType, Type> extension) {
            return this.getExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> extension) {
            return this.getExtension((ExtensionLite<MessageType, Type>)extension);
        }
        
        @Override
        public final <Type> Type getExtension(final Extension<MessageType, List<Type>> extension, final int index) {
            return this.getExtension((ExtensionLite<MessageType, List<Type>>)extension, index);
        }
        
        @Override
        public final <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension, final int index) {
            return this.getExtension((ExtensionLite<MessageType, List<Type>>)extension, index);
        }
        
        public final <Type> BuilderType setExtension(final Extension<MessageType, Type> extension, final Type value) {
            return this.setExtension((ExtensionLite<MessageType, Type>)extension, value);
        }
        
        public <Type> BuilderType setExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> extension, final Type value) {
            return this.setExtension((ExtensionLite<MessageType, Type>)extension, value);
        }
        
        public final <Type> BuilderType setExtension(final Extension<MessageType, List<Type>> extension, final int index, final Type value) {
            return this.setExtension((ExtensionLite<MessageType, List<Type>>)extension, index, value);
        }
        
        public <Type> BuilderType setExtension(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension, final int index, final Type value) {
            return this.setExtension((ExtensionLite<MessageType, List<Type>>)extension, index, value);
        }
        
        public final <Type> BuilderType addExtension(final Extension<MessageType, List<Type>> extension, final Type value) {
            return this.addExtension((ExtensionLite<MessageType, List<Type>>)extension, value);
        }
        
        public <Type> BuilderType addExtension(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> extension, final Type value) {
            return this.addExtension((ExtensionLite<MessageType, List<Type>>)extension, value);
        }
        
        public final <Type> BuilderType clearExtension(final Extension<MessageType, ?> extension) {
            return this.clearExtension((ExtensionLite<MessageType, ?>)extension);
        }
        
        public <Type> BuilderType clearExtension(final GeneratedMessage.GeneratedExtension<MessageType, ?> extension) {
            return this.clearExtension((ExtensionLite<MessageType, ?>)extension);
        }
        
        protected boolean extensionsAreInitialized() {
            return this.extensions == null || this.extensions.isInitialized();
        }
        
        private FieldSet<Descriptors.FieldDescriptor> buildExtensions() {
            return (this.extensions == null) ? FieldSet.emptySet() : this.extensions.build();
        }
        
        @Override
        public boolean isInitialized() {
            return super.isInitialized() && this.extensionsAreInitialized();
        }
        
        @Override
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            final Map<Descriptors.FieldDescriptor, Object> result = (Map<Descriptors.FieldDescriptor, Object>)((Builder<Builder>)this).getAllFieldsMutable();
            if (this.extensions != null) {
                result.putAll(this.extensions.getAllFields());
            }
            return Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ?>)result);
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getField(field);
            }
            this.verifyContainingType(field);
            final Object value = (this.extensions == null) ? null : this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }
        
        @Override
        public Message.Builder getFieldBuilder(final Descriptors.FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getFieldBuilder(field);
            }
            this.verifyContainingType(field);
            if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
            this.ensureExtensionsIsMutable();
            final Object value = this.extensions.getFieldAllowBuilders(field);
            if (value == null) {
                final Message.Builder builder = DynamicMessage.newBuilder(field.getMessageType());
                this.extensions.setField(field, builder);
                this.onChanged();
                return builder;
            }
            if (value instanceof Message.Builder) {
                return (Message.Builder)value;
            }
            if (value instanceof Message) {
                final Message.Builder builder = ((Message)value).toBuilder();
                this.extensions.setField(field, builder);
                this.onChanged();
                return builder;
            }
            throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
        }
        
        @Override
        public int getRepeatedFieldCount(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                return (this.extensions == null) ? 0 : this.extensions.getRepeatedFieldCount(field);
            }
            return super.getRepeatedFieldCount(field);
        }
        
        @Override
        public Object getRepeatedField(final Descriptors.FieldDescriptor field, final int index) {
            if (!field.isExtension()) {
                return super.getRepeatedField(field, index);
            }
            this.verifyContainingType(field);
            if (this.extensions == null) {
                throw new IndexOutOfBoundsException();
            }
            return this.extensions.getRepeatedField(field, index);
        }
        
        @Override
        public Message.Builder getRepeatedFieldBuilder(final Descriptors.FieldDescriptor field, final int index) {
            if (!field.isExtension()) {
                return super.getRepeatedFieldBuilder(field, index);
            }
            this.verifyContainingType(field);
            this.ensureExtensionsIsMutable();
            if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            final Object value = this.extensions.getRepeatedFieldAllowBuilders(field, index);
            if (value instanceof Message.Builder) {
                return (Message.Builder)value;
            }
            if (value instanceof Message) {
                final Message.Builder builder = ((Message)value).toBuilder();
                this.extensions.setRepeatedField(field, index, builder);
                this.onChanged();
                return builder;
            }
            throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                return this.extensions != null && this.extensions.hasField(field);
            }
            return super.hasField(field);
        }
        
        @Override
        public BuilderType setField(final Descriptors.FieldDescriptor field, final Object value) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                this.ensureExtensionsIsMutable();
                this.extensions.setField(field, value);
                this.onChanged();
                return (BuilderType)this;
            }
            return super.setField(field, value);
        }
        
        @Override
        public BuilderType clearField(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                this.ensureExtensionsIsMutable();
                this.extensions.clearField(field);
                this.onChanged();
                return (BuilderType)this;
            }
            return super.clearField(field);
        }
        
        @Override
        public BuilderType setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                this.ensureExtensionsIsMutable();
                this.extensions.setRepeatedField(field, index, value);
                this.onChanged();
                return (BuilderType)this;
            }
            return super.setRepeatedField(field, index, value);
        }
        
        @Override
        public BuilderType addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            if (field.isExtension()) {
                this.verifyContainingType(field);
                this.ensureExtensionsIsMutable();
                this.extensions.addRepeatedField(field, value);
                this.onChanged();
                return (BuilderType)this;
            }
            return super.addRepeatedField(field, value);
        }
        
        @Override
        public Message.Builder newBuilderForField(final Descriptors.FieldDescriptor field) {
            if (field.isExtension()) {
                return DynamicMessage.newBuilder(field.getMessageType());
            }
            return super.newBuilderForField(field);
        }
        
        protected final void mergeExtensionFields(final ExtendableMessage other) {
            if (other.extensions != null) {
                this.ensureExtensionsIsMutable();
                this.extensions.mergeFrom(other.extensions);
                this.onChanged();
            }
        }
        
        private void verifyContainingType(final Descriptors.FieldDescriptor field) {
            if (field.getContainingType() != this.getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }
    
    public static final class FieldAccessorTable
    {
        private final Descriptors.Descriptor descriptor;
        private final FieldAccessor[] fields;
        private String[] camelCaseNames;
        private final OneofAccessor[] oneofs;
        private volatile boolean initialized;
        
        public FieldAccessorTable(final Descriptors.Descriptor descriptor, final String[] camelCaseNames, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
            this(descriptor, camelCaseNames);
            this.ensureFieldAccessorsInitialized(messageClass, builderClass);
        }
        
        public FieldAccessorTable(final Descriptors.Descriptor descriptor, final String[] camelCaseNames) {
            this.descriptor = descriptor;
            this.camelCaseNames = camelCaseNames;
            this.fields = new FieldAccessor[descriptor.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor.getOneofs().size()];
            this.initialized = false;
        }
        
        public FieldAccessorTable ensureFieldAccessorsInitialized(final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
            if (this.initialized) {
                return this;
            }
            synchronized (this) {
                if (this.initialized) {
                    return this;
                }
                final int fieldsSize = this.fields.length;
                for (int i = 0; i < fieldsSize; ++i) {
                    final Descriptors.FieldDescriptor field = this.descriptor.getFields().get(i);
                    String containingOneofCamelCaseName = null;
                    if (field.getContainingOneof() != null) {
                        containingOneofCamelCaseName = this.camelCaseNames[fieldsSize + field.getContainingOneof().getIndex()];
                    }
                    if (field.isRepeated()) {
                        if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                            if (field.isMapField()) {
                                this.fields[i] = new MapFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                            }
                            else {
                                this.fields[i] = new RepeatedMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                            }
                        }
                        else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                            this.fields[i] = new RepeatedEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                        }
                        else {
                            this.fields[i] = new RepeatedFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                        }
                    }
                    else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        this.fields[i] = new SingularMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                    }
                    else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                        this.fields[i] = new SingularEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                    }
                    else if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
                        this.fields[i] = new SingularStringFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                    }
                    else {
                        this.fields[i] = new SingularFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                    }
                }
                for (int oneofsSize = this.oneofs.length, j = 0; j < oneofsSize; ++j) {
                    this.oneofs[j] = new OneofAccessor(this.descriptor, this.camelCaseNames[j + fieldsSize], messageClass, builderClass);
                }
                this.initialized = true;
                this.camelCaseNames = null;
                return this;
            }
        }
        
        private FieldAccessor getField(final Descriptors.FieldDescriptor field) {
            if (field.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
            if (field.isExtension()) {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
            return this.fields[field.getIndex()];
        }
        
        private OneofAccessor getOneof(final Descriptors.OneofDescriptor oneof) {
            if (oneof.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
            return this.oneofs[oneof.getIndex()];
        }
        
        private static boolean supportFieldPresence(final Descriptors.FileDescriptor file) {
            return file.getSyntax() == Descriptors.FileDescriptor.Syntax.PROTO2;
        }
        
        private static class OneofAccessor
        {
            private final Descriptors.Descriptor descriptor;
            private final Method caseMethod;
            private final Method caseMethodBuilder;
            private final Method clearMethod;
            
            OneofAccessor(final Descriptors.Descriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                this.descriptor = descriptor;
                this.caseMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "Case", new Class[0]);
                this.caseMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Case", new Class[0]);
                this.clearMethod = getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
            }
            
            public boolean has(final GeneratedMessageV3 message) {
                return ((Internal.EnumLite)invokeOrDie(this.caseMethod, message, new Object[0])).getNumber() != 0;
            }
            
            public boolean has(final Builder builder) {
                return ((Internal.EnumLite)invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() != 0;
            }
            
            public Descriptors.FieldDescriptor get(final GeneratedMessageV3 message) {
                final int fieldNumber = ((Internal.EnumLite)invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }
            
            public Descriptors.FieldDescriptor get(final Builder builder) {
                final int fieldNumber = ((Internal.EnumLite)invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }
            
            public void clear(final Builder builder) {
                invokeOrDie(this.clearMethod, builder, new Object[0]);
            }
        }
        
        private static class SingularFieldAccessor implements FieldAccessor
        {
            protected final Class<?> type;
            protected final Descriptors.FieldDescriptor field;
            protected final boolean isOneofField;
            protected final boolean hasHasMethod;
            protected final MethodInvoker invoker;
            
            SingularFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass, final String containingOneofCamelCaseName) {
                this.isOneofField = (descriptor.getContainingOneof() != null);
                this.hasHasMethod = (supportFieldPresence(descriptor.getFile()) || (!this.isOneofField && descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE));
                final ReflectionInvoker reflectionInvoker = new ReflectionInvoker(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName, this.isOneofField, this.hasHasMethod);
                this.field = descriptor;
                this.type = reflectionInvoker.getMethod.getReturnType();
                this.invoker = getMethodInvoker(reflectionInvoker);
            }
            
            static MethodInvoker getMethodInvoker(final ReflectionInvoker accessor) {
                return accessor;
            }
            
            @Override
            public Object get(final GeneratedMessageV3 message) {
                return this.invoker.get(message);
            }
            
            @Override
            public Object get(final Builder builder) {
                return this.invoker.get(builder);
            }
            
            @Override
            public Object getRaw(final GeneratedMessageV3 message) {
                return this.get(message);
            }
            
            @Override
            public Object getRaw(final Builder builder) {
                return this.get(builder);
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                this.invoker.set(builder, value);
            }
            
            @Override
            public Object getRepeated(final GeneratedMessageV3 message, final int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }
            
            @Override
            public Object getRepeatedRaw(final GeneratedMessageV3 message, final int index) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }
            
            @Override
            public Object getRepeated(final Builder builder, final int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }
            
            @Override
            public Object getRepeatedRaw(final Builder builder, final int index) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }
            
            @Override
            public void setRepeated(final Builder builder, final int index, final Object value) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }
            
            @Override
            public void addRepeated(final Builder builder, final Object value) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }
            
            @Override
            public boolean has(final GeneratedMessageV3 message) {
                if (this.hasHasMethod) {
                    return this.invoker.has(message);
                }
                if (this.isOneofField) {
                    return this.invoker.getOneofFieldNumber(message) == this.field.getNumber();
                }
                return !this.get(message).equals(this.field.getDefaultValue());
            }
            
            @Override
            public boolean has(final Builder builder) {
                if (this.hasHasMethod) {
                    return this.invoker.has(builder);
                }
                if (this.isOneofField) {
                    return this.invoker.getOneofFieldNumber(builder) == this.field.getNumber();
                }
                return !this.get(builder).equals(this.field.getDefaultValue());
            }
            
            @Override
            public int getRepeatedCount(final GeneratedMessageV3 message) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }
            
            @Override
            public int getRepeatedCount(final Builder builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }
            
            @Override
            public void clear(final Builder builder) {
                this.invoker.clear(builder);
            }
            
            @Override
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }
            
            @Override
            public Message.Builder getBuilder(final Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
            
            @Override
            public Message.Builder getRepeatedBuilder(final Builder builder, final int index) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            
            private static final class ReflectionInvoker implements MethodInvoker
            {
                protected final Method getMethod;
                protected final Method getMethodBuilder;
                protected final Method setMethod;
                protected final Method hasMethod;
                protected final Method hasMethodBuilder;
                protected final Method clearMethod;
                protected final Method caseMethod;
                protected final Method caseMethodBuilder;
                
                ReflectionInvoker(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass, final String containingOneofCamelCaseName, final boolean isOneofField, final boolean hasHasMethod) {
                    this.getMethod = getMethodOrDie(messageClass, "get" + camelCaseName, new Class[0]);
                    this.getMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName, new Class[0]);
                    final Class<?> type = this.getMethod.getReturnType();
                    this.setMethod = getMethodOrDie(builderClass, "set" + camelCaseName, new Class[] { type });
                    this.hasMethod = (hasHasMethod ? getMethodOrDie(messageClass, "has" + camelCaseName, new Class[0]) : null);
                    this.hasMethodBuilder = (hasHasMethod ? getMethodOrDie(builderClass, "has" + camelCaseName, new Class[0]) : null);
                    this.clearMethod = getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
                    this.caseMethod = (isOneofField ? getMethodOrDie(messageClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]) : null);
                    this.caseMethodBuilder = (isOneofField ? getMethodOrDie(builderClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]) : null);
                }
                
                @Override
                public Object get(final GeneratedMessageV3 message) {
                    return invokeOrDie(this.getMethod, message, new Object[0]);
                }
                
                @Override
                public Object get(final Builder<?> builder) {
                    return invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
                }
                
                @Override
                public int getOneofFieldNumber(final GeneratedMessageV3 message) {
                    return ((Internal.EnumLite)invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
                }
                
                @Override
                public int getOneofFieldNumber(final Builder<?> builder) {
                    return ((Internal.EnumLite)invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                }
                
                @Override
                public void set(final Builder<?> builder, final Object value) {
                    invokeOrDie(this.setMethod, builder, new Object[] { value });
                }
                
                @Override
                public boolean has(final GeneratedMessageV3 message) {
                    return (boolean)invokeOrDie(this.hasMethod, message, new Object[0]);
                }
                
                @Override
                public boolean has(final Builder<?> builder) {
                    return (boolean)invokeOrDie(this.hasMethodBuilder, builder, new Object[0]);
                }
                
                @Override
                public void clear(final Builder<?> builder) {
                    invokeOrDie(this.clearMethod, builder, new Object[0]);
                }
            }
            
            private interface MethodInvoker
            {
                Object get(final GeneratedMessageV3 p0);
                
                Object get(final Builder<?> p0);
                
                int getOneofFieldNumber(final GeneratedMessageV3 p0);
                
                int getOneofFieldNumber(final Builder<?> p0);
                
                void set(final Builder<?> p0, final Object p1);
                
                boolean has(final GeneratedMessageV3 p0);
                
                boolean has(final Builder<?> p0);
                
                void clear(final Builder<?> p0);
            }
        }
        
        private static class RepeatedFieldAccessor implements FieldAccessor
        {
            protected final Class type;
            protected final MethodInvoker invoker;
            
            RepeatedFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                final ReflectionInvoker reflectionInvoker = new ReflectionInvoker(descriptor, camelCaseName, messageClass, builderClass);
                this.type = reflectionInvoker.getRepeatedMethod.getReturnType();
                this.invoker = getMethodInvoker(reflectionInvoker);
            }
            
            static MethodInvoker getMethodInvoker(final ReflectionInvoker accessor) {
                return accessor;
            }
            
            @Override
            public Object get(final GeneratedMessageV3 message) {
                return this.invoker.get(message);
            }
            
            @Override
            public Object get(final Builder builder) {
                return this.invoker.get(builder);
            }
            
            @Override
            public Object getRaw(final GeneratedMessageV3 message) {
                return this.get(message);
            }
            
            @Override
            public Object getRaw(final Builder builder) {
                return this.get(builder);
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                this.clear(builder);
                for (final Object element : (List)value) {
                    this.addRepeated(builder, element);
                }
            }
            
            @Override
            public Object getRepeated(final GeneratedMessageV3 message, final int index) {
                return this.invoker.getRepeated(message, index);
            }
            
            @Override
            public Object getRepeated(final Builder builder, final int index) {
                return this.invoker.getRepeated(builder, index);
            }
            
            @Override
            public Object getRepeatedRaw(final GeneratedMessageV3 message, final int index) {
                return this.getRepeated(message, index);
            }
            
            @Override
            public Object getRepeatedRaw(final Builder builder, final int index) {
                return this.getRepeated(builder, index);
            }
            
            @Override
            public void setRepeated(final Builder builder, final int index, final Object value) {
                this.invoker.setRepeated(builder, index, value);
            }
            
            @Override
            public void addRepeated(final Builder builder, final Object value) {
                this.invoker.addRepeated(builder, value);
            }
            
            @Override
            public boolean has(final GeneratedMessageV3 message) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }
            
            @Override
            public boolean has(final Builder builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }
            
            @Override
            public int getRepeatedCount(final GeneratedMessageV3 message) {
                return this.invoker.getRepeatedCount(message);
            }
            
            @Override
            public int getRepeatedCount(final Builder builder) {
                return this.invoker.getRepeatedCount(builder);
            }
            
            @Override
            public void clear(final Builder builder) {
                this.invoker.clear(builder);
            }
            
            @Override
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }
            
            @Override
            public Message.Builder getBuilder(final Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
            
            @Override
            public Message.Builder getRepeatedBuilder(final Builder builder, final int index) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            
            private static final class ReflectionInvoker implements MethodInvoker
            {
                protected final Method getMethod;
                protected final Method getMethodBuilder;
                protected final Method getRepeatedMethod;
                protected final Method getRepeatedMethodBuilder;
                protected final Method setRepeatedMethod;
                protected final Method addRepeatedMethod;
                protected final Method getCountMethod;
                protected final Method getCountMethodBuilder;
                protected final Method clearMethod;
                
                ReflectionInvoker(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                    this.getMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "List", new Class[0]);
                    this.getMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "List", new Class[0]);
                    this.getRepeatedMethod = getMethodOrDie(messageClass, "get" + camelCaseName, new Class[] { Integer.TYPE });
                    this.getRepeatedMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName, new Class[] { Integer.TYPE });
                    final Class<?> type = this.getRepeatedMethod.getReturnType();
                    this.setRepeatedMethod = getMethodOrDie(builderClass, "set" + camelCaseName, new Class[] { Integer.TYPE, type });
                    this.addRepeatedMethod = getMethodOrDie(builderClass, "add" + camelCaseName, new Class[] { type });
                    this.getCountMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "Count", new Class[0]);
                    this.getCountMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Count", new Class[0]);
                    this.clearMethod = getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
                }
                
                @Override
                public Object get(final GeneratedMessageV3 message) {
                    return invokeOrDie(this.getMethod, message, new Object[0]);
                }
                
                @Override
                public Object get(final Builder<?> builder) {
                    return invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
                }
                
                @Override
                public Object getRepeated(final GeneratedMessageV3 message, final int index) {
                    return invokeOrDie(this.getRepeatedMethod, message, new Object[] { index });
                }
                
                @Override
                public Object getRepeated(final Builder<?> builder, final int index) {
                    return invokeOrDie(this.getRepeatedMethodBuilder, builder, new Object[] { index });
                }
                
                @Override
                public void setRepeated(final Builder<?> builder, final int index, final Object value) {
                    invokeOrDie(this.setRepeatedMethod, builder, new Object[] { index, value });
                }
                
                @Override
                public void addRepeated(final Builder<?> builder, final Object value) {
                    invokeOrDie(this.addRepeatedMethod, builder, new Object[] { value });
                }
                
                @Override
                public int getRepeatedCount(final GeneratedMessageV3 message) {
                    return (int)invokeOrDie(this.getCountMethod, message, new Object[0]);
                }
                
                @Override
                public int getRepeatedCount(final Builder<?> builder) {
                    return (int)invokeOrDie(this.getCountMethodBuilder, builder, new Object[0]);
                }
                
                @Override
                public void clear(final Builder<?> builder) {
                    invokeOrDie(this.clearMethod, builder, new Object[0]);
                }
            }
            
            interface MethodInvoker
            {
                Object get(final GeneratedMessageV3 p0);
                
                Object get(final Builder<?> p0);
                
                Object getRepeated(final GeneratedMessageV3 p0, final int p1);
                
                Object getRepeated(final Builder<?> p0, final int p1);
                
                void setRepeated(final Builder<?> p0, final int p1, final Object p2);
                
                void addRepeated(final Builder<?> p0, final Object p1);
                
                int getRepeatedCount(final GeneratedMessageV3 p0);
                
                int getRepeatedCount(final Builder<?> p0);
                
                void clear(final Builder<?> p0);
            }
        }
        
        private static class MapFieldAccessor implements FieldAccessor
        {
            private final Descriptors.FieldDescriptor field;
            private final Message mapEntryMessageDefaultInstance;
            
            MapFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                this.field = descriptor;
                final Method getDefaultInstanceMethod = getMethodOrDie(messageClass, "getDefaultInstance", new Class[0]);
                final MapField defaultMapField = this.getMapField((GeneratedMessageV3)invokeOrDie(getDefaultInstanceMethod, null, new Object[0]));
                this.mapEntryMessageDefaultInstance = defaultMapField.getMapEntryMessageDefaultInstance();
            }
            
            private MapField<?, ?> getMapField(final GeneratedMessageV3 message) {
                return (MapField<?, ?>)message.internalGetMapField(this.field.getNumber());
            }
            
            private MapField<?, ?> getMapField(final Builder builder) {
                return (MapField<?, ?>)builder.internalGetMapField(this.field.getNumber());
            }
            
            private MapField<?, ?> getMutableMapField(final Builder builder) {
                return (MapField<?, ?>)builder.internalGetMutableMapField(this.field.getNumber());
            }
            
            private Message coerceType(final Message value) {
                if (value == null) {
                    return null;
                }
                if (this.mapEntryMessageDefaultInstance.getClass().isInstance(value)) {
                    return value;
                }
                return this.mapEntryMessageDefaultInstance.toBuilder().mergeFrom(value).build();
            }
            
            @Override
            public Object get(final GeneratedMessageV3 message) {
                final List result = new ArrayList();
                for (int i = 0; i < this.getRepeatedCount(message); ++i) {
                    result.add(this.getRepeated(message, i));
                }
                return Collections.unmodifiableList((List<?>)result);
            }
            
            @Override
            public Object get(final Builder builder) {
                final List result = new ArrayList();
                for (int i = 0; i < this.getRepeatedCount(builder); ++i) {
                    result.add(this.getRepeated(builder, i));
                }
                return Collections.unmodifiableList((List<?>)result);
            }
            
            @Override
            public Object getRaw(final GeneratedMessageV3 message) {
                return this.get(message);
            }
            
            @Override
            public Object getRaw(final Builder builder) {
                return this.get(builder);
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                this.clear(builder);
                for (final Object entry : (List)value) {
                    this.addRepeated(builder, entry);
                }
            }
            
            @Override
            public Object getRepeated(final GeneratedMessageV3 message, final int index) {
                return this.getMapField(message).getList().get(index);
            }
            
            @Override
            public Object getRepeated(final Builder builder, final int index) {
                return this.getMapField(builder).getList().get(index);
            }
            
            @Override
            public Object getRepeatedRaw(final GeneratedMessageV3 message, final int index) {
                return this.getRepeated(message, index);
            }
            
            @Override
            public Object getRepeatedRaw(final Builder builder, final int index) {
                return this.getRepeated(builder, index);
            }
            
            @Override
            public void setRepeated(final Builder builder, final int index, final Object value) {
                this.getMutableMapField(builder).getMutableList().set(index, this.coerceType((Message)value));
            }
            
            @Override
            public void addRepeated(final Builder builder, final Object value) {
                this.getMutableMapField(builder).getMutableList().add(this.coerceType((Message)value));
            }
            
            @Override
            public boolean has(final GeneratedMessageV3 message) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }
            
            @Override
            public boolean has(final Builder builder) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }
            
            @Override
            public int getRepeatedCount(final GeneratedMessageV3 message) {
                return this.getMapField(message).getList().size();
            }
            
            @Override
            public int getRepeatedCount(final Builder builder) {
                return this.getMapField(builder).getList().size();
            }
            
            @Override
            public void clear(final Builder builder) {
                this.getMutableMapField(builder).getMutableList().clear();
            }
            
            @Override
            public Message.Builder newBuilder() {
                return this.mapEntryMessageDefaultInstance.newBuilderForType();
            }
            
            @Override
            public Message.Builder getBuilder(final Builder builder) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }
            
            @Override
            public Message.Builder getRepeatedBuilder(final Builder builder, final int index) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }
        }
        
        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor
        {
            private Descriptors.EnumDescriptor enumDescriptor;
            private Method valueOfMethod;
            private Method getValueDescriptorMethod;
            private boolean supportUnknownEnumValue;
            private Method getValueMethod;
            private Method getValueMethodBuilder;
            private Method setValueMethod;
            
            SingularEnumFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass, final String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.enumDescriptor = descriptor.getEnumType();
                this.valueOfMethod = getMethodOrDie(this.type, "valueOf", new Class[] { Descriptors.EnumValueDescriptor.class });
                this.getValueDescriptorMethod = getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
                this.supportUnknownEnumValue = descriptor.getFile().supportsUnknownEnumValue();
                if (this.supportUnknownEnumValue) {
                    this.getValueMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "Value", new Class[0]);
                    this.getValueMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Value", new Class[0]);
                    this.setValueMethod = getMethodOrDie(builderClass, "set" + camelCaseName + "Value", new Class[] { Integer.TYPE });
                }
            }
            
            @Override
            public Object get(final GeneratedMessageV3 message) {
                if (this.supportUnknownEnumValue) {
                    final int value = (int)invokeOrDie(this.getValueMethod, message, new Object[0]);
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
                }
                return invokeOrDie(this.getValueDescriptorMethod, super.get(message), new Object[0]);
            }
            
            @Override
            public Object get(final Builder builder) {
                if (this.supportUnknownEnumValue) {
                    final int value = (int)invokeOrDie(this.getValueMethodBuilder, builder, new Object[0]);
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
                }
                return invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                if (this.supportUnknownEnumValue) {
                    invokeOrDie(this.setValueMethod, builder, new Object[] { ((Descriptors.EnumValueDescriptor)value).getNumber() });
                    return;
                }
                super.set(builder, invokeOrDie(this.valueOfMethod, null, new Object[] { value }));
            }
        }
        
        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor
        {
            private Descriptors.EnumDescriptor enumDescriptor;
            private final Method valueOfMethod;
            private final Method getValueDescriptorMethod;
            private boolean supportUnknownEnumValue;
            private Method getRepeatedValueMethod;
            private Method getRepeatedValueMethodBuilder;
            private Method setRepeatedValueMethod;
            private Method addRepeatedValueMethod;
            
            RepeatedEnumFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.enumDescriptor = descriptor.getEnumType();
                this.valueOfMethod = getMethodOrDie(this.type, "valueOf", new Class[] { Descriptors.EnumValueDescriptor.class });
                this.getValueDescriptorMethod = getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
                this.supportUnknownEnumValue = descriptor.getFile().supportsUnknownEnumValue();
                if (this.supportUnknownEnumValue) {
                    this.getRepeatedValueMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "Value", new Class[] { Integer.TYPE });
                    this.getRepeatedValueMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Value", new Class[] { Integer.TYPE });
                    this.setRepeatedValueMethod = getMethodOrDie(builderClass, "set" + camelCaseName + "Value", new Class[] { Integer.TYPE, Integer.TYPE });
                    this.addRepeatedValueMethod = getMethodOrDie(builderClass, "add" + camelCaseName + "Value", new Class[] { Integer.TYPE });
                }
            }
            
            @Override
            public Object get(final GeneratedMessageV3 message) {
                final List newList = new ArrayList();
                for (int size = this.getRepeatedCount(message), i = 0; i < size; ++i) {
                    newList.add(this.getRepeated(message, i));
                }
                return Collections.unmodifiableList((List<?>)newList);
            }
            
            @Override
            public Object get(final Builder builder) {
                final List newList = new ArrayList();
                for (int size = this.getRepeatedCount(builder), i = 0; i < size; ++i) {
                    newList.add(this.getRepeated(builder, i));
                }
                return Collections.unmodifiableList((List<?>)newList);
            }
            
            @Override
            public Object getRepeated(final GeneratedMessageV3 message, final int index) {
                if (this.supportUnknownEnumValue) {
                    final int value = (int)invokeOrDie(this.getRepeatedValueMethod, message, new Object[] { index });
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
                }
                return invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(message, index), new Object[0]);
            }
            
            @Override
            public Object getRepeated(final Builder builder, final int index) {
                if (this.supportUnknownEnumValue) {
                    final int value = (int)invokeOrDie(this.getRepeatedValueMethodBuilder, builder, new Object[] { index });
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(value);
                }
                return invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, index), new Object[0]);
            }
            
            @Override
            public void setRepeated(final Builder builder, final int index, final Object value) {
                if (this.supportUnknownEnumValue) {
                    invokeOrDie(this.setRepeatedValueMethod, builder, new Object[] { index, ((Descriptors.EnumValueDescriptor)value).getNumber() });
                    return;
                }
                super.setRepeated(builder, index, invokeOrDie(this.valueOfMethod, null, new Object[] { value }));
            }
            
            @Override
            public void addRepeated(final Builder builder, final Object value) {
                if (this.supportUnknownEnumValue) {
                    invokeOrDie(this.addRepeatedValueMethod, builder, new Object[] { ((Descriptors.EnumValueDescriptor)value).getNumber() });
                    return;
                }
                super.addRepeated(builder, invokeOrDie(this.valueOfMethod, null, new Object[] { value }));
            }
        }
        
        private static final class SingularStringFieldAccessor extends SingularFieldAccessor
        {
            private final Method getBytesMethod;
            private final Method getBytesMethodBuilder;
            private final Method setBytesMethodBuilder;
            
            SingularStringFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass, final String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.getBytesMethod = getMethodOrDie(messageClass, "get" + camelCaseName + "Bytes", new Class[0]);
                this.getBytesMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Bytes", new Class[0]);
                this.setBytesMethodBuilder = getMethodOrDie(builderClass, "set" + camelCaseName + "Bytes", new Class[] { ByteString.class });
            }
            
            @Override
            public Object getRaw(final GeneratedMessageV3 message) {
                return invokeOrDie(this.getBytesMethod, message, new Object[0]);
            }
            
            @Override
            public Object getRaw(final Builder builder) {
                return invokeOrDie(this.getBytesMethodBuilder, builder, new Object[0]);
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                if (value instanceof ByteString) {
                    invokeOrDie(this.setBytesMethodBuilder, builder, new Object[] { value });
                }
                else {
                    super.set(builder, value);
                }
            }
        }
        
        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor
        {
            private final Method newBuilderMethod;
            private final Method getBuilderMethodBuilder;
            
            SingularMessageFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass, final String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.newBuilderMethod = getMethodOrDie(this.type, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[0]);
            }
            
            private Object coerceType(final Object value) {
                if (this.type.isInstance(value)) {
                    return value;
                }
                return ((Message.Builder)invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message)value).buildPartial();
            }
            
            @Override
            public void set(final Builder builder, final Object value) {
                super.set(builder, this.coerceType(value));
            }
            
            @Override
            public Message.Builder newBuilder() {
                return (Message.Builder)invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }
            
            @Override
            public Message.Builder getBuilder(final Builder builder) {
                return (Message.Builder)invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }
        }
        
        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor
        {
            private final Method newBuilderMethod;
            private final Method getBuilderMethodBuilder;
            
            RepeatedMessageFieldAccessor(final Descriptors.FieldDescriptor descriptor, final String camelCaseName, final Class<? extends GeneratedMessageV3> messageClass, final Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.newBuilderMethod = getMethodOrDie(this.type, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[] { Integer.TYPE });
            }
            
            private Object coerceType(final Object value) {
                if (this.type.isInstance(value)) {
                    return value;
                }
                return ((Message.Builder)invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message)value).build();
            }
            
            @Override
            public void setRepeated(final Builder builder, final int index, final Object value) {
                super.setRepeated(builder, index, this.coerceType(value));
            }
            
            @Override
            public void addRepeated(final Builder builder, final Object value) {
                super.addRepeated(builder, this.coerceType(value));
            }
            
            @Override
            public Message.Builder newBuilder() {
                return (Message.Builder)invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }
            
            @Override
            public Message.Builder getRepeatedBuilder(final Builder builder, final int index) {
                return (Message.Builder)invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[] { index });
            }
        }
        
        private interface FieldAccessor
        {
            Object get(final GeneratedMessageV3 p0);
            
            Object get(final Builder p0);
            
            Object getRaw(final GeneratedMessageV3 p0);
            
            Object getRaw(final Builder p0);
            
            void set(final Builder p0, final Object p1);
            
            Object getRepeated(final GeneratedMessageV3 p0, final int p1);
            
            Object getRepeated(final Builder p0, final int p1);
            
            Object getRepeatedRaw(final GeneratedMessageV3 p0, final int p1);
            
            Object getRepeatedRaw(final Builder p0, final int p1);
            
            void setRepeated(final Builder p0, final int p1, final Object p2);
            
            void addRepeated(final Builder p0, final Object p1);
            
            boolean has(final GeneratedMessageV3 p0);
            
            boolean has(final Builder p0);
            
            int getRepeatedCount(final GeneratedMessageV3 p0);
            
            int getRepeatedCount(final Builder p0);
            
            void clear(final Builder p0);
            
            Message.Builder newBuilder();
            
            Message.Builder getBuilder(final Builder p0);
            
            Message.Builder getRepeatedBuilder(final Builder p0, final int p1);
        }
    }
    
    protected interface BuilderParent extends AbstractMessage.BuilderParent
    {
    }
    
    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage> extends MessageOrBuilder
    {
        Message getDefaultInstanceForType();
        
         <Type> boolean hasExtension(final ExtensionLite<MessageType, Type> p0);
        
         <Type> int getExtensionCount(final ExtensionLite<MessageType, List<Type>> p0);
        
         <Type> Type getExtension(final ExtensionLite<MessageType, Type> p0);
        
         <Type> Type getExtension(final ExtensionLite<MessageType, List<Type>> p0, final int p1);
        
         <Type> boolean hasExtension(final Extension<MessageType, Type> p0);
        
         <Type> boolean hasExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> p0);
        
         <Type> int getExtensionCount(final Extension<MessageType, List<Type>> p0);
        
         <Type> int getExtensionCount(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> p0);
        
         <Type> Type getExtension(final Extension<MessageType, Type> p0);
        
         <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, Type> p0);
        
         <Type> Type getExtension(final Extension<MessageType, List<Type>> p0, final int p1);
        
         <Type> Type getExtension(final GeneratedMessage.GeneratedExtension<MessageType, List<Type>> p0, final int p1);
    }
    
    interface ExtensionDescriptorRetriever
    {
        Descriptors.FieldDescriptor getDescriptor();
    }
}
