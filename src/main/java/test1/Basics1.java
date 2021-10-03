package test1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.Reusablemethods;

public class Basics1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
RestAssured.baseURI="https://rahulshettyacademy.com";
String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
.body(Payload.body()).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
.body("scope",equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().asString();
//Add place -> update place with new address -> get place to validate if new add is present or not

System.out.println("response is \n"+response);
System.out.println("git commit");
System.out.println("git xx");
//parse json
JsonPath js = Reusablemethods.rawjson(response);
String plcid=js.getString("place_id");
System.out.println(plcid);

String new_address="70 Summer walk, USA";
//update
given().log().all().queryParam("key", "qaclick123").header("Contract-Type","application/json")
.body("{\r\n" + 
		"\"place_id\":\""+plcid+"\",\r\n" + 
		"\"address\":\""+new_address+"\",\r\n" + 
		"\"key\":\"qaclick123\"\r\n" + 
		"}\r\n" + 
		"").when().put("maps/api/place/update/json").then().log().all()
.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

//gget place
String getplaceresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",plcid)
.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200)
.extract().asString();
System.out.println(getplaceresponse);

JsonPath js1=Reusablemethods.rawjson(getplaceresponse);
String actual_address=js1.getString("address");
System.out.println(actual_address);

Assert.assertEquals(actual_address, new_address,"address not found");

}

}
