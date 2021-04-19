package com.mysql.cj.protocol;

import java.nio.*;

public interface MessageHeader
{
    ByteBuffer getBuffer();
    
    int getMessageSize();
    
    byte getMessageSequence();
}
