package com.example.webshopity.security;

import com.example.webshopity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminService adminService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(adminService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/customers").hasRole("ADMIN")
                .antMatchers("/customers/save").hasRole("ADMIN")
                .antMatchers("/customers/update/{id}").hasRole("ADMIN")
                .antMatchers("/customers/delete/{id}").hasRole("ADMIN")
                .antMatchers("/products").hasRole("ADMIN")
                .antMatchers("/products/create").hasRole("ADMIN")
                .antMatchers("/products/update/{id}").hasRole("ADMIN")
                .antMatchers("/products/delete/{id}").hasRole("ADMIN")
                .antMatchers("/products/save").hasRole("ADMIN")
                .antMatchers("/orders").hasRole("ADMIN")
                .antMatchers("/orders/update/{id}").hasRole("ADMIN")
                .antMatchers("/orders/delete/{id}").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}
