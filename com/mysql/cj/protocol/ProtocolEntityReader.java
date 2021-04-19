package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.*;
import java.io.*;

public interface ProtocolEntityReader<T extends ProtocolEntity, M extends Message>
{
    default T read(final ProtocolEntityFactory<T, M> sf) throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    default T read(final int maxRows, final boolean streamResults, final M resultPacket, final ColumnDefinition metadata, final ProtocolEntityFactory<T, M> protocolEntityFactory) throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
}
