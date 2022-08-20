package com.xpos.mtdzlog.user.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SignUpDTO {
	
	@NotEmpty(message = "아이디를 입력해주세요.")
	private String id;
	
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	private String password;
	
	@NotEmpty(message = "비밀번호 확인을 입력해주세요.")
	private String passwordConfirm;
	
	private String walletAddress;
	
	@NotEmpty(message = "닉네임을 입력해주세요.")
	private String name;
	
	private boolean showName = true;
}
