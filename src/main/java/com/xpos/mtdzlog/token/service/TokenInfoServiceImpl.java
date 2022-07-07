package com.xpos.mtdzlog.token.service;

import java.util.*;
import java.util.stream.Collectors;

import com.xpos.mtdzlog.client.KlaytnClient;
import com.xpos.mtdzlog.meta.klaytn.NftItemResponse;
import com.xpos.mtdzlog.meta.klaytn.TransferModel;
import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.token.dao.mapper.TokenDAO;
import com.xpos.mtdzlog.token.dao.repository.TokenAttributeRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;

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

	@Autowired
	private KlaytnClient klaytnClient;

	@Value("${klaytn.api.x-chain-id}")
	private String xChainId;

	@Value("${klaytn.api.presets}")
	private Integer presets;

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

	@Override
	public Page<TokenRankingDTO> getTokenRankingList(TokenInfoSearchRequest req) {
		// 토큰 랭킹 리스트 조회.
		List<TokenRankingDTO> tokenRankingList = tokenDAO.getTokenRankingList(req);
		Integer seq = req.getOffset();
		for (TokenRankingDTO tokenRanking: tokenRankingList) {
			tokenRanking.setSeq(++seq);
		}

		// 토큰 랭킹 수량 조회
		long totalCount = tokenDAO.getTokenRankingListCount(req);

		PageRequest pageable = null;
		pageable = PageRequest.of(req.getPage() -1, req.getRows());

		return new PageImpl<>(tokenRankingList, pageable, totalCount);
	}

	@Override
	public List<TokenDTO> getTokenByOwnerAddress(TokenInfoSearchRequest req) {
		return tokenDAO.getOwnerTokenList(req);
	}

	@Override
	@Cacheable(value="getTransferInfo", key = "#type", cacheManager = "cacheManager")
	public List<TokenTransferDTO> getTokenTransferInfo(String type) {
		List<TokenTransferDTO> tokenTransferList = new ArrayList<>();
		NftItemResponse<TransferModel> res = klaytnClient.transferInfo(xChainId, presets, 30);
		if (res != null && res.getItems() != null && res.getItems().size() > 0) {
			for (TransferModel transferModel: res.getItems()) {
				TokenTransferDTO tokenTransfer = new TokenTransferDTO();
				tokenTransfer.setBlockNumber(transferModel.getTransaction().getBlockNumber());
				tokenTransfer.setFrom(transferModel.getFrom());
				tokenTransfer.setTo(transferModel.getTo());
				tokenTransfer.setTransferDate(transferModel.getTransaction().getTimestamp());
				tokenTransfer.setTokenId(Integer.decode(transferModel.getTokenId()));
				TokenInfo tokenInfo = tokenInfoRepository.findByTokenId(tokenTransfer.getTokenId());
				if (tokenInfo != null) {
					tokenTransfer.setGrade(tokenInfo.getGrade());
					tokenTransfer.setImageUrl(tokenInfo.getImageUrl());
				}

				log.info("tokenTransfer : {}", tokenTransfer);
				tokenTransferList.add(tokenTransfer);
			}
		}
		return tokenTransferList;
	}
}
