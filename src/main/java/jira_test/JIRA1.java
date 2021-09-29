package jira_test;

import org.testng.annotations.Test;

import files.Payload;
import files.Reusablemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class JIRA1 {
	String jsessionid = null;
	String issueid=null;
	

	@Test(priority=0)
	public void jira_login() {
		RestAssured.baseURI="http://localhost:8080";
		String response =given().log().all().body(Payload.jiralogin())
		.header("Content-Type","application/json").when()
		.post("/rest/auth/1/session").then().log().all()
		.assertThat().statusCode(200).extract().asString();
		JsonPath js =Reusablemethods.rawjson(response);
		 jsessionid=js.get("session.value");
		System.out.println(jsessionid);
	}
	
	@Test(enabled=true,priority=1)
	public void create_issue() {
		RestAssured.baseURI="http://localhost:8080";
		String Response=given().log().all().body(Payload.create_issue())
.header("Content-Type","application/json").header("cookie","JSESSIONID="+jsessionid).when().post("/rest/api/2/issue/")
.then().log().all().extract().asString();
		JsonPath js =Reusablemethods.rawjson(Response);
		issueid=js.getString("id");
		
	}
	
	@Test(enabled=true,priority=2)
	public void add_comment() {
		RestAssured.baseURI="http://localhost:8080";
		given().log().all().pathParam("id", issueid).header("Content-Type","application/json").header("cookie","JSESSIONID="+jsessionid)
		.body("{\r\n" + 
				"    \"body\": \"add comment to this issue.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").when().post("/rest/api/2/issue/{id}/comment").then().log().all();
	}
}
