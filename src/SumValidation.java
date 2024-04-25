import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumValidationMethod()
	{
		int sum=0;

		JsonPath js = new JsonPath(Payload.coursePrice());
		int count =js.get("courses.size()");

		//Print purchase amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		//System.out.println(purchaseAmount);

		//Print All courses title and there price 
		for(int i=0; i<count ;i++)
		{
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");

			int total= price * copies;
			//System.out.println(total);
			sum = sum +total;
			//System.out.println(sum);

		}
		Assert.assertEquals(sum,purchaseAmount);
		//int sum = sum+total;
	}

}
