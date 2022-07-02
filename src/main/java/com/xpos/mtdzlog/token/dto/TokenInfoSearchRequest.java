package com.xpos.mtdzlog.token.dto;

import java.util.List;

import lombok.Data;

@Data
public class TokenInfoSearchRequest {

	private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_ROWS = 20;

    private int rows = DEFAULT_ROWS;
    private int page = DEFAULT_PAGE;
    private String type;
    private String grade;
    private String keyword;
    private String value;
    private List<String> values;
    private String key;
    
    public Integer getLimit() {
    	return rows;
    }
    
    public Integer getOffset() {
    	return (rows - 1) * page;
    }
}
