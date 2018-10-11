package com.retailpos.scripts;
import java.io.File;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.qentelli.automation.accelerators.Base;
import com.retailpos.bussinessLibrary.Demo_Buisiness_Methods;
import com.retailpos.bussinessLibrary.TestException;
import com.retailpos.objectRepository.HomePage;
import com.retailpos.objectRepository.Inventory;

public class AddInventory extends Demo_Buisiness_Methods{

	@Test(groups={"smoke"})
	public void AddInventoryTest() throws Throwable {
		boolean flag = true;
		try{

			gf_WaitForElementPresent(HomePage.POS,5000);
			
			gf_Click(HomePage.POS,"POS");
			Thread.sleep(5000);
			//gf_WaitForElementPresent(By.id("location"), "Location");
			//gf_SelectByIndex(By.id("location"),8, "Location");
		}
		catch (Exception e) {
		//	fixTheIssue(e);
		}
	}
}
