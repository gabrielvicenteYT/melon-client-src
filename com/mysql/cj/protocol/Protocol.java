package com.mysql.cj.protocol;

import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import com.mysql.cj.*;

public interface Protocol<M extends Message>
{
    void init(final Session p0, final SocketConnection p1, final PropertySet p2, final TransactionEventHandler p3);
    
    PropertySet getPropertySet();
    
    void setPropertySet(final PropertySet p0);
    
    MessageBuilder<M> getMessageBuilder();
    
    ServerCapabilities readServerCapabilities();
    
    ServerSession getServerSession();
    
    SocketConnection getSocketConnection();
    
    AuthenticationProvider<M> getAuthenticationProvider();
    
    ExceptionInterceptor getExceptionInterceptor();
    
    PacketSentTimeHolder getPacketSentTimeHolder();
    
    void setPacketSentTimeHolder(final PacketSentTimeHolder p0);
    
    PacketReceivedTimeHolder getPacketReceivedTimeHolder();
    
    void setPacketReceivedTimeHolder(final PacketReceivedTimeHolder p0);
    
    void connect(final String p0, final String p1, final String p2);
    
    void negotiateSSLConnection();
    
    void beforeHandshake();
    
    void afterHandshake();
    
    void changeDatabase(final String p0);
    
    void changeUser(final String p0, final String p1, final String p2);
    
    String getPasswordCharacterEncoding();
    
    boolean versionMeetsMinimum(final int p0, final int p1, final int p2);
    
    M readMessage(final M p0);
    
    M checkErrorMessage();
    
    void send(final Message p0, final int p1);
    
    ColumnDefinition readMetadata();
    
    M sendCommand(final Message p0, final boolean p1, final int p2);
    
     <T extends ProtocolEntity> T read(final Class<T> p0, final ProtocolEntityFactory<T, M> p1) throws IOException;
    
     <T extends ProtocolEntity> T read(final Class<Resultset> p0, final int p1, final boolean p2, final M p3, final boolean p4, final ColumnDefinition p5, final ProtocolEntityFactory<T, M> p6) throws IOException;
    
    void setLocalInfileInputStream(final InputStream p0);
    
    InputStream getLocalInfileInputStream();
    
    String getQueryComment();
    
    void setQueryComment(final String p0);
    
     <T extends QueryResult> T readQueryResult(final ResultBuilder<T> p0);
    
    void close() throws IOException;
    
    void configureTimeZone();
    
    void initServerSession();
    
    void reset();
    
    String getQueryTimingUnits();
}
