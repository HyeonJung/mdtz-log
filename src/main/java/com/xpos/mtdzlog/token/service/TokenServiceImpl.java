package com.xpos.mtdzlog.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.token.dao.repository.TokenInfoRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenInfoRepository tokenInfoRepository;
}
