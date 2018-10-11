package com.qentelli.automation.utilities;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.qentelli.automation.accelerators.Base;
import com.qentelli.automation.datadriven.ReadResourceData;
import com.qentelli.automation.utilities.HtmlReporters;

public class SendMail extends HtmlReporters{
	static String USER_NAME = "";
	static String PASSWORD = "";
	static String TOADDRESS = "";
	static String CC_ADDRESS = "";
	static String BCC_ADDRESS = "";


	public static void attachReportsToEmail() throws Exception {
		SendMail.fn_EmailDetials();
		
		String[] to=TOADDRESS.split(",");;
		String[] cc=CC_ADDRESS.split(",");
		String[] bcc=BCC_ADDRESS.split(",");


		//This is for google
		SendMail.sendMail(USER_NAME,
				PASSWORD,
				"smtp.gmail.com",
				"465",
				"true",
				"true",
				true,
				"javax.net.ssl.SSLSocketFactory",
				"false",
				to,
				cc,
				bcc,
				"Automation Test Results"); 

	}
	public  static boolean sendMail(String userName,
			String passWord,
			String host,
			String port,
			String starttls,
			String auth,
			boolean debug,
			String socketFactoryClass,
			String fallback,
			String[] to,
			String[] cc,
			String[] bcc,
			String subject) {

		final String user = userName;
		final String pass = passWord;

		//		String strReportsFolder="Firefox";

		Properties props = new Properties();

		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.password", passWord);
		props.put("mail.smtp.host", host);

		if(!"".equals(port))

			props.put("mail.smtp.port", port);

		if(!"".equals(starttls))

			props.put("mail.smtp.starttls.enable",starttls);

		props.put("mail.smtp.auth", auth);



		if(debug){

			props.put("mail.smtp.debug", "true");

		}else{

			props.put("mail.smtp.debug", "false");         

		}

		if(!"".equals(port))

			props.put("mail.smtp.socketFactory.port", port);

		if(!"".equals(socketFactoryClass))

			props.put("mail.smtp.socketFactory.class",socketFactoryClass);

		if(!"".equals(fallback))

			props.put("mail.smtp.socketFactory.fallback", fallback);



		try

		{

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pass);
				}
			});

			session.setDebug(debug);

			MimeMessage msg = new MimeMessage(session);

			msg.setSubject(subject);

			BodyPart messageBodyPart1 = new MimeBodyPart();  
			StringBuffer table3=callhtml();
			messageBodyPart1.setContent(table3.toString(),"text/html"); 

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(userName));

			for(int i=0;i<to.length;i++){

				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));

			}

			for(int i=0;i<cc.length;i++){
				if(!(cc[i].equalsIgnoreCase("")))
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));

			}

			for(int i=0;i<bcc.length;i++){
				if(!(bcc[i].equalsIgnoreCase("")))
					msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));

			}

			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			transport.connect(host, userName, passWord);

			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			return true;

		}

		catch (Exception mex)

		{

			mex.printStackTrace();

			return false;

		}

	}
	@SuppressWarnings("rawtypes")
	private static StringBuffer callhtml() throws UnknownHostException

	{
		StringBuffer str= new StringBuffer("<TABLE border=0 cellSpacing=1 cellPadding=1 width='100%'>"
				+"<DIV><TABLE border=1 cellSpacing=1 cellPadding=1 width='100%' font='arial'>"
				+"<TR><TD colspan='2' align='center' bgcolor='FFCC99'><FONT COLOR='#000000' FACE='Arial' SIZE=3><b>Execution Status</b></TD></TR>");

		Iterator<Entry<String, String>> iterator = map.entrySet()
				.iterator();
		pCount = 0;
		fCount = 0;
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			String value = mapEntry.getValue().toString();
			Base.sTestResult = mapEntry.getValue().toString();
			if (value.equals("PASS")) {
				pCount += 1;
			} else {
				fCount += 1;
			}
			str.append("</tr>");
		}

		str.append("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Suite</b></td>"
				+"<td width=150 align='left'><FONT FACE='Arial' SIZE=2><b>"+ currentSuit + "</b></td></tr>"
				+"<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Executed</b></td>"
				+"<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"+ map.size() + "</b></td></tr>"
				+"<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Passed</b></td>"
				+"<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"+ pCount + "</b></td></tr>"
				+"<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Failed</b></td>"
				+"<td width=150 align='left'color='red' ><FONT FACE='Arial' SIZE=2><b>"+ fCount + "</b></td></tr>"
				+"<TR><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Suite Execution Time</b></td>"
				+ "<TD width=150 align='left'color='red' ><FONT FACE='Arial' SIZE=2><b>"+ ((int)iSuiteExecutionTime) + " secs" + "</b></TD></TR></TABLE></DIV></br>");
		int iSno = 1;
		// Create Summary report third table
		str.append("<DIV><TABLE border=1 cellSpacing=1 cellPadding=1 width='100%' font='arial'>"
				+"<tr><td colspan='4' align='center'bgcolor='FFCC99'><FONT COLOR='#000000' FACE='Arial' SIZE=3><b>Summary Report</b></td></tr>"
				+ "<td align='center' width='10%'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Sno</b></td>"
				+"<td align='center' width='50%'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Test Case</b></td>"
				+ "<td align='center' width='20%'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Browser Selected</b></td>"
				+ "<td align='center' width='20%'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Status</b></td>");


		Iterator<Entry<String, String>> iterator1 = map.entrySet()
				.iterator();

		while (iterator1.hasNext()) {

			Map.Entry mapEntry1 = (Map.Entry) iterator1.next();
			key = mapEntry1.getKey().toString().split(":");
			String b1 = (String)key[1];
			String sBrowser = b1.toString().split(",")[1];
			String value = (String) mapEntry1.getValue();
			str.append("<tr><td><CENTER><FONT color=#000000 size=2 face=Arial><B>"+ (iSno++) + "</B></td>"
					+"<td><FONT color=#000000 size=2 face=Arial><B>"+ key[1].split(",")[0] + "</B></td>"
					+"<td ><CENTER><FONT color=#000000 size=2 face=Arial><B>"+ sBrowser + "</B></td>");


			if (value.equals("PASS")) {
				str.append("<TD width='30%' bgcolor=green align=center><FONT color=white size=2 face=Arial>PASS</td>");
			} else {
				str.append("<TD width='30%' bgcolor=red align=center><FONT color=white size=2 face=Arial>FAIL</td>");
			}

			// Change
			str.append("</tr>");
		}
		str.append("</table bgcolor='#99CCFF'></div></div>");

		return str;


	}
	public static void fn_EmailDetials() throws Exception
	{
		ReadResourceData.readEmailDetailsData();
		USER_NAME = ReadResourceData.EmailDetailsMap.get("USER_NAME");
		PASSWORD = ReadResourceData.EmailDetailsMap.get("PASSWORD");
		TOADDRESS = ReadResourceData.EmailDetailsMap.get("TOADDRESS");
		CC_ADDRESS = ReadResourceData.EmailDetailsMap.get("CC_ADDRESS");
		BCC_ADDRESS = ReadResourceData.EmailDetailsMap.get("BCC_ADDRESS");
	}

}
