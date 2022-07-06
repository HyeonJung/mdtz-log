package com.xpos.mtdzlog.web;

import java.util.List;
import java.util.Map;

import com.xpos.mtdzlog.token.dto.*;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	// 색깔 필터 리스트 조회
	@GetMapping("/colorList")
	public String colorList(Model model, @ModelAttribute TokenInfoSearchRequest req) {
//		log.info("colorList req : {}", req);
		model.addAttribute("colorList", tokenInfoServiceImpl.getTokenColorList(req));
		return "token/include/colorList";
	}

	// 특성 리스트 조회
	@GetMapping("/attributes")
	public String attributes(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		Map<String, List<TokenAttributesDTO>> attributesMap = tokenInfoServiceImpl.getTokenAttributeMap(req);
		model.addAttribute("attributesMap", attributesMap);
		return "token/include/attributeList";
	}

	// 랭킹 리스트 조회
	@GetMapping("/ranking")
	public String ranking(Model model, @ModelAttribute TokenInfoSearchRequest req) {

		// 토큰 등급 리스트
		if (!StringUtils.isEmpty(req.getType()) && req.getType().equals("MTDZ")) {
			List<MtdzGrade> gradeList = tokenInfoServiceImpl.getMtdzGrade();
			model.addAttribute("gradeList", gradeList);
		}

		subRanking(model, req);
		return "token/ranking";
	}

	// 서브 랭킹 리스트 조회
	@GetMapping("/subRanking")
	public String subRanking(Model model, @ModelAttribute TokenInfoSearchRequest req) {
		log.info("ranking req : {}", req);
		Page<TokenRankingDTO> tokenRes = tokenInfoServiceImpl.getTokenRankingList(req);
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
		return "token/include/subRanking";
	}

	@GetMapping("/ranking/{address}/token")
	public String rankingOwnerTokenList(Model model, @PathVariable String address, @ModelAttribute TokenInfoSearchRequest req) {
		req.setAddress(address);
		log.info("ownerTokenList : {}", req);
		List<TokenDTO> tokenList = tokenInfoServiceImpl.getTokenByOwnerAddress(req);
		model.addAttribute("contents", tokenList);

		return "token/include/rankingTokenList";
	}
}
