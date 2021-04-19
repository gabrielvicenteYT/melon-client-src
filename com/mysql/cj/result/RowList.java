package com.mysql.cj.result;

import java.util.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;

public interface RowList extends Iterator<Row>
{
    public static final int RESULT_SET_SIZE_UNKNOWN = -1;
    
    default Row previous() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default Row get(final int n) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default int getPosition() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default int size() {
        return -1;
    }
}
