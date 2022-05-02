package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AjaxController {

    @Autowired
    ProductRepository productRepository;

    @ResponseBody
    @PostMapping("products/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product prod) {
        Product product = prod;
        productRepository.save(product);
        return new ResponseEntity<Product>(prod, HttpStatus.OK);
    }

    @GetMapping(value = "getproducts", params = {"currentPage"})
    @ResponseBody
    public Iterable<Product> allProducts(@RequestParam int currentPage){
        Pageable pageAndSize = PageRequest.of(currentPage, 10);
        return productRepository.findAll(pageAndSize);
    }
}