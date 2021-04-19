package com.mysql.cj.protocol;

import com.mysql.cj.conf.*;
import java.io.*;

public interface SocketFactory extends SocketMetadata
{
     <T extends Closeable> T connect(final String p0, final int p1, final PropertySet p2, final int p3) throws IOException;
    
    default void beforeHandshake() throws IOException {
    }
    
     <T extends Closeable> T performTlsHandshake(final SocketConnection p0, final ServerSession p1) throws IOException;
    
    default void afterHandshake() throws IOException {
    }
}
