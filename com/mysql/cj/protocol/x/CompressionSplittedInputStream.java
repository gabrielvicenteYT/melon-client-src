package com.mysql.cj.protocol.x;

import com.mysql.cj.x.protobuf.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import com.google.protobuf.*;

public class CompressionSplittedInputStream extends FilterInputStream
{
    private CompressorStreamsFactory compressorIoStreamsFactory;
    private byte[] frameHeader;
    private int frameHeaderConsumed;
    private int framePayloadLength;
    private int framePayloadConsumed;
    private XMessageHeader xMessageHeader;
    private InputStream compressorIn;
    private byte[] singleByte;
    private boolean closed;
    
    public CompressionSplittedInputStream(final InputStream in, final CompressorStreamsFactory streamsFactory) {
        super(in);
        this.frameHeader = new byte[5];
        this.frameHeaderConsumed = 0;
        this.framePayloadLength = 0;
        this.framePayloadConsumed = 0;
        this.compressorIn = null;
        this.singleByte = new byte[1];
        this.closed = false;
        this.compressorIoStreamsFactory = streamsFactory;
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        if (this.compressorIn != null) {
            return this.compressorIn.available();
        }
        return ((this.frameHeaderConsumed > 0) ? (5 - this.frameHeaderConsumed) : 0) + this.in.available();
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.in = null;
            if (this.compressorIn != null) {
                this.compressorIn.close();
            }
            this.compressorIn = null;
            this.closed = true;
        }
    }
    
    @Override
    public int read() throws IOException {
        this.ensureOpen();
        final int read = this.read(this.singleByte, 0, 1);
        if (read >= 0) {
            return this.singleByte[0] & 0xFF;
        }
        return read;
    }
    
    @Override
    public int read(final byte[] b) throws IOException {
        this.ensureOpen();
        return this.read(b, 0, b.length);
    }
    
    @Override
    public int read(final byte[] b, int off, int len) throws IOException {
        this.ensureOpen();
        if (len <= 0) {
            return 0;
        }
        this.peekNextFrame();
        try {
            if (this.isCompressedDataAvailable()) {
                final int bytesRead = this.readFully(this.compressorIn, b, off, len);
                if (this.isCompressedDataReadComplete()) {
                    this.compressorIn.close();
                    this.compressorIn = null;
                }
                return bytesRead;
            }
        }
        catch (IOException e) {
            throw e;
        }
        int headerBytesRead = 0;
        if (!this.isFrameHeaderFullyConsumed()) {
            final int lenToConsume = Math.min(len, 5 - this.frameHeaderConsumed);
            System.arraycopy(this.frameHeader, this.frameHeaderConsumed, b, off, lenToConsume);
            off += lenToConsume;
            len -= lenToConsume;
            this.frameHeaderConsumed += lenToConsume;
            headerBytesRead = lenToConsume;
        }
        final int payloadBytesRead = this.readFully(b, off, len);
        this.framePayloadConsumed += payloadBytesRead;
        return headerBytesRead + payloadBytesRead;
    }
    
    private void peekNextFrame() throws IOException {
        if (this.isDataAvailable()) {
            return;
        }
        this.readFully(this.frameHeader, 0, 5);
        this.xMessageHeader = new XMessageHeader(this.frameHeader);
        this.framePayloadLength = this.xMessageHeader.getMessageSize();
        this.frameHeaderConsumed = 0;
        this.framePayloadConsumed = 0;
        if (this.isCompressedFrame()) {
            final MysqlxConnection.Compression compressedMessage = this.parseCompressedMessage();
            this.compressorIn = new ConfinedInputStream(this.compressorIoStreamsFactory.getInputStreamInstance(new ByteArrayInputStream(compressedMessage.getPayload().toByteArray())), (int)compressedMessage.getUncompressedSize());
            this.frameHeaderConsumed = 5;
            this.framePayloadConsumed = this.framePayloadLength;
        }
    }
    
    private boolean isCompressedFrame() {
        return Mysqlx.ServerMessages.Type.forNumber(this.xMessageHeader.getMessageType()) == Mysqlx.ServerMessages.Type.COMPRESSION;
    }
    
    private MysqlxConnection.Compression parseCompressedMessage() {
        final Parser<MysqlxConnection.Compression> parser = MessageConstants.MESSAGE_CLASS_TO_PARSER.get(MessageConstants.MESSAGE_TYPE_TO_CLASS.get(19));
        final byte[] packet = new byte[this.xMessageHeader.getMessageSize()];
        try {
            this.readFully(packet);
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(CJCommunicationsException.class, Messages.getString("Protocol.Compression.Streams.0"), e);
        }
        try {
            return parser.parseFrom(packet);
        }
        catch (InvalidProtocolBufferException e2) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.Compression.Streams.1"), e2);
        }
    }
    
    private boolean isDataAvailable() throws IOException {
        return this.isCompressedDataAvailable() || (this.frameHeaderConsumed > 0 && this.frameHeaderConsumed < 5) || (this.isFrameHeaderFullyConsumed() && this.framePayloadConsumed < this.framePayloadLength);
    }
    
    private boolean isCompressedDataAvailable() throws IOException {
        return this.compressorIn != null && this.compressorIn.available() > 0;
    }
    
    private boolean isCompressedDataReadComplete() throws IOException {
        return this.compressorIn != null && this.compressorIn.available() == 0;
    }
    
    boolean isFrameHeaderFullyConsumed() {
        return this.frameHeaderConsumed == 5;
    }
    
    public int readFully(final byte[] b) throws IOException {
        return this.readFully(b, 0, b.length);
    }
    
    private final int readFully(final byte[] b, final int off, final int len) throws IOException {
        return this.readFully(this.in, b, off, len);
    }
    
    private final int readFully(final InputStream inStream, final byte[] b, final int off, final int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        int total;
        int count;
        for (total = 0; total < len; total += count) {
            count = inStream.read(b, off + total, len - total);
            if (count < 0) {
                throw new EOFException();
            }
        }
        return total;
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
}
