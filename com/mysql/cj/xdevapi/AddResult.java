package com.mysql.cj.xdevapi;

import java.util.*;

public interface AddResult extends Result
{
    List<String> getGeneratedIds();
}
