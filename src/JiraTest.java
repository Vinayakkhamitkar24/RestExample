import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import java.io.File;

import org.testng.Assert;

import io.restassured.filter.session.SessionFilter;

public class JiraTest {

	public static void main(String[] args) {

		// Login and get Session Id in SessionFilter
		RestAssured.baseURI ="http://localhost:8080";

		SessionFilter session = new SessionFilter();
		String response= given().header("Content-Type" ,"application/json").body("{ \"username\": \"Vinayak\", \"password\": \"Vinayak@24\" }").log().all()
				.filter(session)
				.when().post("/rest/auth/1/session").then().log().all().extract().response().asString();


		//Add Comment
		String expectedMsg = "My automation comment";
		String addComment = given().pathParam("Key", "10002")
				.header("Content-Type" ,"application/json")
				.body("{\r\n"
						+ "    \"body\": \""+expectedMsg+"\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}").filter(session)
				.log().all().when().post("/rest/api/2/issue/{Key}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(addComment);
		String commentId=js.get("id");

		//Add attachment
		given()
		.header("X-Atlassian-Token","no-check")
		.header("Content-Type","multipart/form-data")
		.pathParam("Key", "10002")
		.filter(session).multiPart("file",new File("JiraText.txt")).log().all()
		.when().post("/rest/api/2/issue/{Key}/attachments").then().log().all().assertThat().statusCode(200);

		//Get Issue Details
		String issueDetails=given().filter(session).pathParam("Key", "10002")
				.queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{Key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);

		JsonPath js1 = new JsonPath(issueDetails);
		int coumentCount = js1.getInt("fields.comment.comments.size()");

		for (int i=0; i<coumentCount ; i++)
		{
			String cmtId=js1.get("fields.comment.comments["+i+"].id").toString();
			if(cmtId.equals(commentId))
			{
				String myComment=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(myComment);
				Assert.assertEquals(myComment,expectedMsg);
				//break;
			}

		}

	}

}
