package com.google.protobuf;

import java.nio.*;
import java.nio.charset.*;
import java.util.*;
import java.io.*;

public abstract class ByteString implements Iterable<Byte>, Serializable
{
    static final int CONCATENATE_BY_COPY_SIZE = 128;
    static final int MIN_READ_FROM_CHUNK_SIZE = 256;
    static final int MAX_READ_FROM_CHUNK_SIZE = 8192;
    public static final ByteString EMPTY;
    private static final ByteArrayCopier byteArrayCopier;
    private int hash;
    private static final int UNSIGNED_BYTE_MASK = 255;
    private static final Comparator<ByteString> UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
    
    ByteString() {
        this.hash = 0;
    }
    
    public abstract byte byteAt(final int p0);
    
    abstract byte internalByteAt(final int p0);
    
    @Override
    public ByteIterator iterator() {
        return new AbstractByteIterator() {
            private int position = 0;
            private final int limit = ByteString.this.size();
            
            @Override
            public boolean hasNext() {
                return this.position < this.limit;
            }
            
            @Override
            public byte nextByte() {
                final int currentPos = this.position;
                if (currentPos >= this.limit) {
                    throw new NoSuchElementException();
                }
                this.position = currentPos + 1;
                return ByteString.this.internalByteAt(currentPos);
            }
        };
    }
    
    public abstract int size();
    
    public final boolean isEmpty() {
        return this.size() == 0;
    }
    
    private static int toInt(final byte value) {
        return value & 0xFF;
    }
    
    public static Comparator<ByteString> unsignedLexicographicalComparator() {
        return ByteString.UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
    }
    
    public final ByteString substring(final int beginIndex) {
        return this.substring(beginIndex, this.size());
    }
    
    public abstract ByteString substring(final int p0, final int p1);
    
    public final boolean startsWith(final ByteString prefix) {
        return this.size() >= prefix.size() && this.substring(0, prefix.size()).equals(prefix);
    }
    
    public final boolean endsWith(final ByteString suffix) {
        return this.size() >= suffix.size() && this.substring(this.size() - suffix.size()).equals(suffix);
    }
    
    public static ByteString copyFrom(final byte[] bytes, final int offset, final int size) {
        checkRange(offset, offset + size, bytes.length);
        return new LiteralByteString(ByteString.byteArrayCopier.copyFrom(bytes, offset, size));
    }
    
    public static ByteString copyFrom(final byte[] bytes) {
        return copyFrom(bytes, 0, bytes.length);
    }
    
    static ByteString wrap(final ByteBuffer buffer) {
        if (buffer.hasArray()) {
            final int offset = buffer.arrayOffset();
            return wrap(buffer.array(), offset + buffer.position(), buffer.remaining());
        }
        return new NioByteString(buffer);
    }
    
    static ByteString wrap(final byte[] bytes) {
        return new LiteralByteString(bytes);
    }
    
    static ByteString wrap(final byte[] bytes, final int offset, final int length) {
        return new BoundedByteString(bytes, offset, length);
    }
    
    public static ByteString copyFrom(final ByteBuffer bytes, final int size) {
        checkRange(0, size, bytes.remaining());
        final byte[] copy = new byte[size];
        bytes.get(copy);
        return new LiteralByteString(copy);
    }
    
    public static ByteString copyFrom(final ByteBuffer bytes) {
        return copyFrom(bytes, bytes.remaining());
    }
    
    public static ByteString copyFrom(final String text, final String charsetName) throws UnsupportedEncodingException {
        return new LiteralByteString(text.getBytes(charsetName));
    }
    
    public static ByteString copyFrom(final String text, final Charset charset) {
        return new LiteralByteString(text.getBytes(charset));
    }
    
    public static ByteString copyFromUtf8(final String text) {
        return new LiteralByteString(text.getBytes(Internal.UTF_8));
    }
    
    public static ByteString readFrom(final InputStream streamToDrain) throws IOException {
        return readFrom(streamToDrain, 256, 8192);
    }
    
