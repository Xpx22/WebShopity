package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.CartItem;
import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
     Optional<List<CartItem>> findByCustomer(Customer customer);

     Optional<CartItem> findByCustomerAndProduct(Customer customer, Product product);
     Optional<CartItem> findByProduct(Product product);

     @Transactional
     void deleteByCustomer(Customer customer);

     @Modifying
     @Transactional
     @Query("DELETE from CartItem ci where ci.customer.id = :customerId and ci.product.id = :productId")
     void deleteByCustomerAndProduct(@Param("customerId") long customerId, @Param("productId") long productId);
}
