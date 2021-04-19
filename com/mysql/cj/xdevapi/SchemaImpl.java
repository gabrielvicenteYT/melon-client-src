package com.mysql.cj.xdevapi;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import com.mysql.cj.result.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public class SchemaImpl implements Schema
{
    private MysqlxSession mysqlxSession;
    private XMessageBuilder xbuilder;
    private Session session;
    private String name;
    private ValueFactory<String> svf;
    
    SchemaImpl(final MysqlxSession mysqlxSession, final Session session, final String name) {
        this.mysqlxSession = mysqlxSession;
        this.session = session;
        this.name = name;
        this.xbuilder = (XMessageBuilder)this.mysqlxSession.getMessageBuilder();
        this.svf = new StringValueFactory(this.mysqlxSession.getPropertySet());
    }
    
    @Override
    public Session getSession() {
        return this.session;
    }
    
    @Override
    public Schema getSchema() {
        return this;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public DatabaseObject.DbObjectStatus existsInDatabase() {
        final StringBuilder stmt = new StringBuilder("select count(*) from information_schema.schemata where schema_name = '");
        stmt.append(this.name.replaceAll("'", "\\'"));
        stmt.append("'");
        return this.mysqlxSession.getDataStoreMetadata().schemaExists(this.name) ? DatabaseObject.DbObjectStatus.EXISTS : DatabaseObject.DbObjectStatus.NOT_EXISTS;
    }
    
    @Override
    public List<Collection> getCollections() {
        return this.getCollections(null);
    }
    
    @Override
    public List<Collection> getCollections(final String pattern) {
        final Set<String> strTypes = Arrays.stream(new DatabaseObject.DbObjectType[] { DatabaseObject.DbObjectType.COLLECTION }).map((Function<? super DatabaseObject.DbObjectType, ?>)Enum::toString).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
        final Predicate<Row> rowFiler = r -> strTypes.contains(r.getValue(1, this.svf));
        final Function<Row, String> rowToName = (Function<Row, String>)(r -> r.getValue(0, this.svf));
        final List<String> objectNames = this.mysqlxSession.query(this.xbuilder.buildListObjects(this.name, pattern), rowFiler, rowToName, Collectors.toList());
        return objectNames.stream().map((Function<? super Object, ?>)this::getCollection).collect((Collector<? super Object, ?, List<Collection>>)Collectors.toList());
    }
    
    @Override
    public List<Table> getTables() {
        return this.getTables(null);
    }
    
    @Override
    public List<Table> getTables(final String pattern) {
        final Set<String> strTypes = Arrays.stream(new DatabaseObject.DbObjectType[] { DatabaseObject.DbObjectType.TABLE, DatabaseObject.DbObjectType.VIEW, DatabaseObject.DbObjectType.COLLECTION_VIEW }).map((Function<? super DatabaseObject.DbObjectType, ?>)Enum::toString).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
        final Predicate<Row> rowFiler = r -> strTypes.contains(r.getValue(1, this.svf));
        final Function<Row, String> rowToName = (Function<Row, String>)(r -> r.getValue(0, this.svf));
        final List<String> objectNames = this.mysqlxSession.query(this.xbuilder.buildListObjects(this.name, pattern), rowFiler, rowToName, Collectors.toList());
        return objectNames.stream().map((Function<? super Object, ?>)this::getTable).collect((Collector<? super Object, ?, List<Table>>)Collectors.toList());
    }
    
    @Override
    public Collection getCollection(final String collectionName) {
        return new CollectionImpl(this.mysqlxSession, this, collectionName);
    }
    
    @Override
    public Collection getCollection(final String collectionName, final boolean requireExists) {
        final CollectionImpl coll = new CollectionImpl(this.mysqlxSession, this, collectionName);
        if (requireExists && coll.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
            throw new WrongArgumentException(coll.toString() + " doesn't exist");
        }
        return coll;
    }
    
    @Override
    public Table getCollectionAsTable(final String collectionName) {
        return this.getTable(collectionName);
    }
    
    @Override
    public Table getTable(final String tableName) {
        return new TableImpl(this.mysqlxSession, this, tableName);
    }
    
    @Override
    public Table getTable(final String tableName, final boolean requireExists) {
        final TableImpl table = new TableImpl(this.mysqlxSession, this, tableName);
        if (requireExists && table.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
            throw new WrongArgumentException(table.toString() + " doesn't exist");
        }
        return table;
    }
    
    @Override
    public Collection createCollection(final String collectionName) {
        this.mysqlxSession.query(this.xbuilder.buildCreateCollection(this.name, collectionName), new UpdateResultBuilder<QueryResult>());
        return new CollectionImpl(this.mysqlxSession, this, collectionName);
    }
    
    @Override
    public Collection createCollection(final String collectionName, final boolean reuseExisting) {
        try {
            return this.createCollection(collectionName);
        }
        catch (XProtocolError ex) {
            if (reuseExisting && ex.getErrorCode() == 1050) {
                return this.getCollection(collectionName);
            }
            throw ex;
        }
    }
    
    @Override
    public Collection createCollection(final String collectionName, final CreateCollectionOptions options) {
        try {
            this.mysqlxSession.query(this.xbuilder.buildCreateCollection(this.name, collectionName, options), new UpdateResultBuilder<QueryResult>());
            return new CollectionImpl(this.mysqlxSession, this, collectionName);
        }
        catch (XProtocolError ex) {
            if (ex.getErrorCode() == 5015) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Schema.CreateCollection"), ex);
            }
            throw ex;
        }
    }
    
    @Override
    public void modifyCollection(final String collectionName, final ModifyCollectionOptions options) {
        try {
            this.mysqlxSession.query(this.xbuilder.buildModifyCollectionOptions(this.name, collectionName, options), new UpdateResultBuilder<QueryResult>());
        }
        catch (XProtocolError ex) {
            if (ex.getErrorCode() == 5157) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Schema.CreateCollection"), ex);
            }
            throw ex;
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other.getClass() == SchemaImpl.class && ((SchemaImpl)other).session == this.session && ((SchemaImpl)other).mysqlxSession == this.mysqlxSession && this.name.equals(((SchemaImpl)other).name);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schema(");
        sb.append(ExprUnparser.quoteIdentifier(this.name));
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public void dropCollection(final String collectionName) {
        try {
            this.mysqlxSession.query(this.xbuilder.buildDropCollection(this.name, collectionName), new UpdateResultBuilder<QueryResult>());
        }
        catch (XProtocolError e) {
            if (e.getErrorCode() != 1051) {
                throw e;
            }
        }
    }
}
