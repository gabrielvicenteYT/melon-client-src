package com.mysql.cj.protocol.a;

import com.mysql.cj.util.*;
import com.mysql.cj.result.*;
import com.mysql.cj.*;
import java.math.*;
import java.util.regex.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.*;

public class MysqlTextValueDecoder implements ValueDecoder
{
    public static final int DATE_BUF_LEN = 10;
    public static final int TIME_STR_LEN_MIN = 8;
    public static final int TIME_STR_LEN_MAX_NO_FRAC = 10;
    public static final int TIME_STR_LEN_MAX_WITH_MICROS = 17;
    public static final int TIMESTAMP_STR_LEN_NO_FRAC = 19;
    public static final int TIMESTAMP_STR_LEN_WITH_MICROS = 26;
    public static final int TIMESTAMP_STR_LEN_WITH_NANOS = 29;
    public static final Pattern TIME_PTRN;
    public static final int MAX_SIGNED_LONG_LEN = 20;
    
    @Override
    public <T> T decodeDate(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromDate(getDate(bytes, offset, length));
    }
    
    @Override
    public <T> T decodeTime(final byte[] bytes, final int offset, final int length, final int scale, final ValueFactory<T> vf) {
        return vf.createFromTime(getTime(bytes, offset, length, scale));
    }
    
    @Override
    public <T> T decodeTimestamp(final byte[] bytes, final int offset, final int length, final int scale, final ValueFactory<T> vf) {
        return vf.createFromTimestamp(getTimestamp(bytes, offset, length, scale));
    }
    
    @Override
    public <T> T decodeDatetime(final byte[] bytes, final int offset, final int length, final int scale, final ValueFactory<T> vf) {
        return vf.createFromDatetime(getTimestamp(bytes, offset, length, scale));
    }
    
