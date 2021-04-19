package com.mysql.cj.protocol.x;

import java.io.*;

public class ReusableOutputStream extends FilterOutputStream
{
    protected ReusableOutputStream(final OutputStream out) {
        super(out);
    }
    
    public OutputStream setOutputStream(final OutputStream newOut) {
        final OutputStream previousOut = this.out;
        this.out = newOut;
        return previousOut;
    }
}
