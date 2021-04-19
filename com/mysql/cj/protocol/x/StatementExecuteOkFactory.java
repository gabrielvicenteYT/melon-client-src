package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;

public class StatementExecuteOkFactory implements ProtocolEntityFactory<StatementExecuteOk, XMessage>
{
    @Override
    public StatementExecuteOk createFromMessage(final XMessage message) {
        return new StatementExecuteOk();
    }
}
