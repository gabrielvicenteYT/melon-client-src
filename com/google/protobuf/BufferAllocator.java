package com.google.protobuf;

import java.nio.*;

abstract class BufferAllocator
{
    private static final BufferAllocator UNPOOLED;
    
    public static BufferAllocator unpooled() {
        return BufferAllocator.UNPOOLED;
    }
    
    public abstract AllocatedBuffer allocateHeapBuffer(final int p0);
    
    public abstract AllocatedBuffer allocateDirectBuffer(final int p0);
    
    static {
        UNPOOLED = new BufferAllocator() {
            @Override
            public AllocatedBuffer allocateHeapBuffer(final int capacity) {
                return AllocatedBuffer.wrap(new byte[capacity]);
            }
            
            @Override
            public AllocatedBuffer allocateDirectBuffer(final int capacity) {
                return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(capacity));
            }
        };
    }
}
