package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
