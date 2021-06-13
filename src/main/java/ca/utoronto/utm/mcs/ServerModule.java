package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import dagger.Module;
import dagger.Provides;

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
