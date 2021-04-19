package com.google.protobuf;

interface MessageInfoFactory
{
    boolean isSupported(final Class<?> p0);
    
    MessageInfo messageInfoFor(final Class<?> p0);
}
