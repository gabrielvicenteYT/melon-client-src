package com.mysql.cj;

import java.util.concurrent.*;
import java.lang.ref.*;
import com.mysql.cj.interceptors.*;
import com.mysql.cj.conf.*;
import java.util.function.*;
import java.net.*;
import java.io.*;
import com.mysql.cj.util.*;
import java.nio.*;
import java.nio.charset.*;
import com.mysql.cj.log.*;
import java.sql.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.protocol.*;
import java.util.*;
import com.mysql.cj.result.*;
import com.mysql.cj.exceptions.*;

public class NativeSession extends CoreSession implements Serializable
{
    private static final long serialVersionUID = 5323638898749073419L;
    private CacheAdapter<String, Map<String, String>> serverConfigCache;
    private static final Map<String, Map<Integer, String>> customIndexToCharsetMapByUrl;
    private static final Map<String, Map<String, Integer>> customCharsetToMblenMapByUrl;
    private boolean requiresEscapingEncoder;
    private long lastQueryFinishedTime;
    private boolean needsPing;
    private NativeMessageBuilder commandBuilder;
    private boolean isClosed;
    private Throwable forceClosedReason;
    private CopyOnWriteArrayList<WeakReference<Session.SessionEventListener>> listeners;
    private transient Timer cancelTimer;
    private static final String SERVER_VERSION_STRING_VAR_NAME = "server_version_string";
    
    public NativeSession(final HostInfo hostInfo, final PropertySet propSet) {
        super(hostInfo, propSet);
        this.lastQueryFinishedTime = 0L;
        this.needsPing = false;
        this.commandBuilder = new NativeMessageBuilder();
        this.isClosed = true;
        this.listeners = new CopyOnWriteArrayList<WeakReference<Session.SessionEventListener>>();
    }
    
    public void connect(final HostInfo hi, final String user, final String password, final String database, final int loginTimeout, final TransactionEventHandler transactionManager) throws IOException {
        this.hostInfo = hi;
        this.setSessionMaxRows(-1);
        final SocketConnection socketConnection = new NativeSocketConnection();
        socketConnection.connect(this.hostInfo.getHost(), this.hostInfo.getPort(), this.propertySet, this.getExceptionInterceptor(), this.log, loginTimeout);
        if (this.protocol == null) {
            this.protocol = NativeProtocol.getInstance(this, socketConnection, this.propertySet, this.log, transactionManager);
        }
        else {
            this.protocol.init(this, socketConnection, this.propertySet, transactionManager);
        }
        this.protocol.connect(user, password, database);
        this.protocol.getServerSession().setErrorMessageEncoding(this.protocol.getAuthenticationProvider().getEncodingForHandshake());
        this.isClosed = false;
    }
    
    public NativeProtocol getProtocol() {
        return (NativeProtocol)this.protocol;
    }
    
    @Override
    public void quit() {
        if (this.protocol != null) {
            try {
                ((NativeProtocol)this.protocol).quit();
            }
            catch (Exception ex) {}
        }
        synchronized (this) {
            if (this.cancelTimer != null) {
                this.cancelTimer.cancel();
                this.cancelTimer = null;
            }
        }
        this.isClosed = true;
        super.quit();
    }
    
    @Override
    public void forceClose() {
        if (this.protocol != null) {
            try {
                this.protocol.getSocketConnection().forceClose();
                ((NativeProtocol)this.protocol).releaseResources();
            }
            catch (Throwable t) {}
        }
        synchronized (this) {
            if (this.cancelTimer != null) {
                this.cancelTimer.cancel();
                this.cancelTimer = null;
            }
        }
        this.isClosed = true;
        super.forceClose();
    }
    
    public void enableMultiQueries() {
        this.sendCommand(this.commandBuilder.buildComSetOption(((NativeProtocol)this.protocol).getSharedSendPacket(), 0), false, 0);
        ((NativeServerSession)this.getServerSession()).preserveOldTransactionState();
    }
    
    public void disableMultiQueries() {
        this.sendCommand(this.commandBuilder.buildComSetOption(((NativeProtocol)this.protocol).getSharedSendPacket(), 1), false, 0);
        ((NativeServerSession)this.getServerSession()).preserveOldTransactionState();
    }
    
    @Override
    public boolean isSetNeededForAutoCommitMode(final boolean autoCommitFlag) {
        return ((NativeServerSession)this.protocol.getServerSession()).isSetNeededForAutoCommitMode(autoCommitFlag, false);
    }
    
    public int getSessionMaxRows() {
        return this.sessionMaxRows;
    }
    
    public void setSessionMaxRows(final int sessionMaxRows) {
        this.sessionMaxRows = sessionMaxRows;
    }
    
    public void setQueryInterceptors(final List<QueryInterceptor> queryInterceptors) {
        ((NativeProtocol)this.protocol).setQueryInterceptors(queryInterceptors);
    }
    
    public boolean isServerLocal(final Session sess) {
        final SocketFactory factory = this.protocol.getSocketConnection().getSocketFactory();
        return factory.isLocallyConnected(sess);
    }
    
