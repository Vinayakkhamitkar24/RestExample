package files;

import io.restassured.path.json.JsonPath;

public class ReUsablesFunctions {
	
	public static JsonPath rawToJosn(String response)
	{
		JsonPath js1 = new JsonPath(response);
		return js1;
	}

}
