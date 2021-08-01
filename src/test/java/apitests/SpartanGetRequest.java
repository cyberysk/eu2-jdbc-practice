package apitests;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpartanGetRequest {

    String spartanUrl = "http://100.27.15.76:8000";

    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get(spartanUrl + "/api/spartans");

        System.out.println(response.statusCode());

        response.prettyPrint();
    }
    /*
        When users sends a get request to spartans/3
         Then status code should be 200
         And content type should be application/json
         and json body should contain Fidole
    */

    @Test
    public void test2(){

        Response response = given().auth().basic("admin", "admin")
                .when().get(spartanUrl + "/api/spartans/3");

        //verify status code
        assertEquals(response.statusCode(),200);

        //System.out.println(response.contentType());
        //content type should be application/json;charset=UTF-8
        assertEquals(response.contentType(),"application/json");

        //json body should contain Fidole
        assertTrue(response.body().asString().contains("Fidole"));

    }

    /* Given no headers provided
    When users sends get request to api/hello
    Then response status code should be 200
    And Content type header should be "text/plain;charset=UTF-8"
    And header should contain date
    And Content-Length should be 17
    And body should be "Hello from Sparta"
     */

    @Test
    public void test3(){

      Response response = when().get(spartanUrl + "/api/hello");

      //verify status code
      assertEquals(response.statusCode(),200);
        System.out.println(response.statusCode());

      //verify content-type
      assertEquals(response.contentType(), "text/plain;charset=UTF-8");
        System.out.println(response.contentType());

      //verify we have headers named Date
      assertTrue(response.headers().hasHeaderWithName("Date"));

      //get any header with following methods
        System.out.println(response.header("Date"));
        System.out.println(response.getHeader("Content-Length"));
      assertFalse(response.getHeader("Date").isEmpty());

      //verify content length is 17
      assertEquals(response.getHeader("Content-Length"),"17");

//      assertEquals(response.body().asString().length(),17);
//        System.out.println(response.body().asString().length());

        //verify body contains "Hello from Spartan"
      assertEquals(response.body().asString(), "Hello from Sparta");
//        System.out.println(response.body().asString());




    }


}
