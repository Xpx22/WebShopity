package com.example.webshopity;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Product;
import com.example.webshopity.dal.repositories.CustomerRepository;
import com.example.webshopity.dal.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class WebShopityApplicationTests {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testCreateCustomer(){
        Customer c = new Customer();
        c.setUsername("Test name");
        c.setEmail("test@test.com");
        c.setPassword("123");
        customerRepository.save(c);
    }

    @Test
    public void testFindCustomerByID(){
        Optional<Customer> p = customerRepository.findById(1L);
        System.out.println(p.isPresent());
        if(p.isPresent()){
            System.out.println(p.get().getUsername());
        }
    }

    @Test
    public void testUpdateCustomer(){
        Optional<Customer> c = customerRepository.findById(1L);
        c.get().setUsername("New name");
        customerRepository.save(c.get());
    }
    @Test
    public void testDeleteCustomer(){
        customerRepository.deleteById(2L);
    }

    @Test
    public void testAddTenProduct(){
        for (int i = 0; i < 10;i++){
            Product p = new Product();
            p.setName("name"+i);
            p.setDescription("desc"+i);
            p.setCategory("category"+i);
            p.setManufacturer("manufacturer"+i);
            p.setPrice(i);
            productRepository.save(p);
        }
    }

    @Test
    public void testgetProducts(){
        Optional<Product> p = productRepository.findById(1L);
        System.out.println(p.isPresent());
        if(p.isPresent()){
            System.out.println(p.get().getName());
        }
    }

}
