package com.google.protobuf;

import java.io.*;

class UnknownFieldSetSchema extends UnknownFieldSchema<UnknownFieldSet, UnknownFieldSet.Builder>
{
    private final boolean proto3;
    
    public UnknownFieldSetSchema(final boolean proto3) {
        this.proto3 = proto3;
    }
    
    @Override
    boolean shouldDiscardUnknownFields(final Reader reader) {
        return reader.shouldDiscardUnknownFields();
    }
    
    @Override
    UnknownFieldSet.Builder newBuilder() {
        return UnknownFieldSet.newBuilder();
    }
    
    @Override
    void addVarint(final UnknownFieldSet.Builder fields, final int number, final long value) {
        fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addVarint(value).build());
    }
    
    @Override
    void addFixed32(final UnknownFieldSet.Builder fields, final int number, final int value) {
        fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addFixed32(value).build());
    }
    
    @Override
    void addFixed64(final UnknownFieldSet.Builder fields, final int number, final long value) {
        fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addFixed64(value).build());
    }
    
    @Override
    void addLengthDelimited(final UnknownFieldSet.Builder fields, final int number, final ByteString value) {
        fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addLengthDelimited(value).build());
    }
    
    @Override
    void addGroup(final UnknownFieldSet.Builder fields, final int number, final UnknownFieldSet subFieldSet) {
        fields.mergeField(number, UnknownFieldSet.Field.newBuilder().addGroup(subFieldSet).build());
    }
    
    @Override
    UnknownFieldSet toImmutable(final UnknownFieldSet.Builder fields) {
        return fields.build();
    }
    
    @Override
    void writeTo(final UnknownFieldSet message, final Writer writer) throws IOException {
        message.writeTo(writer);
    }
    
    @Override
    void writeAsMessageSetTo(final UnknownFieldSet message, final Writer writer) throws IOException {
        message.writeAsMessageSetTo(writer);
    }
    
    @Override
    UnknownFieldSet getFromMessage(final Object message) {
        return ((GeneratedMessageV3)message).unknownFields;
    }
    
    @Override
    void setToMessage(final Object message, final UnknownFieldSet fields) {
        ((GeneratedMessageV3)message).unknownFields = fields;
    }
    
    @Override
    UnknownFieldSet.Builder getBuilderFromMessage(final Object message) {
        return ((GeneratedMessageV3)message).unknownFields.toBuilder();
    }
    
    @Override
    void setBuilderToMessage(final Object message, final UnknownFieldSet.Builder builder) {
        ((GeneratedMessageV3)message).unknownFields = builder.build();
    }
    
    @Override
    void makeImmutable(final Object message) {
    }
    
    @Override
    UnknownFieldSet merge(final UnknownFieldSet message, final UnknownFieldSet other) {
        return message.toBuilder().mergeFrom(other).build();
    }
    
    @Override
    int getSerializedSize(final UnknownFieldSet message) {
        return message.getSerializedSize();
    }
    
    @Override
    int getSerializedSizeAsMessageSet(final UnknownFieldSet unknowns) {
        return unknowns.getSerializedSizeAsMessageSet();
    }
}
