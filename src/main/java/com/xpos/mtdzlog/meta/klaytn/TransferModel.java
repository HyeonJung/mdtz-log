package com.xpos.mtdzlog.meta.klaytn;

import lombok.Data;

@Data
public class TransferModel {
    private String transferType;
    private TransferTransactionModel transaction;
    private ContractModel contract;
    private String from;
    private String to;
    private String tokenId;
}
