package com.xpos.mtdzlog.token.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dao.repository.search.TokenInfoRepositorySearch;

public interface TokenInfoRepository extends JpaRepository<TokenInfo, Integer>, TokenInfoRepositorySearch {
 
}
