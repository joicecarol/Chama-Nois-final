package com.chamanois.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RotasController {

	@GetMapping("/")
	public String home() {
		return "pages/index";
	}

	@GetMapping("/login")
	public String login() {
		return "pages/entrar";
	}

	@GetMapping("/erro")
	public String erro() {
		return "pages/loginErro";
	}

	@GetMapping("/sobrenos")
	public String sobrenos() {
		return "pages/sobreNos";
	}

	@GetMapping("/privacidade")
	public String privacidade() {
		return "pages/privacidade";
	}

	@GetMapping("/termos")
	public String termos() {
		return "pages/termosServico";
	}
}
