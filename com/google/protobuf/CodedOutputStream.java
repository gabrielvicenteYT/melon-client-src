package com.google.protobuf;

import java.io.*;
import java.util.logging.*;
import java.nio.*;

public abstract class CodedOutputStream extends ByteOutput
{
    private static final Logger logger;
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    CodedOutputStreamWriter wrapper;
    @Deprecated
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    private boolean serializationDeterministic;
    
    static int computePreferredBufferSize(final int dataLength) {
        if (dataLength > 4096) {
            return 4096;
        }
        return dataLength;
    }
    
    public static CodedOutputStream newInstance(final OutputStream output) {
        return newInstance(output, 4096);
    }
    
    public static CodedOutputStream newInstance(final OutputStream output, final int bufferSize) {
        return new OutputStreamEncoder(output, bufferSize);
    }
    
    public static CodedOutputStream newInstance(final byte[] flatArray) {
        return newInstance(flatArray, 0, flatArray.length);
    }
    
    public static CodedOutputStream newInstance(final byte[] flatArray, final int offset, final int length) {
        return new ArrayEncoder(flatArray, offset, length);
    }
    
    public static CodedOutputStream newInstance(final ByteBuffer buffer) {
        if (buffer.hasArray()) {
            return new HeapNioEncoder(buffer);
        }
        if (buffer.isDirect() && !buffer.isReadOnly()) {
            return UnsafeDirectNioEncoder.isSupported() ? newUnsafeInstance(buffer) : newSafeInstance(buffer);
        }
        throw new IllegalArgumentException("ByteBuffer is read-only");
    }
    
    static CodedOutputStream newUnsafeInstance(final ByteBuffer buffer) {
        return new UnsafeDirectNioEncoder(buffer);
    }
    
    static CodedOutputStream newSafeInstance(final ByteBuffer buffer) {
        return new SafeDirectNioEncoder(buffer);
    }
    
    public void useDeterministicSerialization() {
        this.serializationDeterministic = true;
    }
    
    boolean isSerializationDeterministic() {
        return this.serializationDeterministic;
    }
    
    @Deprecated
    public static CodedOutputStream newInstance(final ByteBuffer byteBuffer, final int unused) {
        return newInstance(byteBuffer);
    }
    
    static CodedOutputStream newInstance(final ByteOutput byteOutput, final int bufferSize) {
        if (bufferSize < 0) {
            throw new IllegalArgumentException("bufferSize must be positive");
        }
        return new ByteOutputEncoder(byteOutput, bufferSize);
    }
    
    private CodedOutputStream() {
    }
    
    public abstract void writeTag(final int p0, final int p1) throws IOException;
    
    public abstract void writeInt32(final int p0, final int p1) throws IOException;
    
    public abstract void writeUInt32(final int p0, final int p1) throws IOException;
    
    public final void writeSInt32(final int fieldNumber, final int value) throws IOException {
        this.writeUInt32(fieldNumber, encodeZigZag32(value));
    }
    
    public abstract void writeFixed32(final int p0, final int p1) throws IOException;
    
    public final void writeSFixed32(final int fieldNumber, final int value) throws IOException {
        this.writeFixed32(fieldNumber, value);
    }
    
    public final void writeInt64(final int fieldNumber, final long value) throws IOException {
        this.writeUInt64(fieldNumber, value);
    }
    
    public abstract void writeUInt64(final int p0, final long p1) throws IOException;
    
    public final void writeSInt64(final int fieldNumber, final long value) throws IOException {
        this.writeUInt64(fieldNumber, encodeZigZag64(value));
    }
    
    public abstract void writeFixed64(final int p0, final long p1) throws IOException;
    
    public final void writeSFixed64(final int fieldNumber, final long value) throws IOException {
        this.writeFixed64(fieldNumber, value);
    }
    
    public final void writeFloat(final int fieldNumber, final float value) throws IOException {
        this.writeFixed32(fieldNumber, Float.floatToRawIntBits(value));
    }
    
    public final void writeDouble(final int fieldNumber, final double value) throws IOException {
        this.writeFixed64(fieldNumber, Double.doubleToRawLongBits(value));
    }
    
    public abstract void writeBool(final int p0, final boolean p1) throws IOException;
    
    public final void writeEnum(final int fieldNumber, final int value) throws IOException {
        this.writeInt32(fieldNumber, value);
    }
    
    public abstract void writeString(final int p0, final String p1) throws IOException;
    
    public abstract void writeBytes(final int p0, final ByteString p1) throws IOException;
    
    public abstract void writeByteArray(final int p0, final byte[] p1) throws IOException;
    
    public abstract void writeByteArray(final int p0, final byte[] p1, final int p2, final int p3) throws IOException;
    
    public abstract void writeByteBuffer(final int p0, final ByteBuffer p1) throws IOException;
    
    public final void writeRawByte(final byte value) throws IOException {
        this.write(value);
    }
    
    public final void writeRawByte(final int value) throws IOException {
        this.write((byte)value);
    }
    
    public final void writeRawBytes(final byte[] value) throws IOException {
        this.write(value, 0, value.length);
    }
    
    public final void writeRawBytes(final byte[] value, final int offset, final int length) throws IOException {
        this.write(value, offset, length);
    }
    
    public final void writeRawBytes(final ByteString value) throws IOException {
        value.writeTo(this);
    }
    
    public abstract void writeRawBytes(final ByteBuffer p0) throws IOException;
    
    public abstract void writeMessage(final int p0, final MessageLite p1) throws IOException;
    
    abstract void writeMessage(final int p0, final MessageLite p1, final Schema p2) throws IOException;
    
    public abstract void writeMessageSetExtension(final int p0, final MessageLite p1) throws IOException;
    
    public abstract void writeRawMessageSetExtension(final int p0, final ByteString p1) throws IOException;
    
    public abstract void writeInt32NoTag(final int p0) throws IOException;
    
    public abstract void writeUInt32NoTag(final int p0) throws IOException;
    
    public final void writeSInt32NoTag(final int value) throws IOException {
        this.writeUInt32NoTag(encodeZigZag32(value));
    }
    
    public abstract void writeFixed32NoTag(final int p0) throws IOException;
    
    public final void writeSFixed32NoTag(final int value) throws IOException {
        this.writeFixed32NoTag(value);
    }
    
    public final void writeInt64NoTag(final long value) throws IOException {
        this.writeUInt64NoTag(value);
    }
    
    public abstract void writeUInt64NoTag(final long p0) throws IOException;
    
    public final void writeSInt64NoTag(final long value) throws IOException {
        this.writeUInt64NoTag(encodeZigZag64(value));
    }
    
    public abstract void writeFixed64NoTag(final long p0) throws IOException;
    
    public final void writeSFixed64NoTag(final long value) throws IOException {
        this.writeFixed64NoTag(value);
    }
    
    public final void writeFloatNoTag(final float value) throws IOException {
        this.writeFixed32NoTag(Float.floatToRawIntBits(value));
    }
    
