package Day6;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertEquals;

public class POJO_Deserialize {

//    @BeforeClass
//    public void beforeClass(){
//
//        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");;
//
//    }
    @Test
    public void oneSpartanPojo(){

       Response response =  given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .and().auth().basic("admin", "admin")
                .when().get("http://3.93.199.110:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        // JSON to my custom class(POJO)
        // de-serialization to POJO
        Spartan spartan15 = response.body().as(Spartan.class);

        System.out.println(spartan15.getId());
        System.out.println(spartan15.getName());
        System.out.println(spartan15.getGender());
        System.out.println(spartan15.getPhone());

        System.out.println("spartan15 = " + spartan15.toString());

        //also we can assertion values
       assertEquals(spartan15.getName(), "Meta");
       assertEquals(spartan15.getId(),15);

    }
    @Test
    public void regionWithPojo(){

        Response response = when().get("http://3.81.99.109:1000/ords/hr/regions");

        assertEquals(response.statusCode(),200);

        //Convert JSON response to Pojo,(Custom class)

        Regions regions = response.body().as(Regions.class);

        System.out.println(regions.getCount());
        System.out.println(regions.getHasMore());

        List<Item> items = regions.getItems();
        System.out.println(items.get(0).toString());
        System.out.println(items.size());
        System.out.println(items.get(3).getRegionName());

    }
    @Test
    public void gson_example() {

        Gson gson = new Gson();

        //JSON to Java collections(POJO) --> De-serialization

        String myJson = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        Spartan spartan = gson.fromJson(myJson, Spartan.class);

        Map<String, Object> map = gson.fromJson(myJson, Map.class);

//        System.out.println(map.toString());

        System.out.println(spartan.toString());

        //-------------------SERIALIZATION----------------
        //JAVA Collections/POJO to JSON

        Spartan spartanEU = new Spartan(200,"Mike","Male",123123123);

        String jsonSpartanEU = gson.toJson(spartanEU);
        System.out.println(jsonSpartanEU);

        //gson or jackson is called objectMapper, jsonparser, data binding library

    }

}
