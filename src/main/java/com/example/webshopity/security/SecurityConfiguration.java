package com.example.webshopity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
        /*auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select email, password, enabled" + "from user_accounts where username = ?")
            .authoritiesByUsernameQuery("select email, role" + "from user_accounts where username = ?")
            .passwordEncoder(getPasswordEncoder());*/
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/customers/**").hasRole("ADMIN")
            /*.antMatchers("/customers/save").hasRole("ADMIN")
            .antMatchers("/customers/update/{id}").hasRole("ADMIN")
            .antMatchers("/customers/delete/{id}").hasRole("ADMIN")*/
            .antMatchers("/products/**").hasRole("ADMIN")
            /*.antMatchers("/products/create").hasRole("ADMIN")
            .antMatchers("/products/update/{id}").hasRole("ADMIN")
            .antMatchers("/products/delete/{id}").hasRole("ADMIN")
            .antMatchers("/products/save").hasRole("ADMIN")*/
            .antMatchers("/orders/**").hasRole("ADMIN")
            /*.antMatchers("/orders/update/{id}").hasRole("ADMIN")
            .antMatchers("/orders/delete/{id}").hasRole("ADMIN")*/
            .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true");
        http.logout().permitAll();
        http.csrf().disable().cors();
    }
}
