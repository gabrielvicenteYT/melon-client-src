package com.mysql.cj.protocol;

import java.net.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.*;
import java.util.concurrent.*;
import java.io.*;

public class NamedPipeSocketFactory implements SocketFactory
{
    private static final int DEFAULT_TIMEOUT = 100;
    private Socket namedPipeSocket;
    
    @Override
    public <T extends Closeable> T performTlsHandshake(final SocketConnection socketConnection, final ServerSession serverSession) throws IOException {
        return (T)this.namedPipeSocket;
    }
    
    @Override
    public <T extends Closeable> T connect(final String host, final int portNumber, final PropertySet props, final int loginTimeout) throws IOException {
        String namedPipePath = null;
        final RuntimeProperty<String> path = props.getStringProperty(PropertyKey.PATH);
        if (path != null) {
            namedPipePath = path.getValue();
        }
        if (namedPipePath == null) {
            namedPipePath = "\\\\.\\pipe\\MySQL";
        }
        else if (namedPipePath.length() == 0) {
            throw new SocketException(Messages.getString("NamedPipeSocketFactory.2") + PropertyKey.PATH.getCcAlias() + Messages.getString("NamedPipeSocketFactory.3"));
        }
        final int connectTimeout = props.getIntegerProperty(PropertyKey.connectTimeout.getKeyName()).getValue();
        final int timeout = (connectTimeout > 0 && loginTimeout > 0) ? Math.min(connectTimeout, loginTimeout) : (connectTimeout + loginTimeout);
        return (T)(this.namedPipeSocket = new NamedPipeSocket(namedPipePath, timeout));
    }
    
    @Override
    public boolean isLocallyConnected(final Session sess) {
        return true;
    }
    
    class NamedPipeSocket extends Socket
    {
        private boolean isClosed;
        private RandomAccessFile namedPipeFile;
        
        NamedPipeSocket(final String filePath, final int timeout) throws IOException {
            this.isClosed = false;
            if (filePath == null || filePath.length() == 0) {
                throw new IOException(Messages.getString("NamedPipeSocketFactory.4"));
            }
            final int timeoutCountdown = (timeout == 0) ? 100 : timeout;
            final long startTime = System.currentTimeMillis();
            try {
                this.namedPipeFile = new RandomAccessFile(filePath, "rw");
            }
            catch (FileNotFoundException e) {
                if (timeout == 0) {
                    throw new IOException("Named pipe busy error (ERROR_PIPE_BUSY).\nConsider setting a value for 'connectTimeout' or DriverManager.setLoginTimeout(int) to repeatedly try opening the named pipe before failing.", e);
                }
                if (System.currentTimeMillis() - startTime > timeoutCountdown) {
                    throw e;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(10L);
                }
                catch (InterruptedException e2) {
                    throw new IOException(e2);
                }
            }
        }
        
        @Override
        public synchronized void close() throws IOException {
            this.namedPipeFile.close();
            this.isClosed = true;
        }
        
        @Override
        public InputStream getInputStream() throws IOException {
            return new RandomAccessFileInputStream(this.namedPipeFile);
        }
        
        @Override
        public OutputStream getOutputStream() throws IOException {
            return new RandomAccessFileOutputStream(this.namedPipeFile);
        }
        
        @Override
        public boolean isClosed() {
            return this.isClosed;
        }
        
        @Override
        public void shutdownInput() throws IOException {
        }
    }
    
    class RandomAccessFileInputStream extends InputStream
    {
        RandomAccessFile raFile;
        
        RandomAccessFileInputStream(final RandomAccessFile file) {
            this.raFile = file;
        }
        
        @Override
        public int available() throws IOException {
            return -1;
        }
        
        @Override
        public void close() throws IOException {
            this.raFile.close();
        }
        
        @Override
        public int read() throws IOException {
            return this.raFile.read();
        }
        
        @Override
        public int read(final byte[] b) throws IOException {
            return this.raFile.read(b);
        }
        
        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            return this.raFile.read(b, off, len);
        }
    }
    
    class RandomAccessFileOutputStream extends OutputStream
    {
        RandomAccessFile raFile;
        
        RandomAccessFileOutputStream(final RandomAccessFile file) {
            this.raFile = file;
        }
        
        @Override
        public void close() throws IOException {
            this.raFile.close();
        }
        
        @Override
        public void write(final byte[] b) throws IOException {
            this.raFile.write(b);
        }
        
        @Override
        public void write(final byte[] b, final int off, final int len) throws IOException {
            this.raFile.write(b, off, len);
        }
        
        @Override
        public void write(final int b) throws IOException {
        }
    }
}
