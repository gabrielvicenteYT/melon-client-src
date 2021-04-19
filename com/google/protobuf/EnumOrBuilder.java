package com.google.protobuf;

import java.util.*;

public interface EnumOrBuilder extends MessageOrBuilder
{
    String getName();
    
    ByteString getNameBytes();
    
    List<EnumValue> getEnumvalueList();
    
    EnumValue getEnumvalue(final int p0);
    
    int getEnumvalueCount();
    
    List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList();
    
    EnumValueOrBuilder getEnumvalueOrBuilder(final int p0);
    
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
