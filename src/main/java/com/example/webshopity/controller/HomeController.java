package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model){
        /*for (int i = 0; i < 10;i++){
            Product p = new Product();
            p.setName("name"+i);
            p.setDescription("desc"+i);
            p.setCategory("category"+i);
            p.setManufacturer("manufacturer"+i);
            p.setPrice(i);
            productRepository.save(p);
        }*/
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

    @GetMapping("cart")
    public String cartPage(Model model){
        List<Product> productList = new ArrayList<>();
        Product p = new Product();
        p.setId(1L);
        p.setCategory("cat1");
        p.setDescription("desc1");
        p.setManufacturer("manu1");
        p.setName("name1");
        p.setPrice(2);
        productList.add(p);
        System.out.println(p.getId());
        int totalSum = p.getPrice();
        model.addAttribute("productList", productList);
        model.addAttribute("totalSum", totalSum);
        return "cart.html";
    }

    @GetMapping("add")
    public String errorPage(){
        return "error.html";
    }
}
