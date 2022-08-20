package com.xpos.mtdzlog.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xpos.mtdzlog.user.UserRole;
import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dao.repository.UserRepository;
import com.xpos.mtdzlog.user.dao.repository.UserRoleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Users user = userRepository.findByIdAndStatus(userId, "ON");
        
        if(user == null) {
            throw new UsernameNotFoundException(userId);
        }

        Collection<GrantedAuthority> userRoles = new ArrayList<>();
        List<UserRole> userRoleList = userRoleRepository.findByUserNo(user.getUserNo());
        for (UserRole role: userRoleList) {
            userRoles.add(new SimpleGrantedAuthority(role.getRole().getRoleName()));
        }

        return new CustomUserDetails(user, userRoles);
    }
}
