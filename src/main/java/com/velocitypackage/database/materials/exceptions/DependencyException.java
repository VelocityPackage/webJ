package com.velocitypackage.database.materials.exceptions;

/**
 * This exception tells someone that the specific SQL JDBC driver did not Exist
 *
 * @author maxmielchen
 */
public class DependencyException extends Exception
{
    /**
     * @param msg name or massage to the driver
     */
    public DependencyException(String msg)
    {
        super(msg, null);
    }
}
