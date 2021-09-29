package jira_test;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import files.Reusablemethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JIRA_Sessionfilter {
	
	SessionFilter session=new SessionFilter();
	String expected_message="message to validate";
	//httpvalidation - use relaxhttpvalidation
	@Test
	public void jira_login() {
		RestAssured.baseURI="http://localhost:8080";	
	
	String response =given().relaxedHTTPSValidation().log().all().body(Payload.jiralogin())
	.header("Content-Type","application/json").filter(session).when()
	.post("/rest/auth/1/session").then().log().all()
	.assertThat().statusCode(200).extract().asString();
	/*String response_issue=given().log().all().body(Payload.create_issue())
	.header("Content-Type","application/json").filter(session).when().post("/rest/api/2/issue/")
	.then().log().all().extract().asString();
	JsonPath js =Reusablemethods.rawjson(response_issue);
	String issueid=js.getString("id");*/
	
	String comment_res=given().log().all().pathParam("id", "10305").header("Content-Type","application/json")
	.filter(session).body("{\r\n" + 
			"    \"body\": \""+expected_message+"\",\r\n" + 
			"    \"visibility\": {\r\n" + 
			"        \"type\": \"role\",\r\n" + 
			"        \"value\": \"Administrators\"\r\n" + 
			"    }\r\n" + 
			"}").when().post("/rest/api/2/issue/{id}/comment").then().log().all()
	.assertThat().statusCode(201).extract().asString();
	JsonPath js = Reusablemethods.rawjson(comment_res);
	String comment_id=js.get("id");
	
	
	
	/*given().log().all().filter(session).pathParam("id","10305").header("X-Atlassian-Token","no-check")
	.multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/jira.txt")).post("rest/api/2/issue/{id}/attachments")
	.then().log().all().assertThat().statusCode(200);*/
	
	//get issue
	
	String getissue=given().log().all().filter(session).pathParam("id","10305")
	.queryParam("fields", "comment").when().get("/rest/api/2/issue/{id}").then().log().all()
	.extract().asString();
	System.out.println(getissue);
	
	//validate comment
	JsonPath js1 = Reusablemethods.rawjson(getissue);
	int commentsize=js1.get("fields.comment.comments.size()");
	for(int i =0;i<commentsize;i++) {
		String idreq=js1.get("fields.comment.comments["+i+"].id");
		System.out.println(idreq);
		if(idreq.equals(comment_id)) {
		String actual_message=js1.get("fields.comment.comments["+i+"].body");
		Assert.assertEquals(actual_message, expected_message);
		}
		
	}
}
}
