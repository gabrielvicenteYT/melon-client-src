package com.mysql.cj.exceptions;

import java.util.*;
import com.mysql.cj.log.*;

public interface ExceptionInterceptor
{
    ExceptionInterceptor init(final Properties p0, final Log p1);
    
    void destroy();
    
    Exception interceptException(final Exception p0);
}
