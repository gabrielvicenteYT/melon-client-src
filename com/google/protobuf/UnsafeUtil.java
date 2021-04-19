package com.google.protobuf;

import sun.misc.*;
import java.lang.reflect.*;
import java.security.*;
import java.util.logging.*;
import java.nio.*;

final class UnsafeUtil
{
    private static final Logger logger;
    private static final Unsafe UNSAFE;
    private static final Class<?> MEMORY_CLASS;
    private static final boolean IS_ANDROID_64;
    private static final boolean IS_ANDROID_32;
    private static final MemoryAccessor MEMORY_ACCESSOR;
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    static final long BYTE_ARRAY_BASE_OFFSET;
    private static final long BOOLEAN_ARRAY_BASE_OFFSET;
    private static final long BOOLEAN_ARRAY_INDEX_SCALE;
    private static final long INT_ARRAY_BASE_OFFSET;
    private static final long INT_ARRAY_INDEX_SCALE;
    private static final long LONG_ARRAY_BASE_OFFSET;
    private static final long LONG_ARRAY_INDEX_SCALE;
    private static final long FLOAT_ARRAY_BASE_OFFSET;
    private static final long FLOAT_ARRAY_INDEX_SCALE;
    private static final long DOUBLE_ARRAY_BASE_OFFSET;
    private static final long DOUBLE_ARRAY_INDEX_SCALE;
    private static final long OBJECT_ARRAY_BASE_OFFSET;
    private static final long OBJECT_ARRAY_INDEX_SCALE;
    private static final long BUFFER_ADDRESS_OFFSET;
    private static final int STRIDE = 8;
    private static final int STRIDE_ALIGNMENT_MASK = 7;
    private static final int BYTE_ARRAY_ALIGNMENT;
    static final boolean IS_BIG_ENDIAN;
    
    private UnsafeUtil() {
    }
    
    static boolean hasUnsafeArrayOperations() {
        return UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS;
    }
    
    static boolean hasUnsafeByteBufferOperations() {
        return UnsafeUtil.HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }
    
    static boolean isAndroid64() {
        return UnsafeUtil.IS_ANDROID_64;
    }
    
    static <T> T allocateInstance(final Class<T> clazz) {
        try {
            return (T)UnsafeUtil.UNSAFE.allocateInstance(clazz);
        }
        catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }
    
    static long objectFieldOffset(final Field field) {
        return UnsafeUtil.MEMORY_ACCESSOR.objectFieldOffset(field);
    }
    
    private static int arrayBaseOffset(final Class<?> clazz) {
        return UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS ? UnsafeUtil.MEMORY_ACCESSOR.arrayBaseOffset(clazz) : -1;
    }
    
    private static int arrayIndexScale(final Class<?> clazz) {
        return UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS ? UnsafeUtil.MEMORY_ACCESSOR.arrayIndexScale(clazz) : -1;
    }
    
    static byte getByte(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getByte(target, offset);
    }
    
    static void putByte(final Object target, final long offset, final byte value) {
        UnsafeUtil.MEMORY_ACCESSOR.putByte(target, offset, value);
    }
    
    static int getInt(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getInt(target, offset);
    }
    
    static void putInt(final Object target, final long offset, final int value) {
        UnsafeUtil.MEMORY_ACCESSOR.putInt(target, offset, value);
    }
    
    static long getLong(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getLong(target, offset);
    }
    
    static void putLong(final Object target, final long offset, final long value) {
        UnsafeUtil.MEMORY_ACCESSOR.putLong(target, offset, value);
    }
    
    static boolean getBoolean(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getBoolean(target, offset);
    }
    
    static void putBoolean(final Object target, final long offset, final boolean value) {
        UnsafeUtil.MEMORY_ACCESSOR.putBoolean(target, offset, value);
    }
    
    static float getFloat(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getFloat(target, offset);
    }
    
    static void putFloat(final Object target, final long offset, final float value) {
        UnsafeUtil.MEMORY_ACCESSOR.putFloat(target, offset, value);
    }
    
    static double getDouble(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getDouble(target, offset);
    }
    
