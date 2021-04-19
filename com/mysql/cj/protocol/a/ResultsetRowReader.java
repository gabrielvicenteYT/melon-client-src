package com.mysql.cj.protocol.a;

import com.mysql.cj.conf.*;
import java.util.*;
import java.io.*;
import com.mysql.cj.protocol.*;

public class ResultsetRowReader implements ProtocolEntityReader<ResultsetRow, NativePacketPayload>
{
    protected NativeProtocol protocol;
    protected PropertySet propertySet;
    protected RuntimeProperty<Integer> useBufferRowSizeThreshold;
    
    public ResultsetRowReader(final NativeProtocol prot) {
        this.protocol = prot;
        this.propertySet = this.protocol.getPropertySet();
        this.useBufferRowSizeThreshold = this.propertySet.getMemorySizeProperty(PropertyKey.largeRowSizeThreshold);
    }
    
    @Override
    public ResultsetRow read(final ProtocolEntityFactory<ResultsetRow, NativePacketPayload> sf) throws IOException {
        final AbstractRowFactory rf = (AbstractRowFactory)sf;
        NativePacketPayload rowPacket = null;
        final NativePacketHeader hdr = this.protocol.getPacketReader().readHeader();
        rowPacket = this.protocol.getPacketReader().readMessage(rf.canReuseRowPacketForBufferRow() ? Optional.ofNullable(this.protocol.getReusablePacket()) : Optional.empty(), hdr);
        this.protocol.checkErrorMessage(rowPacket);
        rowPacket.setPosition(rowPacket.getPosition() - 1);
        if ((!this.protocol.getServerSession().isEOFDeprecated() && rowPacket.isEOFPacket()) || (this.protocol.getServerSession().isEOFDeprecated() && rowPacket.isResultSetOKPacket())) {
            this.protocol.readServerStatusForResultSets(rowPacket, true);
            return null;
        }
        return sf.createFromMessage(rowPacket);
    }
}
