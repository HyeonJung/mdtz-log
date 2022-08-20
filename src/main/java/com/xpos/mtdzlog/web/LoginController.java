package com.xpos.mtdzlog.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dto.LoginDTO;
import com.xpos.mtdzlog.user.dto.SignUpDTO;
import com.xpos.mtdzlog.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	private UserService userServiceImpl;
	
	/**
	 * 카이카스로 로그인
	 * @param session
	 * @param walletAddress
	 * @return
	 */
	@PostMapping("/walletLogin")
	public String walletLogin(HttpSession session, @ModelAttribute LoginDTO loginDTO) {
		log.info("walletLogin loginDTO = {}", loginDTO);
		if (StringUtils.isEmpty(loginDTO.getWalletAddress())) {
			return "redirect:/login";
		}
		session.setAttribute("walletAddress", loginDTO.getWalletAddress());
		Users user = userServiceImpl.getByWalleteAddress(loginDTO.getWalletAddress());
		if (user != null) {
			log.info("이미 가입된 계정 walletAddress = {}, redirect login", loginDTO.getWalletAddress());
			return "redirect:/doWalletLogin";
		} else {
			log.info("미가입 계정 walletAddress = {}, redirect signUp", loginDTO.getWalletAddress());
			return "redirect:/signUp";
		}
	}
	
	/**
	 * 회원가입 페이지
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/signUp")
	public String signUp(HttpSession session, Model model) {
		if (session.getAttribute("walletAddress") != null) {
			String walletAddress = (String) session.getAttribute("walletAddress");
			model.addAttribute("walletAddress", walletAddress);
		}
		return "user/signUp";
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(HttpSession session, @ModelAttribute SignUpDTO signUpDTO) {
		String walletAddress = session.getAttribute("walletAddress") != null ? (String) session.getAttribute("walletAddress") : null;
		signUpDTO.setWalletAddress(walletAddress);
		
		log.info("signUp signUpDTO = {}", signUpDTO);
		
		// 1. 아이디 중복 체크
		long idCount = userServiceImpl.countById(signUpDTO.getId());
		if (idCount > 0) {
			return new ResponseEntity<>("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 2. 닉네임 중복 체크
		long nameCount = userServiceImpl.countByName(signUpDTO.getName());
		if (nameCount > 0) {
			return new ResponseEntity<>("중복된 아이디 입니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 3. 지갑 중복 체크
		if (!StringUtils.isEmpty(signUpDTO.getWalletAddress())) {
			long walletAddressCount = userServiceImpl.countByWalletAddress(signUpDTO.getWalletAddress());
			if (walletAddressCount > 0) {
				return new ResponseEntity<>("중복된 지갑주소 입니다.", HttpStatus.BAD_REQUEST);
			}
		}
		
		// 4. 비밀번호 일치 체크
		if (!signUpDTO.getPassword().equals(signUpDTO.getPasswordConfirm())) {
			return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(userServiceImpl.signUp(signUpDTO).getUserNo(), HttpStatus.OK);
	}
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public String login(HttpSession session) {
		session.removeAttribute("walletAddress");
		return "login";
	}
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/doWalletLogin")
	public String doWalletLogin() {
		return "doWalletLogin";
	}

}
