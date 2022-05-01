package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;


    @GetMapping("cart")
    public String cartPage(Model model, @AuthenticationPrincipal Customer customer){
        var customerTemp = customerRepository.findById(customer.getId());
        List<Order> orderList = customerTemp.get().getOrderList();
        //List<Product> productList = customerTemp.get().getOrderList();
        /*Product p = new Product();
        p.setId(1L);
        p.setCategory("cat1");
        p.setDescription("desc1");
        p.setManufacturer("manu1");
        p.setName("name1");
        p.setPrice(2);
        productList.add(p);
        System.out.println(p.getId());
        int totalSum = 0;
        for(Product product : productList){
            totalSum += product.getPrice();
        }
        model.addAttribute("productList", productList);
        model.addAttribute("totalSum", totalSum);*/
        return "cart.html";
    }
}
