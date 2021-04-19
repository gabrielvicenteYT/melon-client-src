package com.google.protobuf;

import java.util.*;

public class TextFormatParseInfoTree
{
    private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
    Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subtreesFromField;
    
    private TextFormatParseInfoTree(final Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField, final Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField) {
        final Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locs = new HashMap<Descriptors.FieldDescriptor, List<TextFormatParseLocation>>();
        for (final Map.Entry<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> kv : locationsFromField.entrySet()) {
            locs.put(kv.getKey(), Collections.unmodifiableList((List<? extends TextFormatParseLocation>)kv.getValue()));
        }
        this.locationsFromField = Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ? extends List<TextFormatParseLocation>>)locs);
        final Map<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>> subs = new HashMap<Descriptors.FieldDescriptor, List<TextFormatParseInfoTree>>();
        for (final Map.Entry<Descriptors.FieldDescriptor, List<Builder>> kv2 : subtreeBuildersFromField.entrySet()) {
            final List<TextFormatParseInfoTree> submessagesOfField = new ArrayList<TextFormatParseInfoTree>();
            for (final Builder subBuilder : kv2.getValue()) {
                submessagesOfField.add(subBuilder.build());
            }
            subs.put(kv2.getKey(), Collections.unmodifiableList((List<? extends TextFormatParseInfoTree>)submessagesOfField));
        }
        this.subtreesFromField = Collections.unmodifiableMap((Map<? extends Descriptors.FieldDescriptor, ? extends List<TextFormatParseInfoTree>>)subs);
    }
    
    public List<TextFormatParseLocation> getLocations(final Descriptors.FieldDescriptor fieldDescriptor) {
        final List<TextFormatParseLocation> result = this.locationsFromField.get(fieldDescriptor);
        return (result == null) ? Collections.emptyList() : result;
    }
    
    public TextFormatParseLocation getLocation(final Descriptors.FieldDescriptor fieldDescriptor, final int index) {
        return getFromList(this.getLocations(fieldDescriptor), index, fieldDescriptor);
    }
    
    public List<TextFormatParseInfoTree> getNestedTrees(final Descriptors.FieldDescriptor fieldDescriptor) {
        final List<TextFormatParseInfoTree> result = this.subtreesFromField.get(fieldDescriptor);
        return (result == null) ? Collections.emptyList() : result;
    }
    
    public TextFormatParseInfoTree getNestedTree(final Descriptors.FieldDescriptor fieldDescriptor, final int index) {
        return getFromList(this.getNestedTrees(fieldDescriptor), index, fieldDescriptor);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    private static <T> T getFromList(final List<T> list, final int index, final Descriptors.FieldDescriptor fieldDescriptor) {
        if (index >= list.size() || index < 0) {
            throw new IllegalArgumentException(String.format("Illegal index field: %s, index %d", (fieldDescriptor == null) ? "<null>" : fieldDescriptor.getName(), index));
        }
        return list.get(index);
    }
    
    public static class Builder
    {
        private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
        private Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField;
        
        private Builder() {
            this.locationsFromField = new HashMap<Descriptors.FieldDescriptor, List<TextFormatParseLocation>>();
            this.subtreeBuildersFromField = new HashMap<Descriptors.FieldDescriptor, List<Builder>>();
        }
        
        public Builder setLocation(final Descriptors.FieldDescriptor fieldDescriptor, final TextFormatParseLocation location) {
            List<TextFormatParseLocation> fieldLocations = this.locationsFromField.get(fieldDescriptor);
            if (fieldLocations == null) {
                fieldLocations = new ArrayList<TextFormatParseLocation>();
                this.locationsFromField.put(fieldDescriptor, fieldLocations);
            }
            fieldLocations.add(location);
            return this;
        }
        
        public Builder getBuilderForSubMessageField(final Descriptors.FieldDescriptor fieldDescriptor) {
            List<Builder> submessageBuilders = this.subtreeBuildersFromField.get(fieldDescriptor);
            if (submessageBuilders == null) {
                submessageBuilders = new ArrayList<Builder>();
                this.subtreeBuildersFromField.put(fieldDescriptor, submessageBuilders);
            }
            final Builder subtreeBuilder = new Builder();
            submessageBuilders.add(subtreeBuilder);
            return subtreeBuilder;
        }
        
        public TextFormatParseInfoTree build() {
            return new TextFormatParseInfoTree(this.locationsFromField, this.subtreeBuildersFromField, null);
        }
    }
}
