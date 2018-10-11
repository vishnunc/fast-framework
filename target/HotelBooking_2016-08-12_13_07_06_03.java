package com.aidemo.objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelBooking {

	public static By imagine=By.xpath("//div[@id='imagine']//a[@id='abcdefghik']");
	public static By userName=By.id("username");
	public static By passWord    = By.id("pssword");
	public static By providerName = By.id("ctl00_Content_UcSearchProvider1_txtName");
	public static By proiverAddress = By.id("ctl00_Content_UcSearchProvider1_txtAddress");
	public static By providerCity = By.id("ctl00_Content_UcSearchProvider1_txtCity");
	public static By providerSearch = By.id("ctl00_Content_UcSearchProvider1_btnSearchProv");
	public static By addNewClaim = By.id("ctl00_Content_btnNewClaim");
	public static By DispenseDate = By.id("ctl00_Content_mdbServDate");
	public static By Patient = By.id("ctl00_Content_mddPatient");
	public static By DIN = By.id("ctl00_Content_txtDIN");
	public static By Quantity = By.id("ctl00_Content_txtQuantity");
}
