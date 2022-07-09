package com.xpos.mtdzlog.token.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.token.TokenFloorPrice;
import com.xpos.mtdzlog.token.TokenFloorPricePk;

public interface TokenFloorPriceRepository extends JpaRepository<TokenFloorPrice, TokenFloorPricePk> {
	TokenFloorPrice findByTypeAndPlatform(String type, String platform);
}
