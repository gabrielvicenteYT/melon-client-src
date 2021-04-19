package com.google.protobuf;

import java.util.*;

public interface ApiOrBuilder extends MessageOrBuilder
{
    String getName();
    
    ByteString getNameBytes();
    
    List<Method> getMethodsList();
    
    Method getMethods(final int p0);
    
    int getMethodsCount();
    
    List<? extends MethodOrBuilder> getMethodsOrBuilderList();
    
    MethodOrBuilder getMethodsOrBuilder(final int p0);
    
    List<Option> getOptionsList();
    
    Option getOptions(final int p0);
    
    int getOptionsCount();
    
    List<? extends OptionOrBuilder> getOptionsOrBuilderList();
    
    OptionOrBuilder getOptionsOrBuilder(final int p0);
    
    String getVersion();
    
    ByteString getVersionBytes();
    
    boolean hasSourceContext();
    
    SourceContext getSourceContext();
    
    SourceContextOrBuilder getSourceContextOrBuilder();
    
    List<Mixin> getMixinsList();
    
    Mixin getMixins(final int p0);
    
    int getMixinsCount();
    
    List<? extends MixinOrBuilder> getMixinsOrBuilderList();
    
    MixinOrBuilder getMixinsOrBuilder(final int p0);
    
    int getSyntaxValue();
    
    Syntax getSyntax();
}
