package com.mysql.cj.log;

import com.mysql.cj.*;
import com.mysql.cj.protocol.*;

public interface ProfilerEventHandler
{
    void init(final Log p0);
    
    void destroy();
    
    void consumeEvent(final ProfilerEvent p0);
    
    void processEvent(final byte p0, final Session p1, final Query p2, final Resultset p3, final long p4, final Throwable p5, final String p6);
}
