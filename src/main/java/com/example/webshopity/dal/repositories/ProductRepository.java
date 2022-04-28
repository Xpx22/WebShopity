package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<List<Product>> findByName(String productname);
    Optional<List<Product>> findByCategory(String category);
    Optional<List<Product>> findByManufacturer(String manufacturer);
}
