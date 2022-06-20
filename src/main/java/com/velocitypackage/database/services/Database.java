package com.velocitypackage.database.services;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database
{
    
    ResultSet query(String query) throws SQLException;
    
    ResultSet secureQuery(String query) throws SQLException;
    
    void close();
}
