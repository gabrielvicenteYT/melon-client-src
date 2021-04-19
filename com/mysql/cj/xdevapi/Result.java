package com.mysql.cj.xdevapi;

import com.mysql.cj.*;
import java.util.*;

public interface Result extends QueryResult
{
    long getAffectedItemsCount();
    
    int getWarningsCount();
    
    Iterator<Warning> getWarnings();
}
