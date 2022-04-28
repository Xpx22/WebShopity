package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/login")
    public String displayLogin(Model model){
        Customer customer = new Customer();
        model.addAttribute("loginForm", customer);
        return "login.html";
    }

    @GetMapping("/logout")
    public String Logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String submitLogin(Customer customer, Model model){
        if("admin".equals(customer.getEmail()) && "admin".equals(customer.getPassword())){
            return "admin-dashboard.html";
        }
        Optional<Customer> customerTemp = customerRepository.findByEmailAndPassword(customer.getEmail(), customer.getPassword());
        if(customerTemp.isPresent()){
            return "redirect:/";
        }

        model.addAttribute("isExist", true);
        return "login.html";
    }
}
