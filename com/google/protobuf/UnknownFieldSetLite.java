package com.google.protobuf;

import java.util.*;
import java.io.*;

public final class UnknownFieldSetLite
{
    private static final int MIN_CAPACITY = 8;
    private static final UnknownFieldSetLite DEFAULT_INSTANCE;
    private int count;
    private int[] tags;
    private Object[] objects;
    private int memoizedSerializedSize;
    private boolean isMutable;
    
    public static UnknownFieldSetLite getDefaultInstance() {
        return UnknownFieldSetLite.DEFAULT_INSTANCE;
    }
    
    static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite();
    }
    
    static UnknownFieldSetLite mutableCopyOf(final UnknownFieldSetLite first, final UnknownFieldSetLite second) {
        final int count = first.count + second.count;
        final int[] tags = Arrays.copyOf(first.tags, count);
        System.arraycopy(second.tags, 0, tags, first.count, second.count);
        final Object[] objects = Arrays.copyOf(first.objects, count);
        System.arraycopy(second.objects, 0, objects, first.count, second.count);
        return new UnknownFieldSetLite(count, tags, objects, true);
    }
    
    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }
    
    private UnknownFieldSetLite(final int count, final int[] tags, final Object[] objects, final boolean isMutable) {
        this.memoizedSerializedSize = -1;
        this.count = count;
        this.tags = tags;
        this.objects = objects;
        this.isMutable = isMutable;
    }
    
    public void makeImmutable() {
        this.isMutable = false;
    }
    
    void checkMutable() {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
    }
    
    public void writeTo(final CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.count; ++i) {
            final int tag = this.tags[i];
            final int fieldNumber = WireFormat.getTagFieldNumber(tag);
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    output.writeUInt64(fieldNumber, (long)this.objects[i]);
                    break;
                }
                case 5: {
                    output.writeFixed32(fieldNumber, (int)this.objects[i]);
                    break;
                }
                case 1: {
                    output.writeFixed64(fieldNumber, (long)this.objects[i]);
                    break;
                }
                case 2: {
                    output.writeBytes(fieldNumber, (ByteString)this.objects[i]);
                    break;
                }
                case 3: {
                    output.writeTag(fieldNumber, 3);
                    ((UnknownFieldSetLite)this.objects[i]).writeTo(output);
                    output.writeTag(fieldNumber, 4);
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    public void writeAsMessageSetTo(final CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.count; ++i) {
            final int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
            output.writeRawMessageSetExtension(fieldNumber, (ByteString)this.objects[i]);
        }
    }
    
    void writeAsMessageSetTo(final Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            for (int i = this.count - 1; i >= 0; --i) {
                final int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
                writer.writeMessageSetItem(fieldNumber, this.objects[i]);
            }
        }
        else {
            for (int i = 0; i < this.count; ++i) {
                final int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
                writer.writeMessageSetItem(fieldNumber, this.objects[i]);
            }
        }
    }
    
    public void writeTo(final Writer writer) throws IOException {
        if (this.count == 0) {
            return;
        }
        if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
            for (int i = 0; i < this.count; ++i) {
                writeField(this.tags[i], this.objects[i], writer);
            }
        }
        else {
            for (int i = this.count - 1; i >= 0; --i) {
                writeField(this.tags[i], this.objects[i], writer);
            }
        }
    }
    
    private static void writeField(final int tag, final Object object, final Writer writer) throws IOException {
        final int fieldNumber = WireFormat.getTagFieldNumber(tag);
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                writer.writeInt64(fieldNumber, (long)object);
                break;
            }
            case 5: {
                writer.writeFixed32(fieldNumber, (int)object);
                break;
            }
            case 1: {
                writer.writeFixed64(fieldNumber, (long)object);
                break;
            }
            case 2: {
                writer.writeBytes(fieldNumber, (ByteString)object);
                break;
            }
            case 3: {
                if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
                    writer.writeStartGroup(fieldNumber);
                    ((UnknownFieldSetLite)object).writeTo(writer);
                    writer.writeEndGroup(fieldNumber);
                    break;
                }
                writer.writeEndGroup(fieldNumber);
                ((UnknownFieldSetLite)object).writeTo(writer);
                writer.writeStartGroup(fieldNumber);
                break;
            }
            default: {
                throw new RuntimeException(InvalidProtocolBufferException.invalidWireType());
            }
        }
    }
    
    public int getSerializedSizeAsMessageSet() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        for (int i = 0; i < this.count; ++i) {
            final int tag = this.tags[i];
            final int fieldNumber = WireFormat.getTagFieldNumber(tag);
            size += CodedOutputStream.computeRawMessageSetExtensionSize(fieldNumber, (ByteString)this.objects[i]);
        }
        return this.memoizedSerializedSize = size;
    }
    
    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        for (int i = 0; i < this.count; ++i) {
            final int tag = this.tags[i];
            final int fieldNumber = WireFormat.getTagFieldNumber(tag);
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    size += CodedOutputStream.computeUInt64Size(fieldNumber, (long)this.objects[i]);
                    break;
                }
                case 5: {
                    size += CodedOutputStream.computeFixed32Size(fieldNumber, (int)this.objects[i]);
                    break;
                }
                case 1: {
                    size += CodedOutputStream.computeFixed64Size(fieldNumber, (long)this.objects[i]);
                    break;
                }
                case 2: {
                    size += CodedOutputStream.computeBytesSize(fieldNumber, (ByteString)this.objects[i]);
                    break;
                }
                case 3: {
                    size += CodedOutputStream.computeTagSize(fieldNumber) * 2 + ((UnknownFieldSetLite)this.objects[i]).getSerializedSize();
                    break;
                }
                default: {
                    throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                }
            }
        }
        return this.memoizedSerializedSize = size;
    }
    
    private static boolean equals(final int[] tags1, final int[] tags2, final int count) {
        for (int i = 0; i < count; ++i) {
            if (tags1[i] != tags2[i]) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean equals(final Object[] objects1, final Object[] objects2, final int count) {
        for (int i = 0; i < count; ++i) {
            if (!objects1[i].equals(objects2[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        final UnknownFieldSetLite other = (UnknownFieldSetLite)obj;
        return this.count == other.count && equals(this.tags, other.tags, this.count) && equals(this.objects, other.objects, this.count);
    }
    
    private static int hashCode(final int[] tags, final int count) {
        int hashCode = 17;
        for (int i = 0; i < count; ++i) {
            hashCode = 31 * hashCode + tags[i];
        }
        return hashCode;
    }
    
    private static int hashCode(final Object[] objects, final int count) {
        int hashCode = 17;
        for (int i = 0; i < count; ++i) {
            hashCode = 31 * hashCode + objects[i].hashCode();
        }
        return hashCode;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.count;
        hashCode = 31 * hashCode + hashCode(this.tags, this.count);
        hashCode = 31 * hashCode + hashCode(this.objects, this.count);
        return hashCode;
    }
    
    final void printWithIndent(final StringBuilder buffer, final int indent) {
        for (int i = 0; i < this.count; ++i) {
            final int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
            MessageLiteToString.printField(buffer, indent, String.valueOf(fieldNumber), this.objects[i]);
        }
    }
    
    void storeField(final int tag, final Object value) {
        this.checkMutable();
        this.ensureCapacity();
        this.tags[this.count] = tag;
        this.objects[this.count] = value;
        ++this.count;
    }
    
    private void ensureCapacity() {
        if (this.count == this.tags.length) {
            final int increment = (this.count < 4) ? 8 : (this.count >> 1);
            final int newLength = this.count + increment;
            this.tags = Arrays.copyOf(this.tags, newLength);
            this.objects = Arrays.copyOf(this.objects, newLength);
        }
    }
    
    boolean mergeFieldFrom(final int tag, final CodedInputStream input) throws IOException {
        this.checkMutable();
        final int fieldNumber = WireFormat.getTagFieldNumber(tag);
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                this.storeField(tag, input.readInt64());
                return true;
            }
            case 5: {
                this.storeField(tag, input.readFixed32());
                return true;
            }
            case 1: {
                this.storeField(tag, input.readFixed64());
                return true;
            }
            case 2: {
                this.storeField(tag, input.readBytes());
                return true;
            }
            case 3: {
                final UnknownFieldSetLite subFieldSet = new UnknownFieldSetLite();
                subFieldSet.mergeFrom(input);
                input.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                this.storeField(tag, subFieldSet);
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
    
    UnknownFieldSetLite mergeVarintField(final int fieldNumber, final int value) {
        this.checkMutable();
        if (fieldNumber == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        this.storeField(WireFormat.makeTag(fieldNumber, 0), (long)value);
        return this;
    }
    
    UnknownFieldSetLite mergeLengthDelimitedField(final int fieldNumber, final ByteString value) {
        this.checkMutable();
        if (fieldNumber == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        this.storeField(WireFormat.makeTag(fieldNumber, 2), value);
        return this;
    }
    
    private UnknownFieldSetLite mergeFrom(final CodedInputStream input) throws IOException {
        int tag;
        do {
            tag = input.readTag();
        } while (tag != 0 && this.mergeFieldFrom(tag, input));
        return this;
    }
    
    static {
        DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    }
}
