package com.park.spa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Autowired
	AuthenticationFailureHandler CustomAuthFailureHandler;
	
	@Autowired
	AuthenticationSuccessHandler CustomAuthSuccessHandler;
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			
			.authorizeHttpRequests((requests) -> requests
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/main/**", "/common-js/**", "/adminLTE/**", "/common-css/**").permitAll()
				.anyRequest().authenticated()
//				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/main/login")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(CustomAuthSuccessHandler)  	
                .failureHandler(CustomAuthFailureHandler)  	// 로그인 실패 핸들러
				.permitAll()
			)
			.logout((logout) -> logout
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.permitAll()
			)
			.build();
	}
	
}
