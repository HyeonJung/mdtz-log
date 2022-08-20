package com.xpos.mtdzlog.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.xpos.mtdzlog.user.Users;

public class CustomUserDetails extends User {

    private static final long serialVersionUID = 1L;

    public CustomUserDetails(Users user, Collection<GrantedAuthority> userRoles) {
        super(user.getUserNo().toString(), user.getPassword(), userRoles);
    }
}
