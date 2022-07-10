package com.xpos.mtdzlog.task;

import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xpos.mtdzlog.client.BigDragonClient;
import com.xpos.mtdzlog.client.KlaytnClient;
import com.xpos.mtdzlog.client.PalaClient;
import com.xpos.mtdzlog.meta.klaytn.NftItem;
import com.xpos.mtdzlog.meta.klaytn.NftItemResponse;
import com.xpos.mtdzlog.meta.pala.PalaResponse;
import com.xpos.mtdzlog.token.TokenFloorPrice;
import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dao.repository.TokenFloorPriceRepository;
import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("production")
public class TokenCollectTask {

    @Value("${klaytn.api.x-chain-id}")
    private String xChainId;

    @Value("${mtdz.contractAddress}")
    private String mtdzContractAddress;

    private static Integer DEFAULT_SIZE = 1000;

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    @Autowired
    private KlaytnClient klaytnClient;
    
    @Autowired
    private PalaClient palaClient;
    
    @Autowired
    private BigDragonClient bigDragonClient;
    
    @Autowired
    private TokenFloorPriceRepository tokenFloorPriceRepository;

    // 매일 새벽 3시에 돌자
    @Scheduled(cron = "0 0 3 * * *")
    public void tokenOwnerCollect() {

        Integer callCount = 0;
        String cursor = null;

        log.info("=========================token owner collect start=========================");
        while (callCount == 0 || !StringUtils.isEmpty(cursor)) {
            NftItemResponse<NftItem> res = klaytnClient.tokenInfo(xChainId, mtdzContractAddress, DEFAULT_SIZE, cursor);
            cursor = updateNftOwner(res);
            callCount++;
        }
        log.info("=========================token owner collect end===========================");

    }
    
    // 오픈씨 바닥가 수집
    @Scheduled(fixedDelay = 300000)
    public void getOpenseaFloorPrice() {
    	String res = bigDragonClient.getFloorFp();
    	if (res != null) {
    		Double fp = Double.valueOf(res);
    		log.info("opensea fp = {}", fp);
    		TokenFloorPrice tokenFloorPrice = new TokenFloorPrice();
    		tokenFloorPrice.setType("MTDZ");
    		tokenFloorPrice.setPlatform("OPENSEA");
    		tokenFloorPrice.setFloorPrice(fp);
    		tokenFloorPriceRepository.save(tokenFloorPrice);
    	}
    }
    
    // 팔라 바닥가 수집
    @Scheduled(fixedDelay = 300000)
    public void getPalaFloorPrice() {
    	PalaResponse res = palaClient.getProjectInfo(mtdzContractAddress);
    	if (res != null) {
    		Double fp = Double.valueOf(res.getFloorPriceInKlay().replace("000000000000000000", ""));
    		log.info("pala fp = {}", fp);
    		TokenFloorPrice tokenFloorPrice = new TokenFloorPrice();
    		tokenFloorPrice.setType("MTDZ");
    		tokenFloorPrice.setPlatform("PALA");
    		tokenFloorPrice.setFloorPrice(fp);
    		tokenFloorPriceRepository.save(tokenFloorPrice);
    	}
    }

    @Transactional
    public String updateNftOwner(NftItemResponse res) {
        log.info("res : {}", res);
        if (res == null) {
            return null;
        }
        List<NftItem> nftItemList = res.getItems();
        for (NftItem nftItem: nftItemList) {
            log.info("nftItem : {}", nftItem);
            TokenInfo tokenInfo = tokenInfoRepository.findByTokenId(Integer.decode(nftItem.getTokenId()));
            if (tokenInfo != null) {
                tokenInfo.setOwner(nftItem.getOwner());
                tokenInfoRepository.save(tokenInfo);
            } else {
                log.info("token {} is not exists", Integer.decode(nftItem.getTokenId()));
            }
        }

        return res.getCursor();
    }
}
