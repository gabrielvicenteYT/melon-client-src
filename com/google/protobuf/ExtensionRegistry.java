package com.google.protobuf;

import java.util.*;

public class ExtensionRegistry extends ExtensionRegistryLite
{
    private final Map<String, ExtensionInfo> immutableExtensionsByName;
    private final Map<String, ExtensionInfo> mutableExtensionsByName;
    private final Map<DescriptorIntPair, ExtensionInfo> immutableExtensionsByNumber;
    private final Map<DescriptorIntPair, ExtensionInfo> mutableExtensionsByNumber;
    static final ExtensionRegistry EMPTY_REGISTRY;
    
    public static ExtensionRegistry newInstance() {
        return new ExtensionRegistry();
    }
    
    public static ExtensionRegistry getEmptyRegistry() {
        return ExtensionRegistry.EMPTY_REGISTRY;
    }
    
    @Override
    public ExtensionRegistry getUnmodifiable() {
        return new ExtensionRegistry(this);
    }
    
    @Deprecated
    public ExtensionInfo findExtensionByName(final String fullName) {
        return this.findImmutableExtensionByName(fullName);
    }
    
    public ExtensionInfo findImmutableExtensionByName(final String fullName) {
        return this.immutableExtensionsByName.get(fullName);
    }
    
    public ExtensionInfo findMutableExtensionByName(final String fullName) {
        return this.mutableExtensionsByName.get(fullName);
    }
    
    @Deprecated
    public ExtensionInfo findExtensionByNumber(final Descriptors.Descriptor containingType, final int fieldNumber) {
        return this.findImmutableExtensionByNumber(containingType, fieldNumber);
    }
    
    public ExtensionInfo findImmutableExtensionByNumber(final Descriptors.Descriptor containingType, final int fieldNumber) {
        return this.immutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
    }
    
    public ExtensionInfo findMutableExtensionByNumber(final Descriptors.Descriptor containingType, final int fieldNumber) {
        return this.mutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
    }
    
    public Set<ExtensionInfo> getAllMutableExtensionsByExtendedType(final String fullName) {
        final HashSet<ExtensionInfo> extensions = new HashSet<ExtensionInfo>();
        for (final DescriptorIntPair pair : this.mutableExtensionsByNumber.keySet()) {
            if (pair.descriptor.getFullName().equals(fullName)) {
                extensions.add(this.mutableExtensionsByNumber.get(pair));
            }
        }
        return extensions;
    }
    
    public Set<ExtensionInfo> getAllImmutableExtensionsByExtendedType(final String fullName) {
        final HashSet<ExtensionInfo> extensions = new HashSet<ExtensionInfo>();
        for (final DescriptorIntPair pair : this.immutableExtensionsByNumber.keySet()) {
            if (pair.descriptor.getFullName().equals(fullName)) {
                extensions.add(this.immutableExtensionsByNumber.get(pair));
            }
        }
        return extensions;
    }
    
    public void add(final Extension<?, ?> extension) {
        if (extension.getExtensionType() != Extension.ExtensionType.IMMUTABLE && extension.getExtensionType() != Extension.ExtensionType.MUTABLE) {
            return;
        }
        this.add(newExtensionInfo(extension), extension.getExtensionType());
    }
    
    public void add(final GeneratedMessage.GeneratedExtension<?, ?> extension) {
        this.add((Extension<?, ?>)extension);
    }
    
    static ExtensionInfo newExtensionInfo(final Extension<?, ?> extension) {
        if (extension.getDescriptor().getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
            return new ExtensionInfo(extension.getDescriptor(), (Message)null);
        }
        if (extension.getMessageDefaultInstance() == null) {
            throw new IllegalStateException("Registered message-type extension had null default instance: " + extension.getDescriptor().getFullName());
        }
        return new ExtensionInfo(extension.getDescriptor(), extension.getMessageDefaultInstance());
    }
    
    public void add(final Descriptors.FieldDescriptor type) {
        if (type.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() must be provided a default instance when adding an embedded message extension.");
        }
        final ExtensionInfo info = new ExtensionInfo(type, (Message)null);
        this.add(info, Extension.ExtensionType.IMMUTABLE);
        this.add(info, Extension.ExtensionType.MUTABLE);
    }
    
