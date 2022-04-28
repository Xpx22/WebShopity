package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("products")
    public String getProductDirectory(Model model){
        List<Product> productList = (List<Product>) productRepository.findAll();
        model.addAttribute("productList", productList);
        return "products/product-directory.html";
    }
    @GetMapping("products/create")
    public String displayCreateProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/create-product.html";
    }

    @PostMapping("/products/save")
    public String SaveProduct(Model model, Product product){
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{id}")
    public String DisplayUpdateProduct(@PathVariable("id") long id, Model model){
        Optional<Product> p = productRepository.findById(id);
        model.addAttribute("product", p.get());
        return "products/create-product.html";
    }

    @GetMapping("/products/delete/{id}")
    public String displayDeleteProduct(@PathVariable("id") long id){
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
