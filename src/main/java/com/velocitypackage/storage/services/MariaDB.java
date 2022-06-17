package com.velocitypackage.storage.services;

import com.velocitypackage.storage.materials.exceptions.DependencyException;

import java.sql.*;

/**
 * @author maxmielchen
 */
public final class MariaDB
{
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private Connection connection;
    
    /**
     * @param host     hostname of MariaDB instance
     * @param user     username, default -> "sa"
     * @param passwd   password, default -> "sa"
     * @param database database, default -> "sys"
     * @throws DependencyException throws when the MariaDB not installed
     */
    public MariaDB(String host, String user, String passwd, String database) throws DependencyException
    {
        try
        {
            String databaseURL = "jdbc:mariadb://" + host + "/" + database;
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(databaseURL, user, passwd);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            throw new DependencyException("\"jdbc:mariadb\" not found...");
        }
    }
    
    /**
     * Sends a query to the SQL server.
     * Important is that query has around it a commit
     *
     * @param query the query as String
     * @return the return of the query
     * @throws SQLException throws if the SQL String is incorrect
     */
    public ResultSet query(String query) throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        statement.close();
        return resultSet;
    }
    
    /**
     * close the connection to the MariaDB instance
     */
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
