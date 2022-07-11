package com.xpos.mtdzlog.token.dto;

import lombok.Data;

@Data
public class TokenRankingRatioModel {
    private String ratioName;
    private Integer tokenCount;
    private Integer holderCount;
    private Integer totalHolderCount;
}
