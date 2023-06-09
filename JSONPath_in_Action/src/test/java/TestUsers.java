import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import java.util.List;


public class TestUsers {
    private Response response;
    @BeforeMethod
    public void baseURI() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        response = given().
                contentType(ContentType.JSON).
                when().
                get("users").
                then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getEmail() {
        System.out.println("Emails are " + response.jsonPath().getList("email"));
    }

    @Test
    public void getZipCode() {
        System.out.println("Zipcodes are " + response.jsonPath().getList("address.zipcode"));
    }

    @Test
    public void getZipCodeWithoutDash() {
        JsonPath jsonPath = response.jsonPath();
        System.out.println("Zipcode without dash-symbol is  " + jsonPath.getList("findAll{!it.address.zipcode.contains('-')}.address.zipcode"));
    }

    @Test
    public void getLatAndLng() {
        List<Object> names = response.jsonPath().getList("name");
        List<Object> geoLat = response.jsonPath().getList("address.geo.lat");
        List<Object> geoLng = response.jsonPath().getList("address.geo.lng");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i) + " is situated at: lat = " + geoLat.get(i) + " and lng = " + geoLng.get(i));
        }
    }
    @Test
    public void getNegativeLatAndLng() {
        JsonPath jsonPath = response.jsonPath();
        System.out.println("Users with negative Lat and Lng are " + jsonPath.getList("findAll{(it.address.geo.lat.contains('-'))&&(it.address.geo.lng.contains('-'))}.username"));
    }
    @Test
    public void getEndOfWebsite() {
        JsonPath jsonPath = response.jsonPath();
        List<Object> names = jsonPath.getList("findAll{it.website.endsWith('.info')}.name");
        List<Object> webpage = jsonPath.getList("findAll{it.website.endsWith('.info')}.website");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i)+" has webpage "+ webpage.get(i));
        }
    }
    @Test
    public void getLngMax() {
        JsonPath jsonPath = response.jsonPath();
        System.out.println("User with max Lng is " + jsonPath.getString("max{it.address.geo.lng}.name"));

    }

    @Test
        public void getLongestCatchPhrase() {
        JsonPath jsonPath = response.jsonPath();
        System.out.println("User with longest CatchPhrase is " + jsonPath.getString("max{it.company.catchPhrase.size()}.name"));
    }
}









