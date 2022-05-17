package com.xpos.mtdzlog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mtdz")
public class MtdzController {
	
	@GetMapping("")
	public String list(Model model) {
		
		return "mtdz/list";
	}

}
