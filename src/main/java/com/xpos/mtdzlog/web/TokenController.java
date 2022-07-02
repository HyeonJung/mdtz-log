package com.xpos.mtdzlog.web;

import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.service.TokenInfoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/token")
@Slf4j
public class TokenController {
	
	private static final String TYPE = "MTDZ";
	
	@Autowired
	private TokenInfoService tokenInfoServiceImpl;
	
	// 리스트 조회
	@GetMapping("")
	public String list(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		
		// 토큰 등급 리스트
		if (!StringUtils.isEmpty(req.getType()) && req.getType().equals("MTDZ")) {
			List<MtdzGrade> gradeList = tokenInfoServiceImpl.getMtdzGrade();
			model.addAttribute("gradeList", gradeList);
		}
		
		subList(model, req);
		return "token/list";
	}
	
	// 서브 리스트 조회
	@GetMapping("/subList")
	public String subList(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		log.info("list req : {}", req);
		req.setType(TYPE);
		Page<TokenDTO> tokenRes = tokenInfoServiceImpl.getTokenInfoList(req);
		model.addAttribute("contents", tokenRes.getContent());
		model.addAttribute("totalDocs", tokenRes.getTotalElements());
		model.addAttribute("req", req);
		
		Integer totalPages = tokenRes.getTotalPages();
        Long total = tokenRes.getTotalElements();
        int pageStart = (req.getPage() - 1) / 10 * 10 + 1;
        int pageEnd = pageStart + 9;
        if (pageEnd > totalPages) {
            pageEnd = totalPages;
        }
        model.addAttribute("pageStart", pageStart);
        model.addAttribute("pageEnd", pageEnd);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("total", total);

		return "token/include/list";
	}

}
