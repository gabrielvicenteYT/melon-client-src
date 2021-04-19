package com.google.protobuf;

import java.io.*;
import java.util.*;

interface Reader
{
    public static final int READ_DONE = Integer.MAX_VALUE;
    public static final int TAG_UNKNOWN = 0;
    
    boolean shouldDiscardUnknownFields();
    
    int getFieldNumber() throws IOException;
    
    int getTag();
    
    boolean skipField() throws IOException;
    
    double readDouble() throws IOException;
    
    float readFloat() throws IOException;
    
    long readUInt64() throws IOException;
    
    long readInt64() throws IOException;
    
    int readInt32() throws IOException;
    
    long readFixed64() throws IOException;
    
    int readFixed32() throws IOException;
    
    boolean readBool() throws IOException;
    
    String readString() throws IOException;
    
    String readStringRequireUtf8() throws IOException;
    
     <T> T readMessageBySchemaWithCheck(final Schema<T> p0, final ExtensionRegistryLite p1) throws IOException;
    
     <T> T readMessage(final Class<T> p0, final ExtensionRegistryLite p1) throws IOException;
    
    @Deprecated
     <T> T readGroup(final Class<T> p0, final ExtensionRegistryLite p1) throws IOException;
    
    @Deprecated
     <T> T readGroupBySchemaWithCheck(final Schema<T> p0, final ExtensionRegistryLite p1) throws IOException;
    
    ByteString readBytes() throws IOException;
    
    int readUInt32() throws IOException;
    
    int readEnum() throws IOException;
    
    int readSFixed32() throws IOException;
    
    long readSFixed64() throws IOException;
    
    int readSInt32() throws IOException;
    
    long readSInt64() throws IOException;
    
    void readDoubleList(final List<Double> p0) throws IOException;
    
    void readFloatList(final List<Float> p0) throws IOException;
    
    void readUInt64List(final List<Long> p0) throws IOException;
    
    void readInt64List(final List<Long> p0) throws IOException;
    
    void readInt32List(final List<Integer> p0) throws IOException;
    
    void readFixed64List(final List<Long> p0) throws IOException;
    
    void readFixed32List(final List<Integer> p0) throws IOException;
    
    void readBoolList(final List<Boolean> p0) throws IOException;
    
    void readStringList(final List<String> p0) throws IOException;
    
    void readStringListRequireUtf8(final List<String> p0) throws IOException;
    
     <T> void readMessageList(final List<T> p0, final Schema<T> p1, final ExtensionRegistryLite p2) throws IOException;
    
     <T> void readMessageList(final List<T> p0, final Class<T> p1, final ExtensionRegistryLite p2) throws IOException;
    
    @Deprecated
     <T> void readGroupList(final List<T> p0, final Class<T> p1, final ExtensionRegistryLite p2) throws IOException;
    
    @Deprecated
     <T> void readGroupList(final List<T> p0, final Schema<T> p1, final ExtensionRegistryLite p2) throws IOException;
    
    void readBytesList(final List<ByteString> p0) throws IOException;
    
    void readUInt32List(final List<Integer> p0) throws IOException;
    
    void readEnumList(final List<Integer> p0) throws IOException;
    
    void readSFixed32List(final List<Integer> p0) throws IOException;
    
    void readSFixed64List(final List<Long> p0) throws IOException;
    
    void readSInt32List(final List<Integer> p0) throws IOException;
    
    void readSInt64List(final List<Long> p0) throws IOException;
    
     <K, V> void readMap(final Map<K, V> p0, final MapEntryLite.Metadata<K, V> p1, final ExtensionRegistryLite p2) throws IOException;
}
