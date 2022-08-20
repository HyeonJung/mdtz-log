package com.xpos.mtdzlog.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.user.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	long countById(String id);
	long countByName(String name);
	long countByWalletAddress(String walletAddress);
	Users findByWalletAddress(String walletAddress);
	Users findByWalletAddressAndStatus(String walletAddress ,String status);
	Users findByIdAndStatus(String id, String status);
}
