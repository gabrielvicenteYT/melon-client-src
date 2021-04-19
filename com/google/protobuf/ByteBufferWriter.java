package com.google.protobuf;

import java.lang.ref.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import java.lang.reflect.*;

final class ByteBufferWriter
{
    private static final int MIN_CACHED_BUFFER_SIZE = 1024;
    private static final int MAX_CACHED_BUFFER_SIZE = 16384;
    private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5f;
    private static final ThreadLocal<SoftReference<byte[]>> BUFFER;
    private static final Class<?> FILE_OUTPUT_STREAM_CLASS;
    private static final long CHANNEL_FIELD_OFFSET;
    
    private ByteBufferWriter() {
    }
    
    static void clearCachedBuffer() {
        ByteBufferWriter.BUFFER.set(null);
    }
    
    static void write(final ByteBuffer buffer, final OutputStream output) throws IOException {
        final int initialPos = buffer.position();
        try {
            if (buffer.hasArray()) {
                output.write(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining());
            }
            else if (!writeToChannel(buffer, output)) {
                final byte[] array = getOrCreateBuffer(buffer.remaining());
                while (buffer.hasRemaining()) {
                    final int length = Math.min(buffer.remaining(), array.length);
                    buffer.get(array, 0, length);
                    output.write(array, 0, length);
                }
            }
        }
        finally {
            buffer.position(initialPos);
        }
    }
    
    private static byte[] getOrCreateBuffer(int requestedSize) {
        requestedSize = Math.max(requestedSize, 1024);
        byte[] buffer = getBuffer();
        if (buffer == null || needToReallocate(requestedSize, buffer.length)) {
            buffer = new byte[requestedSize];
            if (requestedSize <= 16384) {
                setBuffer(buffer);
            }
        }
        return buffer;
    }
    
    private static boolean needToReallocate(final int requestedSize, final int bufferLength) {
        return bufferLength < requestedSize && bufferLength < requestedSize * 0.5f;
    }
    
    private static byte[] getBuffer() {
        final SoftReference<byte[]> sr = ByteBufferWriter.BUFFER.get();
        return (byte[])((sr == null) ? null : ((byte[])sr.get()));
    }
    
    private static void setBuffer(final byte[] value) {
        ByteBufferWriter.BUFFER.set(new SoftReference<byte[]>(value));
    }
    
    private static boolean writeToChannel(final ByteBuffer buffer, final OutputStream output) throws IOException {
        if (ByteBufferWriter.CHANNEL_FIELD_OFFSET >= 0L && ByteBufferWriter.FILE_OUTPUT_STREAM_CLASS.isInstance(output)) {
            WritableByteChannel channel = null;
            try {
                channel = (WritableByteChannel)UnsafeUtil.getObject(output, ByteBufferWriter.CHANNEL_FIELD_OFFSET);
            }
            catch (ClassCastException ex) {}
            if (channel != null) {
                channel.write(buffer);
                return true;
            }
        }
        return false;
    }
    
    private static Class<?> safeGetClass(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    private static long getChannelFieldOffset(final Class<?> clazz) {
        try {
            if (clazz != null && UnsafeUtil.hasUnsafeArrayOperations()) {
                final Field field = clazz.getDeclaredField("channel");
                return UnsafeUtil.objectFieldOffset(field);
            }
        }
        catch (Throwable t) {}
        return -1L;
    }
    
    static {
        BUFFER = new ThreadLocal<SoftReference<byte[]>>();
        FILE_OUTPUT_STREAM_CLASS = safeGetClass("java.io.FileOutputStream");
        CHANNEL_FIELD_OFFSET = getChannelFieldOffset(ByteBufferWriter.FILE_OUTPUT_STREAM_CLASS);
    }
}
