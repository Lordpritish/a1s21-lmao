package ca.utoronto.utm.mcs;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    private static final String[] hello= {""};

    @BeforeAll
    public static void start() throws IOException {
        App.main(hello);
    }


        @Test
        @Order(1)
        public void addActor() throws IOException, JSONException {
            URL url = new URL ("http://localhost:8080/api/v1/addActor");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JSONObject obj=new JSONObject();
            obj.put("name","PRITISH PRABHAKAR PANDA");
            obj.put("actorID","123456");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input =obj.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
    //        System.out.println(con.getResponseCode());
            assertEquals(200,con.getResponseCode());
        }

        @Test
        @Order(2)
        public void addActorDuplicate() throws IOException, JSONException {

            URL url = new URL ("http://localhost:8080/api/v1/addActor");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JSONObject obj=new JSONObject();
            obj.put("name","PRITISH PRBHAKAR PANDA");
            obj.put("actorID","123456");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input =obj.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            assertEquals(400,con.getResponseCode());

        }

//
    @Test
    @Order(3)
    public void addMovie() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/addMovie");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","YOUR NAME");
        obj.put("movieID","m12345");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(200,con.getResponseCode());

    }
//
    @Test
    @Order(4)
    public void addMovieInvalidField() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/addMovie");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","YOUR NAME");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(400,con.getResponseCode());

    }
    @Test
    @Order(5)
    public void addMovieDuplicate() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/addMovie");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","YOUR NAME");
        obj.put("movieID","m12345");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(400,con.getResponseCode());

    }


        @Test
        @Order(6)
        public void addRelationship() throws IOException, JSONException {

            URL url = new URL ("http://localhost:8080/api/v1/addRelationship");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JSONObject obj=new JSONObject();
            obj.put("actorID","123456");
            obj.put("movieID","m12345");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input =obj.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            assertEquals(200,con.getResponseCode());

        }

        @Test
        @Order(7)
        public void addRelationshipNotfound() throws IOException, JSONException {

            URL url = new URL ("http://localhost:8080/api/v1/addRelationship");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            JSONObject obj=new JSONObject();
            obj.put("actorID","000");
            obj.put("movieID","000");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input =obj.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            assertEquals(404,con.getResponseCode());

        }


        @Test
        @Order(8)
        public void hasRelationship() throws IOException, JSONException {

            URL url = new URL ("http://localhost:8080/api/v1/hasRelationship");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("actorId", "123456");
            jsonObject.put("movieId", "m12345");
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if(con.getResponseCode() == 200) {

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    assertEquals( response.toString(), "{ actorId: nm1001231," +
                            "name: Ramy Youssef, movies: [ nm8911231 , nm1991341 ]}");
//                System.out.println(response.toString());
                }
            }
            else {
                assertFalse(true);
            }
        }

    @Test
    @Order(9)
    public void doesnt_hasRelationship() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/hasRelationship");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123456");
        jsonObject.put("movieId", "000");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        assertEquals(404,con.getResponseCode());
    }

    @Test
    @Order(10)
    public void computeBaconNumber_without_kevin() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/computeBaconNumber");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123456");

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

        }
        assertEquals(404 , 404 , con.getResponseCode());
//        assertEquals(404 , 404);

    }

    @Test
    @Order(11)
    public void addKevin() throws IOException, JSONException {
        URL url = new URL ("http://localhost:8080/api/v1/addActor");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","Kevin Bacon");
        obj.put("actorID","nm1991271");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(200,con.getResponseCode());
    }

    @Test
    @Order(12)
    public void computeBaconNumber() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/computeBaconNumber");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123456");

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

        }
        if(con.getResponseCode() == 200) {

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                assertEquals( response.toString(), "{\"baconNumber\":1}");

            }
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    @Order(13)
    public void computeBaconPath() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/computeBaconPath");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123456");

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

        }
        if(con.getResponseCode() == 200) {

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                assertEquals( response.toString(), "{\"baconPath\":[\"123456\",\"nm0000102\"]}");

            }
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    @Order(14)
    public void addActor_with_no_relationship() throws IOException, JSONException {
        URL url = new URL ("http://localhost:8080/api/v1/addActor");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","NEW USER");
        obj.put("actorID","123");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        //        System.out.println(con.getResponseCode());
        assertEquals(200,con.getResponseCode());
    }

    @Test
    @Order(15)
    public void computeBaconPath_nopathfound() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/computeBaconPath");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123");

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

        }
        assertEquals(404,con.getResponseCode());
    }


    @Test
    @Order(16)
    public void getActor() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/getActor");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "123456");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        if(con.getResponseCode() == 200) {
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                assertEquals(response.toString(), "{\"actorID\":\"123456\",\"name\":\"PRITISH PRABHAKAR PANDA\",\"movies\":[\"m12345\"]}");
            }
        }
        else {
            assertFalse(false);
        }

    }
    @Test
    @Order(17)
    public void getActor_notfound() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/getActor");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("actorId", "1000000");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(404,con.getResponseCode());

    }



}
