import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.Courses;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
import java.util.*;
public class OAuthTest {

	public static void main(String[] args) {


		String[] courseTitleList = {"Selenium Webdriver Java","Cypress","Protractor"};
		//RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
		String response =given().
				formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust")
				.when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");

		GetCourse gc =given().queryParam("access_token", accessToken)
				.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());


		//get price of specific course using pojo
		String courseTitle = gc.getCourses().getApi().get(1).getCourseTitle();

		List<Api> courseList =gc.getCourses().getApi();

		for(int i=0 ;i<courseList.size();i++)
		{
			String title=courseList.get(i).getCourseTitle();
			if(title.equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(courseList.get(i).getPrice());
			}
		}

		//get all title of webautomation course using pojo
		ArrayList<String> a = new ArrayList<String>();
		
		List<WebAutomation> webCourseList =gc.getCourses().getWebAutomation();
		for(int i=0 ;i<webCourseList.size();i++)
		{
			a.add((webCourseList.get(i).getCourseTitle()));
		}

		List<String> expectedTitle = Arrays.asList(courseTitleList);
		
		Assert.assertTrue(a.equals(expectedTitle));
	}

}
