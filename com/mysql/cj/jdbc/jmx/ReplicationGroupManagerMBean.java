package com.mysql.cj.jdbc.jmx;

import java.sql.*;

public interface ReplicationGroupManagerMBean
{
    void addReplicaHost(final String p0, final String p1) throws SQLException;
    
    @Deprecated
    default void addSlaveHost(final String groupFilter, final String host) throws SQLException {
        this.addReplicaHost(groupFilter, host);
    }
    
    void removeReplicaHost(final String p0, final String p1) throws SQLException;
    
    @Deprecated
    default void removeSlaveHost(final String groupFilter, final String host) throws SQLException {
        this.removeReplicaHost(groupFilter, host);
    }
    
    void promoteReplicaToSource(final String p0, final String p1) throws SQLException;
    
    @Deprecated
    default void promoteSlaveToMaster(final String groupFilter, final String host) throws SQLException {
        this.promoteReplicaToSource(groupFilter, host);
    }
    
    void removeSourceHost(final String p0, final String p1) throws SQLException;
    
    @Deprecated
    default void removeMasterHost(final String groupFilter, final String host) throws SQLException {
        this.removeSourceHost(groupFilter, host);
    }
    
    String getSourceHostsList(final String p0);
    
    @Deprecated
    default String getMasterHostsList(final String group) {
        return this.getSourceHostsList(group);
    }
    
    String getReplicaHostsList(final String p0);
    
    @Deprecated
    default String getSlaveHostsList(final String group) {
        return this.getReplicaHostsList(group);
    }
    
    String getRegisteredConnectionGroups();
    
    int getActiveSourceHostCount(final String p0);
    
    @Deprecated
    default int getActiveMasterHostCount(final String group) {
        return this.getActiveSourceHostCount(group);
    }
    
    int getActiveReplicaHostCount(final String p0);
    
    @Deprecated
    default int getActiveSlaveHostCount(final String group) {
        return this.getActiveReplicaHostCount(group);
    }
    
    int getReplicaPromotionCount(final String p0);
    
    @Deprecated
    default int getSlavePromotionCount(final String group) {
        return this.getReplicaPromotionCount(group);
    }
    
    long getTotalLogicalConnectionCount(final String p0);
    
    long getActiveLogicalConnectionCount(final String p0);
}
