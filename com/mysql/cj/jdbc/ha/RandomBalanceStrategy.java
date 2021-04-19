package com.mysql.cj.jdbc.ha;

import java.lang.reflect.*;
import com.mysql.cj.jdbc.*;
import com.mysql.cj.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import java.sql.*;

public class RandomBalanceStrategy implements BalanceStrategy
{
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        final int numHosts = configuredHosts.size();
        SQLException ex = null;
        final List<String> allowList = new ArrayList<String>(numHosts);
        allowList.addAll(configuredHosts);
        Map<String, Long> blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
        allowList.removeAll(blockList.keySet());
        Map<String, Integer> allowListMap = this.getArrayIndexMap(allowList);
        int attempts = 0;
        while (attempts < numRetries) {
            final int random = (int)Math.floor(Math.random() * allowList.size());
            if (allowList.size() == 0) {
                throw SQLError.createSQLException(Messages.getString("RandomBalanceStrategy.0"), null);
            }
            final String hostPortSpec = allowList.get(random);
            ConnectionImpl conn = liveConnections.get(hostPortSpec);
            if (conn == null) {
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(hostPortSpec);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        final Integer allowListIndex = allowListMap.get(hostPortSpec);
                        if (allowListIndex != null) {
                            allowList.remove((int)allowListIndex);
                            allowListMap = this.getArrayIndexMap(allowList);
                        }
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlocklist(hostPortSpec);
                        if (allowList.size() != 0) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        allowListMap = new HashMap<String, Integer>(numHosts);
                        allowList.addAll(configuredHosts);
                        blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
                        allowList.removeAll(blockList.keySet());
                        allowListMap = this.getArrayIndexMap(allowList);
                        continue;
                    }
                    throw sqlEx;
                }
            }
            return conn;
        }
        if (ex != null) {
            throw ex;
        }
        return null;
    }
    
    private Map<String, Integer> getArrayIndexMap(final List<String> l) {
        final Map<String, Integer> m = new HashMap<String, Integer>(l.size());
        for (int i = 0; i < l.size(); ++i) {
            m.put(l.get(i), i);
        }
        return m;
    }
}
