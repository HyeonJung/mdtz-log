package com.xpos.mtdzlog.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "ROLE")
public class Role {

	@Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "ROLE_NM")
    private String roleName;
    

    @Column(name = "SEQ")
    private Integer seq;

}
