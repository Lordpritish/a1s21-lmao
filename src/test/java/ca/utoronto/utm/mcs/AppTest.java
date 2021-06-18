package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.
public class AppTest {

    private static final String[] hello= {""};

    @BeforeAll
    public static void start() throws IOException {
        App.main(hello);
    }

    @Test
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
    public void addActorInvalidField() throws IOException, JSONException {

        URL url = new URL ("http://localhost:8080/api/v1/addActor");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("name","PRITISH PRBHAKAR PANDA");
//        obj.put("actorID","123456");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input =obj.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        assertEquals(400,con.getResponseCode());

    }
//
    @Test
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
//
    @Test
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



}
