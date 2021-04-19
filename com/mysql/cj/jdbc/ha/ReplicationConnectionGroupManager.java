package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.jmx.*;
import java.sql.*;
import java.util.*;

public class ReplicationConnectionGroupManager
{
    private static HashMap<String, ReplicationConnectionGroup> GROUP_MAP;
    private static ReplicationGroupManager mbean;
    private static boolean hasRegisteredJmx;
    
    public static synchronized ReplicationConnectionGroup getConnectionGroupInstance(final String groupName) {
        if (ReplicationConnectionGroupManager.GROUP_MAP.containsKey(groupName)) {
            return ReplicationConnectionGroupManager.GROUP_MAP.get(groupName);
        }
        final ReplicationConnectionGroup group = new ReplicationConnectionGroup(groupName);
        ReplicationConnectionGroupManager.GROUP_MAP.put(groupName, group);
        return group;
    }
    
    public static void registerJmx() throws SQLException {
        if (ReplicationConnectionGroupManager.hasRegisteredJmx) {
            return;
        }
        ReplicationConnectionGroupManager.mbean.registerJmx();
        ReplicationConnectionGroupManager.hasRegisteredJmx = true;
    }
    
    public static ReplicationConnectionGroup getConnectionGroup(final String groupName) {
        return ReplicationConnectionGroupManager.GROUP_MAP.get(groupName);
    }
    
    public static Collection<ReplicationConnectionGroup> getGroupsMatching(final String group) {
        if (group == null || group.equals("")) {
            final Set<ReplicationConnectionGroup> s = new HashSet<ReplicationConnectionGroup>();
            s.addAll(ReplicationConnectionGroupManager.GROUP_MAP.values());
            return s;
        }
        final Set<ReplicationConnectionGroup> s = new HashSet<ReplicationConnectionGroup>();
        final ReplicationConnectionGroup o = ReplicationConnectionGroupManager.GROUP_MAP.get(group);
        if (o != null) {
            s.add(o);
        }
        return s;
    }
    
