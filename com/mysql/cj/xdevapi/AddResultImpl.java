package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.*;
import java.util.*;

public class AddResultImpl extends UpdateResult implements AddResult
{
    public AddResultImpl(final StatementExecuteOk ok) {
        super(ok);
    }
    
    @Override
    public List<String> getGeneratedIds() {
        return this.ok.getGeneratedIds();
    }
}
