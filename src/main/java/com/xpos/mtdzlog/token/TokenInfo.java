package com.xpos.mtdzlog.token;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "TOKEN_INFO")
public class TokenInfo {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "TOKEN_ID")
	private Integer tokenId;
	
	@Column(name = "imageUrl")
	private String imageUrl;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "description")
	private String description;
	
	@Transient
	private List<TokenAttribute> tokenAttributes;
}
