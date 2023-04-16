import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;


public class TestUsers {
    @Test
    public void sendGetRequest() {
        RestAssured.baseURI ="https://jsonplaceholder.typicode.com";
        Response response = given().
                contentType(ContentType.JSON).
                when().
                get("users").
                then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
        System.out.println("Emails are "+response.jsonPath().getList("email"));
        System.out.println("Zipcodes are "+response.jsonPath().getList("address.zipcode"));
        List<Object> zipCodes = response.jsonPath().getList("address.zipcode");
       for(int i = 0; i < zipCodes.size(); i++) {
           if (zipCodes.get(i).toString().length()==5) {
               System.out.println("Zipcode without dash-symbol is  "+zipCodes.get(i));
           }
       }
        List<Object> names = response.jsonPath().getList("name");
        List<Object> geoLat = response.jsonPath().getList("address.geo.lat");
        List<Object> geoLng = response.jsonPath().getList("address.geo.lng");
        for(int i = 0; i < names.size(); i++) {
           System.out.println(names.get(i)+" is situated at: lat = "+geoLat.get(i)+" and lng = "+geoLng.get(i));
        }
        List<Object> usernames = response.jsonPath().getList("username");

        for(int i = 0; i < usernames.size(); i++) {
            if((geoLat.get(i).toString().contains("-")) && (geoLng.get(i).toString().contains("-"))) {
                System.out.println(usernames.get(i) + "has negative lat and lng.");
            }
        }
        List<Object> websites = response.jsonPath().getList("website");
        for(int i = 0; i < usernames.size(); i++) {
            if((websites.get(i).toString().contains(".info"))) {
                System.out.println(usernames.get(i) + " website contain info.");
            }
        }
        List<Long> geoLng_1 = response.jsonPath().getList("address.geo.lng");
        int index =geoLng_1.indexOf(Collections.max(geoLng_1));
        System.out.println(names.get(index)+ " this is user with max lng");

        List<Object> catchPhrases = response.jsonPath().getList("company.catchPhrase");
        int longestValue = 0;
        int indexOfLongestValue = 0;
        for(int i = 0; i < names.size(); i++) {
            if(catchPhrases.get(i).toString().length()>longestValue) {
                longestValue=catchPhrases.get(i).toString().length();
                indexOfLongestValue=i;
            }
            }
        System.out.println(names.get(indexOfLongestValue)+ " this is user with longest catchPhrase");
        }






    }





