package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/register")
    public String displayRegister(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("isPassWrong", false);
        return "register.html";
    }

    @PostMapping("/register")
    public String submitRegister(Customer customer, @RequestParam("confirm_password") String confirm, Model model){
        if (!Objects.equals(customer.getPassword(), confirm)){
            model.addAttribute("isPassWrong", true);
            model.addAttribute("confirm", confirm);
            return "register.html";
        }
        boolean isExist;
        var c = customerRepository.findByEmail(customer.getEmail());
        if(c.isEmpty()){
            customer.setRole("USER");
            customerRepository.save(customer);
            return "redirect:/login";
        }
        isExist = true;
        model.addAttribute("isExist", isExist);
        return "register.html";
    }
}
