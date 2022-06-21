package com.velocitypackage.database.datatypes;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class QueryTest
{
    
    @Test
    void createDatabase()
    {
        System.out.println(Query.template.createDatabase("db"));
    }
    
    @Test
    void createTable()
    {
        Map<String, DataTyp> datatypes = new HashMap<>();
        datatypes.put("bla1", DataTyp.VARCHAR.setSize(100));
        datatypes.put("bla2", DataTyp.VARCHAR.setSize(100));
        datatypes.put("bla3", DataTyp.VARCHAR.setSize(100));
        datatypes.put("bla4", DataTyp.VARCHAR.setSize(100));
        System.out.println(Query.template.createTable("db", "asdfasdf", datatypes));
    }
}