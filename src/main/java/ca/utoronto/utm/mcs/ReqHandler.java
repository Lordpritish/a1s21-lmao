package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import javax.inject.Inject;

public class ReqHandler implements HttpHandler {

    private String ID;
    private Map getResponse;

    // TODO Complete This Class
    private Driver neo4jDriver;
    @Inject
    public ReqHandler(Neo4jDAO driver) {
        this.neo4jDriver = driver.getDriver();
    }



    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equals("PUT")) {
//                handlePut(exchange);
            }
            else if(exchange.getRequestMethod().equals("GET")){
//                handleGet(exchange);
            }
            else{
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void handleGet(HttpExchange r) throws IOException{
//        String body = Utils.convert(r.getRequestBody());
//        try {
//            JSONObject deserialized = new JSONObject(body);
//
//            //See body and deserilized
//            System.out.println("addActor-HandelGet get input:");
//            System.out.println(deserialized);
//
//            //If actorID is not given return 400 as BAD REQUEST
//            if (!deserialized.has("request_name")) {
//                r.sendResponseHeaders(400, -1);
//            }
//            else {
//                ID = deserialized.getString("request_name");
//                //Interaction with database + assign values to JSONObjects already
//                get(ID);
//                JSONObject responseJSON = new JSONObject(getResponse);
//                byte[] result = responseJSON.toString().getBytes();
//                OutputStream os = r.getResponseBody();
//                //valid actorID passed in and valid result responded by database
//                if (responseJSON.length() != 0) {
//                    result = responseJSON.toString().getBytes();
//                    r.sendResponseHeaders(200, result.length);
//                    os.write(result);
//                }
//                //actorID not found in the database and 404 return as NO DATA FOUND
//                else{
//                    r.sendResponseHeaders(404, -1);
//                }
//                os.close();
//            }
//        }
//        //if deserilized failed, (ex: JSONObeject Null Value)
//        catch(JSONException e) {
//            r.sendResponseHeaders(400, -1);
//        }
//        //if server connection / database connection failed
//        catch(Exception e) {
//            r.sendResponseHeaders(500, -1);
//        }
//    }
}
