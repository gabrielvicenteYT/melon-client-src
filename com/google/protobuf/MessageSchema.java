package com.google.protobuf;

import sun.misc.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;

final class MessageSchema<T> implements Schema<T>
{
    private static final int INTS_PER_FIELD = 3;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int REQUIRED_MASK = 268435456;
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int[] EMPTY_INT_ARRAY;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final Unsafe UNSAFE;
    private final int[] buffer;
    private final Object[] objects;
    private final int minFieldNumber;
    private final int maxFieldNumber;
    private final MessageLite defaultInstance;
    private final boolean hasExtensions;
    private final boolean lite;
    private final boolean proto3;
    private final boolean useCachedSizeField;
    private final int[] intArray;
    private final int checkInitializedCount;
    private final int repeatedFieldOffsetStart;
    private final NewInstanceSchema newInstanceSchema;
    private final ListFieldSchema listFieldSchema;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final ExtensionSchema<?> extensionSchema;
    private final MapFieldSchema mapFieldSchema;
    
    private MessageSchema(final int[] buffer, final Object[] objects, final int minFieldNumber, final int maxFieldNumber, final MessageLite defaultInstance, final boolean proto3, final boolean useCachedSizeField, final int[] intArray, final int checkInitialized, final int mapFieldPositions, final NewInstanceSchema newInstanceSchema, final ListFieldSchema listFieldSchema, final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MapFieldSchema mapFieldSchema) {
        this.buffer = buffer;
        this.objects = objects;
        this.minFieldNumber = minFieldNumber;
        this.maxFieldNumber = maxFieldNumber;
        this.lite = (defaultInstance instanceof GeneratedMessageLite);
        this.proto3 = proto3;
        this.hasExtensions = (extensionSchema != null && extensionSchema.hasExtensions(defaultInstance));
        this.useCachedSizeField = useCachedSizeField;
        this.intArray = intArray;
        this.checkInitializedCount = checkInitialized;
        this.repeatedFieldOffsetStart = mapFieldPositions;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = defaultInstance;
        this.mapFieldSchema = mapFieldSchema;
    }
    
    static <T> MessageSchema<T> newSchema(final Class<T> messageClass, final MessageInfo messageInfo, final NewInstanceSchema newInstanceSchema, final ListFieldSchema listFieldSchema, final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MapFieldSchema mapFieldSchema) {
        if (messageInfo instanceof RawMessageInfo) {
            return newSchemaForRawMessageInfo((RawMessageInfo)messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        return newSchemaForMessageInfo((StructuralMessageInfo)messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }
    
    static <T> MessageSchema<T> newSchemaForRawMessageInfo(final RawMessageInfo messageInfo, final NewInstanceSchema newInstanceSchema, final ListFieldSchema listFieldSchema, final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MapFieldSchema mapFieldSchema) {
        final boolean isProto3 = messageInfo.getSyntax() == ProtoSyntax.PROTO3;
        final String info = messageInfo.getStringInfo();
        final int length = info.length();
        int i = 0;
        int next = info.charAt(i++);
        if (next >= 55296) {
            int result = next & 0x1FFF;
            int shift = 13;
            while ((next = info.charAt(i++)) >= 55296) {
                result |= (next & 0x1FFF) << shift;
                shift += 13;
            }
            next = (result | next << shift);
        }
        final int flags = next;
        next = info.charAt(i++);
        if (next >= 55296) {
            int result2 = next & 0x1FFF;
            int shift2 = 13;
            while ((next = info.charAt(i++)) >= 55296) {
                result2 |= (next & 0x1FFF) << shift2;
                shift2 += 13;
            }
            next = (result2 | next << shift2);
        }
        final int fieldCount = next;
        int oneofCount;
        int minFieldNumber;
        int maxFieldNumber;
        int numEntries;
        int mapFieldCount;
        int checkInitialized;
        int[] intArray;
        int objectsPosition;
        if (fieldCount == 0) {
            oneofCount = 0;
            final int hasBitsCount = 0;
            minFieldNumber = 0;
            maxFieldNumber = 0;
            numEntries = 0;
            mapFieldCount = 0;
            final int repeatedFieldCount = 0;
            checkInitialized = 0;
            intArray = MessageSchema.EMPTY_INT_ARRAY;
            objectsPosition = 0;
        }
        else {
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            oneofCount = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            final int hasBitsCount = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            minFieldNumber = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            maxFieldNumber = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            numEntries = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            mapFieldCount = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            final int repeatedFieldCount = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result3 = next & 0x1FFF;
                int shift3 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result3 |= (next & 0x1FFF) << shift3;
                    shift3 += 13;
                }
                next = (result3 | next << shift3);
            }
            checkInitialized = next;
            intArray = new int[checkInitialized + mapFieldCount + repeatedFieldCount];
            objectsPosition = oneofCount * 2 + hasBitsCount;
        }
        final Unsafe unsafe = MessageSchema.UNSAFE;
        final Object[] messageInfoObjects = messageInfo.getObjects();
        int checkInitializedPosition = 0;
        final Class<?> messageClass = messageInfo.getDefaultInstance().getClass();
        final int[] buffer = new int[numEntries * 3];
        final Object[] objects = new Object[numEntries * 2];
        int mapFieldIndex = checkInitialized;
        int repeatedFieldIndex = checkInitialized + mapFieldCount;
        int bufferIndex = 0;
        while (i < length) {
            next = info.charAt(i++);
            if (next >= 55296) {
                int result4 = next & 0x1FFF;
                int shift4 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result4 |= (next & 0x1FFF) << shift4;
                    shift4 += 13;
                }
                next = (result4 | next << shift4);
            }
            final int fieldNumber = next;
            next = info.charAt(i++);
            if (next >= 55296) {
                int result4 = next & 0x1FFF;
                int shift4 = 13;
                while ((next = info.charAt(i++)) >= 55296) {
                    result4 |= (next & 0x1FFF) << shift4;
                    shift4 += 13;
                }
                next = (result4 | next << shift4);
            }
            final int fieldTypeWithExtraBits = next;
            final int fieldType = fieldTypeWithExtraBits & 0xFF;
            if ((fieldTypeWithExtraBits & 0x400) != 0x0) {
                intArray[checkInitializedPosition++] = bufferIndex;
            }
            int fieldOffset;
            int presenceFieldOffset;
            int presenceMaskShift;
            if (fieldType >= 51) {
                next = info.charAt(i++);
                if (next >= 55296) {
                    int result5 = next & 0x1FFF;
                    int shift5 = 13;
                    while ((next = info.charAt(i++)) >= 55296) {
                        result5 |= (next & 0x1FFF) << shift5;
                        shift5 += 13;
                    }
                    next = (result5 | next << shift5);
                }
                final int oneofIndex = next;
                final int oneofFieldType = fieldType - 51;
                if (oneofFieldType == 9 || oneofFieldType == 17) {
                    objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
                }
                else if (oneofFieldType == 12 && (flags & 0x1) == 0x1) {
                    objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
                }
                int index = oneofIndex * 2;
                Object o = messageInfoObjects[index];
                Field oneofField;
                if (o instanceof Field) {
                    oneofField = (Field)o;
                }
                else {
                    oneofField = reflectField(messageClass, (String)o);
                    messageInfoObjects[index] = oneofField;
                }
                fieldOffset = (int)unsafe.objectFieldOffset(oneofField);
                ++index;
                o = messageInfoObjects[index];
                Field oneofCaseField;
                if (o instanceof Field) {
                    oneofCaseField = (Field)o;
                }
                else {
                    oneofCaseField = reflectField(messageClass, (String)o);
                    messageInfoObjects[index] = oneofCaseField;
                }
                presenceFieldOffset = (int)unsafe.objectFieldOffset(oneofCaseField);
                presenceMaskShift = 0;
            }
            else {
                final Field field = reflectField(messageClass, (String)messageInfoObjects[objectsPosition++]);
                if (fieldType == 9 || fieldType == 17) {
                    objects[bufferIndex / 3 * 2 + 1] = field.getType();
                }
                else if (fieldType == 27 || fieldType == 49) {
                    objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
                }
                else if (fieldType == 12 || fieldType == 30 || fieldType == 44) {
                    if ((flags & 0x1) == 0x1) {
                        objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
                    }
                }
                else if (fieldType == 50) {
                    intArray[mapFieldIndex++] = bufferIndex;
                    objects[bufferIndex / 3 * 2] = messageInfoObjects[objectsPosition++];
                    if ((fieldTypeWithExtraBits & 0x800) != 0x0) {
                        objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
                    }
                }
                fieldOffset = (int)unsafe.objectFieldOffset(field);
                if ((flags & 0x1) == 0x1 && fieldType <= 17) {
                    next = info.charAt(i++);
                    if (next >= 55296) {
                        int result6 = next & 0x1FFF;
                        int shift6 = 13;
                        while ((next = info.charAt(i++)) >= 55296) {
                            result6 |= (next & 0x1FFF) << shift6;
                            shift6 += 13;
                        }
                        next = (result6 | next << shift6);
                    }
                    final int hasBitsIndex = next;
                    final int index = oneofCount * 2 + hasBitsIndex / 32;
                    final Object o = messageInfoObjects[index];
                    Field hasBitsField;
                    if (o instanceof Field) {
                        hasBitsField = (Field)o;
                    }
                    else {
                        hasBitsField = reflectField(messageClass, (String)o);
                        messageInfoObjects[index] = hasBitsField;
                    }
                    presenceFieldOffset = (int)unsafe.objectFieldOffset(hasBitsField);
                    presenceMaskShift = hasBitsIndex % 32;
                }
                else {
                    presenceFieldOffset = 0;
                    presenceMaskShift = 0;
                }
                if (fieldType >= 18 && fieldType <= 49) {
                    intArray[repeatedFieldIndex++] = fieldOffset;
                }
            }
            buffer[bufferIndex++] = fieldNumber;
            buffer[bufferIndex++] = ((((fieldTypeWithExtraBits & 0x200) != 0x0) ? 536870912 : 0) | (((fieldTypeWithExtraBits & 0x100) != 0x0) ? 268435456 : 0) | fieldType << 20 | fieldOffset);
            buffer[bufferIndex++] = (presenceMaskShift << 20 | presenceFieldOffset);
        }
        return new MessageSchema<T>(buffer, objects, minFieldNumber, maxFieldNumber, messageInfo.getDefaultInstance(), isProto3, false, intArray, checkInitialized, checkInitialized + mapFieldCount, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }
    
    private static Field reflectField(final Class<?> messageClass, final String fieldName) {
        try {
            return messageClass.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            final Field[] declaredFields;
            final Field[] fields = declaredFields = messageClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + fieldName + " for " + messageClass.getName() + " not found. Known fields are " + Arrays.toString(fields));
        }
    }
    
