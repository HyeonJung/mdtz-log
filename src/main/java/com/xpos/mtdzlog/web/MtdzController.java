package com.xpos.mtdzlog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.service.TokenService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/mtdz")
@Slf4j
public class MtdzController {
	
	private static final String TYPE = "MTDZ";
	
	@Autowired
	private TokenService tokenService;
	
	// 리스트 조회
	@GetMapping("")
	public String list(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		subList(model, req);
		return "mtdz/list";
	}
	
	// 서브 리스트 조회
	@GetMapping("/subList")
	public String subList(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		req.setType(TYPE);
		Page<TokenDTO> tokenRes = tokenService.getTokenList(req);
		model.addAttribute("contents", tokenRes.getContent());
		model.addAttribute("totalDocs", tokenRes.getTotalElements());
		return "mdtz/include/list";
	}

}
