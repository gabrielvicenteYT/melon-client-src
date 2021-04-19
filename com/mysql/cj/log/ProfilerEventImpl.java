package com.mysql.cj.log;

import java.util.*;
import com.mysql.cj.util.*;

public class ProfilerEventImpl implements ProfilerEvent
{
    private byte eventType;
    private String hostName;
    private String database;
    private long connectionId;
    private int statementId;
    private int resultSetId;
    private long eventCreationTime;
    private long eventDuration;
    private String durationUnits;
    private String eventCreationPointDesc;
    private String message;
    
    public ProfilerEventImpl(final byte eventType, final String hostName, final String db, final long connectionId, final int statementId, final int resultSetId, final long eventDuration, final String durationUnits, final Throwable eventCreationPoint, final String message) {
        this(eventType, hostName, db, connectionId, statementId, resultSetId, System.currentTimeMillis(), eventDuration, durationUnits, LogUtils.findCallingClassAndMethod(eventCreationPoint), message);
    }
    
    private ProfilerEventImpl(final byte eventType, final String hostName, final String db, final long connectionId, final int statementId, final int resultSetId, final long eventCreationTime, final long eventDuration, final String durationUnits, final String eventCreationPointDesc, final String message) {
        this.eventType = eventType;
        this.hostName = ((hostName == null) ? "" : hostName);
        this.database = ((db == null) ? "" : db);
        this.connectionId = connectionId;
        this.statementId = statementId;
        this.resultSetId = resultSetId;
        this.eventCreationTime = eventCreationTime;
        this.eventDuration = eventDuration;
        this.durationUnits = ((durationUnits == null) ? "" : durationUnits);
        this.eventCreationPointDesc = ((eventCreationPointDesc == null) ? "" : eventCreationPointDesc);
        this.message = ((message == null) ? "" : message);
    }
    
    @Override
    public byte getEventType() {
        return this.eventType;
    }
    
    @Override
    public String getHostName() {
        return this.hostName;
    }
    
    @Override
    public String getDatabase() {
        return this.database;
    }
    
    @Override
    public long getConnectionId() {
        return this.connectionId;
    }
    
    @Override
    public int getStatementId() {
        return this.statementId;
    }
    
    @Override
    public int getResultSetId() {
        return this.resultSetId;
    }
    
    @Override
    public long getEventCreationTime() {
        return this.eventCreationTime;
    }
    
    @Override
    public long getEventDuration() {
        return this.eventDuration;
    }
    
    @Override
    public String getDurationUnits() {
        return this.durationUnits;
    }
    
    @Override
    public String getEventCreationPointAsString() {
        return this.eventCreationPointDesc;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("[");
        switch (this.getEventType()) {
            case 4: {
                buf.append("EXECUTE");
                break;
            }
            case 5: {
                buf.append("FETCH");
                break;
            }
            case 1: {
                buf.append("CONSTRUCT");
                break;
            }
            case 2: {
                buf.append("PREPARE");
                break;
            }
            case 3: {
                buf.append("QUERY");
                break;
            }
            case 0: {
                buf.append("USAGE ADVISOR");
                break;
            }
            case 6: {
                buf.append("SLOW QUERY");
                break;
            }
            default: {
                buf.append("UNKNOWN");
                break;
            }
        }
        buf.append("] ");
        buf.append(this.message);
        buf.append(" [Created on: ");
        buf.append(new Date(this.eventCreationTime));
        buf.append(", duration: ");
        buf.append(this.eventDuration);
        buf.append(", connection-id: ");
        buf.append(this.connectionId);
        buf.append(", statement-id: ");
        buf.append(this.statementId);
        buf.append(", resultset-id: ");
        buf.append(this.resultSetId);
        buf.append(",");
        buf.append(this.eventCreationPointDesc);
        buf.append("]");
        return buf.toString();
    }
    
