package com.mysql.cj.protocol.a.authentication;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import com.mysql.cj.protocol.a.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.util.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import javax.security.sasl.*;
import javax.security.auth.login.*;
import com.mysql.cj.protocol.*;
import java.io.*;
import com.mysql.cj.sasl.*;
import java.security.*;

public class AuthenticationLdapSaslClientPlugin implements AuthenticationPlugin<NativePacketPayload>
{
    public static String PLUGIN_NAME;
    private static final String LOGIN_CONFIG_ENTRY = "MySQLConnectorJ";
    private static final String LDAP_SERVICE_NAME = "ldap";
    private Protocol<?> protocol;
    private String user;
    private String password;
    private AuthenticationMechanisms authMech;
    private SaslClient saslClient;
    private Subject subject;
    private boolean firstPass;
    private CallbackHandler credentialsCallbackHandler;
    
    public AuthenticationLdapSaslClientPlugin() {
        this.protocol = null;
        this.subject = null;
        this.firstPass = true;
        int length;
        int i = 0;
        Callback cb;
        this.credentialsCallbackHandler = (cbs -> {
            for (length = cbs.length; i < length; ++i) {
                cb = cbs[i];
                if (NameCallback.class.isAssignableFrom(cb.getClass())) {
                    ((NameCallback)cb).setName(this.user);
                }
                else if (PasswordCallback.class.isAssignableFrom(cb.getClass())) {
                    ((PasswordCallback)cb).setPassword(this.password.toCharArray());
                }
                else {
                    throw new UnsupportedCallbackException(cb, cb.getClass().getName());
                }
            }
        });
    }
    
    @Override
    public void init(final Protocol<NativePacketPayload> prot) {
        this.protocol = prot;
    }
    
    @Override
    public void reset() {
        if (this.saslClient != null) {
            try {
                this.saslClient.dispose();
            }
            catch (SaslException ex) {}
        }
        this.user = null;
        this.password = null;
        this.authMech = null;
        this.saslClient = null;
        this.subject = null;
    }
    
    @Override
    public void destroy() {
        this.protocol = null;
        this.reset();
    }
    
    @Override
    public String getProtocolPluginName() {
        return AuthenticationLdapSaslClientPlugin.PLUGIN_NAME;
    }
    
    @Override
    public boolean requiresConfidentiality() {
        return false;
    }
    
    @Override
    public boolean isReusable() {
        return false;
    }
    
    @Override
    public void setAuthenticationParameters(final String user, final String password) {
        this.user = user;
        this.password = password;
    }
    
