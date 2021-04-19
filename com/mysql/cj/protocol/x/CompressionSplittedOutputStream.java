package com.mysql.cj.protocol.x;

import java.io.*;
import com.mysql.cj.x.protobuf.*;
import com.google.protobuf.*;
import java.nio.*;
import java.util.*;

public class CompressionSplittedOutputStream extends FilterOutputStream
{
    private CompressorStreamsFactory compressorIoStreamsFactory;
    private byte[] frameHeader;
    private int frameHeaderBuffered;
    private int frameHeaderDumped;
    private int framePayloadLength;
    private int framePayloadDumped;
    private XMessageHeader xMessageHeader;
    private boolean compressionEnabled;
    private ByteArrayOutputStream bufferOut;
    private OutputStream compressorOut;
    private byte[] singleByte;
    private boolean closed;
    
    public CompressionSplittedOutputStream(final OutputStream out, final CompressorStreamsFactory ioStreamsFactory) {
        super(out);
        this.frameHeader = new byte[5];
        this.frameHeaderBuffered = 0;
        this.frameHeaderDumped = 0;
        this.framePayloadLength = 0;
        this.framePayloadDumped = 0;
        this.xMessageHeader = null;
        this.compressionEnabled = false;
        this.bufferOut = null;
        this.compressorOut = null;
        this.singleByte = new byte[1];
        this.closed = false;
        this.compressorIoStreamsFactory = ioStreamsFactory;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.out = null;
            this.bufferOut = null;
            if (this.compressorOut != null) {
                this.compressorOut.close();
            }
            this.compressorOut = null;
            this.closed = true;
        }
    }
    
    @Override
    public void write(final int b) throws IOException {
        this.ensureOpen();
        this.singleByte[0] = (byte)b;
        this.write(this.singleByte, 0, 1);
    }
    
    @Override
    public void write(final byte[] b) throws IOException {
        this.ensureOpen();
        this.write(b, 0, b.length);
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.ensureOpen();
        if ((off | len | b.length - (len + off) | off + len) < 0) {
            throw new IndexOutOfBoundsException();
        }
        final int bytesProcessed = this.peekFrameHeader(b, off, len);
        if (this.isFrameHeaderBuffered() && !this.isFrameHeaderWriteComplete()) {
            this.xMessageHeader = new XMessageHeader(this.frameHeader);
            this.framePayloadLength = this.xMessageHeader.getMessageSize();
            this.framePayloadDumped = 0;
            this.compressionEnabled = (this.framePayloadLength >= 250);
            if (this.compressionEnabled) {
                this.bufferOut = new ByteArrayOutputStream();
                (this.compressorOut = this.compressorIoStreamsFactory.getOutputStreamInstance(this.bufferOut)).write(this.frameHeader, 0, 5);
            }
            else {
                this.out.write(this.frameHeader, 0, 5);
            }
            this.frameHeaderDumped = 5;
        }
        final int bytesToDump = len - bytesProcessed;
        if (bytesToDump > 0) {
            if (this.compressionEnabled) {
                this.compressorOut.write(b, off + bytesProcessed, bytesToDump);
            }
            else {
                this.out.write(b, off + bytesProcessed, bytesToDump);
            }
        }
        this.framePayloadDumped += bytesToDump;
        this.finalizeWrite();
    }
    
    private int peekFrameHeader(final byte[] b, final int off, final int len) {
        if (this.isPayloadWriteReady()) {
            return 0;
        }
        int toCollect = 0;
        if (this.frameHeaderBuffered < 5) {
            toCollect = Math.min(len, 5 - this.frameHeaderBuffered);
            System.arraycopy(b, off, this.frameHeader, this.frameHeaderBuffered, toCollect);
            this.frameHeaderBuffered += toCollect;
        }
        return toCollect;
    }
    
    private boolean isFrameHeaderBuffered() {
        return this.frameHeaderBuffered == 5;
    }
    
    private boolean isFrameHeaderWriteComplete() {
        return this.frameHeaderDumped == 5;
    }
    
    private boolean isPayloadWriteReady() {
        return this.isFrameHeaderWriteComplete() && this.framePayloadDumped < this.framePayloadLength;
    }
    
    private boolean isWriteComplete() {
        return this.isFrameHeaderWriteComplete() && this.framePayloadDumped >= this.framePayloadLength;
    }
    
    private void finalizeWrite() throws IOException {
        if (this.isWriteComplete()) {
            if (this.compressionEnabled) {
                this.compressorOut.close();
                this.compressorOut = null;
                final byte[] compressedData = this.bufferOut.toByteArray();
                final MysqlxConnection.Compression compressedMessage = MysqlxConnection.Compression.newBuilder().setUncompressedSize(5 + this.framePayloadLength).setClientMessages(Mysqlx.ClientMessages.Type.forNumber(this.xMessageHeader.getMessageType())).setPayload(ByteString.copyFrom(compressedData)).build();
                final ByteBuffer messageHeader = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
                messageHeader.putInt(compressedMessage.getSerializedSize() + 1);
                messageHeader.put((byte)46);
                this.out.write(messageHeader.array());
                compressedMessage.writeTo(this.out);
                this.out.flush();
                this.compressionEnabled = false;
            }
            Arrays.fill(this.frameHeader, (byte)0);
            this.frameHeaderBuffered = 0;
            this.frameHeaderDumped = 0;
            this.framePayloadLength = 0;
            this.framePayloadDumped = 0;
            this.xMessageHeader = null;
        }
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
}
