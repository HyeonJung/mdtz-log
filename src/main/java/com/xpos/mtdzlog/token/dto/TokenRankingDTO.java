package com.xpos.mtdzlog.token.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenRankingDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer seq;
    private String owner;
    private Integer tokenCount;
    private String tokenIds;
    private String maskingOwnerAddress;
}