    static <T> MessageSchema<T> newSchemaForMessageInfo(final StructuralMessageInfo messageInfo, final NewInstanceSchema newInstanceSchema, final ListFieldSchema listFieldSchema, final UnknownFieldSchema<?, ?> unknownFieldSchema, final ExtensionSchema<?> extensionSchema, final MapFieldSchema mapFieldSchema) {
        final boolean isProto3 = messageInfo.getSyntax() == ProtoSyntax.PROTO3;
        final FieldInfo[] fis = messageInfo.getFields();
        int minFieldNumber;
        int maxFieldNumber;
        if (fis.length == 0) {
            minFieldNumber = 0;
            maxFieldNumber = 0;
        }
        else {
            minFieldNumber = fis[0].getFieldNumber();
            maxFieldNumber = fis[fis.length - 1].getFieldNumber();
        }
        final int numEntries = fis.length;
        final int[] buffer = new int[numEntries * 3];
        final Object[] objects = new Object[numEntries * 2];
        int mapFieldCount = 0;
        int repeatedFieldCount = 0;
        for (final FieldInfo fi : fis) {
            if (fi.getType() == FieldType.MAP) {
                ++mapFieldCount;
            }
            else if (fi.getType().id() >= 18 && fi.getType().id() <= 49) {
                ++repeatedFieldCount;
            }
        }
        int[] mapFieldPositions = (int[])((mapFieldCount > 0) ? new int[mapFieldCount] : null);
        int[] repeatedFieldOffsets = (int[])((repeatedFieldCount > 0) ? new int[repeatedFieldCount] : null);
        mapFieldCount = 0;
        repeatedFieldCount = 0;
        int[] checkInitialized = messageInfo.getCheckInitialized();
        if (checkInitialized == null) {
            checkInitialized = MessageSchema.EMPTY_INT_ARRAY;
        }
        int checkInitializedIndex = 0;
        for (int fieldIndex = 0, bufferIndex = 0; fieldIndex < fis.length; ++fieldIndex, bufferIndex += 3) {
            final FieldInfo fi2 = fis[fieldIndex];
            final int fieldNumber = fi2.getFieldNumber();
            storeFieldData(fi2, buffer, bufferIndex, isProto3, objects);
            if (checkInitializedIndex < checkInitialized.length && checkInitialized[checkInitializedIndex] == fieldNumber) {
                checkInitialized[checkInitializedIndex++] = bufferIndex;
            }
            if (fi2.getType() == FieldType.MAP) {
                mapFieldPositions[mapFieldCount++] = bufferIndex;
            }
            else if (fi2.getType().id() >= 18 && fi2.getType().id() <= 49) {
                repeatedFieldOffsets[repeatedFieldCount++] = (int)UnsafeUtil.objectFieldOffset(fi2.getField());
            }
        }
        if (mapFieldPositions == null) {
            mapFieldPositions = MessageSchema.EMPTY_INT_ARRAY;
        }
        if (repeatedFieldOffsets == null) {
            repeatedFieldOffsets = MessageSchema.EMPTY_INT_ARRAY;
        }
        final int[] combined = new int[checkInitialized.length + mapFieldPositions.length + repeatedFieldOffsets.length];
        System.arraycopy(checkInitialized, 0, combined, 0, checkInitialized.length);
        System.arraycopy(mapFieldPositions, 0, combined, checkInitialized.length, mapFieldPositions.length);
        System.arraycopy(repeatedFieldOffsets, 0, combined, checkInitialized.length + mapFieldPositions.length, repeatedFieldOffsets.length);
        return new MessageSchema<T>(buffer, objects, minFieldNumber, maxFieldNumber, messageInfo.getDefaultInstance(), isProto3, true, combined, checkInitialized.length, checkInitialized.length + mapFieldPositions.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }
    
    private static void storeFieldData(final FieldInfo fi, final int[] buffer, final int bufferIndex, final boolean proto3, final Object[] objects) {
        final OneofInfo oneof = fi.getOneof();
        int typeId;
        int fieldOffset;
        int presenceFieldOffset;
        int presenceMaskShift;
        if (oneof != null) {
            typeId = fi.getType().id() + 51;
            fieldOffset = (int)UnsafeUtil.objectFieldOffset(oneof.getValueField());
            presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(oneof.getCaseField());
            presenceMaskShift = 0;
        }
        else {
            final FieldType type = fi.getType();
            fieldOffset = (int)UnsafeUtil.objectFieldOffset(fi.getField());
            typeId = type.id();
            if (!proto3 && !type.isList() && !type.isMap()) {
                presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(fi.getPresenceField());
                presenceMaskShift = Integer.numberOfTrailingZeros(fi.getPresenceMask());
            }
            else if (fi.getCachedSizeField() == null) {
                presenceFieldOffset = 0;
                presenceMaskShift = 0;
            }
            else {
                presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(fi.getCachedSizeField());
                presenceMaskShift = 0;
            }
        }
        buffer[bufferIndex] = fi.getFieldNumber();
        buffer[bufferIndex + 1] = ((fi.isEnforceUtf8() ? 536870912 : 0) | (fi.isRequired() ? 268435456 : 0) | typeId << 20 | fieldOffset);
        buffer[bufferIndex + 2] = (presenceMaskShift << 20 | presenceFieldOffset);
        final Object messageFieldClass = fi.getMessageFieldClass();
        if (fi.getMapDefaultEntry() != null) {
            objects[bufferIndex / 3 * 2] = fi.getMapDefaultEntry();
            if (messageFieldClass != null) {
                objects[bufferIndex / 3 * 2 + 1] = messageFieldClass;
            }
            else if (fi.getEnumVerifier() != null) {
                objects[bufferIndex / 3 * 2 + 1] = fi.getEnumVerifier();
            }
        }
        else if (messageFieldClass != null) {
            objects[bufferIndex / 3 * 2 + 1] = messageFieldClass;
        }
        else if (fi.getEnumVerifier() != null) {
            objects[bufferIndex / 3 * 2 + 1] = fi.getEnumVerifier();
        }
    }
    
    @Override
    public T newInstance() {
        return (T)this.newInstanceSchema.newInstance(this.defaultInstance);
    }
    
    @Override
    public boolean equals(final T message, final T other) {
        for (int bufferLength = this.buffer.length, pos = 0; pos < bufferLength; pos += 3) {
            if (!this.equals(message, other, pos)) {
                return false;
            }
        }
        final Object messageUnknown = this.unknownFieldSchema.getFromMessage(message);
        final Object otherUnknown = this.unknownFieldSchema.getFromMessage(other);
        if (!messageUnknown.equals(otherUnknown)) {
            return false;
        }
        if (this.hasExtensions) {
            final FieldSet<?> messageExtensions = this.extensionSchema.getExtensions(message);
            final FieldSet<?> otherExtensions = this.extensionSchema.getExtensions(other);
            return messageExtensions.equals(otherExtensions);
        }
        return true;
    }
    
    private boolean equals(final T message, final T other, final int pos) {
        final int typeAndOffset = this.typeAndOffsetAt(pos);
        final long offset = offset(typeAndOffset);
        switch (type(typeAndOffset)) {
            case 0: {
                return this.arePresentForEquals(message, other, pos) && Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)) == Double.doubleToLongBits(UnsafeUtil.getDouble(other, offset));
            }
            case 1: {
                return this.arePresentForEquals(message, other, pos) && Float.floatToIntBits(UnsafeUtil.getFloat(message, offset)) == Float.floatToIntBits(UnsafeUtil.getFloat(other, offset));
            }
            case 2: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset);
            }
            case 3: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset);
            }
            case 4: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 5: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset);
            }
            case 6: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 7: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getBoolean(message, offset) == UnsafeUtil.getBoolean(other, offset);
            }
            case 8: {
                return this.arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 9: {
                return this.arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 10: {
                return this.arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 11: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 12: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 13: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 14: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset);
            }
            case 15: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset);
            }
            case 16: {
                return this.arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset);
            }
            case 17: {
                return this.arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49: {
                return SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 50: {
                return SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68: {
                return this.isOneofCaseEqual(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
            }
            default: {
                return true;
            }
        }
    }
    
    @Override
    public int hashCode(final T message) {
        int hashCode = 0;
        for (int bufferLength = this.buffer.length, pos = 0; pos < bufferLength; pos += 3) {
            final int typeAndOffset = this.typeAndOffsetAt(pos);
            final int entryNumber = this.numberAt(pos);
            final long offset = offset(typeAndOffset);
            switch (type(typeAndOffset)) {
                case 0: {
                    hashCode = hashCode * 53 + Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)));
                    break;
                }
                case 1: {
                    hashCode = hashCode * 53 + Float.floatToIntBits(UnsafeUtil.getFloat(message, offset));
                    break;
                }
                case 2: {
                    hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                }
                case 3: {
                    hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                }
                case 4: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 5: {
                    hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                }
                case 6: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 7: {
                    hashCode = hashCode * 53 + Internal.hashBoolean(UnsafeUtil.getBoolean(message, offset));
                    break;
                }
                case 8: {
                    hashCode = hashCode * 53 + ((String)UnsafeUtil.getObject(message, offset)).hashCode();
                    break;
                }
                case 9: {
                    int protoHash = 37;
                    final Object submessage = UnsafeUtil.getObject(message, offset);
                    if (submessage != null) {
                        protoHash = submessage.hashCode();
                    }
                    hashCode = 53 * hashCode + protoHash;
                    break;
                }
                case 10: {
                    hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                }
                case 11: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 12: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 13: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 14: {
                    hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                }
                case 15: {
                    hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
                    break;
                }
                case 16: {
                    hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
                    break;
                }
                case 17: {
                    int protoHash = 37;
                    final Object submessage = UnsafeUtil.getObject(message, offset);
                    if (submessage != null) {
                        protoHash = submessage.hashCode();
                    }
                    hashCode = 53 * hashCode + protoHash;
                    break;
                }
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49: {
                    hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                }
                case 50: {
                    hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(message, offset)));
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Float.floatToIntBits(oneofFloatAt(message, offset));
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashBoolean(oneofBooleanAt(message, offset));
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + ((String)UnsafeUtil.getObject(message, offset)).hashCode();
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        final Object submessage2 = UnsafeUtil.getObject(message, offset);
                        hashCode = 53 * hashCode + submessage2.hashCode();
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + oneofIntAt(message, offset);
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, entryNumber, pos)) {
                        final Object submessage2 = UnsafeUtil.getObject(message, offset);
                        hashCode = 53 * hashCode + submessage2.hashCode();
                        break;
                    }
                    break;
                }
            }
        }
        hashCode = hashCode * 53 + this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions) {
            hashCode = hashCode * 53 + this.extensionSchema.getExtensions(message).hashCode();
        }
        return hashCode;
    }
    
    @Override
    public void mergeFrom(final T message, final T other) {
        if (other == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < this.buffer.length; i += 3) {
            this.mergeSingleField(message, other, i);
        }
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
        }
    }
    
    private void mergeSingleField(final T message, final T other, final int pos) {
        final int typeAndOffset = this.typeAndOffsetAt(pos);
        final long offset = offset(typeAndOffset);
        final int number = this.numberAt(pos);
        switch (type(typeAndOffset)) {
            case 0: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putDouble(message, offset, UnsafeUtil.getDouble(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 1: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putFloat(message, offset, UnsafeUtil.getFloat(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 2: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 3: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 4: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 5: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 6: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 7: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putBoolean(message, offset, UnsafeUtil.getBoolean(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 8: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 9: {
                this.mergeMessage(message, other, pos);
                break;
            }
            case 10: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 11: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 12: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 13: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 14: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 15: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 16: {
                if (this.isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
                    this.setFieldPresent(message, pos);
                    break;
                }
                break;
            }
            case 17: {
                this.mergeMessage(message, other, pos);
                break;
            }
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49: {
                this.listFieldSchema.mergeListsAt(message, other, offset);
                break;
            }
            case 50: {
                SchemaUtil.mergeMap(this.mapFieldSchema, message, other, offset);
                break;
            }
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59: {
                if (this.isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    this.setOneofPresent(message, number, pos);
                    break;
                }
                break;
            }
            case 60: {
                this.mergeOneofMessage(message, other, pos);
                break;
            }
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67: {
                if (this.isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
                    this.setOneofPresent(message, number, pos);
                    break;
                }
                break;
            }
            case 68: {
                this.mergeOneofMessage(message, other, pos);
                break;
            }
        }
    }
    
    private void mergeMessage(final T message, final T other, final int pos) {
        final int typeAndOffset = this.typeAndOffsetAt(pos);
        final long offset = offset(typeAndOffset);
        if (!this.isFieldPresent(other, pos)) {
            return;
        }
        final Object mine = UnsafeUtil.getObject(message, offset);
        final Object theirs = UnsafeUtil.getObject(other, offset);
        if (mine != null && theirs != null) {
            final Object merged = Internal.mergeMessage(mine, theirs);
            UnsafeUtil.putObject(message, offset, merged);
            this.setFieldPresent(message, pos);
        }
        else if (theirs != null) {
            UnsafeUtil.putObject(message, offset, theirs);
            this.setFieldPresent(message, pos);
        }
    }
    
    private void mergeOneofMessage(final T message, final T other, final int pos) {
        final int typeAndOffset = this.typeAndOffsetAt(pos);
        final int number = this.numberAt(pos);
        final long offset = offset(typeAndOffset);
        if (!this.isOneofPresent(other, number, pos)) {
            return;
        }
        final Object mine = UnsafeUtil.getObject(message, offset);
        final Object theirs = UnsafeUtil.getObject(other, offset);
        if (mine != null && theirs != null) {
            final Object merged = Internal.mergeMessage(mine, theirs);
            UnsafeUtil.putObject(message, offset, merged);
            this.setOneofPresent(message, number, pos);
        }
        else if (theirs != null) {
            UnsafeUtil.putObject(message, offset, theirs);
            this.setOneofPresent(message, number, pos);
        }
    }
    
    @Override
    public int getSerializedSize(final T message) {
        return this.proto3 ? this.getSerializedSizeProto3(message) : this.getSerializedSizeProto2(message);
    }
    
    private int getSerializedSizeProto2(final T message) {
        int size = 0;
        final Unsafe unsafe = MessageSchema.UNSAFE;
        int currentPresenceFieldOffset = -1;
        int currentPresenceField = 0;
        for (int i = 0; i < this.buffer.length; i += 3) {
            final int typeAndOffset = this.typeAndOffsetAt(i);
            final int number = this.numberAt(i);
            final int fieldType = type(typeAndOffset);
            int presenceMaskAndOffset = 0;
            int presenceMask = 0;
            if (fieldType <= 17) {
                presenceMaskAndOffset = this.buffer[i + 2];
                final int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                if (presenceFieldOffset != currentPresenceFieldOffset) {
                    currentPresenceFieldOffset = presenceFieldOffset;
                    currentPresenceField = unsafe.getInt(message, (long)presenceFieldOffset);
                }
            }
            else if (this.useCachedSizeField && fieldType >= FieldType.DOUBLE_LIST_PACKED.id() && fieldType <= FieldType.SINT64_LIST_PACKED.id()) {
                presenceMaskAndOffset = (this.buffer[i + 2] & 0xFFFFF);
            }
            final long offset = offset(typeAndOffset);
            switch (fieldType) {
                case 0: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0);
                        break;
                    }
                    break;
                }
                case 1: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                    break;
                }
                case 2: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeInt64Size(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 3: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeUInt64Size(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 4: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeInt32Size(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 5: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 6: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 7: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                    break;
                }
                case 8: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        final Object value = unsafe.getObject(message, offset);
                        if (value instanceof ByteString) {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString)value);
                        }
                        else {
                            size += CodedOutputStream.computeStringSize(number, (String)value);
                        }
                        break;
                    }
                    break;
                }
                case 9: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        final Object value = unsafe.getObject(message, offset);
                        size += SchemaUtil.computeSizeMessage(number, value, this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 10: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        final ByteString value2 = (ByteString)unsafe.getObject(message, offset);
                        size += CodedOutputStream.computeBytesSize(number, value2);
                        break;
                    }
                    break;
                }
                case 11: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeUInt32Size(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 12: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeEnumSize(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 13: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 14: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeSFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 15: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeSInt32Size(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 16: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeSInt64Size(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 17: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite)unsafe.getObject(message, offset), this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 18: {
                    size += SchemaUtil.computeSizeFixed64List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 19: {
                    size += SchemaUtil.computeSizeFixed32List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 20: {
                    size += SchemaUtil.computeSizeInt64List(number, (List<Long>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 21: {
                    size += SchemaUtil.computeSizeUInt64List(number, (List<Long>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 22: {
                    size += SchemaUtil.computeSizeInt32List(number, (List<Integer>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 23: {
                    size += SchemaUtil.computeSizeFixed64List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 24: {
                    size += SchemaUtil.computeSizeFixed32List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 25: {
                    size += SchemaUtil.computeSizeBoolList(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 26: {
                    size += SchemaUtil.computeSizeStringList(number, (List<?>)unsafe.getObject(message, offset));
                    break;
                }
                case 27: {
                    size += SchemaUtil.computeSizeMessageList(number, (List<?>)unsafe.getObject(message, offset), this.getMessageFieldSchema(i));
                    break;
                }
                case 28: {
                    size += SchemaUtil.computeSizeByteStringList(number, (List<ByteString>)unsafe.getObject(message, offset));
                    break;
                }
                case 29: {
                    size += SchemaUtil.computeSizeUInt32List(number, (List<Integer>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 30: {
                    size += SchemaUtil.computeSizeEnumList(number, (List<Integer>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 31: {
                    size += SchemaUtil.computeSizeFixed32List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 32: {
                    size += SchemaUtil.computeSizeFixed64List(number, (List<?>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 33: {
                    size += SchemaUtil.computeSizeSInt32List(number, (List<Integer>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 34: {
                    size += SchemaUtil.computeSizeSInt64List(number, (List<Long>)unsafe.getObject(message, offset), false);
                    break;
                }
                case 35: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 36: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 37: {
                    final int fieldSize = SchemaUtil.computeSizeInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 38: {
                    final int fieldSize = SchemaUtil.computeSizeUInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 39: {
                    final int fieldSize = SchemaUtil.computeSizeInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 40: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 41: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 42: {
                    final int fieldSize = SchemaUtil.computeSizeBoolListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 43: {
                    final int fieldSize = SchemaUtil.computeSizeUInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 44: {
                    final int fieldSize = SchemaUtil.computeSizeEnumListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 45: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 46: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 47: {
                    final int fieldSize = SchemaUtil.computeSizeSInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 48: {
                    final int fieldSize = SchemaUtil.computeSizeSInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)presenceMaskAndOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 49: {
                    size += SchemaUtil.computeSizeGroupList(number, (List<MessageLite>)unsafe.getObject(message, offset), this.getMessageFieldSchema(i));
                    break;
                }
                case 50: {
                    size += this.mapFieldSchema.getSerializedSize(number, unsafe.getObject(message, offset), this.getMapFieldDefaultEntry(i));
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0);
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeUInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, number, i)) {
                        final Object value = unsafe.getObject(message, offset);
                        if (value instanceof ByteString) {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString)value);
                        }
                        else {
                            size += CodedOutputStream.computeStringSize(number, (String)value);
                        }
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, number, i)) {
                        final Object value = unsafe.getObject(message, offset);
                        size += SchemaUtil.computeSizeMessage(number, value, this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString)unsafe.getObject(message, offset));
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeUInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeEnumSize(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite)unsafe.getObject(message, offset), this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
            }
        }
        size += this.getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
        if (this.hasExtensions) {
            size += this.extensionSchema.getExtensions(message).getSerializedSize();
        }
        return size;
    }
    
    private int getSerializedSizeProto3(final T message) {
        final Unsafe unsafe = MessageSchema.UNSAFE;
        int size = 0;
        for (int i = 0; i < this.buffer.length; i += 3) {
            final int typeAndOffset = this.typeAndOffsetAt(i);
            final int fieldType = type(typeAndOffset);
            final int number = this.numberAt(i);
            final long offset = offset(typeAndOffset);
            final int cachedSizeOffset = (fieldType >= FieldType.DOUBLE_LIST_PACKED.id() && fieldType <= FieldType.SINT64_LIST_PACKED.id()) ? (this.buffer[i + 2] & 0xFFFFF) : 0;
            switch (fieldType) {
                case 0: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0);
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeInt64Size(number, UnsafeUtil.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeUInt64Size(number, UnsafeUtil.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeInt32Size(number, UnsafeUtil.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 5: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 6: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 7: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                    break;
                }
                case 8: {
                    if (this.isFieldPresent(message, i)) {
                        final Object value = UnsafeUtil.getObject(message, offset);
                        if (value instanceof ByteString) {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString)value);
                        }
                        else {
                            size += CodedOutputStream.computeStringSize(number, (String)value);
                        }
                        break;
                    }
                    break;
                }
                case 9: {
                    if (this.isFieldPresent(message, i)) {
                        final Object value = UnsafeUtil.getObject(message, offset);
                        size += SchemaUtil.computeSizeMessage(number, value, this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 10: {
                    if (this.isFieldPresent(message, i)) {
                        final ByteString value2 = (ByteString)UnsafeUtil.getObject(message, offset);
                        size += CodedOutputStream.computeBytesSize(number, value2);
                        break;
                    }
                    break;
                }
                case 11: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeUInt32Size(number, UnsafeUtil.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 12: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeEnumSize(number, UnsafeUtil.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 13: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 14: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeSFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 15: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeSInt32Size(number, UnsafeUtil.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 16: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeSInt64Size(number, UnsafeUtil.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 17: {
                    if (this.isFieldPresent(message, i)) {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite)UnsafeUtil.getObject(message, offset), this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 18: {
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(message, offset), false);
                    break;
                }
                case 19: {
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(message, offset), false);
                    break;
                }
                case 20: {
                    size += SchemaUtil.computeSizeInt64List(number, (List<Long>)listAt(message, offset), false);
                    break;
                }
                case 21: {
                    size += SchemaUtil.computeSizeUInt64List(number, (List<Long>)listAt(message, offset), false);
                    break;
                }
                case 22: {
                    size += SchemaUtil.computeSizeInt32List(number, (List<Integer>)listAt(message, offset), false);
                    break;
                }
                case 23: {
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(message, offset), false);
                    break;
                }
                case 24: {
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(message, offset), false);
                    break;
                }
                case 25: {
                    size += SchemaUtil.computeSizeBoolList(number, listAt(message, offset), false);
                    break;
                }
                case 26: {
                    size += SchemaUtil.computeSizeStringList(number, listAt(message, offset));
                    break;
                }
                case 27: {
                    size += SchemaUtil.computeSizeMessageList(number, listAt(message, offset), this.getMessageFieldSchema(i));
                    break;
                }
                case 28: {
                    size += SchemaUtil.computeSizeByteStringList(number, (List<ByteString>)listAt(message, offset));
                    break;
                }
                case 29: {
                    size += SchemaUtil.computeSizeUInt32List(number, (List<Integer>)listAt(message, offset), false);
                    break;
                }
                case 30: {
                    size += SchemaUtil.computeSizeEnumList(number, (List<Integer>)listAt(message, offset), false);
                    break;
                }
                case 31: {
                    size += SchemaUtil.computeSizeFixed32List(number, listAt(message, offset), false);
                    break;
                }
                case 32: {
                    size += SchemaUtil.computeSizeFixed64List(number, listAt(message, offset), false);
                    break;
                }
                case 33: {
                    size += SchemaUtil.computeSizeSInt32List(number, (List<Integer>)listAt(message, offset), false);
                    break;
                }
                case 34: {
                    size += SchemaUtil.computeSizeSInt64List(number, (List<Long>)listAt(message, offset), false);
                    break;
                }
                case 35: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 36: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 37: {
                    final int fieldSize = SchemaUtil.computeSizeInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 38: {
                    final int fieldSize = SchemaUtil.computeSizeUInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 39: {
                    final int fieldSize = SchemaUtil.computeSizeInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 40: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 41: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 42: {
                    final int fieldSize = SchemaUtil.computeSizeBoolListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 43: {
                    final int fieldSize = SchemaUtil.computeSizeUInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 44: {
                    final int fieldSize = SchemaUtil.computeSizeEnumListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 45: {
                    final int fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 46: {
                    final int fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 47: {
                    final int fieldSize = SchemaUtil.computeSizeSInt32ListNoTag((List<Integer>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 48: {
                    final int fieldSize = SchemaUtil.computeSizeSInt64ListNoTag((List<Long>)unsafe.getObject(message, offset));
                    if (fieldSize > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(message, (long)cachedSizeOffset, fieldSize);
                        }
                        size += CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
                        break;
                    }
                    break;
                }
                case 49: {
                    size += SchemaUtil.computeSizeGroupList(number, (List<MessageLite>)listAt(message, offset), this.getMessageFieldSchema(i));
                    break;
                }
                case 50: {
                    size += this.mapFieldSchema.getSerializedSize(number, UnsafeUtil.getObject(message, offset), this.getMapFieldDefaultEntry(i));
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeDoubleSize(number, 0.0);
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFloatSize(number, 0.0f);
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeUInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeBoolSize(number, true);
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, number, i)) {
                        final Object value = UnsafeUtil.getObject(message, offset);
                        if (value instanceof ByteString) {
                            size += CodedOutputStream.computeBytesSize(number, (ByteString)value);
                        }
                        else {
                            size += CodedOutputStream.computeStringSize(number, (String)value);
                        }
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, number, i)) {
                        final Object value = UnsafeUtil.getObject(message, offset);
                        size += SchemaUtil.computeSizeMessage(number, value, this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeBytesSize(number, (ByteString)UnsafeUtil.getObject(message, offset));
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeUInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeEnumSize(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSFixed32Size(number, 0);
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSFixed64Size(number, 0L);
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSInt32Size(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeSInt64Size(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, number, i)) {
                        size += CodedOutputStream.computeGroupSize(number, (MessageLite)UnsafeUtil.getObject(message, offset), this.getMessageFieldSchema(i));
                        break;
                    }
                    break;
                }
            }
        }
        size += this.getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
        return size;
    }
    
    private <UT, UB> int getUnknownFieldsSerializedSize(final UnknownFieldSchema<UT, UB> schema, final T message) {
        final UT unknowns = schema.getFromMessage(message);
        return schema.getSerializedSize(unknowns);
    }
    
    private static List<?> listAt(final Object message, final long offset) {
        return (List<?>)UnsafeUtil.getObject(message, offset);
    }
    
    @Override
    public void writeTo(final T message, final Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            this.writeFieldsInDescendingOrder(message, writer);
        }
        else if (this.proto3) {
            this.writeFieldsInAscendingOrderProto3(message, writer);
        }
        else {
            this.writeFieldsInAscendingOrderProto2(message, writer);
        }
    }
    
    private void writeFieldsInAscendingOrderProto2(final T message, final Writer writer) throws IOException {
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.iterator();
                nextExtension = (Map.Entry)extensionIterator.next();
            }
        }
        int currentPresenceFieldOffset = -1;
        int currentPresenceField = 0;
        final int bufferLength = this.buffer.length;
        final Unsafe unsafe = MessageSchema.UNSAFE;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            final int typeAndOffset = this.typeAndOffsetAt(pos);
            final int number = this.numberAt(pos);
            final int fieldType = type(typeAndOffset);
            int presenceMaskAndOffset = 0;
            int presenceMask = 0;
            if (!this.proto3 && fieldType <= 17) {
                presenceMaskAndOffset = this.buffer[pos + 2];
                final int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
                if (presenceFieldOffset != currentPresenceFieldOffset) {
                    currentPresenceFieldOffset = presenceFieldOffset;
                    currentPresenceField = unsafe.getInt(message, (long)presenceFieldOffset);
                }
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
            }
            while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) <= number) {
                this.extensionSchema.serializeExtension(writer, nextExtension);
                nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null);
            }
            final long offset = offset(typeAndOffset);
            switch (fieldType) {
                case 0: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeDouble(number, doubleAt(message, offset));
                        break;
                    }
                    break;
                }
                case 1: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeFloat(number, floatAt(message, offset));
                        break;
                    }
                    break;
                }
                case 2: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeInt64(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 3: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeUInt64(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 4: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeInt32(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 5: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeFixed64(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 6: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeFixed32(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 7: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeBool(number, booleanAt(message, offset));
                        break;
                    }
                    break;
                }
                case 8: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        this.writeString(number, unsafe.getObject(message, offset), writer);
                        break;
                    }
                    break;
                }
                case 9: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        final Object value = unsafe.getObject(message, offset);
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 10: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeBytes(number, (ByteString)unsafe.getObject(message, offset));
                        break;
                    }
                    break;
                }
                case 11: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeUInt32(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 12: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeEnum(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 13: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeSFixed32(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 14: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeSFixed64(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 15: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeSInt32(number, unsafe.getInt(message, offset));
                        break;
                    }
                    break;
                }
                case 16: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeSInt64(number, unsafe.getLong(message, offset));
                        break;
                    }
                    break;
                }
                case 17: {
                    if ((currentPresenceField & presenceMask) != 0x0) {
                        writer.writeGroup(number, unsafe.getObject(message, offset), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 18: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 19: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 20: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 21: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 22: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 23: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 24: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 25: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 26: {
                    SchemaUtil.writeStringList(this.numberAt(pos), (List<String>)unsafe.getObject(message, offset), writer);
                    break;
                }
                case 27: {
                    SchemaUtil.writeMessageList(this.numberAt(pos), (List<?>)unsafe.getObject(message, offset), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 28: {
                    SchemaUtil.writeBytesList(this.numberAt(pos), (List<ByteString>)unsafe.getObject(message, offset), writer);
                    break;
                }
                case 29: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 30: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 31: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 32: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 33: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 34: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
                    break;
                }
                case 35: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 36: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 37: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 38: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 39: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 40: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 41: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 42: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 43: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 44: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 45: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 46: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 47: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 48: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
                    break;
                }
                case 49: {
                    SchemaUtil.writeGroupList(this.numberAt(pos), (List<?>)unsafe.getObject(message, offset), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 50: {
                    this.writeMapHelper(writer, number, unsafe.getObject(message, offset), pos);
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeDouble(number, oneofDoubleAt(message, offset));
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFloat(number, oneofFloatAt(message, offset));
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt64(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt64(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt32(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed64(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed32(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBool(number, oneofBooleanAt(message, offset));
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, number, pos)) {
                        this.writeString(number, unsafe.getObject(message, offset), writer);
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, number, pos)) {
                        final Object value = unsafe.getObject(message, offset);
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBytes(number, (ByteString)unsafe.getObject(message, offset));
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt32(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeEnum(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed32(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed64(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt32(number, oneofIntAt(message, offset));
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt64(number, oneofLongAt(message, offset));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeGroup(number, unsafe.getObject(message, offset), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
            }
        }
        while (nextExtension != null) {
            this.extensionSchema.serializeExtension(writer, nextExtension);
            nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null);
        }
        this.writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
    }
    
    private void writeFieldsInAscendingOrderProto3(final T message, final Writer writer) throws IOException {
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.iterator();
                nextExtension = (Map.Entry)extensionIterator.next();
            }
        }
        for (int bufferLength = this.buffer.length, pos = 0; pos < bufferLength; pos += 3) {
            final int typeAndOffset = this.typeAndOffsetAt(pos);
            int number;
            for (number = this.numberAt(pos); nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) <= number; nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null)) {
                this.extensionSchema.serializeExtension(writer, nextExtension);
            }
            switch (type(typeAndOffset)) {
                case 0: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 5: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 6: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 7: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 8: {
                    if (this.isFieldPresent(message, pos)) {
                        this.writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                    break;
                }
                case 9: {
                    if (this.isFieldPresent(message, pos)) {
                        final Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 10: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeBytes(number, (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 11: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 12: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 13: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 14: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 15: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 16: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 17: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 18: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 19: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 20: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 21: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 22: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 23: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 24: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 25: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 26: {
                    SchemaUtil.writeStringList(this.numberAt(pos), (List<String>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                }
                case 27: {
                    SchemaUtil.writeMessageList(this.numberAt(pos), (List<?>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 28: {
                    SchemaUtil.writeBytesList(this.numberAt(pos), (List<ByteString>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                }
                case 29: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 30: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 31: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 32: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 33: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 34: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 35: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 36: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 37: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 38: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 39: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 40: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 41: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 42: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 43: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 44: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 45: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 46: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 47: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 48: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 49: {
                    SchemaUtil.writeGroupList(this.numberAt(pos), (List<?>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 50: {
                    this.writeMapHelper(writer, number, UnsafeUtil.getObject(message, offset(typeAndOffset)), pos);
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, number, pos)) {
                        this.writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, number, pos)) {
                        final Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBytes(number, (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
            }
        }
        while (nextExtension != null) {
            this.extensionSchema.serializeExtension(writer, nextExtension);
            nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null);
        }
        this.writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
    }
    
    private void writeFieldsInDescendingOrder(final T message, final Writer writer) throws IOException {
        this.writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
        Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            final FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                extensionIterator = extensions.descendingIterator();
                nextExtension = (Map.Entry)extensionIterator.next();
            }
        }
        for (int pos = this.buffer.length - 3; pos >= 0; pos -= 3) {
            final int typeAndOffset = this.typeAndOffsetAt(pos);
            int number;
            for (number = this.numberAt(pos); nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) > number; nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null)) {
                this.extensionSchema.serializeExtension(writer, nextExtension);
            }
            switch (type(typeAndOffset)) {
                case 0: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 5: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 6: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 7: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 8: {
                    if (this.isFieldPresent(message, pos)) {
                        this.writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                    break;
                }
                case 9: {
                    if (this.isFieldPresent(message, pos)) {
                        final Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 10: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeBytes(number, (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 11: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 12: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 13: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 14: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 15: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 16: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 17: {
                    if (this.isFieldPresent(message, pos)) {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 18: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 19: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 20: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 21: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 22: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 23: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 24: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 25: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 26: {
                    SchemaUtil.writeStringList(this.numberAt(pos), (List<String>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                }
                case 27: {
                    SchemaUtil.writeMessageList(this.numberAt(pos), (List<?>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 28: {
                    SchemaUtil.writeBytesList(this.numberAt(pos), (List<ByteString>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                    break;
                }
                case 29: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 30: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 31: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 32: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 33: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 34: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
                    break;
                }
                case 35: {
                    SchemaUtil.writeDoubleList(this.numberAt(pos), (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 36: {
                    SchemaUtil.writeFloatList(this.numberAt(pos), (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 37: {
                    SchemaUtil.writeInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 38: {
                    SchemaUtil.writeUInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 39: {
                    SchemaUtil.writeInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 40: {
                    SchemaUtil.writeFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 41: {
                    SchemaUtil.writeFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 42: {
                    SchemaUtil.writeBoolList(this.numberAt(pos), (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 43: {
                    SchemaUtil.writeUInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 44: {
                    SchemaUtil.writeEnumList(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 45: {
                    SchemaUtil.writeSFixed32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 46: {
                    SchemaUtil.writeSFixed64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 47: {
                    SchemaUtil.writeSInt32List(this.numberAt(pos), (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 48: {
                    SchemaUtil.writeSInt64List(this.numberAt(pos), (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
                    break;
                }
                case 49: {
                    SchemaUtil.writeGroupList(this.numberAt(pos), (List<?>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, this.getMessageFieldSchema(pos));
                    break;
                }
                case 50: {
                    this.writeMapHelper(writer, number, UnsafeUtil.getObject(message, offset(typeAndOffset)), pos);
                    break;
                }
                case 51: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 52: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 53: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 54: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 55: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 56: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 57: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 58: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 59: {
                    if (this.isOneofPresent(message, number, pos)) {
                        this.writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
                        break;
                    }
                    break;
                }
                case 60: {
                    if (this.isOneofPresent(message, number, pos)) {
                        final Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
                        writer.writeMessage(number, value, this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
                case 61: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeBytes(number, (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 62: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 63: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 64: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 65: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 66: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 67: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                        break;
                    }
                    break;
                }
                case 68: {
                    if (this.isOneofPresent(message, number, pos)) {
                        writer.writeGroup(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), this.getMessageFieldSchema(pos));
                        break;
                    }
                    break;
                }
            }
        }
        while (nextExtension != null) {
            this.extensionSchema.serializeExtension(writer, nextExtension);
            nextExtension = (extensionIterator.hasNext() ? ((Map.Entry)extensionIterator.next()) : null);
        }
    }
    
    private <K, V> void writeMapHelper(final Writer writer, final int number, final Object mapField, final int pos) throws IOException {
        if (mapField != null) {
            writer.writeMap(number, this.mapFieldSchema.forMapMetadata(this.getMapFieldDefaultEntry(pos)), this.mapFieldSchema.forMapData(mapField));
        }
    }
    
    private <UT, UB> void writeUnknownInMessageTo(final UnknownFieldSchema<UT, UB> schema, final T message, final Writer writer) throws IOException {
        schema.writeTo(schema.getFromMessage(message), writer);
    }
    
    @Override
    public void mergeFrom(final T message, final Reader reader, final ExtensionRegistryLite extensionRegistry) throws IOException {
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        this.mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
    }
    
    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(final UnknownFieldSchema<UT, UB> unknownFieldSchema, final ExtensionSchema<ET> extensionSchema, final T message, final Reader reader, final ExtensionRegistryLite extensionRegistry) throws IOException {
        UB unknownFields = null;
        FieldSet<ET> extensions = null;
        try {
            while (true) {
                final int number = reader.getFieldNumber();
                final int pos = this.positionForFieldNumber(number);
                if (pos < 0) {
                    if (number == Integer.MAX_VALUE) {
                        return;
                    }
                    final Object extension = this.hasExtensions ? extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, number) : null;
                    if (extension != null) {
                        if (extensions == null) {
                            extensions = extensionSchema.getMutableExtensions(message);
                        }
                        unknownFields = extensionSchema.parseExtension(reader, extension, extensionRegistry, extensions, unknownFields, unknownFieldSchema);
                    }
                    else if (unknownFieldSchema.shouldDiscardUnknownFields(reader)) {
                        if (reader.skipField()) {
                            continue;
                        }
                        break;
                    }
                    else {
                        if (unknownFields == null) {
                            unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
                        }
                        if (unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader)) {
                            continue;
                        }
                        break;
                    }
                }
                else {
                    final int typeAndOffset = this.typeAndOffsetAt(pos);
                    try {
                        switch (type(typeAndOffset)) {
                            case 0: {
                                UnsafeUtil.putDouble(message, offset(typeAndOffset), reader.readDouble());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 1: {
                                UnsafeUtil.putFloat(message, offset(typeAndOffset), reader.readFloat());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 2: {
                                UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readInt64());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 3: {
                                UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readUInt64());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 4: {
                                UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readInt32());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 5: {
                                UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readFixed64());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 6: {
                                UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readFixed32());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 7: {
                                UnsafeUtil.putBoolean(message, offset(typeAndOffset), reader.readBool());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 8: {
                                this.readString(message, typeAndOffset, reader);
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 9: {
                                if (this.isFieldPresent(message, pos)) {
                                    final Object mergedResult = Internal.mergeMessage(UnsafeUtil.getObject(message, offset(typeAndOffset)), reader.readMessageBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                    UnsafeUtil.putObject(message, offset(typeAndOffset), mergedResult);
                                    continue;
                                }
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readMessageBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 10: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 11: {
                                UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readUInt32());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 12: {
                                final int enumValue = reader.readEnum();
                                final Internal.EnumVerifier enumVerifier = this.getEnumFieldVerifier(pos);
                                if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
                                    UnsafeUtil.putInt(message, offset(typeAndOffset), enumValue);
                                    this.setFieldPresent(message, pos);
                                    continue;
                                }
                                unknownFields = SchemaUtil.storeUnknownEnum(number, enumValue, unknownFields, unknownFieldSchema);
                                continue;
                            }
                            case 13: {
                                UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readSFixed32());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 14: {
                                UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readSFixed64());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 15: {
                                UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readSInt32());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 16: {
                                UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readSInt64());
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 17: {
                                if (this.isFieldPresent(message, pos)) {
                                    final Object mergedResult = Internal.mergeMessage(UnsafeUtil.getObject(message, offset(typeAndOffset)), reader.readGroupBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                    UnsafeUtil.putObject(message, offset(typeAndOffset), mergedResult);
                                    continue;
                                }
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readGroupBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                this.setFieldPresent(message, pos);
                                continue;
                            }
                            case 18: {
                                reader.readDoubleList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 19: {
                                reader.readFloatList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 20: {
                                reader.readInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 21: {
                                reader.readUInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 22: {
                                reader.readInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 23: {
                                reader.readFixed64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 24: {
                                reader.readFixed32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 25: {
                                reader.readBoolList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 26: {
                                this.readStringList(message, typeAndOffset, reader);
                                continue;
                            }
                            case 27: {
                                this.readMessageList(message, typeAndOffset, reader, (Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry);
                                continue;
                            }
                            case 28: {
                                reader.readBytesList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 29: {
                                reader.readUInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 30: {
                                final List<Integer> enumList = this.listFieldSchema.mutableListAt(message, offset(typeAndOffset));
                                reader.readEnumList(enumList);
                                unknownFields = SchemaUtil.filterUnknownEnumList(number, enumList, this.getEnumFieldVerifier(pos), unknownFields, unknownFieldSchema);
                                continue;
                            }
                            case 31: {
                                reader.readSFixed32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 32: {
                                reader.readSFixed64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 33: {
                                reader.readSInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 34: {
                                reader.readSInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 35: {
                                reader.readDoubleList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 36: {
                                reader.readFloatList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 37: {
                                reader.readInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 38: {
                                reader.readUInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 39: {
                                reader.readInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 40: {
                                reader.readFixed64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 41: {
                                reader.readFixed32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 42: {
                                reader.readBoolList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 43: {
                                reader.readUInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 44: {
                                final List<Integer> enumList = this.listFieldSchema.mutableListAt(message, offset(typeAndOffset));
                                reader.readEnumList(enumList);
                                unknownFields = SchemaUtil.filterUnknownEnumList(number, enumList, this.getEnumFieldVerifier(pos), unknownFields, unknownFieldSchema);
                                continue;
                            }
                            case 45: {
                                reader.readSFixed32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 46: {
                                reader.readSFixed64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 47: {
                                reader.readSInt32List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 48: {
                                reader.readSInt64List(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
                                continue;
                            }
                            case 49: {
                                this.readGroupList(message, offset(typeAndOffset), reader, (Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry);
                                continue;
                            }
                            case 50: {
                                this.mergeMap(message, pos, this.getMapFieldDefaultEntry(pos), extensionRegistry, reader);
                                continue;
                            }
                            case 51: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readDouble());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 52: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readFloat());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 53: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readInt64());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 54: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readUInt64());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 55: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readInt32());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 56: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readFixed64());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 57: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readFixed32());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 58: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBool());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 59: {
                                this.readString(message, typeAndOffset, reader);
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 60: {
                                if (this.isOneofPresent(message, number, pos)) {
                                    final Object mergedResult = Internal.mergeMessage(UnsafeUtil.getObject(message, offset(typeAndOffset)), reader.readMessageBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                    UnsafeUtil.putObject(message, offset(typeAndOffset), mergedResult);
                                }
                                else {
                                    UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readMessageBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                    this.setFieldPresent(message, pos);
                                }
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 61: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 62: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readUInt32());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 63: {
                                final int enumValue = reader.readEnum();
                                final Internal.EnumVerifier enumVerifier = this.getEnumFieldVerifier(pos);
                                if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
                                    UnsafeUtil.putObject(message, offset(typeAndOffset), enumValue);
                                    this.setOneofPresent(message, number, pos);
                                    continue;
                                }
                                unknownFields = SchemaUtil.storeUnknownEnum(number, enumValue, unknownFields, unknownFieldSchema);
                                continue;
                            }
                            case 64: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readSFixed32());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 65: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readSFixed64());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 66: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readSInt32());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 67: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readSInt64());
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            case 68: {
                                UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readGroupBySchemaWithCheck((Schema<Object>)this.getMessageFieldSchema(pos), extensionRegistry));
                                this.setOneofPresent(message, number, pos);
                                continue;
                            }
                            default: {
                                if (unknownFields == null) {
                                    unknownFields = unknownFieldSchema.newBuilder();
                                }
                                if (!unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader)) {
                                    return;
                                }
                                continue;
                            }
                        }
                    }
                    catch (InvalidProtocolBufferException.InvalidWireTypeException e) {
                        if (unknownFieldSchema.shouldDiscardUnknownFields(reader)) {
                            if (!reader.skipField()) {
                                return;
                            }
                            continue;
                        }
                        else {
                            if (unknownFields == null) {
                                unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
                            }
                            if (!unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader)) {
                                return;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        finally {
            for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; ++i) {
                unknownFields = this.filterMapUnknownEnumValues(message, this.intArray[i], unknownFields, unknownFieldSchema);
            }
            if (unknownFields != null) {
                unknownFieldSchema.setBuilderToMessage(message, unknownFields);
            }
        }
    }
    
    static UnknownFieldSetLite getMutableUnknownFields(final Object message) {
        UnknownFieldSetLite unknownFields = ((GeneratedMessageLite)message).unknownFields;
        if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            unknownFields = UnknownFieldSetLite.newInstance();
            ((GeneratedMessageLite)message).unknownFields = unknownFields;
        }
        return unknownFields;
    }
    
    private int decodeMapEntryValue(final byte[] data, int position, final int limit, final WireFormat.FieldType fieldType, final Class<?> messageType, final ArrayDecoders.Registers registers) throws IOException {
        switch (fieldType) {
            case BOOL: {
                position = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = (registers.long1 != 0L);
                break;
            }
            case BYTES: {
                position = ArrayDecoders.decodeBytes(data, position, registers);
                break;
            }
            case DOUBLE: {
                registers.object1 = ArrayDecoders.decodeDouble(data, position);
                position += 8;
                break;
            }
            case FIXED32:
            case SFIXED32: {
                registers.object1 = ArrayDecoders.decodeFixed32(data, position);
                position += 4;
                break;
            }
            case FIXED64:
            case SFIXED64: {
                registers.object1 = ArrayDecoders.decodeFixed64(data, position);
                position += 8;
                break;
            }
            case FLOAT: {
                registers.object1 = ArrayDecoders.decodeFloat(data, position);
                position += 4;
                break;
            }
            case ENUM:
            case INT32:
            case UINT32: {
                position = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = registers.int1;
                break;
            }
            case INT64:
            case UINT64: {
                position = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = registers.long1;
                break;
            }
            case MESSAGE: {
                position = ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(messageType), data, position, limit, registers);
                break;
            }
            case SINT32: {
                position = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = CodedInputStream.decodeZigZag32(registers.int1);
                break;
            }
            case SINT64: {
                position = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = CodedInputStream.decodeZigZag64(registers.long1);
                break;
            }
            case STRING: {
                position = ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
                break;
            }
            default: {
                throw new RuntimeException("unsupported field type.");
            }
        }
        return position;
    }
    
    private <K, V> int decodeMapEntry(final byte[] data, int position, final int limit, final MapEntryLite.Metadata<K, V> metadata, final Map<K, V> target, final ArrayDecoders.Registers registers) throws IOException {
        position = ArrayDecoders.decodeVarint32(data, position, registers);
        final int length = registers.int1;
        if (length < 0 || length > limit - position) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        final int end = position + length;
        K key = metadata.defaultKey;
        V value = metadata.defaultValue;
        while (position < end) {
            int tag = data[position++];
            if (tag < 0) {
                position = ArrayDecoders.decodeVarint32(tag, data, position, registers);
                tag = registers.int1;
            }
            final int fieldNumber = tag >>> 3;
            final int wireType = tag & 0x7;
            switch (fieldNumber) {
                case 1: {
                    if (wireType == metadata.keyType.getWireType()) {
                        position = this.decodeMapEntryValue(data, position, limit, metadata.keyType, null, registers);
                        key = (K)registers.object1;
                        continue;
                    }
                    break;
                }
                case 2: {
                    if (wireType == metadata.valueType.getWireType()) {
                        position = this.decodeMapEntryValue(data, position, limit, metadata.valueType, metadata.defaultValue.getClass(), registers);
                        value = (V)registers.object1;
                        continue;
                    }
                    break;
                }
            }
            position = ArrayDecoders.skipField(tag, data, position, limit, registers);
        }
        if (position != end) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        target.put(key, value);
        return end;
    }
    
    private int parseRepeatedField(final T message, final byte[] data, int position, final int limit, final int tag, final int number, final int wireType, final int bufferPosition, final long typeAndOffset, final int fieldType, final long fieldOffset, final ArrayDecoders.Registers registers) throws IOException {
        Internal.ProtobufList<?> list = (Internal.ProtobufList<?>)MessageSchema.UNSAFE.getObject(message, fieldOffset);
        if (!list.isModifiable()) {
            final int size = list.size();
            list = list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
            MessageSchema.UNSAFE.putObject(message, fieldOffset, list);
        }
        switch (fieldType) {
            case 18:
            case 35: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedDoubleList(data, position, list, registers);
                    break;
                }
                if (wireType == 1) {
                    position = ArrayDecoders.decodeDoubleList(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 19:
            case 36: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedFloatList(data, position, list, registers);
                    break;
                }
                if (wireType == 5) {
                    position = ArrayDecoders.decodeFloatList(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 20:
            case 21:
            case 37:
            case 38: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedVarint64List(data, position, list, registers);
                    break;
                }
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint64List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 22:
            case 29:
            case 39:
            case 43: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedVarint32List(data, position, list, registers);
                    break;
                }
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint32List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 23:
            case 32:
            case 40:
            case 46: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedFixed64List(data, position, list, registers);
                    break;
                }
                if (wireType == 1) {
                    position = ArrayDecoders.decodeFixed64List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 24:
            case 31:
            case 41:
            case 45: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedFixed32List(data, position, list, registers);
                    break;
                }
                if (wireType == 5) {
                    position = ArrayDecoders.decodeFixed32List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 25:
            case 42: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedBoolList(data, position, list, registers);
                    break;
                }
                if (wireType == 0) {
                    position = ArrayDecoders.decodeBoolList(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 26: {
                if (wireType != 2) {
                    break;
                }
                if ((typeAndOffset & 0x20000000L) == 0x0L) {
                    position = ArrayDecoders.decodeStringList(tag, data, position, limit, list, registers);
                    break;
                }
                position = ArrayDecoders.decodeStringListRequireUtf8(tag, data, position, limit, list, registers);
                break;
            }
            case 27: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodeMessageList(this.getMessageFieldSchema(bufferPosition), tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 28: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodeBytesList(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 30:
            case 44: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedVarint32List(data, position, list, registers);
                }
                else {
                    if (wireType != 0) {
                        break;
                    }
                    position = ArrayDecoders.decodeVarint32List(tag, data, position, limit, list, registers);
                }
                UnknownFieldSetLite unknownFields = ((GeneratedMessageLite)message).unknownFields;
                if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFields = null;
                }
                unknownFields = SchemaUtil.filterUnknownEnumList(number, (List<Integer>)list, this.getEnumFieldVerifier(bufferPosition), unknownFields, this.unknownFieldSchema);
                if (unknownFields != null) {
                    ((GeneratedMessageLite)message).unknownFields = unknownFields;
                    break;
                }
                break;
            }
            case 33:
            case 47: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedSInt32List(data, position, list, registers);
                    break;
                }
                if (wireType == 0) {
                    position = ArrayDecoders.decodeSInt32List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 34:
            case 48: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodePackedSInt64List(data, position, list, registers);
                    break;
                }
                if (wireType == 0) {
                    position = ArrayDecoders.decodeSInt64List(tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
            case 49: {
                if (wireType == 3) {
                    position = ArrayDecoders.decodeGroupList(this.getMessageFieldSchema(bufferPosition), tag, data, position, limit, list, registers);
                    break;
                }
                break;
            }
        }
        return position;
    }
    
    private <K, V> int parseMapField(final T message, final byte[] data, final int position, final int limit, final int bufferPosition, final long fieldOffset, final ArrayDecoders.Registers registers) throws IOException {
        final Unsafe unsafe = MessageSchema.UNSAFE;
        final Object mapDefaultEntry = this.getMapFieldDefaultEntry(bufferPosition);
        Object mapField = unsafe.getObject(message, fieldOffset);
        if (this.mapFieldSchema.isImmutable(mapField)) {
            final Object oldMapField = mapField;
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField, oldMapField);
            unsafe.putObject(message, fieldOffset, mapField);
        }
        return this.decodeMapEntry(data, position, limit, this.mapFieldSchema.forMapMetadata(mapDefaultEntry), this.mapFieldSchema.forMutableMapData(mapField), registers);
    }
    
    private int parseOneofField(final T message, final byte[] data, int position, final int limit, final int tag, final int number, final int wireType, final int typeAndOffset, final int fieldType, final long fieldOffset, final int bufferPosition, final ArrayDecoders.Registers registers) throws IOException {
        final Unsafe unsafe = MessageSchema.UNSAFE;
        final long oneofCaseOffset = this.buffer[bufferPosition + 2] & 0xFFFFF;
        switch (fieldType) {
            case 51: {
                if (wireType == 1) {
                    unsafe.putObject(message, fieldOffset, ArrayDecoders.decodeDouble(data, position));
                    position += 8;
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 52: {
                if (wireType == 5) {
                    unsafe.putObject(message, fieldOffset, ArrayDecoders.decodeFloat(data, position));
                    position += 4;
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 53:
            case 54: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint64(data, position, registers);
                    unsafe.putObject(message, fieldOffset, registers.long1);
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 55:
            case 62: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint32(data, position, registers);
                    unsafe.putObject(message, fieldOffset, registers.int1);
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 56:
            case 65: {
                if (wireType == 1) {
                    unsafe.putObject(message, fieldOffset, ArrayDecoders.decodeFixed64(data, position));
                    position += 8;
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 57:
            case 64: {
                if (wireType == 5) {
                    unsafe.putObject(message, fieldOffset, ArrayDecoders.decodeFixed32(data, position));
                    position += 4;
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 58: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint64(data, position, registers);
                    unsafe.putObject(message, fieldOffset, registers.long1 != 0L);
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 59: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodeVarint32(data, position, registers);
                    final int length = registers.int1;
                    if (length == 0) {
                        unsafe.putObject(message, fieldOffset, "");
                    }
                    else {
                        if ((typeAndOffset & 0x20000000) != 0x0 && !Utf8.isValidUtf8(data, position, position + length)) {
                            throw InvalidProtocolBufferException.invalidUtf8();
                        }
                        final String value = new String(data, position, length, Internal.UTF_8);
                        unsafe.putObject(message, fieldOffset, value);
                        position += length;
                    }
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 60: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodeMessageField(this.getMessageFieldSchema(bufferPosition), data, position, limit, registers);
                    final Object oldValue = (unsafe.getInt(message, oneofCaseOffset) == number) ? unsafe.getObject(message, fieldOffset) : null;
                    if (oldValue == null) {
                        unsafe.putObject(message, fieldOffset, registers.object1);
                    }
                    else {
                        unsafe.putObject(message, fieldOffset, Internal.mergeMessage(oldValue, registers.object1));
                    }
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 61: {
                if (wireType == 2) {
                    position = ArrayDecoders.decodeBytes(data, position, registers);
                    unsafe.putObject(message, fieldOffset, registers.object1);
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 63: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint32(data, position, registers);
                    final int enumValue = registers.int1;
                    final Internal.EnumVerifier enumVerifier = this.getEnumFieldVerifier(bufferPosition);
                    if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
                        unsafe.putObject(message, fieldOffset, enumValue);
                        unsafe.putInt(message, oneofCaseOffset, number);
                    }
                    else {
                        getMutableUnknownFields(message).storeField(tag, (long)enumValue);
                    }
                    break;
                }
                break;
            }
            case 66: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint32(data, position, registers);
                    unsafe.putObject(message, fieldOffset, CodedInputStream.decodeZigZag32(registers.int1));
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 67: {
                if (wireType == 0) {
                    position = ArrayDecoders.decodeVarint64(data, position, registers);
                    unsafe.putObject(message, fieldOffset, CodedInputStream.decodeZigZag64(registers.long1));
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
            case 68: {
                if (wireType == 3) {
                    final int endTag = (tag & 0xFFFFFFF8) | 0x4;
                    position = ArrayDecoders.decodeGroupField(this.getMessageFieldSchema(bufferPosition), data, position, limit, endTag, registers);
                    final Object oldValue2 = (unsafe.getInt(message, oneofCaseOffset) == number) ? unsafe.getObject(message, fieldOffset) : null;
                    if (oldValue2 == null) {
                        unsafe.putObject(message, fieldOffset, registers.object1);
                    }
                    else {
                        unsafe.putObject(message, fieldOffset, Internal.mergeMessage(oldValue2, registers.object1));
                    }
                    unsafe.putInt(message, oneofCaseOffset, number);
                    break;
                }
                break;
            }
        }
        return position;
    }
    
    private Schema getMessageFieldSchema(final int pos) {
        final int index = pos / 3 * 2;
        Schema schema = (Schema)this.objects[index];
        if (schema != null) {
            return schema;
        }
        schema = Protobuf.getInstance().schemaFor((Class<Object>)this.objects[index + 1]);
        return (Schema)(this.objects[index] = schema);
    }
    
    private Object getMapFieldDefaultEntry(final int pos) {
        return this.objects[pos / 3 * 2];
    }
    
    private Internal.EnumVerifier getEnumFieldVerifier(final int pos) {
        return (Internal.EnumVerifier)this.objects[pos / 3 * 2 + 1];
    }
    
    int parseProto2Message(final T message, final byte[] data, int position, final int limit, final int endGroup, final ArrayDecoders.Registers registers) throws IOException {
        final Unsafe unsafe = MessageSchema.UNSAFE;
        int currentPresenceFieldOffset = -1;
        int currentPresenceField = 0;
        int tag = 0;
        int oldNumber = -1;
        int pos = 0;
        while (position < limit) {
            tag = data[position++];
            if (tag < 0) {
                position = ArrayDecoders.decodeVarint32(tag, data, position, registers);
                tag = registers.int1;
            }
            final int number = tag >>> 3;
            final int wireType = tag & 0x7;
            if (number > oldNumber) {
                pos = this.positionForFieldNumber(number, pos / 3);
            }
            else {
                pos = this.positionForFieldNumber(number);
            }
            oldNumber = number;
            if (pos == -1) {
                pos = 0;
            }
            else {
                final int typeAndOffset = this.buffer[pos + 1];
                final int fieldType = type(typeAndOffset);
                final long fieldOffset = offset(typeAndOffset);
                if (fieldType <= 17) {
                    final int presenceMaskAndOffset = this.buffer[pos + 2];
                    final int presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                    final int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
                    if (presenceFieldOffset != currentPresenceFieldOffset) {
                        if (currentPresenceFieldOffset != -1) {
                            unsafe.putInt(message, (long)currentPresenceFieldOffset, currentPresenceField);
                        }
                        currentPresenceFieldOffset = presenceFieldOffset;
                        currentPresenceField = unsafe.getInt(message, (long)presenceFieldOffset);
                    }
                    switch (fieldType) {
                        case 0: {
                            if (wireType == 1) {
                                UnsafeUtil.putDouble(message, fieldOffset, ArrayDecoders.decodeDouble(data, position));
                                position += 8;
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 1: {
                            if (wireType == 5) {
                                UnsafeUtil.putFloat(message, fieldOffset, ArrayDecoders.decodeFloat(data, position));
                                position += 4;
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 2:
                        case 3: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                unsafe.putLong(message, fieldOffset, registers.long1);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 4:
                        case 11: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                unsafe.putInt(message, fieldOffset, registers.int1);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 5:
                        case 14: {
                            if (wireType == 1) {
                                unsafe.putLong(message, fieldOffset, ArrayDecoders.decodeFixed64(data, position));
                                position += 8;
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 6:
                        case 13: {
                            if (wireType == 5) {
                                unsafe.putInt(message, fieldOffset, ArrayDecoders.decodeFixed32(data, position));
                                position += 4;
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 7: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                UnsafeUtil.putBoolean(message, fieldOffset, registers.long1 != 0L);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 8: {
                            if (wireType == 2) {
                                if ((typeAndOffset & 0x20000000) == 0x0) {
                                    position = ArrayDecoders.decodeString(data, position, registers);
                                }
                                else {
                                    position = ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
                                }
                                unsafe.putObject(message, fieldOffset, registers.object1);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 9: {
                            if (wireType == 2) {
                                position = ArrayDecoders.decodeMessageField(this.getMessageFieldSchema(pos), data, position, limit, registers);
                                if ((currentPresenceField & presenceMask) == 0x0) {
                                    unsafe.putObject(message, fieldOffset, registers.object1);
                                }
                                else {
                                    unsafe.putObject(message, fieldOffset, Internal.mergeMessage(unsafe.getObject(message, fieldOffset), registers.object1));
                                }
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 10: {
                            if (wireType == 2) {
                                position = ArrayDecoders.decodeBytes(data, position, registers);
                                unsafe.putObject(message, fieldOffset, registers.object1);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 12: {
                            if (wireType != 0) {
                                break;
                            }
                            position = ArrayDecoders.decodeVarint32(data, position, registers);
                            final int enumValue = registers.int1;
                            final Internal.EnumVerifier enumVerifier = this.getEnumFieldVerifier(pos);
                            if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
                                unsafe.putInt(message, fieldOffset, enumValue);
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            getMutableUnknownFields(message).storeField(tag, (long)enumValue);
                            continue;
                        }
                        case 15: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                unsafe.putInt(message, fieldOffset, CodedInputStream.decodeZigZag32(registers.int1));
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 16: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                unsafe.putLong(message, fieldOffset, CodedInputStream.decodeZigZag64(registers.long1));
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                        case 17: {
                            if (wireType == 3) {
                                final int endTag = number << 3 | 0x4;
                                position = ArrayDecoders.decodeGroupField(this.getMessageFieldSchema(pos), data, position, limit, endTag, registers);
                                if ((currentPresenceField & presenceMask) == 0x0) {
                                    unsafe.putObject(message, fieldOffset, registers.object1);
                                }
                                else {
                                    unsafe.putObject(message, fieldOffset, Internal.mergeMessage(unsafe.getObject(message, fieldOffset), registers.object1));
                                }
                                currentPresenceField |= presenceMask;
                                continue;
                            }
                            break;
                        }
                    }
                }
                else if (fieldType == 27) {
                    if (wireType == 2) {
                        Internal.ProtobufList<?> list = (Internal.ProtobufList<?>)unsafe.getObject(message, fieldOffset);
                        if (!list.isModifiable()) {
                            final int size = list.size();
                            list = list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
                            unsafe.putObject(message, fieldOffset, list);
                        }
                        position = ArrayDecoders.decodeMessageList(this.getMessageFieldSchema(pos), tag, data, position, limit, list, registers);
                        continue;
                    }
                }
                else if (fieldType <= 49) {
                    final int oldPosition = position;
                    position = this.parseRepeatedField(message, data, position, limit, tag, number, wireType, pos, typeAndOffset, fieldType, fieldOffset, registers);
                    if (position != oldPosition) {
                        continue;
                    }
                }
                else if (fieldType == 50) {
                    if (wireType == 2) {
                        final int oldPosition = position;
                        position = this.parseMapField(message, data, position, limit, pos, fieldOffset, registers);
                        if (position != oldPosition) {
                            continue;
                        }
                    }
                }
                else {
                    final int oldPosition = position;
                    position = this.parseOneofField(message, data, position, limit, tag, number, wireType, typeAndOffset, fieldType, fieldOffset, pos, registers);
                    if (position != oldPosition) {
                        continue;
                    }
                }
            }
            if (tag == endGroup && endGroup != 0) {
                break;
            }
            if (this.hasExtensions && registers.extensionRegistry != ExtensionRegistryLite.getEmptyRegistry()) {
                position = ArrayDecoders.decodeExtensionOrUnknownField(tag, data, position, limit, message, this.defaultInstance, (UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite>)this.unknownFieldSchema, registers);
            }
            else {
                position = ArrayDecoders.decodeUnknownField(tag, data, position, limit, getMutableUnknownFields(message), registers);
            }
        }
        if (currentPresenceFieldOffset != -1) {
            unsafe.putInt(message, (long)currentPresenceFieldOffset, currentPresenceField);
        }
        UnknownFieldSetLite unknownFields = null;
        for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; ++i) {
            unknownFields = this.filterMapUnknownEnumValues(message, this.intArray[i], unknownFields, this.unknownFieldSchema);
        }
        if (unknownFields != null) {
            this.unknownFieldSchema.setBuilderToMessage(message, unknownFields);
        }
        if (endGroup == 0) {
            if (position != limit) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        }
        else if (position > limit || tag != endGroup) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return position;
    }
    
    private int parseProto3Message(final T message, final byte[] data, int position, final int limit, final ArrayDecoders.Registers registers) throws IOException {
        final Unsafe unsafe = MessageSchema.UNSAFE;
        int tag = 0;
        int oldNumber = -1;
        int pos = 0;
        while (position < limit) {
            tag = data[position++];
            if (tag < 0) {
                position = ArrayDecoders.decodeVarint32(tag, data, position, registers);
                tag = registers.int1;
            }
            final int number = tag >>> 3;
            final int wireType = tag & 0x7;
            if (number > oldNumber) {
                pos = this.positionForFieldNumber(number, pos / 3);
            }
            else {
                pos = this.positionForFieldNumber(number);
            }
            oldNumber = number;
            if (pos == -1) {
                pos = 0;
            }
            else {
                final int typeAndOffset = this.buffer[pos + 1];
                final int fieldType = type(typeAndOffset);
                final long fieldOffset = offset(typeAndOffset);
                if (fieldType <= 17) {
                    switch (fieldType) {
                        case 0: {
                            if (wireType == 1) {
                                UnsafeUtil.putDouble(message, fieldOffset, ArrayDecoders.decodeDouble(data, position));
                                position += 8;
                                continue;
                            }
                            break;
                        }
                        case 1: {
                            if (wireType == 5) {
                                UnsafeUtil.putFloat(message, fieldOffset, ArrayDecoders.decodeFloat(data, position));
                                position += 4;
                                continue;
                            }
                            break;
                        }
                        case 2:
                        case 3: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                unsafe.putLong(message, fieldOffset, registers.long1);
                                continue;
                            }
                            break;
                        }
                        case 4:
                        case 11: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                unsafe.putInt(message, fieldOffset, registers.int1);
                                continue;
                            }
                            break;
                        }
                        case 5:
                        case 14: {
                            if (wireType == 1) {
                                unsafe.putLong(message, fieldOffset, ArrayDecoders.decodeFixed64(data, position));
                                position += 8;
                                continue;
                            }
                            break;
                        }
                        case 6:
                        case 13: {
                            if (wireType == 5) {
                                unsafe.putInt(message, fieldOffset, ArrayDecoders.decodeFixed32(data, position));
                                position += 4;
                                continue;
                            }
                            break;
                        }
                        case 7: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                UnsafeUtil.putBoolean(message, fieldOffset, registers.long1 != 0L);
                                continue;
                            }
                            break;
                        }
                        case 8: {
                            if (wireType == 2) {
                                if ((typeAndOffset & 0x20000000) == 0x0) {
                                    position = ArrayDecoders.decodeString(data, position, registers);
                                }
                                else {
                                    position = ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
                                }
                                unsafe.putObject(message, fieldOffset, registers.object1);
                                continue;
                            }
                            break;
                        }
                        case 9: {
                            if (wireType != 2) {
                                break;
                            }
                            position = ArrayDecoders.decodeMessageField(this.getMessageFieldSchema(pos), data, position, limit, registers);
                            final Object oldValue = unsafe.getObject(message, fieldOffset);
                            if (oldValue == null) {
                                unsafe.putObject(message, fieldOffset, registers.object1);
                                continue;
                            }
                            unsafe.putObject(message, fieldOffset, Internal.mergeMessage(oldValue, registers.object1));
                            continue;
                        }
                        case 10: {
                            if (wireType == 2) {
                                position = ArrayDecoders.decodeBytes(data, position, registers);
                                unsafe.putObject(message, fieldOffset, registers.object1);
                                continue;
                            }
                            break;
                        }
                        case 12: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                unsafe.putInt(message, fieldOffset, registers.int1);
                                continue;
                            }
                            break;
                        }
                        case 15: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint32(data, position, registers);
                                unsafe.putInt(message, fieldOffset, CodedInputStream.decodeZigZag32(registers.int1));
                                continue;
                            }
                            break;
                        }
                        case 16: {
                            if (wireType == 0) {
                                position = ArrayDecoders.decodeVarint64(data, position, registers);
                                unsafe.putLong(message, fieldOffset, CodedInputStream.decodeZigZag64(registers.long1));
                                continue;
                            }
                            break;
                        }
                    }
                }
                else if (fieldType == 27) {
                    if (wireType == 2) {
                        Internal.ProtobufList<?> list = (Internal.ProtobufList<?>)unsafe.getObject(message, fieldOffset);
                        if (!list.isModifiable()) {
                            final int size = list.size();
                            list = list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
                            unsafe.putObject(message, fieldOffset, list);
                        }
                        position = ArrayDecoders.decodeMessageList(this.getMessageFieldSchema(pos), tag, data, position, limit, list, registers);
                        continue;
                    }
                }
                else if (fieldType <= 49) {
                    final int oldPosition = position;
                    position = this.parseRepeatedField(message, data, position, limit, tag, number, wireType, pos, typeAndOffset, fieldType, fieldOffset, registers);
                    if (position != oldPosition) {
                        continue;
                    }
                }
                else if (fieldType == 50) {
                    if (wireType == 2) {
                        final int oldPosition = position;
                        position = this.parseMapField(message, data, position, limit, pos, fieldOffset, registers);
                        if (position != oldPosition) {
                            continue;
                        }
                    }
                }
                else {
                    final int oldPosition = position;
                    position = this.parseOneofField(message, data, position, limit, tag, number, wireType, typeAndOffset, fieldType, fieldOffset, pos, registers);
                    if (position != oldPosition) {
                        continue;
                    }
                }
            }
            position = ArrayDecoders.decodeUnknownField(tag, data, position, limit, getMutableUnknownFields(message), registers);
        }
        if (position != limit) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return position;
    }
    
    @Override
    public void mergeFrom(final T message, final byte[] data, final int position, final int limit, final ArrayDecoders.Registers registers) throws IOException {
        if (this.proto3) {
            this.parseProto3Message(message, data, position, limit, registers);
        }
        else {
            this.parseProto2Message(message, data, position, limit, 0, registers);
        }
    }
    
    @Override
    public void makeImmutable(final T message) {
        for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; ++i) {
            final long offset = offset(this.typeAndOffsetAt(this.intArray[i]));
            final Object mapField = UnsafeUtil.getObject(message, offset);
            if (mapField != null) {
                UnsafeUtil.putObject(message, offset, this.mapFieldSchema.toImmutable(mapField));
            }
        }
        for (int length = this.intArray.length, j = this.repeatedFieldOffsetStart; j < length; ++j) {
            this.listFieldSchema.makeImmutableListAt(message, this.intArray[j]);
        }
        this.unknownFieldSchema.makeImmutable(message);
        if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(message);
        }
    }
    
    private final <K, V> void mergeMap(final Object message, final int pos, final Object mapDefaultEntry, final ExtensionRegistryLite extensionRegistry, final Reader reader) throws IOException {
        final long offset = offset(this.typeAndOffsetAt(pos));
        Object mapField = UnsafeUtil.getObject(message, offset);
        if (mapField == null) {
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            UnsafeUtil.putObject(message, offset, mapField);
        }
        else if (this.mapFieldSchema.isImmutable(mapField)) {
            final Object oldMapField = mapField;
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField, oldMapField);
            UnsafeUtil.putObject(message, offset, mapField);
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(mapField), this.mapFieldSchema.forMapMetadata(mapDefaultEntry), extensionRegistry);
    }
    
    private final <UT, UB> UB filterMapUnknownEnumValues(final Object message, final int pos, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        final int fieldNumber = this.numberAt(pos);
        final long offset = offset(this.typeAndOffsetAt(pos));
        final Object mapField = UnsafeUtil.getObject(message, offset);
        if (mapField == null) {
            return unknownFields;
        }
        final Internal.EnumVerifier enumVerifier = this.getEnumFieldVerifier(pos);
        if (enumVerifier == null) {
            return unknownFields;
        }
        final Map<?, ?> mapData = this.mapFieldSchema.forMutableMapData(mapField);
        unknownFields = this.filterUnknownEnumMap(pos, fieldNumber, mapData, enumVerifier, unknownFields, unknownFieldSchema);
        return unknownFields;
    }
    
    private final <K, V, UT, UB> UB filterUnknownEnumMap(final int pos, final int number, final Map<K, V> mapData, final Internal.EnumVerifier enumVerifier, UB unknownFields, final UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        final MapEntryLite.Metadata<K, V> metadata = (MapEntryLite.Metadata<K, V>)this.mapFieldSchema.forMapMetadata(this.getMapFieldDefaultEntry(pos));
        final Iterator<Map.Entry<K, V>> it = mapData.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<K, V> entry = it.next();
            if (!enumVerifier.isInRange((int)entry.getValue())) {
                if (unknownFields == null) {
                    unknownFields = unknownFieldSchema.newBuilder();
                }
                final int entrySize = MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue());
                final ByteString.CodedBuilder codedBuilder = ByteString.newCodedBuilder(entrySize);
                final CodedOutputStream codedOutput = codedBuilder.getCodedOutput();
                try {
                    MapEntryLite.writeTo(codedOutput, metadata, entry.getKey(), entry.getValue());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                unknownFieldSchema.addLengthDelimited(unknownFields, number, codedBuilder.build());
                it.remove();
            }
        }
        return unknownFields;
    }
    
    @Override
    public final boolean isInitialized(final T message) {
        int currentPresenceFieldOffset = -1;
        int currentPresenceField = 0;
        for (int i = 0; i < this.checkInitializedCount; ++i) {
            final int pos = this.intArray[i];
            final int number = this.numberAt(pos);
            final int typeAndOffset = this.typeAndOffsetAt(pos);
            int presenceMaskAndOffset = 0;
            int presenceMask = 0;
            if (!this.proto3) {
                presenceMaskAndOffset = this.buffer[pos + 2];
                final int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
                presenceMask = 1 << (presenceMaskAndOffset >>> 20);
                if (presenceFieldOffset != currentPresenceFieldOffset) {
                    currentPresenceFieldOffset = presenceFieldOffset;
                    currentPresenceField = MessageSchema.UNSAFE.getInt(message, (long)presenceFieldOffset);
                }
            }
            if (isRequired(typeAndOffset) && !this.isFieldPresent(message, pos, currentPresenceField, presenceMask)) {
                return false;
            }
            switch (type(typeAndOffset)) {
                case 9:
                case 17: {
                    if (this.isFieldPresent(message, pos, currentPresenceField, presenceMask) && !isInitialized(message, typeAndOffset, this.getMessageFieldSchema(pos))) {
                        return false;
                    }
                    break;
                }
                case 27:
                case 49: {
                    if (!this.isListInitialized(message, typeAndOffset, pos)) {
                        return false;
                    }
                    break;
                }
                case 60:
                case 68: {
                    if (this.isOneofPresent(message, number, pos) && !isInitialized(message, typeAndOffset, this.getMessageFieldSchema(pos))) {
                        return false;
                    }
                    break;
                }
                case 50: {
                    if (!this.isMapInitialized(message, typeAndOffset, pos)) {
                        return false;
                    }
                    break;
                }
            }
        }
        return !this.hasExtensions || this.extensionSchema.getExtensions(message).isInitialized();
    }
    
    private static boolean isInitialized(final Object message, final int typeAndOffset, final Schema schema) {
        final Object nested = UnsafeUtil.getObject(message, offset(typeAndOffset));
        return schema.isInitialized(nested);
    }
    
    private <N> boolean isListInitialized(final Object message, final int typeAndOffset, final int pos) {
        final List<N> list = (List<N>)UnsafeUtil.getObject(message, offset(typeAndOffset));
        if (list.isEmpty()) {
            return true;
        }
        final Schema schema = this.getMessageFieldSchema(pos);
        for (int i = 0; i < list.size(); ++i) {
            final N nested = list.get(i);
            if (!schema.isInitialized(nested)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isMapInitialized(final T message, final int typeAndOffset, final int pos) {
        final Map<?, ?> map = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(message, offset(typeAndOffset)));
        if (map.isEmpty()) {
            return true;
        }
        final Object mapDefaultEntry = this.getMapFieldDefaultEntry(pos);
        final MapEntryLite.Metadata<?, ?> metadata = this.mapFieldSchema.forMapMetadata(mapDefaultEntry);
        if (metadata.valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        Schema schema = null;
        for (final Object nested : map.values()) {
            if (schema == null) {
                schema = Protobuf.getInstance().schemaFor(nested.getClass());
            }
            if (!schema.isInitialized(nested)) {
                return false;
            }
        }
        return true;
    }
    
    private void writeString(final int fieldNumber, final Object value, final Writer writer) throws IOException {
        if (value instanceof String) {
            writer.writeString(fieldNumber, (String)value);
        }
        else {
            writer.writeBytes(fieldNumber, (ByteString)value);
        }
    }
    
    private void readString(final Object message, final int typeAndOffset, final Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readStringRequireUtf8());
        }
        else if (this.lite) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readString());
        }
        else {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
        }
    }
    
    private void readStringList(final Object message, final int typeAndOffset, final Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        }
        else {
            reader.readStringList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        }
    }
    
    private <E> void readMessageList(final Object message, final int typeAndOffset, final Reader reader, final Schema<E> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final long offset = offset(typeAndOffset);
        reader.readMessageList(this.listFieldSchema.mutableListAt(message, offset), schema, extensionRegistry);
    }
    
    private <E> void readGroupList(final Object message, final long offset, final Reader reader, final Schema<E> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(message, offset), schema, extensionRegistry);
    }
    
    private int numberAt(final int pos) {
        return this.buffer[pos];
    }
    
    private int typeAndOffsetAt(final int pos) {
        return this.buffer[pos + 1];
    }
    
    private int presenceMaskAndOffsetAt(final int pos) {
        return this.buffer[pos + 2];
    }
    
    private static int type(final int value) {
        return (value & 0xFF00000) >>> 20;
    }
    
    private static boolean isRequired(final int value) {
        return (value & 0x10000000) != 0x0;
    }
    
    private static boolean isEnforceUtf8(final int value) {
        return (value & 0x20000000) != 0x0;
    }
    
    private static long offset(final int value) {
        return value & 0xFFFFF;
    }
    
    private static <T> double doubleAt(final T message, final long offset) {
        return UnsafeUtil.getDouble(message, offset);
    }
    
    private static <T> float floatAt(final T message, final long offset) {
        return UnsafeUtil.getFloat(message, offset);
    }
    
    private static <T> int intAt(final T message, final long offset) {
        return UnsafeUtil.getInt(message, offset);
    }
    
    private static <T> long longAt(final T message, final long offset) {
        return UnsafeUtil.getLong(message, offset);
    }
    
    private static <T> boolean booleanAt(final T message, final long offset) {
        return UnsafeUtil.getBoolean(message, offset);
    }
    
    private static <T> double oneofDoubleAt(final T message, final long offset) {
        return (double)UnsafeUtil.getObject(message, offset);
    }
    
    private static <T> float oneofFloatAt(final T message, final long offset) {
        return (float)UnsafeUtil.getObject(message, offset);
    }
    
    private static <T> int oneofIntAt(final T message, final long offset) {
        return (int)UnsafeUtil.getObject(message, offset);
    }
    
    private static <T> long oneofLongAt(final T message, final long offset) {
        return (long)UnsafeUtil.getObject(message, offset);
    }
    
    private static <T> boolean oneofBooleanAt(final T message, final long offset) {
        return (boolean)UnsafeUtil.getObject(message, offset);
    }
    
    private boolean arePresentForEquals(final T message, final T other, final int pos) {
        return this.isFieldPresent(message, pos) == this.isFieldPresent(other, pos);
    }
    
    private boolean isFieldPresent(final T message, final int pos, final int presenceField, final int presenceMask) {
        if (this.proto3) {
            return this.isFieldPresent(message, pos);
        }
        return (presenceField & presenceMask) != 0x0;
    }
    
    private boolean isFieldPresent(final T message, final int pos) {
        if (!this.proto3) {
            final int presenceMaskAndOffset = this.presenceMaskAndOffsetAt(pos);
            final int presenceMask = 1 << (presenceMaskAndOffset >>> 20);
            return (UnsafeUtil.getInt(message, presenceMaskAndOffset & 0xFFFFF) & presenceMask) != 0x0;
        }
        final int typeAndOffset = this.typeAndOffsetAt(pos);
        final long offset = offset(typeAndOffset);
        switch (type(typeAndOffset)) {
            case 0: {
                return UnsafeUtil.getDouble(message, offset) != 0.0;
            }
            case 1: {
                return UnsafeUtil.getFloat(message, offset) != 0.0f;
            }
            case 2: {
                return UnsafeUtil.getLong(message, offset) != 0L;
            }
            case 3: {
                return UnsafeUtil.getLong(message, offset) != 0L;
            }
            case 4: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 5: {
                return UnsafeUtil.getLong(message, offset) != 0L;
            }
            case 6: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 7: {
                return UnsafeUtil.getBoolean(message, offset);
            }
            case 8: {
                final Object value = UnsafeUtil.getObject(message, offset);
                if (value instanceof String) {
                    return !((String)value).isEmpty();
                }
                if (value instanceof ByteString) {
                    return !ByteString.EMPTY.equals(value);
                }
                throw new IllegalArgumentException();
            }
            case 9: {
                return UnsafeUtil.getObject(message, offset) != null;
            }
            case 10: {
                return !ByteString.EMPTY.equals(UnsafeUtil.getObject(message, offset));
            }
            case 11: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 12: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 13: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 14: {
                return UnsafeUtil.getLong(message, offset) != 0L;
            }
            case 15: {
                return UnsafeUtil.getInt(message, offset) != 0;
            }
            case 16: {
                return UnsafeUtil.getLong(message, offset) != 0L;
            }
            case 17: {
                return UnsafeUtil.getObject(message, offset) != null;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private void setFieldPresent(final T message, final int pos) {
        if (this.proto3) {
            return;
        }
        final int presenceMaskAndOffset = this.presenceMaskAndOffsetAt(pos);
        final int presenceMask = 1 << (presenceMaskAndOffset >>> 20);
        final long presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
        UnsafeUtil.putInt(message, presenceFieldOffset, UnsafeUtil.getInt(message, presenceFieldOffset) | presenceMask);
    }
    
    private boolean isOneofPresent(final T message, final int fieldNumber, final int pos) {
        final int presenceMaskAndOffset = this.presenceMaskAndOffsetAt(pos);
        return UnsafeUtil.getInt(message, presenceMaskAndOffset & 0xFFFFF) == fieldNumber;
    }
    
    private boolean isOneofCaseEqual(final T message, final T other, final int pos) {
        final int presenceMaskAndOffset = this.presenceMaskAndOffsetAt(pos);
        return UnsafeUtil.getInt(message, presenceMaskAndOffset & 0xFFFFF) == UnsafeUtil.getInt(other, presenceMaskAndOffset & 0xFFFFF);
    }
    
    private void setOneofPresent(final T message, final int fieldNumber, final int pos) {
        final int presenceMaskAndOffset = this.presenceMaskAndOffsetAt(pos);
        UnsafeUtil.putInt(message, presenceMaskAndOffset & 0xFFFFF, fieldNumber);
    }
    
    private int positionForFieldNumber(final int number) {
        if (number >= this.minFieldNumber && number <= this.maxFieldNumber) {
            return this.slowPositionForFieldNumber(number, 0);
        }
        return -1;
    }
    
    private int positionForFieldNumber(final int number, final int min) {
        if (number >= this.minFieldNumber && number <= this.maxFieldNumber) {
            return this.slowPositionForFieldNumber(number, min);
        }
        return -1;
    }
    
    private int slowPositionForFieldNumber(final int number, int min) {
        int max = this.buffer.length / 3 - 1;
        while (min <= max) {
            final int mid = max + min >>> 1;
            final int pos = mid * 3;
            final int midFieldNumber = this.numberAt(pos);
            if (number == midFieldNumber) {
                return pos;
            }
            if (number < midFieldNumber) {
                max = mid - 1;
            }
            else {
                min = mid + 1;
            }
        }
        return -1;
    }
    
    int getSchemaSize() {
        return this.buffer.length * 3;
    }
    
    static {
        EMPTY_INT_ARRAY = new int[0];
        UNSAFE = UnsafeUtil.getUnsafe();
    }
}
