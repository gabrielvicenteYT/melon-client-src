package com.mysql.cj.xdevapi;

import java.util.function.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.exceptions.*;
import java.util.*;
import com.mysql.cj.result.*;

public abstract class AbstractDataResult<T> implements ResultStreamer, Iterator<T>, Result
{
    protected int position;
    protected int count;
    protected RowList rows;
    protected Supplier<ProtocolEntity> completer;
    protected StatementExecuteOk ok;
    protected ProtocolEntityFactory<T, XMessage> rowToData;
    protected List<T> all;
    
    public AbstractDataResult(final RowList rows, final Supplier<ProtocolEntity> completer, final ProtocolEntityFactory<T, XMessage> rowToData) {
        this.position = -1;
        this.count = -1;
        this.rows = rows;
        this.completer = completer;
        this.rowToData = rowToData;
    }
    
    @Override
    public T next() {
        if (this.all != null) {
            throw new WrongArgumentException("Cannot iterate after fetchAll()");
        }
        final Row r = this.rows.next();
        if (r == null) {
            throw new NoSuchElementException();
        }
        ++this.position;
        return this.rowToData.createFromProtocolEntity(r);
    }
    
    public List<T> fetchAll() {
        if (this.position > -1) {
            throw new WrongArgumentException("Cannot fetchAll() after starting iteration");
        }
        if (this.all == null) {
            this.all = new ArrayList<T>((int)this.count());
            this.rows.forEachRemaining(r -> this.all.add(this.rowToData.createFromProtocolEntity(r)));
            this.all = Collections.unmodifiableList((List<? extends T>)this.all);
        }
        return this.all;
    }
    
    public long count() {
        this.finishStreaming();
        return this.count;
    }
    
    @Override
    public boolean hasNext() {
        return this.rows.hasNext();
    }
    
    public StatementExecuteOk getStatementExecuteOk() {
        this.finishStreaming();
        return this.ok;
    }
    
    @Override
    public void finishStreaming() {
        if (this.ok == null) {
            final BufferedRowList remainingRows = new BufferedRowList(this.rows);
            this.count = 1 + this.position + remainingRows.size();
            this.rows = remainingRows;
            this.ok = this.completer.get();
        }
    }
    
    @Override
    public long getAffectedItemsCount() {
        return this.getStatementExecuteOk().getAffectedItemsCount();
    }
    
    @Override
    public int getWarningsCount() {
        return this.getStatementExecuteOk().getWarningsCount();
    }
    
    @Override
    public Iterator<Warning> getWarnings() {
        return this.getStatementExecuteOk().getWarnings();
    }
}
