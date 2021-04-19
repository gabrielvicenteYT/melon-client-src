package com.mysql.cj.protocol.x;

import java.io.*;
import java.util.zip.*;

public class SyncFlushDeflaterOutputStream extends DeflaterOutputStream
{
    public SyncFlushDeflaterOutputStream(final OutputStream out) {
        super(out, new Deflater(), true);
    }
}
