package com.mysql.cj;

import com.mysql.cj.conf.*;
import com.mysql.cj.exceptions.*;
import java.io.*;
import com.mysql.cj.xdevapi.*;
import com.mysql.cj.result.*;
import java.util.function.*;
import java.util.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.protocol.*;
import java.util.stream.*;
import java.util.concurrent.*;

public class MysqlxSession extends CoreSession
{
    public MysqlxSession(final HostInfo hostInfo, final PropertySet propSet) {
        super(hostInfo, propSet);
        this.protocol = new XProtocol(hostInfo, propSet);
        this.messageBuilder = this.protocol.getMessageBuilder();
        this.protocol.connect(hostInfo.getUser(), hostInfo.getPassword(), hostInfo.getDatabase());
    }
    
    public MysqlxSession(final XProtocol prot) {
        super(null, prot.getPropertySet());
        this.protocol = prot;
        this.messageBuilder = this.protocol.getMessageBuilder();
    }
    
    @Override
    public String getProcessHost() {
        return this.protocol.getSocketConnection().getHost();
    }
    
    public int getPort() {
        return this.protocol.getSocketConnection().getPort();
    }
    
    public XProtocol getProtocol() {
        return (XProtocol)this.protocol;
    }
    
    @Override
    public void quit() {
        try {
            this.protocol.close();
        }
        catch (IOException ex) {
            throw new CJCommunicationsException(ex);
        }
        super.quit();
    }
    
    @Override
    public boolean isClosed() {
        return !((XProtocol)this.protocol).isOpen();
    }
    
    public boolean supportsPreparedStatements() {
        return ((XProtocol)this.protocol).supportsPreparedStatements();
    }
    
    public boolean readyForPreparingStatements() {
        return ((XProtocol)this.protocol).readyForPreparingStatements();
    }
    
    public int getNewPreparedStatementId(final PreparableStatement<?> preparableStatement) {
        return ((XProtocol)this.protocol).getNewPreparedStatementId(preparableStatement);
    }
    
    public void freePreparedStatementId(final int preparedStatementId) {
        ((XProtocol)this.protocol).freePreparedStatementId(preparedStatementId);
    }
    
    public boolean failedPreparingStatement(final int preparedStatementId, final XProtocolError e) {
        return ((XProtocol)this.protocol).failedPreparingStatement(preparedStatementId, e);
    }
    
    @Override
    public <M extends Message, R, RES> RES query(final M message, final Predicate<Row> rowFilter, final Function<Row, R> rowMapper, final Collector<R, ?, RES> collector) {
        this.protocol.send(message, 0);
        final ColumnDefinition metadata = this.protocol.readMetadata();
        final Iterator<Row> ris = new XProtocolRowInputStream(metadata, (XProtocol)this.protocol, null);
        Stream<Row> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<? extends Row>)ris, 0), false);
        if (rowFilter != null) {
            stream = stream.filter(rowFilter);
        }
        final RES result = stream.map((Function<? super Row, ?>)rowMapper).collect((Collector<? super Object, ?, RES>)collector);
        this.protocol.readQueryResult((ResultBuilder<QueryResult>)new StatementExecuteOkBuilder());
        return result;
    }
    
    @Override
    public <M extends Message, R extends QueryResult> R query(final M message, final ResultBuilder<R> resultBuilder) {
        return ((XProtocol)this.protocol).query(message, resultBuilder);
    }
    
    @Override
    public <M extends Message, R extends QueryResult> CompletableFuture<R> queryAsync(final M message, final ResultBuilder<R> resultBuilder) {
        return ((XProtocol)this.protocol).queryAsync(message, resultBuilder);
    }
}
