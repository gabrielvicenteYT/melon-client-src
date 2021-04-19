package com.google.protobuf;

import java.util.*;

public interface LazyStringList extends ProtocolStringList
{
    ByteString getByteString(final int p0);
    
    Object getRaw(final int p0);
    
    byte[] getByteArray(final int p0);
    
    void add(final ByteString p0);
    
    void add(final byte[] p0);
    
    void set(final int p0, final ByteString p1);
    
    void set(final int p0, final byte[] p1);
    
    boolean addAllByteString(final Collection<? extends ByteString> p0);
    
    boolean addAllByteArray(final Collection<byte[]> p0);
    
    List<?> getUnderlyingElements();
    
    void mergeFrom(final LazyStringList p0);
    
    List<byte[]> asByteArrayList();
    
    LazyStringList getUnmodifiableView();
}
