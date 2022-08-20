package com.xpos.mtdzlog.user.service;

import java.util.Optional;

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
	
	/**
	 * 유저 검색
	 * @param userNo
	 * @return
	 */
	Optional<Users> searchUser(Integer userNo);
	
	/**
	 * 지갑연결
	 * @param user
	 * @param kaikasWallet
	 * @return
	 */
	Users connectKaikasWallet(Users user, String kaikasWallet);
}
