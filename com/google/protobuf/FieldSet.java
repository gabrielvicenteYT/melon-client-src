package com.google.protobuf;

import java.util.*;
import java.io.*;

final class FieldSet<T extends FieldDescriptorLite<T>>
{
    private static final int DEFAULT_FIELD_MAP_ARRAY_SIZE = 16;
    private final SmallSortedMap<T, Object> fields;
    private boolean isImmutable;
    private boolean hasLazyField;
    private static final FieldSet DEFAULT_INSTANCE;
    
    private FieldSet() {
        this.fields = SmallSortedMap.newFieldMap(16);
    }
    
    private FieldSet(final boolean dummy) {
        this(SmallSortedMap.newFieldMap(0));
        this.makeImmutable();
    }
    
    private FieldSet(final SmallSortedMap<T, Object> fields) {
        this.fields = fields;
        this.makeImmutable();
    }
    
    public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
        return new FieldSet<T>();
    }
    
    public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet() {
        return (FieldSet<T>)FieldSet.DEFAULT_INSTANCE;
    }
    
    public static <T extends FieldDescriptorLite<T>> Builder<T> newBuilder() {
        return new Builder<T>();
    }
    
    boolean isEmpty() {
        return this.fields.isEmpty();
    }
    
    public void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        this.fields.makeImmutable();
        this.isImmutable = true;
    }
    
    public boolean isImmutable() {
        return this.isImmutable;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldSet)) {
            return false;
        }
        final FieldSet<?> other = (FieldSet<?>)o;
        return this.fields.equals(other.fields);
    }
    
    @Override
    public int hashCode() {
        return this.fields.hashCode();
    }
    
    public FieldSet<T> clone() {
        final FieldSet<T> clone = newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            final Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            clone.setField(entry.getKey(), entry.getValue());
        }
        final Iterator<Map.Entry<T, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<T, Object> entry = iterator.next();
            clone.setField(entry.getKey(), entry.getValue());
        }
        clone.hasLazyField = this.hasLazyField;
        return clone;
    }
    
    public void clear() {
        this.fields.clear();
        this.hasLazyField = false;
    }
    
    public Map<T, Object> getAllFields() {
        if (this.hasLazyField) {
            final SmallSortedMap<T, Object> result = cloneAllFieldsMap(this.fields, false);
            if (this.fields.isImmutable()) {
                result.makeImmutable();
            }
            return result;
        }
        return this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap((Map<? extends T, ?>)this.fields);
    }
    
    private static <T extends FieldDescriptorLite<T>> SmallSortedMap<T, Object> cloneAllFieldsMap(final SmallSortedMap<T, Object> fields, final boolean copyList) {
        final SmallSortedMap<T, Object> result = SmallSortedMap.newFieldMap(16);
        for (int i = 0; i < fields.getNumArrayEntries(); ++i) {
            cloneFieldEntry(result, fields.getArrayEntryAt(i), copyList);
        }
        for (final Map.Entry<T, Object> entry : fields.getOverflowEntries()) {
            cloneFieldEntry(result, entry, copyList);
        }
        return result;
    }
    
    private static <T extends FieldDescriptorLite<T>> void cloneFieldEntry(final Map<T, Object> map, final Map.Entry<T, Object> entry, final boolean copyList) {
        final T key = entry.getKey();
        final Object value = entry.getValue();
        if (value instanceof LazyField) {
            map.put(key, ((LazyField)value).getValue());
        }
        else if (copyList && value instanceof List) {
            map.put(key, new ArrayList((Collection<?>)value));
        }
        else {
            map.put(key, value);
        }
    }
    
    public Iterator<Map.Entry<T, Object>> iterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator<T>(this.fields.entrySet().iterator());
        }
        return this.fields.entrySet().iterator();
    }
    
    Iterator<Map.Entry<T, Object>> descendingIterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator<T>(this.fields.descendingEntrySet().iterator());
        }
        return this.fields.descendingEntrySet().iterator();
    }
    
    public boolean hasField(final T descriptor) {
        if (descriptor.isRepeated()) {
            throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
        }
        return this.fields.get(descriptor) != null;
    }
    
    public Object getField(final T descriptor) {
        final Object o = this.fields.get(descriptor);
        if (o instanceof LazyField) {
            return ((LazyField)o).getValue();
        }
        return o;
    }
    
    public void setField(final T descriptor, Object value) {
        if (descriptor.isRepeated()) {
            if (!(value instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            final List newList = new ArrayList();
            newList.addAll((Collection)value);
            for (final Object element : newList) {
                this.verifyType(descriptor.getLiteType(), element);
            }
            value = newList;
        }
        else {
            this.verifyType(descriptor.getLiteType(), value);
        }
        if (value instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put(descriptor, value);
    }
    
    public void clearField(final T descriptor) {
        this.fields.remove(descriptor);
        if (this.fields.isEmpty()) {
            this.hasLazyField = false;
        }
    }
    
    public int getRepeatedFieldCount(final T descriptor) {
        if (!descriptor.isRepeated()) {
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        final Object value = this.getField(descriptor);
        if (value == null) {
            return 0;
        }
        return ((List)value).size();
    }
    
    public Object getRepeatedField(final T descriptor, final int index) {
        if (!descriptor.isRepeated()) {
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        final Object value = this.getField(descriptor);
        if (value == null) {
            throw new IndexOutOfBoundsException();
        }
        return ((List)value).get(index);
    }
    
    public void setRepeatedField(final T descriptor, final int index, final Object value) {
        if (!descriptor.isRepeated()) {
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        final Object list = this.getField(descriptor);
        if (list == null) {
            throw new IndexOutOfBoundsException();
        }
        this.verifyType(descriptor.getLiteType(), value);
        ((List)list).set(index, value);
    }
    
    public void addRepeatedField(final T descriptor, final Object value) {
        if (!descriptor.isRepeated()) {
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }
        this.verifyType(descriptor.getLiteType(), value);
        final Object existingValue = this.getField(descriptor);
        List<Object> list;
        if (existingValue == null) {
            list = new ArrayList<Object>();
            this.fields.put(descriptor, list);
        }
        else {
            list = (List<Object>)existingValue;
        }
        list.add(value);
    }
    
    private void verifyType(final WireFormat.FieldType type, final Object value) {
        if (!isValidType(type, value)) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }
    
    private static boolean isValidType(final WireFormat.FieldType type, final Object value) {
        Internal.checkNotNull(value);
        switch (type.getJavaType()) {
            case INT: {
                return value instanceof Integer;
            }
            case LONG: {
                return value instanceof Long;
            }
            case FLOAT: {
                return value instanceof Float;
            }
            case DOUBLE: {
                return value instanceof Double;
            }
            case BOOLEAN: {
                return value instanceof Boolean;
            }
            case STRING: {
                return value instanceof String;
            }
            case BYTE_STRING: {
                return value instanceof ByteString || value instanceof byte[];
            }
            case ENUM: {
                return value instanceof Integer || value instanceof Internal.EnumLite;
            }
            case MESSAGE: {
                return value instanceof MessageLite || value instanceof LazyField;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isInitialized() {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            if (!isInitialized(this.fields.getArrayEntryAt(i))) {
                return false;
            }
        }
        for (final Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            if (!isInitialized(entry)) {
                return false;
            }
        }
        return true;
    }
    
    private static <T extends FieldDescriptorLite<T>> boolean isInitialized(final Map.Entry<T, Object> entry) {
        final T descriptor = entry.getKey();
        if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (descriptor.isRepeated()) {
                for (final MessageLite element : entry.getValue()) {
                    if (!element.isInitialized()) {
                        return false;
                    }
                }
            }
            else {
                final Object value = entry.getValue();
                if (value instanceof MessageLite) {
                    if (!((MessageLite)value).isInitialized()) {
                        return false;
                    }
                }
                else {
                    if (value instanceof LazyField) {
                        return true;
                    }
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }
    
    static int getWireFormatForFieldType(final WireFormat.FieldType type, final boolean isPacked) {
        if (isPacked) {
            return 2;
        }
        return type.getWireType();
    }
    
    public void mergeFrom(final FieldSet<T> other) {
        for (int i = 0; i < other.fields.getNumArrayEntries(); ++i) {
            this.mergeFromField(other.fields.getArrayEntryAt(i));
        }
        for (final Map.Entry<T, Object> entry : other.fields.getOverflowEntries()) {
            this.mergeFromField(entry);
        }
    }
    
    private static Object cloneIfMutable(final Object value) {
        if (value instanceof byte[]) {
            final byte[] bytes = (byte[])value;
            final byte[] copy = new byte[bytes.length];
            System.arraycopy(bytes, 0, copy, 0, bytes.length);
            return copy;
        }
        return value;
    }
    
    private void mergeFromField(final Map.Entry<T, Object> entry) {
        final T descriptor = entry.getKey();
        Object otherValue = entry.getValue();
        if (otherValue instanceof LazyField) {
            otherValue = ((LazyField)otherValue).getValue();
        }
        if (descriptor.isRepeated()) {
            Object value = this.getField(descriptor);
            if (value == null) {
                value = new ArrayList();
            }
            for (final Object element : (List)otherValue) {
                ((List)value).add(cloneIfMutable(element));
            }
            this.fields.put(descriptor, value);
        }
        else if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            Object value = this.getField(descriptor);
            if (value == null) {
                this.fields.put(descriptor, cloneIfMutable(otherValue));
            }
            else {
                value = descriptor.internalMergeFrom(((MessageLite)value).toBuilder(), (MessageLite)otherValue).build();
                this.fields.put(descriptor, value);
            }
        }
        else {
            this.fields.put(descriptor, cloneIfMutable(otherValue));
        }
    }
    
    public static Object readPrimitiveField(final CodedInputStream input, final WireFormat.FieldType type, final boolean checkUtf8) throws IOException {
        if (checkUtf8) {
            return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.STRICT);
        }
        return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.LOOSE);
    }
    
    public void writeTo(final CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            final Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            writeField(entry.getKey(), entry.getValue(), output);
        }
        final Iterator<Map.Entry<T, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<T, Object> entry = iterator.next();
            writeField(entry.getKey(), entry.getValue(), output);
        }
    }
    
    public void writeMessageSetTo(final CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            this.writeMessageSetTo(this.fields.getArrayEntryAt(i), output);
        }
        for (final Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            this.writeMessageSetTo(entry, output);
        }
    }
    
    private void writeMessageSetTo(final Map.Entry<T, Object> entry, final CodedOutputStream output) throws IOException {
        final T descriptor = entry.getKey();
        if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !descriptor.isRepeated() && !descriptor.isPacked()) {
            Object value = entry.getValue();
            if (value instanceof LazyField) {
                value = ((LazyField)value).getValue();
            }
            output.writeMessageSetExtension(entry.getKey().getNumber(), (MessageLite)value);
        }
        else {
            writeField(descriptor, entry.getValue(), output);
        }
    }
    
    static void writeElement(final CodedOutputStream output, final WireFormat.FieldType type, final int number, final Object value) throws IOException {
        if (type == WireFormat.FieldType.GROUP) {
            output.writeGroup(number, (MessageLite)value);
        }
        else {
            output.writeTag(number, getWireFormatForFieldType(type, false));
            writeElementNoTag(output, type, value);
        }
    }
    
    static void writeElementNoTag(final CodedOutputStream output, final WireFormat.FieldType type, final Object value) throws IOException {
        switch (type) {
            case DOUBLE: {
                output.writeDoubleNoTag((double)value);
                break;
            }
            case FLOAT: {
                output.writeFloatNoTag((float)value);
                break;
            }
            case INT64: {
                output.writeInt64NoTag((long)value);
                break;
            }
            case UINT64: {
                output.writeUInt64NoTag((long)value);
                break;
            }
            case INT32: {
                output.writeInt32NoTag((int)value);
                break;
            }
            case FIXED64: {
                output.writeFixed64NoTag((long)value);
                break;
            }
            case FIXED32: {
                output.writeFixed32NoTag((int)value);
                break;
            }
            case BOOL: {
                output.writeBoolNoTag((boolean)value);
                break;
            }
            case GROUP: {
                output.writeGroupNoTag((MessageLite)value);
                break;
            }
            case MESSAGE: {
                output.writeMessageNoTag((MessageLite)value);
                break;
            }
            case STRING: {
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString)value);
                    break;
                }
                output.writeStringNoTag((String)value);
                break;
            }
            case BYTES: {
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString)value);
                    break;
                }
                output.writeByteArrayNoTag((byte[])value);
                break;
            }
            case UINT32: {
                output.writeUInt32NoTag((int)value);
                break;
            }
            case SFIXED32: {
                output.writeSFixed32NoTag((int)value);
                break;
            }
            case SFIXED64: {
                output.writeSFixed64NoTag((long)value);
                break;
            }
            case SINT32: {
                output.writeSInt32NoTag((int)value);
                break;
            }
            case SINT64: {
                output.writeSInt64NoTag((long)value);
                break;
            }
            case ENUM: {
                if (value instanceof Internal.EnumLite) {
                    output.writeEnumNoTag(((Internal.EnumLite)value).getNumber());
                    break;
                }
                output.writeEnumNoTag((int)value);
                break;
            }
        }
    }
    
    public static void writeField(final FieldDescriptorLite<?> descriptor, final Object value, final CodedOutputStream output) throws IOException {
        final WireFormat.FieldType type = descriptor.getLiteType();
        final int number = descriptor.getNumber();
        if (descriptor.isRepeated()) {
            final List<?> valueList = (List<?>)value;
            if (descriptor.isPacked()) {
                output.writeTag(number, 2);
                int dataSize = 0;
                for (final Object element : valueList) {
                    dataSize += computeElementSizeNoTag(type, element);
                }
                output.writeRawVarint32(dataSize);
                for (final Object element : valueList) {
                    writeElementNoTag(output, type, element);
                }
            }
            else {
                for (final Object element2 : valueList) {
                    writeElement(output, type, number, element2);
                }
            }
        }
        else if (value instanceof LazyField) {
            writeElement(output, type, number, ((LazyField)value).getValue());
        }
        else {
            writeElement(output, type, number, value);
        }
    }
    
    public int getSerializedSize() {
        int size = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            final Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            size += computeFieldSize(entry.getKey(), entry.getValue());
        }
        final Iterator<Map.Entry<T, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<T, Object> entry = iterator.next();
            size += computeFieldSize(entry.getKey(), entry.getValue());
        }
        return size;
    }
    
    public int getMessageSetSerializedSize() {
        int size = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            size += this.getMessageSetSerializedSize(this.fields.getArrayEntryAt(i));
        }
        for (final Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            size += this.getMessageSetSerializedSize(entry);
        }
        return size;
    }
    
    private int getMessageSetSerializedSize(final Map.Entry<T, Object> entry) {
        final T descriptor = entry.getKey();
        final Object value = entry.getValue();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated() || descriptor.isPacked()) {
            return computeFieldSize(descriptor, value);
        }
        if (value instanceof LazyField) {
            return CodedOutputStream.computeLazyFieldMessageSetExtensionSize(entry.getKey().getNumber(), (LazyFieldLite)value);
        }
        return CodedOutputStream.computeMessageSetExtensionSize(entry.getKey().getNumber(), (MessageLite)value);
    }
    
    static int computeElementSize(final WireFormat.FieldType type, final int number, final Object value) {
        int tagSize = CodedOutputStream.computeTagSize(number);
        if (type == WireFormat.FieldType.GROUP) {
            tagSize *= 2;
        }
        return tagSize + computeElementSizeNoTag(type, value);
    }
    
    static int computeElementSizeNoTag(final WireFormat.FieldType type, final Object value) {
        switch (type) {
            case DOUBLE: {
                return CodedOutputStream.computeDoubleSizeNoTag((double)value);
            }
            case FLOAT: {
                return CodedOutputStream.computeFloatSizeNoTag((float)value);
            }
            case INT64: {
                return CodedOutputStream.computeInt64SizeNoTag((long)value);
            }
            case UINT64: {
                return CodedOutputStream.computeUInt64SizeNoTag((long)value);
            }
            case INT32: {
                return CodedOutputStream.computeInt32SizeNoTag((int)value);
            }
            case FIXED64: {
                return CodedOutputStream.computeFixed64SizeNoTag((long)value);
            }
            case FIXED32: {
                return CodedOutputStream.computeFixed32SizeNoTag((int)value);
            }
            case BOOL: {
                return CodedOutputStream.computeBoolSizeNoTag((boolean)value);
            }
            case GROUP: {
                return CodedOutputStream.computeGroupSizeNoTag((MessageLite)value);
            }
            case BYTES: {
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
                }
                return CodedOutputStream.computeByteArraySizeNoTag((byte[])value);
            }
            case STRING: {
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
                }
                return CodedOutputStream.computeStringSizeNoTag((String)value);
            }
            case UINT32: {
                return CodedOutputStream.computeUInt32SizeNoTag((int)value);
            }
            case SFIXED32: {
                return CodedOutputStream.computeSFixed32SizeNoTag((int)value);
            }
            case SFIXED64: {
                return CodedOutputStream.computeSFixed64SizeNoTag((long)value);
            }
            case SINT32: {
                return CodedOutputStream.computeSInt32SizeNoTag((int)value);
            }
            case SINT64: {
                return CodedOutputStream.computeSInt64SizeNoTag((long)value);
            }
            case MESSAGE: {
                if (value instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite)value);
                }
                return CodedOutputStream.computeMessageSizeNoTag((MessageLite)value);
            }
            case ENUM: {
                if (value instanceof Internal.EnumLite) {
                    return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite)value).getNumber());
                }
                return CodedOutputStream.computeEnumSizeNoTag((int)value);
            }
            default: {
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
            }
        }
    }
    
    public static int computeFieldSize(final FieldDescriptorLite<?> descriptor, final Object value) {
        final WireFormat.FieldType type = descriptor.getLiteType();
        final int number = descriptor.getNumber();
        if (!descriptor.isRepeated()) {
            return computeElementSize(type, number, value);
        }
        if (descriptor.isPacked()) {
            int dataSize = 0;
            for (final Object element : (List)value) {
                dataSize += computeElementSizeNoTag(type, element);
            }
            return dataSize + CodedOutputStream.computeTagSize(number) + CodedOutputStream.computeRawVarint32Size(dataSize);
        }
        int size = 0;
        for (final Object element : (List)value) {
            size += computeElementSize(type, number, element);
        }
        return size;
    }
    
    static {
        DEFAULT_INSTANCE = new FieldSet(true);
    }
    
    static final class Builder<T extends FieldDescriptorLite<T>>
    {
        private SmallSortedMap<T, Object> fields;
        private boolean hasLazyField;
        private boolean isMutable;
        private boolean hasNestedBuilders;
        
        private Builder() {
            this(SmallSortedMap.newFieldMap(16));
        }
        
        private Builder(final SmallSortedMap<T, Object> fields) {
            this.fields = fields;
            this.isMutable = true;
        }
        
        public FieldSet<T> build() {
            if (this.fields.isEmpty()) {
                return FieldSet.emptySet();
            }
            this.isMutable = false;
            SmallSortedMap<T, Object> fieldsForBuild = this.fields;
            if (this.hasNestedBuilders) {
                fieldsForBuild = (SmallSortedMap<T, Object>)cloneAllFieldsMap((SmallSortedMap<FieldDescriptorLite, Object>)this.fields, false);
                replaceBuilders(fieldsForBuild);
            }
            final FieldSet<T> fieldSet = new FieldSet<T>(fieldsForBuild, null);
            ((FieldSet<FieldDescriptorLite>)fieldSet).hasLazyField = this.hasLazyField;
            return fieldSet;
        }
        
        private static <T extends FieldDescriptorLite<T>> void replaceBuilders(final SmallSortedMap<T, Object> fieldMap) {
            for (int i = 0; i < fieldMap.getNumArrayEntries(); ++i) {
                replaceBuilders(fieldMap.getArrayEntryAt(i));
            }
            for (final Map.Entry<T, Object> entry : fieldMap.getOverflowEntries()) {
                replaceBuilders(entry);
            }
        }
        
        private static <T extends FieldDescriptorLite<T>> void replaceBuilders(final Map.Entry<T, Object> entry) {
            entry.setValue(replaceBuilders(entry.getKey(), entry.getValue()));
        }
        
        private static <T extends FieldDescriptorLite<T>> Object replaceBuilders(final T descriptor, final Object value) {
            if (value == null) {
                return value;
            }
            if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE) {
                return value;
            }
            if (!descriptor.isRepeated()) {
                return replaceBuilder(value);
            }
            if (!(value instanceof List)) {
                throw new IllegalStateException("Repeated field should contains a List but actually contains type: " + value.getClass());
            }
            List<Object> list = (List<Object>)value;
            for (int i = 0; i < list.size(); ++i) {
                final Object oldElement = list.get(i);
                final Object newElement = replaceBuilder(oldElement);
                if (newElement != oldElement) {
                    if (list == value) {
                        list = new ArrayList<Object>(list);
                    }
                    list.set(i, newElement);
                }
            }
            return list;
        }
        
        private static Object replaceBuilder(final Object value) {
            return (value instanceof MessageLite.Builder) ? ((MessageLite.Builder)value).build() : value;
        }
        
        public static <T extends FieldDescriptorLite<T>> Builder<T> fromFieldSet(final FieldSet<T> fieldSet) {
            final Builder<T> builder = new Builder<T>(cloneAllFieldsMap(((FieldSet<FieldDescriptorLite>)fieldSet).fields, true));
            builder.hasLazyField = ((FieldSet<FieldDescriptorLite>)fieldSet).hasLazyField;
            return builder;
        }
        
        public Map<T, Object> getAllFields() {
            if (this.hasLazyField) {
                final SmallSortedMap<T, Object> result = (SmallSortedMap<T, Object>)cloneAllFieldsMap((SmallSortedMap<FieldDescriptorLite, Object>)this.fields, false);
                if (this.fields.isImmutable()) {
                    result.makeImmutable();
                }
                else {
                    replaceBuilders(result);
                }
                return result;
            }
            return this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap((Map<? extends T, ?>)this.fields);
        }
        
        public boolean hasField(final T descriptor) {
            if (descriptor.isRepeated()) {
                throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
            }
            return this.fields.get(descriptor) != null;
        }
        
        public Object getField(final T descriptor) {
            final Object value = this.getFieldAllowBuilders(descriptor);
            return replaceBuilders(descriptor, value);
        }
        
        Object getFieldAllowBuilders(final T descriptor) {
            final Object o = this.fields.get(descriptor);
            if (o instanceof LazyField) {
                return ((LazyField)o).getValue();
            }
            return o;
        }
        
        private void ensureIsMutable() {
            if (!this.isMutable) {
                this.fields = (SmallSortedMap<T, Object>)cloneAllFieldsMap((SmallSortedMap<FieldDescriptorLite, Object>)this.fields, true);
                this.isMutable = true;
            }
        }
        
        public void setField(final T descriptor, Object value) {
            this.ensureIsMutable();
            if (descriptor.isRepeated()) {
                if (!(value instanceof List)) {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
                final List newList = new ArrayList();
                newList.addAll((Collection)value);
                for (final Object element : newList) {
                    verifyType(descriptor.getLiteType(), element);
                    this.hasNestedBuilders = (this.hasNestedBuilders || element instanceof MessageLite.Builder);
                }
                value = newList;
            }
            else {
                verifyType(descriptor.getLiteType(), value);
            }
            if (value instanceof LazyField) {
                this.hasLazyField = true;
            }
            this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
            this.fields.put(descriptor, value);
        }
        
        public void clearField(final T descriptor) {
            this.ensureIsMutable();
            this.fields.remove(descriptor);
            if (this.fields.isEmpty()) {
                this.hasLazyField = false;
            }
        }
        
        public int getRepeatedFieldCount(final T descriptor) {
            if (!descriptor.isRepeated()) {
                throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
            }
            final Object value = this.getField(descriptor);
            if (value == null) {
                return 0;
            }
            return ((List)value).size();
        }
        
        public Object getRepeatedField(final T descriptor, final int index) {
            if (this.hasNestedBuilders) {
                this.ensureIsMutable();
            }
            final Object value = this.getRepeatedFieldAllowBuilders(descriptor, index);
            return replaceBuilder(value);
        }
        
        Object getRepeatedFieldAllowBuilders(final T descriptor, final int index) {
            if (!descriptor.isRepeated()) {
                throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
            }
            final Object value = this.getFieldAllowBuilders(descriptor);
            if (value == null) {
                throw new IndexOutOfBoundsException();
            }
            return ((List)value).get(index);
        }
        
        public void setRepeatedField(final T descriptor, final int index, final Object value) {
            this.ensureIsMutable();
            if (!descriptor.isRepeated()) {
                throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
            }
            this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
            final Object list = this.getField(descriptor);
            if (list == null) {
                throw new IndexOutOfBoundsException();
            }
            verifyType(descriptor.getLiteType(), value);
            ((List)list).set(index, value);
        }
        
        public void addRepeatedField(final T descriptor, final Object value) {
            this.ensureIsMutable();
            if (!descriptor.isRepeated()) {
                throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
            }
            this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
            verifyType(descriptor.getLiteType(), value);
            final Object existingValue = this.getField(descriptor);
            List<Object> list;
            if (existingValue == null) {
                list = new ArrayList<Object>();
                this.fields.put(descriptor, list);
            }
            else {
                list = (List<Object>)existingValue;
            }
            list.add(value);
        }
        
        private static void verifyType(final WireFormat.FieldType type, final Object value) {
            if (isValidType(type, value)) {
                return;
            }
            if (type.getJavaType() == WireFormat.JavaType.MESSAGE && value instanceof MessageLite.Builder) {
                return;
            }
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        
        public boolean isInitialized() {
            for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
                if (!isInitialized((Map.Entry<FieldDescriptorLite, Object>)this.fields.getArrayEntryAt(i))) {
                    return false;
                }
            }
            for (final Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
                if (!isInitialized((Map.Entry<FieldDescriptorLite, Object>)entry)) {
                    return false;
                }
            }
            return true;
        }
        
        public void mergeFrom(final FieldSet<T> other) {
            this.ensureIsMutable();
            for (int i = 0; i < ((FieldSet<FieldDescriptorLite>)other).fields.getNumArrayEntries(); ++i) {
                this.mergeFromField(((FieldSet<FieldDescriptorLite>)other).fields.getArrayEntryAt(i));
            }
            for (final Map.Entry<T, Object> entry : ((FieldSet<FieldDescriptorLite>)other).fields.getOverflowEntries()) {
                this.mergeFromField(entry);
            }
        }
        
        private void mergeFromField(final Map.Entry<T, Object> entry) {
            final T descriptor = entry.getKey();
            Object otherValue = entry.getValue();
            if (otherValue instanceof LazyField) {
                otherValue = ((LazyField)otherValue).getValue();
            }
            if (descriptor.isRepeated()) {
                Object value = this.getField(descriptor);
                if (value == null) {
                    value = new ArrayList();
                }
                for (final Object element : (List)otherValue) {
                    ((List)value).add(cloneIfMutable(element));
                }
                this.fields.put(descriptor, value);
            }
            else if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
                Object value = this.getField(descriptor);
                if (value == null) {
                    this.fields.put(descriptor, cloneIfMutable(otherValue));
                }
                else if (value instanceof MessageLite.Builder) {
                    descriptor.internalMergeFrom((MessageLite.Builder)value, (MessageLite)otherValue);
                }
                else {
                    value = descriptor.internalMergeFrom(((MessageLite)value).toBuilder(), (MessageLite)otherValue).build();
                    this.fields.put(descriptor, value);
                }
            }
            else {
                this.fields.put(descriptor, cloneIfMutable(otherValue));
            }
        }
    }
    
    public interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T>
    {
        int getNumber();
        
        WireFormat.FieldType getLiteType();
        
        WireFormat.JavaType getLiteJavaType();
        
        boolean isRepeated();
        
        boolean isPacked();
        
        Internal.EnumLiteMap<?> getEnumType();
        
        MessageLite.Builder internalMergeFrom(final MessageLite.Builder p0, final MessageLite p1);
    }
}
