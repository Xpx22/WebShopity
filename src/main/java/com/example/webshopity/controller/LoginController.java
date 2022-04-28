package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/userlogin")
    public String displayLogin(Model model){
        Customer customer = new Customer();
        model.addAttribute("loginForm", customer);
        return "login.html";
    }

    @PostMapping("/userlogin")
    public String submitLogin(Customer customer, Model model){
        if("admin".equals(customer.getEmail()) && "admin".equals(customer.getPassword())){
            return "admin-dashboard.html";
        }
        Optional<Customer> customerTemp = customerRepository.findByEmailAndPassword(customer.getEmail(), customer.getPassword());
        if(customerTemp.isPresent()){
            return "redirect: index.html";
        }

        model.addAttribute("invalidCredentials", true);
        return "login.html";
    }
}
