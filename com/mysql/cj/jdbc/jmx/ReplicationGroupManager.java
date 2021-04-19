package com.mysql.cj.jdbc.jmx;

import java.lang.management.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import javax.management.*;
import java.sql.*;
import java.util.*;
import com.mysql.cj.jdbc.ha.*;

public class ReplicationGroupManager implements ReplicationGroupManagerMBean
{
    private boolean isJmxRegistered;
    
    public ReplicationGroupManager() {
        this.isJmxRegistered = false;
    }
    
    public synchronized void registerJmx() throws SQLException {
        if (this.isJmxRegistered) {
            return;
        }
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName name = new ObjectName("com.mysql.cj.jdbc.jmx:type=ReplicationGroupManager");
            mbs.registerMBean(this, name);
            this.isJmxRegistered = true;
        }
        catch (Exception e) {
            throw SQLError.createSQLException(Messages.getString("ReplicationGroupManager.0"), null, e, null);
        }
    }
    
    @Override
    public void addReplicaHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.addReplicaHost(groupFilter, host);
    }
    
    @Override
    public void removeReplicaHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.removeReplicaHost(groupFilter, host);
    }
    
    @Override
    public void promoteReplicaToSource(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.promoteReplicaToSource(groupFilter, host);
    }
    
    @Override
    public void removeSourceHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.removeSourceHost(groupFilter, host);
    }
    
    @Override
    public String getSourceHostsList(final String group) {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final String host : ReplicationConnectionGroupManager.getSourceHosts(group)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(host);
        }
        return sb.toString();
    }
    
    @Override
    public String getReplicaHostsList(final String group) {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final String host : ReplicationConnectionGroupManager.getReplicaHosts(group)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(host);
        }
        return sb.toString();
    }
    
    @Override
    public String getRegisteredConnectionGroups() {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final ReplicationConnectionGroup group : ReplicationConnectionGroupManager.getGroupsMatching(null)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(group.getGroupName());
        }
        return sb.toString();
    }
    
    @Override
    public int getActiveSourceHostCount(final String group) {
        return ReplicationConnectionGroupManager.getSourceHosts(group).size();
    }
    
    @Override
    public int getActiveReplicaHostCount(final String group) {
        return ReplicationConnectionGroupManager.getReplicaHosts(group).size();
    }
    
    @Override
    public int getReplicaPromotionCount(final String group) {
        return ReplicationConnectionGroupManager.getNumberOfSourcePromotion(group);
    }
    
    @Override
    public long getTotalLogicalConnectionCount(final String group) {
        return ReplicationConnectionGroupManager.getTotalConnectionCount(group);
    }
    
    @Override
    public long getActiveLogicalConnectionCount(final String group) {
        return ReplicationConnectionGroupManager.getActiveConnectionCount(group);
    }
}
