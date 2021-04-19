package com.mysql.cj.jdbc.exceptions;

import java.sql.*;
import com.mysql.cj.exceptions.*;

public class MySQLTransactionRollbackException extends SQLTransactionRollbackException implements DeadlockTimeoutRollbackMarker
{
    static final long serialVersionUID = 6034999468737899730L;
    
    public MySQLTransactionRollbackException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLTransactionRollbackException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLTransactionRollbackException(final String reason) {
        super(reason);
    }
    
    public MySQLTransactionRollbackException() {
    }
}
