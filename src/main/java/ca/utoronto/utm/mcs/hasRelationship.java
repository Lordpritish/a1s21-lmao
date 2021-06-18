package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.neo4j.driver.Driver;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import org.neo4j.driver.Session;

import java.io.OutputStream;
import java.util.Map;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class hasRelationship {

    private Driver neo4jDriver;
    public hasRelationship(Driver driver) {
        this.neo4jDriver = driver;
    }

    public void run(HttpExchange r)  throws IOException {
        try {
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);

            //See body and deserilized
//            System.out.println("hasRelationhshi[ get input:");
//            System.out.println(deserialized);
            //If actorID is not given return 400 as BAD REQUEST
            if (!deserialized.has("actorID") || !deserialized.has("movieID")) {
                r.sendResponseHeaders(400, -1);
            }
            else {
                String actorID = deserialized.getString("actorID");
                String movieID = deserialized.getString("movieID");

                Map response;
                //Interaction with database + assign values to JSONObjects already
                try ( Session session = neo4jDriver.session() )
                {
                    response = Neo4jDAO.hasRelationship(session,actorID,movieID);
                }

                JSONObject responseJSON = new JSONObject(response);
                byte[] result = responseJSON.toString().getBytes();
                OutputStream os = r.getResponseBody();
                //valid actorID passed in and valid result responded by database
                System.out.println("has response" + responseJSON);
                if (responseJSON.length() != 0) {
                    result = responseJSON.toString().getBytes();
                    r.sendResponseHeaders(200, result.length);
                    os.write(result);
                }
                //actorID not found in the database and 404 return as NO DATA FOUND
                else{
                    r.sendResponseHeaders(404, -1);
                }
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
