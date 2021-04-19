package com.mysql.cj;

import com.mysql.cj.log.*;
import com.mysql.cj.conf.*;
import java.net.*;
import com.mysql.cj.result.*;
import java.util.function.*;
import java.util.stream.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;
import java.util.concurrent.*;

public interface Session
{
    PropertySet getPropertySet();
    
     <M extends Message> MessageBuilder<M> getMessageBuilder();
    
    void changeUser(final String p0, final String p1, final String p2);
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void setExceptionInterceptor(final ExceptionInterceptor p0);
    
    void quit();
    
    void forceClose();
    
    boolean versionMeetsMinimum(final int p0, final int p1, final int p2);
    
    long getThreadId();
    
    boolean isSetNeededForAutoCommitMode(final boolean p0);
    
    Log getLog();
    
    ProfilerEventHandler getProfilerEventHandler();
    
    HostInfo getHostInfo();
    
    String getQueryTimingUnits();
    
    ServerSession getServerSession();
    
    boolean isSSLEstablished();
    
    SocketAddress getRemoteSocketAddress();
    
    String getProcessHost();
    
    void addListener(final SessionEventListener p0);
    
    void removeListener(final SessionEventListener p0);
    
    boolean isClosed();
    
    String getIdentifierQuoteString();
    
    DataStoreMetadata getDataStoreMetadata();
    
    default <M extends Message, R, RES> RES query(final M message, final Predicate<Row> rowFilter, final Function<Row, R> rowMapper, final Collector<R, ?, RES> collector) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default <M extends Message, R extends QueryResult> R query(final M message, final ResultBuilder<R> resultBuilder) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default <M extends Message, R extends QueryResult> CompletableFuture<R> queryAsync(final M message, final ResultBuilder<R> resultBuilder) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    public interface SessionEventListener
    {
        void handleNormalClose();
        
        void handleReconnect();
        
        void handleCleanup(final Throwable p0);
    }
}
