package com.google.protobuf;

final class Android
{
    private static final Class<?> MEMORY_CLASS;
    private static final boolean IS_ROBOLECTRIC;
    
    static boolean isOnAndroidDevice() {
        return Android.MEMORY_CLASS != null && !Android.IS_ROBOLECTRIC;
    }
    
    static Class<?> getMemoryClass() {
        return Android.MEMORY_CLASS;
    }
    
    private static <T> Class<T> getClassForName(final String name) {
        try {
            return (Class<T>)Class.forName(name);
        }
        catch (Throwable e) {
            return null;
        }
    }
    
    static {
        MEMORY_CLASS = getClassForName("libcore.io.Memory");
        IS_ROBOLECTRIC = (getClassForName("org.robolectric.Robolectric") != null);
    }
}
