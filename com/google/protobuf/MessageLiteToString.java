package com.google.protobuf;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

final class MessageLiteToString
{
    private static final String LIST_SUFFIX = "List";
    private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final String MAP_SUFFIX = "Map";
    private static final String BYTES_SUFFIX = "Bytes";
    
    static String toString(final MessageLite messageLite, final String commentString) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("# ").append(commentString);
        reflectivePrintWithIndent(messageLite, buffer, 0);
        return buffer.toString();
    }
    
    private static void reflectivePrintWithIndent(final MessageLite messageLite, final StringBuilder buffer, final int indent) {
        final Map<String, Method> nameToNoArgMethod = new HashMap<String, Method>();
        final Map<String, Method> nameToMethod = new HashMap<String, Method>();
        final Set<String> getters = new TreeSet<String>();
        for (final Method method : messageLite.getClass().getDeclaredMethods()) {
            nameToMethod.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                nameToNoArgMethod.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    getters.add(method.getName());
                }
            }
        }
        for (final String getter : getters) {
            final String suffix = getter.startsWith("get") ? getter.substring(3) : getter;
            if (suffix.endsWith("List") && !suffix.endsWith("OrBuilderList") && !suffix.equals("List")) {
                final String camelCase = suffix.substring(0, 1).toLowerCase() + suffix.substring(1, suffix.length() - "List".length());
                final Method listMethod = nameToNoArgMethod.get(getter);
                if (listMethod != null && listMethod.getReturnType().equals(List.class)) {
                    printField(buffer, indent, camelCaseToSnakeCase(camelCase), GeneratedMessageLite.invokeOrDie(listMethod, messageLite, new Object[0]));
                    continue;
                }
            }
            if (suffix.endsWith("Map") && !suffix.equals("Map")) {
                final String camelCase = suffix.substring(0, 1).toLowerCase() + suffix.substring(1, suffix.length() - "Map".length());
                final Method mapMethod = nameToNoArgMethod.get(getter);
                if (mapMethod != null && mapMethod.getReturnType().equals(Map.class) && !mapMethod.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(mapMethod.getModifiers())) {
                    printField(buffer, indent, camelCaseToSnakeCase(camelCase), GeneratedMessageLite.invokeOrDie(mapMethod, messageLite, new Object[0]));
                    continue;
                }
            }
            final Method setter = nameToMethod.get("set" + suffix);
            if (setter == null) {
                continue;
            }
            if (suffix.endsWith("Bytes") && nameToNoArgMethod.containsKey("get" + suffix.substring(0, suffix.length() - "Bytes".length()))) {
                continue;
            }
            final String camelCase2 = suffix.substring(0, 1).toLowerCase() + suffix.substring(1);
            final Method getMethod = nameToNoArgMethod.get("get" + suffix);
            final Method hasMethod = nameToNoArgMethod.get("has" + suffix);
            if (getMethod == null) {
                continue;
            }
            final Object value = GeneratedMessageLite.invokeOrDie(getMethod, messageLite, new Object[0]);
            final boolean hasValue = (boolean)((hasMethod == null) ? (!isDefaultValue(value)) : GeneratedMessageLite.invokeOrDie(hasMethod, messageLite, new Object[0]));
            if (!hasValue) {
                continue;
            }
            printField(buffer, indent, camelCaseToSnakeCase(camelCase2), value);
        }
        if (messageLite instanceof GeneratedMessageLite.ExtendableMessage) {
            for (final Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> entry : ((GeneratedMessageLite.ExtendableMessage)messageLite).extensions) {
                printField(buffer, indent, "[" + entry.getKey().getNumber() + "]", entry.getValue());
            }
        }
        if (((GeneratedMessageLite)messageLite).unknownFields != null) {
            ((GeneratedMessageLite)messageLite).unknownFields.printWithIndent(buffer, indent);
        }
    }
    
    private static boolean isDefaultValue(final Object o) {
        if (o instanceof Boolean) {
            return !(boolean)o;
        }
        if (o instanceof Integer) {
            return (int)o == 0;
        }
        if (o instanceof Float) {
            return (float)o == 0.0f;
        }
        if (o instanceof Double) {
            return (double)o == 0.0;
        }
        if (o instanceof String) {
            return o.equals("");
        }
        if (o instanceof ByteString) {
            return o.equals(ByteString.EMPTY);
        }
        if (o instanceof MessageLite) {
            return o == ((MessageLite)o).getDefaultInstanceForType();
        }
        return o instanceof Enum && ((Enum)o).ordinal() == 0;
    }
    
    static final void printField(final StringBuilder buffer, final int indent, final String name, final Object object) {
        if (object instanceof List) {
            final List<?> list = (List<?>)object;
            for (final Object entry : list) {
                printField(buffer, indent, name, entry);
            }
            return;
        }
        if (object instanceof Map) {
            final Map<?, ?> map = (Map<?, ?>)object;
            for (final Map.Entry<?, ?> entry2 : map.entrySet()) {
                printField(buffer, indent, name, entry2);
            }
            return;
        }
        buffer.append('\n');
        for (int i = 0; i < indent; ++i) {
            buffer.append(' ');
        }
        buffer.append(name);
        if (object instanceof String) {
            buffer.append(": \"").append(TextFormatEscaper.escapeText((String)object)).append('\"');
        }
        else if (object instanceof ByteString) {
            buffer.append(": \"").append(TextFormatEscaper.escapeBytes((ByteString)object)).append('\"');
        }
        else if (object instanceof GeneratedMessageLite) {
            buffer.append(" {");
            reflectivePrintWithIndent((MessageLite)object, buffer, indent + 2);
            buffer.append("\n");
            for (int i = 0; i < indent; ++i) {
                buffer.append(' ');
            }
            buffer.append("}");
        }
        else if (object instanceof Map.Entry) {
            buffer.append(" {");
            final Map.Entry<?, ?> entry3 = (Map.Entry<?, ?>)object;
            printField(buffer, indent + 2, "key", entry3.getKey());
            printField(buffer, indent + 2, "value", entry3.getValue());
            buffer.append("\n");
            for (int j = 0; j < indent; ++j) {
                buffer.append(' ');
            }
            buffer.append("}");
        }
        else {
            buffer.append(": ").append(object.toString());
        }
    }
    
    private static final String camelCaseToSnakeCase(final String camelCase) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camelCase.length(); ++i) {
            final char ch = camelCase.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append("_");
            }
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }
}
