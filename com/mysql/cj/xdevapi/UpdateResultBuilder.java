package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.exceptions.*;

public class UpdateResultBuilder<T extends Result> implements ResultBuilder<T>
{
    protected StatementExecuteOkBuilder statementExecuteOkBuilder;
    
    public UpdateResultBuilder() {
        this.statementExecuteOkBuilder = new StatementExecuteOkBuilder();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
            return false;
        }
        if (entity instanceof StatementExecuteOk) {
            return true;
        }
        if (entity instanceof Ok) {
            return true;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unexpected protocol entity " + entity);
    }
    
    @Override
    public T build() {
        return (T)new UpdateResult(this.statementExecuteOkBuilder.build());
    }
}
