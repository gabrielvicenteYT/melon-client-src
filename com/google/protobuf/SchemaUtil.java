package com.google.protobuf;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

final class SchemaUtil
{
    private static final Class<?> GENERATED_MESSAGE_CLASS;
    private static final UnknownFieldSchema<?, ?> PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    private static final UnknownFieldSchema<?, ?> PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_LITE_SCHEMA;
    private static final int DEFAULT_LOOK_UP_START_NUMBER = 40;
    
    private SchemaUtil() {
    }
    
    public static void requireGeneratedMessage(final Class<?> messageType) {
        if (!GeneratedMessageLite.class.isAssignableFrom(messageType) && SchemaUtil.GENERATED_MESSAGE_CLASS != null && !SchemaUtil.GENERATED_MESSAGE_CLASS.isAssignableFrom(messageType)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }
    
    public static void writeDouble(final int fieldNumber, final double value, final Writer writer) throws IOException {
        if (Double.compare(value, 0.0) != 0) {
            writer.writeDouble(fieldNumber, value);
        }
    }
    
    public static void writeFloat(final int fieldNumber, final float value, final Writer writer) throws IOException {
        if (Float.compare(value, 0.0f) != 0) {
            writer.writeFloat(fieldNumber, value);
        }
    }
    
    public static void writeInt64(final int fieldNumber, final long value, final Writer writer) throws IOException {
        if (value != 0L) {
            writer.writeInt64(fieldNumber, value);
        }
    }
    
    public static void writeUInt64(final int fieldNumber, final long value, final Writer writer) throws IOException {
        if (value != 0L) {
            writer.writeUInt64(fieldNumber, value);
        }
    }
    
    public static void writeSInt64(final int fieldNumber, final long value, final Writer writer) throws IOException {
        if (value != 0L) {
            writer.writeSInt64(fieldNumber, value);
        }
    }
    
    public static void writeFixed64(final int fieldNumber, final long value, final Writer writer) throws IOException {
        if (value != 0L) {
            writer.writeFixed64(fieldNumber, value);
        }
    }
    
    public static void writeSFixed64(final int fieldNumber, final long value, final Writer writer) throws IOException {
        if (value != 0L) {
            writer.writeSFixed64(fieldNumber, value);
        }
    }
    
    public static void writeInt32(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeInt32(fieldNumber, value);
        }
    }
    
