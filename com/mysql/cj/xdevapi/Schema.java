package com.mysql.cj.xdevapi;

import java.util.*;

public interface Schema extends DatabaseObject
{
    List<Collection> getCollections();
    
    List<Collection> getCollections(final String p0);
    
    List<Table> getTables();
    
    List<Table> getTables(final String p0);
    
    Collection getCollection(final String p0);
    
    Collection getCollection(final String p0, final boolean p1);
    
    Table getCollectionAsTable(final String p0);
    
    Table getTable(final String p0);
    
    Table getTable(final String p0, final boolean p1);
    
    Collection createCollection(final String p0);
    
    Collection createCollection(final String p0, final boolean p1);
    
    Collection createCollection(final String p0, final CreateCollectionOptions p1);
    
    void modifyCollection(final String p0, final ModifyCollectionOptions p1);
    
    void dropCollection(final String p0);
    
    public static class CreateCollectionOptions
    {
        private Boolean reuseExisting;
        private Validation validation;
        
        public CreateCollectionOptions() {
            this.reuseExisting = null;
            this.validation = null;
        }
        
        public CreateCollectionOptions setReuseExisting(final boolean reuse) {
            this.reuseExisting = reuse;
            return this;
        }
        
        public Boolean getReuseExisting() {
            return this.reuseExisting;
        }
        
        public CreateCollectionOptions setValidation(final Validation validation) {
            this.validation = validation;
            return this;
        }
        
        public Validation getValidation() {
            return this.validation;
        }
    }
    
    public static class ModifyCollectionOptions
    {
        private Validation validation;
        
        public ModifyCollectionOptions() {
            this.validation = null;
        }
        
        public ModifyCollectionOptions setValidation(final Validation validation) {
            this.validation = validation;
            return this;
        }
        
        public Validation getValidation() {
            return this.validation;
        }
    }
    
    public static class Validation
    {
        private ValidationLevel level;
        private String schema;
        
        public Validation() {
            this.level = null;
            this.schema = null;
        }
        
        public Validation setLevel(final ValidationLevel level) {
            this.level = level;
            return this;
        }
        
        public ValidationLevel getLevel() {
            return this.level;
        }
        
        public Validation setSchema(final String schema) {
            this.schema = schema;
            return this;
        }
        
        public String getSchema() {
            return this.schema;
        }
        
        public enum ValidationLevel
        {
            STRICT, 
            OFF;
        }
    }
}
