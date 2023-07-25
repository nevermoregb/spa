package com.park.spa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
    @Autowired
	AuthenticationFailureHandler CustomAuthFailureHandler;
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			
			.authorizeHttpRequests((requests) -> requests
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/main/login", "/main/join", "/common-js/**").permitAll()
				.anyRequest().authenticated()
//				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/main/login")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("username")
                .passwordParameter("password")
//                .failureHandler(getFailureHandler())  // 로그인 실패 핸들러
                .failureHandler(CustomAuthFailureHandler)  // 로그인 실패 핸들러
//                .failureUrl("/login-error")
				.defaultSuccessUrl("/spa/main", true)
				.permitAll()
			)
			.logout((logout) -> logout
				.logoutUrl("/logout")	
				.permitAll()
			)
			.build();
	}
	
//	@Bean
//	public MessageSource messageSource() {
//		Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//
//		messageSource.setDefaultEncoding("UTF-8"); // 인코딩 설정
//		messageSource.setBasenames("classpath:message/security_message", "classpath:org/springframework/security/messages"); // 커스텀한 properties 파일, security properties 파일 순서대로 설정
//		return messageSource;
//	}
	
}
