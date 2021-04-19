package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;

public class OkFactory implements ProtocolEntityFactory<Ok, XMessage>
{
    @Override
    public Ok createFromMessage(final XMessage message) {
        return new Ok();
    }
}
