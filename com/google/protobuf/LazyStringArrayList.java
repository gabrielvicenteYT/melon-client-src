package com.google.protobuf;

import java.util.*;

public class LazyStringArrayList extends AbstractProtobufList<String> implements LazyStringList, RandomAccess
{
    private static final LazyStringArrayList EMPTY_LIST;
    public static final LazyStringList EMPTY;
    private final List<Object> list;
    
    static LazyStringArrayList emptyList() {
        return LazyStringArrayList.EMPTY_LIST;
    }
    
    public LazyStringArrayList() {
        this(10);
    }
    
    public LazyStringArrayList(final int initialCapacity) {
        this(new ArrayList<Object>(initialCapacity));
    }
    
    public LazyStringArrayList(final LazyStringList from) {
        this.list = new ArrayList<Object>(from.size());
        this.addAll(from);
    }
    
    public LazyStringArrayList(final List<String> from) {
        this(new ArrayList<Object>(from));
    }
    
    private LazyStringArrayList(final ArrayList<Object> list) {
        this.list = list;
    }
    
    @Override
    public LazyStringArrayList mutableCopyWithCapacity(final int capacity) {
        if (capacity < this.size()) {
            throw new IllegalArgumentException();
        }
        final ArrayList<Object> newList = new ArrayList<Object>(capacity);
        newList.addAll(this.list);
        return new LazyStringArrayList(newList);
    }
    
