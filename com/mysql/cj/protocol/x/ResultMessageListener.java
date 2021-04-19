package com.mysql.cj.protocol.x;

import java.util.concurrent.*;
import com.google.protobuf.*;
import java.util.*;
import com.mysql.cj.x.protobuf.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public class ResultMessageListener<R> implements MessageListener<XMessage>
{
    private ResultBuilder<?> resultBuilder;
    private CompletableFuture<R> future;
    private Map<Class<? extends GeneratedMessageV3>, ProtocolEntityFactory<? extends ProtocolEntity, XMessage>> messageToProtocolEntityFactory;
    
    public ResultMessageListener(final Map<Class<? extends GeneratedMessageV3>, ProtocolEntityFactory<? extends ProtocolEntity, XMessage>> messageToProtocolEntityFactory, final ResultBuilder<R> resultBuilder, final CompletableFuture<R> future) {
        this.messageToProtocolEntityFactory = new HashMap<Class<? extends GeneratedMessageV3>, ProtocolEntityFactory<? extends ProtocolEntity, XMessage>>();
        this.messageToProtocolEntityFactory = messageToProtocolEntityFactory;
        this.resultBuilder = resultBuilder;
        this.future = future;
    }
    
    @Override
    public boolean processMessage(final XMessage message) {
        final Class<? extends GeneratedMessageV3> msgClass = (Class<? extends GeneratedMessageV3>)message.getMessage().getClass();
        if (Mysqlx.Error.class.equals(msgClass)) {
            this.future.completeExceptionally(new XProtocolError(Mysqlx.Error.class.cast(message.getMessage())));
        }
        else if (!this.messageToProtocolEntityFactory.containsKey(msgClass)) {
            this.future.completeExceptionally(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + message.getMessage()));
        }
        else {
            if (!this.resultBuilder.addProtocolEntity((ProtocolEntity)this.messageToProtocolEntityFactory.get(msgClass).createFromMessage(message))) {
                return false;
            }
            this.future.complete((R)this.resultBuilder.build());
        }
        return true;
    }
    
    @Override
    public void error(final Throwable ex) {
        this.future.completeExceptionally(ex);
    }
}
