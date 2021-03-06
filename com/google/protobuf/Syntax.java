package com.google.protobuf;

public enum Syntax implements ProtocolMessageEnum
{
    SYNTAX_PROTO2(0), 
    SYNTAX_PROTO3(1), 
    UNRECOGNIZED(-1);
    
    public static final int SYNTAX_PROTO2_VALUE = 0;
    public static final int SYNTAX_PROTO3_VALUE = 1;
    private static final Internal.EnumLiteMap<Syntax> internalValueMap;
    private static final Syntax[] VALUES;
    private final int value;
    
    @Override
    public final int getNumber() {
        if (this == Syntax.UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }
    
    @Deprecated
    public static Syntax valueOf(final int value) {
        return forNumber(value);
    }
    
    public static Syntax forNumber(final int value) {
        switch (value) {
            case 0: {
                return Syntax.SYNTAX_PROTO2;
            }
            case 1: {
                return Syntax.SYNTAX_PROTO3;
            }
            default: {
                return null;
            }
        }
    }
    
    public static Internal.EnumLiteMap<Syntax> internalGetValueMap() {
        return Syntax.internalValueMap;
    }
    
    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        return getDescriptor().getValues().get(this.ordinal());
    }
    
    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return getDescriptor();
    }
    
    public static final Descriptors.EnumDescriptor getDescriptor() {
        return TypeProto.getDescriptor().getEnumTypes().get(0);
    }
    
    public static Syntax valueOf(final Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return Syntax.UNRECOGNIZED;
        }
        return Syntax.VALUES[desc.getIndex()];
    }
    
    private Syntax(final int value) {
        this.value = value;
    }
    
    static {
        internalValueMap = new Internal.EnumLiteMap<Syntax>() {
            @Override
            public Syntax findValueByNumber(final int number) {
                return Syntax.forNumber(number);
            }
        };
        VALUES = values();
    }
}
