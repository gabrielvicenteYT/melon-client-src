package com.google.protobuf;

import java.util.*;

public interface TypeOrBuilder extends MessageOrBuilder
{
    String getName();
    
    ByteString getNameBytes();
    
    List<Field> getFieldsList();
    
    Field getFields(final int p0);
    
    int getFieldsCount();
    
    List<? extends FieldOrBuilder> getFieldsOrBuilderList();
    
    FieldOrBuilder getFieldsOrBuilder(final int p0);
    
    List<String> getOneofsList();
    
    int getOneofsCount();
    
    String getOneofs(final int p0);
    
    ByteString getOneofsBytes(final int p0);
    
    List<Option> getOptionsList();
    
    Option getOptions(final int p0);
    
    int getOptionsCount();
    
    List<? extends OptionOrBuilder> getOptionsOrBuilderList();
    
    OptionOrBuilder getOptionsOrBuilder(final int p0);
    
    boolean hasSourceContext();
    
    SourceContext getSourceContext();
    
    SourceContextOrBuilder getSourceContextOrBuilder();
    
    int getSyntaxValue();
    
    Syntax getSyntax();
}
