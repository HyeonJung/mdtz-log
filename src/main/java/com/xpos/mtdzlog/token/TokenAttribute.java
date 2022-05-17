package com.xpos.mtdzlog.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Data
@Entity(name = "TOKEN_ATTRIBUTE")
@IdClass(TokenAttributePk.class)
public class TokenAttribute {
	
	@Id
	@Column(name = "TOKEN_INFO_ID")
	private Integer tokenInfoId;
	
	@Id
	@Column(name = "ATTRIBUTE_KEY")
	private String attributeKey;
	
	@Column(name = "ATTRIBUTE_VALUE")
	private String attributeValue;

}
