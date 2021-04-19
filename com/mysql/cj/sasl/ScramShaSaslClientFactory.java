package com.mysql.cj.sasl;

import java.util.*;
import javax.security.sasl.*;
import com.mysql.cj.util.*;
import java.io.*;
import javax.security.auth.callback.*;

public class ScramShaSaslClientFactory implements SaslClientFactory
{
    private static final String[] SUPPORTED_MECHANISMS;
    
    @Override
    public SaslClient createSaslClient(final String[] mechanisms, final String authorizationId, final String protocol, final String serverName, final Map<String, ?> props, final CallbackHandler cbh) throws SaslException {
        for (final String mech : mechanisms) {
            if (mech.equals("MYSQLCJ-SCRAM-SHA-1")) {
                return new ScramSha1SaslClient(authorizationId, this.getUsername(mech, authorizationId, cbh), this.getPassword(mech, cbh));
            }
            if (mech.equals("MYSQLCJ-SCRAM-SHA-256")) {
                return new ScramSha256SaslClient(authorizationId, this.getUsername(mech, authorizationId, cbh), this.getPassword(mech, cbh));
            }
        }
        return null;
    }
    
    @Override
    public String[] getMechanismNames(final Map<String, ?> props) {
        return ScramShaSaslClientFactory.SUPPORTED_MECHANISMS.clone();
    }
    
    private String getUsername(final String prefix, final String authorizationId, final CallbackHandler cbh) throws SaslException {
        if (cbh == null) {
            throw new SaslException("Callback handler required to get username.");
        }
        try {
            final String prompt = prefix + " authentication id:";
            final NameCallback ncb = StringUtils.isNullOrEmpty(authorizationId) ? new NameCallback(prompt) : new NameCallback(prompt, authorizationId);
            cbh.handle(new Callback[] { ncb });
            final String userName = ncb.getName();
            return userName;
        }
        catch (IOException | UnsupportedCallbackException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw new SaslException("Cannot get username", e);
        }
    }
    
    private String getPassword(final String prefix, final CallbackHandler cbh) throws SaslException {
        if (cbh == null) {
            throw new SaslException("Callback handler required to get password.");
        }
        try {
            final String prompt = prefix + " password:";
            final PasswordCallback pcb = new PasswordCallback(prompt, false);
            cbh.handle(new Callback[] { pcb });
            final String password = new String(pcb.getPassword());
            pcb.clearPassword();
            return password;
        }
        catch (IOException | UnsupportedCallbackException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw new SaslException("Cannot get password", e);
        }
    }
    
    static {
        SUPPORTED_MECHANISMS = new String[] { "MYSQLCJ-SCRAM-SHA-1", "MYSQLCJ-SCRAM-SHA-256" };
    }
}
