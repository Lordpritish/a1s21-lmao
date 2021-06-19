package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpServer;

import javax.inject.Inject;

/**
 *Creates a server
 * @param: HttpServer server
 * @return:
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
