package com.google.protobuf;

import java.util.*;

public class MapField<K, V> implements MutabilityOracle
{
    private volatile boolean isMutable;
    private volatile StorageMode mode;
    private MutatabilityAwareMap<K, V> mapData;
    private List<Message> listData;
    private final Converter<K, V> converter;
    
    private MapField(final Converter<K, V> converter, final StorageMode mode, final Map<K, V> mapData) {
        this.converter = converter;
        this.isMutable = true;
        this.mode = mode;
        this.mapData = new MutatabilityAwareMap<K, V>(this, mapData);
        this.listData = null;
    }
    
    private MapField(final MapEntry<K, V> defaultEntry, final StorageMode mode, final Map<K, V> mapData) {
        this((Converter)new ImmutableMessageConverter(defaultEntry), mode, mapData);
    }
    
    public static <K, V> MapField<K, V> emptyMapField(final MapEntry<K, V> defaultEntry) {
        return new MapField<K, V>(defaultEntry, StorageMode.MAP, Collections.emptyMap());
    }
    
    public static <K, V> MapField<K, V> newMapField(final MapEntry<K, V> defaultEntry) {
        return new MapField<K, V>(defaultEntry, StorageMode.MAP, new LinkedHashMap<K, V>());
    }
    
    private Message convertKeyAndValueToMessage(final K key, final V value) {
        return this.converter.convertKeyAndValueToMessage(key, value);
    }
    
    private void convertMessageToKeyAndValue(final Message message, final Map<K, V> map) {
        this.converter.convertMessageToKeyAndValue(message, map);
    }
    
    private List<Message> convertMapToList(final MutatabilityAwareMap<K, V> mapData) {
        final List<Message> listData = new ArrayList<Message>();
        for (final Map.Entry<K, V> entry : mapData.entrySet()) {
            listData.add(this.convertKeyAndValueToMessage(entry.getKey(), entry.getValue()));
        }
        return listData;
    }
    
    private MutatabilityAwareMap<K, V> convertListToMap(final List<Message> listData) {
        final Map<K, V> mapData = new LinkedHashMap<K, V>();
        for (final Message item : listData) {
            this.convertMessageToKeyAndValue(item, mapData);
        }
        return new MutatabilityAwareMap<K, V>(this, mapData);
    }
    
    public Map<K, V> getMap() {
        if (this.mode == StorageMode.LIST) {
            synchronized (this) {
                if (this.mode == StorageMode.LIST) {
                    this.mapData = this.convertListToMap(this.listData);
                    this.mode = StorageMode.BOTH;
                }
            }
        }
        return Collections.unmodifiableMap((Map<? extends K, ? extends V>)this.mapData);
    }
    
    public Map<K, V> getMutableMap() {
        if (this.mode != StorageMode.MAP) {
            if (this.mode == StorageMode.LIST) {
                this.mapData = this.convertListToMap(this.listData);
            }
            this.listData = null;
            this.mode = StorageMode.MAP;
        }
        return this.mapData;
    }
    
    public void mergeFrom(final MapField<K, V> other) {
        this.getMutableMap().putAll(MapFieldLite.copy((Map<? extends K, ? extends V>)other.getMap()));
    }
    
