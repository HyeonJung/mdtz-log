package com.xpos.mtdzlog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.xpos.mtdzlog.config.KlaytnFeignClientConfiguration;
import com.xpos.mtdzlog.meta.pala.PalaResponse;

@FeignClient(name="pala", url = "${pala.api.host}", configuration = KlaytnFeignClientConfiguration.class)
public interface PalaClient {

	@GetMapping(value = "/projects/{contractAddress}")
	PalaResponse getProjectInfo(@PathVariable("contractAddress") String contractAddress);
}
