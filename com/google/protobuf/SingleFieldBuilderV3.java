package com.google.protobuf;

public class SingleFieldBuilderV3<MType extends AbstractMessage, BType extends AbstractMessage.Builder, IType extends MessageOrBuilder> implements AbstractMessage.BuilderParent
{
    private AbstractMessage.BuilderParent parent;
    private BType builder;
    private MType message;
    private boolean isClean;
    
    public SingleFieldBuilderV3(final MType message, final AbstractMessage.BuilderParent parent, final boolean isClean) {
        this.message = Internal.checkNotNull(message);
        this.parent = parent;
        this.isClean = isClean;
    }
    
    public void dispose() {
        this.parent = null;
    }
    
    public MType getMessage() {
        if (this.message == null) {
            this.message = (MType)this.builder.buildPartial();
        }
        return this.message;
    }
    
    public MType build() {
        this.isClean = true;
        return this.getMessage();
    }
    
    public BType getBuilder() {
        if (this.builder == null) {
            (this.builder = (BType)this.message.newBuilderForType(this)).mergeFrom(this.message);
            this.builder.markClean();
        }
        return this.builder;
    }
    
    public IType getMessageOrBuilder() {
        if (this.builder != null) {
            return (IType)this.builder;
        }
        return (IType)this.message;
    }
    
    public SingleFieldBuilderV3<MType, BType, IType> setMessage(final MType message) {
        this.message = Internal.checkNotNull(message);
        if (this.builder != null) {
            this.builder.dispose();
            this.builder = null;
        }
        this.onChanged();
        return this;
    }
    
    public SingleFieldBuilderV3<MType, BType, IType> mergeFrom(final MType value) {
        if (this.builder == null && this.message == this.message.getDefaultInstanceForType()) {
            this.message = value;
        }
        else {
            this.getBuilder().mergeFrom(value);
        }
        this.onChanged();
        return this;
    }
    
    public SingleFieldBuilderV3<MType, BType, IType> clear() {
        this.message = (MType)((this.message != null) ? this.message.getDefaultInstanceForType() : this.builder.getDefaultInstanceForType());
        if (this.builder != null) {
            this.builder.dispose();
            this.builder = null;
        }
        this.onChanged();
        return this;
    }
    
    private void onChanged() {
        if (this.builder != null) {
            this.message = null;
        }
        if (this.isClean && this.parent != null) {
            this.parent.markDirty();
            this.isClean = false;
        }
    }
    
    @Override
    public void markDirty() {
        this.onChanged();
    }
}
