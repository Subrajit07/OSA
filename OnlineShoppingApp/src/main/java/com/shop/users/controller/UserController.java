package com.shop.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.home.service.IProductService;
import com.shop.users.dto.LoginRequest;
import com.shop.users.dto.SignUpRequest;
import com.shop.users.service.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final IProductService productService;

	private final IUserService userService;
	
	@GetMapping("/login")
	public String loginPage() {
		return "/auth/login";
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("signUpRequest",new SignUpRequest("", "", "", "", "", null, null));
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String successSignUp(@ModelAttribute SignUpRequest req,RedirectAttributes redirectAttributes) {
		userService.saveUser(req);
		redirectAttributes.addFlashAttribute("successMessage", "Signup successfull ! please login.");
		return "redirect:/user/login";
	}
	
}
