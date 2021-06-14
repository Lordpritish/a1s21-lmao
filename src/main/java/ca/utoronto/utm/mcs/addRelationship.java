package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.io.OutputStream;

public class addRelationship {

    private Driver neo4jDriver;

    public addRelationship(Driver driver) {
        this.neo4jDriver = driver;
    }
    public void putaddRelationship(HttpExchange r)  throws IOException {
        try {
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);

            //If either movieID or name is not given return 400 as BAD REQUEST
            if (!deserialized.has("actorID") || !deserialized.has("movieID") ||
                    (!deserialized.get("actorID").getClass().equals(String.class)) && !deserialized.get("movieID").getClass().equals(String.class)) {
                r.sendResponseHeaders(400, -1);
            }
            else {
                String actorID = deserialized.getString("actorID");
                String movieID = deserialized.getString("movieID");
                String response;
                //interaction with database

                try ( Session session = neo4jDriver.session() )
                {
                    response = Neo4jDAO.addRelationship(session,actorID,movieID);
                }

                r.sendResponseHeaders(200, 0);
                OutputStream os = r.getResponseBody();
                os.close();
            }
        }
        //if deserilized failed, (ex: JSONObeject Null Value)
        catch(JSONException e) {
            r.sendResponseHeaders(400, -1);
        }
        //if server connection / database connection failed
        catch(Exception e) {
            r.sendResponseHeaders(500, -1);
        }

    }
}
