package com.xpos.mtdzlog.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.xpos.mtdzlog.token.dto.FloorPriceModel;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.dto.TokenRankingDTO;
import com.xpos.mtdzlog.token.dto.TokenTransferDTO;
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
		model.addAttribute("fp", fp);
		
		// 메인 도감
		TokenInfoSearchRequest req = new TokenInfoSearchRequest();
		req.setValue(tokenInfoServiceImpl.getRandAttributeValue());
		req.setRows(12);
		List<TokenDTO> mainCollectList = tokenInfoServiceImpl.getRandAttributeTokenList(req);
		model.addAttribute("collectList", mainCollectList);
		
		// 전송이력
		List<TokenTransferDTO> tokenTransferList = tokenInfoServiceImpl.getTokenTransferInfo(TYPE);
		model.addAttribute("tokenTransferList", tokenTransferList.stream().limit(3).collect(Collectors.toList()));
		
		// 랭킹
		List<TokenRankingDTO> rankingList = tokenInfoServiceImpl.getMainRankingList();
		model.addAttribute("contents", rankingList);
		return "main";
	}
}
