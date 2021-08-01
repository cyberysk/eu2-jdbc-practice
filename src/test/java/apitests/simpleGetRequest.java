package apitests;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class simpleGetRequest {

    String hrurl = "http://100.27.15.76:1000/ords/hr/regions";

    @Test
    public void test1() {

        Response response = RestAssured.get(hrurl);

        //print status code
        System.out.println("Status Code : " + response.statusCode());

        //print the body
        System.out.println(response.prettyPrint());
//        System.out.println("response.body() = " + response.body().print());
//        System.out.println("response.body().prettyPrint() = " + response.body().prettyPrint());
    }
       /*Given accept type is json
        When the user sends get request to regions end point
        Then response status code must be 200
        and body is json format
        */
    @Test
    public void test2(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                            .when().get(hrurl);

        //verify response status code is 200
        Assert.assertEquals(response.statusCode(), 200);

        //verify content type is application/json
        Assert.assertEquals(response.contentType(), "application/json");

    }

    @Test
    public void test3(){

        RestAssured.given().accept(ContentType.JSON)
                .when().get(hrurl).then()
                .assertThat().statusCode(200)
                .and().contentType("application/json");

    }
    /*  Given accept type is json
        When the user sends get request to regions/2
        Then response status code must be 200
        and body is json format
        and response body contains Americas
        */

    @Test
    public void test4(){

        Response response = given().accept(ContentType.JSON)
                            .when().get(hrurl+"\2");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json");

        //verify body includes Americas
        Assert.assertTrue(response.body().asString().contains("Americas"));
    }

}