    public static ByteString readFrom(final InputStream streamToDrain, final int chunkSize) throws IOException {
        return readFrom(streamToDrain, chunkSize, chunkSize);
    }
    
    public static ByteString readFrom(final InputStream streamToDrain, final int minChunkSize, final int maxChunkSize) throws IOException {
        final Collection<ByteString> results = new ArrayList<ByteString>();
        int chunkSize = minChunkSize;
        while (true) {
            final ByteString chunk = readChunk(streamToDrain, chunkSize);
            if (chunk == null) {
                break;
            }
            results.add(chunk);
            chunkSize = Math.min(chunkSize * 2, maxChunkSize);
        }
        return copyFrom(results);
    }
    
    private static ByteString readChunk(final InputStream in, final int chunkSize) throws IOException {
        final byte[] buf = new byte[chunkSize];
        int bytesRead;
        int count;
        for (bytesRead = 0; bytesRead < chunkSize; bytesRead += count) {
            count = in.read(buf, bytesRead, chunkSize - bytesRead);
            if (count == -1) {
                break;
            }
        }
        if (bytesRead == 0) {
            return null;
        }
        return copyFrom(buf, 0, bytesRead);
    }
    
    public final ByteString concat(final ByteString other) {
        if (Integer.MAX_VALUE - this.size() < other.size()) {
            throw new IllegalArgumentException("ByteString would be too long: " + this.size() + "+" + other.size());
        }
        return RopeByteString.concatenate(this, other);
    }
    
    public static ByteString copyFrom(final Iterable<ByteString> byteStrings) {
        int size;
        if (!(byteStrings instanceof Collection)) {
            int tempSize = 0;
            final Iterator<ByteString> iter = byteStrings.iterator();
            while (iter.hasNext()) {
                iter.next();
                ++tempSize;
            }
            size = tempSize;
        }
        else {
            size = ((Collection)byteStrings).size();
        }
        if (size == 0) {
            return ByteString.EMPTY;
        }
        return balancedConcat(byteStrings.iterator(), size);
    }
    
