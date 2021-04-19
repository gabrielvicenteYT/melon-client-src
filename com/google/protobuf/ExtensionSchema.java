package com.google.protobuf;

import java.io.*;
import java.util.*;

abstract class ExtensionSchema<T extends FieldSet.FieldDescriptorLite<T>>
{
    abstract boolean hasExtensions(final MessageLite p0);
    
    abstract FieldSet<T> getExtensions(final Object p0);
    
    abstract void setExtensions(final Object p0, final FieldSet<T> p1);
    
    abstract FieldSet<T> getMutableExtensions(final Object p0);
    
    abstract void makeImmutable(final Object p0);
    
    abstract <UT, UB> UB parseExtension(final Reader p0, final Object p1, final ExtensionRegistryLite p2, final FieldSet<T> p3, final UB p4, final UnknownFieldSchema<UT, UB> p5) throws IOException;
    
    abstract int extensionNumber(final Map.Entry<?, ?> p0);
    
    abstract void serializeExtension(final Writer p0, final Map.Entry<?, ?> p1) throws IOException;
    
    abstract Object findExtensionByNumber(final ExtensionRegistryLite p0, final MessageLite p1, final int p2);
    
    abstract void parseLengthPrefixedMessageSetItem(final Reader p0, final Object p1, final ExtensionRegistryLite p2, final FieldSet<T> p3) throws IOException;
    
    abstract void parseMessageSetItem(final ByteString p0, final Object p1, final ExtensionRegistryLite p2, final FieldSet<T> p3) throws IOException;
}
