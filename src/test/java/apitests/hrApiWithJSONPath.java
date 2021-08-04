package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


    public class hrApiWithJSONPath {

        @BeforeClass
        public void beforeClass(){
            RestAssured.baseURI = ConfigurationReader.getProperty("hrapi_url");
        }
        @Test
        public void CountriesWithJsonPath(){
            //request
            Response response = get("/countries");

            //assign response body to jsonpath
            JsonPath json = response.jsonPath();

            //read second country name
            String secondCountryName = json.get("items.country_name[1]");
            System.out.println("secondCountryName = " + secondCountryName);

            //get all country ids
            List<String> allCountryIds = json.getList("items.country_id");
            System.out.println(allCountryIds);

            // print countryIds line by line
//        for (String eachCountryId : allCountryIds) {
//            System.out.println(eachCountryId);
//        }

            // get all country names where their region id is equal to 2
            List <String> CountryNamesWithRegion2 = json.getList("items.findAll {it.region_id==2}.country_name");
            System.out.println(CountryNamesWithRegion2);

            List <String> CountryIdsWithRegion2 = json.getList("items.findAll {it.region_id==2}.country_id");
            System.out.println(CountryIdsWithRegion2);

        }

        @Test
        public void findAllEmployeesExample(){
            //request

            Response response = given().queryParam("limit", 107)
                    .get("/employees");

            assertEquals(response.statusCode(), 200);

            JsonPath json = response.jsonPath();

            // get me all firstName of employees who is making more than 10000
            List <String> firstNamesWhoOver10000 = json.getList("items.findAll {it.salary>10000}.first_name");
            System.out.println(firstNamesWhoOver10000);
            System.out.println(firstNamesWhoOver10000.size());

            // get me all emails who is working as IT_PROG

            List <String> emailsAllIT_PROG = json.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
            System.out.println(emailsAllIT_PROG);
            System.out.println(emailsAllIT_PROG.size());

            //find the firstname of who is making highest salary
            String kingName = json.get("items.max {it.salary}.first_name ");
            System.out.println(kingName);

        }
}
