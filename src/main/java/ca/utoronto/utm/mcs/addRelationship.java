package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

import java.io.IOException;
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

                actorIDEXIST check=  new actorIDEXIST(this.neo4jDriver);
                movieIDEXIST check2=  new movieIDEXIST(this.neo4jDriver);



                Boolean actor_NOT_Exist = check.run(r,actorID);
                Boolean movie_NOT_Exist = check2.run(movieID);

                //neither movie or actor is not existed in the database
                //responded 404 as NO ACTOR EXIST
                if ((actor_NOT_Exist) || (movie_NOT_Exist)){
                    r.sendResponseHeaders(404,-1);
                }else
                {
                    Map response2;
                    System.out.println("here1");
                    try ( Session session = neo4jDriver.session() )
                    {
                        response2 = Neo4jDAO.hasRelationship(session,actorID,movieID);
                    }
                    System.out.println("here2");
                    //RELATIONSHIP ALREADY EXIST
                    if(response2.get("hasRelationship").toString().equals("true")){
                        System.out.println("RELATIONSHIP ALREADY EXIST");
                        r.sendResponseHeaders(400, -1);
                    }
                    else {
                        System.out.println("RELATIONSHIP add");
                        try ( Session session = neo4jDriver.session() )
                        {
                            response = Neo4jDAO.addRelationship(session,actorID,movieID);
                        }

                        r.sendResponseHeaders(200, 0);
                        OutputStream os = r.getResponseBody();
                        os.close();
                    }
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
