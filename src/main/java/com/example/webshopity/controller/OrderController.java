package com.example.webshopity.controller;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.OrderRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("orders")
    public String getCustomerDirectory(Model model){
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        model.addAttribute("orderList", orderList);
        return "orders/order-directory.html";
    }

    @PostMapping("orders/save")
    public String SaveCustomerInfo(Model model, Order order){
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/update/{id}")
    public String DisplayUpdateProduct(@PathVariable("id") long id, Model model){
        Optional<Order> o = orderRepository.findById(id);
        model.addAttribute("order", o.get());
        return "orders/update-order.html";
    }

    @GetMapping("/orders/delete/{id}")
    public String displayDeleteProduct(@PathVariable("id") long id){
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }
}
