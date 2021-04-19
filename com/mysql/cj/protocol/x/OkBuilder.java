package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;
import com.mysql.cj.exceptions.*;

public class OkBuilder implements ResultBuilder<Ok>
{
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Notice) {
            return false;
        }
        if (entity instanceof Ok) {
            return true;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unexpected protocol entity " + entity);
    }
    
    @Override
    public Ok build() {
        return new Ok();
    }
}
