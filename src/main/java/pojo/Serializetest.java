package pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
public class Serializetest {

	public static void main(String[] args) {
		// TODO Auto-generated metho
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		GetLocation l =new GetLocation();
		l.setAccuracy(50);
		l.setAddress("29, side layout, cohen 09");
		l.setLanguage("French-IN");
		l.setName("Frontline house");
		l.setPhone_number("(+91) 983 893 3937");
		l.setWebsite("http://google.com");
		List<String> t = new ArrayList<String>();
		t.add("shoe park");
		t.add("shop");
		l.setTypes(t);
		Location l1 = new Location();
		l1.setLat(-38.383494);
		l1.setLng(33.427362);
		l.setLocation(l1);
		/*String res=given().queryParam("key","qaclick123").body(l).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.extract().asString();*/
				
		
			RequestSpecification req=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();
			
			RequestSpecification res=given().spec(req).body(l);
			ResponseSpecification response_spec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
			
					String response =res.when().post("/maps/api/place/add/json").then()
							.spec(response_spec).extract().asString();	
					System.out.println(response);
	}

}
