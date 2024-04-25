import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {

	public static void main(String[] args) {
		
		//Print number of courses
		JsonPath js = new JsonPath(Payload.coursePrice());
		int count =js.get("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//print title of first course
		String firstcourseTitle = js.get("courses[0].title");
		System.out.println(firstcourseTitle);
		
		//Print All courses title and there price 
		for(int i=0; i<count ;i++)
		{
			String title = js.get("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			
			System.out.println(title);
			System.out.println(price);
		}
		
		//print specific course number of copies
		for(int i=0; i<count ;i++)
		{
			String title = js.get("courses["+i+"].title");
			
			if(title.equalsIgnoreCase("Appium"))
			{
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
			
		}

	}

}
