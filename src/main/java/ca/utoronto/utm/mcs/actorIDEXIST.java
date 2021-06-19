package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.neo4j.driver.Driver;

import java.io.IOException;
import org.neo4j.driver.Session;
/**
 * @param: actorId: String
 * @return:
 * - true if actorid exist in data base return false otherwise true
 **/
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