    public static ProfilerEvent unpack(final byte[] buf) {
        int pos = 0;
        final byte eventType = buf[pos++];
        final byte[] host = readBytes(buf, pos);
        pos += 4 + host.length;
        final byte[] db = readBytes(buf, pos);
        pos += 4 + db.length;
        final long connectionId = readLong(buf, pos);
        pos += 8;
        final int statementId = readInt(buf, pos);
        pos += 4;
        final int resultSetId = readInt(buf, pos);
        pos += 4;
        final long eventCreationTime = readLong(buf, pos);
        pos += 8;
        final long eventDuration = readLong(buf, pos);
        pos += 8;
        final byte[] eventDurationUnits = readBytes(buf, pos);
        pos += 4 + eventDurationUnits.length;
        final byte[] eventCreationAsBytes = readBytes(buf, pos);
        pos += 4 + eventCreationAsBytes.length;
        final byte[] message = readBytes(buf, pos);
        pos += 4 + message.length;
        return new ProfilerEventImpl(eventType, StringUtils.toString(host, "ISO8859_1"), StringUtils.toString(db, "ISO8859_1"), connectionId, statementId, resultSetId, eventCreationTime, eventDuration, StringUtils.toString(eventDurationUnits, "ISO8859_1"), StringUtils.toString(eventCreationAsBytes, "ISO8859_1"), StringUtils.toString(message, "ISO8859_1"));
    }
    
    @Override
    public byte[] pack() {
        final byte[] hostNameAsBytes = StringUtils.getBytes(this.hostName, "ISO8859_1");
        final byte[] dbAsBytes = StringUtils.getBytes(this.database, "ISO8859_1");
        final byte[] durationUnitsAsBytes = StringUtils.getBytes(this.durationUnits, "ISO8859_1");
        final byte[] eventCreationAsBytes = StringUtils.getBytes(this.eventCreationPointDesc, "ISO8859_1");
        final byte[] messageAsBytes = StringUtils.getBytes(this.message, "ISO8859_1");
        final int len = 1 + (4 + hostNameAsBytes.length) + (4 + dbAsBytes.length) + 8 + 4 + 4 + 8 + 8 + (4 + durationUnitsAsBytes.length) + (4 + eventCreationAsBytes.length) + (4 + messageAsBytes.length);
        final byte[] buf = new byte[len];
        int pos = 0;
        buf[pos++] = this.eventType;
        pos = writeBytes(hostNameAsBytes, buf, pos);
        pos = writeBytes(dbAsBytes, buf, pos);
        pos = writeLong(this.connectionId, buf, pos);
        pos = writeInt(this.statementId, buf, pos);
        pos = writeInt(this.resultSetId, buf, pos);
        pos = writeLong(this.eventCreationTime, buf, pos);
        pos = writeLong(this.eventDuration, buf, pos);
        pos = writeBytes(durationUnitsAsBytes, buf, pos);
        pos = writeBytes(eventCreationAsBytes, buf, pos);
        pos = writeBytes(messageAsBytes, buf, pos);
        return buf;
    }
    
    private static int writeInt(final int i, final byte[] buf, int pos) {
        buf[pos++] = (byte)(i & 0xFF);
        buf[pos++] = (byte)(i >>> 8);
        buf[pos++] = (byte)(i >>> 16);
        buf[pos++] = (byte)(i >>> 24);
        return pos;
    }
    
    private static int writeLong(final long l, final byte[] buf, int pos) {
        buf[pos++] = (byte)(l & 0xFFL);
        buf[pos++] = (byte)(l >>> 8);
        buf[pos++] = (byte)(l >>> 16);
        buf[pos++] = (byte)(l >>> 24);
        buf[pos++] = (byte)(l >>> 32);
        buf[pos++] = (byte)(l >>> 40);
        buf[pos++] = (byte)(l >>> 48);
        buf[pos++] = (byte)(l >>> 56);
        return pos;
    }
    
    private static int writeBytes(final byte[] msg, final byte[] buf, int pos) {
        pos = writeInt(msg.length, buf, pos);
        System.arraycopy(msg, 0, buf, pos, msg.length);
        return pos + msg.length;
    }
    
    private static int readInt(final byte[] buf, int pos) {
        return (buf[pos++] & 0xFF) | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24;
    }
    
    private static long readLong(final byte[] buf, int pos) {
        return (long)(buf[pos++] & 0xFF) | (long)(buf[pos++] & 0xFF) << 8 | (long)(buf[pos++] & 0xFF) << 16 | (long)(buf[pos++] & 0xFF) << 24 | (long)(buf[pos++] & 0xFF) << 32 | (long)(buf[pos++] & 0xFF) << 40 | (long)(buf[pos++] & 0xFF) << 48 | (long)(buf[pos++] & 0xFF) << 56;
    }
    
    private static byte[] readBytes(final byte[] buf, final int pos) {
        final int length = readInt(buf, pos);
        final byte[] msg = new byte[length];
        System.arraycopy(buf, pos + 4, msg, 0, length);
        return msg;
    }
}
