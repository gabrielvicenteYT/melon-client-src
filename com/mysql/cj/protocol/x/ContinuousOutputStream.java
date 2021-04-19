package com.mysql.cj.protocol.x;

import java.io.*;

public class ContinuousOutputStream extends FilterOutputStream
{
    protected ContinuousOutputStream(final OutputStream out) {
        super(out);
    }
    
    @Override
    public void close() throws IOException {
        this.flush();
    }
}
