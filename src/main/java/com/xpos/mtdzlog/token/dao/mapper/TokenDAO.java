package com.xpos.mtdzlog.token.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.xpos.mtdzlog.token.dto.TokenAttributesDTO;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

@Mapper
@Repository
public interface TokenDAO {

	// 토큰 정보 조회
	List<TokenDTO> getTokenList(TokenInfoSearchRequest req);
	
	// 토큰 정보 수량
	long getTokenListCount(TokenInfoSearchRequest req);
	
	// 토큰 등급
	List<String> getTokenGrade(String type);
	
	// 토큰 색깔 조회
	List<String> getTokenColorList(TokenInfoSearchRequest req);
	
	// 토큰 속성 값 조회
	List<TokenAttributesDTO> getTokenAttributeList(TokenInfoSearchRequest req);
}
