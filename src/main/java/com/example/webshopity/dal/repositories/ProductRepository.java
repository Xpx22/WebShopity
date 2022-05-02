package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Optional<List<Product>> findByNameContains(String productname);
    Optional<List<Product>> findByCategoryContains(String category);
    Optional<List<Product>> findByManufacturerContains(String manufacturer);

    Optional<List<Product>> findTop10By();

}
