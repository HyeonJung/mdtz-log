package com.xpos.mtdzlog.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TOKEN_FILTER")
public class TokenFilter {
	
	@Id
	@Column(name = "FILTER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer filterId;
	
	@Column(name = "ATTRIBUTE_KEY")
	private String attributeKey;
	
	@Column(name = "ATTRIBUTE_VALUE")
	private String attributeValue;
	
	@Column(name = "ALIAS")
	private String alias;

}
