package com.velocitypackage.database.services;

import com.velocitypackage.database.materials.exceptions.DependencyException;

import java.sql.*;

public class MySQL implements Database
{
    private Connection connection;
    
    /**
     * @param host     hostname of MySQL instance
     * @param user     username, default -> "sa"
     * @param passwd   password, default -> "sa"
     * @throws DependencyException throws when the MySQL not installed
     */
    public MySQL(String host, String user, String passwd) throws DependencyException
    {
        try
        {
            String databaseURL = "jdbc:mysql://" + host + "/";
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
        Statement statement;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        statement.close();
        return resultSet;
    }
    
    @Deprecated
    @Override
    public ResultSet secureQuery(String query) throws SQLException
    {
        return query(query); // TODO: 21.06.22 query 
    }
    
    @Override
    public void close()
    {
        try
        {
            connection.close();
        } catch (SQLException ignore)
        {
        }
    }
}
