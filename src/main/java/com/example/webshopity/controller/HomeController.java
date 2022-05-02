package com.example.webshopity.controller;

import com.example.webshopity.SearchParam;
import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping({"/", "index"})
    public String index(Model model){
        model.addAttribute("searchParam", new SearchParam());
        var productListTemp = productRepository.findTop10By();
        //List<Product> productList = new ArrayList<>();
        //productListTemp.forEach(productList::add);
        model.addAttribute("productList", productListTemp.get());
        model.addAttribute("addedToCart", false);
        return "index.html";
    }

    /*@PostMapping(value = "/", params = {"category"})
    public String indexParam(@RequestParam String category){
        System.out.println(category.split(",")[1]);
        return "redirect:/";
    }
    @PostMapping(value = "/", params = {"product"})
    public String indexParamP(@RequestParam String product, Model model){
        System.out.println("after press: " + product);
        SearchParam sp = new SearchParam();
        sp.setProduct(product);
        model.addAttribute("searchParam", sp);
        if(Objects.equals(product, ",")){
            var productListTemp = productRepository.findTop10By();
            model.addAttribute("productList", productListTemp.get());
            model.addAttribute("addedToCart", false);
            return "index.html";
        }
        var result = productRepository.findByNameContains(product);
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        model.addAttribute("addedToCart", false);
        return "index.html";
    }
    @PostMapping(value = "/", params = {"manufacturer"})
    public String indexParamM(@RequestParam String manufacturer){
        System.out.println(manufacturer.split(",")[1]);
        return "index.html";
    }
*/
    @GetMapping("admin")
    public String adminDashboard(){
        return "admin-dashboard.html";
    }

    @GetMapping("genadmin")
    public String generateAdmin(){
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setEmail("admin");
        admin.setPassword("admin");
        admin.setRole("ADMIN");
        admin.setOrderList(null);
        customerRepository.save(admin);
        return "redirect:/";
    }

    @GetMapping("error")
    public String errorPage(){
        return "error.html";
    }

    @GetMapping("gen")
    public String generateProducts(Model model){
        model.addAttribute("searchParam", new SearchParam());
        for (int i = 0; i < 10;i++){
            Product p = new Product();
            p.setName("name"+i);
            p.setDescription("desc"+i);
            p.setCategory("category"+i);
            p.setManufacturer("manufacturer"+i);
            p.setPrice(i);
            productRepository.save(p);
        }
        return "redirect:/";
    }

    @GetMapping("genuser")
    public String generateUser(Model model){
        model.addAttribute("searchParam", new SearchParam());
        for (int i = 0; i < 10;i++){
            Customer c = new Customer();
            c.setUsername("user" + (i+1));
            c.setEmail("test"+(i+1)+"@test.com");
            c.setPassword("123");
            c.setRole("USER");
            customerRepository.save(c);
        }
        return "redirect:/";
    }

    @PostMapping("searchproducts")
    public String searchByProd(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            model.addAttribute("addedToCart", false);
            return "index.html";
        }
        var result = productRepository.findByNameContains(sp.getProduct());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        model.addAttribute("addedToCart", false);
        return "index.html";
    }
    @PostMapping("searchcategories")
    public String searchByCat(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            model.addAttribute("addedToCart", false);
            return "index.html";
        }
        var result = productRepository.findByCategoryContains(sp.getCategory());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        model.addAttribute("addedToCart", false);
        return "index.html";
    }
    @PostMapping("searchmanufacturers")
    public String searchByManu(Model model, SearchParam sp){
        if(Objects.equals(sp.getProduct(), "")){
            var productListTemp = productRepository.findAll();
            List<Product> productList = new ArrayList<>();
            productListTemp.forEach(productList::add);
            model.addAttribute("productList", productList);
            model.addAttribute("addedToCart", false);
            return "index.html";
        }
        var result = productRepository.findByManufacturerContains(sp.getManufacturer());
        if(result.isPresent()){
            model.addAttribute("productList", result.get());
        }else {
            model.addAttribute("productList", new ArrayList<Product>());
        }
        model.addAttribute("addedToCart", false);
        return "index.html";
    }
}
