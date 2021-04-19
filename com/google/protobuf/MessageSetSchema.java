package com.google.protobuf;

import java.util.*;
import java.io.*;

final class MessageSetSchema<T> implements Schema<T>
{
    private final MessageLite defaultInstance;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean hasExtensions;
    private final ExtensionSchema<?> extensionSchema;
    
    private MessageSetSchema(final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MessageLite defaultInstance) {
        this.unknownFieldSchema = unknownFieldSchema;
        this.hasExtensions = extensionSchema.hasExtensions(defaultInstance);
        this.extensionSchema = extensionSchema;
        this.defaultInstance = defaultInstance;
    }
    
    static <T> MessageSetSchema<T> newSchema(final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MessageLite defaultInstance) {
        return new MessageSetSchema<T>(unknownFieldSchema, extensionSchema, defaultInstance);
    }
    
    @Override
    public T newInstance() {
        return (T)this.defaultInstance.newBuilderForType().buildPartial();
    }
    
    @Override
    public boolean equals(final T message, final T other) {
        final Object messageUnknown = this.unknownFieldSchema.getFromMessage(message);
        final Object otherUnknown = this.unknownFieldSchema.getFromMessage(other);
        if (!messageUnknown.equals(otherUnknown)) {
            return false;
        }
        if (this.hasExtensions) {
            final FieldSet<?> messageExtensions = this.extensionSchema.getExtensions(message);
            final FieldSet<?> otherExtensions = this.extensionSchema.getExtensions(other);
            return messageExtensions.equals(otherExtensions);
        }
        return true;
    }
    
