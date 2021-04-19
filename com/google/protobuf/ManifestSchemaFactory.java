package com.google.protobuf;

final class ManifestSchemaFactory implements SchemaFactory
{
    private final MessageInfoFactory messageInfoFactory;
    private static final MessageInfoFactory EMPTY_FACTORY;
    
    public ManifestSchemaFactory() {
        this(getDefaultMessageInfoFactory());
    }
    
    private ManifestSchemaFactory(final MessageInfoFactory messageInfoFactory) {
        this.messageInfoFactory = Internal.checkNotNull(messageInfoFactory, "messageInfoFactory");
    }
    
    @Override
    public <T> Schema<T> createSchema(final Class<T> messageType) {
        SchemaUtil.requireGeneratedMessage(messageType);
        final MessageInfo messageInfo = this.messageInfoFactory.messageInfoFor(messageType);
        if (!messageInfo.isMessageSetWireFormat()) {
            return newSchema(messageType, messageInfo);
        }
        if (GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            return (Schema<T>)MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), messageInfo.getDefaultInstance());
        }
        return (Schema<T>)MessageSetSchema.newSchema(SchemaUtil.proto2UnknownFieldSetSchema(), ExtensionSchemas.full(), messageInfo.getDefaultInstance());
    }
    
    private static <T> Schema<T> newSchema(final Class<T> messageType, final MessageInfo messageInfo) {
        if (GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            return isProto2(messageInfo) ? MessageSchema.newSchema(messageType, messageInfo, NewInstanceSchemas.lite(), ListFieldSchema.lite(), SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), MapFieldSchemas.lite()) : MessageSchema.newSchema(messageType, messageInfo, NewInstanceSchemas.lite(), ListFieldSchema.lite(), SchemaUtil.unknownFieldSetLiteSchema(), null, MapFieldSchemas.lite());
        }
        return isProto2(messageInfo) ? MessageSchema.newSchema(messageType, messageInfo, NewInstanceSchemas.full(), ListFieldSchema.full(), SchemaUtil.proto2UnknownFieldSetSchema(), ExtensionSchemas.full(), MapFieldSchemas.full()) : MessageSchema.newSchema(messageType, messageInfo, NewInstanceSchemas.full(), ListFieldSchema.full(), SchemaUtil.proto3UnknownFieldSetSchema(), null, MapFieldSchemas.full());
    }
    
    private static boolean isProto2(final MessageInfo messageInfo) {
        return messageInfo.getSyntax() == ProtoSyntax.PROTO2;
    }
    
    private static MessageInfoFactory getDefaultMessageInfoFactory() {
        return new CompositeMessageInfoFactory(new MessageInfoFactory[] { GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory() });
    }
    
    private static MessageInfoFactory getDescriptorMessageInfoFactory() {
        try {
            final Class<?> clazz = Class.forName("com.google.protobuf.DescriptorMessageInfoFactory");
            return (MessageInfoFactory)clazz.getDeclaredMethod("getInstance", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception e) {
            return ManifestSchemaFactory.EMPTY_FACTORY;
        }
    }
    
    static {
        EMPTY_FACTORY = new MessageInfoFactory() {
            @Override
            public boolean isSupported(final Class<?> clazz) {
                return false;
            }
            
            @Override
            public MessageInfo messageInfoFor(final Class<?> clazz) {
                throw new IllegalStateException("This should never be called.");
            }
        };
    }
    
    private static class CompositeMessageInfoFactory implements MessageInfoFactory
    {
        private MessageInfoFactory[] factories;
        
        CompositeMessageInfoFactory(final MessageInfoFactory... factories) {
            this.factories = factories;
        }
        
        @Override
        public boolean isSupported(final Class<?> clazz) {
            for (final MessageInfoFactory factory : this.factories) {
                if (factory.isSupported(clazz)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public MessageInfo messageInfoFor(final Class<?> clazz) {
            for (final MessageInfoFactory factory : this.factories) {
                if (factory.isSupported(clazz)) {
                    return factory.messageInfoFor(clazz);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: " + clazz.getName());
        }
    }
}
