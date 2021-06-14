package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;

import java.io.IOException;
import org.neo4j.driver.Session;

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
