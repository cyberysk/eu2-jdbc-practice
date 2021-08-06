package Day8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.given;


public class JsonSchemaValidation {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");;
    }

    @Test
    public void JsonSchemaValidationForSpartan(){
        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .pathParam("id", 50)
                .when().get("/api/spartans/{id}").then()
                .assertThat().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
    }

}
