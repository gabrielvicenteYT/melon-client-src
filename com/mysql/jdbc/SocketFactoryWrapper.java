package com.mysql.jdbc;

import com.mysql.cj.conf.*;
import java.io.*;
import com.mysql.cj.protocol.*;

public class SocketFactoryWrapper extends StandardSocketFactory implements SocketFactory
{
    com.mysql.jdbc.SocketFactory socketFactory;
    
    public SocketFactoryWrapper(final Object legacyFactory) {
        this.socketFactory = (com.mysql.jdbc.SocketFactory)legacyFactory;
    }
    
    @Override
    public <T extends Closeable> T connect(final String hostname, final int portNumber, final PropertySet pset, final int loginTimeout) throws IOException {
        return (T)(this.rawSocket = this.socketFactory.connect(hostname, portNumber, pset.exposeAsProperties()));
    }
    
    @Override
    public <T extends Closeable> T performTlsHandshake(final SocketConnection socketConnection, final ServerSession serverSession) throws IOException {
        return super.performTlsHandshake(socketConnection, serverSession);
    }
    
    @Override
    public void beforeHandshake() throws IOException {
        this.socketFactory.beforeHandshake();
    }
    
    @Override
    public void afterHandshake() throws IOException {
        this.socketFactory.afterHandshake();
    }
}
