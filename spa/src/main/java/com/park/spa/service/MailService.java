package com.park.spa.service;

import java.net.URLDecoder;
import java.time.Instant;
import java.time.Instant;
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
import com.park.spa.vo.MemberVo;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender mailSender;
		
	@Value("${spring.mail.username}")
	String sendFrom;
		
	@Autowired 
	Environment env;
	
	private static final int timeExpired = 3;		//코드인증 제한시간
		
	/**
	 * 이메일 전송
	 * 
	 * @param sendTo
	 * @param mailContent
	 * @return
	 * @throws Exception 
	 */
	public boolean sendMail(String sendTo, String mailContent) throws Exception {
		String mailTitle 	= "이메일 인증 코드입니다.";
		String decodeSendTo = URLDecoder.decode(sendTo, "UTF-8");
		
		SessionUtil.setAttribute("emailCheckVo", null);
			
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
        int savedTime = 0;
    	
    	if(SessionUtil.getAttribute("emailCheckVo") != null) {
    		emailCheckVo = (EmailCheckVo) SessionUtil.getAttribute("emailCheckVo");
    		savedCode = emailCheckVo.getCode();
    		savedTime = emailCheckVo.getByTime();
    	}
    	
    	if(!"".equals(code) && code.equals(savedCode)) {
    		int currentTime = (int) Instant.now().getEpochSecond();			//사용자가 코드를 입력한시간
    		int timeDiffer = (currentTime - savedTime) / 60;					//이메일 보낸 시간과 입력시간 차
    		
    		if(timeDiffer < timeExpired) {
    			outMap.put("result", true);
        		outMap.put("msg", "이메일 확인 완료");
        		
        		// 확인 이메일 세션에 저장
        		emailCheckVo.setChecked(true);					
        		SessionUtil.setAttribute("emailCheckVo", emailCheckVo);
    		} else {
    			outMap.put("result", false);
    			outMap.put("msg", "코드 유효기간이 지났습니다.");
    		}
    	} else {
    		outMap.put("result", false);
    		outMap.put("msg", "정확한 코드를 입력해 주세요.");
    	}
    	
    	return outMap;
	}

	/**
	 * 타이머 시간 연장
	 * 
	 * @param memberVo
	 * @throws Exception 
	 */
	public boolean extendPutdownCodeTime(MemberVo memberVo) throws Exception {
		EmailCheckVo emailCheckVo = (EmailCheckVo) SessionUtil.getAttribute("emailCheckVo");
		int currentTime = (int) Instant.now().getEpochSecond();	
		emailCheckVo.setByTime(currentTime);
		SessionUtil.setAttribute("emailCheckVo", emailCheckVo);
		
		return true;
		
	}
	
	
}
