package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CartItemRepository;
import com.example.webshopity.dal.repositories.OrderItemRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AjaxController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @ResponseBody
    @PostMapping("products/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product prod) {
        var cartItemTemp = cartItemRepository.findByProduct(prod);
        if (cartItemTemp.isPresent()){
            cartItemTemp.get().setProduct(prod);
            cartItemRepository.save(cartItemTemp.get());
        }

        var orderItemTemp = orderItemRepository.findByProduct(prod);
        if (orderItemTemp.isPresent()){
            orderItemTemp.get().setProduct(prod);
            orderItemRepository.save(orderItemTemp.get());
        }
        productRepository.save(prod);
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }
    @GetMapping("allproducts")
    @ResponseBody
    public Iterable<Product> searchProducts(@RequestParam String currentPage,
                                            @RequestParam(required = false) String productname,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false) String manufacturer){
        int offset = Integer.parseInt(currentPage)*10;
        if(productname != null){
            return productRepository.findByNameContains(productname, offset);
        }
        else if (category != null){
            return productRepository.findByCategoryContains(category, offset);
        }
        else if (manufacturer != null){
            return productRepository.findByManufacturerContains(manufacturer, offset);
        }
        return productRepository.findAllNormal(offset);
    }
}