package com.google.protobuf;

final class MapFieldSchemas
{
    private static final MapFieldSchema FULL_SCHEMA;
    private static final MapFieldSchema LITE_SCHEMA;
    
    static MapFieldSchema full() {
        return MapFieldSchemas.FULL_SCHEMA;
    }
    
    static MapFieldSchema lite() {
        return MapFieldSchemas.LITE_SCHEMA;
    }
    
    private static MapFieldSchema loadSchemaForFullRuntime() {
        try {
            final Class<?> clazz = Class.forName("com.google.protobuf.MapFieldSchemaFull");
            return (MapFieldSchema)clazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        FULL_SCHEMA = loadSchemaForFullRuntime();
        LITE_SCHEMA = new MapFieldSchemaLite();
    }
}
