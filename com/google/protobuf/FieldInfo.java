package com.google.protobuf;

import java.lang.reflect.*;

final class FieldInfo implements Comparable<FieldInfo>
{
    private final Field field;
    private final FieldType type;
    private final Class<?> messageClass;
    private final int fieldNumber;
    private final Field presenceField;
    private final int presenceMask;
    private final boolean required;
    private final boolean enforceUtf8;
    private final OneofInfo oneof;
    private final Field cachedSizeField;
    private final Class<?> oneofStoredType;
    private final Object mapDefaultEntry;
    private final Internal.EnumVerifier enumVerifier;
    
    public static FieldInfo forField(final Field field, final int fieldNumber, final FieldType fieldType, final boolean enforceUtf8) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
            throw new IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, enforceUtf8, null, null, null, null, null);
    }
    
    public static FieldInfo forPackedField(final Field field, final int fieldNumber, final FieldType fieldType, final Field cachedSizeField) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
            throw new IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, null, cachedSizeField);
    }
    
    public static FieldInfo forRepeatedMessageField(final Field field, final int fieldNumber, final FieldType fieldType, final Class<?> messageClass) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(messageClass, "messageClass");
        return new FieldInfo(field, fieldNumber, fieldType, messageClass, null, 0, false, false, null, null, null, null, null);
    }
    
    public static FieldInfo forFieldWithEnumVerifier(final Field field, final int fieldNumber, final FieldType fieldType, final Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, null);
    }
    
    public static FieldInfo forPackedFieldWithEnumVerifier(final Field field, final int fieldNumber, final FieldType fieldType, final Internal.EnumVerifier enumVerifier, final Field cachedSizeField) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        return new FieldInfo(field, fieldNumber, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, cachedSizeField);
    }
    
    public static FieldInfo forProto2OptionalField(final Field field, final int fieldNumber, final FieldType fieldType, final Field presenceField, final int presenceMask, final boolean enforceUtf8, final Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(presenceField, "presenceField");
        if (presenceField != null && !isExactlyOneBitSet(presenceMask)) {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask);
        }
        return new FieldInfo(field, fieldNumber, fieldType, null, presenceField, presenceMask, false, enforceUtf8, null, null, null, enumVerifier, null);
    }
    
    public static FieldInfo forOneofMemberField(final int fieldNumber, final FieldType fieldType, final OneofInfo oneof, final Class<?> oneofStoredType, final boolean enforceUtf8, final Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(oneof, "oneof");
        Internal.checkNotNull(oneofStoredType, "oneofStoredType");
        if (!fieldType.isScalar()) {
            throw new IllegalArgumentException("Oneof is only supported for scalar fields. Field " + fieldNumber + " is of type " + fieldType);
        }
        return new FieldInfo(null, fieldNumber, fieldType, null, null, 0, false, enforceUtf8, oneof, oneofStoredType, null, enumVerifier, null);
    }
    
    private static void checkFieldNumber(final int fieldNumber) {
        if (fieldNumber <= 0) {
            throw new IllegalArgumentException("fieldNumber must be positive: " + fieldNumber);
        }
    }
    
    public static FieldInfo forProto2RequiredField(final Field field, final int fieldNumber, final FieldType fieldType, final Field presenceField, final int presenceMask, final boolean enforceUtf8, final Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(presenceField, "presenceField");
        if (presenceField != null && !isExactlyOneBitSet(presenceMask)) {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask);
        }
        return new FieldInfo(field, fieldNumber, fieldType, null, presenceField, presenceMask, true, enforceUtf8, null, null, null, enumVerifier, null);
    }
    
    public static FieldInfo forMapField(final Field field, final int fieldNumber, final Object mapDefaultEntry, final Internal.EnumVerifier enumVerifier) {
        Internal.checkNotNull(mapDefaultEntry, "mapDefaultEntry");
        checkFieldNumber(fieldNumber);
        Internal.checkNotNull(field, "field");
        return new FieldInfo(field, fieldNumber, FieldType.MAP, null, null, 0, false, true, null, null, mapDefaultEntry, enumVerifier, null);
    }
    
    private FieldInfo(final Field field, final int fieldNumber, final FieldType type, final Class<?> messageClass, final Field presenceField, final int presenceMask, final boolean required, final boolean enforceUtf8, final OneofInfo oneof, final Class<?> oneofStoredType, final Object mapDefaultEntry, final Internal.EnumVerifier enumVerifier, final Field cachedSizeField) {
        this.field = field;
        this.type = type;
        this.messageClass = messageClass;
        this.fieldNumber = fieldNumber;
        this.presenceField = presenceField;
        this.presenceMask = presenceMask;
        this.required = required;
        this.enforceUtf8 = enforceUtf8;
        this.oneof = oneof;
        this.oneofStoredType = oneofStoredType;
        this.mapDefaultEntry = mapDefaultEntry;
        this.enumVerifier = enumVerifier;
        this.cachedSizeField = cachedSizeField;
    }
    
    public int getFieldNumber() {
        return this.fieldNumber;
    }
    
    public Field getField() {
        return this.field;
    }
    
    public FieldType getType() {
        return this.type;
    }
    
    public OneofInfo getOneof() {
        return this.oneof;
    }
    
    public Class<?> getOneofStoredType() {
        return this.oneofStoredType;
    }
    
    public Internal.EnumVerifier getEnumVerifier() {
        return this.enumVerifier;
    }
    
    @Override
    public int compareTo(final FieldInfo o) {
        return this.fieldNumber - o.fieldNumber;
    }
    
    public Class<?> getListElementType() {
        return this.messageClass;
    }
    
    public Field getPresenceField() {
        return this.presenceField;
    }
    
    public Object getMapDefaultEntry() {
        return this.mapDefaultEntry;
    }
    
    public int getPresenceMask() {
        return this.presenceMask;
    }
    
    public boolean isRequired() {
        return this.required;
    }
    
    public boolean isEnforceUtf8() {
        return this.enforceUtf8;
    }
    
    public Field getCachedSizeField() {
        return this.cachedSizeField;
    }
    
    public Class<?> getMessageFieldClass() {
        switch (this.type) {
            case MESSAGE:
            case GROUP: {
                return (this.field != null) ? this.field.getType() : this.oneofStoredType;
            }
            case MESSAGE_LIST:
            case GROUP_LIST: {
                return this.messageClass;
            }
            default: {
                return null;
            }
        }
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    private static boolean isExactlyOneBitSet(final int value) {
        return value != 0 && (value & value - 1) == 0x0;
    }
    
    public static final class Builder
    {
        private Field field;
        private FieldType type;
        private int fieldNumber;
        private Field presenceField;
        private int presenceMask;
        private boolean required;
        private boolean enforceUtf8;
        private OneofInfo oneof;
        private Class<?> oneofStoredType;
        private Object mapDefaultEntry;
        private Internal.EnumVerifier enumVerifier;
        private Field cachedSizeField;
        
        private Builder() {
        }
        
        public Builder withField(final Field field) {
            if (this.oneof != null) {
                throw new IllegalStateException("Cannot set field when building a oneof.");
            }
            this.field = field;
            return this;
        }
        
        public Builder withType(final FieldType type) {
            this.type = type;
            return this;
        }
        
        public Builder withFieldNumber(final int fieldNumber) {
            this.fieldNumber = fieldNumber;
            return this;
        }
        
        public Builder withPresence(final Field presenceField, final int presenceMask) {
            this.presenceField = Internal.checkNotNull(presenceField, "presenceField");
            this.presenceMask = presenceMask;
            return this;
        }
        
        public Builder withOneof(final OneofInfo oneof, final Class<?> oneofStoredType) {
            if (this.field != null || this.presenceField != null) {
                throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
            }
            this.oneof = oneof;
            this.oneofStoredType = oneofStoredType;
            return this;
        }
        
        public Builder withRequired(final boolean required) {
            this.required = required;
            return this;
        }
        
        public Builder withMapDefaultEntry(final Object mapDefaultEntry) {
            this.mapDefaultEntry = mapDefaultEntry;
            return this;
        }
        
        public Builder withEnforceUtf8(final boolean enforceUtf8) {
            this.enforceUtf8 = enforceUtf8;
            return this;
        }
        
        public Builder withEnumVerifier(final Internal.EnumVerifier enumVerifier) {
            this.enumVerifier = enumVerifier;
            return this;
        }
        
        public Builder withCachedSizeField(final Field cachedSizeField) {
            this.cachedSizeField = cachedSizeField;
            return this;
        }
        
        public FieldInfo build() {
            if (this.oneof != null) {
                return FieldInfo.forOneofMemberField(this.fieldNumber, this.type, this.oneof, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
            }
            if (this.mapDefaultEntry != null) {
                return FieldInfo.forMapField(this.field, this.fieldNumber, this.mapDefaultEntry, this.enumVerifier);
            }
            if (this.presenceField != null) {
                if (this.required) {
                    return FieldInfo.forProto2RequiredField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
                }
                return FieldInfo.forProto2OptionalField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
            }
            else if (this.enumVerifier != null) {
                if (this.cachedSizeField == null) {
                    return FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier);
                }
                return FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier, this.cachedSizeField);
            }
            else {
                if (this.cachedSizeField == null) {
                    return FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8);
                }
                return FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, this.cachedSizeField);
            }
        }
    }
}