    static void putDouble(final Object target, final long offset, final double value) {
        UnsafeUtil.MEMORY_ACCESSOR.putDouble(target, offset, value);
    }
    
    static Object getObject(final Object target, final long offset) {
        return UnsafeUtil.MEMORY_ACCESSOR.getObject(target, offset);
    }
    
    static void putObject(final Object target, final long offset, final Object value) {
        UnsafeUtil.MEMORY_ACCESSOR.putObject(target, offset, value);
    }
    
    static byte getByte(final byte[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getByte(target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + index);
    }
    
    static void putByte(final byte[] target, final long index, final byte value) {
        UnsafeUtil.MEMORY_ACCESSOR.putByte(target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + index, value);
    }
    
    static int getInt(final int[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getInt(target, UnsafeUtil.INT_ARRAY_BASE_OFFSET + index * UnsafeUtil.INT_ARRAY_INDEX_SCALE);
    }
    
    static void putInt(final int[] target, final long index, final int value) {
        UnsafeUtil.MEMORY_ACCESSOR.putInt(target, UnsafeUtil.INT_ARRAY_BASE_OFFSET + index * UnsafeUtil.INT_ARRAY_INDEX_SCALE, value);
    }
    
    static long getLong(final long[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getLong(target, UnsafeUtil.LONG_ARRAY_BASE_OFFSET + index * UnsafeUtil.LONG_ARRAY_INDEX_SCALE);
    }
    
    static void putLong(final long[] target, final long index, final long value) {
        UnsafeUtil.MEMORY_ACCESSOR.putLong(target, UnsafeUtil.LONG_ARRAY_BASE_OFFSET + index * UnsafeUtil.LONG_ARRAY_INDEX_SCALE, value);
    }
    
    static boolean getBoolean(final boolean[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getBoolean(target, UnsafeUtil.BOOLEAN_ARRAY_BASE_OFFSET + index * UnsafeUtil.BOOLEAN_ARRAY_INDEX_SCALE);
    }
    
    static void putBoolean(final boolean[] target, final long index, final boolean value) {
        UnsafeUtil.MEMORY_ACCESSOR.putBoolean(target, UnsafeUtil.BOOLEAN_ARRAY_BASE_OFFSET + index * UnsafeUtil.BOOLEAN_ARRAY_INDEX_SCALE, value);
    }
    
    static float getFloat(final float[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getFloat(target, UnsafeUtil.FLOAT_ARRAY_BASE_OFFSET + index * UnsafeUtil.FLOAT_ARRAY_INDEX_SCALE);
    }
    
    static void putFloat(final float[] target, final long index, final float value) {
        UnsafeUtil.MEMORY_ACCESSOR.putFloat(target, UnsafeUtil.FLOAT_ARRAY_BASE_OFFSET + index * UnsafeUtil.FLOAT_ARRAY_INDEX_SCALE, value);
    }
    
    static double getDouble(final double[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getDouble(target, UnsafeUtil.DOUBLE_ARRAY_BASE_OFFSET + index * UnsafeUtil.DOUBLE_ARRAY_INDEX_SCALE);
    }
    
    static void putDouble(final double[] target, final long index, final double value) {
        UnsafeUtil.MEMORY_ACCESSOR.putDouble(target, UnsafeUtil.DOUBLE_ARRAY_BASE_OFFSET + index * UnsafeUtil.DOUBLE_ARRAY_INDEX_SCALE, value);
    }
    
    static Object getObject(final Object[] target, final long index) {
        return UnsafeUtil.MEMORY_ACCESSOR.getObject(target, UnsafeUtil.OBJECT_ARRAY_BASE_OFFSET + index * UnsafeUtil.OBJECT_ARRAY_INDEX_SCALE);
    }
    
    static void putObject(final Object[] target, final long index, final Object value) {
        UnsafeUtil.MEMORY_ACCESSOR.putObject(target, UnsafeUtil.OBJECT_ARRAY_BASE_OFFSET + index * UnsafeUtil.OBJECT_ARRAY_INDEX_SCALE, value);
    }
    
    static void copyMemory(final byte[] src, final long srcIndex, final long targetOffset, final long length) {
        UnsafeUtil.MEMORY_ACCESSOR.copyMemory(src, srcIndex, targetOffset, length);
    }
    
    static void copyMemory(final long srcOffset, final byte[] target, final long targetIndex, final long length) {
        UnsafeUtil.MEMORY_ACCESSOR.copyMemory(srcOffset, target, targetIndex, length);
    }
    
    static void copyMemory(final byte[] src, final long srcIndex, final byte[] target, final long targetIndex, final long length) {
        System.arraycopy(src, (int)srcIndex, target, (int)targetIndex, (int)length);
    }
    
    static byte getByte(final long address) {
        return UnsafeUtil.MEMORY_ACCESSOR.getByte(address);
    }
    
    static void putByte(final long address, final byte value) {
        UnsafeUtil.MEMORY_ACCESSOR.putByte(address, value);
    }
    
    static int getInt(final long address) {
        return UnsafeUtil.MEMORY_ACCESSOR.getInt(address);
    }
    
    static void putInt(final long address, final int value) {
        UnsafeUtil.MEMORY_ACCESSOR.putInt(address, value);
    }
    
    static long getLong(final long address) {
        return UnsafeUtil.MEMORY_ACCESSOR.getLong(address);
    }
    
    static void putLong(final long address, final long value) {
        UnsafeUtil.MEMORY_ACCESSOR.putLong(address, value);
    }
    
    static long addressOffset(final ByteBuffer buffer) {
        return UnsafeUtil.MEMORY_ACCESSOR.getLong(buffer, UnsafeUtil.BUFFER_ADDRESS_OFFSET);
    }
    
    static Object getStaticObject(final Field field) {
        return UnsafeUtil.MEMORY_ACCESSOR.getStaticObject(field);
    }
    
    static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            unsafe = AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>)new PrivilegedExceptionAction<Unsafe>() {
                @Override
                public Unsafe run() throws Exception {
                    final Class<Unsafe> k = Unsafe.class;
                    for (final Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        final Object x = f.get(null);
                        if (k.isInstance(x)) {
                            return k.cast(x);
                        }
                    }
                    return null;
                }
            });
        }
        catch (Throwable t) {}
        return unsafe;
    }
    
    private static MemoryAccessor getMemoryAccessor() {
        if (UnsafeUtil.UNSAFE == null) {
            return null;
        }
        if (!Android.isOnAndroidDevice()) {
            return new JvmMemoryAccessor(UnsafeUtil.UNSAFE);
        }
        if (UnsafeUtil.IS_ANDROID_64) {
            return new Android64MemoryAccessor(UnsafeUtil.UNSAFE);
        }
        if (UnsafeUtil.IS_ANDROID_32) {
            return new Android32MemoryAccessor(UnsafeUtil.UNSAFE);
        }
        return null;
    }
    
    private static boolean supportsUnsafeArrayOperations() {
        if (UnsafeUtil.UNSAFE == null) {
            return false;
        }
        try {
            final Class<?> clazz = UnsafeUtil.UNSAFE.getClass();
            clazz.getMethod("objectFieldOffset", Field.class);
            clazz.getMethod("arrayBaseOffset", Class.class);
            clazz.getMethod("arrayIndexScale", Class.class);
            clazz.getMethod("getInt", Object.class, Long.TYPE);
            clazz.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            clazz.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            clazz.getMethod("getObject", Object.class, Long.TYPE);
            clazz.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (Android.isOnAndroidDevice()) {
                return true;
            }
            clazz.getMethod("getByte", Object.class, Long.TYPE);
            clazz.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            clazz.getMethod("getBoolean", Object.class, Long.TYPE);
            clazz.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            clazz.getMethod("getFloat", Object.class, Long.TYPE);
            clazz.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            clazz.getMethod("getDouble", Object.class, Long.TYPE);
            clazz.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        }
        catch (Throwable e) {
            UnsafeUtil.logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + e);
            return false;
        }
    }
    
    private static boolean supportsUnsafeByteBufferOperations() {
        if (UnsafeUtil.UNSAFE == null) {
            return false;
        }
        try {
            final Class<?> clazz = UnsafeUtil.UNSAFE.getClass();
            clazz.getMethod("objectFieldOffset", Field.class);
            clazz.getMethod("getLong", Object.class, Long.TYPE);
            if (bufferAddressField() == null) {
                return false;
            }
            if (Android.isOnAndroidDevice()) {
                return true;
            }
            clazz.getMethod("getByte", Long.TYPE);
            clazz.getMethod("putByte", Long.TYPE, Byte.TYPE);
            clazz.getMethod("getInt", Long.TYPE);
            clazz.getMethod("putInt", Long.TYPE, Integer.TYPE);
            clazz.getMethod("getLong", Long.TYPE);
            clazz.getMethod("putLong", Long.TYPE, Long.TYPE);
            clazz.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            clazz.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        }
        catch (Throwable e) {
            UnsafeUtil.logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + e);
            return false;
        }
    }
    
