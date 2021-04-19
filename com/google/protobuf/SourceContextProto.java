package com.google.protobuf;

public final class SourceContextProto
{
    static final Descriptors.Descriptor internal_static_google_protobuf_SourceContext_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_SourceContext_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private SourceContextProto() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return SourceContextProto.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n$google/protobuf/source_context.proto\u0012\u000fgoogle.protobuf\"\"\n\rSourceContext\u0012\u0011\n\tfile_name\u0018\u0001 \u0001(\tB\u0095\u0001\n\u0013com.google.protobufB\u0012SourceContextProtoP\u0001ZAgoogle.golang.org/genproto/protobuf/source_context;source_context¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3" };
        SourceContextProto.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_protobuf_SourceContext_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_google_protobuf_SourceContext_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor, new String[] { "FileName" });
    }
}
