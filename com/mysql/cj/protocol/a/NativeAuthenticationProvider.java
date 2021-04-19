package com.mysql.cj.protocol.a;

import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.a.authentication.*;
import java.util.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.a.result.*;
import com.mysql.cj.*;

public class NativeAuthenticationProvider implements AuthenticationProvider<NativePacketPayload>
{
    protected static final int AUTH_411_OVERHEAD = 33;
    private static final String NONE = "none";
    protected String seed;
    private boolean useConnectWithDb;
    private ExceptionInterceptor exceptionInterceptor;
    private PropertySet propertySet;
    private Protocol<NativePacketPayload> protocol;
    private Map<String, AuthenticationPlugin<NativePacketPayload>> authenticationPlugins;
    private List<String> disabledAuthenticationPlugins;
    private String clientDefaultAuthenticationPlugin;
    private String clientDefaultAuthenticationPluginName;
    private String serverDefaultAuthenticationPluginName;
    
    public NativeAuthenticationProvider() {
        this.authenticationPlugins = null;
        this.disabledAuthenticationPlugins = null;
        this.clientDefaultAuthenticationPlugin = null;
        this.clientDefaultAuthenticationPluginName = null;
        this.serverDefaultAuthenticationPluginName = null;
    }
    
    @Override
    public void init(final Protocol<NativePacketPayload> prot, final PropertySet propSet, final ExceptionInterceptor excInterceptor) {
        this.protocol = prot;
        this.propertySet = propSet;
        this.exceptionInterceptor = excInterceptor;
    }
    
    @Override
    public void connect(final ServerSession sessState, final String user, final String password, final String database) {
        final NativeCapabilities capabilities = (NativeCapabilities)sessState.getCapabilities();
        final NativePacketPayload buf = capabilities.getInitialHandshakePacket();
        final PropertyDefinitions.SslMode sslMode = this.propertySet.getEnumProperty(PropertyKey.sslMode).getValue();
        final int capabilityFlags = capabilities.getCapabilityFlags();
        if ((capabilityFlags & 0x800) == 0x0 && sslMode != PropertyDefinitions.SslMode.DISABLED && sslMode != PropertyDefinitions.SslMode.PREFERRED) {
            throw ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("MysqlIO.15"), this.getExceptionInterceptor());
        }
        if ((capabilityFlags & 0x8000) == 0x0) {
            throw ExceptionFactory.createException(UnableToConnectException.class, "CLIENT_SECURE_CONNECTION is required", this.getExceptionInterceptor());
        }
        if ((capabilityFlags & 0x80000) == 0x0) {
            throw ExceptionFactory.createException(UnableToConnectException.class, "CLIENT_PLUGIN_AUTH is required", this.getExceptionInterceptor());
        }
        sessState.setServerDefaultCollationIndex(capabilities.getServerDefaultCollationIndex());
        sessState.setStatusFlags(capabilities.getStatusFlags());
        final int authPluginDataLength = capabilities.getAuthPluginDataLength();
        final StringBuilder fullSeed = new StringBuilder((authPluginDataLength > 0) ? authPluginDataLength : 20);
        fullSeed.append(capabilities.getSeed());
        fullSeed.append((authPluginDataLength > 0) ? buf.readString(NativeConstants.StringLengthDataType.STRING_FIXED, "ASCII", authPluginDataLength - 8) : buf.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII"));
        this.seed = fullSeed.toString();
        this.useConnectWithDb = (database != null && database.length() > 0 && !this.propertySet.getBooleanProperty(PropertyKey.createDatabaseIfNotExist).getValue());
        final long clientParam = 0x88000 | (capabilityFlags & 0x1) | (capabilityFlags & 0x200) | (capabilityFlags & 0x2000) | (capabilityFlags & 0x20000) | (capabilityFlags & 0x40000) | (capabilityFlags & 0x4) | (capabilityFlags & 0x1000000) | (capabilityFlags & 0x200000) | (this.propertySet.getBooleanProperty(PropertyKey.useCompression).getValue() ? (capabilityFlags & 0x20) : 0) | (this.useConnectWithDb ? (capabilityFlags & 0x8) : 0) | (this.propertySet.getBooleanProperty(PropertyKey.useAffectedRows).getValue() ? 0 : (capabilityFlags & 0x2)) | ((this.propertySet.getBooleanProperty(PropertyKey.allowLoadLocalInfile).getValue() || this.propertySet.getStringProperty(PropertyKey.allowLoadLocalInfileInPath).isExplicitlySet()) ? (capabilityFlags & 0x80) : 0) | (this.propertySet.getBooleanProperty(PropertyKey.interactiveClient).getValue() ? (capabilityFlags & 0x400) : 0) | (this.propertySet.getBooleanProperty(PropertyKey.allowMultiQueries).getValue() ? (capabilityFlags & 0x10000) : 0) | (this.propertySet.getBooleanProperty(PropertyKey.disconnectOnExpiredPasswords).getValue() ? 0 : (capabilityFlags & 0x400000)) | ("none".equals(this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue()) ? 0 : (capabilityFlags & 0x100000)) | ((this.propertySet.getEnumProperty(PropertyKey.sslMode).getValue() != PropertyDefinitions.SslMode.DISABLED) ? (capabilityFlags & 0x800) : 0);
        sessState.setClientParam(clientParam);
        if ((clientParam & 0x800L) != 0x0L) {
            this.protocol.negotiateSSLConnection();
        }
        if (buf.isOKPacket()) {
            throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.UnexpectedAuthenticationApproval"), this.getExceptionInterceptor());
        }
        this.proceedHandshakeWithPluggableAuthentication(sessState, user, password, database, buf);
    }
    
