package com.im.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.im.model.User;
import com.im.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserRepository repository;
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		user.setCreatedon(LocalDateTime.now().withNano(0));
		user.setStatus("0");
		user.setRole("2");
		repository.save(user);
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username,@RequestParam String password,HttpSession session) {
		
		User user = repository.findByUsername(username);
		
		if(user != null && password.equals(user.getPassword()) && user.getStatus().equals("1")) {
			String userRole = repository.getUserRoleByID(user.getRole(),user.getId());
			System.out.println(userRole);
			session.setAttribute("user", user);
			if(userRole.equals("1")){
				return "redirect:/adminDashboard";
			}
			return "redirect:/dashboard";
		}
		return"redirect:/login?error=true";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
		User user = (User) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login";
	    }
		return "dashboard";
	}
	
	@GetMapping("/adminDashboard")
	public String adminDashboard(HttpSession session) {
		User user = (User) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login";
	    }
		return "adminDashboard";
	}
	
	@GetMapping("/userRequest")
	public String userRequest() {
		return "userRequest";
	}
}
