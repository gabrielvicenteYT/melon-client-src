package org.slf4j.impl;

import java.util.*;
import org.slf4j.*;

public class SimpleLoggerFactory implements ILoggerFactory
{
    static final SimpleLoggerFactory INSTANCE;
    Map loggerMap;
    
    public SimpleLoggerFactory() {
        this.loggerMap = new HashMap();
    }
    
    public Logger getLogger(final String name) {
        Logger slogger = null;
        synchronized (this) {
            slogger = this.loggerMap.get(name);
            if (slogger == null) {
                slogger = new SimpleLogger(name);
                this.loggerMap.put(name, slogger);
            }
        }
        return slogger;
    }
    
    static {
        INSTANCE = new SimpleLoggerFactory();
    }
}
