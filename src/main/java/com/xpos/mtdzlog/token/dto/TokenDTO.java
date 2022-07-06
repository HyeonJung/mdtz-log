package com.xpos.mtdzlog.token.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TokenDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer tokenId;
	private String imageUrl;
	private String type;
	private String grade;
	private String description;
	private String owner;
	private List<TokenAttributesDTO> tokenAttributes;

}
