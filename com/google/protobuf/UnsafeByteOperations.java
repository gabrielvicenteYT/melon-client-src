package com.google.protobuf;

import java.nio.*;
import java.io.*;

public final class UnsafeByteOperations
{
    private UnsafeByteOperations() {
    }
    
    public static ByteString unsafeWrap(final byte[] buffer) {
        return ByteString.wrap(buffer);
    }
    
    public static ByteString unsafeWrap(final byte[] buffer, final int offset, final int length) {
        return ByteString.wrap(buffer, offset, length);
    }
    
    public static ByteString unsafeWrap(final ByteBuffer buffer) {
        return ByteString.wrap(buffer);
    }
    
    public static void unsafeWriteTo(final ByteString bytes, final ByteOutput output) throws IOException {
        bytes.writeTo(output);
    }
}
