package com.park.spa.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.park.spa.common.SessionUtil;
import com.park.spa.vo.EmailCheckVo;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender mailSender;
		
	@Value("${spring.mail.username}")
	String sendFrom;
		
	@Autowired 
	Environment env;
		
	/**
	 * 이메일 전송
	 * 
	 * @param sendTo
	 * @param mailContent
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendMail(String sendTo, String mailContent) throws UnsupportedEncodingException {

		String mailTitle 	= "이메일 인증 코드입니다.";
		String decodeSendTo = URLDecoder.decode(sendTo, "UTF-8");
			
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true, "UTF-8");
//				ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");

				message.setTo(decodeSendTo);
				message.setFrom(sendFrom);				//env.getProperty("spring.mail.username")
				message.setSubject(mailTitle);
				message.setText(mailContent, true); 	
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
	
	/**
	 * 이메일로 전송된 코드확인
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> emailCodeCheck(Map<String, String> paramMap) throws Exception {
		
		Map<String, Object> outMap = new HashMap<>();
		
    	EmailCheckVo emailCheckVo = null;
    	String code = (paramMap.get("code") != null) ? paramMap.get("code") : "";		//화면에서 입력된 코드
    	String savedCode = "";															//세션에 저장된 코드
    	
    	if(SessionUtil.getAttribute("emailCheckVo") != null) {
    		emailCheckVo = (EmailCheckVo) SessionUtil.getAttribute("emailCheckVo");
    		savedCode = emailCheckVo.getCode();
    	}
    	
    	if(!"".equals(code) && code.equals(savedCode)) {
    		outMap.put("result", true);
    		outMap.put("msg", "이메일 확인 완료");
    		
    		// 확인 이메일 세션에 저장
    		emailCheckVo.setChecked(true);					
    		SessionUtil.setAttribute("emailCheckVo", emailCheckVo);
    		
    	} else {
    		outMap.put("result", false);
    		outMap.put("msg", "정확한 코드를 입력해 주세요.");
    	}
    	
    	return outMap;
	}
}
