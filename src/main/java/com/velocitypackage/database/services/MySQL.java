package com.velocitypackage.database.services;

import com.velocitypackage.database.materials.exceptions.DependencyException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL implements Database
{
    private Connection connection;
    
    /**
     * @param host     hostname of MySQL instance
     * @param user     username, default -> "sa"
     * @param passwd   password, default -> "sa"
     * @param database database, default -> "sys"
     * @throws DependencyException throws when the MySQL not installed
     */
    public MySQL(String host, String user, String passwd, String database) throws DependencyException
    {
        try
        {
            String databaseURL = "jdbc:mysql://" + host + "/" + database;
            connection = DriverManager.getConnection(databaseURL, user, passwd);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            throw new DependencyException("\"jdbc:mysql\" not found...");
        }
    }
    
    @Override
    public ResultSet query(String query) throws SQLException
    {
        return null;
    }
    
    @Override
    public ResultSet secureQuery(String query) throws SQLException
    {
        return null;
    }
    
    @Override
    public void close()
    {
    
    }
}
