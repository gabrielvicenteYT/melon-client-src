package com.google.protobuf;

import java.util.logging.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.*;
import java.nio.*;

public final class TextFormat
{
    private static final Logger logger;
    private static final Parser PARSER;
    
    private TextFormat() {
    }
    
    @Deprecated
    public static void print(final MessageOrBuilder message, final Appendable output) throws IOException {
        printer().print(message, output);
    }
    
    @Deprecated
    public static void print(final UnknownFieldSet fields, final Appendable output) throws IOException {
        printer().print(fields, output);
    }
    
    @Deprecated
    public static void printUnicode(final MessageOrBuilder message, final Appendable output) throws IOException {
        printer().escapingNonAscii(false).print(message, output);
    }
    
    @Deprecated
    public static void printUnicode(final UnknownFieldSet fields, final Appendable output) throws IOException {
        printer().escapingNonAscii(false).print(fields, output);
    }
    
    public static String shortDebugString(final MessageOrBuilder message) {
        return printer().shortDebugString(message);
    }
    
    @Deprecated
    public static String shortDebugString(final Descriptors.FieldDescriptor field, final Object value) {
        return printer().shortDebugString(field, value);
    }
    
    @Deprecated
    public static String shortDebugString(final UnknownFieldSet fields) {
        return printer().shortDebugString(fields);
    }
    
    @Deprecated
    public static String printToString(final MessageOrBuilder message) {
        return printer().printToString(message);
    }
    
    @Deprecated
    public static String printToString(final UnknownFieldSet fields) {
        return printer().printToString(fields);
    }
    
    @Deprecated
    public static String printToUnicodeString(final MessageOrBuilder message) {
        return printer().escapingNonAscii(false).printToString(message);
    }
    
    @Deprecated
    public static String printToUnicodeString(final UnknownFieldSet fields) {
        return printer().escapingNonAscii(false).printToString(fields);
    }
    
    @Deprecated
    public static void printField(final Descriptors.FieldDescriptor field, final Object value, final Appendable output) throws IOException {
        printer().printField(field, value, output);
    }
    
    @Deprecated
    public static String printFieldToString(final Descriptors.FieldDescriptor field, final Object value) {
        return printer().printFieldToString(field, value);
    }
    
    @Deprecated
    public static void printUnicodeFieldValue(final Descriptors.FieldDescriptor field, final Object value, final Appendable output) throws IOException {
        printer().escapingNonAscii(false).printFieldValue(field, value, output);
    }
    
    @Deprecated
    public static void printFieldValue(final Descriptors.FieldDescriptor field, final Object value, final Appendable output) throws IOException {
        printer().printFieldValue(field, value, output);
    }
    
    public static void printUnknownFieldValue(final int tag, final Object value, final Appendable output) throws IOException {
        printUnknownFieldValue(tag, value, multiLineOutput(output));
    }
    
