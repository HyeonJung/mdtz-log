package com.xpos.mtdzlog.token.dto;

import lombok.Data;

@Data
public class TokenInfoSearchRequest {

	private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_ROWS = 20;

    private int rows = DEFAULT_ROWS;
    private int page = DEFAULT_PAGE;
    private String grade;
    private String keyword;
}
