package com.velocitypackage.database.datatypes;

@SuppressWarnings("unused")
public class Query
{
    private final StringBuilder query;
    
    private Query(String query)
    {
        this.query = new StringBuilder(query);
    }
    
    /**
     * Create a new Query
     *
     * @return the new query
     */
    public static Query create()
    {
        return new Query("");
    }
    
    /**
     * Compiles and returns the query as String
     *
     * @return the query as string
     */
    public String getQuery()
    {
        return new String(query).trim() + ";";
    }
    
    /**
     * @return (this.getQuery)
     * @deprecated use *.getQuery()
     */
    @Deprecated
    @Override
    public String toString()
    {
        return getQuery();
    }
    
    /**
     * Append the query with s = String
     *
     * @param s the string
     * @return the object self
     */
    public Query s(String s)
    {
        query.append(s).append(" ");
        return this;
    }
    
    /**
     * Append the query with SELECT
     *
     * @return the object self
     */
    public Query select()
    {
        query.append("SELECT ");
        return this;
    }
    
    /**
     * Append the query with SELECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query select(String c)
    {
        query.append("SELECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISTINCT
     *
     * @return the object self
     */
    public Query distinct()
    {
        query.append("DISTINCT ");
        return this;
    }
    
    /**
     * Append the query with DISTINCT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query distinct(String c)
    {
        query.append("DISTINCT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ALL
     *
     * @return the object self
     */
    public Query all()
    {
        query.append("ALL ");
        return this;
    }
    
    /**
     * Append the query with ALL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query all(String c)
    {
        query.append("ALL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FROM
     *
     * @return the object self
     */
    public Query from()
    {
        query.append("FROM ");
        return this;
    }
    
    /**
     * Append the query with FROM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query from(String c)
    {
        query.append("FROM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WHERE
     *
     * @return the object self
     */
    public Query where()
    {
        query.append("WHERE ");
        return this;
    }
    
    /**
     * Append the query with WHERE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query where(String c)
    {
        query.append("WHERE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GROUP BY
     *
     * @return the object self
     */
    public Query group_by()
    {
        query.append("GROUP BY ");
        return this;
    }
    
    /**
     * Append the query with GROUP BY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query group_by(String c)
    {
        query.append("GROUP BY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HAVING
     *
     * @return the object self
     */
    public Query having()
    {
        query.append("HAVING ");
        return this;
    }
    
    /**
     * Append the query with HAVING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query having(String c)
    {
        query.append("HAVING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ORDER BY
     *
     * @return the object self
     */
    public Query order_by()
    {
        query.append("ORDER BY ");
        return this;
    }
    
    /**
     * Append the query with ORDER BY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query order_by(String c)
    {
        query.append("ORDER BY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LIMIT
     *
     * @return the object self
     */
    public Query limit()
    {
        query.append("LIMIT ");
        return this;
    }
    
    /**
     * Append the query with LIMIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query limit(String c)
    {
        query.append("LIMIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UPSERT INTO
     *
     * @return the object self
     */
    public Query upsert_into()
    {
        query.append("UPSERT INTO ");
        return this;
    }
    
    /**
     * Append the query with UPSERT INTO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query upsert_into(String c)
    {
        query.append("UPSERT INTO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DELETE
     *
     * @return the object self
     */
    public Query delete()
    {
        query.append("DELETE ");
        return this;
    }
    
    /**
     * Append the query with DELETE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query delete(String c)
    {
        query.append("DELETE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VIEW
     *
     * @return the object self
     */
    public Query view()
    {
        query.append("VIEW ");
        return this;
    }
    
    /**
     * Append the query with VIEW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query view(String c)
    {
        query.append("VIEW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TABLE
     *
     * @return the object self
     */
    public Query table()
    {
        query.append("TABLE ");
        return this;
    }
    
    /**
     * Append the query with TABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query table(String c)
    {
        query.append("TABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IF NOT EXIST
     *
     * @return the object self
     */
    public Query if_not_exist()
    {
        query.append("IF NOT EXIST ");
        return this;
    }
    
    /**
     * Append the query with IF NOT EXIST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query if_not_exist(String c)
    {
        query.append("IF NOT EXIST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPLIT ON
     *
     * @return the object self
     */
    public Query split_on()
    {
        query.append("SPLIT ON ");
        return this;
    }
    
    /**
     * Append the query with SPLIT ON statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query split_on(String c)
    {
        query.append("SPLIT ON ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DROP
     *
     * @return the object self
     */
    public Query drop()
    {
        query.append("DROP ");
        return this;
    }
    
    /**
     * Append the query with DROP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query drop(String c)
    {
        query.append("DROP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IF EXIST
     *
     * @return the object self
     */
    public Query if_exist()
    {
        query.append("IF EXIST ");
        return this;
    }
    
    /**
     * Append the query with IF EXIST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query if_exist(String c)
    {
        query.append("IF EXIST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ADD
     *
     * @return the object self
     */
    public Query add()
    {
        query.append("ADD ");
        return this;
    }
    
    /**
     * Append the query with ADD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query add(String c)
    {
        query.append("ADD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DROP COLUMN
     *
     * @return the object self
     */
    public Query drop_column()
    {
        query.append("DROP COLUMN ");
        return this;
    }
    
    /**
     * Append the query with DROP COLUMN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query drop_column(String c)
    {
        query.append("DROP COLUMN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SET
     *
     * @return the object self
     */
    public Query set()
    {
        query.append("SET ");
        return this;
    }
    
