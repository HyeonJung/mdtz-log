package com.xpos.mtdzlog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.xpos.mtdzlog.token.dto.FloorPriceModel;
import com.xpos.mtdzlog.token.service.TokenInfoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
private static final String TYPE = "MTDZ";
	
	@Autowired
	private TokenInfoService tokenInfoServiceImpl;
	
	@GetMapping(value = "")
	public String root(Model model) {
		return "redirect:/token";
	}

	@GetMapping(value = "/main")
	public String main(Model model) {
		FloorPriceModel fp = tokenInfoServiceImpl.getFloorPrice(TYPE);
		log.info("fp = {}", fp);
		model.addAttribute("fp", fp);
		return "main";
	}
}
