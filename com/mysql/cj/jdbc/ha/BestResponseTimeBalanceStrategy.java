package com.mysql.cj.jdbc.ha;

import java.lang.reflect.*;
import java.util.*;
import com.mysql.cj.jdbc.*;
import java.sql.*;

public class BestResponseTimeBalanceStrategy implements BalanceStrategy
{
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        Map<String, Long> blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
        SQLException ex = null;
        int attempts = 0;
        while (attempts < numRetries) {
            long minResponseTime = Long.MAX_VALUE;
            int bestHostIndex = 0;
            if (blockList.size() == configuredHosts.size()) {
                blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
            }
            for (int i = 0; i < responseTimes.length; ++i) {
                final long candidateResponseTime = responseTimes[i];
                if (candidateResponseTime < minResponseTime && !blockList.containsKey(configuredHosts.get(i))) {
                    if (candidateResponseTime == 0L) {
                        bestHostIndex = i;
                        break;
                    }
                    bestHostIndex = i;
                    minResponseTime = candidateResponseTime;
                }
            }
            final String bestHost = configuredHosts.get(bestHostIndex);
            ConnectionImpl conn = liveConnections.get(bestHost);
            if (conn == null) {
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(bestHost);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlocklist(bestHost);
                        blockList.put(bestHost, null);
                        if (blockList.size() != configuredHosts.size()) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        blockList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlocklist();
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
}
