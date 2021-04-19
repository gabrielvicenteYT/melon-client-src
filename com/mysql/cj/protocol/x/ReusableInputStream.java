package com.mysql.cj.protocol.x;

import java.io.*;

public class ReusableInputStream extends FilterInputStream
{
    protected ReusableInputStream(final InputStream in) {
        super(in);
    }
    
    public InputStream setInputStream(final InputStream newIn) {
        final InputStream previousIn = this.in;
        this.in = newIn;
        return previousIn;
    }
}
