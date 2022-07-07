package com.xpos.mtdzlog.meta.klaytn;

import lombok.Data;

import java.util.List;

@Data
public class NftItemResponse<V> {
    private List<V> items;
    private String cursor;
}
