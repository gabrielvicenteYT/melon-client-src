package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.*;
import java.sql.*;

public interface ReplicationConnection extends JdbcConnection
{
    long getConnectionGroupId();
    
    JdbcConnection getCurrentConnection();
    
    JdbcConnection getSourceConnection();
    
    @Deprecated
    default JdbcConnection getMasterConnection() {
        return this.getSourceConnection();
    }
    
    void promoteReplicaToSource(final String p0) throws SQLException;
    
    @Deprecated
    default void promoteSlaveToMaster(final String host) throws SQLException {
        this.promoteReplicaToSource(host);
    }
    
    void removeSourceHost(final String p0) throws SQLException;
    
    @Deprecated
    default void removeMasterHost(final String host) throws SQLException {
        this.removeSourceHost(host);
    }
    
    void removeSourceHost(final String p0, final boolean p1) throws SQLException;
    
    @Deprecated
    default void removeMasterHost(final String host, final boolean waitUntilNotInUse) throws SQLException {
        this.removeSourceHost(host, waitUntilNotInUse);
    }
    
    boolean isHostSource(final String p0);
    
    @Deprecated
    default boolean isHostMaster(final String host) {
        return this.isHostSource(host);
    }
    
    JdbcConnection getReplicaConnection();
    
    @Deprecated
    default JdbcConnection getSlavesConnection() {
        return this.getReplicaConnection();
    }
    
    void addReplicaHost(final String p0) throws SQLException;
    
    @Deprecated
    default void addSlaveHost(final String host) throws SQLException {
        this.addReplicaHost(host);
    }
    
    void removeReplica(final String p0) throws SQLException;
    
    @Deprecated
    default void removeSlave(final String host) throws SQLException {
        this.removeReplica(host);
    }
    
    void removeReplica(final String p0, final boolean p1) throws SQLException;
    
    @Deprecated
    default void removeSlave(final String host, final boolean closeGently) throws SQLException {
        this.removeReplica(host, closeGently);
    }
    
    boolean isHostReplica(final String p0);
    
    @Deprecated
    default boolean isHostSlave(final String host) {
        return this.isHostReplica(host);
    }
}