    public final void writeDoubleNoTag(final double value) throws IOException {
        this.writeFixed64NoTag(Double.doubleToRawLongBits(value));
    }
    
    public final void writeBoolNoTag(final boolean value) throws IOException {
        this.write((byte)(value ? 1 : 0));
    }
    
    public final void writeEnumNoTag(final int value) throws IOException {
        this.writeInt32NoTag(value);
    }
    
    public abstract void writeStringNoTag(final String p0) throws IOException;
    
    public abstract void writeBytesNoTag(final ByteString p0) throws IOException;
    
    public final void writeByteArrayNoTag(final byte[] value) throws IOException {
        this.writeByteArrayNoTag(value, 0, value.length);
    }
    
    public abstract void writeMessageNoTag(final MessageLite p0) throws IOException;
    
    abstract void writeMessageNoTag(final MessageLite p0, final Schema p1) throws IOException;
    
    @Override
    public abstract void write(final byte p0) throws IOException;
    
    @Override
    public abstract void write(final byte[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public abstract void writeLazy(final byte[] p0, final int p1, final int p2) throws IOException;
    
    @Override
    public abstract void write(final ByteBuffer p0) throws IOException;
    
    @Override
    public abstract void writeLazy(final ByteBuffer p0) throws IOException;
    
    public static int computeInt32Size(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeInt32SizeNoTag(value);
    }
    
    public static int computeUInt32Size(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeUInt32SizeNoTag(value);
    }
    
    public static int computeSInt32Size(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeSInt32SizeNoTag(value);
    }
    
    public static int computeFixed32Size(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeFixed32SizeNoTag(value);
    }
    
    public static int computeSFixed32Size(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeSFixed32SizeNoTag(value);
    }
    
    public static int computeInt64Size(final int fieldNumber, final long value) {
        return computeTagSize(fieldNumber) + computeInt64SizeNoTag(value);
    }
    
    public static int computeUInt64Size(final int fieldNumber, final long value) {
        return computeTagSize(fieldNumber) + computeUInt64SizeNoTag(value);
    }
    
    public static int computeSInt64Size(final int fieldNumber, final long value) {
        return computeTagSize(fieldNumber) + computeSInt64SizeNoTag(value);
    }
    
    public static int computeFixed64Size(final int fieldNumber, final long value) {
        return computeTagSize(fieldNumber) + computeFixed64SizeNoTag(value);
    }
    
    public static int computeSFixed64Size(final int fieldNumber, final long value) {
        return computeTagSize(fieldNumber) + computeSFixed64SizeNoTag(value);
    }
    
    public static int computeFloatSize(final int fieldNumber, final float value) {
        return computeTagSize(fieldNumber) + computeFloatSizeNoTag(value);
    }
    
    public static int computeDoubleSize(final int fieldNumber, final double value) {
        return computeTagSize(fieldNumber) + computeDoubleSizeNoTag(value);
    }
    
    public static int computeBoolSize(final int fieldNumber, final boolean value) {
        return computeTagSize(fieldNumber) + computeBoolSizeNoTag(value);
    }
    
    public static int computeEnumSize(final int fieldNumber, final int value) {
        return computeTagSize(fieldNumber) + computeEnumSizeNoTag(value);
    }
    
    public static int computeStringSize(final int fieldNumber, final String value) {
        return computeTagSize(fieldNumber) + computeStringSizeNoTag(value);
    }
    
    public static int computeBytesSize(final int fieldNumber, final ByteString value) {
        return computeTagSize(fieldNumber) + computeBytesSizeNoTag(value);
    }
    
    public static int computeByteArraySize(final int fieldNumber, final byte[] value) {
        return computeTagSize(fieldNumber) + computeByteArraySizeNoTag(value);
    }
    
    public static int computeByteBufferSize(final int fieldNumber, final ByteBuffer value) {
        return computeTagSize(fieldNumber) + computeByteBufferSizeNoTag(value);
    }
    
    public static int computeLazyFieldSize(final int fieldNumber, final LazyFieldLite value) {
        return computeTagSize(fieldNumber) + computeLazyFieldSizeNoTag(value);
    }
    
    public static int computeMessageSize(final int fieldNumber, final MessageLite value) {
        return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value);
    }
    
    static int computeMessageSize(final int fieldNumber, final MessageLite value, final Schema schema) {
        return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value, schema);
    }
    
    public static int computeMessageSetExtensionSize(final int fieldNumber, final MessageLite value) {
        return computeTagSize(1) * 2 + computeUInt32Size(2, fieldNumber) + computeMessageSize(3, value);
    }
    
    public static int computeRawMessageSetExtensionSize(final int fieldNumber, final ByteString value) {
        return computeTagSize(1) * 2 + computeUInt32Size(2, fieldNumber) + computeBytesSize(3, value);
    }
    
    public static int computeLazyFieldMessageSetExtensionSize(final int fieldNumber, final LazyFieldLite value) {
        return computeTagSize(1) * 2 + computeUInt32Size(2, fieldNumber) + computeLazyFieldSize(3, value);
    }
    
    public static int computeTagSize(final int fieldNumber) {
        return computeUInt32SizeNoTag(WireFormat.makeTag(fieldNumber, 0));
    }
    
    public static int computeInt32SizeNoTag(final int value) {
        if (value >= 0) {
            return computeUInt32SizeNoTag(value);
        }
        return 10;
    }
    
    public static int computeUInt32SizeNoTag(final int value) {
        if ((value & 0xFFFFFF80) == 0x0) {
            return 1;
        }
        if ((value & 0xFFFFC000) == 0x0) {
            return 2;
        }
        if ((value & 0xFFE00000) == 0x0) {
            return 3;
        }
        if ((value & 0xF0000000) == 0x0) {
            return 4;
        }
        return 5;
    }
    
    public static int computeSInt32SizeNoTag(final int value) {
        return computeUInt32SizeNoTag(encodeZigZag32(value));
    }
    
    public static int computeFixed32SizeNoTag(final int unused) {
        return 4;
    }
    
    public static int computeSFixed32SizeNoTag(final int unused) {
        return 4;
    }
    
    public static int computeInt64SizeNoTag(final long value) {
        return computeUInt64SizeNoTag(value);
    }
    
    public static int computeUInt64SizeNoTag(long value) {
        if ((value & 0xFFFFFFFFFFFFFF80L) == 0x0L) {
            return 1;
        }
        if (value < 0L) {
            return 10;
        }
        int n = 2;
        if ((value & 0xFFFFFFF800000000L) != 0x0L) {
            n += 4;
            value >>>= 28;
        }
        if ((value & 0xFFFFFFFFFFE00000L) != 0x0L) {
            n += 2;
            value >>>= 14;
        }
        if ((value & 0xFFFFFFFFFFFFC000L) != 0x0L) {
            ++n;
        }
        return n;
    }
    
    public static int computeSInt64SizeNoTag(final long value) {
        return computeUInt64SizeNoTag(encodeZigZag64(value));
    }
    
