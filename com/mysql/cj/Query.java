package com.mysql.cj;

import com.mysql.cj.protocol.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public interface Query
{
    int getId();
    
    void setCancelStatus(final CancelStatus p0);
    
    void checkCancelTimeout();
    
     <T extends Resultset, M extends Message> ProtocolEntityFactory<T, M> getResultSetFactory();
    
    Session getSession();
    
    Object getCancelTimeoutMutex();
    
    void resetCancelledState();
    
    void closeQuery();
    
    void addBatch(final Object p0);
    
    List<Object> getBatchedArgs();
    
    void clearBatchedArgs();
    
    int getResultFetchSize();
    
    void setResultFetchSize(final int p0);
    
    Resultset.Type getResultType();
    
    void setResultType(final Resultset.Type p0);
    
    int getTimeoutInMillis();
    
    void setTimeoutInMillis(final int p0);
    
    void setExecuteTime(final long p0);
    
    long getExecuteTime();
    
    CancelQueryTask startQueryTimer(final Query p0, final int p1);
    
    AtomicBoolean getStatementExecuting();
    
    String getCurrentDatabase();
    
    void setCurrentDatabase(final String p0);
    
    boolean isClearWarningsCalled();
    
    void setClearWarningsCalled(final boolean p0);
    
    void statementBegins();
    
    void stopQueryTimer(final CancelQueryTask p0, final boolean p1, final boolean p2);
    
    public enum CancelStatus
    {
        NOT_CANCELED, 
        CANCELED_BY_USER, 
        CANCELED_BY_TIMEOUT;
    }
}
