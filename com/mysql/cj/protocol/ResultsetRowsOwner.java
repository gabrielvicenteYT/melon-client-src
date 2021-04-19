package com.mysql.cj.protocol;

import com.mysql.cj.*;

public interface ResultsetRowsOwner
{
    void closeOwner(final boolean p0);
    
    MysqlConnection getConnection();
    
    Session getSession();
    
    Object getSyncMutex();
    
    String getPointOfOrigin();
    
    int getOwnerFetchSize();
    
    Query getOwningQuery();
    
    int getOwningStatementMaxRows();
    
    int getOwningStatementFetchSize();
    
    long getOwningStatementServerId();
}
