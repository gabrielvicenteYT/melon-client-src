package com.mysql.cj.jdbc.ha;

import com.mysql.cj.util.*;
import java.lang.reflect.*;
import java.util.*;
import com.mysql.cj.jdbc.*;
import java.sql.*;

public class ServerAffinityStrategy extends RandomBalanceStrategy
{
    public String[] affinityOrderedServers;
    
    public ServerAffinityStrategy(final String affinityOrdervers) {
        this.affinityOrderedServers = null;
        if (!StringUtils.isNullOrEmpty(affinityOrdervers)) {
            this.affinityOrderedServers = affinityOrdervers.split(",");
        }
    }
    
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        if (this.affinityOrderedServers == null) {
            return super.pickConnection(proxy, configuredHosts, liveConnections, responseTimes, numRetries);
        }
        final Map<String, Long> blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
        for (final String host : this.affinityOrderedServers) {
            if (configuredHosts.contains(host) && !blockList.containsKey(host)) {
                ConnectionImpl conn = liveConnections.get(host);
                if (conn != null) {
                    return conn;
                }
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(host);
                    return conn;
                }
                catch (SQLException sqlEx) {
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlocklist(host);
                    }
                }
            }
        }
        return super.pickConnection(proxy, configuredHosts, liveConnections, responseTimes, numRetries);
    }
}
