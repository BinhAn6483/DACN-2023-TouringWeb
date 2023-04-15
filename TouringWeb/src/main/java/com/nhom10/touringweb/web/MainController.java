package com.nhom10.touringweb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/forgotPasswordPage")
	public String forgotPassword() {
		return "forgotPassword";
	}


}
