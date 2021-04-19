package com.mysql.cj.protocol.x;

import com.mysql.cj.x.protobuf.*;
import com.mysql.cj.protocol.*;

public class XProtocolRowFactory implements ProtocolEntityFactory<XProtocolRow, XMessage>
{
    @Override
    public XProtocolRow createFromMessage(final XMessage message) {
        return new XProtocolRow(MysqlxResultset.Row.class.cast(message.getMessage()));
    }
}
