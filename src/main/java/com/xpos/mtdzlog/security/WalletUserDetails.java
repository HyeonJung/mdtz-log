package com.xpos.mtdzlog.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.xpos.mtdzlog.user.Users;

public class WalletUserDetails extends User {

    private static final long serialVersionUID = 1L;

    public WalletUserDetails(Users user, Collection<GrantedAuthority> userRoles) {
        super(user.getUserNo().toString(), "kaikas", userRoles);
    }

}
