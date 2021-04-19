package com.mysql.cj.protocol;

import com.mysql.cj.conf.*;
import java.net.*;

public class SocksProxySocketFactory extends StandardSocketFactory
{
    @Override
    protected Socket createSocket(final PropertySet props) {
        final String socksProxyHost = props.getStringProperty(PropertyKey.socksProxyHost).getValue();
        final int socksProxyPort = props.getIntegerProperty(PropertyKey.socksProxyPort).getValue();
        return new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(socksProxyHost, socksProxyPort)));
    }
}
