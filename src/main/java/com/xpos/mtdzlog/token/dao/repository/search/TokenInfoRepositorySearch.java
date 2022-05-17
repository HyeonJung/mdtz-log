package com.xpos.mtdzlog.token.dao.repository.search;

import org.springframework.data.domain.Page;

import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

public interface TokenInfoRepositorySearch {
	
	Page<TokenInfo> search(TokenInfoSearchRequest req);
}
