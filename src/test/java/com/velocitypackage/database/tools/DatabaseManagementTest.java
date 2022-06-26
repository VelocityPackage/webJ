package com.velocitypackage.database.tools;

import com.velocitypackage.database.datatypes.Query;
import com.velocitypackage.database.services.DatabaseSystem;
import org.junit.jupiter.api.Test;

class DatabaseManagementTest
{
    
    @Test
    void MariaDB()
    {
        DatabaseManagement databaseManagement = new DatabaseManagement(DatabaseSystem.MARIADB, "192.168.178.13:7000", "root", "sa");
        databaseManagement.send(Query.template.createDatabase("Test"));
    }
}