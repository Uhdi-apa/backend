package com.uhdi_apa.base.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.cors(Customizer.withDefaults()); // cors 설정

		http.headers((headers) ->
			headers.xssProtection(Customizer.withDefaults())
				.contentSecurityPolicy(Customizer.withDefaults())); //xss 설정

		http.authorizeHttpRequests(
			(auth) -> auth
				.anyRequest().permitAll()
		);

		return http.build();
	}

}
