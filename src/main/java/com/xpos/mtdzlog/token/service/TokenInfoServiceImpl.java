package com.xpos.mtdzlog.token.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.token.dao.mapper.TokenDAO;
import com.xpos.mtdzlog.token.dao.repository.TokenAttributeRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;
import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenInfoServiceImpl implements TokenInfoService {

	@Autowired
	private TokenInfoRepository tokenInfoRepository;
	
	@Autowired
	private TokenAttributeRepository tokenAttributeRepository;
	
	@Autowired
	private TokenDAO tokenDAO;

	@Override
	public Page<TokenDTO> getTokenInfoList(TokenInfoSearchRequest req) {
		// 토큰 리스트 조회
		List<TokenDTO> tokenList = tokenDAO.getTokenList(req);
		// 토큰 수량 조회
		long totalCount = tokenDAO.getTokenListCount(req);
		
		PageRequest pageable = null;
		pageable = PageRequest.of(req.getPage() -1, req.getRows());

		return new PageImpl<>(tokenList, pageable, totalCount);
	}
	
	@Override
	public List<MtdzGrade> getMtdzGrade() {
		return Arrays.asList(MtdzGrade.values());
	}
}
