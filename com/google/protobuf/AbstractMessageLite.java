package com.google.protobuf;

import java.util.*;
import java.io.*;

public abstract class AbstractMessageLite<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> implements MessageLite
{
    protected int memoizedHashCode;
    
    public AbstractMessageLite() {
        this.memoizedHashCode = 0;
    }
    
    @Override
    public ByteString toByteString() {
        try {
            final ByteString.CodedBuilder out = ByteString.newCodedBuilder(this.getSerializedSize());
            this.writeTo(out.getCodedOutput());
            return out.build();
        }
        catch (IOException e) {
            throw new RuntimeException(this.getSerializingExceptionMessage("ByteString"), e);
        }
    }
    
    @Override
    public byte[] toByteArray() {
        try {
            final byte[] result = new byte[this.getSerializedSize()];
            final CodedOutputStream output = CodedOutputStream.newInstance(result);
            this.writeTo(output);
            output.checkNoSpaceLeft();
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException(this.getSerializingExceptionMessage("byte array"), e);
        }
    }
    
    @Override
    public void writeTo(final OutputStream output) throws IOException {
        final int bufferSize = CodedOutputStream.computePreferredBufferSize(this.getSerializedSize());
        final CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
        this.writeTo(codedOutput);
        codedOutput.flush();
    }
    
    @Override
    public void writeDelimitedTo(final OutputStream output) throws IOException {
        final int serialized = this.getSerializedSize();
        final int bufferSize = CodedOutputStream.computePreferredBufferSize(CodedOutputStream.computeRawVarint32Size(serialized) + serialized);
        final CodedOutputStream codedOutput = CodedOutputStream.newInstance(output, bufferSize);
        codedOutput.writeRawVarint32(serialized);
        this.writeTo(codedOutput);
        codedOutput.flush();
    }
    
    int getMemoizedSerializedSize() {
        throw new UnsupportedOperationException();
    }
    
    void setMemoizedSerializedSize(final int size) {
        throw new UnsupportedOperationException();
    }
    
    int getSerializedSize(final Schema schema) {
        int memoizedSerializedSize = this.getMemoizedSerializedSize();
        if (memoizedSerializedSize == -1) {
            memoizedSerializedSize = schema.getSerializedSize(this);
            this.setMemoizedSerializedSize(memoizedSerializedSize);
        }
        return memoizedSerializedSize;
    }
    
    UninitializedMessageException newUninitializedMessageException() {
        return new UninitializedMessageException(this);
    }
    
    private String getSerializingExceptionMessage(final String target) {
        return "Serializing " + this.getClass().getName() + " to a " + target + " threw an IOException (should never happen).";
    }
    
    protected static void checkByteStringIsUtf8(final ByteString byteString) throws IllegalArgumentException {
        if (!byteString.isValidUtf8()) {
            throw new IllegalArgumentException("Byte string is not UTF-8.");
        }
    }
    
    @Deprecated
    protected static <T> void addAll(final Iterable<T> values, final Collection<? super T> list) {
        Builder.addAll(values, (List)list);
    }
    
    protected static <T> void addAll(final Iterable<T> values, final List<? super T> list) {
        Builder.addAll(values, list);
    }
    
    public abstract static class Builder<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> implements MessageLite.Builder
    {
        @Override
        public abstract BuilderType clone();
        
        @Override
        public BuilderType mergeFrom(final CodedInputStream input) throws IOException {
            return this.mergeFrom(input, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public abstract BuilderType mergeFrom(final CodedInputStream p0, final ExtensionRegistryLite p1) throws IOException;
        
        @Override
        public BuilderType mergeFrom(final ByteString data) throws InvalidProtocolBufferException {
            try {
                final CodedInputStream input = data.newCodedInput();
                this.mergeFrom(input);
                input.checkLastTagWas(0);
                return (BuilderType)this;
            }
            catch (InvalidProtocolBufferException e) {
                throw e;
            }
            catch (IOException e2) {
                throw new RuntimeException(this.getReadingExceptionMessage("ByteString"), e2);
            }
        }
        
        @Override
        public BuilderType mergeFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            try {
                final CodedInputStream input = data.newCodedInput();
                this.mergeFrom(input, extensionRegistry);
                input.checkLastTagWas(0);
                return (BuilderType)this;
            }
            catch (InvalidProtocolBufferException e) {
                throw e;
            }
            catch (IOException e2) {
                throw new RuntimeException(this.getReadingExceptionMessage("ByteString"), e2);
            }
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data) throws InvalidProtocolBufferException {
            return this.mergeFrom(data, 0, data.length);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final int off, final int len) throws InvalidProtocolBufferException {
            try {
                final CodedInputStream input = CodedInputStream.newInstance(data, off, len);
                this.mergeFrom(input);
                input.checkLastTagWas(0);
                return (BuilderType)this;
            }
            catch (InvalidProtocolBufferException e) {
                throw e;
            }
            catch (IOException e2) {
                throw new RuntimeException(this.getReadingExceptionMessage("byte array"), e2);
            }
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return this.mergeFrom(data, 0, data.length, extensionRegistry);
        }
        
        @Override
        public BuilderType mergeFrom(final byte[] data, final int off, final int len, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            try {
                final CodedInputStream input = CodedInputStream.newInstance(data, off, len);
                this.mergeFrom(input, extensionRegistry);
                input.checkLastTagWas(0);
                return (BuilderType)this;
            }
            catch (InvalidProtocolBufferException e) {
                throw e;
            }
            catch (IOException e2) {
                throw new RuntimeException(this.getReadingExceptionMessage("byte array"), e2);
            }
        }
        
        @Override
        public BuilderType mergeFrom(final InputStream input) throws IOException {
            final CodedInputStream codedInput = CodedInputStream.newInstance(input);
            this.mergeFrom(codedInput);
            codedInput.checkLastTagWas(0);
            return (BuilderType)this;
        }
        
        @Override
        public BuilderType mergeFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final CodedInputStream codedInput = CodedInputStream.newInstance(input);
            this.mergeFrom(codedInput, extensionRegistry);
            codedInput.checkLastTagWas(0);
            return (BuilderType)this;
        }
        
