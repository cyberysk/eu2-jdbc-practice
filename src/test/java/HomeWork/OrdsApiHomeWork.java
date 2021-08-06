package HomeWork;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class OrdsApiHomeWork {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi_url");
    }
    /*
        Q1:
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And Content - Type is Json
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is
    */
    @Test
    public void q1(){

        Response response = given().accept(ContentType.JSON).
                and().queryParam("q", "{\"country_id\": \"US\"}")
                .when().get("countries");

        //Verify the statusCode
        assertEquals(response.statusCode(), 200);

        //Verify the Content Type
        assertEquals(response.contentType(), "application/json");

        //Verify the country_id is US
       assertEquals(response.path("items.country_id[0]"), "US");

       // Verify country_name
        assertEquals(response.path("items.country_name[0]"), "United States of America");

        // Verify region_id
        int reg_id = response.path("items.region_id[0]");
        assertEquals(reg_id, 2);

    }

     /*   Q2:
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And Content - Type is Json
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
    */




    /*    Q3:
    - Given accept type is Json
    -Query param value q= region_id 3
    - When users sends request to /countries
    - Then status code is 200
    - And all regions_id is 3
    - And count is 6
    - And hasMore is false
    - And Country_name are;
        Australia,China,India,Japan,Malaysia,Singapore

    */
}
