package com.xpos.mtdzlog.web;

import java.util.List;
import java.util.Map;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenAttributesDTO;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.dto.TokenRankingDTO;
import com.xpos.mtdzlog.token.dto.TokenTransferDTO;
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
	public String rankingOwnerTokenList(Model model, @PathVariable String address, @ModelAttribute TokenInfoSearchRequest req
			, @RequestParam Integer seq) {
		req.setAddress(address);
		log.info("ownerTokenList : {}", req);
		List<TokenDTO> tokenList = tokenInfoServiceImpl.getTokenByOwnerAddress(req);
		model.addAttribute("contents", tokenList);
		model.addAttribute("address", address);
		model.addAttribute("seq", seq);

		return "token/include/rankingTokenList";
	}

	/**
	 * 토큰 전송이력
	 * @param type
	 * @return
	 */
	@GetMapping("/transfer")
    public String getTransferInfo(@RequestParam(required=false, defaultValue="mtdz") String type, Model model) {
		List<TokenTransferDTO> tokenTransferList = tokenInfoServiceImpl.getTokenTransferInfo(type);
		model.addAttribute("tokenTransferList", tokenTransferList);
	    return "token/transferList";
    }
	
	/**
	 * meta data 조회
	 * @return
	 */
	@GetMapping("/metaData")
	public ResponseEntity<?> getMetaData() {
		tokenInfoServiceImpl.getMetaData();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
