package com.xpos.mtdzlog.user.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.user.UserRole;
import com.xpos.mtdzlog.user.UserRolePk;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePk> {
	List<UserRole> findByUserNo(Integer userNo);
}
