package com.yuno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.yuno.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAthFilter jwtAthFilter;
	private final UserService userService;

//	@Bean
//	@Order(1)
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests( x -> 
//					x.requestMatchers("/musicweb/signup**", "/js/**", "/css/**", "/img/**",
//						"/video/**", "/audio/**").permitAll()
//				)
//				.authorizeHttpRequests( x -> 
//					x.requestMatchers("/homemusicweb/**").hasRole("USER")
//					 .anyRequest().authenticated()
//				)
//				.authenticationProvider(authenticationProvider())
//				.formLogin(x -> x.loginPage("/musicweb/login").permitAll()
//						.defaultSuccessUrl("/homemusicweb"))
//                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//		return http.build();
//	}
	
	@Bean
//	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests( x -> 
				x.requestMatchers("/musicweb/login", "/musicweb/signup","/homemusicweb/**", "/js/**", "/css/**", "/img/**",
						"/video/**", "/audio/**").permitAll()
			)
			.authorizeHttpRequests( x ->
				x.anyRequest().authenticated()
			)
			.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();

	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
