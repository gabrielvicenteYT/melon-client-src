package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.*;
import java.util.*;

public class UpdateResult implements Result
{
    protected StatementExecuteOk ok;
    
    public UpdateResult(final StatementExecuteOk ok) {
        this.ok = ok;
    }
    
    @Override
    public long getAffectedItemsCount() {
        return this.ok.getAffectedItemsCount();
    }
    
    @Override
    public int getWarningsCount() {
        return this.ok.getWarningsCount();
    }
    
    @Override
    public Iterator<Warning> getWarnings() {
        return this.ok.getWarnings();
    }
}