    public static int computeFixed64SizeNoTag(final long unused) {
        return 8;
    }
    
    public static int computeSFixed64SizeNoTag(final long unused) {
        return 8;
    }
    
    public static int computeFloatSizeNoTag(final float unused) {
        return 4;
    }
    
    public static int computeDoubleSizeNoTag(final double unused) {
        return 8;
    }
    
    public static int computeBoolSizeNoTag(final boolean unused) {
        return 1;
    }
    
    public static int computeEnumSizeNoTag(final int value) {
        return computeInt32SizeNoTag(value);
    }
    
    public static int computeStringSizeNoTag(final String value) {
        int length;
        try {
            length = Utf8.encodedLength(value);
        }
        catch (Utf8.UnpairedSurrogateException e) {
            final byte[] bytes = value.getBytes(Internal.UTF_8);
            length = bytes.length;
        }
        return computeLengthDelimitedFieldSize(length);
    }
    
    public static int computeLazyFieldSizeNoTag(final LazyFieldLite value) {
        return computeLengthDelimitedFieldSize(value.getSerializedSize());
    }
    
    public static int computeBytesSizeNoTag(final ByteString value) {
        return computeLengthDelimitedFieldSize(value.size());
    }
    
    public static int computeByteArraySizeNoTag(final byte[] value) {
        return computeLengthDelimitedFieldSize(value.length);
    }
    
    public static int computeByteBufferSizeNoTag(final ByteBuffer value) {
        return computeLengthDelimitedFieldSize(value.capacity());
    }
    
    public static int computeMessageSizeNoTag(final MessageLite value) {
        return computeLengthDelimitedFieldSize(value.getSerializedSize());
    }
    
    static int computeMessageSizeNoTag(final MessageLite value, final Schema schema) {
        return computeLengthDelimitedFieldSize(((AbstractMessageLite)value).getSerializedSize(schema));
    }
    
    static int computeLengthDelimitedFieldSize(final int fieldLength) {
        return computeUInt32SizeNoTag(fieldLength) + fieldLength;
    }
    
    public static int encodeZigZag32(final int n) {
        return n << 1 ^ n >> 31;
    }
    
    public static long encodeZigZag64(final long n) {
        return n << 1 ^ n >> 63;
    }
    
    public abstract void flush() throws IOException;
    
    public abstract int spaceLeft();
    
    public final void checkNoSpaceLeft() {
        if (this.spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }
    
    public abstract int getTotalBytesWritten();
    
    abstract void writeByteArrayNoTag(final byte[] p0, final int p1, final int p2) throws IOException;
    
    final void inefficientWriteStringNoTag(final String value, final Utf8.UnpairedSurrogateException cause) throws IOException {
        CodedOutputStream.logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", cause);
        final byte[] bytes = value.getBytes(Internal.UTF_8);
        try {
            this.writeUInt32NoTag(bytes.length);
            this.writeLazy(bytes, 0, bytes.length);
        }
        catch (IndexOutOfBoundsException e) {
            throw new OutOfSpaceException(e);
        }
        catch (OutOfSpaceException e2) {
            throw e2;
        }
    }
    
    @Deprecated
    public final void writeGroup(final int fieldNumber, final MessageLite value) throws IOException {
        this.writeTag(fieldNumber, 3);
        this.writeGroupNoTag(value);
        this.writeTag(fieldNumber, 4);
    }
    
    @Deprecated
    final void writeGroup(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
        this.writeTag(fieldNumber, 3);
        this.writeGroupNoTag(value, schema);
        this.writeTag(fieldNumber, 4);
    }
    
    @Deprecated
    public final void writeGroupNoTag(final MessageLite value) throws IOException {
        value.writeTo(this);
    }
    
    @Deprecated
    final void writeGroupNoTag(final MessageLite value, final Schema schema) throws IOException {
        schema.writeTo(value, this.wrapper);
    }
    
    @Deprecated
    public static int computeGroupSize(final int fieldNumber, final MessageLite value) {
        return computeTagSize(fieldNumber) * 2 + computeGroupSizeNoTag(value);
    }
    
    @Deprecated
    static int computeGroupSize(final int fieldNumber, final MessageLite value, final Schema schema) {
        return computeTagSize(fieldNumber) * 2 + computeGroupSizeNoTag(value, schema);
    }
    
    @Deprecated
    public static int computeGroupSizeNoTag(final MessageLite value) {
        return value.getSerializedSize();
    }
    
    @Deprecated
    static int computeGroupSizeNoTag(final MessageLite value, final Schema schema) {
        return ((AbstractMessageLite)value).getSerializedSize(schema);
    }
    
    @Deprecated
    public final void writeRawVarint32(final int value) throws IOException {
        this.writeUInt32NoTag(value);
    }
    
    @Deprecated
    public final void writeRawVarint64(final long value) throws IOException {
        this.writeUInt64NoTag(value);
    }
    
    @Deprecated
    public static int computeRawVarint32Size(final int value) {
        return computeUInt32SizeNoTag(value);
    }
    
    @Deprecated
    public static int computeRawVarint64Size(final long value) {
        return computeUInt64SizeNoTag(value);
    }
    
    @Deprecated
    public final void writeRawLittleEndian32(final int value) throws IOException {
        this.writeFixed32NoTag(value);
    }
    
    @Deprecated
    public final void writeRawLittleEndian64(final long value) throws IOException {
        this.writeFixed64NoTag(value);
    }
    
    static {
        logger = Logger.getLogger(CodedOutputStream.class.getName());
        HAS_UNSAFE_ARRAY_OPERATIONS = UnsafeUtil.hasUnsafeArrayOperations();
    }
    
    public static class OutOfSpaceException extends IOException
    {
        private static final long serialVersionUID = -6947486886997889499L;
        private static final String MESSAGE = "CodedOutputStream was writing to a flat byte array and ran out of space.";
        
        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
        
        OutOfSpaceException(final String explanationMessage) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage);
        }
        
