package com.google.protobuf;

import java.util.*;

final class FloatArrayList extends AbstractProtobufList<Float> implements Internal.FloatList, RandomAccess, PrimitiveNonBoxingCollection
{
    private static final FloatArrayList EMPTY_LIST;
    private float[] array;
    private int size;
    
    public static FloatArrayList emptyList() {
        return FloatArrayList.EMPTY_LIST;
    }
    
    FloatArrayList() {
        this(new float[10], 0);
    }
    
    private FloatArrayList(final float[] other, final int size) {
        this.array = other;
        this.size = size;
    }
    
    @Override
    protected void removeRange(final int fromIndex, final int toIndex) {
        this.ensureIsMutable();
        if (toIndex < fromIndex) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        System.arraycopy(this.array, toIndex, this.array, fromIndex, this.size - toIndex);
        this.size -= toIndex - fromIndex;
        ++this.modCount;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloatArrayList)) {
            return super.equals(o);
        }
        final FloatArrayList other = (FloatArrayList)o;
        if (this.size != other.size) {
            return false;
        }
        final float[] arr = other.array;
        for (int i = 0; i < this.size; ++i) {
            if (Float.floatToIntBits(this.array[i]) != Float.floatToIntBits(arr[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; ++i) {
            result = 31 * result + Float.floatToIntBits(this.array[i]);
        }
        return result;
    }
    
    @Override
    public Internal.FloatList mutableCopyWithCapacity(final int capacity) {
        if (capacity < this.size) {
            throw new IllegalArgumentException();
        }
        return new FloatArrayList(Arrays.copyOf(this.array, capacity), this.size);
    }
    
    @Override
    public Float get(final int index) {
        return this.getFloat(index);
    }
    
    @Override
    public float getFloat(final int index) {
        this.ensureIndexInRange(index);
        return this.array[index];
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public Float set(final int index, final Float element) {
        return this.setFloat(index, element);
    }
    
    @Override
    public float setFloat(final int index, final float element) {
        this.ensureIsMutable();
        this.ensureIndexInRange(index);
        final float previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }
    
    @Override
    public boolean add(final Float element) {
        this.addFloat(element);
        return true;
    }
    
    @Override
    public void add(final int index, final Float element) {
        this.addFloat(index, element);
    }
    
    @Override
    public void addFloat(final float element) {
        this.ensureIsMutable();
        if (this.size == this.array.length) {
            final int length = this.size * 3 / 2 + 1;
            final float[] newArray = new float[length];
            System.arraycopy(this.array, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        this.array[this.size++] = element;
    }
    
    private void addFloat(final int index, final float element) {
        this.ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        }
        else {
            final int length = this.size * 3 / 2 + 1;
            final float[] newArray = new float[length];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        ++this.size;
        ++this.modCount;
    }
    
    @Override
    public boolean addAll(final Collection<? extends Float> collection) {
        this.ensureIsMutable();
        Internal.checkNotNull(collection);
        if (!(collection instanceof FloatArrayList)) {
            return super.addAll(collection);
        }
        final FloatArrayList list = (FloatArrayList)collection;
        if (list.size == 0) {
            return false;
        }
        final int overflow = Integer.MAX_VALUE - this.size;
        if (overflow < list.size) {
            throw new OutOfMemoryError();
        }
        final int newSize = this.size + list.size;
        if (newSize > this.array.length) {
            this.array = Arrays.copyOf(this.array, newSize);
        }
        System.arraycopy(list.array, 0, this.array, this.size, list.size);
        this.size = newSize;
        ++this.modCount;
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (o.equals(this.array[i])) {
                System.arraycopy(this.array, i + 1, this.array, i, this.size - i - 1);
                --this.size;
                ++this.modCount;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Float remove(final int index) {
        this.ensureIsMutable();
        this.ensureIndexInRange(index);
        final float value = this.array[index];
        if (index < this.size - 1) {
            System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
        }
        --this.size;
        ++this.modCount;
        return value;
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
        (EMPTY_LIST = new FloatArrayList(new float[0], 0)).makeImmutable();
    }
}
