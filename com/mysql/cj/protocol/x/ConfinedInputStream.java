package com.mysql.cj.protocol.x;

import java.io.*;

public class ConfinedInputStream extends FilterInputStream
{
    private int limit;
    private int consumed;
    private boolean closed;
    
    protected ConfinedInputStream(final InputStream in) {
        this(in, 0);
    }
    
    protected ConfinedInputStream(final InputStream in, final int lim) {
        super(in);
        this.limit = 0;
        this.consumed = 0;
        this.closed = false;
        this.limit = lim;
        this.consumed = 0;
    }
    
    @Override
    public int available() throws IOException {
        this.ensureOpen();
        return this.limit - this.consumed;
    }
    
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            this.dumpLeftovers();
            this.closed = true;
        }
    }
    
    @Override
    public int read() throws IOException {
        this.ensureOpen();
        final int read = super.read();
        if (read >= 0) {
            ++this.consumed;
        }
        return read;
    }
    
    @Override
    public int read(final byte[] b) throws IOException {
        this.ensureOpen();
        return this.read(b, 0, b.length);
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        this.ensureOpen();
        if (this.consumed >= this.limit) {
            return -1;
        }
        final int toRead = Math.min(len, this.available());
        final int read = super.read(b, off, toRead);
        if (read > 0) {
            this.consumed += read;
        }
        return read;
    }
    
    public int resetLimit(final int len) {
        int remaining = 0;
        try {
            remaining = this.available();
        }
        catch (IOException ex) {}
        this.limit = len;
        this.consumed = 0;
        return remaining;
    }
    
    protected long dumpLeftovers() throws IOException {
        final long skipped = this.skip(this.available());
        this.consumed += (int)skipped;
        return skipped;
    }
    
    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }
}
