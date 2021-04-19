package com.google.protobuf;

import java.util.*;

final class ProtobufArrayList<E> extends AbstractProtobufList<E> implements RandomAccess
{
    private static final ProtobufArrayList<Object> EMPTY_LIST;
    private E[] array;
    private int size;
    
    public static <E> ProtobufArrayList<E> emptyList() {
        return (ProtobufArrayList<E>)ProtobufArrayList.EMPTY_LIST;
    }
    
    ProtobufArrayList() {
        this(new Object[10], 0);
    }
    
    private ProtobufArrayList(final E[] array, final int size) {
        this.array = array;
        this.size = size;
    }
    
    @Override
    public ProtobufArrayList<E> mutableCopyWithCapacity(final int capacity) {
        if (capacity < this.size) {
            throw new IllegalArgumentException();
        }
        final E[] newArray = Arrays.copyOf(this.array, capacity);
        return new ProtobufArrayList<E>(newArray, this.size);
    }
    
    @Override
    public boolean add(final E element) {
        this.ensureIsMutable();
        if (this.size == this.array.length) {
            final int length = this.size * 3 / 2 + 1;
            final E[] newArray = Arrays.copyOf(this.array, length);
            this.array = newArray;
        }
        this.array[this.size++] = element;
        ++this.modCount;
        return true;
    }
    
    @Override
    public void add(final int index, final E element) {
        this.ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        }
        else {
            final int length = this.size * 3 / 2 + 1;
            final E[] newArray = createArray(length);
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        ++this.size;
        ++this.modCount;
    }
    
    @Override
    public E get(final int index) {
        this.ensureIndexInRange(index);
        return this.array[index];
    }
    
    @Override
    public E remove(final int index) {
        this.ensureIsMutable();
        this.ensureIndexInRange(index);
        final E value = this.array[index];
        if (index < this.size - 1) {
            System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
        }
        --this.size;
        ++this.modCount;
        return value;
    }
    
    @Override
    public E set(final int index, final E element) {
        this.ensureIsMutable();
        this.ensureIndexInRange(index);
        final E toReturn = this.array[index];
        this.array[index] = element;
        ++this.modCount;
        return toReturn;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    private static <E> E[] createArray(final int capacity) {
        return (E[])new Object[capacity];
    }
    
    private void ensureIndexInRange(final int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(index));
        }
    }
    
    private String makeOutOfBoundsExceptionMessage(final int index) {
        return "Index:" + index + ", Size:" + this.size;
    }
    
    static {
        (EMPTY_LIST = new ProtobufArrayList<Object>(new Object[0], 0)).makeImmutable();
    }
}
