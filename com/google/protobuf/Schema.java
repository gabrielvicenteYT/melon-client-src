package com.google.protobuf;

import java.io.*;

interface Schema<T>
{
    void writeTo(final T p0, final Writer p1) throws IOException;
    
    void mergeFrom(final T p0, final Reader p1, final ExtensionRegistryLite p2) throws IOException;
    
    void mergeFrom(final T p0, final byte[] p1, final int p2, final int p3, final ArrayDecoders.Registers p4) throws IOException;
    
    void makeImmutable(final T p0);
    
    boolean isInitialized(final T p0);
    
    T newInstance();
    
    boolean equals(final T p0, final T p1);
    
    int hashCode(final T p0);
    
    void mergeFrom(final T p0, final T p1);
    
    int getSerializedSize(final T p0);
}