    public void shutdownServer() {
        if (this.versionMeetsMinimum(5, 7, 9)) {
            this.sendCommand(this.commandBuilder.buildComQuery(this.getSharedSendPacket(), "SHUTDOWN"), false, 0);
        }
        else {
            this.sendCommand(this.commandBuilder.buildComShutdown(this.getSharedSendPacket()), false, 0);
        }
    }
    
    public void setSocketTimeout(final int milliseconds) {
        this.getPropertySet().getProperty(PropertyKey.socketTimeout).setValue(milliseconds);
        ((NativeProtocol)this.protocol).setSocketTimeout(milliseconds);
    }
    
    public int getSocketTimeout() {
        final RuntimeProperty<Integer> sto = this.getPropertySet().getProperty(PropertyKey.socketTimeout);
        return sto.getValue();
    }
    
    public void checkForCharsetMismatch() {
        ((NativeProtocol)this.protocol).checkForCharsetMismatch();
    }
    
    public NativePacketPayload getSharedSendPacket() {
        return ((NativeProtocol)this.protocol).getSharedSendPacket();
    }
    
    public void dumpPacketRingBuffer() {
        ((NativeProtocol)this.protocol).dumpPacketRingBuffer();
    }
    
    public <T extends Resultset> T invokeQueryInterceptorsPre(final Supplier<String> sql, final Query interceptedQuery, final boolean forceExecute) {
        return ((NativeProtocol)this.protocol).invokeQueryInterceptorsPre(sql, interceptedQuery, forceExecute);
    }
    
    public <T extends Resultset> T invokeQueryInterceptorsPost(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final boolean forceExecute) {
        return ((NativeProtocol)this.protocol).invokeQueryInterceptorsPost(sql, interceptedQuery, originalResultSet, forceExecute);
    }
    
    public boolean shouldIntercept() {
        return ((NativeProtocol)this.protocol).getQueryInterceptors() != null;
    }
    
    public long getCurrentTimeNanosOrMillis() {
        return ((NativeProtocol)this.protocol).getCurrentTimeNanosOrMillis();
    }
    
    public final NativePacketPayload sendCommand(final NativePacketPayload queryPacket, final boolean skipCheck, final int timeoutMillis) {
        return (NativePacketPayload)this.protocol.sendCommand(queryPacket, skipCheck, timeoutMillis);
    }
    
    public long getSlowQueryThreshold() {
        return ((NativeProtocol)this.protocol).getSlowQueryThreshold();
    }
    
    public boolean hadWarnings() {
        return ((NativeProtocol)this.protocol).hadWarnings();
    }
    
    public void clearInputStream() {
        ((NativeProtocol)this.protocol).clearInputStream();
    }
    
    public NetworkResources getNetworkResources() {
        return this.protocol.getSocketConnection().getNetworkResources();
    }
    
    @Override
    public boolean isSSLEstablished() {
        return this.protocol.getSocketConnection().isSSLEstablished();
    }
    
    public int getCommandCount() {
        return ((NativeProtocol)this.protocol).getCommandCount();
    }
    
    @Override
    public SocketAddress getRemoteSocketAddress() {
        try {
            return this.protocol.getSocketConnection().getMysqlSocket().getRemoteSocketAddress();
        }
        catch (IOException e) {
            throw new CJCommunicationsException(e);
        }
    }
    
    public InputStream getLocalInfileInputStream() {
        return this.protocol.getLocalInfileInputStream();
    }
    
    public void setLocalInfileInputStream(final InputStream stream) {
        this.protocol.setLocalInfileInputStream(stream);
    }
    
