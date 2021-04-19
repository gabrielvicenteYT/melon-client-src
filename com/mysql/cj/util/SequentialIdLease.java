package com.mysql.cj.util;

import java.util.*;

public class SequentialIdLease
{
    private Set<Integer> sequentialIdsLease;
    
    public SequentialIdLease() {
        this.sequentialIdsLease = new TreeSet<Integer>();
    }
    
    public int allocateSequentialId() {
        int nextSequentialId = 0;
        for (Iterator<Integer> it = this.sequentialIdsLease.iterator(); it.hasNext() && nextSequentialId + 1 == it.next(); ++nextSequentialId) {}
        this.sequentialIdsLease.add(++nextSequentialId);
        return nextSequentialId;
    }
    
    public void releaseSequentialId(final int sequentialId) {
        this.sequentialIdsLease.remove(sequentialId);
    }
}
