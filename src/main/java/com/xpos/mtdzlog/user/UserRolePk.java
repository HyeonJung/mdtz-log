package com.xpos.mtdzlog.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3078753290478999514L;
	
	private Integer userNo;
	private Integer roleId;

}
