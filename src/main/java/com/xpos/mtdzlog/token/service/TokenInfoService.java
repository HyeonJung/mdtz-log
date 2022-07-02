package com.xpos.mtdzlog.token.service;

import org.springframework.data.domain.Page;

import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

public interface TokenInfoService {
	
	// 토큰 리스트 조회
	Page<TokenDTO> getTokenInfoList(TokenInfoSearchRequest req);
}
