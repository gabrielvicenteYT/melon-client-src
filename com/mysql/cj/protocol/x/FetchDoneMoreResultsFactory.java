package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;

public class FetchDoneMoreResultsFactory implements ProtocolEntityFactory<FetchDoneMoreResults, XMessage>
{
    @Override
    public FetchDoneMoreResults createFromMessage(final XMessage message) {
        return new FetchDoneMoreResults();
    }
}