    @Override
    public int hashCode(final T message) {
        int hashCode = this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions) {
            final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            hashCode = hashCode * 53 + extensions.hashCode();
        }
        return hashCode;
    }
    
    @Override
    public void mergeFrom(final T message, final T other) {
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
        }
    }
    
    @Override
    public void writeTo(final T message, final Writer writer) throws IOException {
        final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
        for (final Map.Entry<?, ?> extension : extensions) {
            final FieldSet.FieldDescriptorLite<?> fd = (FieldSet.FieldDescriptorLite<?>)extension.getKey();
            if (fd.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fd.isRepeated() || fd.isPacked()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (extension instanceof LazyField.LazyEntry) {
                writer.writeMessageSetItem(fd.getNumber(), ((LazyField.LazyEntry)extension).getField().toByteString());
            }
            else {
                writer.writeMessageSetItem(fd.getNumber(), extension.getValue());
            }
        }
        this.writeUnknownFieldsHelper(this.unknownFieldSchema, message, writer);
    }
    
    private <UT, UB> void writeUnknownFieldsHelper(final UnknownFieldSchema<UT, UB> unknownFieldSchema, final T message, final Writer writer) throws IOException {
        unknownFieldSchema.writeAsMessageSetTo(unknownFieldSchema.getFromMessage(message), writer);
    }
    
    @Override
    public void mergeFrom(final T message, final byte[] data, int position, final int limit, final ArrayDecoders.Registers registers) throws IOException {
        UnknownFieldSetLite unknownFields = ((GeneratedMessageLite)message).unknownFields;
        if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            unknownFields = UnknownFieldSetLite.newInstance();
            ((GeneratedMessageLite)message).unknownFields = unknownFields;
        }
        final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = (FieldSet<GeneratedMessageLite.ExtensionDescriptor>)((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
        GeneratedMessageLite.GeneratedExtension<?, ?> extension = null;
        while (position < limit) {
            position = ArrayDecoders.decodeVarint32(data, position, registers);
            final int startTag = registers.int1;
            if (startTag != WireFormat.MESSAGE_SET_ITEM_TAG) {
                if (WireFormat.getTagWireType(startTag) == 2) {
                    extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, WireFormat.getTagFieldNumber(startTag));
                    if (extension != null) {
                        position = ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()), data, position, limit, registers);
                        extensions.setField(extension.descriptor, registers.object1);
                    }
                    else {
                        position = ArrayDecoders.decodeUnknownField(startTag, data, position, limit, unknownFields, registers);
                    }
                }
                else {
                    position = ArrayDecoders.skipField(startTag, data, position, limit, registers);
                }
            }
            else {
                int typeId = 0;
                ByteString rawBytes = null;
                while (position < limit) {
                    position = ArrayDecoders.decodeVarint32(data, position, registers);
                    final int tag = registers.int1;
                    final int number = WireFormat.getTagFieldNumber(tag);
                    final int wireType = WireFormat.getTagWireType(tag);
                    switch (number) {
                        case 2: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                typeId = registers.int1;
                                extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, typeId);
                                continue;
                            }
                            break;
                        }
                        case 3: {
                            if (extension != null) {
                                position = ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()), data, position, limit, registers);
                                extensions.setField(extension.descriptor, registers.object1);
                                continue;
                            }
                            if (wireType == 2) {
                                position = ArrayDecoders.decodeBytes(data, position, registers);
                                rawBytes = (ByteString)registers.object1;
                                continue;
                            }
                            break;
                        }
                    }
                    if (tag == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                        break;
                    }
                    position = ArrayDecoders.skipField(tag, data, position, limit, registers);
                }
                if (rawBytes == null) {
                    continue;
                }
                unknownFields.storeField(WireFormat.makeTag(typeId, 2), rawBytes);
            }
        }
        if (position != limit) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }
    
    @Override
    public void mergeFrom(final T message, final Reader reader, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
    }
    
    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(final UnknownFieldSchema<UT, UB> unknownFieldSchema, final ExtensionSchema<ET> extensionSchema, final T message, final Reader reader, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final UB unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
        final FieldSet<ET> extensions = extensionSchema.getMutableExtensions(message);
        try {
            while (true) {
                final int number = reader.getFieldNumber();
                if (number == Integer.MAX_VALUE) {
                    return;
                }
                if (this.parseMessageSetItemOrUnknownField(reader, extensionRegistry, extensionSchema, extensions, unknownFieldSchema, unknownFields)) {
                    continue;
                }
            }
        }
        finally {
            unknownFieldSchema.setBuilderToMessage(message, unknownFields);
        }
    }
    
    @Override
    public void makeImmutable(final T message) {
        this.unknownFieldSchema.makeImmutable(message);
        this.extensionSchema.makeImmutable(message);
    }
    
    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(final Reader reader, final ExtensionRegistryLite extensionRegistry, final ExtensionSchema<ET> extensionSchema, final FieldSet<ET> extensions, final UnknownFieldSchema<UT, UB> unknownFieldSchema, final UB unknownFields) throws IOException {
        final int startTag = reader.getTag();
        if (startTag != WireFormat.MESSAGE_SET_ITEM_TAG) {
            if (WireFormat.getTagWireType(startTag) != 2) {
                return reader.skipField();
            }
            final Object extension = extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, WireFormat.getTagFieldNumber(startTag));
            if (extension != null) {
                extensionSchema.parseLengthPrefixedMessageSetItem(reader, extension, extensionRegistry, extensions);
                return true;
            }
            return unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader);
        }
        else {
            int typeId = 0;
            ByteString rawBytes = null;
            Object extension2 = null;
            while (true) {
                final int number = reader.getFieldNumber();
                if (number == Integer.MAX_VALUE) {
                    break;
                }
                final int tag = reader.getTag();
                if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = reader.readUInt32();
                    extension2 = extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, typeId);
                }
                else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (extension2 != null) {
                        extensionSchema.parseLengthPrefixedMessageSetItem(reader, extension2, extensionRegistry, extensions);
                    }
                    else {
                        rawBytes = reader.readBytes();
                    }
                }
                else {
                    if (!reader.skipField()) {
                        break;
                    }
                    continue;
                }
            }
            if (reader.getTag() != WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
            if (rawBytes != null) {
                if (extension2 != null) {
                    extensionSchema.parseMessageSetItem(rawBytes, extension2, extensionRegistry, extensions);
                }
                else {
                    unknownFieldSchema.addLengthDelimited(unknownFields, typeId, rawBytes);
                }
            }
            return true;
        }
    }
    
    @Override
    public final boolean isInitialized(final T message) {
        final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
        return extensions.isInitialized();
    }
    
    @Override
    public int getSerializedSize(final T message) {
        int size = 0;
        size += this.getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
        if (this.hasExtensions) {
            size += this.extensionSchema.getExtensions(message).getMessageSetSerializedSize();
        }
        return size;
    }
    
    private <UT, UB> int getUnknownFieldsSerializedSize(final UnknownFieldSchema<UT, UB> schema, final T message) {
        final UT unknowns = schema.getFromMessage(message);
        return schema.getSerializedSizeAsMessageSet(unknowns);
    }
}
