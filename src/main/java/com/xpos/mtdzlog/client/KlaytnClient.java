package com.xpos.mtdzlog.client;

import com.xpos.mtdzlog.config.KlaytnFeignClientConfiguration;
import com.xpos.mtdzlog.meta.klaytn.NftItem;
import com.xpos.mtdzlog.meta.klaytn.NftItemResponse;
import com.xpos.mtdzlog.meta.klaytn.TransferModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="klaytn", url = "${klaytn.api.host}", configuration = KlaytnFeignClientConfiguration.class)
public interface KlaytnClient {

    /**
     * NFT 정보 조회
     * @param xChainId
     * @param contractAddress
     * @param size
     * @param cursor
     */
    @GetMapping(value = "v2/contract/nft/{contractAddress}/token")
    NftItemResponse<NftItem> tokenInfo(@RequestHeader("x-chain-Id") String xChainId, @PathVariable("contractAddress") String contractAddress
                   , @RequestParam(value = "size", required = false, defaultValue = "1000") Integer size
                   , @RequestParam(value = "cursor", required = false) String cursor);

    /**
     * NFT 전송내역 조회
     * @param xChainId
     * @param presets
     * @param size
     * @return
     */
    @GetMapping(value = "v2/transfer")
    NftItemResponse<TransferModel> transferInfo(@RequestHeader("x-chain-Id") String xChainId, @RequestParam("presets") Integer presets,
                                                @RequestParam(value = "size", required = false, defaultValue = "30") Integer size);


}
