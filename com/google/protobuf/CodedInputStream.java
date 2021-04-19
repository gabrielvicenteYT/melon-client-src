package com.google.protobuf;

import java.nio.*;
import java.util.*;
import java.io.*;

public abstract class CodedInputStream
{
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;
    int recursionDepth;
    int recursionLimit;
    int sizeLimit;
    CodedInputStreamReader wrapper;
    private boolean shouldDiscardUnknownFields;
    
    public static CodedInputStream newInstance(final InputStream input) {
        return newInstance(input, 4096);
    }
    
    public static CodedInputStream newInstance(final InputStream input, final int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be > 0");
        }
        if (input == null) {
            return newInstance(Internal.EMPTY_BYTE_ARRAY);
        }
        return new StreamDecoder(input, bufferSize);
    }
    
    public static CodedInputStream newInstance(final Iterable<ByteBuffer> input) {
        if (!UnsafeDirectNioDecoder.isSupported()) {
            return newInstance(new IterableByteBufferInputStream(input));
        }
        return newInstance(input, false);
    }
    
    static CodedInputStream newInstance(final Iterable<ByteBuffer> bufs, final boolean bufferIsImmutable) {
        int flag = 0;
        int totalSize = 0;
        for (final ByteBuffer buf : bufs) {
            totalSize += buf.remaining();
            if (buf.hasArray()) {
                flag |= 0x1;
            }
            else if (buf.isDirect()) {
                flag |= 0x2;
            }
            else {
                flag |= 0x4;
            }
        }
        if (flag == 2) {
            return new IterableDirectByteBufferDecoder((Iterable)bufs, totalSize, bufferIsImmutable);
        }
        return newInstance(new IterableByteBufferInputStream(bufs));
    }
    
    public static CodedInputStream newInstance(final byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }
    
    public static CodedInputStream newInstance(final byte[] buf, final int off, final int len) {
        return newInstance(buf, off, len, false);
    }
    
    static CodedInputStream newInstance(final byte[] buf, final int off, final int len, final boolean bufferIsImmutable) {
        final ArrayDecoder result = new ArrayDecoder(buf, off, len, bufferIsImmutable);
        try {
            result.pushLimit(len);
        }
        catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
        return result;
    }
    
    public static CodedInputStream newInstance(final ByteBuffer buf) {
        return newInstance(buf, false);
    }
    
    static CodedInputStream newInstance(final ByteBuffer buf, final boolean bufferIsImmutable) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining(), bufferIsImmutable);
        }
        if (buf.isDirect() && UnsafeDirectNioDecoder.isSupported()) {
            return new UnsafeDirectNioDecoder(buf, bufferIsImmutable);
        }
        final byte[] buffer = new byte[buf.remaining()];
        buf.duplicate().get(buffer);
        return newInstance(buffer, 0, buffer.length, true);
    }
    
    private CodedInputStream() {
        this.recursionLimit = 100;
        this.sizeLimit = Integer.MAX_VALUE;
        this.shouldDiscardUnknownFields = false;
    }
    
    public abstract int readTag() throws IOException;
    
    public abstract void checkLastTagWas(final int p0) throws InvalidProtocolBufferException;
    
    public abstract int getLastTag();
    
    public abstract boolean skipField(final int p0) throws IOException;
    
    @Deprecated
    public abstract boolean skipField(final int p0, final CodedOutputStream p1) throws IOException;
    
    public abstract void skipMessage() throws IOException;
    
    public abstract void skipMessage(final CodedOutputStream p0) throws IOException;
    
    public abstract double readDouble() throws IOException;
    
    public abstract float readFloat() throws IOException;
    
    public abstract long readUInt64() throws IOException;
    
    public abstract long readInt64() throws IOException;
    
    public abstract int readInt32() throws IOException;
    
    public abstract long readFixed64() throws IOException;
    
    public abstract int readFixed32() throws IOException;
    
    public abstract boolean readBool() throws IOException;
    
    public abstract String readString() throws IOException;
    
    public abstract String readStringRequireUtf8() throws IOException;
    
    public abstract void readGroup(final int p0, final MessageLite.Builder p1, final ExtensionRegistryLite p2) throws IOException;
    
    public abstract <T extends MessageLite> T readGroup(final int p0, final Parser<T> p1, final ExtensionRegistryLite p2) throws IOException;
    
    @Deprecated
    public abstract void readUnknownGroup(final int p0, final MessageLite.Builder p1) throws IOException;
    
    public abstract void readMessage(final MessageLite.Builder p0, final ExtensionRegistryLite p1) throws IOException;
    
    public abstract <T extends MessageLite> T readMessage(final Parser<T> p0, final ExtensionRegistryLite p1) throws IOException;
    
    public abstract ByteString readBytes() throws IOException;
    
    public abstract byte[] readByteArray() throws IOException;
    
    public abstract ByteBuffer readByteBuffer() throws IOException;
    
    public abstract int readUInt32() throws IOException;
    
    public abstract int readEnum() throws IOException;
    
    public abstract int readSFixed32() throws IOException;
    
    public abstract long readSFixed64() throws IOException;
    
    public abstract int readSInt32() throws IOException;
    
    public abstract long readSInt64() throws IOException;
    
    public abstract int readRawVarint32() throws IOException;
    
    public abstract long readRawVarint64() throws IOException;
    
    abstract long readRawVarint64SlowPath() throws IOException;
    
    public abstract int readRawLittleEndian32() throws IOException;
    
    public abstract long readRawLittleEndian64() throws IOException;
    
    public abstract void enableAliasing(final boolean p0);
    
    public final int setRecursionLimit(final int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
        }
        final int oldLimit = this.recursionLimit;
        this.recursionLimit = limit;
        return oldLimit;
    }
    
    public final int setSizeLimit(final int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
        }
        final int oldLimit = this.sizeLimit;
        this.sizeLimit = limit;
        return oldLimit;
    }
    
    final void discardUnknownFields() {
        this.shouldDiscardUnknownFields = true;
    }
    
    final void unsetDiscardUnknownFields() {
        this.shouldDiscardUnknownFields = false;
    }
    
    final boolean shouldDiscardUnknownFields() {
        return this.shouldDiscardUnknownFields;
    }
    
    public abstract void resetSizeCounter();
    
    public abstract int pushLimit(final int p0) throws InvalidProtocolBufferException;
    
    public abstract void popLimit(final int p0);
    
    public abstract int getBytesUntilLimit();
    
    public abstract boolean isAtEnd() throws IOException;
    
    public abstract int getTotalBytesRead();
    
    public abstract byte readRawByte() throws IOException;
    
    public abstract byte[] readRawBytes(final int p0) throws IOException;
    
    public abstract void skipRawBytes(final int p0) throws IOException;
    
    public static int decodeZigZag32(final int n) {
        return n >>> 1 ^ -(n & 0x1);
    }
    
    public static long decodeZigZag64(final long n) {
        return n >>> 1 ^ -(n & 0x1L);
    }
    
    public static int readRawVarint32(final int firstByte, final InputStream input) throws IOException {
        if ((firstByte & 0x80) == 0x0) {
            return firstByte;
        }
        int result = firstByte & 0x7F;
        int offset;
        for (offset = 7; offset < 32; offset += 7) {
            final int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result |= (b & 0x7F) << offset;
            if ((b & 0x80) == 0x0) {
                return result;
            }
        }
        while (offset < 64) {
            final int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((b & 0x80) == 0x0) {
                return result;
            }
            offset += 7;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }
    
    static int readRawVarint32(final InputStream input) throws IOException {
        final int firstByte = input.read();
        if (firstByte == -1) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        return readRawVarint32(firstByte, input);
    }
    
    private static final class ArrayDecoder extends CodedInputStream
    {
        private final byte[] buffer;
        private final boolean immutable;
        private int limit;
        private int bufferSizeAfterLimit;
        private int pos;
        private int startPos;
        private int lastTag;
        private boolean enableAliasing;
        private int currentLimit;
        
        private ArrayDecoder(final byte[] buffer, final int offset, final int len, final boolean immutable) {
            super(null);
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = buffer;
            this.limit = offset + len;
            this.pos = offset;
            this.startPos = this.pos;
            this.immutable = immutable;
        }
        
        @Override
        public int readTag() throws IOException {
            if (this.isAtEnd()) {
                return this.lastTag = 0;
            }
            this.lastTag = this.readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }
        
        @Override
        public void checkLastTagWas(final int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }
        
        @Override
        public int getLastTag() {
            return this.lastTag;
        }
        
        @Override
        public boolean skipField(final int tag) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    this.skipRawVarint();
                    return true;
                }
                case 1: {
                    this.skipRawBytes(8);
                    return true;
                }
                case 2: {
                    this.skipRawBytes(this.readRawVarint32());
                    return true;
                }
                case 3: {
                    this.skipMessage();
                    this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    this.skipRawBytes(4);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public boolean skipField(final int tag, final CodedOutputStream output) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    final long value = this.readInt64();
                    output.writeRawVarint32(tag);
                    output.writeUInt64NoTag(value);
                    return true;
                }
                case 1: {
                    final long value = this.readRawLittleEndian64();
                    output.writeRawVarint32(tag);
                    output.writeFixed64NoTag(value);
                    return true;
                }
                case 2: {
                    final ByteString value2 = this.readBytes();
                    output.writeRawVarint32(tag);
                    output.writeBytesNoTag(value2);
                    return true;
                }
                case 3: {
                    output.writeRawVarint32(tag);
                    this.skipMessage(output);
                    final int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                    this.checkLastTagWas(endtag);
                    output.writeRawVarint32(endtag);
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    final int value3 = this.readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value3);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag));
        }
        
        @Override
        public void skipMessage(final CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag, output));
        }
        
        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readRawLittleEndian64());
        }
        
        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readRawLittleEndian32());
        }
        
        @Override
        public long readUInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public long readInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public int readInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public long readFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public boolean readBool() throws IOException {
            return this.readRawVarint64() != 0L;
        }
        
        @Override
        public String readString() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.limit - this.pos) {
                final String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public String readStringRequireUtf8() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.limit - this.pos) {
                final String result = Utf8.decodeUtf8(this.buffer, this.pos, size);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public void readGroup(final int fieldNumber, final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
        }
        
        @Override
        public <T extends MessageLite> T readGroup(final int fieldNumber, final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
            return result;
        }
        
        @Deprecated
        @Override
        public void readUnknownGroup(final int fieldNumber, final MessageLite.Builder builder) throws IOException {
            this.readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public void readMessage(final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
        }
        
        @Override
        public <T extends MessageLite> T readMessage(final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
            return result;
        }
        
        @Override
        public ByteString readBytes() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.limit - this.pos) {
                final ByteString result = (this.immutable && this.enableAliasing) ? ByteString.wrap(this.buffer, this.pos, size) : ByteString.copyFrom(this.buffer, this.pos, size);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return ByteString.EMPTY;
            }
            return ByteString.wrap(this.readRawBytes(size));
        }
        
        @Override
        public byte[] readByteArray() throws IOException {
            final int size = this.readRawVarint32();
            return this.readRawBytes(size);
        }
        
        @Override
        public ByteBuffer readByteBuffer() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.limit - this.pos) {
                final ByteBuffer result = (!this.immutable && this.enableAliasing) ? ByteBuffer.wrap(this.buffer, this.pos, size).slice() : ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.pos, this.pos + size));
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public int readUInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readEnum() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readSFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public long readSFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(this.readRawVarint32());
        }
        
        @Override
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(this.readRawVarint64());
        }
        
        @Override
        public int readRawVarint32() throws IOException {
            int tempPos = this.pos;
            if (this.limit != tempPos) {
                final byte[] buffer = this.buffer;
                int x;
                if ((x = buffer[tempPos++]) >= 0) {
                    this.pos = tempPos;
                    return x;
                }
                if (this.limit - tempPos >= 9) {
                    if ((x ^= buffer[tempPos++] << 7) < 0) {
                        x ^= 0xFFFFFF80;
                    }
                    else if ((x ^= buffer[tempPos++] << 14) >= 0) {
                        x ^= 0x3F80;
                    }
                    else if ((x ^= buffer[tempPos++] << 21) < 0) {
                        x ^= 0xFFE03F80;
                    }
                    else {
                        final int y = buffer[tempPos++];
                        x ^= y << 28;
                        x ^= 0xFE03F80;
                        if (y < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0) {
                            return (int)this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return (int)this.readRawVarint64SlowPath();
        }
        
        private void skipRawVarint() throws IOException {
            if (this.limit - this.pos >= 10) {
                this.skipRawVarintFastPath();
            }
            else {
                this.skipRawVarintSlowPath();
            }
        }
        
        private void skipRawVarintFastPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.buffer[this.pos++] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        private void skipRawVarintSlowPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public long readRawVarint64() throws IOException {
            int tempPos = this.pos;
            if (this.limit != tempPos) {
                final byte[] buffer = this.buffer;
                int y;
                if ((y = buffer[tempPos++]) >= 0) {
                    this.pos = tempPos;
                    return y;
                }
                if (this.limit - tempPos >= 9) {
                    long x;
                    if ((y ^= buffer[tempPos++] << 7) < 0) {
                        x = (y ^ 0xFFFFFF80);
                    }
                    else if ((y ^= buffer[tempPos++] << 14) >= 0) {
                        x = (y ^ 0x3F80);
                    }
                    else if ((y ^= buffer[tempPos++] << 21) < 0) {
                        x = (y ^ 0xFFE03F80);
                    }
                    else if ((x = ((long)y ^ (long)buffer[tempPos++] << 28)) >= 0L) {
                        x ^= 0xFE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 35) < 0L) {
                        x ^= 0xFFFFFFF80FE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 42) >= 0L) {
                        x ^= 0x3F80FE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 49) < 0L) {
                        x ^= 0xFFFE03F80FE03F80L;
                    }
                    else {
                        x ^= (long)buffer[tempPos++] << 56;
                        x ^= 0xFE03F80FE03F80L;
                        if (x < 0L && buffer[tempPos++] < 0L) {
                            return this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return this.readRawVarint64SlowPath();
        }
        
        @Override
        long readRawVarint64SlowPath() throws IOException {
            long result = 0L;
            for (int shift = 0; shift < 64; shift += 7) {
                final byte b = this.readRawByte();
                result |= (long)(b & 0x7F) << shift;
                if ((b & 0x80) == 0x0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public int readRawLittleEndian32() throws IOException {
            final int tempPos = this.pos;
            if (this.limit - tempPos < 4) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            final byte[] buffer = this.buffer;
            this.pos = tempPos + 4;
            return (buffer[tempPos] & 0xFF) | (buffer[tempPos + 1] & 0xFF) << 8 | (buffer[tempPos + 2] & 0xFF) << 16 | (buffer[tempPos + 3] & 0xFF) << 24;
        }
        
        @Override
        public long readRawLittleEndian64() throws IOException {
            final int tempPos = this.pos;
            if (this.limit - tempPos < 8) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            final byte[] buffer = this.buffer;
            this.pos = tempPos + 8;
            return ((long)buffer[tempPos] & 0xFFL) | ((long)buffer[tempPos + 1] & 0xFFL) << 8 | ((long)buffer[tempPos + 2] & 0xFFL) << 16 | ((long)buffer[tempPos + 3] & 0xFFL) << 24 | ((long)buffer[tempPos + 4] & 0xFFL) << 32 | ((long)buffer[tempPos + 5] & 0xFFL) << 40 | ((long)buffer[tempPos + 6] & 0xFFL) << 48 | ((long)buffer[tempPos + 7] & 0xFFL) << 56;
        }
        
        @Override
        public void enableAliasing(final boolean enabled) {
            this.enableAliasing = enabled;
        }
        
        @Override
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }
        
        @Override
        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            byteLimit += this.getTotalBytesRead();
            final int oldLimit = this.currentLimit;
            if (byteLimit > oldLimit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = byteLimit;
            this.recomputeBufferSizeAfterLimit();
            return oldLimit;
        }
        
        private void recomputeBufferSizeAfterLimit() {
            this.limit += this.bufferSizeAfterLimit;
            final int bufferEnd = this.limit - this.startPos;
            if (bufferEnd > this.currentLimit) {
                this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
                this.limit -= this.bufferSizeAfterLimit;
            }
            else {
                this.bufferSizeAfterLimit = 0;
            }
        }
        
        @Override
        public void popLimit(final int oldLimit) {
            this.currentLimit = oldLimit;
            this.recomputeBufferSizeAfterLimit();
        }
        
        @Override
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - this.getTotalBytesRead();
        }
        
        @Override
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }
        
        @Override
        public int getTotalBytesRead() {
            return this.pos - this.startPos;
        }
        
        @Override
        public byte readRawByte() throws IOException {
            if (this.pos == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            return this.buffer[this.pos++];
        }
        
        @Override
        public byte[] readRawBytes(final int length) throws IOException {
            if (length > 0 && length <= this.limit - this.pos) {
                final int tempPos = this.pos;
                this.pos += length;
                return Arrays.copyOfRange(this.buffer, tempPos, this.pos);
            }
            if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        
        @Override
        public void skipRawBytes(final int length) throws IOException {
            if (length >= 0 && length <= this.limit - this.pos) {
                this.pos += length;
                return;
            }
            if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }
    
    private static final class UnsafeDirectNioDecoder extends CodedInputStream
    {
        private final ByteBuffer buffer;
        private final boolean immutable;
        private final long address;
        private long limit;
        private long pos;
        private long startPos;
        private int bufferSizeAfterLimit;
        private int lastTag;
        private boolean enableAliasing;
        private int currentLimit;
        
        static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }
        
        private UnsafeDirectNioDecoder(final ByteBuffer buffer, final boolean immutable) {
            super(null);
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = buffer;
            this.address = UnsafeUtil.addressOffset(buffer);
            this.limit = this.address + buffer.limit();
            this.pos = this.address + buffer.position();
            this.startPos = this.pos;
            this.immutable = immutable;
        }
        
        @Override
        public int readTag() throws IOException {
            if (this.isAtEnd()) {
                return this.lastTag = 0;
            }
            this.lastTag = this.readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }
        
        @Override
        public void checkLastTagWas(final int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }
        
        @Override
        public int getLastTag() {
            return this.lastTag;
        }
        
        @Override
        public boolean skipField(final int tag) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    this.skipRawVarint();
                    return true;
                }
                case 1: {
                    this.skipRawBytes(8);
                    return true;
                }
                case 2: {
                    this.skipRawBytes(this.readRawVarint32());
                    return true;
                }
                case 3: {
                    this.skipMessage();
                    this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    this.skipRawBytes(4);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public boolean skipField(final int tag, final CodedOutputStream output) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    final long value = this.readInt64();
                    output.writeRawVarint32(tag);
                    output.writeUInt64NoTag(value);
                    return true;
                }
                case 1: {
                    final long value = this.readRawLittleEndian64();
                    output.writeRawVarint32(tag);
                    output.writeFixed64NoTag(value);
                    return true;
                }
                case 2: {
                    final ByteString value2 = this.readBytes();
                    output.writeRawVarint32(tag);
                    output.writeBytesNoTag(value2);
                    return true;
                }
                case 3: {
                    output.writeRawVarint32(tag);
                    this.skipMessage(output);
                    final int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                    this.checkLastTagWas(endtag);
                    output.writeRawVarint32(endtag);
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    final int value3 = this.readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value3);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag));
        }
        
        @Override
        public void skipMessage(final CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag, output));
        }
        
        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readRawLittleEndian64());
        }
        
        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readRawLittleEndian32());
        }
        
        @Override
        public long readUInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public long readInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public int readInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public long readFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public boolean readBool() throws IOException {
            return this.readRawVarint64() != 0L;
        }
        
        @Override
        public String readString() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.remaining()) {
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
                final String result = new String(bytes, Internal.UTF_8);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public String readStringRequireUtf8() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.remaining()) {
                final int bufferPos = this.bufferPos(this.pos);
                final String result = Utf8.decodeUtf8(this.buffer, bufferPos, size);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public void readGroup(final int fieldNumber, final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
        }
        
        @Override
        public <T extends MessageLite> T readGroup(final int fieldNumber, final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
            return result;
        }
        
        @Deprecated
        @Override
        public void readUnknownGroup(final int fieldNumber, final MessageLite.Builder builder) throws IOException {
            this.readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public void readMessage(final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
        }
        
        @Override
        public <T extends MessageLite> T readMessage(final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
            return result;
        }
        
        @Override
        public ByteString readBytes() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.remaining()) {
                if (this.immutable && this.enableAliasing) {
                    final ByteBuffer result = this.slice(this.pos, this.pos + size);
                    this.pos += size;
                    return ByteString.wrap(result);
                }
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
                this.pos += size;
                return ByteString.wrap(bytes);
            }
            else {
                if (size == 0) {
                    return ByteString.EMPTY;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        
        @Override
        public byte[] readByteArray() throws IOException {
            return this.readRawBytes(this.readRawVarint32());
        }
        
        @Override
        public ByteBuffer readByteBuffer() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.remaining()) {
                if (!this.immutable && this.enableAliasing) {
                    final ByteBuffer result = this.slice(this.pos, this.pos + size);
                    this.pos += size;
                    return result;
                }
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.pos, bytes, 0L, size);
                this.pos += size;
                return ByteBuffer.wrap(bytes);
            }
            else {
                if (size == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        
        @Override
        public int readUInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readEnum() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readSFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public long readSFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(this.readRawVarint32());
        }
        
        @Override
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(this.readRawVarint64());
        }
        
        @Override
        public int readRawVarint32() throws IOException {
            long tempPos = this.pos;
            if (this.limit != tempPos) {
                int x;
                if ((x = UnsafeUtil.getByte(tempPos++)) >= 0) {
                    this.pos = tempPos;
                    return x;
                }
                if (this.limit - tempPos >= 9L) {
                    if ((x ^= UnsafeUtil.getByte(tempPos++) << 7) < 0) {
                        x ^= 0xFFFFFF80;
                    }
                    else if ((x ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0) {
                        x ^= 0x3F80;
                    }
                    else if ((x ^= UnsafeUtil.getByte(tempPos++) << 21) < 0) {
                        x ^= 0xFFE03F80;
                    }
                    else {
                        final int y = UnsafeUtil.getByte(tempPos++);
                        x ^= y << 28;
                        x ^= 0xFE03F80;
                        if (y < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0) {
                            return (int)this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return (int)this.readRawVarint64SlowPath();
        }
        
        private void skipRawVarint() throws IOException {
            if (this.remaining() >= 10) {
                this.skipRawVarintFastPath();
            }
            else {
                this.skipRawVarintSlowPath();
            }
        }
        
        private void skipRawVarintFastPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (UnsafeUtil.getByte(this.pos++) >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        private void skipRawVarintSlowPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public long readRawVarint64() throws IOException {
            long tempPos = this.pos;
            if (this.limit != tempPos) {
                int y;
                if ((y = UnsafeUtil.getByte(tempPos++)) >= 0) {
                    this.pos = tempPos;
                    return y;
                }
                if (this.limit - tempPos >= 9L) {
                    long x;
                    if ((y ^= UnsafeUtil.getByte(tempPos++) << 7) < 0) {
                        x = (y ^ 0xFFFFFF80);
                    }
                    else if ((y ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0) {
                        x = (y ^ 0x3F80);
                    }
                    else if ((y ^= UnsafeUtil.getByte(tempPos++) << 21) < 0) {
                        x = (y ^ 0xFFE03F80);
                    }
                    else if ((x = ((long)y ^ (long)UnsafeUtil.getByte(tempPos++) << 28)) >= 0L) {
                        x ^= 0xFE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 35) < 0L) {
                        x ^= 0xFFFFFFF80FE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 42) >= 0L) {
                        x ^= 0x3F80FE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 49) < 0L) {
                        x ^= 0xFFFE03F80FE03F80L;
                    }
                    else {
                        x ^= (long)UnsafeUtil.getByte(tempPos++) << 56;
                        x ^= 0xFE03F80FE03F80L;
                        if (x < 0L && UnsafeUtil.getByte(tempPos++) < 0L) {
                            return this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return this.readRawVarint64SlowPath();
        }
        
        @Override
        long readRawVarint64SlowPath() throws IOException {
            long result = 0L;
            for (int shift = 0; shift < 64; shift += 7) {
                final byte b = this.readRawByte();
                result |= (long)(b & 0x7F) << shift;
                if ((b & 0x80) == 0x0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public int readRawLittleEndian32() throws IOException {
            final long tempPos = this.pos;
            if (this.limit - tempPos < 4L) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = tempPos + 4L;
            return (UnsafeUtil.getByte(tempPos) & 0xFF) | (UnsafeUtil.getByte(tempPos + 1L) & 0xFF) << 8 | (UnsafeUtil.getByte(tempPos + 2L) & 0xFF) << 16 | (UnsafeUtil.getByte(tempPos + 3L) & 0xFF) << 24;
        }
        
        @Override
        public long readRawLittleEndian64() throws IOException {
            final long tempPos = this.pos;
            if (this.limit - tempPos < 8L) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = tempPos + 8L;
            return ((long)UnsafeUtil.getByte(tempPos) & 0xFFL) | ((long)UnsafeUtil.getByte(tempPos + 1L) & 0xFFL) << 8 | ((long)UnsafeUtil.getByte(tempPos + 2L) & 0xFFL) << 16 | ((long)UnsafeUtil.getByte(tempPos + 3L) & 0xFFL) << 24 | ((long)UnsafeUtil.getByte(tempPos + 4L) & 0xFFL) << 32 | ((long)UnsafeUtil.getByte(tempPos + 5L) & 0xFFL) << 40 | ((long)UnsafeUtil.getByte(tempPos + 6L) & 0xFFL) << 48 | ((long)UnsafeUtil.getByte(tempPos + 7L) & 0xFFL) << 56;
        }
        
        @Override
        public void enableAliasing(final boolean enabled) {
            this.enableAliasing = enabled;
        }
        
        @Override
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }
        
        @Override
        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            byteLimit += this.getTotalBytesRead();
            final int oldLimit = this.currentLimit;
            if (byteLimit > oldLimit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = byteLimit;
            this.recomputeBufferSizeAfterLimit();
            return oldLimit;
        }
        
        @Override
        public void popLimit(final int oldLimit) {
            this.currentLimit = oldLimit;
            this.recomputeBufferSizeAfterLimit();
        }
        
        @Override
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - this.getTotalBytesRead();
        }
        
        @Override
        public boolean isAtEnd() throws IOException {
            return this.pos == this.limit;
        }
        
        @Override
        public int getTotalBytesRead() {
            return (int)(this.pos - this.startPos);
        }
        
        @Override
        public byte readRawByte() throws IOException {
            if (this.pos == this.limit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            return UnsafeUtil.getByte(this.pos++);
        }
        
        @Override
        public byte[] readRawBytes(final int length) throws IOException {
            if (length >= 0 && length <= this.remaining()) {
                final byte[] bytes = new byte[length];
                this.slice(this.pos, this.pos + length).get(bytes);
                this.pos += length;
                return bytes;
            }
            if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        
        @Override
        public void skipRawBytes(final int length) throws IOException {
            if (length >= 0 && length <= this.remaining()) {
                this.pos += length;
                return;
            }
            if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        private void recomputeBufferSizeAfterLimit() {
            this.limit += this.bufferSizeAfterLimit;
            final int bufferEnd = (int)(this.limit - this.startPos);
            if (bufferEnd > this.currentLimit) {
                this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
                this.limit -= this.bufferSizeAfterLimit;
            }
            else {
                this.bufferSizeAfterLimit = 0;
            }
        }
        
        private int remaining() {
            return (int)(this.limit - this.pos);
        }
        
        private int bufferPos(final long pos) {
            return (int)(pos - this.address);
        }
        
        private ByteBuffer slice(final long begin, final long end) throws IOException {
            final int prevPos = this.buffer.position();
            final int prevLimit = this.buffer.limit();
            try {
                this.buffer.position(this.bufferPos(begin));
                this.buffer.limit(this.bufferPos(end));
                return this.buffer.slice();
            }
            catch (IllegalArgumentException e) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            finally {
                this.buffer.position(prevPos);
                this.buffer.limit(prevLimit);
            }
        }
    }
    
    private static final class StreamDecoder extends CodedInputStream
    {
        private final InputStream input;
        private final byte[] buffer;
        private int bufferSize;
        private int bufferSizeAfterLimit;
        private int pos;
        private int lastTag;
        private int totalBytesRetired;
        private int currentLimit;
        private RefillCallback refillCallback;
        
        private StreamDecoder(final InputStream input, final int bufferSize) {
            super(null);
            this.currentLimit = Integer.MAX_VALUE;
            this.refillCallback = null;
            Internal.checkNotNull(input, "input");
            this.input = input;
            this.buffer = new byte[bufferSize];
            this.bufferSize = 0;
            this.pos = 0;
            this.totalBytesRetired = 0;
        }
        
        @Override
        public int readTag() throws IOException {
            if (this.isAtEnd()) {
                return this.lastTag = 0;
            }
            this.lastTag = this.readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }
        
        @Override
        public void checkLastTagWas(final int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }
        
        @Override
        public int getLastTag() {
            return this.lastTag;
        }
        
        @Override
        public boolean skipField(final int tag) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    this.skipRawVarint();
                    return true;
                }
                case 1: {
                    this.skipRawBytes(8);
                    return true;
                }
                case 2: {
                    this.skipRawBytes(this.readRawVarint32());
                    return true;
                }
                case 3: {
                    this.skipMessage();
                    this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    this.skipRawBytes(4);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public boolean skipField(final int tag, final CodedOutputStream output) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    final long value = this.readInt64();
                    output.writeRawVarint32(tag);
                    output.writeUInt64NoTag(value);
                    return true;
                }
                case 1: {
                    final long value = this.readRawLittleEndian64();
                    output.writeRawVarint32(tag);
                    output.writeFixed64NoTag(value);
                    return true;
                }
                case 2: {
                    final ByteString value2 = this.readBytes();
                    output.writeRawVarint32(tag);
                    output.writeBytesNoTag(value2);
                    return true;
                }
                case 3: {
                    output.writeRawVarint32(tag);
                    this.skipMessage(output);
                    final int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                    this.checkLastTagWas(endtag);
                    output.writeRawVarint32(endtag);
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    final int value3 = this.readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value3);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag));
        }
        
        @Override
        public void skipMessage(final CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag, output));
        }
        
        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readRawLittleEndian64());
        }
        
        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readRawLittleEndian32());
        }
        
        @Override
        public long readUInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public long readInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public int readInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public long readFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public boolean readBool() throws IOException {
            return this.readRawVarint64() != 0L;
        }
        
        @Override
        public String readString() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.bufferSize - this.pos) {
                final String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size <= this.bufferSize) {
                this.refillBuffer(size);
                final String result = new String(this.buffer, this.pos, size, Internal.UTF_8);
                this.pos += size;
                return result;
            }
            return new String(this.readRawBytesSlowPath(size, false), Internal.UTF_8);
        }
        
        @Override
        public String readStringRequireUtf8() throws IOException {
            final int size = this.readRawVarint32();
            final int oldPos = this.pos;
            byte[] bytes;
            int tempPos;
            if (size <= this.bufferSize - oldPos && size > 0) {
                bytes = this.buffer;
                this.pos = oldPos + size;
                tempPos = oldPos;
            }
            else {
                if (size == 0) {
                    return "";
                }
                if (size <= this.bufferSize) {
                    this.refillBuffer(size);
                    bytes = this.buffer;
                    tempPos = 0;
                    this.pos = tempPos + size;
                }
                else {
                    bytes = this.readRawBytesSlowPath(size, false);
                    tempPos = 0;
                }
            }
            return Utf8.decodeUtf8(bytes, tempPos, size);
        }
        
        @Override
        public void readGroup(final int fieldNumber, final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
        }
        
        @Override
        public <T extends MessageLite> T readGroup(final int fieldNumber, final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
            return result;
        }
        
        @Deprecated
        @Override
        public void readUnknownGroup(final int fieldNumber, final MessageLite.Builder builder) throws IOException {
            this.readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public void readMessage(final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
        }
        
        @Override
        public <T extends MessageLite> T readMessage(final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
            return result;
        }
        
        @Override
        public ByteString readBytes() throws IOException {
            final int size = this.readRawVarint32();
            if (size <= this.bufferSize - this.pos && size > 0) {
                final ByteString result = ByteString.copyFrom(this.buffer, this.pos, size);
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return ByteString.EMPTY;
            }
            return this.readBytesSlowPath(size);
        }
        
        @Override
        public byte[] readByteArray() throws IOException {
            final int size = this.readRawVarint32();
            if (size <= this.bufferSize - this.pos && size > 0) {
                final byte[] result = Arrays.copyOfRange(this.buffer, this.pos, this.pos + size);
                this.pos += size;
                return result;
            }
            return this.readRawBytesSlowPath(size, false);
        }
        
        @Override
        public ByteBuffer readByteBuffer() throws IOException {
            final int size = this.readRawVarint32();
            if (size <= this.bufferSize - this.pos && size > 0) {
                final ByteBuffer result = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.pos, this.pos + size));
                this.pos += size;
                return result;
            }
            if (size == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            return ByteBuffer.wrap(this.readRawBytesSlowPath(size, true));
        }
        
        @Override
        public int readUInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readEnum() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readSFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public long readSFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(this.readRawVarint32());
        }
        
        @Override
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(this.readRawVarint64());
        }
        
        @Override
        public int readRawVarint32() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize != tempPos) {
                final byte[] buffer = this.buffer;
                int x;
                if ((x = buffer[tempPos++]) >= 0) {
                    this.pos = tempPos;
                    return x;
                }
                if (this.bufferSize - tempPos >= 9) {
                    if ((x ^= buffer[tempPos++] << 7) < 0) {
                        x ^= 0xFFFFFF80;
                    }
                    else if ((x ^= buffer[tempPos++] << 14) >= 0) {
                        x ^= 0x3F80;
                    }
                    else if ((x ^= buffer[tempPos++] << 21) < 0) {
                        x ^= 0xFFE03F80;
                    }
                    else {
                        final int y = buffer[tempPos++];
                        x ^= y << 28;
                        x ^= 0xFE03F80;
                        if (y < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0 && buffer[tempPos++] < 0) {
                            return (int)this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return (int)this.readRawVarint64SlowPath();
        }
        
        private void skipRawVarint() throws IOException {
            if (this.bufferSize - this.pos >= 10) {
                this.skipRawVarintFastPath();
            }
            else {
                this.skipRawVarintSlowPath();
            }
        }
        
        private void skipRawVarintFastPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.buffer[this.pos++] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        private void skipRawVarintSlowPath() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public long readRawVarint64() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize != tempPos) {
                final byte[] buffer = this.buffer;
                int y;
                if ((y = buffer[tempPos++]) >= 0) {
                    this.pos = tempPos;
                    return y;
                }
                if (this.bufferSize - tempPos >= 9) {
                    long x;
                    if ((y ^= buffer[tempPos++] << 7) < 0) {
                        x = (y ^ 0xFFFFFF80);
                    }
                    else if ((y ^= buffer[tempPos++] << 14) >= 0) {
                        x = (y ^ 0x3F80);
                    }
                    else if ((y ^= buffer[tempPos++] << 21) < 0) {
                        x = (y ^ 0xFFE03F80);
                    }
                    else if ((x = ((long)y ^ (long)buffer[tempPos++] << 28)) >= 0L) {
                        x ^= 0xFE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 35) < 0L) {
                        x ^= 0xFFFFFFF80FE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 42) >= 0L) {
                        x ^= 0x3F80FE03F80L;
                    }
                    else if ((x ^= (long)buffer[tempPos++] << 49) < 0L) {
                        x ^= 0xFFFE03F80FE03F80L;
                    }
                    else {
                        x ^= (long)buffer[tempPos++] << 56;
                        x ^= 0xFE03F80FE03F80L;
                        if (x < 0L && buffer[tempPos++] < 0L) {
                            return this.readRawVarint64SlowPath();
                        }
                    }
                    this.pos = tempPos;
                    return x;
                }
            }
            return this.readRawVarint64SlowPath();
        }
        
        @Override
        long readRawVarint64SlowPath() throws IOException {
            long result = 0L;
            for (int shift = 0; shift < 64; shift += 7) {
                final byte b = this.readRawByte();
                result |= (long)(b & 0x7F) << shift;
                if ((b & 0x80) == 0x0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public int readRawLittleEndian32() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize - tempPos < 4) {
                this.refillBuffer(4);
                tempPos = this.pos;
            }
            final byte[] buffer = this.buffer;
            this.pos = tempPos + 4;
            return (buffer[tempPos] & 0xFF) | (buffer[tempPos + 1] & 0xFF) << 8 | (buffer[tempPos + 2] & 0xFF) << 16 | (buffer[tempPos + 3] & 0xFF) << 24;
        }
        
        @Override
        public long readRawLittleEndian64() throws IOException {
            int tempPos = this.pos;
            if (this.bufferSize - tempPos < 8) {
                this.refillBuffer(8);
                tempPos = this.pos;
            }
            final byte[] buffer = this.buffer;
            this.pos = tempPos + 8;
            return ((long)buffer[tempPos] & 0xFFL) | ((long)buffer[tempPos + 1] & 0xFFL) << 8 | ((long)buffer[tempPos + 2] & 0xFFL) << 16 | ((long)buffer[tempPos + 3] & 0xFFL) << 24 | ((long)buffer[tempPos + 4] & 0xFFL) << 32 | ((long)buffer[tempPos + 5] & 0xFFL) << 40 | ((long)buffer[tempPos + 6] & 0xFFL) << 48 | ((long)buffer[tempPos + 7] & 0xFFL) << 56;
        }
        
        @Override
        public void enableAliasing(final boolean enabled) {
        }
        
        @Override
        public void resetSizeCounter() {
            this.totalBytesRetired = -this.pos;
        }
        
        @Override
        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            byteLimit += this.totalBytesRetired + this.pos;
            final int oldLimit = this.currentLimit;
            if (byteLimit > oldLimit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = byteLimit;
            this.recomputeBufferSizeAfterLimit();
            return oldLimit;
        }
        
        private void recomputeBufferSizeAfterLimit() {
            this.bufferSize += this.bufferSizeAfterLimit;
            final int bufferEnd = this.totalBytesRetired + this.bufferSize;
            if (bufferEnd > this.currentLimit) {
                this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
                this.bufferSize -= this.bufferSizeAfterLimit;
            }
            else {
                this.bufferSizeAfterLimit = 0;
            }
        }
        
        @Override
        public void popLimit(final int oldLimit) {
            this.currentLimit = oldLimit;
            this.recomputeBufferSizeAfterLimit();
        }
        
        @Override
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            final int currentAbsolutePosition = this.totalBytesRetired + this.pos;
            return this.currentLimit - currentAbsolutePosition;
        }
        
        @Override
        public boolean isAtEnd() throws IOException {
            return this.pos == this.bufferSize && !this.tryRefillBuffer(1);
        }
        
        @Override
        public int getTotalBytesRead() {
            return this.totalBytesRetired + this.pos;
        }
        
        private void refillBuffer(final int n) throws IOException {
            if (this.tryRefillBuffer(n)) {
                return;
            }
            if (n > this.sizeLimit - this.totalBytesRetired - this.pos) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        private boolean tryRefillBuffer(final int n) throws IOException {
            if (this.pos + n <= this.bufferSize) {
                throw new IllegalStateException("refillBuffer() called when " + n + " bytes were already available in buffer");
            }
            if (n > this.sizeLimit - this.totalBytesRetired - this.pos) {
                return false;
            }
            if (this.totalBytesRetired + this.pos + n > this.currentLimit) {
                return false;
            }
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            final int tempPos = this.pos;
            if (tempPos > 0) {
                if (this.bufferSize > tempPos) {
                    System.arraycopy(this.buffer, tempPos, this.buffer, 0, this.bufferSize - tempPos);
                }
                this.totalBytesRetired += tempPos;
                this.bufferSize -= tempPos;
                this.pos = 0;
            }
            final int bytesRead = this.input.read(this.buffer, this.bufferSize, Math.min(this.buffer.length - this.bufferSize, this.sizeLimit - this.totalBytesRetired - this.bufferSize));
            if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                throw new IllegalStateException(this.input.getClass() + "#read(byte[]) returned invalid result: " + bytesRead + "\nThe InputStream implementation is buggy.");
            }
            if (bytesRead > 0) {
                this.bufferSize += bytesRead;
                this.recomputeBufferSizeAfterLimit();
                return this.bufferSize >= n || this.tryRefillBuffer(n);
            }
            return false;
        }
        
        @Override
        public byte readRawByte() throws IOException {
            if (this.pos == this.bufferSize) {
                this.refillBuffer(1);
            }
            return this.buffer[this.pos++];
        }
        
        @Override
        public byte[] readRawBytes(final int size) throws IOException {
            final int tempPos = this.pos;
            if (size <= this.bufferSize - tempPos && size > 0) {
                this.pos = tempPos + size;
                return Arrays.copyOfRange(this.buffer, tempPos, tempPos + size);
            }
            return this.readRawBytesSlowPath(size, false);
        }
        
        private byte[] readRawBytesSlowPath(final int size, final boolean ensureNoLeakedReferences) throws IOException {
            final byte[] result = this.readRawBytesSlowPathOneChunk(size);
            if (result != null) {
                return ensureNoLeakedReferences ? result.clone() : result;
            }
            final int originalBufferPos = this.pos;
            final int bufferedBytes = this.bufferSize - this.pos;
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            final int sizeLeft = size - bufferedBytes;
            final List<byte[]> chunks = this.readRawBytesSlowPathRemainingChunks(sizeLeft);
            final byte[] bytes = new byte[size];
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
            int tempPos = bufferedBytes;
            for (final byte[] chunk : chunks) {
                System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
                tempPos += chunk.length;
            }
            return bytes;
        }
        
        private byte[] readRawBytesSlowPathOneChunk(final int size) throws IOException {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            final int currentMessageSize = this.totalBytesRetired + this.pos + size;
            if (currentMessageSize - this.sizeLimit > 0) {
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
            if (currentMessageSize > this.currentLimit) {
                this.skipRawBytes(this.currentLimit - this.totalBytesRetired - this.pos);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            final int bufferedBytes = this.bufferSize - this.pos;
            final int sizeLeft = size - bufferedBytes;
            if (sizeLeft < 4096 || sizeLeft <= this.input.available()) {
                final byte[] bytes = new byte[size];
                System.arraycopy(this.buffer, this.pos, bytes, 0, bufferedBytes);
                this.totalBytesRetired += this.bufferSize;
                this.pos = 0;
                this.bufferSize = 0;
                int n;
                for (int tempPos = bufferedBytes; tempPos < bytes.length; tempPos += n) {
                    n = this.input.read(bytes, tempPos, size - tempPos);
                    if (n == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n;
                }
                return bytes;
            }
            return null;
        }
        
        private List<byte[]> readRawBytesSlowPathRemainingChunks(int sizeLeft) throws IOException {
            final List<byte[]> chunks = new ArrayList<byte[]>();
            while (sizeLeft > 0) {
                final byte[] chunk = new byte[Math.min(sizeLeft, 4096)];
                int n;
                for (int tempPos = 0; tempPos < chunk.length; tempPos += n) {
                    n = this.input.read(chunk, tempPos, chunk.length - tempPos);
                    if (n == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n;
                }
                sizeLeft -= chunk.length;
                chunks.add(chunk);
            }
            return chunks;
        }
        
        private ByteString readBytesSlowPath(final int size) throws IOException {
            final byte[] result = this.readRawBytesSlowPathOneChunk(size);
            if (result != null) {
                return ByteString.copyFrom(result);
            }
            final int originalBufferPos = this.pos;
            final int bufferedBytes = this.bufferSize - this.pos;
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            final int sizeLeft = size - bufferedBytes;
            final List<byte[]> chunks = this.readRawBytesSlowPathRemainingChunks(sizeLeft);
            final byte[] bytes = new byte[size];
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, bufferedBytes);
            int tempPos = bufferedBytes;
            for (final byte[] chunk : chunks) {
                System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
                tempPos += chunk.length;
            }
            return ByteString.wrap(bytes);
        }
        
        @Override
        public void skipRawBytes(final int size) throws IOException {
            if (size <= this.bufferSize - this.pos && size >= 0) {
                this.pos += size;
            }
            else {
                this.skipRawBytesSlowPath(size);
            }
        }
        
        private void skipRawBytesSlowPath(final int size) throws IOException {
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (this.totalBytesRetired + this.pos + size > this.currentLimit) {
                this.skipRawBytes(this.currentLimit - this.totalBytesRetired - this.pos);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            int totalSkipped = 0;
            if (this.refillCallback == null) {
                this.totalBytesRetired += this.pos;
                totalSkipped = this.bufferSize - this.pos;
                this.bufferSize = 0;
                this.pos = 0;
                try {
                    while (totalSkipped < size) {
                        final int toSkip = size - totalSkipped;
                        final long skipped = this.input.skip(toSkip);
                        if (skipped < 0L || skipped > toSkip) {
                            throw new IllegalStateException(this.input.getClass() + "#skip returned invalid result: " + skipped + "\nThe InputStream implementation is buggy.");
                        }
                        if (skipped == 0L) {
                            break;
                        }
                        totalSkipped += (int)skipped;
                    }
                }
                finally {
                    this.totalBytesRetired += totalSkipped;
                    this.recomputeBufferSizeAfterLimit();
                }
            }
            if (totalSkipped < size) {
                int tempPos = this.bufferSize - this.pos;
                this.pos = this.bufferSize;
                this.refillBuffer(1);
                while (size - tempPos > this.bufferSize) {
                    tempPos += this.bufferSize;
                    this.pos = this.bufferSize;
                    this.refillBuffer(1);
                }
                this.pos = size - tempPos;
            }
        }
        
        private class SkippedDataSink implements RefillCallback
        {
            private int lastPos;
            private ByteArrayOutputStream byteArrayStream;
            
            private SkippedDataSink() {
                this.lastPos = StreamDecoder.this.pos;
            }
            
            @Override
            public void onRefill() {
                if (this.byteArrayStream == null) {
                    this.byteArrayStream = new ByteArrayOutputStream();
                }
                this.byteArrayStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                this.lastPos = 0;
            }
            
            ByteBuffer getSkippedData() {
                if (this.byteArrayStream == null) {
                    return ByteBuffer.wrap(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos - this.lastPos);
                }
                this.byteArrayStream.write(StreamDecoder.this.buffer, this.lastPos, StreamDecoder.this.pos);
                return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
            }
        }
        
        private interface RefillCallback
        {
            void onRefill();
        }
    }
    
    private static final class IterableDirectByteBufferDecoder extends CodedInputStream
    {
        private Iterable<ByteBuffer> input;
        private Iterator<ByteBuffer> iterator;
        private ByteBuffer currentByteBuffer;
        private boolean immutable;
        private boolean enableAliasing;
        private int totalBufferSize;
        private int bufferSizeAfterCurrentLimit;
        private int currentLimit;
        private int lastTag;
        private int totalBytesRead;
        private int startOffset;
        private long currentByteBufferPos;
        private long currentByteBufferStartPos;
        private long currentAddress;
        private long currentByteBufferLimit;
        
        private IterableDirectByteBufferDecoder(final Iterable<ByteBuffer> inputBufs, final int size, final boolean immutableFlag) {
            super(null);
            this.currentLimit = Integer.MAX_VALUE;
            this.totalBufferSize = size;
            this.input = inputBufs;
            this.iterator = this.input.iterator();
            this.immutable = immutableFlag;
            final int n = 0;
            this.totalBytesRead = n;
            this.startOffset = n;
            if (size == 0) {
                this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
                this.currentByteBufferPos = 0L;
                this.currentByteBufferStartPos = 0L;
                this.currentByteBufferLimit = 0L;
                this.currentAddress = 0L;
            }
            else {
                this.tryGetNextByteBuffer();
            }
        }
        
        private void getNextByteBuffer() throws InvalidProtocolBufferException {
            if (!this.iterator.hasNext()) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.tryGetNextByteBuffer();
        }
        
        private void tryGetNextByteBuffer() {
            this.currentByteBuffer = this.iterator.next();
            this.totalBytesRead += (int)(this.currentByteBufferPos - this.currentByteBufferStartPos);
            this.currentByteBufferPos = this.currentByteBuffer.position();
            this.currentByteBufferStartPos = this.currentByteBufferPos;
            this.currentByteBufferLimit = this.currentByteBuffer.limit();
            this.currentAddress = UnsafeUtil.addressOffset(this.currentByteBuffer);
            this.currentByteBufferPos += this.currentAddress;
            this.currentByteBufferStartPos += this.currentAddress;
            this.currentByteBufferLimit += this.currentAddress;
        }
        
        @Override
        public int readTag() throws IOException {
            if (this.isAtEnd()) {
                return this.lastTag = 0;
            }
            this.lastTag = this.readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }
        
        @Override
        public void checkLastTagWas(final int value) throws InvalidProtocolBufferException {
            if (this.lastTag != value) {
                throw InvalidProtocolBufferException.invalidEndTag();
            }
        }
        
        @Override
        public int getLastTag() {
            return this.lastTag;
        }
        
        @Override
        public boolean skipField(final int tag) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    this.skipRawVarint();
                    return true;
                }
                case 1: {
                    this.skipRawBytes(8);
                    return true;
                }
                case 2: {
                    this.skipRawBytes(this.readRawVarint32());
                    return true;
                }
                case 3: {
                    this.skipMessage();
                    this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    this.skipRawBytes(4);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public boolean skipField(final int tag, final CodedOutputStream output) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    final long value = this.readInt64();
                    output.writeRawVarint32(tag);
                    output.writeUInt64NoTag(value);
                    return true;
                }
                case 1: {
                    final long value = this.readRawLittleEndian64();
                    output.writeRawVarint32(tag);
                    output.writeFixed64NoTag(value);
                    return true;
                }
                case 2: {
                    final ByteString value2 = this.readBytes();
                    output.writeRawVarint32(tag);
                    output.writeBytesNoTag(value2);
                    return true;
                }
                case 3: {
                    output.writeRawVarint32(tag);
                    this.skipMessage(output);
                    final int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                    this.checkLastTagWas(endtag);
                    output.writeRawVarint32(endtag);
                    return true;
                }
                case 4: {
                    return false;
                }
                case 5: {
                    final int value3 = this.readRawLittleEndian32();
                    output.writeRawVarint32(tag);
                    output.writeFixed32NoTag(value3);
                    return true;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        
        @Override
        public void skipMessage() throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag));
        }
        
        @Override
        public void skipMessage(final CodedOutputStream output) throws IOException {
            int tag;
            do {
                tag = this.readTag();
            } while (tag != 0 && this.skipField(tag, output));
        }
        
        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readRawLittleEndian64());
        }
        
        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readRawLittleEndian32());
        }
        
        @Override
        public long readUInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public long readInt64() throws IOException {
            return this.readRawVarint64();
        }
        
        @Override
        public int readInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public long readFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public boolean readBool() throws IOException {
            return this.readRawVarint64() != 0L;
        }
        
        @Override
        public String readString() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
                final String result = new String(bytes, Internal.UTF_8);
                this.currentByteBufferPos += size;
                return result;
            }
            if (size > 0 && size <= this.remaining()) {
                final byte[] bytes = new byte[size];
                this.readRawBytesTo(bytes, 0, size);
                final String result = new String(bytes, Internal.UTF_8);
                return result;
            }
            if (size == 0) {
                return "";
            }
            if (size < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public String readStringRequireUtf8() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                final int bufferPos = (int)(this.currentByteBufferPos - this.currentByteBufferStartPos);
                final String result = Utf8.decodeUtf8(this.currentByteBuffer, bufferPos, size);
                this.currentByteBufferPos += size;
                return result;
            }
            if (size >= 0 && size <= this.remaining()) {
                final byte[] bytes = new byte[size];
                this.readRawBytesTo(bytes, 0, size);
                return Utf8.decodeUtf8(bytes, 0, size);
            }
            if (size == 0) {
                return "";
            }
            if (size <= 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        @Override
        public void readGroup(final int fieldNumber, final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
        }
        
        @Override
        public <T extends MessageLite> T readGroup(final int fieldNumber, final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
            --this.recursionDepth;
            return result;
        }
        
        @Deprecated
        @Override
        public void readUnknownGroup(final int fieldNumber, final MessageLite.Builder builder) throws IOException {
            this.readGroup(fieldNumber, builder, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public void readMessage(final MessageLite.Builder builder, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
        }
        
        @Override
        public <T extends MessageLite> T readMessage(final Parser<T> parser, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int length = this.readRawVarint32();
            if (this.recursionDepth >= this.recursionLimit) {
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            }
            final int oldLimit = this.pushLimit(length);
            ++this.recursionDepth;
            final T result = parser.parsePartialFrom(this, extensionRegistry);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(oldLimit);
            return result;
        }
        
        @Override
        public ByteString readBytes() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                if (this.immutable && this.enableAliasing) {
                    final int idx = (int)(this.currentByteBufferPos - this.currentAddress);
                    final ByteString result = ByteString.wrap(this.slice(idx, idx + size));
                    this.currentByteBufferPos += size;
                    return result;
                }
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
                this.currentByteBufferPos += size;
                return ByteString.wrap(bytes);
            }
            else {
                if (size > 0 && size <= this.remaining()) {
                    final byte[] temp = new byte[size];
                    this.readRawBytesTo(temp, 0, size);
                    return ByteString.wrap(temp);
                }
                if (size == 0) {
                    return ByteString.EMPTY;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        
        @Override
        public byte[] readByteArray() throws IOException {
            return this.readRawBytes(this.readRawVarint32());
        }
        
        @Override
        public ByteBuffer readByteBuffer() throws IOException {
            final int size = this.readRawVarint32();
            if (size > 0 && size <= this.currentRemaining()) {
                if (!this.immutable && this.enableAliasing) {
                    this.currentByteBufferPos += size;
                    return this.slice((int)(this.currentByteBufferPos - this.currentAddress - size), (int)(this.currentByteBufferPos - this.currentAddress));
                }
                final byte[] bytes = new byte[size];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, size);
                this.currentByteBufferPos += size;
                return ByteBuffer.wrap(bytes);
            }
            else {
                if (size > 0 && size <= this.remaining()) {
                    final byte[] temp = new byte[size];
                    this.readRawBytesTo(temp, 0, size);
                    return ByteBuffer.wrap(temp);
                }
                if (size == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (size < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        
        @Override
        public int readUInt32() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readEnum() throws IOException {
            return this.readRawVarint32();
        }
        
        @Override
        public int readSFixed32() throws IOException {
            return this.readRawLittleEndian32();
        }
        
        @Override
        public long readSFixed64() throws IOException {
            return this.readRawLittleEndian64();
        }
        
        @Override
        public int readSInt32() throws IOException {
            return CodedInputStream.decodeZigZag32(this.readRawVarint32());
        }
        
        @Override
        public long readSInt64() throws IOException {
            return CodedInputStream.decodeZigZag64(this.readRawVarint64());
        }
        
        @Override
        public int readRawVarint32() throws IOException {
            long tempPos = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != this.currentByteBufferPos) {
                int x;
                if ((x = UnsafeUtil.getByte(tempPos++)) >= 0) {
                    ++this.currentByteBufferPos;
                    return x;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L) {
                    if ((x ^= UnsafeUtil.getByte(tempPos++) << 7) < 0) {
                        x ^= 0xFFFFFF80;
                    }
                    else if ((x ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0) {
                        x ^= 0x3F80;
                    }
                    else if ((x ^= UnsafeUtil.getByte(tempPos++) << 21) < 0) {
                        x ^= 0xFFE03F80;
                    }
                    else {
                        final int y = UnsafeUtil.getByte(tempPos++);
                        x ^= y << 28;
                        x ^= 0xFE03F80;
                        if (y < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0 && UnsafeUtil.getByte(tempPos++) < 0) {
                            return (int)this.readRawVarint64SlowPath();
                        }
                    }
                    this.currentByteBufferPos = tempPos;
                    return x;
                }
            }
            return (int)this.readRawVarint64SlowPath();
        }
        
        @Override
        public long readRawVarint64() throws IOException {
            long tempPos = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != this.currentByteBufferPos) {
                int y;
                if ((y = UnsafeUtil.getByte(tempPos++)) >= 0) {
                    ++this.currentByteBufferPos;
                    return y;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L) {
                    long x;
                    if ((y ^= UnsafeUtil.getByte(tempPos++) << 7) < 0) {
                        x = (y ^ 0xFFFFFF80);
                    }
                    else if ((y ^= UnsafeUtil.getByte(tempPos++) << 14) >= 0) {
                        x = (y ^ 0x3F80);
                    }
                    else if ((y ^= UnsafeUtil.getByte(tempPos++) << 21) < 0) {
                        x = (y ^ 0xFFE03F80);
                    }
                    else if ((x = ((long)y ^ (long)UnsafeUtil.getByte(tempPos++) << 28)) >= 0L) {
                        x ^= 0xFE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 35) < 0L) {
                        x ^= 0xFFFFFFF80FE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 42) >= 0L) {
                        x ^= 0x3F80FE03F80L;
                    }
                    else if ((x ^= (long)UnsafeUtil.getByte(tempPos++) << 49) < 0L) {
                        x ^= 0xFFFE03F80FE03F80L;
                    }
                    else {
                        x ^= (long)UnsafeUtil.getByte(tempPos++) << 56;
                        x ^= 0xFE03F80FE03F80L;
                        if (x < 0L && UnsafeUtil.getByte(tempPos++) < 0L) {
                            return this.readRawVarint64SlowPath();
                        }
                    }
                    this.currentByteBufferPos = tempPos;
                    return x;
                }
            }
            return this.readRawVarint64SlowPath();
        }
        
        @Override
        long readRawVarint64SlowPath() throws IOException {
            long result = 0L;
            for (int shift = 0; shift < 64; shift += 7) {
                final byte b = this.readRawByte();
                result |= (long)(b & 0x7F) << shift;
                if ((b & 0x80) == 0x0) {
                    return result;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        @Override
        public int readRawLittleEndian32() throws IOException {
            if (this.currentRemaining() >= 4L) {
                final long tempPos = this.currentByteBufferPos;
                this.currentByteBufferPos += 4L;
                return (UnsafeUtil.getByte(tempPos) & 0xFF) | (UnsafeUtil.getByte(tempPos + 1L) & 0xFF) << 8 | (UnsafeUtil.getByte(tempPos + 2L) & 0xFF) << 16 | (UnsafeUtil.getByte(tempPos + 3L) & 0xFF) << 24;
            }
            return (this.readRawByte() & 0xFF) | (this.readRawByte() & 0xFF) << 8 | (this.readRawByte() & 0xFF) << 16 | (this.readRawByte() & 0xFF) << 24;
        }
        
        @Override
        public long readRawLittleEndian64() throws IOException {
            if (this.currentRemaining() >= 8L) {
                final long tempPos = this.currentByteBufferPos;
                this.currentByteBufferPos += 8L;
                return ((long)UnsafeUtil.getByte(tempPos) & 0xFFL) | ((long)UnsafeUtil.getByte(tempPos + 1L) & 0xFFL) << 8 | ((long)UnsafeUtil.getByte(tempPos + 2L) & 0xFFL) << 16 | ((long)UnsafeUtil.getByte(tempPos + 3L) & 0xFFL) << 24 | ((long)UnsafeUtil.getByte(tempPos + 4L) & 0xFFL) << 32 | ((long)UnsafeUtil.getByte(tempPos + 5L) & 0xFFL) << 40 | ((long)UnsafeUtil.getByte(tempPos + 6L) & 0xFFL) << 48 | ((long)UnsafeUtil.getByte(tempPos + 7L) & 0xFFL) << 56;
            }
            return ((long)this.readRawByte() & 0xFFL) | ((long)this.readRawByte() & 0xFFL) << 8 | ((long)this.readRawByte() & 0xFFL) << 16 | ((long)this.readRawByte() & 0xFFL) << 24 | ((long)this.readRawByte() & 0xFFL) << 32 | ((long)this.readRawByte() & 0xFFL) << 40 | ((long)this.readRawByte() & 0xFFL) << 48 | ((long)this.readRawByte() & 0xFFL) << 56;
        }
        
        @Override
        public void enableAliasing(final boolean enabled) {
            this.enableAliasing = enabled;
        }
        
        @Override
        public void resetSizeCounter() {
            this.startOffset = (int)(this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos);
        }
        
        @Override
        public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
            if (byteLimit < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            byteLimit += this.getTotalBytesRead();
            final int oldLimit = this.currentLimit;
            if (byteLimit > oldLimit) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = byteLimit;
            this.recomputeBufferSizeAfterLimit();
            return oldLimit;
        }
        
        private void recomputeBufferSizeAfterLimit() {
            this.totalBufferSize += this.bufferSizeAfterCurrentLimit;
            final int bufferEnd = this.totalBufferSize - this.startOffset;
            if (bufferEnd > this.currentLimit) {
                this.bufferSizeAfterCurrentLimit = bufferEnd - this.currentLimit;
                this.totalBufferSize -= this.bufferSizeAfterCurrentLimit;
            }
            else {
                this.bufferSizeAfterCurrentLimit = 0;
            }
        }
        
        @Override
        public void popLimit(final int oldLimit) {
            this.currentLimit = oldLimit;
            this.recomputeBufferSizeAfterLimit();
        }
        
        @Override
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - this.getTotalBytesRead();
        }
        
        @Override
        public boolean isAtEnd() throws IOException {
            return this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos == this.totalBufferSize;
        }
        
        @Override
        public int getTotalBytesRead() {
            return (int)(this.totalBytesRead - this.startOffset + this.currentByteBufferPos - this.currentByteBufferStartPos);
        }
        
        @Override
        public byte readRawByte() throws IOException {
            if (this.currentRemaining() == 0L) {
                this.getNextByteBuffer();
            }
            return UnsafeUtil.getByte(this.currentByteBufferPos++);
        }
        
        @Override
        public byte[] readRawBytes(final int length) throws IOException {
            if (length >= 0 && length <= this.currentRemaining()) {
                final byte[] bytes = new byte[length];
                UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, 0L, length);
                this.currentByteBufferPos += length;
                return bytes;
            }
            if (length >= 0 && length <= this.remaining()) {
                final byte[] bytes = new byte[length];
                this.readRawBytesTo(bytes, 0, length);
                return bytes;
            }
            if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (length == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        
        private void readRawBytesTo(final byte[] bytes, final int offset, final int length) throws IOException {
            if (length >= 0 && length <= this.remaining()) {
                int bytesToCopy;
                for (int l = length; l > 0; l -= bytesToCopy, this.currentByteBufferPos += bytesToCopy) {
                    if (this.currentRemaining() == 0L) {
                        this.getNextByteBuffer();
                    }
                    bytesToCopy = Math.min(l, (int)this.currentRemaining());
                    UnsafeUtil.copyMemory(this.currentByteBufferPos, bytes, length - l + offset, bytesToCopy);
                }
                return;
            }
            if (length > 0) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (length == 0) {
                return;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        
        @Override
        public void skipRawBytes(final int length) throws IOException {
            if (length >= 0 && length <= this.totalBufferSize - this.totalBytesRead - this.currentByteBufferPos + this.currentByteBufferStartPos) {
                int rl;
                for (int l = length; l > 0; l -= rl, this.currentByteBufferPos += rl) {
                    if (this.currentRemaining() == 0L) {
                        this.getNextByteBuffer();
                    }
                    rl = Math.min(l, (int)this.currentRemaining());
                }
                return;
            }
            if (length < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        
        private void skipRawVarint() throws IOException {
            for (int i = 0; i < 10; ++i) {
                if (this.readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.malformedVarint();
        }
        
        private int remaining() {
            return (int)(this.totalBufferSize - this.totalBytesRead - this.currentByteBufferPos + this.currentByteBufferStartPos);
        }
        
        private long currentRemaining() {
            return this.currentByteBufferLimit - this.currentByteBufferPos;
        }
        
        private ByteBuffer slice(final int begin, final int end) throws IOException {
            final int prevPos = this.currentByteBuffer.position();
            final int prevLimit = this.currentByteBuffer.limit();
            try {
                this.currentByteBuffer.position(begin);
                this.currentByteBuffer.limit(end);
                return this.currentByteBuffer.slice();
            }
            catch (IllegalArgumentException e) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            finally {
                this.currentByteBuffer.position(prevPos);
                this.currentByteBuffer.limit(prevLimit);
            }
        }
    }
}
