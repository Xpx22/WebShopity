package com.example.webshopity.controller;

import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/")
    public String getCustomer(){
        return "test";
    }

    @PostMapping("customer/create")
    public void postCustomer(){}

    @PutMapping("customer/update/{id}")
    public void updateCustomer(@PathVariable("id") Long id){
        //long customerId = Long.parseLong(id);
        var customer = customerRepository.findById(id);
    }

    @DeleteMapping("/customers/delete/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        //long customerId = Long.parseLong(id);
        customerRepository.deleteById(id);
    }
}
