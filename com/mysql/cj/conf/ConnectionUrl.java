package com.mysql.cj.conf;

import com.mysql.cj.*;
import java.util.stream.*;
import java.io.*;
import java.util.function.*;
import java.util.*;
import javax.naming.*;
import java.util.concurrent.locks.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;

public abstract class ConnectionUrl implements DatabaseUrlContainer
{
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 3306;
    private static final LRUCache<String, ConnectionUrl> connectionUrlCache;
    private static final ReadWriteLock rwLock;
    protected Type type;
    protected String originalConnStr;
    protected String originalDatabase;
    protected List<HostInfo> hosts;
    protected Map<String, String> properties;
    ConnectionPropertiesTransform propertiesTransformer;
    
    public static ConnectionUrl getConnectionUrlInstance(final String connString, final Properties info) {
        if (connString == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
        }
        final String connStringCacheKey = buildConnectionStringCacheKey(connString, info);
        ConnectionUrl.rwLock.readLock().lock();
        ConnectionUrl connectionUrl = ConnectionUrl.connectionUrlCache.get(connStringCacheKey);
        if (connectionUrl == null) {
            ConnectionUrl.rwLock.readLock().unlock();
            ConnectionUrl.rwLock.writeLock().lock();
            try {
                connectionUrl = ConnectionUrl.connectionUrlCache.get(connStringCacheKey);
                if (connectionUrl == null) {
                    final ConnectionUrlParser connStrParser = ConnectionUrlParser.parseConnectionString(connString);
                    connectionUrl = Type.getConnectionUrlInstance(connStrParser, info);
                    ConnectionUrl.connectionUrlCache.put(connStringCacheKey, connectionUrl);
                }
                ConnectionUrl.rwLock.readLock().lock();
            }
            finally {
                ConnectionUrl.rwLock.writeLock().unlock();
            }
        }
        ConnectionUrl.rwLock.readLock().unlock();
        return connectionUrl;
    }
    
    private static String buildConnectionStringCacheKey(final String connString, final Properties info) {
        final StringBuilder sbKey = new StringBuilder(connString);
        sbKey.append("§");
        sbKey.append((info == null) ? null : info.stringPropertyNames().stream().map(k -> k + "=" + info.getProperty(k)).collect((Collector<? super Object, ?, String>)Collectors.joining(", ", "{", "}")));
        return sbKey.toString();
    }
    
    public static boolean acceptsUrl(final String connString) {
        return ConnectionUrlParser.isConnectionStringSupported(connString);
    }
    
    protected ConnectionUrl() {
        this.hosts = new ArrayList<HostInfo>();
        this.properties = new HashMap<String, String>();
    }
    
    public ConnectionUrl(final String origUrl) {
        this.hosts = new ArrayList<HostInfo>();
        this.properties = new HashMap<String, String>();
        this.originalConnStr = origUrl;
    }
    
