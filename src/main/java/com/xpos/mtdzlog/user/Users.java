package com.xpos.mtdzlog.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "USERS")
@Data
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_NO")
	private Integer userNo;
	
	@Column(name = "ID")
	private String id;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "WALLET_ADDRESS")
	private String walletAddress;
	
	@Column(name = "REG_DATE")
	private Date regDate;
	
	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;
	
	@Column(name = "SHOW_NAME")
	private boolean showName;
	
	@Column(name = "PROFILE_IMG_URL")
	private String profileImgUrl;
	
	@Column(name = "STATUS")
	private String status;

}
