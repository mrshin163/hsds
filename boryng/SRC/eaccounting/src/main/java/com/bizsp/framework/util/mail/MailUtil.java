package com.bizsp.framework.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtil implements Runnable {

	public static Logger logger = Logger.getRootLogger();

	public static final String HOST = "webmail.boryung.co.kr";
	public static final String ID = "eaccounting";
	public static final String PW = "eaccounting!#";
	public static final String FROM = "breas@boryung.co.kr";
	public static final String FROM_NAME = "BR-EAS(법인카드 전자결재시스템)";

	public HashMap<String, String> hashMap = null;

	public MailUtil(HashMap<String, String> hashMap) {
		this.hashMap = hashMap;
	}

	public static void sendMail(HashMap<String, String> map) throws MessagingException, UnsupportedEncodingException {

		String recipients = map.get("recipients").toString();

		String[] toMailAddrArr = recipients.split(",");
		InternetAddress[] internetAddressArr = new InternetAddress[toMailAddrArr.length];

		for (int i = 0; i < internetAddressArr.length; i++) {
			internetAddressArr[i] = new InternetAddress(toMailAddrArr[i]);
		}

		String subject = map.get("subject").toString();
		String content = map.get("content").toString();

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new EmailAuthenticator(ID, PW));

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM, FROM_NAME, "euc-kr"));
		message.addRecipients(Message.RecipientType.TO, internetAddressArr);
		message.setSubject(subject);
		message.setContent(content, "text/html; charset=euc-kr");
		message.setSentDate(new Date());

		Transport.send(message);
	}

	public static void runSendMailThread(HashMap<String, String> hashMap) {
		new Thread(new MailUtil(hashMap)).start();
	}

	public void run() {
		try {
			sendMail(hashMap);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ".run()", e);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {

		HashMap<String, String> hashMap = new HashMap<String, String>();

		hashMap.put("recipients", "eaccounting@boryung.co.kr");
		hashMap.put("subject", "mail send test : " + String.valueOf(System.currentTimeMillis()));
		hashMap.put("content", String.valueOf(System.currentTimeMillis()));

		sendMail(hashMap);
	}
}

class EmailAuthenticator extends Authenticator {

	private String id;
	private String pw;

	public EmailAuthenticator(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(id, pw);
	}
}