package com.example.webshopity.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                /*.antMatchers("/customers/update").hasRole("ADMIN")
                .antMatchers("/customers/delete").hasRole("ADMIN")
                .antMatchers("/products/create").hasRole("ADMIN")
                .antMatchers("/products/update").hasRole("ADMIN")
                .antMatchers("/products/delete").hasRole("ADMIN")
                .antMatchers("/orders/update").hasRole("ADMIN")
                .antMatchers("/orders/delete").hasRole("ADMIN")*/
                //.antMatchers("/orders").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}
