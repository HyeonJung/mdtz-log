package com.xpos.mtdzlog.token.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TokenAttributesDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;

}
