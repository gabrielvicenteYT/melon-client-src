package com.mysql.cj.jdbc.exceptions;

import java.sql.*;
import com.mysql.cj.*;

public class NotUpdatable extends SQLException
{
    private static final long serialVersionUID = 6004153665887216929L;
    
    public NotUpdatable(final String reason) {
        super(reason + Messages.getString("NotUpdatable.1"), "S1000");
    }
}
