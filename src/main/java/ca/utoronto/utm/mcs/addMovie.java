package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.neo4j.driver.Driver;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.io.OutputStream;


import java.io.IOException;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class addMovie {

    private Driver neo4jDriver;

    public addMovie(Driver driver) {
        this.neo4jDriver = driver;
    }

    public void putaddMovie(HttpExchange r)  throws IOException {
        try {
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);

            //If either movieID or name is not given return 400 as BAD REQUEST
            if (!deserialized.has("name") || !deserialized.has("movieID") ||
                    (!deserialized.get("name").getClass().equals(String.class)) && !deserialized.get("movieID").getClass().equals(String.class)) {
                r.sendResponseHeaders(400, -1);
            }
            else {

                String name = deserialized.getString("name");
                String movieID = deserialized.getString("movieID");

                movieIDEXIST check=  new movieIDEXIST(this.neo4jDriver);
                Boolean movie_NOT_Exist = check.run(movieID);
                if (!movie_NOT_Exist){
                    System.out.println("Duplicate already exist");
                    r.sendResponseHeaders(400,-1);
                }
                else
                {
                    String response;
                    //interaction with database
                    try ( Session session = neo4jDriver.session() )
                    {
                        response = Neo4jDAO.addMovie(session,name,movieID);
                    }

                    r.sendResponseHeaders(200, 0);
                    OutputStream os = r.getResponseBody();
                    os.close();

                }


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