    @Override
    public boolean nextAuthenticationStep(final NativePacketPayload fromServer, final List<NativePacketPayload> toServer) {
        toServer.clear();
        if (this.saslClient == null) {
            final String authMechId = fromServer.readString(NativeConstants.StringSelfDataType.STRING_EOF, "ASCII");
            try {
                this.authMech = AuthenticationMechanisms.fromValue(authMechId);
            }
            catch (CJException e) {
                if (this.firstPass) {
                    this.firstPass = false;
                    toServer.add(new NativePacketPayload(new byte[0]));
                    return true;
                }
                throw e;
            }
            this.firstPass = false;
            try {
                switch (this.authMech) {
                    case GSSAPI: {
                        String ldapServerHostname = this.protocol.getPropertySet().getStringProperty(PropertyKey.ldapServerHostname).getValue();
                        if (StringUtils.isNullOrEmpty(ldapServerHostname)) {
                            final String krb5Kdc = System.getProperty("java.security.krb5.kdc");
                            if (!StringUtils.isNullOrEmpty(krb5Kdc)) {
                                ldapServerHostname = krb5Kdc;
                                final int dotIndex = krb5Kdc.indexOf(46);
                                if (dotIndex > 0) {
                                    ldapServerHostname = krb5Kdc.substring(0, dotIndex).toLowerCase(Locale.ENGLISH);
                                }
                            }
                        }
                        if (StringUtils.isNullOrEmpty(ldapServerHostname)) {
                            throw ExceptionFactory.createException(Messages.getString("AuthenticationLdapSaslClientPlugin.MissingLdapServerHostname"));
                        }
                        final String loginConfigFile = System.getProperty("java.security.auth.login.config");
                        Configuration loginConfig = null;
                        if (StringUtils.isNullOrEmpty(loginConfigFile)) {
                            final String localUser = this.user;
                            final boolean debug = Boolean.getBoolean("sun.security.jgss.debug");
                            loginConfig = new Configuration() {
                                @Override
                                public AppConfigurationEntry[] getAppConfigurationEntry(final String name) {
                                    final Map<String, String> options = new HashMap<String, String>();
                                    options.put("useTicketCache", "true");
                                    options.put("renewTGT", "false");
                                    options.put("principal", localUser);
                                    options.put("debug", Boolean.toString(debug));
                                    return new AppConfigurationEntry[] { new AppConfigurationEntry("com.sun.security.auth.module.Krb5LoginModule", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options) };
                                }
                            };
                        }
                        final LoginContext loginContext = new LoginContext("MySQLConnectorJ", null, this.credentialsCallbackHandler, loginConfig);
                        loginContext.login();
                        this.subject = loginContext.getSubject();
                        try {
                            final String localLdapServerHostname = ldapServerHostname;
                            this.saslClient = Subject.doAs(this.subject, () -> Sasl.createSaslClient(new String[] { this.authMech.getSaslServiceName() }, null, "ldap", localLdapServerHostname, null, null));
                            break;
                        }
                        catch (PrivilegedActionException e2) {
                            throw (SaslException)e2.getException();
                        }
                    }
                    case SCRAM_SHA_1:
                    case SCRAM_SHA_256: {
                        this.saslClient = Sasl.createSaslClient(new String[] { this.authMech.getSaslServiceName() }, null, null, null, null, this.credentialsCallbackHandler);
                        break;
                    }
                }
            }
            catch (LoginException | SaslException ex2) {
                final Exception ex;
                final Exception e3 = ex;
                throw ExceptionFactory.createException(Messages.getString("AuthenticationLdapSaslClientPlugin.FailCreateSaslClient", new Object[] { this.authMech.getMechName() }), e3);
            }
            if (this.saslClient == null) {
                throw ExceptionFactory.createException(Messages.getString("AuthenticationLdapSaslClientPlugin.FailCreateSaslClient", new Object[] { this.authMech.getMechName() }));
            }
        }
        if (!this.saslClient.isComplete()) {
            try {
                final byte[] response;
                NativePacketPayload bresp;
                Subject.doAs(this.subject, () -> {
                    response = this.saslClient.evaluateChallenge(fromServer.readBytes(NativeConstants.StringSelfDataType.STRING_EOF));
                    if (response != null) {
                        bresp = new NativePacketPayload(response);
                        bresp.setPosition(0);
                        toServer.add(bresp);
                    }
                    return null;
                });
            }
            catch (PrivilegedActionException e4) {
                throw ExceptionFactory.createException(Messages.getString("AuthenticationLdapSaslClientPlugin.ErrProcessingAuthIter", new Object[] { this.authMech.getMechName() }), e4.getException());
            }
        }
        return true;
    }
    
    static {
        Security.addProvider(new ScramShaSaslProvider());
        AuthenticationLdapSaslClientPlugin.PLUGIN_NAME = "authentication_ldap_sasl_client";
    }
    
    private enum AuthenticationMechanisms
    {
        SCRAM_SHA_1("SCRAM-SHA-1", "MYSQLCJ-SCRAM-SHA-1"), 
        SCRAM_SHA_256("SCRAM-SHA-256", "MYSQLCJ-SCRAM-SHA-256"), 
        GSSAPI("GSSAPI", "GSSAPI");
        
        private String mechName;
        private String saslServiceName;
        
        private AuthenticationMechanisms(final String mechName, final String serviceName) {
            this.mechName = mechName;
            this.saslServiceName = serviceName;
        }
        
        static AuthenticationMechanisms fromValue(final String mechName) {
            for (final AuthenticationMechanisms am : values()) {
                if (am.mechName.equalsIgnoreCase(mechName)) {
                    return am;
                }
            }
            throw ExceptionFactory.createException(Messages.getString("AuthenticationLdapSaslClientPlugin.UnsupportedAuthMech", new String[] { mechName }));
        }
        
        String getMechName() {
            return this.mechName;
        }
        
        String getSaslServiceName() {
            return this.saslServiceName;
        }
    }
}
