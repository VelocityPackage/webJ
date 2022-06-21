package com.velocitypackage.database.datatypes;

public enum DataTyp
{
    CHAR, VARCHAR, BINARY, VARBINARY, TINYBLOB, TINYTEXT, TEXT, BLOB, MEDIUMTEXT, MEDIUMBLOB, LONGTEXT, LONGBLOB, BIT, TINYINT, BOOL, BOOLEAN, SMALLINT, MEDIUMINT, INT, INTEGER, BIGINT, FLOAT, DOUBLE, DECIMAL, DEC, DATE, DATETIME, TIMESTAMP, TIME, YEAR;
    
    private Integer size = null;
    
    public DataTyp setSize(Integer size)
    {
        this.size = size;
        return this;
    }
    
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
