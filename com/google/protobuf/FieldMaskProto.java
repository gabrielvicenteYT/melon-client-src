package com.google.protobuf;

public final class FieldMaskProto
{
    static final Descriptors.Descriptor internal_static_google_protobuf_FieldMask_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_protobuf_FieldMask_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private FieldMaskProto() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return FieldMaskProto.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n google/protobuf/field_mask.proto\u0012\u000fgoogle.protobuf\"\u001a\n\tFieldMask\u0012\r\n\u0005paths\u0018\u0001 \u0003(\tB\u008c\u0001\n\u0013com.google.protobufB\u000eFieldMaskProtoP\u0001Z9google.golang.org/genproto/protobuf/field_mask;field_mask\u00f8\u0001\u0001¢\u0002\u0003GPBª\u0002\u001eGoogle.Protobuf.WellKnownTypesb\u0006proto3" };
        FieldMaskProto.descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_google_protobuf_FieldMask_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_google_protobuf_FieldMask_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(FieldMaskProto.internal_static_google_protobuf_FieldMask_descriptor, new String[] { "Paths" });
    }
}
