package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

// All your database transactions or queries should
// go in this class
public class Neo4jDAO implements AutoCloseable{
    // TODO Complete This Class
    private final Driver driver;


    public Neo4jDAO( Driver driver)
    {
        this.driver = driver;
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }
    public Driver getDriver(){
        return driver;
    }

    public static String addActor(Session session,String name, String ID ){
            return session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run( "MERGE (a:Actor{name:$name,actorID:$actorID}) "  +
                                    "RETURN a.name, a.actorID",
                            parameters("name", name , "actorID", ID));

                    return result.single().get( 0 ).asString();
                }
            });
    }

    public static String addMovie(Session session,String name, String ID ){
        return session.writeTransaction( new TransactionWork<String>() {
            @Override
            public String execute(Transaction tx) {
                Result result = tx.run( "MERGE (m:Movie{movieID:$movieID}) "  +
                                "SET m.name = $name " +
                                "RETURN m.name, m.movieID",
                        parameters("name", name , "movieID", ID));

                System.out.println("here9");


                return result.single().get( 0 ).asString();
            }
        });
    }
    public static String addRelationship(Session session,String actorID, String movieID){
        return session.writeTransaction( new TransactionWork<String>() {
            @Override
            public String execute(Transaction tx) {
                Result result = tx.run( "MATCH (a: Actor), (m: Movie)" +
               " WHERE a.actorID = $actorID AND m.movieID = $movieID" +
               " CREATE (a)-[r:ACTED_IN]->(m) "+
                       " RETURN type(r)",
                        parameters("actorID",actorID , "movieID", movieID));

                System.out.println("here9");

                return result.single().get( 0 ).asString();
            }
        });
    }

    public static Map getActor (Session session,String actorID){
        return session.writeTransaction( new TransactionWork<Map>() {
            @Override
            public Map execute(Transaction tx) {
//                System.out.println(actorID);
                Result result = tx.run( "MATCH (a:Actor{actorID:$actorID})-" +
                                "[ACTED_IN]->(m:Movie) " +
                                "RETURN a.actorID as actorID, a.name as name, collect(m.movieID) as movies",
                        parameters("actorID", actorID));

               List<Record> records = result.list();
                Map recordMap = new HashMap();
                //valid data responded from database
                if (!records.isEmpty()){
                    Record record = records.get(0);
                    recordMap = record.asMap();
                }
                return recordMap;
            }
        });
    }
    public static Map hasRelationship (Session session,String actorID,String movieID){
        return session.writeTransaction( new TransactionWork<Map>() {
            @Override
            public Map execute(Transaction tx) {
//                System.out.println(actorID);
//                System.out.println(movieID);
                Result result = tx.run( "match (a:Actor{actorID:$actorID}), " +
                                "(m:Movie{movieID:$movieID})" +
                                "RETURN a.actorID, m.movieID, exists((a)-[:ACTED_IN]->(m)) as b",
                        parameters("actorID", actorID, "movieID", movieID));

                List<Record> records = result.list();
                Map recordMap = new HashMap();
                //valid data responded from database
                if (!records.isEmpty()){
                    Record record = records.get(0);
                    recordMap = record.asMap();
                }
                return recordMap;
            }
        });
    }
    public static Boolean actorIDExist (Session session,String actorID){
        return session.writeTransaction( new TransactionWork<Boolean>() {
            @Override
            public Boolean execute(Transaction tx) {
//                System.out.println(actorID);
                Result result = tx.run(  "MATCH (a:Actor{actorID:$actorID}) " +
                                "RETURN a.actorID as actorID",
                        parameters("actorID", actorID));

                List<Record> records = result.list();
                return records.isEmpty();
            }
        });
    }


    public static Map computeBaconNumber (Session session,String actorID,String baconID){
        return session.writeTransaction( new TransactionWork<Map>() {
            @Override
            public Map execute(Transaction tx) {
//                System.out.println(actorID);
//                System.out.println(baconID);
                Result result = tx.run( "MATCH p=shortestPath((a:Actor{actorID:$actorID})-[*]-" +
                                "(b:Actor{actorID:$baconID})) " +
                                "RETURN length(p)/2 as baconNumber",
                        parameters("actorID", actorID, "baconID", baconID));

                List<Record> records = result.list();
                Map recordMap = new HashMap();
                //valid data responded from database
                if (!records.isEmpty()){
                    Record record = records.get(0);
                    recordMap = record.asMap();
                }
                return recordMap;
            }
        });
    }

    public static Map computeBaconPath (Session session,String actorID,String baconID){
        return session.writeTransaction( new TransactionWork<Map>() {
            @Override
            public Map execute(Transaction tx) {
//                System.out.println("here "+actorID);
//                System.out.println("here1" + baconID);
                Result result = tx.run( "MATCH p=shortestPath((a:Actor{actorID:$actorID})-[*]-" +
                                "(b:Actor{actorID:$baconID})) " +
                                "RETURN  p as baconPath",
                        parameters("actorID", actorID, "baconID", baconID));
//                Result result = tx.run( "MATCH (a:Actor{actorID:$actorID}),"+
//                                "(b:Actor{actorID:$baconID}) " +
//                        "p = shortestPath((a)-[*]-(b))" +
//                                "RETURN  p as baconPath",
//                        parameters("actorID", actorID, "baconID", baconID));
                List<Record> records = result.list();
                Map recordMap = new HashMap();
                //valid data responded from database
                if (!records.isEmpty()){
                    Record record = records.get(0);
                    recordMap = record.asMap();
                }
                return recordMap;
            }
        });
    }



}
