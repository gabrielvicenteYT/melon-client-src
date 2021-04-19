package com.mysql.cj.conf.url;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import com.mysql.cj.conf.*;
import java.util.*;

public class LoadBalanceDnsSrvConnectionUrl extends ConnectionUrl
{
    private static final String DEFAULT_HOST = "";
    private static final int DEFAULT_PORT = -1;
    
    public LoadBalanceDnsSrvConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.LOADBALANCE_DNS_SRV_CONNECTION;
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
        if (hostProps.containsKey(PropertyKey.loadBalanceConnectionGroup.getKeyName())) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.25", new Object[] { PropertyKey.loadBalanceConnectionGroup.getKeyName() }));
        }
    }
    
    @Override
    protected void injectPerTypeProperties(final Map<String, String> props) {
        if (props.containsKey(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName())) {
            try {
                final int autoCommitSwapThreshold = Integer.parseInt(props.get(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName()));
                if (autoCommitSwapThreshold > 0) {
                    final String queryInterceptors = props.get(PropertyKey.queryInterceptors.getKeyName());
                    final String lbi = "com.mysql.cj.jdbc.ha.LoadBalancedAutoCommitInterceptor";
                    if (StringUtils.isNullOrEmpty(queryInterceptors)) {
                        props.put(PropertyKey.queryInterceptors.getKeyName(), lbi);
                    }
                    else {
                        props.put(PropertyKey.queryInterceptors.getKeyName(), queryInterceptors + "," + lbi);
                    }
                }
            }
            catch (Throwable t) {}
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
