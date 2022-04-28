package com.example.webshopity.service;

import com.example.webshopity.dal.entities.AdminUser;
import com.example.webshopity.dal.repositories.AdminRepository;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*return adminRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException(
                        String.format("admin with username %s not found", username)
                ));*/
        return new AdminUser();
        //return null;
    }
}
