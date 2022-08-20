package com.xpos.mtdzlog.user.service;

import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dto.SignUpDTO;

public interface UserService {

	/**
	 * 지갑주소로 계정조회
	 * @param walletAddress
	 * @return
	 */
	Users getByWalleteAddress(String walletAddress);
	
	long countById(String id);
	long countByName(String name);
	long countByWalletAddress(String walletAddress);
	
	/*
	 * 회원가입
	 */
	Users signUp(SignUpDTO signUpDTO);
}
