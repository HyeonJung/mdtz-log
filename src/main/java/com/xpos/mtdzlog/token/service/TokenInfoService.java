package com.xpos.mtdzlog.token.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenAttributesDTO;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

public interface TokenInfoService {
	
	// 토큰 리스트 조회
	Page<TokenDTO> getTokenInfoList(TokenInfoSearchRequest req);
	
	// 메토드 등급 조회
	List<MtdzGrade> getMtdzGrade();
	
	// 토큰 색깔 조회
	List<String> getTokenColorList(TokenInfoSearchRequest req);
	
	// 토큰 속성 값 조회.
	Map<String, List<TokenAttributesDTO>> getTokenAttributeMap(TokenInfoSearchRequest req);
}
