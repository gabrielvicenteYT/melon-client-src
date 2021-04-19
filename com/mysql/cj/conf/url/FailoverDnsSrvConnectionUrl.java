package com.mysql.cj.conf.url;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.conf.*;
import java.util.*;

public class FailoverDnsSrvConnectionUrl extends ConnectionUrl
{
    private static final String DEFAULT_HOST = "";
    private static final int DEFAULT_PORT = -1;
    
    public FailoverDnsSrvConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.FAILOVER_DNS_SRV_CONNECTION;
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
        if (hostProps.containsKey(PropertyKey.dnsSrv.getKeyName()) && !BooleanPropertyDefinition.booleanFrom(PropertyKey.dnsSrv.getKeyName(), hostProps.get(PropertyKey.dnsSrv.getKeyName()), null)) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.23", new Object[] { PropertyKey.dnsSrv.getKeyName() }));
        }
        if (hostProps.containsKey(PropertyKey.PROTOCOL.getKeyName()) && hostProps.get(PropertyKey.PROTOCOL.getKeyName()).equalsIgnoreCase("PIPE")) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.24"));
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
    public List<HostInfo> getHostsList(final HostsListView view) {
        return this.getHostsListFromDnsSrv(this.getMainHost());
    }
}
