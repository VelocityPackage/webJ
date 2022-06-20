package com.velocitypackage.storage.datatypes;

@SuppressWarnings("unused")
public class Query
{
    private final StringBuilder query;
    
    private Query(String query)
    {
        this.query = new StringBuilder(query);
    }
    
    public static Query create()
    {
        return new Query("");
    }
    
    public String getQuery()
    {
        return new String(query);
    }
    
    @Override
    public String toString()
    {
        return getQuery();
    }
    
    public Query s(String s)
    {
        query.append(s).append(" ");
        return this;
    }
    
    public Query select()
    {
        query.append("SELECT ");
        return this;
    }
    
    public Query select(String c)
    {
        query.append("SELECT ").append(c).append(" ");
        return this;
    }
    
    public Query distinct()
    {
        query.append("DISTINCT ");
        return this;
    }
    
    public Query distinct(String c)
    {
        query.append("DISTINCT ").append(c).append(" ");
        return this;
    }
    
    public Query all()
    {
        query.append("ALL ");
        return this;
    }
    
    public Query all(String c)
    {
        query.append("ALL ").append(c).append(" ");
        return this;
    }
    
    public Query from()
    {
        query.append("FROM ");
        return this;
    }
    
    public Query from(String c)
    {
        query.append("FROM ").append(c).append(" ");
        return this;
    }
    
    public Query where()
    {
        query.append("WHERE ");
        return this;
    }
    
    public Query where(String c)
    {
        query.append("WHERE ").append(c).append(" ");
        return this;
    }
    
    public Query group_by()
    {
        query.append("GROUP BY ");
        return this;
    }
    
    public Query group_by(String c)
    {
        query.append("GROUP BY ").append(c).append(" ");
        return this;
    }
    
    public Query having()
    {
        query.append("HAVING ");
        return this;
    }
    
    public Query having(String c)
    {
        query.append("HAVING ").append(c).append(" ");
        return this;
    }
    
    public Query order_by()
    {
        query.append("ORDER BY ");
        return this;
    }
    
    public Query order_by(String c)
    {
        query.append("ORDER BY ").append(c).append(" ");
        return this;
    }
    
    public Query limit()
    {
        query.append("LIMIT ");
        return this;
    }
    
    public Query limit(String c)
    {
        query.append("LIMIT ").append(c).append(" ");
        return this;
    }
    
    public Query upsert_into()
    {
        query.append("UPSERT INTO ");
        return this;
    }
    
    public Query upsert_into(String c)
    {
        query.append("UPSERT INTO ").append(c).append(" ");
        return this;
    }
    
    public Query delete()
    {
        query.append("DELETE ");
        return this;
    }
    
    public Query delete(String c)
    {
        query.append("DELETE ").append(c).append(" ");
        return this;
    }
    
    public Query view()
    {
        query.append("VIEW ");
        return this;
    }
    
    public Query view(String c)
    {
        query.append("VIEW ").append(c).append(" ");
        return this;
    }
    
    public Query table()
    {
        query.append("TABLE ");
        return this;
    }
    
    public Query table(String c)
    {
        query.append("TABLE ").append(c).append(" ");
        return this;
    }
    
    public Query if_not_exist()
    {
        query.append("IF NOT EXIST ");
        return this;
    }
    
    public Query if_not_exist(String c)
    {
        query.append("IF NOT EXIST ").append(c).append(" ");
        return this;
    }
    
    public Query split_on()
    {
        query.append("SPLIT ON ");
        return this;
    }
    
    public Query split_on(String c)
    {
        query.append("SPLIT ON ").append(c).append(" ");
        return this;
    }
    
    public Query drop()
    {
        query.append("DROP ");
        return this;
    }
    
    public Query drop(String c)
    {
        query.append("DROP ").append(c).append(" ");
        return this;
    }
    
    public Query if_exist()
    {
        query.append("IF EXIST ");
        return this;
    }
    
    public Query if_exist(String c)
    {
        query.append("IF EXIST ").append(c).append(" ");
        return this;
    }
    
    public Query add()
    {
        query.append("ADD ");
        return this;
    }
    
    public Query add(String c)
    {
        query.append("ADD ").append(c).append(" ");
        return this;
    }
    
    public Query drop_column()
    {
        query.append("DROP COLUMN ");
        return this;
    }
    
    public Query drop_column(String c)
    {
        query.append("DROP COLUMN ").append(c).append(" ");
        return this;
    }
    
    public Query set()
    {
        query.append("SET ");
        return this;
    }
    
    public Query set(String c)
    {
        query.append("SET ").append(c).append(" ");
        return this;
    }
    
    public Query create_index()
    {
        query.append("CREATE INDEX ");
        return this;
    }
    
