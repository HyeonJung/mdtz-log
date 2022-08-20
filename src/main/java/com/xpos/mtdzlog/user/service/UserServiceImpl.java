package com.xpos.mtdzlog.user.service;

import java.util.Date;
import java.util.Optional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.token.dao.mapper.TokenDAO;
import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dao.repository.UserRepository;
import com.xpos.mtdzlog.user.dto.SignUpDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Override
	public Optional<Users> searchUser(Integer userNo) {
		return userRepository.findById(userNo);
	}
	
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
		Users user = new Users();
		user.setId(signUpDTO.getId());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
		user.setRegDate(new Date());
		user.setName(signUpDTO.getName());
		user.setShowName(signUpDTO.isShowName());
		user.setWalletAddress(signUpDTO.getWalletAddress());
		user.setStatus("ON");
		String imgUrl = "https://sandboxnetwork.mypinata.cloud/ipfs/QmYxdEZfLm8ms57TYBUHFsym9BeuqzspYqvDmYhYW7HUrJ";
		
		if (!StringUtils.isEmpty(user.getWalletAddress())) {
			imgUrl = tokenDAO.getDefaultImgUrl(user.getWalletAddress());
		}
		user.setProfileImgUrl(imgUrl);

		return userRepository.save(user);
	}
	
	@Override
	public Users connectKaikasWallet(Users user, String kaikasWallet) {
		user.setWalletAddress(kaikasWallet);
		if (!StringUtils.isEmpty(user.getWalletAddress())) {
			String imgUrl = tokenDAO.getDefaultImgUrl(user.getWalletAddress());
			if (!StringUtils.isEmpty(imgUrl)) {
				user.setProfileImgUrl(imgUrl);
			}
		}
		return userRepository.save(user);
	}
}
