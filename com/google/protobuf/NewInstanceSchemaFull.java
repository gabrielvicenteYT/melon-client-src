package com.google.protobuf;

final class NewInstanceSchemaFull implements NewInstanceSchema
{
    @Override
    public Object newInstance(final Object defaultInstance) {
        return ((GeneratedMessageV3)defaultInstance).newInstance(GeneratedMessageV3.UnusedPrivateParameter.INSTANCE);
    }
}
