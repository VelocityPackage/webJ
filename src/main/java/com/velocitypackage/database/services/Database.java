package com.velocitypackage.database.services;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database
{
    
    /**
     * Sends a query to the SQL server.
     * When u. will use it without the possibility to inject query use recommend: (*.secureQuery(query:string))
     *
     * @param query the query as String
     * @return the return of the query
     * @throws SQLException throws if the SQL String is incorrect
     */
    ResultSet query(String query) throws SQLException;
    
    /**
     * Sends a query to the SQL server.
     * It is the way u. can save for injection
     *
     * @param query the query as String
     * @return the return of the query
     * @throws SQLException throws if the SQL String is incorrect
     * @deprecated it is deprecated, because it isn't tasted. Use: (*.query(query:string))
     */
    ResultSet secureQuery(String query) throws SQLException;
    
    /**
     * close the connection to the MariaDB instance
     */
    void close();
}
