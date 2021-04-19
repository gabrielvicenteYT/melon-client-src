package com.google.protobuf;

import java.io.*;

class UnknownFieldSetLiteSchema extends UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite>
{
    @Override
    boolean shouldDiscardUnknownFields(final Reader reader) {
        return false;
    }
    
    @Override
    UnknownFieldSetLite newBuilder() {
        return UnknownFieldSetLite.newInstance();
    }
    
    @Override
    void addVarint(final UnknownFieldSetLite fields, final int number, final long value) {
        fields.storeField(WireFormat.makeTag(number, 0), value);
    }
    
    @Override
    void addFixed32(final UnknownFieldSetLite fields, final int number, final int value) {
        fields.storeField(WireFormat.makeTag(number, 5), value);
    }
    
    @Override
    void addFixed64(final UnknownFieldSetLite fields, final int number, final long value) {
        fields.storeField(WireFormat.makeTag(number, 1), value);
    }
    
    @Override
    void addLengthDelimited(final UnknownFieldSetLite fields, final int number, final ByteString value) {
        fields.storeField(WireFormat.makeTag(number, 2), value);
    }
    
    @Override
    void addGroup(final UnknownFieldSetLite fields, final int number, final UnknownFieldSetLite subFieldSet) {
        fields.storeField(WireFormat.makeTag(number, 3), subFieldSet);
    }
    
    @Override
    UnknownFieldSetLite toImmutable(final UnknownFieldSetLite fields) {
        fields.makeImmutable();
        return fields;
    }
    
    @Override
    void setToMessage(final Object message, final UnknownFieldSetLite fields) {
        ((GeneratedMessageLite)message).unknownFields = fields;
    }
    
    @Override
    UnknownFieldSetLite getFromMessage(final Object message) {
        return ((GeneratedMessageLite)message).unknownFields;
    }
    
    @Override
    UnknownFieldSetLite getBuilderFromMessage(final Object message) {
        UnknownFieldSetLite unknownFields = this.getFromMessage(message);
        if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            unknownFields = UnknownFieldSetLite.newInstance();
            this.setToMessage(message, unknownFields);
        }
        return unknownFields;
    }
    
    @Override
    void setBuilderToMessage(final Object message, final UnknownFieldSetLite fields) {
        this.setToMessage(message, fields);
    }
    
    @Override
    void makeImmutable(final Object message) {
        this.getFromMessage(message).makeImmutable();
    }
    
    @Override
    void writeTo(final UnknownFieldSetLite fields, final Writer writer) throws IOException {
        fields.writeTo(writer);
    }
    
    @Override
    void writeAsMessageSetTo(final UnknownFieldSetLite fields, final Writer writer) throws IOException {
        fields.writeAsMessageSetTo(writer);
    }
    
    @Override
    UnknownFieldSetLite merge(final UnknownFieldSetLite message, final UnknownFieldSetLite other) {
        return other.equals(UnknownFieldSetLite.getDefaultInstance()) ? message : UnknownFieldSetLite.mutableCopyOf(message, other);
    }
    
    @Override
    int getSerializedSize(final UnknownFieldSetLite unknowns) {
        return unknowns.getSerializedSize();
    }
    
    @Override
    int getSerializedSizeAsMessageSet(final UnknownFieldSetLite unknowns) {
        return unknowns.getSerializedSizeAsMessageSet();
    }
}
