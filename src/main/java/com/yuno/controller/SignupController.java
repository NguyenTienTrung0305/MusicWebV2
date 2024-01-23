package com.yuno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuno.model.Users;
import com.yuno.service.UserService;

@Controller
@RequestMapping("/musicweb/signup")
public class SignupController {
	
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	public SignupController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping()
	public String login() {
		return "signup";
	}
	
	// truyen thong tin User tu controller den view
	// thông tin hiện tại được truyền là đối tượng User rỗng
	// trong view truy xuất các thuộc tính của User thông qua key "user" vd: "${user.thuoctinh}"
	@ModelAttribute("user")
	public Users userRegistration() {
		return new Users();
	}
	
	// Nhận lại đối tượng User khi người dùng submit
	@PostMapping()
	public String submitRegisterUser(@ModelAttribute("user") Users user) {
		String password = user.getPassword();
		String encodePassword = passwordEncoder.encode(password);
		user.setPassword(encodePassword);
		userService.save(user);
		return "redirect:/musicweb/login?success";
	}
}
