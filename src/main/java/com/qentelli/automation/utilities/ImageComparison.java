package com.qentelli.automation.utilities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.openqa.selenium.By;

import com.qentelli.automation.accelerators.Actiondriver;

public class ImageComparison extends Actiondriver
{
	public static void imageComparison(String strFileName, By objLocator, String strSrcType) throws Throwable
	{

		try {
			gf_WaitForVisibilityOfElement(objLocator, lWait_Medium);
			String strFilePath = "";
			if(strSrcType.equalsIgnoreCase("File"))
				strFilePath = System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\"+strFileName;
			else if(strSrcType.equalsIgnoreCase("Locator"))
				strFilePath=driver.findElement(By.xpath(strFileName)).getAttribute("src");
			else
				strFilePath = strFileName;
			String file2=driver.findElement(objLocator).getAttribute("src");

			FileOutputStream fos1 = null;

			if(!(strSrcType.equalsIgnoreCase("File")))
			{
				if(strFilePath.toLowerCase().contains("mobile"))
					strFilePath = strFilePath.replace("https", "http");
				URL url = new URL(strFilePath);
				InputStream in = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1!=(n=in.read(buf)))
				{
					out.write(buf, 0, n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();

				fos1 = new FileOutputStream(System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\TestImg1.jpg");
				fos1.write(response);
				fos1.close();

			}
			if(file2.toLowerCase().contains("mobile"))
				file2 = file2.replace("https", "http");
			URL url = new URL(file2);
			
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();

			FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\TestImg.jpg");
			fos.write(response);
			fos.close();
			Image pic1 = null;
			
			if(!(strSrcType.equalsIgnoreCase("File")))
				pic1= Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\TestImg1.jpg");
			else
				pic1= Toolkit.getDefaultToolkit().getImage(strFilePath);
			Image pic2= Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\TestImg.jpg");

			try {

				PixelGrabber grab11 = new PixelGrabber(pic1, 0, 0, -1, -1,
						false);
				PixelGrabber grab21 = new PixelGrabber(pic2, 0, 0, -1, -1,
						false);

				int[] array1= null;

				if (grab11.grabPixels()) {
					int width = grab11.getWidth();
					int height = grab11.getHeight();
					array1= new int[width * height];
					array1= (int[]) grab11.getPixels();
				}

				int[] array2 = null;

				if (grab21.grabPixels()) 
				{
					int width = grab21.getWidth();
					int height = grab21.getHeight();
					array2 = new int[width * height];
					array2 = (int[]) grab21.getPixels();
				}
				if(java.util.Arrays.equals(array1, array2 ))
					Reporters.successReport("Validate the Image in the application", "Image is Successfully Compared <br> The image on the page: "+"\n"+driver.getCurrentUrl()+""+"\n"+" is same as Expected image path is : "+strFilePath);
				else
					Reporters.failureReport("Validate the Image in the application", "Image is Failed to compare into the application <br> The image on the page: "+"\n"+driver.getCurrentUrl()+""+"\n"+" is not same as the compared file",false);

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
		    e.printStackTrace();
			Reporters.failureReport("Validate the Image in the application", "Image is Failed to compare into the application <br> The image on the page: "+"\n"+ driver.getCurrentUrl()+" "+"\n"+" is not same as the compared file",false);

		}
		finally
		{
			File file = new File(System.getProperty("user.dir").replace(File.separator, "\\")+"\\ApplicationFiles\\TestImg.jpg");
			if(file.exists())
				file.delete();
		}

	}
}
