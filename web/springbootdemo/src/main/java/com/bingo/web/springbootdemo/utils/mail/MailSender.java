package com.bingo.web.springbootdemo.utils.mail;

import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class MailSender {
	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 * @throws UnsupportedEncodingException
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) throws UnsupportedEncodingException {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = (new MailSender()).new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address[] tos = null;
            String[] receivers = mailInfo.getToAddress().split(",");
            if (receivers != null){
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length];
                for (int i=0; i<receivers.length; i++){
                    tos[i] = new InternetAddress(receivers[i]);
                }
            }
            Address[] tos2 = null;
            String[] receivers2 = mailInfo.getCopyToAddress().split(",");
            if (receivers2 != null){
                // 为每个邮件接收者创建一个地址
                tos2 = new InternetAddress[receivers2.length];
                for (int i=0; i<receivers2.length; i++){
                    tos2[i] = new InternetAddress(receivers2[i]);
                }
            }
			
			//Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			if(tos2.length > 0){
				mailMessage.setRecipients(Message.RecipientType.CC, tos2);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			if(mailInfo.getAttachFileNames()!=null){//有附件  
                BASE64Encoder enc = new BASE64Encoder();
                for (String string : mailInfo.getAttachFileNames()) {
                	html = new MimeBodyPart();
                    FileDataSource fds=new MyFileDataSource(string); //得到数据源
                    html.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                  //  html.setFileName(fds.getName());  //得到文件名同样至入BodyPart
                    System.out.println(fds.getName());
                    html.setFileName("=?GBK?B?" + enc.encode(fds.getName().getBytes("GBK")) + "?=");
                    //html.setFileName(MimeUtility.encodeText(fds.getName()));
                    mainPart.addBodyPart(html);
                }
            }
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean sendHtmlMail2(MailSenderInfo mailInfo) throws UnsupportedEncodingException {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = (new MailSender()).new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			sendMailSession.setDebug(true);  
	        MimeMessage mailMessage = new MimeMessage(sendMailSession);  
	        mailMessage.setSentDate(new Date());
	        mailMessage.setFrom(new InternetAddress(mailInfo.getFromAddress(),"电渠提数程序"));

	        // 创建邮件的接收者地址，并设置到邮件消息中
            Address[] tos = null;
            String[] receivers = mailInfo.getToAddress().split(",");
            if (receivers != null){
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length];
                for (int i=0; i<receivers.length; i++){
                    tos[i] = new InternetAddress(receivers[i]);
                }
            } else {
                tos = new InternetAddress[1];
                tos[0] = new InternetAddress(mailInfo.getToAddress());
            }
	        
	        mailMessage.setRecipients(Message.RecipientType.TO, tos);
	        mailMessage.setSubject(mailInfo.getSubject());
	        MimeMultipart multipart = new MimeMultipart();  
	        MimeBodyPart html_body = new MimeBodyPart();  
	        html_body.setContent(mailInfo.getContent(),"text/html; charset=utf-8");  
	        multipart.addBodyPart(html_body);  
	        MimeBodyPart file = new MimeBodyPart();
	        for (String string : mailInfo.getAttachFileNames()) {
	        	file = new MimeBodyPart();
	        	FileDataSource fds = new MyFileDataSource(string);
		        file.setDataHandler(new DataHandler(fds));
		        file.setFileName(fds.getName());
		        multipart.addBodyPart(file);
	        }
	        mailMessage.setContent(multipart);
	        Transport transport = sendMailSession.getTransport();  
	        try {
	        	Transport.send(mailMessage);
	        	return true;
	        } finally {  
	            transport.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public class MailSenderInfo {
		// 发送邮件的服务器的IP和端口
		private String mailServerHost;
		private String mailServerPort = "25";
		// 邮件发送者的地址
		private String fromAddress;
		// 邮件接收者的地址
		private String toAddress;
		// 邮件接收者的地址
		private String copyToAddress;
		// 登陆邮件发送服务器的用户名和密码
		private String userName;
		private String password;
		// 是否需要身份验证
		private boolean validate = false;
		// 邮件主题
		private String subject;
		// 邮件的文本内容
		private String content;
		// 邮件附件的文件名
		private String[] attachFileNames;

		/**
		 * 获得邮件会话属性
		 */
		public Properties getProperties() {
			Properties p = new Properties();
			p.put("mail.smtp.host", this.mailServerHost);
			p.put("mail.smtp.port", this.mailServerPort);
			p.put("mail.smtp.auth", validate ? "true" : "false");
			return p;
		}

		public String getMailServerHost() {
			return mailServerHost;
		}

		public void setMailServerHost(String mailServerHost) {
			this.mailServerHost = mailServerHost;
		}

		public String getMailServerPort() {
			return mailServerPort;
		}

		public void setMailServerPort(String mailServerPort) {
			this.mailServerPort = mailServerPort;
		}

		public boolean isValidate() {
			return validate;
		}

		public void setValidate(boolean validate) {
			this.validate = validate;
		}

		public String[] getAttachFileNames() {
			return attachFileNames;
		}

		public void setAttachFileNames(String[] fileNames) {
			this.attachFileNames = fileNames;
		}

		public String getFromAddress() {
			return fromAddress;
		}

		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getToAddress() {
			return toAddress;
		}

		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}
		
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String textContent) {
			this.content = textContent;
		}

		public String getCopyToAddress() {
			return copyToAddress;
		}

		public void setCopyToAddress(String copyToAddress) {
			this.copyToAddress = copyToAddress;
		}
	}

	public class MyAuthenticator extends Authenticator {
		String userName = null;
		String password = null;

		public MyAuthenticator() {
		}

		public MyAuthenticator(String username, String password) {
			this.userName = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password);
		}
	}
}