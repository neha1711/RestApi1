package test1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.Reusablemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Dynamicjson1 {
@Test(dataProvider="Bookdata")
public void addbook(String isbn,String aisle) {
	RestAssured.baseURI="http://216.10.245.166";
	String response=given().log().all().header("Content-Type","application/json")
	.body(Payload.addbook(isbn,aisle)).when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200)
	.extract().response().asString();
	JsonPath js =Reusablemethods.rawjson(response);
	String id =js.get("ID");
	System.out.println("id generates is :" +id);	
	
}
@DataProvider(name="Bookdata")
public Object[][] getData(){
	return new Object[][] {{"oddfgdfg","1234532"},{"sdfdvfdgv","234324"},{"tyttr","4345534"}};
	
}

}
