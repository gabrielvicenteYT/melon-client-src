package com.google.protobuf;

import java.util.*;

public final class TextFormatParseLocation
{
    public static final TextFormatParseLocation EMPTY;
    private final int line;
    private final int column;
    
    static TextFormatParseLocation create(final int line, final int column) {
        if (line == -1 && column == -1) {
            return TextFormatParseLocation.EMPTY;
        }
        if (line < 0 || column < 0) {
            throw new IllegalArgumentException(String.format("line and column values must be >= 0: line %d, column: %d", line, column));
        }
        return new TextFormatParseLocation(line, column);
    }
    
    private TextFormatParseLocation(final int line, final int column) {
        this.line = line;
        this.column = column;
    }
    
    public int getLine() {
        return this.line;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    @Override
    public String toString() {
        return String.format("ParseLocation{line=%d, column=%d}", this.line, this.column);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TextFormatParseLocation)) {
            return false;
        }
        final TextFormatParseLocation that = (TextFormatParseLocation)o;
        return this.line == that.getLine() && this.column == that.getColumn();
    }
    
    @Override
    public int hashCode() {
        final int[] values = { this.line, this.column };
        return Arrays.hashCode(values);
    }
    
    static {
        EMPTY = new TextFormatParseLocation(-1, -1);
    }
}
