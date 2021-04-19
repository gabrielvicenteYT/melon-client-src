package com.mysql.cj.protocol;

public interface ResultBuilder<T>
{
    boolean addProtocolEntity(final ProtocolEntity p0);
    
    T build();
}
