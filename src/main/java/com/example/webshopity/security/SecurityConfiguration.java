package com.example.webshopity.security;

import com.example.webshopity.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userDetailsService());
        authProvider.setPasswordEncoder(this.getPasswordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(this.authenticationProvider());

       /* auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");*/
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            //.antMatchers("/cart").authenticated().anyRequest().permitAll()
            .antMatchers("/admin").hasAuthority("ADMIN")
            .antMatchers("/customers/**").hasAuthority("ADMIN")
            /*.antMatchers("/customers/save").hasRole("ADMIN")
            .antMatchers("/customers/update/{id}").hasRole("ADMIN")
            .antMatchers("/customers/delete/{id}").hasRole("ADMIN")*/
            .antMatchers("/products/**").hasAuthority("ADMIN")
            /*.antMatchers("/products/create").hasRole("ADMIN")
            .antMatchers("/products/update/{id}").hasRole("ADMIN")
            .antMatchers("/products/delete/{id}").hasRole("ADMIN")
            .antMatchers("/products/save").hasRole("ADMIN")*/
            .antMatchers("/orders/**").hasAuthority("ADMIN")
            .antMatchers("/cart/**").hasAuthority("USER")
            /*.antMatchers("/orders/update/{id}").hasRole("ADMIN")
            .antMatchers("/orders/delete/{id}").hasRole("ADMIN")*/
            .antMatchers("/", "/**").permitAll();
        http.formLogin().permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true").permitAll();
        http.csrf().disable().cors();
    }
}
