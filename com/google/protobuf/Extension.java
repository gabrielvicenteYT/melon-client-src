package com.google.protobuf;

public abstract class Extension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type>
{
    @Override
    public abstract Message getMessageDefaultInstance();
    
    public abstract Descriptors.FieldDescriptor getDescriptor();
    
    @Override
    final boolean isLite() {
        return false;
    }
    
    protected abstract ExtensionType getExtensionType();
    
    public MessageType getMessageType() {
        return MessageType.PROTO2;
    }
    
    protected abstract Object fromReflectionType(final Object p0);
    
    protected abstract Object singularFromReflectionType(final Object p0);
    
    protected abstract Object toReflectionType(final Object p0);
    
    protected abstract Object singularToReflectionType(final Object p0);
    
    protected enum ExtensionType
    {
        IMMUTABLE, 
        MUTABLE, 
        PROTO1;
    }
    
    public enum MessageType
    {
        PROTO1, 
        PROTO2;
    }
}
