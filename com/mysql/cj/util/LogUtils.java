package com.mysql.cj.util;

public class LogUtils
{
    public static final String CALLER_INFORMATION_NOT_AVAILABLE = "Caller information not available";
    private static final String LINE_SEPARATOR;
    private static final int LINE_SEPARATOR_LENGTH;
    
    public static String findCallingClassAndMethod(final Throwable t) {
        final String stackTraceAsString = Util.stackTraceToString(t);
        String callingClassAndMethod = "Caller information not available";
        final int endInternalMethods = Math.max(Math.max(stackTraceAsString.lastIndexOf("com.mysql.cj"), stackTraceAsString.lastIndexOf("com.mysql.cj.core")), stackTraceAsString.lastIndexOf("com.mysql.cj.jdbc"));
        if (endInternalMethods != -1) {
            final int endOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endInternalMethods);
            if (endOfLine != -1) {
                final int nextEndOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endOfLine + LogUtils.LINE_SEPARATOR_LENGTH);
                callingClassAndMethod = ((nextEndOfLine != -1) ? stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH, nextEndOfLine) : stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH));
            }
        }
        if (!callingClassAndMethod.startsWith("\tat ") && !callingClassAndMethod.startsWith("at ")) {
            return "at " + callingClassAndMethod;
        }
        return callingClassAndMethod;
    }
    
    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
        LINE_SEPARATOR_LENGTH = LogUtils.LINE_SEPARATOR.length();
    }
}
