package com.xpos.mtdzlog.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "TOKEN_ATTRIBUTE")
public class TokenAttribute {
	
	@Id
	@Column(name = "TOKEN_INFO_ID")
	private Integer tokenInfoId;
	
	@Column(name = "ATTRIBUTE_KEY")
	private String attributeKey;
	
	@Column(name = "ATTRIBUTE_VALUE")
	private String attributeValue;

}
