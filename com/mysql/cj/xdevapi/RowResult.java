package com.mysql.cj.xdevapi;

import java.util.*;

public interface RowResult extends FetchResult<Row>, Result
{
    int getColumnCount();
    
    List<Column> getColumns();
    
    List<String> getColumnNames();
}
