package com.mysql.cj.protocol.x;

import java.io.*;
import com.mysql.cj.*;
import com.mysql.cj.util.*;
import com.mysql.cj.exceptions.*;

public class CompressorStreamsFactory
{
    private CompressionAlgorithm compressionAlgorithm;
    private InputStream compressorInputStreamInstance;
    private ReusableInputStream underlyingInputStream;
    private OutputStream compressorOutputStreamInstance;
    private ReusableOutputStream underlyingOutputStream;
    
    public CompressorStreamsFactory(final CompressionAlgorithm algorithm) {
        this.compressorInputStreamInstance = null;
        this.underlyingInputStream = null;
        this.compressorOutputStreamInstance = null;
        this.underlyingOutputStream = null;
        this.compressionAlgorithm = algorithm;
    }
    
    public CompressionMode getCompressionMode() {
        return this.compressionAlgorithm.getCompressionMode();
    }
    
    public boolean areCompressedStreamsReusable() {
        return this.getCompressionMode() == CompressionMode.STREAM;
    }
    
    public InputStream getInputStreamInstance(final InputStream in) {
        InputStream underlyingIn = in;
        if (this.areCompressedStreamsReusable()) {
            if (this.compressorInputStreamInstance != null) {
                this.underlyingInputStream.setInputStream(underlyingIn);
                return this.compressorInputStreamInstance;
            }
            this.underlyingInputStream = new ReusableInputStream(underlyingIn);
            underlyingIn = this.underlyingInputStream;
        }
        final InputStream compressionIn = (InputStream)Util.getInstance(this.compressionAlgorithm.getInputStreamClass().getName(), new Class[] { InputStream.class }, new Object[] { underlyingIn }, null, Messages.getString("Protocol.Compression.IoFactory.0", new Object[] { this.compressionAlgorithm.getInputStreamClass().getName(), this.compressionAlgorithm }));
        if (this.areCompressedStreamsReusable()) {
            this.compressorInputStreamInstance = compressionIn;
        }
        return compressionIn;
    }
    
    public OutputStream getOutputStreamInstance(final OutputStream out) {
        OutputStream underlyingOut = out;
        if (this.areCompressedStreamsReusable()) {
            if (this.compressorOutputStreamInstance != null) {
                this.underlyingOutputStream.setOutputStream(underlyingOut);
                return this.compressorOutputStreamInstance;
            }
            this.underlyingOutputStream = new ReusableOutputStream(underlyingOut);
            underlyingOut = this.underlyingOutputStream;
        }
        OutputStream compressionOut = (OutputStream)Util.getInstance(this.compressionAlgorithm.getOutputStreamClass().getName(), new Class[] { OutputStream.class }, new Object[] { underlyingOut }, null, Messages.getString("Protocol.Compression.IoFactory.1", new Object[] { this.compressionAlgorithm.getOutputStreamClass().getName(), this.compressionAlgorithm }));
        if (this.areCompressedStreamsReusable()) {
            compressionOut = new ContinuousOutputStream(compressionOut);
            this.compressorOutputStreamInstance = compressionOut;
        }
        return compressionOut;
    }
}
