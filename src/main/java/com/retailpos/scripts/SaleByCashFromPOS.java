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

public class SaleByCashFromPOS  extends Demo_Buisiness_Methods{

	@Test(groups={"smoke"})
	public void SaleByCashFromPOS() throws Throwable {
		System.out.println("Session Started");
		try {
	
		Thread.sleep(10000);
		System.out.println("Click Succeeded");
		gf_Click(HomePage.POS, "POS");
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
			gf_Click(POS.Client,"Client");
			gf_Click(POS.Find, "Find");
			gf_Type(By.id("100"),"Ren","Client Name");
			gf_Click(By.name("Name"),"Name");
			gf_Click(POS.OK,"OK");
			gf_Click(POS.Find, "Find");
			gf_Type(POS.LookUptext,"9999314", "Look Up Item Code");
			gf_Click(POS.WordSearch,"Word Search");
			gf_Click(POS.OK, "OK");
			gf_Click(POS.AccpetEnter,"Accept Enter");
			
			try{
				if(driver.findElement(POS.CustomerOrder).isDisplayed()){
					driver.findElement(POS.OK).click();
				}
			}
			catch(Exception ex){
				
			}
			Thread.sleep(3000);
			gf_Type(POS.ScanItemOREnterStockCode, "9999465","Look Up Item");
			gf_Click(POS.OK, "OK");
			gf_Click(POS.ChgQnt,"Increase Quantity");
			gf_Click(By.name("2"),"Enter Quantity 2");
			gf_Click(POS.OK, "OK");
			gf_Click(POS.AccpetEnter,"Accept Enter");
			try{
				if(driver.findElement(POS.CustomerOrder).isDisplayed()){
					driver.findElement(POS.OK).click();
				}
			}
			catch(Exception ex){
				
			}
			
			/*gf_Click(By.name("6"), "Enter Total");
			gf_Click(POS.OK, "OK");*/
			gf_Click(POS.Pay,"PAY");
			gf_Click(POS.Print,"Print");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
