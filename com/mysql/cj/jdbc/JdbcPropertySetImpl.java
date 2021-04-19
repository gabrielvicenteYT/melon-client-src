package com.mysql.cj.jdbc;

import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.sql.*;
import com.mysql.cj.conf.*;

public class JdbcPropertySetImpl extends DefaultPropertySet implements JdbcPropertySet
{
    private static final long serialVersionUID = -8223499903182568260L;
    
    @Override
    public void postInitialization() {
        if (this.getIntegerProperty(PropertyKey.maxRows).getValue() == 0) {
            super.getProperty(PropertyKey.maxRows).setValue(-1, null);
        }
        final String testEncoding = this.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (testEncoding != null) {
            final String testString = "abc";
            StringUtils.getBytes(testString, testEncoding);
        }
        if (this.getBooleanProperty(PropertyKey.useCursorFetch).getValue()) {
            super.getProperty(PropertyKey.useServerPrepStmts).setValue(true);
        }
    }
    
    @Override
    public List<DriverPropertyInfo> exposeAsDriverPropertyInfo() throws SQLException {
        return PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.entrySet().stream().filter(e -> !e.getValue().getCategory().equals(PropertyDefinitions.CATEGORY_XDEVAPI)).map((Function<? super Object, ?>)Map.Entry::getKey).map((Function<? super Object, ?>)this::getProperty).map((Function<? super Object, ?>)this::getAsDriverPropertyInfo).collect((Collector<? super Object, ?, List<DriverPropertyInfo>>)Collectors.toList());
    }
    
    private DriverPropertyInfo getAsDriverPropertyInfo(final RuntimeProperty<?> pr) {
        final PropertyDefinition<?> pdef = pr.getPropertyDefinition();
        final DriverPropertyInfo dpi = new DriverPropertyInfo(pdef.getName(), null);
        dpi.choices = pdef.getAllowableValues();
        dpi.value = ((pr.getStringValue() != null) ? pr.getStringValue() : null);
        dpi.required = false;
        dpi.description = pdef.getDescription();
        return dpi;
    }
}
