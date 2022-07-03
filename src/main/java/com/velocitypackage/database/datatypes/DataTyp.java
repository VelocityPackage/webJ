package com.velocitypackage.database.datatypes;

/**
 * Interpretation of SQL column type
 * @author maxmielchen
 */
public enum DataTyp
{
    CHAR, VARCHAR, BINARY, VARBINARY, TINYBLOB, TINYTEXT, TEXT, BLOB, MEDIUMTEXT, MEDIUMBLOB, LONGTEXT, LONGBLOB, BIT, TINYINT, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, INT, INTEGER, BIGINT, FLOAT, DOUBLE, DECIMAL, DEC, DATE, DATETIME, TIMESTAMP, TIME, YEAR;
    
    private Integer size = null;
    
    /**
     * Returns the object self with object size
     *
     * @param size column size
     * @return the object self
     */
    public DataTyp setSize(Integer size)
    {
        this.size = size;
        return this;
    }
    
    /**
     * Get the column size
     * @return size as integer
     */
    public Integer getSize()
    {
        return size;
    }
    
    @Override
    public String toString()
    {
        return this.name().trim().toLowerCase();
    }
}
