package com.google.protobuf;

import java.io.*;
import java.util.concurrent.*;
import java.util.*;

final class Protobuf
{
    private static final Protobuf INSTANCE;
    private final SchemaFactory schemaFactory;
    private final ConcurrentMap<Class<?>, Schema<?>> schemaCache;
    
    public static Protobuf getInstance() {
        return Protobuf.INSTANCE;
    }
    
    public <T> void writeTo(final T message, final Writer writer) throws IOException {
        this.schemaFor(message).writeTo(message, writer);
    }
    
    public <T> void mergeFrom(final T message, final Reader reader) throws IOException {
        this.mergeFrom(message, reader, ExtensionRegistryLite.getEmptyRegistry());
    }
    
    public <T> void mergeFrom(final T message, final Reader reader, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.schemaFor(message).mergeFrom(message, reader, extensionRegistry);
    }
    
    public <T> void makeImmutable(final T message) {
        this.schemaFor(message).makeImmutable(message);
    }
    
    public <T> boolean isInitialized(final T message) {
        return this.schemaFor(message).isInitialized(message);
    }
    
    public <T> Schema<T> schemaFor(final Class<T> messageType) {
        Internal.checkNotNull(messageType, "messageType");
        Schema<T> schema = (Schema<T>)this.schemaCache.get(messageType);
        if (schema == null) {
            schema = this.schemaFactory.createSchema(messageType);
            final Schema<T> previous = (Schema<T>)this.registerSchema(messageType, schema);
            if (previous != null) {
                schema = previous;
            }
        }
        return schema;
    }
    
    public <T> Schema<T> schemaFor(final T message) {
        return this.schemaFor((Class<T>)message.getClass());
    }
    
    public Schema<?> registerSchema(final Class<?> messageType, final Schema<?> schema) {
        Internal.checkNotNull(messageType, "messageType");
        Internal.checkNotNull(schema, "schema");
        return this.schemaCache.putIfAbsent(messageType, schema);
    }
    
    public Schema<?> registerSchemaOverride(final Class<?> messageType, final Schema<?> schema) {
        Internal.checkNotNull(messageType, "messageType");
        Internal.checkNotNull(schema, "schema");
        return this.schemaCache.put(messageType, schema);
    }
    
    private Protobuf() {
        this.schemaCache = new ConcurrentHashMap<Class<?>, Schema<?>>();
        this.schemaFactory = new ManifestSchemaFactory();
    }
    
    int getTotalSchemaSize() {
        int result = 0;
        for (final Schema<?> schema : this.schemaCache.values()) {
            if (schema instanceof MessageSchema) {
                result += ((MessageSchema)schema).getSchemaSize();
            }
        }
        return result;
    }
    
    static {
        INSTANCE = new Protobuf();
    }
}
