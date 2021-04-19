package com.mysql.cj.conf.url;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import com.mysql.cj.conf.*;
import java.util.*;

public class XDevApiDnsSrvConnectionUrl extends ConnectionUrl
{
    private static final String DEFAULT_HOST = "";
    private static final int DEFAULT_PORT = -1;
    
    public XDevApiDnsSrvConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.XDEVAPI_DNS_SRV_SESSION;
        final HostInfo srvHost = super.getMainHost();
        final Map<String, String> hostProps = srvHost.getHostProperties();
        if ("".equals(srvHost.getHost())) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.18"));
        }
        if (this.hosts.size() != 1) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.19"));
        }
        if (srvHost.getPort() != -1) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.22"));
        }
        if (hostProps.containsKey(PropertyKey.xdevapiDnsSrv.getKeyName()) && !BooleanPropertyDefinition.booleanFrom(PropertyKey.xdevapiDnsSrv.getKeyName(), hostProps.get(PropertyKey.xdevapiDnsSrv.getKeyName()), null)) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.23", new Object[] { PropertyKey.xdevapiDnsSrv.getKeyName() }));
        }
    }
    
    @Override
    protected void preprocessPerTypeHostProperties(final Map<String, String> hostProps) {
        if (hostProps.containsKey(PropertyKey.ADDRESS.getKeyName())) {
            final String address = hostProps.get(PropertyKey.ADDRESS.getKeyName());
            final ConnectionUrlParser.Pair<String, Integer> hostPortPair = ConnectionUrlParser.parseHostPortPair(address);
            final String host = StringUtils.safeTrim(hostPortPair.left);
            final Integer port = hostPortPair.right;
            if (!StringUtils.isNullOrEmpty(host) && !hostProps.containsKey(PropertyKey.HOST.getKeyName())) {
                hostProps.put(PropertyKey.HOST.getKeyName(), host);
            }
            if (port != -1 && !hostProps.containsKey(PropertyKey.PORT.getKeyName())) {
                hostProps.put(PropertyKey.PORT.getKeyName(), port.toString());
            }
        }
    }
    
    @Override
    public String getDefaultHost() {
        return "";
    }
    
    @Override
    public int getDefaultPort() {
        return -1;
    }
    
    @Override
    protected void fixProtocolDependencies(final Map<String, String> hostProps) {
    }
    
    @Override
    public List<HostInfo> getHostsList(final HostsListView view) {
        return this.getHostsListFromDnsSrv(this.getMainHost());
    }
}
