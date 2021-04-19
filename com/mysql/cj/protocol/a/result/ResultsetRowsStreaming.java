package com.mysql.cj.protocol.a.result;

import com.mysql.cj.result.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;

public class ResultsetRowsStreaming<T extends ProtocolEntity> extends AbstractResultsetRows implements ResultsetRows
{
    private NativeProtocol protocol;
    private boolean isAfterEnd;
    private boolean noMoreRows;
    private boolean isBinaryEncoded;
    private Row nextRow;
    private boolean streamerClosed;
    private ExceptionInterceptor exceptionInterceptor;
    private ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory;
    private NativeMessageBuilder commandBuilder;
    
    public ResultsetRowsStreaming(final NativeProtocol io, final ColumnDefinition columnDefinition, final boolean isBinaryEncoded, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) {
        this.isAfterEnd = false;
        this.noMoreRows = false;
        this.isBinaryEncoded = false;
        this.streamerClosed = false;
        this.commandBuilder = new NativeMessageBuilder();
        this.protocol = io;
        this.isBinaryEncoded = isBinaryEncoded;
        this.metadata = columnDefinition;
        this.exceptionInterceptor = this.protocol.getExceptionInterceptor();
        this.resultSetFactory = resultSetFactory;
        this.rowFactory = (this.isBinaryEncoded ? new BinaryRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, true) : new TextRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, true));
    }
    
    @Override
    public void close() {
        final Object mutex = (this.owner != null && this.owner.getSyncMutex() != null) ? this.owner.getSyncMutex() : this;
        boolean hadMore = false;
        int howMuchMore = 0;
        synchronized (mutex) {
            while (this.next() != null) {
                hadMore = true;
                if (++howMuchMore % 100 == 0) {
                    Thread.yield();
                }
            }
            if (!this.protocol.getPropertySet().getBooleanProperty(PropertyKey.clobberStreamingResults).getValue() && this.protocol.getPropertySet().getIntegerProperty(PropertyKey.netTimeoutForStreamingResults).getValue() > 0) {
                final int oldValue = this.protocol.getServerSession().getServerVariable("net_write_timeout", 60);
                this.protocol.clearInputStream();
                try {
                    this.protocol.sendCommand(this.commandBuilder.buildComQuery(this.protocol.getSharedSendPacket(), "SET net_write_timeout=" + oldValue, this.protocol.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue()), false, 0);
                }
                catch (Exception ex) {
                    throw ExceptionFactory.createException(ex.getMessage(), ex, this.exceptionInterceptor);
                }
            }
            if (this.protocol.getPropertySet().getBooleanProperty(PropertyKey.useUsageAdvisor).getValue() && hadMore) {
                this.owner.getSession().getProfilerEventHandler().processEvent((byte)0, this.owner.getSession(), this.owner.getOwningQuery(), null, 0L, new Throwable(), Messages.getString("RowDataDynamic.1", new String[] { String.valueOf(howMuchMore), this.owner.getPointOfOrigin() }));
            }
        }
        this.metadata = null;
        this.owner = null;
    }
    
    @Override
    public boolean hasNext() {
        final boolean hasNext = this.nextRow != null;
        if (!hasNext && !this.streamerClosed) {
            this.protocol.unsetStreamingData(this);
            this.streamerClosed = true;
        }
        return hasNext;
    }
    
    @Override
    public boolean isAfterLast() {
        return this.isAfterEnd;
    }
    
    @Override
    public boolean isBeforeFirst() {
        return this.currentPositionInFetchedRows < 0;
    }
    
    @Override
    public boolean isEmpty() {
        return this.wasEmpty;
    }
    
    @Override
    public boolean isFirst() {
        return this.currentPositionInFetchedRows == 0;
    }
    
    @Override
    public boolean isLast() {
        return !this.isBeforeFirst() && !this.isAfterLast() && this.noMoreRows;
    }
    
    @Override
    public Row next() {
        try {
            if (!this.noMoreRows) {
                this.nextRow = this.protocol.read(ResultsetRow.class, this.rowFactory);
                if (this.nextRow == null) {
                    this.noMoreRows = true;
                    this.isAfterEnd = true;
                    if (this.currentPositionInFetchedRows == -1) {
                        this.wasEmpty = true;
                    }
                }
            }
            else {
                this.nextRow = null;
                this.isAfterEnd = true;
            }
            if (this.nextRow == null && !this.streamerClosed) {
                if (this.protocol.getServerSession().hasMoreResults()) {
                    this.protocol.readNextResultset((ProtocolEntity)this.owner, this.owner.getOwningStatementMaxRows(), true, this.isBinaryEncoded, (ProtocolEntityFactory<ProtocolEntity, NativePacketPayload>)this.resultSetFactory);
                }
                else {
                    this.protocol.unsetStreamingData(this);
                    this.streamerClosed = true;
                }
            }
            if (this.nextRow != null && this.currentPositionInFetchedRows != Integer.MAX_VALUE) {
                ++this.currentPositionInFetchedRows;
            }
            return this.nextRow;
        }
        catch (CJException sqlEx) {
            if (sqlEx instanceof StreamingNotifiable) {
                ((StreamingNotifiable)sqlEx).setWasStreamingResults();
            }
            this.noMoreRows = true;
            throw sqlEx;
        }
        catch (Exception ex) {
            final CJException cjEx = ExceptionFactory.createException(Messages.getString("RowDataDynamic.2", new String[] { ex.getClass().getName(), ex.getMessage(), Util.stackTraceToString(ex) }), ex, this.exceptionInterceptor);
            throw cjEx;
        }
    }
    
    @Override
    public void afterLast() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void beforeFirst() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void beforeLast() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void moveRowRelative(final int rows) {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void setCurrentRow(final int rowNumber) {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
}
