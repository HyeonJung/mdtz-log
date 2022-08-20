package com.xpos.mtdzlog.user.dto;

import lombok.Data;

@Data
public class LoginDTO {
	private String id;
	private String password;
	private String walletAddress;
}
