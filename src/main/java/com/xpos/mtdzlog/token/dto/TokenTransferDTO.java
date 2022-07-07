package com.xpos.mtdzlog.token.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenTransferDTO {
    private Integer tokenId;
    private String imageUrl;
    private String grade;
    private String from;
    private String to;
    private Date transferDate;
    private Long blockNumber;
}
