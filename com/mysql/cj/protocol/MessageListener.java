package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.*;

public interface MessageListener<M extends Message>
{
    default boolean processMessage(final M message) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    void error(final Throwable p0);
}
