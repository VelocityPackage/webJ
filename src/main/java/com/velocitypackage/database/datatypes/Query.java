package com.velocitypackage.database.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Query
{
    private final StringBuilder query;
    
    public Query()
    {
        this.query = new StringBuilder();
    }
    
    public Query(String query)
    {
        this.query = new StringBuilder(query);
    }
    
    //Builder
    
    public static class template
    {
    
        /**
         * Creates a query which can creates a new database
         *
         * @param database the name of the database as string
         * @return the query which can creates a new database
         */
        public static Query createDatabase(String database)
        {
            return new Query().create().database(createString(database));
        }
    
        /**
         * Creates a query which can create d new table in a database
         *
         * @param database the name of an existing database
         * @param table the name of the table
         * @param columns a hashmap of the columns. The entrys must look like (name of column, datatype.size(size)) its important for some datatypes to set the size
         * @return the query which can crates a new table
         */
        public static Query createTable(String database, String table, Map<String, DataTyp> columns)
        {
            Query query = new Query();
            query.create().table();
            query.s(createString(database) + "." + createString(table));
            query.s("(");
            for (Map.Entry<String, DataTyp> column : columns.entrySet())
            {
                DataTyp dataTyp = column.getValue();
                if (dataTyp.getSize() == null)
                {
                    query.s(createString(column.getKey())).s(dataTyp.toString()).null_().s(",");
                } else
                {
                    query.s(createString(column.getKey())).s(dataTyp + "(" + dataTyp.getSize() + ")").null_().s(",");
                }
            }
            query.s("id").s(DataTyp.INT.toString()).auto_increment().not().null_().s(",");
            query.primary().key().s("(id)");
            query.s(")");
            query.s("ENGINE=InnoDB");
            query.default_("CHARSET=utf8mb4");
            query.s("COLLATE=utf8mb4_general_ci");
            return query;
        }
    
        /**
         * Insert in a spezific table a new dataset
         *
         * @param database the name of an existing database
         * @param table the name of an existing table
         * @param data a hashmap of the columns. The entrys must look like (name of column, value)
         * @return the query which can insert data in a table
         */
        public static Query insertData(String database, String table, Map<String, String> data)
        {
            Query query = new Query();
            query.insert().into();
            query.s(createString(database) + "." + createString(table));
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for (Map.Entry<String, String> entry : data.entrySet())
            {
                columns.add(entry.getKey());
                values.add(entry.getValue());
            }
            StringBuilder columnsAsString = new StringBuilder();
            columnsAsString.append("(");
            for (String column : columns)
            {
                columnsAsString.append(column).append(",");
            }
            columnsAsString.deleteCharAt(columnsAsString.length() - 1);
            columnsAsString.append(")");
            StringBuilder valuesAsString = new StringBuilder();
            valuesAsString.append("(");
            for (String value : values)
            {
                valuesAsString.append(createString(value)).append(",");
            }
            valuesAsString.deleteCharAt(valuesAsString.length() - 1);
            valuesAsString.append(")");
            query.s(new String(columnsAsString)).values(new String(valuesAsString));
            return query;
        }
    
        /**
         * Create a query which can update an existing dataset
         *
         * @param database name of existing database
         * @param table name of existing table
         * @param where this entry must look like (name of column, value) its important to know in which row the new data must update
         * @param data the new data. The data must look like (name of column, value)
         * @return returns the query which can update a dataset
         */
        public static Query updateData(String database, String table, Map.Entry<String, String> where, Map<String, String> data)
        {
            Query query = new Query();
            query.update();
            query.s(createString(database) + "." + createString(table));
            StringBuilder newValues = new StringBuilder();
            for (Map.Entry<String, String> entry : data.entrySet())
            {
                newValues.append(entry.getKey()).append("=").append(createString(entry.getValue())).append(",");
            }
            newValues.deleteCharAt(newValues.length() - 1);
            query.set(new String(newValues)).where(where.getKey() + "=" + where.getValue());
            return query;
        }
    
        /**
         * Creates a new query which delete a dataset
         *
         * @param database name of existing database
         * @param table name of existing table
         * @param where this entry must look like (name of column, value) its important to know which row must be delete
         * @return returns the query which can delete a dataset
         */
        public static Query deleteData(String database, String table, Map.Entry<String, String> where)
        {
            Query query = new Query();
            query.delete().from();
            query.s(createString(database) + "." + createString(table));
            query.where(where.getKey() + "=" + where.getValue());
            return query;
        }
    
        /**
         * Create a new query which can select all of an table
         *
         * @param database name of an existing database
         * @param table name of existing table
         * @return returns the query which can select all of a table
         */
        public static Query selectAll(String database, String table)
        {
            Query query = new Query();
            query.select("*").from(createString(database) + "." + createString(table));
            return query;
        }
    
        /**
         * Create a new query which can select all of an table
         *
         * @param database name of an existing database
         * @param table name of existing table
         * @param where this entry must look like (name of column, value) its important to know which row must be select
         * @return returns the query which can select all of a table
         */
        public static Query selectAllWhere(String database, String table, Map.Entry<String, String> where)
        {
            Query query = new Query();
            query.select("*").from(createString(database) + "." + createString(table)).where(where.getKey() + "=" + where.getValue());
            return query;
        }
    }
    
    //Builder
    
    /**
     * Creates a Query_String with safe components like `*`
     *
     * @param string Query_String
     * @return Query_String
     */
    public static String createString(String string)
    {
        return "`" + string + "`";
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
     * Append the query with A
     *
     * @return the object self
     */
    public Query a()
    {
        query.append("A ");
        return this;
    }
    
    /**
     * Append the query with A statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query a(String c)
    {
        query.append("A ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ABORT
     *
     * @return the object self
     */
    public Query abort()
    {
        query.append("ABORT ");
        return this;
    }
    
    /**
     * Append the query with ABORT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query abort(String c)
    {
        query.append("ABORT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ABS
     *
     * @return the object self
     */
    public Query abs()
    {
        query.append("ABS ");
        return this;
    }
    
    /**
     * Append the query with ABS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query abs(String c)
    {
        query.append("ABS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ABSOLUTE
     *
     * @return the object self
     */
    public Query absolute()
    {
        query.append("ABSOLUTE ");
        return this;
    }
    
    /**
     * Append the query with ABSOLUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query absolute(String c)
    {
        query.append("ABSOLUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ACCESS
     *
     * @return the object self
     */
    public Query access()
    {
        query.append("ACCESS ");
        return this;
    }
    
    /**
     * Append the query with ACCESS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query access(String c)
    {
        query.append("ACCESS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ACTION
     *
     * @return the object self
     */
    public Query action()
    {
        query.append("ACTION ");
        return this;
    }
    
    /**
     * Append the query with ACTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query action(String c)
    {
        query.append("ACTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ADA
     *
     * @return the object self
     */
    public Query ada()
    {
        query.append("ADA ");
        return this;
    }
    
    /**
     * Append the query with ADA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ada(String c)
    {
        query.append("ADA ").append(c).append(" ");
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
     * Append the query with ADMIN
     *
     * @return the object self
     */
    public Query admin()
    {
        query.append("ADMIN ");
        return this;
    }
    
    /**
     * Append the query with ADMIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query admin(String c)
    {
        query.append("ADMIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AFTER
     *
     * @return the object self
     */
    public Query after()
    {
        query.append("AFTER ");
        return this;
    }
    
    /**
     * Append the query with AFTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query after(String c)
    {
        query.append("AFTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AGGREGATE
     *
     * @return the object self
     */
    public Query aggregate()
    {
        query.append("AGGREGATE ");
        return this;
    }
    
    /**
     * Append the query with AGGREGATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query aggregate(String c)
    {
        query.append("AGGREGATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ALIAS
     *
     * @return the object self
     */
    public Query alias()
    {
        query.append("ALIAS ");
        return this;
    }
    
    /**
     * Append the query with ALIAS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query alias(String c)
    {
        query.append("ALIAS ").append(c).append(" ");
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
     * Append the query with ALLOCATE
     *
     * @return the object self
     */
    public Query allocate()
    {
        query.append("ALLOCATE ");
        return this;
    }
    
    /**
     * Append the query with ALLOCATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query allocate(String c)
    {
        query.append("ALLOCATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ALSO
     *
     * @return the object self
     */
    public Query also()
    {
        query.append("ALSO ");
        return this;
    }
    
    /**
     * Append the query with ALSO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query also(String c)
    {
        query.append("ALSO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ALTER
     *
     * @return the object self
     */
    public Query alter()
    {
        query.append("ALTER ");
        return this;
    }
    
    /**
     * Append the query with ALTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query alter(String c)
    {
        query.append("ALTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ALWAYS
     *
     * @return the object self
     */
    public Query always()
    {
        query.append("ALWAYS ");
        return this;
    }
    
    /**
     * Append the query with ALWAYS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query always(String c)
    {
        query.append("ALWAYS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ANALYSE
     *
     * @return the object self
     */
    public Query analyse()
    {
        query.append("ANALYSE ");
        return this;
    }
    
    /**
     * Append the query with ANALYSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query analyse(String c)
    {
        query.append("ANALYSE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ANALYZE
     *
     * @return the object self
     */
    public Query analyze()
    {
        query.append("ANALYZE ");
        return this;
    }
    
    /**
     * Append the query with ANALYZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query analyze(String c)
    {
        query.append("ANALYZE ").append(c).append(" ");
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
     * Append the query with ANY
     *
     * @return the object self
     */
    public Query any()
    {
        query.append("ANY ");
        return this;
    }
    
    /**
     * Append the query with ANY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query any(String c)
    {
        query.append("ANY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ARE
     *
     * @return the object self
     */
    public Query are()
    {
        query.append("ARE ");
        return this;
    }
    
    /**
     * Append the query with ARE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query are(String c)
    {
        query.append("ARE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ARRAY
     *
     * @return the object self
     */
    public Query array()
    {
        query.append("ARRAY ");
        return this;
    }
    
    /**
     * Append the query with ARRAY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query array(String c)
    {
        query.append("ARRAY ").append(c).append(" ");
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
     * Append the query with ASENSITIVE
     *
     * @return the object self
     */
    public Query asensitive()
    {
        query.append("ASENSITIVE ");
        return this;
    }
    
    /**
     * Append the query with ASENSITIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query asensitive(String c)
    {
        query.append("ASENSITIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ASSERTION
     *
     * @return the object self
     */
    public Query assertion()
    {
        query.append("ASSERTION ");
        return this;
    }
    
    /**
     * Append the query with ASSERTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query assertion(String c)
    {
        query.append("ASSERTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ASSIGNMENT
     *
     * @return the object self
     */
    public Query assignment()
    {
        query.append("ASSIGNMENT ");
        return this;
    }
    
    /**
     * Append the query with ASSIGNMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query assignment(String c)
    {
        query.append("ASSIGNMENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ASYMMETRIC
     *
     * @return the object self
     */
    public Query asymmetric()
    {
        query.append("ASYMMETRIC ");
        return this;
    }
    
    /**
     * Append the query with ASYMMETRIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query asymmetric(String c)
    {
        query.append("ASYMMETRIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AT
     *
     * @return the object self
     */
    public Query at()
    {
        query.append("AT ");
        return this;
    }
    
    /**
     * Append the query with AT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query at(String c)
    {
        query.append("AT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ATOMIC
     *
     * @return the object self
     */
    public Query atomic()
    {
        query.append("ATOMIC ");
        return this;
    }
    
    /**
     * Append the query with ATOMIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query atomic(String c)
    {
        query.append("ATOMIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ATTRIBUTE
     *
     * @return the object self
     */
    public Query attribute()
    {
        query.append("ATTRIBUTE ");
        return this;
    }
    
    /**
     * Append the query with ATTRIBUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query attribute(String c)
    {
        query.append("ATTRIBUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ATTRIBUTES
     *
     * @return the object self
     */
    public Query attributes()
    {
        query.append("ATTRIBUTES ");
        return this;
    }
    
    /**
     * Append the query with ATTRIBUTES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query attributes(String c)
    {
        query.append("ATTRIBUTES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AUDIT
     *
     * @return the object self
     */
    public Query audit()
    {
        query.append("AUDIT ");
        return this;
    }
    
    /**
     * Append the query with AUDIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query audit(String c)
    {
        query.append("AUDIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AUTHORIZATION
     *
     * @return the object self
     */
    public Query authorization()
    {
        query.append("AUTHORIZATION ");
        return this;
    }
    
    /**
     * Append the query with AUTHORIZATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query authorization(String c)
    {
        query.append("AUTHORIZATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AUTO_INCREMENT
     *
     * @return the object self
     */
    public Query auto_increment()
    {
        query.append("AUTO_INCREMENT ");
        return this;
    }
    
    /**
     * Append the query with AUTO_INCREMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query auto_increment(String c)
    {
        query.append("AUTO_INCREMENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AVG
     *
     * @return the object self
     */
    public Query avg()
    {
        query.append("AVG ");
        return this;
    }
    
    /**
     * Append the query with AVG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query avg(String c)
    {
        query.append("AVG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with AVG_ROW_LENGTH
     *
     * @return the object self
     */
    public Query avg_row_length()
    {
        query.append("AVG_ROW_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with AVG_ROW_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query avg_row_length(String c)
    {
        query.append("AVG_ROW_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BACKUP
     *
     * @return the object self
     */
    public Query backup()
    {
        query.append("BACKUP ");
        return this;
    }
    
    /**
     * Append the query with BACKUP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query backup(String c)
    {
        query.append("BACKUP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BACKWARD
     *
     * @return the object self
     */
    public Query backward()
    {
        query.append("BACKWARD ");
        return this;
    }
    
    /**
     * Append the query with BACKWARD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query backward(String c)
    {
        query.append("BACKWARD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BEFORE
     *
     * @return the object self
     */
    public Query before()
    {
        query.append("BEFORE ");
        return this;
    }
    
    /**
     * Append the query with BEFORE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query before(String c)
    {
        query.append("BEFORE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BEGIN
     *
     * @return the object self
     */
    public Query begin()
    {
        query.append("BEGIN ");
        return this;
    }
    
    /**
     * Append the query with BEGIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query begin(String c)
    {
        query.append("BEGIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BERNOULLI
     *
     * @return the object self
     */
    public Query bernoulli()
    {
        query.append("BERNOULLI ");
        return this;
    }
    
    /**
     * Append the query with BERNOULLI statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bernoulli(String c)
    {
        query.append("BERNOULLI ").append(c).append(" ");
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
     * Append the query with BIGINT
     *
     * @return the object self
     */
    public Query bigint()
    {
        query.append("BIGINT ");
        return this;
    }
    
    /**
     * Append the query with BIGINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bigint(String c)
    {
        query.append("BIGINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BINARY
     *
     * @return the object self
     */
    public Query binary()
    {
        query.append("BINARY ");
        return this;
    }
    
    /**
     * Append the query with BINARY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query binary(String c)
    {
        query.append("BINARY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BIT
     *
     * @return the object self
     */
    public Query bit()
    {
        query.append("BIT ");
        return this;
    }
    
    /**
     * Append the query with BIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bit(String c)
    {
        query.append("BIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BIT_LENGTH
     *
     * @return the object self
     */
    public Query bit_length()
    {
        query.append("BIT_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with BIT_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bit_length(String c)
    {
        query.append("BIT_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BITVAR
     *
     * @return the object self
     */
    public Query bitvar()
    {
        query.append("BITVAR ");
        return this;
    }
    
    /**
     * Append the query with BITVAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bitvar(String c)
    {
        query.append("BITVAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BLOB
     *
     * @return the object self
     */
    public Query blob()
    {
        query.append("BLOB ");
        return this;
    }
    
    /**
     * Append the query with BLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query blob(String c)
    {
        query.append("BLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BOOL
     *
     * @return the object self
     */
    public Query bool()
    {
        query.append("BOOL ");
        return this;
    }
    
    /**
     * Append the query with BOOL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bool(String c)
    {
        query.append("BOOL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BOOLEAN
     *
     * @return the object self
     */
    public Query boolean_()
    {
        query.append("BOOLEAN ");
        return this;
    }
    
    /**
     * Append the query with BOOLEAN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query boolean_(String c)
    {
        query.append("BOOLEAN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BOTH
     *
     * @return the object self
     */
    public Query both()
    {
        query.append("BOTH ");
        return this;
    }
    
    /**
     * Append the query with BOTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query both(String c)
    {
        query.append("BOTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BREADTH
     *
     * @return the object self
     */
    public Query breadth()
    {
        query.append("BREADTH ");
        return this;
    }
    
    /**
     * Append the query with BREADTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query breadth(String c)
    {
        query.append("BREADTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BREAK
     *
     * @return the object self
     */
    public Query break_()
    {
        query.append("BREAK ");
        return this;
    }
    
    /**
     * Append the query with BREAK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query break_(String c)
    {
        query.append("BREAK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BROWSE
     *
     * @return the object self
     */
    public Query browse()
    {
        query.append("BROWSE ");
        return this;
    }
    
    /**
     * Append the query with BROWSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query browse(String c)
    {
        query.append("BROWSE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BULK
     *
     * @return the object self
     */
    public Query bulk()
    {
        query.append("BULK ");
        return this;
    }
    
    /**
     * Append the query with BULK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query bulk(String c)
    {
        query.append("BULK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with BY
     *
     * @return the object self
     */
    public Query by()
    {
        query.append("BY ");
        return this;
    }
    
    /**
     * Append the query with BY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query by(String c)
    {
        query.append("BY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with C
     *
     * @return the object self
     */
    public Query c()
    {
        query.append("C ");
        return this;
    }
    
    /**
     * Append the query with C statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query c(String c)
    {
        query.append("C ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CACHE
     *
     * @return the object self
     */
    public Query cache()
    {
        query.append("CACHE ");
        return this;
    }
    
    /**
     * Append the query with CACHE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cache(String c)
    {
        query.append("CACHE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CALL
     *
     * @return the object self
     */
    public Query call()
    {
        query.append("CALL ");
        return this;
    }
    
    /**
     * Append the query with CALL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query call(String c)
    {
        query.append("CALL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CALLED
     *
     * @return the object self
     */
    public Query called()
    {
        query.append("CALLED ");
        return this;
    }
    
    /**
     * Append the query with CALLED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query called(String c)
    {
        query.append("CALLED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CARDINALITY
     *
     * @return the object self
     */
    public Query cardinality()
    {
        query.append("CARDINALITY ");
        return this;
    }
    
    /**
     * Append the query with CARDINALITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cardinality(String c)
    {
        query.append("CARDINALITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CASCADE
     *
     * @return the object self
     */
    public Query cascade()
    {
        query.append("CASCADE ");
        return this;
    }
    
    /**
     * Append the query with CASCADE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cascade(String c)
    {
        query.append("CASCADE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CASCADED
     *
     * @return the object self
     */
    public Query cascaded()
    {
        query.append("CASCADED ");
        return this;
    }
    
    /**
     * Append the query with CASCADED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cascaded(String c)
    {
        query.append("CASCADED ").append(c).append(" ");
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
     * Append the query with CAST
     *
     * @return the object self
     */
    public Query cast()
    {
        query.append("CAST ");
        return this;
    }
    
    /**
     * Append the query with CAST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cast(String c)
    {
        query.append("CAST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CATALOG
     *
     * @return the object self
     */
    public Query catalog()
    {
        query.append("CATALOG ");
        return this;
    }
    
    /**
     * Append the query with CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query catalog(String c)
    {
        query.append("CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CATALOG_NAME
     *
     * @return the object self
     */
    public Query catalog_name()
    {
        query.append("CATALOG_NAME ");
        return this;
    }
    
    /**
     * Append the query with CATALOG_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query catalog_name(String c)
    {
        query.append("CATALOG_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CEIL
     *
     * @return the object self
     */
    public Query ceil()
    {
        query.append("CEIL ");
        return this;
    }
    
    /**
     * Append the query with CEIL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ceil(String c)
    {
        query.append("CEIL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CEILING
     *
     * @return the object self
     */
    public Query ceiling()
    {
        query.append("CEILING ");
        return this;
    }
    
    /**
     * Append the query with CEILING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ceiling(String c)
    {
        query.append("CEILING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHAIN
     *
     * @return the object self
     */
    public Query chain()
    {
        query.append("CHAIN ");
        return this;
    }
    
    /**
     * Append the query with CHAIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query chain(String c)
    {
        query.append("CHAIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHANGE
     *
     * @return the object self
     */
    public Query change()
    {
        query.append("CHANGE ");
        return this;
    }
    
    /**
     * Append the query with CHANGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query change(String c)
    {
        query.append("CHANGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHAR
     *
     * @return the object self
     */
    public Query char_()
    {
        query.append("CHAR ");
        return this;
    }
    
    /**
     * Append the query with CHAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query char_(String c)
    {
        query.append("CHAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHAR_LENGTH
     *
     * @return the object self
     */
    public Query char_length()
    {
        query.append("CHAR_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with CHAR_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query char_length(String c)
    {
        query.append("CHAR_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER
     *
     * @return the object self
     */
    public Query character()
    {
        query.append("CHARACTER ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query character(String c)
    {
        query.append("CHARACTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_LENGTH
     *
     * @return the object self
     */
    public Query character_length()
    {
        query.append("CHARACTER_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query character_length(String c)
    {
        query.append("CHARACTER_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_CATALOG
     *
     * @return the object self
     */
    public Query character_set_catalog()
    {
        query.append("CHARACTER_SET_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query character_set_catalog(String c)
    {
        query.append("CHARACTER_SET_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_NAME
     *
     * @return the object self
     */
    public Query character_set_name()
    {
        query.append("CHARACTER_SET_NAME ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query character_set_name(String c)
    {
        query.append("CHARACTER_SET_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_SCHEMA
     *
     * @return the object self
     */
    public Query character_set_schema()
    {
        query.append("CHARACTER_SET_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with CHARACTER_SET_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query character_set_schema(String c)
    {
        query.append("CHARACTER_SET_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTERISTICS
     *
     * @return the object self
     */
    public Query characteristics()
    {
        query.append("CHARACTERISTICS ");
        return this;
    }
    
    /**
     * Append the query with CHARACTERISTICS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query characteristics(String c)
    {
        query.append("CHARACTERISTICS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHARACTERS
     *
     * @return the object self
     */
    public Query characters()
    {
        query.append("CHARACTERS ");
        return this;
    }
    
    /**
     * Append the query with CHARACTERS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query characters(String c)
    {
        query.append("CHARACTERS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHECK
     *
     * @return the object self
     */
    public Query check()
    {
        query.append("CHECK ");
        return this;
    }
    
    /**
     * Append the query with CHECK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query check(String c)
    {
        query.append("CHECK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHECKED
     *
     * @return the object self
     */
    public Query checked()
    {
        query.append("CHECKED ");
        return this;
    }
    
    /**
     * Append the query with CHECKED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query checked(String c)
    {
        query.append("CHECKED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHECKPOINT
     *
     * @return the object self
     */
    public Query checkpoint()
    {
        query.append("CHECKPOINT ");
        return this;
    }
    
    /**
     * Append the query with CHECKPOINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query checkpoint(String c)
    {
        query.append("CHECKPOINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CHECKSUM
     *
     * @return the object self
     */
    public Query checksum()
    {
        query.append("CHECKSUM ");
        return this;
    }
    
    /**
     * Append the query with CHECKSUM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query checksum(String c)
    {
        query.append("CHECKSUM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLASS
     *
     * @return the object self
     */
    public Query class_()
    {
        query.append("CLASS ");
        return this;
    }
    
    /**
     * Append the query with CLASS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query class_(String c)
    {
        query.append("CLASS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLASS_ORIGIN
     *
     * @return the object self
     */
    public Query class_origin()
    {
        query.append("CLASS_ORIGIN ");
        return this;
    }
    
    /**
     * Append the query with CLASS_ORIGIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query class_origin(String c)
    {
        query.append("CLASS_ORIGIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLOB
     *
     * @return the object self
     */
    public Query clob()
    {
        query.append("CLOB ");
        return this;
    }
    
    /**
     * Append the query with CLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query clob(String c)
    {
        query.append("CLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLOSE
     *
     * @return the object self
     */
    public Query close()
    {
        query.append("CLOSE ");
        return this;
    }
    
    /**
     * Append the query with CLOSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query close(String c)
    {
        query.append("CLOSE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLUSTER
     *
     * @return the object self
     */
    public Query cluster()
    {
        query.append("CLUSTER ");
        return this;
    }
    
    /**
     * Append the query with CLUSTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cluster(String c)
    {
        query.append("CLUSTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CLUSTERED
     *
     * @return the object self
     */
    public Query clustered()
    {
        query.append("CLUSTERED ");
        return this;
    }
    
    /**
     * Append the query with CLUSTERED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query clustered(String c)
    {
        query.append("CLUSTERED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COALESCE
     *
     * @return the object self
     */
    public Query coalesce()
    {
        query.append("COALESCE ");
        return this;
    }
    
    /**
     * Append the query with COALESCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query coalesce(String c)
    {
        query.append("COALESCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COBOL
     *
     * @return the object self
     */
    public Query cobol()
    {
        query.append("COBOL ");
        return this;
    }
    
    /**
     * Append the query with COBOL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cobol(String c)
    {
        query.append("COBOL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLATE
     *
     * @return the object self
     */
    public Query collate()
    {
        query.append("COLLATE ");
        return this;
    }
    
    /**
     * Append the query with COLLATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collate(String c)
    {
        query.append("COLLATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLATION
     *
     * @return the object self
     */
    public Query collation()
    {
        query.append("COLLATION ");
        return this;
    }
    
    /**
     * Append the query with COLLATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collation(String c)
    {
        query.append("COLLATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_CATALOG
     *
     * @return the object self
     */
    public Query collation_catalog()
    {
        query.append("COLLATION_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collation_catalog(String c)
    {
        query.append("COLLATION_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_NAME
     *
     * @return the object self
     */
    public Query collation_name()
    {
        query.append("COLLATION_NAME ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collation_name(String c)
    {
        query.append("COLLATION_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_SCHEMA
     *
     * @return the object self
     */
    public Query collation_schema()
    {
        query.append("COLLATION_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with COLLATION_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collation_schema(String c)
    {
        query.append("COLLATION_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLLECT
     *
     * @return the object self
     */
    public Query collect()
    {
        query.append("COLLECT ");
        return this;
    }
    
    /**
     * Append the query with COLLECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query collect(String c)
    {
        query.append("COLLECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLUMN
     *
     * @return the object self
     */
    public Query column()
    {
        query.append("COLUMN ");
        return this;
    }
    
    /**
     * Append the query with COLUMN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query column(String c)
    {
        query.append("COLUMN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLUMN_NAME
     *
     * @return the object self
     */
    public Query column_name()
    {
        query.append("COLUMN_NAME ");
        return this;
    }
    
    /**
     * Append the query with COLUMN_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query column_name(String c)
    {
        query.append("COLUMN_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COLUMNS
     *
     * @return the object self
     */
    public Query columns()
    {
        query.append("COLUMNS ");
        return this;
    }
    
    /**
     * Append the query with COLUMNS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query columns(String c)
    {
        query.append("COLUMNS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMMAND_FUNCTION
     *
     * @return the object self
     */
    public Query command_function()
    {
        query.append("COMMAND_FUNCTION ");
        return this;
    }
    
    /**
     * Append the query with COMMAND_FUNCTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query command_function(String c)
    {
        query.append("COMMAND_FUNCTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMMAND_FUNCTION_CODE
     *
     * @return the object self
     */
    public Query command_function_code()
    {
        query.append("COMMAND_FUNCTION_CODE ");
        return this;
    }
    
    /**
     * Append the query with COMMAND_FUNCTION_CODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query command_function_code(String c)
    {
        query.append("COMMAND_FUNCTION_CODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMMENT
     *
     * @return the object self
     */
    public Query comment()
    {
        query.append("COMMENT ");
        return this;
    }
    
    /**
     * Append the query with COMMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query comment(String c)
    {
        query.append("COMMENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMMIT
     *
     * @return the object self
     */
    public Query commit()
    {
        query.append("COMMIT ");
        return this;
    }
    
    /**
     * Append the query with COMMIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query commit(String c)
    {
        query.append("COMMIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMMITTED
     *
     * @return the object self
     */
    public Query committed()
    {
        query.append("COMMITTED ");
        return this;
    }
    
    /**
     * Append the query with COMMITTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query committed(String c)
    {
        query.append("COMMITTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMPLETION
     *
     * @return the object self
     */
    public Query completion()
    {
        query.append("COMPLETION ");
        return this;
    }
    
    /**
     * Append the query with COMPLETION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query completion(String c)
    {
        query.append("COMPLETION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMPRESS
     *
     * @return the object self
     */
    public Query compress()
    {
        query.append("COMPRESS ");
        return this;
    }
    
    /**
     * Append the query with COMPRESS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query compress(String c)
    {
        query.append("COMPRESS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COMPUTE
     *
     * @return the object self
     */
    public Query compute()
    {
        query.append("COMPUTE ");
        return this;
    }
    
    /**
     * Append the query with COMPUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query compute(String c)
    {
        query.append("COMPUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONDITION
     *
     * @return the object self
     */
    public Query condition()
    {
        query.append("CONDITION ");
        return this;
    }
    
    /**
     * Append the query with CONDITION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query condition(String c)
    {
        query.append("CONDITION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONDITION_NUMBER
     *
     * @return the object self
     */
    public Query condition_number()
    {
        query.append("CONDITION_NUMBER ");
        return this;
    }
    
    /**
     * Append the query with CONDITION_NUMBER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query condition_number(String c)
    {
        query.append("CONDITION_NUMBER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONNECT
     *
     * @return the object self
     */
    public Query connect()
    {
        query.append("CONNECT ");
        return this;
    }
    
    /**
     * Append the query with CONNECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query connect(String c)
    {
        query.append("CONNECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONNECTION
     *
     * @return the object self
     */
    public Query connection()
    {
        query.append("CONNECTION ");
        return this;
    }
    
    /**
     * Append the query with CONNECTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query connection(String c)
    {
        query.append("CONNECTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONNECTION_NAME
     *
     * @return the object self
     */
    public Query connection_name()
    {
        query.append("CONNECTION_NAME ");
        return this;
    }
    
    /**
     * Append the query with CONNECTION_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query connection_name(String c)
    {
        query.append("CONNECTION_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT
     *
     * @return the object self
     */
    public Query constraint()
    {
        query.append("CONSTRAINT ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constraint(String c)
    {
        query.append("CONSTRAINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_CATALOG
     *
     * @return the object self
     */
    public Query constraint_catalog()
    {
        query.append("CONSTRAINT_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constraint_catalog(String c)
    {
        query.append("CONSTRAINT_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_NAME
     *
     * @return the object self
     */
    public Query constraint_name()
    {
        query.append("CONSTRAINT_NAME ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constraint_name(String c)
    {
        query.append("CONSTRAINT_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_SCHEMA
     *
     * @return the object self
     */
    public Query constraint_schema()
    {
        query.append("CONSTRAINT_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINT_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constraint_schema(String c)
    {
        query.append("CONSTRAINT_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINTS
     *
     * @return the object self
     */
    public Query constraints()
    {
        query.append("CONSTRAINTS ");
        return this;
    }
    
    /**
     * Append the query with CONSTRAINTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constraints(String c)
    {
        query.append("CONSTRAINTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONSTRUCTOR
     *
     * @return the object self
     */
    public Query constructor()
    {
        query.append("CONSTRUCTOR ");
        return this;
    }
    
    /**
     * Append the query with CONSTRUCTOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query constructor(String c)
    {
        query.append("CONSTRUCTOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONTAINS
     *
     * @return the object self
     */
    public Query contains()
    {
        query.append("CONTAINS ");
        return this;
    }
    
    /**
     * Append the query with CONTAINS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query contains(String c)
    {
        query.append("CONTAINS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONTAINSTABLE
     *
     * @return the object self
     */
    public Query containstable()
    {
        query.append("CONTAINSTABLE ");
        return this;
    }
    
    /**
     * Append the query with CONTAINSTABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query containstable(String c)
    {
        query.append("CONTAINSTABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONTINUE
     *
     * @return the object self
     */
    public Query continue_()
    {
        query.append("CONTINUE ");
        return this;
    }
    
    /**
     * Append the query with CONTINUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query continue_(String c)
    {
        query.append("CONTINUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONVERSION
     *
     * @return the object self
     */
    public Query conversion()
    {
        query.append("CONVERSION ");
        return this;
    }
    
    /**
     * Append the query with CONVERSION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query conversion(String c)
    {
        query.append("CONVERSION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CONVERT
     *
     * @return the object self
     */
    public Query convert()
    {
        query.append("CONVERT ");
        return this;
    }
    
    /**
     * Append the query with CONVERT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query convert(String c)
    {
        query.append("CONVERT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COPY
     *
     * @return the object self
     */
    public Query copy()
    {
        query.append("COPY ");
        return this;
    }
    
    /**
     * Append the query with COPY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query copy(String c)
    {
        query.append("COPY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CORR
     *
     * @return the object self
     */
    public Query corr()
    {
        query.append("CORR ");
        return this;
    }
    
    /**
     * Append the query with CORR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query corr(String c)
    {
        query.append("CORR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CORRESPONDING
     *
     * @return the object self
     */
    public Query corresponding()
    {
        query.append("CORRESPONDING ");
        return this;
    }
    
    /**
     * Append the query with CORRESPONDING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query corresponding(String c)
    {
        query.append("CORRESPONDING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COUNT
     *
     * @return the object self
     */
    public Query count()
    {
        query.append("COUNT ");
        return this;
    }
    
    /**
     * Append the query with COUNT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query count(String c)
    {
        query.append("COUNT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COVAR_POP
     *
     * @return the object self
     */
    public Query covar_pop()
    {
        query.append("COVAR_POP ");
        return this;
    }
    
    /**
     * Append the query with COVAR_POP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query covar_pop(String c)
    {
        query.append("COVAR_POP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with COVAR_SAMP
     *
     * @return the object self
     */
    public Query covar_samp()
    {
        query.append("COVAR_SAMP ");
        return this;
    }
    
    /**
     * Append the query with COVAR_SAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query covar_samp(String c)
    {
        query.append("COVAR_SAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CREATE
     *
     * @return the object self
     */
    public Query create()
    {
        query.append("CREATE ");
        return this;
    }
    
    /**
     * Append the query with CREATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query create(String c)
    {
        query.append("CREATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CREATEDB
     *
     * @return the object self
     */
    public Query createdb()
    {
        query.append("CREATEDB ");
        return this;
    }
    
    /**
     * Append the query with CREATEDB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query createdb(String c)
    {
        query.append("CREATEDB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CREATEROLE
     *
     * @return the object self
     */
    public Query createrole()
    {
        query.append("CREATEROLE ");
        return this;
    }
    
    /**
     * Append the query with CREATEROLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query createrole(String c)
    {
        query.append("CREATEROLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CREATEUSER
     *
     * @return the object self
     */
    public Query createuser()
    {
        query.append("CREATEUSER ");
        return this;
    }
    
    /**
     * Append the query with CREATEUSER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query createuser(String c)
    {
        query.append("CREATEUSER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CROSS
     *
     * @return the object self
     */
    public Query cross()
    {
        query.append("CROSS ");
        return this;
    }
    
    /**
     * Append the query with CROSS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cross(String c)
    {
        query.append("CROSS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CSV
     *
     * @return the object self
     */
    public Query csv()
    {
        query.append("CSV ");
        return this;
    }
    
    /**
     * Append the query with CSV statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query csv(String c)
    {
        query.append("CSV ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CUBE
     *
     * @return the object self
     */
    public Query cube()
    {
        query.append("CUBE ");
        return this;
    }
    
    /**
     * Append the query with CUBE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cube(String c)
    {
        query.append("CUBE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CUME_DIST
     *
     * @return the object self
     */
    public Query cume_dist()
    {
        query.append("CUME_DIST ");
        return this;
    }
    
    /**
     * Append the query with CUME_DIST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cume_dist(String c)
    {
        query.append("CUME_DIST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT
     *
     * @return the object self
     */
    public Query current()
    {
        query.append("CURRENT ");
        return this;
    }
    
    /**
     * Append the query with CURRENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current(String c)
    {
        query.append("CURRENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_DATE
     *
     * @return the object self
     */
    public Query current_date()
    {
        query.append("CURRENT_DATE ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_DATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_date(String c)
    {
        query.append("CURRENT_DATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_DEFAULT_TRANSFORM_GROUP
     *
     * @return the object self
     */
    public Query current_default_transform_group()
    {
        query.append("CURRENT_DEFAULT_TRANSFORM_GROUP ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_DEFAULT_TRANSFORM_GROUP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_default_transform_group(String c)
    {
        query.append("CURRENT_DEFAULT_TRANSFORM_GROUP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_PATH
     *
     * @return the object self
     */
    public Query current_path()
    {
        query.append("CURRENT_PATH ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_PATH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_path(String c)
    {
        query.append("CURRENT_PATH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_ROLE
     *
     * @return the object self
     */
    public Query current_role()
    {
        query.append("CURRENT_ROLE ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_ROLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_role(String c)
    {
        query.append("CURRENT_ROLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TIME
     *
     * @return the object self
     */
    public Query current_time()
    {
        query.append("CURRENT_TIME ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TIME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_time(String c)
    {
        query.append("CURRENT_TIME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TIMESTAMP
     *
     * @return the object self
     */
    public Query current_timestamp()
    {
        query.append("CURRENT_TIMESTAMP ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TIMESTAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_timestamp(String c)
    {
        query.append("CURRENT_TIMESTAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TRANSFORM_GROUP_FOR_TYPE
     *
     * @return the object self
     */
    public Query current_transform_group_for_type()
    {
        query.append("CURRENT_TRANSFORM_GROUP_FOR_TYPE ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_TRANSFORM_GROUP_FOR_TYPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_transform_group_for_type(String c)
    {
        query.append("CURRENT_TRANSFORM_GROUP_FOR_TYPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_USER
     *
     * @return the object self
     */
    public Query current_user()
    {
        query.append("CURRENT_USER ");
        return this;
    }
    
    /**
     * Append the query with CURRENT_USER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query current_user(String c)
    {
        query.append("CURRENT_USER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURSOR
     *
     * @return the object self
     */
    public Query cursor()
    {
        query.append("CURSOR ");
        return this;
    }
    
    /**
     * Append the query with CURSOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cursor(String c)
    {
        query.append("CURSOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CURSOR_NAME
     *
     * @return the object self
     */
    public Query cursor_name()
    {
        query.append("CURSOR_NAME ");
        return this;
    }
    
    /**
     * Append the query with CURSOR_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cursor_name(String c)
    {
        query.append("CURSOR_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with CYCLE
     *
     * @return the object self
     */
    public Query cycle()
    {
        query.append("CYCLE ");
        return this;
    }
    
    /**
     * Append the query with CYCLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query cycle(String c)
    {
        query.append("CYCLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATA
     *
     * @return the object self
     */
    public Query data()
    {
        query.append("DATA ");
        return this;
    }
    
    /**
     * Append the query with DATA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query data(String c)
    {
        query.append("DATA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATABASE
     *
     * @return the object self
     */
    public Query database()
    {
        query.append("DATABASE ");
        return this;
    }
    
    /**
     * Append the query with DATABASE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query database(String c)
    {
        query.append("DATABASE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATABASES
     *
     * @return the object self
     */
    public Query databases()
    {
        query.append("DATABASES ");
        return this;
    }
    
    /**
     * Append the query with DATABASES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query databases(String c)
    {
        query.append("DATABASES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATE
     *
     * @return the object self
     */
    public Query date()
    {
        query.append("DATE ");
        return this;
    }
    
    /**
     * Append the query with DATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query date(String c)
    {
        query.append("DATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATETIME
     *
     * @return the object self
     */
    public Query datetime()
    {
        query.append("DATETIME ");
        return this;
    }
    
    /**
     * Append the query with DATETIME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query datetime(String c)
    {
        query.append("DATETIME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATETIME_INTERVAL_CODE
     *
     * @return the object self
     */
    public Query datetime_interval_code()
    {
        query.append("DATETIME_INTERVAL_CODE ");
        return this;
    }
    
    /**
     * Append the query with DATETIME_INTERVAL_CODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query datetime_interval_code(String c)
    {
        query.append("DATETIME_INTERVAL_CODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DATETIME_INTERVAL_PRECISION
     *
     * @return the object self
     */
    public Query datetime_interval_precision()
    {
        query.append("DATETIME_INTERVAL_PRECISION ");
        return this;
    }
    
    /**
     * Append the query with DATETIME_INTERVAL_PRECISION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query datetime_interval_precision(String c)
    {
        query.append("DATETIME_INTERVAL_PRECISION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAY
     *
     * @return the object self
     */
    public Query day()
    {
        query.append("DAY ");
        return this;
    }
    
    /**
     * Append the query with DAY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query day(String c)
    {
        query.append("DAY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAY_HOUR
     *
     * @return the object self
     */
    public Query day_hour()
    {
        query.append("DAY_HOUR ");
        return this;
    }
    
    /**
     * Append the query with DAY_HOUR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query day_hour(String c)
    {
        query.append("DAY_HOUR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAY_MICROSECOND
     *
     * @return the object self
     */
    public Query day_microsecond()
    {
        query.append("DAY_MICROSECOND ");
        return this;
    }
    
    /**
     * Append the query with DAY_MICROSECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query day_microsecond(String c)
    {
        query.append("DAY_MICROSECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAY_MINUTE
     *
     * @return the object self
     */
    public Query day_minute()
    {
        query.append("DAY_MINUTE ");
        return this;
    }
    
    /**
     * Append the query with DAY_MINUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query day_minute(String c)
    {
        query.append("DAY_MINUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAY_SECOND
     *
     * @return the object self
     */
    public Query day_second()
    {
        query.append("DAY_SECOND ");
        return this;
    }
    
    /**
     * Append the query with DAY_SECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query day_second(String c)
    {
        query.append("DAY_SECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAYOFMONTH
     *
     * @return the object self
     */
    public Query dayofmonth()
    {
        query.append("DAYOFMONTH ");
        return this;
    }
    
    /**
     * Append the query with DAYOFMONTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dayofmonth(String c)
    {
        query.append("DAYOFMONTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAYOFWEEK
     *
     * @return the object self
     */
    public Query dayofweek()
    {
        query.append("DAYOFWEEK ");
        return this;
    }
    
    /**
     * Append the query with DAYOFWEEK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dayofweek(String c)
    {
        query.append("DAYOFWEEK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DAYOFYEAR
     *
     * @return the object self
     */
    public Query dayofyear()
    {
        query.append("DAYOFYEAR ");
        return this;
    }
    
    /**
     * Append the query with DAYOFYEAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dayofyear(String c)
    {
        query.append("DAYOFYEAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DBCC
     *
     * @return the object self
     */
    public Query dbcc()
    {
        query.append("DBCC ");
        return this;
    }
    
    /**
     * Append the query with DBCC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dbcc(String c)
    {
        query.append("DBCC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEALLOCATE
     *
     * @return the object self
     */
    public Query deallocate()
    {
        query.append("DEALLOCATE ");
        return this;
    }
    
    /**
     * Append the query with DEALLOCATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deallocate(String c)
    {
        query.append("DEALLOCATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEC
     *
     * @return the object self
     */
    public Query dec()
    {
        query.append("DEC ");
        return this;
    }
    
    /**
     * Append the query with DEC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dec(String c)
    {
        query.append("DEC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DECIMAL
     *
     * @return the object self
     */
    public Query decimal()
    {
        query.append("DECIMAL ");
        return this;
    }
    
    /**
     * Append the query with DECIMAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query decimal(String c)
    {
        query.append("DECIMAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DECLARE
     *
     * @return the object self
     */
    public Query declare()
    {
        query.append("DECLARE ");
        return this;
    }
    
    /**
     * Append the query with DECLARE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query declare(String c)
    {
        query.append("DECLARE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFAULT
     *
     * @return the object self
     */
    public Query default_()
    {
        query.append("DEFAULT ");
        return this;
    }
    
    /**
     * Append the query with DEFAULT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query default_(String c)
    {
        query.append("DEFAULT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFAULTS
     *
     * @return the object self
     */
    public Query defaults()
    {
        query.append("DEFAULTS ");
        return this;
    }
    
    /**
     * Append the query with DEFAULTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query defaults(String c)
    {
        query.append("DEFAULTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFERRABLE
     *
     * @return the object self
     */
    public Query deferrable()
    {
        query.append("DEFERRABLE ");
        return this;
    }
    
    /**
     * Append the query with DEFERRABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deferrable(String c)
    {
        query.append("DEFERRABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFERRED
     *
     * @return the object self
     */
    public Query deferred()
    {
        query.append("DEFERRED ");
        return this;
    }
    
    /**
     * Append the query with DEFERRED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deferred(String c)
    {
        query.append("DEFERRED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFINED
     *
     * @return the object self
     */
    public Query defined()
    {
        query.append("DEFINED ");
        return this;
    }
    
    /**
     * Append the query with DEFINED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query defined(String c)
    {
        query.append("DEFINED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEFINER
     *
     * @return the object self
     */
    public Query definer()
    {
        query.append("DEFINER ");
        return this;
    }
    
    /**
     * Append the query with DEFINER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query definer(String c)
    {
        query.append("DEFINER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEGREE
     *
     * @return the object self
     */
    public Query degree()
    {
        query.append("DEGREE ");
        return this;
    }
    
    /**
     * Append the query with DEGREE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query degree(String c)
    {
        query.append("DEGREE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DELAY_KEY_WRITE
     *
     * @return the object self
     */
    public Query delay_key_write()
    {
        query.append("DELAY_KEY_WRITE ");
        return this;
    }
    
    /**
     * Append the query with DELAY_KEY_WRITE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query delay_key_write(String c)
    {
        query.append("DELAY_KEY_WRITE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DELAYED
     *
     * @return the object self
     */
    public Query delayed()
    {
        query.append("DELAYED ");
        return this;
    }
    
    /**
     * Append the query with DELAYED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query delayed(String c)
    {
        query.append("DELAYED ").append(c).append(" ");
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
     * Append the query with DELIMITER
     *
     * @return the object self
     */
    public Query delimiter()
    {
        query.append("DELIMITER ");
        return this;
    }
    
    /**
     * Append the query with DELIMITER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query delimiter(String c)
    {
        query.append("DELIMITER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DELIMITERS
     *
     * @return the object self
     */
    public Query delimiters()
    {
        query.append("DELIMITERS ");
        return this;
    }
    
    /**
     * Append the query with DELIMITERS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query delimiters(String c)
    {
        query.append("DELIMITERS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DENSE_RANK
     *
     * @return the object self
     */
    public Query dense_rank()
    {
        query.append("DENSE_RANK ");
        return this;
    }
    
    /**
     * Append the query with DENSE_RANK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dense_rank(String c)
    {
        query.append("DENSE_RANK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DENY
     *
     * @return the object self
     */
    public Query deny()
    {
        query.append("DENY ");
        return this;
    }
    
    /**
     * Append the query with DENY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deny(String c)
    {
        query.append("DENY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEPTH
     *
     * @return the object self
     */
    public Query depth()
    {
        query.append("DEPTH ");
        return this;
    }
    
    /**
     * Append the query with DEPTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query depth(String c)
    {
        query.append("DEPTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DEREF
     *
     * @return the object self
     */
    public Query deref()
    {
        query.append("DEREF ");
        return this;
    }
    
    /**
     * Append the query with DEREF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deref(String c)
    {
        query.append("DEREF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DERIVED
     *
     * @return the object self
     */
    public Query derived()
    {
        query.append("DERIVED ");
        return this;
    }
    
    /**
     * Append the query with DERIVED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query derived(String c)
    {
        query.append("DERIVED ").append(c).append(" ");
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
     * Append the query with DESCRIBE
     *
     * @return the object self
     */
    public Query describe()
    {
        query.append("DESCRIBE ");
        return this;
    }
    
    /**
     * Append the query with DESCRIBE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query describe(String c)
    {
        query.append("DESCRIBE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DESCRIPTOR
     *
     * @return the object self
     */
    public Query descriptor()
    {
        query.append("DESCRIPTOR ");
        return this;
    }
    
    /**
     * Append the query with DESCRIPTOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query descriptor(String c)
    {
        query.append("DESCRIPTOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DESTROY
     *
     * @return the object self
     */
    public Query destroy()
    {
        query.append("DESTROY ");
        return this;
    }
    
    /**
     * Append the query with DESTROY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query destroy(String c)
    {
        query.append("DESTROY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DESTRUCTOR
     *
     * @return the object self
     */
    public Query destructor()
    {
        query.append("DESTRUCTOR ");
        return this;
    }
    
    /**
     * Append the query with DESTRUCTOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query destructor(String c)
    {
        query.append("DESTRUCTOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DETERMINISTIC
     *
     * @return the object self
     */
    public Query deterministic()
    {
        query.append("DETERMINISTIC ");
        return this;
    }
    
    /**
     * Append the query with DETERMINISTIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query deterministic(String c)
    {
        query.append("DETERMINISTIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DIAGNOSTICS
     *
     * @return the object self
     */
    public Query diagnostics()
    {
        query.append("DIAGNOSTICS ");
        return this;
    }
    
    /**
     * Append the query with DIAGNOSTICS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query diagnostics(String c)
    {
        query.append("DIAGNOSTICS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DICTIONARY
     *
     * @return the object self
     */
    public Query dictionary()
    {
        query.append("DICTIONARY ");
        return this;
    }
    
    /**
     * Append the query with DICTIONARY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dictionary(String c)
    {
        query.append("DICTIONARY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISABLE
     *
     * @return the object self
     */
    public Query disable()
    {
        query.append("DISABLE ");
        return this;
    }
    
    /**
     * Append the query with DISABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query disable(String c)
    {
        query.append("DISABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISCONNECT
     *
     * @return the object self
     */
    public Query disconnect()
    {
        query.append("DISCONNECT ");
        return this;
    }
    
    /**
     * Append the query with DISCONNECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query disconnect(String c)
    {
        query.append("DISCONNECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISK
     *
     * @return the object self
     */
    public Query disk()
    {
        query.append("DISK ");
        return this;
    }
    
    /**
     * Append the query with DISK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query disk(String c)
    {
        query.append("DISK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISPATCH
     *
     * @return the object self
     */
    public Query dispatch()
    {
        query.append("DISPATCH ");
        return this;
    }
    
    /**
     * Append the query with DISPATCH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dispatch(String c)
    {
        query.append("DISPATCH ").append(c).append(" ");
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
     * Append the query with DISTINCTROW
     *
     * @return the object self
     */
    public Query distinctrow()
    {
        query.append("DISTINCTROW ");
        return this;
    }
    
    /**
     * Append the query with DISTINCTROW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query distinctrow(String c)
    {
        query.append("DISTINCTROW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DISTRIBUTED
     *
     * @return the object self
     */
    public Query distributed()
    {
        query.append("DISTRIBUTED ");
        return this;
    }
    
    /**
     * Append the query with DISTRIBUTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query distributed(String c)
    {
        query.append("DISTRIBUTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DIV
     *
     * @return the object self
     */
    public Query div()
    {
        query.append("DIV ");
        return this;
    }
    
    /**
     * Append the query with DIV statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query div(String c)
    {
        query.append("DIV ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DO
     *
     * @return the object self
     */
    public Query do_()
    {
        query.append("DO ");
        return this;
    }
    
    /**
     * Append the query with DO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query do_(String c)
    {
        query.append("DO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DOMAIN
     *
     * @return the object self
     */
    public Query domain()
    {
        query.append("DOMAIN ");
        return this;
    }
    
    /**
     * Append the query with DOMAIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query domain(String c)
    {
        query.append("DOMAIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DOUBLE
     *
     * @return the object self
     */
    public Query double_()
    {
        query.append("DOUBLE ");
        return this;
    }
    
    /**
     * Append the query with DOUBLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query double_(String c)
    {
        query.append("DOUBLE ").append(c).append(" ");
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
     * Append the query with DUAL
     *
     * @return the object self
     */
    public Query dual()
    {
        query.append("DUAL ");
        return this;
    }
    
    /**
     * Append the query with DUAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dual(String c)
    {
        query.append("DUAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DUMMY
     *
     * @return the object self
     */
    public Query dummy()
    {
        query.append("DUMMY ");
        return this;
    }
    
    /**
     * Append the query with DUMMY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dummy(String c)
    {
        query.append("DUMMY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DUMP
     *
     * @return the object self
     */
    public Query dump()
    {
        query.append("DUMP ");
        return this;
    }
    
    /**
     * Append the query with DUMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dump(String c)
    {
        query.append("DUMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC
     *
     * @return the object self
     */
    public Query dynamic()
    {
        query.append("DYNAMIC ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dynamic(String c)
    {
        query.append("DYNAMIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC_FUNCTION
     *
     * @return the object self
     */
    public Query dynamic_function()
    {
        query.append("DYNAMIC_FUNCTION ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC_FUNCTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dynamic_function(String c)
    {
        query.append("DYNAMIC_FUNCTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC_FUNCTION_CODE
     *
     * @return the object self
     */
    public Query dynamic_function_code()
    {
        query.append("DYNAMIC_FUNCTION_CODE ");
        return this;
    }
    
    /**
     * Append the query with DYNAMIC_FUNCTION_CODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query dynamic_function_code(String c)
    {
        query.append("DYNAMIC_FUNCTION_CODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EACH
     *
     * @return the object self
     */
    public Query each()
    {
        query.append("EACH ");
        return this;
    }
    
    /**
     * Append the query with EACH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query each(String c)
    {
        query.append("EACH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ELEMENT
     *
     * @return the object self
     */
    public Query element()
    {
        query.append("ELEMENT ");
        return this;
    }
    
    /**
     * Append the query with ELEMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query element(String c)
    {
        query.append("ELEMENT ").append(c).append(" ");
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
     * Append the query with ELSEIF
     *
     * @return the object self
     */
    public Query elseif()
    {
        query.append("ELSEIF ");
        return this;
    }
    
    /**
     * Append the query with ELSEIF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query elseif(String c)
    {
        query.append("ELSEIF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ENABLE
     *
     * @return the object self
     */
    public Query enable()
    {
        query.append("ENABLE ");
        return this;
    }
    
    /**
     * Append the query with ENABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query enable(String c)
    {
        query.append("ENABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ENCLOSED
     *
     * @return the object self
     */
    public Query enclosed()
    {
        query.append("ENCLOSED ");
        return this;
    }
    
    /**
     * Append the query with ENCLOSED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query enclosed(String c)
    {
        query.append("ENCLOSED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ENCODING
     *
     * @return the object self
     */
    public Query encoding()
    {
        query.append("ENCODING ");
        return this;
    }
    
    /**
     * Append the query with ENCODING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query encoding(String c)
    {
        query.append("ENCODING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ENCRYPTED
     *
     * @return the object self
     */
    public Query encrypted()
    {
        query.append("ENCRYPTED ");
        return this;
    }
    
    /**
     * Append the query with ENCRYPTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query encrypted(String c)
    {
        query.append("ENCRYPTED ").append(c).append(" ");
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
     * Append the query with END-EXEC
     *
     * @return the object self
     */
    public Query end_exec()
    {
        query.append("END-EXEC ");
        return this;
    }
    
    /**
     * Append the query with END-EXEC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query end_exec(String c)
    {
        query.append("END-EXEC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ENUM
     *
     * @return the object self
     */
    public Query enum_()
    {
        query.append("ENUM ");
        return this;
    }
    
    /**
     * Append the query with ENUM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query enum_(String c)
    {
        query.append("ENUM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EQUALS
     *
     * @return the object self
     */
    public Query equals()
    {
        query.append("EQUALS ");
        return this;
    }
    
    /**
     * Append the query with EQUALS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query equals(String c)
    {
        query.append("EQUALS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ERRLVL
     *
     * @return the object self
     */
    public Query errlvl()
    {
        query.append("ERRLVL ");
        return this;
    }
    
    /**
     * Append the query with ERRLVL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query errlvl(String c)
    {
        query.append("ERRLVL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ESCAPE
     *
     * @return the object self
     */
    public Query escape()
    {
        query.append("ESCAPE ");
        return this;
    }
    
    /**
     * Append the query with ESCAPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query escape(String c)
    {
        query.append("ESCAPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ESCAPED
     *
     * @return the object self
     */
    public Query escaped()
    {
        query.append("ESCAPED ");
        return this;
    }
    
    /**
     * Append the query with ESCAPED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query escaped(String c)
    {
        query.append("ESCAPED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EVERY
     *
     * @return the object self
     */
    public Query every()
    {
        query.append("EVERY ");
        return this;
    }
    
    /**
     * Append the query with EVERY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query every(String c)
    {
        query.append("EVERY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXCEPT
     *
     * @return the object self
     */
    public Query except()
    {
        query.append("EXCEPT ");
        return this;
    }
    
    /**
     * Append the query with EXCEPT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query except(String c)
    {
        query.append("EXCEPT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXCEPTION
     *
     * @return the object self
     */
    public Query exception()
    {
        query.append("EXCEPTION ");
        return this;
    }
    
    /**
     * Append the query with EXCEPTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exception(String c)
    {
        query.append("EXCEPTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXCLUDE
     *
     * @return the object self
     */
    public Query exclude()
    {
        query.append("EXCLUDE ");
        return this;
    }
    
    /**
     * Append the query with EXCLUDE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exclude(String c)
    {
        query.append("EXCLUDE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXCLUDING
     *
     * @return the object self
     */
    public Query excluding()
    {
        query.append("EXCLUDING ");
        return this;
    }
    
    /**
     * Append the query with EXCLUDING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query excluding(String c)
    {
        query.append("EXCLUDING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXCLUSIVE
     *
     * @return the object self
     */
    public Query exclusive()
    {
        query.append("EXCLUSIVE ");
        return this;
    }
    
    /**
     * Append the query with EXCLUSIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exclusive(String c)
    {
        query.append("EXCLUSIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXEC
     *
     * @return the object self
     */
    public Query exec()
    {
        query.append("EXEC ");
        return this;
    }
    
    /**
     * Append the query with EXEC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exec(String c)
    {
        query.append("EXEC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXECUTE
     *
     * @return the object self
     */
    public Query execute()
    {
        query.append("EXECUTE ");
        return this;
    }
    
    /**
     * Append the query with EXECUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query execute(String c)
    {
        query.append("EXECUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXISTING
     *
     * @return the object self
     */
    public Query existing()
    {
        query.append("EXISTING ");
        return this;
    }
    
    /**
     * Append the query with EXISTING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query existing(String c)
    {
        query.append("EXISTING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXISTS
     *
     * @return the object self
     */
    public Query exists()
    {
        query.append("EXISTS ");
        return this;
    }
    
    /**
     * Append the query with EXISTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exists(String c)
    {
        query.append("EXISTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXIT
     *
     * @return the object self
     */
    public Query exit()
    {
        query.append("EXIT ");
        return this;
    }
    
    /**
     * Append the query with EXIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exit(String c)
    {
        query.append("EXIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXP
     *
     * @return the object self
     */
    public Query exp()
    {
        query.append("EXP ");
        return this;
    }
    
    /**
     * Append the query with EXP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query exp(String c)
    {
        query.append("EXP ").append(c).append(" ");
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
     * Append the query with EXTERNAL
     *
     * @return the object self
     */
    public Query external()
    {
        query.append("EXTERNAL ");
        return this;
    }
    
    /**
     * Append the query with EXTERNAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query external(String c)
    {
        query.append("EXTERNAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with EXTRACT
     *
     * @return the object self
     */
    public Query extract()
    {
        query.append("EXTRACT ");
        return this;
    }
    
    /**
     * Append the query with EXTRACT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query extract(String c)
    {
        query.append("EXTRACT ").append(c).append(" ");
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
    
    /**
     * Append the query with FETCH
     *
     * @return the object self
     */
    public Query fetch()
    {
        query.append("FETCH ");
        return this;
    }
    
    /**
     * Append the query with FETCH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fetch(String c)
    {
        query.append("FETCH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FIELDS
     *
     * @return the object self
     */
    public Query fields()
    {
        query.append("FIELDS ");
        return this;
    }
    
    /**
     * Append the query with FIELDS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fields(String c)
    {
        query.append("FIELDS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FILE
     *
     * @return the object self
     */
    public Query file()
    {
        query.append("FILE ");
        return this;
    }
    
    /**
     * Append the query with FILE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query file(String c)
    {
        query.append("FILE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FILLFACTOR
     *
     * @return the object self
     */
    public Query fillfactor()
    {
        query.append("FILLFACTOR ");
        return this;
    }
    
    /**
     * Append the query with FILLFACTOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fillfactor(String c)
    {
        query.append("FILLFACTOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FILTER
     *
     * @return the object self
     */
    public Query filter()
    {
        query.append("FILTER ");
        return this;
    }
    
    /**
     * Append the query with FILTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query filter(String c)
    {
        query.append("FILTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FINAL
     *
     * @return the object self
     */
    public Query final_()
    {
        query.append("FINAL ");
        return this;
    }
    
    /**
     * Append the query with FINAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query final_(String c)
    {
        query.append("FINAL ").append(c).append(" ");
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
     * Append the query with FLOAT
     *
     * @return the object self
     */
    public Query float_()
    {
        query.append("FLOAT ");
        return this;
    }
    
    /**
     * Append the query with FLOAT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query float_(String c)
    {
        query.append("FLOAT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FLOAT4
     *
     * @return the object self
     */
    public Query float4()
    {
        query.append("FLOAT4 ");
        return this;
    }
    
    /**
     * Append the query with FLOAT4 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query float4(String c)
    {
        query.append("FLOAT4 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FLOAT8
     *
     * @return the object self
     */
    public Query float8()
    {
        query.append("FLOAT8 ");
        return this;
    }
    
    /**
     * Append the query with FLOAT8 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query float8(String c)
    {
        query.append("FLOAT8 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FLOOR
     *
     * @return the object self
     */
    public Query floor()
    {
        query.append("FLOOR ");
        return this;
    }
    
    /**
     * Append the query with FLOOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query floor(String c)
    {
        query.append("FLOOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FLUSH
     *
     * @return the object self
     */
    public Query flush()
    {
        query.append("FLUSH ");
        return this;
    }
    
    /**
     * Append the query with FLUSH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query flush(String c)
    {
        query.append("FLUSH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FOLLOWING
     *
     * @return the object self
     */
    public Query following()
    {
        query.append("FOLLOWING ");
        return this;
    }
    
    /**
     * Append the query with FOLLOWING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query following(String c)
    {
        query.append("FOLLOWING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FOR
     *
     * @return the object self
     */
    public Query for_()
    {
        query.append("FOR ");
        return this;
    }
    
    /**
     * Append the query with FOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query for_(String c)
    {
        query.append("FOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FORCE
     *
     * @return the object self
     */
    public Query force()
    {
        query.append("FORCE ");
        return this;
    }
    
    /**
     * Append the query with FORCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query force(String c)
    {
        query.append("FORCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FOREIGN
     *
     * @return the object self
     */
    public Query foreign()
    {
        query.append("FOREIGN ");
        return this;
    }
    
    /**
     * Append the query with FOREIGN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query foreign(String c)
    {
        query.append("FOREIGN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FORTRAN
     *
     * @return the object self
     */
    public Query fortran()
    {
        query.append("FORTRAN ");
        return this;
    }
    
    /**
     * Append the query with FORTRAN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fortran(String c)
    {
        query.append("FORTRAN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FORWARD
     *
     * @return the object self
     */
    public Query forward()
    {
        query.append("FORWARD ");
        return this;
    }
    
    /**
     * Append the query with FORWARD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query forward(String c)
    {
        query.append("FORWARD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FOUND
     *
     * @return the object self
     */
    public Query found()
    {
        query.append("FOUND ");
        return this;
    }
    
    /**
     * Append the query with FOUND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query found(String c)
    {
        query.append("FOUND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FREE
     *
     * @return the object self
     */
    public Query free()
    {
        query.append("FREE ");
        return this;
    }
    
    /**
     * Append the query with FREE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query free(String c)
    {
        query.append("FREE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FREETEXT
     *
     * @return the object self
     */
    public Query freetext()
    {
        query.append("FREETEXT ");
        return this;
    }
    
    /**
     * Append the query with FREETEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query freetext(String c)
    {
        query.append("FREETEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FREETEXTTABLE
     *
     * @return the object self
     */
    public Query freetexttable()
    {
        query.append("FREETEXTTABLE ");
        return this;
    }
    
    /**
     * Append the query with FREETEXTTABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query freetexttable(String c)
    {
        query.append("FREETEXTTABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FREEZE
     *
     * @return the object self
     */
    public Query freeze()
    {
        query.append("FREEZE ");
        return this;
    }
    
    /**
     * Append the query with FREEZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query freeze(String c)
    {
        query.append("FREEZE ").append(c).append(" ");
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
     * Append the query with FULL
     *
     * @return the object self
     */
    public Query full()
    {
        query.append("FULL ");
        return this;
    }
    
    /**
     * Append the query with FULL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query full(String c)
    {
        query.append("FULL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FULLTEXT
     *
     * @return the object self
     */
    public Query fulltext()
    {
        query.append("FULLTEXT ");
        return this;
    }
    
    /**
     * Append the query with FULLTEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fulltext(String c)
    {
        query.append("FULLTEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FUNCTION
     *
     * @return the object self
     */
    public Query function()
    {
        query.append("FUNCTION ");
        return this;
    }
    
    /**
     * Append the query with FUNCTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query function(String c)
    {
        query.append("FUNCTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with FUSION
     *
     * @return the object self
     */
    public Query fusion()
    {
        query.append("FUSION ");
        return this;
    }
    
    /**
     * Append the query with FUSION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query fusion(String c)
    {
        query.append("FUSION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with G
     *
     * @return the object self
     */
    public Query g()
    {
        query.append("G ");
        return this;
    }
    
    /**
     * Append the query with G statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query g(String c)
    {
        query.append("G ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GENERAL
     *
     * @return the object self
     */
    public Query general()
    {
        query.append("GENERAL ");
        return this;
    }
    
    /**
     * Append the query with GENERAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query general(String c)
    {
        query.append("GENERAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GENERATED
     *
     * @return the object self
     */
    public Query generated()
    {
        query.append("GENERATED ");
        return this;
    }
    
    /**
     * Append the query with GENERATED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query generated(String c)
    {
        query.append("GENERATED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GET
     *
     * @return the object self
     */
    public Query get()
    {
        query.append("GET ");
        return this;
    }
    
    /**
     * Append the query with GET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query get(String c)
    {
        query.append("GET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GLOBAL
     *
     * @return the object self
     */
    public Query global()
    {
        query.append("GLOBAL ");
        return this;
    }
    
    /**
     * Append the query with GLOBAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query global(String c)
    {
        query.append("GLOBAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GO
     *
     * @return the object self
     */
    public Query go()
    {
        query.append("GO ");
        return this;
    }
    
    /**
     * Append the query with GO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query go(String c)
    {
        query.append("GO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GOTO
     *
     * @return the object self
     */
    public Query goto_()
    {
        query.append("GOTO ");
        return this;
    }
    
    /**
     * Append the query with GOTO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query goto_(String c)
    {
        query.append("GOTO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GRANT
     *
     * @return the object self
     */
    public Query grant()
    {
        query.append("GRANT ");
        return this;
    }
    
    /**
     * Append the query with GRANT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query grant(String c)
    {
        query.append("GRANT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GRANTED
     *
     * @return the object self
     */
    public Query granted()
    {
        query.append("GRANTED ");
        return this;
    }
    
    /**
     * Append the query with GRANTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query granted(String c)
    {
        query.append("GRANTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GRANTS
     *
     * @return the object self
     */
    public Query grants()
    {
        query.append("GRANTS ");
        return this;
    }
    
    /**
     * Append the query with GRANTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query grants(String c)
    {
        query.append("GRANTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GREATEST
     *
     * @return the object self
     */
    public Query greatest()
    {
        query.append("GREATEST ");
        return this;
    }
    
    /**
     * Append the query with GREATEST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query greatest(String c)
    {
        query.append("GREATEST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GROUP
     *
     * @return the object self
     */
    public Query group()
    {
        query.append("GROUP ");
        return this;
    }
    
    /**
     * Append the query with GROUP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query group(String c)
    {
        query.append("GROUP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with GROUPING
     *
     * @return the object self
     */
    public Query grouping()
    {
        query.append("GROUPING ");
        return this;
    }
    
    /**
     * Append the query with GROUPING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query grouping(String c)
    {
        query.append("GROUPING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HANDLER
     *
     * @return the object self
     */
    public Query handler()
    {
        query.append("HANDLER ");
        return this;
    }
    
    /**
     * Append the query with HANDLER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query handler(String c)
    {
        query.append("HANDLER ").append(c).append(" ");
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
     * Append the query with HEADER
     *
     * @return the object self
     */
    public Query header()
    {
        query.append("HEADER ");
        return this;
    }
    
    /**
     * Append the query with HEADER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query header(String c)
    {
        query.append("HEADER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HEAP
     *
     * @return the object self
     */
    public Query heap()
    {
        query.append("HEAP ");
        return this;
    }
    
    /**
     * Append the query with HEAP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query heap(String c)
    {
        query.append("HEAP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HIERARCHY
     *
     * @return the object self
     */
    public Query hierarchy()
    {
        query.append("HIERARCHY ");
        return this;
    }
    
    /**
     * Append the query with HIERARCHY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hierarchy(String c)
    {
        query.append("HIERARCHY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HIGH_PRIORITY
     *
     * @return the object self
     */
    public Query high_priority()
    {
        query.append("HIGH_PRIORITY ");
        return this;
    }
    
    /**
     * Append the query with HIGH_PRIORITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query high_priority(String c)
    {
        query.append("HIGH_PRIORITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOLD
     *
     * @return the object self
     */
    public Query hold()
    {
        query.append("HOLD ");
        return this;
    }
    
    /**
     * Append the query with HOLD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hold(String c)
    {
        query.append("HOLD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOLDLOCK
     *
     * @return the object self
     */
    public Query holdlock()
    {
        query.append("HOLDLOCK ");
        return this;
    }
    
    /**
     * Append the query with HOLDLOCK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query holdlock(String c)
    {
        query.append("HOLDLOCK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOST
     *
     * @return the object self
     */
    public Query host()
    {
        query.append("HOST ");
        return this;
    }
    
    /**
     * Append the query with HOST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query host(String c)
    {
        query.append("HOST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOSTS
     *
     * @return the object self
     */
    public Query hosts()
    {
        query.append("HOSTS ");
        return this;
    }
    
    /**
     * Append the query with HOSTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hosts(String c)
    {
        query.append("HOSTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOUR
     *
     * @return the object self
     */
    public Query hour()
    {
        query.append("HOUR ");
        return this;
    }
    
    /**
     * Append the query with HOUR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hour(String c)
    {
        query.append("HOUR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOUR_MICROSECOND
     *
     * @return the object self
     */
    public Query hour_microsecond()
    {
        query.append("HOUR_MICROSECOND ");
        return this;
    }
    
    /**
     * Append the query with HOUR_MICROSECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hour_microsecond(String c)
    {
        query.append("HOUR_MICROSECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOUR_MINUTE
     *
     * @return the object self
     */
    public Query hour_minute()
    {
        query.append("HOUR_MINUTE ");
        return this;
    }
    
    /**
     * Append the query with HOUR_MINUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hour_minute(String c)
    {
        query.append("HOUR_MINUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with HOUR_SECOND
     *
     * @return the object self
     */
    public Query hour_second()
    {
        query.append("HOUR_SECOND ");
        return this;
    }
    
    /**
     * Append the query with HOUR_SECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query hour_second(String c)
    {
        query.append("HOUR_SECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IDENTIFIED
     *
     * @return the object self
     */
    public Query identified()
    {
        query.append("IDENTIFIED ");
        return this;
    }
    
    /**
     * Append the query with IDENTIFIED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query identified(String c)
    {
        query.append("IDENTIFIED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IDENTITY
     *
     * @return the object self
     */
    public Query identity()
    {
        query.append("IDENTITY ");
        return this;
    }
    
    /**
     * Append the query with IDENTITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query identity(String c)
    {
        query.append("IDENTITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IDENTITY_INSERT
     *
     * @return the object self
     */
    public Query identity_insert()
    {
        query.append("IDENTITY_INSERT ");
        return this;
    }
    
    /**
     * Append the query with IDENTITY_INSERT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query identity_insert(String c)
    {
        query.append("IDENTITY_INSERT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IDENTITYCOL
     *
     * @return the object self
     */
    public Query identitycol()
    {
        query.append("IDENTITYCOL ");
        return this;
    }
    
    /**
     * Append the query with IDENTITYCOL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query identitycol(String c)
    {
        query.append("IDENTITYCOL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IF
     *
     * @return the object self
     */
    public Query if_()
    {
        query.append("IF ");
        return this;
    }
    
    /**
     * Append the query with IF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query if_(String c)
    {
        query.append("IF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IGNORE
     *
     * @return the object self
     */
    public Query ignore()
    {
        query.append("IGNORE ");
        return this;
    }
    
    /**
     * Append the query with IGNORE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ignore(String c)
    {
        query.append("IGNORE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ILIKE
     *
     * @return the object self
     */
    public Query ilike()
    {
        query.append("ILIKE ");
        return this;
    }
    
    /**
     * Append the query with ILIKE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ilike(String c)
    {
        query.append("ILIKE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IMMEDIATE
     *
     * @return the object self
     */
    public Query immediate()
    {
        query.append("IMMEDIATE ");
        return this;
    }
    
    /**
     * Append the query with IMMEDIATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query immediate(String c)
    {
        query.append("IMMEDIATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IMMUTABLE
     *
     * @return the object self
     */
    public Query immutable()
    {
        query.append("IMMUTABLE ");
        return this;
    }
    
    /**
     * Append the query with IMMUTABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query immutable(String c)
    {
        query.append("IMMUTABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IMPLEMENTATION
     *
     * @return the object self
     */
    public Query implementation()
    {
        query.append("IMPLEMENTATION ");
        return this;
    }
    
    /**
     * Append the query with IMPLEMENTATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query implementation(String c)
    {
        query.append("IMPLEMENTATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IMPLICIT
     *
     * @return the object self
     */
    public Query implicit()
    {
        query.append("IMPLICIT ");
        return this;
    }
    
    /**
     * Append the query with IMPLICIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query implicit(String c)
    {
        query.append("IMPLICIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IN
     *
     * @return the object self
     */
    public Query in()
    {
        query.append("IN ");
        return this;
    }
    
    /**
     * Append the query with IN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query in(String c)
    {
        query.append("IN ").append(c).append(" ");
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
     * Append the query with INCLUDING
     *
     * @return the object self
     */
    public Query including()
    {
        query.append("INCLUDING ");
        return this;
    }
    
    /**
     * Append the query with INCLUDING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query including(String c)
    {
        query.append("INCLUDING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INCREMENT
     *
     * @return the object self
     */
    public Query increment()
    {
        query.append("INCREMENT ");
        return this;
    }
    
    /**
     * Append the query with INCREMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query increment(String c)
    {
        query.append("INCREMENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INDEX
     *
     * @return the object self
     */
    public Query index()
    {
        query.append("INDEX ");
        return this;
    }
    
    /**
     * Append the query with INDEX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query index(String c)
    {
        query.append("INDEX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INDICATOR
     *
     * @return the object self
     */
    public Query indicator()
    {
        query.append("INDICATOR ");
        return this;
    }
    
    /**
     * Append the query with INDICATOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query indicator(String c)
    {
        query.append("INDICATOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INFILE
     *
     * @return the object self
     */
    public Query infile()
    {
        query.append("INFILE ");
        return this;
    }
    
    /**
     * Append the query with INFILE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query infile(String c)
    {
        query.append("INFILE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INFIX
     *
     * @return the object self
     */
    public Query infix()
    {
        query.append("INFIX ");
        return this;
    }
    
    /**
     * Append the query with INFIX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query infix(String c)
    {
        query.append("INFIX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INHERIT
     *
     * @return the object self
     */
    public Query inherit()
    {
        query.append("INHERIT ");
        return this;
    }
    
    /**
     * Append the query with INHERIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query inherit(String c)
    {
        query.append("INHERIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INHERITS
     *
     * @return the object self
     */
    public Query inherits()
    {
        query.append("INHERITS ");
        return this;
    }
    
    /**
     * Append the query with INHERITS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query inherits(String c)
    {
        query.append("INHERITS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INITIAL
     *
     * @return the object self
     */
    public Query initial()
    {
        query.append("INITIAL ");
        return this;
    }
    
    /**
     * Append the query with INITIAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query initial(String c)
    {
        query.append("INITIAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INITIALIZE
     *
     * @return the object self
     */
    public Query initialize()
    {
        query.append("INITIALIZE ");
        return this;
    }
    
    /**
     * Append the query with INITIALIZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query initialize(String c)
    {
        query.append("INITIALIZE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INITIALLY
     *
     * @return the object self
     */
    public Query initially()
    {
        query.append("INITIALLY ");
        return this;
    }
    
    /**
     * Append the query with INITIALLY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query initially(String c)
    {
        query.append("INITIALLY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INNER
     *
     * @return the object self
     */
    public Query inner()
    {
        query.append("INNER ");
        return this;
    }
    
    /**
     * Append the query with INNER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query inner(String c)
    {
        query.append("INNER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INOUT
     *
     * @return the object self
     */
    public Query inout()
    {
        query.append("INOUT ");
        return this;
    }
    
    /**
     * Append the query with INOUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query inout(String c)
    {
        query.append("INOUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INPUT
     *
     * @return the object self
     */
    public Query input()
    {
        query.append("INPUT ");
        return this;
    }
    
    /**
     * Append the query with INPUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query input(String c)
    {
        query.append("INPUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSENSITIVE
     *
     * @return the object self
     */
    public Query insensitive()
    {
        query.append("INSENSITIVE ");
        return this;
    }
    
    /**
     * Append the query with INSENSITIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query insensitive(String c)
    {
        query.append("INSENSITIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSERT
     *
     * @return the object self
     */
    public Query insert()
    {
        query.append("INSERT ");
        return this;
    }
    
    /**
     * Append the query with INSERT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query insert(String c)
    {
        query.append("INSERT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSERT_ID
     *
     * @return the object self
     */
    public Query insert_id()
    {
        query.append("INSERT_ID ");
        return this;
    }
    
    /**
     * Append the query with INSERT_ID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query insert_id(String c)
    {
        query.append("INSERT_ID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSTANCE
     *
     * @return the object self
     */
    public Query instance()
    {
        query.append("INSTANCE ");
        return this;
    }
    
    /**
     * Append the query with INSTANCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query instance(String c)
    {
        query.append("INSTANCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSTANTIABLE
     *
     * @return the object self
     */
    public Query instantiable()
    {
        query.append("INSTANTIABLE ");
        return this;
    }
    
    /**
     * Append the query with INSTANTIABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query instantiable(String c)
    {
        query.append("INSTANTIABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INSTEAD
     *
     * @return the object self
     */
    public Query instead()
    {
        query.append("INSTEAD ");
        return this;
    }
    
    /**
     * Append the query with INSTEAD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query instead(String c)
    {
        query.append("INSTEAD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT
     *
     * @return the object self
     */
    public Query int_()
    {
        query.append("INT ");
        return this;
    }
    
    /**
     * Append the query with INT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int_(String c)
    {
        query.append("INT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT1
     *
     * @return the object self
     */
    public Query int1()
    {
        query.append("INT1 ");
        return this;
    }
    
    /**
     * Append the query with INT1 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int1(String c)
    {
        query.append("INT1 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT2
     *
     * @return the object self
     */
    public Query int2()
    {
        query.append("INT2 ");
        return this;
    }
    
    /**
     * Append the query with INT2 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int2(String c)
    {
        query.append("INT2 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT3
     *
     * @return the object self
     */
    public Query int3()
    {
        query.append("INT3 ");
        return this;
    }
    
    /**
     * Append the query with INT3 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int3(String c)
    {
        query.append("INT3 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT4
     *
     * @return the object self
     */
    public Query int4()
    {
        query.append("INT4 ");
        return this;
    }
    
    /**
     * Append the query with INT4 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int4(String c)
    {
        query.append("INT4 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INT8
     *
     * @return the object self
     */
    public Query int8()
    {
        query.append("INT8 ");
        return this;
    }
    
    /**
     * Append the query with INT8 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query int8(String c)
    {
        query.append("INT8 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INTEGER
     *
     * @return the object self
     */
    public Query integer()
    {
        query.append("INTEGER ");
        return this;
    }
    
    /**
     * Append the query with INTEGER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query integer(String c)
    {
        query.append("INTEGER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INTERSECT
     *
     * @return the object self
     */
    public Query intersect()
    {
        query.append("INTERSECT ");
        return this;
    }
    
    /**
     * Append the query with INTERSECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query intersect(String c)
    {
        query.append("INTERSECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INTERSECTION
     *
     * @return the object self
     */
    public Query intersection()
    {
        query.append("INTERSECTION ");
        return this;
    }
    
    /**
     * Append the query with INTERSECTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query intersection(String c)
    {
        query.append("INTERSECTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INTERVAL
     *
     * @return the object self
     */
    public Query interval()
    {
        query.append("INTERVAL ");
        return this;
    }
    
    /**
     * Append the query with INTERVAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query interval(String c)
    {
        query.append("INTERVAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INTO
     *
     * @return the object self
     */
    public Query into()
    {
        query.append("INTO ");
        return this;
    }
    
    /**
     * Append the query with INTO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query into(String c)
    {
        query.append("INTO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with INVOKER
     *
     * @return the object self
     */
    public Query invoker()
    {
        query.append("INVOKER ");
        return this;
    }
    
    /**
     * Append the query with INVOKER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query invoker(String c)
    {
        query.append("INVOKER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with IS
     *
     * @return the object self
     */
    public Query is()
    {
        query.append("IS ");
        return this;
    }
    
    /**
     * Append the query with IS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query is(String c)
    {
        query.append("IS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ISAM
     *
     * @return the object self
     */
    public Query isam()
    {
        query.append("ISAM ");
        return this;
    }
    
    /**
     * Append the query with ISAM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query isam(String c)
    {
        query.append("ISAM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ISNULL
     *
     * @return the object self
     */
    public Query isnull()
    {
        query.append("ISNULL ");
        return this;
    }
    
    /**
     * Append the query with ISNULL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query isnull(String c)
    {
        query.append("ISNULL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ISOLATION
     *
     * @return the object self
     */
    public Query isolation()
    {
        query.append("ISOLATION ");
        return this;
    }
    
    /**
     * Append the query with ISOLATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query isolation(String c)
    {
        query.append("ISOLATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ITERATE
     *
     * @return the object self
     */
    public Query iterate()
    {
        query.append("ITERATE ");
        return this;
    }
    
    /**
     * Append the query with ITERATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query iterate(String c)
    {
        query.append("ITERATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with JOIN
     *
     * @return the object self
     */
    public Query join()
    {
        query.append("JOIN ");
        return this;
    }
    
    /**
     * Append the query with JOIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query join(String c)
    {
        query.append("JOIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with K
     *
     * @return the object self
     */
    public Query k()
    {
        query.append("K ");
        return this;
    }
    
    /**
     * Append the query with K statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query k(String c)
    {
        query.append("K ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with KEY
     *
     * @return the object self
     */
    public Query key()
    {
        query.append("KEY ");
        return this;
    }
    
    /**
     * Append the query with KEY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query key(String c)
    {
        query.append("KEY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with KEY_MEMBER
     *
     * @return the object self
     */
    public Query key_member()
    {
        query.append("KEY_MEMBER ");
        return this;
    }
    
    /**
     * Append the query with KEY_MEMBER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query key_member(String c)
    {
        query.append("KEY_MEMBER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with KEY_TYPE
     *
     * @return the object self
     */
    public Query key_type()
    {
        query.append("KEY_TYPE ");
        return this;
    }
    
    /**
     * Append the query with KEY_TYPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query key_type(String c)
    {
        query.append("KEY_TYPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with KEYS
     *
     * @return the object self
     */
    public Query keys()
    {
        query.append("KEYS ");
        return this;
    }
    
    /**
     * Append the query with KEYS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query keys(String c)
    {
        query.append("KEYS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with KILL
     *
     * @return the object self
     */
    public Query kill()
    {
        query.append("KILL ");
        return this;
    }
    
    /**
     * Append the query with KILL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query kill(String c)
    {
        query.append("KILL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LANCOMPILER
     *
     * @return the object self
     */
    public Query lancompiler()
    {
        query.append("LANCOMPILER ");
        return this;
    }
    
    /**
     * Append the query with LANCOMPILER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lancompiler(String c)
    {
        query.append("LANCOMPILER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LANGUAGE
     *
     * @return the object self
     */
    public Query language()
    {
        query.append("LANGUAGE ");
        return this;
    }
    
    /**
     * Append the query with LANGUAGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query language(String c)
    {
        query.append("LANGUAGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LARGE
     *
     * @return the object self
     */
    public Query large()
    {
        query.append("LARGE ");
        return this;
    }
    
    /**
     * Append the query with LARGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query large(String c)
    {
        query.append("LARGE ").append(c).append(" ");
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
     * Append the query with LAST_INSERT_ID
     *
     * @return the object self
     */
    public Query last_insert_id()
    {
        query.append("LAST_INSERT_ID ");
        return this;
    }
    
    /**
     * Append the query with LAST_INSERT_ID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query last_insert_id(String c)
    {
        query.append("LAST_INSERT_ID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LATERAL
     *
     * @return the object self
     */
    public Query lateral()
    {
        query.append("LATERAL ");
        return this;
    }
    
    /**
     * Append the query with LATERAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lateral(String c)
    {
        query.append("LATERAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEAD
     *
     * @return the object self
     */
    public Query lead()
    {
        query.append("LEAD ");
        return this;
    }
    
    /**
     * Append the query with LEAD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lead(String c)
    {
        query.append("LEAD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEADING
     *
     * @return the object self
     */
    public Query leading()
    {
        query.append("LEADING ");
        return this;
    }
    
    /**
     * Append the query with LEADING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query leading(String c)
    {
        query.append("LEADING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEAST
     *
     * @return the object self
     */
    public Query least()
    {
        query.append("LEAST ");
        return this;
    }
    
    /**
     * Append the query with LEAST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query least(String c)
    {
        query.append("LEAST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEAVE
     *
     * @return the object self
     */
    public Query leave()
    {
        query.append("LEAVE ");
        return this;
    }
    
    /**
     * Append the query with LEAVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query leave(String c)
    {
        query.append("LEAVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEFT
     *
     * @return the object self
     */
    public Query left()
    {
        query.append("LEFT ");
        return this;
    }
    
    /**
     * Append the query with LEFT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query left(String c)
    {
        query.append("LEFT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LENGTH
     *
     * @return the object self
     */
    public Query length()
    {
        query.append("LENGTH ");
        return this;
    }
    
    /**
     * Append the query with LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query length(String c)
    {
        query.append("LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LESS
     *
     * @return the object self
     */
    public Query less()
    {
        query.append("LESS ");
        return this;
    }
    
    /**
     * Append the query with LESS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query less(String c)
    {
        query.append("LESS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LEVEL
     *
     * @return the object self
     */
    public Query level()
    {
        query.append("LEVEL ");
        return this;
    }
    
    /**
     * Append the query with LEVEL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query level(String c)
    {
        query.append("LEVEL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LIKE
     *
     * @return the object self
     */
    public Query like()
    {
        query.append("LIKE ");
        return this;
    }
    
    /**
     * Append the query with LIKE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query like(String c)
    {
        query.append("LIKE ").append(c).append(" ");
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
     * Append the query with LINENO
     *
     * @return the object self
     */
    public Query lineno()
    {
        query.append("LINENO ");
        return this;
    }
    
    /**
     * Append the query with LINENO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lineno(String c)
    {
        query.append("LINENO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LINES
     *
     * @return the object self
     */
    public Query lines()
    {
        query.append("LINES ");
        return this;
    }
    
    /**
     * Append the query with LINES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lines(String c)
    {
        query.append("LINES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LISTEN
     *
     * @return the object self
     */
    public Query listen()
    {
        query.append("LISTEN ");
        return this;
    }
    
    /**
     * Append the query with LISTEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query listen(String c)
    {
        query.append("LISTEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LN
     *
     * @return the object self
     */
    public Query ln()
    {
        query.append("LN ");
        return this;
    }
    
    /**
     * Append the query with LN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ln(String c)
    {
        query.append("LN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOAD
     *
     * @return the object self
     */
    public Query load()
    {
        query.append("LOAD ");
        return this;
    }
    
    /**
     * Append the query with LOAD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query load(String c)
    {
        query.append("LOAD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCAL
     *
     * @return the object self
     */
    public Query local()
    {
        query.append("LOCAL ");
        return this;
    }
    
    /**
     * Append the query with LOCAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query local(String c)
    {
        query.append("LOCAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCALTIME
     *
     * @return the object self
     */
    public Query localtime()
    {
        query.append("LOCALTIME ");
        return this;
    }
    
    /**
     * Append the query with LOCALTIME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query localtime(String c)
    {
        query.append("LOCALTIME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCALTIMESTAMP
     *
     * @return the object self
     */
    public Query localtimestamp()
    {
        query.append("LOCALTIMESTAMP ");
        return this;
    }
    
    /**
     * Append the query with LOCALTIMESTAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query localtimestamp(String c)
    {
        query.append("LOCALTIMESTAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCATION
     *
     * @return the object self
     */
    public Query location()
    {
        query.append("LOCATION ");
        return this;
    }
    
    /**
     * Append the query with LOCATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query location(String c)
    {
        query.append("LOCATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCATOR
     *
     * @return the object self
     */
    public Query locator()
    {
        query.append("LOCATOR ");
        return this;
    }
    
    /**
     * Append the query with LOCATOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query locator(String c)
    {
        query.append("LOCATOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOCK
     *
     * @return the object self
     */
    public Query lock()
    {
        query.append("LOCK ");
        return this;
    }
    
    /**
     * Append the query with LOCK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lock(String c)
    {
        query.append("LOCK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOGIN
     *
     * @return the object self
     */
    public Query login()
    {
        query.append("LOGIN ");
        return this;
    }
    
    /**
     * Append the query with LOGIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query login(String c)
    {
        query.append("LOGIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOGS
     *
     * @return the object self
     */
    public Query logs()
    {
        query.append("LOGS ");
        return this;
    }
    
    /**
     * Append the query with LOGS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query logs(String c)
    {
        query.append("LOGS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LONG
     *
     * @return the object self
     */
    public Query long_()
    {
        query.append("LONG ");
        return this;
    }
    
    /**
     * Append the query with LONG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query long_(String c)
    {
        query.append("LONG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LONGBLOB
     *
     * @return the object self
     */
    public Query longblob()
    {
        query.append("LONGBLOB ");
        return this;
    }
    
    /**
     * Append the query with LONGBLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query longblob(String c)
    {
        query.append("LONGBLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LONGTEXT
     *
     * @return the object self
     */
    public Query longtext()
    {
        query.append("LONGTEXT ");
        return this;
    }
    
    /**
     * Append the query with LONGTEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query longtext(String c)
    {
        query.append("LONGTEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOOP
     *
     * @return the object self
     */
    public Query loop()
    {
        query.append("LOOP ");
        return this;
    }
    
    /**
     * Append the query with LOOP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query loop(String c)
    {
        query.append("LOOP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOW_PRIORITY
     *
     * @return the object self
     */
    public Query low_priority()
    {
        query.append("LOW_PRIORITY ");
        return this;
    }
    
    /**
     * Append the query with LOW_PRIORITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query low_priority(String c)
    {
        query.append("LOW_PRIORITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with LOWER
     *
     * @return the object self
     */
    public Query lower()
    {
        query.append("LOWER ");
        return this;
    }
    
    /**
     * Append the query with LOWER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query lower(String c)
    {
        query.append("LOWER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with M
     *
     * @return the object self
     */
    public Query m()
    {
        query.append("M ");
        return this;
    }
    
    /**
     * Append the query with M statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query m(String c)
    {
        query.append("M ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MAP
     *
     * @return the object self
     */
    public Query map()
    {
        query.append("MAP ");
        return this;
    }
    
    /**
     * Append the query with MAP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query map(String c)
    {
        query.append("MAP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MATCH
     *
     * @return the object self
     */
    public Query match()
    {
        query.append("MATCH ");
        return this;
    }
    
    /**
     * Append the query with MATCH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query match(String c)
    {
        query.append("MATCH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MATCHED
     *
     * @return the object self
     */
    public Query matched()
    {
        query.append("MATCHED ");
        return this;
    }
    
    /**
     * Append the query with MATCHED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query matched(String c)
    {
        query.append("MATCHED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MAX
     *
     * @return the object self
     */
    public Query max()
    {
        query.append("MAX ");
        return this;
    }
    
    /**
     * Append the query with MAX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query max(String c)
    {
        query.append("MAX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MAX_ROWS
     *
     * @return the object self
     */
    public Query max_rows()
    {
        query.append("MAX_ROWS ");
        return this;
    }
    
    /**
     * Append the query with MAX_ROWS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query max_rows(String c)
    {
        query.append("MAX_ROWS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MAXEXTENTS
     *
     * @return the object self
     */
    public Query maxextents()
    {
        query.append("MAXEXTENTS ");
        return this;
    }
    
    /**
     * Append the query with MAXEXTENTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query maxextents(String c)
    {
        query.append("MAXEXTENTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MAXVALUE
     *
     * @return the object self
     */
    public Query maxvalue()
    {
        query.append("MAXVALUE ");
        return this;
    }
    
    /**
     * Append the query with MAXVALUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query maxvalue(String c)
    {
        query.append("MAXVALUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMBLOB
     *
     * @return the object self
     */
    public Query mediumblob()
    {
        query.append("MEDIUMBLOB ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMBLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mediumblob(String c)
    {
        query.append("MEDIUMBLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMINT
     *
     * @return the object self
     */
    public Query mediumint()
    {
        query.append("MEDIUMINT ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mediumint(String c)
    {
        query.append("MEDIUMINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMTEXT
     *
     * @return the object self
     */
    public Query mediumtext()
    {
        query.append("MEDIUMTEXT ");
        return this;
    }
    
    /**
     * Append the query with MEDIUMTEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mediumtext(String c)
    {
        query.append("MEDIUMTEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MEMBER
     *
     * @return the object self
     */
    public Query member()
    {
        query.append("MEMBER ");
        return this;
    }
    
    /**
     * Append the query with MEMBER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query member(String c)
    {
        query.append("MEMBER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MERGE
     *
     * @return the object self
     */
    public Query merge()
    {
        query.append("MERGE ");
        return this;
    }
    
    /**
     * Append the query with MERGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query merge(String c)
    {
        query.append("MERGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_LENGTH
     *
     * @return the object self
     */
    public Query message_length()
    {
        query.append("MESSAGE_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query message_length(String c)
    {
        query.append("MESSAGE_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_OCTET_LENGTH
     *
     * @return the object self
     */
    public Query message_octet_length()
    {
        query.append("MESSAGE_OCTET_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_OCTET_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query message_octet_length(String c)
    {
        query.append("MESSAGE_OCTET_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_TEXT
     *
     * @return the object self
     */
    public Query message_text()
    {
        query.append("MESSAGE_TEXT ");
        return this;
    }
    
    /**
     * Append the query with MESSAGE_TEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query message_text(String c)
    {
        query.append("MESSAGE_TEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with METHOD
     *
     * @return the object self
     */
    public Query method()
    {
        query.append("METHOD ");
        return this;
    }
    
    /**
     * Append the query with METHOD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query method(String c)
    {
        query.append("METHOD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MIDDLEINT
     *
     * @return the object self
     */
    public Query middleint()
    {
        query.append("MIDDLEINT ");
        return this;
    }
    
    /**
     * Append the query with MIDDLEINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query middleint(String c)
    {
        query.append("MIDDLEINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MIN
     *
     * @return the object self
     */
    public Query min()
    {
        query.append("MIN ");
        return this;
    }
    
    /**
     * Append the query with MIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query min(String c)
    {
        query.append("MIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MIN_ROWS
     *
     * @return the object self
     */
    public Query min_rows()
    {
        query.append("MIN_ROWS ");
        return this;
    }
    
    /**
     * Append the query with MIN_ROWS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query min_rows(String c)
    {
        query.append("MIN_ROWS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MINUS
     *
     * @return the object self
     */
    public Query minus()
    {
        query.append("MINUS ");
        return this;
    }
    
    /**
     * Append the query with MINUS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query minus(String c)
    {
        query.append("MINUS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MINUTE
     *
     * @return the object self
     */
    public Query minute()
    {
        query.append("MINUTE ");
        return this;
    }
    
    /**
     * Append the query with MINUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query minute(String c)
    {
        query.append("MINUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MINUTE_MICROSECOND
     *
     * @return the object self
     */
    public Query minute_microsecond()
    {
        query.append("MINUTE_MICROSECOND ");
        return this;
    }
    
    /**
     * Append the query with MINUTE_MICROSECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query minute_microsecond(String c)
    {
        query.append("MINUTE_MICROSECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MINUTE_SECOND
     *
     * @return the object self
     */
    public Query minute_second()
    {
        query.append("MINUTE_SECOND ");
        return this;
    }
    
    /**
     * Append the query with MINUTE_SECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query minute_second(String c)
    {
        query.append("MINUTE_SECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MINVALUE
     *
     * @return the object self
     */
    public Query minvalue()
    {
        query.append("MINVALUE ");
        return this;
    }
    
    /**
     * Append the query with MINVALUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query minvalue(String c)
    {
        query.append("MINVALUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MLSLABEL
     *
     * @return the object self
     */
    public Query mlslabel()
    {
        query.append("MLSLABEL ");
        return this;
    }
    
    /**
     * Append the query with MLSLABEL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mlslabel(String c)
    {
        query.append("MLSLABEL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MOD
     *
     * @return the object self
     */
    public Query mod()
    {
        query.append("MOD ");
        return this;
    }
    
    /**
     * Append the query with MOD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mod(String c)
    {
        query.append("MOD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MODE
     *
     * @return the object self
     */
    public Query mode()
    {
        query.append("MODE ");
        return this;
    }
    
    /**
     * Append the query with MODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mode(String c)
    {
        query.append("MODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MODIFIES
     *
     * @return the object self
     */
    public Query modifies()
    {
        query.append("MODIFIES ");
        return this;
    }
    
    /**
     * Append the query with MODIFIES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query modifies(String c)
    {
        query.append("MODIFIES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MODIFY
     *
     * @return the object self
     */
    public Query modify()
    {
        query.append("MODIFY ");
        return this;
    }
    
    /**
     * Append the query with MODIFY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query modify(String c)
    {
        query.append("MODIFY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MODULE
     *
     * @return the object self
     */
    public Query module()
    {
        query.append("MODULE ");
        return this;
    }
    
    /**
     * Append the query with MODULE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query module(String c)
    {
        query.append("MODULE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MONTH
     *
     * @return the object self
     */
    public Query month()
    {
        query.append("MONTH ");
        return this;
    }
    
    /**
     * Append the query with MONTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query month(String c)
    {
        query.append("MONTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MONTHNAME
     *
     * @return the object self
     */
    public Query monthname()
    {
        query.append("MONTHNAME ");
        return this;
    }
    
    /**
     * Append the query with MONTHNAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query monthname(String c)
    {
        query.append("MONTHNAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MORE
     *
     * @return the object self
     */
    public Query more()
    {
        query.append("MORE ");
        return this;
    }
    
    /**
     * Append the query with MORE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query more(String c)
    {
        query.append("MORE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MOVE
     *
     * @return the object self
     */
    public Query move()
    {
        query.append("MOVE ");
        return this;
    }
    
    /**
     * Append the query with MOVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query move(String c)
    {
        query.append("MOVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MULTISET
     *
     * @return the object self
     */
    public Query multiset()
    {
        query.append("MULTISET ");
        return this;
    }
    
    /**
     * Append the query with MULTISET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query multiset(String c)
    {
        query.append("MULTISET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MUMPS
     *
     * @return the object self
     */
    public Query mumps()
    {
        query.append("MUMPS ");
        return this;
    }
    
    /**
     * Append the query with MUMPS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query mumps(String c)
    {
        query.append("MUMPS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with MYISAM
     *
     * @return the object self
     */
    public Query myisam()
    {
        query.append("MYISAM ");
        return this;
    }
    
    /**
     * Append the query with MYISAM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query myisam(String c)
    {
        query.append("MYISAM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NAME
     *
     * @return the object self
     */
    public Query name()
    {
        query.append("NAME ");
        return this;
    }
    
    /**
     * Append the query with NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query name(String c)
    {
        query.append("NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NAMES
     *
     * @return the object self
     */
    public Query names()
    {
        query.append("NAMES ");
        return this;
    }
    
    /**
     * Append the query with NAMES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query names(String c)
    {
        query.append("NAMES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NATIONAL
     *
     * @return the object self
     */
    public Query national()
    {
        query.append("NATIONAL ");
        return this;
    }
    
    /**
     * Append the query with NATIONAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query national(String c)
    {
        query.append("NATIONAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NATURAL
     *
     * @return the object self
     */
    public Query natural()
    {
        query.append("NATURAL ");
        return this;
    }
    
    /**
     * Append the query with NATURAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query natural(String c)
    {
        query.append("NATURAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NCHAR
     *
     * @return the object self
     */
    public Query nchar()
    {
        query.append("NCHAR ");
        return this;
    }
    
    /**
     * Append the query with NCHAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nchar(String c)
    {
        query.append("NCHAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NCLOB
     *
     * @return the object self
     */
    public Query nclob()
    {
        query.append("NCLOB ");
        return this;
    }
    
    /**
     * Append the query with NCLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nclob(String c)
    {
        query.append("NCLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NESTING
     *
     * @return the object self
     */
    public Query nesting()
    {
        query.append("NESTING ");
        return this;
    }
    
    /**
     * Append the query with NESTING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nesting(String c)
    {
        query.append("NESTING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NEW
     *
     * @return the object self
     */
    public Query new_()
    {
        query.append("NEW ");
        return this;
    }
    
    /**
     * Append the query with NEW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query new_(String c)
    {
        query.append("NEW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NEXT
     *
     * @return the object self
     */
    public Query next()
    {
        query.append("NEXT ");
        return this;
    }
    
    /**
     * Append the query with NEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query next(String c)
    {
        query.append("NEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NO
     *
     * @return the object self
     */
    public Query no()
    {
        query.append("NO ");
        return this;
    }
    
    /**
     * Append the query with NO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query no(String c)
    {
        query.append("NO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NO_WRITE_TO_BINLOG
     *
     * @return the object self
     */
    public Query no_write_to_binlog()
    {
        query.append("NO_WRITE_TO_BINLOG ");
        return this;
    }
    
    /**
     * Append the query with NO_WRITE_TO_BINLOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query no_write_to_binlog(String c)
    {
        query.append("NO_WRITE_TO_BINLOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOAUDIT
     *
     * @return the object self
     */
    public Query noaudit()
    {
        query.append("NOAUDIT ");
        return this;
    }
    
    /**
     * Append the query with NOAUDIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query noaudit(String c)
    {
        query.append("NOAUDIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOCHECK
     *
     * @return the object self
     */
    public Query nocheck()
    {
        query.append("NOCHECK ");
        return this;
    }
    
    /**
     * Append the query with NOCHECK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nocheck(String c)
    {
        query.append("NOCHECK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOCOMPRESS
     *
     * @return the object self
     */
    public Query nocompress()
    {
        query.append("NOCOMPRESS ");
        return this;
    }
    
    /**
     * Append the query with NOCOMPRESS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nocompress(String c)
    {
        query.append("NOCOMPRESS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEDB
     *
     * @return the object self
     */
    public Query nocreatedb()
    {
        query.append("NOCREATEDB ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEDB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nocreatedb(String c)
    {
        query.append("NOCREATEDB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEROLE
     *
     * @return the object self
     */
    public Query nocreaterole()
    {
        query.append("NOCREATEROLE ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEROLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nocreaterole(String c)
    {
        query.append("NOCREATEROLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEUSER
     *
     * @return the object self
     */
    public Query nocreateuser()
    {
        query.append("NOCREATEUSER ");
        return this;
    }
    
    /**
     * Append the query with NOCREATEUSER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nocreateuser(String c)
    {
        query.append("NOCREATEUSER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOINHERIT
     *
     * @return the object self
     */
    public Query noinherit()
    {
        query.append("NOINHERIT ");
        return this;
    }
    
    /**
     * Append the query with NOINHERIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query noinherit(String c)
    {
        query.append("NOINHERIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOLOGIN
     *
     * @return the object self
     */
    public Query nologin()
    {
        query.append("NOLOGIN ");
        return this;
    }
    
    /**
     * Append the query with NOLOGIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nologin(String c)
    {
        query.append("NOLOGIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NONCLUSTERED
     *
     * @return the object self
     */
    public Query nonclustered()
    {
        query.append("NONCLUSTERED ");
        return this;
    }
    
    /**
     * Append the query with NONCLUSTERED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nonclustered(String c)
    {
        query.append("NONCLUSTERED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NONE
     *
     * @return the object self
     */
    public Query none()
    {
        query.append("NONE ");
        return this;
    }
    
    /**
     * Append the query with NONE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query none(String c)
    {
        query.append("NONE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NORMALIZE
     *
     * @return the object self
     */
    public Query normalize()
    {
        query.append("NORMALIZE ");
        return this;
    }
    
    /**
     * Append the query with NORMALIZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query normalize(String c)
    {
        query.append("NORMALIZE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NORMALIZED
     *
     * @return the object self
     */
    public Query normalized()
    {
        query.append("NORMALIZED ");
        return this;
    }
    
    /**
     * Append the query with NORMALIZED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query normalized(String c)
    {
        query.append("NORMALIZED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOSUPERUSER
     *
     * @return the object self
     */
    public Query nosuperuser()
    {
        query.append("NOSUPERUSER ");
        return this;
    }
    
    /**
     * Append the query with NOSUPERUSER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nosuperuser(String c)
    {
        query.append("NOSUPERUSER ").append(c).append(" ");
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
     * Append the query with NOTHING
     *
     * @return the object self
     */
    public Query nothing()
    {
        query.append("NOTHING ");
        return this;
    }
    
    /**
     * Append the query with NOTHING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nothing(String c)
    {
        query.append("NOTHING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOTIFY
     *
     * @return the object self
     */
    public Query notify_()
    {
        query.append("NOTIFY ");
        return this;
    }
    
    /**
     * Append the query with NOTIFY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query notify_(String c)
    {
        query.append("NOTIFY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOTNULL
     *
     * @return the object self
     */
    public Query notnull()
    {
        query.append("NOTNULL ");
        return this;
    }
    
    /**
     * Append the query with NOTNULL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query notnull(String c)
    {
        query.append("NOTNULL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NOWAIT
     *
     * @return the object self
     */
    public Query nowait()
    {
        query.append("NOWAIT ");
        return this;
    }
    
    /**
     * Append the query with NOWAIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nowait(String c)
    {
        query.append("NOWAIT ").append(c).append(" ");
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
     * Append the query with NULLABLE
     *
     * @return the object self
     */
    public Query nullable()
    {
        query.append("NULLABLE ");
        return this;
    }
    
    /**
     * Append the query with NULLABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nullable(String c)
    {
        query.append("NULLABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NULLIF
     *
     * @return the object self
     */
    public Query nullif()
    {
        query.append("NULLIF ");
        return this;
    }
    
    /**
     * Append the query with NULLIF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query nullif(String c)
    {
        query.append("NULLIF ").append(c).append(" ");
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
     * Append the query with NUMBER
     *
     * @return the object self
     */
    public Query number()
    {
        query.append("NUMBER ");
        return this;
    }
    
    /**
     * Append the query with NUMBER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query number(String c)
    {
        query.append("NUMBER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with NUMERIC
     *
     * @return the object self
     */
    public Query numeric()
    {
        query.append("NUMERIC ");
        return this;
    }
    
    /**
     * Append the query with NUMERIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query numeric(String c)
    {
        query.append("NUMERIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OBJECT
     *
     * @return the object self
     */
    public Query object()
    {
        query.append("OBJECT ");
        return this;
    }
    
    /**
     * Append the query with OBJECT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query object(String c)
    {
        query.append("OBJECT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OCTET_LENGTH
     *
     * @return the object self
     */
    public Query octet_length()
    {
        query.append("OCTET_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with OCTET_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query octet_length(String c)
    {
        query.append("OCTET_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OCTETS
     *
     * @return the object self
     */
    public Query octets()
    {
        query.append("OCTETS ");
        return this;
    }
    
    /**
     * Append the query with OCTETS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query octets(String c)
    {
        query.append("OCTETS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OF
     *
     * @return the object self
     */
    public Query of()
    {
        query.append("OF ");
        return this;
    }
    
    /**
     * Append the query with OF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query of(String c)
    {
        query.append("OF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OFF
     *
     * @return the object self
     */
    public Query off()
    {
        query.append("OFF ");
        return this;
    }
    
    /**
     * Append the query with OFF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query off(String c)
    {
        query.append("OFF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OFFLINE
     *
     * @return the object self
     */
    public Query offline()
    {
        query.append("OFFLINE ");
        return this;
    }
    
    /**
     * Append the query with OFFLINE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query offline(String c)
    {
        query.append("OFFLINE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OFFSET
     *
     * @return the object self
     */
    public Query offset()
    {
        query.append("OFFSET ");
        return this;
    }
    
    /**
     * Append the query with OFFSET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query offset(String c)
    {
        query.append("OFFSET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OFFSETS
     *
     * @return the object self
     */
    public Query offsets()
    {
        query.append("OFFSETS ");
        return this;
    }
    
    /**
     * Append the query with OFFSETS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query offsets(String c)
    {
        query.append("OFFSETS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OIDS
     *
     * @return the object self
     */
    public Query oids()
    {
        query.append("OIDS ");
        return this;
    }
    
    /**
     * Append the query with OIDS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query oids(String c)
    {
        query.append("OIDS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OLD
     *
     * @return the object self
     */
    public Query old()
    {
        query.append("OLD ");
        return this;
    }
    
    /**
     * Append the query with OLD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query old(String c)
    {
        query.append("OLD ").append(c).append(" ");
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
     * Append the query with ONLINE
     *
     * @return the object self
     */
    public Query online()
    {
        query.append("ONLINE ");
        return this;
    }
    
    /**
     * Append the query with ONLINE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query online(String c)
    {
        query.append("ONLINE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ONLY
     *
     * @return the object self
     */
    public Query only()
    {
        query.append("ONLY ");
        return this;
    }
    
    /**
     * Append the query with ONLY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query only(String c)
    {
        query.append("ONLY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPEN
     *
     * @return the object self
     */
    public Query open()
    {
        query.append("OPEN ");
        return this;
    }
    
    /**
     * Append the query with OPEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query open(String c)
    {
        query.append("OPEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPENDATASOURCE
     *
     * @return the object self
     */
    public Query opendatasource()
    {
        query.append("OPENDATASOURCE ");
        return this;
    }
    
    /**
     * Append the query with OPENDATASOURCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query opendatasource(String c)
    {
        query.append("OPENDATASOURCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPENQUERY
     *
     * @return the object self
     */
    public Query openquery()
    {
        query.append("OPENQUERY ");
        return this;
    }
    
    /**
     * Append the query with OPENQUERY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query openquery(String c)
    {
        query.append("OPENQUERY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPENROWSET
     *
     * @return the object self
     */
    public Query openrowset()
    {
        query.append("OPENROWSET ");
        return this;
    }
    
    /**
     * Append the query with OPENROWSET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query openrowset(String c)
    {
        query.append("OPENROWSET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPENXML
     *
     * @return the object self
     */
    public Query openxml()
    {
        query.append("OPENXML ");
        return this;
    }
    
    /**
     * Append the query with OPENXML statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query openxml(String c)
    {
        query.append("OPENXML ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPERATION
     *
     * @return the object self
     */
    public Query operation()
    {
        query.append("OPERATION ");
        return this;
    }
    
    /**
     * Append the query with OPERATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query operation(String c)
    {
        query.append("OPERATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPERATOR
     *
     * @return the object self
     */
    public Query operator()
    {
        query.append("OPERATOR ");
        return this;
    }
    
    /**
     * Append the query with OPERATOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query operator(String c)
    {
        query.append("OPERATOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPTIMIZE
     *
     * @return the object self
     */
    public Query optimize()
    {
        query.append("OPTIMIZE ");
        return this;
    }
    
    /**
     * Append the query with OPTIMIZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query optimize(String c)
    {
        query.append("OPTIMIZE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPTION
     *
     * @return the object self
     */
    public Query option()
    {
        query.append("OPTION ");
        return this;
    }
    
    /**
     * Append the query with OPTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query option(String c)
    {
        query.append("OPTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPTIONALLY
     *
     * @return the object self
     */
    public Query optionally()
    {
        query.append("OPTIONALLY ");
        return this;
    }
    
    /**
     * Append the query with OPTIONALLY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query optionally(String c)
    {
        query.append("OPTIONALLY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OPTIONS
     *
     * @return the object self
     */
    public Query options()
    {
        query.append("OPTIONS ");
        return this;
    }
    
    /**
     * Append the query with OPTIONS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query options(String c)
    {
        query.append("OPTIONS ").append(c).append(" ");
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
     * Append the query with ORDER
     *
     * @return the object self
     */
    public Query order()
    {
        query.append("ORDER ");
        return this;
    }
    
    /**
     * Append the query with ORDER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query order(String c)
    {
        query.append("ORDER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ORDERING
     *
     * @return the object self
     */
    public Query ordering()
    {
        query.append("ORDERING ");
        return this;
    }
    
    /**
     * Append the query with ORDERING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ordering(String c)
    {
        query.append("ORDERING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ORDINALITY
     *
     * @return the object self
     */
    public Query ordinality()
    {
        query.append("ORDINALITY ");
        return this;
    }
    
    /**
     * Append the query with ORDINALITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ordinality(String c)
    {
        query.append("ORDINALITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OTHERS
     *
     * @return the object self
     */
    public Query others()
    {
        query.append("OTHERS ");
        return this;
    }
    
    /**
     * Append the query with OTHERS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query others(String c)
    {
        query.append("OTHERS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OUT
     *
     * @return the object self
     */
    public Query out()
    {
        query.append("OUT ");
        return this;
    }
    
    /**
     * Append the query with OUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query out(String c)
    {
        query.append("OUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OUTER
     *
     * @return the object self
     */
    public Query outer()
    {
        query.append("OUTER ");
        return this;
    }
    
    /**
     * Append the query with OUTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query outer(String c)
    {
        query.append("OUTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OUTFILE
     *
     * @return the object self
     */
    public Query outfile()
    {
        query.append("OUTFILE ");
        return this;
    }
    
    /**
     * Append the query with OUTFILE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query outfile(String c)
    {
        query.append("OUTFILE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OUTPUT
     *
     * @return the object self
     */
    public Query output()
    {
        query.append("OUTPUT ");
        return this;
    }
    
    /**
     * Append the query with OUTPUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query output(String c)
    {
        query.append("OUTPUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OVER
     *
     * @return the object self
     */
    public Query over()
    {
        query.append("OVER ");
        return this;
    }
    
    /**
     * Append the query with OVER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query over(String c)
    {
        query.append("OVER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OVERLAPS
     *
     * @return the object self
     */
    public Query overlaps()
    {
        query.append("OVERLAPS ");
        return this;
    }
    
    /**
     * Append the query with OVERLAPS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query overlaps(String c)
    {
        query.append("OVERLAPS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OVERLAY
     *
     * @return the object self
     */
    public Query overlay()
    {
        query.append("OVERLAY ");
        return this;
    }
    
    /**
     * Append the query with OVERLAY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query overlay(String c)
    {
        query.append("OVERLAY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OVERRIDING
     *
     * @return the object self
     */
    public Query overriding()
    {
        query.append("OVERRIDING ");
        return this;
    }
    
    /**
     * Append the query with OVERRIDING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query overriding(String c)
    {
        query.append("OVERRIDING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with OWNER
     *
     * @return the object self
     */
    public Query owner()
    {
        query.append("OWNER ");
        return this;
    }
    
    /**
     * Append the query with OWNER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query owner(String c)
    {
        query.append("OWNER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PACK_KEYS
     *
     * @return the object self
     */
    public Query pack_keys()
    {
        query.append("PACK_KEYS ");
        return this;
    }
    
    /**
     * Append the query with PACK_KEYS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query pack_keys(String c)
    {
        query.append("PACK_KEYS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PAD
     *
     * @return the object self
     */
    public Query pad()
    {
        query.append("PAD ");
        return this;
    }
    
    /**
     * Append the query with PAD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query pad(String c)
    {
        query.append("PAD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER
     *
     * @return the object self
     */
    public Query parameter()
    {
        query.append("PARAMETER ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter(String c)
    {
        query.append("PARAMETER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_MODE
     *
     * @return the object self
     */
    public Query parameter_mode()
    {
        query.append("PARAMETER_MODE ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_MODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_mode(String c)
    {
        query.append("PARAMETER_MODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_NAME
     *
     * @return the object self
     */
    public Query parameter_name()
    {
        query.append("PARAMETER_NAME ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_name(String c)
    {
        query.append("PARAMETER_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_ORDINAL_POSITION
     *
     * @return the object self
     */
    public Query parameter_ordinal_position()
    {
        query.append("PARAMETER_ORDINAL_POSITION ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_ORDINAL_POSITION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_ordinal_position(String c)
    {
        query.append("PARAMETER_ORDINAL_POSITION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_CATALOG
     *
     * @return the object self
     */
    public Query parameter_specific_catalog()
    {
        query.append("PARAMETER_SPECIFIC_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_specific_catalog(String c)
    {
        query.append("PARAMETER_SPECIFIC_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_NAME
     *
     * @return the object self
     */
    public Query parameter_specific_name()
    {
        query.append("PARAMETER_SPECIFIC_NAME ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_specific_name(String c)
    {
        query.append("PARAMETER_SPECIFIC_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_SCHEMA
     *
     * @return the object self
     */
    public Query parameter_specific_schema()
    {
        query.append("PARAMETER_SPECIFIC_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with PARAMETER_SPECIFIC_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameter_specific_schema(String c)
    {
        query.append("PARAMETER_SPECIFIC_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARAMETERS
     *
     * @return the object self
     */
    public Query parameters()
    {
        query.append("PARAMETERS ");
        return this;
    }
    
    /**
     * Append the query with PARAMETERS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query parameters(String c)
    {
        query.append("PARAMETERS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARTIAL
     *
     * @return the object self
     */
    public Query partial()
    {
        query.append("PARTIAL ");
        return this;
    }
    
    /**
     * Append the query with PARTIAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query partial(String c)
    {
        query.append("PARTIAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PARTITION
     *
     * @return the object self
     */
    public Query partition()
    {
        query.append("PARTITION ");
        return this;
    }
    
    /**
     * Append the query with PARTITION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query partition(String c)
    {
        query.append("PARTITION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PASCAL
     *
     * @return the object self
     */
    public Query pascal()
    {
        query.append("PASCAL ");
        return this;
    }
    
    /**
     * Append the query with PASCAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query pascal(String c)
    {
        query.append("PASCAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PASSWORD
     *
     * @return the object self
     */
    public Query password()
    {
        query.append("PASSWORD ");
        return this;
    }
    
    /**
     * Append the query with PASSWORD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query password(String c)
    {
        query.append("PASSWORD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PATH
     *
     * @return the object self
     */
    public Query path()
    {
        query.append("PATH ");
        return this;
    }
    
    /**
     * Append the query with PATH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query path(String c)
    {
        query.append("PATH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PCTFREE
     *
     * @return the object self
     */
    public Query pctfree()
    {
        query.append("PCTFREE ");
        return this;
    }
    
    /**
     * Append the query with PCTFREE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query pctfree(String c)
    {
        query.append("PCTFREE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PERCENT
     *
     * @return the object self
     */
    public Query percent()
    {
        query.append("PERCENT ");
        return this;
    }
    
    /**
     * Append the query with PERCENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query percent(String c)
    {
        query.append("PERCENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PERCENT_RANK
     *
     * @return the object self
     */
    public Query percent_rank()
    {
        query.append("PERCENT_RANK ");
        return this;
    }
    
    /**
     * Append the query with PERCENT_RANK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query percent_rank(String c)
    {
        query.append("PERCENT_RANK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PERCENTILE_CONT
     *
     * @return the object self
     */
    public Query percentile_cont()
    {
        query.append("PERCENTILE_CONT ");
        return this;
    }
    
    /**
     * Append the query with PERCENTILE_CONT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query percentile_cont(String c)
    {
        query.append("PERCENTILE_CONT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PERCENTILE_DISC
     *
     * @return the object self
     */
    public Query percentile_disc()
    {
        query.append("PERCENTILE_DISC ");
        return this;
    }
    
    /**
     * Append the query with PERCENTILE_DISC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query percentile_disc(String c)
    {
        query.append("PERCENTILE_DISC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PLACING
     *
     * @return the object self
     */
    public Query placing()
    {
        query.append("PLACING ");
        return this;
    }
    
    /**
     * Append the query with PLACING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query placing(String c)
    {
        query.append("PLACING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PLAN
     *
     * @return the object self
     */
    public Query plan()
    {
        query.append("PLAN ");
        return this;
    }
    
    /**
     * Append the query with PLAN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query plan(String c)
    {
        query.append("PLAN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PLI
     *
     * @return the object self
     */
    public Query pli()
    {
        query.append("PLI ");
        return this;
    }
    
    /**
     * Append the query with PLI statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query pli(String c)
    {
        query.append("PLI ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with POSITION
     *
     * @return the object self
     */
    public Query position()
    {
        query.append("POSITION ");
        return this;
    }
    
    /**
     * Append the query with POSITION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query position(String c)
    {
        query.append("POSITION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with POSTFIX
     *
     * @return the object self
     */
    public Query postfix()
    {
        query.append("POSTFIX ");
        return this;
    }
    
    /**
     * Append the query with POSTFIX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query postfix(String c)
    {
        query.append("POSTFIX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with POWER
     *
     * @return the object self
     */
    public Query power()
    {
        query.append("POWER ");
        return this;
    }
    
    /**
     * Append the query with POWER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query power(String c)
    {
        query.append("POWER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRECEDING
     *
     * @return the object self
     */
    public Query preceding()
    {
        query.append("PRECEDING ");
        return this;
    }
    
    /**
     * Append the query with PRECEDING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query preceding(String c)
    {
        query.append("PRECEDING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRECISION
     *
     * @return the object self
     */
    public Query precision()
    {
        query.append("PRECISION ");
        return this;
    }
    
    /**
     * Append the query with PRECISION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query precision(String c)
    {
        query.append("PRECISION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PREFIX
     *
     * @return the object self
     */
    public Query prefix()
    {
        query.append("PREFIX ");
        return this;
    }
    
    /**
     * Append the query with PREFIX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query prefix(String c)
    {
        query.append("PREFIX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PREORDER
     *
     * @return the object self
     */
    public Query preorder()
    {
        query.append("PREORDER ");
        return this;
    }
    
    /**
     * Append the query with PREORDER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query preorder(String c)
    {
        query.append("PREORDER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PREPARE
     *
     * @return the object self
     */
    public Query prepare()
    {
        query.append("PREPARE ");
        return this;
    }
    
    /**
     * Append the query with PREPARE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query prepare(String c)
    {
        query.append("PREPARE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PREPARED
     *
     * @return the object self
     */
    public Query prepared()
    {
        query.append("PREPARED ");
        return this;
    }
    
    /**
     * Append the query with PREPARED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query prepared(String c)
    {
        query.append("PREPARED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRESERVE
     *
     * @return the object self
     */
    public Query preserve()
    {
        query.append("PRESERVE ");
        return this;
    }
    
    /**
     * Append the query with PRESERVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query preserve(String c)
    {
        query.append("PRESERVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRIMARY
     *
     * @return the object self
     */
    public Query primary()
    {
        query.append("PRIMARY ");
        return this;
    }
    
    /**
     * Append the query with PRIMARY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query primary(String c)
    {
        query.append("PRIMARY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRINT
     *
     * @return the object self
     */
    public Query print()
    {
        query.append("PRINT ");
        return this;
    }
    
    /**
     * Append the query with PRINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query print(String c)
    {
        query.append("PRINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRIOR
     *
     * @return the object self
     */
    public Query prior()
    {
        query.append("PRIOR ");
        return this;
    }
    
    /**
     * Append the query with PRIOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query prior(String c)
    {
        query.append("PRIOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PRIVILEGES
     *
     * @return the object self
     */
    public Query privileges()
    {
        query.append("PRIVILEGES ");
        return this;
    }
    
    /**
     * Append the query with PRIVILEGES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query privileges(String c)
    {
        query.append("PRIVILEGES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PROC
     *
     * @return the object self
     */
    public Query proc()
    {
        query.append("PROC ");
        return this;
    }
    
    /**
     * Append the query with PROC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query proc(String c)
    {
        query.append("PROC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PROCEDURAL
     *
     * @return the object self
     */
    public Query procedural()
    {
        query.append("PROCEDURAL ");
        return this;
    }
    
    /**
     * Append the query with PROCEDURAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query procedural(String c)
    {
        query.append("PROCEDURAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PROCEDURE
     *
     * @return the object self
     */
    public Query procedure()
    {
        query.append("PROCEDURE ");
        return this;
    }
    
    /**
     * Append the query with PROCEDURE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query procedure(String c)
    {
        query.append("PROCEDURE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PROCESS
     *
     * @return the object self
     */
    public Query process()
    {
        query.append("PROCESS ");
        return this;
    }
    
    /**
     * Append the query with PROCESS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query process(String c)
    {
        query.append("PROCESS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PROCESSLIST
     *
     * @return the object self
     */
    public Query processlist()
    {
        query.append("PROCESSLIST ");
        return this;
    }
    
    /**
     * Append the query with PROCESSLIST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query processlist(String c)
    {
        query.append("PROCESSLIST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PUBLIC
     *
     * @return the object self
     */
    public Query public_()
    {
        query.append("PUBLIC ");
        return this;
    }
    
    /**
     * Append the query with PUBLIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query public_(String c)
    {
        query.append("PUBLIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with PURGE
     *
     * @return the object self
     */
    public Query purge()
    {
        query.append("PURGE ");
        return this;
    }
    
    /**
     * Append the query with PURGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query purge(String c)
    {
        query.append("PURGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with QUOTE
     *
     * @return the object self
     */
    public Query quote()
    {
        query.append("QUOTE ");
        return this;
    }
    
    /**
     * Append the query with QUOTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query quote(String c)
    {
        query.append("QUOTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RAID0
     *
     * @return the object self
     */
    public Query raid0()
    {
        query.append("RAID0 ");
        return this;
    }
    
    /**
     * Append the query with RAID0 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query raid0(String c)
    {
        query.append("RAID0 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RAISERROR
     *
     * @return the object self
     */
    public Query raiserror()
    {
        query.append("RAISERROR ");
        return this;
    }
    
    /**
     * Append the query with RAISERROR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query raiserror(String c)
    {
        query.append("RAISERROR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RANGE
     *
     * @return the object self
     */
    public Query range()
    {
        query.append("RANGE ");
        return this;
    }
    
    /**
     * Append the query with RANGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query range(String c)
    {
        query.append("RANGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RANK
     *
     * @return the object self
     */
    public Query rank()
    {
        query.append("RANK ");
        return this;
    }
    
    /**
     * Append the query with RANK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rank(String c)
    {
        query.append("RANK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RAW
     *
     * @return the object self
     */
    public Query raw()
    {
        query.append("RAW ");
        return this;
    }
    
    /**
     * Append the query with RAW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query raw(String c)
    {
        query.append("RAW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with READ
     *
     * @return the object self
     */
    public Query read()
    {
        query.append("READ ");
        return this;
    }
    
    /**
     * Append the query with READ statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query read(String c)
    {
        query.append("READ ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with READS
     *
     * @return the object self
     */
    public Query reads()
    {
        query.append("READS ");
        return this;
    }
    
    /**
     * Append the query with READS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query reads(String c)
    {
        query.append("READS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with READTEXT
     *
     * @return the object self
     */
    public Query readtext()
    {
        query.append("READTEXT ");
        return this;
    }
    
    /**
     * Append the query with READTEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query readtext(String c)
    {
        query.append("READTEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REAL
     *
     * @return the object self
     */
    public Query real()
    {
        query.append("REAL ");
        return this;
    }
    
    /**
     * Append the query with REAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query real(String c)
    {
        query.append("REAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RECHECK
     *
     * @return the object self
     */
    public Query recheck()
    {
        query.append("RECHECK ");
        return this;
    }
    
    /**
     * Append the query with RECHECK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query recheck(String c)
    {
        query.append("RECHECK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RECONFIGURE
     *
     * @return the object self
     */
    public Query reconfigure()
    {
        query.append("RECONFIGURE ");
        return this;
    }
    
    /**
     * Append the query with RECONFIGURE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query reconfigure(String c)
    {
        query.append("RECONFIGURE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RECURSIVE
     *
     * @return the object self
     */
    public Query recursive()
    {
        query.append("RECURSIVE ");
        return this;
    }
    
    /**
     * Append the query with RECURSIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query recursive(String c)
    {
        query.append("RECURSIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REF
     *
     * @return the object self
     */
    public Query ref()
    {
        query.append("REF ");
        return this;
    }
    
    /**
     * Append the query with REF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ref(String c)
    {
        query.append("REF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REFERENCES
     *
     * @return the object self
     */
    public Query references()
    {
        query.append("REFERENCES ");
        return this;
    }
    
    /**
     * Append the query with REFERENCES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query references(String c)
    {
        query.append("REFERENCES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REFERENCING
     *
     * @return the object self
     */
    public Query referencing()
    {
        query.append("REFERENCING ");
        return this;
    }
    
    /**
     * Append the query with REFERENCING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query referencing(String c)
    {
        query.append("REFERENCING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGEXP
     *
     * @return the object self
     */
    public Query regexp()
    {
        query.append("REGEXP ");
        return this;
    }
    
    /**
     * Append the query with REGEXP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regexp(String c)
    {
        query.append("REGEXP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_AVGX
     *
     * @return the object self
     */
    public Query regr_avgx()
    {
        query.append("REGR_AVGX ");
        return this;
    }
    
    /**
     * Append the query with REGR_AVGX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_avgx(String c)
    {
        query.append("REGR_AVGX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_AVGY
     *
     * @return the object self
     */
    public Query regr_avgy()
    {
        query.append("REGR_AVGY ");
        return this;
    }
    
    /**
     * Append the query with REGR_AVGY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_avgy(String c)
    {
        query.append("REGR_AVGY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_COUNT
     *
     * @return the object self
     */
    public Query regr_count()
    {
        query.append("REGR_COUNT ");
        return this;
    }
    
    /**
     * Append the query with REGR_COUNT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_count(String c)
    {
        query.append("REGR_COUNT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_INTERCEPT
     *
     * @return the object self
     */
    public Query regr_intercept()
    {
        query.append("REGR_INTERCEPT ");
        return this;
    }
    
    /**
     * Append the query with REGR_INTERCEPT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_intercept(String c)
    {
        query.append("REGR_INTERCEPT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_R2
     *
     * @return the object self
     */
    public Query regr_r2()
    {
        query.append("REGR_R2 ");
        return this;
    }
    
    /**
     * Append the query with REGR_R2 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_r2(String c)
    {
        query.append("REGR_R2 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_SLOPE
     *
     * @return the object self
     */
    public Query regr_slope()
    {
        query.append("REGR_SLOPE ");
        return this;
    }
    
    /**
     * Append the query with REGR_SLOPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_slope(String c)
    {
        query.append("REGR_SLOPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_SXX
     *
     * @return the object self
     */
    public Query regr_sxx()
    {
        query.append("REGR_SXX ");
        return this;
    }
    
    /**
     * Append the query with REGR_SXX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_sxx(String c)
    {
        query.append("REGR_SXX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_SXY
     *
     * @return the object self
     */
    public Query regr_sxy()
    {
        query.append("REGR_SXY ");
        return this;
    }
    
    /**
     * Append the query with REGR_SXY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_sxy(String c)
    {
        query.append("REGR_SXY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REGR_SYY
     *
     * @return the object self
     */
    public Query regr_syy()
    {
        query.append("REGR_SYY ");
        return this;
    }
    
    /**
     * Append the query with REGR_SYY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query regr_syy(String c)
    {
        query.append("REGR_SYY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REINDEX
     *
     * @return the object self
     */
    public Query reindex()
    {
        query.append("REINDEX ");
        return this;
    }
    
    /**
     * Append the query with REINDEX statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query reindex(String c)
    {
        query.append("REINDEX ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RELATIVE
     *
     * @return the object self
     */
    public Query relative()
    {
        query.append("RELATIVE ");
        return this;
    }
    
    /**
     * Append the query with RELATIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query relative(String c)
    {
        query.append("RELATIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RELEASE
     *
     * @return the object self
     */
    public Query release()
    {
        query.append("RELEASE ");
        return this;
    }
    
    /**
     * Append the query with RELEASE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query release(String c)
    {
        query.append("RELEASE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RELOAD
     *
     * @return the object self
     */
    public Query reload()
    {
        query.append("RELOAD ");
        return this;
    }
    
    /**
     * Append the query with RELOAD statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query reload(String c)
    {
        query.append("RELOAD ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RENAME
     *
     * @return the object self
     */
    public Query rename()
    {
        query.append("RENAME ");
        return this;
    }
    
    /**
     * Append the query with RENAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rename(String c)
    {
        query.append("RENAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REPEAT
     *
     * @return the object self
     */
    public Query repeat()
    {
        query.append("REPEAT ");
        return this;
    }
    
    /**
     * Append the query with REPEAT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query repeat(String c)
    {
        query.append("REPEAT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REPEATABLE
     *
     * @return the object self
     */
    public Query repeatable()
    {
        query.append("REPEATABLE ");
        return this;
    }
    
    /**
     * Append the query with REPEATABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query repeatable(String c)
    {
        query.append("REPEATABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REPLACE
     *
     * @return the object self
     */
    public Query replace()
    {
        query.append("REPLACE ");
        return this;
    }
    
    /**
     * Append the query with REPLACE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query replace(String c)
    {
        query.append("REPLACE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REPLICATION
     *
     * @return the object self
     */
    public Query replication()
    {
        query.append("REPLICATION ");
        return this;
    }
    
    /**
     * Append the query with REPLICATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query replication(String c)
    {
        query.append("REPLICATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REQUIRE
     *
     * @return the object self
     */
    public Query require()
    {
        query.append("REQUIRE ");
        return this;
    }
    
    /**
     * Append the query with REQUIRE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query require(String c)
    {
        query.append("REQUIRE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESET
     *
     * @return the object self
     */
    public Query reset()
    {
        query.append("RESET ");
        return this;
    }
    
    /**
     * Append the query with RESET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query reset(String c)
    {
        query.append("RESET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESIGNAL
     *
     * @return the object self
     */
    public Query resignal()
    {
        query.append("RESIGNAL ");
        return this;
    }
    
    /**
     * Append the query with RESIGNAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query resignal(String c)
    {
        query.append("RESIGNAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESOURCE
     *
     * @return the object self
     */
    public Query resource()
    {
        query.append("RESOURCE ");
        return this;
    }
    
    /**
     * Append the query with RESOURCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query resource(String c)
    {
        query.append("RESOURCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESTART
     *
     * @return the object self
     */
    public Query restart()
    {
        query.append("RESTART ");
        return this;
    }
    
    /**
     * Append the query with RESTART statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query restart(String c)
    {
        query.append("RESTART ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESTORE
     *
     * @return the object self
     */
    public Query restore()
    {
        query.append("RESTORE ");
        return this;
    }
    
    /**
     * Append the query with RESTORE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query restore(String c)
    {
        query.append("RESTORE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESTRICT
     *
     * @return the object self
     */
    public Query restrict()
    {
        query.append("RESTRICT ");
        return this;
    }
    
    /**
     * Append the query with RESTRICT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query restrict(String c)
    {
        query.append("RESTRICT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RESULT
     *
     * @return the object self
     */
    public Query result()
    {
        query.append("RESULT ");
        return this;
    }
    
    /**
     * Append the query with RESULT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query result(String c)
    {
        query.append("RESULT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURN
     *
     * @return the object self
     */
    public Query return_()
    {
        query.append("RETURN ");
        return this;
    }
    
    /**
     * Append the query with RETURN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query return_(String c)
    {
        query.append("RETURN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_CARDINALITY
     *
     * @return the object self
     */
    public Query returned_cardinality()
    {
        query.append("RETURNED_CARDINALITY ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_CARDINALITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query returned_cardinality(String c)
    {
        query.append("RETURNED_CARDINALITY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_LENGTH
     *
     * @return the object self
     */
    public Query returned_length()
    {
        query.append("RETURNED_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query returned_length(String c)
    {
        query.append("RETURNED_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_OCTET_LENGTH
     *
     * @return the object self
     */
    public Query returned_octet_length()
    {
        query.append("RETURNED_OCTET_LENGTH ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_OCTET_LENGTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query returned_octet_length(String c)
    {
        query.append("RETURNED_OCTET_LENGTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_SQLSTATE
     *
     * @return the object self
     */
    public Query returned_sqlstate()
    {
        query.append("RETURNED_SQLSTATE ");
        return this;
    }
    
    /**
     * Append the query with RETURNED_SQLSTATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query returned_sqlstate(String c)
    {
        query.append("RETURNED_SQLSTATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RETURNS
     *
     * @return the object self
     */
    public Query returns()
    {
        query.append("RETURNS ");
        return this;
    }
    
    /**
     * Append the query with RETURNS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query returns(String c)
    {
        query.append("RETURNS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with REVOKE
     *
     * @return the object self
     */
    public Query revoke()
    {
        query.append("REVOKE ");
        return this;
    }
    
    /**
     * Append the query with REVOKE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query revoke(String c)
    {
        query.append("REVOKE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RIGHT
     *
     * @return the object self
     */
    public Query right()
    {
        query.append("RIGHT ");
        return this;
    }
    
    /**
     * Append the query with RIGHT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query right(String c)
    {
        query.append("RIGHT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RLIKE
     *
     * @return the object self
     */
    public Query rlike()
    {
        query.append("RLIKE ");
        return this;
    }
    
    /**
     * Append the query with RLIKE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rlike(String c)
    {
        query.append("RLIKE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROLE
     *
     * @return the object self
     */
    public Query role()
    {
        query.append("ROLE ");
        return this;
    }
    
    /**
     * Append the query with ROLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query role(String c)
    {
        query.append("ROLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROLLBACK
     *
     * @return the object self
     */
    public Query rollback()
    {
        query.append("ROLLBACK ");
        return this;
    }
    
    /**
     * Append the query with ROLLBACK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rollback(String c)
    {
        query.append("ROLLBACK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROLLUP
     *
     * @return the object self
     */
    public Query rollup()
    {
        query.append("ROLLUP ");
        return this;
    }
    
    /**
     * Append the query with ROLLUP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rollup(String c)
    {
        query.append("ROLLUP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE
     *
     * @return the object self
     */
    public Query routine()
    {
        query.append("ROUTINE ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query routine(String c)
    {
        query.append("ROUTINE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_CATALOG
     *
     * @return the object self
     */
    public Query routine_catalog()
    {
        query.append("ROUTINE_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query routine_catalog(String c)
    {
        query.append("ROUTINE_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_NAME
     *
     * @return the object self
     */
    public Query routine_name()
    {
        query.append("ROUTINE_NAME ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query routine_name(String c)
    {
        query.append("ROUTINE_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_SCHEMA
     *
     * @return the object self
     */
    public Query routine_schema()
    {
        query.append("ROUTINE_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with ROUTINE_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query routine_schema(String c)
    {
        query.append("ROUTINE_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROW
     *
     * @return the object self
     */
    public Query row()
    {
        query.append("ROW ");
        return this;
    }
    
    /**
     * Append the query with ROW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query row(String c)
    {
        query.append("ROW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROW_COUNT
     *
     * @return the object self
     */
    public Query row_count()
    {
        query.append("ROW_COUNT ");
        return this;
    }
    
    /**
     * Append the query with ROW_COUNT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query row_count(String c)
    {
        query.append("ROW_COUNT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROW_NUMBER
     *
     * @return the object self
     */
    public Query row_number()
    {
        query.append("ROW_NUMBER ");
        return this;
    }
    
    /**
     * Append the query with ROW_NUMBER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query row_number(String c)
    {
        query.append("ROW_NUMBER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROWCOUNT
     *
     * @return the object self
     */
    public Query rowcount()
    {
        query.append("ROWCOUNT ");
        return this;
    }
    
    /**
     * Append the query with ROWCOUNT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rowcount(String c)
    {
        query.append("ROWCOUNT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROWGUIDCOL
     *
     * @return the object self
     */
    public Query rowguidcol()
    {
        query.append("ROWGUIDCOL ");
        return this;
    }
    
    /**
     * Append the query with ROWGUIDCOL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rowguidcol(String c)
    {
        query.append("ROWGUIDCOL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROWID
     *
     * @return the object self
     */
    public Query rowid()
    {
        query.append("ROWID ");
        return this;
    }
    
    /**
     * Append the query with ROWID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rowid(String c)
    {
        query.append("ROWID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROWNUM
     *
     * @return the object self
     */
    public Query rownum()
    {
        query.append("ROWNUM ");
        return this;
    }
    
    /**
     * Append the query with ROWNUM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rownum(String c)
    {
        query.append("ROWNUM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ROWS
     *
     * @return the object self
     */
    public Query rows()
    {
        query.append("ROWS ");
        return this;
    }
    
    /**
     * Append the query with ROWS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rows(String c)
    {
        query.append("ROWS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with RULE
     *
     * @return the object self
     */
    public Query rule()
    {
        query.append("RULE ");
        return this;
    }
    
    /**
     * Append the query with RULE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query rule(String c)
    {
        query.append("RULE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SAVE
     *
     * @return the object self
     */
    public Query save()
    {
        query.append("SAVE ");
        return this;
    }
    
    /**
     * Append the query with SAVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query save(String c)
    {
        query.append("SAVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SAVEPOINT
     *
     * @return the object self
     */
    public Query savepoint()
    {
        query.append("SAVEPOINT ");
        return this;
    }
    
    /**
     * Append the query with SAVEPOINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query savepoint(String c)
    {
        query.append("SAVEPOINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCALE
     *
     * @return the object self
     */
    public Query scale()
    {
        query.append("SCALE ");
        return this;
    }
    
    /**
     * Append the query with SCALE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scale(String c)
    {
        query.append("SCALE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCHEMA
     *
     * @return the object self
     */
    public Query schema()
    {
        query.append("SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query schema(String c)
    {
        query.append("SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCHEMA_NAME
     *
     * @return the object self
     */
    public Query schema_name()
    {
        query.append("SCHEMA_NAME ");
        return this;
    }
    
    /**
     * Append the query with SCHEMA_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query schema_name(String c)
    {
        query.append("SCHEMA_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCHEMAS
     *
     * @return the object self
     */
    public Query schemas()
    {
        query.append("SCHEMAS ");
        return this;
    }
    
    /**
     * Append the query with SCHEMAS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query schemas(String c)
    {
        query.append("SCHEMAS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCOPE
     *
     * @return the object self
     */
    public Query scope()
    {
        query.append("SCOPE ");
        return this;
    }
    
    /**
     * Append the query with SCOPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scope(String c)
    {
        query.append("SCOPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_CATALOG
     *
     * @return the object self
     */
    public Query scope_catalog()
    {
        query.append("SCOPE_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scope_catalog(String c)
    {
        query.append("SCOPE_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_NAME
     *
     * @return the object self
     */
    public Query scope_name()
    {
        query.append("SCOPE_NAME ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scope_name(String c)
    {
        query.append("SCOPE_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_SCHEMA
     *
     * @return the object self
     */
    public Query scope_schema()
    {
        query.append("SCOPE_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with SCOPE_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scope_schema(String c)
    {
        query.append("SCOPE_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SCROLL
     *
     * @return the object self
     */
    public Query scroll()
    {
        query.append("SCROLL ");
        return this;
    }
    
    /**
     * Append the query with SCROLL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query scroll(String c)
    {
        query.append("SCROLL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SEARCH
     *
     * @return the object self
     */
    public Query search()
    {
        query.append("SEARCH ");
        return this;
    }
    
    /**
     * Append the query with SEARCH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query search(String c)
    {
        query.append("SEARCH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SECOND
     *
     * @return the object self
     */
    public Query second()
    {
        query.append("SECOND ");
        return this;
    }
    
    /**
     * Append the query with SECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query second(String c)
    {
        query.append("SECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SECOND_MICROSECOND
     *
     * @return the object self
     */
    public Query second_microsecond()
    {
        query.append("SECOND_MICROSECOND ");
        return this;
    }
    
    /**
     * Append the query with SECOND_MICROSECOND statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query second_microsecond(String c)
    {
        query.append("SECOND_MICROSECOND ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SECTION
     *
     * @return the object self
     */
    public Query section()
    {
        query.append("SECTION ");
        return this;
    }
    
    /**
     * Append the query with SECTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query section(String c)
    {
        query.append("SECTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SECURITY
     *
     * @return the object self
     */
    public Query security()
    {
        query.append("SECURITY ");
        return this;
    }
    
    /**
     * Append the query with SECURITY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query security(String c)
    {
        query.append("SECURITY ").append(c).append(" ");
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
     * Append the query with SELF
     *
     * @return the object self
     */
    public Query self()
    {
        query.append("SELF ");
        return this;
    }
    
    /**
     * Append the query with SELF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query self(String c)
    {
        query.append("SELF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SENSITIVE
     *
     * @return the object self
     */
    public Query sensitive()
    {
        query.append("SENSITIVE ");
        return this;
    }
    
    /**
     * Append the query with SENSITIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sensitive(String c)
    {
        query.append("SENSITIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SEPARATOR
     *
     * @return the object self
     */
    public Query separator()
    {
        query.append("SEPARATOR ");
        return this;
    }
    
    /**
     * Append the query with SEPARATOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query separator(String c)
    {
        query.append("SEPARATOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SEQUENCE
     *
     * @return the object self
     */
    public Query sequence()
    {
        query.append("SEQUENCE ");
        return this;
    }
    
    /**
     * Append the query with SEQUENCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sequence(String c)
    {
        query.append("SEQUENCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SERIALIZABLE
     *
     * @return the object self
     */
    public Query serializable()
    {
        query.append("SERIALIZABLE ");
        return this;
    }
    
    /**
     * Append the query with SERIALIZABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query serializable(String c)
    {
        query.append("SERIALIZABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SERVER_NAME
     *
     * @return the object self
     */
    public Query server_name()
    {
        query.append("SERVER_NAME ");
        return this;
    }
    
    /**
     * Append the query with SERVER_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query server_name(String c)
    {
        query.append("SERVER_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SESSION
     *
     * @return the object self
     */
    public Query session()
    {
        query.append("SESSION ");
        return this;
    }
    
    /**
     * Append the query with SESSION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query session(String c)
    {
        query.append("SESSION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SESSION_USER
     *
     * @return the object self
     */
    public Query session_user()
    {
        query.append("SESSION_USER ");
        return this;
    }
    
    /**
     * Append the query with SESSION_USER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query session_user(String c)
    {
        query.append("SESSION_USER ").append(c).append(" ");
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
     * Append the query with SETOF
     *
     * @return the object self
     */
    public Query setof()
    {
        query.append("SETOF ");
        return this;
    }
    
    /**
     * Append the query with SETOF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query setof(String c)
    {
        query.append("SETOF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SETS
     *
     * @return the object self
     */
    public Query sets()
    {
        query.append("SETS ");
        return this;
    }
    
    /**
     * Append the query with SETS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sets(String c)
    {
        query.append("SETS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SETUSER
     *
     * @return the object self
     */
    public Query setuser()
    {
        query.append("SETUSER ");
        return this;
    }
    
    /**
     * Append the query with SETUSER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query setuser(String c)
    {
        query.append("SETUSER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SHARE
     *
     * @return the object self
     */
    public Query share()
    {
        query.append("SHARE ");
        return this;
    }
    
    /**
     * Append the query with SHARE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query share(String c)
    {
        query.append("SHARE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SHOW
     *
     * @return the object self
     */
    public Query show()
    {
        query.append("SHOW ");
        return this;
    }
    
    /**
     * Append the query with SHOW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query show(String c)
    {
        query.append("SHOW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SHUTDOWN
     *
     * @return the object self
     */
    public Query shutdown()
    {
        query.append("SHUTDOWN ");
        return this;
    }
    
    /**
     * Append the query with SHUTDOWN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query shutdown(String c)
    {
        query.append("SHUTDOWN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SIGNAL
     *
     * @return the object self
     */
    public Query signal()
    {
        query.append("SIGNAL ");
        return this;
    }
    
    /**
     * Append the query with SIGNAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query signal(String c)
    {
        query.append("SIGNAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SIMILAR
     *
     * @return the object self
     */
    public Query similar()
    {
        query.append("SIMILAR ");
        return this;
    }
    
    /**
     * Append the query with SIMILAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query similar(String c)
    {
        query.append("SIMILAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SIMPLE
     *
     * @return the object self
     */
    public Query simple()
    {
        query.append("SIMPLE ");
        return this;
    }
    
    /**
     * Append the query with SIMPLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query simple(String c)
    {
        query.append("SIMPLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SIZE
     *
     * @return the object self
     */
    public Query size()
    {
        query.append("SIZE ");
        return this;
    }
    
    /**
     * Append the query with SIZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query size(String c)
    {
        query.append("SIZE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SMALLINT
     *
     * @return the object self
     */
    public Query smallint()
    {
        query.append("SMALLINT ");
        return this;
    }
    
    /**
     * Append the query with SMALLINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query smallint(String c)
    {
        query.append("SMALLINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SOME
     *
     * @return the object self
     */
    public Query some()
    {
        query.append("SOME ");
        return this;
    }
    
    /**
     * Append the query with SOME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query some(String c)
    {
        query.append("SOME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SONAME
     *
     * @return the object self
     */
    public Query soname()
    {
        query.append("SONAME ");
        return this;
    }
    
    /**
     * Append the query with SONAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query soname(String c)
    {
        query.append("SONAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SOURCE
     *
     * @return the object self
     */
    public Query source()
    {
        query.append("SOURCE ");
        return this;
    }
    
    /**
     * Append the query with SOURCE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query source(String c)
    {
        query.append("SOURCE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPACE
     *
     * @return the object self
     */
    public Query space()
    {
        query.append("SPACE ");
        return this;
    }
    
    /**
     * Append the query with SPACE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query space(String c)
    {
        query.append("SPACE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPATIAL
     *
     * @return the object self
     */
    public Query spatial()
    {
        query.append("SPATIAL ");
        return this;
    }
    
    /**
     * Append the query with SPATIAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query spatial(String c)
    {
        query.append("SPATIAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPECIFIC
     *
     * @return the object self
     */
    public Query specific()
    {
        query.append("SPECIFIC ");
        return this;
    }
    
    /**
     * Append the query with SPECIFIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query specific(String c)
    {
        query.append("SPECIFIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPECIFIC_NAME
     *
     * @return the object self
     */
    public Query specific_name()
    {
        query.append("SPECIFIC_NAME ");
        return this;
    }
    
    /**
     * Append the query with SPECIFIC_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query specific_name(String c)
    {
        query.append("SPECIFIC_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SPECIFICTYPE
     *
     * @return the object self
     */
    public Query specifictype()
    {
        query.append("SPECIFICTYPE ");
        return this;
    }
    
    /**
     * Append the query with SPECIFICTYPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query specifictype(String c)
    {
        query.append("SPECIFICTYPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL
     *
     * @return the object self
     */
    public Query sql()
    {
        query.append("SQL ");
        return this;
    }
    
    /**
     * Append the query with SQL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql(String c)
    {
        query.append("SQL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_RESULT
     *
     * @return the object self
     */
    public Query sql_big_result()
    {
        query.append("SQL_BIG_RESULT ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_RESULT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_big_result(String c)
    {
        query.append("SQL_BIG_RESULT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_SELECTS
     *
     * @return the object self
     */
    public Query sql_big_selects()
    {
        query.append("SQL_BIG_SELECTS ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_SELECTS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_big_selects(String c)
    {
        query.append("SQL_BIG_SELECTS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_TABLES
     *
     * @return the object self
     */
    public Query sql_big_tables()
    {
        query.append("SQL_BIG_TABLES ");
        return this;
    }
    
    /**
     * Append the query with SQL_BIG_TABLES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_big_tables(String c)
    {
        query.append("SQL_BIG_TABLES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_CALC_FOUND_ROWS
     *
     * @return the object self
     */
    public Query sql_calc_found_rows()
    {
        query.append("SQL_CALC_FOUND_ROWS ");
        return this;
    }
    
    /**
     * Append the query with SQL_CALC_FOUND_ROWS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_calc_found_rows(String c)
    {
        query.append("SQL_CALC_FOUND_ROWS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOG_OFF
     *
     * @return the object self
     */
    public Query sql_log_off()
    {
        query.append("SQL_LOG_OFF ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOG_OFF statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_log_off(String c)
    {
        query.append("SQL_LOG_OFF ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOG_UPDATE
     *
     * @return the object self
     */
    public Query sql_log_update()
    {
        query.append("SQL_LOG_UPDATE ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOG_UPDATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_log_update(String c)
    {
        query.append("SQL_LOG_UPDATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOW_PRIORITY_UPDATES
     *
     * @return the object self
     */
    public Query sql_low_priority_updates()
    {
        query.append("SQL_LOW_PRIORITY_UPDATES ");
        return this;
    }
    
    /**
     * Append the query with SQL_LOW_PRIORITY_UPDATES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_low_priority_updates(String c)
    {
        query.append("SQL_LOW_PRIORITY_UPDATES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_SELECT_LIMIT
     *
     * @return the object self
     */
    public Query sql_select_limit()
    {
        query.append("SQL_SELECT_LIMIT ");
        return this;
    }
    
    /**
     * Append the query with SQL_SELECT_LIMIT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_select_limit(String c)
    {
        query.append("SQL_SELECT_LIMIT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_SMALL_RESULT
     *
     * @return the object self
     */
    public Query sql_small_result()
    {
        query.append("SQL_SMALL_RESULT ");
        return this;
    }
    
    /**
     * Append the query with SQL_SMALL_RESULT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_small_result(String c)
    {
        query.append("SQL_SMALL_RESULT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQL_WARNINGS
     *
     * @return the object self
     */
    public Query sql_warnings()
    {
        query.append("SQL_WARNINGS ");
        return this;
    }
    
    /**
     * Append the query with SQL_WARNINGS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sql_warnings(String c)
    {
        query.append("SQL_WARNINGS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLCA
     *
     * @return the object self
     */
    public Query sqlca()
    {
        query.append("SQLCA ");
        return this;
    }
    
    /**
     * Append the query with SQLCA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlca(String c)
    {
        query.append("SQLCA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLCODE
     *
     * @return the object self
     */
    public Query sqlcode()
    {
        query.append("SQLCODE ");
        return this;
    }
    
    /**
     * Append the query with SQLCODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlcode(String c)
    {
        query.append("SQLCODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLERROR
     *
     * @return the object self
     */
    public Query sqlerror()
    {
        query.append("SQLERROR ");
        return this;
    }
    
    /**
     * Append the query with SQLERROR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlerror(String c)
    {
        query.append("SQLERROR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLEXCEPTION
     *
     * @return the object self
     */
    public Query sqlexception()
    {
        query.append("SQLEXCEPTION ");
        return this;
    }
    
    /**
     * Append the query with SQLEXCEPTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlexception(String c)
    {
        query.append("SQLEXCEPTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLSTATE
     *
     * @return the object self
     */
    public Query sqlstate()
    {
        query.append("SQLSTATE ");
        return this;
    }
    
    /**
     * Append the query with SQLSTATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlstate(String c)
    {
        query.append("SQLSTATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQLWARNING
     *
     * @return the object self
     */
    public Query sqlwarning()
    {
        query.append("SQLWARNING ");
        return this;
    }
    
    /**
     * Append the query with SQLWARNING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqlwarning(String c)
    {
        query.append("SQLWARNING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SQRT
     *
     * @return the object self
     */
    public Query sqrt()
    {
        query.append("SQRT ");
        return this;
    }
    
    /**
     * Append the query with SQRT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sqrt(String c)
    {
        query.append("SQRT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SSL
     *
     * @return the object self
     */
    public Query ssl()
    {
        query.append("SSL ");
        return this;
    }
    
    /**
     * Append the query with SSL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ssl(String c)
    {
        query.append("SSL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STABLE
     *
     * @return the object self
     */
    public Query stable()
    {
        query.append("STABLE ");
        return this;
    }
    
    /**
     * Append the query with STABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query stable(String c)
    {
        query.append("STABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with START
     *
     * @return the object self
     */
    public Query start()
    {
        query.append("START ");
        return this;
    }
    
    /**
     * Append the query with START statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query start(String c)
    {
        query.append("START ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STARTING
     *
     * @return the object self
     */
    public Query starting()
    {
        query.append("STARTING ");
        return this;
    }
    
    /**
     * Append the query with STARTING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query starting(String c)
    {
        query.append("STARTING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STATE
     *
     * @return the object self
     */
    public Query state()
    {
        query.append("STATE ");
        return this;
    }
    
    /**
     * Append the query with STATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query state(String c)
    {
        query.append("STATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STATEMENT
     *
     * @return the object self
     */
    public Query statement()
    {
        query.append("STATEMENT ");
        return this;
    }
    
    /**
     * Append the query with STATEMENT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query statement(String c)
    {
        query.append("STATEMENT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STATIC
     *
     * @return the object self
     */
    public Query static_()
    {
        query.append("STATIC ");
        return this;
    }
    
    /**
     * Append the query with STATIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query static_(String c)
    {
        query.append("STATIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STATISTICS
     *
     * @return the object self
     */
    public Query statistics()
    {
        query.append("STATISTICS ");
        return this;
    }
    
    /**
     * Append the query with STATISTICS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query statistics(String c)
    {
        query.append("STATISTICS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STATUS
     *
     * @return the object self
     */
    public Query status()
    {
        query.append("STATUS ");
        return this;
    }
    
    /**
     * Append the query with STATUS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query status(String c)
    {
        query.append("STATUS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STDDEV_POP
     *
     * @return the object self
     */
    public Query stddev_pop()
    {
        query.append("STDDEV_POP ");
        return this;
    }
    
    /**
     * Append the query with STDDEV_POP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query stddev_pop(String c)
    {
        query.append("STDDEV_POP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STDDEV_SAMP
     *
     * @return the object self
     */
    public Query stddev_samp()
    {
        query.append("STDDEV_SAMP ");
        return this;
    }
    
    /**
     * Append the query with STDDEV_SAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query stddev_samp(String c)
    {
        query.append("STDDEV_SAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STDIN
     *
     * @return the object self
     */
    public Query stdin()
    {
        query.append("STDIN ");
        return this;
    }
    
    /**
     * Append the query with STDIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query stdin(String c)
    {
        query.append("STDIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STDOUT
     *
     * @return the object self
     */
    public Query stdout()
    {
        query.append("STDOUT ");
        return this;
    }
    
    /**
     * Append the query with STDOUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query stdout(String c)
    {
        query.append("STDOUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STORAGE
     *
     * @return the object self
     */
    public Query storage()
    {
        query.append("STORAGE ");
        return this;
    }
    
    /**
     * Append the query with STORAGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query storage(String c)
    {
        query.append("STORAGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STRAIGHT_JOIN
     *
     * @return the object self
     */
    public Query straight_join()
    {
        query.append("STRAIGHT_JOIN ");
        return this;
    }
    
    /**
     * Append the query with STRAIGHT_JOIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query straight_join(String c)
    {
        query.append("STRAIGHT_JOIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STRICT
     *
     * @return the object self
     */
    public Query strict()
    {
        query.append("STRICT ");
        return this;
    }
    
    /**
     * Append the query with STRICT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query strict(String c)
    {
        query.append("STRICT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STRING
     *
     * @return the object self
     */
    public Query string()
    {
        query.append("STRING ");
        return this;
    }
    
    /**
     * Append the query with STRING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query string(String c)
    {
        query.append("STRING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STRUCTURE
     *
     * @return the object self
     */
    public Query structure()
    {
        query.append("STRUCTURE ");
        return this;
    }
    
    /**
     * Append the query with STRUCTURE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query structure(String c)
    {
        query.append("STRUCTURE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with STYLE
     *
     * @return the object self
     */
    public Query style()
    {
        query.append("STYLE ");
        return this;
    }
    
    /**
     * Append the query with STYLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query style(String c)
    {
        query.append("STYLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUBCLASS_ORIGIN
     *
     * @return the object self
     */
    public Query subclass_origin()
    {
        query.append("SUBCLASS_ORIGIN ");
        return this;
    }
    
    /**
     * Append the query with SUBCLASS_ORIGIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query subclass_origin(String c)
    {
        query.append("SUBCLASS_ORIGIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUBLIST
     *
     * @return the object self
     */
    public Query sublist()
    {
        query.append("SUBLIST ");
        return this;
    }
    
    /**
     * Append the query with SUBLIST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sublist(String c)
    {
        query.append("SUBLIST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUBMULTISET
     *
     * @return the object self
     */
    public Query submultiset()
    {
        query.append("SUBMULTISET ");
        return this;
    }
    
    /**
     * Append the query with SUBMULTISET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query submultiset(String c)
    {
        query.append("SUBMULTISET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUBSTRING
     *
     * @return the object self
     */
    public Query substring()
    {
        query.append("SUBSTRING ");
        return this;
    }
    
    /**
     * Append the query with SUBSTRING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query substring(String c)
    {
        query.append("SUBSTRING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUCCESSFUL
     *
     * @return the object self
     */
    public Query successful()
    {
        query.append("SUCCESSFUL ");
        return this;
    }
    
    /**
     * Append the query with SUCCESSFUL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query successful(String c)
    {
        query.append("SUCCESSFUL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUM
     *
     * @return the object self
     */
    public Query sum()
    {
        query.append("SUM ");
        return this;
    }
    
    /**
     * Append the query with SUM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sum(String c)
    {
        query.append("SUM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SUPERUSER
     *
     * @return the object self
     */
    public Query superuser()
    {
        query.append("SUPERUSER ");
        return this;
    }
    
    /**
     * Append the query with SUPERUSER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query superuser(String c)
    {
        query.append("SUPERUSER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYMMETRIC
     *
     * @return the object self
     */
    public Query symmetric()
    {
        query.append("SYMMETRIC ");
        return this;
    }
    
    /**
     * Append the query with SYMMETRIC statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query symmetric(String c)
    {
        query.append("SYMMETRIC ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYNONYM
     *
     * @return the object self
     */
    public Query synonym()
    {
        query.append("SYNONYM ");
        return this;
    }
    
    /**
     * Append the query with SYNONYM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query synonym(String c)
    {
        query.append("SYNONYM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYSDATE
     *
     * @return the object self
     */
    public Query sysdate()
    {
        query.append("SYSDATE ");
        return this;
    }
    
    /**
     * Append the query with SYSDATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sysdate(String c)
    {
        query.append("SYSDATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYSID
     *
     * @return the object self
     */
    public Query sysid()
    {
        query.append("SYSID ");
        return this;
    }
    
    /**
     * Append the query with SYSID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query sysid(String c)
    {
        query.append("SYSID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYSTEM
     *
     * @return the object self
     */
    public Query system()
    {
        query.append("SYSTEM ");
        return this;
    }
    
    /**
     * Append the query with SYSTEM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query system(String c)
    {
        query.append("SYSTEM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with SYSTEM_USER
     *
     * @return the object self
     */
    public Query system_user()
    {
        query.append("SYSTEM_USER ");
        return this;
    }
    
    /**
     * Append the query with SYSTEM_USER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query system_user(String c)
    {
        query.append("SYSTEM_USER ").append(c).append(" ");
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
     * Append the query with TABLE_NAME
     *
     * @return the object self
     */
    public Query table_name()
    {
        query.append("TABLE_NAME ");
        return this;
    }
    
    /**
     * Append the query with TABLE_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query table_name(String c)
    {
        query.append("TABLE_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TABLES
     *
     * @return the object self
     */
    public Query tables()
    {
        query.append("TABLES ");
        return this;
    }
    
    /**
     * Append the query with TABLES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tables(String c)
    {
        query.append("TABLES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TABLESAMPLE
     *
     * @return the object self
     */
    public Query tablesample()
    {
        query.append("TABLESAMPLE ");
        return this;
    }
    
    /**
     * Append the query with TABLESAMPLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tablesample(String c)
    {
        query.append("TABLESAMPLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TABLESPACE
     *
     * @return the object self
     */
    public Query tablespace()
    {
        query.append("TABLESPACE ");
        return this;
    }
    
    /**
     * Append the query with TABLESPACE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tablespace(String c)
    {
        query.append("TABLESPACE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TEMP
     *
     * @return the object self
     */
    public Query temp()
    {
        query.append("TEMP ");
        return this;
    }
    
    /**
     * Append the query with TEMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query temp(String c)
    {
        query.append("TEMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TEMPLATE
     *
     * @return the object self
     */
    public Query template()
    {
        query.append("TEMPLATE ");
        return this;
    }
    
    /**
     * Append the query with TEMPLATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query template(String c)
    {
        query.append("TEMPLATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TEMPORARY
     *
     * @return the object self
     */
    public Query temporary()
    {
        query.append("TEMPORARY ");
        return this;
    }
    
    /**
     * Append the query with TEMPORARY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query temporary(String c)
    {
        query.append("TEMPORARY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TERMINATE
     *
     * @return the object self
     */
    public Query terminate()
    {
        query.append("TERMINATE ");
        return this;
    }
    
    /**
     * Append the query with TERMINATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query terminate(String c)
    {
        query.append("TERMINATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TERMINATED
     *
     * @return the object self
     */
    public Query terminated()
    {
        query.append("TERMINATED ");
        return this;
    }
    
    /**
     * Append the query with TERMINATED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query terminated(String c)
    {
        query.append("TERMINATED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TEXT
     *
     * @return the object self
     */
    public Query text()
    {
        query.append("TEXT ");
        return this;
    }
    
    /**
     * Append the query with TEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query text(String c)
    {
        query.append("TEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TEXTSIZE
     *
     * @return the object self
     */
    public Query textsize()
    {
        query.append("TEXTSIZE ");
        return this;
    }
    
    /**
     * Append the query with TEXTSIZE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query textsize(String c)
    {
        query.append("TEXTSIZE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with THAN
     *
     * @return the object self
     */
    public Query than()
    {
        query.append("THAN ");
        return this;
    }
    
    /**
     * Append the query with THAN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query than(String c)
    {
        query.append("THAN ").append(c).append(" ");
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
     * Append the query with TIES
     *
     * @return the object self
     */
    public Query ties()
    {
        query.append("TIES ");
        return this;
    }
    
    /**
     * Append the query with TIES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query ties(String c)
    {
        query.append("TIES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TIME
     *
     * @return the object self
     */
    public Query time()
    {
        query.append("TIME ");
        return this;
    }
    
    /**
     * Append the query with TIME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query time(String c)
    {
        query.append("TIME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TIMESTAMP
     *
     * @return the object self
     */
    public Query timestamp()
    {
        query.append("TIMESTAMP ");
        return this;
    }
    
    /**
     * Append the query with TIMESTAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query timestamp(String c)
    {
        query.append("TIMESTAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TIMEZONE_HOUR
     *
     * @return the object self
     */
    public Query timezone_hour()
    {
        query.append("TIMEZONE_HOUR ");
        return this;
    }
    
    /**
     * Append the query with TIMEZONE_HOUR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query timezone_hour(String c)
    {
        query.append("TIMEZONE_HOUR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TIMEZONE_MINUTE
     *
     * @return the object self
     */
    public Query timezone_minute()
    {
        query.append("TIMEZONE_MINUTE ");
        return this;
    }
    
    /**
     * Append the query with TIMEZONE_MINUTE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query timezone_minute(String c)
    {
        query.append("TIMEZONE_MINUTE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TINYBLOB
     *
     * @return the object self
     */
    public Query tinyblob()
    {
        query.append("TINYBLOB ");
        return this;
    }
    
    /**
     * Append the query with TINYBLOB statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tinyblob(String c)
    {
        query.append("TINYBLOB ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TINYINT
     *
     * @return the object self
     */
    public Query tinyint()
    {
        query.append("TINYINT ");
        return this;
    }
    
    /**
     * Append the query with TINYINT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tinyint(String c)
    {
        query.append("TINYINT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TINYTEXT
     *
     * @return the object self
     */
    public Query tinytext()
    {
        query.append("TINYTEXT ");
        return this;
    }
    
    /**
     * Append the query with TINYTEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tinytext(String c)
    {
        query.append("TINYTEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TO
     *
     * @return the object self
     */
    public Query to()
    {
        query.append("TO ");
        return this;
    }
    
    /**
     * Append the query with TO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query to(String c)
    {
        query.append("TO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TOAST
     *
     * @return the object self
     */
    public Query toast()
    {
        query.append("TOAST ");
        return this;
    }
    
    /**
     * Append the query with TOAST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query toast(String c)
    {
        query.append("TOAST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TOP
     *
     * @return the object self
     */
    public Query top()
    {
        query.append("TOP ");
        return this;
    }
    
    /**
     * Append the query with TOP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query top(String c)
    {
        query.append("TOP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TOP_LEVEL_COUNT
     *
     * @return the object self
     */
    public Query top_level_count()
    {
        query.append("TOP_LEVEL_COUNT ");
        return this;
    }
    
    /**
     * Append the query with TOP_LEVEL_COUNT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query top_level_count(String c)
    {
        query.append("TOP_LEVEL_COUNT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRAILING
     *
     * @return the object self
     */
    public Query trailing()
    {
        query.append("TRAILING ");
        return this;
    }
    
    /**
     * Append the query with TRAILING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trailing(String c)
    {
        query.append("TRAILING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRAN
     *
     * @return the object self
     */
    public Query tran()
    {
        query.append("TRAN ");
        return this;
    }
    
    /**
     * Append the query with TRAN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tran(String c)
    {
        query.append("TRAN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTION
     *
     * @return the object self
     */
    public Query transaction()
    {
        query.append("TRANSACTION ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transaction(String c)
    {
        query.append("TRANSACTION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTION_ACTIVE
     *
     * @return the object self
     */
    public Query transaction_active()
    {
        query.append("TRANSACTION_ACTIVE ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTION_ACTIVE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transaction_active(String c)
    {
        query.append("TRANSACTION_ACTIVE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTIONS_COMMITTED
     *
     * @return the object self
     */
    public Query transactions_committed()
    {
        query.append("TRANSACTIONS_COMMITTED ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTIONS_COMMITTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transactions_committed(String c)
    {
        query.append("TRANSACTIONS_COMMITTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTIONS_ROLLED_BACK
     *
     * @return the object self
     */
    public Query transactions_rolled_back()
    {
        query.append("TRANSACTIONS_ROLLED_BACK ");
        return this;
    }
    
    /**
     * Append the query with TRANSACTIONS_ROLLED_BACK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transactions_rolled_back(String c)
    {
        query.append("TRANSACTIONS_ROLLED_BACK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSFORM
     *
     * @return the object self
     */
    public Query transform()
    {
        query.append("TRANSFORM ");
        return this;
    }
    
    /**
     * Append the query with TRANSFORM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transform(String c)
    {
        query.append("TRANSFORM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSFORMS
     *
     * @return the object self
     */
    public Query transforms()
    {
        query.append("TRANSFORMS ");
        return this;
    }
    
    /**
     * Append the query with TRANSFORMS statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query transforms(String c)
    {
        query.append("TRANSFORMS ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSLATE
     *
     * @return the object self
     */
    public Query translate()
    {
        query.append("TRANSLATE ");
        return this;
    }
    
    /**
     * Append the query with TRANSLATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query translate(String c)
    {
        query.append("TRANSLATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRANSLATION
     *
     * @return the object self
     */
    public Query translation()
    {
        query.append("TRANSLATION ");
        return this;
    }
    
    /**
     * Append the query with TRANSLATION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query translation(String c)
    {
        query.append("TRANSLATION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TREAT
     *
     * @return the object self
     */
    public Query treat()
    {
        query.append("TREAT ");
        return this;
    }
    
    /**
     * Append the query with TREAT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query treat(String c)
    {
        query.append("TREAT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER
     *
     * @return the object self
     */
    public Query trigger()
    {
        query.append("TRIGGER ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trigger(String c)
    {
        query.append("TRIGGER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_CATALOG
     *
     * @return the object self
     */
    public Query trigger_catalog()
    {
        query.append("TRIGGER_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trigger_catalog(String c)
    {
        query.append("TRIGGER_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_NAME
     *
     * @return the object self
     */
    public Query trigger_name()
    {
        query.append("TRIGGER_NAME ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trigger_name(String c)
    {
        query.append("TRIGGER_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_SCHEMA
     *
     * @return the object self
     */
    public Query trigger_schema()
    {
        query.append("TRIGGER_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with TRIGGER_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trigger_schema(String c)
    {
        query.append("TRIGGER_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRIM
     *
     * @return the object self
     */
    public Query trim()
    {
        query.append("TRIM ");
        return this;
    }
    
    /**
     * Append the query with TRIM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trim(String c)
    {
        query.append("TRIM ").append(c).append(" ");
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
     * Append the query with TRUNCATE
     *
     * @return the object self
     */
    public Query truncate()
    {
        query.append("TRUNCATE ");
        return this;
    }
    
    /**
     * Append the query with TRUNCATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query truncate(String c)
    {
        query.append("TRUNCATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TRUSTED
     *
     * @return the object self
     */
    public Query trusted()
    {
        query.append("TRUSTED ");
        return this;
    }
    
    /**
     * Append the query with TRUSTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query trusted(String c)
    {
        query.append("TRUSTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TSEQUAL
     *
     * @return the object self
     */
    public Query tsequal()
    {
        query.append("TSEQUAL ");
        return this;
    }
    
    /**
     * Append the query with TSEQUAL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query tsequal(String c)
    {
        query.append("TSEQUAL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with TYPE
     *
     * @return the object self
     */
    public Query type()
    {
        query.append("TYPE ");
        return this;
    }
    
    /**
     * Append the query with TYPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query type(String c)
    {
        query.append("TYPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UESCAPE
     *
     * @return the object self
     */
    public Query uescape()
    {
        query.append("UESCAPE ");
        return this;
    }
    
    /**
     * Append the query with UESCAPE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query uescape(String c)
    {
        query.append("UESCAPE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UID
     *
     * @return the object self
     */
    public Query uid()
    {
        query.append("UID ");
        return this;
    }
    
    /**
     * Append the query with UID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query uid(String c)
    {
        query.append("UID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNBOUNDED
     *
     * @return the object self
     */
    public Query unbounded()
    {
        query.append("UNBOUNDED ");
        return this;
    }
    
    /**
     * Append the query with UNBOUNDED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unbounded(String c)
    {
        query.append("UNBOUNDED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNCOMMITTED
     *
     * @return the object self
     */
    public Query uncommitted()
    {
        query.append("UNCOMMITTED ");
        return this;
    }
    
    /**
     * Append the query with UNCOMMITTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query uncommitted(String c)
    {
        query.append("UNCOMMITTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNDER
     *
     * @return the object self
     */
    public Query under()
    {
        query.append("UNDER ");
        return this;
    }
    
    /**
     * Append the query with UNDER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query under(String c)
    {
        query.append("UNDER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNDO
     *
     * @return the object self
     */
    public Query undo()
    {
        query.append("UNDO ");
        return this;
    }
    
    /**
     * Append the query with UNDO statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query undo(String c)
    {
        query.append("UNDO ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNENCRYPTED
     *
     * @return the object self
     */
    public Query unencrypted()
    {
        query.append("UNENCRYPTED ");
        return this;
    }
    
    /**
     * Append the query with UNENCRYPTED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unencrypted(String c)
    {
        query.append("UNENCRYPTED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNION
     *
     * @return the object self
     */
    public Query union()
    {
        query.append("UNION ");
        return this;
    }
    
    /**
     * Append the query with UNION statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query union(String c)
    {
        query.append("UNION ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNIQUE
     *
     * @return the object self
     */
    public Query unique()
    {
        query.append("UNIQUE ");
        return this;
    }
    
    /**
     * Append the query with UNIQUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unique(String c)
    {
        query.append("UNIQUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNKNOWN
     *
     * @return the object self
     */
    public Query unknown()
    {
        query.append("UNKNOWN ");
        return this;
    }
    
    /**
     * Append the query with UNKNOWN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unknown(String c)
    {
        query.append("UNKNOWN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNLISTEN
     *
     * @return the object self
     */
    public Query unlisten()
    {
        query.append("UNLISTEN ");
        return this;
    }
    
    /**
     * Append the query with UNLISTEN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unlisten(String c)
    {
        query.append("UNLISTEN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNLOCK
     *
     * @return the object self
     */
    public Query unlock()
    {
        query.append("UNLOCK ");
        return this;
    }
    
    /**
     * Append the query with UNLOCK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unlock(String c)
    {
        query.append("UNLOCK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNNAMED
     *
     * @return the object self
     */
    public Query unnamed()
    {
        query.append("UNNAMED ");
        return this;
    }
    
    /**
     * Append the query with UNNAMED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unnamed(String c)
    {
        query.append("UNNAMED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNNEST
     *
     * @return the object self
     */
    public Query unnest()
    {
        query.append("UNNEST ");
        return this;
    }
    
    /**
     * Append the query with UNNEST statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unnest(String c)
    {
        query.append("UNNEST ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNSIGNED
     *
     * @return the object self
     */
    public Query unsigned()
    {
        query.append("UNSIGNED ");
        return this;
    }
    
    /**
     * Append the query with UNSIGNED statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query unsigned(String c)
    {
        query.append("UNSIGNED ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UNTIL
     *
     * @return the object self
     */
    public Query until()
    {
        query.append("UNTIL ");
        return this;
    }
    
    /**
     * Append the query with UNTIL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query until(String c)
    {
        query.append("UNTIL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UPDATE
     *
     * @return the object self
     */
    public Query update()
    {
        query.append("UPDATE ");
        return this;
    }
    
    /**
     * Append the query with UPDATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query update(String c)
    {
        query.append("UPDATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UPDATETEXT
     *
     * @return the object self
     */
    public Query updatetext()
    {
        query.append("UPDATETEXT ");
        return this;
    }
    
    /**
     * Append the query with UPDATETEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query updatetext(String c)
    {
        query.append("UPDATETEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UPPER
     *
     * @return the object self
     */
    public Query upper()
    {
        query.append("UPPER ");
        return this;
    }
    
    /**
     * Append the query with UPPER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query upper(String c)
    {
        query.append("UPPER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USAGE
     *
     * @return the object self
     */
    public Query usage()
    {
        query.append("USAGE ");
        return this;
    }
    
    /**
     * Append the query with USAGE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query usage(String c)
    {
        query.append("USAGE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USE
     *
     * @return the object self
     */
    public Query use()
    {
        query.append("USE ");
        return this;
    }
    
    /**
     * Append the query with USE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query use(String c)
    {
        query.append("USE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USER
     *
     * @return the object self
     */
    public Query user()
    {
        query.append("USER ");
        return this;
    }
    
    /**
     * Append the query with USER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query user(String c)
    {
        query.append("USER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_CATALOG
     *
     * @return the object self
     */
    public Query user_defined_type_catalog()
    {
        query.append("USER_DEFINED_TYPE_CATALOG ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_CATALOG statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query user_defined_type_catalog(String c)
    {
        query.append("USER_DEFINED_TYPE_CATALOG ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_CODE
     *
     * @return the object self
     */
    public Query user_defined_type_code()
    {
        query.append("USER_DEFINED_TYPE_CODE ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_CODE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query user_defined_type_code(String c)
    {
        query.append("USER_DEFINED_TYPE_CODE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_NAME
     *
     * @return the object self
     */
    public Query user_defined_type_name()
    {
        query.append("USER_DEFINED_TYPE_NAME ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_NAME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query user_defined_type_name(String c)
    {
        query.append("USER_DEFINED_TYPE_NAME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_SCHEMA
     *
     * @return the object self
     */
    public Query user_defined_type_schema()
    {
        query.append("USER_DEFINED_TYPE_SCHEMA ");
        return this;
    }
    
    /**
     * Append the query with USER_DEFINED_TYPE_SCHEMA statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query user_defined_type_schema(String c)
    {
        query.append("USER_DEFINED_TYPE_SCHEMA ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with USING
     *
     * @return the object self
     */
    public Query using()
    {
        query.append("USING ");
        return this;
    }
    
    /**
     * Append the query with USING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query using(String c)
    {
        query.append("USING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UTC_DATE
     *
     * @return the object self
     */
    public Query utc_date()
    {
        query.append("UTC_DATE ");
        return this;
    }
    
    /**
     * Append the query with UTC_DATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query utc_date(String c)
    {
        query.append("UTC_DATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UTC_TIME
     *
     * @return the object self
     */
    public Query utc_time()
    {
        query.append("UTC_TIME ");
        return this;
    }
    
    /**
     * Append the query with UTC_TIME statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query utc_time(String c)
    {
        query.append("UTC_TIME ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with UTC_TIMESTAMP
     *
     * @return the object self
     */
    public Query utc_timestamp()
    {
        query.append("UTC_TIMESTAMP ");
        return this;
    }
    
    /**
     * Append the query with UTC_TIMESTAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query utc_timestamp(String c)
    {
        query.append("UTC_TIMESTAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VACUUM
     *
     * @return the object self
     */
    public Query vacuum()
    {
        query.append("VACUUM ");
        return this;
    }
    
    /**
     * Append the query with VACUUM statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query vacuum(String c)
    {
        query.append("VACUUM ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VALID
     *
     * @return the object self
     */
    public Query valid()
    {
        query.append("VALID ");
        return this;
    }
    
    /**
     * Append the query with VALID statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query valid(String c)
    {
        query.append("VALID ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VALIDATE
     *
     * @return the object self
     */
    public Query validate()
    {
        query.append("VALIDATE ");
        return this;
    }
    
    /**
     * Append the query with VALIDATE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query validate(String c)
    {
        query.append("VALIDATE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VALIDATOR
     *
     * @return the object self
     */
    public Query validator()
    {
        query.append("VALIDATOR ");
        return this;
    }
    
    /**
     * Append the query with VALIDATOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query validator(String c)
    {
        query.append("VALIDATOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VALUE
     *
     * @return the object self
     */
    public Query value()
    {
        query.append("VALUE ");
        return this;
    }
    
    /**
     * Append the query with VALUE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query value(String c)
    {
        query.append("VALUE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VALUES
     *
     * @return the object self
     */
    public Query values()
    {
        query.append("VALUES ");
        return this;
    }
    
    /**
     * Append the query with VALUES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query values(String c)
    {
        query.append("VALUES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VAR_POP
     *
     * @return the object self
     */
    public Query var_pop()
    {
        query.append("VAR_POP ");
        return this;
    }
    
    /**
     * Append the query with VAR_POP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query var_pop(String c)
    {
        query.append("VAR_POP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VAR_SAMP
     *
     * @return the object self
     */
    public Query var_samp()
    {
        query.append("VAR_SAMP ");
        return this;
    }
    
    /**
     * Append the query with VAR_SAMP statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query var_samp(String c)
    {
        query.append("VAR_SAMP ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARBINARY
     *
     * @return the object self
     */
    public Query varbinary()
    {
        query.append("VARBINARY ");
        return this;
    }
    
    /**
     * Append the query with VARBINARY statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query varbinary(String c)
    {
        query.append("VARBINARY ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARCHAR
     *
     * @return the object self
     */
    public Query varchar()
    {
        query.append("VARCHAR ");
        return this;
    }
    
    /**
     * Append the query with VARCHAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query varchar(String c)
    {
        query.append("VARCHAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARCHAR2
     *
     * @return the object self
     */
    public Query varchar2()
    {
        query.append("VARCHAR2 ");
        return this;
    }
    
    /**
     * Append the query with VARCHAR2 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query varchar2(String c)
    {
        query.append("VARCHAR2 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARCHARACTER
     *
     * @return the object self
     */
    public Query varcharacter()
    {
        query.append("VARCHARACTER ");
        return this;
    }
    
    /**
     * Append the query with VARCHARACTER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query varcharacter(String c)
    {
        query.append("VARCHARACTER ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARIABLE
     *
     * @return the object self
     */
    public Query variable()
    {
        query.append("VARIABLE ");
        return this;
    }
    
    /**
     * Append the query with VARIABLE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query variable(String c)
    {
        query.append("VARIABLE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARIABLES
     *
     * @return the object self
     */
    public Query variables()
    {
        query.append("VARIABLES ");
        return this;
    }
    
    /**
     * Append the query with VARIABLES statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query variables(String c)
    {
        query.append("VARIABLES ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VARYING
     *
     * @return the object self
     */
    public Query varying()
    {
        query.append("VARYING ");
        return this;
    }
    
    /**
     * Append the query with VARYING statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query varying(String c)
    {
        query.append("VARYING ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with VERBOSE
     *
     * @return the object self
     */
    public Query verbose()
    {
        query.append("VERBOSE ");
        return this;
    }
    
    /**
     * Append the query with VERBOSE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query verbose(String c)
    {
        query.append("VERBOSE ").append(c).append(" ");
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
     * Append the query with VOLATILE
     *
     * @return the object self
     */
    public Query volatile_()
    {
        query.append("VOLATILE ");
        return this;
    }
    
    /**
     * Append the query with VOLATILE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query volatile_(String c)
    {
        query.append("VOLATILE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WAITFOR
     *
     * @return the object self
     */
    public Query waitfor()
    {
        query.append("WAITFOR ");
        return this;
    }
    
    /**
     * Append the query with WAITFOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query waitfor(String c)
    {
        query.append("WAITFOR ").append(c).append(" ");
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
     * Append the query with WHENEVER
     *
     * @return the object self
     */
    public Query whenever()
    {
        query.append("WHENEVER ");
        return this;
    }
    
    /**
     * Append the query with WHENEVER statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query whenever(String c)
    {
        query.append("WHENEVER ").append(c).append(" ");
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
     * Append the query with WHILE
     *
     * @return the object self
     */
    public Query while_()
    {
        query.append("WHILE ");
        return this;
    }
    
    /**
     * Append the query with WHILE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query while_(String c)
    {
        query.append("WHILE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WIDTH_BUCKET
     *
     * @return the object self
     */
    public Query width_bucket()
    {
        query.append("WIDTH_BUCKET ");
        return this;
    }
    
    /**
     * Append the query with WIDTH_BUCKET statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query width_bucket(String c)
    {
        query.append("WIDTH_BUCKET ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WINDOW
     *
     * @return the object self
     */
    public Query window()
    {
        query.append("WINDOW ");
        return this;
    }
    
    /**
     * Append the query with WINDOW statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query window(String c)
    {
        query.append("WINDOW ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WITH
     *
     * @return the object self
     */
    public Query with()
    {
        query.append("WITH ");
        return this;
    }
    
    /**
     * Append the query with WITH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query with(String c)
    {
        query.append("WITH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WITHIN
     *
     * @return the object self
     */
    public Query within()
    {
        query.append("WITHIN ");
        return this;
    }
    
    /**
     * Append the query with WITHIN statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query within(String c)
    {
        query.append("WITHIN ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WITHOUT
     *
     * @return the object self
     */
    public Query without()
    {
        query.append("WITHOUT ");
        return this;
    }
    
    /**
     * Append the query with WITHOUT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query without(String c)
    {
        query.append("WITHOUT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WORK
     *
     * @return the object self
     */
    public Query work()
    {
        query.append("WORK ");
        return this;
    }
    
    /**
     * Append the query with WORK statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query work(String c)
    {
        query.append("WORK ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WRITE
     *
     * @return the object self
     */
    public Query write()
    {
        query.append("WRITE ");
        return this;
    }
    
    /**
     * Append the query with WRITE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query write(String c)
    {
        query.append("WRITE ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with WRITETEXT
     *
     * @return the object self
     */
    public Query writetext()
    {
        query.append("WRITETEXT ");
        return this;
    }
    
    /**
     * Append the query with WRITETEXT statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query writetext(String c)
    {
        query.append("WRITETEXT ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with X509
     *
     * @return the object self
     */
    public Query x509()
    {
        query.append("X509 ");
        return this;
    }
    
    /**
     * Append the query with X509 statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query x509(String c)
    {
        query.append("X509 ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with XOR
     *
     * @return the object self
     */
    public Query xor()
    {
        query.append("XOR ");
        return this;
    }
    
    /**
     * Append the query with XOR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query xor(String c)
    {
        query.append("XOR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with YEAR
     *
     * @return the object self
     */
    public Query year()
    {
        query.append("YEAR ");
        return this;
    }
    
    /**
     * Append the query with YEAR statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query year(String c)
    {
        query.append("YEAR ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with YEAR_MONTH
     *
     * @return the object self
     */
    public Query year_month()
    {
        query.append("YEAR_MONTH ");
        return this;
    }
    
    /**
     * Append the query with YEAR_MONTH statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query year_month(String c)
    {
        query.append("YEAR_MONTH ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ZEROFILL
     *
     * @return the object self
     */
    public Query zerofill()
    {
        query.append("ZEROFILL ");
        return this;
    }
    
    /**
     * Append the query with ZEROFILL statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query zerofill(String c)
    {
        query.append("ZEROFILL ").append(c).append(" ");
        return this;
    }
    
    /**
     * Append the query with ZONE
     *
     * @return the object self
     */
    public Query zone()
    {
        query.append("ZONE ");
        return this;
    }
    
    /**
     * Append the query with ZONE statement
     *
     * @param c the statement value
     * @return the object self
     */
    public Query zone(String c)
    {
        query.append("ZONE ").append(c).append(" ");
        return this;
    }
}
