package com.xpos.mtdzlog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.xpos.mtdzlog.config.KlaytnFeignClientConfiguration;
import com.xpos.mtdzlog.meta.bigdragon.BigDragonResponse;

@FeignClient(name="bigdragon", url = "${bigdragon.api.host}", configuration = KlaytnFeignClientConfiguration.class)
public interface BigDragonClient {

	// 오픈씨 바닥가 조회
	@GetMapping(value = "/wallet/getMtdzFP")
	String getFloorFp();
}
