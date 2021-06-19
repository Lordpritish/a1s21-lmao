package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;

import java.io.IOException;
import org.neo4j.driver.Session;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class movieIDEXIST {

    private  final Driver neo4jDriver;
    public movieIDEXIST(Driver driver) {
        this.neo4jDriver = driver;
    }

    public Boolean run(String movieID)  throws IOException {

        try ( Session session = neo4jDriver.session() )
        {
            return Neo4jDAO.movieIDExist(session,movieID);
        }
    }
}
