package com.xpos.mtdzlog.token;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TokenFloorPricePk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String platform;
}
