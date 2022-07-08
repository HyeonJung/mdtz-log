package com.xpos.mtdzlog.token.dto;

import java.util.List;

import lombok.Data;

@Data
public class TokenInfoSearchRequest {

	private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_ROWS = 60;

    private int rows = DEFAULT_ROWS;
    private int page = DEFAULT_PAGE;
    private String type = "MTDZ";
    private String grade;
    private String keyword;
    private String value;
    private List<String> values;
    private List<String> attributes;
    private List<String> gradeList;
    private String key;
    private String address;
    
    public Integer getLimit() {
    	return rows;
    }
    
    public Integer getOffset() {
    	return rows * (page - 1);
    }
}
