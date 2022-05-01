package com.example.webshopity.service;

import com.example.webshopity.dal.entities.CustomerUserDetails;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new CustomerUserDetails(customer.get());
    }
}
