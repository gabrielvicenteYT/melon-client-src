package com.mysql.cj.protocol.x;

import com.google.protobuf.*;
import java.util.function.*;
import java.util.stream.*;
import com.mysql.cj.*;
import com.mysql.cj.xdevapi.*;
import java.util.*;
import com.mysql.cj.util.*;
import com.mysql.cj.protocol.*;
import java.security.*;
import java.math.*;
import javax.security.auth.callback.*;
import javax.security.sasl.*;
import com.mysql.cj.x.protobuf.*;

public class XMessageBuilder implements MessageBuilder<XMessage>
{
    private static final String XPLUGIN_NAMESPACE = "mysqlx";
    
    public XMessage buildCapabilitiesGet() {
        return new XMessage(MysqlxConnection.CapabilitiesGet.getDefaultInstance());
    }
    
    public XMessage buildCapabilitiesSet(final Map<String, Object> keyValuePair) {
        final MysqlxConnection.Capabilities.Builder capsB = MysqlxConnection.Capabilities.newBuilder();
        MysqlxDatatypes.Object.Builder attrB;
        MysqlxDatatypes.Any val;
        final MysqlxConnection.Capability cap;
        final MysqlxConnection.Capabilities.Builder builder;
        keyValuePair.forEach((k, v) -> {
            if (XServerCapabilities.KEY_SESSION_CONNECT_ATTRS.equals(k) || XServerCapabilities.KEY_COMPRESSION.equals(k)) {
                attrB = MysqlxDatatypes.Object.newBuilder();
                v.forEach((name, value) -> attrB.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey(name).setValue(ExprUtil.argObjectToScalarAny(value)).build()));
                val = MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(attrB).build();
            }
            else {
                val = ExprUtil.argObjectToScalarAny(v);
            }
            cap = MysqlxConnection.Capability.newBuilder().setName(k).setValue(val).build();
            builder.addCapabilities(cap);
            return;
        });
        return new XMessage(MysqlxConnection.CapabilitiesSet.newBuilder().setCapabilities(capsB).build());
    }
    
    public XMessage buildDocInsert(final String schemaName, final String collectionName, final List<String> json, final boolean upsert) {
        final MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setCollection(ExprUtil.buildCollection(schemaName, collectionName));
        if (upsert != builder.getUpsert()) {
            builder.setUpsert(upsert);
        }
        json.stream().map(str -> MysqlxCrud.Insert.TypedRow.newBuilder().addField(ExprUtil.argObjectToExpr(str, false)).build()).forEach((Consumer<? super Object>)builder::addRow);
        return new XMessage(builder.build());
    }
    
    private MysqlxCrud.Insert.Builder commonRowInsertBuilder(final String schemaName, final String tableName, final InsertParams insertParams) {
        final MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection(ExprUtil.buildCollection(schemaName, tableName));
        if (insertParams.getProjection() != null) {
            builder.addAllProjection((Iterable<? extends MysqlxCrud.Column>)insertParams.getProjection());
        }
        return builder;
    }
    
    public XMessage buildRowInsert(final String schemaName, final String tableName, final InsertParams insertParams) {
        final MysqlxCrud.Insert.Builder builder = this.commonRowInsertBuilder(schemaName, tableName, insertParams);
        builder.addAllRow((Iterable<? extends MysqlxCrud.Insert.TypedRow>)insertParams.getRows());
        return new XMessage(builder.build());
    }
    
    private MysqlxCrud.Update.Builder commonDocUpdateBuilder(final FilterParams filterParams, final List<UpdateSpec> updates) {
        final MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        final MysqlxCrud.UpdateOperation.Builder opBuilder;
        final MysqlxCrud.Update.Builder builder2;
        updates.forEach(u -> {
            opBuilder = MysqlxCrud.UpdateOperation.newBuilder();
            opBuilder.setOperation((MysqlxCrud.UpdateOperation.UpdateType)u.getUpdateType());
            opBuilder.setSource((MysqlxExpr.ColumnIdentifier)u.getSource());
            if (u.getValue() != null) {
                opBuilder.setValue((MysqlxExpr.Expr)u.getValue());
            }
            builder2.addOperation(opBuilder.build());
            return;
        });
        return builder;
    }
    
    public XMessage buildDocUpdate(final FilterParams filterParams, final List<UpdateSpec> updates) {
        final MysqlxCrud.Update.Builder builder = this.commonDocUpdateBuilder(filterParams, updates);
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareDocUpdate(final int preparedStatementId, final FilterParams filterParams, final List<UpdateSpec> updates) {
        final MysqlxCrud.Update.Builder updateBuilder = this.commonDocUpdateBuilder(filterParams, updates);
        applyFilterParams(filterParams, updateBuilder::addAllOrder, updateBuilder::setLimitExpr, updateBuilder::setCriteria);
        final MysqlxPrepare.Prepare.Builder builder = MysqlxPrepare.Prepare.newBuilder().setStmtId(preparedStatementId);
        builder.setStmt(MysqlxPrepare.Prepare.OneOfMessage.newBuilder().setType(MysqlxPrepare.Prepare.OneOfMessage.Type.UPDATE).setUpdate(updateBuilder.build()).build());
        return new XMessage(builder.build());
    }
    
    private MysqlxCrud.Update.Builder commonRowUpdateBuilder(final FilterParams filterParams, final UpdateParams updateParams) {
        final MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        ((Map)updateParams.getUpdates()).entrySet().stream().map(e -> MysqlxCrud.UpdateOperation.newBuilder().setOperation(MysqlxCrud.UpdateOperation.UpdateType.SET).setSource(e.getKey()).setValue((MysqlxExpr.Expr)e.getValue()).build()).forEach(builder::addOperation);
        return builder;
    }
    
    public XMessage buildRowUpdate(final FilterParams filterParams, final UpdateParams updateParams) {
        final MysqlxCrud.Update.Builder builder = this.commonRowUpdateBuilder(filterParams, updateParams);
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareRowUpdate(final int preparedStatementId, final FilterParams filterParams, final UpdateParams updateParams) {
        final MysqlxCrud.Update.Builder updateBuilder = this.commonRowUpdateBuilder(filterParams, updateParams);
        applyFilterParams(filterParams, updateBuilder::addAllOrder, updateBuilder::setLimitExpr, updateBuilder::setCriteria);
        final MysqlxPrepare.Prepare.Builder builder = MysqlxPrepare.Prepare.newBuilder().setStmtId(preparedStatementId);
        builder.setStmt(MysqlxPrepare.Prepare.OneOfMessage.newBuilder().setType(MysqlxPrepare.Prepare.OneOfMessage.Type.UPDATE).setUpdate(updateBuilder.build()).build());
        return new XMessage(builder.build());
    }
    
    private MysqlxCrud.Find.Builder commonFindBuilder(final FilterParams filterParams) {
        final MysqlxCrud.Find.Builder builder = MysqlxCrud.Find.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        builder.setDataModel(filterParams.isRelational() ? MysqlxCrud.DataModel.TABLE : MysqlxCrud.DataModel.DOCUMENT);
        if (filterParams.getFields() != null) {
            builder.addAllProjection((Iterable<? extends MysqlxCrud.Projection>)filterParams.getFields());
        }
        if (filterParams.getGrouping() != null) {
            builder.addAllGrouping((Iterable<? extends MysqlxExpr.Expr>)filterParams.getGrouping());
        }
        if (filterParams.getGroupingCriteria() != null) {
            builder.setGroupingCriteria((MysqlxExpr.Expr)filterParams.getGroupingCriteria());
        }
        if (filterParams.getLock() != null) {
            builder.setLocking(MysqlxCrud.Find.RowLock.forNumber(filterParams.getLock().asNumber()));
        }
        if (filterParams.getLockOption() != null) {
            builder.setLockingOptions(MysqlxCrud.Find.RowLockOptions.forNumber(filterParams.getLockOption().asNumber()));
        }
        return builder;
    }
    
    public XMessage buildFind(final FilterParams filterParams) {
        final MysqlxCrud.Find.Builder builder = this.commonFindBuilder(filterParams);
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareFind(final int preparedStatementId, final FilterParams filterParams) {
        final MysqlxCrud.Find.Builder findBuilder = this.commonFindBuilder(filterParams);
        applyFilterParams(filterParams, findBuilder::addAllOrder, findBuilder::setLimitExpr, findBuilder::setCriteria);
        final MysqlxPrepare.Prepare.Builder builder = MysqlxPrepare.Prepare.newBuilder().setStmtId(preparedStatementId);
        builder.setStmt(MysqlxPrepare.Prepare.OneOfMessage.newBuilder().setType(MysqlxPrepare.Prepare.OneOfMessage.Type.FIND).setFind(findBuilder.build()).build());
        return new XMessage(builder.build());
    }
    
    private MysqlxCrud.Delete.Builder commonDeleteBuilder(final FilterParams filterParams) {
        final MysqlxCrud.Delete.Builder builder = MysqlxCrud.Delete.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        return builder;
    }
    
    public XMessage buildDelete(final FilterParams filterParams) {
        final MysqlxCrud.Delete.Builder builder = this.commonDeleteBuilder(filterParams);
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareDelete(final int preparedStatementId, final FilterParams filterParams) {
        final MysqlxCrud.Delete.Builder deleteBuilder = this.commonDeleteBuilder(filterParams);
        applyFilterParams(filterParams, deleteBuilder::addAllOrder, deleteBuilder::setLimitExpr, deleteBuilder::setCriteria);
        final MysqlxPrepare.Prepare.Builder builder = MysqlxPrepare.Prepare.newBuilder().setStmtId(preparedStatementId);
        builder.setStmt(MysqlxPrepare.Prepare.OneOfMessage.newBuilder().setType(MysqlxPrepare.Prepare.OneOfMessage.Type.DELETE).setDelete(deleteBuilder.build()).build());
        return new XMessage(builder.build());
    }
    
    private MysqlxSql.StmtExecute.Builder commonSqlStatementBuilder(final String statement) {
        final MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
        builder.setStmt(ByteString.copyFromUtf8(statement));
        return builder;
    }
    
    @Override
    public XMessage buildSqlStatement(final String statement) {
        return this.buildSqlStatement(statement, (List<Object>)null);
    }
    
    @Override
    public XMessage buildSqlStatement(final String statement, final List<Object> args) {
        final MysqlxSql.StmtExecute.Builder builder = this.commonSqlStatementBuilder(statement);
        if (args != null) {
            builder.addAllArgs((Iterable<? extends MysqlxDatatypes.Any>)args.stream().map((Function<? super Object, ?>)ExprUtil::argObjectToScalarAny).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareSqlStatement(final int preparedStatementId, final String statement) {
        final MysqlxSql.StmtExecute.Builder stmtExecBuilder = this.commonSqlStatementBuilder(statement);
        final MysqlxPrepare.Prepare.Builder builder = MysqlxPrepare.Prepare.newBuilder().setStmtId(preparedStatementId);
        builder.setStmt(MysqlxPrepare.Prepare.OneOfMessage.newBuilder().setType(MysqlxPrepare.Prepare.OneOfMessage.Type.STMT).setStmtExecute(stmtExecBuilder.build()).build());
        return new XMessage(builder.build());
    }
    
    private static void applyFilterParams(final FilterParams filterParams, final Consumer<List<MysqlxCrud.Order>> setOrder, final Consumer<MysqlxCrud.Limit> setLimit, final Consumer<MysqlxExpr.Expr> setCriteria, final Consumer<List<MysqlxDatatypes.Scalar>> setArgs) {
        filterParams.verifyAllArgsBound();
        if (filterParams.getOrder() != null) {
            setOrder.accept((List<MysqlxCrud.Order>)filterParams.getOrder());
        }
        if (filterParams.getLimit() != null) {
            final MysqlxCrud.Limit.Builder lb = MysqlxCrud.Limit.newBuilder().setRowCount(filterParams.getLimit());
            if (filterParams.getOffset() != null) {
                lb.setOffset(filterParams.getOffset());
            }
            setLimit.accept(lb.build());
        }
        if (filterParams.getCriteria() != null) {
            setCriteria.accept((MysqlxExpr.Expr)filterParams.getCriteria());
        }
        if (filterParams.getArgs() != null) {
            setArgs.accept((List<MysqlxDatatypes.Scalar>)filterParams.getArgs());
        }
    }
    
    private static void applyFilterParams(final FilterParams filterParams, final Consumer<List<MysqlxCrud.Order>> setOrder, final Consumer<MysqlxCrud.LimitExpr> setLimit, final Consumer<MysqlxExpr.Expr> setCriteria) {
        if (filterParams.getOrder() != null) {
            setOrder.accept((List<MysqlxCrud.Order>)filterParams.getOrder());
        }
        final Object argsList = filterParams.getArgs();
        final int numberOfArgs = (argsList == null) ? 0 : ((List)argsList).size();
        if (filterParams.getLimit() != null) {
            final MysqlxCrud.LimitExpr.Builder lb = MysqlxCrud.LimitExpr.newBuilder().setRowCount(ExprUtil.buildPlaceholderExpr(numberOfArgs));
            if (filterParams.supportsOffset()) {
                lb.setOffset(ExprUtil.buildPlaceholderExpr(numberOfArgs + 1));
            }
            setLimit.accept(lb.build());
        }
        if (filterParams.getCriteria() != null) {
            setCriteria.accept((MysqlxExpr.Expr)filterParams.getCriteria());
        }
    }
    
    public XMessage buildPrepareExecute(final int preparedStatementId, final FilterParams filterParams) {
        final MysqlxPrepare.Execute.Builder builder = MysqlxPrepare.Execute.newBuilder().setStmtId(preparedStatementId);
        if (filterParams.getArgs() != null) {
            builder.addAllArgs((Iterable<? extends MysqlxDatatypes.Any>)((List)filterParams.getArgs()).stream().map(s -> MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(s).build()).collect(Collectors.toList()));
        }
        if (filterParams.getLimit() != null) {
            builder.addArgs(ExprUtil.anyOf(ExprUtil.scalarOf(filterParams.getLimit())));
            if (filterParams.supportsOffset()) {
                builder.addArgs(ExprUtil.anyOf(ExprUtil.scalarOf((filterParams.getOffset() != null) ? ((long)filterParams.getOffset()) : 0L)));
            }
        }
        return new XMessage(builder.build());
    }
    
    public XMessage buildPrepareDeallocate(final int preparedStatementId) {
        final MysqlxPrepare.Deallocate.Builder builder = MysqlxPrepare.Deallocate.newBuilder().setStmtId(preparedStatementId);
        return new XMessage(builder.build());
    }
    
    public XMessage buildCreateCollection(final String schemaName, final String collectionName, final Schema.CreateCollectionOptions options) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        final MysqlxDatatypes.Object.Builder argsBuilder = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)));
        final MysqlxDatatypes.Object.Builder optBuilder = MysqlxDatatypes.Object.newBuilder();
        boolean hasOptions = false;
        if (options.getReuseExisting() != null) {
            hasOptions = true;
            optBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("reuse_existing").setValue(ExprUtil.buildAny(options.getReuseExisting())));
        }
        if (options.getValidation() != null) {
            hasOptions = true;
            final MysqlxDatatypes.Object.Builder validationBuilder = MysqlxDatatypes.Object.newBuilder();
            if (options.getValidation().getSchema() != null) {
                validationBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(options.getValidation().getSchema())));
            }
            if (options.getValidation().getLevel() != null) {
                validationBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("level").setValue(ExprUtil.buildAny(options.getValidation().getLevel().name().toLowerCase())));
            }
            optBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("validation").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(validationBuilder)));
        }
        if (hasOptions) {
            argsBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("options").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(optBuilder)));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(argsBuilder).build()));
    }
    
    public XMessage buildModifyCollectionOptions(final String schemaName, final String collectionName, final Schema.ModifyCollectionOptions options) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        final MysqlxDatatypes.Object.Builder argsBuilder = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)));
        final MysqlxDatatypes.Object.Builder optBuilder = MysqlxDatatypes.Object.newBuilder();
        if (options != null && options.getValidation() != null) {
            final MysqlxDatatypes.Object.Builder validationBuilder = MysqlxDatatypes.Object.newBuilder();
            if (options.getValidation().getSchema() != null) {
                validationBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(options.getValidation().getSchema())));
            }
            if (options.getValidation().getLevel() != null) {
                validationBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("level").setValue(ExprUtil.buildAny(options.getValidation().getLevel().name().toLowerCase())));
            }
            optBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("validation").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(validationBuilder)));
        }
        argsBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("options").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(optBuilder)));
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_MODIFY_COLLECTION_OPTIONS, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(argsBuilder).build()));
    }
    
    public XMessage buildCreateCollection(final String schemaName, final String collectionName) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    public XMessage buildDropCollection(final String schemaName, final String collectionName) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    @Override
    public XMessage buildClose() {
        return new XMessage(MysqlxSession.Close.getDefaultInstance());
    }
    
    public XMessage buildListObjects(final String schemaName, final String pattern) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        final MysqlxDatatypes.Object.Builder obj = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)));
        if (pattern != null) {
            obj.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("pattern").setValue(ExprUtil.buildAny(pattern)));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_LIST_OBJECTS, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(obj).build()));
    }
    
    public XMessage buildEnableNotices(final String... notices) {
        final MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
        for (final String notice : notices) {
            abuilder.addValue(ExprUtil.buildAny(notice));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_ENABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
    }
    
    public XMessage buildDisableNotices(final String... notices) {
        final MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
        for (final String notice : notices) {
            abuilder.addValue(ExprUtil.buildAny(notice));
        }
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DISABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
    }
    
    public XMessage buildListNotices() {
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_LIST_NOTICES, new MysqlxDatatypes.Any[0]));
    }
    
    public XMessage buildCreateCollectionIndex(final String schemaName, final String collectionName, final CreateIndexParams params) {
        final MysqlxDatatypes.Object.Builder builder = MysqlxDatatypes.Object.newBuilder();
        builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(params.getIndexName()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("unique").setValue(ExprUtil.buildAny(false)));
        if (params.getIndexType() != null) {
            builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("type").setValue(ExprUtil.buildAny(params.getIndexType())));
        }
        final MysqlxDatatypes.Array.Builder aBuilder = MysqlxDatatypes.Array.newBuilder();
        for (final CreateIndexParams.IndexField indexField : params.getFields()) {
            final MysqlxDatatypes.Object.Builder fBuilder = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("member").setValue(ExprUtil.buildAny(indexField.getField()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("type").setValue(ExprUtil.buildAny(indexField.getType())));
            if (indexField.isRequired() != null) {
                fBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("required").setValue(ExprUtil.buildAny(indexField.isRequired())));
            }
            if (indexField.getOptions() != null) {
                fBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("options").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_UINT).setVUnsignedInt(indexField.getOptions())).build()));
            }
            if (indexField.getSrid() != null) {
                fBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("srid").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_UINT).setVUnsignedInt(indexField.getSrid())).build()));
            }
            if (indexField.isArray() != null) {
                fBuilder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("array").setValue(ExprUtil.buildAny(indexField.isArray())));
            }
            aBuilder.addValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(fBuilder));
        }
        builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("constraint").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(aBuilder)));
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(builder).build()));
    }
    
    public XMessage buildDropCollectionIndex(final String schemaName, final String collectionName, final String indexName) {
        return new XMessage(this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(indexName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    private MysqlxSql.StmtExecute buildXpluginCommand(final XpluginStatementCommand command, final MysqlxDatatypes.Any... args) {
        final MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
        builder.setNamespace("mysqlx");
        builder.setStmt(ByteString.copyFromUtf8(command.commandName));
        Arrays.stream(args).forEach(a -> builder.addArgs(a));
        return builder.build();
    }
    
    public XMessage buildSha256MemoryAuthStart() {
        return new XMessage(MysqlxSession.AuthenticateStart.newBuilder().setMechName("SHA256_MEMORY").build());
    }
    
    public XMessage buildSha256MemoryAuthContinue(final String user, final String password, final byte[] nonce, final String database) {
        final String encoding = "UTF8";
        final byte[] databaseBytes = (database == null) ? new byte[0] : StringUtils.getBytes(database, encoding);
        final byte[] userBytes = (user == null) ? new byte[0] : StringUtils.getBytes(user, encoding);
        byte[] hashedPassword;
        final byte[] passwordBytes = hashedPassword = ((password == null || password.length() == 0) ? new byte[0] : StringUtils.getBytes(password, encoding));
        try {
            hashedPassword = Security.scrambleCachingSha2(passwordBytes, nonce);
        }
        catch (DigestException e) {
            throw new RuntimeException(e);
        }
        hashedPassword = StringUtils.toHexString(hashedPassword, hashedPassword.length).getBytes();
        final byte[] reply = new byte[databaseBytes.length + userBytes.length + hashedPassword.length + 2];
        System.arraycopy(databaseBytes, 0, reply, 0, databaseBytes.length);
        int pos = databaseBytes.length;
        reply[pos++] = 0;
        System.arraycopy(userBytes, 0, reply, pos, userBytes.length);
        pos += userBytes.length;
        reply[pos++] = 0;
        System.arraycopy(hashedPassword, 0, reply, pos, hashedPassword.length);
        final MysqlxSession.AuthenticateContinue.Builder builder = MysqlxSession.AuthenticateContinue.newBuilder();
        builder.setAuthData(ByteString.copyFrom(reply));
        return new XMessage(builder.build());
    }
    
    public XMessage buildMysql41AuthStart() {
        return new XMessage(MysqlxSession.AuthenticateStart.newBuilder().setMechName("MYSQL41").build());
    }
    
    public XMessage buildMysql41AuthContinue(final String user, final String password, final byte[] salt, final String database) {
        final String encoding = "UTF8";
        final byte[] userBytes = (user == null) ? new byte[0] : StringUtils.getBytes(user, encoding);
        final byte[] passwordBytes = (password == null || password.length() == 0) ? new byte[0] : StringUtils.getBytes(password, encoding);
        final byte[] databaseBytes = (database == null) ? new byte[0] : StringUtils.getBytes(database, encoding);
        byte[] hashedPassword = passwordBytes;
        if (password != null && password.length() > 0) {
            hashedPassword = Security.scramble411(passwordBytes, salt);
            hashedPassword = String.format("*%040x", new BigInteger(1, hashedPassword)).getBytes();
        }
        final byte[] reply = new byte[databaseBytes.length + userBytes.length + hashedPassword.length + 2];
        System.arraycopy(databaseBytes, 0, reply, 0, databaseBytes.length);
        int pos = databaseBytes.length;
        reply[pos++] = 0;
        System.arraycopy(userBytes, 0, reply, pos, userBytes.length);
        pos += userBytes.length;
        reply[pos++] = 0;
        System.arraycopy(hashedPassword, 0, reply, pos, hashedPassword.length);
        final MysqlxSession.AuthenticateContinue.Builder builder = MysqlxSession.AuthenticateContinue.newBuilder();
        builder.setAuthData(ByteString.copyFrom(reply));
        return new XMessage(builder.build());
    }
    
    public XMessage buildPlainAuthStart(final String user, final String password, final String database) {
        final CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(final Callback[] callbacks) throws UnsupportedCallbackException {
                for (final Callback c : callbacks) {
                    if (NameCallback.class.isAssignableFrom(c.getClass())) {
                        ((NameCallback)c).setName(user);
                    }
                    else {
                        if (!PasswordCallback.class.isAssignableFrom(c.getClass())) {
                            throw new UnsupportedCallbackException(c);
                        }
                        ((PasswordCallback)c).setPassword(password.toCharArray());
                    }
                }
            }
        };
        try {
            final String[] mechanisms = { "PLAIN" };
            final String authorizationId = (database == null || database.trim().length() == 0) ? null : database;
            final String protocol = "X Protocol";
            final Map<String, ?> props = null;
            final String serverName = "<unknown>";
            final SaslClient saslClient = Sasl.createSaslClient(mechanisms, authorizationId, protocol, serverName, props, callbackHandler);
            final MysqlxSession.AuthenticateStart.Builder authStartBuilder = MysqlxSession.AuthenticateStart.newBuilder();
            authStartBuilder.setMechName("PLAIN");
            authStartBuilder.setAuthData(ByteString.copyFrom(saslClient.evaluateChallenge(null)));
            return new XMessage(authStartBuilder.build());
        }
        catch (SaslException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public XMessage buildExternalAuthStart(final String database) {
        final CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(final Callback[] callbacks) throws UnsupportedCallbackException {
                final int length = callbacks.length;
                final int n = 0;
                if (n >= length) {
                    return;
                }
                final Callback c = callbacks[n];
                if (NameCallback.class.isAssignableFrom(c.getClass())) {
                    throw new UnsupportedCallbackException(c);
                }
                if (PasswordCallback.class.isAssignableFrom(c.getClass())) {
                    throw new UnsupportedCallbackException(c);
                }
                throw new UnsupportedCallbackException(c);
            }
        };
        try {
            final String[] mechanisms = { "EXTERNAL" };
            final String authorizationId = (database == null || database.trim().length() == 0) ? null : database;
            final String protocol = "X Protocol";
            final Map<String, ?> props = null;
            final String serverName = "<unknown>";
            final SaslClient saslClient = Sasl.createSaslClient(mechanisms, authorizationId, protocol, serverName, props, callbackHandler);
            final MysqlxSession.AuthenticateStart.Builder authStartBuilder = MysqlxSession.AuthenticateStart.newBuilder();
            authStartBuilder.setMechName("EXTERNAL");
            authStartBuilder.setAuthData(ByteString.copyFrom(saslClient.evaluateChallenge(null)));
            return new XMessage(authStartBuilder.build());
        }
        catch (SaslException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public XMessage buildSessionResetAndClose() {
        return new XMessage(MysqlxSession.Reset.newBuilder().build());
    }
    
    public XMessage buildSessionResetKeepOpen() {
        return new XMessage(MysqlxSession.Reset.newBuilder().setKeepOpen(true).build());
    }
    
    public XMessage buildExpectOpen() {
        return new XMessage(MysqlxExpect.Open.newBuilder().addCond(MysqlxExpect.Open.Condition.newBuilder().setConditionKey(2).setConditionValue(ByteString.copyFromUtf8("6.1"))).build());
    }
}
