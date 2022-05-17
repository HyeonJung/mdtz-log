package com.xpos.mtdzlog.token.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xpos.mtdzlog.token.TokenInfo;
import com.xpos.mtdzlog.token.dao.repository.search.TokenInfoRepositorySearch;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;

public class TokenInfoRepositoryImpl extends QuerydslRepositorySupport implements TokenInfoRepositorySearch {

    private final JPAQueryFactory queryFactory;

    public TokenInfoRepositoryImpl(JPAQueryFactory queryFactory) {
        super(TokenInfo.class);
        this.queryFactory = queryFactory;
    }

	@Override
	public Page<TokenInfo> search(TokenInfoSearchRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

}
