package com.xpos.mtdzlog.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "USER_ROLE")
@IdClass(UserRolePk.class)
@Getter
@Setter
public class UserRole {

	@Id
    @Column(name = "USER_NO")
    private Integer userNo;

    @Id
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", insertable = false, updatable = false)
    private Role role;
}
