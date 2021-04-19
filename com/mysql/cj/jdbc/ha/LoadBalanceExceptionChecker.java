package com.mysql.cj.jdbc.ha;

import java.util.*;

public interface LoadBalanceExceptionChecker
{
    void init(final Properties p0);
    
    void destroy();
    
    boolean shouldExceptionTriggerFailover(final Throwable p0);
}
