package com.mysql.cj.conf.url;

import com.mysql.cj.conf.*;
import java.util.*;

public class FailoverConnectionUrl extends ConnectionUrl
{
    public FailoverConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.FAILOVER_CONNECTION;
    }
}
