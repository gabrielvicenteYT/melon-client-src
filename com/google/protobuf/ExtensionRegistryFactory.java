package com.google.protobuf;

final class ExtensionRegistryFactory
{
    static final String FULL_REGISTRY_CLASS_NAME = "com.google.protobuf.ExtensionRegistry";
    static final Class<?> EXTENSION_REGISTRY_CLASS;
    
    static Class<?> reflectExtensionRegistry() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    public static ExtensionRegistryLite create() {
        final ExtensionRegistryLite result = invokeSubclassFactory("newInstance");
        return (result != null) ? result : new ExtensionRegistryLite();
    }
    
    public static ExtensionRegistryLite createEmpty() {
        final ExtensionRegistryLite result = invokeSubclassFactory("getEmptyRegistry");
        return (result != null) ? result : ExtensionRegistryLite.EMPTY_REGISTRY_LITE;
    }
    
    static boolean isFullRegistry(final ExtensionRegistryLite registry) {
        return ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS != null && ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS.isAssignableFrom(registry.getClass());
    }
    
    private static final ExtensionRegistryLite invokeSubclassFactory(final String methodName) {
        if (ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS == null) {
            return null;
        }
        try {
            return (ExtensionRegistryLite)ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS.getDeclaredMethod(methodName, (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        EXTENSION_REGISTRY_CLASS = reflectExtensionRegistry();
    }
}
