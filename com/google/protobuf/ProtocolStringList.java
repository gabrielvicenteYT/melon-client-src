package com.google.protobuf;

import java.util.*;

public interface ProtocolStringList extends List<String>
{
    List<ByteString> asByteStringList();
}
