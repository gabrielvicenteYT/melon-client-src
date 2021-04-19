package com.google.protobuf;

public enum JavaType
{
    VOID((Class<?>)Void.class, (Class<?>)Void.class, (Object)null), 
    INT((Class<?>)Integer.TYPE, (Class<?>)Integer.class, (Object)0), 
    LONG((Class<?>)Long.TYPE, (Class<?>)Long.class, (Object)0L), 
    FLOAT((Class<?>)Float.TYPE, (Class<?>)Float.class, (Object)0.0f), 
    DOUBLE((Class<?>)Double.TYPE, (Class<?>)Double.class, (Object)0.0), 
    BOOLEAN((Class<?>)Boolean.TYPE, (Class<?>)Boolean.class, (Object)false), 
    STRING((Class<?>)String.class, (Class<?>)String.class, (Object)""), 
    BYTE_STRING((Class<?>)ByteString.class, (Class<?>)ByteString.class, (Object)ByteString.EMPTY), 
    ENUM((Class<?>)Integer.TYPE, (Class<?>)Integer.class, (Object)null), 
    MESSAGE((Class<?>)Object.class, (Class<?>)Object.class, (Object)null);
    
    private final Class<?> type;
    private final Class<?> boxedType;
    private final Object defaultDefault;
    
    private JavaType(final Class<?> type, final Class<?> boxedType, final Object defaultDefault) {
        this.type = type;
        this.boxedType = boxedType;
        this.defaultDefault = defaultDefault;
    }
    
    public Object getDefaultDefault() {
        return this.defaultDefault;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public Class<?> getBoxedType() {
        return this.boxedType;
    }
    
    public boolean isValidType(final Class<?> t) {
        return this.type.isAssignableFrom(t);
    }
}
