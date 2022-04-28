package com.example.webshopity.service;

import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {
/*
    @Autowired
    private final CustomerRepository customerRepository;

    public AdminService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*return customerRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException(
                        String.format("user with email %s not found", email)
                ));*/
        //return new UserDetailsClass(user);
        return null;
    }
}
