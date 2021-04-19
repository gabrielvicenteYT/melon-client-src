package com.google.protobuf;

import java.util.logging.*;
import java.util.*;

public class TypeRegistry
{
    private static final Logger logger;
    private final Map<String, Descriptors.Descriptor> types;
    
    public static TypeRegistry getEmptyTypeRegistry() {
        return EmptyTypeRegistryHolder.EMPTY;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public Descriptors.Descriptor find(final String name) {
        return this.types.get(name);
    }
    
    public final Descriptors.Descriptor getDescriptorForTypeUrl(final String typeUrl) throws InvalidProtocolBufferException {
        return this.find(getTypeName(typeUrl));
    }
    
    TypeRegistry(final Map<String, Descriptors.Descriptor> types) {
        this.types = types;
    }
    
    private static String getTypeName(final String typeUrl) throws InvalidProtocolBufferException {
        final String[] parts = typeUrl.split("/");
        if (parts.length == 1) {
            throw new InvalidProtocolBufferException("Invalid type url found: " + typeUrl);
        }
        return parts[parts.length - 1];
    }
    
    static {
        logger = Logger.getLogger(TypeRegistry.class.getName());
    }
    
    private static class EmptyTypeRegistryHolder
    {
        private static final TypeRegistry EMPTY;
        
        static {
            EMPTY = new TypeRegistry(Collections.emptyMap());
        }
    }
    
    public static final class Builder
    {
        private final Set<String> files;
        private Map<String, Descriptors.Descriptor> types;
        
        private Builder() {
            this.files = new HashSet<String>();
            this.types = new HashMap<String, Descriptors.Descriptor>();
        }
        
        public Builder add(final Descriptors.Descriptor messageType) {
            if (this.types == null) {
                throw new IllegalStateException("A TypeRegistry.Builder can only be used once.");
            }
            this.addFile(messageType.getFile());
            return this;
        }
        
        public Builder add(final Iterable<Descriptors.Descriptor> messageTypes) {
            if (this.types == null) {
                throw new IllegalStateException("A TypeRegistry.Builder can only be used once.");
            }
            for (final Descriptors.Descriptor type : messageTypes) {
                this.addFile(type.getFile());
            }
            return this;
        }
        
        public TypeRegistry build() {
            final TypeRegistry result = new TypeRegistry(this.types);
            this.types = null;
            return result;
        }
        
        private void addFile(final Descriptors.FileDescriptor file) {
            if (!this.files.add(file.getFullName())) {
                return;
            }
            for (final Descriptors.FileDescriptor dependency : file.getDependencies()) {
                this.addFile(dependency);
            }
            for (final Descriptors.Descriptor message : file.getMessageTypes()) {
                this.addMessage(message);
            }
        }
        
        private void addMessage(final Descriptors.Descriptor message) {
            for (final Descriptors.Descriptor nestedType : message.getNestedTypes()) {
                this.addMessage(nestedType);
            }
            if (this.types.containsKey(message.getFullName())) {
                TypeRegistry.logger.warning("Type " + message.getFullName() + " is added multiple times.");
                return;
            }
            this.types.put(message.getFullName(), message);
        }
    }
}