    public Query create_index(String c)
    {
        query.append("CREATE INDEX ").append(c).append(" ");
        return this;
    }
    
    public Query on()
    {
        query.append("ON ");
        return this;
    }
    
    public Query on(String c)
    {
        query.append("ON ").append(c).append(" ");
        return this;
    }
    
    public Query asc()
    {
        query.append("ASC ");
        return this;
    }
    
    public Query asc(String c)
    {
        query.append("ASC ").append(c).append(" ");
        return this;
    }
    
    public Query desc()
    {
        query.append("DESC ");
        return this;
    }
    
    public Query desc(String c)
    {
        query.append("DESC ").append(c).append(" ");
        return this;
    }
    
    public Query include()
    {
        query.append("INCLUDE ");
        return this;
    }
    
    public Query include(String c)
    {
        query.append("INCLUDE ").append(c).append(" ");
        return this;
    }
    
    public Query drop_index()
    {
        query.append("DROP INDEX ");
        return this;
    }
    
    public Query drop_index(String c)
    {
        query.append("DROP INDEX ").append(c).append(" ");
        return this;
    }
    
    public Query explain()
    {
        query.append("EXPLAIN ");
        return this;
    }
    
    public Query explain(String c)
    {
        query.append("EXPLAIN ").append(c).append(" ");
        return this;
    }
    
    public Query constrain()
    {
        query.append("CONSTRAIN ");
        return this;
    }
    
    public Query constrain(String c)
    {
        query.append("CONSTRAIN ").append(c).append(" ");
        return this;
    }
    
    public Query primary_key()
    {
        query.append("PRIMARY KEY ");
        return this;
    }
    
    public Query primary_key(String c)
    {
        query.append("PRIMARY KEY ").append(c).append(" ");
        return this;
    }
    
    public Query not()
    {
        query.append("NOT ");
        return this;
    }
    
    public Query not(String c)
    {
        query.append("NOT ").append(c).append(" ");
        return this;
    }
    
    public Query null_()
    {
        query.append("NULL ");
        return this;
    }
    
    public Query null_(String c)
    {
        query.append("NULL ").append(c).append(" ");
        return this;
    }
    
    public Query as()
    {
        query.append("AS ");
        return this;
    }
    
    public Query as(String c)
    {
        query.append("AS ").append(c).append(" ");
        return this;
    }
    
    public Query nulls()
    {
        query.append("NULLS ");
        return this;
    }
    
    public Query nulls(String c)
    {
        query.append("NULLS ").append(c).append(" ");
        return this;
    }
    
    public Query first()
    {
        query.append("FIRST ");
        return this;
    }
    
    public Query first(String c)
    {
        query.append("FIRST ").append(c).append(" ");
        return this;
    }
    
    public Query last()
    {
        query.append("LAST ");
        return this;
    }
    
    public Query last(String c)
    {
        query.append("LAST ").append(c).append(" ");
        return this;
    }
    
    public Query or()
    {
        query.append("OR ");
        return this;
    }
    
    public Query or(String c)
    {
        query.append("OR ").append(c).append(" ");
        return this;
    }
    
    public Query and()
    {
        query.append("AND ");
        return this;
    }
    
    public Query and(String c)
    {
        query.append("AND ").append(c).append(" ");
        return this;
    }
    
    public Query between()
    {
        query.append("BETWEEN ");
        return this;
    }
    
    public Query between(String c)
    {
        query.append("BETWEEN ").append(c).append(" ");
        return this;
    }
    
    public Query case_()
    {
        query.append("CASE ");
        return this;
    }
    
    public Query case_(String c)
    {
        query.append("CASE ").append(c).append(" ");
        return this;
    }
    
    public Query when()
    {
        query.append("WHEN ");
        return this;
    }
    
    public Query when(String c)
    {
        query.append("WHEN ").append(c).append(" ");
        return this;
    }
    
    public Query then()
    {
        query.append("THEN ");
        return this;
    }
    
    public Query then(String c)
    {
        query.append("THEN ").append(c).append(" ");
        return this;
    }
    
    public Query else_()
    {
        query.append("ELSE ");
        return this;
    }
    
    public Query else_(String c)
    {
        query.append("ELSE ").append(c).append(" ");
        return this;
    }
    
    public Query end()
    {
        query.append("END ");
        return this;
    }
    
    public Query end(String c)
    {
        query.append("END ").append(c).append(" ");
        return this;
    }
    
    public Query true_()
    {
        query.append("TRUE ");
        return this;
    }
    
    public Query true_(String c)
    {
        query.append("TRUE ").append(c).append(" ");
        return this;
    }
    
    public Query false_()
    {
        query.append("FALSE ");
        return this;
    }
    
    public Query false_(String c)
    {
        query.append("FALSE ").append(c).append(" ");
        return this;
    }
    
}
