package com.park.spa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/main/login", "/common-js/**", "/main/join").permitAll()
				.anyRequest().authenticated()
//				.anyRequest().permitAll()
			)
			
			.csrf().disable()
			
			.formLogin((form) -> form
				.loginPage("/main/login")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(customFailureHandler)  // 로그인 실패 핸들러
				.defaultSuccessUrl("/spa/main", true)
				.permitAll()
			)
			
			.logout((logout) -> logout
				.logoutUrl("/logout")	
				.permitAll());
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
}
