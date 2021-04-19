package com.google.protobuf;

import java.util.*;

abstract class ListFieldSchema
{
    private static final ListFieldSchema FULL_INSTANCE;
    private static final ListFieldSchema LITE_INSTANCE;
    
    private ListFieldSchema() {
    }
    
    abstract <L> List<L> mutableListAt(final Object p0, final long p1);
    
    abstract void makeImmutableListAt(final Object p0, final long p1);
    
    abstract <L> void mergeListsAt(final Object p0, final Object p1, final long p2);
    
    static ListFieldSchema full() {
        return ListFieldSchema.FULL_INSTANCE;
    }
    
    static ListFieldSchema lite() {
        return ListFieldSchema.LITE_INSTANCE;
    }
    
    static {
        FULL_INSTANCE = new ListFieldSchemaFull();
        LITE_INSTANCE = new ListFieldSchemaLite();
    }
    
    private static final class ListFieldSchemaFull extends ListFieldSchema
    {
        private static final Class<?> UNMODIFIABLE_LIST_CLASS;
        
        private ListFieldSchemaFull() {
            super(null);
        }
        
        @Override
         <L> List<L> mutableListAt(final Object message, final long offset) {
            return mutableListAt(message, offset, 10);
        }
        
        @Override
        void makeImmutableListAt(final Object message, final long offset) {
            final List<?> list = (List<?>)UnsafeUtil.getObject(message, offset);
            Object immutable = null;
            if (list instanceof LazyStringList) {
                immutable = ((LazyStringList)list).getUnmodifiableView();
            }
            else {
                if (ListFieldSchemaFull.UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList) {
                    if (((Internal.ProtobufList)list).isModifiable()) {
                        ((Internal.ProtobufList)list).makeImmutable();
                    }
                    return;
                }
                immutable = Collections.unmodifiableList(list);
            }
            UnsafeUtil.putObject(message, offset, immutable);
        }
        
        private static <L> List<L> mutableListAt(final Object message, final long offset, final int additionalCapacity) {
            List<L> list = getList(message, offset);
            if (list.isEmpty()) {
                if (list instanceof LazyStringList) {
                    list = (List<L>)new LazyStringArrayList(additionalCapacity);
                }
                else if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList) {
                    list = (List<L>)((Internal.ProtobufList)list).mutableCopyWithCapacity(additionalCapacity);
                }
                else {
                    list = new ArrayList<L>(additionalCapacity);
                }
                UnsafeUtil.putObject(message, offset, list);
            }
            else if (ListFieldSchemaFull.UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                final ArrayList<L> newList = new ArrayList<L>(list.size() + additionalCapacity);
                newList.addAll((Collection<? extends L>)list);
                list = newList;
                UnsafeUtil.putObject(message, offset, list);
            }
            else if (list instanceof UnmodifiableLazyStringList) {
                final LazyStringArrayList newList2 = new LazyStringArrayList(list.size() + additionalCapacity);
                newList2.addAll((Collection<? extends String>)list);
                list = (List<L>)newList2;
                UnsafeUtil.putObject(message, offset, list);
            }
            else if (list instanceof PrimitiveNonBoxingCollection && list instanceof Internal.ProtobufList && !((Internal.ProtobufList)list).isModifiable()) {
                list = (List<L>)((Internal.ProtobufList)list).mutableCopyWithCapacity(list.size() + additionalCapacity);
                UnsafeUtil.putObject(message, offset, list);
            }
            return list;
        }
        
        @Override
         <E> void mergeListsAt(final Object msg, final Object otherMsg, final long offset) {
            final List<E> other = getList(otherMsg, offset);
            final List<E> mine = mutableListAt(msg, offset, other.size());
            final int size = mine.size();
            final int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                mine.addAll((Collection<? extends E>)other);
            }
            final List<E> merged = (size > 0) ? mine : other;
            UnsafeUtil.putObject(msg, offset, merged);
        }
        
        static <E> List<E> getList(final Object message, final long offset) {
            return (List<E>)UnsafeUtil.getObject(message, offset);
        }
        
        static {
            UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();
        }
    }
    
    private static final class ListFieldSchemaLite extends ListFieldSchema
    {
        private ListFieldSchemaLite() {
            super(null);
        }
        
        @Override
         <L> List<L> mutableListAt(final Object message, final long offset) {
            Internal.ProtobufList<L> list = getProtobufList(message, offset);
            if (!list.isModifiable()) {
                final int size = list.size();
                list = list.mutableCopyWithCapacity((size == 0) ? 10 : (size * 2));
                UnsafeUtil.putObject(message, offset, list);
            }
            return list;
        }
        
        @Override
        void makeImmutableListAt(final Object message, final long offset) {
            final Internal.ProtobufList<?> list = getProtobufList(message, offset);
            list.makeImmutable();
        }
        
        @Override
         <E> void mergeListsAt(final Object msg, final Object otherMsg, final long offset) {
            Internal.ProtobufList<E> mine = getProtobufList(msg, offset);
            final Internal.ProtobufList<E> other = getProtobufList(otherMsg, offset);
            final int size = mine.size();
            final int otherSize = other.size();
            if (size > 0 && otherSize > 0) {
                if (!mine.isModifiable()) {
                    mine = mine.mutableCopyWithCapacity(size + otherSize);
                }
                mine.addAll((Collection<?>)other);
            }
            final Internal.ProtobufList<E> merged = (size > 0) ? mine : other;
            UnsafeUtil.putObject(msg, offset, merged);
        }
        
        static <E> Internal.ProtobufList<E> getProtobufList(final Object message, final long offset) {
            return (Internal.ProtobufList<E>)UnsafeUtil.getObject(message, offset);
        }
    }
}
