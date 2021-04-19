package com.mysql.cj.protocol.x;

import com.mysql.cj.*;
import java.nio.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import com.google.protobuf.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import com.mysql.cj.protocol.*;

public class SyncMessageSender implements MessageSender<XMessage>, PacketSentTimeHolder
{
    static final int HEADER_LEN = 5;
    private OutputStream outputStream;
    private long lastPacketSentTime;
    private long previousPacketSentTime;
    private int maxAllowedPacket;
    Object waitingAsyncOperationMonitor;
    
    public SyncMessageSender(final OutputStream os) {
        this.lastPacketSentTime = 0L;
        this.previousPacketSentTime = 0L;
        this.maxAllowedPacket = -1;
        this.waitingAsyncOperationMonitor = new Object();
        this.outputStream = os;
    }
    
    @Override
    public void send(final XMessage message) {
        synchronized (this.waitingAsyncOperationMonitor) {
            final MessageLite msg = message.getMessage();
            try {
                final int type = MessageConstants.getTypeForMessageClass(msg.getClass());
                final int size = 1 + msg.getSerializedSize();
                if (this.maxAllowedPacket > 0 && size > this.maxAllowedPacket) {
                    throw new CJPacketTooBigException(Messages.getString("PacketTooBigException.1", new Object[] { size, this.maxAllowedPacket }));
                }
                final byte[] sizeHeader = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array();
                this.outputStream.write(sizeHeader);
                this.outputStream.write(type);
                msg.writeTo(this.outputStream);
                this.outputStream.flush();
                this.previousPacketSentTime = this.lastPacketSentTime;
                this.lastPacketSentTime = System.currentTimeMillis();
            }
            catch (IOException ex) {
                throw new CJCommunicationsException("Unable to write message", ex);
            }
        }
    }
    
    @Override
    public CompletableFuture<?> send(final XMessage message, final CompletableFuture<?> future, final Runnable callback) {
        synchronized (this.waitingAsyncOperationMonitor) {
            final CompletionHandler<Long, Void> resultHandler = new ErrorToFutureCompletionHandler<Long>(future, callback);
            final MessageLite msg = message.getMessage();
            try {
                this.send(message);
                final long result = 5 + msg.getSerializedSize();
                resultHandler.completed(result, null);
            }
            catch (Throwable t) {
                resultHandler.failed(t, null);
            }
            return future;
        }
    }
    
    @Override
    public long getLastPacketSentTime() {
        return this.lastPacketSentTime;
    }
    
    @Override
    public long getPreviousPacketSentTime() {
        return this.previousPacketSentTime;
    }
    
    @Override
    public void setMaxAllowedPacket(final int maxAllowedPacket) {
        this.maxAllowedPacket = maxAllowedPacket;
    }
}
