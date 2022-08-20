package com.xpos.mtdzlog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xpos.mtdzlog.user.dto.SignUpDTO;
import com.xpos.mtdzlog.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userServiceImpl;

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
}
