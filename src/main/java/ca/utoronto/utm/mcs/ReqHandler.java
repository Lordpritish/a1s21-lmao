package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import java.util.List;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

import javax.inject.Inject;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class ReqHandler implements HttpHandler {

    private String name;
    private String ID;
    private String addResponse;

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
                handlePut(exchange);
            }
            else if(exchange.getRequestMethod().equals("GET")){
                handleGet(exchange);
            }
            else{
                exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleGet(HttpExchange r) throws IOException {
        String request =  r.getRequestURI().toString().substring(r.getRequestURI().toString().lastIndexOf('/')+1);
        System.out.println(request);
        if(request.equals("getActor")){
            getActor GET_actor =  new getActor(this.neo4jDriver);
            GET_actor.run(r);
        }
        else if(request.equals("hasRelationship")){
            hasRelationship GET_Relationship=  new hasRelationship(this.neo4jDriver);
            GET_Relationship.run(r);
        }
        else if(request.equals("computeBaconNumber")){

            computeBaconNumber  GET_computeBaconNumber = new computeBaconNumber (this.neo4jDriver);
            GET_computeBaconNumber.run(r);
        }
        else if(request.equals("computeBaconPath")){
            computeBaconPath GET_computeBaconPath= new computeBaconPath(this.neo4jDriver);
            GET_computeBaconPath.run(r);
        }
        else
        {
            r.sendResponseHeaders(404,-1);
        }

    }

    private void handlePut(HttpExchange r) throws IOException {
            String request =  r.getRequestURI().toString().substring(r.getRequestURI().toString().lastIndexOf('/')+1);
            if(request.equals("addActor")){
                addActor Add_actor =  new addActor(this.neo4jDriver);
                 Add_actor.putaddActor(r);
            }
            else if(request.equals("addMovie")){

                addMovie ADD_movie = new addMovie(this.neo4jDriver);
                ADD_movie.putaddMovie(r);

            }
            else if(request.equals("addRelationship")){
                addRelationship ADD_Relationship = new addRelationship(this.neo4jDriver);
                ADD_Relationship.putaddRelationship(r);
            }
            else
            {
                r.sendResponseHeaders(404,-1);
            }

    }
}



