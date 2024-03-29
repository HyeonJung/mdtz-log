package com.xpos.mtdzlog.token.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.client.BitThumbClient;
import com.xpos.mtdzlog.client.KlaytnClient;
import com.xpos.mtdzlog.client.MtdzClient;
import com.xpos.mtdzlog.meta.MetaAttribute;
import com.xpos.mtdzlog.meta.MetaData;
import com.xpos.mtdzlog.meta.bitThumb.BitThumbResponse;
import com.xpos.mtdzlog.meta.klaytn.NftItemResponse;
import com.xpos.mtdzlog.meta.klaytn.TransferModel;
import com.xpos.mtdzlog.token.TokenAttribute;
import com.xpos.mtdzlog.token.TokenFloorPrice;
import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dao.mapper.TokenDAO;
import com.xpos.mtdzlog.token.dao.repository.TokenAttributeRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenFloorPriceRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;
import com.xpos.mtdzlog.token.dto.FloorPriceModel;
import com.xpos.mtdzlog.token.dto.MtdzGrade;
import com.xpos.mtdzlog.token.dto.TokenAttributesDTO;
import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.dto.TokenRankingDTO;
import com.xpos.mtdzlog.token.dto.TokenRankingRatioModel;
import com.xpos.mtdzlog.token.dto.TokenTransferDTO;

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
	
	@Autowired
	private MtdzClient mtdzClient;
	
	@Autowired
	private BitThumbClient bitThumbClient;
	
	@Autowired
	private TokenFloorPriceRepository tokenFloorPriceRepository;
	
	@Value("${klaytn.api.x-chain-id}")
	private String xChainId;

	@Value("${klaytn.api.presets}")
	private Integer presets;
	
	@Override
	public TokenInfo getTokenInfo(String type, Integer tokenId) {
		TokenInfo tokenInfo = tokenInfoRepository.findByTypeAndTokenId(type, tokenId);
		return tokenInfo;
	}

	@Override
	public Page<TokenDTO> getTokenInfoList(TokenInfoSearchRequest req) {
		// 토큰 리스트 조회x
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
	@Cacheable(value="getTransferInfo", key = "'tokenByOwner_' + #req.address + '_' + #req.gradeList + '_' + #req.ownerRows", cacheManager = "cacheManager")
	public List<TokenDTO> getTokenByOwnerAddress(TokenInfoSearchRequest req) {
		return tokenDAO.getOwnerTokenList(req);
	}

	@Override
	@Cacheable(value="getTransferInfo", key = "'info_' + #type", cacheManager = "cacheManager")
	public List<TokenTransferDTO> getTokenTransferInfo(String type) {
		List<TokenTransferDTO> tokenTransferList = new ArrayList<>();
		NftItemResponse<TransferModel> res = klaytnClient.transferInfo(xChainId, presets, 30);
		if (res != null && res.getItems() != null && res.getItems().size() > 0) {
			for (TransferModel transferModel: res.getItems()) {
				TokenTransferDTO tokenTransfer = new TokenTransferDTO();
				tokenTransfer.setBlockNumber(transferModel.getTransaction().getBlockNumber());
				tokenTransfer.setFrom(transferModel.getFrom());
				tokenTransfer.setTo(transferModel.getTo());
				tokenTransfer.setTransferDate(new Date(transferModel.getTransaction().getTimestamp() * 1000));
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

	@Override
	public List<MetaData> getMetaData() {
		List<MetaData> metaDataList = new ArrayList<>();
		List<TokenInfo> tokenList = tokenInfoRepository.findAll();
		for (TokenInfo token : tokenList) {
			MetaData metaData = mtdzClient.getJsonData(token.getTokenId());
			log.info("metaData = {}", metaData);
			List<TokenAttribute> tokenAttributeList = new ArrayList<>();
			for (MetaAttribute attribute : metaData.getAttributes()) {
				TokenAttribute attr = new TokenAttribute();
				attr.setTokenInfoId(token.getId());
				attr.setAttributeKey(attribute.getTrait_type());
				attr.setAttributeValue(attribute.getValue());
				tokenAttributeList.add(attr);
			}
			log.info("tokenAttributeList : {}", tokenAttributeList);
			tokenAttributeRepository.saveAll(tokenAttributeList);
			metaDataList.add(metaData);			
		}
		
		
		return metaDataList;
	}
	
	@Override
	@Cacheable(value="getFloorPrice", key = "'floorPrice_' + #type", cacheManager = "cacheManager")
	public FloorPriceModel getFloorPrice(String type) {
		FloorPriceModel fp = new FloorPriceModel();
		try {
			TokenFloorPrice openSeaFp = tokenFloorPriceRepository.findByTypeAndPlatform(type, "OPENSEA");
			fp.setOpenSeaFp(openSeaFp.getFloorPrice());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			TokenFloorPrice palaFp = tokenFloorPriceRepository.findByTypeAndPlatform(type, "PALA");
			fp.setPalaFp(palaFp.getFloorPrice());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			BitThumbResponse res = bitThumbClient.getKlayPrice();
			fp.setKlayPrice(Double.valueOf(res.getData().getClosing_price()));
			TokenFloorPrice klayPrice = new TokenFloorPrice();
			klayPrice.setFloorPrice(Double.parseDouble(res.getData().getClosing_price()));
			klayPrice.setPlatform("BITHUMB");
			klayPrice.setType("KLAY");
			tokenFloorPriceRepository.save(klayPrice);
		} catch (Exception e) {
			TokenFloorPrice klayPrice = tokenFloorPriceRepository.findByTypeAndPlatform("KLAY", "BITHUMB");
			fp.setKlayPrice(klayPrice.getFloorPrice());
			e.printStackTrace();
		}
		
		
		return fp;
	}
	
	@Override
	@Cacheable(value="mainRankingList", key = "'mainRanking_v2_' + #type", cacheManager = "cacheManager")
	public List<TokenRankingDTO> getMainRankingList() {
		TokenInfoSearchRequest req = new TokenInfoSearchRequest();
		req.setType("MTDZ");
		req.setRows(3);
		// 토큰 랭킹 리스트 조회.
		List<TokenRankingDTO> tokenRankingList = tokenDAO.getTokenRankingList(req);
		Integer seq = req.getOffset();
		TokenInfoSearchRequest subReq = new TokenInfoSearchRequest();
		for (TokenRankingDTO tokenRanking: tokenRankingList) {
			subReq.setAddress(tokenRanking.getOwner());
			tokenRanking.setSeq(++seq);
			tokenRanking.setOwnerTokenList(getTokenByOwnerAddress(subReq));
		}
		return tokenRankingList;
	}
	
	@Override
	public String getRandAttributeValue() {
		return tokenDAO.getRandAttributeValue();
	}
	
	@Override
	@Cacheable(value="randAttributeTokenList", key = "'randAttributeTokenList_' + #req.value", cacheManager = "cacheManager")
	public List<TokenDTO> getRandAttributeTokenList(TokenInfoSearchRequest req) {
		// 토큰 리스트 조회
		List<TokenDTO> tokenList = tokenDAO.getTokenList(req);
		return tokenList;
	}

	@Override
	public List<TokenRankingRatioModel> getTokenRankingRatio(TokenInfoSearchRequest req) {
		List<TokenRankingRatioModel> tokenRankingRatioList = new ArrayList<>();
		List<TokenRankingRatioModel> temp = tokenDAO.tokenRankingRatio();

		TokenRankingRatioModel etc = new TokenRankingRatioModel();
		etc.setRatioName("기타");
		etc.setHolderCount(0);
		etc.setTokenCount(0);

		int count = 1;
		for (TokenRankingRatioModel ratio: temp) {
			if (count < 10) {
				ratio.setRatioName(ratio.getTokenCount() + "개 홀더");
				tokenRankingRatioList.add(ratio);
			} else {
				etc.setHolderCount(etc.getHolderCount() + ratio.getHolderCount());
				etc.setTokenCount(etc.getTokenCount() + ratio.getTokenCount());
				etc.setTotalHolderCount(ratio.getTotalHolderCount());
			}
			count++;
		}
		tokenRankingRatioList.add(etc);

		return tokenRankingRatioList;
	}
	
}
