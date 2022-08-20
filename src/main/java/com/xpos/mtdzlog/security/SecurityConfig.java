package com.xpos.mtdzlog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.xpos.mtdzlog.security.WalletSecurityConfig.WalletPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
	
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false).userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").csrf().disable()
                .authorizeRequests()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/main")
                        .successHandler(customLoginSuccessHandler)
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true) // HTTP Session 초기화
        ;
	    
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security가 인증을 무시할 경로 설정
        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/lib/**", "/fonts/**", "/error");
    }
}