    private void configureCharsetProperties() {
        if (this.characterEncoding.getValue() != null) {
            try {
                final String testString = "abc";
                StringUtils.getBytes(testString, this.characterEncoding.getValue());
            }
            catch (WrongArgumentException waEx) {
                final String oldEncoding = this.characterEncoding.getValue();
                this.characterEncoding.setValue(CharsetMapping.getJavaEncodingForMysqlCharset(oldEncoding));
                if (this.characterEncoding.getValue() == null) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Connection.5", new Object[] { oldEncoding }), this.getExceptionInterceptor());
                }
                final String testString2 = "abc";
                StringUtils.getBytes(testString2, this.characterEncoding.getValue());
            }
        }
    }
    
    public boolean configureClientCharacterSet(final boolean dontCheckServerMatch) {
        String realJavaEncoding = this.characterEncoding.getValue();
        final RuntimeProperty<String> characterSetResults = this.getPropertySet().getProperty(PropertyKey.characterSetResults);
        boolean characterSetAlreadyConfigured = false;
        try {
            characterSetAlreadyConfigured = true;
            this.configureCharsetProperties();
            realJavaEncoding = this.characterEncoding.getValue();
            String connectionCollationSuffix = "";
            String connectionCollationCharset = null;
            final String connectionCollation = this.getPropertySet().getStringProperty(PropertyKey.connectionCollation).getStringValue();
            if (connectionCollation != null) {
                for (int i = 1; i < CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME.length; ++i) {
                    if (CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[i].equals(connectionCollation)) {
                        connectionCollationSuffix = " COLLATE " + CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[i];
                        connectionCollationCharset = CharsetMapping.COLLATION_INDEX_TO_CHARSET[i].charsetName;
                        realJavaEncoding = CharsetMapping.getJavaEncodingForCollationIndex(i);
                    }
                }
            }
            try {
                String serverEncodingToSet = CharsetMapping.getJavaEncodingForCollationIndex(this.protocol.getServerSession().getServerDefaultCollationIndex());
                if (serverEncodingToSet == null || serverEncodingToSet.length() == 0) {
                    if (realJavaEncoding == null) {
                        throw ExceptionFactory.createException(Messages.getString("Connection.6", new Object[] { this.protocol.getServerSession().getServerDefaultCollationIndex() }), this.getExceptionInterceptor());
                    }
                    this.characterEncoding.setValue(realJavaEncoding);
                }
                if ("ISO8859_1".equalsIgnoreCase(serverEncodingToSet)) {
                    serverEncodingToSet = "Cp1252";
                }
                if ("UnicodeBig".equalsIgnoreCase(serverEncodingToSet) || "UTF-16".equalsIgnoreCase(serverEncodingToSet) || "UTF-16LE".equalsIgnoreCase(serverEncodingToSet) || "UTF-32".equalsIgnoreCase(serverEncodingToSet)) {
                    serverEncodingToSet = "UTF-8";
                }
                this.characterEncoding.setValue(serverEncodingToSet);
            }
            catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                if (realJavaEncoding == null) {
                    throw ExceptionFactory.createException(Messages.getString("Connection.6", new Object[] { this.protocol.getServerSession().getServerDefaultCollationIndex() }), this.getExceptionInterceptor());
                }
                this.characterEncoding.setValue(realJavaEncoding);
            }
            if (this.characterEncoding.getValue() == null) {
                this.characterEncoding.setValue("ISO8859_1");
            }
            if (realJavaEncoding != null) {
                if (realJavaEncoding.equalsIgnoreCase("UTF-8") || realJavaEncoding.equalsIgnoreCase("UTF8")) {
                    final String utf8CharsetName = (connectionCollationSuffix.length() > 0) ? connectionCollationCharset : "utf8mb4";
                    if (dontCheckServerMatch || !this.protocol.getServerSession().characterSetNamesMatches("utf8") || !this.protocol.getServerSession().characterSetNamesMatches("utf8mb4") || (connectionCollationSuffix.length() > 0 && !connectionCollation.equalsIgnoreCase(this.protocol.getServerSession().getServerVariable("collation_server")))) {
                        this.sendCommand(this.commandBuilder.buildComQuery(null, "SET NAMES " + utf8CharsetName + connectionCollationSuffix), false, 0);
                        this.protocol.getServerSession().getServerVariables().put("character_set_client", utf8CharsetName);
                        this.protocol.getServerSession().getServerVariables().put("character_set_connection", utf8CharsetName);
                    }
                    this.characterEncoding.setValue(realJavaEncoding);
                }
                else {
                    final String mysqlCharsetName = (connectionCollationSuffix.length() > 0) ? connectionCollationCharset : CharsetMapping.getMysqlCharsetForJavaEncoding(realJavaEncoding.toUpperCase(Locale.ENGLISH), this.getServerSession().getServerVersion());
                    if (mysqlCharsetName != null && (dontCheckServerMatch || !this.protocol.getServerSession().characterSetNamesMatches(mysqlCharsetName))) {
                        this.sendCommand(this.commandBuilder.buildComQuery(null, "SET NAMES " + mysqlCharsetName + connectionCollationSuffix), false, 0);
                        this.protocol.getServerSession().getServerVariables().put("character_set_client", mysqlCharsetName);
                        this.protocol.getServerSession().getServerVariables().put("character_set_connection", mysqlCharsetName);
                    }
                    this.characterEncoding.setValue(realJavaEncoding);
                }
            }
            else if (this.characterEncoding.getValue() != null) {
                String mysqlCharsetName = (connectionCollationSuffix.length() > 0) ? connectionCollationCharset : this.getServerSession().getServerDefaultCharset();
                boolean ucs2 = false;
                if ("ucs2".equalsIgnoreCase(mysqlCharsetName) || "utf16".equalsIgnoreCase(mysqlCharsetName) || "utf16le".equalsIgnoreCase(mysqlCharsetName) || "utf32".equalsIgnoreCase(mysqlCharsetName)) {
                    mysqlCharsetName = "utf8";
                    ucs2 = true;
                    if (characterSetResults.getValue() == null) {
                        characterSetResults.setValue("UTF-8");
                    }
                }
                if (dontCheckServerMatch || !this.protocol.getServerSession().characterSetNamesMatches(mysqlCharsetName) || ucs2) {
                    this.sendCommand(this.commandBuilder.buildComQuery(null, "SET NAMES " + mysqlCharsetName + connectionCollationSuffix), false, 0);
                    this.protocol.getServerSession().getServerVariables().put("character_set_client", mysqlCharsetName);
                    this.protocol.getServerSession().getServerVariables().put("character_set_connection", mysqlCharsetName);
                }
                realJavaEncoding = this.characterEncoding.getValue();
            }
            final String onServer = this.protocol.getServerSession().getServerVariable("character_set_results");
            if (characterSetResults.getValue() == null) {
                if (onServer != null && onServer.length() > 0 && !"NULL".equalsIgnoreCase(onServer)) {
                    this.sendCommand(this.commandBuilder.buildComQuery(null, "SET character_set_results = NULL"), false, 0);
                    this.protocol.getServerSession().getServerVariables().put("local.character_set_results", null);
                }
                else {
                    this.protocol.getServerSession().getServerVariables().put("local.character_set_results", onServer);
                }
            }
            else {
                final String charsetResults = characterSetResults.getValue();
                String mysqlEncodingName = null;
                if ("UTF-8".equalsIgnoreCase(charsetResults) || "UTF8".equalsIgnoreCase(charsetResults)) {
                    mysqlEncodingName = "utf8";
                }
                else if ("null".equalsIgnoreCase(charsetResults)) {
                    mysqlEncodingName = "NULL";
                }
                else {
                    mysqlEncodingName = CharsetMapping.getMysqlCharsetForJavaEncoding(charsetResults.toUpperCase(Locale.ENGLISH), this.getServerSession().getServerVersion());
                }
                if (mysqlEncodingName == null) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Connection.7", new Object[] { charsetResults }), this.getExceptionInterceptor());
                }
                if (!mysqlEncodingName.equalsIgnoreCase(this.protocol.getServerSession().getServerVariable("character_set_results"))) {
                    final StringBuilder setBuf = new StringBuilder("SET character_set_results = ".length() + mysqlEncodingName.length());
                    setBuf.append("SET character_set_results = ").append(mysqlEncodingName);
                    this.sendCommand(this.commandBuilder.buildComQuery(null, setBuf.toString()), false, 0);
                    this.protocol.getServerSession().getServerVariables().put("local.character_set_results", mysqlEncodingName);
                    this.protocol.getServerSession().setErrorMessageEncoding(charsetResults);
                }
                else {
                    this.protocol.getServerSession().getServerVariables().put("local.character_set_results", onServer);
                }
            }
        }
        finally {
            this.characterEncoding.setValue(realJavaEncoding);
        }
        try {
            final CharsetEncoder enc = Charset.forName(this.characterEncoding.getValue()).newEncoder();
            final CharBuffer cbuf = CharBuffer.allocate(1);
            final ByteBuffer bbuf = ByteBuffer.allocate(1);
            cbuf.put("¥");
            cbuf.position(0);
            enc.encode(cbuf, bbuf, true);
            if (bbuf.get(0) == 92) {
                this.requiresEscapingEncoder = true;
            }
            else {
                cbuf.clear();
                bbuf.clear();
                cbuf.put("\u20a9");
                cbuf.position(0);
                enc.encode(cbuf, bbuf, true);
                if (bbuf.get(0) == 92) {
                    this.requiresEscapingEncoder = true;
                }
            }
        }
        catch (UnsupportedCharsetException ucex) {
            byte[] bbuf2 = StringUtils.getBytes("¥", this.characterEncoding.getValue());
            if (bbuf2[0] == 92) {
                this.requiresEscapingEncoder = true;
            }
            else {
                bbuf2 = StringUtils.getBytes("\u20a9", this.characterEncoding.getValue());
                if (bbuf2[0] == 92) {
                    this.requiresEscapingEncoder = true;
                }
            }
        }
        return characterSetAlreadyConfigured;
    }
    
    public boolean getRequiresEscapingEncoder() {
        return this.requiresEscapingEncoder;
    }
    
    private void createConfigCacheIfNeeded(final Object syncMutex) {
        synchronized (syncMutex) {
            if (this.serverConfigCache != null) {
                return;
            }
            try {
                final Class<?> factoryClass = Class.forName(this.getPropertySet().getStringProperty(PropertyKey.serverConfigCacheFactory).getStringValue());
                final CacheAdapterFactory<String, Map<String, String>> cacheFactory = (CacheAdapterFactory<String, Map<String, String>>)factoryClass.newInstance();
                this.serverConfigCache = cacheFactory.getInstance(syncMutex, this.hostInfo.getDatabaseUrl(), Integer.MAX_VALUE, Integer.MAX_VALUE);
                final ExceptionInterceptor evictOnCommsError = new ExceptionInterceptor() {
                    @Override
                    public ExceptionInterceptor init(final Properties config, final Log log1) {
                        return this;
                    }
                    
                    @Override
                    public void destroy() {
                    }
                    
                    @Override
                    public Exception interceptException(final Exception sqlEx) {
                        if (sqlEx instanceof SQLException && ((SQLException)sqlEx).getSQLState() != null && ((SQLException)sqlEx).getSQLState().startsWith("08")) {
                            NativeSession.this.serverConfigCache.invalidate(NativeSession.this.hostInfo.getDatabaseUrl());
                        }
                        return null;
                    }
                };
                if (this.exceptionInterceptor == null) {
                    this.exceptionInterceptor = evictOnCommsError;
                }
                else {
                    ((ExceptionInterceptorChain)this.exceptionInterceptor).addRingZero(evictOnCommsError);
                }
            }
            catch (ClassNotFoundException e) {
                throw ExceptionFactory.createException(Messages.getString("Connection.CantFindCacheFactory", new Object[] { this.getPropertySet().getStringProperty(PropertyKey.parseInfoCacheFactory).getValue(), PropertyKey.parseInfoCacheFactory }), e, this.getExceptionInterceptor());
            }
            catch (InstantiationException | IllegalAccessException | CJException ex2) {
                final Exception ex;
                final Exception e2 = ex;
                throw ExceptionFactory.createException(Messages.getString("Connection.CantLoadCacheFactory", new Object[] { this.getPropertySet().getStringProperty(PropertyKey.parseInfoCacheFactory).getValue(), PropertyKey.parseInfoCacheFactory }), e2, this.getExceptionInterceptor());
            }
        }
    }
    
    public void loadServerVariables(final Object syncMutex, String version) {
        if (this.cacheServerConfiguration.getValue()) {
            this.createConfigCacheIfNeeded(syncMutex);
            final Map<String, String> cachedVariableMap = this.serverConfigCache.get(this.hostInfo.getDatabaseUrl());
            if (cachedVariableMap != null) {
                final String cachedServerVersion = cachedVariableMap.get("server_version_string");
                if (cachedServerVersion != null && this.getServerSession().getServerVersion() != null && cachedServerVersion.equals(this.getServerSession().getServerVersion().toString())) {
                    this.protocol.getServerSession().setServerVariables(cachedVariableMap);
                    return;
                }
                this.serverConfigCache.invalidate(this.hostInfo.getDatabaseUrl());
            }
        }
        try {
            if (version != null && version.indexOf(42) != -1) {
                final StringBuilder buf = new StringBuilder(version.length() + 10);
                for (int i = 0; i < version.length(); ++i) {
                    final char c = version.charAt(i);
                    buf.append((c == '*') ? "[star]" : Character.valueOf(c));
                }
                version = buf.toString();
            }
            final String versionComment = (this.propertySet.getBooleanProperty(PropertyKey.paranoid).getValue() || version == null) ? "" : ("/* " + version + " */");
            this.protocol.getServerSession().setServerVariables(new HashMap<String, String>());
            if (this.versionMeetsMinimum(5, 1, 0)) {
                final StringBuilder queryBuf = new StringBuilder(versionComment).append("SELECT");
                queryBuf.append("  @@session.auto_increment_increment AS auto_increment_increment");
                queryBuf.append(", @@character_set_client AS character_set_client");
                queryBuf.append(", @@character_set_connection AS character_set_connection");
                queryBuf.append(", @@character_set_results AS character_set_results");
                queryBuf.append(", @@character_set_server AS character_set_server");
                queryBuf.append(", @@collation_server AS collation_server");
                queryBuf.append(", @@collation_connection AS collation_connection");
                queryBuf.append(", @@init_connect AS init_connect");
                queryBuf.append(", @@interactive_timeout AS interactive_timeout");
                if (!this.versionMeetsMinimum(5, 5, 0)) {
                    queryBuf.append(", @@language AS language");
                }
                queryBuf.append(", @@license AS license");
                queryBuf.append(", @@lower_case_table_names AS lower_case_table_names");
                queryBuf.append(", @@max_allowed_packet AS max_allowed_packet");
                queryBuf.append(", @@net_write_timeout AS net_write_timeout");
                queryBuf.append(", @@performance_schema AS performance_schema");
                if (!this.versionMeetsMinimum(8, 0, 3)) {
                    queryBuf.append(", @@query_cache_size AS query_cache_size");
                    queryBuf.append(", @@query_cache_type AS query_cache_type");
                }
                queryBuf.append(", @@sql_mode AS sql_mode");
                queryBuf.append(", @@system_time_zone AS system_time_zone");
                queryBuf.append(", @@time_zone AS time_zone");
                if (this.versionMeetsMinimum(8, 0, 3) || (this.versionMeetsMinimum(5, 7, 20) && !this.versionMeetsMinimum(8, 0, 0))) {
                    queryBuf.append(", @@transaction_isolation AS transaction_isolation");
                }
                else {
                    queryBuf.append(", @@tx_isolation AS transaction_isolation");
                }
                queryBuf.append(", @@wait_timeout AS wait_timeout");
                final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(null, queryBuf.toString()), false, 0);
                final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                final Field[] f = rs.getColumnDefinition().getFields();
                if (f.length > 0) {
                    final ValueFactory<String> vf = new StringValueFactory(this.propertySet);
                    final Row r;
                    if ((r = rs.getRows().next()) != null) {
                        for (int j = 0; j < f.length; ++j) {
                            this.protocol.getServerSession().getServerVariables().put(f[j].getColumnLabel(), r.getValue(j, vf));
                        }
                    }
                }
            }
            else {
                final NativePacketPayload resultPacket2 = this.sendCommand(this.commandBuilder.buildComQuery(null, versionComment + "SHOW VARIABLES"), false, 0);
                final Resultset rs2 = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket2, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                final ValueFactory<String> vf2 = new StringValueFactory(this.propertySet);
                Row r2;
                while ((r2 = rs2.getRows().next()) != null) {
                    this.protocol.getServerSession().getServerVariables().put(r2.getValue(0, vf2), r2.getValue(1, vf2));
                }
            }
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
        if (this.cacheServerConfiguration.getValue()) {
            this.protocol.getServerSession().getServerVariables().put("server_version_string", this.getServerSession().getServerVersion().toString());
            this.serverConfigCache.put(this.hostInfo.getDatabaseUrl(), this.protocol.getServerSession().getServerVariables());
        }
    }
    
    public void setSessionVariables() {
        final String sessionVariables = this.getPropertySet().getStringProperty(PropertyKey.sessionVariables).getValue();
        if (sessionVariables != null) {
            final List<String> variablesToSet = new ArrayList<String>();
            for (final String part : StringUtils.split(sessionVariables, ",", "\"'(", "\"')", "\"'", true)) {
                variablesToSet.addAll(StringUtils.split(part, ";", "\"'(", "\"')", "\"'", true));
            }
            if (!variablesToSet.isEmpty()) {
                final StringBuilder query = new StringBuilder("SET ");
                String separator = "";
                for (final String variableToSet : variablesToSet) {
                    if (variableToSet.length() > 0) {
                        query.append(separator);
                        if (!variableToSet.startsWith("@")) {
                            query.append("SESSION ");
                        }
                        query.append(variableToSet);
                        separator = ",";
                    }
                }
                this.sendCommand(this.commandBuilder.buildComQuery(null, query.toString()), false, 0);
            }
        }
    }
    
    public void buildCollationMapping() {
        Map<Integer, String> customCharset = null;
        Map<String, Integer> customMblen = null;
        final String databaseURL = this.hostInfo.getDatabaseUrl();
        if (this.cacheServerConfiguration.getValue()) {
            synchronized (NativeSession.customIndexToCharsetMapByUrl) {
                customCharset = NativeSession.customIndexToCharsetMapByUrl.get(databaseURL);
                customMblen = NativeSession.customCharsetToMblenMapByUrl.get(databaseURL);
            }
        }
        if (customCharset == null && this.getPropertySet().getBooleanProperty(PropertyKey.detectCustomCollations).getValue()) {
            customCharset = new HashMap<Integer, String>();
            customMblen = new HashMap<String, Integer>();
            final ValueFactory<Integer> ivf = new IntegerValueFactory(this.getPropertySet());
            try {
                final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(null, "SHOW COLLATION"), false, 0);
                final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                final ValueFactory<String> svf = new StringValueFactory(this.propertySet);
                Row r;
                while ((r = rs.getRows().next()) != null) {
                    final int collationIndex = r.getValue(2, ivf).intValue();
                    final String charsetName = r.getValue(1, svf);
                    if (collationIndex >= 2048 || !charsetName.equals(CharsetMapping.getMysqlCharsetNameForCollationIndex(collationIndex))) {
                        customCharset.put(collationIndex, charsetName);
                    }
                    if (!CharsetMapping.CHARSET_NAME_TO_CHARSET.containsKey(charsetName)) {
                        customMblen.put(charsetName, null);
                    }
                }
            }
            catch (IOException e) {
                throw ExceptionFactory.createException(e.getMessage(), e, this.exceptionInterceptor);
            }
            if (customMblen.size() > 0) {
                try {
                    final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(null, "SHOW CHARACTER SET"), false, 0);
                    final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                    final int charsetColumn = rs.getColumnDefinition().getColumnNameToIndex().get("Charset");
                    final int maxlenColumn = rs.getColumnDefinition().getColumnNameToIndex().get("Maxlen");
                    final ValueFactory<String> svf2 = new StringValueFactory(this.propertySet);
                    Row r2;
                    while ((r2 = rs.getRows().next()) != null) {
                        final String charsetName2 = r2.getValue(charsetColumn, svf2);
                        if (customMblen.containsKey(charsetName2)) {
                            customMblen.put(charsetName2, r2.getValue(maxlenColumn, ivf));
                        }
                    }
                }
                catch (IOException e) {
                    throw ExceptionFactory.createException(e.getMessage(), e, this.exceptionInterceptor);
                }
            }
            if (this.cacheServerConfiguration.getValue()) {
                synchronized (NativeSession.customIndexToCharsetMapByUrl) {
                    NativeSession.customIndexToCharsetMapByUrl.put(databaseURL, customCharset);
                    NativeSession.customCharsetToMblenMapByUrl.put(databaseURL, customMblen);
                }
            }
        }
        if (customCharset != null) {
            ((NativeServerSession)this.protocol.getServerSession()).indexToCustomMysqlCharset = Collections.unmodifiableMap((Map<? extends Integer, ? extends String>)customCharset);
        }
        if (customMblen != null) {
            ((NativeServerSession)this.protocol.getServerSession()).mysqlCharsetToCustomMblen = Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)customMblen);
        }
        if (this.protocol.getServerSession().getServerDefaultCollationIndex() == 0) {
            final String collationServer = this.protocol.getServerSession().getServerVariable("collation_server");
            if (collationServer != null) {
                for (int i = 1; i < CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME.length; ++i) {
                    if (CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[i].equals(collationServer)) {
                        this.protocol.getServerSession().setServerDefaultCollationIndex(i);
                        break;
                    }
                }
            }
            else {
                this.protocol.getServerSession().setServerDefaultCollationIndex(45);
            }
        }
    }
    
    @Override
    public String getProcessHost() {
        try {
            long threadId = this.getThreadId();
            String processHost = this.findProcessHost(threadId);
            if (processHost == null) {
                this.log.logWarn(String.format("Connection id %d not found in \"SHOW PROCESSLIST\", assuming 32-bit overflow, using SELECT CONNECTION_ID() instead", threadId));
                final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(null, "SELECT CONNECTION_ID()"), false, 0);
                final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                final ValueFactory<Long> lvf = new LongValueFactory(this.getPropertySet());
                final Row r;
                if ((r = rs.getRows().next()) != null) {
                    threadId = r.getValue(0, lvf);
                    processHost = this.findProcessHost(threadId);
                }
                else {
                    this.log.logError("No rows returned for statement \"SELECT CONNECTION_ID()\", local connection check will most likely be incorrect");
                }
            }
            if (processHost == null) {
                this.log.logWarn(String.format("Cannot find process listing for connection %d in SHOW PROCESSLIST output, unable to determine if locally connected", threadId));
            }
            return processHost;
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    private String findProcessHost(final long threadId) {
        try {
            String processHost = null;
            final String ps = this.protocol.getServerSession().getServerVariable("performance_schema");
            final NativePacketPayload resultPacket = (this.versionMeetsMinimum(5, 6, 0) && ps != null && ("1".contentEquals(ps) || "ON".contentEquals(ps))) ? this.sendCommand(this.commandBuilder.buildComQuery(null, "select PROCESSLIST_ID, PROCESSLIST_USER, PROCESSLIST_HOST from performance_schema.threads where PROCESSLIST_ID=" + threadId), false, 0) : this.sendCommand(this.commandBuilder.buildComQuery(null, "SHOW PROCESSLIST"), false, 0);
            final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
            final ValueFactory<Long> lvf = new LongValueFactory(this.getPropertySet());
            final ValueFactory<String> svf = new StringValueFactory(this.propertySet);
            Row r;
            while ((r = rs.getRows().next()) != null) {
                final long id = r.getValue(0, lvf);
                if (threadId == id) {
                    processHost = r.getValue(2, svf);
                    break;
                }
            }
            return processHost;
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    public String queryServerVariable(final String varName) {
        try {
            final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(null, "SELECT " + varName), false, 0);
            final Resultset rs = ((NativeProtocol)this.protocol).readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
            final ValueFactory<String> svf = new StringValueFactory(this.propertySet);
            final Row r;
            if ((r = rs.getRows().next()) != null) {
                final String s = r.getValue(0, svf);
                if (s != null) {
                    return s;
                }
            }
            return null;
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    public <T extends Resultset> T execSQL(final Query callingQuery, final String query, final int maxRows, final NativePacketPayload packet, final boolean streamResults, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory, final ColumnDefinition cachedMetadata, final boolean isBatch) {
        final long queryStartTime = this.gatherPerfMetrics.getValue() ? System.currentTimeMillis() : 0L;
        final int endOfQueryPacketPosition = (packet != null) ? packet.getPosition() : 0;
        this.lastQueryFinishedTime = 0L;
        Label_0128: {
            if (!this.autoReconnect.getValue() || (!this.getServerSession().isAutoCommit() && !this.autoReconnectForPools.getValue()) || !this.needsPing || isBatch) {
                break Label_0128;
            }
            try {
                this.ping(false, 0);
                this.needsPing = false;
            }
            catch (Exception Ex) {
                this.invokeReconnectListeners();
            }
            try {
                return (packet == null) ? ((NativeProtocol)this.protocol).sendQueryString(callingQuery, query, this.characterEncoding.getValue(), maxRows, streamResults, cachedMetadata, resultSetFactory) : ((NativeProtocol)this.protocol).sendQueryPacket(callingQuery, packet, maxRows, streamResults, cachedMetadata, resultSetFactory);
            }
            catch (CJException sqlE) {
                if (this.getPropertySet().getBooleanProperty(PropertyKey.dumpQueriesOnException).getValue()) {
                    final String extractedSql = NativePacketPayload.extractSqlFromPacket(query, packet, endOfQueryPacketPosition, this.getPropertySet().getIntegerProperty(PropertyKey.maxQuerySizeToLog).getValue());
                    final StringBuilder messageBuf = new StringBuilder(extractedSql.length() + 32);
                    messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                    messageBuf.append(extractedSql);
                    messageBuf.append("\n\n");
                    sqlE.appendMessage(messageBuf.toString());
                }
                if (this.autoReconnect.getValue()) {
                    if (sqlE instanceof CJCommunicationsException) {
                        this.protocol.getSocketConnection().forceClose();
                    }
                    this.needsPing = true;
                }
                else if (sqlE instanceof CJCommunicationsException) {
                    this.invokeCleanupListeners(sqlE);
                }
                throw sqlE;
            }
            catch (Throwable ex) {
                if (this.autoReconnect.getValue()) {
                    if (ex instanceof IOException) {
                        this.protocol.getSocketConnection().forceClose();
                    }
                    else if (ex instanceof IOException) {
                        this.invokeCleanupListeners(ex);
                    }
                    this.needsPing = true;
                }
                throw ExceptionFactory.createException(ex.getMessage(), ex, this.exceptionInterceptor);
            }
            finally {
                if (this.maintainTimeStats.getValue()) {
                    this.lastQueryFinishedTime = System.currentTimeMillis();
                }
                if (this.gatherPerfMetrics.getValue()) {
                    ((NativeProtocol)this.protocol).getMetricsHolder().registerQueryExecutionTime(System.currentTimeMillis() - queryStartTime);
                }
            }
        }
    }
    
    public long getIdleFor() {
        return (this.lastQueryFinishedTime == 0L) ? 0L : (System.currentTimeMillis() - this.lastQueryFinishedTime);
    }
    
    public boolean isNeedsPing() {
        return this.needsPing;
    }
    
    public void setNeedsPing(final boolean needsPing) {
        this.needsPing = needsPing;
    }
    
    public void ping(final boolean checkForClosedConnection, final int timeoutMillis) {
        if (checkForClosedConnection) {
            this.checkClosed();
        }
        final long pingMillisLifetime = this.getPropertySet().getIntegerProperty(PropertyKey.selfDestructOnPingSecondsLifetime).getValue();
        final int pingMaxOperations = this.getPropertySet().getIntegerProperty(PropertyKey.selfDestructOnPingMaxOperations).getValue();
        if ((pingMillisLifetime > 0L && System.currentTimeMillis() - this.connectionCreationTimeMillis > pingMillisLifetime) || (pingMaxOperations > 0 && pingMaxOperations <= this.getCommandCount())) {
            this.invokeNormalCloseListeners();
            throw ExceptionFactory.createException(Messages.getString("Connection.exceededConnectionLifetime"), "08S01", 0, false, null, this.exceptionInterceptor);
        }
        this.sendCommand(this.commandBuilder.buildComPing(null), false, timeoutMillis);
    }
    
    public long getConnectionCreationTimeMillis() {
        return this.connectionCreationTimeMillis;
    }
    
    public void setConnectionCreationTimeMillis(final long connectionCreationTimeMillis) {
        this.connectionCreationTimeMillis = connectionCreationTimeMillis;
    }
    
    @Override
    public boolean isClosed() {
        return this.isClosed;
    }
    
    public void checkClosed() {
        if (!this.isClosed) {
            return;
        }
        if (this.forceClosedReason != null && this.forceClosedReason.getClass().equals(OperationCancelledException.class)) {
            throw (OperationCancelledException)this.forceClosedReason;
        }
        throw ExceptionFactory.createException(ConnectionIsClosedException.class, Messages.getString("Connection.2"), this.forceClosedReason, this.getExceptionInterceptor());
    }
    
    public Throwable getForceClosedReason() {
        return this.forceClosedReason;
    }
    
    public void setForceClosedReason(final Throwable forceClosedReason) {
        this.forceClosedReason = forceClosedReason;
    }
    
    @Override
    public void addListener(final Session.SessionEventListener l) {
        this.listeners.addIfAbsent(new WeakReference<Session.SessionEventListener>(l));
    }
    
    @Override
    public void removeListener(final Session.SessionEventListener listener) {
        for (final WeakReference<Session.SessionEventListener> wr : this.listeners) {
            final Session.SessionEventListener l = wr.get();
            if (l == listener) {
                this.listeners.remove(wr);
                break;
            }
        }
    }
    
    protected void invokeNormalCloseListeners() {
        for (final WeakReference<Session.SessionEventListener> wr : this.listeners) {
            final Session.SessionEventListener l = wr.get();
            if (l != null) {
                l.handleNormalClose();
            }
            else {
                this.listeners.remove(wr);
            }
        }
    }
    
    protected void invokeReconnectListeners() {
        for (final WeakReference<Session.SessionEventListener> wr : this.listeners) {
            final Session.SessionEventListener l = wr.get();
            if (l != null) {
                l.handleReconnect();
            }
            else {
                this.listeners.remove(wr);
            }
        }
    }
    
    public void invokeCleanupListeners(final Throwable whyCleanedUp) {
        for (final WeakReference<Session.SessionEventListener> wr : this.listeners) {
            final Session.SessionEventListener l = wr.get();
            if (l != null) {
                l.handleCleanup(whyCleanedUp);
            }
            else {
                this.listeners.remove(wr);
            }
        }
    }
    
    @Override
    public String getIdentifierQuoteString() {
        return (this.protocol != null && this.protocol.getServerSession().useAnsiQuotedIdentifiers()) ? "\"" : "`";
    }
    
    public synchronized Timer getCancelTimer() {
        if (this.cancelTimer == null) {
            this.cancelTimer = new Timer("MySQL Statement Cancellation Timer", Boolean.TRUE);
        }
        return this.cancelTimer;
    }
    
    static {
        customIndexToCharsetMapByUrl = new HashMap<String, Map<Integer, String>>();
        customCharsetToMblenMapByUrl = new HashMap<String, Map<String, Integer>>();
    }
}
