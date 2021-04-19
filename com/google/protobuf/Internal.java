package com.google.protobuf;

import java.nio.charset.*;
import java.nio.*;
import java.lang.reflect.*;
import java.util.*;

public final class Internal
{
    static final Charset UTF_8;
    static final Charset ISO_8859_1;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final ByteBuffer EMPTY_BYTE_BUFFER;
    public static final CodedInputStream EMPTY_CODED_INPUT_STREAM;
    
    private Internal() {
    }
    
    static <T> T checkNotNull(final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
    
    static <T> T checkNotNull(final T obj, final String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        return obj;
    }
    
    public static String stringDefaultValue(final String bytes) {
        return new String(bytes.getBytes(Internal.ISO_8859_1), Internal.UTF_8);
    }
    
    public static ByteString bytesDefaultValue(final String bytes) {
        return ByteString.copyFrom(bytes.getBytes(Internal.ISO_8859_1));
    }
    
    public static byte[] byteArrayDefaultValue(final String bytes) {
        return bytes.getBytes(Internal.ISO_8859_1);
    }
    
    public static ByteBuffer byteBufferDefaultValue(final String bytes) {
        return ByteBuffer.wrap(byteArrayDefaultValue(bytes));
    }
    
    public static ByteBuffer copyByteBuffer(final ByteBuffer source) {
        final ByteBuffer temp = source.duplicate();
        temp.clear();
        final ByteBuffer result = ByteBuffer.allocate(temp.capacity());
        result.put(temp);
        result.clear();
        return result;
    }
    
    public static boolean isValidUtf8(final ByteString byteString) {
        return byteString.isValidUtf8();
    }
    
    public static boolean isValidUtf8(final byte[] byteArray) {
        return Utf8.isValidUtf8(byteArray);
    }
    
    public static byte[] toByteArray(final String value) {
        return value.getBytes(Internal.UTF_8);
    }
    
    public static String toStringUtf8(final byte[] bytes) {
        return new String(bytes, Internal.UTF_8);
    }
    
    public static int hashLong(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    public static int hashBoolean(final boolean b) {
        return b ? 1231 : 1237;
    }
    
    public static int hashEnum(final EnumLite e) {
        return e.getNumber();
    }
    
    public static int hashEnumList(final List<? extends EnumLite> list) {
        int hash = 1;
        for (final EnumLite e : list) {
            hash = 31 * hash + hashEnum(e);
        }
        return hash;
    }
    
    public static boolean equals(final List<byte[]> a, final List<byte[]> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); ++i) {
            if (!Arrays.equals(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static int hashCode(final List<byte[]> list) {
        int hash = 1;
        for (final byte[] bytes : list) {
            hash = 31 * hash + hashCode(bytes);
        }
        return hash;
    }
    
    public static int hashCode(final byte[] bytes) {
        return hashCode(bytes, 0, bytes.length);
    }
    
    static int hashCode(final byte[] bytes, final int offset, final int length) {
        final int h = partialHash(length, bytes, offset, length);
        return (h == 0) ? 1 : h;
    }
    
    static int partialHash(int h, final byte[] bytes, final int offset, final int length) {
        for (int i = offset; i < offset + length; ++i) {
            h = h * 31 + bytes[i];
        }
        return h;
    }
    
    public static boolean equalsByteBuffer(final ByteBuffer a, final ByteBuffer b) {
        return a.capacity() == b.capacity() && a.duplicate().clear().equals(b.duplicate().clear());
    }
    
    public static boolean equalsByteBuffer(final List<ByteBuffer> a, final List<ByteBuffer> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); ++i) {
            if (!equalsByteBuffer(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static int hashCodeByteBuffer(final List<ByteBuffer> list) {
        int hash = 1;
        for (final ByteBuffer bytes : list) {
            hash = 31 * hash + hashCodeByteBuffer(bytes);
        }
        return hash;
    }
    
    public static int hashCodeByteBuffer(final ByteBuffer bytes) {
        if (bytes.hasArray()) {
            final int h = partialHash(bytes.capacity(), bytes.array(), bytes.arrayOffset(), bytes.capacity());
            return (h == 0) ? 1 : h;
        }
        final int bufferSize = (bytes.capacity() > 4096) ? 4096 : bytes.capacity();
        final byte[] buffer = new byte[bufferSize];
        final ByteBuffer duplicated = bytes.duplicate();
        duplicated.clear();
        int h2 = bytes.capacity();
        while (duplicated.remaining() > 0) {
            final int length = (duplicated.remaining() <= bufferSize) ? duplicated.remaining() : bufferSize;
            duplicated.get(buffer, 0, length);
            h2 = partialHash(h2, buffer, 0, length);
        }
        return (h2 == 0) ? 1 : h2;
    }
    
    public static <T extends MessageLite> T getDefaultInstance(final Class<T> clazz) {
        try {
            final Method method = clazz.getMethod("getDefaultInstance", (Class<?>[])new Class[0]);
            return (T)method.invoke(method, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get default instance for " + clazz, e);
        }
    }
    
    static Object mergeMessage(final Object destination, final Object source) {
        return ((MessageLite)destination).toBuilder().mergeFrom((MessageLite)source).buildPartial();
    }
    
    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_BYTE_BUFFER = ByteBuffer.wrap(Internal.EMPTY_BYTE_ARRAY);
        EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(Internal.EMPTY_BYTE_ARRAY);
    }
    
    public static class ListAdapter<F, T> extends AbstractList<T>
    {
        private final List<F> fromList;
        private final Converter<F, T> converter;
        
        public ListAdapter(final List<F> fromList, final Converter<F, T> converter) {
            this.fromList = fromList;
            this.converter = converter;
        }
        
        @Override
        public T get(final int index) {
            return this.converter.convert(this.fromList.get(index));
        }
        
        @Override
        public int size() {
            return this.fromList.size();
        }
        
        public interface Converter<F, T>
        {
            T convert(final F p0);
        }
    }
    
    public static class MapAdapter<K, V, RealValue> extends AbstractMap<K, V>
    {
        private final Map<K, RealValue> realMap;
        private final Converter<RealValue, V> valueConverter;
        
        public static <T extends EnumLite> Converter<Integer, T> newEnumConverter(final EnumLiteMap<T> enumMap, final T unrecognizedValue) {
            return new Converter<Integer, T>() {
                @Override
                public T doForward(final Integer value) {
                    final T result = enumMap.findValueByNumber(value);
                    return (result == null) ? unrecognizedValue : result;
                }
                
                @Override
                public Integer doBackward(final T value) {
                    return value.getNumber();
                }
            };
        }
        
        public MapAdapter(final Map<K, RealValue> realMap, final Converter<RealValue, V> valueConverter) {
            this.realMap = realMap;
            this.valueConverter = valueConverter;
        }
        
        @Override
        public V get(final Object key) {
            final RealValue result = this.realMap.get(key);
            if (result == null) {
                return null;
            }
            return this.valueConverter.doForward(result);
        }
        
        @Override
        public V put(final K key, final V value) {
            final RealValue oldValue = this.realMap.put(key, this.valueConverter.doBackward(value));
            if (oldValue == null) {
                return null;
            }
            return this.valueConverter.doForward(oldValue);
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return new SetAdapter(this.realMap.entrySet());
        }
        
        private class SetAdapter extends AbstractSet<Map.Entry<K, V>>
        {
            private final Set<Map.Entry<K, RealValue>> realSet;
            
            public SetAdapter(final Set<Map.Entry<K, RealValue>> realSet) {
                this.realSet = realSet;
            }
            
            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new IteratorAdapter(this.realSet.iterator());
            }
            
            @Override
            public int size() {
                return this.realSet.size();
            }
        }
        
        private class IteratorAdapter implements Iterator<Map.Entry<K, V>>
        {
            private final Iterator<Map.Entry<K, RealValue>> realIterator;
            
            public IteratorAdapter(final Iterator<Map.Entry<K, RealValue>> realIterator) {
                this.realIterator = realIterator;
            }
            
            @Override
            public boolean hasNext() {
                return this.realIterator.hasNext();
            }
            
            @Override
            public Map.Entry<K, V> next() {
                return new EntryAdapter(this.realIterator.next());
            }
            
            @Override
            public void remove() {
                this.realIterator.remove();
            }
        }
        
        private class EntryAdapter implements Map.Entry<K, V>
        {
            private final Map.Entry<K, RealValue> realEntry;
            
            public EntryAdapter(final Map.Entry<K, RealValue> realEntry) {
                this.realEntry = realEntry;
            }
            
            @Override
            public K getKey() {
                return this.realEntry.getKey();
            }
            
            @Override
            public V getValue() {
                return MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
            }
            
            @Override
            public V setValue(final V value) {
                final RealValue oldValue = this.realEntry.setValue(MapAdapter.this.valueConverter.doBackward(value));
                if (oldValue == null) {
                    return null;
                }
                return MapAdapter.this.valueConverter.doForward(oldValue);
            }
            
            @Override
            public boolean equals(final Object o) {
                if (o == this) {
                    return true;
                }
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
                return this.getKey().equals(other.getKey()) && this.getValue().equals(this.getValue());
            }
            
            @Override
            public int hashCode() {
                return this.realEntry.hashCode();
            }
        }
        
        public interface Converter<A, B>
        {
            B doForward(final A p0);
            
            A doBackward(final B p0);
        }
    }
    
    public interface FloatList extends ProtobufList<Float>
    {
        float getFloat(final int p0);
        
        void addFloat(final float p0);
        
        float setFloat(final int p0, final float p1);
        
        FloatList mutableCopyWithCapacity(final int p0);
    }
    
    public interface ProtobufList<E> extends List<E>, RandomAccess
    {
        void makeImmutable();
        
        boolean isModifiable();
        
        ProtobufList<E> mutableCopyWithCapacity(final int p0);
    }
    
    public interface DoubleList extends ProtobufList<Double>
    {
        double getDouble(final int p0);
        
        void addDouble(final double p0);
        
        double setDouble(final int p0, final double p1);
        
        DoubleList mutableCopyWithCapacity(final int p0);
    }
    
    public interface LongList extends ProtobufList<Long>
    {
        long getLong(final int p0);
        
        void addLong(final long p0);
        
        long setLong(final int p0, final long p1);
        
        LongList mutableCopyWithCapacity(final int p0);
    }
    
    public interface BooleanList extends ProtobufList<Boolean>
    {
        boolean getBoolean(final int p0);
        
        void addBoolean(final boolean p0);
        
        boolean setBoolean(final int p0, final boolean p1);
        
        BooleanList mutableCopyWithCapacity(final int p0);
    }
    
    public interface IntList extends ProtobufList<Integer>
    {
        int getInt(final int p0);
        
        void addInt(final int p0);
        
        int setInt(final int p0, final int p1);
        
        IntList mutableCopyWithCapacity(final int p0);
    }
    
    public interface EnumLiteMap<T extends EnumLite>
    {
        T findValueByNumber(final int p0);
    }
    
    public interface EnumLite
    {
        int getNumber();
    }
    
    public interface EnumVerifier
    {
        boolean isInRange(final int p0);
    }
}
