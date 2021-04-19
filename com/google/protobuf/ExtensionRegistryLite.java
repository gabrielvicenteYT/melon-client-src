package com.google.protobuf;

import java.util.*;

public class ExtensionRegistryLite
{
    private static volatile boolean eagerlyParseMessageSets;
    private static boolean doFullRuntimeInheritanceCheck;
    static final String EXTENSION_CLASS_NAME = "com.google.protobuf.Extension";
    private static volatile ExtensionRegistryLite emptyRegistry;
    static final ExtensionRegistryLite EMPTY_REGISTRY_LITE;
    private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;
    
    public static boolean isEagerlyParseMessageSets() {
        return ExtensionRegistryLite.eagerlyParseMessageSets;
    }
    
    public static void setEagerlyParseMessageSets(final boolean isEagerlyParse) {
        ExtensionRegistryLite.eagerlyParseMessageSets = isEagerlyParse;
    }
    
    public static ExtensionRegistryLite newInstance() {
        return ExtensionRegistryLite.doFullRuntimeInheritanceCheck ? ExtensionRegistryFactory.create() : new ExtensionRegistryLite();
    }
    
    public static ExtensionRegistryLite getEmptyRegistry() {
        ExtensionRegistryLite result = ExtensionRegistryLite.emptyRegistry;
        if (result == null) {
            synchronized (ExtensionRegistryLite.class) {
                result = ExtensionRegistryLite.emptyRegistry;
                if (result == null) {
                    result = (ExtensionRegistryLite.emptyRegistry = (ExtensionRegistryLite.doFullRuntimeInheritanceCheck ? ExtensionRegistryFactory.createEmpty() : ExtensionRegistryLite.EMPTY_REGISTRY_LITE));
                }
            }
        }
        return result;
    }
    
    public ExtensionRegistryLite getUnmodifiable() {
        return new ExtensionRegistryLite(this);
    }
    
    public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(final ContainingType containingTypeDefaultInstance, final int fieldNumber) {
        return (GeneratedMessageLite.GeneratedExtension<ContainingType, ?>)this.extensionsByNumber.get(new ObjectIntPair(containingTypeDefaultInstance, fieldNumber));
    }
    
    public final void add(final GeneratedMessageLite.GeneratedExtension<?, ?> extension) {
        this.extensionsByNumber.put(new ObjectIntPair(extension.getContainingTypeDefaultInstance(), extension.getNumber()), extension);
    }
    
    public final void add(final ExtensionLite<?, ?> extension) {
        if (GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(extension.getClass())) {
            this.add((GeneratedMessageLite.GeneratedExtension)extension);
        }
        if (ExtensionRegistryLite.doFullRuntimeInheritanceCheck && ExtensionRegistryFactory.isFullRegistry(this)) {
            try {
                this.getClass().getMethod("add", ExtensionClassHolder.INSTANCE).invoke(this, extension);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(String.format("Could not invoke ExtensionRegistry#add for %s", extension), e);
            }
        }
    }
    
    ExtensionRegistryLite() {
        this.extensionsByNumber = new HashMap<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>>();
    }
    
    ExtensionRegistryLite(final ExtensionRegistryLite other) {
        if (other == ExtensionRegistryLite.EMPTY_REGISTRY_LITE) {
            this.extensionsByNumber = Collections.emptyMap();
        }
        else {
            this.extensionsByNumber = Collections.unmodifiableMap((Map<? extends ObjectIntPair, ? extends GeneratedMessageLite.GeneratedExtension<?, ?>>)other.extensionsByNumber);
        }
    }
    
    ExtensionRegistryLite(final boolean empty) {
        this.extensionsByNumber = Collections.emptyMap();
    }
    
    static {
        ExtensionRegistryLite.eagerlyParseMessageSets = false;
        ExtensionRegistryLite.doFullRuntimeInheritanceCheck = true;
        EMPTY_REGISTRY_LITE = new ExtensionRegistryLite(true);
    }
    
    private static class ExtensionClassHolder
    {
        static final Class<?> INSTANCE;
        
        static Class<?> resolveExtensionClass() {
            try {
                return Class.forName("com.google.protobuf.Extension");
            }
            catch (ClassNotFoundException e) {
                return null;
            }
        }
        
        static {
            INSTANCE = resolveExtensionClass();
        }
    }
    
    private static final class ObjectIntPair
    {
        private final Object object;
        private final int number;
        
        ObjectIntPair(final Object object, final int number) {
            this.object = object;
            this.number = number;
        }
        
        @Override
        public int hashCode() {
            return System.identityHashCode(this.object) * 65535 + this.number;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof ObjectIntPair)) {
                return false;
            }
            final ObjectIntPair other = (ObjectIntPair)obj;
            return this.object == other.object && this.number == other.number;
        }
    }
}
