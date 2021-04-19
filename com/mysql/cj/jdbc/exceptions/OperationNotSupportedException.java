package com.mysql.cj.jdbc.exceptions;

import java.sql.*;
import com.mysql.cj.*;

public class OperationNotSupportedException extends SQLException
{
    static final long serialVersionUID = 474918612056813430L;
    
    public OperationNotSupportedException() {
        super(Messages.getString("RowDataDynamic.3"), "S1009");
    }
    
    public OperationNotSupportedException(final String message) {
        super(message, "S1009");
    }
}
