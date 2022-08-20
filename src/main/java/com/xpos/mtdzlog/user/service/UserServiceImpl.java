package com.xpos.mtdzlog.user.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dao.repository.UserRepository;
import com.xpos.mtdzlog.user.dto.SignUpDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Users getByWalleteAddress(String walletAddress) {
		return userRepository.findByWalletAddress(walletAddress);
	}
	
	@Override
	public long countById(String id) {
		return userRepository.countById(id);
	}
	
	@Override
	public long countByName(String name) {
		return userRepository.countByName(name);
	}
	
	@Override
	public long countByWalletAddress(String walletAddress) {
		return userRepository.countByWalletAddress(walletAddress);
	}
	
	@Override
	public Users signUp(SignUpDTO signUpDTO) {
		Users users = new Users();
		users.setId(signUpDTO.getId());
		users.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
		users.setRegDate(new Date());
		users.setName(signUpDTO.getName());
		users.setShowName(signUpDTO.isShowName());
		users.setWalletAddress(signUpDTO.getWalletAddress());
		users.setStatus("ON");

		return userRepository.save(users);
	}
}