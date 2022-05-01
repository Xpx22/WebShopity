package com.example.webshopity.controller;

import com.example.webshopity.SearchParam;
import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("searchParam", new SearchParam());
        //var productListIter = productRepository.findByCategory("category1");
        var productListTemp = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        productListTemp.forEach(productList::add);
        model.addAttribute("productList", productList);
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
    public String generateProducts(Model model){
        model.addAttribute("searchParam", new SearchParam());
        for (int i = 0; i < 10;i++){
            Product p = new Product();
            p.setName("name"+i);
            p.setDescription("desc"+i);
            p.setCategory("category"+i);
            p.setManufacturer("manufacturer"+i);
            p.setPrice(i);
            productRepository.save(p);
        }
        var productListTemp = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        productListTemp.forEach(productList::add);
        model.addAttribute("productList", productList);
        return "index.html";
    }

    @PostMapping("products")
    public String searchByProd(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            return "index.html";
        }
        var result = productRepository.findByNameContains(sp.getProduct());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        return "index.html";
    }
    @PostMapping("categories")
    public String searchByCat(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            return "index.html";
        }
        var result = productRepository.findByCategoryContains(sp.getCategory());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        return "index.html";
    }
    @PostMapping("manufacturers")
    public String searchByManu(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            return "index.html";
        }
        var result = productRepository.findByManufacturerContains(sp.getManufacturer());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        return "index.html";
    }
}
