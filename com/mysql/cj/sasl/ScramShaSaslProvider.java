package com.mysql.cj.sasl;

import java.util.*;
import java.security.*;

public final class ScramShaSaslProvider extends Provider
{
    private static final long serialVersionUID = 866717063477857937L;
    private static final String INFO = "MySQL Connector/J SASL provider (implements client mechanisms for MYSQLCJ-SCRAM-SHA-1 and MYSQLCJ-SCRAM-SHA-256)";
    
    public ScramShaSaslProvider() {
        super("MySQLScramShaSasl", 1.0, "MySQL Connector/J SASL provider (implements client mechanisms for MYSQLCJ-SCRAM-SHA-1 and MYSQLCJ-SCRAM-SHA-256)");
        AccessController.doPrivileged(() -> {
            this.putService(new ProviderService(this, "SaslClientFactory", "MYSQLCJ-SCRAM-SHA-1", ScramShaSaslClientFactory.class.getName()));
            this.putService(new ProviderService(this, "SaslClientFactory", "MYSQLCJ-SCRAM-SHA-256", ScramShaSaslClientFactory.class.getName()));
            return null;
        });
    }
    
    private static final class ProviderService extends Service
    {
        public ProviderService(final Provider provider, final String type, final String algorithm, final String className) {
            super(provider, type, algorithm, className, null, null);
        }
        
        @Override
        public Object newInstance(final Object constructorParameter) throws NoSuchAlgorithmException {
            final String type = this.getType();
            if (constructorParameter != null) {
                throw new InvalidParameterException("constructorParameter not used with " + type + " engines");
            }
            final String algorithm = this.getAlgorithm();
            if (type.equals("SaslClientFactory")) {
                if (algorithm.equals("MYSQLCJ-SCRAM-SHA-1")) {
                    return new ScramShaSaslClientFactory();
                }
                if (algorithm.equals("MYSQLCJ-SCRAM-SHA-256")) {
                    return new ScramShaSaslClientFactory();
                }
            }
            throw new ProviderException("No implementation for " + algorithm + " " + type);
        }
    }
}
