package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import java.util.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;

public class SessionFactory
{
    protected ConnectionUrl parseUrl(final String url) {
        final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(url, null);
        if (connUrl == null || (connUrl.getType() != ConnectionUrl.Type.XDEVAPI_SESSION && connUrl.getType() != ConnectionUrl.Type.XDEVAPI_DNS_SRV_SESSION)) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, "Initialization via URL failed for \"" + url + "\"");
        }
        return connUrl;
    }
    
    protected Session getSession(final ConnectionUrl connUrl) {
        CJException latestException = null;
        final List<HostInfo> hostsList = connUrl.getHostsList();
        for (final HostInfo hi : hostsList) {
            try {
                return new SessionImpl(hi);
            }
            catch (CJCommunicationsException e) {
                if (e.getCause() == null) {
                    throw e;
                }
                latestException = e;
                continue;
            }
            break;
        }
        if (latestException != null) {
            throw ExceptionFactory.createException(CJCommunicationsException.class, Messages.getString("Session.Create.Failover.0"), latestException);
        }
        return null;
    }
    
    public Session getSession(final String url) {
        return this.getSession(this.parseUrl(url));
    }
    
    public Session getSession(final Properties properties) {
        if (properties.containsKey(PropertyKey.xdevapiDnsSrv.getKeyName()) && (boolean)PropertyDefinitions.getPropertyDefinition(PropertyKey.xdevapiDnsSrv).parseObject(properties.getProperty(PropertyKey.xdevapiDnsSrv.getKeyName()), null)) {
            final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(ConnectionUrl.Type.XDEVAPI_DNS_SRV_SESSION.getScheme(), properties);
            return this.getSession(connUrl);
        }
        final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(ConnectionUrl.Type.XDEVAPI_SESSION.getScheme(), properties);
        return new SessionImpl(connUrl.getMainHost());
    }
}