    /**
     * Append the query with SET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query set(String c)
    {
        query.append("SET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CREATE INDEX
     *
     * @return the object self
     */
    public Query create_index()
    {
        query.append("CREATE INDEX ");
        return this;
    }
    
    /**
     * Append the query with CREATE INDEX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query create_index(String c)
    {
        query.append("CREATE INDEX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ON
     *
     * @return the object self
     */
    public Query on()
    {
        query.append("ON ");
        return this;
    }
    
    /**
     * Append the query with ON statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query on(String c)
    {
        query.append("ON ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ASC
     *
     * @return the object self
     */
    public Query asc()
    {
        query.append("ASC ");
        return this;
    }
    
    /**
     * Append the query with ASC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query asc(String c)
    {
        query.append("ASC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DESC
     *
     * @return the object self
     */
    public Query desc()
    {
        query.append("DESC ");
        return this;
    }
    
    /**
     * Append the query with DESC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query desc(String c)
    {
        query.append("DESC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INCLUDE
     *
     * @return the object self
     */
    public Query include()
    {
        query.append("INCLUDE ");
        return this;
    }
    
    /**
     * Append the query with INCLUDE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query include(String c)
    {
        query.append("INCLUDE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DROP INDEX
     *
     * @return the object self
     */
    public Query drop_index()
    {
        query.append("DROP INDEX ");
        return this;
    }
    
    /**
     * Append the query with DROP INDEX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query drop_index(String c)
    {
        query.append("DROP INDEX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXPLAIN
     *
     * @return the object self
     */
    public Query explain()
    {
        query.append("EXPLAIN ");
        return this;
    }
    
    /**
     * Append the query with EXPLAIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query explain(String c)
    {
        query.append("EXPLAIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAIN
     *
     * @return the object self
     */
    public Query constrain()
    {
        query.append("CONSTRAIN ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constrain(String c)
    {
        query.append("CONSTRAIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRIMARY KEY
     *
     * @return the object self
     */
    public Query primary_key()
    {
        query.append("PRIMARY KEY ");
        return this;
    }
    
    /**
     * Append the query with PRIMARY KEY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query primary_key(String c)
    {
        query.append("PRIMARY KEY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOT
     *
     * @return the object self
     */
    public Query not()
    {
        query.append("NOT ");
        return this;
    }
    
    /**
     * Append the query with NOT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query not(String c)
    {
        query.append("NOT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NULL
     *
     * @return the object self
     */
    public Query null_()
    {
        query.append("NULL ");
        return this;
    }
    
    /**
     * Append the query with NULL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query null_(String c)
    {
        query.append("NULL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AS
     *
     * @return the object self
     */
    public Query as()
    {
        query.append("AS ");
        return this;
    }
    
    /**
     * Append the query with AS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query as(String c)
    {
        query.append("AS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NULLS
     *
     * @return the object self
     */
    public Query nulls()
    {
        query.append("NULLS ");
        return this;
    }
    
    /**
     * Append the query with NULLS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nulls(String c)
    {
        query.append("NULLS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FIRST
     *
     * @return the object self
     */
    public Query first()
    {
        query.append("FIRST ");
        return this;
    }
    
    /**
     * Append the query with FIRST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query first(String c)
    {
        query.append("FIRST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LAST
     *
     * @return the object self
     */
    public Query last()
    {
        query.append("LAST ");
        return this;
    }
    
    /**
     * Append the query with LAST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query last(String c)
    {
        query.append("LAST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OR
     *
     * @return the object self
     */
    public Query or()
    {
        query.append("OR ");
        return this;
    }
    
    /**
     * Append the query with OR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query or(String c)
    {
        query.append("OR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AND
     *
     * @return the object self
     */
    public Query and()
    {
        query.append("AND ");
        return this;
    }
    
    /**
     * Append the query with AND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query and(String c)
    {
        query.append("AND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BETWEEN
     *
     * @return the object self
     */
    public Query between()
    {
        query.append("BETWEEN ");
        return this;
    }
    
    /**
     * Append the query with BETWEEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query between(String c)
    {
        query.append("BETWEEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CASE
     *
     * @return the object self
     */
    public Query case_()
    {
        query.append("CASE ");
        return this;
    }
    
    /**
     * Append the query with CASE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query case_(String c)
    {
        query.append("CASE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WHEN
     *
     * @return the object self
     */
    public Query when()
    {
        query.append("WHEN ");
        return this;
    }
    
    /**
     * Append the query with WHEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query when(String c)
    {
        query.append("WHEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with THEN
     *
     * @return the object self
     */
    public Query then()
    {
        query.append("THEN ");
        return this;
    }
    
    /**
     * Append the query with THEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query then(String c)
    {
        query.append("THEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ELSE
     *
     * @return the object self
     */
    public Query else_()
    {
        query.append("ELSE ");
        return this;
    }
    
    /**
     * Append the query with ELSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query else_(String c)
    {
        query.append("ELSE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with END
     *
     * @return the object self
     */
    public Query end()
    {
        query.append("END ");
        return this;
    }
    
    /**
     * Append the query with END statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query end(String c)
    {
        query.append("END ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRUE
     *
     * @return the object self
     */
    public Query true_()
    {
        query.append("TRUE ");
        return this;
    }
    
    /**
     * Append the query with TRUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query true_(String c)
    {
        query.append("TRUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FALSE
     *
     * @return the object self
     */
    public Query false_()
    {
        query.append("FALSE ");
        return this;
    }
    
    /**
     * Append the query with FALSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query false_(String c)
    {
        query.append("FALSE ").append(c).append(" ");
        return this;
    }
    
}
