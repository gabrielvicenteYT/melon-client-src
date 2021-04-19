package com.google.protobuf;

final class NewInstanceSchemaLite implements NewInstanceSchema
{
    @Override
    public Object newInstance(final Object defaultInstance) {
        return ((GeneratedMessageLite)defaultInstance).dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }
}
