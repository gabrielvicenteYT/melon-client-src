package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;
import java.util.*;
import com.mysql.cj.xdevapi.*;
import java.util.stream.*;

public class StatementExecuteOk implements ProtocolEntity, Result
{
    private long rowsAffected;
    private Long lastInsertId;
    private List<String> generatedIds;
    private List<Warning> warnings;
    
    public StatementExecuteOk() {
        this.rowsAffected = 0L;
        this.lastInsertId = null;
        this.generatedIds = Collections.emptyList();
        this.warnings = new ArrayList<Warning>();
    }
    
    public StatementExecuteOk(final long rowsAffected, final Long lastInsertId, final List<String> generatedIds, final List<Warning> warnings) {
        this.rowsAffected = 0L;
        this.lastInsertId = null;
        this.rowsAffected = rowsAffected;
        this.lastInsertId = lastInsertId;
        this.generatedIds = Collections.unmodifiableList((List<? extends String>)generatedIds);
        this.warnings = warnings;
    }
    
    @Override
    public long getAffectedItemsCount() {
        return this.rowsAffected;
    }
    
    public Long getLastInsertId() {
        return this.lastInsertId;
    }
    
    public List<String> getGeneratedIds() {
        return this.generatedIds;
    }
    
    @Override
    public int getWarningsCount() {
        return this.warnings.size();
    }
    
    @Override
    public Iterator<com.mysql.cj.xdevapi.Warning> getWarnings() {
        return this.warnings.stream().map(w -> new WarningImpl(w)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).iterator();
    }
}
