package com.google.protobuf;

import java.io.*;
import java.nio.*;

public abstract class ByteOutput
{
    public abstract void write(final byte p0) throws IOException;
    
    public abstract void write(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void writeLazy(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public abstract void write(final ByteBuffer p0) throws IOException;
    
    public abstract void writeLazy(final ByteBuffer p0) throws IOException;
}
