package com.datapine.webapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder amb) throws Exception {
        amb.inMemoryAuthentication()
                .withUser("User").password("user").roles("User")
                .and().withUser("Admin").password("admin").roles("Admin");
    }

    @Override
    protected void configure(HttpSecurity hs) throws Exception {
        hs.authorizeRequests()
                .antMatchers("/chart").hasAnyRole("User", "Admin")
                .antMatchers("/statistics").hasRole("Admin")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();
    }
}
