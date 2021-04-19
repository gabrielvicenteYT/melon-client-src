package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import java.io.*;

public class BinaryStreamValueFactory extends DefaultValueFactory<InputStream>
{
    public BinaryStreamValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    @Override
    public InputStream createFromBytes(final byte[] bytes, final int offset, final int length, final Field f) {
        return new ByteArrayInputStream(bytes, offset, length);
    }
    
    @Override
    public String getTargetTypeName() {
        return InputStream.class.getName();
    }
}
