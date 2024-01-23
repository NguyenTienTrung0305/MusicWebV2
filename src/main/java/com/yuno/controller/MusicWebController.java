package com.yuno.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuno.model.Users;
import com.yuno.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/homemusicweb")
@RequiredArgsConstructor
public class MusicWebController {
	
	private final UserService userService;
	
	@GetMapping()
//	@PreAuthorize("hasRole('ROLE_USER')")
    public String musicWebPage(HttpServletResponse response , Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
	
			Users users = userService.findUserByEmail(username);
			System.err.println(authentication.getPrincipal());
			
			model.addAttribute("infoUsers" , users);
			
		}
        return "musicweb";
    }
}
