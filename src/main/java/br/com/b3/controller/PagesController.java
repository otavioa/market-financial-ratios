package br.com.b3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {

	@RequestMapping
	public String home() {
		return "home";
	}
	
	@RequestMapping("/json")
	public String importarJson() {
		return "importar-json";
	}
	
}
