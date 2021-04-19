package com.mysql.cj.protocol.x;

import java.util.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.exceptions.*;
import java.util.stream.*;
import com.mysql.cj.x.protobuf.*;

public class StatementExecuteOkBuilder implements ResultBuilder<StatementExecuteOk>
{
    private long rowsAffected;
    private Long lastInsertId;
    private List<String> generatedIds;
    private List<Warning> warnings;
    
    public StatementExecuteOkBuilder() {
        this.rowsAffected = 0L;
        this.lastInsertId = null;
        this.generatedIds = Collections.emptyList();
        this.warnings = new ArrayList<Warning>();
    }
    
    @Override
    public boolean addProtocolEntity(final ProtocolEntity entity) {
        if (entity instanceof Notice) {
            this.addNotice((Notice)entity);
            return false;
        }
        if (entity instanceof FetchDoneEntity) {
            return false;
        }
        if (entity instanceof StatementExecuteOk) {
            return true;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unexpected protocol entity " + entity);
    }
    
    @Override
    public StatementExecuteOk build() {
        return new StatementExecuteOk(this.rowsAffected, this.lastInsertId, this.generatedIds, this.warnings);
    }
    
    private void addNotice(final Notice notice) {
        if (notice instanceof Notice.XWarning) {
            this.warnings.add((Notice.XWarning)notice);
        }
        else if (notice instanceof Notice.XSessionStateChanged) {
            switch (((Notice.XSessionStateChanged)notice).getParamType()) {
                case 3: {
                    this.lastInsertId = ((Notice.XSessionStateChanged)notice).getValue().getVUnsignedInt();
                    break;
                }
                case 4: {
                    this.rowsAffected = ((Notice.XSessionStateChanged)notice).getValue().getVUnsignedInt();
                    break;
                }
                case 12: {
                    this.generatedIds = ((Notice.XSessionStateChanged)notice).getValueList().stream().map(v -> v.getVOctets().getValue().toStringUtf8()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
                    break;
                }
            }
        }
    }
}
