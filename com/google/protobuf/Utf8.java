package com.google.protobuf;

import java.nio.*;

final class Utf8
{
    private static final Processor processor;
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    static final int MAX_BYTES_PER_CHAR = 3;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    
    public static boolean isValidUtf8(final byte[] bytes) {
        return Utf8.processor.isValidUtf8(bytes, 0, bytes.length);
    }
    
    public static boolean isValidUtf8(final byte[] bytes, final int index, final int limit) {
        return Utf8.processor.isValidUtf8(bytes, index, limit);
    }
    
    public static int partialIsValidUtf8(final int state, final byte[] bytes, final int index, final int limit) {
        return Utf8.processor.partialIsValidUtf8(state, bytes, index, limit);
    }
    
    private static int incompleteStateFor(final int byte1) {
        return (byte1 > -12) ? -1 : byte1;
    }
    
    private static int incompleteStateFor(final int byte1, final int byte2) {
        return (byte1 > -12 || byte2 > -65) ? -1 : (byte1 ^ byte2 << 8);
    }
    
    private static int incompleteStateFor(final int byte1, final int byte2, final int byte3) {
        return (byte1 > -12 || byte2 > -65 || byte3 > -65) ? -1 : (byte1 ^ byte2 << 8 ^ byte3 << 16);
    }
    
