package com.xpos.mtdzlog.token.dto;

public enum MtdzGrade {

	/** 깡통 */
	canister("깡통"),
	
	/** 그라 */
	gradation("그라"),
	
	/** 유리 */
	glass("유리"),
	
	/** 조명 */
	light("조명"),
	
	/** 레전더리 */
	legendary("레전더리"),
	
	/** 스페셜 */
	special("스페셜");

	private String description;


	MtdzGrade(String description) {
		this.description = description;
	}


	public String getDescription() {
		return description;
	}
}
