package com.retailpos.objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Inventory {

	public static By New=By.name("New");
	public static By StockCode=By.id("100");
	public static By Description=By.id("101");
	public static By RegPrice=By.id("102");
	public static By Qty=By.id("103");
	public static By ItemType=By.id("106");
	public static By Save=By.name("Save");
	public static By Exit=By.name("Exit");
	public static By CantSave = By.name("CAN'T SAVE");
	public static By passWord;
	public static By userName;
}