    @Override
    public String get(final int index) {
        final Object o = this.list.get(index);
        if (o instanceof String) {
            return (String)o;
        }
        if (o instanceof ByteString) {
            final ByteString bs = (ByteString)o;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.list.set(index, s);
            }
            return s;
        }
        final byte[] ba = (byte[])o;
        final String s = Internal.toStringUtf8(ba);
        if (Internal.isValidUtf8(ba)) {
            this.list.set(index, s);
        }
        return s;
    }
    
    @Override
    public int size() {
        return this.list.size();
    }
    
    @Override
    public String set(final int index, final String s) {
        this.ensureIsMutable();
        final Object o = this.list.set(index, s);
        return asString(o);
    }
    
    @Override
    public void add(final int index, final String element) {
        this.ensureIsMutable();
        this.list.add(index, element);
        ++this.modCount;
    }
    
    private void add(final int index, final ByteString element) {
        this.ensureIsMutable();
        this.list.add(index, element);
        ++this.modCount;
    }
    
    private void add(final int index, final byte[] element) {
        this.ensureIsMutable();
        this.list.add(index, element);
        ++this.modCount;
    }
    
    @Override
    public boolean addAll(final Collection<? extends String> c) {
        return this.addAll(this.size(), c);
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends String> c) {
        this.ensureIsMutable();
        final Collection<?> collection = (c instanceof LazyStringList) ? ((LazyStringList)c).getUnderlyingElements() : c;
        final boolean ret = this.list.addAll(index, collection);
        ++this.modCount;
        return ret;
    }
    
    @Override
    public boolean addAllByteString(final Collection<? extends ByteString> values) {
        this.ensureIsMutable();
        final boolean ret = this.list.addAll(values);
        ++this.modCount;
        return ret;
    }
    
    @Override
    public boolean addAllByteArray(final Collection<byte[]> c) {
        this.ensureIsMutable();
        final boolean ret = this.list.addAll(c);
        ++this.modCount;
        return ret;
    }
    
    @Override
    public String remove(final int index) {
        this.ensureIsMutable();
        final Object o = this.list.remove(index);
        ++this.modCount;
        return asString(o);
    }
    
    @Override
    public void clear() {
        this.ensureIsMutable();
        this.list.clear();
        ++this.modCount;
    }
    
    @Override
    public void add(final ByteString element) {
        this.ensureIsMutable();
        this.list.add(element);
        ++this.modCount;
    }
    
    @Override
    public void add(final byte[] element) {
        this.ensureIsMutable();
        this.list.add(element);
        ++this.modCount;
    }
    
    @Override
    public Object getRaw(final int index) {
        return this.list.get(index);
    }
    
    @Override
    public ByteString getByteString(final int index) {
        final Object o = this.list.get(index);
        final ByteString b = asByteString(o);
        if (b != o) {
            this.list.set(index, b);
        }
        return b;
    }
    
    @Override
    public byte[] getByteArray(final int index) {
        final Object o = this.list.get(index);
        final byte[] b = asByteArray(o);
        if (b != o) {
            this.list.set(index, b);
        }
        return b;
    }
    
    @Override
    public void set(final int index, final ByteString s) {
        this.setAndReturn(index, s);
    }
    
    private Object setAndReturn(final int index, final ByteString s) {
        this.ensureIsMutable();
        return this.list.set(index, s);
    }
    
    @Override
    public void set(final int index, final byte[] s) {
        this.setAndReturn(index, s);
    }
    
    private Object setAndReturn(final int index, final byte[] s) {
        this.ensureIsMutable();
        return this.list.set(index, s);
    }
    
    private static String asString(final Object o) {
        if (o instanceof String) {
            return (String)o;
        }
        if (o instanceof ByteString) {
            return ((ByteString)o).toStringUtf8();
        }
        return Internal.toStringUtf8((byte[])o);
    }
    
    private static ByteString asByteString(final Object o) {
        if (o instanceof ByteString) {
            return (ByteString)o;
        }
        if (o instanceof String) {
            return ByteString.copyFromUtf8((String)o);
        }
        return ByteString.copyFrom((byte[])o);
    }
    
    private static byte[] asByteArray(final Object o) {
        if (o instanceof byte[]) {
            return (byte[])o;
        }
        if (o instanceof String) {
            return Internal.toByteArray((String)o);
        }
        return ((ByteString)o).toByteArray();
    }
    
    @Override
    public List<?> getUnderlyingElements() {
        return Collections.unmodifiableList((List<?>)this.list);
    }
    
    @Override
    public void mergeFrom(final LazyStringList other) {
        this.ensureIsMutable();
        for (final Object o : other.getUnderlyingElements()) {
            if (o instanceof byte[]) {
                final byte[] b = (byte[])o;
                this.list.add(Arrays.copyOf(b, b.length));
            }
            else {
                this.list.add(o);
            }
        }
    }
    
    @Override
    public List<byte[]> asByteArrayList() {
        return new ByteArrayListView(this);
    }
    
    @Override
    public List<ByteString> asByteStringList() {
        return new ByteStringListView(this);
    }
    
    @Override
    public LazyStringList getUnmodifiableView() {
        if (this.isModifiable()) {
            return new UnmodifiableLazyStringList(this);
        }
        return this;
    }
    
    static {
        (EMPTY_LIST = new LazyStringArrayList()).makeImmutable();
        EMPTY = LazyStringArrayList.EMPTY_LIST;
    }
    
    private static class ByteArrayListView extends AbstractList<byte[]> implements RandomAccess
    {
        private final LazyStringArrayList list;
        
        ByteArrayListView(final LazyStringArrayList list) {
            this.list = list;
        }
        
        @Override
        public byte[] get(final int index) {
            return this.list.getByteArray(index);
        }
        
        @Override
        public int size() {
            return this.list.size();
        }
        
        @Override
        public byte[] set(final int index, final byte[] s) {
            final Object o = this.list.setAndReturn(index, s);
            ++this.modCount;
            return asByteArray(o);
        }
        
        @Override
        public void add(final int index, final byte[] s) {
            this.list.add(index, s);
            ++this.modCount;
        }
        
        @Override
        public byte[] remove(final int index) {
            final Object o = this.list.remove(index);
            ++this.modCount;
            return asByteArray(o);
        }
    }
    
    private static class ByteStringListView extends AbstractList<ByteString> implements RandomAccess
    {
        private final LazyStringArrayList list;
        
        ByteStringListView(final LazyStringArrayList list) {
            this.list = list;
        }
        
        @Override
        public ByteString get(final int index) {
            return this.list.getByteString(index);
        }
        
        @Override
        public int size() {
            return this.list.size();
        }
        
        @Override
        public ByteString set(final int index, final ByteString s) {
            final Object o = this.list.setAndReturn(index, s);
            ++this.modCount;
            return asByteString(o);
        }
        
        @Override
        public void add(final int index, final ByteString s) {
            this.list.add(index, s);
            ++this.modCount;
        }
        
        @Override
        public ByteString remove(final int index) {
            final Object o = this.list.remove(index);
            ++this.modCount;
            return asByteString(o);
        }
    }
}
