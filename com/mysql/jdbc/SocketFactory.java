package com.mysql.jdbc;

import java.net.*;
import java.io.*;
import java.util.*;

@Deprecated
public interface SocketFactory
{
    Socket afterHandshake() throws SocketException, IOException;
    
    Socket beforeHandshake() throws SocketException, IOException;
    
    Socket connect(final String p0, final int p1, final Properties p2) throws SocketException, IOException;
}
