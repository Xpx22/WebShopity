package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("customers")
    public String getCustomerDirectory(Model model){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        model.addAttribute("customerList", customerList);
        return "customers/customer-directory.html";
    }

    @PostMapping("customers/save")
    public String SaveCustomerInfo(Model model, Customer customer){
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/update/{id}")
    public String DisplayUpdateProduct(@PathVariable("id") long id, Model model){
        Optional<Customer> c = customerRepository.findById(id);
        model.addAttribute("customer", c.get());
        return "customers/update-customer.html";
    }

    @GetMapping("/customers/delete/{id}")
    public String displayDeleteProduct(@PathVariable("id") long id){
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }
}