        OutOfSpaceException(final Throwable cause) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", cause);
        }
        
        OutOfSpaceException(final String explanationMessage, final Throwable cause) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage, cause);
        }
    }
    
    private static class ArrayEncoder extends CodedOutputStream
    {
        private final byte[] buffer;
        private final int offset;
        private final int limit;
        private int position;
        
        ArrayEncoder(final byte[] buffer, final int offset, final int length) {
            super(null);
            if (buffer == null) {
                throw new NullPointerException("buffer");
            }
            if ((offset | length | buffer.length - (offset + length)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", buffer.length, offset, length));
            }
            this.buffer = buffer;
            this.offset = offset;
            this.position = offset;
            this.limit = offset + length;
        }
        
        @Override
        public final void writeTag(final int fieldNumber, final int wireType) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        public final void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeInt32NoTag(value);
        }
        
        @Override
        public final void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt32NoTag(value);
        }
        
        @Override
        public final void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 5);
            this.writeFixed32NoTag(value);
        }
        
        @Override
        public final void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt64NoTag(value);
        }
        
        @Override
        public final void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 1);
            this.writeFixed64NoTag(value);
        }
        
        @Override
        public final void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        public final void writeString(final int fieldNumber, final String value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeStringNoTag(value);
        }
        
        @Override
        public final void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeBytesNoTag(value);
        }
        
        @Override
        public final void writeByteArray(final int fieldNumber, final byte[] value) throws IOException {
            this.writeByteArray(fieldNumber, value, 0, value.length);
        }
        
        @Override
        public final void writeByteArray(final int fieldNumber, final byte[] value, final int offset, final int length) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeByteArrayNoTag(value, offset, length);
        }
        
        @Override
        public final void writeByteBuffer(final int fieldNumber, final ByteBuffer value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(value.capacity());
            this.writeRawBytes(value);
        }
        
        @Override
        public final void writeBytesNoTag(final ByteString value) throws IOException {
            this.writeUInt32NoTag(value.size());
            value.writeTo(this);
        }
        
        public final void writeByteArrayNoTag(final byte[] value, final int offset, final int length) throws IOException {
            this.writeUInt32NoTag(length);
            this.write(value, offset, length);
        }
        
        @Override
        public final void writeRawBytes(final ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                this.write(value.array(), value.arrayOffset(), value.capacity());
            }
            else {
                final ByteBuffer duplicated = value.duplicate();
                duplicated.clear();
                this.write(duplicated);
            }
        }
        
        @Override
        public final void writeMessage(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value);
        }
        
        @Override
        final void writeMessage(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public final void writeMessageSetExtension(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeMessage(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public final void writeRawMessageSetExtension(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeBytes(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public final void writeMessageNoTag(final MessageLite value) throws IOException {
            this.writeUInt32NoTag(value.getSerializedSize());
            value.writeTo(this);
        }
        
        @Override
        final void writeMessageNoTag(final MessageLite value, final Schema schema) throws IOException {
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public final void write(final byte value) throws IOException {
            try {
                this.buffer[this.position++] = value;
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), e);
            }
        }
        
        @Override
        public final void writeInt32NoTag(final int value) throws IOException {
            if (value >= 0) {
                this.writeUInt32NoTag(value);
            }
            else {
                this.writeUInt64NoTag(value);
            }
        }
        
        @Override
        public final void writeUInt32NoTag(int value) throws IOException {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS && !Android.isOnAndroidDevice() && this.spaceLeft() >= 5) {
                if ((value & 0xFFFFFF80) == 0x0) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)(value | 0x80));
                value >>>= 7;
                if ((value & 0xFFFFFF80) == 0x0) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)(value | 0x80));
                value >>>= 7;
                if ((value & 0xFFFFFF80) == 0x0) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)(value | 0x80));
                value >>>= 7;
                if ((value & 0xFFFFFF80) == 0x0) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)(value | 0x80));
                value >>>= 7;
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
            }
            else {
                try {
                    while ((value & 0xFFFFFF80) != 0x0) {
                        this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
                        value >>>= 7;
                    }
                    this.buffer[this.position++] = (byte)value;
                }
                catch (IndexOutOfBoundsException e) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), e);
                }
            }
        }
        
        @Override
        public final void writeFixed32NoTag(final int value) throws IOException {
            try {
                this.buffer[this.position++] = (byte)(value & 0xFF);
                this.buffer[this.position++] = (byte)(value >> 8 & 0xFF);
                this.buffer[this.position++] = (byte)(value >> 16 & 0xFF);
                this.buffer[this.position++] = (byte)(value >> 24 & 0xFF);
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), e);
            }
        }
        
        @Override
        public final void writeUInt64NoTag(long value) throws IOException {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS && this.spaceLeft() >= 10) {
                while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)(((int)value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                return;
            }
            try {
                while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                    this.buffer[this.position++] = (byte)(((int)value & 0x7F) | 0x80);
                    value >>>= 7;
                }
                this.buffer[this.position++] = (byte)value;
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), e);
            }
        }
        
        @Override
        public final void writeFixed64NoTag(final long value) throws IOException {
            try {
                this.buffer[this.position++] = (byte)((int)value & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 8) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 16) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 24) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 32) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 40) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 48) & 0xFF);
                this.buffer[this.position++] = (byte)((int)(value >> 56) & 0xFF);
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), e);
            }
        }
        
        @Override
        public final void write(final byte[] value, final int offset, final int length) throws IOException {
            try {
                System.arraycopy(value, offset, this.buffer, this.position, length);
                this.position += length;
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, length), e);
            }
        }
        
        @Override
        public final void writeLazy(final byte[] value, final int offset, final int length) throws IOException {
            this.write(value, offset, length);
        }
        
        @Override
        public final void write(final ByteBuffer value) throws IOException {
            final int length = value.remaining();
            try {
                value.get(this.buffer, this.position, length);
                this.position += length;
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, length), e);
            }
        }
        
        @Override
        public final void writeLazy(final ByteBuffer value) throws IOException {
            this.write(value);
        }
        
        @Override
        public final void writeStringNoTag(final String value) throws IOException {
            final int oldPosition = this.position;
            try {
                final int maxLength = value.length() * 3;
                final int maxLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(maxLength);
                final int minLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    this.position = oldPosition + minLengthVarIntSize;
                    final int newPosition = Utf8.encode(value, this.buffer, this.position, this.spaceLeft());
                    this.position = oldPosition;
                    final int length = newPosition - oldPosition - minLengthVarIntSize;
                    this.writeUInt32NoTag(length);
                    this.position = newPosition;
                }
                else {
                    final int length2 = Utf8.encodedLength(value);
                    this.writeUInt32NoTag(length2);
                    this.position = Utf8.encode(value, this.buffer, this.position, this.spaceLeft());
                }
            }
            catch (Utf8.UnpairedSurrogateException e) {
                this.position = oldPosition;
                this.inefficientWriteStringNoTag(value, e);
            }
            catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public final int spaceLeft() {
            return this.limit - this.position;
        }
        
        @Override
        public final int getTotalBytesWritten() {
            return this.position - this.offset;
        }
    }
    
    private static final class HeapNioEncoder extends ArrayEncoder
    {
        private final ByteBuffer byteBuffer;
        private int initialPosition;
        
        HeapNioEncoder(final ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.byteBuffer = byteBuffer;
            this.initialPosition = byteBuffer.position();
        }
        
        @Override
        public void flush() {
            this.byteBuffer.position(this.initialPosition + this.getTotalBytesWritten());
        }
    }
    
    private static final class SafeDirectNioEncoder extends CodedOutputStream
    {
        private final ByteBuffer originalBuffer;
        private final ByteBuffer buffer;
        private final int initialPosition;
        
        SafeDirectNioEncoder(final ByteBuffer buffer) {
            super(null);
            this.originalBuffer = buffer;
            this.buffer = buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.initialPosition = buffer.position();
        }
        
        @Override
        public void writeTag(final int fieldNumber, final int wireType) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeInt32NoTag(value);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 5);
            this.writeFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 1);
            this.writeFixed64NoTag(value);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeStringNoTag(value);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeBytesNoTag(value);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value) throws IOException {
            this.writeByteArray(fieldNumber, value, 0, value.length);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value, final int offset, final int length) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeByteArrayNoTag(value, offset, length);
        }
        
        @Override
        public void writeByteBuffer(final int fieldNumber, final ByteBuffer value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(value.capacity());
            this.writeRawBytes(value);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value);
        }
        
        @Override
        void writeMessage(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value, schema);
        }
        
        @Override
        public void writeMessageSetExtension(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeMessage(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeRawMessageSetExtension(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeBytes(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeMessageNoTag(final MessageLite value) throws IOException {
            this.writeUInt32NoTag(value.getSerializedSize());
            value.writeTo(this);
        }
        
        @Override
        void writeMessageNoTag(final MessageLite value, final Schema schema) throws IOException {
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public void write(final byte value) throws IOException {
            try {
                this.buffer.put(value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeBytesNoTag(final ByteString value) throws IOException {
            this.writeUInt32NoTag(value.size());
            value.writeTo(this);
        }
        
        public void writeByteArrayNoTag(final byte[] value, final int offset, final int length) throws IOException {
            this.writeUInt32NoTag(length);
            this.write(value, offset, length);
        }
        
        @Override
        public void writeRawBytes(final ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                this.write(value.array(), value.arrayOffset(), value.capacity());
            }
            else {
                final ByteBuffer duplicated = value.duplicate();
                duplicated.clear();
                this.write(duplicated);
            }
        }
        
        @Override
        public void writeInt32NoTag(final int value) throws IOException {
            if (value >= 0) {
                this.writeUInt32NoTag(value);
            }
            else {
                this.writeUInt64NoTag(value);
            }
        }
        
        @Override
        public void writeUInt32NoTag(int value) throws IOException {
            try {
                while ((value & 0xFFFFFF80) != 0x0) {
                    this.buffer.put((byte)((value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                this.buffer.put((byte)value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeFixed32NoTag(final int value) throws IOException {
            try {
                this.buffer.putInt(value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeUInt64NoTag(long value) throws IOException {
            try {
                while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                    this.buffer.put((byte)(((int)value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                this.buffer.put((byte)value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeFixed64NoTag(final long value) throws IOException {
            try {
                this.buffer.putLong(value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) throws IOException {
            try {
                this.buffer.put(value, offset, length);
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(e);
            }
            catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) throws IOException {
            this.write(value, offset, length);
        }
        
        @Override
        public void write(final ByteBuffer value) throws IOException {
            try {
                this.buffer.put(value);
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) throws IOException {
            this.write(value);
        }
        
        @Override
        public void writeStringNoTag(final String value) throws IOException {
            final int startPos = this.buffer.position();
            try {
                final int maxEncodedSize = value.length() * 3;
                final int maxLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(maxEncodedSize);
                final int minLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    final int startOfBytes = this.buffer.position() + minLengthVarIntSize;
                    this.buffer.position(startOfBytes);
                    this.encode(value);
                    final int endOfBytes = this.buffer.position();
                    this.buffer.position(startPos);
                    this.writeUInt32NoTag(endOfBytes - startOfBytes);
                    this.buffer.position(endOfBytes);
                }
                else {
                    final int length = Utf8.encodedLength(value);
                    this.writeUInt32NoTag(length);
                    this.encode(value);
                }
            }
            catch (Utf8.UnpairedSurrogateException e) {
                this.buffer.position(startPos);
                this.inefficientWriteStringNoTag(value, e);
            }
            catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
        
        @Override
        public void flush() {
            this.originalBuffer.position(this.buffer.position());
        }
        
        @Override
        public int spaceLeft() {
            return this.buffer.remaining();
        }
        
        @Override
        public int getTotalBytesWritten() {
            return this.buffer.position() - this.initialPosition;
        }
        
        private void encode(final String value) throws IOException {
            try {
                Utf8.encodeUtf8(value, this.buffer);
            }
            catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(e);
            }
        }
    }
    
    private static final class UnsafeDirectNioEncoder extends CodedOutputStream
    {
        private final ByteBuffer originalBuffer;
        private final ByteBuffer buffer;
        private final long address;
        private final long initialPosition;
        private final long limit;
        private final long oneVarintLimit;
        private long position;
        
        UnsafeDirectNioEncoder(final ByteBuffer buffer) {
            super(null);
            this.originalBuffer = buffer;
            this.buffer = buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.address = UnsafeUtil.addressOffset(buffer);
            this.initialPosition = this.address + buffer.position();
            this.limit = this.address + buffer.limit();
            this.oneVarintLimit = this.limit - 10L;
            this.position = this.initialPosition;
        }
        
        static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }
        
        @Override
        public void writeTag(final int fieldNumber, final int wireType) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeInt32NoTag(value);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.writeTag(fieldNumber, 5);
            this.writeFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.writeUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.writeTag(fieldNumber, 1);
            this.writeFixed64NoTag(value);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.writeTag(fieldNumber, 0);
            this.write((byte)(value ? 1 : 0));
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeStringNoTag(value);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeBytesNoTag(value);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value) throws IOException {
            this.writeByteArray(fieldNumber, value, 0, value.length);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value, final int offset, final int length) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeByteArrayNoTag(value, offset, length);
        }
        
        @Override
        public void writeByteBuffer(final int fieldNumber, final ByteBuffer value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(value.capacity());
            this.writeRawBytes(value);
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value);
        }
        
        @Override
        void writeMessage(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value, schema);
        }
        
        @Override
        public void writeMessageSetExtension(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeMessage(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeRawMessageSetExtension(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeBytes(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeMessageNoTag(final MessageLite value) throws IOException {
            this.writeUInt32NoTag(value.getSerializedSize());
            value.writeTo(this);
        }
        
        @Override
        void writeMessageNoTag(final MessageLite value, final Schema schema) throws IOException {
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public void write(final byte value) throws IOException {
            if (this.position >= this.limit) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1));
            }
            UnsafeUtil.putByte(this.position++, value);
        }
        
        @Override
        public void writeBytesNoTag(final ByteString value) throws IOException {
            this.writeUInt32NoTag(value.size());
            value.writeTo(this);
        }
        
        public void writeByteArrayNoTag(final byte[] value, final int offset, final int length) throws IOException {
            this.writeUInt32NoTag(length);
            this.write(value, offset, length);
        }
        
        @Override
        public void writeRawBytes(final ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                this.write(value.array(), value.arrayOffset(), value.capacity());
            }
            else {
                final ByteBuffer duplicated = value.duplicate();
                duplicated.clear();
                this.write(duplicated);
            }
        }
        
        @Override
        public void writeInt32NoTag(final int value) throws IOException {
            if (value >= 0) {
                this.writeUInt32NoTag(value);
            }
            else {
                this.writeUInt64NoTag(value);
            }
        }
        
        @Override
        public void writeUInt32NoTag(int value) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((value & 0xFFFFFF80) != 0x0) {
                    UnsafeUtil.putByte(this.position++, (byte)((value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                UnsafeUtil.putByte(this.position++, (byte)value);
                return;
            }
            while (this.position < this.limit) {
                if ((value & 0xFFFFFF80) == 0x0) {
                    UnsafeUtil.putByte(this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.position++, (byte)((value & 0x7F) | 0x80));
                value >>>= 7;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1));
        }
        
        @Override
        public void writeFixed32NoTag(final int value) throws IOException {
            this.buffer.putInt(this.bufferPos(this.position), value);
            this.position += 4L;
        }
        
        @Override
        public void writeUInt64NoTag(long value) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                    UnsafeUtil.putByte(this.position++, (byte)(((int)value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                UnsafeUtil.putByte(this.position++, (byte)value);
                return;
            }
            while (this.position < this.limit) {
                if ((value & 0xFFFFFFFFFFFFFF80L) == 0x0L) {
                    UnsafeUtil.putByte(this.position++, (byte)value);
                    return;
                }
                UnsafeUtil.putByte(this.position++, (byte)(((int)value & 0x7F) | 0x80));
                value >>>= 7;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1));
        }
        
        @Override
        public void writeFixed64NoTag(final long value) throws IOException {
            this.buffer.putLong(this.bufferPos(this.position), value);
            this.position += 8L;
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) throws IOException {
            if (value != null && offset >= 0 && length >= 0 && value.length - length >= offset && this.limit - length >= this.position) {
                UnsafeUtil.copyMemory(value, offset, this.position, length);
                this.position += length;
                return;
            }
            if (value == null) {
                throw new NullPointerException("value");
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, length));
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) throws IOException {
            this.write(value, offset, length);
        }
        
        @Override
        public void write(final ByteBuffer value) throws IOException {
            try {
                final int length = value.remaining();
                this.repositionBuffer(this.position);
                this.buffer.put(value);
                this.position += length;
            }
            catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) throws IOException {
            this.write(value);
        }
        
        @Override
        public void writeStringNoTag(final String value) throws IOException {
            final long prevPos = this.position;
            try {
                final int maxEncodedSize = value.length() * 3;
                final int maxLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(maxEncodedSize);
                final int minLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    final int stringStart = this.bufferPos(this.position) + minLengthVarIntSize;
                    this.buffer.position(stringStart);
                    Utf8.encodeUtf8(value, this.buffer);
                    final int length = this.buffer.position() - stringStart;
                    this.writeUInt32NoTag(length);
                    this.position += length;
                }
                else {
                    final int length2 = Utf8.encodedLength(value);
                    this.writeUInt32NoTag(length2);
                    this.repositionBuffer(this.position);
                    Utf8.encodeUtf8(value, this.buffer);
                    this.position += length2;
                }
            }
            catch (Utf8.UnpairedSurrogateException e) {
                this.repositionBuffer(this.position = prevPos);
                this.inefficientWriteStringNoTag(value, e);
            }
            catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException(e2);
            }
            catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException(e3);
            }
        }
        
        @Override
        public void flush() {
            this.originalBuffer.position(this.bufferPos(this.position));
        }
        
        @Override
        public int spaceLeft() {
            return (int)(this.limit - this.position);
        }
        
        @Override
        public int getTotalBytesWritten() {
            return (int)(this.position - this.initialPosition);
        }
        
        private void repositionBuffer(final long pos) {
            this.buffer.position(this.bufferPos(pos));
        }
        
        private int bufferPos(final long pos) {
            return (int)(pos - this.address);
        }
    }
    
    private abstract static class AbstractBufferedEncoder extends CodedOutputStream
    {
        final byte[] buffer;
        final int limit;
        int position;
        int totalBytesWritten;
        
        AbstractBufferedEncoder(final int bufferSize) {
            super(null);
            if (bufferSize < 0) {
                throw new IllegalArgumentException("bufferSize must be >= 0");
            }
            this.buffer = new byte[Math.max(bufferSize, 20)];
            this.limit = this.buffer.length;
        }
        
        @Override
        public final int spaceLeft() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }
        
        @Override
        public final int getTotalBytesWritten() {
            return this.totalBytesWritten;
        }
        
        final void buffer(final byte value) {
            this.buffer[this.position++] = value;
            ++this.totalBytesWritten;
        }
        
        final void bufferTag(final int fieldNumber, final int wireType) {
            this.bufferUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        final void bufferInt32NoTag(final int value) {
            if (value >= 0) {
                this.bufferUInt32NoTag(value);
            }
            else {
                this.bufferUInt64NoTag(value);
            }
        }
        
        final void bufferUInt32NoTag(int value) {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                final long originalPos = this.position;
                while ((value & 0xFFFFFF80) != 0x0) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)((value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                final int delta = (int)(this.position - originalPos);
                this.totalBytesWritten += delta;
                return;
            }
            while ((value & 0xFFFFFF80) != 0x0) {
                this.buffer[this.position++] = (byte)((value & 0x7F) | 0x80);
                ++this.totalBytesWritten;
                value >>>= 7;
            }
            this.buffer[this.position++] = (byte)value;
            ++this.totalBytesWritten;
        }
        
        final void bufferUInt64NoTag(long value) {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                final long originalPos = this.position;
                while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                    UnsafeUtil.putByte(this.buffer, this.position++, (byte)(((int)value & 0x7F) | 0x80));
                    value >>>= 7;
                }
                UnsafeUtil.putByte(this.buffer, this.position++, (byte)value);
                final int delta = (int)(this.position - originalPos);
                this.totalBytesWritten += delta;
                return;
            }
            while ((value & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
                this.buffer[this.position++] = (byte)(((int)value & 0x7F) | 0x80);
                ++this.totalBytesWritten;
                value >>>= 7;
            }
            this.buffer[this.position++] = (byte)value;
            ++this.totalBytesWritten;
        }
        
        final void bufferFixed32NoTag(final int value) {
            this.buffer[this.position++] = (byte)(value & 0xFF);
            this.buffer[this.position++] = (byte)(value >> 8 & 0xFF);
            this.buffer[this.position++] = (byte)(value >> 16 & 0xFF);
            this.buffer[this.position++] = (byte)(value >> 24 & 0xFF);
            this.totalBytesWritten += 4;
        }
        
        final void bufferFixed64NoTag(final long value) {
            this.buffer[this.position++] = (byte)(value & 0xFFL);
            this.buffer[this.position++] = (byte)(value >> 8 & 0xFFL);
            this.buffer[this.position++] = (byte)(value >> 16 & 0xFFL);
            this.buffer[this.position++] = (byte)(value >> 24 & 0xFFL);
            this.buffer[this.position++] = (byte)((int)(value >> 32) & 0xFF);
            this.buffer[this.position++] = (byte)((int)(value >> 40) & 0xFF);
            this.buffer[this.position++] = (byte)((int)(value >> 48) & 0xFF);
            this.buffer[this.position++] = (byte)((int)(value >> 56) & 0xFF);
            this.totalBytesWritten += 8;
        }
    }
    
    private static final class ByteOutputEncoder extends AbstractBufferedEncoder
    {
        private final ByteOutput out;
        
        ByteOutputEncoder(final ByteOutput out, final int bufferSize) {
            super(bufferSize);
            if (out == null) {
                throw new NullPointerException("out");
            }
            this.out = out;
        }
        
        @Override
        public void writeTag(final int fieldNumber, final int wireType) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferInt32NoTag(value);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(14);
            this.bufferTag(fieldNumber, 5);
            this.bufferFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.flushIfNotAvailable(18);
            this.bufferTag(fieldNumber, 1);
            this.bufferFixed64NoTag(value);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.flushIfNotAvailable(11);
            this.bufferTag(fieldNumber, 0);
            this.buffer((byte)(value ? 1 : 0));
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeStringNoTag(value);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeBytesNoTag(value);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value) throws IOException {
            this.writeByteArray(fieldNumber, value, 0, value.length);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value, final int offset, final int length) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeByteArrayNoTag(value, offset, length);
        }
        
        @Override
        public void writeByteBuffer(final int fieldNumber, final ByteBuffer value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(value.capacity());
            this.writeRawBytes(value);
        }
        
        @Override
        public void writeBytesNoTag(final ByteString value) throws IOException {
            this.writeUInt32NoTag(value.size());
            value.writeTo(this);
        }
        
        public void writeByteArrayNoTag(final byte[] value, final int offset, final int length) throws IOException {
            this.writeUInt32NoTag(length);
            this.write(value, offset, length);
        }
        
        @Override
        public void writeRawBytes(final ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                this.write(value.array(), value.arrayOffset(), value.capacity());
            }
            else {
                final ByteBuffer duplicated = value.duplicate();
                duplicated.clear();
                this.write(duplicated);
            }
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value);
        }
        
        @Override
        void writeMessage(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value, schema);
        }
        
        @Override
        public void writeMessageSetExtension(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeMessage(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeRawMessageSetExtension(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeBytes(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeMessageNoTag(final MessageLite value) throws IOException {
            this.writeUInt32NoTag(value.getSerializedSize());
            value.writeTo(this);
        }
        
        @Override
        void writeMessageNoTag(final MessageLite value, final Schema schema) throws IOException {
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public void write(final byte value) throws IOException {
            if (this.position == this.limit) {
                this.doFlush();
            }
            this.buffer(value);
        }
        
        @Override
        public void writeInt32NoTag(final int value) throws IOException {
            if (value >= 0) {
                this.writeUInt32NoTag(value);
            }
            else {
                this.writeUInt64NoTag(value);
            }
        }
        
        @Override
        public void writeUInt32NoTag(final int value) throws IOException {
            this.flushIfNotAvailable(5);
            this.bufferUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32NoTag(final int value) throws IOException {
            this.flushIfNotAvailable(4);
            this.bufferFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64NoTag(final long value) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64NoTag(final long value) throws IOException {
            this.flushIfNotAvailable(8);
            this.bufferFixed64NoTag(value);
        }
        
        @Override
        public void writeStringNoTag(final String value) throws IOException {
            final int maxLength = value.length() * 3;
            final int maxLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(maxLength);
            if (maxLengthVarIntSize + maxLength > this.limit) {
                final byte[] encodedBytes = new byte[maxLength];
                final int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
                this.writeUInt32NoTag(actualLength);
                this.writeLazy(encodedBytes, 0, actualLength);
                return;
            }
            if (maxLengthVarIntSize + maxLength > this.limit - this.position) {
                this.doFlush();
            }
            final int oldPosition = this.position;
            try {
                final int minLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    this.position = oldPosition + minLengthVarIntSize;
                    final int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
                    this.position = oldPosition;
                    final int length = newPosition - oldPosition - minLengthVarIntSize;
                    this.bufferUInt32NoTag(length);
                    this.position = newPosition;
                    this.totalBytesWritten += length;
                }
                else {
                    final int length2 = Utf8.encodedLength(value);
                    this.bufferUInt32NoTag(length2);
                    this.position = Utf8.encode(value, this.buffer, this.position, length2);
                    this.totalBytesWritten += length2;
                }
            }
            catch (Utf8.UnpairedSurrogateException e) {
                this.totalBytesWritten -= this.position - oldPosition;
                this.position = oldPosition;
                this.inefficientWriteStringNoTag(value, e);
            }
            catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(e2);
            }
        }
        
        @Override
        public void flush() throws IOException {
            if (this.position > 0) {
                this.doFlush();
            }
        }
        
        @Override
        public void write(final byte[] value, final int offset, final int length) throws IOException {
            this.flush();
            this.out.write(value, offset, length);
            this.totalBytesWritten += length;
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) throws IOException {
            this.flush();
            this.out.writeLazy(value, offset, length);
            this.totalBytesWritten += length;
        }
        
        @Override
        public void write(final ByteBuffer value) throws IOException {
            this.flush();
            final int length = value.remaining();
            this.out.write(value);
            this.totalBytesWritten += length;
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) throws IOException {
            this.flush();
            final int length = value.remaining();
            this.out.writeLazy(value);
            this.totalBytesWritten += length;
        }
        
        private void flushIfNotAvailable(final int requiredSize) throws IOException {
            if (this.limit - this.position < requiredSize) {
                this.doFlush();
            }
        }
        
        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }
    }
    
    private static final class OutputStreamEncoder extends AbstractBufferedEncoder
    {
        private final OutputStream out;
        
        OutputStreamEncoder(final OutputStream out, final int bufferSize) {
            super(bufferSize);
            if (out == null) {
                throw new NullPointerException("out");
            }
            this.out = out;
        }
        
        @Override
        public void writeTag(final int fieldNumber, final int wireType) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }
        
        @Override
        public void writeInt32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferInt32NoTag(value);
        }
        
        @Override
        public void writeUInt32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32(final int fieldNumber, final int value) throws IOException {
            this.flushIfNotAvailable(14);
            this.bufferTag(fieldNumber, 5);
            this.bufferFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64(final int fieldNumber, final long value) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(fieldNumber, 0);
            this.bufferUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64(final int fieldNumber, final long value) throws IOException {
            this.flushIfNotAvailable(18);
            this.bufferTag(fieldNumber, 1);
            this.bufferFixed64NoTag(value);
        }
        
        @Override
        public void writeBool(final int fieldNumber, final boolean value) throws IOException {
            this.flushIfNotAvailable(11);
            this.bufferTag(fieldNumber, 0);
            this.buffer((byte)(value ? 1 : 0));
        }
        
        @Override
        public void writeString(final int fieldNumber, final String value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeStringNoTag(value);
        }
        
        @Override
        public void writeBytes(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeBytesNoTag(value);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value) throws IOException {
            this.writeByteArray(fieldNumber, value, 0, value.length);
        }
        
        @Override
        public void writeByteArray(final int fieldNumber, final byte[] value, final int offset, final int length) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeByteArrayNoTag(value, offset, length);
        }
        
        @Override
        public void writeByteBuffer(final int fieldNumber, final ByteBuffer value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeUInt32NoTag(value.capacity());
            this.writeRawBytes(value);
        }
        
        @Override
        public void writeBytesNoTag(final ByteString value) throws IOException {
            this.writeUInt32NoTag(value.size());
            value.writeTo(this);
        }
        
        public void writeByteArrayNoTag(final byte[] value, final int offset, final int length) throws IOException {
            this.writeUInt32NoTag(length);
            this.write(value, offset, length);
        }
        
        @Override
        public void writeRawBytes(final ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                this.write(value.array(), value.arrayOffset(), value.capacity());
            }
            else {
                final ByteBuffer duplicated = value.duplicate();
                duplicated.clear();
                this.write(duplicated);
            }
        }
        
        @Override
        public void writeMessage(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value);
        }
        
        @Override
        void writeMessage(final int fieldNumber, final MessageLite value, final Schema schema) throws IOException {
            this.writeTag(fieldNumber, 2);
            this.writeMessageNoTag(value, schema);
        }
        
        @Override
        public void writeMessageSetExtension(final int fieldNumber, final MessageLite value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeMessage(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeRawMessageSetExtension(final int fieldNumber, final ByteString value) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, fieldNumber);
            this.writeBytes(3, value);
            this.writeTag(1, 4);
        }
        
        @Override
        public void writeMessageNoTag(final MessageLite value) throws IOException {
            this.writeUInt32NoTag(value.getSerializedSize());
            value.writeTo(this);
        }
        
        @Override
        void writeMessageNoTag(final MessageLite value, final Schema schema) throws IOException {
            this.writeUInt32NoTag(((AbstractMessageLite)value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }
        
        @Override
        public void write(final byte value) throws IOException {
            if (this.position == this.limit) {
                this.doFlush();
            }
            this.buffer(value);
        }
        
        @Override
        public void writeInt32NoTag(final int value) throws IOException {
            if (value >= 0) {
                this.writeUInt32NoTag(value);
            }
            else {
                this.writeUInt64NoTag(value);
            }
        }
        
        @Override
        public void writeUInt32NoTag(final int value) throws IOException {
            this.flushIfNotAvailable(5);
            this.bufferUInt32NoTag(value);
        }
        
        @Override
        public void writeFixed32NoTag(final int value) throws IOException {
            this.flushIfNotAvailable(4);
            this.bufferFixed32NoTag(value);
        }
        
        @Override
        public void writeUInt64NoTag(final long value) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt64NoTag(value);
        }
        
        @Override
        public void writeFixed64NoTag(final long value) throws IOException {
            this.flushIfNotAvailable(8);
            this.bufferFixed64NoTag(value);
        }
        
        @Override
        public void writeStringNoTag(final String value) throws IOException {
            try {
                final int maxLength = value.length() * 3;
                final int maxLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(maxLength);
                if (maxLengthVarIntSize + maxLength > this.limit) {
                    final byte[] encodedBytes = new byte[maxLength];
                    final int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
                    this.writeUInt32NoTag(actualLength);
                    this.writeLazy(encodedBytes, 0, actualLength);
                    return;
                }
                if (maxLengthVarIntSize + maxLength > this.limit - this.position) {
                    this.doFlush();
                }
                final int minLengthVarIntSize = CodedOutputStream.computeUInt32SizeNoTag(value.length());
                final int oldPosition = this.position;
                try {
                    int length;
                    if (minLengthVarIntSize == maxLengthVarIntSize) {
                        this.position = oldPosition + minLengthVarIntSize;
                        final int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
                        this.position = oldPosition;
                        length = newPosition - oldPosition - minLengthVarIntSize;
                        this.bufferUInt32NoTag(length);
                        this.position = newPosition;
                    }
                    else {
                        length = Utf8.encodedLength(value);
                        this.bufferUInt32NoTag(length);
                        this.position = Utf8.encode(value, this.buffer, this.position, length);
                    }
                    this.totalBytesWritten += length;
                }
                catch (Utf8.UnpairedSurrogateException e) {
                    this.totalBytesWritten -= this.position - oldPosition;
                    this.position = oldPosition;
                    throw e;
                }
                catch (ArrayIndexOutOfBoundsException e2) {
                    throw new OutOfSpaceException(e2);
                }
            }
            catch (Utf8.UnpairedSurrogateException e3) {
                this.inefficientWriteStringNoTag(value, e3);
            }
        }
        
        @Override
        public void flush() throws IOException {
            if (this.position > 0) {
                this.doFlush();
            }
        }
        
        @Override
        public void write(final byte[] value, int offset, int length) throws IOException {
            if (this.limit - this.position >= length) {
                System.arraycopy(value, offset, this.buffer, this.position, length);
                this.position += length;
                this.totalBytesWritten += length;
            }
            else {
                final int bytesWritten = this.limit - this.position;
                System.arraycopy(value, offset, this.buffer, this.position, bytesWritten);
                offset += bytesWritten;
                length -= bytesWritten;
                this.position = this.limit;
                this.totalBytesWritten += bytesWritten;
                this.doFlush();
                if (length <= this.limit) {
                    System.arraycopy(value, offset, this.buffer, 0, length);
                    this.position = length;
                }
                else {
                    this.out.write(value, offset, length);
                }
                this.totalBytesWritten += length;
            }
        }
        
        @Override
        public void writeLazy(final byte[] value, final int offset, final int length) throws IOException {
            this.write(value, offset, length);
        }
        
        @Override
        public void write(final ByteBuffer value) throws IOException {
            int length = value.remaining();
            if (this.limit - this.position >= length) {
                value.get(this.buffer, this.position, length);
                this.position += length;
                this.totalBytesWritten += length;
            }
            else {
                final int bytesWritten = this.limit - this.position;
                value.get(this.buffer, this.position, bytesWritten);
                length -= bytesWritten;
                this.position = this.limit;
                this.totalBytesWritten += bytesWritten;
                this.doFlush();
                while (length > this.limit) {
                    value.get(this.buffer, 0, this.limit);
                    this.out.write(this.buffer, 0, this.limit);
                    length -= this.limit;
                    this.totalBytesWritten += this.limit;
                }
                value.get(this.buffer, 0, length);
                this.position = length;
                this.totalBytesWritten += length;
            }
        }
        
        @Override
        public void writeLazy(final ByteBuffer value) throws IOException {
            this.write(value);
        }
        
        private void flushIfNotAvailable(final int requiredSize) throws IOException {
            if (this.limit - this.position < requiredSize) {
                this.doFlush();
            }
        }
        
        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }
    }
}
