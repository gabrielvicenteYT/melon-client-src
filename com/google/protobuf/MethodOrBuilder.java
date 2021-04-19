package com.google.protobuf;

import java.util.*;

public interface MethodOrBuilder extends MessageOrBuilder
{
    String getName();
    
    ByteString getNameBytes();
    
    String getRequestTypeUrl();
    
    ByteString getRequestTypeUrlBytes();
    
    boolean getRequestStreaming();
    
    String getResponseTypeUrl();
    
    ByteString getResponseTypeUrlBytes();
    
    boolean getResponseStreaming();
    
    List<Option> getOptionsList();
    
    Option getOptions(final int p0);
    
    int getOptionsCount();
    
    List<? extends OptionOrBuilder> getOptionsOrBuilderList();
    
    OptionOrBuilder getOptionsOrBuilder(final int p0);
    
    int getSyntaxValue();
    
    Syntax getSyntax();
}
