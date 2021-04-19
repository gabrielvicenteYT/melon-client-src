package com.mysql.cj.conf.url;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import com.mysql.cj.conf.*;

public class ReplicationDnsSrvConnectionUrl extends ConnectionUrl
{
    private static final String DEFAULT_HOST = "";
    private static final int DEFAULT_PORT = -1;
    private static final String TYPE_SOURCE = "SOURCE";
    private static final String TYPE_REPLICA = "REPLICA";
    @Deprecated
    private static final String TYPE_SOURCE_DEPRECATED = "MASTER";
    @Deprecated
    private static final String TYPE_REPLICA_DEPRECATED = "SLAVE";
    private List<HostInfo> sourceHosts;
    private List<HostInfo> replicaHosts;
    
    public ReplicationDnsSrvConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.sourceHosts = new ArrayList<HostInfo>();
        this.replicaHosts = new ArrayList<HostInfo>();
        this.type = Type.REPLICATION_DNS_SRV_CONNECTION;
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
        final HostInfo srvHostSource = this.sourceHosts.isEmpty() ? null : this.sourceHosts.get(0);
        final Map<String, String> hostPropsSource = (srvHostSource == null) ? Collections.emptyMap() : srvHostSource.getHostProperties();
        final HostInfo srvHostReplica = this.replicaHosts.isEmpty() ? null : this.replicaHosts.get(0);
        final Map<String, String> hostPropsReplica = (srvHostReplica == null) ? Collections.emptyMap() : srvHostReplica.getHostProperties();
        if (srvHostSource == null || srvHostReplica == null || "".equals(srvHostSource.getHost()) || "".equals(srvHostReplica.getHost())) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.20"));
        }
        if (this.sourceHosts.size() != 1 || this.replicaHosts.size() != 1) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.21"));
        }
        if (srvHostSource.getPort() != -1 || srvHostReplica.getPort() != -1) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.22"));
        }
        if ((hostPropsSource.containsKey(PropertyKey.dnsSrv.getKeyName()) || hostPropsReplica.containsKey(PropertyKey.dnsSrv.getKeyName())) && (!BooleanPropertyDefinition.booleanFrom(PropertyKey.dnsSrv.getKeyName(), hostPropsSource.get(PropertyKey.dnsSrv.getKeyName()), null) || !BooleanPropertyDefinition.booleanFrom(PropertyKey.dnsSrv.getKeyName(), hostPropsReplica.get(PropertyKey.dnsSrv.getKeyName()), null))) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.23", new Object[] { PropertyKey.dnsSrv.getKeyName() }));
        }
        if ((hostPropsSource.containsKey(PropertyKey.PROTOCOL.getKeyName()) && hostPropsSource.get(PropertyKey.PROTOCOL.getKeyName()).equalsIgnoreCase("PIPE")) || (hostPropsReplica.containsKey(PropertyKey.PROTOCOL.getKeyName()) && hostPropsReplica.get(PropertyKey.PROTOCOL.getKeyName()).equalsIgnoreCase("PIPE"))) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.24"));
        }
        if (hostPropsSource.containsKey(PropertyKey.replicationConnectionGroup.getKeyName()) || hostPropsReplica.containsKey(PropertyKey.replicationConnectionGroup.getKeyName())) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.25", new Object[] { PropertyKey.replicationConnectionGroup.getKeyName() }));
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
        switch (view) {
            case SOURCES: {
                return this.getHostsListFromDnsSrv(this.sourceHosts.get(0));
            }
            case REPLICAS: {
                return this.getHostsListFromDnsSrv(this.replicaHosts.get(0));
            }
            default: {
                return super.getHostsList(HostsListView.ALL);
            }
        }
    }
}
