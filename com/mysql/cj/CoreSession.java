package com.mysql.cj;

import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import java.net.*;
import com.mysql.cj.log.*;

public abstract class CoreSession implements Session
{
    protected PropertySet propertySet;
    protected ExceptionInterceptor exceptionInterceptor;
    protected transient Log log;
    protected static final Log NULL_LOGGER;
    protected transient Protocol<? extends Message> protocol;
    protected MessageBuilder<? extends Message> messageBuilder;
    protected long connectionCreationTimeMillis;
    protected HostInfo hostInfo;
    protected RuntimeProperty<Boolean> gatherPerfMetrics;
    protected RuntimeProperty<String> characterEncoding;
    protected RuntimeProperty<Boolean> disconnectOnExpiredPasswords;
    protected RuntimeProperty<Boolean> cacheServerConfiguration;
    protected RuntimeProperty<Boolean> autoReconnect;
    protected RuntimeProperty<Boolean> autoReconnectForPools;
    protected RuntimeProperty<Boolean> maintainTimeStats;
    protected int sessionMaxRows;
    private ProfilerEventHandler eventSink;
    
    public CoreSession(final HostInfo hostInfo, final PropertySet propSet) {
        this.connectionCreationTimeMillis = 0L;
        this.hostInfo = null;
        this.sessionMaxRows = -1;
        this.connectionCreationTimeMillis = System.currentTimeMillis();
        this.hostInfo = hostInfo;
        this.propertySet = propSet;
        this.gatherPerfMetrics = this.getPropertySet().getBooleanProperty(PropertyKey.gatherPerfMetrics);
        this.characterEncoding = this.getPropertySet().getStringProperty(PropertyKey.characterEncoding);
        this.disconnectOnExpiredPasswords = this.getPropertySet().getBooleanProperty(PropertyKey.disconnectOnExpiredPasswords);
        this.cacheServerConfiguration = this.getPropertySet().getBooleanProperty(PropertyKey.cacheServerConfiguration);
        this.autoReconnect = this.getPropertySet().getBooleanProperty(PropertyKey.autoReconnect);
        this.autoReconnectForPools = this.getPropertySet().getBooleanProperty(PropertyKey.autoReconnectForPools);
        this.maintainTimeStats = this.getPropertySet().getBooleanProperty(PropertyKey.maintainTimeStats);
        this.log = LogFactory.getLogger(this.getPropertySet().getStringProperty(PropertyKey.logger).getStringValue(), "MySQL");
    }
    
    @Override
    public void changeUser(final String user, final String password, final String database) {
        this.sessionMaxRows = -1;
        this.protocol.changeUser(user, password, database);
    }
    
    @Override
    public PropertySet getPropertySet() {
        return this.propertySet;
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public void setExceptionInterceptor(final ExceptionInterceptor exceptionInterceptor) {
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public Log getLog() {
        return this.log;
    }
    
    @Override
    public HostInfo getHostInfo() {
        return this.hostInfo;
    }
    
    @Override
    public <M extends Message> MessageBuilder<M> getMessageBuilder() {
        return (MessageBuilder<M>)this.messageBuilder;
    }
    
    @Override
    public ServerSession getServerSession() {
        return this.protocol.getServerSession();
    }
    
    @Override
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) {
        return this.protocol.versionMeetsMinimum(major, minor, subminor);
    }
    
    @Override
    public long getThreadId() {
        return this.protocol.getServerSession().getThreadId();
    }
    
    @Override
    public void quit() {
        if (this.eventSink != null) {
            this.eventSink.destroy();
            this.eventSink = null;
        }
    }
    
    @Override
    public void forceClose() {
        if (this.eventSink != null) {
            this.eventSink.destroy();
            this.eventSink = null;
        }
    }
    
    @Override
    public boolean isSetNeededForAutoCommitMode(final boolean autoCommitFlag) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public ProfilerEventHandler getProfilerEventHandler() {
        if (this.eventSink == null) {
            synchronized (this) {
                if (this.eventSink == null) {
                    (this.eventSink = (ProfilerEventHandler)Util.getInstance(this.propertySet.getStringProperty(PropertyKey.profilerEventHandler).getStringValue(), new Class[0], new Object[0], this.exceptionInterceptor)).init(this.log);
                }
            }
        }
        return this.eventSink;
    }
    
    @Override
    public boolean isSSLEstablished() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public SocketAddress getRemoteSocketAddress() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void addListener(final SessionEventListener l) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void removeListener(final SessionEventListener l) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getIdentifierQuoteString() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public DataStoreMetadata getDataStoreMetadata() {
        return new DataStoreMetadataImpl(this);
    }
    
    @Override
    public String getQueryTimingUnits() {
        return this.protocol.getQueryTimingUnits();
    }
    
    static {
        NULL_LOGGER = new NullLogger("MySQL");
    }
}
