package test1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import files.Payload;
import files.Reusablemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Static1 {
	public static void main(String[] args) throws IOException {
//json data is in file
	//content of file to string>>
		//content of file convert to byte>>byte data to String
	RestAssured.baseURI="https://rahulshettyacademy.com";
	String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body(Payload.getresource(System.getProperty("user.dir")+"/src/test/resources/addbook.json")).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
	.body("scope",equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().asString();
	//Add place -> update place with new address -> get place to validate if new add is present or not

	System.out.println("response is \n"+response);

	//parse json
	JsonPath js = Reusablemethods.rawjson(response);
	String plcid=js.getString("place_id");
	System.out.println(plcid);	
}
	}
