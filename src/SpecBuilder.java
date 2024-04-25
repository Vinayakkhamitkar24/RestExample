import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import Pojo.AddPlace;
import Pojo.Location;
import java.util.*;

public class SpecBuilder {
	
	public static void main(String[] args) {
	
		RestAssured.baseURI ="https://rahulshettyacademy.com";
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("French-IN");
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shoe");
		ap.setTypes(myList);
		ap.setWebsite("https://rahulshettyacademy.com");
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);
		
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		RequestSpecification reqSpec= given().spec(req).body(ap);
		
		ResponseSpecification res =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		Response response = reqSpec.when().post("/maps/api/place/add/json").then().spec(res).extract().response();
		
		String responseString = response.asString();
		
		//String res = given().queryParam("key","qaclick123")
		//.body(ap).log().all().when().post("/maps/api/place/add/json").then().log().all().extract().response().asString();
		
		System.out.println(responseString);
	}

}
