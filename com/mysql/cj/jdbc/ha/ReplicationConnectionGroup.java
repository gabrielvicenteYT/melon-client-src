package com.mysql.cj.jdbc.ha;

import java.util.concurrent.*;
import java.util.*;
import java.sql.*;

public class ReplicationConnectionGroup
{
    private String groupName;
    private long connections;
    private long replicasAdded;
    private long replicasRemoved;
    private long replicasPromoted;
    private long activeConnections;
    private HashMap<Long, ReplicationConnection> replicationConnections;
    private Set<String> replicaHostList;
    private boolean isInitialized;
    private Set<String> sourceHostList;
    
    ReplicationConnectionGroup(final String groupName) {
        this.connections = 0L;
        this.replicasAdded = 0L;
        this.replicasRemoved = 0L;
        this.replicasPromoted = 0L;
        this.activeConnections = 0L;
        this.replicationConnections = new HashMap<Long, ReplicationConnection>();
        this.replicaHostList = new CopyOnWriteArraySet<String>();
        this.isInitialized = false;
        this.sourceHostList = new CopyOnWriteArraySet<String>();
        this.groupName = groupName;
    }
    
    public long getConnectionCount() {
        return this.connections;
    }
    
    public long registerReplicationConnection(final ReplicationConnection conn, final List<String> localSourceList, final List<String> localReplicaList) {
        final long currentConnectionId;
        synchronized (this) {
            if (!this.isInitialized) {
                if (localSourceList != null) {
                    this.sourceHostList.addAll(localSourceList);
                }
                if (localReplicaList != null) {
                    this.replicaHostList.addAll(localReplicaList);
                }
                this.isInitialized = true;
            }
            final long connections = this.connections + 1L;
            this.connections = connections;
            currentConnectionId = connections;
            this.replicationConnections.put(currentConnectionId, conn);
        }
        ++this.activeConnections;
        return currentConnectionId;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public Collection<String> getSourceHosts() {
        return this.sourceHostList;
    }
    
    @Deprecated
    public Collection<String> getMasterHosts() {
        return this.getSourceHosts();
    }
    
    public Collection<String> getReplicaHosts() {
        return this.replicaHostList;
    }
    
    @Deprecated
    public Collection<String> getSlaveHosts() {
        return this.getReplicaHosts();
    }
    
    public void addReplicaHost(final String hostPortPair) throws SQLException {
        if (this.replicaHostList.add(hostPortPair)) {
            ++this.replicasAdded;
            for (final ReplicationConnection c : this.replicationConnections.values()) {
                c.addReplicaHost(hostPortPair);
            }
        }
    }
    
    @Deprecated
    public void addSlaveHost(final String hostPortPair) throws SQLException {
        this.addReplicaHost(hostPortPair);
    }
    
    public void handleCloseConnection(final ReplicationConnection conn) {
        this.replicationConnections.remove(conn.getConnectionGroupId());
        --this.activeConnections;
    }
    
    public void removeReplicaHost(final String hostPortPair, final boolean closeGently) throws SQLException {
        if (this.replicaHostList.remove(hostPortPair)) {
            ++this.replicasRemoved;
            for (final ReplicationConnection c : this.replicationConnections.values()) {
                c.removeReplica(hostPortPair, closeGently);
            }
        }
    }
    
    @Deprecated
    public void removeSlaveHost(final String hostPortPair, final boolean closeGently) throws SQLException {
        this.removeReplicaHost(hostPortPair, closeGently);
    }
    
    public void promoteReplicaToSource(final String hostPortPair) throws SQLException {
        if (this.replicaHostList.remove(hostPortPair) | this.sourceHostList.add(hostPortPair)) {
            ++this.replicasPromoted;
            for (final ReplicationConnection c : this.replicationConnections.values()) {
                c.promoteReplicaToSource(hostPortPair);
            }
        }
    }
    
    @Deprecated
    public void promoteSlaveToMaster(final String hostPortPair) throws SQLException {
        this.promoteReplicaToSource(hostPortPair);
    }
    
    public void removeSourceHost(final String hostPortPair) throws SQLException {
        this.removeSourceHost(hostPortPair, true);
    }
    
    @Deprecated
    public void removeMasterHost(final String hostPortPair) throws SQLException {
        this.removeSourceHost(hostPortPair);
    }
    
    public void removeSourceHost(final String hostPortPair, final boolean closeGently) throws SQLException {
        if (this.sourceHostList.remove(hostPortPair)) {
            for (final ReplicationConnection c : this.replicationConnections.values()) {
                c.removeSourceHost(hostPortPair, closeGently);
            }
        }
    }
    
    @Deprecated
    public void removeMasterHost(final String hostPortPair, final boolean closeGently) throws SQLException {
        this.removeSourceHost(hostPortPair, closeGently);
    }
    
    public int getConnectionCountWithHostAsReplica(final String hostPortPair) {
        int matched = 0;
        for (final ReplicationConnection c : this.replicationConnections.values()) {
            if (c.isHostReplica(hostPortPair)) {
                ++matched;
            }
        }
        return matched;
    }
    
    @Deprecated
    public int getConnectionCountWithHostAsSlave(final String hostPortPair) {
        return this.getConnectionCountWithHostAsReplica(hostPortPair);
    }
    
    public int getConnectionCountWithHostAsSource(final String hostPortPair) {
        int matched = 0;
        for (final ReplicationConnection c : this.replicationConnections.values()) {
            if (c.isHostSource(hostPortPair)) {
                ++matched;
            }
        }
        return matched;
    }
    
    @Deprecated
    public int getConnectionCountWithHostAsMaster(final String hostPortPair) {
        return this.getConnectionCountWithHostAsSource(hostPortPair);
    }
    
    public long getNumberOfReplicasAdded() {
        return this.replicasAdded;
    }
    
    @Deprecated
    public long getNumberOfSlavesAdded() {
        return this.getNumberOfReplicasAdded();
    }
    
    public long getNumberOfReplicasRemoved() {
        return this.replicasRemoved;
    }
    
    @Deprecated
    public long getNumberOfSlavesRemoved() {
        return this.getNumberOfReplicasRemoved();
    }
    
    public long getNumberOfReplicaPromotions() {
        return this.replicasPromoted;
    }
    
    @Deprecated
    public long getNumberOfSlavePromotions() {
        return this.getNumberOfReplicaPromotions();
    }
    
    public long getTotalConnectionCount() {
        return this.connections;
    }
    
    public long getActiveConnectionCount() {
        return this.activeConnections;
    }
    
    @Override
    public String toString() {
        return "ReplicationConnectionGroup[groupName=" + this.groupName + ",sourceHostList=" + this.sourceHostList + ",replicaHostList=" + this.replicaHostList + "]";
    }
}
