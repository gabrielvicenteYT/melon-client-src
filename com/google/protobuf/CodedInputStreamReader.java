package com.google.protobuf;

import java.io.*;
import java.util.*;

final class CodedInputStreamReader implements Reader
{
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;
    private static final int NEXT_TAG_UNSET = 0;
    private final CodedInputStream input;
    private int tag;
    private int endGroupTag;
    private int nextTag;
    
    public static CodedInputStreamReader forCodedInput(final CodedInputStream input) {
        if (input.wrapper != null) {
            return input.wrapper;
        }
        return new CodedInputStreamReader(input);
    }
    
    private CodedInputStreamReader(final CodedInputStream input) {
        this.nextTag = 0;
        this.input = Internal.checkNotNull(input, "input");
        this.input.wrapper = this;
    }
    
    @Override
    public boolean shouldDiscardUnknownFields() {
        return this.input.shouldDiscardUnknownFields();
    }
    
    @Override
    public int getFieldNumber() throws IOException {
        if (this.nextTag != 0) {
            this.tag = this.nextTag;
            this.nextTag = 0;
        }
        else {
            this.tag = this.input.readTag();
        }
        if (this.tag == 0 || this.tag == this.endGroupTag) {
            return Integer.MAX_VALUE;
        }
        return WireFormat.getTagFieldNumber(this.tag);
    }
    
    @Override
    public int getTag() {
        return this.tag;
    }
    
    @Override
    public boolean skipField() throws IOException {
        return !this.input.isAtEnd() && this.tag != this.endGroupTag && this.input.skipField(this.tag);
    }
    