    private static ByteString balancedConcat(final Iterator<ByteString> iterator, final int length) {
        if (length < 1) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", length));
        }
        ByteString result;
        if (length == 1) {
            result = iterator.next();
        }
        else {
            final int halfLength = length >>> 1;
            final ByteString left = balancedConcat(iterator, halfLength);
            final ByteString right = balancedConcat(iterator, length - halfLength);
            result = left.concat(right);
        }
        return result;
    }
    
    public void copyTo(final byte[] target, final int offset) {
        this.copyTo(target, 0, offset, this.size());
    }
    
    @Deprecated
    public final void copyTo(final byte[] target, final int sourceOffset, final int targetOffset, final int numberToCopy) {
        checkRange(sourceOffset, sourceOffset + numberToCopy, this.size());
        checkRange(targetOffset, targetOffset + numberToCopy, target.length);
        if (numberToCopy > 0) {
            this.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        }
    }
    
    protected abstract void copyToInternal(final byte[] p0, final int p1, final int p2, final int p3);
    
    public abstract void copyTo(final ByteBuffer p0);
    
    public final byte[] toByteArray() {
        final int size = this.size();
        if (size == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[size];
        this.copyToInternal(result, 0, 0, size);
        return result;
    }
    
    public abstract void writeTo(final OutputStream p0) throws IOException;
    
    final void writeTo(final OutputStream out, final int sourceOffset, final int numberToWrite) throws IOException {
        checkRange(sourceOffset, sourceOffset + numberToWrite, this.size());
        if (numberToWrite > 0) {
            this.writeToInternal(out, sourceOffset, numberToWrite);
        }
    }
    
    abstract void writeToInternal(final OutputStream p0, final int p1, final int p2) throws IOException;
    
    abstract void writeTo(final ByteOutput p0) throws IOException;
    
    abstract void writeToReverse(final ByteOutput p0) throws IOException;
    
    public abstract ByteBuffer asReadOnlyByteBuffer();
    
    public abstract List<ByteBuffer> asReadOnlyByteBufferList();
    
    public final String toString(final String charsetName) throws UnsupportedEncodingException {
        try {
            return this.toString(Charset.forName(charsetName));
        }
        catch (UnsupportedCharsetException e) {
            final UnsupportedEncodingException exception = new UnsupportedEncodingException(charsetName);
            exception.initCause(e);
            throw exception;
        }
    }
    
    public final String toString(final Charset charset) {
        return (this.size() == 0) ? "" : this.toStringInternal(charset);
    }
    
    protected abstract String toStringInternal(final Charset p0);
    
    public final String toStringUtf8() {
        return this.toString(Internal.UTF_8);
    }
    
    public abstract boolean isValidUtf8();
    
    protected abstract int partialIsValidUtf8(final int p0, final int p1, final int p2);
    
    @Override
    public abstract boolean equals(final Object p0);
    
    @Override
    public final int hashCode() {
        int h = this.hash;
        if (h == 0) {
            final int size = this.size();
            h = this.partialHash(size, 0, size);
            if (h == 0) {
                h = 1;
            }
            this.hash = h;
        }
        return h;
    }
    
    public abstract InputStream newInput();
    
    public abstract CodedInputStream newCodedInput();
    
    public static Output newOutput(final int initialCapacity) {
        return new Output(initialCapacity);
    }
    
    public static Output newOutput() {
        return new Output(128);
    }
    
    static CodedBuilder newCodedBuilder(final int size) {
        return new CodedBuilder(size);
    }
    
    protected abstract int getTreeDepth();
    
    protected abstract boolean isBalanced();
    
    protected final int peekCachedHashCode() {
        return this.hash;
    }
    
    protected abstract int partialHash(final int p0, final int p1, final int p2);
    
    static void checkIndex(final int index, final int size) {
        if ((index | size - (index + 1)) >= 0) {
            return;
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + index);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + index + ", " + size);
    }
    
    static int checkRange(final int startIndex, final int endIndex, final int size) {
        final int length = endIndex - startIndex;
        if ((startIndex | endIndex | length | size - endIndex) >= 0) {
            return length;
        }
        if (startIndex < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + startIndex + " < 0");
        }
        if (endIndex < startIndex) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + startIndex + ", " + endIndex);
        }
        throw new IndexOutOfBoundsException("End index: " + endIndex + " >= " + size);
    }
    
    @Override
    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), this.size(), this.truncateAndEscapeForDisplay());
    }
    
    private String truncateAndEscapeForDisplay() {
        final int limit = 50;
        return (this.size() <= 50) ? TextFormatEscaper.escapeBytes(this) : (TextFormatEscaper.escapeBytes(this.substring(0, 47)) + "...");
    }
    
    static {
        EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
        byteArrayCopier = (Android.isOnAndroidDevice() ? new SystemByteArrayCopier() : new ArraysByteArrayCopier());
        UNSIGNED_LEXICOGRAPHICAL_COMPARATOR = new Comparator<ByteString>() {
            @Override
            public int compare(final ByteString former, final ByteString latter) {
                final ByteIterator formerBytes = former.iterator();
                final ByteIterator latterBytes = latter.iterator();
                while (formerBytes.hasNext() && latterBytes.hasNext()) {
                    final int result = Integer.compare(toInt(formerBytes.nextByte()), toInt(latterBytes.nextByte()));
                    if (result != 0) {
                        return result;
                    }
                }
                return Integer.compare(former.size(), latter.size());
            }
        };
    }
    
    private static final class SystemByteArrayCopier implements ByteArrayCopier
    {
        @Override
        public byte[] copyFrom(final byte[] bytes, final int offset, final int size) {
            final byte[] copy = new byte[size];
            System.arraycopy(bytes, offset, copy, 0, size);
            return copy;
        }
    }
    
    private static final class ArraysByteArrayCopier implements ByteArrayCopier
    {
        @Override
        public byte[] copyFrom(final byte[] bytes, final int offset, final int size) {
            return Arrays.copyOfRange(bytes, offset, offset + size);
        }
    }
    
    abstract static class AbstractByteIterator implements ByteIterator
    {
        @Override
        public final Byte next() {
            return this.nextByte();
        }
        
        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    abstract static class LeafByteString extends ByteString
    {
        @Override
        protected final int getTreeDepth() {
            return 0;
        }
        
        @Override
        protected final boolean isBalanced() {
            return true;
        }
        
        @Override
        void writeToReverse(final ByteOutput byteOutput) throws IOException {
            this.writeTo(byteOutput);
        }
        
        abstract boolean equalsRange(final ByteString p0, final int p1, final int p2);
    }
    
    public static final class Output extends OutputStream
    {
        private static final byte[] EMPTY_BYTE_ARRAY;
        private final int initialCapacity;
        private final ArrayList<ByteString> flushedBuffers;
        private int flushedBuffersTotalBytes;
        private byte[] buffer;
        private int bufferPos;
        
        Output(final int initialCapacity) {
            if (initialCapacity < 0) {
                throw new IllegalArgumentException("Buffer size < 0");
            }
            this.initialCapacity = initialCapacity;
            this.flushedBuffers = new ArrayList<ByteString>();
            this.buffer = new byte[initialCapacity];
        }
        
        @Override
        public synchronized void write(final int b) {
            if (this.bufferPos == this.buffer.length) {
                this.flushFullBuffer(1);
            }
            this.buffer[this.bufferPos++] = (byte)b;
        }
        
        @Override
        public synchronized void write(final byte[] b, int offset, int length) {
            if (length <= this.buffer.length - this.bufferPos) {
                System.arraycopy(b, offset, this.buffer, this.bufferPos, length);
                this.bufferPos += length;
            }
            else {
                final int copySize = this.buffer.length - this.bufferPos;
                System.arraycopy(b, offset, this.buffer, this.bufferPos, copySize);
                offset += copySize;
                length -= copySize;
                this.flushFullBuffer(length);
                System.arraycopy(b, offset, this.buffer, 0, length);
                this.bufferPos = length;
            }
        }
        
        public synchronized ByteString toByteString() {
            this.flushLastBuffer();
            return ByteString.copyFrom(this.flushedBuffers);
        }
        
        private byte[] copyArray(final byte[] buffer, final int length) {
            final byte[] result = new byte[length];
            System.arraycopy(buffer, 0, result, 0, Math.min(buffer.length, length));
            return result;
        }
        
        public void writeTo(final OutputStream out) throws IOException {
            final ByteString[] cachedFlushBuffers;
            final byte[] cachedBuffer;
            final int cachedBufferPos;
            synchronized (this) {
                cachedFlushBuffers = this.flushedBuffers.toArray(new ByteString[this.flushedBuffers.size()]);
                cachedBuffer = this.buffer;
                cachedBufferPos = this.bufferPos;
            }
            for (final ByteString byteString : cachedFlushBuffers) {
                byteString.writeTo(out);
            }
            out.write(this.copyArray(cachedBuffer, cachedBufferPos));
        }
        
        public synchronized int size() {
            return this.flushedBuffersTotalBytes + this.bufferPos;
        }
        
        public synchronized void reset() {
            this.flushedBuffers.clear();
            this.flushedBuffersTotalBytes = 0;
            this.bufferPos = 0;
        }
        
        @Override
        public String toString() {
            return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
        }
        
        private void flushFullBuffer(final int minSize) {
            this.flushedBuffers.add(new LiteralByteString(this.buffer));
            this.flushedBuffersTotalBytes += this.buffer.length;
            final int newSize = Math.max(this.initialCapacity, Math.max(minSize, this.flushedBuffersTotalBytes >>> 1));
            this.buffer = new byte[newSize];
            this.bufferPos = 0;
        }
        
        private void flushLastBuffer() {
            if (this.bufferPos < this.buffer.length) {
                if (this.bufferPos > 0) {
                    final byte[] bufferCopy = this.copyArray(this.buffer, this.bufferPos);
                    this.flushedBuffers.add(new LiteralByteString(bufferCopy));
                }
            }
            else {
                this.flushedBuffers.add(new LiteralByteString(this.buffer));
                this.buffer = Output.EMPTY_BYTE_ARRAY;
            }
            this.flushedBuffersTotalBytes += this.bufferPos;
            this.bufferPos = 0;
        }
        
        static {
            EMPTY_BYTE_ARRAY = new byte[0];
        }
    }
    
    static final class CodedBuilder
    {
        private final CodedOutputStream output;
        private final byte[] buffer;
        
        private CodedBuilder(final int size) {
            this.buffer = new byte[size];
            this.output = CodedOutputStream.newInstance(this.buffer);
        }
        
        public ByteString build() {
            this.output.checkNoSpaceLeft();
            return new LiteralByteString(this.buffer);
        }
        
        public CodedOutputStream getCodedOutput() {
            return this.output;
        }
    }
    
    private static class LiteralByteString extends LeafByteString
    {
        private static final long serialVersionUID = 1L;
        protected final byte[] bytes;
        
        LiteralByteString(final byte[] bytes) {
            if (bytes == null) {
                throw new NullPointerException();
            }
            this.bytes = bytes;
        }
        
        @Override
        public byte byteAt(final int index) {
            return this.bytes[index];
        }
        
        @Override
        byte internalByteAt(final int index) {
            return this.bytes[index];
        }
        
        @Override
        public int size() {
            return this.bytes.length;
        }
        
        @Override
        public final ByteString substring(final int beginIndex, final int endIndex) {
            final int length = ByteString.checkRange(beginIndex, endIndex, this.size());
            if (length == 0) {
                return ByteString.EMPTY;
            }
            return new BoundedByteString(this.bytes, this.getOffsetIntoBytes() + beginIndex, length);
        }
        
        @Override
        protected void copyToInternal(final byte[] target, final int sourceOffset, final int targetOffset, final int numberToCopy) {
            System.arraycopy(this.bytes, sourceOffset, target, targetOffset, numberToCopy);
        }
        
        @Override
        public final void copyTo(final ByteBuffer target) {
            target.put(this.bytes, this.getOffsetIntoBytes(), this.size());
        }
        
        @Override
        public final ByteBuffer asReadOnlyByteBuffer() {
            return ByteBuffer.wrap(this.bytes, this.getOffsetIntoBytes(), this.size()).asReadOnlyBuffer();
        }
        
        @Override
        public final List<ByteBuffer> asReadOnlyByteBufferList() {
            return Collections.singletonList(this.asReadOnlyByteBuffer());
        }
        
        @Override
        public final void writeTo(final OutputStream outputStream) throws IOException {
            outputStream.write(this.toByteArray());
        }
        
        @Override
        final void writeToInternal(final OutputStream outputStream, final int sourceOffset, final int numberToWrite) throws IOException {
            outputStream.write(this.bytes, this.getOffsetIntoBytes() + sourceOffset, numberToWrite);
        }
        
        @Override
        final void writeTo(final ByteOutput output) throws IOException {
            output.writeLazy(this.bytes, this.getOffsetIntoBytes(), this.size());
        }
        
        @Override
        protected final String toStringInternal(final Charset charset) {
            return new String(this.bytes, this.getOffsetIntoBytes(), this.size(), charset);
        }
        
        @Override
        public final boolean isValidUtf8() {
            final int offset = this.getOffsetIntoBytes();
            return Utf8.isValidUtf8(this.bytes, offset, offset + this.size());
        }
        
        @Override
        protected final int partialIsValidUtf8(final int state, final int offset, final int length) {
            final int index = this.getOffsetIntoBytes() + offset;
            return Utf8.partialIsValidUtf8(state, this.bytes, index, index + length);
        }
        
        @Override
        public final boolean equals(final Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof ByteString)) {
                return false;
            }
            if (this.size() != ((ByteString)other).size()) {
                return false;
            }
            if (this.size() == 0) {
                return true;
            }
            if (other instanceof LiteralByteString) {
                final LiteralByteString otherAsLiteral = (LiteralByteString)other;
                final int thisHash = this.peekCachedHashCode();
                final int thatHash = otherAsLiteral.peekCachedHashCode();
                return (thisHash == 0 || thatHash == 0 || thisHash == thatHash) && this.equalsRange((ByteString)other, 0, this.size());
            }
            return other.equals(this);
        }
        
        @Override
        final boolean equalsRange(final ByteString other, final int offset, final int length) {
            if (length > other.size()) {
                throw new IllegalArgumentException("Length too large: " + length + this.size());
            }
            if (offset + length > other.size()) {
                throw new IllegalArgumentException("Ran off end of other: " + offset + ", " + length + ", " + other.size());
            }
            if (other instanceof LiteralByteString) {
                final LiteralByteString lbsOther = (LiteralByteString)other;
                final byte[] thisBytes = this.bytes;
                final byte[] otherBytes = lbsOther.bytes;
                for (int thisLimit = this.getOffsetIntoBytes() + length, thisIndex = this.getOffsetIntoBytes(), otherIndex = lbsOther.getOffsetIntoBytes() + offset; thisIndex < thisLimit; ++thisIndex, ++otherIndex) {
                    if (thisBytes[thisIndex] != otherBytes[otherIndex]) {
                        return false;
                    }
                }
                return true;
            }
            return other.substring(offset, offset + length).equals(this.substring(0, length));
        }
        
        @Override
        protected final int partialHash(final int h, final int offset, final int length) {
            return Internal.partialHash(h, this.bytes, this.getOffsetIntoBytes() + offset, length);
        }
        
        @Override
        public final InputStream newInput() {
            return new ByteArrayInputStream(this.bytes, this.getOffsetIntoBytes(), this.size());
        }
        
        @Override
        public final CodedInputStream newCodedInput() {
            return CodedInputStream.newInstance(this.bytes, this.getOffsetIntoBytes(), this.size(), true);
        }
        
        protected int getOffsetIntoBytes() {
            return 0;
        }
    }
    
    private static final class BoundedByteString extends LiteralByteString
    {
        private final int bytesOffset;
        private final int bytesLength;
        private static final long serialVersionUID = 1L;
        
        BoundedByteString(final byte[] bytes, final int offset, final int length) {
            super(bytes);
            ByteString.checkRange(offset, offset + length, bytes.length);
            this.bytesOffset = offset;
            this.bytesLength = length;
        }
        
        @Override
        public byte byteAt(final int index) {
            ByteString.checkIndex(index, this.size());
            return this.bytes[this.bytesOffset + index];
        }
        
        @Override
        byte internalByteAt(final int index) {
            return this.bytes[this.bytesOffset + index];
        }
        
        @Override
        public int size() {
            return this.bytesLength;
        }
        
        @Override
        protected int getOffsetIntoBytes() {
            return this.bytesOffset;
        }
        
        @Override
        protected void copyToInternal(final byte[] target, final int sourceOffset, final int targetOffset, final int numberToCopy) {
            System.arraycopy(this.bytes, this.getOffsetIntoBytes() + sourceOffset, target, targetOffset, numberToCopy);
        }
        
        Object writeReplace() {
            return ByteString.wrap(this.toByteArray());
        }
        
        private void readObject(final ObjectInputStream in) throws IOException {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }
    }
    
    public interface ByteIterator extends Iterator<Byte>
    {
        byte nextByte();
    }
    
    private interface ByteArrayCopier
    {
        byte[] copyFrom(final byte[] p0, final int p1, final int p2);
    }
}
