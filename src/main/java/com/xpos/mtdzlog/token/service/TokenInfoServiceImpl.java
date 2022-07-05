package com.xpos.mtdzlog.token.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.token.dao.mapper.TokenDAO;
import com.xpos.mtdzlog.token.dao.repository.TokenAttributeRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;
import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenAttributesDTO;
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
	
	@Override
	public List<String> getTokenColorList(TokenInfoSearchRequest req) {
		return tokenDAO.getTokenColorList(req);
	}
	
	public Map<String, List<TokenAttributesDTO>> getTokenAttributeMap(TokenInfoSearchRequest req) {
		List<TokenAttributesDTO> tokenAttributeList = tokenDAO.getTokenAttributeList(req);
		Map<String, List<TokenAttributesDTO>> tokenAttributeMap = new HashMap<>();
		if (tokenAttributeList != null && tokenAttributeList.size() > 0) {
			tokenAttributeMap = tokenAttributeList.stream().collect(Collectors.groupingBy(r -> r.getAttributeKey()));
		}
		
		return tokenAttributeMap;
	}
}
