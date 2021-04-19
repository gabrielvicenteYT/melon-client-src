package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.a.result.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.exceptions.*;

public class ResultsetFactory implements ProtocolEntityFactory<Resultset, NativePacketPayload>
{
    private Resultset.Type type;
    private Resultset.Concurrency concurrency;
    
    public ResultsetFactory(final Resultset.Type type, final Resultset.Concurrency concurrency) {
        this.type = Resultset.Type.FORWARD_ONLY;
        this.concurrency = Resultset.Concurrency.READ_ONLY;
        this.type = type;
        this.concurrency = concurrency;
    }
    
    @Override
    public Resultset.Type getResultSetType() {
        return this.type;
    }
    
    @Override
    public Resultset.Concurrency getResultSetConcurrency() {
        return this.concurrency;
    }
    
    @Override
    public Resultset createFromProtocolEntity(final ProtocolEntity protocolEntity) {
        if (protocolEntity instanceof OkPacket) {
            return new NativeResultset((OkPacket)protocolEntity);
        }
        if (protocolEntity instanceof ResultsetRows) {
            return new NativeResultset((ResultsetRows)protocolEntity);
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unknown ProtocolEntity class " + protocolEntity);
    }
}
