import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

import files.Payload;
import files.ReUsablesFunctions;

public class TestAPI {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		String resonse = given().log().all().queryParam("key", "qaclick123")
	    .header("Content-Type","application/json")
		.body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		 
		 JsonPath js = new JsonPath(resonse);
		 
		String placeId = js.getString("place_id");
		 
		 System.out.print(placeId);
		 
		String newAddress = "35 summer walk, USA";
		given().log().all().queryParam("key", "qaclick123")
		    .header("Content-Type","application/json")
		    .body("{\r\n"
		    		+ "\"place_id\":\""+placeId+"\",\r\n"
		    		+ "\"address\":\""+newAddress+"\",\r\n"
		    		+ "\"key\":\"qaclick123\"\r\n"
		    		+ "}\r\n"
		    		+ "").when().put("/maps/api/place/update/json")
		    .then().log().all().assertThat().statusCode(200)
		    .body("msg", equalTo("Address successfully updated"));
		
		
		
		String getplaceResponse = given().log().all().queryParam("key", "qaclick123")
			    .queryParam("place_id",placeId)
			    .when().get("/maps/api/place/get/json")
			    .then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 =ReUsablesFunctions.rawToJosn(getplaceResponse);
		String actualAddress =js1.getString("address");
		
		
		Assert.assertEquals(actualAddress,newAddress);
	
	}

}