        @Override
        public boolean mergeDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            final int firstByte = input.read();
            if (firstByte == -1) {
                return false;
            }
            final int size = CodedInputStream.readRawVarint32(firstByte, input);
            final InputStream limitedInput = new LimitedInputStream(input, size);
            this.mergeFrom(limitedInput, extensionRegistry);
            return true;
        }
        
        @Override
        public boolean mergeDelimitedFrom(final InputStream input) throws IOException {
            return this.mergeDelimitedFrom(input, ExtensionRegistryLite.getEmptyRegistry());
        }
        
        @Override
        public BuilderType mergeFrom(final MessageLite other) {
            if (!this.getDefaultInstanceForType().getClass().isInstance(other)) {
                throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
            }
            return this.internalMergeFrom((MessageType)other);
        }
        
        protected abstract BuilderType internalMergeFrom(final MessageType p0);
        
        private String getReadingExceptionMessage(final String target) {
            return "Reading " + this.getClass().getName() + " from a " + target + " threw an IOException (should never happen).";
        }
        
        private static <T> void addAllCheckingNulls(final Iterable<T> values, final List<? super T> list) {
            if (list instanceof ArrayList && values instanceof Collection) {
                ((ArrayList)list).ensureCapacity(list.size() + ((Collection)values).size());
            }
            final int begin = list.size();
            for (final T value : values) {
                if (value == null) {
                    final String message = "Element at index " + (list.size() - begin) + " is null.";
                    for (int i = list.size() - 1; i >= begin; --i) {
                        list.remove(i);
                    }
                    throw new NullPointerException(message);
                }
                list.add((Object)value);
            }
        }
        
        protected static UninitializedMessageException newUninitializedMessageException(final MessageLite message) {
            return new UninitializedMessageException(message);
        }
        
        @Deprecated
        protected static <T> void addAll(final Iterable<T> values, final Collection<? super T> list) {
            addAll(values, (List)list);
        }
        
        protected static <T> void addAll(final Iterable<T> values, final List<? super T> list) {
            Internal.checkNotNull(values);
            if (values instanceof LazyStringList) {
                final List<?> lazyValues = ((LazyStringList)values).getUnderlyingElements();
                final LazyStringList lazyList = (LazyStringList)list;
                final int begin = list.size();
                for (final Object value : lazyValues) {
                    if (value == null) {
                        final String message = "Element at index " + (lazyList.size() - begin) + " is null.";
                        for (int i = lazyList.size() - 1; i >= begin; --i) {
                            lazyList.remove(i);
                        }
                        throw new NullPointerException(message);
                    }
                    if (value instanceof ByteString) {
                        lazyList.add((ByteString)value);
                    }
                    else {
                        lazyList.add((String)value);
                    }
                }
            }
            else if (values instanceof PrimitiveNonBoxingCollection) {
                list.addAll((Collection)values);
            }
            else {
                addAllCheckingNulls((Iterable<Object>)values, (List<? super Object>)list);
            }
        }
        
        static final class LimitedInputStream extends FilterInputStream
        {
            private int limit;
            
            LimitedInputStream(final InputStream in, final int limit) {
                super(in);
                this.limit = limit;
            }
            
            @Override
            public int available() throws IOException {
                return Math.min(super.available(), this.limit);
            }
            
            @Override
            public int read() throws IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                final int result = super.read();
                if (result >= 0) {
                    --this.limit;
                }
                return result;
            }
            
            @Override
            public int read(final byte[] b, final int off, int len) throws IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                len = Math.min(len, this.limit);
                final int result = super.read(b, off, len);
                if (result >= 0) {
                    this.limit -= result;
                }
                return result;
            }
            
            @Override
            public long skip(final long n) throws IOException {
                final long result = super.skip(Math.min(n, this.limit));
                if (result >= 0L) {
                    this.limit -= (int)result;
                }
                return result;
            }
        }
    }
    
    protected interface InternalOneOfEnum
    {
        int getNumber();
    }
}