    private void requireWireType(final int requiredWireType) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != requiredWireType) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }
    
    @Override
    public double readDouble() throws IOException {
        this.requireWireType(1);
        return this.input.readDouble();
    }
    
    @Override
    public float readFloat() throws IOException {
        this.requireWireType(5);
        return this.input.readFloat();
    }
    
    @Override
    public long readUInt64() throws IOException {
        this.requireWireType(0);
        return this.input.readUInt64();
    }
    
    @Override
    public long readInt64() throws IOException {
        this.requireWireType(0);
        return this.input.readInt64();
    }
    
    @Override
    public int readInt32() throws IOException {
        this.requireWireType(0);
        return this.input.readInt32();
    }
    
    @Override
    public long readFixed64() throws IOException {
        this.requireWireType(1);
        return this.input.readFixed64();
    }
    
    @Override
    public int readFixed32() throws IOException {
        this.requireWireType(5);
        return this.input.readFixed32();
    }
    
    @Override
    public boolean readBool() throws IOException {
        this.requireWireType(0);
        return this.input.readBool();
    }
    
    @Override
    public String readString() throws IOException {
        this.requireWireType(2);
        return this.input.readString();
    }
    
    @Override
    public String readStringRequireUtf8() throws IOException {
        this.requireWireType(2);
        return this.input.readStringRequireUtf8();
    }
    
    @Override
    public <T> T readMessage(final Class<T> clazz, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.requireWireType(2);
        return this.readMessage((Schema<T>)Protobuf.getInstance().schemaFor((Class<T>)clazz), extensionRegistry);
    }
    
    @Override
    public <T> T readMessageBySchemaWithCheck(final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.requireWireType(2);
        return (T)this.readMessage((Schema<Object>)schema, extensionRegistry);
    }
    
    @Override
    public <T> T readGroup(final Class<T> clazz, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.requireWireType(3);
        return this.readGroup((Schema<T>)Protobuf.getInstance().schemaFor((Class<T>)clazz), extensionRegistry);
    }
    
    @Override
    public <T> T readGroupBySchemaWithCheck(final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.requireWireType(3);
        return (T)this.readGroup((Schema<Object>)schema, extensionRegistry);
    }
    
    private <T> T readMessage(final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final int size = this.input.readUInt32();
        if (this.input.recursionDepth >= this.input.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        final int prevLimit = this.input.pushLimit(size);
        final T message = schema.newInstance();
        final CodedInputStream input = this.input;
        ++input.recursionDepth;
        schema.mergeFrom(message, this, extensionRegistry);
        schema.makeImmutable(message);
        this.input.checkLastTagWas(0);
        final CodedInputStream input2 = this.input;
        --input2.recursionDepth;
        this.input.popLimit(prevLimit);
        return message;
    }
    
    private <T> T readGroup(final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final int prevEndGroupTag = this.endGroupTag;
        this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
        try {
            final T message = schema.newInstance();
            schema.mergeFrom(message, this, extensionRegistry);
            schema.makeImmutable(message);
            if (this.tag != this.endGroupTag) {
                throw InvalidProtocolBufferException.parseFailure();
            }
            return message;
        }
        finally {
            this.endGroupTag = prevEndGroupTag;
        }
    }
    
    @Override
    public ByteString readBytes() throws IOException {
        this.requireWireType(2);
        return this.input.readBytes();
    }
    
    @Override
    public int readUInt32() throws IOException {
        this.requireWireType(0);
        return this.input.readUInt32();
    }
    
    @Override
    public int readEnum() throws IOException {
        this.requireWireType(0);
        return this.input.readEnum();
    }
    
    @Override
    public int readSFixed32() throws IOException {
        this.requireWireType(5);
        return this.input.readSFixed32();
    }
    
    @Override
    public long readSFixed64() throws IOException {
        this.requireWireType(1);
        return this.input.readSFixed64();
    }
    
    @Override
    public int readSInt32() throws IOException {
        this.requireWireType(0);
        return this.input.readSInt32();
    }
    
    @Override
    public long readSInt64() throws IOException {
        this.requireWireType(0);
        return this.input.readSInt64();
    }
    
    @Override
    public void readDoubleList(final List<Double> target) throws IOException {
        if (target instanceof DoubleArrayList) {
            final DoubleArrayList plist = (DoubleArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addDouble(this.input.readDouble());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 1: {
                    while (true) {
                        plist.addDouble(this.input.readDouble());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readDouble());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 1: {
                    while (true) {
                        target.add(this.input.readDouble());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readFloatList(final List<Float> target) throws IOException {
        if (target instanceof FloatArrayList) {
            final FloatArrayList plist = (FloatArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addFloat(this.input.readFloat());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 5: {
                    while (true) {
                        plist.addFloat(this.input.readFloat());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readFloat());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 5: {
                    while (true) {
                        target.add(this.input.readFloat());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readUInt64List(final List<Long> target) throws IOException {
        if (target instanceof LongArrayList) {
            final LongArrayList plist = (LongArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readUInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addLong(this.input.readUInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readUInt64());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readUInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readInt64List(final List<Long> target) throws IOException {
        if (target instanceof LongArrayList) {
            final LongArrayList plist = (LongArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addLong(this.input.readInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readInt64());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readInt32List(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addInt(this.input.readInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readInt32());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readFixed64List(final List<Long> target) throws IOException {
        if (target instanceof LongArrayList) {
            final LongArrayList plist = (LongArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readFixed64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 1: {
                    while (true) {
                        plist.addLong(this.input.readFixed64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readFixed64());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 1: {
                    while (true) {
                        target.add(this.input.readFixed64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readFixed32List(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readFixed32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 5: {
                    while (true) {
                        plist.addInt(this.input.readFixed32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readFixed32());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 5: {
                    while (true) {
                        target.add(this.input.readFixed32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readBoolList(final List<Boolean> target) throws IOException {
        if (target instanceof BooleanArrayList) {
            final BooleanArrayList plist = (BooleanArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addBoolean(this.input.readBool());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addBoolean(this.input.readBool());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readBool());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readBool());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readStringList(final List<String> target) throws IOException {
        this.readStringListInternal(target, false);
    }
    
    @Override
    public void readStringListRequireUtf8(final List<String> target) throws IOException {
        this.readStringListInternal(target, true);
    }
    
    public void readStringListInternal(final List<String> target, final boolean requireUtf8) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        if (target instanceof LazyStringList && !requireUtf8) {
            final LazyStringList lazyList = (LazyStringList)target;
            while (true) {
                lazyList.add(this.readBytes());
                if (this.input.isAtEnd()) {
                    return;
                }
                final int nextTag = this.input.readTag();
                if (nextTag != this.tag) {
                    this.nextTag = nextTag;
                }
            }
        }
        else {
            while (true) {
                target.add(requireUtf8 ? this.readStringRequireUtf8() : this.readString());
                if (this.input.isAtEnd()) {
                    return;
                }
                final int nextTag2 = this.input.readTag();
                if (nextTag2 != this.tag) {
                    this.nextTag = nextTag2;
                }
            }
        }
    }
    
    @Override
    public <T> void readMessageList(final List<T> target, final Class<T> targetType, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
        this.readMessageList(target, schema, extensionRegistry);
    }
    
    @Override
    public <T> void readMessageList(final List<T> target, final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        final int listTag = this.tag;
        while (true) {
            target.add(this.readMessage(schema, extensionRegistry));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            }
            final int nextTag = this.input.readTag();
            if (nextTag != listTag) {
                this.nextTag = nextTag;
            }
        }
    }
    
    @Override
    public <T> void readGroupList(final List<T> target, final Class<T> targetType, final ExtensionRegistryLite extensionRegistry) throws IOException {
        final Schema<T> schema = Protobuf.getInstance().schemaFor(targetType);
        this.readGroupList(target, schema, extensionRegistry);
    }
    
    @Override
    public <T> void readGroupList(final List<T> target, final Schema<T> schema, final ExtensionRegistryLite extensionRegistry) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        final int listTag = this.tag;
        while (true) {
            target.add(this.readGroup(schema, extensionRegistry));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            }
            final int nextTag = this.input.readTag();
            if (nextTag != listTag) {
                this.nextTag = nextTag;
            }
        }
    }
    
    @Override
    public void readBytesList(final List<ByteString> target) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        while (true) {
            target.add(this.readBytes());
            if (this.input.isAtEnd()) {
                return;
            }
            final int nextTag = this.input.readTag();
            if (nextTag != this.tag) {
                this.nextTag = nextTag;
            }
        }
    }
    
    @Override
    public void readUInt32List(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readUInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addInt(this.input.readUInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readUInt32());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readUInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readEnumList(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readEnum());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addInt(this.input.readEnum());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readEnum());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readEnum());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readSFixed32List(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readSFixed32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 5: {
                    while (true) {
                        plist.addInt(this.input.readSFixed32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed32Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readSFixed32());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 5: {
                    while (true) {
                        target.add(this.input.readSFixed32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readSFixed64List(final List<Long> target) throws IOException {
        if (target instanceof LongArrayList) {
            final LongArrayList plist = (LongArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes);
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readSFixed64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    break;
                }
                case 1: {
                    while (true) {
                        plist.addLong(this.input.readSFixed64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    this.verifyPackedFixed64Length(bytes2);
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readSFixed64());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    break;
                }
                case 1: {
                    while (true) {
                        target.add(this.input.readSFixed64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readSInt32List(final List<Integer> target) throws IOException {
        if (target instanceof IntArrayList) {
            final IntArrayList plist = (IntArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readSInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addInt(this.input.readSInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readSInt32());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readSInt32());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    @Override
    public void readSInt64List(final List<Long> target) throws IOException {
        if (target instanceof LongArrayList) {
            final LongArrayList plist = (LongArrayList)target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes = this.input.readUInt32();
                    final int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readSInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    this.requirePosition(endPos);
                    break;
                }
                case 0: {
                    while (true) {
                        plist.addLong(this.input.readSInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag = this.input.readTag();
                        if (nextTag != this.tag) {
                            this.nextTag = nextTag;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
        else {
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2: {
                    final int bytes2 = this.input.readUInt32();
                    final int endPos2 = this.input.getTotalBytesRead() + bytes2;
                    do {
                        target.add(this.input.readSInt64());
                    } while (this.input.getTotalBytesRead() < endPos2);
                    this.requirePosition(endPos2);
                    break;
                }
                case 0: {
                    while (true) {
                        target.add(this.input.readSInt64());
                        if (this.input.isAtEnd()) {
                            return;
                        }
                        final int nextTag2 = this.input.readTag();
                        if (nextTag2 != this.tag) {
                            this.nextTag = nextTag2;
                            return;
                        }
                    }
                    break;
                }
                default: {
                    throw InvalidProtocolBufferException.invalidWireType();
                }
            }
        }
    }
    
    private void verifyPackedFixed64Length(final int bytes) throws IOException {
        if ((bytes & 0x7) != 0x0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }
    
    @Override
    public <K, V> void readMap(final Map<K, V> target, final MapEntryLite.Metadata<K, V> metadata, final ExtensionRegistryLite extensionRegistry) throws IOException {
        this.requireWireType(2);
        final int size = this.input.readUInt32();
        final int prevLimit = this.input.pushLimit(size);
        K key = metadata.defaultKey;
        V value = metadata.defaultValue;
        try {
            while (true) {
                final int number = this.getFieldNumber();
                if (number == Integer.MAX_VALUE || this.input.isAtEnd()) {
                    break;
                }
                try {
                    switch (number) {
                        case 1: {
                            key = (K)this.readField(metadata.keyType, null, null);
                            continue;
                        }
                        case 2: {
                            value = (V)this.readField(metadata.valueType, metadata.defaultValue.getClass(), extensionRegistry);
                            continue;
                        }
                        default: {
                            if (!this.skipField()) {
                                throw new InvalidProtocolBufferException("Unable to parse map entry.");
                            }
                            continue;
                        }
                    }
                }
                catch (InvalidProtocolBufferException.InvalidWireTypeException ignore) {
                    if (!this.skipField()) {
                        throw new InvalidProtocolBufferException("Unable to parse map entry.");
                    }
                    continue;
                }
            }
            target.put(key, value);
        }
        finally {
            this.input.popLimit(prevLimit);
        }
    }
    
    private Object readField(final WireFormat.FieldType fieldType, final Class<?> messageType, final ExtensionRegistryLite extensionRegistry) throws IOException {
        switch (fieldType) {
            case BOOL: {
                return this.readBool();
            }
            case BYTES: {
                return this.readBytes();
            }
            case DOUBLE: {
                return this.readDouble();
            }
            case ENUM: {
                return this.readEnum();
            }
            case FIXED32: {
                return this.readFixed32();
            }
            case FIXED64: {
                return this.readFixed64();
            }
            case FLOAT: {
                return this.readFloat();
            }
            case INT32: {
                return this.readInt32();
            }
            case INT64: {
                return this.readInt64();
            }
            case MESSAGE: {
                return this.readMessage(messageType, extensionRegistry);
            }
            case SFIXED32: {
                return this.readSFixed32();
            }
            case SFIXED64: {
                return this.readSFixed64();
            }
            case SINT32: {
                return this.readSInt32();
            }
            case SINT64: {
                return this.readSInt64();
            }
            case STRING: {
                return this.readStringRequireUtf8();
            }
            case UINT32: {
                return this.readUInt32();
            }
            case UINT64: {
                return this.readUInt64();
            }
            default: {
                throw new RuntimeException("unsupported field type.");
            }
        }
    }
    
    private void verifyPackedFixed32Length(final int bytes) throws IOException {
        if ((bytes & 0x3) != 0x0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }
    
    private void requirePosition(final int expectedPosition) throws IOException {
        if (this.input.getTotalBytesRead() != expectedPosition) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }
}
