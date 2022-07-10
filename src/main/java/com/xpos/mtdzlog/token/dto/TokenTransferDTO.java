package com.xpos.mtdzlog.token.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TokenTransferDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tokenId;
    private String imageUrl;
    private String grade;
    private String from;
    private String to;
    private Date transferDate;
    private Long blockNumber;
}
