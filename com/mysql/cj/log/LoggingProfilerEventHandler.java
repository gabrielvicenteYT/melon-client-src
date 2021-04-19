package com.mysql.cj.log;

import com.mysql.cj.protocol.*;
import com.mysql.cj.*;

public class LoggingProfilerEventHandler implements ProfilerEventHandler
{
    private Log logger;
    
    @Override
    public void consumeEvent(final ProfilerEvent evt) {
        switch (evt.getEventType()) {
            case 0: {
                this.logger.logWarn(evt);
                break;
            }
            default: {
                this.logger.logInfo(evt);
                break;
            }
        }
    }
    
    @Override
    public void destroy() {
        this.logger = null;
    }
    
    @Override
    public void init(final Log log) {
        this.logger = log;
    }
    
    @Override
    public void processEvent(final byte eventType, final Session session, final Query query, final Resultset resultSet, final long eventDuration, final Throwable eventCreationPoint, final String message) {
        this.consumeEvent(new ProfilerEventImpl(eventType, (session == null) ? "" : session.getHostInfo().getHost(), (session == null) ? "" : session.getHostInfo().getDatabase(), (session == null) ? -1L : session.getThreadId(), (query == null) ? -1 : query.getId(), (resultSet == null) ? -1 : resultSet.getResultId(), eventDuration, (session == null) ? Constants.MILLIS_I18N : session.getQueryTimingUnits(), eventCreationPoint, message));
    }
}
