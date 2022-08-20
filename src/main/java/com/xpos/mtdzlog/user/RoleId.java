package com.xpos.mtdzlog.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleId {

	ROLE_ADMIN(1, "관리자"),
    ROLE_USER(2, "일반사용자");

    private Integer roleId;
    private String roleName;

    RoleId(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getCode() {
        return this.name();
    }

}
