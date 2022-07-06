package com.xpos.mtdzlog.meta.klaytn;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NftItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String tokenId;
    private String owner;
    private String previousOwner;
    private String tokenUri;
    private String transactionHash;
    private Date createdAt;
    private Date updatedat;
}
