package org.slf4j.impl;

import org.slf4j.spi.*;
import org.slf4j.*;
import org.slf4j.helpers.*;

public class StaticMarkerBinder implements MarkerFactoryBinder
{
    public static final StaticMarkerBinder SINGLETON;
    final IMarkerFactory markerFactory;
    
    private StaticMarkerBinder() {
        this.markerFactory = new BasicMarkerFactory();
    }
    
    public IMarkerFactory getMarkerFactory() {
        return this.markerFactory;
    }
    
    public String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.class.getName();
    }
    
    static {
        SINGLETON = new StaticMarkerBinder();
    }
}
