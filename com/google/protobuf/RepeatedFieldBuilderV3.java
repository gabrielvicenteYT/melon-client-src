package com.google.protobuf;

import java.util.*;

public class RepeatedFieldBuilderV3<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> implements AbstractMessage.BuilderParent
{
    private AbstractMessage.BuilderParent parent;
    private List<MType> messages;
    private boolean isMessagesListMutable;
    private List<SingleFieldBuilderV3<MType, BType, IType>> builders;
    private boolean isClean;
    private MessageExternalList<MType, BType, IType> externalMessageList;
    private BuilderExternalList<MType, BType, IType> externalBuilderList;
    private MessageOrBuilderExternalList<MType, BType, IType> externalMessageOrBuilderList;
    
    public RepeatedFieldBuilderV3(final List<MType> messages, final boolean isMessagesListMutable, final AbstractMessage.BuilderParent parent, final boolean isClean) {
        this.messages = messages;
        this.isMessagesListMutable = isMessagesListMutable;
        this.parent = parent;
        this.isClean = isClean;
    }
    
    public void dispose() {
        this.parent = null;
    }
    
    private void ensureMutableMessageList() {
        if (!this.isMessagesListMutable) {
            this.messages = new ArrayList<MType>((Collection<? extends MType>)this.messages);
            this.isMessagesListMutable = true;
        }
    }
    
    private void ensureBuilders() {
        if (this.builders == null) {
            this.builders = new ArrayList<SingleFieldBuilderV3<MType, BType, IType>>(this.messages.size());
            for (int i = 0; i < this.messages.size(); ++i) {
                this.builders.add(null);
            }
        }
    }
    
    public int getCount() {
        return this.messages.size();
    }
    
    public boolean isEmpty() {
        return this.messages.isEmpty();
    }
    
    public MType getMessage(final int index) {
        return this.getMessage(index, false);
    }
    
    private MType getMessage(final int index, final boolean forBuild) {
        if (this.builders == null) {
            return this.messages.get(index);
        }
        final SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
        if (builder == null) {
            return this.messages.get(index);
        }
        return forBuild ? builder.build() : builder.getMessage();
    }
    
    public BType getBuilder(final int index) {
        this.ensureBuilders();
        SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
        if (builder == null) {
            final MType message = this.messages.get(index);
            builder = new SingleFieldBuilderV3<MType, BType, IType>(message, this, this.isClean);
            this.builders.set(index, builder);
        }
        return builder.getBuilder();
    }
    
    public IType getMessageOrBuilder(final int index) {
        if (this.builders == null) {
            return (IType)this.messages.get(index);
        }
        final SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(index);
        if (builder == null) {
            return (IType)this.messages.get(index);
        }
        return builder.getMessageOrBuilder();
    }
    
    public RepeatedFieldBuilderV3<MType, BType, IType> setMessage(final int index, final MType message) {
        Internal.checkNotNull(message);
        this.ensureMutableMessageList();
        this.messages.set(index, message);
        if (this.builders != null) {
            final SingleFieldBuilderV3<MType, BType, IType> entry = this.builders.set(index, null);
            if (entry != null) {
                entry.dispose();
            }
        }
        this.onChanged();
        this.incrementModCounts();
        return this;
    }
    
    public RepeatedFieldBuilderV3<MType, BType, IType> addMessage(final MType message) {
        Internal.checkNotNull(message);
        this.ensureMutableMessageList();
        this.messages.add(message);
        if (this.builders != null) {
            this.builders.add(null);
        }
        this.onChanged();
        this.incrementModCounts();
        return this;
    }
    
    public RepeatedFieldBuilderV3<MType, BType, IType> addMessage(final int index, final MType message) {
        Internal.checkNotNull(message);
        this.ensureMutableMessageList();
        this.messages.add(index, message);
        if (this.builders != null) {
            this.builders.add(index, null);
        }
        this.onChanged();
        this.incrementModCounts();
        return this;
    }
    
    public RepeatedFieldBuilderV3<MType, BType, IType> addAllMessages(final Iterable<? extends MType> values) {
        for (final MType value : values) {
            Internal.checkNotNull(value);
        }
        int size = -1;
        if (values instanceof Collection) {
            final Collection<MType> collection = (Collection<MType>)(Collection)values;
            if (collection.size() == 0) {
                return this;
            }
            size = collection.size();
        }
        this.ensureMutableMessageList();
        if (size >= 0 && this.messages instanceof ArrayList) {
            ((ArrayList)this.messages).ensureCapacity(this.messages.size() + size);
        }
        for (final MType value2 : values) {
            this.addMessage(value2);
        }
        this.onChanged();
        this.incrementModCounts();
        return this;
    }
    
    public BType addBuilder(final MType message) {
        this.ensureMutableMessageList();
        this.ensureBuilders();
        final SingleFieldBuilderV3<MType, BType, IType> builder = new SingleFieldBuilderV3<MType, BType, IType>(message, this, this.isClean);
        this.messages.add(null);
        this.builders.add(builder);
        this.onChanged();
        this.incrementModCounts();
        return builder.getBuilder();
    }
    
