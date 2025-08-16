package com.shop.common;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
*/
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Bean
	@SneakyThrows
	public SecurityFilterChain filterChain(HttpSecurity security) {
		security.csrf(cs->cs.disable())
								.authorizeHttpRequests(req->req
										.requestMatchers("/user/login","/user/signup").permitAll()
										.requestMatchers("/images/**", "/css/**", "/js/**", "/webjars/**").permitAll()
										.anyRequest().authenticated()
										)
								.formLogin(login->login
										.loginPage("/user/login")
										.loginProcessingUrl("/user/login")
										.usernameParameter("username")
										.passwordParameter("password")
										.defaultSuccessUrl("/products/home",true)
										.permitAll()
									)
								.logout(out->out
										.logoutUrl("/user/logout")
										.logoutSuccessUrl("/user/login?logout")
										.permitAll());
		return security.build();
	}
}