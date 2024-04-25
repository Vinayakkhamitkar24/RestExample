import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import Pojo.AddPlace;
import Pojo.Location;
import java.util.*;

public class Serilization {
	
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
		
		
		String res = given().queryParam("key","qaclick123")
		.body(ap).log().all().when().post("/maps/api/place/add/json").then().log().all().extract().response().asString();
		
		System.out.println(res);
	}

}
