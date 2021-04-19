package com.mysql.cj.exceptions;

import com.mysql.cj.log.*;
import com.mysql.cj.util.*;
import java.util.stream.*;
import java.util.*;

public class ExceptionInterceptorChain implements ExceptionInterceptor
{
    List<ExceptionInterceptor> interceptors;
    
    public ExceptionInterceptorChain(final String interceptorClasses, final Properties props, final Log log) {
        this.interceptors = Util.loadClasses(interceptorClasses, "Connection.BadExceptionInterceptor", this).stream().map(o -> o.init(props, log)).collect((Collector<? super Object, ?, List<ExceptionInterceptor>>)Collectors.toList());
    }
    
    public void addRingZero(final ExceptionInterceptor interceptor) {
        this.interceptors.add(0, interceptor);
    }
    
    @Override
    public Exception interceptException(Exception sqlEx) {
        if (this.interceptors != null) {
            final Iterator<ExceptionInterceptor> iter = this.interceptors.iterator();
            while (iter.hasNext()) {
                sqlEx = iter.next().interceptException(sqlEx);
            }
        }
        return sqlEx;
    }
    
    @Override
    public void destroy() {
        if (this.interceptors != null) {
            final Iterator<ExceptionInterceptor> iter = this.interceptors.iterator();
            while (iter.hasNext()) {
                iter.next().destroy();
            }
        }
    }
    
    @Override
    public ExceptionInterceptor init(final Properties properties, final Log log) {
        if (this.interceptors != null) {
            final Iterator<ExceptionInterceptor> iter = this.interceptors.iterator();
            while (iter.hasNext()) {
                iter.next().init(properties, log);
            }
        }
        return this;
    }
    
    public List<ExceptionInterceptor> getInterceptors() {
        return this.interceptors;
    }
}
