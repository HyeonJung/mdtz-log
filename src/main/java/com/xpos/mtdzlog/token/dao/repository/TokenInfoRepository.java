package com.xpos.mtdzlog.token.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.token.TokenInfo;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, Integer> {

	List<TokenInfo> findByIdIn(List<Integer> ids);
	TokenInfo findByTokenId(Integer tokenId);
}
