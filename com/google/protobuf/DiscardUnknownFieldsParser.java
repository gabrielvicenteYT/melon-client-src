package com.google.protobuf;

public final class DiscardUnknownFieldsParser
{
    public static final <T extends Message> Parser<T> wrap(final Parser<T> parser) {
        return new AbstractParser<T>() {
            @Override
            public T parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                try {
                    input.discardUnknownFields();
                    return parser.parsePartialFrom(input, extensionRegistry);
                }
                finally {
                    input.unsetDiscardUnknownFields();
                }
            }
        };
    }
    
    private DiscardUnknownFieldsParser() {
    }
}
