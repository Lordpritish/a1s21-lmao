package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpServer;

import javax.inject.Inject;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


public class Server {
    // TODO Complete This Class
    private HttpServer server;

    @Inject
    public Server(HttpServer server){
        System.out.println("server created");
        this.server = server;

    }

    public HttpServer getServer(){
        return this.server;
    }

}