    public void add(final Descriptors.FieldDescriptor type, final Message defaultInstance) {
        if (type.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() provided a default instance for a non-message extension.");
        }
        this.add(new ExtensionInfo(type, defaultInstance), Extension.ExtensionType.IMMUTABLE);
    }
    
    private ExtensionRegistry() {
        this.immutableExtensionsByName = new HashMap<String, ExtensionInfo>();
        this.mutableExtensionsByName = new HashMap<String, ExtensionInfo>();
        this.immutableExtensionsByNumber = new HashMap<DescriptorIntPair, ExtensionInfo>();
        this.mutableExtensionsByNumber = new HashMap<DescriptorIntPair, ExtensionInfo>();
    }
    
    private ExtensionRegistry(final ExtensionRegistry other) {
        super(other);
        this.immutableExtensionsByName = Collections.unmodifiableMap((Map<? extends String, ? extends ExtensionInfo>)other.immutableExtensionsByName);
        this.mutableExtensionsByName = Collections.unmodifiableMap((Map<? extends String, ? extends ExtensionInfo>)other.mutableExtensionsByName);
        this.immutableExtensionsByNumber = Collections.unmodifiableMap((Map<? extends DescriptorIntPair, ? extends ExtensionInfo>)other.immutableExtensionsByNumber);
        this.mutableExtensionsByNumber = Collections.unmodifiableMap((Map<? extends DescriptorIntPair, ? extends ExtensionInfo>)other.mutableExtensionsByNumber);
    }
    
    ExtensionRegistry(final boolean empty) {
        super(ExtensionRegistry.EMPTY_REGISTRY_LITE);
        this.immutableExtensionsByName = Collections.emptyMap();
        this.mutableExtensionsByName = Collections.emptyMap();
        this.immutableExtensionsByNumber = Collections.emptyMap();
        this.mutableExtensionsByNumber = Collections.emptyMap();
    }
    
    private void add(final ExtensionInfo extension, final Extension.ExtensionType extensionType) {
        if (!extension.descriptor.isExtension()) {
            throw new IllegalArgumentException("ExtensionRegistry.add() was given a FieldDescriptor for a regular (non-extension) field.");
        }
        Map<String, ExtensionInfo> extensionsByName = null;
        Map<DescriptorIntPair, ExtensionInfo> extensionsByNumber = null;
        switch (extensionType) {
            case IMMUTABLE: {
                extensionsByName = this.immutableExtensionsByName;
                extensionsByNumber = this.immutableExtensionsByNumber;
                break;
            }
            case MUTABLE: {
                extensionsByName = this.mutableExtensionsByName;
                extensionsByNumber = this.mutableExtensionsByNumber;
                break;
            }
            default: {
                return;
            }
        }
        extensionsByName.put(extension.descriptor.getFullName(), extension);
        extensionsByNumber.put(new DescriptorIntPair(extension.descriptor.getContainingType(), extension.descriptor.getNumber()), extension);
        final Descriptors.FieldDescriptor field = extension.descriptor;
        if (field.getContainingType().getOptions().getMessageSetWireFormat() && field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && field.isOptional() && field.getExtensionScope() == field.getMessageType()) {
            extensionsByName.put(field.getMessageType().getFullName(), extension);
        }
    }
    
    static {
        EMPTY_REGISTRY = new ExtensionRegistry(true);
    }
    
    public static final class ExtensionInfo
    {
        public final Descriptors.FieldDescriptor descriptor;
        public final Message defaultInstance;
        
        private ExtensionInfo(final Descriptors.FieldDescriptor descriptor) {
            this.descriptor = descriptor;
            this.defaultInstance = null;
        }
        
        private ExtensionInfo(final Descriptors.FieldDescriptor descriptor, final Message defaultInstance) {
            this.descriptor = descriptor;
            this.defaultInstance = defaultInstance;
        }
    }
    
    private static final class DescriptorIntPair
    {
        private final Descriptors.Descriptor descriptor;
        private final int number;
        
        DescriptorIntPair(final Descriptors.Descriptor descriptor, final int number) {
            this.descriptor = descriptor;
            this.number = number;
        }
        
        @Override
        public int hashCode() {
            return this.descriptor.hashCode() * 65535 + this.number;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof DescriptorIntPair)) {
                return false;
            }
            final DescriptorIntPair other = (DescriptorIntPair)obj;
            return this.descriptor == other.descriptor && this.number == other.number;
        }
    }
}
