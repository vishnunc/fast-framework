package com.retailpos.scripts;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.retailpos.bussinessLibrary.Demo_Buisiness_Methods;
import com.retailpos.objectRepository.HomePage;
import com.retailpos.objectRepository.Inventory;
import com.retailpos.objectRepository.POS;

public class AddItemToInventoryWithInvalidData  extends Demo_Buisiness_Methods{

	@Test(groups={"smoke"})
	public void AddItemToInventoryWithInvalidData() throws Throwable {
		System.out.println("Session Started");
		try {
	
		Thread.sleep(10000);
		System.out.println("Click Succeeded");
		gf_Click(HomePage.Inventory, "Inventory");
			try {
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gf_Click(Inventory.New, "New Item");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int random = (int )(Math.random() * 499 + 1);
			gf_Type(Inventory.StockCode, "9999"+random, "Stock Code");
			//gf_Click(Inventory.Description,"Description");
			Thread.sleep(2000);
		//	driver.findElement(By.id("101")).sendKeys("Hello This is Sample Description Please consider");
		//	gf_Type(Inventory.Description,"This is New Product for any feature use please save this","Item Description");
			gf_Type(Inventory.RegPrice, "2.00", "Item reg Price");
			gf_Type(Inventory.Qty, "10", "Quantity");
			Thread.sleep(5000);
			gf_Click(Inventory.ItemType,"ItemType Drop Down");
			gf_Click(By.name("KIT"),"KIT");
			gf_Click(Inventory.Save,"Save");
			System.out.println("Added New Item");
			Thread.sleep(4000);
			gf_IsElementNotPresent(POS.CantSave, "Cant Save Due to Invalid Data or Missing Information");
			try{
				if(gf_IsElementPresent(POS.CantSave, "Cant Save")){
					gf_Click(POS.OK, "OK");
				}
			}
		catch(Exception ex){
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
