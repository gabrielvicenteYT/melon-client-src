package com.google.protobuf;

import java.io.*;
import java.util.*;

interface Writer
{
    FieldOrder fieldOrder();
    
    void writeSFixed32(final int p0, final int p1) throws IOException;
    
    void writeInt64(final int p0, final long p1) throws IOException;
    
    void writeSFixed64(final int p0, final long p1) throws IOException;
    
    void writeFloat(final int p0, final float p1) throws IOException;
    
    void writeDouble(final int p0, final double p1) throws IOException;
    
    void writeEnum(final int p0, final int p1) throws IOException;
    
    void writeUInt64(final int p0, final long p1) throws IOException;
    
    void writeInt32(final int p0, final int p1) throws IOException;
    
    void writeFixed64(final int p0, final long p1) throws IOException;
    
    void writeFixed32(final int p0, final int p1) throws IOException;
    
    void writeBool(final int p0, final boolean p1) throws IOException;
    
    void writeString(final int p0, final String p1) throws IOException;
    
    void writeBytes(final int p0, final ByteString p1) throws IOException;
    
    void writeUInt32(final int p0, final int p1) throws IOException;
    
    void writeSInt32(final int p0, final int p1) throws IOException;
    
    void writeSInt64(final int p0, final long p1) throws IOException;
    
    void writeMessage(final int p0, final Object p1) throws IOException;
    
    void writeMessage(final int p0, final Object p1, final Schema p2) throws IOException;
    
    @Deprecated
    void writeGroup(final int p0, final Object p1) throws IOException;
    
    @Deprecated
    void writeGroup(final int p0, final Object p1, final Schema p2) throws IOException;
    
    @Deprecated
    void writeStartGroup(final int p0) throws IOException;
    
    @Deprecated
    void writeEndGroup(final int p0) throws IOException;
    
    void writeInt32List(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeFixed32List(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeInt64List(final int p0, final List<Long> p1, final boolean p2) throws IOException;
    
    void writeUInt64List(final int p0, final List<Long> p1, final boolean p2) throws IOException;
    
    void writeFixed64List(final int p0, final List<Long> p1, final boolean p2) throws IOException;
    
    void writeFloatList(final int p0, final List<Float> p1, final boolean p2) throws IOException;
    
    void writeDoubleList(final int p0, final List<Double> p1, final boolean p2) throws IOException;
    
    void writeEnumList(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeBoolList(final int p0, final List<Boolean> p1, final boolean p2) throws IOException;
    
    void writeStringList(final int p0, final List<String> p1) throws IOException;
    
    void writeBytesList(final int p0, final List<ByteString> p1) throws IOException;
    
    void writeUInt32List(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeSFixed32List(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeSFixed64List(final int p0, final List<Long> p1, final boolean p2) throws IOException;
    
    void writeSInt32List(final int p0, final List<Integer> p1, final boolean p2) throws IOException;
    
    void writeSInt64List(final int p0, final List<Long> p1, final boolean p2) throws IOException;
    
    void writeMessageList(final int p0, final List<?> p1) throws IOException;
    
    void writeMessageList(final int p0, final List<?> p1, final Schema p2) throws IOException;
    
    @Deprecated
    void writeGroupList(final int p0, final List<?> p1) throws IOException;
    
    @Deprecated
    void writeGroupList(final int p0, final List<?> p1, final Schema p2) throws IOException;
    
    void writeMessageSetItem(final int p0, final Object p1) throws IOException;
    
     <K, V> void writeMap(final int p0, final MapEntryLite.Metadata<K, V> p1, final Map<K, V> p2) throws IOException;
    
    public enum FieldOrder
    {
        ASCENDING, 
        DESCENDING;
    }
}