    public void clear() {
        this.mapData = new MutatabilityAwareMap<K, V>(this, new LinkedHashMap<K, V>());
        this.mode = StorageMode.MAP;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof MapField)) {
            return false;
        }
        final MapField<K, V> other = (MapField<K, V>)object;
        return MapFieldLite.equals(this.getMap(), other.getMap());
    }
    
    @Override
    public int hashCode() {
        return MapFieldLite.calculateHashCodeForMap(this.getMap());
    }
    
    public MapField<K, V> copy() {
        return new MapField<K, V>(this.converter, StorageMode.MAP, MapFieldLite.copy(this.getMap()));
    }
    
    List<Message> getList() {
        if (this.mode == StorageMode.MAP) {
            synchronized (this) {
                if (this.mode == StorageMode.MAP) {
                    this.listData = this.convertMapToList(this.mapData);
                    this.mode = StorageMode.BOTH;
                }
            }
        }
        return Collections.unmodifiableList((List<? extends Message>)this.listData);
    }
    
    List<Message> getMutableList() {
        if (this.mode != StorageMode.LIST) {
            if (this.mode == StorageMode.MAP) {
                this.listData = this.convertMapToList(this.mapData);
            }
            this.mapData = null;
            this.mode = StorageMode.LIST;
        }
        return this.listData;
    }
    
    Message getMapEntryMessageDefaultInstance() {
        return this.converter.getMessageDefaultInstance();
    }
    
    public void makeImmutable() {
        this.isMutable = false;
    }
    
    public boolean isMutable() {
        return this.isMutable;
    }
    
    @Override
    public void ensureMutable() {
        if (!this.isMutable()) {
            throw new UnsupportedOperationException();
        }
    }
    
    private enum StorageMode
    {
        MAP, 
        LIST, 
        BOTH;
    }
    
    private static class ImmutableMessageConverter<K, V> implements Converter<K, V>
    {
        private final MapEntry<K, V> defaultEntry;
        
        public ImmutableMessageConverter(final MapEntry<K, V> defaultEntry) {
            this.defaultEntry = defaultEntry;
        }
        
        @Override
        public Message convertKeyAndValueToMessage(final K key, final V value) {
            return this.defaultEntry.newBuilderForType().setKey(key).setValue(value).buildPartial();
        }
        
        @Override
        public void convertMessageToKeyAndValue(final Message message, final Map<K, V> map) {
            final MapEntry<K, V> entry = (MapEntry<K, V>)message;
            map.put(entry.getKey(), entry.getValue());
        }
        
        @Override
        public Message getMessageDefaultInstance() {
            return this.defaultEntry;
        }
    }
    
    private static class MutatabilityAwareMap<K, V> implements Map<K, V>
    {
        private final MutabilityOracle mutabilityOracle;
        private final Map<K, V> delegate;
        
        MutatabilityAwareMap(final MutabilityOracle mutabilityOracle, final Map<K, V> delegate) {
            this.mutabilityOracle = mutabilityOracle;
            this.delegate = delegate;
        }
        
        @Override
        public int size() {
            return this.delegate.size();
        }
        
        @Override
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }
        
        @Override
        public boolean containsKey(final Object key) {
            return this.delegate.containsKey(key);
        }
        
        @Override
        public boolean containsValue(final Object value) {
            return this.delegate.containsValue(value);
        }
        
        @Override
        public V get(final Object key) {
            return this.delegate.get(key);
        }
        
        @Override
        public V put(final K key, final V value) {
            this.mutabilityOracle.ensureMutable();
            Internal.checkNotNull(key);
            Internal.checkNotNull(value);
            return this.delegate.put(key, value);
        }
        
        @Override
        public V remove(final Object key) {
            this.mutabilityOracle.ensureMutable();
            return this.delegate.remove(key);
        }
        
        @Override
        public void putAll(final Map<? extends K, ? extends V> m) {
            this.mutabilityOracle.ensureMutable();
            for (final K key : m.keySet()) {
                Internal.checkNotNull(key);
                Internal.checkNotNull(m.get(key));
            }
            this.delegate.putAll(m);
        }
        
        @Override
        public void clear() {
            this.mutabilityOracle.ensureMutable();
            this.delegate.clear();
        }
        
        @Override
        public Set<K> keySet() {
            return new MutatabilityAwareSet<K>(this.mutabilityOracle, this.delegate.keySet());
        }
        
        @Override
        public Collection<V> values() {
            return new MutatabilityAwareCollection<V>(this.mutabilityOracle, this.delegate.values());
        }
        
        @Override
        public Set<Entry<K, V>> entrySet() {
            return new MutatabilityAwareSet<Entry<K, V>>(this.mutabilityOracle, this.delegate.entrySet());
        }
        
        @Override
        public boolean equals(final Object o) {
            return this.delegate.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.delegate.hashCode();
        }
        
        @Override
        public String toString() {
            return this.delegate.toString();
        }
        
        private static class MutatabilityAwareCollection<E> implements Collection<E>
        {
            private final MutabilityOracle mutabilityOracle;
            private final Collection<E> delegate;
            
            MutatabilityAwareCollection(final MutabilityOracle mutabilityOracle, final Collection<E> delegate) {
                this.mutabilityOracle = mutabilityOracle;
                this.delegate = delegate;
            }
            
            @Override
            public int size() {
                return this.delegate.size();
            }
            
            @Override
            public boolean isEmpty() {
                return this.delegate.isEmpty();
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.delegate.contains(o);
            }
            
            @Override
            public Iterator<E> iterator() {
                return new MutatabilityAwareIterator<E>(this.mutabilityOracle, this.delegate.iterator());
            }
            
            @Override
            public Object[] toArray() {
                return this.delegate.toArray();
            }
            
            @Override
            public <T> T[] toArray(final T[] a) {
                return this.delegate.toArray(a);
            }
            
            @Override
            public boolean add(final E e) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean remove(final Object o) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.remove(o);
            }
            
            @Override
            public boolean containsAll(final Collection<?> c) {
                return this.delegate.containsAll(c);
            }
            
            @Override
            public boolean addAll(final Collection<? extends E> c) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean removeAll(final Collection<?> c) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.removeAll(c);
            }
            
            @Override
            public boolean retainAll(final Collection<?> c) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.retainAll(c);
            }
            
            @Override
            public void clear() {
                this.mutabilityOracle.ensureMutable();
                this.delegate.clear();
            }
            
            @Override
            public boolean equals(final Object o) {
                return this.delegate.equals(o);
            }
            
            @Override
            public int hashCode() {
                return this.delegate.hashCode();
            }
            
            @Override
            public String toString() {
                return this.delegate.toString();
            }
        }
        
        private static class MutatabilityAwareSet<E> implements Set<E>
        {
            private final MutabilityOracle mutabilityOracle;
            private final Set<E> delegate;
            
            MutatabilityAwareSet(final MutabilityOracle mutabilityOracle, final Set<E> delegate) {
                this.mutabilityOracle = mutabilityOracle;
                this.delegate = delegate;
            }
            
            @Override
            public int size() {
                return this.delegate.size();
            }
            
            @Override
            public boolean isEmpty() {
                return this.delegate.isEmpty();
            }
            
            @Override
            public boolean contains(final Object o) {
                return this.delegate.contains(o);
            }
            
            @Override
            public Iterator<E> iterator() {
                return new MutatabilityAwareIterator<E>(this.mutabilityOracle, this.delegate.iterator());
            }
            
            @Override
            public Object[] toArray() {
                return this.delegate.toArray();
            }
            
            @Override
            public <T> T[] toArray(final T[] a) {
                return this.delegate.toArray(a);
            }
            
            @Override
            public boolean add(final E e) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.add(e);
            }
            
            @Override
            public boolean remove(final Object o) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.remove(o);
            }
            
            @Override
            public boolean containsAll(final Collection<?> c) {
                return this.delegate.containsAll(c);
            }
            
            @Override
            public boolean addAll(final Collection<? extends E> c) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.addAll(c);
            }
            
            @Override
            public boolean retainAll(final Collection<?> c) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.retainAll(c);
            }
            
            @Override
            public boolean removeAll(final Collection<?> c) {
                this.mutabilityOracle.ensureMutable();
                return this.delegate.removeAll(c);
            }
            
            @Override
            public void clear() {
                this.mutabilityOracle.ensureMutable();
                this.delegate.clear();
            }
            
            @Override
            public boolean equals(final Object o) {
                return this.delegate.equals(o);
            }
            
            @Override
            public int hashCode() {
                return this.delegate.hashCode();
            }
            
            @Override
            public String toString() {
                return this.delegate.toString();
            }
        }
        
        private static class MutatabilityAwareIterator<E> implements Iterator<E>
        {
            private final MutabilityOracle mutabilityOracle;
            private final Iterator<E> delegate;
            
            MutatabilityAwareIterator(final MutabilityOracle mutabilityOracle, final Iterator<E> delegate) {
                this.mutabilityOracle = mutabilityOracle;
                this.delegate = delegate;
            }
            
            @Override
            public boolean hasNext() {
                return this.delegate.hasNext();
            }
            
            @Override
            public E next() {
                return this.delegate.next();
            }
            
            @Override
            public void remove() {
                this.mutabilityOracle.ensureMutable();
                this.delegate.remove();
            }
            
            @Override
            public boolean equals(final Object obj) {
                return this.delegate.equals(obj);
            }
            
            @Override
            public int hashCode() {
                return this.delegate.hashCode();
            }
            
            @Override
            public String toString() {
                return this.delegate.toString();
            }
        }
    }
    
    private interface Converter<K, V>
    {
        Message convertKeyAndValueToMessage(final K p0, final V p1);
        
        void convertMessageToKeyAndValue(final Message p0, final Map<K, V> p1);
        
        Message getMessageDefaultInstance();
    }
}
