package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.types.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class computeBaconPath {
    private final Driver neo4jDriver;
    private byte[] result;
    private Map response;
    private final String BaconID = "nm0000102";
    public computeBaconPath(Driver driver) {
        this.neo4jDriver = driver;
    }

    public void run(HttpExchange r)  throws IOException {
        try {
            //get Request variables
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);
            JSONObject responseJSON = new JSONObject();
            OutputStream os = r.getResponseBody();

            //If actorID is not given return 400 as BAD REQUEST
            if (!deserialized.has("actorID")){
                r.sendResponseHeaders(400, -1);
            }
            //actorID is given, then to test existence
            else {
                String actorID = deserialized.getString("actorID");
                actorIDEXIST check=  new actorIDEXIST(this.neo4jDriver);

                //Test if Kevin Bacon is in the database
                Boolean bacon_NOT_Exist = check.run(r,this.BaconID);

                //Test if actorID input is in the database
                Boolean actor_NOT_Exist = check.run(r,actorID);

                //neither Kevin Bacon or actor is not existed in the database
                //responded 400 as NO ACTOR EXIST
                if ((bacon_NOT_Exist) || (actor_NOT_Exist)){
                    r.sendResponseHeaders(404,-1);
                }
                //both actors existed, try to find the baconPath
                else {
                    if (!actorID.equals(BaconID)) {
                        //interaction with database to calculate baconPath
                        try ( Session session = neo4jDriver.session() )
                        {
                            response = Neo4jDAO.computeBaconPath(session,actorID,this.BaconID);
                        }
                        try {
                            //add baconNumber response
//                            responseJSON.put("baconPath", response.get("baconNumber"));

//                            //add baconPath in a list<JSONObject> form
                            responseJSON.put("baconPath", createBaconPath(response));
                        }
                        //actorID found but path not found in the database and 404 return as NO PATH FOUND
                        catch (NullPointerException e) {
                            r.sendResponseHeaders(404, -1);
                        }
                    }
                    //actorID given is the same as actorID for Kevin Bacon
                    else {
                        responseJSON.put("baconPath", actorID);
                    }
                    //valid actorID passed in and valid result responded by database
                    if (responseJSON.length() != 0) {
                        result = responseJSON.toString().getBytes();
                        r.sendResponseHeaders(200, result.length);
                        //write to a byte[] for OutputStream
                        os.write(result);
                    }
                }
            }
            os.close();
        }

        catch(JSONException e) {
            r.sendResponseHeaders(400, -1);
        }
        //if server connection / database connection failed
        catch(Exception e) {
            r.sendResponseHeaders(500, -1);
        }
    }


    private List<String> createBaconPath(Map response) throws JSONException {
        System.out.println("createBaconPath is running:");
        //change format of data from: Path ->Iterable<Node> ->List<JSONObject>
        InternalPath path = (InternalPath) response.get("baconPath");
        List<String> al = new ArrayList<>();
        Iterable<Node> nodeIterable = path.nodes();

        //change format of data from: Nodes ->Two Lists

        for (Node node: nodeIterable
        ) {
            Map nodeMap = node.asMap();
            String actorID ;
            if (node.hasLabel("actor")){
                actorID = nodeMap.get("id").toString();
                al.add(actorID);
            }
        }


        return al;
    }

}
