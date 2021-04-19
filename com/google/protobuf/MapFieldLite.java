package com.google.protobuf;

import java.util.*;

public final class MapFieldLite<K, V> extends LinkedHashMap<K, V>
{
    private boolean isMutable;
    private static final MapFieldLite EMPTY_MAP_FIELD;
    
    private MapFieldLite() {
        this.isMutable = true;
    }
    
    private MapFieldLite(final Map<K, V> mapData) {
        super(mapData);
        this.isMutable = true;
    }
    
    public static <K, V> MapFieldLite<K, V> emptyMapField() {
        return (MapFieldLite<K, V>)MapFieldLite.EMPTY_MAP_FIELD;
    }
    
    public void mergeFrom(final MapFieldLite<K, V> other) {
        this.ensureMutable();
        if (!other.isEmpty()) {
            this.putAll((Map<? extends K, ? extends V>)other);
        }
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.isEmpty() ? Collections.emptySet() : super.entrySet();
    }
    
    @Override
    public void clear() {
        this.ensureMutable();
        super.clear();
    }
    
    @Override
    public V put(final K key, final V value) {
        this.ensureMutable();
        Internal.checkNotNull(key);
        Internal.checkNotNull(value);
        return super.put(key, value);
    }
    
    public V put(final Map.Entry<K, V> entry) {
        return this.put(entry.getKey(), entry.getValue());
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        this.ensureMutable();
        checkForNullKeysAndValues(m);
        super.putAll(m);
    }
    
    @Override
    public V remove(final Object key) {
        this.ensureMutable();
        return super.remove(key);
    }
    
    private static void checkForNullKeysAndValues(final Map<?, ?> m) {
        for (final Object key : m.keySet()) {
            Internal.checkNotNull(key);
            Internal.checkNotNull(m.get(key));
        }
    }
    
    private static boolean equals(final Object a, final Object b) {
        if (a instanceof byte[] && b instanceof byte[]) {
            return Arrays.equals((byte[])a, (byte[])b);
        }
        return a.equals(b);
    }
    
    static <K, V> boolean equals(final Map<K, V> a, final Map<K, V> b) {
        if (a == b) {
            return true;
        }
        if (a.size() != b.size()) {
            return false;
        }
        for (final Map.Entry<K, V> entry : a.entrySet()) {
            if (!b.containsKey(entry.getKey())) {
                return false;
            }
            if (!equals(entry.getValue(), b.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object object) {
        return object instanceof Map && equals((Map<Object, Object>)this, (Map<Object, Object>)object);
    }
    
    private static int calculateHashCodeForObject(final Object a) {
        if (a instanceof byte[]) {
            return Internal.hashCode((byte[])a);
        }
        if (a instanceof Internal.EnumLite) {
            throw new UnsupportedOperationException();
        }
        return a.hashCode();
    }
    
    static <K, V> int calculateHashCodeForMap(final Map<K, V> a) {
        int result = 0;
        for (final Map.Entry<K, V> entry : a.entrySet()) {
            result += (calculateHashCodeForObject(entry.getKey()) ^ calculateHashCodeForObject(entry.getValue()));
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        return calculateHashCodeForMap((Map<Object, Object>)this);
    }
    
    private static Object copy(final Object object) {
        if (object instanceof byte[]) {
            final byte[] data = (byte[])object;
            return Arrays.copyOf(data, data.length);
        }
        return object;
    }
    
    static <K, V> Map<K, V> copy(final Map<K, V> map) {
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            result.put(entry.getKey(), (V)copy(entry.getValue()));
        }
        return result;
    }
    
    public MapFieldLite<K, V> mutableCopy() {
        return this.isEmpty() ? new MapFieldLite<K, V>() : new MapFieldLite<K, V>(this);
    }
    
    public void makeImmutable() {
        this.isMutable = false;
    }
    
    public boolean isMutable() {
        return this.isMutable;
    }
    
    private void ensureMutable() {
        if (!this.isMutable()) {
            throw new UnsupportedOperationException();
        }
    }
    
    static {
        (EMPTY_MAP_FIELD = new MapFieldLite()).makeImmutable();
    }
}
