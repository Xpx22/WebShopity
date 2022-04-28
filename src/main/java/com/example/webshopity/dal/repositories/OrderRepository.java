package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByStatus(String status);
}
