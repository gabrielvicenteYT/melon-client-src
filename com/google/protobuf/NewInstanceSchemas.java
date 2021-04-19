package com.google.protobuf;

final class NewInstanceSchemas
{
    private static final NewInstanceSchema FULL_SCHEMA;
    private static final NewInstanceSchema LITE_SCHEMA;
    
    static NewInstanceSchema full() {
        return NewInstanceSchemas.FULL_SCHEMA;
    }
    
    static NewInstanceSchema lite() {
        return NewInstanceSchemas.LITE_SCHEMA;
    }
    
    private static NewInstanceSchema loadSchemaForFullRuntime() {
        try {
            final Class<?> clazz = Class.forName("com.google.protobuf.NewInstanceSchemaFull");
            return (NewInstanceSchema)clazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        FULL_SCHEMA = loadSchemaForFullRuntime();
        LITE_SCHEMA = new NewInstanceSchemaLite();
    }
}
