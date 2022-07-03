package com.velocitypackage.database.tools;

import com.velocitypackage.database.datatypes.Query;
import com.velocitypackage.database.materials.exceptions.DependencyException;
import com.velocitypackage.database.services.Database;
import com.velocitypackage.database.services.DatabaseSystem;
import com.velocitypackage.database.services.MariaDB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author maxmielchen
 */
public class DatabaseManagement
{
    private Database database;
    
    /**
     * Connect to the database
     * @param system database system options:(mariadb)
     * @param host host address with port
     * @param user username default sa
     * @param passwd password default sa
     */
    public DatabaseManagement(DatabaseSystem system, String host, String user, String passwd)
    {
        try
        {
            switch (system)
            {
                case MARIADB:
                    database = new MariaDB(host, user, passwd);
                    break;
                default:
                    database = new Database()
                    {
                        @Override
                        public ResultSet query(String query)
                        {
                            return null;
                        }
                        
                        @Override
                        public ResultSet secureQuery(String query)
                        {
                            return null;
                        }
                        
                        @Override
                        public void close()
                        {
                        }
                    };
                    break;
            }
        } catch (DependencyException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Convert the query into a readable query and send it to the database server
     * @param query the query self (not null)
     * @return null = if the query is null || the result set
     */
    public ResultSet send(Query query)
    {
        if (query == null)
        {
            return null;
        }
        try
        {
            return database.query(query.getQuery());
        } catch (SQLException e)
        {
            return null;
        }
    }
}
