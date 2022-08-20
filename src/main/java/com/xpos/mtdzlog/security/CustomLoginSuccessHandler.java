package com.xpos.mtdzlog.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.xpos.mtdzlog.user.Users;
import com.xpos.mtdzlog.user.dao.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Integer userNo = Integer.parseInt(authentication.getName());
        Users user = userRepository.findById(userNo).get();

        if (user != null) {
        	session.setAttribute("sessionUserNo", user.getUserNo());
        	session.setAttribute("sessionName", user.getName());
        	session.setAttribute("sessionProfileImgUrl", user.getProfileImgUrl());
        	user.setLastLoginDate(new Date());
        	userRepository.save(user);
        }

        String redirectUrl = (String) session.getAttribute("prevPage");
		
		if (redirectUrl != null) {
			session.removeAttribute("prevPage");
			getRedirectStrategy().sendRedirect(request, response, redirectUrl);
		}
		else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
    }
}
