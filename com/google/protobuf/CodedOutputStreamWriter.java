package com.google.protobuf;

import java.io.*;
import java.util.*;

final class CodedOutputStreamWriter implements Writer
{
    private final CodedOutputStream output;
    
    public static CodedOutputStreamWriter forCodedOutput(final CodedOutputStream output) {
        if (output.wrapper != null) {
            return output.wrapper;
        }
        return new CodedOutputStreamWriter(output);
    }
    
    private CodedOutputStreamWriter(final CodedOutputStream output) {
        this.output = Internal.checkNotNull(output, "output");
        this.output.wrapper = this;
    }
    
    @Override
    public FieldOrder fieldOrder() {
        return FieldOrder.ASCENDING;
    }
    
    public int getTotalBytesWritten() {
        return this.output.getTotalBytesWritten();
    }
    
    @Override
    public void writeSFixed32(final int fieldNumber, final int value) throws IOException {
        this.output.writeSFixed32(fieldNumber, value);
    }
    
    @Override
    public void writeInt64(final int fieldNumber, final long value) throws IOException {
        this.output.writeInt64(fieldNumber, value);
    }
    
    @Override
    public void writeSFixed64(final int fieldNumber, final long value) throws IOException {
        this.output.writeSFixed64(fieldNumber, value);
    }
    
    @Override
    public void writeFloat(final int fieldNumber, final float value) throws IOException {
        this.output.writeFloat(fieldNumber, value);
    }
    
    @Override
    public void writeDouble(final int fieldNumber, final double value) throws IOException {
        this.output.writeDouble(fieldNumber, value);
    }
    
    @Override
    public void writeEnum(final int fieldNumber, final int value) throws IOException {
        this.output.writeEnum(fieldNumber, value);
    }
    
    @Override
    public void writeUInt64(final int fieldNumber, final long value) throws IOException {
        this.output.writeUInt64(fieldNumber, value);
    }
    
    @Override
    public void writeInt32(final int fieldNumber, final int value) throws IOException {
        this.output.writeInt32(fieldNumber, value);
    }
    
    @Override
    public void writeFixed64(final int fieldNumber, final long value) throws IOException {
        this.output.writeFixed64(fieldNumber, value);
    }
    
    @Override
    public void writeFixed32(final int fieldNumber, final int value) throws IOException {
        this.output.writeFixed32(fieldNumber, value);
    }
    
    @Override
    public void writeBool(final int fieldNumber, final boolean value) throws IOException {
        this.output.writeBool(fieldNumber, value);
    }
    
    @Override
    public void writeString(final int fieldNumber, final String value) throws IOException {
        this.output.writeString(fieldNumber, value);
    }
    
