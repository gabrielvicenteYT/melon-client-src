package com.mysql.cj.conf.url;

import com.mysql.cj.conf.*;
import java.util.*;

public class SingleConnectionUrl extends ConnectionUrl
{
    public SingleConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.SINGLE_CONNECTION;
    }
}
