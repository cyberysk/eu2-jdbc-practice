package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertEquals;

public class JsonToJavaCollection {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");
    }


    @Test
    public void SpartanToMap(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .and().auth().basic("admin", "admin")
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //we will convert Json Response to Java Collection
        // Also we can use Map<String, ?> spartanMap = response.body().as(Map.class);
        // We need to dependency for this converting

        Map<String,Object> spartanMap = response.body().as(Map.class);

        System.out.println(spartanMap);
        String name = (String) spartanMap.get("name");
        assertEquals(name,"Meta");

    }

    @Test
    public void allSpartanWithList(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .when().get("/api/spartans");

        assertEquals(response.statusCode(),200);

        List<Map<String,Object>> allSpartanList = response.body().as(List.class);

        System.out.println(allSpartanList);

       // System.out.println(allSpartanList.get(0));

        //print first spartan first name (first get for index second get for key name)
        System.out.println(allSpartanList.get(0).get("name"));
        //get one spartan from list and assign to map
        Map<String, Object> spartan2 = allSpartanList.get(1);
        //print the name for second spartan
        System.out.println(spartan2.get("name"));

    }
    @Test
    public void regionJsonMap(){

        Response response = when().get("http://3.93.199.110:1000/ords/hr/regions");

        assertEquals(response.statusCode(),200);

        Map<String, Object> regionMap = response.body().as(Map.class);

        System.out.println(regionMap.get("count"));

        System.out.println(regionMap.get("hasMore"));

        System.out.println(regionMap.get("items"));

       List<Map<String,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        //get second region information(which you want key's value that region_name is here in map)
        System.out.println("itemsList.get(1) = " + itemsList.get(1));
        System.out.println("List(indeaxnumber1)'s value  = " + itemsList.get(1).get("region_name"));

        //print europe
        System.out.println(itemsList.get(0).get("region_name"));

    }

}
