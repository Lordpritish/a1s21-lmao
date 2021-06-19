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


public class computeBaconNumber {

    private final Driver neo4jDriver;
    private byte[] result;
    private Map response;
    private final String BaconID = "nm0000102";
    public computeBaconNumber(Driver driver) {
        this.neo4jDriver = driver;
    }

    public void run(HttpExchange r)  throws IOException {
        try {
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);
            OutputStream os = r.getResponseBody();
            JSONObject responseJSON;

//            System.out.println("getRelationship handler get:");
//            System.out.println(deserialized);
            //If actorID is not given return 400 as BAD REQUEST
            if (!deserialized.has("actorID")){
                r.sendResponseHeaders(400, -1);
            }
            //actorID is given, then test for existence
            else {
                String actorID = deserialized.getString("actorID");

                //Test if Kevin Bacon is in the database
                actorIDEXIST check=  new actorIDEXIST(this.neo4jDriver);
                Boolean bacon_NOT_Exist = check.run(r,this.BaconID);

                //Test if actorID input is in the database
                Boolean actor_NOT_Exist = check.run(r,actorID);
                //neither Kevin Bacon or actor is not existed in the database
                //responded 400 as NO ACTOR EXIST
                if ((bacon_NOT_Exist) || (actor_NOT_Exist)){
                    r.sendResponseHeaders(404,-1);
                }
                //normal case to compute a baconNumber&baconPath
                else {

                    if (!actorID.equals(this.BaconID)) {
                        //interaction with database
                        try ( Session session = neo4jDriver.session() )
                        {
                            response = Neo4jDAO.computeBaconNumber(session,actorID,this.BaconID);
                        }
                        //result for server-client interaction
                        responseJSON = new JSONObject(response);

                    }
                    //actorID given is the same as actorID for Kevin Bacon
                    else {
                        responseJSON = new JSONObject(response);
                        responseJSON.put("baconNumber", "0");
                    }
                    //valid actorID passed in and valid result responded by database
                    if (responseJSON.length() != 0) {
                        result = responseJSON.toString().getBytes();
                        r.sendResponseHeaders(200, result.length);
                        //write to a byte[] for OutputStream
                        os.write(result);
                    }
                    //both actors found but no path in the database and 404 return as NO PATH FOUND
                    else {
                        r.sendResponseHeaders(404, -1);
                    }
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
