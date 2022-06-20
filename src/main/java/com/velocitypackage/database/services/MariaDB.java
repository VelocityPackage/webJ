package com.velocitypackage.database.services;

import com.velocitypackage.database.materials.exceptions.DependencyException;

import java.sql.*;

/**
 * @author maxmielchen
 */
public final class MariaDB implements Database
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
     * When u. will use it without the possibility to inject query use recommend: (*.secureQuery(query:string))
     *
     * @param query the query as String
     * @return the return of the query
     * @throws SQLException throws if the SQL String is incorrect
     */
    @Override
    public ResultSet query(String query) throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        statement.close();
        return resultSet;
    }
    
    /**
     * Sends a query to the SQL server.
     * It is the way u. can save for injection
     *
     * @param query the query as String
     * @return the return of the query
     * @throws SQLException throws if the SQL String is incorrect
     * @deprecated it is deprecated, because it isn't tasted. Use: (*.query(query:string))
     */
    @Deprecated()
    @Override
    public ResultSet secureQuery(String query) throws SQLException
    {
        String begin = "BEGIN TRANSACTION;";
        String end = "COMMIT;";
        query = query.trim();
        if (! query.startsWith(begin))
        {
            query = begin + " " + query;
        }
        if (! query.endsWith(end))
        {
            query = query + " " + end;
        }
        return query(query);
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
