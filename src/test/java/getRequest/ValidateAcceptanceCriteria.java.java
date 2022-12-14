package getRequest;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ValidateAcceptanceCriteria {
	
	Response resp;
	String Name;
	boolean CanRelist;
		
	@BeforeClass
	public void setupMethod(){
		
		//Service response is assigned to variable resp. 
		resp=RestAssured.get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false");
	}
	
	/**
	 *The purpose of this test case is to verify whether above API is up and running 
	 *
	 */
	
	@Test(priority = 0)
	public void verifyResponsecode()
	{
		//Verify whether the response status code will return 200(SUCCESS).	
	    int code= resp.getStatusCode();
	    System.out.println("Status code"+code);
	    Assert.assertEquals(code, 200); 
	    
	    if(code == 200) {
	    	System.out.println("Successfully connected");
	    	
	    }
	    else {
	    	System.out.println("Unable to connect");
	    }
	    
	  }
		
	/**
	 * The purpose of this test case is to validate the first acceptance criteria.
	 * Verify whether name will return as "Carbon credits".
	 */
			
	@Test(priority = 1)
	public void findName()
	{		
		Name = resp.jsonPath().getString("Name");
		Assert.assertEquals(Name, "Carbon credits");
		System.out.println("Name ="+ Name);
	}
		
	/**
	 * The purpose of this test case is to validate the second acceptance criteria
	 * Verify whether CanRelist will return true value
	 */
	
	@Test(priority = 2)
	public void findCanRelist()
	{
		CanRelist = resp.jsonPath().getBoolean("CanRelist");
		Assert.assertEquals(CanRelist, true);
		System.out.println("CanRelist ="+CanRelist);
	}

	/**
	 * The purpose of this test case is to validate the third acceptance criteria
	 * 
	 */
		
	@Test(priority = 3)
	public void findDescInGallryItem()
	{
	
	String strRes = resp.asString();
	boolean flag = false;
	
	//Getting Promotions JSON object array into a Java List<HashMap>. 
	List<HashMap> promo = JsonPath.from(strRes).getList("Promotions");
	
	//Iterating Promotions list.
	for(HashMap obj : promo){
		
		//Identifying the expected object (Gallery).
		if(obj.get("Name").equals("Gallery")) {
			
			//Getting Gallery element's description through JsonPath get method.
			String desc = (String) obj.get("Description");
			
			if(desc.contains("Good position in category")) {
				flag = true;
				break;
			}
		}
						
	}
	
	if(flag==true)
	{
		System.out.println("The Promotions element with Name = \"Gallery\" has a Description that contains the text \"Good position in category\"");
	}
	else
	{
		System.out.println("The Promotions element with Name = \"Gallery\" has not a Description that contains the text \"Good position in category\"");
	}
	
	//Asserting whether the flag is true.
	Assert.assertEquals(flag, true);
	
	}
		
}
