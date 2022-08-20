package com.xpos.mtdzlog.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xpos.mtdzlog.token.dto.TokenDTO;
import com.xpos.mtdzlog.token.dto.TokenInfoSearchRequest;
import com.xpos.mtdzlog.token.service.TokenInfoServiceImpl;
import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dto.LoginDTO;
import com.xpos.mtdzlog.user.dto.SignUpDTO;
import com.xpos.mtdzlog.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private TokenInfoServiceImpl tokenInfoServiceImpl;

	// 아이디 중복 체크
	@PostMapping(value = "/checkId")
	public ResponseEntity<?> checkId(@ModelAttribute SignUpDTO signUpDTO) {
		return new ResponseEntity<Long>(userServiceImpl.countById(signUpDTO.getId()), HttpStatus.OK);
	}
	
	// 아이디 중복 체크
	@PostMapping(value = "/checkName")
	public ResponseEntity<?> checkName(@ModelAttribute SignUpDTO signUpDTO) {
		return new ResponseEntity<Long>(userServiceImpl.countByName(signUpDTO.getName()), HttpStatus.OK);
	}
	
	// 내 정보
	@GetMapping(value  = "/info")
	public String info(Principal principal, Model model) {
		Integer userNo = Integer.parseInt(principal.getName());
		Optional<Users> userRes = userServiceImpl.searchUser(userNo);
		if (userRes.isEmpty()) {
			return "redirect:/logout";
		}
		
		Users user = userRes.get();
		model.addAttribute("user", user);
		
		if (!StringUtils.isEmpty(user.getWalletAddress())) {
			TokenInfoSearchRequest req = new TokenInfoSearchRequest();
			req.setAddress(user.getWalletAddress());
			req.setOwnerRows(200);
			List<TokenDTO> userCollectList = tokenInfoServiceImpl.getTokenByOwnerAddress(req);
			model.addAttribute("collectList", userCollectList);
		}
		
		return "user/info";
	}
	
	/**
	 * 카이카스 연결
	 * @param principal
	 * @param session
	 * @param loginDTO
	 * @return
	 */
	@PostMapping(value = "/connectKaikas")
	public ResponseEntity<?> connectKaikas (Principal principal, HttpSession session, @ModelAttribute LoginDTO loginDTO) {
		Integer userNo = Integer.parseInt(principal.getName());
		Users user = userServiceImpl.searchUser(userNo).get();
		if (!StringUtils.isEmpty(user.getWalletAddress())) {
			return new ResponseEntity<>("이미 지갑을 등록한 계정입니다.", HttpStatus.BAD_REQUEST);
		}
		log.info("connectKaikasWallet userNo = {}, walletAddress = {}", userNo, loginDTO.getWalletAddress());
		long walletAddressCount = userServiceImpl.countByWalletAddress(loginDTO.getWalletAddress());
		if (walletAddressCount > 0) {
			return new ResponseEntity<>("중복된 지갑주소 입니다.", HttpStatus.BAD_REQUEST);
		}
		userServiceImpl.connectKaikasWallet(user, loginDTO.getWalletAddress());
		session.setAttribute("sessionProfileImgUrl", user.getProfileImgUrl());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
