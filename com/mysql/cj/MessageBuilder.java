package com.mysql.cj;

import com.mysql.cj.protocol.*;
import java.util.*;

public interface MessageBuilder<M extends Message>
{
    M buildSqlStatement(final String p0);
    
    M buildSqlStatement(final String p0, final List<Object> p1);
    
    M buildClose();
}
