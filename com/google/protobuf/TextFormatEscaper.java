package com.google.protobuf;

final class TextFormatEscaper
{
    private TextFormatEscaper() {
    }
    
    static String escapeBytes(final ByteSequence input) {
        final StringBuilder builder = new StringBuilder(input.size());
        for (int i = 0; i < input.size(); ++i) {
            final byte b = input.byteAt(i);
            switch (b) {
                case 7: {
                    builder.append("\\a");
                    break;
                }
                case 8: {
                    builder.append("\\b");
                    break;
                }
                case 12: {
                    builder.append("\\f");
                    break;
                }
                case 10: {
                    builder.append("\\n");
                    break;
                }
                case 13: {
                    builder.append("\\r");
                    break;
                }
                case 9: {
                    builder.append("\\t");
                    break;
                }
                case 11: {
                    builder.append("\\v");
                    break;
                }
                case 92: {
                    builder.append("\\\\");
                    break;
                }
                case 39: {
                    builder.append("\\'");
                    break;
                }
                case 34: {
                    builder.append("\\\"");
                    break;
                }
                default: {
                    if (b >= 32 && b <= 126) {
                        builder.append((char)b);
                        break;
                    }
                    builder.append('\\');
                    builder.append((char)(48 + (b >>> 6 & 0x3)));
                    builder.append((char)(48 + (b >>> 3 & 0x7)));
                    builder.append((char)(48 + (b & 0x7)));
                    break;
                }
            }
        }
        return builder.toString();
    }
    
    static String escapeBytes(final ByteString input) {
        return escapeBytes(new ByteSequence() {
            @Override
            public int size() {
                return input.size();
            }
            
            @Override
            public byte byteAt(final int offset) {
                return input.byteAt(offset);
            }
        });
    }
    
    static String escapeBytes(final byte[] input) {
        return escapeBytes(new ByteSequence() {
            @Override
            public int size() {
                return input.length;
            }
            
            @Override
            public byte byteAt(final int offset) {
                return input[offset];
            }
        });
    }
    
    static String escapeText(final String input) {
        return escapeBytes(ByteString.copyFromUtf8(input));
    }
    
    static String escapeDoubleQuotesAndBackslashes(final String input) {
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    
    private interface ByteSequence
    {
        int size();
        
        byte byteAt(final int p0);
    }
}
