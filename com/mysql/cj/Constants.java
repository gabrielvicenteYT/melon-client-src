package com.mysql.cj;

import java.math.*;

public class Constants
{
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final String MILLIS_I18N;
    public static final byte[] SLASH_STAR_SPACE_AS_BYTES;
    public static final byte[] SPACE_STAR_SLASH_SPACE_AS_BYTES;
    public static final String JVM_VENDOR;
    public static final String JVM_VERSION;
    public static final String OS_NAME;
    public static final String OS_ARCH;
    public static final String OS_VERSION;
    public static final String PLATFORM_ENCODING;
    public static final String CJ_NAME = "MySQL Connector/J";
    public static final String CJ_FULL_NAME = "mysql-connector-java-8.0.23";
    public static final String CJ_REVISION = "18bbd5e68195d0da083cbd5bd0d05d76320df7cd";
    public static final String CJ_VERSION = "8.0.23";
    public static final String CJ_MAJOR_VERSION = "8";
    public static final String CJ_MINOR_VERSION = "0";
    public static final String CJ_LICENSE = "GPL";
    public static final BigInteger BIG_INTEGER_ZERO;
    public static final BigInteger BIG_INTEGER_ONE;
    public static final BigInteger BIG_INTEGER_NEGATIVE_ONE;
    public static final BigInteger BIG_INTEGER_MIN_BYTE_VALUE;
    public static final BigInteger BIG_INTEGER_MAX_BYTE_VALUE;
    public static final BigInteger BIG_INTEGER_MIN_SHORT_VALUE;
    public static final BigInteger BIG_INTEGER_MAX_SHORT_VALUE;
    public static final BigInteger BIG_INTEGER_MIN_INTEGER_VALUE;
    public static final BigInteger BIG_INTEGER_MAX_INTEGER_VALUE;
    public static final BigInteger BIG_INTEGER_MIN_LONG_VALUE;
    public static final BigInteger BIG_INTEGER_MAX_LONG_VALUE;
    public static final BigDecimal BIG_DECIMAL_ZERO;
    public static final BigDecimal BIG_DECIMAL_ONE;
    public static final BigDecimal BIG_DECIMAL_NEGATIVE_ONE;
    public static final BigDecimal BIG_DECIMAL_MIN_BYTE_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_BYTE_VALUE;
    public static final BigDecimal BIG_DECIMAL_MIN_SHORT_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_SHORT_VALUE;
    public static final BigDecimal BIG_DECIMAL_MIN_INTEGER_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_INTEGER_VALUE;
    public static final BigDecimal BIG_DECIMAL_MIN_LONG_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_LONG_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_DOUBLE_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_NEGATIVE_DOUBLE_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_FLOAT_VALUE;
    public static final BigDecimal BIG_DECIMAL_MAX_NEGATIVE_FLOAT_VALUE;
    
    private Constants() {
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        MILLIS_I18N = Messages.getString("Milliseconds");
        SLASH_STAR_SPACE_AS_BYTES = new byte[] { 47, 42, 32 };
        SPACE_STAR_SLASH_SPACE_AS_BYTES = new byte[] { 32, 42, 47, 32 };
        JVM_VENDOR = System.getProperty("java.vendor");
        JVM_VERSION = System.getProperty("java.version");
        OS_NAME = System.getProperty("os.name");
        OS_ARCH = System.getProperty("os.arch");
        OS_VERSION = System.getProperty("os.version");
        PLATFORM_ENCODING = System.getProperty("file.encoding");
        BIG_INTEGER_ZERO = BigInteger.valueOf(0L);
        BIG_INTEGER_ONE = BigInteger.valueOf(1L);
        BIG_INTEGER_NEGATIVE_ONE = BigInteger.valueOf(-1L);
        BIG_INTEGER_MIN_BYTE_VALUE = BigInteger.valueOf(-128L);
        BIG_INTEGER_MAX_BYTE_VALUE = BigInteger.valueOf(127L);
        BIG_INTEGER_MIN_SHORT_VALUE = BigInteger.valueOf(-32768L);
        BIG_INTEGER_MAX_SHORT_VALUE = BigInteger.valueOf(32767L);
        BIG_INTEGER_MIN_INTEGER_VALUE = BigInteger.valueOf(-2147483648L);
        BIG_INTEGER_MAX_INTEGER_VALUE = BigInteger.valueOf(2147483647L);
        BIG_INTEGER_MIN_LONG_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
        BIG_INTEGER_MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
        BIG_DECIMAL_ZERO = BigDecimal.valueOf(0L);
        BIG_DECIMAL_ONE = BigDecimal.valueOf(1L);
        BIG_DECIMAL_NEGATIVE_ONE = BigDecimal.valueOf(-1L);
        BIG_DECIMAL_MIN_BYTE_VALUE = BigDecimal.valueOf(-128L);
        BIG_DECIMAL_MAX_BYTE_VALUE = BigDecimal.valueOf(127L);
        BIG_DECIMAL_MIN_SHORT_VALUE = BigDecimal.valueOf(-32768L);
        BIG_DECIMAL_MAX_SHORT_VALUE = BigDecimal.valueOf(32767L);
        BIG_DECIMAL_MIN_INTEGER_VALUE = BigDecimal.valueOf(-2147483648L);
        BIG_DECIMAL_MAX_INTEGER_VALUE = BigDecimal.valueOf(2147483647L);
        BIG_DECIMAL_MIN_LONG_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);
        BIG_DECIMAL_MAX_LONG_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
        BIG_DECIMAL_MAX_DOUBLE_VALUE = BigDecimal.valueOf(Double.MAX_VALUE);
        BIG_DECIMAL_MAX_NEGATIVE_DOUBLE_VALUE = BigDecimal.valueOf(-1.7976931348623157E308);
        BIG_DECIMAL_MAX_FLOAT_VALUE = BigDecimal.valueOf(3.4028234663852886E38);
        BIG_DECIMAL_MAX_NEGATIVE_FLOAT_VALUE = BigDecimal.valueOf(-3.4028234663852886E38);
    }
}
