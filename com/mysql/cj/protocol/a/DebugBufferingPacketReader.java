package com.mysql.cj.protocol.a;

import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import java.io.*;
import com.mysql.cj.util.*;
import java.util.*;
import com.mysql.cj.protocol.*;

public class DebugBufferingPacketReader implements MessageReader<NativePacketHeader, NativePacketPayload>
{
    private static final int MAX_PACKET_DUMP_LENGTH = 1024;
    private static final int DEBUG_MSG_LEN = 96;
    private MessageReader<NativePacketHeader, NativePacketPayload> packetReader;
    private LinkedList<StringBuilder> packetDebugBuffer;
    private RuntimeProperty<Integer> packetDebugBufferSize;
    private String lastHeaderPayload;
    private boolean packetSequenceReset;
    
    public DebugBufferingPacketReader(final MessageReader<NativePacketHeader, NativePacketPayload> packetReader, final LinkedList<StringBuilder> packetDebugBuffer, final RuntimeProperty<Integer> packetDebugBufferSize) {
        this.lastHeaderPayload = "";
        this.packetSequenceReset = false;
        this.packetReader = packetReader;
        this.packetDebugBuffer = packetDebugBuffer;
        this.packetDebugBufferSize = packetDebugBufferSize;
    }
    
    @Override
    public NativePacketHeader readHeader() throws IOException {
        final byte prevPacketSeq = this.packetReader.getMessageSequence();
        final NativePacketHeader hdr = this.packetReader.readHeader();
        final byte currPacketSeq = hdr.getMessageSequence();
        if (!this.packetSequenceReset) {
            if (currPacketSeq == -128 && prevPacketSeq != 127) {
                throw new IOException(Messages.getString("PacketReader.9", new Object[] { "-128", currPacketSeq }));
            }
            if (prevPacketSeq == -1 && currPacketSeq != 0) {
                throw new IOException(Messages.getString("PacketReader.9", new Object[] { "-1", currPacketSeq }));
            }
            if (currPacketSeq != -128 && prevPacketSeq != -1 && currPacketSeq != prevPacketSeq + 1) {
                throw new IOException(Messages.getString("PacketReader.9", new Object[] { prevPacketSeq + 1, currPacketSeq }));
            }
        }
        else {
            this.packetSequenceReset = false;
        }
        this.lastHeaderPayload = StringUtils.dumpAsHex(hdr.getBuffer().array(), 4);
        return hdr;
    }
    
    @Override
    public NativePacketPayload readMessage(final Optional<NativePacketPayload> reuse, final NativePacketHeader header) throws IOException {
        final int packetLength = header.getMessageSize();
        final NativePacketPayload buf = this.packetReader.readMessage(reuse, header);
        final int bytesToDump = Math.min(1024, packetLength);
        final String PacketPayloadImpl = StringUtils.dumpAsHex(buf.getByteBuffer(), bytesToDump);
        final StringBuilder packetDump = new StringBuilder(100 + PacketPayloadImpl.length());
        packetDump.append("Server ");
        packetDump.append(reuse.isPresent() ? "(re-used) " : "(new) ");
        packetDump.append(buf.toString());
        packetDump.append(" --------------------> Client\n");
        packetDump.append("\nPacket payload:\n\n");
        packetDump.append(this.lastHeaderPayload);
        packetDump.append(PacketPayloadImpl);
        if (bytesToDump == 1024) {
            packetDump.append("\nNote: Packet of " + packetLength + " bytes truncated to " + 1024 + " bytes.\n");
        }
        if (this.packetDebugBuffer.size() + 1 > this.packetDebugBufferSize.getValue()) {
            this.packetDebugBuffer.removeFirst();
        }
        this.packetDebugBuffer.addLast(packetDump);
        return buf;
    }
    
    @Override
    public byte getMessageSequence() {
        return this.packetReader.getMessageSequence();
    }
    
    @Override
    public void resetMessageSequence() {
        this.packetReader.resetMessageSequence();
        this.packetSequenceReset = true;
    }
    
    @Override
    public MessageReader<NativePacketHeader, NativePacketPayload> undecorateAll() {
        return this.packetReader.undecorateAll();
    }
    
    @Override
    public MessageReader<NativePacketHeader, NativePacketPayload> undecorate() {
        return this.packetReader;
    }
}
