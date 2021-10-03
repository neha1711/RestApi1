package test1;

import org.testng.Assert;

import files.Payload;

import io.restassured.path.json.JsonPath;

public class Basics2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
JsonPath js = new JsonPath(Payload.body2());
int coursecount=js.getInt("courses.size()");
System.out.println(coursecount);
int purchaseamt=js.getInt("dashboard.purchaseAmount");
System.out.println(purchaseamt);
String title1=js.getString("courses[0].title");
System.out.println(title1);
int sum_course_prices=0;
for(int i =0;i<coursecount;i++) {
	System.out.println(js.getString("courses["+i+"].title")+" : "+js.getString("courses["+i+"].price"));
	
}
for(int i =0;i<coursecount;i++) {
	String coursetitle=js.getString("courses["+i+"].title");
	if (coursetitle.equals("RPA")){
		System.out.println("copiews of rpa sold is "+js.getInt("courses["+i+"].copies"));
		break;
	}
	
}
for(int i =0;i<coursecount;i++) {

	int each_courseprice=js.getInt("courses["+i+"].price");
	int copies_per_course=js.getInt("courses["+i+"].copies");
	sum_course_prices+=(each_courseprice*copies_per_course);
}
System.out.println("sum_course_prices : "+sum_course_prices);
Assert.assertEquals(sum_course_prices, purchaseamt,"price didn't match");
System.out.println("change to basics 2");
	}


}