    public static void writeUInt32(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeUInt32(fieldNumber, value);
        }
    }
    
    public static void writeSInt32(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeSInt32(fieldNumber, value);
        }
    }
    
    public static void writeFixed32(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeFixed32(fieldNumber, value);
        }
    }
    
    public static void writeSFixed32(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeSFixed32(fieldNumber, value);
        }
    }
    
    public static void writeEnum(final int fieldNumber, final int value, final Writer writer) throws IOException {
        if (value != 0) {
            writer.writeEnum(fieldNumber, value);
        }
    }
    
    public static void writeBool(final int fieldNumber, final boolean value, final Writer writer) throws IOException {
        if (value) {
            writer.writeBool(fieldNumber, true);
        }
    }
    
    public static void writeString(final int fieldNumber, final Object value, final Writer writer) throws IOException {
        if (value instanceof String) {
            writeStringInternal(fieldNumber, (String)value, writer);
        }
        else {
            writeBytes(fieldNumber, (ByteString)value, writer);
        }
    }
    
    private static void writeStringInternal(final int fieldNumber, final String value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeString(fieldNumber, value);
        }
    }
    
    public static void writeBytes(final int fieldNumber, final ByteString value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeBytes(fieldNumber, value);
        }
    }
    
    public static void writeMessage(final int fieldNumber, final Object value, final Writer writer) throws IOException {
        if (value != null) {
            writer.writeMessage(fieldNumber, value);
        }
    }
    
    public static void writeDoubleList(final int fieldNumber, final List<Double> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeDoubleList(fieldNumber, value, packed);
        }
    }
    
    public static void writeFloatList(final int fieldNumber, final List<Float> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeFloatList(fieldNumber, value, packed);
        }
    }
    
    public static void writeInt64List(final int fieldNumber, final List<Long> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeInt64List(fieldNumber, value, packed);
        }
    }
    
    public static void writeUInt64List(final int fieldNumber, final List<Long> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeUInt64List(fieldNumber, value, packed);
        }
    }
    
    public static void writeSInt64List(final int fieldNumber, final List<Long> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeSInt64List(fieldNumber, value, packed);
        }
    }
    
    public static void writeFixed64List(final int fieldNumber, final List<Long> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeFixed64List(fieldNumber, value, packed);
        }
    }
    
    public static void writeSFixed64List(final int fieldNumber, final List<Long> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeSFixed64List(fieldNumber, value, packed);
        }
    }
    
    public static void writeInt32List(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeInt32List(fieldNumber, value, packed);
        }
    }
    
    public static void writeUInt32List(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeUInt32List(fieldNumber, value, packed);
        }
    }
    
    public static void writeSInt32List(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeSInt32List(fieldNumber, value, packed);
        }
    }
    
    public static void writeFixed32List(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeFixed32List(fieldNumber, value, packed);
        }
    }
    
    public static void writeSFixed32List(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeSFixed32List(fieldNumber, value, packed);
        }
    }
    
    public static void writeEnumList(final int fieldNumber, final List<Integer> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeEnumList(fieldNumber, value, packed);
        }
    }
    
    public static void writeBoolList(final int fieldNumber, final List<Boolean> value, final Writer writer, final boolean packed) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeBoolList(fieldNumber, value, packed);
        }
    }
    
    public static void writeStringList(final int fieldNumber, final List<String> value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeStringList(fieldNumber, value);
        }
    }
    
    public static void writeBytesList(final int fieldNumber, final List<ByteString> value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeBytesList(fieldNumber, value);
        }
    }
    
    public static void writeMessageList(final int fieldNumber, final List<?> value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeMessageList(fieldNumber, value);
        }
    }
    
    public static void writeMessageList(final int fieldNumber, final List<?> value, final Writer writer, final Schema schema) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeMessageList(fieldNumber, value, schema);
        }
    }
    
    public static void writeLazyFieldList(final int fieldNumber, final List<?> value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            for (final Object item : value) {
                ((LazyFieldLite)item).writeTo(writer, fieldNumber);
            }
        }
    }
    
    public static void writeGroupList(final int fieldNumber, final List<?> value, final Writer writer) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeGroupList(fieldNumber, value);
        }
    }
    
    public static void writeGroupList(final int fieldNumber, final List<?> value, final Writer writer, final Schema schema) throws IOException {
        if (value != null && !value.isEmpty()) {
            writer.writeGroupList(fieldNumber, value, schema);
        }
    }
    
    static int computeSizeInt64ListNoTag(final List<Long> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof LongArrayList) {
            final LongArrayList primitiveList = (LongArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeInt64SizeNoTag(primitiveList.getLong(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeInt64SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeInt64List(final int fieldNumber, final List<Long> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeInt64ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + list.size() * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeUInt64ListNoTag(final List<Long> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof LongArrayList) {
            final LongArrayList primitiveList = (LongArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeUInt64SizeNoTag(primitiveList.getLong(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeUInt64SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeUInt64List(final int fieldNumber, final List<Long> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeUInt64ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeSInt64ListNoTag(final List<Long> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof LongArrayList) {
            final LongArrayList primitiveList = (LongArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeSInt64SizeNoTag(primitiveList.getLong(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeSInt64SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeSInt64List(final int fieldNumber, final List<Long> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeSInt64ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeEnumListNoTag(final List<Integer> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof IntArrayList) {
            final IntArrayList primitiveList = (IntArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeEnumSizeNoTag(primitiveList.getInt(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeEnumSizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeEnumList(final int fieldNumber, final List<Integer> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeEnumListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeInt32ListNoTag(final List<Integer> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof IntArrayList) {
            final IntArrayList primitiveList = (IntArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeInt32SizeNoTag(primitiveList.getInt(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeInt32SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeInt32ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeUInt32ListNoTag(final List<Integer> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof IntArrayList) {
            final IntArrayList primitiveList = (IntArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeUInt32SizeNoTag(primitiveList.getInt(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeUInt32SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeUInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeUInt32ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeSInt32ListNoTag(final List<Integer> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        if (list instanceof IntArrayList) {
            final IntArrayList primitiveList = (IntArrayList)list;
            for (int i = 0; i < length; ++i) {
                size += CodedOutputStream.computeSInt32SizeNoTag(primitiveList.getInt(i));
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                size += CodedOutputStream.computeSInt32SizeNoTag(list.get(j));
            }
        }
        return size;
    }
    
    static int computeSizeSInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        final int size = computeSizeSInt32ListNoTag(list);
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size + length * CodedOutputStream.computeTagSize(fieldNumber);
    }
    
    static int computeSizeFixed32ListNoTag(final List<?> list) {
        return list.size() * 4;
    }
    
    static int computeSizeFixed32List(final int fieldNumber, final List<?> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        if (packed) {
            final int dataSize = length * 4;
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(dataSize);
        }
        return length * CodedOutputStream.computeFixed32Size(fieldNumber, 0);
    }
    
    static int computeSizeFixed64ListNoTag(final List<?> list) {
        return list.size() * 8;
    }
    
    static int computeSizeFixed64List(final int fieldNumber, final List<?> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        if (packed) {
            final int dataSize = length * 8;
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(dataSize);
        }
        return length * CodedOutputStream.computeFixed64Size(fieldNumber, 0L);
    }
    
    static int computeSizeBoolListNoTag(final List<?> list) {
        return list.size();
    }
    
    static int computeSizeBoolList(final int fieldNumber, final List<?> list, final boolean packed) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        if (packed) {
            return CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeLengthDelimitedFieldSize(length);
        }
        return length * CodedOutputStream.computeBoolSize(fieldNumber, true);
    }
    
    static int computeSizeStringList(final int fieldNumber, final List<?> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = length * CodedOutputStream.computeTagSize(fieldNumber);
        if (list instanceof LazyStringList) {
            final LazyStringList lazyList = (LazyStringList)list;
            for (int i = 0; i < length; ++i) {
                final Object value = lazyList.getRaw(i);
                if (value instanceof ByteString) {
                    size += CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
                }
                else {
                    size += CodedOutputStream.computeStringSizeNoTag((String)value);
                }
            }
        }
        else {
            for (int j = 0; j < length; ++j) {
                final Object value2 = list.get(j);
                if (value2 instanceof ByteString) {
                    size += CodedOutputStream.computeBytesSizeNoTag((ByteString)value2);
                }
                else {
                    size += CodedOutputStream.computeStringSizeNoTag((String)value2);
                }
            }
        }
        return size;
    }
    
    static int computeSizeMessage(final int fieldNumber, final Object value, final Schema schema) {
        if (value instanceof LazyFieldLite) {
            return CodedOutputStream.computeLazyFieldSize(fieldNumber, (LazyFieldLite)value);
        }
        return CodedOutputStream.computeMessageSize(fieldNumber, (MessageLite)value, schema);
    }
    
    static int computeSizeMessageList(final int fieldNumber, final List<?> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = length * CodedOutputStream.computeTagSize(fieldNumber);
        for (int i = 0; i < length; ++i) {
            final Object value = list.get(i);
            if (value instanceof LazyFieldLite) {
                size += CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite)value);
            }
            else {
                size += CodedOutputStream.computeMessageSizeNoTag((MessageLite)value);
            }
        }
        return size;
    }
    
    static int computeSizeMessageList(final int fieldNumber, final List<?> list, final Schema schema) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = length * CodedOutputStream.computeTagSize(fieldNumber);
        for (int i = 0; i < length; ++i) {
            final Object value = list.get(i);
            if (value instanceof LazyFieldLite) {
                size += CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite)value);
            }
            else {
                size += CodedOutputStream.computeMessageSizeNoTag((MessageLite)value, schema);
            }
        }
        return size;
    }
    
    static int computeSizeByteStringList(final int fieldNumber, final List<ByteString> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = length * CodedOutputStream.computeTagSize(fieldNumber);
        for (int i = 0; i < list.size(); ++i) {
            size += CodedOutputStream.computeBytesSizeNoTag(list.get(i));
        }
        return size;
    }
    
    static int computeSizeGroupList(final int fieldNumber, final List<MessageLite> list) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        for (int i = 0; i < length; ++i) {
            size += CodedOutputStream.computeGroupSize(fieldNumber, list.get(i));
        }
        return size;
    }
    
    static int computeSizeGroupList(final int fieldNumber, final List<MessageLite> list, final Schema schema) {
        final int length = list.size();
        if (length == 0) {
            return 0;
        }
        int size = 0;
        for (int i = 0; i < length; ++i) {
            size += CodedOutputStream.computeGroupSize(fieldNumber, list.get(i), schema);
        }
        return size;
    }
    
    public static boolean shouldUseTableSwitch(final FieldInfo[] fields) {
        if (fields.length == 0) {
            return false;
        }
        final int lo = fields[0].getFieldNumber();
        final int hi = fields[fields.length - 1].getFieldNumber();
        return shouldUseTableSwitch(lo, hi, fields.length);
    }
    
    public static boolean shouldUseTableSwitch(final int lo, final int hi, final int numFields) {
        if (hi < 40) {
            return true;
        }
        final long tableSpaceCost = hi - (long)lo + 1L;
        final long tableTimeCost = 3L;
        final long lookupSpaceCost = 3L + 2L * numFields;
        final long lookupTimeCost = 3L + numFields;
        return tableSpaceCost + 3L * tableTimeCost <= lookupSpaceCost + 3L * lookupTimeCost;
    }
    
    public static UnknownFieldSchema<?, ?> proto2UnknownFieldSetSchema() {
        return SchemaUtil.PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    }
    
    public static UnknownFieldSchema<?, ?> proto3UnknownFieldSetSchema() {
        return SchemaUtil.PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    }
    
    public static UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema() {
        return SchemaUtil.UNKNOWN_FIELD_SET_LITE_SCHEMA;
    }
    
    private static UnknownFieldSchema<?, ?> getUnknownFieldSetSchema(final boolean proto3) {
        try {
            final Class<?> clz = getUnknownFieldSetSchemaClass();
            if (clz == null) {
                return null;
            }
            return (UnknownFieldSchema<?, ?>)clz.getConstructor(Boolean.TYPE).newInstance(proto3);
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    private static Class<?> getGeneratedMessageClass() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessageV3");
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    private static Class<?> getUnknownFieldSetSchemaClass() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    static Object getMapDefaultEntry(final Class<?> clazz, final String name) {
        try {
            final Class<?> holder = Class.forName(clazz.getName() + "$" + toCamelCase(name, true) + "DefaultEntryHolder");
            final Field[] fields = holder.getDeclaredFields();
            if (fields.length != 1) {
                throw new IllegalStateException("Unable to look up map field default entry holder class for " + name + " in " + clazz.getName());
            }
            return UnsafeUtil.getStaticObject(fields[0]);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    static String toCamelCase(final String name, boolean capNext) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            final char c = name.charAt(i);
            if ('a' <= c && c <= 'z') {
                if (capNext) {
                    sb.append((char)(c - 32));
                }
                else {
                    sb.append(c);
                }
                capNext = false;
            }
            else if ('A' <= c && c <= 'Z') {
                if (i == 0 && !capNext) {
                    sb.append((char)(c + 32));
                }
                else {
                    sb.append(c);
                }
                capNext = false;
            }
            else if ('0' <= c && c <= '9') {
                sb.append(c);
                capNext = true;
            }
            else {
                capNext = true;
            }
        }
        return sb.toString();
    }
    
    static boolean safeEquals(final Object a, final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    static <T> void mergeMap(final MapFieldSchema mapFieldSchema, final T message, final T o, final long offset) {
        final Object merged = mapFieldSchema.mergeFrom(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(o, offset));
        UnsafeUtil.putObject(message, offset, merged);
    }
    
    static <T, FT extends FieldSet.FieldDescriptorLite<FT>> void mergeExtensions(final ExtensionSchema<FT> schema, final T message, final T other) {
        final FieldSet<FT> otherExtensions = schema.getExtensions(other);
        if (!otherExtensions.isEmpty()) {
            final FieldSet<FT> messageExtensions = schema.getMutableExtensions(message);
            messageExtensions.mergeFrom(otherExtensions);
        }
    }
    
    static <T, UT, UB> void mergeUnknownFields(final UnknownFieldSchema<UT, UB> schema, final T message, final T other) {
        final UT messageUnknowns = schema.getFromMessage(message);
        final UT otherUnknowns = schema.getFromMessage(other);
        final UT merged = schema.merge(messageUnknowns, otherUnknowns);
        schema.setToMessage(message, merged);
    }
    
    static <UT, UB> UB filterUnknownEnumList(final int number, final List<Integer> enumList, final Internal.EnumLiteMap<?> enumMap, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (enumMap == null) {
            return unknownFields;
        }
        if (enumList instanceof RandomAccess) {
            int writePos = 0;
            final int size = enumList.size();
            for (int readPos = 0; readPos < size; ++readPos) {
                final int enumValue = enumList.get(readPos);
                if (enumMap.findValueByNumber(enumValue) != null) {
                    if (readPos != writePos) {
                        enumList.set(writePos, enumValue);
                    }
                    ++writePos;
                }
                else {
                    unknownFields = storeUnknownEnum(number, enumValue, unknownFields, unknownFieldSchema);
                }
            }
            if (writePos != size) {
                enumList.subList(writePos, size).clear();
            }
        }
        else {
            final Iterator<Integer> it = enumList.iterator();
            while (it.hasNext()) {
                final int enumValue2 = it.next();
                if (enumMap.findValueByNumber(enumValue2) == null) {
                    unknownFields = storeUnknownEnum(number, enumValue2, unknownFields, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return unknownFields;
    }
    
    static <UT, UB> UB filterUnknownEnumList(final int number, final List<Integer> enumList, final Internal.EnumVerifier enumVerifier, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (enumVerifier == null) {
            return unknownFields;
        }
        if (enumList instanceof RandomAccess) {
            int writePos = 0;
            final int size = enumList.size();
            for (int readPos = 0; readPos < size; ++readPos) {
                final int enumValue = enumList.get(readPos);
                if (enumVerifier.isInRange(enumValue)) {
                    if (readPos != writePos) {
                        enumList.set(writePos, enumValue);
                    }
                    ++writePos;
                }
                else {
                    unknownFields = storeUnknownEnum(number, enumValue, unknownFields, unknownFieldSchema);
                }
            }
            if (writePos != size) {
                enumList.subList(writePos, size).clear();
            }
        }
        else {
            final Iterator<Integer> it = enumList.iterator();
            while (it.hasNext()) {
                final int enumValue2 = it.next();
                if (!enumVerifier.isInRange(enumValue2)) {
                    unknownFields = storeUnknownEnum(number, enumValue2, unknownFields, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return unknownFields;
    }
    
    static <UT, UB> UB storeUnknownEnum(final int number, final int enumValue, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (unknownFields == null) {
            unknownFields = unknownFieldSchema.newBuilder();
        }
        unknownFieldSchema.addVarint(unknownFields, number, enumValue);
        return unknownFields;
    }
    
    static {
        GENERATED_MESSAGE_CLASS = getGeneratedMessageClass();
        PROTO2_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(false);
        PROTO3_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(true);
        UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();
    }
}
