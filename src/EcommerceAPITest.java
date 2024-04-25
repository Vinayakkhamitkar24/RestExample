import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.OrderDetails;
import Pojo.Orders;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.List;

import org.testng.Assert;

import java.util.*;
public class EcommerceAPITest {

	public static void main(String[] args) {

		
		//Login
		RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("");
		loginRequest.setUserPassword("");

		RequestSpecification reqSpec = given().log().all().spec(req).body(loginRequest);
		LoginResponse loginRes =reqSpec.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		System.out.println(loginRes.getToken());
		System.out.println(loginRes.getUserId());
		
		//Add Product
		String authToken = loginRes.getToken();
		RequestSpecification addProduct =new RequestSpecBuilder().addHeader("authorization",authToken).setBaseUri("https://rahulshettyacademy.com/").build();
		RequestSpecification addProductReq=given().log().all().spec(addProduct).formParam("productName", "Laptop")
		.formParam("producetAddedBy", loginRes.getUserId())
		.formParam("productCategory", "fashion")
		.formParam("productSubCategory", "shirt")
		.formParam("productPrice", "10000")
		.formParam("productDescription", "Lene")
		.formParam("productFor", "men")
		.multiPart("productImage",new File("roi-001-neg-887d8-max-fill-highlighted-f802f6f6.png"));
		
		String addProducetresponse=addProductReq.when().post("/api/ecom/product/add-product")
				.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProducetresponse);
		String productId=js.get("productId");
		
		System.out.println(productId);
		
		//create Order
		RequestSpecification createOrder =new RequestSpecBuilder().addHeader("authorization",authToken)
				.setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
		
		OrderDetails orld = new OrderDetails();
		orld.setCountry("India");
		orld.setProductOrderedId(productId);
		List<OrderDetails> orldList = new ArrayList<OrderDetails>();
		orldList.add(orld);
		
		Orders or = new Orders();
		or.setOrders(orldList);
		
		RequestSpecification createOrderSpec=given().log().all().spec(createOrder).body(or);
		
		String createOrderResponse=createOrderSpec.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		
		//Delete Product
		
		RequestSpecification deleteProduct =new RequestSpecBuilder().addHeader("authorization",authToken).setBaseUri("https://rahulshettyacademy.com/").build();
		RequestSpecification deleteProductReq=given().log().all().spec(deleteProduct).pathParam("productId", productId);
		String deleteResponse=deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
	}

}
