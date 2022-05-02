package com.example.webshopity.controller;

import com.example.webshopity.SearchParam;
import com.example.webshopity.dal.entities.*;
import com.example.webshopity.dal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    @GetMapping("cart")
    public String cartPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();
        var customer = customerRepository.findById(customerUser.getId());

        var cartItemsList = cartItemRepository.findByCustomer(customer.get());
        if (cartItemsList.isPresent() && cartItemsList.get().size() < 1){
            model.addAttribute("productList", new ArrayList<Product>());
            model.addAttribute("totalSum", 0);
            return "cart.html";
        }

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < cartItemsList.get().size(); i++){
            Long productIdTemp = cartItemsList.get().get(i).getProduct().getId();
            var productToAdd = productRepository.findById(productIdTemp);
            productList.add(productToAdd.get());
        }

        int totalSum = 0;
        for (int i = 0; i < productList.size(); i++){
            totalSum += productList.get(i).getPrice();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("totalSum", totalSum);
        return "cart.html";
    }

    @GetMapping("cart/add/{pid}")
    public String addToCart(Model model, @PathVariable("pid") Long pid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();
        var customer = customerRepository.findById(customerUser.getId());
        var product = productRepository.findById(pid);
        var cartItem = cartItemRepository.findByCustomerAndProduct(customer.get(), product.get());
        if(cartItem.isEmpty()) {
            CartItem ci = new CartItem();
            ci.setCustomer(customer.get());
            ci.setProduct(product.get());
            cartItemRepository.save(ci);
        }

        model.addAttribute("searchParam", new SearchParam());
        var productListTemp = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        productListTemp.forEach(productList::add);
        model.addAttribute("productList", productList);
        model.addAttribute("addedToCart", true);
        return "index.html";
    }

    @GetMapping("cart/delete/{pid}")
    public String removerFromCart(Model model, @PathVariable("pid") Long pid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();
        var customer = customerRepository.findById(customerUser.getId());
        var product = productRepository.findById(pid);
        cartItemRepository.deleteByCustomerAndProduct(customer.get().getId(), product.get().getId());
        return "redirect:/cart";
    }

    @GetMapping("cart/order")
    public String placeOrder(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();

        List<Product> productList = new ArrayList<>();

        var customer = customerRepository.findById(customerUser.getId());
        var cartItemList = cartItemRepository.findByCustomer(customer.get());
        for (int i = 0; i < cartItemList.get().size(); i++){
            Long prodId = cartItemList.get().get(i).getProduct().getId();
            var productTemp = productRepository.findById(prodId);
            productList.add(productTemp.get());
            System.out.println("product: " + productList.get(i).getName());
        }


        int orderSum = 0;
        for (var product: productList){
            orderSum = orderSum + product.getPrice();


        }

/*
        for (int i = 0; i < productList.size(); i++){
            Order order = new Order();
            order.setCustomer(customer.get());
            order.setStatus("Submitted");
            order.setProduct(productList.get(i));
            order.setOrderSum(orderSum);
            customer.get().getOrderList().add(order);
            orderRepository.save(order);
        }*/

        Order order = new Order();
        order.setCustomer(customer.get());
        order.setStatus("Submitted");
        //order.setProductList(productList);
        order.setOrderSum(orderSum);
        customer.get().getOrderList().add(order);
        orderRepository.save(order);

        for (int i = 0; i < productList.size(); i++){
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(productList.get(i));
            orderItemRepository.save(oi);
        }

        cartItemRepository.deleteByCustomer(customer.get());
        return "redirect:/";
    }

}
