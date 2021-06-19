package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class App
{
    static int port = 8080;

    public static void main(String[] args) throws IOException
    {

       ServerComponent component = DaggerServerComponent.create();
       Server server = component.buildServer();
       HttpServer main_server = server.getServer();

       ReqHandlerComponent component1 = DaggerReqHandlerComponent.create();
       ReqHandler handler = component1.buildHandler();

        // TODO Create Your Server Context Here, There Should Only Be One Context
         main_server.createContext("/api/v1/",handler);
  main_server.start();

    	System.out.printf("Server started on port %d\n", port);
//        System.exit(0);
    }
}
