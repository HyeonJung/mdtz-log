package com.xpos.mtdzlog.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.token.TokenInfo;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, Integer> {
 
}
