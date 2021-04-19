package com.google.protobuf;

import java.lang.reflect.*;

final class OneofInfo
{
    private final int id;
    private final Field caseField;
    private final Field valueField;
    
    public OneofInfo(final int id, final Field caseField, final Field valueField) {
        this.id = id;
        this.caseField = caseField;
        this.valueField = valueField;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Field getCaseField() {
        return this.caseField;
    }
    
    public Field getValueField() {
        return this.valueField;
    }
}
