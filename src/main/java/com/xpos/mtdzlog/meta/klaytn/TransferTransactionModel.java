package com.xpos.mtdzlog.meta.klaytn;

import lombok.Data;

import java.util.Date;

@Data
public class TransferTransactionModel {
    private String from;
    private String to;
    private Long blockNumber;
    private String transactionHash;
    private Integer typeInt;
    private Date timestamp;
    private String value;
    private String feePayer;
    private Integer feeRatio;
}
