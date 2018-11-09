import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTest {


    @DataProvider(name="creatingClientsWithSpecificNames")
    public Object[][] createTestDataRecords() {
        return new Object[][] {
                {"UserFromDataProvider1","Krakow"},
                {"UserFromDataProvider2","warsaw"},
                {"UserFromDataProvider3","wroclaw"}
        };
    }


    @Test
    public void get(){
        Response response = given().header("Content-Type", "application/json").
                and().header("X-Ninja-Token", "yzjako2f7h8l0e6m3eibik6p4grkrjjr").
                when().get("http://79.137.68.21/api/v1/clients/117").
                then().assertThat().
                statusCode(200).body("data.name",equalTo("SoapUITest1")).extract().response();

        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asString());
    }

    @Test(dataProvider = "creatingClientsWithSpecificNames")
    public void post(String name, String city){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("city", city);

        Response response = given().header("Content-Type", "application/json").
                and().header("X-Ninja-Token", "yzjako2f7h8l0e6m3eibik6p4grkrjjr").
                body(jsonObject.toString()).
                when().post("http://79.137.68.21/api/v1/clients").
                then().assertThat().
                statusCode(200).and().assertThat().body("data.name",equalTo(name)).
                and().assertThat().header("Content-Type", equalTo("application/json")).
                extract().response();

        ResponseBody responseBody = response.getBody();
        System.out.println(responseBody.asString());
    }
}

