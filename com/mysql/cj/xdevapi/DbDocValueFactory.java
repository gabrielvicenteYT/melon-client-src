package com.mysql.cj.xdevapi;

import com.mysql.cj.conf.*;
import com.mysql.cj.result.*;
import com.mysql.cj.util.*;
import com.mysql.cj.exceptions.*;
import java.io.*;

public class DbDocValueFactory extends DefaultValueFactory<DbDoc>
{
    public DbDocValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public DbDoc createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        try {
            return JsonParser.parseDoc(new StringReader(StringUtils.toString(bytes, offset, length, f.getEncoding())));
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    @Override
    public DbDoc createFromNull() {
        return null;
    }
    
    @Override
    public String getTargetTypeName() {
        return DbDoc.class.getName();
    }
}
