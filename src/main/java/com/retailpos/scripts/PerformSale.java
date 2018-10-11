package com.retailpos.scripts;
import java.io.File;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.retailpos.bussinessLibrary.Demo_Buisiness_Methods;
import com.retailpos.bussinessLibrary.TestException;
import com.retailpos.objectRepository.Inventory;

public class PerformSale extends Demo_Buisiness_Methods{
	@Test(groups={"smoke"})
	public void Change_ObjectProperty() throws Throwable {
		boolean flag = true;
		try{
			gf_LaunchUrl(configProps.getProperty("adactin"));
			gf_WaitForElementPresent(By.id("username"), "userName");
			login();
			gf_WaitForElementPresent(By.id("location"), "Location");
			gf_SelectByIndex(By.id("location"),8, "Location");
		}
		catch (Exception e) {
			System.out.println("This is Exception");
		}
	}
}
