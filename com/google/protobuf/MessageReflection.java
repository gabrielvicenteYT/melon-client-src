package com.google.protobuf;

import java.io.*;
import java.util.*;

class MessageReflection
{
    static void writeMessageTo(final Message message, Map<Descriptors.FieldDescriptor, Object> fields, final CodedOutputStream output, final boolean alwaysWriteRequiredFields) throws IOException {
        final boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        if (alwaysWriteRequiredFields) {
            fields = new TreeMap<Descriptors.FieldDescriptor, Object>(fields);
            for (final Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
                if (field.isRequired() && !fields.containsKey(field)) {
                    fields.put(field, message.getField(field));
                }
            }
        }
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            final Descriptors.FieldDescriptor field2 = entry.getKey();
            final Object value = entry.getValue();
            if (isMessageSet && field2.isExtension() && field2.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !field2.isRepeated()) {
                output.writeMessageSetExtension(field2.getNumber(), (MessageLite)value);
            }
            else {
                FieldSet.writeField(field2, value, output);
            }
        }
        final UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            unknownFields.writeAsMessageSetTo(output);
        }
        else {
            unknownFields.writeTo(output);
        }
    }
    
    static int getSerializedSize(final Message message, final Map<Descriptors.FieldDescriptor, Object> fields) {
        int size = 0;
        final boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : fields.entrySet()) {
            final Descriptors.FieldDescriptor field = entry.getKey();
            final Object value = entry.getValue();
            if (isMessageSet && field.isExtension() && field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !field.isRepeated()) {
                size += CodedOutputStream.computeMessageSetExtensionSize(field.getNumber(), (MessageLite)value);
            }
            else {
                size += FieldSet.computeFieldSize(field, value);
            }
        }
        final UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            size += unknownFields.getSerializedSizeAsMessageSet();
        }
        else {
            size += unknownFields.getSerializedSize();
        }
        return size;
    }
    
    static String delimitWithCommas(final List<String> parts) {
        final StringBuilder result = new StringBuilder();
        for (final String part : parts) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(part);
        }
        return result.toString();
    }
    
    static boolean isInitialized(final MessageOrBuilder message) {
        for (final Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
            if (field.isRequired() && !message.hasField(field)) {
                return false;
            }
        }
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            final Descriptors.FieldDescriptor field2 = entry.getKey();
            if (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    for (final Message element : entry.getValue()) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                }
                else {
                    if (!entry.getValue().isInitialized()) {
                        return false;
                    }
                    continue;
                }
            }
        }
        return true;
    }
    
    private static String subMessagePrefix(final String prefix, final Descriptors.FieldDescriptor field, final int index) {
        final StringBuilder result = new StringBuilder(prefix);
        if (field.isExtension()) {
            result.append('(').append(field.getFullName()).append(')');
        }
        else {
            result.append(field.getName());
        }
        if (index != -1) {
            result.append('[').append(index).append(']');
        }
        result.append('.');
        return result.toString();
    }
    
    private static void findMissingFields(final MessageOrBuilder message, final String prefix, final List<String> results) {
        for (final Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
            if (field.isRequired() && !message.hasField(field)) {
                results.add(prefix + field.getName());
            }
        }
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            final Descriptors.FieldDescriptor field2 = entry.getKey();
            final Object value = entry.getValue();
            if (field2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    int i = 0;
                    for (final Object element : (List)value) {
                        findMissingFields((MessageOrBuilder)element, subMessagePrefix(prefix, field2, i++), results);
                    }
                }
                else {
                    if (!message.hasField(field2)) {
                        continue;
                    }
                    findMissingFields((MessageOrBuilder)value, subMessagePrefix(prefix, field2, -1), results);
                }
            }
        }
    }
    
    static List<String> findMissingFields(final MessageOrBuilder message) {
        final List<String> results = new ArrayList<String>();
        findMissingFields(message, "", results);
        return results;
    }
    
    static boolean mergeFieldFrom(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final Descriptors.Descriptor type, final MergeTarget target, final int tag) throws IOException {
        if (type.getOptions().getMessageSetWireFormat() && tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
            mergeMessageSetExtensionFromCodedStream(input, unknownFields, extensionRegistry, type, target);
            return true;
        }
        final int wireType = WireFormat.getTagWireType(tag);
        final int fieldNumber = WireFormat.getTagFieldNumber(tag);
        Message defaultInstance = null;
        Descriptors.FieldDescriptor field;
        if (type.isExtensionNumber(fieldNumber)) {
            if (extensionRegistry instanceof ExtensionRegistry) {
                final ExtensionRegistry.ExtensionInfo extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, fieldNumber);
                if (extension == null) {
                    field = null;
                }
                else {
                    field = extension.descriptor;
                    defaultInstance = extension.defaultInstance;
                    if (defaultInstance == null && field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        throw new IllegalStateException("Message-typed extension lacked default instance: " + field.getFullName());
                    }
                }
            }
            else {
                field = null;
            }
        }
        else if (target.getContainerType() == MergeTarget.ContainerType.MESSAGE) {
            field = type.findFieldByNumber(fieldNumber);
        }
        else {
            field = null;
        }
        boolean unknown = false;
        boolean packed = false;
        if (field == null) {
            unknown = true;
        }
        else if (wireType == FieldSet.getWireFormatForFieldType(field.getLiteType(), false)) {
            packed = false;
        }
        else if (field.isPackable() && wireType == FieldSet.getWireFormatForFieldType(field.getLiteType(), true)) {
            packed = true;
        }
        else {
            unknown = true;
        }
        if (!unknown) {
            if (packed) {
                final int length = input.readRawVarint32();
                final int limit = input.pushLimit(length);
                if (field.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        final int rawValue = input.readEnum();
                        if (field.getFile().supportsUnknownEnumValue()) {
                            target.addRepeatedField(field, field.getEnumType().findValueByNumberCreatingIfUnknown(rawValue));
                        }
                        else {
                            final Object value = field.getEnumType().findValueByNumber(rawValue);
                            if (value == null) {
                                if (unknownFields == null) {
                                    continue;
                                }
                                unknownFields.mergeVarintField(fieldNumber, rawValue);
                            }
                            else {
                                target.addRepeatedField(field, value);
                            }
                        }
                    }
                }
                else {
                    while (input.getBytesUntilLimit() > 0) {
                        final Object value2 = WireFormat.readPrimitiveField(input, field.getLiteType(), target.getUtf8Validation(field));
                        target.addRepeatedField(field, value2);
                    }
                }
                input.popLimit(limit);
            }
            else {
                Object value3 = null;
                switch (field.getType()) {
                    case GROUP: {
                        value3 = target.parseGroup(input, extensionRegistry, field, defaultInstance);
                        break;
                    }
                    case MESSAGE: {
                        value3 = target.parseMessage(input, extensionRegistry, field, defaultInstance);
                        break;
                    }
                    case ENUM: {
                        final int rawValue2 = input.readEnum();
                        if (field.getFile().supportsUnknownEnumValue()) {
                            value3 = field.getEnumType().findValueByNumberCreatingIfUnknown(rawValue2);
                            break;
                        }
                        value3 = field.getEnumType().findValueByNumber(rawValue2);
                        if (value3 == null) {
                            if (unknownFields != null) {
                                unknownFields.mergeVarintField(fieldNumber, rawValue2);
                            }
                            return true;
                        }
                        break;
                    }
                    default: {
                        value3 = WireFormat.readPrimitiveField(input, field.getLiteType(), target.getUtf8Validation(field));
                        break;
                    }
                }
                if (field.isRepeated()) {
                    target.addRepeatedField(field, value3);
                }
                else {
                    target.setField(field, value3);
                }
            }
            return true;
        }
        if (unknownFields != null) {
            return unknownFields.mergeFieldFrom(tag, input);
        }
        return input.skipField(tag);
    }
    
    private static void mergeMessageSetExtensionFromCodedStream(final CodedInputStream input, final UnknownFieldSet.Builder unknownFields, final ExtensionRegistryLite extensionRegistry, final Descriptors.Descriptor type, final MergeTarget target) throws IOException {
        int typeId = 0;
        ByteString rawBytes = null;
        ExtensionRegistry.ExtensionInfo extension = null;
        while (true) {
            final int tag = input.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                typeId = input.readUInt32();
                if (typeId == 0 || !(extensionRegistry instanceof ExtensionRegistry)) {
                    continue;
                }
                extension = target.findExtensionByNumber((ExtensionRegistry)extensionRegistry, type, typeId);
            }
            else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (typeId != 0 && extension != null && ExtensionRegistryLite.isEagerlyParseMessageSets()) {
                    eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, target);
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
                mergeMessageSetExtensionFromBytes(rawBytes, extension, extensionRegistry, target);
            }
            else if (rawBytes != null && unknownFields != null) {
                unknownFields.mergeField(typeId, UnknownFieldSet.Field.newBuilder().addLengthDelimited(rawBytes).build());
            }
        }
    }
    
    private static void mergeMessageSetExtensionFromBytes(final ByteString rawBytes, final ExtensionRegistry.ExtensionInfo extension, final ExtensionRegistryLite extensionRegistry, final MergeTarget target) throws IOException {
        final Descriptors.FieldDescriptor field = extension.descriptor;
        final boolean hasOriginalValue = target.hasField(field);
        if (hasOriginalValue || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            final Object value = target.parseMessageFromBytes(rawBytes, extensionRegistry, field, extension.defaultInstance);
            target.setField(field, value);
        }
        else {
            final LazyField lazyField = new LazyField(extension.defaultInstance, extensionRegistry, rawBytes);
            target.setField(field, lazyField);
        }
    }
    
    private static void eagerlyMergeMessageSetExtension(final CodedInputStream input, final ExtensionRegistry.ExtensionInfo extension, final ExtensionRegistryLite extensionRegistry, final MergeTarget target) throws IOException {
        final Descriptors.FieldDescriptor field = extension.descriptor;
        final Object value = target.parseMessage(input, extensionRegistry, field, extension.defaultInstance);
        target.setField(field, value);
    }
    
    static class BuilderAdapter implements MergeTarget
    {
        private final Message.Builder builder;
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return this.builder.getDescriptorForType();
        }
        
        public BuilderAdapter(final Message.Builder builder) {
            this.builder = builder;
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            return this.builder.getField(field);
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            return this.builder.hasField(field);
        }
        
        @Override
        public MergeTarget setField(final Descriptors.FieldDescriptor field, final Object value) {
            this.builder.setField(field, value);
            return this;
        }
        
        @Override
        public MergeTarget clearField(final Descriptors.FieldDescriptor field) {
            this.builder.clearField(field);
            return this;
        }
        
        @Override
        public MergeTarget setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            this.builder.setRepeatedField(field, index, value);
            return this;
        }
        
        @Override
        public MergeTarget addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            this.builder.addRepeatedField(field, value);
            return this;
        }
        
        @Override
        public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
            return this.builder.hasOneof(oneof);
        }
        
        @Override
        public MergeTarget clearOneof(final Descriptors.OneofDescriptor oneof) {
            this.builder.clearOneof(oneof);
            return this;
        }
        
        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
            return this.builder.getOneofFieldDescriptor(oneof);
        }
        
        @Override
        public ContainerType getContainerType() {
            return ContainerType.MESSAGE;
        }
        
        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByName(final ExtensionRegistry registry, final String name) {
            return registry.findImmutableExtensionByName(name);
        }
        
        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(final ExtensionRegistry registry, final Descriptors.Descriptor containingType, final int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }
        
        @Override
        public Object parseGroup(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            }
            else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public Object parseMessage(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            }
            else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readMessage(subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public Object parseMessageFromBytes(final ByteString bytes, final ExtensionRegistryLite extensionRegistry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            Message.Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            }
            else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            subBuilder.mergeFrom(bytes, extensionRegistry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public MergeTarget newMergeTargetForField(final Descriptors.FieldDescriptor field, final Message defaultInstance) {
            Message.Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            }
            else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            return new BuilderAdapter(subBuilder);
        }
        
        @Override
        public MergeTarget newEmptyTargetForField(final Descriptors.FieldDescriptor field, final Message defaultInstance) {
            Message.Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            }
            else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            return new BuilderAdapter(subBuilder);
        }
        
        @Override
        public WireFormat.Utf8Validation getUtf8Validation(final Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            if (!descriptor.isRepeated() && this.builder instanceof GeneratedMessage.Builder) {
                return WireFormat.Utf8Validation.LAZY;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }
        
        @Override
        public Object finish() {
            return this.builder.buildPartial();
        }
    }
    
    static class ExtensionAdapter implements MergeTarget
    {
        private final FieldSet<Descriptors.FieldDescriptor> extensions;
        
        ExtensionAdapter(final FieldSet<Descriptors.FieldDescriptor> extensions) {
            this.extensions = extensions;
        }
        
        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
        }
        
        @Override
        public Object getField(final Descriptors.FieldDescriptor field) {
            return this.extensions.getField(field);
        }
        
        @Override
        public boolean hasField(final Descriptors.FieldDescriptor field) {
            return this.extensions.hasField(field);
        }
        
        @Override
        public MergeTarget setField(final Descriptors.FieldDescriptor field, final Object value) {
            this.extensions.setField(field, value);
            return this;
        }
        
        @Override
        public MergeTarget clearField(final Descriptors.FieldDescriptor field) {
            this.extensions.clearField(field);
            return this;
        }
        
        @Override
        public MergeTarget setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
            this.extensions.setRepeatedField(field, index, value);
            return this;
        }
        
        @Override
        public MergeTarget addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
            this.extensions.addRepeatedField(field, value);
            return this;
        }
        
        @Override
        public boolean hasOneof(final Descriptors.OneofDescriptor oneof) {
            return false;
        }
        
        @Override
        public MergeTarget clearOneof(final Descriptors.OneofDescriptor oneof) {
            return this;
        }
        
        @Override
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor oneof) {
            return null;
        }
        
        @Override
        public ContainerType getContainerType() {
            return ContainerType.EXTENSION_SET;
        }
        
        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByName(final ExtensionRegistry registry, final String name) {
            return registry.findImmutableExtensionByName(name);
        }
        
        @Override
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(final ExtensionRegistry registry, final Descriptors.Descriptor containingType, final int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }
        
        @Override
        public Object parseGroup(final CodedInputStream input, final ExtensionRegistryLite registry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            final Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readGroup(field.getNumber(), subBuilder, registry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public Object parseMessage(final CodedInputStream input, final ExtensionRegistryLite registry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            final Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readMessage(subBuilder, registry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public Object parseMessageFromBytes(final ByteString bytes, final ExtensionRegistryLite registry, final Descriptors.FieldDescriptor field, final Message defaultInstance) throws IOException {
            final Message.Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                final Message originalMessage = (Message)this.getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            subBuilder.mergeFrom(bytes, registry);
            return subBuilder.buildPartial();
        }
        
        @Override
        public MergeTarget newMergeTargetForField(final Descriptors.FieldDescriptor descriptor, final Message defaultInstance) {
            throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
        }
        
        @Override
        public MergeTarget newEmptyTargetForField(final Descriptors.FieldDescriptor descriptor, final Message defaultInstance) {
            throw new UnsupportedOperationException("newEmptyTargetForField() called on FieldSet object");
        }
        
        @Override
        public WireFormat.Utf8Validation getUtf8Validation(final Descriptors.FieldDescriptor descriptor) {
            if (descriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }
        
        @Override
        public Object finish() {
            throw new UnsupportedOperationException("finish() called on FieldSet object");
        }
    }
    
    interface MergeTarget
    {
        Descriptors.Descriptor getDescriptorForType();
        
        ContainerType getContainerType();
        
        ExtensionRegistry.ExtensionInfo findExtensionByName(final ExtensionRegistry p0, final String p1);
        
        ExtensionRegistry.ExtensionInfo findExtensionByNumber(final ExtensionRegistry p0, final Descriptors.Descriptor p1, final int p2);
        
        Object getField(final Descriptors.FieldDescriptor p0);
        
        boolean hasField(final Descriptors.FieldDescriptor p0);
        
        MergeTarget setField(final Descriptors.FieldDescriptor p0, final Object p1);
        
        MergeTarget clearField(final Descriptors.FieldDescriptor p0);
        
        MergeTarget setRepeatedField(final Descriptors.FieldDescriptor p0, final int p1, final Object p2);
        
        MergeTarget addRepeatedField(final Descriptors.FieldDescriptor p0, final Object p1);
        
        boolean hasOneof(final Descriptors.OneofDescriptor p0);
        
        MergeTarget clearOneof(final Descriptors.OneofDescriptor p0);
        
        Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor p0);
        
        Object parseGroup(final CodedInputStream p0, final ExtensionRegistryLite p1, final Descriptors.FieldDescriptor p2, final Message p3) throws IOException;
        
        Object parseMessage(final CodedInputStream p0, final ExtensionRegistryLite p1, final Descriptors.FieldDescriptor p2, final Message p3) throws IOException;
        
        Object parseMessageFromBytes(final ByteString p0, final ExtensionRegistryLite p1, final Descriptors.FieldDescriptor p2, final Message p3) throws IOException;
        
        WireFormat.Utf8Validation getUtf8Validation(final Descriptors.FieldDescriptor p0);
        
        MergeTarget newMergeTargetForField(final Descriptors.FieldDescriptor p0, final Message p1);
        
        MergeTarget newEmptyTargetForField(final Descriptors.FieldDescriptor p0, final Message p1);
        
        Object finish();
        
        public enum ContainerType
        {
            MESSAGE, 
            EXTENSION_SET;
        }
    }
}
