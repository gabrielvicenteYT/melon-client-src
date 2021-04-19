package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;

public class NoticeFactory implements ProtocolEntityFactory<Notice, XMessage>
{
    @Override
    public Notice createFromMessage(final XMessage message) {
        return Notice.getInstance(message);
    }
}
