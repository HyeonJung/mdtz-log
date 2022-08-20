package com.xpos.mtdzlog.token.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

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
    private String name;
    private List<TokenDTO> ownerTokenList;
}
