package com.google.protobuf;

import java.util.*;
import java.nio.charset.*;
import java.io.*;
import java.nio.*;

final class NioByteString extends LeafByteString
{
    private final ByteBuffer buffer;
    
    NioByteString(final ByteBuffer buffer) {
        Internal.checkNotNull(buffer, "buffer");
        this.buffer = buffer.slice().order(ByteOrder.nativeOrder());
    }
    
    private Object writeReplace() {
        return ByteString.copyFrom(this.buffer.slice());
    }
    
    private void readObject(final ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
    }
    
    @Override
    public byte byteAt(final int index) {
        try {
            return this.buffer.get(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }
    
    public byte internalByteAt(final int index) {
        return this.byteAt(index);
    }
    
    @Override
    public int size() {
        return this.buffer.remaining();
    }
    
    @Override
    public ByteString substring(final int beginIndex, final int endIndex) {
        try {
            final ByteBuffer slice = this.slice(beginIndex, endIndex);
            return new NioByteString(slice);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }
    
    @Override
    protected void copyToInternal(final byte[] target, final int sourceOffset, final int targetOffset, final int numberToCopy) {
        final ByteBuffer slice = this.buffer.slice();
        slice.position(sourceOffset);
        slice.get(target, targetOffset, numberToCopy);
    }
    
    @Override
    public void copyTo(final ByteBuffer target) {
        target.put(this.buffer.slice());
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.toByteArray());
    }
    
    @Override
    boolean equalsRange(final ByteString other, final int offset, final int length) {
        return this.substring(0, length).equals(other.substring(offset, offset + length));
    }
    
    @Override
    void writeToInternal(final OutputStream out, final int sourceOffset, final int numberToWrite) throws IOException {
        if (this.buffer.hasArray()) {
            final int bufferOffset = this.buffer.arrayOffset() + this.buffer.position() + sourceOffset;
            out.write(this.buffer.array(), bufferOffset, numberToWrite);
            return;
        }
        ByteBufferWriter.write(this.slice(sourceOffset, sourceOffset + numberToWrite), out);
    }
    
    @Override
    void writeTo(final ByteOutput output) throws IOException {
        output.writeLazy(this.buffer.slice());
    }
    
    @Override
    public ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }
    
    @Override
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        return Collections.singletonList(this.asReadOnlyByteBuffer());
    }
    
    @Override
    protected String toStringInternal(final Charset charset) {
        byte[] bytes;
        int offset;
        int length;
        if (this.buffer.hasArray()) {
            bytes = this.buffer.array();
            offset = this.buffer.arrayOffset() + this.buffer.position();
            length = this.buffer.remaining();
        }
        else {
            bytes = this.toByteArray();
            offset = 0;
            length = bytes.length;
        }
        return new String(bytes, offset, length, charset);
    }
    
    @Override
    public boolean isValidUtf8() {
        return Utf8.isValidUtf8(this.buffer);
    }
    
    @Override
    protected int partialIsValidUtf8(final int state, final int offset, final int length) {
        return Utf8.partialIsValidUtf8(state, this.buffer, offset, offset + length);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        final ByteString otherString = (ByteString)other;
        if (this.size() != otherString.size()) {
            return false;
        }
        if (this.size() == 0) {
            return true;
        }
        if (other instanceof NioByteString) {
            return this.buffer.equals(((NioByteString)other).buffer);
        }
        if (other instanceof RopeByteString) {
            return other.equals(this);
        }
        return this.buffer.equals(otherString.asReadOnlyByteBuffer());
    }
    
    @Override
    protected int partialHash(int h, final int offset, final int length) {
        for (int i = offset; i < offset + length; ++i) {
            h = h * 31 + this.buffer.get(i);
        }
        return h;
    }
    
    @Override
    public InputStream newInput() {
        return new InputStream() {
            private final ByteBuffer buf = NioByteString.this.buffer.slice();
            
            @Override
            public void mark(final int readlimit) {
                this.buf.mark();
            }
            
            @Override
            public boolean markSupported() {
                return true;
            }
            
            @Override
            public void reset() throws IOException {
                try {
                    this.buf.reset();
                }
                catch (InvalidMarkException e) {
                    throw new IOException(e);
                }
            }
            
            @Override
            public int available() throws IOException {
                return this.buf.remaining();
            }
            
            @Override
            public int read() throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                return this.buf.get() & 0xFF;
            }
            
            @Override
            public int read(final byte[] bytes, final int off, int len) throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                len = Math.min(len, this.buf.remaining());
                this.buf.get(bytes, off, len);
                return len;
            }
        };
    }
    
    @Override
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(this.buffer, true);
    }
    
    private ByteBuffer slice(final int beginIndex, final int endIndex) {
        if (beginIndex < this.buffer.position() || endIndex > this.buffer.limit() || beginIndex > endIndex) {
            throw new IllegalArgumentException(String.format("Invalid indices [%d, %d]", beginIndex, endIndex));
        }
        final ByteBuffer slice = this.buffer.slice();
        slice.position(beginIndex - this.buffer.position());
        slice.limit(endIndex - this.buffer.position());
        return slice;
    }
}
