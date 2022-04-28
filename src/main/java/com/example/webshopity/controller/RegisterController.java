package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/register")
    public String displayRegister(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "register.html";
    }

    @PostMapping("/register")
    public String submitRegister(Customer customer, Model model){
        customerRepository.save(customer);
        return "redirect: index.html";
    }
}
