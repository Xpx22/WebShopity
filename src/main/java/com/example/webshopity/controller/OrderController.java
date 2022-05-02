package com.example.webshopity.controller;

import com.example.webshopity.SearchParam;
import com.example.webshopity.dal.entities.CustomerUserDetails;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.OrderItemRepository;
import com.example.webshopity.dal.repositories.OrderRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import com.example.webshopity.OrderSearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("orders")
    public String getOrderDirectory(Model model){
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderSearchParam", new OrderSearchParam());
        return "orders/order-directory.html";
    }

    @PostMapping("orders")
    public String searchOrderDirectory(Model model, OrderSearchParam osp){
        if(osp.getOrderNumber() == 0){
            List<Order> orderList = (List<Order>) orderRepository.findAll();
            model.addAttribute("orderList", orderList);
            model.addAttribute("orderSearchParam", new OrderSearchParam());
            return "orders/order-directory.html";
        }
        var result = orderRepository.findById((long) osp.getOrderNumber());
        if(result.isPresent()){
            model.addAttribute("orderList", result.get());
        }else {
            model.addAttribute("orderList", new ArrayList<Order>());
        }
        model.addAttribute("orderSearchParam", osp);
        return "orders/order-directory.html";
    }

    @PostMapping("orders/save")
    public String SaveOrderInfo(Model model, Order order){
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/update/{id}")
    public String DisplayUpdateOrder(@PathVariable("id") long id, Model model){
        Optional<Order> o = orderRepository.findById(id);
        model.addAttribute("order", o.get());
        return "orders/update-order.html";
    }
    @PostMapping("/orders/update/{id}")
    public String UpdateOrder(@PathVariable("id") long id, Order order){
        orderRepository.updateStatusById(order.getStatus(), id);
        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String displayDeleteOrder(@PathVariable("id") long id){
        Order o = new Order();
        o.setId(id);
        orderItemRepository.deleteByOrder(o);
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }


    @GetMapping("user/orders")
    public String displayUserOrders(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();
        var customer = customerRepository.findById(customerUser.getId());

        model.addAttribute("orderList", customer.get().getOrderList());
        return "user-orders.html";
    }

    @GetMapping("user/orders/{oid}")
    public String displayUserOrderProducts(@PathVariable("oid") long oid, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerUserDetails customerUser = (CustomerUserDetails) auth.getPrincipal();
        var customer = customerRepository.findById(customerUser.getId());

        //todo dont know why cant find by id
        //var orderList = orderRepository.findByCustomer(customer.get().getId());
        List<Product> productList = new ArrayList<>();
        var orderItemList = orderItemRepository.findProductsByOrderId(oid);

        for (int i = 0; i < orderItemList.get().size(); i++){
            productList.add(orderItemList.get().get(i).getProduct());
        }
        /*
        for (int i = 0; i < orderList.get().size(); i++){
            var product = productRepository.findById(orderList.get().get(i).getProduct().getId());
            productList.add(product.get());
        }*/

        //model.addAttribute("productList", customer.get().getOrderList().get((int) oid - 1).getProductList());
        model.addAttribute("productList", productList);
        return "user-orders-products.html";
    }
}
