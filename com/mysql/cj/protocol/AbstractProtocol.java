package com.mysql.cj.protocol;

import com.mysql.cj.log.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.*;

public abstract class AbstractProtocol<M extends Message> implements Protocol<M>
{
    protected Session session;
    protected SocketConnection socketConnection;
    protected PropertySet propertySet;
    protected TransactionEventHandler transactionManager;
    protected transient Log log;
    protected ExceptionInterceptor exceptionInterceptor;
    protected AuthenticationProvider<M> authProvider;
    protected MessageBuilder<M> messageBuilder;
    private PacketSentTimeHolder packetSentTimeHolder;
    private PacketReceivedTimeHolder packetReceivedTimeHolder;
    protected LinkedList<StringBuilder> packetDebugRingBuffer;
    protected boolean useNanosForElapsedTime;
    protected String queryTimingUnits;
    
    public AbstractProtocol() {
        this.packetSentTimeHolder = new PacketSentTimeHolder() {};
        this.packetReceivedTimeHolder = new PacketReceivedTimeHolder() {};
        this.packetDebugRingBuffer = null;
    }
    
    @Override
    public void init(final Session sess, final SocketConnection phConnection, final PropertySet propSet, final TransactionEventHandler trManager) {
        this.session = sess;
        this.propertySet = propSet;
        this.socketConnection = phConnection;
        this.exceptionInterceptor = this.socketConnection.getExceptionInterceptor();
        this.transactionManager = trManager;
        this.useNanosForElapsedTime = (this.propertySet.getBooleanProperty(PropertyKey.useNanosForElapsedTime).getValue() && TimeUtil.nanoTimeAvailable());
        this.queryTimingUnits = (this.useNanosForElapsedTime ? Messages.getString("Nanoseconds") : Messages.getString("Milliseconds"));
    }
    
    @Override
    public SocketConnection getSocketConnection() {
        return this.socketConnection;
    }
    
    @Override
    public AuthenticationProvider<M> getAuthenticationProvider() {
        return this.authProvider;
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public PacketSentTimeHolder getPacketSentTimeHolder() {
        return this.packetSentTimeHolder;
    }
    
    @Override
    public void setPacketSentTimeHolder(final PacketSentTimeHolder packetSentTimeHolder) {
        this.packetSentTimeHolder = packetSentTimeHolder;
    }
    
    @Override
    public PacketReceivedTimeHolder getPacketReceivedTimeHolder() {
        return this.packetReceivedTimeHolder;
    }
    
    @Override
    public void setPacketReceivedTimeHolder(final PacketReceivedTimeHolder packetReceivedTimeHolder) {
        this.packetReceivedTimeHolder = packetReceivedTimeHolder;
    }
    
    @Override
    public PropertySet getPropertySet() {
        return this.propertySet;
    }
    
    @Override
    public void setPropertySet(final PropertySet propertySet) {
        this.propertySet = propertySet;
    }
    
    @Override
    public MessageBuilder<M> getMessageBuilder() {
        return this.messageBuilder;
    }
    
    @Override
    public void reset() {
    }
    
    @Override
    public String getQueryTimingUnits() {
        return this.queryTimingUnits;
    }
}
