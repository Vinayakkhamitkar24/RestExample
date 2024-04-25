import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUsablesFunctions;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	public static String id;
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI ="http://216.10.245.166";
		String response =given().log().all().header("Content-Type","application/json")
		.body(Payload.addBook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsablesFunctions.rawToJosn(response);
		 id = js.get("ID");
		
		System.out.println(id);	
			
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"lll","314"},{"ppp","332"},{"xxx","840"}};
	}

}
