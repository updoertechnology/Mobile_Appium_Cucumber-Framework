package com.qa.utils;

import java.util.stream.StreamSupport;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

public class GetMobileOTP {
	
	TestUtils utils = new TestUtils();
	public static final String ACCOUNT_SID="AC3fb7da83a85cddf3df1994e13726ad53";
	public static final String AUTH_TOKEN="cbc67f5d1acaca1d114ee811dc1ed820";
		
	
	public String getOTP() {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody=getMessage();
		System.out.print(smsBody);
		String Otp = smsBody.split(" is your")[0].split(" ")[1];
		System.out.print("OTP is: "+Otp);
		return Otp;
	}
	
	public static String getMessage() {
		return ((java.util.stream.Stream<Message>) getMessages()).filter(m->m.getDirection().compareTo(Message.Direction.INBOUND)==0)
				.filter(m ->m.getTo().equals("+14153389516")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}
	
	private static java.util.stream.Stream<Message> getMessages() {
		ResourceSet<Message> message=Message.reader(ACCOUNT_SID).read( );
		return (java.util.stream.Stream<Message>) StreamSupport.stream(message.spliterator(), false);
	}
	
	
	
	
	
}
