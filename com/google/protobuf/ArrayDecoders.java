package com.google.protobuf;

import java.io.*;
import java.util.*;

final class ArrayDecoders
{
    static int decodeVarint32(final byte[] data, int position, final Registers registers) {
        final int value = data[position++];
        if (value >= 0) {
            registers.int1 = value;
            return position;
        }
        return decodeVarint32(value, data, position, registers);
    }
    
    static int decodeVarint32(final int firstByte, final byte[] data, int position, final Registers registers) {
        int value = firstByte & 0x7F;
        final byte b2 = data[position++];
        if (b2 >= 0) {
            registers.int1 = (value | b2 << 7);
            return position;
        }
        value |= (b2 & 0x7F) << 7;
        final byte b3 = data[position++];
        if (b3 >= 0) {
            registers.int1 = (value | b3 << 14);
            return position;
        }
        value |= (b3 & 0x7F) << 14;
        final byte b4 = data[position++];
        if (b4 >= 0) {
            registers.int1 = (value | b4 << 21);
            return position;
        }
        value |= (b4 & 0x7F) << 21;
        final byte b5 = data[position++];
        if (b5 >= 0) {
            registers.int1 = (value | b5 << 28);
            return position;
        }
        value |= (b5 & 0x7F) << 28;
        while (data[position++] < 0) {}
        registers.int1 = value;
        return position;
    }
    
    static int decodeVarint64(final byte[] data, int position, final Registers registers) {
        final long value = data[position++];
        if (value >= 0L) {
            registers.long1 = value;
            return position;
        }
        return decodeVarint64(value, data, position, registers);
    }
    
    static int decodeVarint64(final long firstByte, final byte[] data, int position, final Registers registers) {
        long value;
        byte next;
        int shift;
        for (value = (firstByte & 0x7FL), next = data[position++], shift = 7, value |= (long)(next & 0x7F) << 7; next < 0; next = data[position++], shift += 7, value |= (long)(next & 0x7F) << shift) {}
        registers.long1 = value;
        return position;
    }
    
    static int decodeFixed32(final byte[] data, final int position) {
        return (data[position] & 0xFF) | (data[position + 1] & 0xFF) << 8 | (data[position + 2] & 0xFF) << 16 | (data[position + 3] & 0xFF) << 24;
    }
    
    static long decodeFixed64(final byte[] data, final int position) {
        return ((long)data[position] & 0xFFL) | ((long)data[position + 1] & 0xFFL) << 8 | ((long)data[position + 2] & 0xFFL) << 16 | ((long)data[position + 3] & 0xFFL) << 24 | ((long)data[position + 4] & 0xFFL) << 32 | ((long)data[position + 5] & 0xFFL) << 40 | ((long)data[position + 6] & 0xFFL) << 48 | ((long)data[position + 7] & 0xFFL) << 56;
    }
    
    static double decodeDouble(final byte[] data, final int position) {
        return Double.longBitsToDouble(decodeFixed64(data, position));
    }
    
    static float decodeFloat(final byte[] data, final int position) {
        return Float.intBitsToFloat(decodeFixed32(data, position));
    }
    
