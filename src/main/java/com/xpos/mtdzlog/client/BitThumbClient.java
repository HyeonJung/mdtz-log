package com.xpos.mtdzlog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.xpos.mtdzlog.config.KlaytnFeignClientConfiguration;
import com.xpos.mtdzlog.meta.bitThumb.BitThumbResponse;

@FeignClient(name="bitThumb", url = "${bitThumb.api.host}", configuration = KlaytnFeignClientConfiguration.class)
public interface BitThumbClient {

	@GetMapping(value = "/public/ticker/KLAY_KRW")
	BitThumbResponse getKlayPrice();
}
