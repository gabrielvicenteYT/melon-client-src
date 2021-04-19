package com.mysql.cj.conf.url;

import com.mysql.cj.conf.*;
import java.util.function.*;
import com.mysql.cj.util.*;
import java.util.stream.*;
import java.util.*;

public class LoadBalanceConnectionUrl extends ConnectionUrl
{
    public LoadBalanceConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.LOADBALANCE_CONNECTION;
    }
    
    public LoadBalanceConnectionUrl(final List<HostInfo> hosts, final Map<String, String> properties) {
        this.originalConnStr = Type.LOADBALANCE_CONNECTION.getScheme() + "//**internally_generated**" + System.currentTimeMillis() + "**";
        this.originalDatabase = (properties.containsKey(PropertyKey.DBNAME.getKeyName()) ? properties.get(PropertyKey.DBNAME.getKeyName()) : "");
        this.type = Type.LOADBALANCE_CONNECTION;
        this.properties.putAll(properties);
        this.injectPerTypeProperties(this.properties);
        this.setupPropertiesTransformer();
        hosts.stream().map((Function<? super Object, ?>)this::fixHostInfo).forEach(this.hosts::add);
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
    
    public List<String> getHostInfoListAsHostPortPairs() {
        return this.hosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getHostInfoListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
}
