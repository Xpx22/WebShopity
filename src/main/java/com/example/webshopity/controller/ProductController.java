package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @GetMapping("products/create")
    public String displayCreateProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/create-product.html";
    }

    @PostMapping("products/create")
    public String SubmitCreateProduct(Model model){
        return "products/create-product.html";
    }

    @GetMapping("products/update")
    public String displayUpdateProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/update-product.html";
    }

    @PutMapping("products/update")
    public String SubmitUpdateProduct(Model model){
        return "products/update-product.html";
    }

    @GetMapping("products/delete")
    public String displayDeleteProduct(Model model){
        return "products/delete-product.html";
    }

    @DeleteMapping("products/delete")
    public String SubmitDeleteProduct(Model model){
        return "products/delete-product.html";
    }


}
