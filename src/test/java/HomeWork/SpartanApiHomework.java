package HomeWork;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SpartanApiHomework {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");
    }

    /*Q1:
Given accept type is json
And path param id is 20
When user sends a get request to "/spartans/{id}"
Then status code is 200
And content-type is "application/json;char"
And response header contains Date
And Transfer-Encoding is chunked
And response payload values match the following:
id is 20,
name is "Lothario",
gender is "Male",
phone is 7551551687

     */
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "20")
                .get("/api/spartans/{id}");

        System.out.println(response.body().asString());

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.getHeader("Transfer-Encoding"), "chunked");

       assertEquals((int)response.body().path("id"),20);
        assertEquals(response.body().path("name"), "Lothario");
        assertEquals(response.body().path("gender"),"Male");
        assertEquals((long)response.body().path("phone"),7551551687L) ;


    }

    /*
    Q2:
Given accept type is json
And query param gender = Female
And queary param nameContains = r
When user sends a get request to "/spartans/search"
Then status code is 200
And content-type is "application/json;char"
And all genders are Female
And all names contains r
And size is 20
And totalPages is 1
And sorted is false
     */

    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "r")
                .get("/api/spartans/search");

        System.out.println(response.body().asString());

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json");

        JsonPath json = response.jsonPath();

        List<String> gendersResult = json.getList("content.gender");

        for (String eachGender:gendersResult) {
            assertTrue(eachGender.equals("Female"));

        }

        List <String> namesResult = json.getList("content.name");
        System.out.println("namesResult = " + namesResult);;


        for (String eachName:namesResult) {
            assertTrue(eachName.toLowerCase().contains("r"));
        }

        assertTrue(json.get("totalElement").equals(17));
        System.out.println(response.header("sorted"));

    }

}
