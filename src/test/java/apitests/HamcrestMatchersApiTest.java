package apitests;


import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersApiTest {

 /*
    given accept type is Json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106
     */

    @Test
    public void OneSpartanwithHamcrest(){

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .and().auth().basic("admin", "admin")
        .when().get("http://3.93.199.110:8000/api/spartans/{id}")
        .then().statusCode(200)
                .assertThat().contentType("application/json")
                .and().assertThat().body("id", equalTo(15),
                "name", equalTo("Meta"),
                                        "gender", equalTo("Female"),
                                        "phone", equalTo(1938695106));
    }
    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .pathParam("id","10423")
                .when().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200)
                        .and().contentType("application/json;charset=UTF-8")
                        .and().header("Connection", equalTo("Keep-Alive") )
                        .and().header("Date", notNullValue())
                        .and().body("teachers.firstName[0]", equalTo("Alexander"))
                        .and().body("teachers.lastName[0]", equalTo("Syrup"),
                        "teachers.gender[0]",equalTo("male"));

    }

    @Test
    public void TeacherWithDepartments(){
        //it is like a contains but it is looking just first names
        //what is difference from other (upper Matchers); others assert one data
        // here we are assert list of teachers.firstnames  like for each loop checking contain exact matching names
        // tat we wanted check names

        given().accept(ContentType.JSON)
                .pathParam("name", "Computer")
        .when().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("teachers.firstName", hasItems("Marteen","Alexander"));



    }

}
