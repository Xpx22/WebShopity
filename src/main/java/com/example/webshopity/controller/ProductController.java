package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CartItemRepository;
import com.example.webshopity.dal.repositories.OrderItemRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final OrderItemRepository orderItemRepository;

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
    public String SaveProduct(Product product){
        var cartItemTemp = cartItemRepository.findByProduct(product);
        if (cartItemTemp.isPresent()){
            cartItemTemp.get().setProduct(product);
            cartItemRepository.save(cartItemTemp.get());
        }

        var orderItemTemp = orderItemRepository.findByProduct(product);
        if (orderItemTemp.isPresent()){
            orderItemTemp.get().setProduct(product);
            orderItemRepository.save(orderItemTemp.get());
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{id}")
    public String DisplayUpdateProduct(@PathVariable("id") long id, Model model){
        Optional<Product> p = productRepository.findById(id);
        model.addAttribute("product", p.get());
        return "products/update-product.html";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        Product prod = new Product();
        prod.setId(id);
        var cartItemTemp = cartItemRepository.findByProduct(prod);
        if (cartItemTemp.isPresent()){
            cartItemTemp.get().setProduct(prod);
            cartItemRepository.delete(cartItemTemp.get());
        }

        var orderItemTemp = orderItemRepository.findByProduct(prod);
        if (orderItemTemp.isPresent()){
            orderItemTemp.get().setProduct(prod);
            orderItemRepository.delete(orderItemTemp.get());
        }
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
