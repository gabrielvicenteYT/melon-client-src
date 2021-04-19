package com.google.protobuf;

import java.util.*;

public class LazyField extends LazyFieldLite
{
    private final MessageLite defaultInstance;
    
    public LazyField(final MessageLite defaultInstance, final ExtensionRegistryLite extensionRegistry, final ByteString bytes) {
        super(extensionRegistry, bytes);
        this.defaultInstance = defaultInstance;
    }
    
    @Override
    public boolean containsDefaultInstance() {
        return super.containsDefaultInstance() || this.value == this.defaultInstance;
    }
    
    public MessageLite getValue() {
        return this.getValue(this.defaultInstance);
    }
    
    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this.getValue().equals(obj);
    }
    
    @Override
    public String toString() {
        return this.getValue().toString();
    }
    
    static class LazyEntry<K> implements Map.Entry<K, Object>
    {
        private Map.Entry<K, LazyField> entry;
        
        private LazyEntry(final Map.Entry<K, LazyField> entry) {
            this.entry = entry;
        }
        
        @Override
        public K getKey() {
            return this.entry.getKey();
        }
        
        @Override
        public Object getValue() {
            final LazyField field = this.entry.getValue();
            if (field == null) {
                return null;
            }
            return field.getValue();
        }
        
        public LazyField getField() {
            return this.entry.getValue();
        }
        
        @Override
        public Object setValue(final Object value) {
            if (!(value instanceof MessageLite)) {
                throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
            }
            return this.entry.getValue().setValue((MessageLite)value);
        }
    }
    
    static class LazyIterator<K> implements Iterator<Map.Entry<K, Object>>
    {
        private Iterator<Map.Entry<K, Object>> iterator;
        
        public LazyIterator(final Iterator<Map.Entry<K, Object>> iterator) {
            this.iterator = iterator;
        }
        
        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }
        
        @Override
        public Map.Entry<K, Object> next() {
            final Map.Entry<K, ?> entry = this.iterator.next();
            if (entry.getValue() instanceof LazyField) {
                return new LazyEntry<K>((Map.Entry)entry);
            }
            return (Map.Entry<K, Object>)entry;
        }
        
        @Override
        public void remove() {
            this.iterator.remove();
        }
    }
}
