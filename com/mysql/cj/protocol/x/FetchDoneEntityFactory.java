package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;

public class FetchDoneEntityFactory implements ProtocolEntityFactory<FetchDoneEntity, XMessage>
{
    @Override
    public FetchDoneEntity createFromMessage(final XMessage message) {
        return new FetchDoneEntity();
    }
}
