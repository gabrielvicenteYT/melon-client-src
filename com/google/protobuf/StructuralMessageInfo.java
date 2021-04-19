package com.google.protobuf;

import java.util.*;

final class StructuralMessageInfo implements MessageInfo
{
    private final ProtoSyntax syntax;
    private final boolean messageSetWireFormat;
    private final int[] checkInitialized;
    private final FieldInfo[] fields;
    private final MessageLite defaultInstance;
    
    StructuralMessageInfo(final ProtoSyntax syntax, final boolean messageSetWireFormat, final int[] checkInitialized, final FieldInfo[] fields, final Object defaultInstance) {
        this.syntax = syntax;
        this.messageSetWireFormat = messageSetWireFormat;
        this.checkInitialized = checkInitialized;
        this.fields = fields;
        this.defaultInstance = Internal.checkNotNull(defaultInstance, "defaultInstance");
    }
    
    @Override
    public ProtoSyntax getSyntax() {
        return this.syntax;
    }
    
    @Override
    public boolean isMessageSetWireFormat() {
        return this.messageSetWireFormat;
    }
    
    public int[] getCheckInitialized() {
        return this.checkInitialized;
    }
    
    public FieldInfo[] getFields() {
        return this.fields;
    }
    
    @Override
    public MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final int numFields) {
        return new Builder(numFields);
    }
    
    public static final class Builder
    {
        private final List<FieldInfo> fields;
        private ProtoSyntax syntax;
        private boolean wasBuilt;
        private boolean messageSetWireFormat;
        private int[] checkInitialized;
        private Object defaultInstance;
        
        public Builder() {
            this.checkInitialized = null;
            this.fields = new ArrayList<FieldInfo>();
        }
        
        public Builder(final int numFields) {
            this.checkInitialized = null;
            this.fields = new ArrayList<FieldInfo>(numFields);
        }
        
        public void withDefaultInstance(final Object defaultInstance) {
            this.defaultInstance = defaultInstance;
        }
        
        public void withSyntax(final ProtoSyntax syntax) {
            this.syntax = Internal.checkNotNull(syntax, "syntax");
        }
        
        public void withMessageSetWireFormat(final boolean messageSetWireFormat) {
            this.messageSetWireFormat = messageSetWireFormat;
        }
        
        public void withCheckInitialized(final int[] checkInitialized) {
            this.checkInitialized = checkInitialized;
        }
        
        public void withField(final FieldInfo field) {
            if (this.wasBuilt) {
                throw new IllegalStateException("Builder can only build once");
            }
            this.fields.add(field);
        }
        
        public StructuralMessageInfo build() {
            if (this.wasBuilt) {
                throw new IllegalStateException("Builder can only build once");
            }
            if (this.syntax == null) {
                throw new IllegalStateException("Must specify a proto syntax");
            }
            this.wasBuilt = true;
            Collections.sort(this.fields);
            return new StructuralMessageInfo(this.syntax, this.messageSetWireFormat, this.checkInitialized, this.fields.toArray(new FieldInfo[0]), this.defaultInstance);
        }
    }
}
