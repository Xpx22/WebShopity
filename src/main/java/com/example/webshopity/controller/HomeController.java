package com.example.webshopity.controller;


import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping({"/", "index"})
    public String index(Model model){
        var productListTemp = productRepository.findTop10By();
        model.addAttribute("productListSize", productListTemp.get().size());
        return "index.html";
    }

    @GetMapping("admin")
    public String adminDashboard(){
        return "admin-dashboard.html";
    }

    @GetMapping("genadmin")
    public String generateAdmin(){
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setEmail("admin");
        admin.setPassword("admin");
        admin.setRole("ADMIN");
        admin.setOrderList(null);
        customerRepository.save(admin);
        return "redirect:/";
    }

    @GetMapping("error")
    public String errorPage(){
        return "error.html";
    }

    @GetMapping("gen")
    public String generateProducts(){
        for (int i = 0; i < 10;i++){
            Product p = new Product();
            p.setName("name"+i);
            p.setDescription("desc"+i);
            p.setCategory("category"+i);
            p.setManufacturer("manufacturer"+i);
            p.setPrice(i);
            productRepository.save(p);
        }
        return "redirect:/";
    }

    @GetMapping("genuser")
    public String generateUser(){
        for (int i = 0; i < 10;i++){
            Customer c = new Customer();
            c.setUsername("user" + (i+1));
            c.setEmail("test"+(i+1)+"@test.com");
            c.setPassword("123");
            c.setRole("USER");
            customerRepository.save(c);
        }
        return "redirect:/";
    }
}
