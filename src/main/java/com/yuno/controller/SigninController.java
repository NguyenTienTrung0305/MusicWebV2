package com.yuno.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuno.config.JwtUtils;
import com.yuno.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/musicweb/login")
@RequiredArgsConstructor
public class SigninController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils; 
	private final UserService userService;
	
	
	
	@GetMapping()
	public String signin(Model model) {
		return "signin";
	}
	

	@PostMapping()
    public String submitLogin(@RequestParam("email") String email, 
            @RequestParam("password") String password , HttpServletResponse response) {
		Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(email, password)
	    );
		
		
		if (authentication.isAuthenticated()) {
			UserDetails userDetails = userService.loadUserByUsername(email);
			
			String token = jwtUtils.generateToken(userDetails);
			System.err.println(token);
			response.addHeader("Authorization", "Bearer " + token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "redirect:/homemusicweb";
		}
		throw new UsernameNotFoundException("invalid username {} " + email);
	}
	
}
