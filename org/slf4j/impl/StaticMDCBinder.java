package org.slf4j.impl;

import org.slf4j.spi.*;
import org.slf4j.helpers.*;

public class StaticMDCBinder
{
    public static final StaticMDCBinder SINGLETON;
    
    private StaticMDCBinder() {
    }
    
    public MDCAdapter getMDCA() {
        return new NOPMDCAdapter();
    }
    
    public String getMDCAdapterClassStr() {
        return NOPMDCAdapter.class.getName();
    }
    
    static {
        SINGLETON = new StaticMDCBinder();
    }
}
