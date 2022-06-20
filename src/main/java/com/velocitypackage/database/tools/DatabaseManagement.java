package com.velocitypackage.database.tools;

import com.velocitypackage.database.materials.exceptions.DependencyException;
import com.velocitypackage.database.services.Database;
import com.velocitypackage.database.services.DatabaseSystem;
import com.velocitypackage.database.services.MariaDB;

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
                    database = new MariaDB(host, user, passwd, "sys");
                    break;
                default:
                    database = new MariaDB(host, user, passwd, "sys");
                    break;
            }
        } catch (DependencyException e)
        {
            e.printStackTrace();
        }
    }
    
    
}
