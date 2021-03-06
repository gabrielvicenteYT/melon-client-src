package com.mysql.cj.jdbc;

import com.mysql.cj.util.*;
import com.mysql.cj.jdbc.exceptions.*;
import com.mysql.cj.jdbc.ha.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.conf.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

public class NonRegisteringDriver implements Driver
{
    public static String getOSName() {
        return Constants.OS_NAME;
    }
    
    public static String getPlatform() {
        return Constants.OS_ARCH;
    }
    
    static int getMajorVersionInternal() {
        return StringUtils.safeIntParse("8");
    }
    
    static int getMinorVersionInternal() {
        return StringUtils.safeIntParse("0");
    }
    
    public NonRegisteringDriver() throws SQLException {
    }
    
    @Override
    public boolean acceptsURL(final String url) throws SQLException {
        try {
            return ConnectionUrl.acceptsUrl(url);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public Connection connect(final String url, final Properties info) throws SQLException {
        try {
            try {
                if (!ConnectionUrl.acceptsUrl(url)) {
                    return null;
                }
                final ConnectionUrl conStr = ConnectionUrl.getConnectionUrlInstance(url, info);
                switch (conStr.getType()) {
                    case SINGLE_CONNECTION: {
                        return ConnectionImpl.getInstance(conStr.getMainHost());
                    }
                    case FAILOVER_CONNECTION:
                    case FAILOVER_DNS_SRV_CONNECTION: {
                        return FailoverConnectionProxy.createProxyInstance(conStr);
                    }
                    case LOADBALANCE_CONNECTION:
                    case LOADBALANCE_DNS_SRV_CONNECTION: {
                        return LoadBalancedConnectionProxy.createProxyInstance(conStr);
                    }
                    case REPLICATION_CONNECTION:
                    case REPLICATION_DNS_SRV_CONNECTION: {
                        return ReplicationConnectionProxy.createProxyInstance(conStr);
                    }
                    default: {
                        return null;
                    }
                }
            }
            catch (UnsupportedConnectionStringException e) {
                return null;
            }
            catch (CJException ex) {
                throw ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("NonRegisteringDriver.17", new Object[] { ex.toString() }), ex);
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2);
        }
    }
    
    @Override
    public int getMajorVersion() {
        return getMajorVersionInternal();
    }
    
    @Override
    public int getMinorVersion() {
        return getMinorVersionInternal();
    }
    
    @Override
    public DriverPropertyInfo[] getPropertyInfo(final String url, Properties info) throws SQLException {
        try {
            String host = "";
            String port = "";
            String database = "";
            String user = "";
            String password = "";
            if (!StringUtils.isNullOrEmpty(url)) {
                final ConnectionUrl connStr = ConnectionUrl.getConnectionUrlInstance(url, info);
                if (connStr.getType() == ConnectionUrl.Type.SINGLE_CONNECTION) {
                    final HostInfo hostInfo = connStr.getMainHost();
                    info = hostInfo.exposeAsProperties();
                }
            }
            if (info != null) {
                host = info.getProperty(PropertyKey.HOST.getKeyName());
                port = info.getProperty(PropertyKey.PORT.getKeyName());
                database = info.getProperty(PropertyKey.DBNAME.getKeyName());
                user = info.getProperty(PropertyKey.USER.getKeyName());
                password = info.getProperty(PropertyKey.PASSWORD.getKeyName());
            }
            final DriverPropertyInfo hostProp = new DriverPropertyInfo(PropertyKey.HOST.getKeyName(), host);
            hostProp.required = true;
            hostProp.description = Messages.getString("NonRegisteringDriver.3");
            final DriverPropertyInfo portProp = new DriverPropertyInfo(PropertyKey.PORT.getKeyName(), port);
            portProp.required = false;
            portProp.description = Messages.getString("NonRegisteringDriver.7");
            final DriverPropertyInfo dbProp = new DriverPropertyInfo(PropertyKey.DBNAME.getKeyName(), database);
            dbProp.required = false;
            dbProp.description = Messages.getString("NonRegisteringDriver.10");
            final DriverPropertyInfo userProp = new DriverPropertyInfo(PropertyKey.USER.getKeyName(), user);
            userProp.required = true;
            userProp.description = Messages.getString("NonRegisteringDriver.13");
            final DriverPropertyInfo passwordProp = new DriverPropertyInfo(PropertyKey.PASSWORD.getKeyName(), password);
            passwordProp.required = true;
            passwordProp.description = Messages.getString("NonRegisteringDriver.16");
            final JdbcPropertySet propSet = new JdbcPropertySetImpl();
            propSet.initializeProperties(info);
            final List<DriverPropertyInfo> driverPropInfo = propSet.exposeAsDriverPropertyInfo();
            final DriverPropertyInfo[] dpi = new DriverPropertyInfo[5 + driverPropInfo.size()];
            dpi[0] = hostProp;
            dpi[1] = portProp;
            dpi[2] = dbProp;
            dpi[3] = userProp;
            dpi[4] = passwordProp;
            System.arraycopy(driverPropInfo.toArray(new DriverPropertyInfo[0]), 0, dpi, 5, driverPropInfo.size());
            return dpi;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public boolean jdbcCompliant() {
        return false;
    }
    
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
    
    static {
        try {
            Class.forName(AbandonedConnectionCleanupThread.class.getName());
        }
        catch (ClassNotFoundException ex) {}
    }
}
