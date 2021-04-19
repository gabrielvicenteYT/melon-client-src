package com.mysql.cj.protocol.a;

import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public abstract class AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow, NativePacketPayload>
{
    protected ColumnDefinition columnDefinition;
    protected Resultset.Concurrency resultSetConcurrency;
    protected boolean canReuseRowPacketForBufferRow;
    protected RuntimeProperty<Integer> useBufferRowSizeThreshold;
    protected ExceptionInterceptor exceptionInterceptor;
    protected ValueDecoder valueDecoder;
    
    public boolean canReuseRowPacketForBufferRow() {
        return this.canReuseRowPacketForBufferRow;
    }
}
