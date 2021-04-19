package com.google.protobuf;

import java.io.*;
import java.util.*;
import java.nio.*;

abstract class BinaryWriter extends ByteOutput implements Writer
{
    public static final int DEFAULT_CHUNK_SIZE = 4096;
    private final BufferAllocator alloc;
    private final int chunkSize;
    final ArrayDeque<AllocatedBuffer> buffers;
    int totalDoneBytes;
    private static final int MAP_KEY_NUMBER = 1;
    private static final int MAP_VALUE_NUMBER = 2;
    
    public static BinaryWriter newHeapInstance(final BufferAllocator alloc) {
        return newHeapInstance(alloc, 4096);
    }
    
    public static BinaryWriter newHeapInstance(final BufferAllocator alloc, final int chunkSize) {
        return isUnsafeHeapSupported() ? newUnsafeHeapInstance(alloc, chunkSize) : newSafeHeapInstance(alloc, chunkSize);
    }
    
    public static BinaryWriter newDirectInstance(final BufferAllocator alloc) {
        return newDirectInstance(alloc, 4096);
    }
    
    public static BinaryWriter newDirectInstance(final BufferAllocator alloc, final int chunkSize) {
        return isUnsafeDirectSupported() ? newUnsafeDirectInstance(alloc, chunkSize) : newSafeDirectInstance(alloc, chunkSize);
    }
    
    static boolean isUnsafeHeapSupported() {
        return UnsafeHeapWriter.isSupported();
    }
    
    static boolean isUnsafeDirectSupported() {
        return isSupported();
    }
    
    static BinaryWriter newSafeHeapInstance(final BufferAllocator alloc, final int chunkSize) {
        return new SafeHeapWriter(alloc, chunkSize);
    }
    
    static BinaryWriter newUnsafeHeapInstance(final BufferAllocator alloc, final int chunkSize) {
        if (!isUnsafeHeapSupported()) {
            throw new UnsupportedOperationException("Unsafe operations not supported");
        }
        return new UnsafeHeapWriter(alloc, chunkSize);
    }
    
    static BinaryWriter newSafeDirectInstance(final BufferAllocator alloc, final int chunkSize) {
        return new SafeDirectWriter(alloc, chunkSize);
    }
    
    static BinaryWriter newUnsafeDirectInstance(final BufferAllocator alloc, final int chunkSize) {
        if (!isUnsafeDirectSupported()) {
            throw new UnsupportedOperationException("Unsafe operations not supported");
        }
        return new UnsafeDirectWriter(alloc, chunkSize);
    }
    
