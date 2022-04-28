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
        boolean isExist;
        var c = customerRepository.findByEmail(customer.getEmail());
        if(c.isEmpty()){
            customerRepository.save(customer);
            isExist = false;
            return "redirect:/";
        }
        isExist = true;
        model.addAttribute("isExist", isExist);
        return "register.html";
    }
}
