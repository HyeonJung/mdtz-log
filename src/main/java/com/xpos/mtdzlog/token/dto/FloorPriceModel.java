package com.xpos.mtdzlog.token.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FloorPriceModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double openSeaFp;
	private Double palaFp;
	private Double klayPrice;
}
