package com.park.spa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.internet.MimeMessage;

public class MailService {
	
	@Autowired
	JavaMailSender mailSender;
		
	@Value("${spring.mail.username}")
	String sendFrom;
		
	@Autowired 
	Environment env;
		
	public boolean sendMail(String sendTo, String mailTitle, String mailContent) {
//		String sendTo 		= "받는 사람 주소";
//		String mailTitle 	= "Mail Title";
//		String mailContent 	= "Mail Content";
			
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true, "UTF-8");
//				ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");

				message.setTo(sendTo);
				message.setFrom(sendFrom);				//env.getProperty("spring.mail.username")
				message.setSubject(mailTitle);
				message.setText(mailContent, true); 	//ture : html 형식 사용
//				message.addInline("img", resource.getFile());
			}
		};
			
		try {
			mailSender.send(preparator);
		} catch (MailException e) {
			return false;
		}
		
		return true;
	}
	
	

}
