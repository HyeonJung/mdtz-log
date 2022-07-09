package com.xpos.mtdzlog.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity(name = "TOKEN_FLOOR_PRICE")
@Data
@IdClass(TokenFloorPricePk.class)
public class TokenFloorPrice {
	
	@Id
	@Column(name = "TYPE")
	private String type;
	
	@Id
	@Column(name = "PLATFORM")
	private String platform;
	
	@Column(name = "FLOOR_PRICE")
	private Double floorPrice;

}
