package com.google.protobuf;

public interface RpcCallback<ParameterType>
{
    void run(final ParameterType p0);
}
