package com.xpos.mtdzlog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Order(1)
@Slf4j
public class WalletSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private WalletUserDetailService walletUserDetailService;

	@Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
	
	@Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(walletUserDetailService).passwordEncoder(new WalletPasswordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/wallet/**").csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/wallet/login")
                .defaultSuccessUrl("/main")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) // HTTP Session 초기화
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoderDefault() {
        return new WalletPasswordEncoder();
    }

    // 암호 인코더 커스텀 설정
    public static class WalletPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return true;
        }
    }
}
