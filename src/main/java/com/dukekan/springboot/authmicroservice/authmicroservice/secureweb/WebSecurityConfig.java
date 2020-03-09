package com.dukekan.springboot.authmicroservice.authmicroservice.secureweb;

import com.dukekan.springboot.authmicroservice.authmicroservice.filters.AuthFilter;
import com.dukekan.springboot.authmicroservice.authmicroservice.filters.RedirectRememberingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedirectRememberingFilter redirectRememberingFilter;
    @Autowired
    private AuthFilter authFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(redirectRememberingFilter, CsrfFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/home", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request,
                                 response, authentication) -> {
                    authFilter.onSuccessLogin(request, response);
                })
                .permitAll()
                .and()
                .logout()
                .permitAll()
        ;
    }
}
