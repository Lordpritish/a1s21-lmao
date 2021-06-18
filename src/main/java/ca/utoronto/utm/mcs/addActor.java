package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.io.OutputStream;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class addActor {
    private Driver neo4jDriver;
    public addActor(Driver driver) {
        this.neo4jDriver = driver;
    }

    public void putaddActor(HttpExchange r)  throws IOException {
        try {
//            System.out.println("here1");
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);
//            System.out.println("here2");
            //If either name or actorID is not given return 400 as BAD REQUEST
            if (!deserialized.has("name") || !deserialized.has("actorID")) {
//                System.out.println("here3");
                r.sendResponseHeaders(400, -1);

            }
            //Interacted with database and add actor, then return 200 as OK
            else {
                String name = deserialized.getString("name");
                String actorID = deserialized.getString("actorID");
//
                actorIDEXIST check=  new actorIDEXIST(this.neo4jDriver);
                Boolean actor_NOT_Exist = check.run(r,actorID);
                if (!actor_NOT_Exist){
//                    System.out.println("Duplicate already exist");
                    r.sendResponseHeaders(400,-1);
                }
                else
                {
                    String addResponse;
                    //interaction with database
                    try ( Session session = neo4jDriver.session() )
                    {
//                        System.out.println("here4");
                        addResponse  = Neo4jDAO.addActor(session,name,actorID);
                    }
                    //result for server-client interaction
                    r.sendResponseHeaders(200, 0);
                    OutputStream os = r.getResponseBody();
                    os.close();
                }

            }
        }
        //if deserilized failed, (ex: JSONObeject Null Value)
        catch(JSONException e) {
//            System.out.println("here6");
            r.sendResponseHeaders(400, -1);
        }
        //if server connection / database connection failed
        catch(Exception e) {
//            System.out.println("here8");
            r.sendResponseHeaders(500, -1);
        }

    }


}
