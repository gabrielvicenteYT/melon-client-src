package com.mysql.cj.xdevapi;

import java.util.*;

public interface FetchResult<T> extends Iterator<T>, Iterable<T>
{
    default boolean hasData() {
        return true;
    }
    
    default T fetchOne() {
        if (this.hasNext()) {
            return this.next();
        }
        return null;
    }
    
    default Iterator<T> iterator() {
        return this.fetchAll().iterator();
    }
    
    long count();
    
    List<T> fetchAll();
}
