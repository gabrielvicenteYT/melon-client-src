package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import java.lang.ref.*;

public abstract class PreparableStatement<RES_T>
{
    protected int preparedStatementId;
    protected PreparedState preparedState;
    protected MysqlxSession mysqlxSession;
    
    public PreparableStatement() {
        this.preparedStatementId = 0;
        this.preparedState = PreparedState.UNPREPARED;
    }
    
    protected XMessageBuilder getMessageBuilder() {
        return (XMessageBuilder)this.mysqlxSession.getMessageBuilder();
    }
    
    protected void resetPrepareState() {
        if (this.preparedState == PreparedState.PREPARED || this.preparedState == PreparedState.REPREPARE) {
            this.preparedState = PreparedState.DEALLOCATE;
        }
        else if (this.preparedState == PreparedState.PREPARE) {
            this.preparedState = PreparedState.UNPREPARED;
        }
    }
    
    protected void setReprepareState() {
        if (this.preparedState == PreparedState.PREPARED) {
            this.preparedState = PreparedState.REPREPARE;
        }
    }
    
    public RES_T execute() {
        while (true) {
            switch (this.preparedState) {
                case UNSUPPORTED: {
                    return this.executeStatement();
                }
                case UNPREPARED: {
                    final RES_T result = this.executeStatement();
                    this.preparedState = PreparedState.PREPARE;
                    return result;
                }
                case SUSPENDED: {
                    if (!this.mysqlxSession.supportsPreparedStatements()) {
                        this.preparedState = PreparedState.UNSUPPORTED;
                        continue;
                    }
                    if (this.mysqlxSession.readyForPreparingStatements()) {
                        this.preparedState = PreparedState.PREPARE;
                        continue;
                    }
                    return this.executeStatement();
                }
                case PREPARE: {
                    this.preparedState = (this.prepareStatement() ? PreparedState.PREPARED : PreparedState.SUSPENDED);
                    continue;
                }
                case PREPARED: {
                    return this.executePreparedStatement();
                }
                case DEALLOCATE: {
                    this.deallocatePrepared();
                    this.preparedState = PreparedState.UNPREPARED;
                    continue;
                }
                case REPREPARE: {
                    this.deallocatePrepared();
                    this.preparedState = PreparedState.PREPARE;
                    continue;
                }
            }
        }
    }
    
    protected abstract RES_T executeStatement();
    
    protected abstract XMessage getPrepareStatementXMessage();
    
    private boolean prepareStatement() {
        if (!this.mysqlxSession.supportsPreparedStatements()) {
            return false;
        }
        try {
            this.preparedStatementId = this.mysqlxSession.getNewPreparedStatementId(this);
            this.mysqlxSession.query(this.getPrepareStatementXMessage(), new UpdateResultBuilder<QueryResult>());
        }
        catch (XProtocolError e) {
            if (this.mysqlxSession.failedPreparingStatement(this.preparedStatementId, e)) {
                this.preparedStatementId = 0;
                return false;
            }
            this.preparedStatementId = 0;
            throw e;
        }
        catch (Throwable t) {
            this.preparedStatementId = 0;
            throw t;
        }
        return true;
    }
    
    protected abstract RES_T executePreparedStatement();
    
    protected void deallocatePrepared() {
        if (this.preparedState != PreparedState.PREPARED && this.preparedState != PreparedState.DEALLOCATE) {
            if (this.preparedState != PreparedState.REPREPARE) {
                return;
            }
        }
        try {
            this.mysqlxSession.query(this.getMessageBuilder().buildPrepareDeallocate(this.preparedStatementId), new UpdateResultBuilder<QueryResult>());
        }
        finally {
            this.mysqlxSession.freePreparedStatementId(this.preparedStatementId);
            this.preparedStatementId = 0;
        }
    }
    
    protected enum PreparedState
    {
        UNSUPPORTED, 
        UNPREPARED, 
        SUSPENDED, 
        PREPARED, 
        PREPARE, 
        DEALLOCATE, 
        REPREPARE;
    }
    
    public static class PreparableStatementFinalizer extends PhantomReference<PreparableStatement<?>>
    {
        int prepredStatementId;
        
        public PreparableStatementFinalizer(final PreparableStatement<?> referent, final ReferenceQueue<? super PreparableStatement<?>> q, final int preparedStatementId) {
            super(referent, q);
            this.prepredStatementId = preparedStatementId;
        }
        
        public int getPreparedStatementId() {
            return this.prepredStatementId;
        }
    }
}
