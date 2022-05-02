package com.example.webshopity.controller;

import com.example.webshopity.CustomerSearchParam;
import com.example.webshopity.OrderSearchParam;
import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.OrderItemRepository;
import com.example.webshopity.dal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping("customers")
    public String getCustomerDirectory(Model model){
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        model.addAttribute("customerList", customerList);
        model.addAttribute("customerSearchParam", new CustomerSearchParam());
        return "customers/customer-directory.html";
    }

    @PostMapping("customers")
    public String searchCustomerDirectory(Model model, CustomerSearchParam csp){
        if(csp.getCustomerId() == 0){
            List<Customer> customerList = (List<Customer>) customerRepository.findAll();
            model.addAttribute("customerList", customerList);
            model.addAttribute("customerSearchParam", new CustomerSearchParam());
            return "customers/customer-directory.html";
        }
        var result = customerRepository.findById((long) csp.getCustomerId());
        if(result.isPresent()){
            model.addAttribute("customerList", result.get());
        }else {
            model.addAttribute("customerList", new ArrayList<Customer>());
        }
        model.addAttribute("customerSearchParam", csp);
        return "customers/customer-directory.html";
    }

    @PostMapping("customers/save")
    public String SaveCustomerInfo(Model model, Customer customer){
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/update/{id}")
    public String DisplayUpdateProduct(@PathVariable("id") long id, Model model){
        Optional<Customer> c = customerRepository.findById(id);
        model.addAttribute("customer", c.get());
        return "customers/update-customer.html";
    }

    @GetMapping("/customers/delete/{id}")
    public String displayDeleteProduct(@PathVariable("id") long id){
        var orderList = orderRepository.findByCustomer(id);
        if(orderList.isPresent()){
            for (int i = 0; i < orderList.get().size(); i++){
                Order o = new Order();
                o.setId(orderList.get().get(i).getId());
                orderItemRepository.deleteByOrder(o);
                orderRepository.delete(orderList.get().get(i));
            }
        }

        customerRepository.deleteById(id);
        return "redirect:/customers";
    }
}
