package com.mysql.cj.xdevapi;

import com.mysql.cj.result.*;
import java.util.function.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;

public class DocResultImpl extends AbstractDataResult<DbDoc> implements DocResult
{
    public DocResultImpl(final RowList rows, final Supplier<ProtocolEntity> completer, final PropertySet pset) {
        super(rows, completer, new DbDocFactory(pset));
    }
}
