package com.velocitypackage.storage.datatypes;

class QueryTest
{
    
    @org.junit.jupiter.api.Test
    void query()
    {
        Query query = Query.create().select("*").from("table").where("lang=\"de\"");
        System.out.println(query);
    }
}