package com.google.protobuf;

import java.util.*;

abstract class AbstractProtobufList<E> extends AbstractList<E> implements Internal.ProtobufList<E>
{
    protected static final int DEFAULT_CAPACITY = 10;
    private boolean isMutable;
    
    AbstractProtobufList() {
        this.isMutable = true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        if (!(o instanceof RandomAccess)) {
            return super.equals(o);
        }
        final List<?> other = (List<?>)o;
        final int size = this.size();
        if (size != other.size()) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int size = this.size();
        int hashCode = 1;
        for (int i = 0; i < size; ++i) {
            hashCode = 31 * hashCode + this.get(i).hashCode();
        }
        return hashCode;
    }
    
    @Override
    public boolean add(final E e) {
        this.ensureIsMutable();
        return super.add(e);
    }
    
    @Override
    public void add(final int index, final E element) {
        this.ensureIsMutable();
        super.add(index, element);
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        this.ensureIsMutable();
        return super.addAll(c);
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        this.ensureIsMutable();
        return super.addAll(index, c);
    }
    
    @Override
    public void clear() {
        this.ensureIsMutable();
        super.clear();
    }
    
    @Override
    public boolean isModifiable() {
        return this.isMutable;
    }
    
    @Override
    public final void makeImmutable() {
        this.isMutable = false;
    }
    
    @Override
    public E remove(final int index) {
        this.ensureIsMutable();
        return super.remove(index);
    }
    
    @Override
    public boolean remove(final Object o) {
        this.ensureIsMutable();
        return super.remove(o);
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        this.ensureIsMutable();
        return super.removeAll(c);
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        this.ensureIsMutable();
        return super.retainAll(c);
    }
    
    @Override
    public E set(final int index, final E element) {
        this.ensureIsMutable();
        return super.set(index, element);
    }
    
    protected void ensureIsMutable() {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
    }
}
