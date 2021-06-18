package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import dagger.Module;
import dagger.Provides;

/**
 * PUT /api/v1/actorIDEXIST
 * @param:
 * @return:
 * - 200 OK: Actor exist
 * - 400 BAD REQUEST: If name/actorid are improperly formatted
 * - 500 INTERNAL SERVER ERROR: if error occured during a database operation
 */


@Module
public class ServerModule {

    // TODO Complete This Module
    @Provides
    static HttpServer provideHttpServer  (){
        HttpServer server;
        try{
           server = HttpServer.create(new InetSocketAddress("0.0.0.0", App.port), 0);
       }
       catch(IOException e)
       {
          System.out.println("socket faiL");
          server = null;
       }
       return server;
    }

}
