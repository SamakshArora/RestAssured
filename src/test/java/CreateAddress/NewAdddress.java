package CreateAddress;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.RequestPayload;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;



public class NewAdddress {
	
	public static String Actualplace_id;
	
	@Test
	public void testApi() {
		
	
		
		RestAssured.baseURI ="https://rahulshettyacademy.com";
		
		// Post Request
		String postRes = given().log().all().queryParam("key","qaclick123")
		.header("Content-Type","application/json")
		.body(RequestPayload.createNewRecords())
		
		.when().post("maps/api/place/add/json")
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(postRes);
		
		String ActualStatus= js.getString("status");
		assertEquals(ActualStatus, "OK");
	
		
		String Actualscope= js.getString("scope");
		assertEquals(Actualscope, "APP");
		
		Actualplace_id= js.getString("place_id");
	
		
		
		// Put Request 
	String putRes=given().log().all().header("Content-Type","application/json").queryParam("place_id",Actualplace_id).body("{\n"
				+ "\"place_id\":\""+Actualplace_id+"\",\n"
				+ "\"address\":\"Haryana\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		
		.then().and().log().all().assertThat().statusCode(200).extract().response().asString();
		
	   JsonPath j2=new JsonPath(putRes);
		 String ActualMSG=j2.getString("msg");
		 assertEquals(ActualMSG, "Address successfully updated");
		
		// Get Request
     	String getRes= given().log().all().queryParam("place_id", Actualplace_id).queryParam("key", "qaclick123")
	 	
		.when().get("maps/api/place/get/json")
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
	

		JsonPath jss = new JsonPath(getRes);
		
		String name=jss.getString("name");
		System.out.println(name);
		
		String address=jss.getString("address");
		System.out.println(address);
		
		// Delete Request
	String	delRes=given().log().all().header("Content-Type","application/json").queryParam("key", "qaclick123").body("{\n"
				+ "\n"
				+ "    \"place_id\":\""+Actualplace_id+"\"\n"
				+ "}\n"
				+ "")
		.when().delete("maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
	JsonPath js4=new JsonPath(delRes);
	String delmsg=js4.getString("status");
	assertEquals(delmsg, "OK");
		
		
		// To check Above Request is deleted or not we have to run Get Request again with 404 status code
	String	DelGetRes= given().log().all().queryParam("place_id", Actualplace_id).queryParam("key", "qaclick123")
		 	
			.when().get("maps/api/place/get/json")
			
			.then().log().all().assertThat().statusCode(404).extract().response().asString();
			
		JsonPath js3 = new JsonPath(DelGetRes);
		String actMsg1=js3.getString("msg");
		assertEquals(actMsg1, "Get operation failed, looks like place_id  doesn't exists");
	}
	

	
	
	
	
	
	
//	
//	RestAssured.baseURI ="https://web.prepladder.com";
//	
//	given().header("Content-Type","application/json").body("{\n"
//			+ "    \"appName\": \"prepladder\",\n"
//			+ "    \"email\": \"samaksharora18@gmail.com\",\n"
//			+ "    \"apiKey\": \"prpldr_1677652363881:qi645s0lj5dz3oi:2221018\",\n"
//			+ "    \"courseID\": 1,\n"
//			+ "    \"version\": 99,\n"
//			+ "    \"platform\": \"web\"\n"
//			+ "}")
//	.when().post("/v2/dashboard/getDashboard")
//	.then().log().all().statusCode(200);
//}

}
