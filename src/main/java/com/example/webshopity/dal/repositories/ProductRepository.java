package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<List<Product>> findByNameContains(String productname);
    Optional<List<Product>> findByCategoryContains(String category);
    Optional<List<Product>> findByManufacturerContains(String manufacturer);
}