    @Override
    public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
        this.output.writeBytes(fieldNumber, value);
    }
    
    @Override
    public void writeUInt32(final int fieldNumber, final int value) throws IOException {
        this.output.writeUInt32(fieldNumber, value);
    }
    
    @Override
    public void writeSInt32(final int fieldNumber, final int value) throws IOException {
        this.output.writeSInt32(fieldNumber, value);
    }
    
    @Override
    public void writeSInt64(final int fieldNumber, final long value) throws IOException {
        this.output.writeSInt64(fieldNumber, value);
    }
    
    @Override
    public void writeMessage(final int fieldNumber, final Object value) throws IOException {
        this.output.writeMessage(fieldNumber, (MessageLite)value);
    }
    
    @Override
    public void writeMessage(final int fieldNumber, final Object value, final Schema schema) throws IOException {
        this.output.writeMessage(fieldNumber, (MessageLite)value, schema);
    }
    
    @Override
    public void writeGroup(final int fieldNumber, final Object value) throws IOException {
        this.output.writeGroup(fieldNumber, (MessageLite)value);
    }
    
    @Override
    public void writeGroup(final int fieldNumber, final Object value, final Schema schema) throws IOException {
        this.output.writeGroup(fieldNumber, (MessageLite)value, schema);
    }
    
    @Override
    public void writeStartGroup(final int fieldNumber) throws IOException {
        this.output.writeTag(fieldNumber, 3);
    }
    
    @Override
    public void writeEndGroup(final int fieldNumber) throws IOException {
        this.output.writeTag(fieldNumber, 4);
    }
    
    @Override
    public final void writeMessageSetItem(final int fieldNumber, final Object value) throws IOException {
        if (value instanceof ByteString) {
            this.output.writeRawMessageSetExtension(fieldNumber, (ByteString)value);
        }
        else {
            this.output.writeMessageSetExtension(fieldNumber, (MessageLite)value);
        }
    }
    
    @Override
    public void writeInt32List(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeInt32NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeInt32(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeFixed32List(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeFixed32SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeFixed32NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeFixed32(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeInt64List(final int fieldNumber, final List<Long> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeInt64SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeInt64NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeInt64(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeUInt64List(final int fieldNumber, final List<Long> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeUInt64SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeUInt64NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeUInt64(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeFixed64List(final int fieldNumber, final List<Long> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeFixed64SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeFixed64NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeFixed64(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeFloatList(final int fieldNumber, final List<Float> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeFloatSizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeFloatNoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeFloat(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeDoubleList(final int fieldNumber, final List<Double> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeDoubleSizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeDoubleNoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeDouble(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeEnumList(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeEnumSizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeEnumNoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeEnum(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeBoolList(final int fieldNumber, final List<Boolean> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeBoolSizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeBoolNoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeBool(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeStringList(final int fieldNumber, final List<String> value) throws IOException {
        if (value instanceof LazyStringList) {
            final LazyStringList lazyList = (LazyStringList)value;
            for (int i = 0; i < value.size(); ++i) {
                this.writeLazyString(fieldNumber, lazyList.getRaw(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeString(fieldNumber, value.get(j));
            }
        }
    }
    
    private void writeLazyString(final int fieldNumber, final Object value) throws IOException {
        if (value instanceof String) {
            this.output.writeString(fieldNumber, (String)value);
        }
        else {
            this.output.writeBytes(fieldNumber, (ByteString)value);
        }
    }
    
    @Override
    public void writeBytesList(final int fieldNumber, final List<ByteString> value) throws IOException {
        for (int i = 0; i < value.size(); ++i) {
            this.output.writeBytes(fieldNumber, value.get(i));
        }
    }
    
    @Override
    public void writeUInt32List(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeUInt32SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeUInt32NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeUInt32(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeSFixed32List(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeSFixed32SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeSFixed32NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeSFixed32(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeSFixed64List(final int fieldNumber, final List<Long> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeSFixed64SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeSFixed64NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeSFixed64(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeSInt32List(final int fieldNumber, final List<Integer> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeSInt32SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeSInt32NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeSInt32(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeSInt64List(final int fieldNumber, final List<Long> value, final boolean packed) throws IOException {
        if (packed) {
            this.output.writeTag(fieldNumber, 2);
            int dataSize = 0;
            for (int i = 0; i < value.size(); ++i) {
                dataSize += CodedOutputStream.computeSInt64SizeNoTag(value.get(i));
            }
            this.output.writeUInt32NoTag(dataSize);
            for (int i = 0; i < value.size(); ++i) {
                this.output.writeSInt64NoTag(value.get(i));
            }
        }
        else {
            for (int j = 0; j < value.size(); ++j) {
                this.output.writeSInt64(fieldNumber, value.get(j));
            }
        }
    }
    
    @Override
    public void writeMessageList(final int fieldNumber, final List<?> value) throws IOException {
        for (int i = 0; i < value.size(); ++i) {
            this.writeMessage(fieldNumber, value.get(i));
        }
    }
    
    @Override
    public void writeMessageList(final int fieldNumber, final List<?> value, final Schema schema) throws IOException {
        for (int i = 0; i < value.size(); ++i) {
            this.writeMessage(fieldNumber, value.get(i), schema);
        }
    }
    
    @Override
    public void writeGroupList(final int fieldNumber, final List<?> value) throws IOException {
        for (int i = 0; i < value.size(); ++i) {
            this.writeGroup(fieldNumber, value.get(i));
        }
    }
    
    @Override
    public void writeGroupList(final int fieldNumber, final List<?> value, final Schema schema) throws IOException {
        for (int i = 0; i < value.size(); ++i) {
            this.writeGroup(fieldNumber, value.get(i), schema);
        }
    }
    
    @Override
    public <K, V> void writeMap(final int fieldNumber, final MapEntryLite.Metadata<K, V> metadata, final Map<K, V> map) throws IOException {
        if (this.output.isSerializationDeterministic()) {
            this.writeDeterministicMap(fieldNumber, (MapEntryLite.Metadata<Object, Object>)metadata, (Map<Object, Object>)map);
            return;
        }
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            this.output.writeTag(fieldNumber, 2);
            this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
            MapEntryLite.writeTo(this.output, metadata, entry.getKey(), entry.getValue());
        }
    }
    
    private <K, V> void writeDeterministicMap(final int fieldNumber, final MapEntryLite.Metadata<K, V> metadata, final Map<K, V> map) throws IOException {
        switch (metadata.keyType) {
            case BOOL: {
                V value;
                if ((value = map.get(Boolean.FALSE)) != null) {
                    this.writeDeterministicBooleanMapEntry(fieldNumber, false, value, (MapEntryLite.Metadata<Boolean, V>)metadata);
                }
                if ((value = map.get(Boolean.TRUE)) != null) {
                    this.writeDeterministicBooleanMapEntry(fieldNumber, true, value, (MapEntryLite.Metadata<Boolean, V>)metadata);
                    break;
                }
                break;
            }
            case FIXED32:
            case INT32:
            case SFIXED32:
            case SINT32:
            case UINT32: {
                this.writeDeterministicIntegerMap(fieldNumber, (MapEntryLite.Metadata<Integer, V>)metadata, (Map<Integer, V>)map);
                break;
            }
            case FIXED64:
            case INT64:
            case SFIXED64:
            case SINT64:
            case UINT64: {
                this.writeDeterministicLongMap(fieldNumber, (MapEntryLite.Metadata<Long, V>)metadata, (Map<Long, V>)map);
                break;
            }
            case STRING: {
                this.writeDeterministicStringMap(fieldNumber, (MapEntryLite.Metadata<String, V>)metadata, (Map<String, V>)map);
                break;
            }
            default: {
                throw new IllegalArgumentException("does not support key type: " + metadata.keyType);
            }
        }
    }
    
    private <V> void writeDeterministicBooleanMapEntry(final int fieldNumber, final boolean key, final V value, final MapEntryLite.Metadata<Boolean, V> metadata) throws IOException {
        this.output.writeTag(fieldNumber, 2);
        this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, key, value));
        MapEntryLite.writeTo(this.output, metadata, key, value);
    }
    
    private <V> void writeDeterministicIntegerMap(final int fieldNumber, final MapEntryLite.Metadata<Integer, V> metadata, final Map<Integer, V> map) throws IOException {
        final int[] keys = new int[map.size()];
        int index = 0;
        for (final int k : map.keySet()) {
            keys[index++] = k;
        }
        Arrays.sort(keys);
        for (final int key : keys) {
            final V value = map.get(key);
            this.output.writeTag(fieldNumber, 2);
            this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, key, value));
            MapEntryLite.writeTo(this.output, metadata, key, value);
        }
    }
    
    private <V> void writeDeterministicLongMap(final int fieldNumber, final MapEntryLite.Metadata<Long, V> metadata, final Map<Long, V> map) throws IOException {
        final long[] keys = new long[map.size()];
        int index = 0;
        for (final long k : map.keySet()) {
            keys[index++] = k;
        }
        Arrays.sort(keys);
        for (final long key : keys) {
            final V value = map.get(key);
            this.output.writeTag(fieldNumber, 2);
            this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, key, value));
            MapEntryLite.writeTo(this.output, metadata, key, value);
        }
    }
    
    private <V> void writeDeterministicStringMap(final int fieldNumber, final MapEntryLite.Metadata<String, V> metadata, final Map<String, V> map) throws IOException {
        final String[] keys = new String[map.size()];
        int index = 0;
        for (final String k : map.keySet()) {
            keys[index++] = k;
        }
        Arrays.sort(keys);
        for (final String key : keys) {
            final V value = map.get(key);
            this.output.writeTag(fieldNumber, 2);
            this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, key, value));
            MapEntryLite.writeTo(this.output, metadata, key, value);
        }
    }
}
