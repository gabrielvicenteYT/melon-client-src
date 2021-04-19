package com.mysql.cj.protocol.x;

import java.util.*;
import java.util.zip.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public class CompressionAlgorithm
{
    private static final Map<String, String> ALIASES;
    private String algorithmIdentifier;
    private CompressionMode compressionMode;
    private String inputStreamClassFqn;
    private Class<?> inputStreamClass;
    private String outputStreamClassFqn;
    private Class<?> outputStreamClass;
    
    public static Map<String, CompressionAlgorithm> getDefaultInstances() {
        final HashMap<String, CompressionAlgorithm> defaultInstances = new HashMap<String, CompressionAlgorithm>();
        defaultInstances.put("deflate_stream", new CompressionAlgorithm("deflate_stream", InflaterInputStream.class.getName(), SyncFlushDeflaterOutputStream.class.getName()));
        return defaultInstances;
    }
    
    public static String getNormalizedAlgorithmName(final String name) {
        return CompressionAlgorithm.ALIASES.getOrDefault(name, name);
    }
    
    public CompressionAlgorithm(final String name, final String inputStreamClassFqn, final String outputStreamClassFqn) {
        this.inputStreamClass = null;
        this.outputStreamClass = null;
        this.algorithmIdentifier = getNormalizedAlgorithmName(name);
        final String[] nameMode = this.algorithmIdentifier.split("_");
        if (nameMode.length != 2) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.Compression.4", new Object[] { name }));
        }
        try {
            final CompressionMode mode = CompressionMode.valueOf(nameMode[1].toUpperCase());
            this.compressionMode = mode;
        }
        catch (IllegalArgumentException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.Compression.5", new Object[] { nameMode[1] }));
        }
        this.inputStreamClassFqn = inputStreamClassFqn;
        this.outputStreamClassFqn = outputStreamClassFqn;
    }
    
    public String getAlgorithmIdentifier() {
        return this.algorithmIdentifier;
    }
    
    public CompressionMode getCompressionMode() {
        return this.compressionMode;
    }
    
    public Class<?> getInputStreamClass() {
        if (this.inputStreamClass == null) {
            try {
                this.inputStreamClass = Class.forName(this.inputStreamClassFqn);
            }
            catch (ClassNotFoundException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.Compression.3", new Object[] { this.inputStreamClassFqn }), e);
            }
        }
        return this.inputStreamClass;
    }
    
    public Class<?> getOutputStreamClass() {
        if (this.outputStreamClass == null) {
            try {
                this.outputStreamClass = Class.forName(this.outputStreamClassFqn);
            }
            catch (ClassNotFoundException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.Compression.3", new Object[] { this.outputStreamClassFqn }), e);
            }
        }
        return this.outputStreamClass;
    }
    
    static {
        (ALIASES = new HashMap<String, String>()).put("deflate", "deflate_stream");
        CompressionAlgorithm.ALIASES.put("lz4", "lz4_message");
        CompressionAlgorithm.ALIASES.put("zstd", "zstd_stream");
    }
}