    protected ConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        this.hosts = new ArrayList<HostInfo>();
        this.properties = new HashMap<String, String>();
        this.originalConnStr = connStrParser.getDatabaseUrl();
        this.originalDatabase = ((connStrParser.getPath() == null) ? "" : connStrParser.getPath());
        this.collectProperties(connStrParser, info);
        this.collectHostsInfo(connStrParser);
    }
    
    protected void collectProperties(final ConnectionUrlParser connStrParser, final Properties info) {
        final String s;
        connStrParser.getProperties().entrySet().stream().forEach(e -> s = this.properties.put(PropertyKey.normalizeCase(e.getKey()), (String)e.getValue()));
        if (info != null) {
            final String s2;
            info.stringPropertyNames().stream().forEach(k -> s2 = this.properties.put(PropertyKey.normalizeCase(k), info.getProperty(k)));
        }
        this.setupPropertiesTransformer();
        this.expandPropertiesFromConfigFiles(this.properties);
        this.injectPerTypeProperties(this.properties);
    }
    
    protected void setupPropertiesTransformer() {
        final String propertiesTransformClassName = this.properties.get(PropertyKey.propertiesTransform.getKeyName());
        if (!StringUtils.isNullOrEmpty(propertiesTransformClassName)) {
            try {
                this.propertiesTransformer = (ConnectionPropertiesTransform)Class.forName(propertiesTransformClassName).newInstance();
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException | CJException ex2) {
                final Exception ex;
                final Exception e = ex;
                throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.9", new Object[] { propertiesTransformClassName, e.toString() }), e);
            }
        }
    }
    
    protected void expandPropertiesFromConfigFiles(final Map<String, String> props) {
        final String configFiles = props.get(PropertyKey.useConfigs.getKeyName());
        if (!StringUtils.isNullOrEmpty(configFiles)) {
            final Properties configProps = getPropertiesFromConfigFiles(configFiles);
            final String s;
            configProps.stringPropertyNames().stream().map((Function<? super Object, ?>)PropertyKey::normalizeCase).filter(k -> !props.containsKey(k)).forEach(k -> s = props.put(k, configProps.getProperty(k)));
        }
    }
    
    public static Properties getPropertiesFromConfigFiles(final String configFiles) {
        final Properties configProps = new Properties();
        for (final String configFile : configFiles.split(",")) {
            try (final InputStream configAsStream = ConnectionUrl.class.getResourceAsStream("/com/mysql/cj/configurations/" + configFile + ".properties")) {
                if (configAsStream == null) {
                    throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.10", new Object[] { configFile }));
                }
                configProps.load(configAsStream);
            }
            catch (IOException e) {
                throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("ConnectionString.11", new Object[] { configFile }), e);
            }
        }
        return configProps;
    }
    
    protected void injectPerTypeProperties(final Map<String, String> props) {
    }
    
    protected void replaceLegacyPropertyValues(final Map<String, String> props) {
        final String zeroDateTimeBehavior = props.get(PropertyKey.zeroDateTimeBehavior.getKeyName());
        if (zeroDateTimeBehavior != null && zeroDateTimeBehavior.equalsIgnoreCase("convertToNull")) {
            props.put(PropertyKey.zeroDateTimeBehavior.getKeyName(), "CONVERT_TO_NULL");
        }
    }
    
    protected void collectHostsInfo(final ConnectionUrlParser connStrParser) {
        connStrParser.getHosts().stream().map((Function<? super Object, ?>)this::fixHostInfo).forEach(this.hosts::add);
    }
    
    protected HostInfo fixHostInfo(final HostInfo hi) {
        final Map<String, String> hostProps = new HashMap<String, String>();
        hostProps.putAll(this.properties);
        final String s;
        hi.getHostProperties().entrySet().stream().forEach(e -> s = hostProps.put(PropertyKey.normalizeCase(e.getKey()), (String)e.getValue()));
        if (!hostProps.containsKey(PropertyKey.DBNAME.getKeyName())) {
            hostProps.put(PropertyKey.DBNAME.getKeyName(), this.getDatabase());
        }
        this.preprocessPerTypeHostProperties(hostProps);
        String host = hostProps.remove(PropertyKey.HOST.getKeyName());
        if (!StringUtils.isNullOrEmpty(hi.getHost())) {
            host = hi.getHost();
        }
        else if (StringUtils.isNullOrEmpty(host)) {
            host = this.getDefaultHost();
        }
        final String portAsString = hostProps.remove(PropertyKey.PORT.getKeyName());
        int port = hi.getPort();
        if (port == -1 && !StringUtils.isNullOrEmpty(portAsString)) {
            try {
                port = Integer.valueOf(portAsString);
            }
            catch (NumberFormatException e2) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.7", new Object[] { hostProps.get(PropertyKey.PORT.getKeyName()) }), e2);
            }
        }
        if (port == -1) {
            port = this.getDefaultPort();
        }
        String user = hostProps.remove(PropertyKey.USER.getKeyName());
        if (!StringUtils.isNullOrEmpty(hi.getUser())) {
            user = hi.getUser();
        }
        else if (StringUtils.isNullOrEmpty(user)) {
            user = this.getDefaultUser();
        }
        boolean isPasswordless = hi.isPasswordless();
        String password = hostProps.remove(PropertyKey.PASSWORD.getKeyName());
        if (!isPasswordless) {
            password = hi.getPassword();
        }
        else if (password == null) {
            password = this.getDefaultPassword();
            isPasswordless = true;
        }
        else {
            isPasswordless = false;
        }
        this.expandPropertiesFromConfigFiles(hostProps);
        this.fixProtocolDependencies(hostProps);
        this.replaceLegacyPropertyValues(hostProps);
        return this.buildHostInfo(host, port, user, password, isPasswordless, hostProps);
    }
    
    protected void preprocessPerTypeHostProperties(final Map<String, String> hostProps) {
    }
    
    public String getDefaultHost() {
        return "localhost";
    }
    
    public int getDefaultPort() {
        return 3306;
    }
    
    public String getDefaultUser() {
        final String user = this.properties.get(PropertyKey.USER.getKeyName());
        return StringUtils.isNullOrEmpty(user) ? "" : user;
    }
    
    public String getDefaultPassword() {
        final String password = this.properties.get(PropertyKey.PASSWORD.getKeyName());
        return StringUtils.isNullOrEmpty(password) ? "" : password;
    }
    
    protected void fixProtocolDependencies(final Map<String, String> hostProps) {
        final String protocol = hostProps.get(PropertyKey.PROTOCOL.getKeyName());
        if (!StringUtils.isNullOrEmpty(protocol) && protocol.equalsIgnoreCase("PIPE") && !hostProps.containsKey(PropertyKey.socketFactory.getKeyName())) {
            hostProps.put(PropertyKey.socketFactory.getKeyName(), "com.mysql.cj.protocol.NamedPipeSocketFactory");
        }
    }
    
    public Type getType() {
        return this.type;
    }
    
    @Override
    public String getDatabaseUrl() {
        return this.originalConnStr;
    }
    
    public String getDatabase() {
        return this.properties.containsKey(PropertyKey.DBNAME.getKeyName()) ? this.properties.get(PropertyKey.DBNAME.getKeyName()) : this.originalDatabase;
    }
    
    public int hostsCount() {
        return this.hosts.size();
    }
    
    public HostInfo getMainHost() {
        return this.hosts.isEmpty() ? null : this.hosts.get(0);
    }
    
    public List<HostInfo> getHostsList() {
        return this.getHostsList(HostsListView.ALL);
    }
    
    public List<HostInfo> getHostsList(final HostsListView view) {
        return Collections.unmodifiableList((List<? extends HostInfo>)this.hosts);
    }
    
    public HostInfo getHostOrSpawnIsolated(final String hostPortPair) {
        return this.getHostOrSpawnIsolated(hostPortPair, this.hosts);
    }
    
    public HostInfo getHostOrSpawnIsolated(final String hostPortPair, final List<HostInfo> hostsList) {
        for (final HostInfo hi : hostsList) {
            if (hostPortPair.equals(hi.getHostPortPair())) {
                return hi;
            }
        }
        final ConnectionUrlParser.Pair<String, Integer> hostAndPort = ConnectionUrlParser.parseHostPortPair(hostPortPair);
        final String host = hostAndPort.left;
        final Integer port = hostAndPort.right;
        final String user = this.getDefaultUser();
        final String password = this.getDefaultPassword();
        return this.buildHostInfo(host, port, user, password, true, this.properties);
    }
    
    protected HostInfo buildHostInfo(String host, int port, String user, String password, final boolean isDefaultPwd, Map<String, String> hostProps) {
        if (this.propertiesTransformer != null) {
            final Properties props = new Properties();
            props.putAll(hostProps);
            props.setProperty(PropertyKey.HOST.getKeyName(), host);
            props.setProperty(PropertyKey.PORT.getKeyName(), String.valueOf(port));
            props.setProperty(PropertyKey.USER.getKeyName(), user);
            props.setProperty(PropertyKey.PASSWORD.getKeyName(), password);
            final Properties transformedProps = this.propertiesTransformer.transformProperties(props);
            host = transformedProps.getProperty(PropertyKey.PORT.getKeyName());
            try {
                port = Integer.parseInt(transformedProps.getProperty(PropertyKey.PORT.getKeyName()));
            }
            catch (NumberFormatException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.8", new Object[] { PropertyKey.PORT.getKeyName(), transformedProps.getProperty(PropertyKey.PORT.getKeyName()) }), e);
            }
            user = transformedProps.getProperty(PropertyKey.USER.getKeyName());
            password = transformedProps.getProperty(PropertyKey.PASSWORD.getKeyName());
            final Map<String, String> transformedHostProps = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
            final String s;
            transformedProps.stringPropertyNames().stream().forEach(k -> s = transformedHostProps.put(k, transformedProps.getProperty(k)));
            transformedHostProps.remove(PropertyKey.HOST.getKeyName());
            transformedHostProps.remove(PropertyKey.PORT.getKeyName());
            transformedHostProps.remove(PropertyKey.USER.getKeyName());
            transformedHostProps.remove(PropertyKey.PASSWORD.getKeyName());
            hostProps = transformedHostProps;
        }
        return new HostInfo(this, host, port, user, password, isDefaultPwd, hostProps);
    }
    
    public Map<String, String> getOriginalProperties() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.properties);
    }
    
    public Properties getConnectionArgumentsAsProperties() {
        final Properties props = new Properties();
        if (this.properties != null) {
            props.putAll(this.properties);
        }
        return (this.propertiesTransformer != null) ? this.propertiesTransformer.transformProperties(props) : props;
    }
    
    public List<HostInfo> getHostsListFromDnsSrv(final HostInfo srvHost) {
        final String srvServiceName = srvHost.getHost();
        List<DnsSrv.SrvRecord> srvRecords = null;
        try {
            srvRecords = DnsSrv.lookupSrvRecords(srvServiceName);
        }
        catch (NamingException e) {
            throw ExceptionFactory.createException(Messages.getString("ConnectionString.26", new Object[] { srvServiceName }), e);
        }
        if (srvRecords == null || srvRecords.size() == 0) {
            throw ExceptionFactory.createException(Messages.getString("ConnectionString.26", new Object[] { srvServiceName }));
        }
        return Collections.unmodifiableList((List<? extends HostInfo>)this.srvRecordsToHostsList(srvRecords, srvHost));
    }
    
    private List<HostInfo> srvRecordsToHostsList(final List<DnsSrv.SrvRecord> srvRecords, final HostInfo baseHostInfo) {
        return srvRecords.stream().map(s -> this.buildHostInfo(s.getTarget(), s.getPort(), baseHostInfo.getUser(), baseHostInfo.getPassword(), baseHostInfo.isPasswordless(), baseHostInfo.getHostProperties())).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
    
    @Override
    public String toString() {
        final StringBuilder asStr = new StringBuilder(super.toString());
        asStr.append(String.format(" :: {type: \"%s\", hosts: %s, database: \"%s\", properties: %s, propertiesTransformer: %s}", this.type, this.hosts, this.originalDatabase, this.properties, this.propertiesTransformer));
        return asStr.toString();
    }
    
    static {
        connectionUrlCache = new LRUCache<String, ConnectionUrl>(100);
        rwLock = new ReentrantReadWriteLock();
    }
    
    public enum HostsCardinality
    {
        SINGLE {
            @Override
            public boolean assertSize(final int n) {
                return n == 1;
            }
        }, 
        MULTIPLE {
            @Override
            public boolean assertSize(final int n) {
                return n > 1;
            }
        }, 
        ONE_OR_MORE {
            @Override
            public boolean assertSize(final int n) {
                return n >= 1;
            }
        };
        
        public abstract boolean assertSize(final int p0);
    }
    
    public enum Type
    {
        FAILOVER_DNS_SRV_CONNECTION("jdbc:mysql+srv:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.FailoverDnsSrvConnectionUrl"), 
        LOADBALANCE_DNS_SRV_CONNECTION("jdbc:mysql+srv:loadbalance:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.LoadBalanceDnsSrvConnectionUrl"), 
        REPLICATION_DNS_SRV_CONNECTION("jdbc:mysql+srv:replication:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.ReplicationDnsSrvConnectionUrl"), 
        XDEVAPI_DNS_SRV_SESSION("mysqlx+srv:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.XDevApiDnsSrvConnectionUrl"), 
        SINGLE_CONNECTION("jdbc:mysql:", HostsCardinality.SINGLE, "com.mysql.cj.conf.url.SingleConnectionUrl", PropertyKey.dnsSrv, Type.FAILOVER_DNS_SRV_CONNECTION), 
        FAILOVER_CONNECTION("jdbc:mysql:", HostsCardinality.MULTIPLE, "com.mysql.cj.conf.url.FailoverConnectionUrl", PropertyKey.dnsSrv, Type.FAILOVER_DNS_SRV_CONNECTION), 
        LOADBALANCE_CONNECTION("jdbc:mysql:loadbalance:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.LoadBalanceConnectionUrl", PropertyKey.dnsSrv, Type.LOADBALANCE_DNS_SRV_CONNECTION), 
        REPLICATION_CONNECTION("jdbc:mysql:replication:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.ReplicationConnectionUrl", PropertyKey.dnsSrv, Type.REPLICATION_DNS_SRV_CONNECTION), 
        XDEVAPI_SESSION("mysqlx:", HostsCardinality.ONE_OR_MORE, "com.mysql.cj.conf.url.XDevApiConnectionUrl", PropertyKey.xdevapiDnsSrv, Type.XDEVAPI_DNS_SRV_SESSION);
        
        private String scheme;
        private HostsCardinality cardinality;
        private String implementingClass;
        private PropertyKey dnsSrvPropertyKey;
        private Type alternateDnsSrvType;
        
        private Type(final String scheme, final HostsCardinality cardinality, final String implementingClass) {
            this(scheme, cardinality, implementingClass, null, null);
        }
        
        private Type(final String scheme, final HostsCardinality cardinality, final String implementingClass, final PropertyKey dnsSrvPropertyKey, final Type alternateDnsSrvType) {
            this.scheme = scheme;
            this.cardinality = cardinality;
            this.implementingClass = implementingClass;
            this.dnsSrvPropertyKey = dnsSrvPropertyKey;
            this.alternateDnsSrvType = alternateDnsSrvType;
        }
        
        public String getScheme() {
            return this.scheme;
        }
        
        public HostsCardinality getCardinality() {
            return this.cardinality;
        }
        
        public String getImplementingClass() {
            return this.implementingClass;
        }
        
        public PropertyKey getDnsSrvPropertyKey() {
            return this.dnsSrvPropertyKey;
        }
        
        public Type getAlternateDnsSrvType() {
            return this.alternateDnsSrvType;
        }
        
        public static Type fromValue(final String scheme, final int n) {
            for (final Type t : values()) {
                if (t.getScheme().equalsIgnoreCase(scheme) && (n < 0 || t.getCardinality().assertSize(n))) {
                    return t;
                }
            }
            if (n < 0) {
                throw ExceptionFactory.createException(UnsupportedConnectionStringException.class, Messages.getString("ConnectionString.5", new Object[] { scheme }));
            }
            throw ExceptionFactory.createException(UnsupportedConnectionStringException.class, Messages.getString("ConnectionString.6", new Object[] { scheme, n }));
        }
        
        public static ConnectionUrl getConnectionUrlInstance(final ConnectionUrlParser parser, final Properties info) {
            final int hostsCount = parser.getHosts().size();
            Type type = fromValue(parser.getScheme(), hostsCount);
            final PropertyKey dnsSrvPropKey = type.getDnsSrvPropertyKey();
            if (dnsSrvPropKey != null && type.getAlternateDnsSrvType() != null) {
                if (info != null && info.containsKey(dnsSrvPropKey.getKeyName())) {
                    if (PropertyDefinitions.getPropertyDefinition(dnsSrvPropKey).parseObject(info.getProperty(dnsSrvPropKey.getKeyName()), null)) {
                        type = fromValue(type.getAlternateDnsSrvType().getScheme(), hostsCount);
                    }
                }
                else {
                    final Map<String, String> parsedProperties;
                    if ((parsedProperties = parser.getProperties()).containsKey(dnsSrvPropKey.getKeyName()) && (boolean)PropertyDefinitions.getPropertyDefinition(dnsSrvPropKey).parseObject(parsedProperties.get(dnsSrvPropKey.getKeyName()), null)) {
                        type = fromValue(type.getAlternateDnsSrvType().getScheme(), hostsCount);
                    }
                }
            }
            return type.getImplementingInstance(parser, info);
        }
        
        public static boolean isSupported(final String scheme) {
            for (final Type t : values()) {
                if (t.getScheme().equalsIgnoreCase(scheme)) {
                    return true;
                }
            }
            return false;
        }
        
        private ConnectionUrl getImplementingInstance(final ConnectionUrlParser parser, final Properties info) {
            return (ConnectionUrl)Util.getInstance(this.getImplementingClass(), new Class[] { ConnectionUrlParser.class, Properties.class }, new Object[] { parser, info }, null);
        }
    }
}
