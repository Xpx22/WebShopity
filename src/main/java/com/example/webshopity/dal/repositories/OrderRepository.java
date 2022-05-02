package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Customer;
import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT o from Order o where o.customer.id = :customerId group by o.id")
    Optional<List<Order>> findByCustomer(@Param("customerId") long customerId);
    /*
    @Query("SELECT o from Order o where o.customer.id = :customerId")
    Optional<List<Product>> findBy(@Param("customerId") long customerId);*/

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateStatusById (@Param("status") String status, @Param("id") long id);
}
