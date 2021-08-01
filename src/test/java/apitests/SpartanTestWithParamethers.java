package apitests;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SpartanTestWithParamethers {

    @BeforeClass
    public void beforeClass(){

        //if you define address as BaseURI you will not need
        // after that right that you can write directly end point
       RestAssured.baseURI = "http://100.27.15.76:8000";
    }

    /* Given accept type is json
        And Id parameter value is 5
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 200
        And response content-type : application/json
        And "Blythe"should be in response payload (means body)
     */


    @Test
    public void test1() {


        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id", 5)
                .when().get("/api/spartans/{id}");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //verify response content type : application/json
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json");

        //verify response body contains "Blythe"
        System.out.println(response.body().asString());
        Assert.assertTrue(response.body().asString().contains("Blythe"));
    }
        @Test
        public void getSpratanId_Positive_PathParam (){

            Response response = given().accept(ContentType.JSON)
                    .and().auth().basic("admin","admin")
                    .and().pathParam("id", 5)
                    .when().get("/api/spartans/{id}");

            assertEquals(response.statusCode(), 200);
            assertEquals(response.contentType(), "application/json;charset=UTF-8");
            assertTrue(response.body().asString().contains("Blythe"));

        }

    /*
    TASK
    Given Accept type JSON
    And ID parameter value is 500
    When user sends GET request to /api/spartans/{id}
    Then response status code should be 404
    And response content-type : application/json;charset=UTF-8
    And "Not Found" message should be in response payload
    */

        @Test
        public void getSpratanId_Negative_PathParam (){

            Response response = given().accept(ContentType.JSON)
                    .and().auth().basic("admin","admin")
                    .and().pathParam("id", 500)
                    .when().get("/api/spartans/{id}");

            assertEquals(response.statusCode(), 404);
            assertEquals(response.contentType(), "application/json");
           // System.out.println(response.body().asString());
            assertTrue(response.body().asString().contains("Not Found"));

        }
/*
    Given accept type is Jason
    And Query parameter values are :
    gender | Female
    nameContains| e
    When user sends GET request to /api/spartans/search
    Then response status code should be 200
    And response content-type : application/json;
    And "Female" should be in response payload
    And "Janetta" should be in response payload

    */

    @Test
    public void SpartanWithParam(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().queryParam("gender", "Female")
                .queryParam("nameContains", "e")
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");
        assertTrue(response.getBody().asString().contains("Female"));
        assertTrue(response.getBody().asString().contains("Janette"));

    }

    @Test
    public void positiveTestWithQueryParamWithMaps(){
        //create a map and add parameters
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains","e");

        Response response = given().accept(ContentType.JSON).and()
                .auth().basic("admin", "admin")
                .queryParams(queryMap)
                .when().get("/api/spartans/search");

        //response verification
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        //verify body contains
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));
    }

}
