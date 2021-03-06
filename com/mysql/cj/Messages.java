package com.mysql.cj;

import java.text.*;
import java.util.*;

public class Messages
{
    private static final String BUNDLE_NAME = "com.mysql.cj.LocalizedErrorMessages";
    private static final ResourceBundle RESOURCE_BUNDLE;
    private static final Object[] emptyObjectArray;
    
    public static String getString(final String key) {
        return getString(key, Messages.emptyObjectArray);
    }
    
    public static String getString(final String key, final Object[] args) {
        if (Messages.RESOURCE_BUNDLE == null) {
            throw new RuntimeException("Localized messages from resource bundle 'com.mysql.cj.LocalizedErrorMessages' not loaded during initialization of driver.");
        }
        try {
            if (key == null) {
                throw new IllegalArgumentException("Message key can not be null");
            }
            String message = Messages.RESOURCE_BUNDLE.getString(key);
            if (message == null) {
                message = "Missing error message for key '" + key + "'";
            }
            return MessageFormat.format(message, args);
        }
        catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    private Messages() {
    }
    
    static {
        emptyObjectArray = new Object[0];
        ResourceBundle temp = null;
        try {
            temp = ResourceBundle.getBundle("com.mysql.cj.LocalizedErrorMessages", Locale.getDefault(), Messages.class.getClassLoader());
        }
        catch (Throwable t) {
            try {
                temp = ResourceBundle.getBundle("com.mysql.cj.LocalizedErrorMessages");
            }
            catch (Throwable t2) {
                final RuntimeException rt = new RuntimeException("Can't load resource bundle due to underlying exception " + t.toString());
                rt.initCause(t2);
                throw rt;
            }
        }
        finally {
            RESOURCE_BUNDLE = temp;
        }
    }
}
