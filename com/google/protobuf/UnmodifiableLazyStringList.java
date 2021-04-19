package com.google.protobuf;

import java.util.*;

public class UnmodifiableLazyStringList extends AbstractList<String> implements LazyStringList, RandomAccess
{
    private final LazyStringList list;
    
    public UnmodifiableLazyStringList(final LazyStringList list) {
        this.list = list;
    }
    
    @Override
    public String get(final int index) {
        return this.list.get(index);
    }
    
    @Override
    public Object getRaw(final int index) {
        return this.list.getRaw(index);
    }
    
    @Override
    public int size() {
        return this.list.size();
    }
    
    @Override
    public ByteString getByteString(final int index) {
        return this.list.getByteString(index);
    }
    
    @Override
    public void add(final ByteString element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void set(final int index, final ByteString element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAllByteString(final Collection<? extends ByteString> element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public byte[] getByteArray(final int index) {
        return this.list.getByteArray(index);
    }
    
    @Override
    public void add(final byte[] element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void set(final int index, final byte[] element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAllByteArray(final Collection<byte[]> element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ListIterator<String> listIterator(final int index) {
        return new ListIterator<String>() {
            ListIterator<String> iter = UnmodifiableLazyStringList.this.list.listIterator(index);
            
            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            @Override
            public String next() {
                return this.iter.next();
            }
            
            @Override
            public boolean hasPrevious() {
                return this.iter.hasPrevious();
            }
            
            @Override
            public String previous() {
                return this.iter.previous();
            }
            
            @Override
            public int nextIndex() {
                return this.iter.nextIndex();
            }
            
            @Override
            public int previousIndex() {
                return this.iter.previousIndex();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void set(final String o) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void add(final String o) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            Iterator<String> iter = UnmodifiableLazyStringList.this.list.iterator();
            
            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            @Override
            public String next() {
                return this.iter.next();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    @Override
    public List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }
    
    @Override
    public void mergeFrom(final LazyStringList other) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<byte[]> asByteArrayList() {
        return Collections.unmodifiableList((List<? extends byte[]>)this.list.asByteArrayList());
    }
    
    @Override
    public List<ByteString> asByteStringList() {
        return Collections.unmodifiableList((List<? extends ByteString>)this.list.asByteStringList());
    }
    
    @Override
    public LazyStringList getUnmodifiableView() {
        return this;
    }
}
