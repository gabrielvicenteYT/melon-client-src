package com.google.protobuf;

interface SchemaFactory
{
     <T> Schema<T> createSchema(final Class<T> p0);
}