    static int decodeString(final byte[] data, int position, final Registers registers) throws InvalidProtocolBufferException {
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length == 0) {
            registers.object1 = "";
            return position;
        }
        registers.object1 = new String(data, position, length, Internal.UTF_8);
        return position + length;
    }
    
    static int decodeStringRequireUtf8(final byte[] data, int position, final Registers registers) throws InvalidProtocolBufferException {
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length == 0) {
            registers.object1 = "";
            return position;
        }
        registers.object1 = Utf8.decodeUtf8(data, position, length);
        return position + length;
    }
    
    static int decodeBytes(final byte[] data, int position, final Registers registers) throws InvalidProtocolBufferException {
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length > data.length - position) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (length == 0) {
            registers.object1 = ByteString.EMPTY;
            return position;
        }
        registers.object1 = ByteString.copyFrom(data, position, length);
        return position + length;
    }
    
    static int decodeMessageField(final Schema schema, final byte[] data, int position, final int limit, final Registers registers) throws IOException {
        int length = data[position++];
        if (length < 0) {
            position = decodeVarint32(length, data, position, registers);
            length = registers.int1;
        }
        if (length < 0 || length > limit - position) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        final Object result = schema.newInstance();
        schema.mergeFrom(result, data, position, position + length, registers);
        schema.makeImmutable(result);
        registers.object1 = result;
        return position + length;
    }
    
    static int decodeGroupField(final Schema schema, final byte[] data, final int position, final int limit, final int endGroup, final Registers registers) throws IOException {
        final MessageSchema messageSchema = (MessageSchema)schema;
        final Object result = messageSchema.newInstance();
        final int endPosition = messageSchema.parseProto2Message(result, data, position, limit, endGroup, registers);
        messageSchema.makeImmutable(result);
        registers.object1 = result;
        return endPosition;
    }
    
    static int decodeVarint32List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final IntArrayList output = (IntArrayList)list;
        position = decodeVarint32(data, position, registers);
        output.addInt(registers.int1);
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint32(data, nextPosition, registers);
            output.addInt(registers.int1);
        }
        return position;
    }
    
    static int decodeVarint64List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final LongArrayList output = (LongArrayList)list;
        position = decodeVarint64(data, position, registers);
        output.addLong(registers.long1);
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint64(data, nextPosition, registers);
            output.addLong(registers.long1);
        }
        return position;
    }
    
    static int decodeFixed32List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final IntArrayList output = (IntArrayList)list;
        output.addInt(decodeFixed32(data, position));
        int nextPosition;
        for (position += 4; position < limit; position = nextPosition + 4) {
            nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addInt(decodeFixed32(data, nextPosition));
        }
        return position;
    }
    
    static int decodeFixed64List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final LongArrayList output = (LongArrayList)list;
        output.addLong(decodeFixed64(data, position));
        int nextPosition;
        for (position += 8; position < limit; position = nextPosition + 8) {
            nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addLong(decodeFixed64(data, nextPosition));
        }
        return position;
    }
    
    static int decodeFloatList(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final FloatArrayList output = (FloatArrayList)list;
        output.addFloat(decodeFloat(data, position));
        int nextPosition;
        for (position += 4; position < limit; position = nextPosition + 4) {
            nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addFloat(decodeFloat(data, nextPosition));
        }
        return position;
    }
    
    static int decodeDoubleList(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final DoubleArrayList output = (DoubleArrayList)list;
        output.addDouble(decodeDouble(data, position));
        int nextPosition;
        for (position += 8; position < limit; position = nextPosition + 8) {
            nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addDouble(decodeDouble(data, nextPosition));
        }
        return position;
    }
    
    static int decodeBoolList(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final BooleanArrayList output = (BooleanArrayList)list;
        position = decodeVarint64(data, position, registers);
        output.addBoolean(registers.long1 != 0L);
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint64(data, nextPosition, registers);
            output.addBoolean(registers.long1 != 0L);
        }
        return position;
    }
    
    static int decodeSInt32List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final IntArrayList output = (IntArrayList)list;
        position = decodeVarint32(data, position, registers);
        output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint32(data, nextPosition, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        return position;
    }
    
    static int decodeSInt64List(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) {
        final LongArrayList output = (LongArrayList)list;
        position = decodeVarint64(data, position, registers);
        output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint64(data, nextPosition, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        return position;
    }
    
    static int decodePackedVarint32List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final IntArrayList output = (IntArrayList)list;
        position = decodeVarint32(data, position, registers);
        final int fieldLimit = position + registers.int1;
        while (position < fieldLimit) {
            position = decodeVarint32(data, position, registers);
            output.addInt(registers.int1);
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedVarint64List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final LongArrayList output = (LongArrayList)list;
        position = decodeVarint32(data, position, registers);
        final int fieldLimit = position + registers.int1;
        while (position < fieldLimit) {
            position = decodeVarint64(data, position, registers);
            output.addLong(registers.long1);
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedFixed32List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final IntArrayList output = (IntArrayList)list;
        int fieldLimit;
        for (position = decodeVarint32(data, position, registers), fieldLimit = position + registers.int1; position < fieldLimit; position += 4) {
            output.addInt(decodeFixed32(data, position));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedFixed64List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final LongArrayList output = (LongArrayList)list;
        int fieldLimit;
        for (position = decodeVarint32(data, position, registers), fieldLimit = position + registers.int1; position < fieldLimit; position += 8) {
            output.addLong(decodeFixed64(data, position));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedFloatList(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final FloatArrayList output = (FloatArrayList)list;
        int fieldLimit;
        for (position = decodeVarint32(data, position, registers), fieldLimit = position + registers.int1; position < fieldLimit; position += 4) {
            output.addFloat(decodeFloat(data, position));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedDoubleList(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final DoubleArrayList output = (DoubleArrayList)list;
        int fieldLimit;
        for (position = decodeVarint32(data, position, registers), fieldLimit = position + registers.int1; position < fieldLimit; position += 8) {
            output.addDouble(decodeDouble(data, position));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedBoolList(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final BooleanArrayList output = (BooleanArrayList)list;
        position = decodeVarint32(data, position, registers);
        final int fieldLimit = position + registers.int1;
        while (position < fieldLimit) {
            position = decodeVarint64(data, position, registers);
            output.addBoolean(registers.long1 != 0L);
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedSInt32List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final IntArrayList output = (IntArrayList)list;
        position = decodeVarint32(data, position, registers);
        final int fieldLimit = position + registers.int1;
        while (position < fieldLimit) {
            position = decodeVarint32(data, position, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodePackedSInt64List(final byte[] data, int position, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final LongArrayList output = (LongArrayList)list;
        position = decodeVarint32(data, position, registers);
        final int fieldLimit = position + registers.int1;
        while (position < fieldLimit) {
            position = decodeVarint64(data, position, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (position != fieldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return position;
    }
    
    static int decodeStringList(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) throws InvalidProtocolBufferException {
        final Internal.ProtobufList<String> output = (Internal.ProtobufList<String>)list;
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length == 0) {
            output.add("");
        }
        else {
            final String value = new String(data, position, length, Internal.UTF_8);
            output.add(value);
            position += length;
        }
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint32(data, nextPosition, registers);
            final int nextLength = registers.int1;
            if (nextLength < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (nextLength == 0) {
                output.add("");
            }
            else {
                final String value2 = new String(data, position, nextLength, Internal.UTF_8);
                output.add(value2);
                position += nextLength;
            }
        }
        return position;
    }
    
    static int decodeStringListRequireUtf8(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) throws InvalidProtocolBufferException {
        final Internal.ProtobufList<String> output = (Internal.ProtobufList<String>)list;
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length == 0) {
            output.add("");
        }
        else {
            if (!Utf8.isValidUtf8(data, position, position + length)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            final String value = new String(data, position, length, Internal.UTF_8);
            output.add(value);
            position += length;
        }
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint32(data, nextPosition, registers);
            final int nextLength = registers.int1;
            if (nextLength < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (nextLength == 0) {
                output.add("");
            }
            else {
                if (!Utf8.isValidUtf8(data, position, position + nextLength)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                final String value2 = new String(data, position, nextLength, Internal.UTF_8);
                output.add(value2);
                position += nextLength;
            }
        }
        return position;
    }
    
    static int decodeBytesList(final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) throws InvalidProtocolBufferException {
        final Internal.ProtobufList<ByteString> output = (Internal.ProtobufList<ByteString>)list;
        position = decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (length > data.length - position) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (length == 0) {
            output.add(ByteString.EMPTY);
        }
        else {
            output.add(ByteString.copyFrom(data, position, length));
            position += length;
        }
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeVarint32(data, nextPosition, registers);
            final int nextLength = registers.int1;
            if (nextLength < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (nextLength > data.length - position) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (nextLength == 0) {
                output.add(ByteString.EMPTY);
            }
            else {
                output.add(ByteString.copyFrom(data, position, nextLength));
                position += nextLength;
            }
        }
        return position;
    }
    
    static int decodeMessageList(final Schema<?> schema, final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final Internal.ProtobufList<Object> output = (Internal.ProtobufList<Object>)list;
        position = decodeMessageField(schema, data, position, limit, registers);
        output.add(registers.object1);
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeMessageField(schema, data, nextPosition, limit, registers);
            output.add(registers.object1);
        }
        return position;
    }
    
    static int decodeGroupList(final Schema schema, final int tag, final byte[] data, int position, final int limit, final Internal.ProtobufList<?> list, final Registers registers) throws IOException {
        final Internal.ProtobufList<Object> output = (Internal.ProtobufList<Object>)list;
        final int endgroup = (tag & 0xFFFFFFF8) | 0x4;
        position = decodeGroupField(schema, data, position, limit, endgroup, registers);
        output.add(registers.object1);
        while (position < limit) {
            final int nextPosition = decodeVarint32(data, position, registers);
            if (tag != registers.int1) {
                break;
            }
            position = decodeGroupField(schema, data, nextPosition, limit, endgroup, registers);
            output.add(registers.object1);
        }
        return position;
    }
    
    static int decodeExtensionOrUnknownField(final int tag, final byte[] data, final int position, final int limit, final Object message, final MessageLite defaultInstance, final UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, final Registers registers) throws IOException {
        final int number = tag >>> 3;
        final GeneratedMessageLite.GeneratedExtension extension = registers.extensionRegistry.findLiteExtensionByNumber(defaultInstance, number);
        if (extension == null) {
            return decodeUnknownField(tag, data, position, limit, MessageSchema.getMutableUnknownFields(message), registers);
        }
        ((GeneratedMessageLite.ExtendableMessage)message).ensureExtensionsAreMutable();
        return decodeExtension(tag, data, position, limit, (GeneratedMessageLite.ExtendableMessage<?, ?>)message, extension, unknownFieldSchema, registers);
    }
    
    static int decodeExtension(final int tag, final byte[] data, int position, final int limit, final GeneratedMessageLite.ExtendableMessage<?, ?> message, final GeneratedMessageLite.GeneratedExtension<?, ?> extension, final UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, final Registers registers) throws IOException {
        final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = message.extensions;
        final int fieldNumber = tag >>> 3;
        if (extension.descriptor.isRepeated() && extension.descriptor.isPacked()) {
            switch (extension.getLiteType()) {
                case DOUBLE: {
                    final DoubleArrayList list = new DoubleArrayList();
                    position = decodePackedDoubleList(data, position, list, registers);
                    extensions.setField(extension.descriptor, list);
                    break;
                }
                case FLOAT: {
                    final FloatArrayList list2 = new FloatArrayList();
                    position = decodePackedFloatList(data, position, list2, registers);
                    extensions.setField(extension.descriptor, list2);
                    break;
                }
                case INT64:
                case UINT64: {
                    final LongArrayList list3 = new LongArrayList();
                    position = decodePackedVarint64List(data, position, list3, registers);
                    extensions.setField(extension.descriptor, list3);
                    break;
                }
                case INT32:
                case UINT32: {
                    final IntArrayList list4 = new IntArrayList();
                    position = decodePackedVarint32List(data, position, list4, registers);
                    extensions.setField(extension.descriptor, list4);
                    break;
                }
                case FIXED64:
                case SFIXED64: {
                    final LongArrayList list3 = new LongArrayList();
                    position = decodePackedFixed64List(data, position, list3, registers);
                    extensions.setField(extension.descriptor, list3);
                    break;
                }
                case FIXED32:
                case SFIXED32: {
                    final IntArrayList list4 = new IntArrayList();
                    position = decodePackedFixed32List(data, position, list4, registers);
                    extensions.setField(extension.descriptor, list4);
                    break;
                }
                case BOOL: {
                    final BooleanArrayList list5 = new BooleanArrayList();
                    position = decodePackedBoolList(data, position, list5, registers);
                    extensions.setField(extension.descriptor, list5);
                    break;
                }
                case SINT32: {
                    final IntArrayList list4 = new IntArrayList();
                    position = decodePackedSInt32List(data, position, list4, registers);
                    extensions.setField(extension.descriptor, list4);
                    break;
                }
                case SINT64: {
                    final LongArrayList list3 = new LongArrayList();
                    position = decodePackedSInt64List(data, position, list3, registers);
                    extensions.setField(extension.descriptor, list3);
                    break;
                }
                case ENUM: {
                    final IntArrayList list4 = new IntArrayList();
                    position = decodePackedVarint32List(data, position, list4, registers);
                    UnknownFieldSetLite unknownFields = message.unknownFields;
                    if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
                        unknownFields = null;
                    }
                    unknownFields = SchemaUtil.filterUnknownEnumList(fieldNumber, list4, extension.descriptor.getEnumType(), unknownFields, unknownFieldSchema);
                    if (unknownFields != null) {
                        message.unknownFields = unknownFields;
                    }
                    extensions.setField(extension.descriptor, list4);
                    break;
                }
                default: {
                    throw new IllegalStateException("Type cannot be packed: " + extension.descriptor.getLiteType());
                }
            }
        }
        else {
            Object value = null;
            if (extension.getLiteType() == WireFormat.FieldType.ENUM) {
                position = decodeVarint32(data, position, registers);
                final Object enumValue = extension.descriptor.getEnumType().findValueByNumber(registers.int1);
                if (enumValue == null) {
                    UnknownFieldSetLite unknownFields2 = message.unknownFields;
                    if (unknownFields2 == UnknownFieldSetLite.getDefaultInstance()) {
                        unknownFields2 = UnknownFieldSetLite.newInstance();
                        message.unknownFields = unknownFields2;
                    }
                    SchemaUtil.storeUnknownEnum(fieldNumber, registers.int1, unknownFields2, unknownFieldSchema);
                    return position;
                }
                value = registers.int1;
            }
            else {
                switch (extension.getLiteType()) {
                    case DOUBLE: {
                        value = decodeDouble(data, position);
                        position += 8;
                        break;
                    }
                    case FLOAT: {
                        value = decodeFloat(data, position);
                        position += 4;
                        break;
                    }
                    case INT64:
                    case UINT64: {
                        position = decodeVarint64(data, position, registers);
                        value = registers.long1;
                        break;
                    }
                    case INT32:
                    case UINT32: {
                        position = decodeVarint32(data, position, registers);
                        value = registers.int1;
                        break;
                    }
                    case FIXED64:
                    case SFIXED64: {
                        value = decodeFixed64(data, position);
                        position += 8;
                        break;
                    }
                    case FIXED32:
                    case SFIXED32: {
                        value = decodeFixed32(data, position);
                        position += 4;
                        break;
                    }
                    case BOOL: {
                        position = decodeVarint64(data, position, registers);
                        value = (registers.long1 != 0L);
                        break;
                    }
                    case BYTES: {
                        position = decodeBytes(data, position, registers);
                        value = registers.object1;
                        break;
                    }
                    case SINT32: {
                        position = decodeVarint32(data, position, registers);
                        value = CodedInputStream.decodeZigZag32(registers.int1);
                        break;
                    }
                    case SINT64: {
                        position = decodeVarint64(data, position, registers);
                        value = CodedInputStream.decodeZigZag64(registers.long1);
                        break;
                    }
                    case STRING: {
                        position = decodeString(data, position, registers);
                        value = registers.object1;
                        break;
                    }
                    case GROUP: {
                        final int endTag = fieldNumber << 3 | 0x4;
                        position = decodeGroupField(Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()), data, position, limit, endTag, registers);
                        value = registers.object1;
                        break;
                    }
                    case MESSAGE: {
                        position = decodeMessageField(Protobuf.getInstance().schemaFor(extension.getMessageDefaultInstance().getClass()), data, position, limit, registers);
                        value = registers.object1;
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
        return position;
    }
    
    static int decodeUnknownField(final int tag, final byte[] data, int position, final int limit, final UnknownFieldSetLite unknownFields, final Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(tag) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                position = decodeVarint64(data, position, registers);
                unknownFields.storeField(tag, registers.long1);
                return position;
            }
            case 5: {
                unknownFields.storeField(tag, decodeFixed32(data, position));
                return position + 4;
            }
            case 1: {
                unknownFields.storeField(tag, decodeFixed64(data, position));
                return position + 8;
            }
            case 2: {
                position = decodeVarint32(data, position, registers);
                final int length = registers.int1;
                if (length < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                if (length > data.length - position) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                if (length == 0) {
                    unknownFields.storeField(tag, ByteString.EMPTY);
                }
                else {
                    unknownFields.storeField(tag, ByteString.copyFrom(data, position, length));
                }
                return position + length;
            }
            case 3: {
                final UnknownFieldSetLite child = UnknownFieldSetLite.newInstance();
                final int endGroup = (tag & 0xFFFFFFF8) | 0x4;
                int lastTag;
                for (lastTag = 0; position < limit; position = decodeUnknownField(lastTag, data, position, limit, child, registers)) {
                    position = decodeVarint32(data, position, registers);
                    lastTag = registers.int1;
                    if (lastTag == endGroup) {
                        break;
                    }
                }
                if (position > limit || lastTag != endGroup) {
                    throw InvalidProtocolBufferException.parseFailure();
                }
                unknownFields.storeField(tag, child);
                return position;
            }
            default: {
                throw InvalidProtocolBufferException.invalidTag();
            }
        }
    }
    
    static int skipField(final int tag, final byte[] data, int position, final int limit, final Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(tag) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                position = decodeVarint64(data, position, registers);
                return position;
            }
            case 5: {
                return position + 4;
            }
            case 1: {
                return position + 8;
            }
            case 2: {
                position = decodeVarint32(data, position, registers);
                return position + registers.int1;
            }
            case 3: {
                final int endGroup = (tag & 0xFFFFFFF8) | 0x4;
                int lastTag;
                for (lastTag = 0; position < limit; position = skipField(lastTag, data, position, limit, registers)) {
                    position = decodeVarint32(data, position, registers);
                    lastTag = registers.int1;
                    if (lastTag == endGroup) {
                        break;
                    }
                }
                if (position > limit || lastTag != endGroup) {
                    throw InvalidProtocolBufferException.parseFailure();
                }
                return position;
            }
            default: {
                throw InvalidProtocolBufferException.invalidTag();
            }
        }
    }
    
    static final class Registers
    {
        public int int1;
        public long long1;
        public Object object1;
        public final ExtensionRegistryLite extensionRegistry;
        
        Registers() {
            this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
        }
        
        Registers(final ExtensionRegistryLite extensionRegistry) {
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            this.extensionRegistry = extensionRegistry;
        }
    }
}
