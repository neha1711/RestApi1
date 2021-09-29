package oauth2_test;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.json.Json;
import org.testng.Assert;

import files.Reusablemethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class Oauthtest {
  
	public static void main(String[] args) {
		String [] course_titles= {"Selenium Webdriver Java","Cypress","Protractor"};
		// TODO Auto-generated method stub
		/*ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[@type='email']")).sendKeys("nehakumari0994");
		driver.findElement(By.cssSelector("input[@type='password']")).sendKeys("nehagupta1711");
		driver.findElement(By.cssSelector("input[@type='password']")).sendKeys(Keys.ENTER);*/
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWjni-ul87SREgQgaTzyNeynsiqCVoblexJTBn9MKtz8aQyjKnZ2ieTB4lGOooZIFQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=1&prompt=none#";
		String partial_code=url.split("code=")[1];
		String code=partial_code.split("&scope")[0];
		System.out.println(code);
		//get code
		
		
		//access_token
		String access_token_response=given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		JsonPath js = Reusablemethods.rawjson(access_token_response);
		String access_token_code=js.get("access_token");
		System.out.println("access token :"+access_token_code);
		
		//actual request
		GetCourse gc=given().queryParam("access_token", access_token_code)
				.expect().defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		List<Api> apicourses=gc.getCourses().getApi();
		for(int i=0;i<apicourses.size();i++) {
			if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println("course price is " +apicourses.get(i).getPrice());
				
			}
		}
		
	List<WebAutomation> wacourses=gc.getCourses().getWebAutomation();
	for(int i=0;i<wacourses.size();i++) {
		System.out.println(wacourses.get(i).getCourseTitle());
		
		
	}
	
ArrayList<String> actual_courses = new ArrayList<String>();
for(int i =0;i<wacourses.size();i++) {
	actual_courses.add(wacourses.get(i).getCourseTitle());
	
}
List<String> expected_courses=Arrays.asList(course_titles);
Assert.assertTrue(actual_courses.equals(expected_courses));

System.out.println("course matches ....!!!!!!!");
}
}
