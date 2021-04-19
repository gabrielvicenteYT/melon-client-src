package com.mysql.cj.xdevapi;

import java.util.*;
import com.mysql.cj.result.*;
import java.util.function.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.conf.*;

public class SqlSingleResult extends RowResultImpl implements SqlResult
{
    public SqlSingleResult(final ColumnDefinition metadata, final TimeZone defaultTimeZone, final RowList rows, final Supplier<ProtocolEntity> completer, final PropertySet pset) {
        super(metadata, defaultTimeZone, rows, completer, pset);
    }
}
