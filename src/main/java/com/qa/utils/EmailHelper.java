package com.qa.utils;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class EmailHelper {
	 TestUtils utils = new TestUtils();
	
	public String getOTPFromEmailText(String email, String password, String subjectOfMail) throws Exception {
		Properties props = System.getProperties();
		String result ="";
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com", email, password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);

		System.out.println("Total Message:" + folder.getMessageCount());
		System.out.println("Unread Message:" + folder.getUnreadMessageCount());

		Message[] messages = null;
		boolean isMailFound = false;
		Message mailFromProx = null;

		// Search for mail from Prox with Subject = 'Email varification Testcase'
		try{
			for (int i = 0; i <= 5; i++) {
		

			messages = folder.search(new SubjectTerm(subjectOfMail), folder.getMessages());
			// Wait for 20 seconds
			if (messages.length == 0) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
		}

		// Search for unread mail
		// This is to avoid using the mail for which
		// Registration is already done
		for (Message mail : messages) {
			if (!mail.isSet(Flags.Flag.SEEN)) {
				mailFromProx = mail;
				//Log.info("Message Count is: " + mailFromProx.getMessageNumber());
				isMailFound = true;
			}
		}

		// Test fails if no unread mail was found
	/*	if (!isMailFound) {
			try {
				throw new Exception("Could not find new mail from iGotThis :-(");
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			// Read the content of mail and get confirmation Token
		} else {
*/			String line;
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromProx.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			 String outputEmail = Jsoup.clean(buffer.toString(), new Whitelist());
			 //System.out.println(buffer);
			 utils.log().info("Email Content is"+ outputEmail);
			 //System.out.println("Email Content is"+ outputEmail);
			result = buffer.toString().split("7bit")[1].split(" is")[0];
			 utils.log().info("OTP is: "+result);
			//System.out.println("OTP is: "+result);
			
	//	}
		return result;
		
	}
	catch(Exception e) {
		for (int i = 0; i <= 5; i++) {
			

			messages = folder.search(new SubjectTerm("OTP to login into Koo"), folder.getMessages());
			// Wait for 20 seconds
			if (messages.length == 0) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				
					e.printStackTrace();
				}
			}
		}

		// Search for unread mail
		// This is to avoid using the mail for which
		// Registration is already done
		for (Message mail : messages) {
			if (!mail.isSet(Flags.Flag.SEEN)) {
				mailFromProx = mail;
				//Log.info("Message Count is: " + mailFromProx.getMessageNumber());
				isMailFound = true;
			}
		}

		// Test fails if no unread mail was found
		if (!isMailFound) {
			try {
				throw new Exception("Could not find this email too new mail from iGotThis :-(");
			} catch (Exception e2) {
				
				e.printStackTrace();
			}

			// Read the content of mail and get confirmation Token
		} else {
			String line;
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromProx.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			 String outputEmail = Jsoup.clean(buffer.toString(), new Whitelist());
			 //System.out.println(buffer);
			 utils.log().info("Email Content is"+ outputEmail);
			 //System.out.println("Email Content is"+ outputEmail);
			result = buffer.toString().split(" is")[0];
			 utils.log().info("OTP is: "+result);
			//System.out.println("OTP is: "+result);
			
		}
		return result;
	}
	
}
}