    private static boolean determineAndroidSupportByAddressSize(final Class<?> addressClass) {
        if (!Android.isOnAndroidDevice()) {
            return false;
        }
        try {
            final Class<?> clazz = UnsafeUtil.MEMORY_CLASS;
            clazz.getMethod("peekLong", addressClass, Boolean.TYPE);
            clazz.getMethod("pokeLong", addressClass, Long.TYPE, Boolean.TYPE);
            clazz.getMethod("pokeInt", addressClass, Integer.TYPE, Boolean.TYPE);
            clazz.getMethod("peekInt", addressClass, Boolean.TYPE);
            clazz.getMethod("pokeByte", addressClass, Byte.TYPE);
            clazz.getMethod("peekByte", addressClass);
            clazz.getMethod("pokeByteArray", addressClass, byte[].class, Integer.TYPE, Integer.TYPE);
            clazz.getMethod("peekByteArray", addressClass, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    private static Field bufferAddressField() {
        if (Android.isOnAndroidDevice()) {
            final Field field = field(Buffer.class, "effectiveDirectAddress");
            if (field != null) {
                return field;
            }
        }
        final Field field = field(Buffer.class, "address");
        return (field != null && field.getType() == Long.TYPE) ? field : null;
    }
    
    private static int firstDifferingByteIndexNativeEndian(final long left, final long right) {
        final int n = UnsafeUtil.IS_BIG_ENDIAN ? Long.numberOfLeadingZeros(left ^ right) : Long.numberOfTrailingZeros(left ^ right);
        return n >> 3;
    }
    
    static int mismatch(final byte[] left, final int leftOff, final byte[] right, final int rightOff, final int length) {
        if (leftOff < 0 || rightOff < 0 || length < 0 || leftOff + length > left.length || rightOff + length > right.length) {
            throw new IndexOutOfBoundsException();
        }
        int index = 0;
        if (UnsafeUtil.HAS_UNSAFE_ARRAY_OPERATIONS) {
            for (int leftAlignment = UnsafeUtil.BYTE_ARRAY_ALIGNMENT + leftOff & 0x7; index < length && (leftAlignment & 0x7) != 0x0; ++index, ++leftAlignment) {
                if (left[leftOff + index] != right[rightOff + index]) {
                    return index;
                }
            }
            for (int strideLength = (length - index & 0xFFFFFFF8) + index; index < strideLength; index += 8) {
                final long leftLongWord = getLong(left, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + leftOff + index);
                final long rightLongWord = getLong(right, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + rightOff + index);
                if (leftLongWord != rightLongWord) {
                    return index + firstDifferingByteIndexNativeEndian(leftLongWord, rightLongWord);
                }
            }
        }
        while (index < length) {
            if (left[leftOff + index] != right[rightOff + index]) {
                return index;
            }
            ++index;
        }
        return -1;
    }
    
    private static long fieldOffset(final Field field) {
        return (field == null || UnsafeUtil.MEMORY_ACCESSOR == null) ? -1L : UnsafeUtil.MEMORY_ACCESSOR.objectFieldOffset(field);
    }
    
    private static Field field(final Class<?> clazz, final String fieldName) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        }
        catch (Throwable t) {
            field = null;
        }
        return field;
    }
    
    private static byte getByteBigEndian(final Object target, final long offset) {
        return (byte)(getInt(target, offset & 0xFFFFFFFFFFFFFFFCL) >>> (int)((~offset & 0x3L) << 3) & 0xFF);
    }
    
    private static byte getByteLittleEndian(final Object target, final long offset) {
        return (byte)(getInt(target, offset & 0xFFFFFFFFFFFFFFFCL) >>> (int)((offset & 0x3L) << 3) & 0xFF);
    }
    
    private static void putByteBigEndian(final Object target, final long offset, final byte value) {
        final int intValue = getInt(target, offset & 0xFFFFFFFFFFFFFFFCL);
        final int shift = (~(int)offset & 0x3) << 3;
        final int output = (intValue & ~(255 << shift)) | (0xFF & value) << shift;
        putInt(target, offset & 0xFFFFFFFFFFFFFFFCL, output);
    }
    
    private static void putByteLittleEndian(final Object target, final long offset, final byte value) {
        final int intValue = getInt(target, offset & 0xFFFFFFFFFFFFFFFCL);
        final int shift = ((int)offset & 0x3) << 3;
        final int output = (intValue & ~(255 << shift)) | (0xFF & value) << shift;
        putInt(target, offset & 0xFFFFFFFFFFFFFFFCL, output);
    }
    
    private static boolean getBooleanBigEndian(final Object target, final long offset) {
        return getByteBigEndian(target, offset) != 0;
    }
    
    private static boolean getBooleanLittleEndian(final Object target, final long offset) {
        return getByteLittleEndian(target, offset) != 0;
    }
    
    private static void putBooleanBigEndian(final Object target, final long offset, final boolean value) {
        putByteBigEndian(target, offset, (byte)(value ? 1 : 0));
    }
    
    private static void putBooleanLittleEndian(final Object target, final long offset, final boolean value) {
        putByteLittleEndian(target, offset, (byte)(value ? 1 : 0));
    }
    
    static {
        logger = Logger.getLogger(UnsafeUtil.class.getName());
        UNSAFE = getUnsafe();
        MEMORY_CLASS = Android.getMemoryClass();
        IS_ANDROID_64 = determineAndroidSupportByAddressSize(Long.TYPE);
        IS_ANDROID_32 = determineAndroidSupportByAddressSize(Integer.TYPE);
        MEMORY_ACCESSOR = getMemoryAccessor();
        HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
        HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
        BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
        BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
        BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);
        INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
        INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);
        LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
        LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);
        FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
        FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);
        DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
        DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);
        OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(Object[].class);
        OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(Object[].class);
        BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
        BYTE_ARRAY_ALIGNMENT = (int)(UnsafeUtil.BYTE_ARRAY_BASE_OFFSET & 0x7L);
        IS_BIG_ENDIAN = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
    }
    
    private abstract static class MemoryAccessor
    {
        Unsafe unsafe;
        
        MemoryAccessor(final Unsafe unsafe) {
            this.unsafe = unsafe;
        }
        
        public final long objectFieldOffset(final Field field) {
            return this.unsafe.objectFieldOffset(field);
        }
        
        public abstract byte getByte(final Object p0, final long p1);
        
        public abstract void putByte(final Object p0, final long p1, final byte p2);
        
        public final int getInt(final Object target, final long offset) {
            return this.unsafe.getInt(target, offset);
        }
        
        public final void putInt(final Object target, final long offset, final int value) {
            this.unsafe.putInt(target, offset, value);
        }
        
        public final long getLong(final Object target, final long offset) {
            return this.unsafe.getLong(target, offset);
        }
        
        public final void putLong(final Object target, final long offset, final long value) {
            this.unsafe.putLong(target, offset, value);
        }
        
        public abstract boolean getBoolean(final Object p0, final long p1);
        
        public abstract void putBoolean(final Object p0, final long p1, final boolean p2);
        
        public abstract float getFloat(final Object p0, final long p1);
        
        public abstract void putFloat(final Object p0, final long p1, final float p2);
        
        public abstract double getDouble(final Object p0, final long p1);
        
        public abstract void putDouble(final Object p0, final long p1, final double p2);
        
        public final Object getObject(final Object target, final long offset) {
            return this.unsafe.getObject(target, offset);
        }
        
        public final void putObject(final Object target, final long offset, final Object value) {
            this.unsafe.putObject(target, offset, value);
        }
        
        public final int arrayBaseOffset(final Class<?> clazz) {
            return this.unsafe.arrayBaseOffset(clazz);
        }
        
        public final int arrayIndexScale(final Class<?> clazz) {
            return this.unsafe.arrayIndexScale(clazz);
        }
        
        public abstract byte getByte(final long p0);
        
        public abstract void putByte(final long p0, final byte p1);
        
        public abstract int getInt(final long p0);
        
        public abstract void putInt(final long p0, final int p1);
        
        public abstract long getLong(final long p0);
        
        public abstract void putLong(final long p0, final long p1);
        
        public abstract Object getStaticObject(final Field p0);
        
        public abstract void copyMemory(final long p0, final byte[] p1, final long p2, final long p3);
        
        public abstract void copyMemory(final byte[] p0, final long p1, final long p2, final long p3);
    }
    
    private static final class JvmMemoryAccessor extends MemoryAccessor
    {
        JvmMemoryAccessor(final Unsafe unsafe) {
            super(unsafe);
        }
        
        @Override
        public byte getByte(final long address) {
            return this.unsafe.getByte(address);
        }
        
        @Override
        public void putByte(final long address, final byte value) {
            this.unsafe.putByte(address, value);
        }
        
        @Override
        public int getInt(final long address) {
            return this.unsafe.getInt(address);
        }
        
        @Override
        public void putInt(final long address, final int value) {
            this.unsafe.putInt(address, value);
        }
        
        @Override
        public long getLong(final long address) {
            return this.unsafe.getLong(address);
        }
        
        @Override
        public void putLong(final long address, final long value) {
            this.unsafe.putLong(address, value);
        }
        
        @Override
        public byte getByte(final Object target, final long offset) {
            return this.unsafe.getByte(target, offset);
        }
        
        @Override
        public void putByte(final Object target, final long offset, final byte value) {
            this.unsafe.putByte(target, offset, value);
        }
        
        @Override
        public boolean getBoolean(final Object target, final long offset) {
            return this.unsafe.getBoolean(target, offset);
        }
        
        @Override
        public void putBoolean(final Object target, final long offset, final boolean value) {
            this.unsafe.putBoolean(target, offset, value);
        }
        
        @Override
        public float getFloat(final Object target, final long offset) {
            return this.unsafe.getFloat(target, offset);
        }
        
        @Override
        public void putFloat(final Object target, final long offset, final float value) {
            this.unsafe.putFloat(target, offset, value);
        }
        
        @Override
        public double getDouble(final Object target, final long offset) {
            return this.unsafe.getDouble(target, offset);
        }
        
        @Override
        public void putDouble(final Object target, final long offset, final double value) {
            this.unsafe.putDouble(target, offset, value);
        }
        
        @Override
        public void copyMemory(final long srcOffset, final byte[] target, final long targetIndex, final long length) {
            this.unsafe.copyMemory(null, srcOffset, target, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + targetIndex, length);
        }
        
        @Override
        public void copyMemory(final byte[] src, final long srcIndex, final long targetOffset, final long length) {
            this.unsafe.copyMemory(src, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + srcIndex, null, targetOffset, length);
        }
        
        @Override
        public Object getStaticObject(final Field field) {
            return this.getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
        }
    }
    
    private static final class Android64MemoryAccessor extends MemoryAccessor
    {
        Android64MemoryAccessor(final Unsafe unsafe) {
            super(unsafe);
        }
        
        @Override
        public byte getByte(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putByte(final long address, final byte value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int getInt(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putInt(final long address, final int value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public long getLong(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putLong(final long address, final long value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public byte getByte(final Object target, final long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return getByteBigEndian(target, offset);
            }
            return getByteLittleEndian(target, offset);
        }
        
        @Override
        public void putByte(final Object target, final long offset, final byte value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                putByteBigEndian(target, offset, value);
            }
            else {
                putByteLittleEndian(target, offset, value);
            }
        }
        
        @Override
        public boolean getBoolean(final Object target, final long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return getBooleanBigEndian(target, offset);
            }
            return getBooleanLittleEndian(target, offset);
        }
        
        @Override
        public void putBoolean(final Object target, final long offset, final boolean value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                putBooleanBigEndian(target, offset, value);
            }
            else {
                putBooleanLittleEndian(target, offset, value);
            }
        }
        
        @Override
        public float getFloat(final Object target, final long offset) {
            return Float.intBitsToFloat(this.getInt(target, offset));
        }
        
        @Override
        public void putFloat(final Object target, final long offset, final float value) {
            this.putInt(target, offset, Float.floatToIntBits(value));
        }
        
        @Override
        public double getDouble(final Object target, final long offset) {
            return Double.longBitsToDouble(this.getLong(target, offset));
        }
        
        @Override
        public void putDouble(final Object target, final long offset, final double value) {
            this.putLong(target, offset, Double.doubleToLongBits(value));
        }
        
        @Override
        public void copyMemory(final long srcOffset, final byte[] target, final long targetIndex, final long length) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void copyMemory(final byte[] src, final long srcIndex, final long targetOffset, final long length) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object getStaticObject(final Field field) {
            try {
                return field.get(null);
            }
            catch (IllegalAccessException e) {
                return null;
            }
        }
    }
    
    private static final class Android32MemoryAccessor extends MemoryAccessor
    {
        private static final long SMALL_ADDRESS_MASK = -1L;
        
        private static int smallAddress(final long address) {
            return (int)(-1L & address);
        }
        
        Android32MemoryAccessor(final Unsafe unsafe) {
            super(unsafe);
        }
        
        @Override
        public byte getByte(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putByte(final long address, final byte value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int getInt(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putInt(final long address, final int value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public long getLong(final long address) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void putLong(final long address, final long value) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public byte getByte(final Object target, final long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return getByteBigEndian(target, offset);
            }
            return getByteLittleEndian(target, offset);
        }
        
        @Override
        public void putByte(final Object target, final long offset, final byte value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                putByteBigEndian(target, offset, value);
            }
            else {
                putByteLittleEndian(target, offset, value);
            }
        }
        
        @Override
        public boolean getBoolean(final Object target, final long offset) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                return getBooleanBigEndian(target, offset);
            }
            return getBooleanLittleEndian(target, offset);
        }
        
        @Override
        public void putBoolean(final Object target, final long offset, final boolean value) {
            if (UnsafeUtil.IS_BIG_ENDIAN) {
                putBooleanBigEndian(target, offset, value);
            }
            else {
                putBooleanLittleEndian(target, offset, value);
            }
        }
        
        @Override
        public float getFloat(final Object target, final long offset) {
            return Float.intBitsToFloat(this.getInt(target, offset));
        }
        
        @Override
        public void putFloat(final Object target, final long offset, final float value) {
            this.putInt(target, offset, Float.floatToIntBits(value));
        }
        
        @Override
        public double getDouble(final Object target, final long offset) {
            return Double.longBitsToDouble(this.getLong(target, offset));
        }
        
        @Override
        public void putDouble(final Object target, final long offset, final double value) {
            this.putLong(target, offset, Double.doubleToLongBits(value));
        }
        
        @Override
        public void copyMemory(final long srcOffset, final byte[] target, final long targetIndex, final long length) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public void copyMemory(final byte[] src, final long srcIndex, final long targetOffset, final long length) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object getStaticObject(final Field field) {
            try {
                return field.get(null);
            }
            catch (IllegalAccessException e) {
                return null;
            }
        }
    }
}