    private static void printUnknownFieldValue(final int tag, final Object value, final TextGenerator generator) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0: {
                generator.print(unsignedToString((long)value));
                break;
            }
            case 5: {
                generator.print(String.format(null, "0x%08x", (Integer)value));
                break;
            }
            case 1: {
                generator.print(String.format(null, "0x%016x", (Long)value));
                break;
            }
            case 2: {
                try {
                    final UnknownFieldSet message = UnknownFieldSet.parseFrom((ByteString)value);
                    generator.print("{");
                    generator.eol();
                    generator.indent();
                    printUnknownFields(message, generator);
                    generator.outdent();
                    generator.print("}");
                }
                catch (InvalidProtocolBufferException e) {
                    generator.print("\"");
                    generator.print(escapeBytes((ByteString)value));
                    generator.print("\"");
                }
                break;
            }
            case 3: {
                printUnknownFields((UnknownFieldSet)value, generator);
                break;
            }
            default: {
                throw new IllegalArgumentException("Bad tag: " + tag);
            }
        }
    }
    
    public static Printer printer() {
        return Printer.DEFAULT;
    }
    
    public static String unsignedToString(final int value) {
        if (value >= 0) {
            return Integer.toString(value);
        }
        return Long.toString((long)value & 0xFFFFFFFFL);
    }
    
    public static String unsignedToString(final long value) {
        if (value >= 0L) {
            return Long.toString(value);
        }
        return BigInteger.valueOf(value & Long.MAX_VALUE).setBit(63).toString();
    }
    
    private static TextGenerator multiLineOutput(final Appendable output) {
        return new TextGenerator(output, false);
    }
    
    private static TextGenerator singleLineOutput(final Appendable output) {
        return new TextGenerator(output, true);
    }
    
    public static Parser getParser() {
        return TextFormat.PARSER;
    }
    
    public static void merge(final Readable input, final Message.Builder builder) throws IOException {
        TextFormat.PARSER.merge(input, builder);
    }
    
    public static void merge(final CharSequence input, final Message.Builder builder) throws ParseException {
        TextFormat.PARSER.merge(input, builder);
    }
    
    public static <T extends Message> T parse(final CharSequence input, final Class<T> protoClass) throws ParseException {
        final Message.Builder builder = Internal.getDefaultInstance(protoClass).newBuilderForType();
        merge(input, builder);
        final T output = (T)builder.build();
        return output;
    }
    
    public static void merge(final Readable input, final ExtensionRegistry extensionRegistry, final Message.Builder builder) throws IOException {
        TextFormat.PARSER.merge(input, extensionRegistry, builder);
    }
    
    public static void merge(final CharSequence input, final ExtensionRegistry extensionRegistry, final Message.Builder builder) throws ParseException {
        TextFormat.PARSER.merge(input, extensionRegistry, builder);
    }
    
    public static <T extends Message> T parse(final CharSequence input, final ExtensionRegistry extensionRegistry, final Class<T> protoClass) throws ParseException {
        final Message.Builder builder = Internal.getDefaultInstance(protoClass).newBuilderForType();
        merge(input, extensionRegistry, builder);
        final T output = (T)builder.build();
        return output;
    }
    
    public static String escapeBytes(final ByteString input) {
        return TextFormatEscaper.escapeBytes(input);
    }
    
    public static String escapeBytes(final byte[] input) {
        return TextFormatEscaper.escapeBytes(input);
    }
    
    public static ByteString unescapeBytes(final CharSequence charString) throws InvalidEscapeSequenceException {
        final ByteString input = ByteString.copyFromUtf8(charString.toString());
        final byte[] result = new byte[input.size()];
        int pos = 0;
        for (int i = 0; i < input.size(); ++i) {
            byte c = input.byteAt(i);
            if (c == 92) {
                if (i + 1 >= input.size()) {
                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
                }
                ++i;
                c = input.byteAt(i);
                if (isOctal(c)) {
                    int code = digitValue(c);
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        ++i;
                        code = code * 8 + digitValue(input.byteAt(i));
                    }
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        ++i;
                        code = code * 8 + digitValue(input.byteAt(i));
                    }
                    result[pos++] = (byte)code;
                }
                else {
                    switch (c) {
                        case 97: {
                            result[pos++] = 7;
                            break;
                        }
                        case 98: {
                            result[pos++] = 8;
                            break;
                        }
                        case 102: {
                            result[pos++] = 12;
                            break;
                        }
                        case 110: {
                            result[pos++] = 10;
                            break;
                        }
                        case 114: {
                            result[pos++] = 13;
                            break;
                        }
                        case 116: {
                            result[pos++] = 9;
                            break;
                        }
                        case 118: {
                            result[pos++] = 11;
                            break;
                        }
                        case 92: {
                            result[pos++] = 92;
                            break;
                        }
                        case 39: {
                            result[pos++] = 39;
                            break;
                        }
                        case 34: {
                            result[pos++] = 34;
                            break;
                        }
                        case 120: {
                            int code = 0;
                            if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                ++i;
                                code = digitValue(input.byteAt(i));
                                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                    ++i;
                                    code = code * 16 + digitValue(input.byteAt(i));
                                }
                                result[pos++] = (byte)code;
                                break;
                            }
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                        }
                        default: {
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + (char)c + '\'');
                        }
                    }
                }
            }
            else {
                result[pos++] = c;
            }
        }
        return (result.length == pos) ? ByteString.wrap(result) : ByteString.copyFrom(result, 0, pos);
    }
    
    static String escapeText(final String input) {
        return escapeBytes(ByteString.copyFromUtf8(input));
    }
    
    public static String escapeDoubleQuotesAndBackslashes(final String input) {
        return TextFormatEscaper.escapeDoubleQuotesAndBackslashes(input);
    }
    
    static String unescapeText(final String input) throws InvalidEscapeSequenceException {
        return unescapeBytes(input).toStringUtf8();
    }
    
    private static boolean isOctal(final byte c) {
        return 48 <= c && c <= 55;
    }
    
    private static boolean isHex(final byte c) {
        return (48 <= c && c <= 57) || (97 <= c && c <= 102) || (65 <= c && c <= 70);
    }
    
    private static int digitValue(final byte c) {
        if (48 <= c && c <= 57) {
            return c - 48;
        }
        if (97 <= c && c <= 122) {
            return c - 97 + 10;
        }
        return c - 65 + 10;
    }
    
    static int parseInt32(final String text) throws NumberFormatException {
        return (int)parseInteger(text, true, false);
    }
    
    static int parseUInt32(final String text) throws NumberFormatException {
        return (int)parseInteger(text, false, false);
    }
    
    static long parseInt64(final String text) throws NumberFormatException {
        return parseInteger(text, true, true);
    }
    
    static long parseUInt64(final String text) throws NumberFormatException {
        return parseInteger(text, false, true);
    }
    
    private static long parseInteger(final String text, final boolean isSigned, final boolean isLong) throws NumberFormatException {
        int pos = 0;
        boolean negative = false;
        if (text.startsWith("-", pos)) {
            if (!isSigned) {
                throw new NumberFormatException("Number must be positive: " + text);
            }
            ++pos;
            negative = true;
        }
        int radix = 10;
        if (text.startsWith("0x", pos)) {
            pos += 2;
            radix = 16;
        }
        else if (text.startsWith("0", pos)) {
            radix = 8;
        }
        final String numberText = text.substring(pos);
        long result = 0L;
        if (numberText.length() < 16) {
            result = Long.parseLong(numberText, radix);
            if (negative) {
                result = -result;
            }
            if (!isLong) {
                if (isSigned) {
                    if (result > 2147483647L || result < -2147483648L) {
                        throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
                    }
                }
                else if (result >= 4294967296L || result < 0L) {
                    throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
                }
            }
        }
        else {
            BigInteger bigValue = new BigInteger(numberText, radix);
            if (negative) {
                bigValue = bigValue.negate();
            }
            if (!isLong) {
                if (isSigned) {
                    if (bigValue.bitLength() > 31) {
                        throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
                    }
                }
                else if (bigValue.bitLength() > 32) {
                    throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
                }
            }
            else if (isSigned) {
                if (bigValue.bitLength() > 63) {
                    throw new NumberFormatException("Number out of range for 64-bit signed integer: " + text);
                }
            }
            else if (bigValue.bitLength() > 64) {
                throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + text);
            }
            result = bigValue.longValue();
        }
        return result;
    }
    
    static {
        logger = Logger.getLogger(TextFormat.class.getName());
        PARSER = Parser.newBuilder().build();
    }
    
    public static final class Printer
    {
        private static final Printer DEFAULT;
        private final boolean escapeNonAscii;
        private final TypeRegistry typeRegistry;
        
        private Printer(final boolean escapeNonAscii, final TypeRegistry typeRegistry) {
            this.escapeNonAscii = escapeNonAscii;
            this.typeRegistry = typeRegistry;
        }
        
        public Printer escapingNonAscii(final boolean escapeNonAscii) {
            return new Printer(escapeNonAscii, this.typeRegistry);
        }
        
        public Printer usingTypeRegistry(final TypeRegistry typeRegistry) {
            if (this.typeRegistry != TypeRegistry.getEmptyTypeRegistry()) {
                throw new IllegalArgumentException("Only one typeRegistry is allowed.");
            }
            return new Printer(this.escapeNonAscii, typeRegistry);
        }
        
        public void print(final MessageOrBuilder message, final Appendable output) throws IOException {
            this.print(message, multiLineOutput(output));
        }
        
        public void print(final UnknownFieldSet fields, final Appendable output) throws IOException {
            printUnknownFields(fields, multiLineOutput(output));
        }
        
        private void print(final MessageOrBuilder message, final TextGenerator generator) throws IOException {
            if (message.getDescriptorForType().getFullName().equals("google.protobuf.Any") && this.printAny(message, generator)) {
                return;
            }
            this.printMessage(message, generator);
        }
        
        private boolean printAny(final MessageOrBuilder message, final TextGenerator generator) throws IOException {
            final Descriptors.Descriptor messageType = message.getDescriptorForType();
            final Descriptors.FieldDescriptor typeUrlField = messageType.findFieldByNumber(1);
            final Descriptors.FieldDescriptor valueField = messageType.findFieldByNumber(2);
            if (typeUrlField == null || typeUrlField.getType() != Descriptors.FieldDescriptor.Type.STRING || valueField == null || valueField.getType() != Descriptors.FieldDescriptor.Type.BYTES) {
                return false;
            }
            final String typeUrl = (String)message.getField(typeUrlField);
            if (typeUrl.isEmpty()) {
                return false;
            }
            final Object value = message.getField(valueField);
            Message.Builder contentBuilder = null;
            try {
                final Descriptors.Descriptor contentType = this.typeRegistry.getDescriptorForTypeUrl(typeUrl);
                if (contentType == null) {
                    return false;
                }
                contentBuilder = DynamicMessage.getDefaultInstance(contentType).newBuilderForType();
                contentBuilder.mergeFrom((ByteString)value);
            }
            catch (InvalidProtocolBufferException e) {
                return false;
            }
            generator.print("[");
            generator.print(typeUrl);
            generator.print("] {");
            generator.eol();
            generator.indent();
            this.print(contentBuilder, generator);
            generator.outdent();
            generator.print("}");
            generator.eol();
            return true;
        }
        
        public String printFieldToString(final Descriptors.FieldDescriptor field, final Object value) {
            try {
                final StringBuilder text = new StringBuilder();
                this.printField(field, value, text);
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public void printField(final Descriptors.FieldDescriptor field, final Object value, final Appendable output) throws IOException {
            this.printField(field, value, multiLineOutput(output));
        }
        
        private void printField(final Descriptors.FieldDescriptor field, final Object value, final TextGenerator generator) throws IOException {
            if (field.isRepeated()) {
                for (final Object element : (List)value) {
                    this.printSingleField(field, element, generator);
                }
            }
            else {
                this.printSingleField(field, value, generator);
            }
        }
        
        public void printFieldValue(final Descriptors.FieldDescriptor field, final Object value, final Appendable output) throws IOException {
            this.printFieldValue(field, value, multiLineOutput(output));
        }
        
        private void printFieldValue(final Descriptors.FieldDescriptor field, final Object value, final TextGenerator generator) throws IOException {
            switch (field.getType()) {
                case INT32:
                case SINT32:
                case SFIXED32: {
                    generator.print(((Integer)value).toString());
                    break;
                }
                case INT64:
                case SINT64:
                case SFIXED64: {
                    generator.print(((Long)value).toString());
                    break;
                }
                case BOOL: {
                    generator.print(((Boolean)value).toString());
                    break;
                }
                case FLOAT: {
                    generator.print(((Float)value).toString());
                    break;
                }
                case DOUBLE: {
                    generator.print(((Double)value).toString());
                    break;
                }
                case UINT32:
                case FIXED32: {
                    generator.print(TextFormat.unsignedToString((int)value));
                    break;
                }
                case UINT64:
                case FIXED64: {
                    generator.print(TextFormat.unsignedToString((long)value));
                    break;
                }
                case STRING: {
                    generator.print("\"");
                    generator.print(this.escapeNonAscii ? TextFormatEscaper.escapeText((String)value) : TextFormat.escapeDoubleQuotesAndBackslashes((String)value).replace("\n", "\\n"));
                    generator.print("\"");
                    break;
                }
                case BYTES: {
                    generator.print("\"");
                    if (value instanceof ByteString) {
                        generator.print(TextFormat.escapeBytes((ByteString)value));
                    }
                    else {
                        generator.print(TextFormat.escapeBytes((byte[])value));
                    }
                    generator.print("\"");
                    break;
                }
                case ENUM: {
                    generator.print(((Descriptors.EnumValueDescriptor)value).getName());
                    break;
                }
                case MESSAGE:
                case GROUP: {
                    this.print((MessageOrBuilder)value, generator);
                    break;
                }
            }
        }
        
        public String printToString(final MessageOrBuilder message) {
            try {
                final StringBuilder text = new StringBuilder();
                this.print(message, text);
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String printToString(final UnknownFieldSet fields) {
            try {
                final StringBuilder text = new StringBuilder();
                this.print(fields, text);
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String shortDebugString(final MessageOrBuilder message) {
            try {
                final StringBuilder text = new StringBuilder();
                this.print(message, singleLineOutput(text));
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String shortDebugString(final Descriptors.FieldDescriptor field, final Object value) {
            try {
                final StringBuilder text = new StringBuilder();
                this.printField(field, value, singleLineOutput(text));
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        public String shortDebugString(final UnknownFieldSet fields) {
            try {
                final StringBuilder text = new StringBuilder();
                printUnknownFields(fields, singleLineOutput(text));
                return text.toString();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        private static void printUnknownFieldValue(final int tag, final Object value, final TextGenerator generator) throws IOException {
            switch (WireFormat.getTagWireType(tag)) {
                case 0: {
                    generator.print(TextFormat.unsignedToString((long)value));
                    break;
                }
                case 5: {
                    generator.print(String.format(null, "0x%08x", (Integer)value));
                    break;
                }
                case 1: {
                    generator.print(String.format(null, "0x%016x", (Long)value));
                    break;
                }
                case 2: {
                    try {
                        final UnknownFieldSet message = UnknownFieldSet.parseFrom((ByteString)value);
                        generator.print("{");
                        generator.eol();
                        generator.indent();
                        printUnknownFields(message, generator);
                        generator.outdent();
                        generator.print("}");
                    }
                    catch (InvalidProtocolBufferException e) {
                        generator.print("\"");
                        generator.print(TextFormat.escapeBytes((ByteString)value));
                        generator.print("\"");
                    }
                    break;
                }
                case 3: {
                    printUnknownFields((UnknownFieldSet)value, generator);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Bad tag: " + tag);
                }
            }
        }
        
        private void printMessage(final MessageOrBuilder message, final TextGenerator generator) throws IOException {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
                this.printField(field.getKey(), field.getValue(), generator);
            }
            printUnknownFields(message.getUnknownFields(), generator);
        }
        
        private void printSingleField(final Descriptors.FieldDescriptor field, final Object value, final TextGenerator generator) throws IOException {
            if (field.isExtension()) {
                generator.print("[");
                if (field.getContainingType().getOptions().getMessageSetWireFormat() && field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && field.isOptional() && field.getExtensionScope() == field.getMessageType()) {
                    generator.print(field.getMessageType().getFullName());
                }
                else {
                    generator.print(field.getFullName());
                }
                generator.print("]");
            }
            else if (field.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
                generator.print(field.getMessageType().getName());
            }
            else {
                generator.print(field.getName());
            }
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                generator.print(" {");
                generator.eol();
                generator.indent();
            }
            else {
                generator.print(": ");
            }
            this.printFieldValue(field, value, generator);
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                generator.outdent();
                generator.print("}");
            }
            generator.eol();
        }
        
        private static void printUnknownFields(final UnknownFieldSet unknownFields, final TextGenerator generator) throws IOException {
            for (final Map.Entry<Integer, UnknownFieldSet.Field> entry : unknownFields.asMap().entrySet()) {
                final int number = entry.getKey();
                final UnknownFieldSet.Field field = entry.getValue();
                printUnknownField(number, 0, field.getVarintList(), generator);
                printUnknownField(number, 5, field.getFixed32List(), generator);
                printUnknownField(number, 1, field.getFixed64List(), generator);
                printUnknownField(number, 2, field.getLengthDelimitedList(), generator);
                for (final UnknownFieldSet value : field.getGroupList()) {
                    generator.print(entry.getKey().toString());
                    generator.print(" {");
                    generator.eol();
                    generator.indent();
                    printUnknownFields(value, generator);
                    generator.outdent();
                    generator.print("}");
                    generator.eol();
                }
            }
        }
        
        private static void printUnknownField(final int number, final int wireType, final List<?> values, final TextGenerator generator) throws IOException {
            for (final Object value : values) {
                generator.print(String.valueOf(number));
                generator.print(": ");
                printUnknownFieldValue(wireType, value, generator);
                generator.eol();
            }
        }
        
        static {
            DEFAULT = new Printer(true, TypeRegistry.getEmptyTypeRegistry());
        }
    }
    
    private static final class TextGenerator
    {
        private final Appendable output;
        private final StringBuilder indent;
        private final boolean singleLineMode;
        private boolean atStartOfLine;
        
        private TextGenerator(final Appendable output, final boolean singleLineMode) {
            this.indent = new StringBuilder();
            this.atStartOfLine = false;
            this.output = output;
            this.singleLineMode = singleLineMode;
        }
        
        public void indent() {
            this.indent.append("  ");
        }
        
        public void outdent() {
            final int length = this.indent.length();
            if (length == 0) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.setLength(length - 2);
        }
        
        public void print(final CharSequence text) throws IOException {
            if (this.atStartOfLine) {
                this.atStartOfLine = false;
                this.output.append((CharSequence)(this.singleLineMode ? " " : this.indent));
            }
            this.output.append(text);
        }
        
        public void eol() throws IOException {
            if (!this.singleLineMode) {
                this.output.append("\n");
            }
            this.atStartOfLine = true;
        }
    }
    
    private static final class Tokenizer
    {
        private final CharSequence text;
        private final Matcher matcher;
        private String currentToken;
        private int pos;
        private int line;
        private int column;
        private int previousLine;
        private int previousColumn;
        private static final Pattern WHITESPACE;
        private static final Pattern TOKEN;
        private static final Pattern DOUBLE_INFINITY;
        private static final Pattern FLOAT_INFINITY;
        private static final Pattern FLOAT_NAN;
        
        private Tokenizer(final CharSequence text) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.text = text;
            this.matcher = Tokenizer.WHITESPACE.matcher(text);
            this.skipWhitespace();
            this.nextToken();
        }
        
        int getPreviousLine() {
            return this.previousLine;
        }
        
        int getPreviousColumn() {
            return this.previousColumn;
        }
        
        int getLine() {
            return this.line;
        }
        
        int getColumn() {
            return this.column;
        }
        
        public boolean atEnd() {
            return this.currentToken.length() == 0;
        }
        
        public void nextToken() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == '\n') {
                    ++this.line;
                    this.column = 0;
                }
                else {
                    ++this.column;
                }
                ++this.pos;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
            }
            else {
                this.matcher.usePattern(Tokenizer.TOKEN);
                if (this.matcher.lookingAt()) {
                    this.currentToken = this.matcher.group();
                    this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
                }
                else {
                    this.currentToken = String.valueOf(this.text.charAt(this.pos));
                    this.matcher.region(this.pos + 1, this.matcher.regionEnd());
                }
                this.skipWhitespace();
            }
        }
        
        private void skipWhitespace() {
            this.matcher.usePattern(Tokenizer.WHITESPACE);
            if (this.matcher.lookingAt()) {
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            }
        }
        
        public boolean tryConsume(final String token) {
            if (this.currentToken.equals(token)) {
                this.nextToken();
                return true;
            }
            return false;
        }
        
        public void consume(final String token) throws ParseException {
            if (!this.tryConsume(token)) {
                throw this.parseException("Expected \"" + token + "\".");
            }
        }
        
        public boolean lookingAtInteger() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            final char c = this.currentToken.charAt(0);
            return ('0' <= c && c <= '9') || c == '-' || c == '+';
        }
        
        public boolean lookingAt(final String text) {
            return this.currentToken.equals(text);
        }
        
        public String consumeIdentifier() throws ParseException {
            for (int i = 0; i < this.currentToken.length(); ++i) {
                final char c = this.currentToken.charAt(i);
                if (('a' > c || c > 'z') && ('A' > c || c > 'Z') && ('0' > c || c > '9') && c != '_' && c != '.') {
                    throw this.parseException("Expected identifier. Found '" + this.currentToken + "'");
                }
            }
            final String result = this.currentToken;
            this.nextToken();
            return result;
        }
        
        public boolean tryConsumeIdentifier() {
            try {
                this.consumeIdentifier();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public int consumeInt32() throws ParseException {
            try {
                final int result = TextFormat.parseInt32(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.integerParseException(e);
            }
        }
        
        public int consumeUInt32() throws ParseException {
            try {
                final int result = TextFormat.parseUInt32(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.integerParseException(e);
            }
        }
        
        public long consumeInt64() throws ParseException {
            try {
                final long result = TextFormat.parseInt64(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.integerParseException(e);
            }
        }
        
        public boolean tryConsumeInt64() {
            try {
                this.consumeInt64();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public long consumeUInt64() throws ParseException {
            try {
                final long result = TextFormat.parseUInt64(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.integerParseException(e);
            }
        }
        
        public boolean tryConsumeUInt64() {
            try {
                this.consumeUInt64();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public double consumeDouble() throws ParseException {
            if (Tokenizer.DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                final boolean negative = this.currentToken.startsWith("-");
                this.nextToken();
                return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            if (this.currentToken.equalsIgnoreCase("nan")) {
                this.nextToken();
                return Double.NaN;
            }
            try {
                final double result = Double.parseDouble(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.floatParseException(e);
            }
        }
        
        public boolean tryConsumeDouble() {
            try {
                this.consumeDouble();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public float consumeFloat() throws ParseException {
            if (Tokenizer.FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                final boolean negative = this.currentToken.startsWith("-");
                this.nextToken();
                return negative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            }
            if (Tokenizer.FLOAT_NAN.matcher(this.currentToken).matches()) {
                this.nextToken();
                return Float.NaN;
            }
            try {
                final float result = Float.parseFloat(this.currentToken);
                this.nextToken();
                return result;
            }
            catch (NumberFormatException e) {
                throw this.floatParseException(e);
            }
        }
        
        public boolean tryConsumeFloat() {
            try {
                this.consumeFloat();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("True") || this.currentToken.equals("t") || this.currentToken.equals("1")) {
                this.nextToken();
                return true;
            }
            if (this.currentToken.equals("false") || this.currentToken.equals("False") || this.currentToken.equals("f") || this.currentToken.equals("0")) {
                this.nextToken();
                return false;
            }
            throw this.parseException("Expected \"true\" or \"false\". Found \"" + this.currentToken + "\".");
        }
        
        public String consumeString() throws ParseException {
            return this.consumeByteString().toStringUtf8();
        }
        
        public boolean tryConsumeString() {
            try {
                this.consumeString();
                return true;
            }
            catch (ParseException e) {
                return false;
            }
        }
        
        public ByteString consumeByteString() throws ParseException {
            final List<ByteString> list = new ArrayList<ByteString>();
            this.consumeByteString(list);
            while (this.currentToken.startsWith("'") || this.currentToken.startsWith("\"")) {
                this.consumeByteString(list);
            }
            return ByteString.copyFrom(list);
        }
        
        private void consumeByteString(final List<ByteString> list) throws ParseException {
            final char quote = (this.currentToken.length() > 0) ? this.currentToken.charAt(0) : '\0';
            if (quote != '\"' && quote != '\'') {
                throw this.parseException("Expected string.");
            }
            if (this.currentToken.length() < 2 || this.currentToken.charAt(this.currentToken.length() - 1) != quote) {
                throw this.parseException("String missing ending quote.");
            }
            try {
                final String escaped = this.currentToken.substring(1, this.currentToken.length() - 1);
                final ByteString result = TextFormat.unescapeBytes(escaped);
                this.nextToken();
                list.add(result);
            }
            catch (InvalidEscapeSequenceException e) {
                throw this.parseException(e.getMessage());
            }
        }
        
        public ParseException parseException(final String description) {
            return new ParseException(this.line + 1, this.column + 1, description);
        }
        
        public ParseException parseExceptionPreviousToken(final String description) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, description);
        }
        
        private ParseException integerParseException(final NumberFormatException e) {
            return this.parseException("Couldn't parse integer: " + e.getMessage());
        }
        
        private ParseException floatParseException(final NumberFormatException e) {
            return this.parseException("Couldn't parse number: " + e.getMessage());
        }
        
        public UnknownFieldParseException unknownFieldParseExceptionPreviousToken(final String unknownField, final String description) {
            return new UnknownFieldParseException(this.previousLine + 1, this.previousColumn + 1, unknownField, description);
        }
        
        static {
            WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
            TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
            DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
            FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
            FLOAT_NAN = Pattern.compile("nanf?", 2);
        }
    }
    
    public static class ParseException extends IOException
    {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int line;
        private final int column;
        
        public ParseException(final String message) {
            this(-1, -1, message);
        }
        
        public ParseException(final int line, final int column, final String message) {
            super(Integer.toString(line) + ":" + column + ": " + message);
            this.line = line;
            this.column = column;
        }
        
        public int getLine() {
            return this.line;
        }
        
        public int getColumn() {
            return this.column;
        }
    }
    
    public static class UnknownFieldParseException extends ParseException
    {
        private final String unknownField;
        
        public UnknownFieldParseException(final String message) {
            this(-1, -1, "", message);
        }
        
        public UnknownFieldParseException(final int line, final int column, final String unknownField, final String message) {
            super(line, column, message);
            this.unknownField = unknownField;
        }
        
        public String getUnknownField() {
            return this.unknownField;
        }
    }
    
    public static class Parser
    {
        private final TypeRegistry typeRegistry;
        private final boolean allowUnknownFields;
        private final boolean allowUnknownEnumValues;
        private final boolean allowUnknownExtensions;
        private final SingularOverwritePolicy singularOverwritePolicy;
        private TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
        private static final int BUFFER_SIZE = 4096;
        
        private Parser(final TypeRegistry typeRegistry, final boolean allowUnknownFields, final boolean allowUnknownEnumValues, final boolean allowUnknownExtensions, final SingularOverwritePolicy singularOverwritePolicy, final TextFormatParseInfoTree.Builder parseInfoTreeBuilder) {
            this.typeRegistry = typeRegistry;
            this.allowUnknownFields = allowUnknownFields;
            this.allowUnknownEnumValues = allowUnknownEnumValues;
            this.allowUnknownExtensions = allowUnknownExtensions;
            this.singularOverwritePolicy = singularOverwritePolicy;
            this.parseInfoTreeBuilder = parseInfoTreeBuilder;
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public void merge(final Readable input, final Message.Builder builder) throws IOException {
            this.merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }
        
        public void merge(final CharSequence input, final Message.Builder builder) throws ParseException {
            this.merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }
        
        public void merge(final Readable input, final ExtensionRegistry extensionRegistry, final Message.Builder builder) throws IOException {
            this.merge(toStringBuilder(input), extensionRegistry, builder);
        }
        
        private static StringBuilder toStringBuilder(final Readable input) throws IOException {
            final StringBuilder text = new StringBuilder();
            final CharBuffer buffer = CharBuffer.allocate(4096);
            while (true) {
                final int n = input.read(buffer);
                if (n == -1) {
                    break;
                }
                buffer.flip();
                text.append(buffer, 0, n);
            }
            return text;
        }
        
        private void checkUnknownFields(final List<UnknownField> unknownFields) throws ParseException {
            if (unknownFields.isEmpty()) {
                return;
            }
            final StringBuilder msg = new StringBuilder("Input contains unknown fields and/or extensions:");
            for (final UnknownField field : unknownFields) {
                msg.append('\n').append(field.message);
            }
            if (this.allowUnknownFields) {
                TextFormat.logger.warning(msg.toString());
                return;
            }
            int firstErrorIndex = 0;
            if (this.allowUnknownExtensions) {
                boolean allUnknownExtensions = true;
                for (final UnknownField field2 : unknownFields) {
                    if (field2.type == UnknownField.Type.FIELD) {
                        allUnknownExtensions = false;
                        break;
                    }
                    ++firstErrorIndex;
                }
                if (allUnknownExtensions) {
                    TextFormat.logger.warning(msg.toString());
                    return;
                }
            }
            final String[] lineColumn = unknownFields.get(firstErrorIndex).message.split(":");
            throw new ParseException(Integer.parseInt(lineColumn[0]), Integer.parseInt(lineColumn[1]), msg.toString());
        }
        
        public void merge(final CharSequence input, final ExtensionRegistry extensionRegistry, final Message.Builder builder) throws ParseException {
            final Tokenizer tokenizer = new Tokenizer(input);
            final MessageReflection.BuilderAdapter target = new MessageReflection.BuilderAdapter(builder);
            final List<UnknownField> unknownFields = new ArrayList<UnknownField>();
            while (!tokenizer.atEnd()) {
                this.mergeField(tokenizer, extensionRegistry, target, unknownFields);
            }
            this.checkUnknownFields(unknownFields);
        }
        
        private void mergeField(final Tokenizer tokenizer, final ExtensionRegistry extensionRegistry, final MessageReflection.MergeTarget target, final List<UnknownField> unknownFields) throws ParseException {
            this.mergeField(tokenizer, extensionRegistry, target, this.parseInfoTreeBuilder, unknownFields);
        }
        
        private void mergeField(final Tokenizer tokenizer, final ExtensionRegistry extensionRegistry, final MessageReflection.MergeTarget target, final TextFormatParseInfoTree.Builder parseTreeBuilder, final List<UnknownField> unknownFields) throws ParseException {
            Descriptors.FieldDescriptor field = null;
            final int startLine = tokenizer.getLine();
            final int startColumn = tokenizer.getColumn();
            final Descriptors.Descriptor type = target.getDescriptorForType();
            ExtensionRegistry.ExtensionInfo extension = null;
            if (tokenizer.tryConsume("[")) {
                final StringBuilder name = new StringBuilder(tokenizer.consumeIdentifier());
                while (tokenizer.tryConsume(".")) {
                    name.append('.');
                    name.append(tokenizer.consumeIdentifier());
                }
                extension = target.findExtensionByName(extensionRegistry, name.toString());
                if (extension == null) {
                    final String message = tokenizer.getPreviousLine() + 1 + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + ".[" + (Object)name + "]";
                    unknownFields.add(new UnknownField(message, UnknownField.Type.EXTENSION));
                }
                else {
                    if (extension.descriptor.getContainingType() != type) {
                        throw tokenizer.parseExceptionPreviousToken("Extension \"" + (Object)name + "\" does not extend message type \"" + type.getFullName() + "\".");
                    }
                    field = extension.descriptor;
                }
                tokenizer.consume("]");
            }
            else {
                final String name2 = tokenizer.consumeIdentifier();
                field = type.findFieldByName(name2);
                if (field == null) {
                    final String lowerName = name2.toLowerCase(Locale.US);
                    field = type.findFieldByName(lowerName);
                    if (field != null && field.getType() != Descriptors.FieldDescriptor.Type.GROUP) {
                        field = null;
                    }
                }
                if (field != null && field.getType() == Descriptors.FieldDescriptor.Type.GROUP && !field.getMessageType().getName().equals(name2)) {
                    field = null;
                }
                if (field == null) {
                    final String message = tokenizer.getPreviousLine() + 1 + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + "." + name2;
                    unknownFields.add(new UnknownField(message, UnknownField.Type.FIELD));
                }
            }
            if (field == null) {
                if (tokenizer.tryConsume(":") && !tokenizer.lookingAt("{") && !tokenizer.lookingAt("<")) {
                    this.skipFieldValue(tokenizer);
                }
                else {
                    this.skipFieldMessage(tokenizer);
                }
                return;
            }
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                tokenizer.tryConsume(":");
                if (parseTreeBuilder != null) {
                    final TextFormatParseInfoTree.Builder childParseTreeBuilder = parseTreeBuilder.getBuilderForSubMessageField(field);
                    this.consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, childParseTreeBuilder, unknownFields);
                }
                else {
                    this.consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                }
            }
            else {
                tokenizer.consume(":");
                this.consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
            }
            if (parseTreeBuilder != null) {
                parseTreeBuilder.setLocation(field, TextFormatParseLocation.create(startLine, startColumn));
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(",");
            }
        }
        
        private void consumeFieldValues(final Tokenizer tokenizer, final ExtensionRegistry extensionRegistry, final MessageReflection.MergeTarget target, final Descriptors.FieldDescriptor field, final ExtensionRegistry.ExtensionInfo extension, final TextFormatParseInfoTree.Builder parseTreeBuilder, final List<UnknownField> unknownFields) throws ParseException {
            if (field.isRepeated() && tokenizer.tryConsume("[")) {
                if (!tokenizer.tryConsume("]")) {
                    while (true) {
                        this.consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
                        if (tokenizer.tryConsume("]")) {
                            break;
                        }
                        tokenizer.consume(",");
                    }
                }
            }
            else {
                this.consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields);
            }
        }
        
        private void consumeFieldValue(final Tokenizer tokenizer, final ExtensionRegistry extensionRegistry, final MessageReflection.MergeTarget target, final Descriptors.FieldDescriptor field, final ExtensionRegistry.ExtensionInfo extension, final TextFormatParseInfoTree.Builder parseTreeBuilder, final List<UnknownField> unknownFields) throws ParseException {
            if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && !field.isRepeated()) {
                if (target.hasField(field)) {
                    throw tokenizer.parseExceptionPreviousToken("Non-repeated field \"" + field.getFullName() + "\" cannot be overwritten.");
                }
                if (field.getContainingOneof() != null && target.hasOneof(field.getContainingOneof())) {
                    final Descriptors.OneofDescriptor oneof = field.getContainingOneof();
                    throw tokenizer.parseExceptionPreviousToken("Field \"" + field.getFullName() + "\" is specified along with field \"" + target.getOneofFieldDescriptor(oneof).getFullName() + "\", another member of oneof \"" + oneof.getName() + "\".");
                }
            }
            Object value = null;
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                String endToken;
                if (tokenizer.tryConsume("<")) {
                    endToken = ">";
                }
                else {
                    tokenizer.consume("{");
                    endToken = "}";
                }
                if (field.getMessageType().getFullName().equals("google.protobuf.Any") && tokenizer.tryConsume("[")) {
                    value = this.consumeAnyFieldValue(tokenizer, extensionRegistry, field, parseTreeBuilder, unknownFields);
                    tokenizer.consume(endToken);
                }
                else {
                    final Message defaultInstance = (extension == null) ? null : extension.defaultInstance;
                    final MessageReflection.MergeTarget subField = target.newMergeTargetForField(field, defaultInstance);
                    while (!tokenizer.tryConsume(endToken)) {
                        if (tokenizer.atEnd()) {
                            throw tokenizer.parseException("Expected \"" + endToken + "\".");
                        }
                        this.mergeField(tokenizer, extensionRegistry, subField, parseTreeBuilder, unknownFields);
                    }
                    value = subField.finish();
                }
            }
            else {
                switch (field.getType()) {
                    case INT32:
                    case SINT32:
                    case SFIXED32: {
                        value = tokenizer.consumeInt32();
                        break;
                    }
                    case INT64:
                    case SINT64:
                    case SFIXED64: {
                        value = tokenizer.consumeInt64();
                        break;
                    }
                    case UINT32:
                    case FIXED32: {
                        value = tokenizer.consumeUInt32();
                        break;
                    }
                    case UINT64:
                    case FIXED64: {
                        value = tokenizer.consumeUInt64();
                        break;
                    }
                    case FLOAT: {
                        value = tokenizer.consumeFloat();
                        break;
                    }
                    case DOUBLE: {
                        value = tokenizer.consumeDouble();
                        break;
                    }
                    case BOOL: {
                        value = tokenizer.consumeBoolean();
                        break;
                    }
                    case STRING: {
                        value = tokenizer.consumeString();
                        break;
                    }
                    case BYTES: {
                        value = tokenizer.consumeByteString();
                        break;
                    }
                    case ENUM: {
                        final Descriptors.EnumDescriptor enumType = field.getEnumType();
                        if (tokenizer.lookingAtInteger()) {
                            final int number = tokenizer.consumeInt32();
                            value = enumType.findValueByNumber(number);
                            if (value != null) {
                                break;
                            }
                            final String unknownValueMsg = "Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.';
                            if (this.allowUnknownEnumValues) {
                                TextFormat.logger.warning(unknownValueMsg);
                                return;
                            }
                            throw tokenizer.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.');
                        }
                        else {
                            final String id = tokenizer.consumeIdentifier();
                            value = enumType.findValueByName(id);
                            if (value != null) {
                                break;
                            }
                            final String unknownValueMsg = "Enum type \"" + enumType.getFullName() + "\" has no value named \"" + id + "\".";
                            if (this.allowUnknownEnumValues) {
                                TextFormat.logger.warning(unknownValueMsg);
                                return;
                            }
                            throw tokenizer.parseExceptionPreviousToken(unknownValueMsg);
                        }
                        break;
                    }
                    case MESSAGE:
                    case GROUP: {
                        throw new RuntimeException("Can't get here.");
                    }
                }
            }
            if (field.isRepeated()) {
                target.addRepeatedField(field, value);
            }
            else {
                target.setField(field, value);
            }
        }
        
        private Object consumeAnyFieldValue(final Tokenizer tokenizer, final ExtensionRegistry extensionRegistry, final Descriptors.FieldDescriptor field, final TextFormatParseInfoTree.Builder parseTreeBuilder, final List<UnknownField> unknownFields) throws ParseException {
            final StringBuilder typeUrlBuilder = new StringBuilder();
            while (true) {
                typeUrlBuilder.append(tokenizer.consumeIdentifier());
                if (tokenizer.tryConsume("]")) {
                    tokenizer.tryConsume(":");
                    String anyEndToken;
                    if (tokenizer.tryConsume("<")) {
                        anyEndToken = ">";
                    }
                    else {
                        tokenizer.consume("{");
                        anyEndToken = "}";
                    }
                    final String typeUrl = typeUrlBuilder.toString();
                    Descriptors.Descriptor contentType = null;
                    try {
                        contentType = this.typeRegistry.getDescriptorForTypeUrl(typeUrl);
                    }
                    catch (InvalidProtocolBufferException e) {
                        throw tokenizer.parseException("Invalid valid type URL. Found: " + typeUrl);
                    }
                    if (contentType == null) {
                        throw tokenizer.parseException("Unable to parse Any of type: " + typeUrl + ". Please make sure that the TypeRegistry contains the descriptors for the given types.");
                    }
                    final Message.Builder contentBuilder = DynamicMessage.getDefaultInstance(contentType).newBuilderForType();
                    final MessageReflection.BuilderAdapter contentTarget = new MessageReflection.BuilderAdapter(contentBuilder);
                    while (!tokenizer.tryConsume(anyEndToken)) {
                        this.mergeField(tokenizer, extensionRegistry, contentTarget, parseTreeBuilder, unknownFields);
                    }
                    final Descriptors.Descriptor anyDescriptor = field.getMessageType();
                    final Message.Builder anyBuilder = DynamicMessage.getDefaultInstance(anyDescriptor).newBuilderForType();
                    anyBuilder.setField(anyDescriptor.findFieldByName("type_url"), typeUrlBuilder.toString());
                    anyBuilder.setField(anyDescriptor.findFieldByName("value"), contentBuilder.build().toByteString());
                    return anyBuilder.build();
                }
                else if (tokenizer.tryConsume("/")) {
                    typeUrlBuilder.append("/");
                }
                else {
                    if (!tokenizer.tryConsume(".")) {
                        throw tokenizer.parseExceptionPreviousToken("Expected a valid type URL.");
                    }
                    typeUrlBuilder.append(".");
                }
            }
        }
        
        private void skipField(final Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsume("[")) {
                do {
                    tokenizer.consumeIdentifier();
                } while (tokenizer.tryConsume("."));
                tokenizer.consume("]");
            }
            else {
                tokenizer.consumeIdentifier();
            }
            if (tokenizer.tryConsume(":") && !tokenizer.lookingAt("<") && !tokenizer.lookingAt("{")) {
                this.skipFieldValue(tokenizer);
            }
            else {
                this.skipFieldMessage(tokenizer);
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(",");
            }
        }
        
        private void skipFieldMessage(final Tokenizer tokenizer) throws ParseException {
            String delimiter;
            if (tokenizer.tryConsume("<")) {
                delimiter = ">";
            }
            else {
                tokenizer.consume("{");
                delimiter = "}";
            }
            while (!tokenizer.lookingAt(">") && !tokenizer.lookingAt("}")) {
                this.skipField(tokenizer);
            }
            tokenizer.consume(delimiter);
        }
        
        private void skipFieldValue(final Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsumeString()) {
                while (tokenizer.tryConsumeString()) {}
                return;
            }
            if (!tokenizer.tryConsumeIdentifier() && !tokenizer.tryConsumeInt64() && !tokenizer.tryConsumeUInt64() && !tokenizer.tryConsumeDouble() && !tokenizer.tryConsumeFloat()) {
                throw tokenizer.parseException("Invalid field value: " + tokenizer.currentToken);
            }
        }
        
        public enum SingularOverwritePolicy
        {
            ALLOW_SINGULAR_OVERWRITES, 
            FORBID_SINGULAR_OVERWRITES;
        }
        
        public static class Builder
        {
            private boolean allowUnknownFields;
            private boolean allowUnknownEnumValues;
            private boolean allowUnknownExtensions;
            private SingularOverwritePolicy singularOverwritePolicy;
            private TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
            private TypeRegistry typeRegistry;
            
            public Builder() {
                this.allowUnknownFields = false;
                this.allowUnknownEnumValues = false;
                this.allowUnknownExtensions = false;
                this.singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;
                this.parseInfoTreeBuilder = null;
                this.typeRegistry = TypeRegistry.getEmptyTypeRegistry();
            }
            
            public Builder setTypeRegistry(final TypeRegistry typeRegistry) {
                this.typeRegistry = typeRegistry;
                return this;
            }
            
            public Builder setAllowUnknownFields(final boolean allowUnknownFields) {
                this.allowUnknownFields = allowUnknownFields;
                return this;
            }
            
            public Builder setAllowUnknownExtensions(final boolean allowUnknownExtensions) {
                this.allowUnknownExtensions = allowUnknownExtensions;
                return this;
            }
            
            public Builder setSingularOverwritePolicy(final SingularOverwritePolicy p) {
                this.singularOverwritePolicy = p;
                return this;
            }
            
            public Builder setParseInfoTreeBuilder(final TextFormatParseInfoTree.Builder parseInfoTreeBuilder) {
                this.parseInfoTreeBuilder = parseInfoTreeBuilder;
                return this;
            }
            
            public Parser build() {
                return new Parser(this.typeRegistry, this.allowUnknownFields, this.allowUnknownEnumValues, this.allowUnknownExtensions, this.singularOverwritePolicy, this.parseInfoTreeBuilder);
            }
        }
        
        static final class UnknownField
        {
            final String message;
            final Type type;
            
            UnknownField(final String message, final Type type) {
                this.message = message;
                this.type = type;
            }
            
            enum Type
            {
                FIELD, 
                EXTENSION;
            }
        }
    }
    
    public static class InvalidEscapeSequenceException extends IOException
    {
        private static final long serialVersionUID = -8164033650142593304L;
        
        InvalidEscapeSequenceException(final String description) {
            super(description);
        }
    }
}
