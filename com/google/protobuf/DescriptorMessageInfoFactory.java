package com.google.protobuf;

import java.lang.reflect.*;
import java.util.concurrent.*;
import java.util.*;

final class DescriptorMessageInfoFactory implements MessageInfoFactory
{
    private static final String GET_DEFAULT_INSTANCE_METHOD_NAME = "getDefaultInstance";
    private static final DescriptorMessageInfoFactory instance;
    private static final Set<String> specialFieldNames;
    private static IsInitializedCheckAnalyzer isInitializedCheckAnalyzer;
    
    private DescriptorMessageInfoFactory() {
    }
    
    public static DescriptorMessageInfoFactory getInstance() {
        return DescriptorMessageInfoFactory.instance;
    }
    
    @Override
    public boolean isSupported(final Class<?> messageType) {
        return GeneratedMessageV3.class.isAssignableFrom(messageType);
    }
    
    @Override
    public MessageInfo messageInfoFor(final Class<?> messageType) {
        if (!GeneratedMessageV3.class.isAssignableFrom(messageType)) {
            throw new IllegalArgumentException("Unsupported message type: " + messageType.getName());
        }
        return convert(messageType, descriptorForType(messageType));
    }
    
    private static Message getDefaultInstance(final Class<?> messageType) {
        try {
            final Method method = messageType.getDeclaredMethod("getDefaultInstance", (Class<?>[])new Class[0]);
            return (Message)method.invoke(null, new Object[0]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to get default instance for message class " + messageType.getName(), e);
        }
    }
    
    private static Descriptors.Descriptor descriptorForType(final Class<?> messageType) {
        return getDefaultInstance(messageType).getDescriptorForType();
    }
    
    private static MessageInfo convert(final Class<?> messageType, final Descriptors.Descriptor messageDescriptor) {
        switch (messageDescriptor.getFile().getSyntax()) {
            case PROTO2: {
                return convertProto2(messageType, messageDescriptor);
            }
            case PROTO3: {
                return convertProto3(messageType, messageDescriptor);
            }
            default: {
                throw new IllegalArgumentException("Unsupported syntax: " + messageDescriptor.getFile().getSyntax());
            }
        }
    }
    
    private static boolean needsIsInitializedCheck(final Descriptors.Descriptor descriptor) {
        return DescriptorMessageInfoFactory.isInitializedCheckAnalyzer.needsIsInitializedCheck(descriptor);
    }
    
    private static StructuralMessageInfo convertProto2(final Class<?> messageType, final Descriptors.Descriptor messageDescriptor) {
        final List<Descriptors.FieldDescriptor> fieldDescriptors = messageDescriptor.getFields();
        final StructuralMessageInfo.Builder builder = StructuralMessageInfo.newBuilder(fieldDescriptors.size());
        builder.withDefaultInstance(getDefaultInstance(messageType));
        builder.withSyntax(ProtoSyntax.PROTO2);
        builder.withMessageSetWireFormat(messageDescriptor.getOptions().getMessageSetWireFormat());
        final OneofState oneofState = new OneofState();
        int bitFieldIndex = 0;
        int presenceMask = 1;
        Field bitField = null;
        for (int i = 0; i < fieldDescriptors.size(); ++i) {
            final Descriptors.FieldDescriptor fd = fieldDescriptors.get(i);
            final boolean enforceUtf8 = fd.getFile().getOptions().getJavaStringCheckUtf8();
            Internal.EnumVerifier enumVerifier = null;
            if (fd.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                enumVerifier = new Internal.EnumVerifier() {
                    @Override
                    public boolean isInRange(final int number) {
                        return fd.getEnumType().findValueByNumber(number) != null;
                    }
                };
            }
            if (fd.getContainingOneof() != null) {
                builder.withField(buildOneofMember(messageType, fd, oneofState, enforceUtf8, enumVerifier));
            }
            else {
                final Field field = field(messageType, fd);
                final int number = fd.getNumber();
                final FieldType type = getFieldType(fd);
                if (fd.isMapField()) {
                    final Descriptors.FieldDescriptor valueField = fd.getMessageType().findFieldByNumber(2);
                    if (valueField.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                        enumVerifier = new Internal.EnumVerifier() {
                            @Override
                            public boolean isInRange(final int number) {
                                return valueField.getEnumType().findValueByNumber(number) != null;
                            }
                        };
                    }
                    builder.withField(FieldInfo.forMapField(field, number, SchemaUtil.getMapDefaultEntry(messageType, fd.getName()), enumVerifier));
                    continue;
                }
                if (fd.isRepeated()) {
                    if (enumVerifier != null) {
                        if (fd.isPacked()) {
                            builder.withField(FieldInfo.forPackedFieldWithEnumVerifier(field, number, type, enumVerifier, cachedSizeField(messageType, fd)));
                            continue;
                        }
                        builder.withField(FieldInfo.forFieldWithEnumVerifier(field, number, type, enumVerifier));
                        continue;
                    }
                    else {
                        if (fd.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                            builder.withField(FieldInfo.forRepeatedMessageField(field, number, type, getTypeForRepeatedMessageField(messageType, fd)));
                            continue;
                        }
                        if (fd.isPacked()) {
                            builder.withField(FieldInfo.forPackedField(field, number, type, cachedSizeField(messageType, fd)));
                            continue;
                        }
                        builder.withField(FieldInfo.forField(field, number, type, enforceUtf8));
                        continue;
                    }
                }
                else {
                    if (bitField == null) {
                        bitField = bitField(messageType, bitFieldIndex);
                    }
                    if (fd.isRequired()) {
                        builder.withField(FieldInfo.forProto2RequiredField(field, number, type, bitField, presenceMask, enforceUtf8, enumVerifier));
                    }
                    else {
                        builder.withField(FieldInfo.forProto2OptionalField(field, number, type, bitField, presenceMask, enforceUtf8, enumVerifier));
                    }
                }
            }
            presenceMask <<= 1;
            if (presenceMask == 0) {
                bitField = null;
                presenceMask = 1;
                ++bitFieldIndex;
            }
        }
        final List<Integer> fieldsToCheckIsInitialized = new ArrayList<Integer>();
        for (int j = 0; j < fieldDescriptors.size(); ++j) {
            final Descriptors.FieldDescriptor fd2 = fieldDescriptors.get(j);
            if (fd2.isRequired() || (fd2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE && needsIsInitializedCheck(fd2.getMessageType()))) {
                fieldsToCheckIsInitialized.add(fd2.getNumber());
            }
        }
        final int[] numbers = new int[fieldsToCheckIsInitialized.size()];
        for (int k = 0; k < fieldsToCheckIsInitialized.size(); ++k) {
            numbers[k] = fieldsToCheckIsInitialized.get(k);
        }
        builder.withCheckInitialized(numbers);
        return builder.build();
    }
    
    private static StructuralMessageInfo convertProto3(final Class<?> messageType, final Descriptors.Descriptor messageDescriptor) {
        final List<Descriptors.FieldDescriptor> fieldDescriptors = messageDescriptor.getFields();
        final StructuralMessageInfo.Builder builder = StructuralMessageInfo.newBuilder(fieldDescriptors.size());
        builder.withDefaultInstance(getDefaultInstance(messageType));
        builder.withSyntax(ProtoSyntax.PROTO3);
        final OneofState oneofState = new OneofState();
        final boolean enforceUtf8 = true;
        for (int i = 0; i < fieldDescriptors.size(); ++i) {
            final Descriptors.FieldDescriptor fd = fieldDescriptors.get(i);
            if (fd.getContainingOneof() != null) {
                builder.withField(buildOneofMember(messageType, fd, oneofState, enforceUtf8, null));
            }
            else if (fd.isMapField()) {
                builder.withField(FieldInfo.forMapField(field(messageType, fd), fd.getNumber(), SchemaUtil.getMapDefaultEntry(messageType, fd.getName()), null));
            }
            else if (fd.isRepeated() && fd.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                builder.withField(FieldInfo.forRepeatedMessageField(field(messageType, fd), fd.getNumber(), getFieldType(fd), getTypeForRepeatedMessageField(messageType, fd)));
            }
            else if (fd.isPacked()) {
                builder.withField(FieldInfo.forPackedField(field(messageType, fd), fd.getNumber(), getFieldType(fd), cachedSizeField(messageType, fd)));
            }
            else {
                builder.withField(FieldInfo.forField(field(messageType, fd), fd.getNumber(), getFieldType(fd), enforceUtf8));
            }
        }
        return builder.build();
    }
    
    private static FieldInfo buildOneofMember(final Class<?> messageType, final Descriptors.FieldDescriptor fd, final OneofState oneofState, final boolean enforceUtf8, final Internal.EnumVerifier enumVerifier) {
        final OneofInfo oneof = oneofState.getOneof(messageType, fd.getContainingOneof());
        final FieldType type = getFieldType(fd);
        final Class<?> oneofStoredType = getOneofStoredType(messageType, fd, type);
        return FieldInfo.forOneofMemberField(fd.getNumber(), type, oneof, oneofStoredType, enforceUtf8, enumVerifier);
    }
    
    private static Class<?> getOneofStoredType(final Class<?> messageType, final Descriptors.FieldDescriptor fd, final FieldType type) {
        switch (type.getJavaType()) {
            case BOOLEAN: {
                return Boolean.class;
            }
            case BYTE_STRING: {
                return ByteString.class;
            }
            case DOUBLE: {
                return Double.class;
            }
            case FLOAT: {
                return Float.class;
            }
            case ENUM:
            case INT: {
                return Integer.class;
            }
            case LONG: {
                return Long.class;
            }
            case STRING: {
                return String.class;
            }
            case MESSAGE: {
                return getOneofStoredTypeForMessage(messageType, fd);
            }
            default: {
                throw new IllegalArgumentException("Invalid type for oneof: " + type);
            }
        }
    }
    
    private static FieldType getFieldType(final Descriptors.FieldDescriptor fd) {
        switch (fd.getType()) {
            case BOOL: {
                if (!fd.isRepeated()) {
                    return FieldType.BOOL;
                }
                return fd.isPacked() ? FieldType.BOOL_LIST_PACKED : FieldType.BOOL_LIST;
            }
            case BYTES: {
                return fd.isRepeated() ? FieldType.BYTES_LIST : FieldType.BYTES;
            }
            case DOUBLE: {
                if (!fd.isRepeated()) {
                    return FieldType.DOUBLE;
                }
                return fd.isPacked() ? FieldType.DOUBLE_LIST_PACKED : FieldType.DOUBLE_LIST;
            }
            case ENUM: {
                if (!fd.isRepeated()) {
                    return FieldType.ENUM;
                }
                return fd.isPacked() ? FieldType.ENUM_LIST_PACKED : FieldType.ENUM_LIST;
            }
            case FIXED32: {
                if (!fd.isRepeated()) {
                    return FieldType.FIXED32;
                }
                return fd.isPacked() ? FieldType.FIXED32_LIST_PACKED : FieldType.FIXED32_LIST;
            }
            case FIXED64: {
                if (!fd.isRepeated()) {
                    return FieldType.FIXED64;
                }
                return fd.isPacked() ? FieldType.FIXED64_LIST_PACKED : FieldType.FIXED64_LIST;
            }
            case FLOAT: {
                if (!fd.isRepeated()) {
                    return FieldType.FLOAT;
                }
                return fd.isPacked() ? FieldType.FLOAT_LIST_PACKED : FieldType.FLOAT_LIST;
            }
            case GROUP: {
                return fd.isRepeated() ? FieldType.GROUP_LIST : FieldType.GROUP;
            }
            case INT32: {
                if (!fd.isRepeated()) {
                    return FieldType.INT32;
                }
                return fd.isPacked() ? FieldType.INT32_LIST_PACKED : FieldType.INT32_LIST;
            }
            case INT64: {
                if (!fd.isRepeated()) {
                    return FieldType.INT64;
                }
                return fd.isPacked() ? FieldType.INT64_LIST_PACKED : FieldType.INT64_LIST;
            }
            case MESSAGE: {
                if (fd.isMapField()) {
                    return FieldType.MAP;
                }
                return fd.isRepeated() ? FieldType.MESSAGE_LIST : FieldType.MESSAGE;
            }
            case SFIXED32: {
                if (!fd.isRepeated()) {
                    return FieldType.SFIXED32;
                }
                return fd.isPacked() ? FieldType.SFIXED32_LIST_PACKED : FieldType.SFIXED32_LIST;
            }
            case SFIXED64: {
                if (!fd.isRepeated()) {
                    return FieldType.SFIXED64;
                }
                return fd.isPacked() ? FieldType.SFIXED64_LIST_PACKED : FieldType.SFIXED64_LIST;
            }
            case SINT32: {
                if (!fd.isRepeated()) {
                    return FieldType.SINT32;
                }
                return fd.isPacked() ? FieldType.SINT32_LIST_PACKED : FieldType.SINT32_LIST;
            }
            case SINT64: {
                if (!fd.isRepeated()) {
                    return FieldType.SINT64;
                }
                return fd.isPacked() ? FieldType.SINT64_LIST_PACKED : FieldType.SINT64_LIST;
            }
            case STRING: {
                return fd.isRepeated() ? FieldType.STRING_LIST : FieldType.STRING;
            }
            case UINT32: {
                if (!fd.isRepeated()) {
                    return FieldType.UINT32;
                }
                return fd.isPacked() ? FieldType.UINT32_LIST_PACKED : FieldType.UINT32_LIST;
            }
            case UINT64: {
                if (!fd.isRepeated()) {
                    return FieldType.UINT64;
                }
                return fd.isPacked() ? FieldType.UINT64_LIST_PACKED : FieldType.UINT64_LIST;
            }
            default: {
                throw new IllegalArgumentException("Unsupported field type: " + fd.getType());
            }
        }
    }
    
    private static Field bitField(final Class<?> messageType, final int index) {
        return field(messageType, "bitField" + index + "_");
    }
    
    private static Field field(final Class<?> messageType, final Descriptors.FieldDescriptor fd) {
        return field(messageType, getFieldName(fd));
    }
    
    private static Field cachedSizeField(final Class<?> messageType, final Descriptors.FieldDescriptor fd) {
        return field(messageType, getCachedSizeFieldName(fd));
    }
    
    private static Field field(final Class<?> messageType, final String fieldName) {
        try {
            return messageType.getDeclaredField(fieldName);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to find field " + fieldName + " in message class " + messageType.getName());
        }
    }
    
    static String getFieldName(final Descriptors.FieldDescriptor fd) {
        final String name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
        final String suffix = DescriptorMessageInfoFactory.specialFieldNames.contains(name) ? "__" : "_";
        return snakeCaseToCamelCase(name) + suffix;
    }
    
    private static String getCachedSizeFieldName(final Descriptors.FieldDescriptor fd) {
        return snakeCaseToCamelCase(fd.getName()) + "MemoizedSerializedSize";
    }
    
    private static String snakeCaseToCamelCase(final String snakeCase) {
        final StringBuilder sb = new StringBuilder(snakeCase.length() + 1);
        boolean capNext = false;
        for (int ctr = 0; ctr < snakeCase.length(); ++ctr) {
            final char next = snakeCase.charAt(ctr);
            if (next == '_') {
                capNext = true;
            }
            else if (Character.isDigit(next)) {
                sb.append(next);
                capNext = true;
            }
            else if (capNext) {
                sb.append(Character.toUpperCase(next));
                capNext = false;
            }
            else if (ctr == 0) {
                sb.append(Character.toLowerCase(next));
            }
            else {
                sb.append(next);
            }
        }
        return sb.toString();
    }
    
    private static Class<?> getOneofStoredTypeForMessage(final Class<?> messageType, final Descriptors.FieldDescriptor fd) {
        try {
            final String name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
            final Method getter = messageType.getDeclaredMethod(getterForField(name), (Class<?>[])new Class[0]);
            return getter.getReturnType();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static Class<?> getTypeForRepeatedMessageField(final Class<?> messageType, final Descriptors.FieldDescriptor fd) {
        try {
            final String name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
            final Method getter = messageType.getDeclaredMethod(getterForField(name), Integer.TYPE);
            return getter.getReturnType();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String getterForField(final String snakeCase) {
        final String camelCase = snakeCaseToCamelCase(snakeCase);
        final StringBuilder builder = new StringBuilder("get");
        builder.append(Character.toUpperCase(camelCase.charAt(0)));
        builder.append(camelCase.substring(1, camelCase.length()));
        return builder.toString();
    }
    
    static {
        instance = new DescriptorMessageInfoFactory();
        specialFieldNames = new HashSet<String>(Arrays.asList("cached_size", "serialized_size", "class"));
        DescriptorMessageInfoFactory.isInitializedCheckAnalyzer = new IsInitializedCheckAnalyzer();
    }
    
    static class IsInitializedCheckAnalyzer
    {
        private final Map<Descriptors.Descriptor, Boolean> resultCache;
        private int index;
        private final Stack<Node> stack;
        private final Map<Descriptors.Descriptor, Node> nodeCache;
        
        IsInitializedCheckAnalyzer() {
            this.resultCache = new ConcurrentHashMap<Descriptors.Descriptor, Boolean>();
            this.index = 0;
            this.stack = new Stack<Node>();
            this.nodeCache = new HashMap<Descriptors.Descriptor, Node>();
        }
        
        public boolean needsIsInitializedCheck(final Descriptors.Descriptor descriptor) {
            Boolean cachedValue = this.resultCache.get(descriptor);
            if (cachedValue != null) {
                return cachedValue;
            }
            synchronized (this) {
                cachedValue = this.resultCache.get(descriptor);
                if (cachedValue != null) {
                    return cachedValue;
                }
                return this.dfs(descriptor).component.needsIsInitializedCheck;
            }
        }
        
        private Node dfs(final Descriptors.Descriptor descriptor) {
            final Node result = new Node(descriptor, this.index++);
            this.stack.push(result);
            this.nodeCache.put(descriptor, result);
            for (final Descriptors.FieldDescriptor field : descriptor.getFields()) {
                if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    Node child = this.nodeCache.get(field.getMessageType());
                    if (child == null) {
                        child = this.dfs(field.getMessageType());
                        result.lowLink = Math.min(result.lowLink, child.lowLink);
                    }
                    else {
                        if (child.component != null) {
                            continue;
                        }
                        result.lowLink = Math.min(result.lowLink, child.lowLink);
                    }
                }
            }
            if (result.index == result.lowLink) {
                final StronglyConnectedComponent component = new StronglyConnectedComponent();
                Node node;
                do {
                    node = this.stack.pop();
                    node.component = component;
                    component.messages.add(node.descriptor);
                } while (node != result);
                this.analyze(component);
            }
            return result;
        }
        
        private void analyze(final StronglyConnectedComponent component) {
            boolean needsIsInitializedCheck = false;
        Label_0153:
            for (final Descriptors.Descriptor descriptor : component.messages) {
                if (descriptor.isExtendable()) {
                    needsIsInitializedCheck = true;
                    break;
                }
                for (final Descriptors.FieldDescriptor field : descriptor.getFields()) {
                    if (field.isRequired()) {
                        needsIsInitializedCheck = true;
                        break Label_0153;
                    }
                    if (field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        continue;
                    }
                    final Node node = this.nodeCache.get(field.getMessageType());
                    if (node.component != component && node.component.needsIsInitializedCheck) {
                        needsIsInitializedCheck = true;
                        break Label_0153;
                    }
                }
            }
            component.needsIsInitializedCheck = needsIsInitializedCheck;
            for (final Descriptors.Descriptor descriptor : component.messages) {
                this.resultCache.put(descriptor, component.needsIsInitializedCheck);
            }
        }
        
        private static class Node
        {
            final Descriptors.Descriptor descriptor;
            final int index;
            int lowLink;
            StronglyConnectedComponent component;
            
            Node(final Descriptors.Descriptor descriptor, final int index) {
                this.descriptor = descriptor;
                this.index = index;
                this.lowLink = index;
                this.component = null;
            }
        }
        
        private static class StronglyConnectedComponent
        {
            final List<Descriptors.Descriptor> messages;
            boolean needsIsInitializedCheck;
            
            private StronglyConnectedComponent() {
                this.messages = new ArrayList<Descriptors.Descriptor>();
                this.needsIsInitializedCheck = false;
            }
        }
    }
    
    private static final class OneofState
    {
        private OneofInfo[] oneofs;
        
        private OneofState() {
            this.oneofs = new OneofInfo[2];
        }
        
        OneofInfo getOneof(final Class<?> messageType, final Descriptors.OneofDescriptor desc) {
            final int index = desc.getIndex();
            if (index >= this.oneofs.length) {
                this.oneofs = Arrays.copyOf(this.oneofs, index * 2);
            }
            OneofInfo info = this.oneofs[index];
            if (info == null) {
                info = newInfo(messageType, desc);
                this.oneofs[index] = info;
            }
            return info;
        }
        
        private static OneofInfo newInfo(final Class<?> messageType, final Descriptors.OneofDescriptor desc) {
            final String camelCase = snakeCaseToCamelCase(desc.getName());
            final String valueFieldName = camelCase + "_";
            final String caseFieldName = camelCase + "Case_";
            return new OneofInfo(desc.getIndex(), field(messageType, caseFieldName), field(messageType, valueFieldName));
        }
    }
}
