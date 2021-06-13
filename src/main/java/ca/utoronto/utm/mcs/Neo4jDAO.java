package ca.utoronto.utm.mcs;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

// All your database transactions or queries should 
// go in this class
public class Neo4jDAO implements AutoCloseable{
    // TODO Complete This Class
    private final Driver driver;

    public Neo4jDAO( Driver driver)
    {
        this.driver = driver;
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }
    public Driver getDriver(){
        return driver;
    }


}
