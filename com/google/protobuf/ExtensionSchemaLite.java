package com.google.protobuf;

import java.io.*;
import java.util.*;
import java.nio.*;

final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor>
{
    @Override
    boolean hasExtensions(final MessageLite prototype) {
        return prototype instanceof GeneratedMessageLite.ExtendableMessage;
    }
    
    @Override
    FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(final Object message) {
        return ((GeneratedMessageLite.ExtendableMessage)message).extensions;
    }
    
    @Override
    void setExtensions(final Object message, final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) {
        ((GeneratedMessageLite.ExtendableMessage)message).extensions = extensions;
    }
    
    @Override
    FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(final Object message) {
        return (FieldSet<GeneratedMessageLite.ExtensionDescriptor>)((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
    }
    
    @Override
    void makeImmutable(final Object message) {
        this.getExtensions(message).makeImmutable();
    }
    
    @Override
     <UT, UB> UB parseExtension(final Reader reader, final Object extensionObject, final ExtensionRegistryLite extensionRegistry, final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException {
        final GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
        final int fieldNumber = extension.getNumber();
        if (extension.descriptor.isRepeated() && extension.descriptor.isPacked()) {
            Object value = null;
            switch (extension.getLiteType()) {
                case DOUBLE: {
                    final List<Double> list = new ArrayList<Double>();
                    reader.readDoubleList(list);
                    value = list;
                    break;
                }
                case FLOAT: {
                    final List<Float> list2 = new ArrayList<Float>();
                    reader.readFloatList(list2);
                    value = list2;
                    break;
                }
                case INT64: {
                    final List<Long> list3 = new ArrayList<Long>();
                    reader.readInt64List(list3);
                    value = list3;
                    break;
                }
                case UINT64: {
                    final List<Long> list3 = new ArrayList<Long>();
                    reader.readUInt64List(list3);
                    value = list3;
                    break;
                }
                case INT32: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readInt32List(list4);
                    value = list4;
                    break;
                }
                case FIXED64: {
                    final List<Long> list3 = new ArrayList<Long>();
                    reader.readFixed64List(list3);
                    value = list3;
                    break;
                }
                case FIXED32: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readFixed32List(list4);
                    value = list4;
                    break;
                }
                case BOOL: {
                    final List<Boolean> list5 = new ArrayList<Boolean>();
                    reader.readBoolList(list5);
                    value = list5;
                    break;
                }
                case UINT32: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readUInt32List(list4);
                    value = list4;
                    break;
                }
                case SFIXED32: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readSFixed32List(list4);
                    value = list4;
                    break;
                }
                case SFIXED64: {
                    final List<Long> list3 = new ArrayList<Long>();
                    reader.readSFixed64List(list3);
                    value = list3;
                    break;
                }
                case SINT32: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readSInt32List(list4);
                    value = list4;
                    break;
                }
                case SINT64: {
                    final List<Long> list3 = new ArrayList<Long>();
                    reader.readSInt64List(list3);
                    value = list3;
                    break;
                }
                case ENUM: {
                    final List<Integer> list4 = new ArrayList<Integer>();
                    reader.readEnumList(list4);
                    unknownFields = SchemaUtil.filterUnknownEnumList(fieldNumber, list4, extension.descriptor.getEnumType(), unknownFields, unknownFieldSchema);
                    value = list4;
                    break;
                }
                default: {
                    throw new IllegalStateException("Type cannot be packed: " + extension.descriptor.getLiteType());
                }
            }
            extensions.setField(extension.descriptor, value);
        }
        else {
            Object value = null;
            if (extension.getLiteType() == WireFormat.FieldType.ENUM) {
                final int number = reader.readInt32();
                final Object enumValue = extension.descriptor.getEnumType().findValueByNumber(number);
                if (enumValue == null) {
                    return SchemaUtil.storeUnknownEnum(fieldNumber, number, unknownFields, unknownFieldSchema);
                }
                value = number;
            }
            else {
                switch (extension.getLiteType()) {
                    case DOUBLE: {
                        value = reader.readDouble();
                        break;
                    }
                    case FLOAT: {
                        value = reader.readFloat();
                        break;
                    }
                    case INT64: {
                        value = reader.readInt64();
                        break;
                    }
                    case UINT64: {
                        value = reader.readUInt64();
                        break;
                    }
                    case INT32: {
                        value = reader.readInt32();
                        break;
                    }
                    case FIXED64: {
                        value = reader.readFixed64();
                        break;
                    }
                    case FIXED32: {
                        value = reader.readFixed32();
                        break;
                    }
                    case BOOL: {
                        value = reader.readBool();
                        break;
                    }
                    case BYTES: {
                        value = reader.readBytes();
                        break;
                    }
                    case UINT32: {
                        value = reader.readUInt32();
                        break;
                    }
                    case SFIXED32: {
                        value = reader.readSFixed32();
                        break;
                    }
                    case SFIXED64: {
                        value = reader.readSFixed64();
                        break;
                    }
                    case SINT32: {
                        value = reader.readSInt32();
                        break;
                    }
                    case SINT64: {
                        value = reader.readSInt64();
                        break;
                    }
                    case STRING: {
                        value = reader.readString();
                        break;
                    }
                    case GROUP: {
                        value = reader.readGroup((Class<Object>)extension.getMessageDefaultInstance().getClass(), extensionRegistry);
                        break;
                    }
                    case MESSAGE: {
                        value = reader.readMessage((Class<Object>)extension.getMessageDefaultInstance().getClass(), extensionRegistry);
                        break;
                    }
                    case ENUM: {
                        throw new IllegalStateException("Shouldn't reach here.");
                    }
                }
            }
            if (extension.isRepeated()) {
                extensions.addRepeatedField(extension.descriptor, value);
            }
            else {
                switch (extension.getLiteType()) {
                    case GROUP:
                    case MESSAGE: {
                        final Object oldValue = extensions.getField(extension.descriptor);
                        if (oldValue != null) {
                            value = Internal.mergeMessage(oldValue, value);
                            break;
                        }
                        break;
                    }
                }
                extensions.setField(extension.descriptor, value);
            }
        }
        return unknownFields;
    }
    
    @Override
    int extensionNumber(final Map.Entry<?, ?> extension) {
        final GeneratedMessageLite.ExtensionDescriptor descriptor = (GeneratedMessageLite.ExtensionDescriptor)extension.getKey();
        return descriptor.getNumber();
    }
    
    @Override
    void serializeExtension(final Writer writer, final Map.Entry<?, ?> extension) throws IOException {
        final GeneratedMessageLite.ExtensionDescriptor descriptor = (GeneratedMessageLite.ExtensionDescriptor)extension.getKey();
        if (descriptor.isRepeated()) {
            switch (descriptor.getLiteType()) {
                case DOUBLE: {
                    SchemaUtil.writeDoubleList(descriptor.getNumber(), (List<Double>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case FLOAT: {
                    SchemaUtil.writeFloatList(descriptor.getNumber(), (List<Float>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case INT64: {
                    SchemaUtil.writeInt64List(descriptor.getNumber(), (List<Long>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case UINT64: {
                    SchemaUtil.writeUInt64List(descriptor.getNumber(), (List<Long>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case INT32: {
                    SchemaUtil.writeInt32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case FIXED64: {
                    SchemaUtil.writeFixed64List(descriptor.getNumber(), (List<Long>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case FIXED32: {
                    SchemaUtil.writeFixed32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case BOOL: {
                    SchemaUtil.writeBoolList(descriptor.getNumber(), (List<Boolean>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case BYTES: {
                    SchemaUtil.writeBytesList(descriptor.getNumber(), (List<ByteString>)extension.getValue(), writer);
                    break;
                }
                case UINT32: {
                    SchemaUtil.writeUInt32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case SFIXED32: {
                    SchemaUtil.writeSFixed32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case SFIXED64: {
                    SchemaUtil.writeSFixed64List(descriptor.getNumber(), (List<Long>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case SINT32: {
                    SchemaUtil.writeSInt32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case SINT64: {
                    SchemaUtil.writeSInt64List(descriptor.getNumber(), (List<Long>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case ENUM: {
                    SchemaUtil.writeInt32List(descriptor.getNumber(), (List<Integer>)extension.getValue(), writer, descriptor.isPacked());
                    break;
                }
                case STRING: {
                    SchemaUtil.writeStringList(descriptor.getNumber(), (List<String>)extension.getValue(), writer);
                    break;
                }
                case GROUP: {
                    final List<?> data = (List<?>)extension.getValue();
                    if (data != null && !data.isEmpty()) {
                        SchemaUtil.writeGroupList(descriptor.getNumber(), (List<?>)extension.getValue(), writer, Protobuf.getInstance().schemaFor(data.get(0).getClass()));
                    }
                    break;
                }
                case MESSAGE: {
                    final List<?> data = (List<?>)extension.getValue();
                    if (data != null && !data.isEmpty()) {
                        SchemaUtil.writeMessageList(descriptor.getNumber(), (List<?>)extension.getValue(), writer, Protobuf.getInstance().schemaFor(data.get(0).getClass()));
                        break;
                    }
                    break;
                }
            }
        }
        else {
            switch (descriptor.getLiteType()) {
                case DOUBLE: {
                    writer.writeDouble(descriptor.getNumber(), (double)extension.getValue());
                    break;
                }
                case FLOAT: {
                    writer.writeFloat(descriptor.getNumber(), (float)extension.getValue());
                    break;
                }
                case INT64: {
                    writer.writeInt64(descriptor.getNumber(), (long)extension.getValue());
                    break;
                }
                case UINT64: {
                    writer.writeUInt64(descriptor.getNumber(), (long)extension.getValue());
                    break;
                }
                case INT32: {
                    writer.writeInt32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case FIXED64: {
                    writer.writeFixed64(descriptor.getNumber(), (long)extension.getValue());
                    break;
                }
                case FIXED32: {
                    writer.writeFixed32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case BOOL: {
                    writer.writeBool(descriptor.getNumber(), (boolean)extension.getValue());
                    break;
                }
                case BYTES: {
                    writer.writeBytes(descriptor.getNumber(), (ByteString)extension.getValue());
                    break;
                }
                case UINT32: {
                    writer.writeUInt32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case SFIXED32: {
                    writer.writeSFixed32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case SFIXED64: {
                    writer.writeSFixed64(descriptor.getNumber(), (long)extension.getValue());
                    break;
                }
                case SINT32: {
                    writer.writeSInt32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case SINT64: {
                    writer.writeSInt64(descriptor.getNumber(), (long)extension.getValue());
                    break;
                }
                case ENUM: {
                    writer.writeInt32(descriptor.getNumber(), (int)extension.getValue());
                    break;
                }
                case STRING: {
                    writer.writeString(descriptor.getNumber(), (String)extension.getValue());
                    break;
                }
                case GROUP: {
                    writer.writeGroup(descriptor.getNumber(), extension.getValue(), Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
                    break;
                }
                case MESSAGE: {
                    writer.writeMessage(descriptor.getNumber(), extension.getValue(), Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
                    break;
                }
            }
        }
    }
    
    @Override
    Object findExtensionByNumber(final ExtensionRegistryLite extensionRegistry, final MessageLite defaultInstance, final int number) {
        return extensionRegistry.findLiteExtensionByNumber(defaultInstance, number);
    }
    
    @Override
    void parseLengthPrefixedMessageSetItem(final Reader reader, final Object extensionObject, final ExtensionRegistryLite extensionRegistry, final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
        final GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
        final Object value = reader.readMessage((Class<Object>)extension.getMessageDefaultInstance().getClass(), extensionRegistry);
        extensions.setField(extension.descriptor, value);
    }
    
    @Override
    void parseMessageSetItem(final ByteString data, final Object extensionObject, final ExtensionRegistryLite extensionRegistry, final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
        final GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension<?, ?>)extensionObject;
        final Object value = extension.getMessageDefaultInstance().newBuilderForType().buildPartial();
        final Reader reader = BinaryReader.newInstance(ByteBuffer.wrap(data.toByteArray()), true);
        Protobuf.getInstance().mergeFrom(value, reader, extensionRegistry);
        extensions.setField(extension.descriptor, value);
        if (reader.getFieldNumber() != Integer.MAX_VALUE) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }
}
