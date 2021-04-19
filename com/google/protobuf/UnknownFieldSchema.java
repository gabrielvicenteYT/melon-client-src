package com.google.protobuf;

import java.io.*;

abstract class UnknownFieldSchema<T, B>
{
    abstract boolean shouldDiscardUnknownFields(final Reader p0);
    
    abstract void addVarint(final B p0, final int p1, final long p2);
    
    abstract void addFixed32(final B p0, final int p1, final int p2);
    
    abstract void addFixed64(final B p0, final int p1, final long p2);
    
    abstract void addLengthDelimited(final B p0, final int p1, final ByteString p2);
    
    abstract void addGroup(final B p0, final int p1, final T p2);
    
    abstract B newBuilder();
    
    abstract T toImmutable(final B p0);
    
    abstract void setToMessage(final Object p0, final T p1);
    
    abstract T getFromMessage(final Object p0);
    
    abstract B getBuilderFromMessage(final Object p0);
    
    abstract void setBuilderToMessage(final Object p0, final B p1);
    
    abstract void makeImmutable(final Object p0);
    
    final boolean mergeOneFieldFrom(final B unknownFields, final Reader reader) throws IOException {
        final int tag = reader.getTag();
        final int fieldNumber = WireFormat.getTagFieldNumber(tag);
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                this.addVarint(unknownFields, fieldNumber, reader.readInt64());
                return true;
            }
            case 5: {
                this.addFixed32(unknownFields, fieldNumber, reader.readFixed32());
                return true;
            }
            case 1: {
                this.addFixed64(unknownFields, fieldNumber, reader.readFixed64());
                return true;
            }
            case 2: {
                this.addLengthDelimited(unknownFields, fieldNumber, reader.readBytes());
                return true;
            }
            case 3: {
                final B subFields = this.newBuilder();
                final int endGroupTag = WireFormat.makeTag(fieldNumber, 4);
                this.mergeFrom(subFields, reader);
                if (endGroupTag != reader.getTag()) {
                    throw InvalidProtocolBufferException.invalidEndTag();
                }
                this.addGroup(unknownFields, fieldNumber, this.toImmutable(subFields));
                return true;
            }
            case 4: {
                return false;
            }
            default: {
                throw InvalidProtocolBufferException.invalidWireType();
            }
        }
    }
    
    final void mergeFrom(final B unknownFields, final Reader reader) throws IOException {
        while (reader.getFieldNumber() != Integer.MAX_VALUE && this.mergeOneFieldFrom(unknownFields, reader)) {}
    }
    
    abstract void writeTo(final T p0, final Writer p1) throws IOException;
    
    abstract void writeAsMessageSetTo(final T p0, final Writer p1) throws IOException;
    
    abstract T merge(final T p0, final T p1);
    
    abstract int getSerializedSizeAsMessageSet(final T p0);
    
    abstract int getSerializedSize(final T p0);
}
