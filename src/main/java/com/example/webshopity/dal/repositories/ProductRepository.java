package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query(value = "select * from products where products.name ~* :productname OFFSET :offset LIMIT 10", nativeQuery = true)
    Iterable<Product> findByNameContains(@Param("productname") String productname, @Param("offset") int offset);
    @Query(value = "select * from products where products.category ~* :category OFFSET :offset LIMIT 10", nativeQuery = true)
    Iterable<Product> findByCategoryContains(@Param("category") String category, @Param("offset") int offset);
    @Query(value = "select * from products where products.manufacturer ~* :manufacturer OFFSET :offset LIMIT 10", nativeQuery = true)
    Iterable<Product> findByManufacturerContains(@Param("manufacturer") String manufacturer, @Param("offset") int offset);

    @Query(value = "select * from products OFFSET :offset LIMIT 10", nativeQuery = true)
    Iterable<Product> findAllNormal(@Param("offset") int offset);

    Optional<List<Product>> findTop10By();

}
