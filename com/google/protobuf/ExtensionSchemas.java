package com.google.protobuf;

final class ExtensionSchemas
{
    private static final ExtensionSchema<?> LITE_SCHEMA;
    private static final ExtensionSchema<?> FULL_SCHEMA;
    
    private static ExtensionSchema<?> loadSchemaForFullRuntime() {
        try {
            final Class<?> clazz = Class.forName("com.google.protobuf.ExtensionSchemaFull");
            return (ExtensionSchema<?>)clazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static ExtensionSchema<?> lite() {
        return ExtensionSchemas.LITE_SCHEMA;
    }
    
    static ExtensionSchema<?> full() {
        if (ExtensionSchemas.FULL_SCHEMA == null) {
            throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
        }
        return ExtensionSchemas.FULL_SCHEMA;
    }
    
    static {
        LITE_SCHEMA = new ExtensionSchemaLite();
        FULL_SCHEMA = loadSchemaForFullRuntime();
    }
}
