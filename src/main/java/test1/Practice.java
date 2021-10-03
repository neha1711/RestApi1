package test1;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class Practice {
	public static void main(String[] args) {
	JsonPath js = new JsonPath(Payload.body2());
	int coursecount=js.getInt("courses.size()");
	System.out.println("coursecount");
	int purchaseamt=js.getInt("dashboard.purchaseAmount");
	System.out.println("purchaseamt");
	String title1=js.getString("courses[0].title");
	System.out.println("title1");
	System.out.println("title2");
}
	public static void test1() {
		System.out.println("title3");
	}
}