    private static int incompleteStateFor(final byte[] bytes, final int index, final int limit) {
        final int byte1 = bytes[index - 1];
        switch (limit - index) {
            case 0: {
                return incompleteStateFor(byte1);
            }
            case 1: {
                return incompleteStateFor(byte1, bytes[index]);
            }
            case 2: {
                return incompleteStateFor(byte1, bytes[index], bytes[index + 1]);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private static int incompleteStateFor(final ByteBuffer buffer, final int byte1, final int index, final int remaining) {
        switch (remaining) {
            case 0: {
                return incompleteStateFor(byte1);
            }
            case 1: {
                return incompleteStateFor(byte1, buffer.get(index));
            }
            case 2: {
                return incompleteStateFor(byte1, buffer.get(index), buffer.get(index + 1));
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    static int encodedLength(final CharSequence sequence) {
        int utf8Length;
        int utf16Length;
        int i;
        for (utf16Length = (utf8Length = sequence.length()), i = 0; i < utf16Length && sequence.charAt(i) < '\u0080'; ++i) {}
        while (i < utf16Length) {
            final char c = sequence.charAt(i);
            if (c >= '\u0800') {
                utf8Length += encodedLengthGeneral(sequence, i);
                break;
            }
            utf8Length += '\u007f' - c >>> 31;
            ++i;
        }
        if (utf8Length < utf16Length) {
            throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (utf8Length + 4294967296L));
        }
        return utf8Length;
    }
    
    private static int encodedLengthGeneral(final CharSequence sequence, final int start) {
        final int utf16Length = sequence.length();
        int utf8Length = 0;
        for (int i = start; i < utf16Length; ++i) {
            final char c = sequence.charAt(i);
            if (c < '\u0800') {
                utf8Length += '\u007f' - c >>> 31;
            }
            else {
                utf8Length += 2;
                if ('\ud800' <= c && c <= '\udfff') {
                    final int cp = Character.codePointAt(sequence, i);
                    if (cp < 65536) {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    }
                    ++i;
                }
            }
        }
        return utf8Length;
    }
    
    static int encode(final CharSequence in, final byte[] out, final int offset, final int length) {
        return Utf8.processor.encodeUtf8(in, out, offset, length);
    }
    
    static boolean isValidUtf8(final ByteBuffer buffer) {
        return Utf8.processor.isValidUtf8(buffer, buffer.position(), buffer.remaining());
    }
    
    static int partialIsValidUtf8(final int state, final ByteBuffer buffer, final int index, final int limit) {
        return Utf8.processor.partialIsValidUtf8(state, buffer, index, limit);
    }
    
    static String decodeUtf8(final ByteBuffer buffer, final int index, final int size) throws InvalidProtocolBufferException {
        return Utf8.processor.decodeUtf8(buffer, index, size);
    }
    
    static String decodeUtf8(final byte[] bytes, final int index, final int size) throws InvalidProtocolBufferException {
        return Utf8.processor.decodeUtf8(bytes, index, size);
    }
    
    static void encodeUtf8(final CharSequence in, final ByteBuffer out) {
        Utf8.processor.encodeUtf8(in, out);
    }
    
    private static int estimateConsecutiveAscii(final ByteBuffer buffer, final int index, final int limit) {
        int i = index;
        for (int lim = limit - 7; i < lim && (buffer.getLong(i) & 0x8080808080808080L) == 0x0L; i += 8) {}
        return i - index;
    }
    
    private Utf8() {
    }
    
    static {
        processor = ((UnsafeProcessor.isAvailable() && !Android.isOnAndroidDevice()) ? new UnsafeProcessor() : new SafeProcessor());
    }
    
    static class UnpairedSurrogateException extends IllegalArgumentException
    {
        UnpairedSurrogateException(final int index, final int length) {
            super("Unpaired surrogate at index " + index + " of " + length);
        }
    }
    
    abstract static class Processor
    {
        final boolean isValidUtf8(final byte[] bytes, final int index, final int limit) {
            return this.partialIsValidUtf8(0, bytes, index, limit) == 0;
        }
        
        abstract int partialIsValidUtf8(final int p0, final byte[] p1, final int p2, final int p3);
        
        final boolean isValidUtf8(final ByteBuffer buffer, final int index, final int limit) {
            return this.partialIsValidUtf8(0, buffer, index, limit) == 0;
        }
        
        final int partialIsValidUtf8(final int state, final ByteBuffer buffer, final int index, final int limit) {
            if (buffer.hasArray()) {
                final int offset = buffer.arrayOffset();
                return this.partialIsValidUtf8(state, buffer.array(), offset + index, offset + limit);
            }
            if (buffer.isDirect()) {
                return this.partialIsValidUtf8Direct(state, buffer, index, limit);
            }
            return this.partialIsValidUtf8Default(state, buffer, index, limit);
        }
        
        abstract int partialIsValidUtf8Direct(final int p0, final ByteBuffer p1, final int p2, final int p3);
        
        final int partialIsValidUtf8Default(final int state, final ByteBuffer buffer, int index, final int limit) {
            if (state != 0) {
                if (index >= limit) {
                    return state;
                }
                final byte byte1 = (byte)state;
                if (byte1 < -32) {
                    if (byte1 < -62 || buffer.get(index++) > -65) {
                        return -1;
                    }
                }
                else if (byte1 < -16) {
                    byte byte2 = (byte)~(state >> 8);
                    if (byte2 == 0) {
                        byte2 = buffer.get(index++);
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || buffer.get(index++) > -65) {
                        return -1;
                    }
                }
                else {
                    byte byte2 = (byte)~(state >> 8);
                    byte byte3 = 0;
                    if (byte2 == 0) {
                        byte2 = buffer.get(index++);
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    else {
                        byte3 = (byte)(state >> 16);
                    }
                    if (byte3 == 0) {
                        byte3 = buffer.get(index++);
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2, byte3);
                        }
                    }
                    if (byte2 > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || byte3 > -65 || buffer.get(index++) > -65) {
                        return -1;
                    }
                }
            }
            return partialIsValidUtf8(buffer, index, limit);
        }
        
        private static int partialIsValidUtf8(final ByteBuffer buffer, int index, final int limit) {
            index += estimateConsecutiveAscii(buffer, index, limit);
            while (index < limit) {
                final int byte1;
                if ((byte1 = buffer.get(index++)) < 0) {
                    if (byte1 < -32) {
                        if (index >= limit) {
                            return byte1;
                        }
                        if (byte1 < -62 || buffer.get(index) > -65) {
                            return -1;
                        }
                        ++index;
                    }
                    else if (byte1 < -16) {
                        if (index >= limit - 1) {
                            return incompleteStateFor(buffer, byte1, index, limit - index);
                        }
                        final byte byte2 = buffer.get(index++);
                        if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || buffer.get(index) > -65) {
                            return -1;
                        }
                        ++index;
                    }
                    else {
                        if (index >= limit - 2) {
                            return incompleteStateFor(buffer, byte1, index, limit - index);
                        }
                        final int byte3 = buffer.get(index++);
                        if (byte3 > -65 || (byte1 << 28) + (byte3 + 112) >> 30 != 0 || buffer.get(index++) > -65 || buffer.get(index++) > -65) {
                            return -1;
                        }
                        continue;
                    }
                }
            }
            return 0;
        }
        
        abstract String decodeUtf8(final byte[] p0, final int p1, final int p2) throws InvalidProtocolBufferException;
        
        final String decodeUtf8(final ByteBuffer buffer, final int index, final int size) throws InvalidProtocolBufferException {
            if (buffer.hasArray()) {
                final int offset = buffer.arrayOffset();
                return this.decodeUtf8(buffer.array(), offset + index, size);
            }
            if (buffer.isDirect()) {
                return this.decodeUtf8Direct(buffer, index, size);
            }
            return this.decodeUtf8Default(buffer, index, size);
        }
        
        abstract String decodeUtf8Direct(final ByteBuffer p0, final int p1, final int p2) throws InvalidProtocolBufferException;
        
        final String decodeUtf8Default(final ByteBuffer buffer, final int index, final int size) throws InvalidProtocolBufferException {
            if ((index | size | buffer.limit() - index - size) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", buffer.limit(), index, size));
            }
            int offset = index;
            final int limit = offset + size;
            final char[] resultArr = new char[size];
            int resultPos = 0;
            while (offset < limit) {
                final byte b = buffer.get(offset);
                if (!isOneByte(b)) {
                    break;
                }
                ++offset;
                handleOneByte(b, resultArr, resultPos++);
            }
            while (offset < limit) {
                final byte byte1 = buffer.get(offset++);
                if (isOneByte(byte1)) {
                    handleOneByte(byte1, resultArr, resultPos++);
                    while (offset < limit) {
                        final byte b2 = buffer.get(offset);
                        if (!isOneByte(b2)) {
                            break;
                        }
                        ++offset;
                        handleOneByte(b2, resultArr, resultPos++);
                    }
                }
                else if (isTwoBytes(byte1)) {
                    if (offset >= limit) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleTwoBytes(byte1, buffer.get(offset++), resultArr, resultPos++);
                }
                else if (isThreeBytes(byte1)) {
                    if (offset >= limit - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleThreeBytes(byte1, buffer.get(offset++), buffer.get(offset++), resultArr, resultPos++);
                }
                else {
                    if (offset >= limit - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleFourBytes(byte1, buffer.get(offset++), buffer.get(offset++), buffer.get(offset++), resultArr, resultPos++);
                    ++resultPos;
                }
            }
            return new String(resultArr, 0, resultPos);
        }
        
        abstract int encodeUtf8(final CharSequence p0, final byte[] p1, final int p2, final int p3);
        
        final void encodeUtf8(final CharSequence in, final ByteBuffer out) {
            if (out.hasArray()) {
                final int offset = out.arrayOffset();
                final int endIndex = Utf8.encode(in, out.array(), offset + out.position(), out.remaining());
                out.position(endIndex - offset);
            }
            else if (out.isDirect()) {
                this.encodeUtf8Direct(in, out);
            }
            else {
                this.encodeUtf8Default(in, out);
            }
        }
        
        abstract void encodeUtf8Direct(final CharSequence p0, final ByteBuffer p1);
        
        final void encodeUtf8Default(final CharSequence in, final ByteBuffer out) {
            final int inLength = in.length();
            int outIx = out.position();
            int inIx = 0;
            try {
                char c;
                while (inIx < inLength && (c = in.charAt(inIx)) < '\u0080') {
                    out.put(outIx + inIx, (byte)c);
                    ++inIx;
                }
                if (inIx == inLength) {
                    out.position(outIx + inIx);
                    return;
                }
                for (outIx += inIx; inIx < inLength; ++inIx, ++outIx) {
                    c = in.charAt(inIx);
                    if (c < '\u0080') {
                        out.put(outIx, (byte)c);
                    }
                    else if (c < '\u0800') {
                        out.put(outIx++, (byte)(0xC0 | c >>> 6));
                        out.put(outIx, (byte)(0x80 | ('?' & c)));
                    }
                    else if (c < '\ud800' || '\udfff' < c) {
                        out.put(outIx++, (byte)(0xE0 | c >>> 12));
                        out.put(outIx++, (byte)(0x80 | (0x3F & c >>> 6)));
                        out.put(outIx, (byte)(0x80 | ('?' & c)));
                    }
                    else {
                        final char low;
                        if (inIx + 1 == inLength || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
                            throw new UnpairedSurrogateException(inIx, inLength);
                        }
                        final int codePoint = Character.toCodePoint(c, low);
                        out.put(outIx++, (byte)(0xF0 | codePoint >>> 18));
                        out.put(outIx++, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                        out.put(outIx++, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                        out.put(outIx, (byte)(0x80 | (0x3F & codePoint)));
                    }
                }
                out.position(outIx);
            }
            catch (IndexOutOfBoundsException e) {
                final int badWriteIndex = out.position() + Math.max(inIx, outIx - out.position() + 1);
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inIx) + " at index " + badWriteIndex);
            }
        }
    }
    
    static final class SafeProcessor extends Processor
    {
        @Override
        int partialIsValidUtf8(final int state, final byte[] bytes, int index, final int limit) {
            if (state != 0) {
                if (index >= limit) {
                    return state;
                }
                final int byte1 = (byte)state;
                if (byte1 < -32) {
                    if (byte1 < -62 || bytes[index++] > -65) {
                        return -1;
                    }
                }
                else if (byte1 < -16) {
                    int byte2 = (byte)~(state >> 8);
                    if (byte2 == 0) {
                        byte2 = bytes[index++];
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || bytes[index++] > -65) {
                        return -1;
                    }
                }
                else {
                    int byte2 = (byte)~(state >> 8);
                    int byte3 = 0;
                    if (byte2 == 0) {
                        byte2 = bytes[index++];
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    else {
                        byte3 = (byte)(state >> 16);
                    }
                    if (byte3 == 0) {
                        byte3 = bytes[index++];
                        if (index >= limit) {
                            return incompleteStateFor(byte1, byte2, byte3);
                        }
                    }
                    if (byte2 > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || byte3 > -65 || bytes[index++] > -65) {
                        return -1;
                    }
                }
            }
            return partialIsValidUtf8(bytes, index, limit);
        }
        
        @Override
        int partialIsValidUtf8Direct(final int state, final ByteBuffer buffer, final int index, final int limit) {
            return this.partialIsValidUtf8Default(state, buffer, index, limit);
        }
        
        @Override
        String decodeUtf8(final byte[] bytes, final int index, final int size) throws InvalidProtocolBufferException {
            if ((index | size | bytes.length - index - size) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", bytes.length, index, size));
            }
            int offset = index;
            final int limit = offset + size;
            final char[] resultArr = new char[size];
            int resultPos = 0;
            while (offset < limit) {
                final byte b = bytes[offset];
                if (!isOneByte(b)) {
                    break;
                }
                ++offset;
                handleOneByte(b, resultArr, resultPos++);
            }
            while (offset < limit) {
                final byte byte1 = bytes[offset++];
                if (isOneByte(byte1)) {
                    handleOneByte(byte1, resultArr, resultPos++);
                    while (offset < limit) {
                        final byte b2 = bytes[offset];
                        if (!isOneByte(b2)) {
                            break;
                        }
                        ++offset;
                        handleOneByte(b2, resultArr, resultPos++);
                    }
                }
                else if (isTwoBytes(byte1)) {
                    if (offset >= limit) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleTwoBytes(byte1, bytes[offset++], resultArr, resultPos++);
                }
                else if (isThreeBytes(byte1)) {
                    if (offset >= limit - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleThreeBytes(byte1, bytes[offset++], bytes[offset++], resultArr, resultPos++);
                }
                else {
                    if (offset >= limit - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleFourBytes(byte1, bytes[offset++], bytes[offset++], bytes[offset++], resultArr, resultPos++);
                    ++resultPos;
                }
            }
            return new String(resultArr, 0, resultPos);
        }
        
        @Override
        String decodeUtf8Direct(final ByteBuffer buffer, final int index, final int size) throws InvalidProtocolBufferException {
            return this.decodeUtf8Default(buffer, index, size);
        }
        
        @Override
        int encodeUtf8(final CharSequence in, final byte[] out, final int offset, final int length) {
            int utf16Length;
            int j;
            int i;
            int limit;
            char c;
            for (utf16Length = in.length(), j = offset, i = 0, limit = offset + length; i < utf16Length && i + j < limit && (c = in.charAt(i)) < '\u0080'; ++i) {
                out[j + i] = (byte)c;
            }
            if (i == utf16Length) {
                return j + utf16Length;
            }
            j += i;
            while (i < utf16Length) {
                c = in.charAt(i);
                if (c < '\u0080' && j < limit) {
                    out[j++] = (byte)c;
                }
                else if (c < '\u0800' && j <= limit - 2) {
                    out[j++] = (byte)(0x3C0 | c >>> 6);
                    out[j++] = (byte)(0x80 | ('?' & c));
                }
                else if ((c < '\ud800' || '\udfff' < c) && j <= limit - 3) {
                    out[j++] = (byte)(0x1E0 | c >>> 12);
                    out[j++] = (byte)(0x80 | (0x3F & c >>> 6));
                    out[j++] = (byte)(0x80 | ('?' & c));
                }
                else if (j <= limit - 4) {
                    final char low;
                    if (i + 1 == in.length() || !Character.isSurrogatePair(c, low = in.charAt(++i))) {
                        throw new UnpairedSurrogateException(i - 1, utf16Length);
                    }
                    final int codePoint = Character.toCodePoint(c, low);
                    out[j++] = (byte)(0xF0 | codePoint >>> 18);
                    out[j++] = (byte)(0x80 | (0x3F & codePoint >>> 12));
                    out[j++] = (byte)(0x80 | (0x3F & codePoint >>> 6));
                    out[j++] = (byte)(0x80 | (0x3F & codePoint));
                }
                else {
                    if ('\ud800' <= c && c <= '\udfff' && (i + 1 == in.length() || !Character.isSurrogatePair(c, in.charAt(i + 1)))) {
                        throw new UnpairedSurrogateException(i, utf16Length);
                    }
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + c + " at index " + j);
                }
                ++i;
            }
            return j;
        }
        
        @Override
        void encodeUtf8Direct(final CharSequence in, final ByteBuffer out) {
            this.encodeUtf8Default(in, out);
        }
        
        private static int partialIsValidUtf8(final byte[] bytes, int index, final int limit) {
            while (index < limit && bytes[index] >= 0) {
                ++index;
            }
            return (index >= limit) ? 0 : partialIsValidUtf8NonAscii(bytes, index, limit);
        }
        
        private static int partialIsValidUtf8NonAscii(final byte[] bytes, int index, final int limit) {
            while (index < limit) {
                final int byte1;
                if ((byte1 = bytes[index++]) < 0) {
                    if (byte1 < -32) {
                        if (index >= limit) {
                            return byte1;
                        }
                        if (byte1 < -62 || bytes[index++] > -65) {
                            return -1;
                        }
                        continue;
                    }
                    else if (byte1 < -16) {
                        if (index >= limit - 1) {
                            return incompleteStateFor(bytes, index, limit);
                        }
                        final int byte2;
                        if ((byte2 = bytes[index++]) > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || bytes[index++] > -65) {
                            return -1;
                        }
                        continue;
                    }
                    else {
                        if (index >= limit - 2) {
                            return incompleteStateFor(bytes, index, limit);
                        }
                        final int byte2;
                        if ((byte2 = bytes[index++]) > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || bytes[index++] > -65 || bytes[index++] > -65) {
                            return -1;
                        }
                        continue;
                    }
                }
            }
            return 0;
        }
    }
    
    static final class UnsafeProcessor extends Processor
    {
        static boolean isAvailable() {
            return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
        }
        
        @Override
        int partialIsValidUtf8(final int state, final byte[] bytes, final int index, final int limit) {
            if ((index | limit | bytes.length - limit) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", bytes.length, index, limit));
            }
            long offset = index;
            final long offsetLimit = limit;
            if (state != 0) {
                if (offset >= offsetLimit) {
                    return state;
                }
                final int byte1 = (byte)state;
                if (byte1 < -32) {
                    if (byte1 < -62 || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                }
                else if (byte1 < -16) {
                    int byte2 = (byte)~(state >> 8);
                    if (byte2 == 0) {
                        byte2 = UnsafeUtil.getByte(bytes, offset++);
                        if (offset >= offsetLimit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                }
                else {
                    int byte2 = (byte)~(state >> 8);
                    int byte3 = 0;
                    if (byte2 == 0) {
                        byte2 = UnsafeUtil.getByte(bytes, offset++);
                        if (offset >= offsetLimit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    else {
                        byte3 = (byte)(state >> 16);
                    }
                    if (byte3 == 0) {
                        byte3 = UnsafeUtil.getByte(bytes, offset++);
                        if (offset >= offsetLimit) {
                            return incompleteStateFor(byte1, byte2, byte3);
                        }
                    }
                    if (byte2 > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || byte3 > -65 || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                }
            }
            return partialIsValidUtf8(bytes, offset, (int)(offsetLimit - offset));
        }
        
        @Override
        int partialIsValidUtf8Direct(final int state, final ByteBuffer buffer, final int index, final int limit) {
            if ((index | limit | buffer.limit() - limit) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", buffer.limit(), index, limit));
            }
            long address = UnsafeUtil.addressOffset(buffer) + index;
            final long addressLimit = address + (limit - index);
            if (state != 0) {
                if (address >= addressLimit) {
                    return state;
                }
                final int byte1 = (byte)state;
                if (byte1 < -32) {
                    if (byte1 < -62 || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                }
                else if (byte1 < -16) {
                    int byte2 = (byte)~(state >> 8);
                    if (byte2 == 0) {
                        byte2 = UnsafeUtil.getByte(address++);
                        if (address >= addressLimit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                }
                else {
                    int byte2 = (byte)~(state >> 8);
                    int byte3 = 0;
                    if (byte2 == 0) {
                        byte2 = UnsafeUtil.getByte(address++);
                        if (address >= addressLimit) {
                            return incompleteStateFor(byte1, byte2);
                        }
                    }
                    else {
                        byte3 = (byte)(state >> 16);
                    }
                    if (byte3 == 0) {
                        byte3 = UnsafeUtil.getByte(address++);
                        if (address >= addressLimit) {
                            return incompleteStateFor(byte1, byte2, byte3);
                        }
                    }
                    if (byte2 > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || byte3 > -65 || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                }
            }
            return partialIsValidUtf8(address, (int)(addressLimit - address));
        }
        
        @Override
        String decodeUtf8(final byte[] bytes, final int index, final int size) throws InvalidProtocolBufferException {
            if ((index | size | bytes.length - index - size) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", bytes.length, index, size));
            }
            int offset = index;
            final int limit = offset + size;
            final char[] resultArr = new char[size];
            int resultPos = 0;
            while (offset < limit) {
                final byte b = UnsafeUtil.getByte(bytes, offset);
                if (!isOneByte(b)) {
                    break;
                }
                ++offset;
                handleOneByte(b, resultArr, resultPos++);
            }
            while (offset < limit) {
                final byte byte1 = UnsafeUtil.getByte(bytes, offset++);
                if (isOneByte(byte1)) {
                    handleOneByte(byte1, resultArr, resultPos++);
                    while (offset < limit) {
                        final byte b2 = UnsafeUtil.getByte(bytes, offset);
                        if (!isOneByte(b2)) {
                            break;
                        }
                        ++offset;
                        handleOneByte(b2, resultArr, resultPos++);
                    }
                }
                else if (isTwoBytes(byte1)) {
                    if (offset >= limit) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleTwoBytes(byte1, UnsafeUtil.getByte(bytes, offset++), resultArr, resultPos++);
                }
                else if (isThreeBytes(byte1)) {
                    if (offset >= limit - 1) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleThreeBytes(byte1, UnsafeUtil.getByte(bytes, offset++), UnsafeUtil.getByte(bytes, offset++), resultArr, resultPos++);
                }
                else {
                    if (offset >= limit - 2) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleFourBytes(byte1, UnsafeUtil.getByte(bytes, offset++), UnsafeUtil.getByte(bytes, offset++), UnsafeUtil.getByte(bytes, offset++), resultArr, resultPos++);
                    ++resultPos;
                }
            }
            return new String(resultArr, 0, resultPos);
        }
        
        @Override
        String decodeUtf8Direct(final ByteBuffer buffer, final int index, final int size) throws InvalidProtocolBufferException {
            if ((index | size | buffer.limit() - index - size) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", buffer.limit(), index, size));
            }
            long address = UnsafeUtil.addressOffset(buffer) + index;
            final long addressLimit = address + size;
            final char[] resultArr = new char[size];
            int resultPos = 0;
            while (address < addressLimit) {
                final byte b = UnsafeUtil.getByte(address);
                if (!isOneByte(b)) {
                    break;
                }
                ++address;
                handleOneByte(b, resultArr, resultPos++);
            }
            while (address < addressLimit) {
                final byte byte1 = UnsafeUtil.getByte(address++);
                if (isOneByte(byte1)) {
                    handleOneByte(byte1, resultArr, resultPos++);
                    while (address < addressLimit) {
                        final byte b2 = UnsafeUtil.getByte(address);
                        if (!isOneByte(b2)) {
                            break;
                        }
                        ++address;
                        handleOneByte(b2, resultArr, resultPos++);
                    }
                }
                else if (isTwoBytes(byte1)) {
                    if (address >= addressLimit) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleTwoBytes(byte1, UnsafeUtil.getByte(address++), resultArr, resultPos++);
                }
                else if (isThreeBytes(byte1)) {
                    if (address >= addressLimit - 1L) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleThreeBytes(byte1, UnsafeUtil.getByte(address++), UnsafeUtil.getByte(address++), resultArr, resultPos++);
                }
                else {
                    if (address >= addressLimit - 2L) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    handleFourBytes(byte1, UnsafeUtil.getByte(address++), UnsafeUtil.getByte(address++), UnsafeUtil.getByte(address++), resultArr, resultPos++);
                    ++resultPos;
                }
            }
            return new String(resultArr, 0, resultPos);
        }
        
        @Override
        int encodeUtf8(final CharSequence in, final byte[] out, final int offset, final int length) {
            long outIx = offset;
            final long outLimit = outIx + length;
            final int inLimit = in.length();
            if (inLimit > length || out.length - length < offset) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inLimit - 1) + " at index " + (offset + length));
            }
            int inIx;
            char c;
            for (inIx = 0; inIx < inLimit && (c = in.charAt(inIx)) < '\u0080'; ++inIx) {
                UnsafeUtil.putByte(out, outIx++, (byte)c);
            }
            if (inIx == inLimit) {
                return (int)outIx;
            }
            while (inIx < inLimit) {
                c = in.charAt(inIx);
                if (c < '\u0080' && outIx < outLimit) {
                    UnsafeUtil.putByte(out, outIx++, (byte)c);
                }
                else if (c < '\u0800' && outIx <= outLimit - 2L) {
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x3C0 | c >>> 6));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | ('?' & c)));
                }
                else if ((c < '\ud800' || '\udfff' < c) && outIx <= outLimit - 3L) {
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x1E0 | c >>> 12));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | (0x3F & c >>> 6)));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | ('?' & c)));
                }
                else if (outIx <= outLimit - 4L) {
                    final char low;
                    if (inIx + 1 == inLimit || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
                        throw new UnpairedSurrogateException(inIx - 1, inLimit);
                    }
                    final int codePoint = Character.toCodePoint(c, low);
                    UnsafeUtil.putByte(out, outIx++, (byte)(0xF0 | codePoint >>> 18));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                    UnsafeUtil.putByte(out, outIx++, (byte)(0x80 | (0x3F & codePoint)));
                }
                else {
                    if ('\ud800' <= c && c <= '\udfff' && (inIx + 1 == inLimit || !Character.isSurrogatePair(c, in.charAt(inIx + 1)))) {
                        throw new UnpairedSurrogateException(inIx, inLimit);
                    }
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + c + " at index " + outIx);
                }
                ++inIx;
            }
            return (int)outIx;
        }
        
        @Override
        void encodeUtf8Direct(final CharSequence in, final ByteBuffer out) {
            final long address = UnsafeUtil.addressOffset(out);
            long outIx = address + out.position();
            final long outLimit = address + out.limit();
            final int inLimit = in.length();
            if (inLimit > outLimit - outIx) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + in.charAt(inLimit - 1) + " at index " + out.limit());
            }
            int inIx;
            char c;
            for (inIx = 0; inIx < inLimit && (c = in.charAt(inIx)) < '\u0080'; ++inIx) {
                UnsafeUtil.putByte(outIx++, (byte)c);
            }
            if (inIx == inLimit) {
                out.position((int)(outIx - address));
                return;
            }
            while (inIx < inLimit) {
                c = in.charAt(inIx);
                if (c < '\u0080' && outIx < outLimit) {
                    UnsafeUtil.putByte(outIx++, (byte)c);
                }
                else if (c < '\u0800' && outIx <= outLimit - 2L) {
                    UnsafeUtil.putByte(outIx++, (byte)(0x3C0 | c >>> 6));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | ('?' & c)));
                }
                else if ((c < '\ud800' || '\udfff' < c) && outIx <= outLimit - 3L) {
                    UnsafeUtil.putByte(outIx++, (byte)(0x1E0 | c >>> 12));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | (0x3F & c >>> 6)));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | ('?' & c)));
                }
                else if (outIx <= outLimit - 4L) {
                    final char low;
                    if (inIx + 1 == inLimit || !Character.isSurrogatePair(c, low = in.charAt(++inIx))) {
                        throw new UnpairedSurrogateException(inIx - 1, inLimit);
                    }
                    final int codePoint = Character.toCodePoint(c, low);
                    UnsafeUtil.putByte(outIx++, (byte)(0xF0 | codePoint >>> 18));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | (0x3F & codePoint >>> 12)));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | (0x3F & codePoint >>> 6)));
                    UnsafeUtil.putByte(outIx++, (byte)(0x80 | (0x3F & codePoint)));
                }
                else {
                    if ('\ud800' <= c && c <= '\udfff' && (inIx + 1 == inLimit || !Character.isSurrogatePair(c, in.charAt(inIx + 1)))) {
                        throw new UnpairedSurrogateException(inIx, inLimit);
                    }
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + c + " at index " + outIx);
                }
                ++inIx;
            }
            out.position((int)(outIx - address));
        }
        
        private static int unsafeEstimateConsecutiveAscii(final byte[] bytes, long offset, final int maxChars) {
            if (maxChars < 16) {
                return 0;
            }
            for (int i = 0; i < maxChars; ++i) {
                if (UnsafeUtil.getByte(bytes, offset++) < 0) {
                    return i;
                }
            }
            return maxChars;
        }
        
        private static int unsafeEstimateConsecutiveAscii(long address, final int maxChars) {
            int remaining = maxChars;
            if (remaining < 16) {
                return 0;
            }
            int j;
            int unaligned;
            for (unaligned = (j = 8 - ((int)address & 0x7)); j > 0; --j) {
                if (UnsafeUtil.getByte(address++) < 0) {
                    return unaligned - j;
                }
            }
            for (remaining -= unaligned; remaining >= 8 && (UnsafeUtil.getLong(address) & 0x8080808080808080L) == 0x0L; address += 8L, remaining -= 8) {}
            return maxChars - remaining;
        }
        
        private static int partialIsValidUtf8(final byte[] bytes, long offset, int remaining) {
            final int skipped = unsafeEstimateConsecutiveAscii(bytes, offset, remaining);
            remaining -= skipped;
            offset += skipped;
            while (true) {
                int byte1 = 0;
                while (remaining > 0 && (byte1 = UnsafeUtil.getByte(bytes, offset++)) >= 0) {
                    --remaining;
                }
                if (remaining == 0) {
                    return 0;
                }
                --remaining;
                if (byte1 < -32) {
                    if (remaining == 0) {
                        return byte1;
                    }
                    --remaining;
                    if (byte1 < -62 || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                    continue;
                }
                else if (byte1 < -16) {
                    if (remaining < 2) {
                        return unsafeIncompleteStateFor(bytes, byte1, offset, remaining);
                    }
                    remaining -= 2;
                    final int byte2;
                    if ((byte2 = UnsafeUtil.getByte(bytes, offset++)) > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                    continue;
                }
                else {
                    if (remaining < 3) {
                        return unsafeIncompleteStateFor(bytes, byte1, offset, remaining);
                    }
                    remaining -= 3;
                    final int byte2;
                    if ((byte2 = UnsafeUtil.getByte(bytes, offset++)) > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || UnsafeUtil.getByte(bytes, offset++) > -65 || UnsafeUtil.getByte(bytes, offset++) > -65) {
                        return -1;
                    }
                    continue;
                }
            }
        }
        
        private static int partialIsValidUtf8(long address, int remaining) {
            final int skipped = unsafeEstimateConsecutiveAscii(address, remaining);
            address += skipped;
            remaining -= skipped;
            while (true) {
                int byte1 = 0;
                while (remaining > 0 && (byte1 = UnsafeUtil.getByte(address++)) >= 0) {
                    --remaining;
                }
                if (remaining == 0) {
                    return 0;
                }
                --remaining;
                if (byte1 < -32) {
                    if (remaining == 0) {
                        return byte1;
                    }
                    --remaining;
                    if (byte1 < -62 || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                    continue;
                }
                else if (byte1 < -16) {
                    if (remaining < 2) {
                        return unsafeIncompleteStateFor(address, byte1, remaining);
                    }
                    remaining -= 2;
                    final byte byte2 = UnsafeUtil.getByte(address++);
                    if (byte2 > -65 || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                    continue;
                }
                else {
                    if (remaining < 3) {
                        return unsafeIncompleteStateFor(address, byte1, remaining);
                    }
                    remaining -= 3;
                    final byte byte2 = UnsafeUtil.getByte(address++);
                    if (byte2 > -65 || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || UnsafeUtil.getByte(address++) > -65 || UnsafeUtil.getByte(address++) > -65) {
                        return -1;
                    }
                    continue;
                }
            }
        }
        
        private static int unsafeIncompleteStateFor(final byte[] bytes, final int byte1, final long offset, final int remaining) {
            switch (remaining) {
                case 0: {
                    return incompleteStateFor(byte1);
                }
                case 1: {
                    return incompleteStateFor(byte1, UnsafeUtil.getByte(bytes, offset));
                }
                case 2: {
                    return incompleteStateFor(byte1, UnsafeUtil.getByte(bytes, offset), UnsafeUtil.getByte(bytes, offset + 1L));
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        private static int unsafeIncompleteStateFor(final long address, final int byte1, final int remaining) {
            switch (remaining) {
                case 0: {
                    return incompleteStateFor(byte1);
                }
                case 1: {
                    return incompleteStateFor(byte1, UnsafeUtil.getByte(address));
                }
                case 2: {
                    return incompleteStateFor(byte1, UnsafeUtil.getByte(address), UnsafeUtil.getByte(address + 1L));
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
    }
    
    private static class DecodeUtil
    {
        private static boolean isOneByte(final byte b) {
            return b >= 0;
        }
        
        private static boolean isTwoBytes(final byte b) {
            return b < -32;
        }
        
        private static boolean isThreeBytes(final byte b) {
            return b < -16;
        }
        
        private static void handleOneByte(final byte byte1, final char[] resultArr, final int resultPos) {
            resultArr[resultPos] = (char)byte1;
        }
        
        private static void handleTwoBytes(final byte byte1, final byte byte2, final char[] resultArr, final int resultPos) throws InvalidProtocolBufferException {
            if (byte1 < -62 || isNotTrailingByte(byte2)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            resultArr[resultPos] = (char)((byte1 & 0x1F) << 6 | trailingByteValue(byte2));
        }
        
        private static void handleThreeBytes(final byte byte1, final byte byte2, final byte byte3, final char[] resultArr, final int resultPos) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(byte2) || (byte1 == -32 && byte2 < -96) || (byte1 == -19 && byte2 >= -96) || isNotTrailingByte(byte3)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            resultArr[resultPos] = (char)((byte1 & 0xF) << 12 | trailingByteValue(byte2) << 6 | trailingByteValue(byte3));
        }
        
        private static void handleFourBytes(final byte byte1, final byte byte2, final byte byte3, final byte byte4, final char[] resultArr, final int resultPos) throws InvalidProtocolBufferException {
            if (isNotTrailingByte(byte2) || (byte1 << 28) + (byte2 + 112) >> 30 != 0 || isNotTrailingByte(byte3) || isNotTrailingByte(byte4)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            final int codepoint = (byte1 & 0x7) << 18 | trailingByteValue(byte2) << 12 | trailingByteValue(byte3) << 6 | trailingByteValue(byte4);
            resultArr[resultPos] = highSurrogate(codepoint);
            resultArr[resultPos + 1] = lowSurrogate(codepoint);
        }
        
        private static boolean isNotTrailingByte(final byte b) {
            return b > -65;
        }
        
        private static int trailingByteValue(final byte b) {
            return b & 0x3F;
        }
        
        private static char highSurrogate(final int codePoint) {
            return (char)(55232 + (codePoint >>> 10));
        }
        
        private static char lowSurrogate(final int codePoint) {
            return (char)(56320 + (codePoint & 0x3FF));
        }
    }
}
