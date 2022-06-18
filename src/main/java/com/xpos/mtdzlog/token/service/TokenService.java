package com.xpos.mtdzlog.token.service;

import org.springframework.data.domain.Page;

import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

public interface TokenService {
	
	// 토큰 리스트 조회
	Page<TokenDTO> getTokenList(TokenInfoSearchRequest req);
}
