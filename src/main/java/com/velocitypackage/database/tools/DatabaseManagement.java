package com.velocitypackage.database.tools;

import com.velocitypackage.database.datatypes.Query;
import com.velocitypackage.database.materials.exceptions.DependencyException;
import com.velocitypackage.database.services.Database;
import com.velocitypackage.database.services.DatabaseSystem;
import com.velocitypackage.database.services.MariaDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManagement
{
    private Database database;
    
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
    
    public ResultSet send(Query query)
    {
        // TODO: 20.06.22 secureQuery
        try
        {
            return database.query(query.getQuery());
        } catch (SQLException e)
        {
            return null;
        }
    }
}
