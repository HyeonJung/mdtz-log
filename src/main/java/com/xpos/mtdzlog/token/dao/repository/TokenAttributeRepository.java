package com.xpos.mtdzlog.token.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpos.mtdzlog.token.TokenAttribute;
import com.xpos.mtdzlog.token.TokenAttributePk;

public interface TokenAttributeRepository extends JpaRepository<TokenAttribute, TokenAttributePk> {

}
