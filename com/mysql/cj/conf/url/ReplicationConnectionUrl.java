package com.mysql.cj.conf.url;

import java.util.function.*;
import com.mysql.cj.conf.*;
import java.util.*;
import java.util.stream.*;

public class ReplicationConnectionUrl extends ConnectionUrl
{
    private static final String TYPE_SOURCE = "SOURCE";
    private static final String TYPE_REPLICA = "REPLICA";
    @Deprecated
    private static final String TYPE_SOURCE_DEPRECATED = "MASTER";
    @Deprecated
    private static final String TYPE_REPLICA_DEPRECATED = "SLAVE";
    private List<HostInfo> sourceHosts;
    private List<HostInfo> replicaHosts;
    
    public ReplicationConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.sourceHosts = new ArrayList<HostInfo>();
        this.replicaHosts = new ArrayList<HostInfo>();
        this.type = Type.REPLICATION_CONNECTION;
        final LinkedList<HostInfo> undefinedHosts = new LinkedList<HostInfo>();
        for (final HostInfo hi : this.hosts) {
            final Map<String, String> hostProperties = hi.getHostProperties();
            if (hostProperties.containsKey(PropertyKey.TYPE.getKeyName())) {
                if ("SOURCE".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName())) || "MASTER".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName()))) {
                    this.sourceHosts.add(hi);
                }
                else if ("REPLICA".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName())) || "SLAVE".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName()))) {
                    this.replicaHosts.add(hi);
                }
                else {
                    undefinedHosts.add(hi);
                }
            }
            else {
                undefinedHosts.add(hi);
            }
        }
        if (!undefinedHosts.isEmpty()) {
            if (this.sourceHosts.isEmpty()) {
                this.sourceHosts.add(undefinedHosts.removeFirst());
            }
            this.replicaHosts.addAll(undefinedHosts);
        }
    }
    
    public ReplicationConnectionUrl(final List<HostInfo> sources, final List<HostInfo> replicas, final Map<String, String> properties) {
        this.sourceHosts = new ArrayList<HostInfo>();
        this.replicaHosts = new ArrayList<HostInfo>();
        this.originalConnStr = Type.REPLICATION_CONNECTION.getScheme() + "//**internally_generated**" + System.currentTimeMillis() + "**";
        this.originalDatabase = (properties.containsKey(PropertyKey.DBNAME.getKeyName()) ? properties.get(PropertyKey.DBNAME.getKeyName()) : "");
        this.type = Type.REPLICATION_CONNECTION;
        this.properties.putAll(properties);
        this.injectPerTypeProperties(this.properties);
        this.setupPropertiesTransformer();
        sources.stream().map((Function<? super Object, ?>)this::fixHostInfo).peek(this.sourceHosts::add).forEach(this.hosts::add);
        replicas.stream().map((Function<? super Object, ?>)this::fixHostInfo).peek(this.replicaHosts::add).forEach(this.hosts::add);
    }
    
    @Override
    public List<HostInfo> getHostsList(final HostsListView view) {
        switch (view) {
            case SOURCES: {
                return Collections.unmodifiableList((List<? extends HostInfo>)this.sourceHosts);
            }
            case REPLICAS: {
                return Collections.unmodifiableList((List<? extends HostInfo>)this.replicaHosts);
            }
            default: {
                return super.getHostsList(HostsListView.ALL);
            }
        }
    }
    
    public HostInfo getSourceHostOrSpawnIsolated(final String hostPortPair) {
        return super.getHostOrSpawnIsolated(hostPortPair, this.sourceHosts);
    }
    
    public List<String> getSourcesListAsHostPortPairs() {
        return this.sourceHosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getSourceHostsListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getSourceHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
    
    public HostInfo getReplicaHostOrSpawnIsolated(final String hostPortPair) {
        return super.getHostOrSpawnIsolated(hostPortPair, this.replicaHosts);
    }
    
    public List<String> getReplicasListAsHostPortPairs() {
        return this.replicaHosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getReplicaHostsListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getReplicaHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
}
