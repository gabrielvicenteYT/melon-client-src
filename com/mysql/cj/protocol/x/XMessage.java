package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;
import java.io.*;
import java.util.*;
import com.google.protobuf.*;

public class XMessage implements Message, com.google.protobuf.Message
{
    private com.google.protobuf.Message message;
    private List<Notice> notices;
    
    public XMessage(final com.google.protobuf.Message mess) {
        this.notices = null;
        this.message = mess;
    }
    
    public com.google.protobuf.Message getMessage() {
        return this.message;
    }
    
    @Override
    public byte[] getByteBuffer() {
        return this.message.toByteArray();
    }
    
    @Override
    public int getPosition() {
        return 0;
    }
    
    @Override
    public int getSerializedSize() {
        return this.message.getSerializedSize();
    }
    
    @Override
    public byte[] toByteArray() {
        return this.message.toByteArray();
    }
    
    @Override
    public ByteString toByteString() {
        return this.message.toByteString();
    }
    
    @Override
    public void writeDelimitedTo(final OutputStream arg0) throws IOException {
        this.message.writeDelimitedTo(arg0);
    }
    
    @Override
    public void writeTo(final CodedOutputStream arg0) throws IOException {
        this.message.writeTo(arg0);
    }
    
    @Override
    public void writeTo(final OutputStream arg0) throws IOException {
        this.message.writeTo(arg0);
    }
    
    @Override
    public boolean isInitialized() {
        return this.message.isInitialized();
    }
    
    @Override
    public List<String> findInitializationErrors() {
        return this.message.findInitializationErrors();
    }
    
    @Override
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return this.message.getAllFields();
    }
    
    @Override
    public com.google.protobuf.Message getDefaultInstanceForType() {
        return this.message.getDefaultInstanceForType();
    }
    
    @Override
    public Descriptors.Descriptor getDescriptorForType() {
        return this.message.getDescriptorForType();
    }
    
    @Override
    public Object getField(final Descriptors.FieldDescriptor arg0) {
        return this.message.getField(arg0);
    }
    
    @Override
    public String getInitializationErrorString() {
        return this.message.getInitializationErrorString();
    }
    
    @Override
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor arg0) {
        return this.message.getOneofFieldDescriptor(arg0);
    }
    
    @Override
    public Object getRepeatedField(final Descriptors.FieldDescriptor arg0, final int arg1) {
        return this.message.getRepeatedField(arg0, arg1);
    }
    
    @Override
    public int getRepeatedFieldCount(final Descriptors.FieldDescriptor arg0) {
        return this.message.getRepeatedFieldCount(arg0);
    }
    
    @Override
    public UnknownFieldSet getUnknownFields() {
        return this.message.getUnknownFields();
    }
    
    @Override
    public boolean hasField(final Descriptors.FieldDescriptor arg0) {
        return this.message.hasField(arg0);
    }
    
    @Override
    public boolean hasOneof(final Descriptors.OneofDescriptor arg0) {
        return this.message.hasOneof(arg0);
    }
    
    @Override
    public Parser<? extends com.google.protobuf.Message> getParserForType() {
        return this.message.getParserForType();
    }
    
    @Override
    public Builder newBuilderForType() {
        return this.message.newBuilderForType();
    }
    
    @Override
    public Builder toBuilder() {
        return this.message.toBuilder();
    }
    
    public List<Notice> getNotices() {
        return this.notices;
    }
    
    public XMessage addNotices(final List<Notice> n) {
        if (n != null) {
            if (this.notices == null) {
                this.notices = new ArrayList<Notice>();
            }
            this.notices.addAll(n);
        }
        return this;
    }
}
