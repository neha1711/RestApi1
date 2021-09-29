package test1;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class sumvalidations {
	@Test
	public void sum_of_courses() {
		
		JsonPath js = new JsonPath(Payload.body2());
		int coursecount=js.getInt("courses.size()");
		int sum_course_prices=0;
		int purchaseamt=js.getInt("dashboard.purchaseAmount");
		for(int i =0;i<coursecount;i++) {

			int each_courseprice=js.getInt("courses["+i+"].price");
			int copies_per_course=js.getInt("courses["+i+"].copies");
			sum_course_prices+=(each_courseprice*copies_per_course);
		}
		System.out.println("sum_course_prices : "+sum_course_prices);
		Assert.assertEquals(sum_course_prices, purchaseamt,"price didn't match");
	}
}
