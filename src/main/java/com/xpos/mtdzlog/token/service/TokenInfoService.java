package com.xpos.mtdzlog.token.service;

import java.util.List;
import java.util.Map;

import com.xpos.mtdzlog.token.dto.*;
import org.springframework.data.domain.Page;

import com.xpos.mtdzlog.meta.MetaData;
import com.xpos.mtdzlog.token.dto.TokenDTO;

public interface TokenInfoService {
	
	// 토큰 리스트 조회
	Page<TokenDTO> getTokenInfoList(TokenInfoSearchRequest req);
	
	// 메토드 등급 조회
	List<MtdzGrade> getMtdzGrade();
	
	// 토큰 색깔 조회
	List<String> getTokenColorList(TokenInfoSearchRequest req);
	
	// 토큰 속성 값 조회.
	Map<String, List<TokenAttributesDTO>> getTokenAttributeMap(TokenInfoSearchRequest req);

	// 토큰 랭킹 리스트
	Page<TokenRankingDTO> getTokenRankingList(TokenInfoSearchRequest req);

	// 지갑주소로 토큰 검색
	List<TokenDTO> getTokenByOwnerAddress(TokenInfoSearchRequest req);

	// 토큰 전송내역 조회
	List<TokenTransferDTO> getTokenTransferInfo(String type);
	
	// json 데이터 조회
	List<MetaData> getMetaData();
	
	// 바닥가 조회
	FloorPriceModel getFloorPrice(String type);
	
	// 메인용 랭킹리스트
	List<TokenRankingDTO> getMainRankingList();
	
	// 랜덤 속성값 불러오기
	String getRandAttributeValue();
	
	// 랜덤 속성값 토큰리스트 불러오기
	List<TokenDTO> getRandAttributeTokenList(TokenInfoSearchRequest req);

	// 토큰 랭킹 비율 추가.
	List<TokenRankingRatioModel> getTokenRankingRatio(TokenInfoSearchRequest req);
}
