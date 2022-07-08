package com.xpos.mtdzlog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.xpos.mtdzlog.meta.MetaData;

@FeignClient(name = "mtdz", url = "${mtdz.json.url}")
public interface MtdzClient {

	// json 정보 불러오기
	@GetMapping(value = "/{tokenId}.json")
	MetaData getJsonData(@PathVariable("tokenId") Integer tokenId);
}
