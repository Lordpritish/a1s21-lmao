package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
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


public class actorIDEXIST {

    private Driver neo4jDriver;
    public actorIDEXIST(Driver driver) {
        this.neo4jDriver = driver;
    }

    public Boolean run(HttpExchange r,String actorID)  throws IOException {

        try ( Session session = neo4jDriver.session() )
        {
            return Neo4jDAO.actorIDExist(session,actorID);
        }
    }
}