    @Override
    public <T> T decodeUInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getLong(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length <= 19 && bytes[offset] >= 48 && bytes[offset] <= 56) {
            return (T)this.decodeInt8(bytes, offset, length, (ValueFactory<Object>)vf);
        }
        return vf.createFromBigInteger(getBigInteger(bytes, offset, length));
    }
    
    @Override
    public <T> T decodeInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(getLong(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeFloat(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return (T)this.decodeDouble(bytes, offset, length, (ValueFactory<Object>)vf);
    }
    
    @Override
    public <T> T decodeDouble(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromDouble(getDouble(bytes, offset, length));
    }
    
    @Override
    public <T> T decodeDecimal(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        final BigDecimal d = new BigDecimal(StringUtils.toAsciiString(bytes, offset, length));
        return vf.createFromBigDecimal(d);
    }
    
    @Override
    public <T> T decodeByteArray(final byte[] bytes, final int offset, final int length, final Field f, final ValueFactory<T> vf) {
        return vf.createFromBytes(bytes, offset, length, f);
    }
    
    @Override
    public <T> T decodeBit(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromBit(bytes, offset, length);
    }
    
    @Override
    public <T> T decodeSet(final byte[] bytes, final int offset, final int length, final Field f, final ValueFactory<T> vf) {
        return (T)this.decodeByteArray(bytes, offset, length, f, (ValueFactory<Object>)vf);
    }
    
    @Override
    public <T> T decodeYear(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromYear(getLong(bytes, offset, offset + length));
    }
    
    public static int getInt(final byte[] buf, final int offset, final int endpos) throws NumberFormatException {
        final long l = getLong(buf, offset, endpos);
        if (l < -2147483648L || l > 2147483647L) {
            throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[] { StringUtils.toString(buf, offset, endpos - offset) }));
        }
        return (int)l;
    }
    
    public static long getLong(final byte[] buf, final int offset, final int endpos) throws NumberFormatException {
        final int base = 10;
        int s;
        for (s = offset; s < endpos && Character.isWhitespace((char)buf[s]); ++s) {}
        if (s == endpos) {
            throw new NumberFormatException(StringUtils.toString(buf));
        }
        boolean negative = false;
        if ((char)buf[s] == '-') {
            negative = true;
            ++s;
        }
        else if ((char)buf[s] == '+') {
            ++s;
        }
        final int save = s;
        final long cutoff = Long.MAX_VALUE / base;
        long cutlim = (int)(Long.MAX_VALUE % base);
        if (negative) {
            ++cutlim;
        }
        boolean overflow = false;
        long i = 0L;
        while (s < endpos) {
            char c = (char)buf[s];
            if (c >= '0' && c <= '9') {
                c -= '0';
            }
            else {
                if (!Character.isLetter(c)) {
                    break;
                }
                c = (char)(Character.toUpperCase(c) - 'A' + 10);
            }
            if (c >= base) {
                break;
            }
            if (i > cutoff || (i == cutoff && c > cutlim)) {
                overflow = true;
            }
            else {
                i *= base;
                i += c;
            }
            ++s;
        }
        if (s == save) {
            throw new NumberFormatException(Messages.getString("StringUtils.badIntFormat", new Object[] { StringUtils.toString(buf, offset, endpos - offset) }));
        }
        if (overflow) {
            throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[] { StringUtils.toString(buf, offset, endpos - offset) }));
        }
        return negative ? (-i) : i;
    }
    
    public static BigInteger getBigInteger(final byte[] buf, final int offset, final int length) throws NumberFormatException {
        final BigInteger i = new BigInteger(StringUtils.toAsciiString(buf, offset, length));
        return i;
    }
    
    public static Double getDouble(final byte[] bytes, final int offset, final int length) {
        return Double.parseDouble(StringUtils.toAsciiString(bytes, offset, length));
    }
    
    public static boolean isDate(final String s) {
        return s.length() == 10 && s.charAt(4) == '-' && s.charAt(7) == '-';
    }
    
    public static boolean isTime(final String s) {
        final Matcher matcher = MysqlTextValueDecoder.TIME_PTRN.matcher(s);
        return matcher.matches();
    }
    
    public static boolean isTimestamp(final String s) {
        final Pattern DATETIME_PTRN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d{1,9}){0,1}");
        final Matcher matcher = DATETIME_PTRN.matcher(s);
        return matcher.matches();
    }
    
    public static InternalDate getDate(final byte[] bytes, final int offset, final int length) {
        if (length != 10) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DATE" }));
        }
        final int year = getInt(bytes, offset, offset + 4);
        final int month = getInt(bytes, offset + 5, offset + 7);
        final int day = getInt(bytes, offset + 8, offset + 10);
        return new InternalDate(year, month, day);
    }
    
    public static InternalTime getTime(final byte[] bytes, final int offset, final int length, final int scale) {
        int pos = 0;
        if (length < 8 || length > 17) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIME" }));
        }
        boolean negative = false;
        if (bytes[offset] == 45) {
            ++pos;
            negative = true;
        }
        int segmentLen;
        for (segmentLen = 0; Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen == 0 || bytes[offset + pos + segmentLen] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { "TIME", StringUtils.toString(bytes, offset, length) }));
        }
        int hours = getInt(bytes, offset + pos, offset + pos + segmentLen);
        if (negative) {
            hours *= -1;
        }
        for (pos += segmentLen + 1, segmentLen = 0; Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen != 2 || bytes[offset + pos + segmentLen] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { "TIME", StringUtils.toString(bytes, offset, length) }));
        }
        final int minutes = getInt(bytes, offset + pos, offset + pos + segmentLen);
        for (pos += segmentLen + 1, segmentLen = 0; offset + pos + segmentLen < offset + length && Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen != 2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIME" }));
        }
        final int seconds = getInt(bytes, offset + pos, offset + pos + segmentLen);
        pos += segmentLen;
        int nanos = 0;
        if (length > pos) {
            ++pos;
            for (segmentLen = 0; offset + pos + segmentLen < offset + length && Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
            if (segmentLen + pos != length) {
                throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIME" }));
            }
            nanos = getInt(bytes, offset + pos, offset + pos + segmentLen);
            nanos *= (int)Math.pow(10.0, 9 - segmentLen);
        }
        return new InternalTime(hours, minutes, seconds, nanos, scale);
    }
    
    public static InternalTimestamp getTimestamp(final byte[] bytes, final int offset, final int length, final int scale) {
        if (length < 19 || (length > 26 && length != 29)) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIMESTAMP" }));
        }
        if (length != 19 && (bytes[offset + 19] != 46 || length < 21)) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIMESTAMP" }));
        }
        if (bytes[offset + 4] != 45 || bytes[offset + 7] != 45 || bytes[offset + 10] != 32 || bytes[offset + 13] != 58 || bytes[offset + 16] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIMESTAMP" }));
        }
        final int year = getInt(bytes, offset, offset + 4);
        final int month = getInt(bytes, offset + 5, offset + 7);
        final int day = getInt(bytes, offset + 8, offset + 10);
        final int hours = getInt(bytes, offset + 11, offset + 13);
        final int minutes = getInt(bytes, offset + 14, offset + 16);
        final int seconds = getInt(bytes, offset + 17, offset + 19);
        int nanos;
        if (length == 29) {
            nanos = getInt(bytes, offset + 20, offset + length);
        }
        else {
            nanos = ((length == 19) ? 0 : getInt(bytes, offset + 20, offset + length));
            nanos *= (int)Math.pow(10.0, 9 - (length - 19 - 1));
        }
        return new InternalTimestamp(year, month, day, hours, minutes, seconds, nanos, scale);
    }
    
    static {
        TIME_PTRN = Pattern.compile("[-]{0,1}\\d{2,3}:\\d{2}:\\d{2}(\\.\\d{1,9})?");
    }
}
