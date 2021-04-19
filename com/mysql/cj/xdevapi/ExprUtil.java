package com.mysql.cj.xdevapi;

import java.text.*;
import com.google.protobuf.*;
import com.mysql.cj.x.protobuf.*;
import java.math.*;
import java.sql.*;
import java.util.stream.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import java.util.*;

public class ExprUtil
{
    private static SimpleDateFormat javaSqlDateFormat;
    private static SimpleDateFormat javaSqlTimestampFormat;
    private static SimpleDateFormat javaSqlTimeFormat;
    private static SimpleDateFormat javaUtilDateFormat;
    
    public static MysqlxExpr.Expr buildLiteralNullScalar() {
        return buildLiteralExpr(nullScalar());
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final double d) {
        return buildLiteralExpr(scalarOf(d));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final long l) {
        return buildLiteralExpr(scalarOf(l));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final String str) {
        return buildLiteralExpr(scalarOf(str));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final byte[] bytes) {
        return buildLiteralExpr(scalarOf(bytes));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final boolean b) {
        return buildLiteralExpr(scalarOf(b));
    }
    
    public static MysqlxExpr.Expr buildLiteralExpr(final MysqlxDatatypes.Scalar scalar) {
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.LITERAL).setLiteral(scalar).build();
    }
    
    public static MysqlxExpr.Expr buildPlaceholderExpr(final int pos) {
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.PLACEHOLDER).setPosition(pos).build();
    }
    
    public static MysqlxDatatypes.Scalar nullScalar() {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_NULL).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final double d) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_DOUBLE).setVDouble(d).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final long l) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_SINT).setVSignedInt(l).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final String str) {
        final MysqlxDatatypes.Scalar.String sstr = MysqlxDatatypes.Scalar.String.newBuilder().setValue(ByteString.copyFromUtf8(str)).build();
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_STRING).setVString(sstr).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final byte[] bytes) {
        final MysqlxDatatypes.Scalar.Octets.Builder o = MysqlxDatatypes.Scalar.Octets.newBuilder().setValue(ByteString.copyFrom(bytes));
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_OCTETS).setVOctets(o).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final boolean b) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_BOOL).setVBool(b).build();
    }
    
    public static MysqlxDatatypes.Any anyOf(final MysqlxDatatypes.Scalar s) {
        return MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(s).build();
    }
    
    public static MysqlxDatatypes.Any buildAny(final String str) {
        final MysqlxDatatypes.Scalar.String sstr = MysqlxDatatypes.Scalar.String.newBuilder().setValue(ByteString.copyFromUtf8(str)).build();
        final MysqlxDatatypes.Scalar s = MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_STRING).setVString(sstr).build();
        return anyOf(s);
    }
    
    public static MysqlxDatatypes.Any buildAny(final boolean b) {
        return MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(scalarOf(b)).build();
    }
    
    public static MysqlxCrud.Collection buildCollection(final String schemaName, final String collectionName) {
        return MysqlxCrud.Collection.newBuilder().setSchema(schemaName).setName(collectionName).build();
    }
    
    public static MysqlxDatatypes.Scalar argObjectToScalar(final Object value) {
        final MysqlxExpr.Expr e = argObjectToExpr(value, false);
        if (!e.hasLiteral()) {
            throw new WrongArgumentException("No literal interpretation of argument: " + value);
        }
        return e.getLiteral();
    }
    
    public static MysqlxDatatypes.Any argObjectToScalarAny(final Object value) {
        final MysqlxDatatypes.Scalar s = argObjectToScalar(value);
        return MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(s).build();
    }
    
    public static MysqlxExpr.Expr argObjectToExpr(final Object value, final boolean allowRelationalColumns) {
        if (value == null) {
            return buildLiteralNullScalar();
        }
        final Class<?> cls = value.getClass();
        if (cls == Boolean.class) {
            return buildLiteralScalar((boolean)value);
        }
        if (cls == Byte.class || cls == Short.class || cls == Integer.class || cls == Long.class || cls == BigInteger.class) {
            return buildLiteralScalar(((Number)value).longValue());
        }
        if (cls == Float.class || cls == Double.class || cls == BigDecimal.class) {
            return buildLiteralScalar(((Number)value).doubleValue());
        }
        if (cls == String.class) {
            return buildLiteralScalar((String)value);
        }
        if (cls == Character.class) {
            return buildLiteralScalar(((Character)value).toString());
        }
        if (cls == Expression.class) {
            return new ExprParser(((Expression)value).getExpressionString(), allowRelationalColumns).parse();
        }
        if (cls == Date.class) {
            return buildLiteralScalar(ExprUtil.javaSqlDateFormat.format((java.util.Date)value));
        }
        if (cls == Time.class) {
            return buildLiteralScalar(ExprUtil.javaSqlTimeFormat.format((java.util.Date)value));
        }
        if (cls == Timestamp.class) {
            return buildLiteralScalar(ExprUtil.javaSqlTimestampFormat.format((java.util.Date)value));
        }
        if (cls == java.util.Date.class) {
            return buildLiteralScalar(ExprUtil.javaUtilDateFormat.format((java.util.Date)value));
        }
        if (DbDoc.class.isAssignableFrom(cls)) {
            return new ExprParser(((DbDoc)value).toString()).parse();
        }
        if (cls == JsonArray.class) {
            return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).setArray(MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).getArrayBuilder().addAllValue((Iterable<? extends MysqlxExpr.Expr>)((JsonArray)value).stream().map(f -> argObjectToExpr(f, true)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()))).build();
        }
        if (cls == JsonString.class) {
            return buildLiteralScalar(((JsonString)value).getString());
        }
        if (cls == JsonNumber.class) {
            return buildLiteralScalar(((JsonNumber)value).getInteger());
        }
        throw new FeatureNotAvailableException("Can not create an expression from " + cls);
    }
    
    static {
        ExprUtil.javaSqlDateFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd", null);
        ExprUtil.javaSqlTimestampFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd'T'HH:mm:ss.S", null);
        ExprUtil.javaSqlTimeFormat = TimeUtil.getSimpleDateFormat(null, "HH:mm:ss.S", null);
        ExprUtil.javaUtilDateFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd'T'HH:mm:ss.S", null);
    }
}
