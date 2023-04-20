package com.today.flower;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/flower")
@RequiredArgsConstructor
@Controller
public class FlowerController {
	
	@GetMapping("/ci")
	public String ci() {
		return "ci/ci";
	}
	@GetMapping("/home")
	public String home() {
		return "home/home1";
	}
	
	@GetMapping("/login2")
	public String login2() {
		return "login/Login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "login/Join2";
	}
	
	@GetMapping("/findid")
	public String findid() {
		return "login/findID";
	}
	
	@GetMapping("/findpw")
	public String findpw() {
		return "login/findPW";
	}
	
	@GetMapping("/shop")
	public String shop() {
		return "shop/Shop1";
	}
	
	@GetMapping("/shop2")
	public String shop2() {
		return "shop/Shop2";
	}
	
	@GetMapping("/shop3")
	public String shop3() {
		return "shop/Shop3";
	}
	
	@GetMapping("/")
	public String root() {
		return "redirect:/flower/main";
	}
}