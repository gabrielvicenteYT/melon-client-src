package com.google.protobuf;

class GeneratedMessageInfoFactory implements MessageInfoFactory
{
    private static final GeneratedMessageInfoFactory instance;
    
    private GeneratedMessageInfoFactory() {
    }
    
    public static GeneratedMessageInfoFactory getInstance() {
        return GeneratedMessageInfoFactory.instance;
    }
    
    @Override
    public boolean isSupported(final Class<?> messageType) {
        return GeneratedMessageLite.class.isAssignableFrom(messageType);
    }
    
    @Override
    public MessageInfo messageInfoFor(final Class<?> messageType) {
        if (!GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            throw new IllegalArgumentException("Unsupported message type: " + messageType.getName());
        }
        try {
            return (MessageInfo)((GeneratedMessageLite)GeneratedMessageLite.getDefaultInstance(messageType.asSubclass(GeneratedMessageLite.class))).buildMessageInfo();
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to get message info for " + messageType.getName(), e);
        }
    }
    
    static {
        instance = new GeneratedMessageInfoFactory();
    }
}