    public static void addReplicaHost(final String group, final String hostPortPair) throws SQLException {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            cg.addReplicaHost(hostPortPair);
        }
    }
    
    @Deprecated
    public static void addSlaveHost(final String group, final String hostPortPair) throws SQLException {
        addReplicaHost(group, hostPortPair);
    }
    
    public static void removeReplicaHost(final String group, final String hostPortPair) throws SQLException {
        removeReplicaHost(group, hostPortPair, true);
    }
    
    @Deprecated
    public static void removeSlaveHost(final String group, final String hostPortPair) throws SQLException {
        removeReplicaHost(group, hostPortPair);
    }
    
    public static void removeReplicaHost(final String group, final String hostPortPair, final boolean closeGently) throws SQLException {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            cg.removeReplicaHost(hostPortPair, closeGently);
        }
    }
    
    @Deprecated
    public static void removeSlaveHost(final String group, final String hostPortPair, final boolean closeGently) throws SQLException {
        removeReplicaHost(group, hostPortPair, closeGently);
    }
    
    public static void promoteReplicaToSource(final String group, final String hostPortPair) throws SQLException {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            cg.promoteReplicaToSource(hostPortPair);
        }
    }
    
    @Deprecated
    public static void promoteSlaveToMaster(final String group, final String hostPortPair) throws SQLException {
        promoteReplicaToSource(group, hostPortPair);
    }
    
    public static long getReplicaPromotionCount(final String group) throws SQLException {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        long promoted = 0L;
        for (final ReplicationConnectionGroup cg : s) {
            final long tmp = cg.getNumberOfReplicaPromotions();
            if (tmp > promoted) {
                promoted = tmp;
            }
        }
        return promoted;
    }
    
    @Deprecated
    public static long getSlavePromotionCount(final String group) throws SQLException {
        return getReplicaPromotionCount(group);
    }
    
    public static void removeSourceHost(final String group, final String hostPortPair) throws SQLException {
        removeSourceHost(group, hostPortPair, true);
    }
    
    @Deprecated
    public static void removeMasterHost(final String group, final String hostPortPair) throws SQLException {
        removeSourceHost(group, hostPortPair);
    }
    
    public static void removeSourceHost(final String group, final String hostPortPair, final boolean closeGently) throws SQLException {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            cg.removeSourceHost(hostPortPair, closeGently);
        }
    }
    
    @Deprecated
    public static void removeMasterHost(final String group, final String hostPortPair, final boolean closeGently) throws SQLException {
        removeSourceHost(group, hostPortPair, closeGently);
    }
    
    public static String getRegisteredReplicationConnectionGroups() {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(null);
        final StringBuilder sb = new StringBuilder();
        String sep = "";
        for (final ReplicationConnectionGroup cg : s) {
            final String group = cg.getGroupName();
            sb.append(sep);
            sb.append(group);
            sep = ",";
        }
        return sb.toString();
    }
    
    public static int getNumberOfSourcePromotion(final String groupFilter) {
        int total = 0;
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(groupFilter);
        for (final ReplicationConnectionGroup cg : s) {
            total += (int)cg.getNumberOfReplicaPromotions();
        }
        return total;
    }
    
    @Deprecated
    public static int getNumberOfMasterPromotion(final String groupFilter) {
        return getNumberOfSourcePromotion(groupFilter);
    }
    
    public static int getConnectionCountWithHostAsReplica(final String groupFilter, final String hostPortPair) {
        int total = 0;
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(groupFilter);
        for (final ReplicationConnectionGroup cg : s) {
            total += cg.getConnectionCountWithHostAsReplica(hostPortPair);
        }
        return total;
    }
    
    @Deprecated
    public static int getConnectionCountWithHostAsSlave(final String groupFilter, final String hostPortPair) {
        return getConnectionCountWithHostAsReplica(groupFilter, hostPortPair);
    }
    
    public static int getConnectionCountWithHostAsSource(final String groupFilter, final String hostPortPair) {
        int total = 0;
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(groupFilter);
        for (final ReplicationConnectionGroup cg : s) {
            total += cg.getConnectionCountWithHostAsSource(hostPortPair);
        }
        return total;
    }
    
    @Deprecated
    public static int getConnectionCountWithHostAsMaster(final String groupFilter, final String hostPortPair) {
        return getConnectionCountWithHostAsSource(groupFilter, hostPortPair);
    }
    
    public static Collection<String> getReplicaHosts(final String groupFilter) {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(groupFilter);
        final Collection<String> hosts = new ArrayList<String>();
        for (final ReplicationConnectionGroup cg : s) {
            hosts.addAll(cg.getReplicaHosts());
        }
        return hosts;
    }
    
    @Deprecated
    public static Collection<String> getSlaveHosts(final String groupFilter) {
        return getReplicaHosts(groupFilter);
    }
    
    public static Collection<String> getSourceHosts(final String groupFilter) {
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(groupFilter);
        final Collection<String> hosts = new ArrayList<String>();
        for (final ReplicationConnectionGroup cg : s) {
            hosts.addAll(cg.getSourceHosts());
        }
        return hosts;
    }
    
    @Deprecated
    public static Collection<String> getMasterHosts(final String groupFilter) {
        return getSourceHosts(groupFilter);
    }
    
    public static long getTotalConnectionCount(final String group) {
        long connections = 0L;
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            connections += cg.getTotalConnectionCount();
        }
        return connections;
    }
    
    public static long getActiveConnectionCount(final String group) {
        long connections = 0L;
        final Collection<ReplicationConnectionGroup> s = getGroupsMatching(group);
        for (final ReplicationConnectionGroup cg : s) {
            connections += cg.getActiveConnectionCount();
        }
        return connections;
    }
    
    static {
        ReplicationConnectionGroupManager.GROUP_MAP = new HashMap<String, ReplicationConnectionGroup>();
        ReplicationConnectionGroupManager.mbean = new ReplicationGroupManager();
        ReplicationConnectionGroupManager.hasRegisteredJmx = false;
    }
}
