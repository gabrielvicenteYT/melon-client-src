package com.google.protobuf;

import java.io.*;

public class LazyFieldLite
{
    private static final ExtensionRegistryLite EMPTY_REGISTRY;
    private ByteString delayedBytes;
    private ExtensionRegistryLite extensionRegistry;
    protected volatile MessageLite value;
    private volatile ByteString memoizedBytes;
    
    public LazyFieldLite(final ExtensionRegistryLite extensionRegistry, final ByteString bytes) {
        checkArguments(extensionRegistry, bytes);
        this.extensionRegistry = extensionRegistry;
        this.delayedBytes = bytes;
    }
    
    public LazyFieldLite() {
    }
    
    public static LazyFieldLite fromValue(final MessageLite value) {
        final LazyFieldLite lf = new LazyFieldLite();
        lf.setValue(value);
        return lf;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LazyFieldLite)) {
            return false;
        }
        final LazyFieldLite other = (LazyFieldLite)o;
        final MessageLite value1 = this.value;
        final MessageLite value2 = other.value;
        if (value1 == null && value2 == null) {
            return this.toByteString().equals(other.toByteString());
        }
        if (value1 != null && value2 != null) {
            return value1.equals(value2);
        }
        if (value1 != null) {
            return value1.equals(other.getValue(value1.getDefaultInstanceForType()));
        }
        return this.getValue(value2.getDefaultInstanceForType()).equals(value2);
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    public boolean containsDefaultInstance() {
        return this.memoizedBytes == ByteString.EMPTY || (this.value == null && (this.delayedBytes == null || this.delayedBytes == ByteString.EMPTY));
    }
    
    public void clear() {
        this.delayedBytes = null;
        this.value = null;
        this.memoizedBytes = null;
    }
    
    public void set(final LazyFieldLite other) {
        this.delayedBytes = other.delayedBytes;
        this.value = other.value;
        this.memoizedBytes = other.memoizedBytes;
        if (other.extensionRegistry != null) {
            this.extensionRegistry = other.extensionRegistry;
        }
    }
    
    public MessageLite getValue(final MessageLite defaultInstance) {
        this.ensureInitialized(defaultInstance);
        return this.value;
    }
    
    public MessageLite setValue(final MessageLite value) {
        final MessageLite originalValue = this.value;
        this.delayedBytes = null;
        this.memoizedBytes = null;
        this.value = value;
        return originalValue;
    }
    
    public void merge(final LazyFieldLite other) {
        if (other.containsDefaultInstance()) {
            return;
        }
        if (this.containsDefaultInstance()) {
            this.set(other);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = other.extensionRegistry;
        }
        if (this.delayedBytes != null && other.delayedBytes != null) {
            this.delayedBytes = this.delayedBytes.concat(other.delayedBytes);
            return;
        }
        if (this.value == null && other.value != null) {
            this.setValue(mergeValueAndBytes(other.value, this.delayedBytes, this.extensionRegistry));
            return;
        }
        if (this.value != null && other.value == null) {
            this.setValue(mergeValueAndBytes(this.value, other.delayedBytes, other.extensionRegistry));
            return;
        }
        this.setValue(this.value.toBuilder().mergeFrom(other.value).build());
    }
    
    public void mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.containsDefaultInstance()) {
            this.setByteString(input.readBytes(), extensionRegistry);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = extensionRegistry;
        }
        if (this.delayedBytes != null) {
            this.setByteString(this.delayedBytes.concat(input.readBytes()), this.extensionRegistry);
            return;
        }
        try {
            this.setValue(this.value.toBuilder().mergeFrom(input, extensionRegistry).build());
        }
        catch (InvalidProtocolBufferException ex) {}
    }
    
    private static MessageLite mergeValueAndBytes(final MessageLite value, final ByteString otherBytes, final ExtensionRegistryLite extensionRegistry) {
        try {
            return value.toBuilder().mergeFrom(otherBytes, extensionRegistry).build();
        }
        catch (InvalidProtocolBufferException e) {
            return value;
        }
    }
    
    public void setByteString(final ByteString bytes, final ExtensionRegistryLite extensionRegistry) {
        checkArguments(extensionRegistry, bytes);
        this.delayedBytes = bytes;
        this.extensionRegistry = extensionRegistry;
        this.value = null;
        this.memoizedBytes = null;
    }
    
    public int getSerializedSize() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes.size();
        }
        if (this.delayedBytes != null) {
            return this.delayedBytes.size();
        }
        if (this.value != null) {
            return this.value.getSerializedSize();
        }
        return 0;
    }
    
    public ByteString toByteString() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes;
        }
        if (this.delayedBytes != null) {
            return this.delayedBytes;
        }
        synchronized (this) {
            if (this.memoizedBytes != null) {
                return this.memoizedBytes;
            }
            if (this.value == null) {
                this.memoizedBytes = ByteString.EMPTY;
            }
            else {
                this.memoizedBytes = this.value.toByteString();
            }
            return this.memoizedBytes;
        }
    }
    
    void writeTo(final Writer writer, final int fieldNumber) throws IOException {
        if (this.memoizedBytes != null) {
            writer.writeBytes(fieldNumber, this.memoizedBytes);
        }
        else if (this.delayedBytes != null) {
            writer.writeBytes(fieldNumber, this.delayedBytes);
        }
        else if (this.value != null) {
            writer.writeMessage(fieldNumber, this.value);
        }
        else {
            writer.writeBytes(fieldNumber, ByteString.EMPTY);
        }
    }
    
    protected void ensureInitialized(final MessageLite defaultInstance) {
        if (this.value != null) {
            return;
        }
        synchronized (this) {
            if (this.value != null) {
                return;
            }
            try {
                if (this.delayedBytes != null) {
                    final MessageLite parsedValue = (MessageLite)defaultInstance.getParserForType().parseFrom(this.delayedBytes, this.extensionRegistry);
                    this.value = parsedValue;
                    this.memoizedBytes = this.delayedBytes;
                }
                else {
                    this.value = defaultInstance;
                    this.memoizedBytes = ByteString.EMPTY;
                }
            }
            catch (InvalidProtocolBufferException e) {
                this.value = defaultInstance;
                this.memoizedBytes = ByteString.EMPTY;
            }
        }
    }
    
    private static void checkArguments(final ExtensionRegistryLite extensionRegistry, final ByteString bytes) {
        if (extensionRegistry == null) {
            throw new NullPointerException("found null ExtensionRegistry");
        }
        if (bytes == null) {
            throw new NullPointerException("found null ByteString");
        }
    }
    
    static {
        EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();
    }
}