    private void loadAuthenticationPlugins() {
        this.clientDefaultAuthenticationPlugin = this.propertySet.getStringProperty(PropertyKey.defaultAuthenticationPlugin).getValue();
        if (this.clientDefaultAuthenticationPlugin == null || "".equals(this.clientDefaultAuthenticationPlugin.trim())) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDefaultAuthenticationPlugin", new Object[] { this.clientDefaultAuthenticationPlugin }), this.getExceptionInterceptor());
        }
        final String disabledPlugins = this.propertySet.getStringProperty(PropertyKey.disabledAuthenticationPlugins).getValue();
        if (disabledPlugins != null && !"".equals(disabledPlugins)) {
            this.disabledAuthenticationPlugins = new ArrayList<String>();
            final List<String> pluginsToDisable = StringUtils.split(disabledPlugins, ",", true);
            final Iterator<String> iter = pluginsToDisable.iterator();
            while (iter.hasNext()) {
                this.disabledAuthenticationPlugins.add(iter.next());
            }
        }
        this.authenticationPlugins = new HashMap<String, AuthenticationPlugin<NativePacketPayload>>();
        boolean defaultIsFound = false;
        final List<AuthenticationPlugin<NativePacketPayload>> pluginsToInit = new LinkedList<AuthenticationPlugin<NativePacketPayload>>();
        pluginsToInit.add(new MysqlNativePasswordPlugin());
        pluginsToInit.add(new MysqlClearPasswordPlugin());
        pluginsToInit.add(new Sha256PasswordPlugin());
        pluginsToInit.add(new CachingSha2PasswordPlugin());
        pluginsToInit.add(new MysqlOldPasswordPlugin());
        pluginsToInit.add(new AuthenticationLdapSaslClientPlugin());
        final String authenticationPluginClasses = this.propertySet.getStringProperty(PropertyKey.authenticationPlugins).getValue();
        if (authenticationPluginClasses != null && !"".equals(authenticationPluginClasses)) {
            final List<String> pluginsToCreate = StringUtils.split(authenticationPluginClasses, ",", true);
            String className = null;
            try {
                for (int i = 0, s = pluginsToCreate.size(); i < s; ++i) {
                    className = pluginsToCreate.get(i);
                    pluginsToInit.add((AuthenticationPlugin<NativePacketPayload>)Class.forName(className).newInstance());
                }
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { className }), t, this.exceptionInterceptor);
            }
        }
        for (final AuthenticationPlugin<NativePacketPayload> plugin : pluginsToInit) {
            plugin.init(this.protocol);
            if (this.addAuthenticationPlugin(plugin)) {
                defaultIsFound = true;
            }
        }
        if (!defaultIsFound) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.DefaultAuthenticationPluginIsNotListed", new Object[] { this.clientDefaultAuthenticationPlugin }), this.getExceptionInterceptor());
        }
    }
    
    private boolean addAuthenticationPlugin(final AuthenticationPlugin<NativePacketPayload> plugin) {
        boolean isDefault = false;
        final String pluginClassName = plugin.getClass().getName();
        final String pluginProtocolName = plugin.getProtocolPluginName();
        final boolean disabledByClassName = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginClassName);
        final boolean disabledByMechanism = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginProtocolName);
        if (disabledByClassName || disabledByMechanism) {
            if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDisabledAuthenticationPlugin", new Object[] { disabledByClassName ? pluginClassName : pluginProtocolName }), this.getExceptionInterceptor());
            }
        }
        else {
            this.authenticationPlugins.put(pluginProtocolName, plugin);
            if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
                this.clientDefaultAuthenticationPluginName = pluginProtocolName;
                isDefault = true;
            }
        }
        return isDefault;
    }
    
    private AuthenticationPlugin<NativePacketPayload> getAuthenticationPlugin(final String pluginName) {
        AuthenticationPlugin<NativePacketPayload> plugin = this.authenticationPlugins.get(pluginName);
        if (plugin != null && !plugin.isReusable()) {
            try {
                plugin = (AuthenticationPlugin<NativePacketPayload>)plugin.getClass().newInstance();
                plugin.init(this.protocol);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { plugin.getClass().getName() }), t, this.getExceptionInterceptor());
            }
        }
        return plugin;
    }
    
    private void checkConfidentiality(final AuthenticationPlugin<?> plugin) {
        if (plugin.requiresConfidentiality() && !this.protocol.getSocketConnection().isSSLEstablished()) {
            throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.AuthenticationPluginRequiresSSL", new Object[] { plugin.getProtocolPluginName() }), this.getExceptionInterceptor());
        }
    }
    
    private void proceedHandshakeWithPluggableAuthentication(final ServerSession serverSession, final String user, final String password, final String database, final NativePacketPayload challenge) {
        if (this.authenticationPlugins == null) {
            this.loadAuthenticationPlugins();
        }
        boolean isChangeUser = challenge == null;
        String pluginName = isChangeUser ? ((this.serverDefaultAuthenticationPluginName == null) ? this.clientDefaultAuthenticationPluginName : this.serverDefaultAuthenticationPluginName) : ((!this.protocol.versionMeetsMinimum(5, 5, 10) || (this.protocol.versionMeetsMinimum(5, 6, 0) && !this.protocol.versionMeetsMinimum(5, 6, 2))) ? challenge.readString(NativeConstants.StringLengthDataType.STRING_FIXED, "ASCII", ((NativeCapabilities)serverSession.getCapabilities()).getAuthPluginDataLength()) : challenge.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII"));
        AuthenticationPlugin<NativePacketPayload> plugin = this.getAuthenticationPlugin(pluginName);
        boolean skipPassword = false;
        if (plugin == null) {
            plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
        }
        else if (pluginName.equals(Sha256PasswordPlugin.PLUGIN_NAME) && !this.protocol.getSocketConnection().isSSLEstablished() && this.propertySet.getStringProperty(PropertyKey.serverRSAPublicKeyFile).getValue() == null && !this.propertySet.getBooleanProperty(PropertyKey.allowPublicKeyRetrieval).getValue()) {
            plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
            skipPassword = !this.clientDefaultAuthenticationPluginName.equals(pluginName);
        }
        this.serverDefaultAuthenticationPluginName = plugin.getProtocolPluginName();
        this.checkConfidentiality(plugin);
        NativePacketPayload fromServer = new NativePacketPayload(StringUtils.getBytes(this.seed));
        boolean old_raw_challenge = false;
        NativePacketPayload last_sent = null;
        NativePacketPayload last_received = challenge;
        final ArrayList<NativePacketPayload> toServer = new ArrayList<NativePacketPayload>();
        int counter = 100;
        while (0 < counter--) {
            plugin.setAuthenticationParameters(user, skipPassword ? null : password);
            plugin.nextAuthenticationStep(fromServer, toServer);
            if (toServer.size() > 0) {
                if (isChangeUser) {
                    last_sent = this.createChangeUserPacket(serverSession, user, database, plugin.getProtocolPluginName(), toServer);
                    this.protocol.send(last_sent, last_sent.getPosition());
                    isChangeUser = false;
                }
                else if (last_received.isAuthMethodSwitchRequestPacket() || last_received.isAuthMoreData() || old_raw_challenge) {
                    for (final NativePacketPayload buffer : toServer) {
                        this.protocol.send(buffer, buffer.getPayloadLength());
                    }
                }
                else {
                    last_sent = this.createHandshakeResponsePacket(serverSession, user, database, plugin.getProtocolPluginName(), toServer);
                    this.protocol.send(last_sent, last_sent.getPosition());
                }
            }
            last_received = this.protocol.checkErrorMessage();
            old_raw_challenge = false;
            if (last_received.isOKPacket()) {
                final OkPacket ok = OkPacket.parse(last_received, null);
                serverSession.setStatusFlags(ok.getStatusFlags(), true);
                plugin.destroy();
                break;
            }
            if (last_received.isAuthMethodSwitchRequestPacket()) {
                skipPassword = false;
                pluginName = last_received.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII");
                if (plugin.getProtocolPluginName().equals(pluginName)) {
                    plugin.reset();
                }
                else {
                    plugin.destroy();
                    plugin = this.getAuthenticationPlugin(pluginName);
                    if (plugin == null) {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { pluginName }), this.getExceptionInterceptor());
                    }
                }
                this.checkConfidentiality(plugin);
                fromServer = new NativePacketPayload(StringUtils.getBytes(last_received.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII")));
            }
            else {
                if (!this.protocol.versionMeetsMinimum(5, 5, 16)) {
                    old_raw_challenge = true;
                    last_received.setPosition(last_received.getPosition() - 1);
                }
                fromServer = new NativePacketPayload(last_received.readBytes(NativeConstants.StringSelfDataType.STRING_EOF));
            }
        }
        if (counter == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("CommunicationsException.TooManyAuthenticationPluginNegotiations"), this.getExceptionInterceptor());
        }
        this.protocol.afterHandshake();
        if (!this.useConnectWithDb) {
            this.protocol.changeDatabase(database);
        }
    }
    
    private Map<String, String> getConnectionAttributesMap(final String attStr) {
        final Map<String, String> attMap = new HashMap<String, String>();
        if (attStr != null) {
            final String[] split;
            final String[] pairs = split = attStr.split(",");
            for (final String pair : split) {
                final int keyEnd = pair.indexOf(":");
                if (keyEnd > 0 && keyEnd + 1 < pair.length()) {
                    attMap.put(pair.substring(0, keyEnd), pair.substring(keyEnd + 1));
                }
            }
        }
        attMap.put("_client_name", "MySQL Connector/J");
        attMap.put("_client_version", "8.0.23");
        attMap.put("_runtime_vendor", Constants.JVM_VENDOR);
        attMap.put("_runtime_version", Constants.JVM_VERSION);
        attMap.put("_client_license", "GPL");
        return attMap;
    }
    
    private void appendConnectionAttributes(final NativePacketPayload buf, final String attributes, final String enc) {
        final NativePacketPayload lb = new NativePacketPayload(100);
        final Map<String, String> attMap = this.getConnectionAttributesMap(attributes);
        for (final String key : attMap.keySet()) {
            lb.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes(key, enc));
            lb.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes(attMap.get(key), enc));
        }
        buf.writeInteger(NativeConstants.IntegerDataType.INT_LENENC, lb.getPosition());
        buf.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, lb.getByteBuffer(), 0, lb.getPosition());
    }
    
    @Override
    public String getEncodingForHandshake() {
        String enc = this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (enc == null) {
            enc = "UTF-8";
        }
        return enc;
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public void changeUser(final ServerSession serverSession, final String userName, final String password, final String database) {
        this.proceedHandshakeWithPluggableAuthentication(serverSession, userName, password, database, null);
    }
    
    private NativePacketPayload createHandshakeResponsePacket(final ServerSession serverSession, final String user, final String database, final String pluginName, final ArrayList<NativePacketPayload> toServer) {
        final long clientParam = serverSession.getClientParam();
        final String enc = this.getEncodingForHandshake();
        final NativePacketPayload last_sent = new NativePacketPayload(88 + 3 * user.length() + (this.useConnectWithDb ? (3 * database.length()) : 0));
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT4, clientParam);
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT4, 16777215L);
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, AuthenticationProvider.getCharsetForHandshake(enc, serverSession.getCapabilities().getServerVersion()));
        last_sent.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, new byte[23]);
        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
        if ((clientParam & 0x200000L) != 0x0L) {
            last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, toServer.get(0).readBytes(NativeConstants.StringSelfDataType.STRING_EOF));
        }
        else {
            last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, toServer.get(0).getPayloadLength());
            last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_EOF, toServer.get(0).getByteBuffer());
        }
        if (this.useConnectWithDb) {
            last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
        }
        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(pluginName, enc));
        if ((clientParam & 0x100000L) != 0x0L) {
            this.appendConnectionAttributes(last_sent, this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue(), enc);
        }
        return last_sent;
    }
    
    private NativePacketPayload createChangeUserPacket(final ServerSession serverSession, final String user, final String database, final String pluginName, final ArrayList<NativePacketPayload> toServer) {
        final long clientParam = serverSession.getClientParam();
        final String enc = this.getEncodingForHandshake();
        final NativePacketPayload last_sent = new NativePacketPayload(88 + 3 * user.length() + (this.useConnectWithDb ? (3 * database.length()) : 1) + 1);
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 17L);
        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
        if (toServer.get(0).getPayloadLength() < 256) {
            last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, toServer.get(0).getPayloadLength());
            last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_EOF, toServer.get(0).getByteBuffer(), 0, toServer.get(0).getPayloadLength());
        }
        else {
            last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        }
        if (this.useConnectWithDb) {
            last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
        }
        else {
            last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        }
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, AuthenticationProvider.getCharsetForHandshake(enc, serverSession.getCapabilities().getServerVersion()));
        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(pluginName, enc));
        if ((clientParam & 0x100000L) != 0x0L) {
            this.appendConnectionAttributes(last_sent, this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue(), enc);
        }
        return last_sent;
    }
}
