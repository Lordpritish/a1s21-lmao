package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Module
public class ReqHandlerModule {
    // TODO Complete This Module
    @Provides
    static Neo4jDAO provideDatatbase(){
        // setting up database
        Driver driver =  GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic("neo4j", "1234" ) );
        Neo4jDAO database = new Neo4jDAO(driver);
        return database;
    }
}
