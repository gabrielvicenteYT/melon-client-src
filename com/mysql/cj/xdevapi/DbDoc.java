package com.mysql.cj.xdevapi;

import java.util.*;

public interface DbDoc extends JsonValue, Map<String, JsonValue>
{
    DbDoc add(final String p0, final JsonValue p1);
}
