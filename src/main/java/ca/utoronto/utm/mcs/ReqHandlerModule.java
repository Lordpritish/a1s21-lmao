package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


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