    public BType addBuilder(final int index, final MType message) {
        this.ensureMutableMessageList();
        this.ensureBuilders();
        final SingleFieldBuilderV3<MType, BType, IType> builder = new SingleFieldBuilderV3<MType, BType, IType>(message, this, this.isClean);
        this.messages.add(index, null);
        this.builders.add(index, builder);
        this.onChanged();
        this.incrementModCounts();
        return builder.getBuilder();
    }
    
    public void remove(final int index) {
        this.ensureMutableMessageList();
        this.messages.remove(index);
        if (this.builders != null) {
            final SingleFieldBuilderV3<MType, BType, IType> entry = this.builders.remove(index);
            if (entry != null) {
                entry.dispose();
            }
        }
        this.onChanged();
        this.incrementModCounts();
    }
    
    public void clear() {
        this.messages = Collections.emptyList();
        this.isMessagesListMutable = false;
        if (this.builders != null) {
            for (final SingleFieldBuilderV3<MType, BType, IType> entry : this.builders) {
                if (entry != null) {
                    entry.dispose();
                }
            }
            this.builders = null;
        }
        this.onChanged();
        this.incrementModCounts();
    }
    
    public List<MType> build() {
        this.isClean = true;
        if (!this.isMessagesListMutable && this.builders == null) {
            return this.messages;
        }
        boolean allMessagesInSync = true;
        if (!this.isMessagesListMutable) {
            for (int i = 0; i < this.messages.size(); ++i) {
                final Message message = this.messages.get(i);
                final SingleFieldBuilderV3<MType, BType, IType> builder = this.builders.get(i);
                if (builder != null && builder.build() != message) {
                    allMessagesInSync = false;
                    break;
                }
            }
            if (allMessagesInSync) {
                return this.messages;
            }
        }
        this.ensureMutableMessageList();
        for (int i = 0; i < this.messages.size(); ++i) {
            this.messages.set(i, this.getMessage(i, true));
        }
        this.messages = Collections.unmodifiableList((List<? extends MType>)this.messages);
        this.isMessagesListMutable = false;
        return this.messages;
    }
    
    public List<MType> getMessageList() {
        if (this.externalMessageList == null) {
            this.externalMessageList = new MessageExternalList<MType, BType, IType>(this);
        }
        return (List<MType>)this.externalMessageList;
    }
    
    public List<BType> getBuilderList() {
        if (this.externalBuilderList == null) {
            this.externalBuilderList = new BuilderExternalList<MType, BType, IType>(this);
        }
        return (List<BType>)this.externalBuilderList;
    }
    
    public List<IType> getMessageOrBuilderList() {
        if (this.externalMessageOrBuilderList == null) {
            this.externalMessageOrBuilderList = new MessageOrBuilderExternalList<MType, BType, IType>(this);
        }
        return (List<IType>)this.externalMessageOrBuilderList;
    }
    
    private void onChanged() {
        if (this.isClean && this.parent != null) {
            this.parent.markDirty();
            this.isClean = false;
        }
    }
    
    @Override
    public void markDirty() {
        this.onChanged();
    }
    
    private void incrementModCounts() {
        if (this.externalMessageList != null) {
            this.externalMessageList.incrementModCount();
        }
        if (this.externalBuilderList != null) {
            this.externalBuilderList.incrementModCount();
        }
        if (this.externalMessageOrBuilderList != null) {
            this.externalMessageOrBuilderList.incrementModCount();
        }
    }
    
    private static class MessageExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<MType> implements List<MType>
    {
        RepeatedFieldBuilderV3<MType, BType, IType> builder;
        
        MessageExternalList(final RepeatedFieldBuilderV3<MType, BType, IType> builder) {
            this.builder = builder;
        }
        
        @Override
        public int size() {
            return this.builder.getCount();
        }
        
        @Override
        public MType get(final int index) {
            return this.builder.getMessage(index);
        }
        
        void incrementModCount() {
            ++this.modCount;
        }
    }
    
    private static class BuilderExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<BType> implements List<BType>
    {
        RepeatedFieldBuilderV3<MType, BType, IType> builder;
        
        BuilderExternalList(final RepeatedFieldBuilderV3<MType, BType, IType> builder) {
            this.builder = builder;
        }
        
        @Override
        public int size() {
            return this.builder.getCount();
        }
        
        @Override
        public BType get(final int index) {
            return this.builder.getBuilder(index);
        }
        
        void incrementModCount() {
            ++this.modCount;
        }
    }
    
    private static class MessageOrBuilderExternalList<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> extends AbstractList<IType> implements List<IType>
    {
        RepeatedFieldBuilderV3<MType, BType, IType> builder;
        
        MessageOrBuilderExternalList(final RepeatedFieldBuilderV3<MType, BType, IType> builder) {
            this.builder = builder;
        }
        
        @Override
        public int size() {
            return this.builder.getCount();
        }
        
        @Override
        public IType get(final int index) {
            return this.builder.getMessageOrBuilder(index);
        }
        
        void incrementModCount() {
            ++this.modCount;
        }
    }
}
