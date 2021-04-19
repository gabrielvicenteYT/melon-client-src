package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.*;
import com.mysql.cj.conf.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.result.*;

public class DbDocFactory implements ProtocolEntityFactory<DbDoc, XMessage>
{
    private PropertySet pset;
    
    public DbDocFactory(final PropertySet pset) {
        this.pset = pset;
    }
    
    @Override
    public DbDoc createFromProtocolEntity(final ProtocolEntity internalRow) {
        return ((Row)internalRow).getValue(0, (ValueFactory<DbDoc>)new DbDocValueFactory(this.pset));
    }
}
