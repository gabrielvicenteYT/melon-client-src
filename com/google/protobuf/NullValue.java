package com.google.protobuf;

public enum NullValue implements ProtocolMessageEnum
{
    NULL_VALUE(0), 
    UNRECOGNIZED(-1);
    
    public static final int NULL_VALUE_VALUE = 0;
    private static final Internal.EnumLiteMap<NullValue> internalValueMap;
    private static final NullValue[] VALUES;
    private final int value;
    
    @Override
    public final int getNumber() {
        if (this == NullValue.UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }
    
    @Deprecated
    public static NullValue valueOf(final int value) {
        return forNumber(value);
    }
    
    public static NullValue forNumber(final int value) {
        switch (value) {
            case 0: {
                return NullValue.NULL_VALUE;
            }
            default: {
                return null;
            }
        }
    }
    
    public static Internal.EnumLiteMap<NullValue> internalGetValueMap() {
        return NullValue.internalValueMap;
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
        return StructProto.getDescriptor().getEnumTypes().get(0);
    }
    
    public static NullValue valueOf(final Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return NullValue.UNRECOGNIZED;
        }
        return NullValue.VALUES[desc.getIndex()];
    }
    
    private NullValue(final int value) {
        this.value = value;
    }
    
    static {
        internalValueMap = new Internal.EnumLiteMap<NullValue>() {
            @Override
            public NullValue findValueByNumber(final int number) {
                return NullValue.forNumber(number);
            }
        };
        VALUES = values();
    }
}