    private BinaryWriter(final BufferAllocator alloc, final int chunkSize) {
        this.buffers = new ArrayDeque<AllocatedBuffer>(4);
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize must be > 0");
        }
        this.alloc = Internal.checkNotNull(alloc, "alloc");
        this.chunkSize = chunkSize;
    }
    
    @Override
    public final FieldOrder fieldOrder() {
        return FieldOrder.DESCENDING;
    }
    
    public final Queue<AllocatedBuffer> complete() {
        this.finishCurrentBuffer();
        return this.buffers;
    }
    
    @Override
    public final void writeSFixed32(final int fieldNumber, final int value) throws IOException {
        this.writeFixed32(fieldNumber, value);
    }
    
    @Override
    public final void writeInt64(final int fieldNumber, final long value) throws IOException {
        this.writeUInt64(fieldNumber, value);
    }
    
    @Override
    public final void writeSFixed64(final int fieldNumber, final long value) throws IOException {
        this.writeFixed64(fieldNumber, value);
    }
    
    @Override
    public final void writeFloat(final int fieldNumber, final float value) throws IOException {
        this.writeFixed32(fieldNumber, Float.floatToRawIntBits(value));
    }
    
    @Override
    public final void writeDouble(final int fieldNumber, final double value) throws IOException {
        this.writeFixed64(fieldNumber, Double.doubleToRawLongBits(value));
    }
    
    @Override
    public final void writeEnum(final int fieldNumber, final int value) throws IOException {
        this.writeInt32(fieldNumber, value);
    }
    
    @Override
    public final void writeInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (list instanceof IntArrayList) {
            this.writeInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
        }
        else {
            this.writeInt32List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeInt32List_Internal(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeInt32(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeInt32(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeInt32List_Internal(final int fieldNumber, final IntArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeInt32(list.getInt(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeInt32(fieldNumber, list.getInt(j));
            }
        }
    }
    
    @Override
    public final void writeFixed32List(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (list instanceof IntArrayList) {
            this.writeFixed32List_Internal(fieldNumber, (IntArrayList)list, packed);
        }
        else {
            this.writeFixed32List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeFixed32List_Internal(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 4);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed32(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFixed32(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeFixed32List_Internal(final int fieldNumber, final IntArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 4);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed32(list.getInt(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFixed32(fieldNumber, list.getInt(j));
            }
        }
    }
    
    @Override
    public final void writeInt64List(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        this.writeUInt64List(fieldNumber, list, packed);
    }
    
    @Override
    public final void writeUInt64List(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (list instanceof LongArrayList) {
            this.writeUInt64List_Internal(fieldNumber, (LongArrayList)list, packed);
        }
        else {
            this.writeUInt64List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeUInt64List_Internal(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeVarint64(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeUInt64(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeUInt64List_Internal(final int fieldNumber, final LongArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeVarint64(list.getLong(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeUInt64(fieldNumber, list.getLong(j));
            }
        }
    }
    
    @Override
    public final void writeFixed64List(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (list instanceof LongArrayList) {
            this.writeFixed64List_Internal(fieldNumber, (LongArrayList)list, packed);
        }
        else {
            this.writeFixed64List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeFixed64List_Internal(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 8);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed64(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFixed64(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeFixed64List_Internal(final int fieldNumber, final LongArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 8);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed64(list.getLong(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFixed64(fieldNumber, list.getLong(j));
            }
        }
    }
    
    @Override
    public final void writeFloatList(final int fieldNumber, final List<Float> list, final boolean packed) throws IOException {
        if (list instanceof FloatArrayList) {
            this.writeFloatList_Internal(fieldNumber, (FloatArrayList)list, packed);
        }
        else {
            this.writeFloatList_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeFloatList_Internal(final int fieldNumber, final List<Float> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 4);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed32(Float.floatToRawIntBits(list.get(i)));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFloat(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeFloatList_Internal(final int fieldNumber, final FloatArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 4);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed32(Float.floatToRawIntBits(list.getFloat(i)));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeFloat(fieldNumber, list.getFloat(j));
            }
        }
    }
    
    @Override
    public final void writeDoubleList(final int fieldNumber, final List<Double> list, final boolean packed) throws IOException {
        if (list instanceof DoubleArrayList) {
            this.writeDoubleList_Internal(fieldNumber, (DoubleArrayList)list, packed);
        }
        else {
            this.writeDoubleList_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeDoubleList_Internal(final int fieldNumber, final List<Double> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 8);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed64(Double.doubleToRawLongBits(list.get(i)));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeDouble(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeDoubleList_Internal(final int fieldNumber, final DoubleArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 8);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeFixed64(Double.doubleToRawLongBits(list.getDouble(i)));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeDouble(fieldNumber, list.getDouble(j));
            }
        }
    }
    
    @Override
    public final void writeEnumList(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        this.writeInt32List(fieldNumber, list, packed);
    }
    
    @Override
    public final void writeBoolList(final int fieldNumber, final List<Boolean> list, final boolean packed) throws IOException {
        if (list instanceof BooleanArrayList) {
            this.writeBoolList_Internal(fieldNumber, (BooleanArrayList)list, packed);
        }
        else {
            this.writeBoolList_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeBoolList_Internal(final int fieldNumber, final List<Boolean> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size());
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeBool(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeBool(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeBoolList_Internal(final int fieldNumber, final BooleanArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size());
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeBool(list.getBoolean(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeBool(fieldNumber, list.getBoolean(j));
            }
        }
    }
    
    @Override
    public final void writeStringList(final int fieldNumber, final List<String> list) throws IOException {
        if (list instanceof LazyStringList) {
            final LazyStringList lazyList = (LazyStringList)list;
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeLazyString(fieldNumber, lazyList.getRaw(i));
            }
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeString(fieldNumber, list.get(j));
            }
        }
    }
    
    private void writeLazyString(final int fieldNumber, final Object value) throws IOException {
        if (value instanceof String) {
            this.writeString(fieldNumber, (String)value);
        }
        else {
            this.writeBytes(fieldNumber, (ByteString)value);
        }
    }
    
    @Override
    public final void writeBytesList(final int fieldNumber, final List<ByteString> list) throws IOException {
        for (int i = list.size() - 1; i >= 0; --i) {
            this.writeBytes(fieldNumber, list.get(i));
        }
    }
    
    @Override
    public final void writeUInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (list instanceof IntArrayList) {
            this.writeUInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
        }
        else {
            this.writeUInt32List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeUInt32List_Internal(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 5);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeVarint32(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeUInt32(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeUInt32List_Internal(final int fieldNumber, final IntArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 5);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeVarint32(list.getInt(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeUInt32(fieldNumber, list.getInt(j));
            }
        }
    }
    
    @Override
    public final void writeSFixed32List(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        this.writeFixed32List(fieldNumber, list, packed);
    }
    
    @Override
    public final void writeSFixed64List(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        this.writeFixed64List(fieldNumber, list, packed);
    }
    
    @Override
    public final void writeSInt32List(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (list instanceof IntArrayList) {
            this.writeSInt32List_Internal(fieldNumber, (IntArrayList)list, packed);
        }
        else {
            this.writeSInt32List_Internal(fieldNumber, list, packed);
        }
    }
    
    private final void writeSInt32List_Internal(final int fieldNumber, final List<Integer> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 5);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeSInt32(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeSInt32(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeSInt32List_Internal(final int fieldNumber, final IntArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 5);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeSInt32(list.getInt(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeSInt32(fieldNumber, list.getInt(j));
            }
        }
    }
    
    @Override
    public final void writeSInt64List(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (list instanceof LongArrayList) {
            this.writeSInt64List_Internal(fieldNumber, (LongArrayList)list, packed);
        }
        else {
            this.writeSInt64List_Internal(fieldNumber, list, packed);
        }
    }
    
    @Override
    public <K, V> void writeMap(final int fieldNumber, final MapEntryLite.Metadata<K, V> metadata, final Map<K, V> map) throws IOException {
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            final int prevBytes = this.getTotalBytesWritten();
            writeMapEntryField(this, 2, metadata.valueType, entry.getValue());
            writeMapEntryField(this, 1, metadata.keyType, entry.getKey());
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
    }
    
    static final void writeMapEntryField(final Writer writer, final int fieldNumber, final WireFormat.FieldType fieldType, final Object object) throws IOException {
        switch (fieldType) {
            case BOOL: {
                writer.writeBool(fieldNumber, (boolean)object);
                break;
            }
            case FIXED32: {
                writer.writeFixed32(fieldNumber, (int)object);
                break;
            }
            case FIXED64: {
                writer.writeFixed64(fieldNumber, (long)object);
                break;
            }
            case INT32: {
                writer.writeInt32(fieldNumber, (int)object);
                break;
            }
            case INT64: {
                writer.writeInt64(fieldNumber, (long)object);
                break;
            }
            case SFIXED32: {
                writer.writeSFixed32(fieldNumber, (int)object);
                break;
            }
            case SFIXED64: {
                writer.writeSFixed64(fieldNumber, (long)object);
                break;
            }
            case SINT32: {
                writer.writeSInt32(fieldNumber, (int)object);
                break;
            }
            case SINT64: {
                writer.writeSInt64(fieldNumber, (long)object);
                break;
            }
            case STRING: {
                writer.writeString(fieldNumber, (String)object);
                break;
            }
            case UINT32: {
                writer.writeUInt32(fieldNumber, (int)object);
                break;
            }
            case UINT64: {
                writer.writeUInt64(fieldNumber, (long)object);
                break;
            }
            case FLOAT: {
                writer.writeFloat(fieldNumber, (float)object);
                break;
            }
            case DOUBLE: {
                writer.writeDouble(fieldNumber, (double)object);
                break;
            }
            case MESSAGE: {
                writer.writeMessage(fieldNumber, object);
                break;
            }
            case BYTES: {
                writer.writeBytes(fieldNumber, (ByteString)object);
                break;
            }
            case ENUM: {
                if (object instanceof Internal.EnumLite) {
                    writer.writeEnum(fieldNumber, ((Internal.EnumLite)object).getNumber());
                    break;
                }
                if (object instanceof Integer) {
                    writer.writeEnum(fieldNumber, (int)object);
                    break;
                }
                throw new IllegalArgumentException("Unexpected type for enum in map.");
            }
            default: {
                throw new IllegalArgumentException("Unsupported map value type for: " + fieldType);
            }
        }
    }
    
    private final void writeSInt64List_Internal(final int fieldNumber, final List<Long> list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeSInt64(list.get(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeSInt64(fieldNumber, list.get(j));
            }
        }
    }
    
    private final void writeSInt64List_Internal(final int fieldNumber, final LongArrayList list, final boolean packed) throws IOException {
        if (packed) {
            this.requireSpace(10 + list.size() * 10);
            final int prevBytes = this.getTotalBytesWritten();
            for (int i = list.size() - 1; i >= 0; --i) {
                this.writeSInt64(list.getLong(i));
            }
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        else {
            for (int j = list.size() - 1; j >= 0; --j) {
                this.writeSInt64(fieldNumber, list.getLong(j));
            }
        }
    }
    
    @Override
    public final void writeMessageList(final int fieldNumber, final List<?> list) throws IOException {
        for (int i = list.size() - 1; i >= 0; --i) {
            this.writeMessage(fieldNumber, list.get(i));
        }
    }
    
    @Override
    public final void writeMessageList(final int fieldNumber, final List<?> list, final Schema schema) throws IOException {
        for (int i = list.size() - 1; i >= 0; --i) {
            this.writeMessage(fieldNumber, list.get(i), schema);
        }
    }
    
    @Override
    public final void writeGroupList(final int fieldNumber, final List<?> list) throws IOException {
        for (int i = list.size() - 1; i >= 0; --i) {
            this.writeGroup(fieldNumber, list.get(i));
        }
    }
    
    @Override
    public final void writeGroupList(final int fieldNumber, final List<?> list, final Schema schema) throws IOException {
        for (int i = list.size() - 1; i >= 0; --i) {
            this.writeGroup(fieldNumber, list.get(i), schema);
        }
    }
    
    @Override
    public final void writeMessageSetItem(final int fieldNumber, final Object value) throws IOException {
        this.writeTag(1, 4);
        if (value instanceof ByteString) {
            this.writeBytes(3, (ByteString)value);
        }
        else {
            this.writeMessage(3, value);
        }
        this.writeUInt32(2, fieldNumber);
        this.writeTag(1, 3);
    }
    
    final AllocatedBuffer newHeapBuffer() {
        return this.alloc.allocateHeapBuffer(this.chunkSize);
    }
    
    final AllocatedBuffer newHeapBuffer(final int capacity) {
        return this.alloc.allocateHeapBuffer(Math.max(capacity, this.chunkSize));
    }
    
    final AllocatedBuffer newDirectBuffer() {
        return this.alloc.allocateDirectBuffer(this.chunkSize);
    }
    
    final AllocatedBuffer newDirectBuffer(final int capacity) {
        return this.alloc.allocateDirectBuffer(Math.max(capacity, this.chunkSize));
    }
    
    public abstract int getTotalBytesWritten();
    
    abstract void requireSpace(final int p0);
    
    abstract void finishCurrentBuffer();
    
    abstract void writeTag(final int p0, final int p1);
    
    abstract void writeVarint32(final int p0);
    
    abstract void writeInt32(final int p0);
    
    abstract void writeSInt32(final int p0);
    
    abstract void writeFixed32(final int p0);
    
    abstract void writeVarint64(final long p0);
    
    abstract void writeSInt64(final long p0);
    
    abstract void writeFixed64(final long p0);
    
    abstract void writeBool(final boolean p0);
    
    abstract void writeString(final String p0);
    
    private static byte computeUInt64SizeNoTag(long value) {
        if ((value & 0xFFFFFFFFFFFFFF80L) == 0x0L) {
            return 1;
        }
        if (value < 0L) {
            return 10;
        }
        byte n = 2;
        if ((value & 0xFFFFFFF800000000L) != 0x0L) {
            n += 4;
            value >>>= 28;
        }
        if ((value & 0xFFFFFFFFFFE00000L) != 0x0L) {
            n += 2;
            value >>>= 14;
        }
        if ((value & 0xFFFFFFFFFFFFC000L) != 0x0L) {
            ++n;
        }
        return n;
    }
    
    private static final class SafeHeapWriter extends BinaryWriter
    {
        private AllocatedBuffer allocatedBuffer;
        private byte[] buffer;
        private int offset;
        private int limit;
        private int offsetMinusOne;
        private int limitMinusOne;
        private int pos;
        
        SafeHeapWriter(final BufferAllocator alloc, final int chunkSize) {
            super(alloc, chunkSize, null);
            this.nextBuffer();
        }
        
        @Override
        void finishCurrentBuffer() {
            if (this.allocatedBuffer != null) {
                this.totalDoneBytes += this.bytesWrittenToCurrentBuffer();
                this.allocatedBuffer.position(this.pos - this.allocatedBuffer.arrayOffset() + 1);
                this.allocatedBuffer = null;
                this.pos = 0;
                this.limitMinusOne = 0;
            }
        }
        
        private void nextBuffer() {
            this.nextBuffer(this.newHeapBuffer());
        }
        
        private void nextBuffer(final int capacity) {
            this.nextBuffer(this.newHeapBuffer(capacity));
        }
        
        private void nextBuffer(final AllocatedBuffer allocatedBuffer) {
            if (!allocatedBuffer.hasArray()) {
                throw new RuntimeException("Allocator returned non-heap buffer");
            }
            this.finishCurrentBuffer();
            this.buffers.addFirst(allocatedBuffer);
            this.allocatedBuffer = allocatedBuffer;
            this.buffer = allocatedBuffer.array();
            final int arrayOffset = allocatedBuffer.arrayOffset();
            this.limit = arrayOffset + allocatedBuffer.limit();
            this.offset = arrayOffset + allocatedBuffer.position();
            this.offsetMinusOne = this.offset - 1;
            this.limitMinusOne = this.limit - 1;
            this.pos = this.limitMinusOne;
        }
        
        @Override
        public int getTotalBytesWritten() {
            return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
        }
        
        int bytesWrittenToCurrentBuffer() {
            return this.limitMinusOne - this.pos;
        }
        
        int spaceLeft() {
            return this.pos - this.offsetMinusOne;
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.requireSpace(10);
            this.writeVarint32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.requireSpace(15);
            this.writeInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt32(final int fieldNumber, final int value) throws IOException {
            this.requireSpace(10);
            this.writeSInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.requireSpace(9);
            this.writeFixed32(value);
            this.writeTag(fieldNumber, 5);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.requireSpace(15);
            this.writeVarint64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt64(final int fieldNumber, final long value) throws IOException {
            this.requireSpace(15);
            this.writeSInt64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.requireSpace(13);
            this.writeFixed64(value);
            this.writeTag(fieldNumber, 1);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.requireSpace(6);
            this.write((byte)(value ? 1 : 0));
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            this.writeString(value);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            try {
                value.writeToReverse(this);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.requireSpace(10);
            this.writeVarint32(value.size());
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            Protobuf.getInstance().writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            schema.writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value) throws IOException {
            this.writeTag(fieldNumber, 4);
            Protobuf.getInstance().writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 4);
            schema.writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeStartGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeEndGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 4);
        }
        
        @Override
        void writeInt32(final int value) {
            if (value >= 0) {
                this.writeVarint32(value);
            }
            else {
                this.writeVarint64(value);
            }
        }
        
        @Override
        void writeSInt32(final int value) {
            this.writeVarint32(CodedOutputStream.encodeZigZag32(value));
        }
        
        @Override
        void writeSInt64(final long value) {
            this.writeVarint64(CodedOutputStream.encodeZigZag64(value));
        }
        
        @Override
        void writeBool(final boolean value) {
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        void writeTag(final int fieldNumber, final int wireType) {
            this.writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        void writeVarint32(final int value) {
            if ((value & 0xFFFFFF80) == 0x0) {
                this.writeVarint32OneByte(value);
            }
            else if ((value & 0xFFFFC000) == 0x0) {
                this.writeVarint32TwoBytes(value);
            }
            else if ((value & 0xFFE00000) == 0x0) {
                this.writeVarint32ThreeBytes(value);
            }
            else if ((value & 0xF0000000) == 0x0) {
                this.writeVarint32FourBytes(value);
            }
            else {
                this.writeVarint32FiveBytes(value);
            }
        }
        
        private void writeVarint32OneByte(final int value) {
            this.buffer[this.pos--] = (byte)value;
        }
        
        private void writeVarint32TwoBytes(final int value) {
            this.buffer[this.pos--] = (byte)(value >>> 7);
            this.buffer[this.pos--] = (byte)((value & 0x7F) | 0x80);
        }
        
        private void writeVarint32ThreeBytes(final int value) {
            this.buffer[this.pos--] = (byte)(value >>> 14);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value & 0x7F) | 0x80);
        }
        
        private void writeVarint32FourBytes(final int value) {
            this.buffer[this.pos--] = (byte)(value >>> 21);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value & 0x7F) | 0x80);
        }
        
        private void writeVarint32FiveBytes(final int value) {
            this.buffer[this.pos--] = (byte)(value >>> 28);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7F) | 0x80);
            this.buffer[this.pos--] = (byte)((value & 0x7F) | 0x80);
        }
        
        @Override
        void writeVarint64(final long value) {
            switch (computeUInt64SizeNoTag(value)) {
                case 1: {
                    this.writeVarint64OneByte(value);
                    break;
                }
                case 2: {
                    this.writeVarint64TwoBytes(value);
                    break;
                }
                case 3: {
                    this.writeVarint64ThreeBytes(value);
                    break;
                }
                case 4: {
                    this.writeVarint64FourBytes(value);
                    break;
                }
                case 5: {
                    this.writeVarint64FiveBytes(value);
                    break;
                }
                case 6: {
                    this.writeVarint64SixBytes(value);
                    break;
                }
                case 7: {
                    this.writeVarint64SevenBytes(value);
                    break;
                }
                case 8: {
                    this.writeVarint64EightBytes(value);
                    break;
                }
                case 9: {
                    this.writeVarint64NineBytes(value);
                    break;
                }
                case 10: {
                    this.writeVarint64TenBytes(value);
                    break;
                }
            }
        }
        
        private void writeVarint64OneByte(final long value) {
            this.buffer[this.pos--] = (byte)value;
        }
        
        private void writeVarint64TwoBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 7);
            this.buffer[this.pos--] = (byte)(((int)value & 0x7F) | 0x80);
        }
        
        private void writeVarint64ThreeBytes(final long value) {
            this.buffer[this.pos--] = (byte)((int)value >>> 14);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64FourBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 21);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64FiveBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 28);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64SixBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 35);
            this.buffer[this.pos--] = (byte)((value >>> 28 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64SevenBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 42);
            this.buffer[this.pos--] = (byte)((value >>> 35 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 28 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64EightBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 49);
            this.buffer[this.pos--] = (byte)((value >>> 42 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 35 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 28 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64NineBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 56);
            this.buffer[this.pos--] = (byte)((value >>> 49 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 42 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 35 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 28 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        private void writeVarint64TenBytes(final long value) {
            this.buffer[this.pos--] = (byte)(value >>> 63);
            this.buffer[this.pos--] = (byte)((value >>> 56 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 49 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 42 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 35 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 28 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 21 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 14 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value >>> 7 & 0x7FL) | 0x80L);
            this.buffer[this.pos--] = (byte)((value & 0x7FL) | 0x80L);
        }
        
        @Override
        void writeFixed32(final int value) {
            this.buffer[this.pos--] = (byte)(value >> 24 & 0xFF);
            this.buffer[this.pos--] = (byte)(value >> 16 & 0xFF);
            this.buffer[this.pos--] = (byte)(value >> 8 & 0xFF);
            this.buffer[this.pos--] = (byte)(value & 0xFF);
        }
        
        @Override
        void writeFixed64(final long value) {
            this.buffer[this.pos--] = (byte)((int)(value >> 56) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 48) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 40) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 32) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 24) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 16) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)(value >> 8) & 0xFF);
            this.buffer[this.pos--] = (byte)((int)value & 0xFF);
        }
        
        @Override
        void writeString(final String in) {
            this.requireSpace(in.length());
            int i = in.length() - 1;
            this.pos -= i;
            char c;
            while (i >= 0 && (c = in.charAt(i)) < '\u0080') {
                this.buffer[this.pos + i] = (byte)c;
                --i;
            }
            if (i == -1) {
                --this.pos;
                return;
            }
            this.pos += i;
            while (i >= 0) {
                c = in.charAt(i);
                if (c < '\u0080' && this.pos > this.offsetMinusOne) {
                    this.buffer[this.pos--] = (byte)c;
                }
                else if (c < '\u0800' && this.pos > this.offset) {
                    this.buffer[this.pos--] = (byte)(0x80 | ('?' & c));
                    this.buffer[this.pos--] = (byte)(0x3C0 | c >>> 6);
                }
                else if ((c < '\ud800' || '\udfff' < c) && this.pos > this.offset + 1) {
                    this.buffer[this.pos--] = (byte)(0x80 | ('?' & c));
                    this.buffer[this.pos--] = (byte)(0x80 | (0x3F & c >>> 6));
                    this.buffer[this.pos--] = (byte)(0x1E0 | c >>> 12);
                }
                else if (this.pos > this.offset + 2) {
                    char high = '\0';
                    if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
                        throw new Utf8.UnpairedSurrogateException(i - 1, i);
                    }
                    --i;
                    final int codePoint = Character.toCodePoint(high, c);
                    this.buffer[this.pos--] = (byte)(0x80 | (0x3F & codePoint));
                    this.buffer[this.pos--] = (byte)(0x80 | (0x3F & codePoint >>> 6));
                    this.buffer[this.pos--] = (byte)(0x80 | (0x3F & codePoint >>> 12));
                    this.buffer[this.pos--] = (byte)(0xF0 | codePoint >>> 18);
                }
                else {
                    this.requireSpace(i);
                    ++i;
                }
                --i;
            }
        }
        
        @Override
        public void write(final byte value) {
            this.buffer[this.pos--] = value;
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            System.arraycopy(value, offset, this.buffer, this.pos + 1, length);
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            System.arraycopy(value, offset, this.buffer, this.pos + 1, length);
        }
        
        @Override
        public void write(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            value.get(this.buffer, this.pos + 1, length);
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value));
                this.nextBuffer();
            }
            this.pos -= length;
            value.get(this.buffer, this.pos + 1, length);
        }
        
        @Override
        void requireSpace(final int size) {
            if (this.spaceLeft() < size) {
                this.nextBuffer(size);
            }
        }
    }
    
    private static final class UnsafeHeapWriter extends BinaryWriter
    {
        private AllocatedBuffer allocatedBuffer;
        private byte[] buffer;
        private long offset;
        private long limit;
        private long offsetMinusOne;
        private long limitMinusOne;
        private long pos;
        
        UnsafeHeapWriter(final BufferAllocator alloc, final int chunkSize) {
            super(alloc, chunkSize, null);
            this.nextBuffer();
        }
        
        static boolean isSupported() {
            return UnsafeUtil.hasUnsafeArrayOperations();
        }
        
        @Override
        void finishCurrentBuffer() {
            if (this.allocatedBuffer != null) {
                this.totalDoneBytes += this.bytesWrittenToCurrentBuffer();
                this.allocatedBuffer.position(this.arrayPos() - this.allocatedBuffer.arrayOffset() + 1);
                this.allocatedBuffer = null;
                this.pos = 0L;
                this.limitMinusOne = 0L;
            }
        }
        
        private int arrayPos() {
            return (int)this.pos;
        }
        
        private void nextBuffer() {
            this.nextBuffer(this.newHeapBuffer());
        }
        
        private void nextBuffer(final int capacity) {
            this.nextBuffer(this.newHeapBuffer(capacity));
        }
        
        private void nextBuffer(final AllocatedBuffer allocatedBuffer) {
            if (!allocatedBuffer.hasArray()) {
                throw new RuntimeException("Allocator returned non-heap buffer");
            }
            this.finishCurrentBuffer();
            this.buffers.addFirst(allocatedBuffer);
            this.allocatedBuffer = allocatedBuffer;
            this.buffer = allocatedBuffer.array();
            final int arrayOffset = allocatedBuffer.arrayOffset();
            this.limit = arrayOffset + allocatedBuffer.limit();
            this.offset = arrayOffset + allocatedBuffer.position();
            this.offsetMinusOne = this.offset - 1L;
            this.limitMinusOne = this.limit - 1L;
            this.pos = this.limitMinusOne;
        }
        
        @Override
        public int getTotalBytesWritten() {
            return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
        }
        
        int bytesWrittenToCurrentBuffer() {
            return (int)(this.limitMinusOne - this.pos);
        }
        
        int spaceLeft() {
            return (int)(this.pos - this.offsetMinusOne);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeVarint32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) {
            this.requireSpace(15);
            this.writeInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeSInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) {
            this.requireSpace(9);
            this.writeFixed32(value);
            this.writeTag(fieldNumber, 5);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeVarint64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeSInt64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) {
            this.requireSpace(13);
            this.writeFixed64(value);
            this.writeTag(fieldNumber, 1);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) {
            this.requireSpace(6);
            this.write((byte)(value ? 1 : 0));
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) {
            final int prevBytes = this.getTotalBytesWritten();
            this.writeString(value);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) {
            try {
                value.writeToReverse(this);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.requireSpace(10);
            this.writeVarint32(value.size());
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            Protobuf.getInstance().writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            schema.writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value) throws IOException {
            this.writeTag(fieldNumber, 4);
            Protobuf.getInstance().writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 4);
            schema.writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeStartGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeEndGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 4);
        }
        
        @Override
        void writeInt32(final int value) {
            if (value >= 0) {
                this.writeVarint32(value);
            }
            else {
                this.writeVarint64(value);
            }
        }
        
        @Override
        void writeSInt32(final int value) {
            this.writeVarint32(CodedOutputStream.encodeZigZag32(value));
        }
        
        @Override
        void writeSInt64(final long value) {
            this.writeVarint64(CodedOutputStream.encodeZigZag64(value));
        }
        
        @Override
        void writeBool(final boolean value) {
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        void writeTag(final int fieldNumber, final int wireType) {
            this.writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        void writeVarint32(final int value) {
            if ((value & 0xFFFFFF80) == 0x0) {
                this.writeVarint32OneByte(value);
            }
            else if ((value & 0xFFFFC000) == 0x0) {
                this.writeVarint32TwoBytes(value);
            }
            else if ((value & 0xFFE00000) == 0x0) {
                this.writeVarint32ThreeBytes(value);
            }
            else if ((value & 0xF0000000) == 0x0) {
                this.writeVarint32FourBytes(value);
            }
            else {
                this.writeVarint32FiveBytes(value);
            }
        }
        
        private void writeVarint32OneByte(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)value);
        }
        
        private void writeVarint32TwoBytes(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32ThreeBytes(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 14));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32FourBytes(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 21));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32FiveBytes(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 28));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        @Override
        void writeVarint64(final long value) {
            switch (computeUInt64SizeNoTag(value)) {
                case 1: {
                    this.writeVarint64OneByte(value);
                    break;
                }
                case 2: {
                    this.writeVarint64TwoBytes(value);
                    break;
                }
                case 3: {
                    this.writeVarint64ThreeBytes(value);
                    break;
                }
                case 4: {
                    this.writeVarint64FourBytes(value);
                    break;
                }
                case 5: {
                    this.writeVarint64FiveBytes(value);
                    break;
                }
                case 6: {
                    this.writeVarint64SixBytes(value);
                    break;
                }
                case 7: {
                    this.writeVarint64SevenBytes(value);
                    break;
                }
                case 8: {
                    this.writeVarint64EightBytes(value);
                    break;
                }
                case 9: {
                    this.writeVarint64NineBytes(value);
                    break;
                }
                case 10: {
                    this.writeVarint64TenBytes(value);
                    break;
                }
            }
        }
        
        private void writeVarint64OneByte(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)value);
        }
        
        private void writeVarint64TwoBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 7));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(((int)value & 0x7F) | 0x80));
        }
        
        private void writeVarint64ThreeBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)value >>> 14));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64FourBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 21));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64FiveBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 28));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64SixBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 35));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64SevenBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 42));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64EightBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 49));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64NineBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 56));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 49 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64TenBytes(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >>> 63));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 56 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 49 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        @Override
        void writeFixed32(final int value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 24 & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 16 & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value >> 8 & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(value & 0xFF));
        }
        
        @Override
        void writeFixed64(final long value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 56) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 48) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 40) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 32) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 24) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 16) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)(value >> 8) & 0xFF));
            UnsafeUtil.putByte(this.buffer, this.pos--, (byte)((int)value & 0xFF));
        }
        
        @Override
        void writeString(final String in) {
            this.requireSpace(in.length());
            int i;
            char c;
            for (i = in.length() - 1; i >= 0 && (c = in.charAt(i)) < '\u0080'; --i) {
                UnsafeUtil.putByte(this.buffer, this.pos--, (byte)c);
            }
            if (i == -1) {
                return;
            }
            while (i >= 0) {
                c = in.charAt(i);
                if (c < '\u0080' && this.pos > this.offsetMinusOne) {
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)c);
                }
                else if (c < '\u0800' && this.pos > this.offset) {
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | ('?' & c)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x3C0 | c >>> 6));
                }
                else if ((c < '\ud800' || '\udfff' < c) && this.pos > this.offset + 1L) {
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | ('?' & c)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | (0x3F & c >>> 6)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x1E0 | c >>> 12));
                }
                else if (this.pos > this.offset + 2L) {
                    final char high;
                    if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
                        throw new Utf8.UnpairedSurrogateException(i - 1, i);
                    }
                    --i;
                    final int codePoint = Character.toCodePoint(high, c);
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | (0x3F & codePoint)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                    UnsafeUtil.putByte(this.buffer, this.pos--, (byte)(0xF0 | codePoint >>> 18));
                }
                else {
                    this.requireSpace(i);
                    ++i;
                }
                --i;
            }
        }
        
        @Override
        public void write(final byte value) {
            UnsafeUtil.putByte(this.buffer, this.pos--, value);
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) {
            if (offset < 0 || offset + length > value.length) {
                throw new ArrayIndexOutOfBoundsException(String.format("value.length=%d, offset=%d, length=%d", value.length, offset, length));
            }
            this.requireSpace(length);
            this.pos -= length;
            System.arraycopy(value, offset, this.buffer, this.arrayPos() + 1, length);
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) {
            if (offset < 0 || offset + length > value.length) {
                throw new ArrayIndexOutOfBoundsException(String.format("value.length=%d, offset=%d, length=%d", value.length, offset, length));
            }
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            System.arraycopy(value, offset, this.buffer, this.arrayPos() + 1, length);
        }
        
        @Override
        public void write(final ByteBuffer value) {
            final int length = value.remaining();
            this.requireSpace(length);
            this.pos -= length;
            value.get(this.buffer, this.arrayPos() + 1, length);
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value));
                this.nextBuffer();
            }
            this.pos -= length;
            value.get(this.buffer, this.arrayPos() + 1, length);
        }
        
        @Override
        void requireSpace(final int size) {
            if (this.spaceLeft() < size) {
                this.nextBuffer(size);
            }
        }
    }
    
    private static final class SafeDirectWriter extends BinaryWriter
    {
        private ByteBuffer buffer;
        private int limitMinusOne;
        private int pos;
        
        SafeDirectWriter(final BufferAllocator alloc, final int chunkSize) {
            super(alloc, chunkSize, null);
            this.nextBuffer();
        }
        
        private void nextBuffer() {
            this.nextBuffer(this.newDirectBuffer());
        }
        
        private void nextBuffer(final int capacity) {
            this.nextBuffer(this.newDirectBuffer(capacity));
        }
        
        private void nextBuffer(final AllocatedBuffer allocatedBuffer) {
            if (!allocatedBuffer.hasNioBuffer()) {
                throw new RuntimeException("Allocated buffer does not have NIO buffer");
            }
            final ByteBuffer nioBuffer = allocatedBuffer.nioBuffer();
            if (!nioBuffer.isDirect()) {
                throw new RuntimeException("Allocator returned non-direct buffer");
            }
            this.finishCurrentBuffer();
            this.buffers.addFirst(allocatedBuffer);
            (this.buffer = nioBuffer).limit(this.buffer.capacity());
            this.buffer.position(0);
            this.buffer.order(ByteOrder.LITTLE_ENDIAN);
            this.limitMinusOne = this.buffer.limit() - 1;
            this.pos = this.limitMinusOne;
        }
        
        @Override
        public int getTotalBytesWritten() {
            return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
        }
        
        private int bytesWrittenToCurrentBuffer() {
            return this.limitMinusOne - this.pos;
        }
        
        private int spaceLeft() {
            return this.pos + 1;
        }
        
        @Override
        void finishCurrentBuffer() {
            if (this.buffer != null) {
                this.totalDoneBytes += this.bytesWrittenToCurrentBuffer();
                this.buffer.position(this.pos + 1);
                this.buffer = null;
                this.pos = 0;
                this.limitMinusOne = 0;
            }
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeVarint32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) {
            this.requireSpace(15);
            this.writeInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeSInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) {
            this.requireSpace(9);
            this.writeFixed32(value);
            this.writeTag(fieldNumber, 5);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeVarint64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeSInt64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) {
            this.requireSpace(13);
            this.writeFixed64(value);
            this.writeTag(fieldNumber, 1);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) {
            this.requireSpace(6);
            this.write((byte)(value ? 1 : 0));
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) {
            final int prevBytes = this.getTotalBytesWritten();
            this.writeString(value);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) {
            try {
                value.writeToReverse(this);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.requireSpace(10);
            this.writeVarint32(value.size());
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            Protobuf.getInstance().writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            schema.writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value) throws IOException {
            this.writeTag(fieldNumber, 4);
            Protobuf.getInstance().writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 4);
            schema.writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeStartGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeEndGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 4);
        }
        
        @Override
        void writeInt32(final int value) {
            if (value >= 0) {
                this.writeVarint32(value);
            }
            else {
                this.writeVarint64(value);
            }
        }
        
        @Override
        void writeSInt32(final int value) {
            this.writeVarint32(CodedOutputStream.encodeZigZag32(value));
        }
        
        @Override
        void writeSInt64(final long value) {
            this.writeVarint64(CodedOutputStream.encodeZigZag64(value));
        }
        
        @Override
        void writeBool(final boolean value) {
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        void writeTag(final int fieldNumber, final int wireType) {
            this.writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        void writeVarint32(final int value) {
            if ((value & 0xFFFFFF80) == 0x0) {
                this.writeVarint32OneByte(value);
            }
            else if ((value & 0xFFFFC000) == 0x0) {
                this.writeVarint32TwoBytes(value);
            }
            else if ((value & 0xFFE00000) == 0x0) {
                this.writeVarint32ThreeBytes(value);
            }
            else if ((value & 0xF0000000) == 0x0) {
                this.writeVarint32FourBytes(value);
            }
            else {
                this.writeVarint32FiveBytes(value);
            }
        }
        
        private void writeVarint32OneByte(final int value) {
            this.buffer.put(this.pos--, (byte)value);
        }
        
        private void writeVarint32TwoBytes(final int value) {
            this.pos -= 2;
            this.buffer.putShort(this.pos + 1, (short)((value & 0x3F80) << 1 | ((value & 0x7F) | 0x80)));
        }
        
        private void writeVarint32ThreeBytes(final int value) {
            this.pos -= 3;
            this.buffer.putInt(this.pos, (value & 0x1FC000) << 10 | ((value & 0x3F80) | 0x4000) << 9 | ((value & 0x7F) | 0x80) << 8);
        }
        
        private void writeVarint32FourBytes(final int value) {
            this.pos -= 4;
            this.buffer.putInt(this.pos + 1, (value & 0xFE00000) << 3 | ((value & 0x1FC000) | 0x200000) << 2 | ((value & 0x3F80) | 0x4000) << 1 | ((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32FiveBytes(final int value) {
            this.buffer.put(this.pos--, (byte)(value >>> 28));
            this.pos -= 4;
            this.buffer.putInt(this.pos + 1, ((value >>> 21 & 0x7F) | 0x80) << 24 | ((value >>> 14 & 0x7F) | 0x80) << 16 | ((value >>> 7 & 0x7F) | 0x80) << 8 | ((value & 0x7F) | 0x80));
        }
        
        @Override
        void writeVarint64(final long value) {
            switch (computeUInt64SizeNoTag(value)) {
                case 1: {
                    this.writeVarint64OneByte(value);
                    break;
                }
                case 2: {
                    this.writeVarint64TwoBytes(value);
                    break;
                }
                case 3: {
                    this.writeVarint64ThreeBytes(value);
                    break;
                }
                case 4: {
                    this.writeVarint64FourBytes(value);
                    break;
                }
                case 5: {
                    this.writeVarint64FiveBytes(value);
                    break;
                }
                case 6: {
                    this.writeVarint64SixBytes(value);
                    break;
                }
                case 7: {
                    this.writeVarint64SevenBytes(value);
                    break;
                }
                case 8: {
                    this.writeVarint64EightBytes(value);
                    break;
                }
                case 9: {
                    this.writeVarint64NineBytes(value);
                    break;
                }
                case 10: {
                    this.writeVarint64TenBytes(value);
                    break;
                }
            }
        }
        
        private void writeVarint64OneByte(final long value) {
            this.writeVarint32OneByte((int)value);
        }
        
        private void writeVarint64TwoBytes(final long value) {
            this.writeVarint32TwoBytes((int)value);
        }
        
        private void writeVarint64ThreeBytes(final long value) {
            this.writeVarint32ThreeBytes((int)value);
        }
        
        private void writeVarint64FourBytes(final long value) {
            this.writeVarint32FourBytes((int)value);
        }
        
        private void writeVarint64FiveBytes(final long value) {
            this.pos -= 5;
            this.buffer.putLong(this.pos - 2, (value & 0x7F0000000L) << 28 | ((value & 0xFE00000L) | 0x10000000L) << 27 | ((value & 0x1FC000L) | 0x200000L) << 26 | ((value & 0x3F80L) | 0x4000L) << 25 | ((value & 0x7FL) | 0x80L) << 24);
        }
        
        private void writeVarint64SixBytes(final long value) {
            this.pos -= 6;
            this.buffer.putLong(this.pos - 1, (value & 0x3F800000000L) << 21 | ((value & 0x7F0000000L) | 0x800000000L) << 20 | ((value & 0xFE00000L) | 0x10000000L) << 19 | ((value & 0x1FC000L) | 0x200000L) << 18 | ((value & 0x3F80L) | 0x4000L) << 17 | ((value & 0x7FL) | 0x80L) << 16);
        }
        
        private void writeVarint64SevenBytes(final long value) {
            this.pos -= 7;
            this.buffer.putLong(this.pos, (value & 0x1FC0000000000L) << 14 | ((value & 0x3F800000000L) | 0x40000000000L) << 13 | ((value & 0x7F0000000L) | 0x800000000L) << 12 | ((value & 0xFE00000L) | 0x10000000L) << 11 | ((value & 0x1FC000L) | 0x200000L) << 10 | ((value & 0x3F80L) | 0x4000L) << 9 | ((value & 0x7FL) | 0x80L) << 8);
        }
        
        private void writeVarint64EightBytes(final long value) {
            this.pos -= 8;
            this.buffer.putLong(this.pos + 1, (value & 0xFE000000000000L) << 7 | ((value & 0x1FC0000000000L) | 0x2000000000000L) << 6 | ((value & 0x3F800000000L) | 0x40000000000L) << 5 | ((value & 0x7F0000000L) | 0x800000000L) << 4 | ((value & 0xFE00000L) | 0x10000000L) << 3 | ((value & 0x1FC000L) | 0x200000L) << 2 | ((value & 0x3F80L) | 0x4000L) << 1 | ((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64EightBytesWithSign(final long value) {
            this.pos -= 8;
            this.buffer.putLong(this.pos + 1, ((value & 0xFE000000000000L) | 0x100000000000000L) << 7 | ((value & 0x1FC0000000000L) | 0x2000000000000L) << 6 | ((value & 0x3F800000000L) | 0x40000000000L) << 5 | ((value & 0x7F0000000L) | 0x800000000L) << 4 | ((value & 0xFE00000L) | 0x10000000L) << 3 | ((value & 0x1FC000L) | 0x200000L) << 2 | ((value & 0x3F80L) | 0x4000L) << 1 | ((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64NineBytes(final long value) {
            this.buffer.put(this.pos--, (byte)(value >>> 56));
            this.writeVarint64EightBytesWithSign(value & 0xFFFFFFFFFFFFFFL);
        }
        
        private void writeVarint64TenBytes(final long value) {
            this.buffer.put(this.pos--, (byte)(value >>> 63));
            this.buffer.put(this.pos--, (byte)((value >>> 56 & 0x7FL) | 0x80L));
            this.writeVarint64EightBytesWithSign(value & 0xFFFFFFFFFFFFFFL);
        }
        
        @Override
        void writeFixed32(final int value) {
            this.pos -= 4;
            this.buffer.putInt(this.pos + 1, value);
        }
        
        @Override
        void writeFixed64(final long value) {
            this.pos -= 8;
            this.buffer.putLong(this.pos + 1, value);
        }
        
        @Override
        void writeString(final String in) {
            this.requireSpace(in.length());
            int i = in.length() - 1;
            this.pos -= i;
            char c;
            while (i >= 0 && (c = in.charAt(i)) < '\u0080') {
                this.buffer.put(this.pos + i, (byte)c);
                --i;
            }
            if (i == -1) {
                --this.pos;
                return;
            }
            this.pos += i;
            while (i >= 0) {
                c = in.charAt(i);
                if (c < '\u0080' && this.pos >= 0) {
                    this.buffer.put(this.pos--, (byte)c);
                }
                else if (c < '\u0800' && this.pos > 0) {
                    this.buffer.put(this.pos--, (byte)(0x80 | ('?' & c)));
                    this.buffer.put(this.pos--, (byte)(0x3C0 | c >>> 6));
                }
                else if ((c < '\ud800' || '\udfff' < c) && this.pos > 1) {
                    this.buffer.put(this.pos--, (byte)(0x80 | ('?' & c)));
                    this.buffer.put(this.pos--, (byte)(0x80 | (0x3F & c >>> 6)));
                    this.buffer.put(this.pos--, (byte)(0x1E0 | c >>> 12));
                }
                else if (this.pos > 2) {
                    char high = '\0';
                    if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
                        throw new Utf8.UnpairedSurrogateException(i - 1, i);
                    }
                    --i;
                    final int codePoint = Character.toCodePoint(high, c);
                    this.buffer.put(this.pos--, (byte)(0x80 | (0x3F & codePoint)));
                    this.buffer.put(this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                    this.buffer.put(this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                    this.buffer.put(this.pos--, (byte)(0xF0 | codePoint >>> 18));
                }
                else {
                    this.requireSpace(i);
                    ++i;
                }
                --i;
            }
        }
        
        @Override
        public void write(final byte value) {
            this.buffer.put(this.pos--, value);
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            this.buffer.position(this.pos + 1);
            this.buffer.put(value, offset, length);
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            this.buffer.position(this.pos + 1);
            this.buffer.put(value, offset, length);
        }
        
        @Override
        public void write(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            this.buffer.position(this.pos + 1);
            this.buffer.put(value);
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            this.buffer.position(this.pos + 1);
            this.buffer.put(value);
        }
        
        @Override
        void requireSpace(final int size) {
            if (this.spaceLeft() < size) {
                this.nextBuffer(size);
            }
        }
    }
    
    private static final class UnsafeDirectWriter extends BinaryWriter
    {
        private ByteBuffer buffer;
        private long bufferOffset;
        private long limitMinusOne;
        private long pos;
        
        UnsafeDirectWriter(final BufferAllocator alloc, final int chunkSize) {
            super(alloc, chunkSize, null);
            this.nextBuffer();
        }
        
        private static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }
        
        private void nextBuffer() {
            this.nextBuffer(this.newDirectBuffer());
        }
        
        private void nextBuffer(final int capacity) {
            this.nextBuffer(this.newDirectBuffer(capacity));
        }
        
        private void nextBuffer(final AllocatedBuffer allocatedBuffer) {
            if (!allocatedBuffer.hasNioBuffer()) {
                throw new RuntimeException("Allocated buffer does not have NIO buffer");
            }
            final ByteBuffer nioBuffer = allocatedBuffer.nioBuffer();
            if (!nioBuffer.isDirect()) {
                throw new RuntimeException("Allocator returned non-direct buffer");
            }
            this.finishCurrentBuffer();
            this.buffers.addFirst(allocatedBuffer);
            (this.buffer = nioBuffer).limit(this.buffer.capacity());
            this.buffer.position(0);
            this.bufferOffset = UnsafeUtil.addressOffset(this.buffer);
            this.limitMinusOne = this.bufferOffset + (this.buffer.limit() - 1);
            this.pos = this.limitMinusOne;
        }
        
        @Override
        public int getTotalBytesWritten() {
            return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
        }
        
        private int bytesWrittenToCurrentBuffer() {
            return (int)(this.limitMinusOne - this.pos);
        }
        
        private int spaceLeft() {
            return this.bufferPos() + 1;
        }
        
        @Override
        void finishCurrentBuffer() {
            if (this.buffer != null) {
                this.totalDoneBytes += this.bytesWrittenToCurrentBuffer();
                this.buffer.position(this.bufferPos() + 1);
                this.buffer = null;
                this.pos = 0L;
                this.limitMinusOne = 0L;
            }
        }
        
        private int bufferPos() {
            return (int)(this.pos - this.bufferOffset);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeVarint32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) {
            this.requireSpace(15);
            this.writeInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt32(final int fieldNumber, final int value) {
            this.requireSpace(10);
            this.writeSInt32(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) {
            this.requireSpace(9);
            this.writeFixed32(value);
            this.writeTag(fieldNumber, 5);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeVarint64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeSInt64(final int fieldNumber, final long value) {
            this.requireSpace(15);
            this.writeSInt64(value);
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) {
            this.requireSpace(13);
            this.writeFixed64(value);
            this.writeTag(fieldNumber, 1);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) {
            this.requireSpace(6);
            this.write((byte)(value ? 1 : 0));
            this.writeTag(fieldNumber, 0);
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) {
            final int prevBytes = this.getTotalBytesWritten();
            this.writeString(value);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) {
            try {
                value.writeToReverse(this);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.requireSpace(10);
            this.writeVarint32(value.size());
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            Protobuf.getInstance().writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            final int prevBytes = this.getTotalBytesWritten();
            schema.writeTo(value, this);
            final int length = this.getTotalBytesWritten() - prevBytes;
            this.requireSpace(10);
            this.writeVarint32(length);
            this.writeTag(fieldNumber, 2);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value) throws IOException {
            this.writeTag(fieldNumber, 4);
            Protobuf.getInstance().writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeGroup(final int fieldNumber, final Object value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 4);
            schema.writeTo(value, this);
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeStartGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 3);
        }
        
        @Override
        public void writeEndGroup(final int fieldNumber) {
            this.writeTag(fieldNumber, 4);
        }
        
        @Override
        void writeInt32(final int value) {
            if (value >= 0) {
                this.writeVarint32(value);
            }
            else {
                this.writeVarint64(value);
            }
        }
        
        @Override
        void writeSInt32(final int value) {
            this.writeVarint32(CodedOutputStream.encodeZigZag32(value));
        }
        
        @Override
        void writeSInt64(final long value) {
            this.writeVarint64(CodedOutputStream.encodeZigZag64(value));
        }
        
        @Override
        void writeBool(final boolean value) {
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        void writeTag(final int fieldNumber, final int wireType) {
            this.writeVarint32(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        void writeVarint32(final int value) {
            if ((value & 0xFFFFFF80) == 0x0) {
                this.writeVarint32OneByte(value);
            }
            else if ((value & 0xFFFFC000) == 0x0) {
                this.writeVarint32TwoBytes(value);
            }
            else if ((value & 0xFFE00000) == 0x0) {
                this.writeVarint32ThreeBytes(value);
            }
            else if ((value & 0xF0000000) == 0x0) {
                this.writeVarint32FourBytes(value);
            }
            else {
                this.writeVarint32FiveBytes(value);
            }
        }
        
        private void writeVarint32OneByte(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)value);
        }
        
        private void writeVarint32TwoBytes(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32ThreeBytes(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 14));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32FourBytes(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 21));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        private void writeVarint32FiveBytes(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 28));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7F) | 0x80));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7F) | 0x80));
        }
        
        @Override
        void writeVarint64(final long value) {
            switch (computeUInt64SizeNoTag(value)) {
                case 1: {
                    this.writeVarint64OneByte(value);
                    break;
                }
                case 2: {
                    this.writeVarint64TwoBytes(value);
                    break;
                }
                case 3: {
                    this.writeVarint64ThreeBytes(value);
                    break;
                }
                case 4: {
                    this.writeVarint64FourBytes(value);
                    break;
                }
                case 5: {
                    this.writeVarint64FiveBytes(value);
                    break;
                }
                case 6: {
                    this.writeVarint64SixBytes(value);
                    break;
                }
                case 7: {
                    this.writeVarint64SevenBytes(value);
                    break;
                }
                case 8: {
                    this.writeVarint64EightBytes(value);
                    break;
                }
                case 9: {
                    this.writeVarint64NineBytes(value);
                    break;
                }
                case 10: {
                    this.writeVarint64TenBytes(value);
                    break;
                }
            }
        }
        
        private void writeVarint64OneByte(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)value);
        }
        
        private void writeVarint64TwoBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 7));
            UnsafeUtil.putByte(this.pos--, (byte)(((int)value & 0x7F) | 0x80));
        }
        
        private void writeVarint64ThreeBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)((int)value >>> 14));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64FourBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 21));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64FiveBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 28));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64SixBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 35));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64SevenBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 42));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64EightBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 49));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64NineBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 56));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 49 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        private void writeVarint64TenBytes(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >>> 63));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 56 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 49 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 42 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 35 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 28 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 21 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 14 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value >>> 7 & 0x7FL) | 0x80L));
            UnsafeUtil.putByte(this.pos--, (byte)((value & 0x7FL) | 0x80L));
        }
        
        @Override
        void writeFixed32(final int value) {
            UnsafeUtil.putByte(this.pos--, (byte)(value >> 24 & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)(value >> 16 & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)(value >> 8 & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)(value & 0xFF));
        }
        
        @Override
        void writeFixed64(final long value) {
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 56) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 48) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 40) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 32) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 24) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 16) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)(value >> 8) & 0xFF));
            UnsafeUtil.putByte(this.pos--, (byte)((int)value & 0xFF));
        }
        
        @Override
        void writeString(final String in) {
            this.requireSpace(in.length());
            int i;
            char c;
            for (i = in.length() - 1; i >= 0 && (c = in.charAt(i)) < '\u0080'; --i) {
                UnsafeUtil.putByte(this.pos--, (byte)c);
            }
            if (i == -1) {
                return;
            }
            while (i >= 0) {
                c = in.charAt(i);
                if (c < '\u0080' && this.pos >= this.bufferOffset) {
                    UnsafeUtil.putByte(this.pos--, (byte)c);
                }
                else if (c < '\u0800' && this.pos > this.bufferOffset) {
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | ('?' & c)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0x3C0 | c >>> 6));
                }
                else if ((c < '\ud800' || '\udfff' < c) && this.pos > this.bufferOffset + 1L) {
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | ('?' & c)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | (0x3F & c >>> 6)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0x1E0 | c >>> 12));
                }
                else if (this.pos > this.bufferOffset + 2L) {
                    final char high;
                    if (i == 0 || !Character.isSurrogatePair(high = in.charAt(i - 1), c)) {
                        throw new Utf8.UnpairedSurrogateException(i - 1, i);
                    }
                    --i;
                    final int codePoint = Character.toCodePoint(high, c);
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | (0x3F & codePoint)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                    UnsafeUtil.putByte(this.pos--, (byte)(0xF0 | codePoint >>> 18));
                }
                else {
                    this.requireSpace(i);
                    ++i;
                }
                --i;
            }
        }
        
        @Override
        public void write(final byte value) {
            UnsafeUtil.putByte(this.pos--, value);
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            this.buffer.position(this.bufferPos() + 1);
            this.buffer.put(value, offset, length);
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) {
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value, offset, length));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            this.buffer.position(this.bufferPos() + 1);
            this.buffer.put(value, offset, length);
        }
        
        @Override
        public void write(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.nextBuffer(length);
            }
            this.pos -= length;
            this.buffer.position(this.bufferPos() + 1);
            this.buffer.put(value);
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) {
            final int length = value.remaining();
            if (this.spaceLeft() < length) {
                this.totalDoneBytes += length;
                this.buffers.addFirst(AllocatedBuffer.wrap(value));
                this.nextBuffer();
                return;
            }
            this.pos -= length;
            this.buffer.position(this.bufferPos() + 1);
            this.buffer.put(value);
        }
        
        @Override
        void requireSpace(final int size) {
            if (this.spaceLeft() < size) {
                this.nextBuffer(size);
            }
        }
    }
}
