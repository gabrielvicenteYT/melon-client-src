package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;
import com.mysql.cj.result.*;
import java.util.function.*;
import java.util.*;

public class XProtocolRowInputStream implements RowList
{
    private ColumnDefinition metadata;
    private XProtocol protocol;
    private boolean isDone;
    private int position;
    private Row next;
    private Consumer<Notice> noticeConsumer;
    
    public XProtocolRowInputStream(final ColumnDefinition metadata, final XProtocol protocol, final Consumer<Notice> noticeConsumer) {
        this.isDone = false;
        this.position = -1;
        this.metadata = metadata;
        this.protocol = protocol;
        this.noticeConsumer = noticeConsumer;
    }
    
    public XProtocolRowInputStream(final ColumnDefinition metadata, final Row row, final XProtocol protocol, final Consumer<Notice> noticeConsumer) {
        this.isDone = false;
        this.position = -1;
        this.metadata = metadata;
        this.protocol = protocol;
        (this.next = row).setMetadata(metadata);
        this.noticeConsumer = noticeConsumer;
    }
    
    public Row readRow() {
        if (!this.hasNext()) {
            this.isDone = true;
            return null;
        }
        ++this.position;
        final Row r = this.next;
        this.next = null;
        return r;
    }
    
    @Override
    public Row next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.readRow();
    }
    
    @Override
    public boolean hasNext() {
        if (this.isDone) {
            return false;
        }
        if (this.next == null) {
            this.next = this.protocol.readRowOrNull(this.metadata, this.noticeConsumer);
        }
        return this.next != null;
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
}
