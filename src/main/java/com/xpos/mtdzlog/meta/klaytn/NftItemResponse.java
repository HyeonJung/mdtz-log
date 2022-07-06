package com.xpos.mtdzlog.meta.klaytn;

import lombok.Data;

import java.util.List;

@Data
public class NftItemResponse {
    private List<NftItem> items;
    private String cursor;
}
