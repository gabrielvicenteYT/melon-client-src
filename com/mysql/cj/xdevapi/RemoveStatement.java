package com.mysql.cj.xdevapi;

public interface RemoveStatement extends Statement<RemoveStatement, Result>
{
    @Deprecated
    RemoveStatement orderBy(final String... p0);
    
    RemoveStatement sort(final String... p0);
    
    RemoveStatement limit(final long p0);
}
