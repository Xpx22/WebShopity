package com.example.webshopity.dal.entities;

import com.example.webshopity.dal.AppUserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String email;
    private String password;
    private String role;
    @Enumerated(EnumType.STRING)
    private AppUserRoleEnum appUserRoleEnum;
    private Boolean locked;
    private Boolean enabled;

    //private List<GrantedAuthority> authorities;

    public AppUser(){

    }

    public AppUser(String userName, String email, String password, String role, AppUserRoleEnum appUserRoleEnum, Boolean locked, Boolean enabled) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.appUserRoleEnum = appUserRoleEnum;
        this.locked = locked;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AppUserRoleEnum getAppUserRoleEnum() {
        return appUserRoleEnum;
    }

    public void setAppUserRoleEnum(AppUserRoleEnum appUserRoleEnum) {
        this.appUserRoleEnum = appUserRoleEnum;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRoleEnum.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
