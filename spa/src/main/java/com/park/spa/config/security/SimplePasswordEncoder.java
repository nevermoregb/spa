package com.park.spa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SimplePasswordEncoder implements PasswordEncoder {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public String encode(CharSequence rawPassword) {
//		return rawPassword.toString();
		//bCrypt로 encode 해준다.
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(encode(rawPassword));
	}

}
